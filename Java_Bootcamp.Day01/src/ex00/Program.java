package ex00;

public class Program {
    public static void main(String[] args) {
        User user1 = new User(1, "John", 5000);
        User user2 = new User(2, "Mary", 15000);

        System.out.println("Users info before do transactions:\n");

        System.out.println(user1);
        System.out.println(user2 + "\n");

        System.out.println("Operation info:" + "\n");

        Transaction transaction1 = new Transaction(user1, user2, Transaction.Category.DEBIT, 5000);
        System.out.println(transaction1);

        System.out.println(user1);
        System.out.println(user2);

        Transaction transaction2 = new Transaction(user1, user2, Transaction.Category.CREDIT, -1500);
        System.out.println(transaction2);

        System.out.println(user1);
        System.out.println(user2);

        Transaction transaction3 = new Transaction(user2, user1, Transaction.Category.CREDIT, -1000);
        System.out.println(transaction3);

        System.out.println(user1);
        System.out.println(user2);

        Transaction transaction4 = new Transaction(user2, user1, Transaction.Category.DEBIT, 1500);
        System.out.println(transaction4);

        System.out.println(user1);
        System.out.println(user2);

    }
}
