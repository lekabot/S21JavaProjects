package ex02;

public class Program {
    public static void main(String[] args) {
        User[] users = new User[21];

        for(int i = 0; i < 21; i++) {
            users[i] = new User("User" + (i + 1), (i + 1000) * 2 / 5);
            System.out.println(users[i]);
        }

        UsersArrayList usersArrayList = new UsersArrayList();

        for (User user : users) {
            usersArrayList.addUser(user);
        }
        
        System.out.println("All users:");
        usersArrayList.printList();

        User userId = usersArrayList.getUserById(15);
        User userIndex = usersArrayList.getByIndex(15);
        System.out.println("Get user with id = 15:");
        System.out.println(userId);
        System.out.println("Get user with index = 15:");
        System.out.println(userIndex);

        try {
            User userError1 = usersArrayList.getUserById(50);
        } catch (UserNotFoundException exception) {
            exception.printStackTrace();
        }

        try {
            User userError2 = usersArrayList.getByIndex(100);
        } catch (UserNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
