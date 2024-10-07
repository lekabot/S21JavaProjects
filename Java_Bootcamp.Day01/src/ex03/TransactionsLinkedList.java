package ex03;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

    private static class Node {
        Transaction transaction;
        Node next;
        Node(Transaction transaction) {
            this.transaction = transaction;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public TransactionsLinkedList() {
        head = null;
        size = 0;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        Node newNode = new Node(transaction);
        newNode.next = head;
        head = newNode;
        size++;
    }

    @Override
    public void removeTransactionByID(UUID id) throws TransactionNotFoundException {
        if (head == null) {
            throw new TransactionNotFoundException("Transaction with ID " + id + " not found.");
        }

        if (head.transaction.getId().equals(id)) {
            head = head.next;
            size--;
            return;
        }

        Node current = head;
        while (current.next != null && !current.next.transaction.getId().equals(id)) {
            current = current.next;
        }

        if (current.next == null) {
            throw new TransactionNotFoundException("Transaction with ID " + id + " not found.");
        }

        current.next = current.next.next;
        size--;
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] array = new Transaction[size];
        Node current = head;
    
        for (int i = size - 1; i >= 0; i--) {
            array[i] = current.transaction;
            current = current.next;
        }
    
        return array;
    }

    public int getSize() {
        return size;
    }
}
