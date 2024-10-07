DROP SCHEMA IF EXISTS chat CASCADE;

CREATE SCHEMA IF NOT EXISTS chat;

CREATE TABLE chat.users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password TEXT NOT NULL

);

CREATE TABLE chat.chatrooms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    owner_id INT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES chat.users(id) ON DELETE CASCADE
);

CREATE TABLE chat.messages (
    id SERIAL PRIMARY KEY,
    author INT NOT NULL,
    room INT NOT NULL,
    text TEXT NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author) REFERENCES chat.users(id) ON DELETE CASCADE,
    FOREIGN KEY (room) REFERENCES chat.chatrooms(id) ON DELETE CASCADE
);

CREATE TABLE chat.user_chatroom (
    user_id INT NOT NULL,
    chatroom_id INT NOT NULL,
    PRIMARY KEY (user_id, chatroom_id),
    FOREIGN KEY (user_id) REFERENCES chat.users (id) ON DELETE CASCADE ,
    FOREIGN KEY (chatroom_id) REFERENCES chat.chatrooms (id) ON DELETE CASCADE
);