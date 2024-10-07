package edu.school21.reflection;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    private Scanner scanner;
    private static final String classesPath = "edu.school21.reflection.classes";

    private static final String separationLine = "---------------------------";
    private Class<?> currentClass = null;

    public static void main(String[] args) {
        Program program = new Program();
        try (Scanner scanner = new Scanner(System.in)) {
            Set<Class<?>> classes = program.findClassesInPackage(
                classesPath);
            program.printClassesNames(classes);
            System.out.println("Enter class name:");
            System.out.print("-> ");
            String className = scanner.nextLine();
            program.printClass(className);
            Object object = program.createObject(scanner);
            program.updateObject(object, scanner);
            program.callMethod(object, scanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callMethod(Object object, Scanner scanner) throws InvocationTargetException, IllegalAccessException {
        System.out.print("Enter name of the method for call:\n-> ");
        String methodToCall = scanner.nextLine();
        Method method = Stream.of(object.getClass().getDeclaredMethods())
                .filter(method1 -> (method1.getName() + "(" + Stream.of(method1.getParameterTypes())
                        .map(Class::getSimpleName)
                        .collect(Collectors.joining(", ")) + ")")
                        .equals(methodToCall))
                .findFirst()
                .orElse(null);
        if (method != null) {
            Class<?>[] paramsClass = method.getParameterTypes();
            Object[] parameters = new Object[paramsClass.length];
            for (int i = 0; i < parameters.length; i++) {
                System.out.println("Enter " + paramsClass[i].getSimpleName() + " value:");
                parameters[i] = castType(scanner.nextLine(), paramsClass[i]);
            }
            method.setAccessible(true);
            System.out.print("Method returned:\n" + method.invoke(object, parameters));
        } else {
            System.out.println("No such method");
        }
    }

    private void updateObject(Object object, Scanner scanner) throws IllegalAccessException {
        System.out.print("Enter name of the field for changing:\n-> ");
        String fieldToUpdate = scanner.nextLine();

        Field field = Arrays.stream(object.getClass()
                .getDeclaredFields())
                .filter(field1 -> field1.getName().equals(fieldToUpdate))
                .findFirst().orElse(null);
        if (field != null) {
            System.out.println("Enter " + field.getType().getSimpleName() + " value:");
            field.setAccessible(true);
            field.set(object, castType(scanner.nextLine(), field.getType()));
            System.out.println("Object update: " + object);
        } else {
            System.out.println("No such field");
        }
        System.out.println(separationLine);
    }

    private Object createObject(Scanner scanner) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Object object = null;
        System.out.println("Let’s create an object.");
        List<String> fieldsList = Stream.of(currentClass.getDeclaredFields())
                .map(Field::getName).toList();

        Constructor<?> constructorWithParam = Stream.of(currentClass.getConstructors())
                .filter(constructor -> constructor.getParameterTypes().length > 0)
                .findFirst().orElse(null);
        if (constructorWithParam != null) {
            Class<?>[] parameterTypes = constructorWithParam.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                System.out.print(fieldsList.get(i) + ":\n-> ");
                String input = scanner.nextLine();
                parameters[i] = castType(input, parameterTypes[i]);
            }
            object = constructorWithParam.newInstance(parameters);
            System.out.println("Object created: " + object);
            System.out.println(separationLine);
        }
        return object;
    }

    private Object castType(String data, Class<?> toClass) {
        if (toClass == int.class) {
            return Integer.parseInt(data);
        } else if (toClass == double.class) {
            return Double.parseDouble(data);
        } else if (toClass == boolean.class) {
            return Boolean.parseBoolean(data);
        } else {
            return data;
        }
    }

    private void printClass(String className) throws ClassNotFoundException {
        System.out.println(separationLine);

        Class<?> claszz = Class.forName(classesPath + "." + className);
        currentClass = claszz;

        System.out.println("fields:");

        for (Field field : claszz.getDeclaredFields()) {
            System.out.println("\t" + field.getType().getSimpleName() + " " + field.getName());
        }

        System.out.println("methods:");

        for (Method method : claszz.getDeclaredMethods()) {

            String params = Arrays.stream(method.getParameterTypes())
                    .map(Class::getSimpleName)
                    .collect(Collectors.joining(", "));

            System.out.println("\t" + method.getReturnType().getSimpleName()
                    + " " + method.getName() + "(" + params + ")");
        }
        System.out.println(separationLine);
    }

    private void printClassesNames(Set<Class<?>> classes) {
        System.out.println("Classes:");
        for (Class<?> c : classes) {
            System.out.println("  - " + c.getSimpleName());
        }
        System.out.println(separationLine);
    }

    private Set<Class<?>> findClassesInPackage(String s) {
        Reflections reflections = new Reflections(s, new SubTypesScanner(false));
        return new HashSet<>(reflections.getSubTypesOf(Object.class));
    }
}