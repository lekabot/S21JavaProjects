package ex04;

public interface UsersList {
    void addUser(User user);
    User getUserById(int id);
    User getByIndex(int index);
    int getNumberOfUsers();
}
