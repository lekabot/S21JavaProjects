package ex01;

public class Program {
    public static void main(String[] args) {
        User[] users = new User[21];
        for (int i = 0; i < 21; i++) {
            users[i] = new User("User" + (i + 1), (i + 1000) * 2 / 5);
            System.out.println(users[i]);
        }
    }
}
