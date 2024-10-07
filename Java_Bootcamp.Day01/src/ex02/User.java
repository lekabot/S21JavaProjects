package ex02;

public class User {
    private final int id;
    private String name;
    private int balance;

    public User() {
        id = UserIdsGenerator.getInstance().generateId();
        balance = 0;
    }

    public User(String name, int balance) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
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

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', balance=" + balance + '}';
    }
}