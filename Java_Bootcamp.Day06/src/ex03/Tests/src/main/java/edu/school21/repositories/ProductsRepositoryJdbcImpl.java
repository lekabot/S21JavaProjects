package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    private DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> allProducts = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                allProducts.add(new Product(id, name, price));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return allProducts;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String query = "SELECT * FROM products WHERE id=?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Product product = new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price")
                );
                return Optional.of(product);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void update(Product product) {
        Long id = product.getId();
        String name = product.getName();
        BigDecimal price = product.getPrice();

        String query = "UPDATE products SET name = ?, price = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setBigDecimal(2, price);
            statement.setLong(3, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product with id " + id + " was updated successfully.");
            } else {
                System.out.println("No product found with id " + id + " to update.");
            }

        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
        }
    }

    @Override
    public void save(Product product) {
        Long id = product.getId();
        String name = product.getName();
        BigDecimal price = product.getPrice();

        String query = "INSERT INTO products (id, name, price) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.setString(2, name);
            statement.setBigDecimal(3, price);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product with id " + id + " was saved successfully.");
            } else {
                System.out.println("Failed to save product with id " + id + ".");
            }

        } catch (SQLException e) {
            System.err.println("Error saving product: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM products WHERE id=?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product with id " + id + " was deleted successfully.");
            } else {
                System.out.println("No product found with id " + id + ".");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
