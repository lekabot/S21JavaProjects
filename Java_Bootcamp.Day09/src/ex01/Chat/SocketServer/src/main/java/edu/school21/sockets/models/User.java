package edu.school21.sockets.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private Long id;
    private String login;
    private String password;
    private boolean authorized;

    public User(String login, String password, boolean authorized) {
        this.login = login;
        this.password = password;
        this.authorized = authorized;
    }
}
