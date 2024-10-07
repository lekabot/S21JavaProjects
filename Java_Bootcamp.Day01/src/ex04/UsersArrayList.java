package ex04;

import java.util.Arrays;

public class UsersArrayList implements UsersList {
    private static final int DEFAULT_CAPACITY = 10;
    private User[] users;
    private int size;

    public UsersArrayList() {
        this.users = new User[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @Override
    public void addUser(User user) {
        if (size == users.length) {
            resize();
        }
        users[size++] = user;
    }

    @Override
    public User getUserById(int id) {
        for (int i = 0; i < size; i++) {
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User with ID " + id + " not found.");
    }

    @Override
    public User getByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
        return users[index];
    }

    @Override
    public int getNumberOfUsers() {
        return size;
    }

    private void resize() {
        int newCapacity = users.length + users.length / 2;
        users = Arrays.copyOf(users, newCapacity);
    }

    public void printList() {
        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + "\tName: " + users[i].getName() + " balance: " + users[i].getBalance());
            System.out.println("\tid: " + users[i].getId());
        }
    }

}
