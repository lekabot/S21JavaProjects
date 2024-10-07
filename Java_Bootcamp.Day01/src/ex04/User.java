package ex04;

public class User {
    private final int id;
    private String name;
    private int balance;
    private TransactionsLinkedList transactionsLinkedList;

    public User() {
        id = UserIdsGenerator.getInstance().generateId();
        balance = 0;
        transactionsLinkedList = new TransactionsLinkedList();
    }

    public User(String name, int balance) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        transactionsLinkedList = new TransactionsLinkedList();
        setBalance(balance);
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getBalance() {
        return this.balance;
    }
    public void setBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        } else {
            this.balance = balance;
        }
    }

    public TransactionsLinkedList getTransactionsList() {
        return transactionsLinkedList;
    }

    public void setTransactionsList(TransactionsLinkedList transactionsLinkedList) {
        this.transactionsLinkedList = transactionsLinkedList;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', balance=" + balance + '}';
    }
}
