package edu.school21.models;

public class User {
    private Long id;
    private String login;
    private String password;
    private boolean authenticated;

    public User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        authenticated = false;
    }

    public boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getPassword() {
        return password;
    }
}
