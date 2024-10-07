package ex04;

import java.util.UUID;

public class TransactionsService {
    UsersArrayList usersList = new UsersArrayList();

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public int getUserBalance(int id) {
        for (int i = 0; i < usersList.getNumberOfUsers(); i++) {
            if (id == usersList.getUserById(i).getId()) {
                return usersList.getUserById(i).getBalance();
            }
        }
        throw new UserNotFoundException("User not found");
    }

    public int getUserBalance(User user) {
        return getUserBalance(user.getId());
    }

    public void executeTransaction(int senderId, int recipientId, int amount) {
        User sender = usersList.getUserById(senderId);
        User recipient = usersList.getUserById(recipientId);

        if (senderId == recipientId || sender.getBalance() < amount || amount < 0) {
            throw new IllegalTransactionException("Illegal transaction");
        }

        Transaction credit = new Transaction(sender, recipient, Transaction.Category.CREDIT, -amount);
        Transaction debit = new Transaction(recipient, sender, Transaction.Category.DEBIT, amount);

        debit.setId(credit.getId());

        recipient.getTransactionsList().addTransaction(debit);
        sender.getTransactionsList().addTransaction(credit);

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
    }

    public Transaction[] getTransactionsList(int userId) {
        return usersList.getUserById(userId).getTransactionsList().toArray();
    }

    public void removeTransaction(UUID transactionId, int userId) {
        usersList.getUserById(userId).getTransactionsList().removeTransactionById(transactionId);
    }

    public Transaction[] checkValidityOfTransactions() {
        TransactionsList transactionsList = getAllTransactions();

        TransactionsLinkedList result = new TransactionsLinkedList();
        Transaction[] arrayFirst = transactionsList.toArray();
        if (arrayFirst != null) {
            int sizeArray = arrayFirst.length;
            for (int i = 0; i < sizeArray; i++) {
                int count = 0;
                for (int j = 0; j < sizeArray; j++) {
                    if (arrayFirst[i].getId().equals(arrayFirst[j].getId())) {
                        count++;
                    }
                }
                if (count != 2) {
                    result.addTransaction(arrayFirst[i]);
                }
            }
        }
        return result.toArray();
    }

    private TransactionsList getAllTransactions() {
        TransactionsList list = new TransactionsLinkedList();

        for (int i = 0; i < usersList.getNumberOfUsers(); i++) {
            User user = usersList.getByIndex(i);
            if (user != null) {
                for (int j = 0; j < user.getTransactionsList().getSize(); j++) {
                    list.addTransaction(user.getTransactionsList().toArray()[j]);
                }
            }
        }
        return list;
    }

}