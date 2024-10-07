package edu.school21.ORM;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrmManager {

    private Connection connection;

    private static final String pathToAnnotatedClasses = "edu.school21.ORM";

    public OrmManager(String url, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);

            for (Class<?> c : this.findAnnotatedClasses()) {
                createTable(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable(Class<?> c) throws SQLException {
        String tableName = c.getAnnotation(OrmEntity.class).table();
        StringBuilder query = new StringBuilder(
                "CREATE TABLE IF NOT EXISTS " + tableName + " ("
        );
        Field[] fields = c.getDeclaredFields();

        for (Field f : fields) {
            if (f.isAnnotationPresent(OrmColumnId.class)) {
                query.append("id SERIAL PRIMARY KEY, ");
            } else if (f.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn ormColumn = f.getAnnotation(OrmColumn.class);
                if (f.getType().getSimpleName().equals("String")) {
                    query.append(ormColumn.name() + " VARCHAR(" + ormColumn.length() + "), ");
                } else if (f.getType().getSimpleName().equals("Integer")) {
                    query.append(ormColumn.name() + " INTEGER, ");
                } else if (f.getType().getSimpleName().equals("Double")) {
                    query.append(ormColumn.name() + " DOUBLE PRECISION, ");
                } else if (f.getType().getSimpleName().equals("Boolean")) {
                    query.append(ormColumn.name() + " BOOLEAN, ");
                } else if (f.getType().getSimpleName().equals("Long")) {
                    query.append(ormColumn.name() + " BIGINT, ");
                }
            }
        }

        query.delete(query.length() - 2, query.length());
        query.append(");");

        PreparedStatement ps = connection.prepareStatement(query.toString());
        ps.executeUpdate();
    }

    private Set<Class<?>> findAnnotatedClasses() {
        Reflections reflection = new Reflections(pathToAnnotatedClasses,
                new SubTypesScanner(false));
        return reflection.getSubTypesOf(Object.class).stream()
                .filter(aClass -> aClass.isAnnotationPresent(OrmEntity.class))
                .collect(Collectors.toSet());
    }

    public void save(Object entity) throws IllegalAccessException, SQLException {
        String tableName = entity.getClass().getAnnotation(OrmEntity.class).table();

        List<Field> fields = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(OrmColumn.class))
                .toList();

        String columnName = fields.stream()
                .map(field -> field.getAnnotation(OrmColumn.class).name())
                .collect(Collectors.joining(", "));

        String placeholders = fields.stream()
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        String query = "INSERT INTO " + tableName + " (" + columnName + ") VALUES (" +
                placeholders + ")";

        PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            field.setAccessible(true);
            ps.setObject(i + 1, field.get(entity));
        }
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
            long id = generatedKeys.getInt(1);
            setIdToEntity(entity, id);
        }
    }

    private void setIdToEntity(Object entity, long id) throws IllegalAccessException {
        Field idField = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(OrmColumnId.class))
                .findFirst().orElse(null);

        if (idField != null) {
            idField.setAccessible(true);
            idField.set(entity, id);
        }
    }

    public void update(Object entity) throws SQLException, IllegalAccessException {
        String tableName = entity.getClass().getAnnotation(OrmEntity.class).table();

        List<Field> fields = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(OrmColumn.class))
                .toList();

        String setClause = fields.stream()
                .map(field -> field.getAnnotation(OrmColumn.class).name() + " = ?")
                .collect(Collectors.joining(", "));

        String query = "UPDATE " + tableName + " SET " + setClause +
                " WHERE id = ?";

        Field idField = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(OrmColumnId.class))
                .findFirst().orElse(null);

        PreparedStatement ps = connection.prepareStatement(query);
        int index = 1;
        for (Field field : fields) {
            field.setAccessible(true);
            ps.setObject(index++, field.get(entity));
        }
        if (idField != null) {
            idField.setAccessible(true);
            ps.setObject(index, idField.get(entity));
        }
        ps.executeUpdate();
    }

    public <T> T findById(Long id, Class<T> aClass) throws SQLException {
        String tableName = aClass.getAnnotation(OrmEntity.class).table();

        T entity = null;

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM " + tableName + " WHERE id = ?;"
            );

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                entity = createEntityFromResultSet(aClass, rs);
            }
        } catch (Exception e) {
            throw new SQLException("A user with id 1 is not find " + e.getMessage());
        }
        return entity;
    }

    public void dropTable(Class<?> aClass) {
        String tableName = aClass.getAnnotation(OrmEntity.class).table();
        try {
            PreparedStatement preparedStatement  = connection.prepareStatement(
                "DROP TABLE IF EXISTS " + tableName + ";"
            );
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T createEntityFromResultSet(Class<T> aClass, ResultSet rs) throws Exception {
        Constructor<T> constructor = aClass.getConstructor();
        constructor.setAccessible(true);
        T entity = constructor.newInstance();
        Field idField = Arrays.stream(aClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(OrmColumnId.class))
                .findFirst().orElse(null);
        if (idField != null) {
            idField.setAccessible(true);
            idField.set(entity, rs.getLong("id"));
        }
        List<Field> fields = Arrays.stream(aClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(OrmColumn.class))
                .toList();

        for (Field f : fields) {
            f.setAccessible(true);
            String columnName = f.getAnnotation(OrmColumn.class).name();
            Object value = rs.getObject(columnName);
            f.set(entity, value);
        }
        return entity;
    }

}
