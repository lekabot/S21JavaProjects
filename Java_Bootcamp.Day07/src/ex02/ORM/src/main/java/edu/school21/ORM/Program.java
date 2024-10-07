package edu.school21.ORM;

import io.github.cdimascio.dotenv.Dotenv;

public class Program {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        OrmManager ormManager = new OrmManager(
            dotenv.get("DB_URL"), dotenv.get("DB_USER"), dotenv.get("DB_PASSWORD")
        );

        try {
            User user = new User("Bob", "Libov", 22);
            ormManager.save(user);
            System.out.println(user);

            user.setFirstName("Aboba");
            ormManager.update(user);
            System.out.println(user);

            System.out.println("User with id=1\t" + ormManager.findById(1L,
                    User.class));

            ormManager.dropTable(User.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
