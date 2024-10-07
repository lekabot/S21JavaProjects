package edu.school21.sockets.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(name = "authorized")
    private boolean authorized;

    public User(String login, String password, boolean authorized) {
        this.login = login;
        this.password = password;
        this.authorized = authorized;
    }
}
