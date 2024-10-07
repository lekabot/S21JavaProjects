package ex03;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User user1 = new User("User1", 5000);
        User user2 = new User("User2", 1000);

        TransactionsLinkedList transactionsLinkedList = user1.getTransactionsList();

        Transaction tr1 = new Transaction(user1, user2, Transaction.Category.CREDIT, -300);
        Transaction tr2 = new Transaction(user1, user2,Transaction.Category.CREDIT, -500);
        Transaction tr3 = new Transaction(user1, user2, Transaction.Category.DEBIT, 300);
        Transaction tr4 = new Transaction(user1, user2, Transaction.Category.DEBIT, 500);

        transactionsLinkedList.addTransaction(tr1);
        transactionsLinkedList.addTransaction(tr2);
        transactionsLinkedList.addTransaction(tr3);
        transactionsLinkedList.addTransaction(tr4);

        System.out.println("Count of transactions: " + user1.getTransactionsList().getSize());

        System.out.println("Remove third transaction");

        user1.getTransactionsList().removeTransactionByID(tr3.getId());

        System.out.println("Remove unknown transaction");

        try {
            user1.getTransactionsList().removeTransactionByID(UUID.randomUUID());
        } catch (TransactionNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Convert list to array and print");

        Transaction[] transactionsArr = user1.getTransactionsList().toArray();

        for(Transaction transaction : transactionsArr) {
            System.out.println(transaction);
        }

    }
}
