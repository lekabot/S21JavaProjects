# Day 08 — Java bootcamp
### Spring

*Takeaways: Today you will learn about enterprise-level Java development and the basics of the Spring framework.*

💡 [Tap here](https://new.oprosso.net/p/4cb31ec3f47a4596bc758ea1861fb624) **to leave your feedback on the project**. It's anonymous and will help our team make your educational experience better. We recommend completing the survey immediately after the project.

# Contents
1. [Chapter I](#chapter-i) \
    1.1. [Preamble](#preamble)
2. [Chapter II](#chapter-ii) \
    2.1. [General Rules](#general-rules)
3. [Chapter III](#chapter-iii) \
    3.1. [Exercise 00 – Spring Context](#exercise-00-spring-context)
4. [Chapter IV](#chapter-iv) \
    4.1. [Exercise 01 – JdbcTemplate](#exercise-01-jdbctemplate)
5. [Chapter V](#chapter-v) \
    5.1. [Exercise 02 – AnnotationConfig](#exercise-02-annotationconfig)

# Chapter I
### Preamble
The Spring Framework is an integral part of most Java-based enterprise systems. This framework makes it much easier to configure applications and relate components to each other. As a result, a developer can fully focus on implementing business logic.

Spring's operating principle is completely based on DI/IoC patterns, which you should learn before using this technology.

The central concept in the Spring framework is a bean (component), which represents an object within an ApplicationContext container. The container also creates connections between beans.

There are several ways to configure beans:
1. Using an xml file.
2. Using a Java configuration (configuring with annotations).
3. Combined configuration.

XML configuration allows you to change the behavior of your application without reassembling it. In turn, Java configuration makes code more developer friendly.

![Sakura](misc/images/sakura.png)

# Chapter II
### General Rules
- Use this page as your only reference. Do not listen to rumors and speculations about how to prepare your solution.
- There is only one Java version for you, 1.8. Make sure you have the compiler and interpreter for this version installed on your machine.
- You can use the IDE to write and debug the source code.
- The code is more often read than written. Carefully read the [document](https://www.oracle.com/technetwork/java/codeconventions-150003.pdf) where code formatting rules are given. When performing any task, make sure you follow the generally accepted [Oracle Standards](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html).

- Comments are not allowed in the source code of your solution. They make it difficult to read the code.
- Be aware of the permissions of your files and directories.
- Your solution must be in your GIT repository to be evaluated.
- Your solutions will be evaluated by your fellow bootcampers.
- You should not leave any files in your src directory other than those explicitly specified in the exercise instructions. It is recommended that you modify your .gitignore to avoid accidents.
- If you need accurate output in your programs, it is forbidden to display precalculated output instead of running the exercise correctly.
- Got a question? Ask your neighbor to the right. Otherwise, try your neighbor on the left.
- Your reference guide: peers / Internet / Google. And one more thing. For every question you have, there's an answer on Stackoverflow. Learn how to ask questions properly.
- Read the examples carefully. They may require things not specified in the subject.
- Use System.out for output.
- And may the Force be with you!
- Never leave for tomorrow what you can do today. ;)

# Chapter III
### Exercise 00 — Spring Context

Exercise 00: Spring Context ||
---|---
Turn-in directory | ex00
Files to turn-in |  Spring-folder

Let's implement a loosely coupled system consisting of a set of components (beans) and conforming to IoC/DI principles.

Let's say there is a Printer interface designed to display a specific message.

This class has two implementations: PrinterWithDateTimeImpl and PrinterWithPrefixImpl. The first class prints messages by specifying the output date/time using LocalDateTime, while the second class can be used to set a text prefix for a message.

In turn, both printer implementations have a dependency on the Renderer interface, which sends messages to the console. Renderer also has two implementations: RendererStandardImpl (outputs a message via the standard System.out) and RendererErrImpl (outputs messages via System.err).

Renderer also has a dependency on the PreProcessor interface, which preprocesses messages. The implementation of PreProcessorToUpperImpl translates all letters to upper case, while the implementation of PreProcessorToLower translates all letters to lower case.

The UML diagram of the classes is shown below:

![Diagram of classes](misc/images/Diagram.png)

An example of code using these classes in a standard way:
```java
public class Main {
   public static void main(String[] args) {
       PreProcessor preProcessor = new PreProcessorToUpperImpl();
       Renderer renderer = new RendererErrImpl(preProcessor);
       PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
       printer.setPrefix("Prefix");
       printer.print("Hello!");
   }
}
```
Running this code will deliver the following result:

```
PREFIX HELLO!
```
You need to describe context.xml file for Spring, where all settings for each component and links between them will be specified.

Using these components with Spring looks as follows:
```java
public class Main {
   public static void main(String[] args) {
       ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
       Printer printer = context.getBean(“printerWithPrefix”, Printer.class);
       printer.print("Hello!");
   }
}
```

# Chapter IV
### Exercise 01 — JdbcTemplate

Exercise 01: JdbcTemplate ||
---|---
Turn-in directory | ex01
Files to turn-in |  Service-folder

JdbcTemplate and its extension NamedParameterJdbcTemplate are convenient mechanisms for working with databases. These classes eliminate the need to write template code for query execution and processing, as well as the need to catch exceptions during check.

In addition, they provide a convenient RowMapper concept for ResultSet processing and converting resulting tables to objects.

Now you need to implement the User model with the following fields
- Identifier;
- Email.

You also need to implement the `CrudRepository<T>` interface with the following methods
- `Optional<T>` findById(Long id)
- `List<T>` findAll()
- void save(T entity)
- void update(T entity)
- void delete(Long id)

The UsersRepository interface declared as UsersRepository extends CrudRepository<User> must contain the following method:
- `Optional<T>` findByEmail(String email)

In addition, two implementations of UsersRepository are required: UsersRepositoryJdbcImpl (uses standard Statements mechanisms) and UsersRepositoryJdbcTemplateImpl (based on JdbcTemplate/NamedParameterJdbcTemplate). Both classes accept DataSource object as constructor argument.

In the context.xml file, beans with different identifiers must be declared for both repository types, as well as two beans of DataSource type: DriverManagerDataSource and HikariDataSource.

In addition, the data for connecting to the DB must be specified in the db.properties file and included in the context.xml using `${db.url}` placeholders.

Example of db.properties:
```
db.url=jdbc:postgresql://localhost:5432/database
db.user=postgres
db.password=qwerty007
db.driver.name=org.postgresql.Driver
```

In Main class, operation of findAll method shall be demonstrated using both repositories:
```
ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
System.out.println(usersRepository.findAll());
usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
System.out.println(usersRepository.findAll());
```

**Project structure**:
- Service
    - src
        - main
            - java
                - school21.spring.service
                    - models
                        - User
                    - repositories
                        - CrudRepository
                        - UsersRepository
                        - UsersRepositoryJdbcImpl
                        - UsersRepositoryJdbcTemplateImpl
                    - application
                        - Main
            - resources
                -   db.properties
                -   context.xml
    -   pom.xml

# Chapter V
### Exercise 02 — AnnotationConfig

Exercise 02: AnnotationConfig ||
---|---
Turn-in directory | ex02
Files to turn-in |  Service-folder

Now you need to configure the configuration mechanisms of Spring applications using annotations. To do this, use the configuration class marked as @Configuration. Within this class, you need to describe beans for connecting to DataSource DB using @Bean annotation. As in the previous task, the connection data must be located in the db.properties file. You also need to make sure that context.xml does not exist.

Also implement UsersService/UsersServiceImpl interface/class pair with a dependency on UsersRepository declared in it. Insertion of the correct repository bean must be implemented using @Autowired annotation (similarly, you must bind DataSource inside repositories). Collisions in automatic binding are resolved with @Qualifier annotation.

Beans for UsersService and UsersRepository are defined using @Component annotation.

In UsersServiceImpl, implement a String signUp(String email) method that registers a new user and stores their details in the DB. This method returns a temporary password assigned to the user by the system (this information should also be stored in the database).

To verify that your service works correctly, implement an integration test for UsersServiceImp using an in-memory database (H2 or HSQLDB). The context configuration for the test environment (DataSource for the in-memory database) is described in a separate TestApplicationConfig class. This test checks whether a temporary password was returned in the signUp method.

**Project structure**:
- Service
    - src
        - main
            - java
                - school21.spring.service
                    - config
                        - ApplicationConfig
                    - models
                        - User
                    - repositories
                        - CrudRepository
                        - UsersRepository
                        - UsersRepositoryJdbcImpl
                        - UsersRepositoryJdbcTemplateImpl
                    - services
                        - UsersService
                        - UsersServiceImpl
                    - application
                        - Main
            - resources
                -   db.properties
        - test
            - java
                - school21.spring.service
                    - config
                        - TestApplicationConfig
                    - services
                        - UsersServiceImplTest
    -   pom.xml
