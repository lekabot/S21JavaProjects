-- Заполнение таблицы chat.users
INSERT INTO chat.users (login, password) VALUES
('user1', 'password1'),
('user2', 'password2'),
('user3', 'password3'),
('user4', 'password4'),
('user5', 'password5');

-- Заполнение таблицы chat.chatrooms
INSERT INTO chat.chatrooms (name, owner_id) VALUES
('General Chat', 1),  -- owner_id = 1 (user1)
('Tech Talk', 2),     -- owner_id = 2 (user2)
('Random Stuff', 3),  -- owner_id = 3 (user3)
('Gaming Zone', 4),   -- owner_id = 4 (user4)
('Study Group', 5);   -- owner_id = 5 (user5)

-- Заполнение таблицы chat.messages
INSERT INTO chat.messages (author, room, text) VALUES
(1, 1, 'Hello everyone!'),          -- author = 1 (user1), room = 1 (General Chat)
(2, 1, 'Hi user1!'),                -- author = 2 (user2), room = 1 (General Chat)
(3, 2, 'What tech are we discussing?'), -- author = 3 (user3), room = 2 (Tech Talk)
(4, 3, 'Anyone up for gaming?'),    -- author = 4 (user4), room = 3 (Random Stuff)
(5, 4, 'Let’s study together!');     -- author = 5 (user5), room = 4 (Gaming Zone)

-- Заполнение таблицы chat.user_chatroom
INSERT INTO chat.user_chatroom (user_id, chatroom_id) VALUES
(1, 1),  -- user1 in General Chat
(1, 2),  -- user1 in Tech Talk
(2, 1),  -- user2 in General Chat
(3, 2),  -- user3 in Tech Talk
(4, 3),  -- user4 in Random Stuff
(5, 4),  -- user5 in Gaming Zone
(1, 3),  -- user1 in Random Stuff
(2, 4),  -- user2 in Gaming Zone
(3, 5),  -- user3 in Study Group
(4, 5);  -- user4 in Study Group
