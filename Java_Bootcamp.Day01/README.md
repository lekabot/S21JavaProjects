# Day 01 – Java bootcamp
### OOP/Collections

*Takeaways: Today you will learn how to correctly model the operation of different collections and create a full-fledged money transfer application.*

💡 [Tap here](https://new.oprosso.net/p/4cb31ec3f47a4596bc758ea1861fb624) **to leave your feedback on the project**. It's anonymous and will help our team make your educational experience better. We recommend completing the survey immediately after the project.

# Contents
1. [Chapter I](#chapter-i) \
	1.1. [Preamble](#preamble)
2. [Chapter II](#chapter-ii) \
	2.1. [General Rules](#general-rules)
3. [Chapter III](#chapter-iii) \
	3.1. [Introduction to exercises](#introduction-to-exercises)
4. [Chapter IV](#chapter-iv) \
	4.1. [Exercise 00 – Models](#exercise-00-models)
5. [Chapter V](#chapter-v) \
	5.1. [Exercise 01 – ID Generator](#exercise-01-id-generator)
6. [Chapter VI](#chapter-vi) \
	6.1. [Exercise 02 – List of Users](#exercise-02-list-of-users)
7. [Chapter VII](#chapter-vii) \
	7.1. [Exercise 03 – List of Transactions](#exercise-03-list-of-transactions)
8. [Chapter VIII](#chapter-viii) \
	8.1. [Exercise 04 – Business Logic](#exercise-04-business-logic)
9. [Chapter IX](#chapter-ix) \
	9.1. [Exercise 05 – Menu](#exercise-05-menu)

# Chapter I 
### Preamble
Domain modeling is the most challenging task in software development. Correct solution of this task ensures flexibility of the implemented system.
Programming languages supporting the concept of object-oriented programming (OOP) allow to effectively divide business processes into logical components called classes.
Each class must comply with SOLID principles:
1.	Single responsibility principle: a class contains a single logically related functionality (a coffee machine cannot clean and monitor changes in the call stack; its purpose is to make coffee).
2.	Open-closed principle: each class may provide an option to extend its functionality. However, such an extension should not require modification of the source class code.
3.	Liskov substitution principle: derived classes only ADD to the functionality of a source class without modifying it.
4.	Interface separation principle: There are many points (interfaces) that describe a logically related behavior. There is no such thing as a general-purpose interface.
5.	Dependency inversion principle: a system must not depend on specific entities; all dependencies are based on abstractions (interfaces).

Today you should focus on the first SOLID principle.

![javanepunchman](misc/images/javanepunchman.png)

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
- Your reference guide: other peers / Internet / Google. And one more thing. For every question you have, there's an answer on Stackoverflow. Learn how to ask questions properly.
- Read the examples carefully. They may require things not specified in the subject.
- Use System.out for output.
- And may the Force be with you!
- Never leave for tomorrow what you can do today ;)

# Chapter III
### Introduction to exercises
An internal money transfer system is an integral part of many business applications. 

Your task today is to automate a business process related to transfers of certain amounts between participants of our system.

Each user of the system can transfer a certain amount of money to another user. We need to make sure that even if we lose the history of incoming and outgoing transfers for a particular user, we will still be able to recover this information.

In the system, all money transactions are stored in the form of debit/credit pairs. For example, John transferred $500 to Mike. The system stores the transaction for both users:
```java
John -> Mike, -500, OUTCOME, transaction ID
Mike -> John, +500, INCOME, transaction ID
```
To restore the connection within such pairs, the identifiers of each transaction should be used.

Of course, in such a complex system, a transfer entry may be lost — it may not be recorded for one of the users (to emulate and debug such a situation, a developer must be able to remove the transfer data for one of the users individually). Since such situations are realistic, functionality is needed to display all "unacknowledged transfers" (transactions recorded for only one user) and to resolve such problems.

Below is a series of exercises that you can do one at a time to solve the task.

# Chapter IV
### Exercise 00 — Models

Exercise 00: Models ||
---|---
Turn-in directory |	ex00
Files to turn-in |	User.java, Transaction.java, Program.java
**User classes can be employed, along with:**
Types (+ all methods of these types) |	Integer, String, UUID, enumerations

Your first task is to develop basic domain models — namely, user and transaction classes.

It is quite likely that different users in the system will have the same name. This problem should be solved by adding a special field for a user's unique ID. This ID can be any integer. The specific logic for creating the ID is described in the next exercise.

Thus, the following set of states (fields) is typical for the User class:

- Identifier,
- User name,
- Balance.

The Transaction class describes a money transfer between two users. A unique identifier should also be defined here. Since the number of such transactions can be very large, we define the identifier as a UUID string. Thus, the following set of states (fields) is typical for the Transaction class:
- Identifier,
- Recipient (user type),
- Sender (user type),
- Transfer category (debit, credit),
- Transfer amount.

It is necessary to check the user's initial balance (it cannot be negative), as well as the balance for outgoing (only negative amounts) and incoming (only positive amounts) transactions (use of get/set methods).

An example of the use of such classes should be included in the Program file (creation, initialization, printing the object contents to a console). All data for class fields must be hard-coded in the Program.

# Chapter V
### Exercise 01 – ID Generator

Exercise 01: ID Generator||
---|---
Turn-in directory |	ex01
Files to turn-in |	UserIdsGenerator.java, User.java, Program.java
**All permissions from the previous exercise can be used**

Make sure that each user ID is unique. To do this, create the UserIdsGenerator class. The behavior of the object of this class defines the functionality for generating user IDs.

Modern database management systems support auto-increment principle, where each new ID is the value of the previously generated ID +1.
Thus, the UserIdsGenerator class contains the last generated ID as its state. The behavior of UserIdsGenerator is defined by the int generateId() method, which returns a newly generated ID each time it is called.

An example of using such classes is contained in the program file (creation, initialization, printing object contents to a console).

**Notes**:

- Make sure that only one UserIdsGenerator object exists (see the Singleton pattern). It is required because the existence of multiple objects of this class cannot guarantee that all user identifiers are unique.

- The user identifier must be read-only because it is initialized only once (when the object is created) and cannot be changed later during program execution.

- Temporary logic for identifier initialization should be added to the User class constructor:
```java
public User(...) {
	this.id = UserIdsGenerator.getInstance().generateId();
}
```

# Chapter VI
### Exercise 02 – List of Users

Exercise 02: List of Users||
---|---
Turn-in directory	| ex02
Files to turn-in |	UsersList.java, UsersArrayList.java, User.java,Program.java, etc.
**All permissions from the previous exercise  + throw can be used.**

Now we need to implement some functionality for saving users while the program is running. 

Currently, your application does not have any persistent storage (such as a file system or database). However, we want to avoid making your logic dependent on the user storage implementation method. To provide more flexibility, we define a UsersList interface that describes the following behavior

- Add a user;
- Get a user by ID;
- Get a user by index;
- Get number of users.

This interface will allow you to develop the business logic of your application so that a specific storage implementation does not affect other system components.

We will also implement a UsersArrayList class that implements the UsersList interface.

This class will use an array to store user data. The default size of the array is 10. When the array is full, its size is increased by half. The user-add method puts an object of type User into the first empty cell of the array.

If an attempt is made to retrieve a user with a non-existent ID, a non-checked UserNotFoundException must be thrown.

An example of using such classes is included in the program file (creating, initializing, printing object contents to a console).

**Note**:<br>
Nested `ArrayList<T>` Java class has the same structure. By modeling behavior of this class on your own, you will learn how to use mechanisms of this standard library class. 

# Chapter VII
### Exercise 03 — List of Transactions

Exercise 03: List of Transactions||
---|---
Turn-in directory |	ex03
Files to turn-in | TransactionsList.java, TransactionsLinkedList.java, User.java, Program.java, etc.
**All permissions from the previous exercise can be used**

Unlike users, a list of transactions requires a special implementation approach. Since the number of transaction creation operations can be very large, we need a storage method to avoid a costly array size extension. 

In this task, we offer you to create TransactionsList interface describing the following behavior:
- Add a transaction;
- Remove a transaction by ID (in this case, UUID string identifier is used);
- Transform into array (ex. Transaction[] toArray()).

A list of transactions shall be implemented as a linked list (LinkedList) in TransactionsLinkedList class. Therefore, each transaction shall contain a field with a link to the next transaction object.
If an attempt is made to remove a transaction with non-existent ID, TransactionNotFoundException runtime exception must be thrown.
An example of use of such classes shall be contained in Program file (creation, initialization, printing object content on a console).

**Note**:<br>
- We need to add transactions field of TransactionsList type to User class so that each user can store the list of their transactions.
- A transaction must be added with a SINGLE operation (O(1)).
- `LinkedList<T>` nested Java class has the same structure, a bidirectional linked list.

# Chapter VIII
### Exercise 04 – Business Logic

Exercise 04: Business Logic||
---|---
Turn-in directory |	ex04
Files to turn-in |	TransactionsService.java, Program.java, etc.
**All permissions from the previous exercise can be used**

The business logic level of the application is located in service classes. Such classes contain basic system algorithms, automated processes, etc. These classes are usually designed according to the facade pattern, which can encapsulate the behavior of several classes.

In this case, the TransactionsService class must contain a UserList type field for user interaction and provide the following functionality
- Add a user;
- Get a user's balance.
- Perform a transfer transaction (user IDs and transfer amount are specified). In this case, two transactions of type DEBIT/CREDIT are created and added to the receiver and sender. The IDs of both transactions must be the same;
- Get transfers of a specific user (an ARRAY of transfers is returned). Remove a transaction by ID for a specific user (transaction ID and user ID are specified);
- Validate transactions (returns an ARRAY of unpaired transactions).

In case of an attempt to transfer the amount exceeding the user's remaining balance, the IllegalTransactionException runtime exception must be thrown.

An example of using such classes is included in the program file (creation, initialization, printing object contents to a console).

# Chapter IX
### Exercise 05 — Menu

Exercise 05: Menu||
---|---
Turn-in directory |	ex05
Files to turn-in |	Menu.java, Program.java, etc.
**All permissions from the previous exercise can be used, as well as try/catch**

As a result, you should have a working application with a console menu. The menu functionality must be implemented in the appropriate class with a link field to TransactionsService.

Each menu item must be accompanied by the number of the command that a user enters to invoke an action.

The application must support two startup modes — production (default mode) and dev (where transfer information for a specific user can be removed by user ID and a function that checks the validity of all transfers can be executed). 

If an exception is thrown, a message containing information about the error is displayed and the user is provided with an opportunity to enter valid data.

The application operation scenario is as follows (the program must carefully follow this output example):

```
$ java Program --profile=dev

1. Add a user
2. View user balances
3. Perform a transfer
4. View all transactions for a specific user
5. DEV – remove a transfer by ID
6. DEV – check transfer validity
7. Finish execution
-> 1
Enter a user name and a balance
-> Jonh 777
User with id = 1 is added
---------------------------------------------------------
1. Add a user
2. View user balances
3. Perform a transfer
4. View all transactions for a specific user
5. DEV – remove a transfer by ID
6. DEV – check transfer validity
7. Finish execution
-> 1
Enter a user name and a balance
-> Mike 100
User with id = 2 is added
---------------------------------------------------------
1. Add a user
2. View user balances
3. Perform a transfer
4. View all transactions for a specific user
5. DEV – remove a transfer by ID
6. DEV – check transfer validity
7. Finish execution
-> 3
Enter a sender ID, a recipient ID, and a transfer amount
-> 1 2 100
The transfer is completed
---------------------------------------------------------
1. Add a user
2. View user balances
3. Perform a transfer
4. View all transactions for a specific user
5. DEV – remove a transfer by ID
6. DEV – check transfer validity
7. Finish execution
-> 3
Enter a sender ID, a recipient ID, and a transfer amount
-> 1 2 150
The transfer is completed
---------------------------------------------------------
1. Add a user
2. View user balances
3. Perform a transfer
4. View all transactions for a specific user
5. DEV – remove a transfer by ID
6. DEV – check transfer validity
7. Finish execution
-> 3
Enter a sender ID, a recipient ID, and a transfer amount
-> 1 2 50
The transfer is completed
---------------------------------------------------------
1. Add a user
2. View user balances
3. Perform a transfer
4. View all transactions for a specific user
5. DEV – remove a transfer by ID
6. DEV – check transfer validity
7. Finish execution
-> 2
Enter a user ID
-> 2
Mike - 400
---------------------------------------------------------
1. Add a user
2. View user balances
3. Perform a transfer
4. View all transactions for a specific user
5. DEV – remove a transfer by ID
6. DEV – check transfer validity
7. Finish execution
-> 4
Enter a user ID
-> 1
To Mike(id = 2) -100 with id = cc128842-2e5c-4cca-a44c-7829f53fc31f
To Mike(id = 2) -150 with id = 1fc852e7-914f-4bfd-913d-0313aab1ed99
TO Mike(id = 2) -50 with id = ce183f49-5be9-4513-bd05-8bd82214eaba
---------------------------------------------------------
1. Add a user
2. View user balances
3. Perform a transfer
4. View all transactions for a specific user
5. DEV – remove a transfer by ID
6. DEV – check transfer validity
7. Finish execution
-> 5
Enter a user ID and a transfer ID
-> 1 1fc852e7-914f-4bfd-913d-0313aab1ed99
Transfer To Mike(id = 2) 150 removed
---------------------------------------------------------
1. Add a user
2. View user balances
3. Perform a transfer
4. View all transactions for a specific user
5. DEV – remove a transfer by ID
6. DEV – check transfer validity
7. Finish execution
-> 6
Check results:
Mike(id = 2) has an unacknowledged transfer id = 1fc852e7-914f-4bfd-913d-0313aab1ed99 from John(id = 1) for 150
```
