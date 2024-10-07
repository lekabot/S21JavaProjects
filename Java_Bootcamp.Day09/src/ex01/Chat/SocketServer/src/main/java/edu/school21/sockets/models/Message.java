package edu.school21.sockets.models;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Message {
    private Long id;
    private User author;
    private String text;
    private Timestamp time;

    public Message(User author, String text, Timestamp time) {
        this.author = author;
        this.text = text;
        this.time = time;
    }
}
