package ex03;

import java.util.UUID;

public class Transaction {
    public enum Category {
        DEBIT, CREDIT
    }

    private final UUID id;
    private User recipient;
    private User sender;
    private Category category;
    private int amount;

    public Transaction(User sender, User recipient, Category category, int amount) {
        this.id = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.category = category;
        setAmount(amount);
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
    public Category getCategory() {
        return category;
    }

     public void setCategory(Category category) {
        this.category = category;
    }
    public int getAmount() {
        return amount;
    }

     public void setAmount(int transferAmount) {
        if (category == Category.CREDIT && (transferAmount > 0 || sender.getBalance() < transferAmount)) {
            this.amount = 0;
        } else if (category == Category.DEBIT && (transferAmount < 0 || recipient.getBalance() < transferAmount)) {
            this.amount = 0;
        } else {
            this.amount = transferAmount;
            changeUsersBalance(transferAmount);
        }
    }

    public void changeUsersBalance(int transferAmount) {
        if (category == Category.CREDIT) {
            sender.setBalance(sender.getBalance() - transferAmount);
            recipient.setBalance(recipient.getBalance() + transferAmount);
        } else {
            sender.setBalance(sender.getBalance() + transferAmount);
            recipient.setBalance(recipient.getBalance() - transferAmount);
        }
    }

    @Override
    public String toString() {
        return "Transaction{id='" + id + "', " +
                "sender=" + sender + ", " +
                "recipient=" + recipient + ", " +
                "category=" + category + ", " +
                "amount=" + amount + '}';
    }
}
