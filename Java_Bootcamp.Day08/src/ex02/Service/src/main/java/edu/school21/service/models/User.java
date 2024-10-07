package edu.school21.service.models;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Long id;
    private String email;
    private String password;
}
