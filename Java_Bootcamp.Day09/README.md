# Day 09 — Java bootcamp
### Sockets

*Takeaways: Today you will implement the basic mechanism of a client/server application based on Java—Sockets API.*


💡 [Tap here](https://new.oprosso.net/p/4cb31ec3f47a4596bc758ea1861fb624) **to leave your feedback on the project**. It's anonymous and will help our team make your educational experience better. We recommend completing the survey immediately after the project.

# Contents
1. [Chapter I](#chapter-i) \
    1.1. [Preamble](#preamble)
2. [Chapter II](#chapter-ii) \
    2.1. [General Rules](#general-rules)
3. [Chapter III](#chapter-iii) \
    3.1. [Exercise 00 — Registration](#exercise-00-registration)
4. [Chapter IV](#chapter-iv) \
    4.1. [Exercise 01 — Messaging](#exercise-01-messaging)
5. [Chapter V](#chapter-v) \
    5.1. [Exercise 02 — Rooms](#exercise-02-rooms)

# Chapter I
### Preamble
Client/server interaction is the backbone of modern systems. The server performs a large amount of business logic and information storage. As a result, the client application load is significantly reduced.

Decomposing the logic into server and client components allows for a flexible overall system architecture where server and client implementations are as independent as possible.

Client and server communicate using a variety of protocols described as different layers in the OSI network model:

Layer |	Example
---|---
7.<font> Application |	HTTP
6.<font> Representation |	ASCII
5.<font> Session |	RPC
4.<font> Transport |	TCP, UDP
3.<font> Network |	IPv4
2.<font> Channel |	Ethernet, DSL
1.<font> Physical |	USB, "twisted pair"

![A Joke](misc/images/joke.png)

# Chapter II
### General Rules
- Use this page as your only reference. Do not listen to rumors and speculations about how to prepare your solution.
- There is only one Java version for you, 1.8. Make sure you have the compiler and interpreter for this version installed on your machine.
- You can use the IDE to write and debug the source code.
- The code is more often read than written. Carefully read the [document](https://www.oracle.com/technetwork/java/codeconventions-150003.pdf) where code formatting rules are given. When performing any task, make sure you follow the generally accepted [Oracle Standards](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html).

- Comments are not allowed in the source code of your solution. They make it difficult to read the code.
- Be aware of the permissions of your files and directories.
- Your solution must be in your GIT repository to be evaluated.
- Your solutions will be evaluated by your fellow bootcampers.
- You should not leave any files in your src directory other than those explicitly specified in the exercise instructions. It is recommended that you modify your .gitignore to avoid accidents.
- If you need accurate output in your programs, it is forbidden to display precalculated output instead of running the exercise correctly.
- Got a question? Ask your neighbor to the right. Otherwise, try your neighbor on the left.
- Your reference guide: peers / Internet / Google. And one more thing. For every question you have, there's an answer on Stackoverflow. Learn how to ask questions properly.
- Read the examples carefully. They may require things not specified in the subject.
- Use System.out for output.
- And may the Force be with you!
- Never leave for tomorrow what you can do today. ;)

# Chapter III
### Exercise 00 — Registration

Exercise 00: Registration ||
---|---
Turn-in directory	| ex00
Files to turn-in |	Chat-folder

Before you start creating a full-scale multi-user chat, you need to implement the core functionality and build the basic architecture of the system.

Now you need to create two applications: a socket server and a socket client. The server will support connecting to a single client and will be created as a separate Maven project. The server JAR file is started as follows:
```
$ java -jar target/socket-server.jar --port=8081
```

Client is also a separate project:
```
$ java -jar target/socket-client.jar --server-port=8081
```

In this task, you need to implement the registration functionality. Example of the client operation:
```
Hello from Server!
> signUp
Enter username:
> Marsel
Enter password:
> qwerty007
Successful!
```

The connection must be closed after the Successful! message is displayed.

To ensure secure storage of passwords, use a hashing mechanism with PasswordEncoder and BCryptPasswordEncoder (see Spring Security components). The container for this component is described in a class of the SocketsApplicationConfig configuration and used in the UsersService.

Key client/server interaction logic and the use of UsersService via Spring Context must be implemented in the Server class.

**Additional Requirements**:
- Use a single DataSource — HikariCP.
- Repository operation must be implemented via JdbcTemplate.
- Services, repositories, utility classes must be context bins.

Server application architecture (client application is at the discretion of the developer):

- Chat
    - SocketServer
        - src
            - main
                - java
                    - edu.school21.sockets
                        - server
                            -	Server
                        - models
                            -	User
                        - services
                            - UsersService
                            - UsersServiceImpl
                        - repositories
                            - CrudRepository
                            - UsersRepository
                            - UsersRepositoryImpl
                        - app
                            - Main
                        - config
                            - SocketsApplicationConfig
                - resources
                    - db.properties
        - pom.xml

# Chapter IV
### Exercise 01 – Messaging

Exercise 01: Messaging ||
---|---
Turn-in directory |	ex01
Files to turn-in |	Chat-folder

Once you have implemented the application backbone, you should provide multi-user messaging.

You need to modify the application to support the following chat user lifecycle:
1. Registration;
2. Sign in (if no user is detected, close a connection);
3. Send messages (each user connected to the server must receive a message);
4. Log off.

Example of how the application works on the client side:
```
Hello from Server!
> signIn
Enter username:
> Marsel
Enter password:
> qwerty007
Start messaging
> Hello!
Marsel: Hello!
NotMarsel: Bye!
> Exit
You have left the chat.
```
Each message is stored in the database and contains the following information:
- Sender;
- Message text;
- Sending time.

**Note**:
- For comprehensive testing, run multiple jar files of the client application.

# Chapter V
### Exercise 02 — Rooms

Exercise 02: Rooms ||
---|---
Turn-in directory |	ex02
Files to turn-in |	Chat-folder

To make our application fully functional, let's add the concept of "chatrooms". Each chatroom can have a certain number of users. The chatroom contains a set of messages from participating users.

Each user can:
1.	Create a chatroom;
2.	Select a chatroom;
3.	Send a message to a chatroom;
4.	Leave a chatroom.

When the user re-enters the application, the last 30 messages are displayed in the room the user previously visited.

Example of how the application works on the client side:
```
Hello from Server!
1. signIn
2. SignUp
3. Exit
> 1
Enter username:
> Marsel
Enter password:
> qwerty007
1.	Create room
2.	Choose room
3.	Exit
> 2
Rooms:
1. First Room
2. SimpleRoom
3. JavaRoom
4. Exit
> 3
Java Room ---
JavaMan: Hello!
> Hello!
Marsel: Hello!
> Exit
You have left the chat.
```

Using the JSON format for message exchange will be a special task for you. For example, each user command or message must be sent to (and received from) the server in the form of a JSON line.

For example, a command to send a message might look like this (the specific content of the messages is at the developer's discretion):
```JSON
{
  "message" : "Hello!",
  "fromId" : 4,
  "roomId": 10
}
```
