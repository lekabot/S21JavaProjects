# Day 03 — Java bootcamp
### Threads

*Takeaways: Today you will learn how to use basic multithreading mechanisms in Java.*

💡 [Tap here](https://new.oprosso.net/p/4cb31ec3f47a4596bc758ea1861fb624) **to leave your feedback on the project**. It's anonymous and will help our team make your educational experience better. We recommend completing the survey immediately after the project.

# Contents
1. [Chapter I](#chapter-i) \
  1.1. [Preamble](#preamble)
2. [Chapter II](#chapter-ii) \
  2.1 [General Rules](#general-rules)
3. [Chapter III](#chapter-iii) \
  3.1 [Exercise 00 — Egg, Hen... or Human?](#exercise-00-egg-hen-or-human)
4. [Chapter IV](#chapter-iv) \
  4.1 [Exercise 01 — Egg, Hen, Egg, Hen...](#exercise-01-egg-hen-egg-hen)
5. [Chapter V](#chapter-v) \
  5.1. [Exercise 02 — Real Multithreading](#exercise-02-real-multithreading)
6. [Chapter VI](#chapter-vi) \
  6.1. [Exercise 03 — Too Many Threads...](#exercise-03-too-many-threads)


# Chapter I
### Preamble
- Every modern client/server application is based on threads.
- Threads implement the concept of asynchronous operation, where several loosely coupled tasks are executed "in parallel".
- Multithreading in client/server applications allows some tasks to run in the background so that the client does not have to wait for the server to respond. For example, when you enter your email address on a website, a page is immediately displayed informing you that the confirmation message has been sent to your email address, regardless of how long it takes to send the message to your email address in a parallel thread.

- Each of your requests to a website is executed in a separate, independent thread on the server.
- The behavior of threads is managed by the operating system and the processor.
- Thread behavior is non-deterministic. You never know which thread will be running at any given time, even if you restart the same multithreaded code.
- For tips on handling threads, see the Object class.
- Threads are the most popular topic in junior interviews.

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
- Your reference guide: friends / Internet / Google. And one more thing. For every question you have, there's an answer on Stackoverflow. Learn how to ask questions properly.
- Read the examples carefully. They may require things not specified in the subject.
- Use System.out for output.
- And may the Force be with you!
- Never leave for tomorrow what you can do today. ;)

# Chapter III
### Exercise 00 — Egg, Hen... or Human?

Exercise 00: Egg, Hen... or Human? ||
---|---
Turn-in directory |	ex00
Files to turn-in |	*.java
**Allowed:** |
Types and their methods: |	Object, Thread, Runnable

Chicken or egg

The truth is born in an argument — let us assume that each thread gives its own answer. The thread that has the last word is right.

You need to implement the operation of two threads. Each of them must display its answer a few times, for example 50:
```
$ java Program --count=50
Egg
Hen
Hen
Hen
...
Egg
```
In this case, the egg thread wins. However, the program also contains a main thread. Inside the thread, the method public static void main(String args[]) is executed. We need this thread to display all its responses at the end of program execution. So the final variant is as follows:
```
$ java Program --count=50
Egg
Hen
Hen
...
Egg
Hen
...
Human
...
...
Human
```
It means that the program outputs Human message 50 times, which main thread prints.

# Chapter IV
### Exercise 01 — Egg, Hen, Egg, Hen...

Exercise 00: Egg, Hen, Egg, Hen... ||
---|---
Turn-in directory |	ex01
Files to turn-in | *.java
**Allowed:** |
Types and their methods: | Object, Thread, Runnable
Keywords: |	Synchronized

Let's orchestrate the argument. Now, each thread can only give its answer after another thread has done so. Let's say the egg thread always responds first.

```
$ java Program --count=50
Egg
Hen
Egg
Hen
Egg
Hen
...
```

**Note**:<br>
To solve this task, we recommend to explore Producer-Consumer model operation principle.

# Chapter V
### Exercise 02 — Real Multithreading

Exercise 02: Real Multithreading ||
---|---
Turn-in directory |	ex02
Files to turn-in | *.java
**Allowed:** |
Types and their methods: | Object, Thread, Runnable
Keywords: | Synchronized

Try to use multithreading for its intended purpose: distributing computations throughout the program.

Let's say you have an array of integer values. Your goal is to compute the sum of the array elements using multiple "summing" threads. Each thread computes a particular section within the array. The number of elements in each section is constant, except for the last one (its size can vary upwards or downwards).

The array is randomly generated each time. The array length and the number of threads are passed as command line arguments.

To make sure the program works correctly, we should first calculate the sum of the array elements using a standard method.

The maximum number of array elements is 2,000,000. Maximum number of threads is not greater than the current number of array elements. Maximum modulo value of any array element is 1,000. All data is guaranteed to be valid.

Example of program operation (each array element is equal to 1):
```
$ java Program --arraySize=13 --threadsCount=3
Sum: 13
Thread 1: from 0 to 4 sum is 5
Thread 2: from 5 to 9 sum is 5
Thread 3: from 10 to 12 sum is 3
Sum by threads: 13
```

**Notes**:
- In the example above, the size of the last summary section used by the third thread is smaller than the others.
- Threads can output the results of operations inconsistently.

# Chapter VI
### Exercise 03 — Too Many Threads...

Exercise 02: Too Many Threads... ||
---|---
Turn-in directory	| ex03
Files to turn-in	| *.java
**Allowed:** |
Types and their methods: |	Object, Thread, Runnable
Keywords: |	Synchronized

Let's say we need to download a list of files from a network. Some files will be downloaded faster than others.

To implement this functionality, we can obviously use multithreaded downloading, where each thread loads a specific file. But what do we do when there are too many files? A large number of threads cannot run at the same time. So many of them will be waiting.

In addition, we should keep in mind that continuously creating and closing threads is a very expensive operation that we should avoid. It makes more sense to start N threads at once, and when one of them finishes downloading the file, it can pick up the next file in the queue.

We need to create a files_urls.txt file (the filename must be explicitly specified in the program code) where you can specify a list of URLs of files to be downloaded, for example:
```
1 https://i.pinimg.com/originals/11/19/2e/11192eba63f6f3aa591d3263fdb66bd5.jpg
2 https://pluspng.com/img-png/balloon-hd-png-balloons-png-hd-2750.png
3 https://i.pinimg.com/originals/db/a1/62/dba162603c71cac00d3548420c52bac6.png
4 https://pngimg.com/uploads/balloon/balloon_PNG4969.png
5 http://tldp.org/LDP/intro-linux/intro-linux.pdf
```
Example of the program operation:
```
$ java Program.java --threadsCount=3
Thread-1 start download file number 1
Thread-2 start download file number 2
Thread-1 finish download file number 1
Thread-1 start download file number 3
Thread-3 start download file number 4
Thread-1 finish download file number 3
Thread-2 finish download file number 2
Thread-1 start download file number 5
Thread-3 finish download file number 4
Thread-1 finish download file number 5
```
**Notes**:
- The output produced by the implemented program may differ from the illustration.
- Each file is downloaded only once by a single thread.
- The program may contain an "infinite loop" without an exit condition (in this case, the program can be terminated by interrupting the process).
