package ex05;

import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final TransactionsService facade;
    private final Scanner scanner;

    public Menu() {
        facade = new TransactionsService();
        scanner = new Scanner(System.in);
    }

    public void run(boolean devMode) {
        System.out.println();
        while (true) { 
            if (devMode) {
                selectionMenu(true);
                choiceMenu(getAnswer(true), true);
            } else {
                selectionMenu(false);
                choiceMenu(getAnswer(false), false);
            }
        }
    }

    public int getAnswer(boolean devMode) {
        String input = scanner.nextLine().trim();
        int answer = 0;
        try {
            answer = Integer.parseInt(input);
            if (answer <= 0 || (devMode && answer > 7 || !devMode && answer > 5)) {
                throw new RuntimeException("Invalid action. Enter a valid number: ");
            }
        } catch (Exception e) {
            System.out.println(e);
            answer = getAnswer(devMode);
        }
        return answer;
    }

    public void selectionMenu(boolean devMode) {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        if (!devMode) {
            System.out.println("5. Finish execution");
            return;
        }
        System.out.println("5. DEV – remove a transfer by ID");
        System.out.println("6. DEV – check transfer validity");
        System.out.println("7. Finish execution");
    }

    public void choiceMenu(int answer, boolean devMode) {
        switch (answer) {
            case 1:
                addUser();
                break;
            case 2:
                viewUserBalance();
                break;
            case 3:
                performTransfer();
                break;
            case 4:
                viewAllTransactionsOfUser();
                break;
            case 5:
                if (!devMode) {
                    scanner.close();
                    System.exit(0);
                }
                removeTransferById();
                break;
            case 6:
                checkTransferValidity();
                break;
            case 7:
                scanner.close();
                System.exit(0);
        }
    }

    public void addUser() {
        System.out.println("Enter a user name and a balance");
        String input = scanner.nextLine().trim();

        try {
            String[] inputArr = input.split("\\s+");
            if (inputArr.length != 2) {
                throw new RuntimeException("Invalid data. Try again");
            }
            String name = inputArr[0];
            int balance = Integer.parseInt(inputArr[1]);
            User user = new User(name, balance);
            facade.addUser(user);
            System.out.println("User with id = " + user.getId() + " is added");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private void viewUserBalance() {
        System.out.println("Enter a user ID");
        String input = scanner.nextLine().trim();
        try {
            int id = Integer.parseInt(input);
            int balance = facade.getUserBalance(id);
            System.out.println(facade.usersList.getUserById(id).getName() + " - " + balance);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String input = scanner.nextLine().trim();
        try {
            String[] inputArr = input.split("\\s+");
            if (inputArr.length != 3) {
                throw new RuntimeException("Invalid data. Try again");
            }
            int senderId = Integer.parseInt(inputArr[0]);
            int recipientId = Integer.parseInt(inputArr[1]);
            int amount = Integer.parseInt(inputArr[2]);
            facade.executeTransaction(senderId, recipientId, amount);
            System.out.println("The transfer is completed");
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }
    
    private void viewAllTransactionsOfUser() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String input = scanner.nextLine().trim();
        try {
            int id = Integer.parseInt(input);
            Transaction[] transactions = facade.getTransactionsList(id);
            if (transactions == null) {
                throw new RuntimeException("User with ID = " + id + " hasn't any transactions");
            }

            for (Transaction transaction : transactions) {
                String category = (transaction.getCategory() == Transaction.Category.DEBIT) ?
                    "From " :
                    "To ";
                User user = (transaction.getCategory() == Transaction.Category.DEBIT) ?
                    transaction.getSender() :
                    transaction.getRecipient();
                System.out.println(category + user.getName() + "(id = " + user.getId() + ") " +
                    transaction.getAmount() + " with id = " + transaction.getId());
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private Transaction getTransaction(Transaction[] transactions, UUID transactionId) {
        if (transactions == null) {
            throw new RuntimeException("Transaction with id = " + transactionId + " not found");
        }
        for (Transaction item : transactions) {
            if (item.getId().equals(transactionId)) {
                return item;
            }
        }
        return null;
    }

    private void removeTransferById() {
        System.out.println("Enter a user ID and a transfer ID");
        String input = scanner.nextLine().trim();
        try {
            String[] inputArr = input.split("\\s+");
            if (inputArr.length != 2) {
                throw new RuntimeException("Invalid data. Try again");
            }
            int userId = Integer.parseInt(inputArr[0]);
            UUID transactionId = UUID.fromString(inputArr[1]);

            Transaction transaction = getTransaction(facade.getTransactionsList(userId), transactionId);
            if (transaction == null) {
                throw new RuntimeException("Transaction with id = " + transactionId + " not found");
            }
            facade.removeTransaction(transactionId, userId);
            User user = (transaction.getCategory() == Transaction.Category.DEBIT) ?
                    transaction.getSender() :
                    transaction.getRecipient();
            String category = (transaction.getCategory() == Transaction.Category.DEBIT) ?
                    "From " :
                    "To ";
            System.out.println("Transfer " + category + " " + user.getName() +
                    "(id = " + user.getId() + ") " + transaction.getAmount() + " removed");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private User getUserHolder(Transaction transaction) {
        UsersArrayList listUsers = facade.usersList;

        for (int i = 0; i < listUsers.getNumberOfUsers(); i++) {
            Transaction[] listTrans = listUsers.getByIndex(i).getTransactionsList().toArray();
            for (int j = 0; listTrans != null && j < listTrans.length; j++) {
                if (listTrans[j].getId().equals(transaction.getId())) {
                    return listUsers.getByIndex(i);
                }
            }
        }
        return null;
    }

    private void checkTransferValidity() {
        System.out.println("Check results:");
        Transaction[] transactions = facade.checkValidityOfTransactions();
        if (transactions != null) {
            for (Transaction item : transactions) {
                User userHolder = getUserHolder(item);
                User userSender = (item.getCategory() == Transaction.Category.DEBIT) ?
                        item.getSender() :
                        item.getRecipient();
                UUID transactionId = item.getId();
                int amount = item.getAmount();
                System.out.println(userHolder.getName() + "(id = " + userHolder.getId() +
                        ") has an unacknowledged transfer id = " + transactionId + " from " +
                        userSender.getName() + "(id = " + userSender.getId() +
                        ") for " + amount);
            }
            System.out.println("---------------------------------------------------------");
            return;
        }
        System.out.println("There are no unpaired transactions");
        System.out.println("---------------------------------------------------------");
    }

}