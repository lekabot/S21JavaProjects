# Day 02 — Java bootcamp
### IO, Files

*Takeways: Today you will learn how to use input/output in Java and implement programs to manipulate a file system.*

💡 [Tap here](https://new.oprosso.net/p/4cb31ec3f47a4596bc758ea1861fb624) **to leave your feedback on the project**. It's anonymous and will help our team make your educational experience better. We recommend completing the survey immediately after the project.

# Contents
1. [Chapter I](#chapter-i) \
  1.1. [Preamble](#preamble)
2. [Chapter II](#chapter-ii) \
  2.1. [General Rules](#general-rules)
3. [Chapter III](#chapter-iii) \
  3.1. [Exercise 00 — File Signatures](#exercise-00-file-signatures)
4. [Chapter IV](#chapter-iv) \
  4.1. [Exercise 01 — Words](#exercise-01-words)
5. [Chapter V](#chapter-v) \
  5.1. [Exercise 02 — File Manager](#exercise-02-file-manager)

# Chapter I
### Preamble
Input/output operations play an important role in enterprise system development. It is often necessary to implement functionality for loading and processing user files, sending various documents by mail, etc.

Obviously, input/output never boils down to working with a file system. Any client/server interaction between applications involves input/output operations. For example, the Java servlet technology used in Web development allows HTML pages to be formatted using the PrintWriter class.

It is important to remember that input/output functionality is not limited to the Java IO stack. There are many libraries that greatly simplify interaction with data flows. Apache Commons IO is one of them.

# Chapter II
### General Rules
- Use this page as your only reference. Do not listen to rumors and speculations about how to prepare your solution.
- There is only one Java version for you, 1.8. Make sure you have the compiler and interpreter for this version installed on your machine.
- You can use the IDE to write and debug the source code.
- The code is more often read than written. Carefully read the [document](https://www.oracle.com/technetwork/java/codeconventions-150003.pdf) where code formatting rules are given. When performing any task, make sure you follow the generally accepted [Oracle Standards](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html).
- Comments are not allowed in the source code of your solution. They make it difficult to read the code. The only allowed comment format is Javadoc.
- Be aware of the permissions of your files and directories.
- Your solution must be in your GIT repository to be evaluated.
- Your solutions will be evaluated by your fellow bootcampers.
- You should not leave any files in your src directory other than those explicitly specified in the exercise instructions. It is recommended that you modify your .gitignore to avoid accidents.
- If you need accurate output in your programs, it is forbidden to display precalculated output instead of running the exercise correctly.
- Got a question? Ask your neighbor to the right. Otherwise, try your neighbor on the left.
- Your reference guide: friends / Internet / Google. And one more thing. For every question you have, there's an answer on Stackoverflow. Learn how to ask questions properly.
- Read the examples carefully. They may require things not otherwise specified in the topic.
- Use System.out for output.
- And may the Force be with you!
- Never leave till tomorrow what you can do today. ;)

# Chapter III
### Exercise 00 — File Signatures

Exercise 00: File Signatures||
---|---
Turn-in | directory ex00
Files to turn-in |  *.java, signatures.txt
**Permissions**
Recommended types | Java Collections API (`List<T>`, `Map<K`, `V>`, etc.), InputStream, OutputStream, FileInputStream, FileOutputStream

Input/output classes in Java are represented by a broad hierarchy. The key classes that describe byte input/output behavior are the abstract classes InputStream and OutputStream. They do not implement specific mechanisms for byte stream processing, but delegate it to their subclasses, such as FileInputStream/FileOutputStream.

To understand the use of this functionality, you should implement an application for analyzing signatures of arbitrary files. This signature allows to define the content type of a file and consists of a set of "magic numbers". These numbers are usually located at the beginning of the file. For example, a signature for the PNG file type is represented by the first eight bytes of a file, which are the same for all PNG images:
```
89 50 4E 47 0D 0A 1A 0A
```

You need to implement an application that accepts signatures.txt as input (you should describe it yourself; the filename is explicitly specified in the program code). It contains a list of file types and their respective signatures in HEX format. Example (the specified format of this file must be respected):
```
PNG, 89 50 4E 47 0D 0A 1A 0A
GIF, 47 49 46 38 37 61
```
During execution, your program should accept full paths to files on disk and keep the type to which the file signature corresponds. The result of the program execution should be written to the result.txt file. If no signature can be defined, the result of execution is UNDEFINED (no information should be written to the file).

Example of program execution:
```
$java Program
-> C:/Users/Admin/images.png
PROCESSED
-> C:/Users/Admin/Games/WoW.iso
PROCESSED
-> 42
```
Contents of result.txt file (there is no need to load this file as a result):
```
PNG
GIF
```

**Notes**:
- We can accurately determine the content type by analyzing the file signature, since the file extension in the name (e.g. image.jpg) can be changed by simply renaming the file.

- The signature file must contain at least 10 different formats for analysis.

# Chapter IV
### Exercise 01 — Words

Exercise 01: Words ||
---|---
Turn-in directory | ex01
Files to turn-in |  *.java
**Permissions**
Recommended types | Java Collections API, Java IO

In addition to classes designed to handle byte streams, Java has classes that simplify the handling of character (char) streams. These include the abstract classes Reader/Writer and their implementations (FileReader/FileWriter, etc.). Of special interest are the BufferedReader/BufferedWriter classes, which speed up the handling of flows by means of buffering mechanisms.

Now you need to implement an application that determines the degree of similarity between texts. The simplest and most obvious way to do this is to analyze the frequency of occurrence of the same words.

Let's say we have the following two texts:
```
1. aaa bba bba a ccc
2. bba a a a bb xxx
```
Let's create a dictionary that contains all words in these texts:
```
a, aaa, bb, bba, ccc, xxx
```
Now let's create two vectors with the same length as the dictionary. At the i-th position of each vector, we will reflect the frequency of occurrence of the i-th word in our dictionary in the former and the latter text:
```
A = (1, 1, 0, 2, 1, 0)
B = (3, 0, 1, 1, 0, 1)
```

Thus, each of these vectors characterizes the text in terms of the frequency of occurrence of words from our dictionary. Let's determine the similarity between the vectors using the following formula:

![formula](misc/images/formula.png)

Thus, similarity value for these vectors is:
```
Numerator A. B = (1 * 3 + 1 * 0 + 0 * 1 + 2 * 1 + 1 * 0 + 0 * 1) = 5
Denominator ||A|| * ||B|| = sqrt(1 * 1 + 1 * 1 + 0 * 0 + 2  * 2 + 1 * 1 + 0 * 0) * sqrt(3 * 3 + 0 * 0 + 1 * 1 + 1 * 1  + 0 * 0 + 1 * 1) = sqrt(7) * sqrt(12) = 2.64 * 3.46 = 9.1
similarity = 5 / 9.1 = 0.54
```
Your goal is to implement an application that takes two files as input (both files are passed as command line arguments) and displays the result of their similarity comparison (cosine measure).

The program should also create a file dictionary.txt that contains a dictionary based on these files.

Example of program execution:
```
$ java Program inputA.txt inputB.txt
Similarity = 0.54
```

**Notes**:
1. The maximum size of these files is 10MB.
2. Files may contain non-letter characters.

# Chapter V
### Exercise 02 — File Manager

Exercise 02: File Manager ||
---|---
Turn-in directory | ex02
Files to turn-in |  *.java
**Permissions**
Recommended types | Java Collections API, Java IO, Files, Paths, etc.

Let's implement a utility to handle the files. The application should display information about the files, folder contents and size, and provide moving/renaming functionality. In essence, the application emulates a command line of Unix-like systems.

The program will take as argument the absolute path to the folder where we want to start working, and will support the following commands:

`mv` WHAT WHERE — enables to transfer or rename a file if WHERE contains a file name without a path.
`ls` — displays the current folder contents (file and subfolder names and sizes in KB)
`cd FOLDER_NAME` — changes the current directory

Let's assume there is MAIN folder on disk C:/ (or in the root directory, depending on OS) with the following hierarchy:
- MAIN
  + folder1
    * image.jpg
    * animation.gif
  + folder2
    * text.txt
    * Program.java

Example of the program execution for MAIN directory:
```
$ java Program --current-folder=C:/MAIN
C:/MAIN
-> ls
folder1 60 KB
folder2 90 KB
-> cd folder1
C:/MAIN/folder1
-> ls
image.jpg 10 KB
animation.gif 50 KB
-> mv image.jpg image2.jpg
-> ls
image2.jpg 10 KB
animation.gif 50 KB
-> mv animation.gif ../folder2
-> ls
image2.jpg 10 KB
-> cd ../folder2
C:/MAIN/folder2
-> ls
text.txt 10 KB
Program.java 80 KB
animation.gif 50 KB
-> exit
```

**Note**:<br>
You should test the program functionality with your own set of files/folders.
