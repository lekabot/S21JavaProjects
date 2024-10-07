# Team 00 — Java bootcamp
### Console Game & Maven

*Takeaways: Today you will implement quite a complicated game business process using Maven build tool.*

💡 [Tap here](https://new.oprosso.net/p/4cb31ec3f47a4596bc758ea1861fb624) **to leave your feedback on the project**. It's anonymous and will help our team make your educational experience better. We recommend completing the survey immediately after the project.

# Contents
1. [Chapter I](#chapter-i) \
	1.1. [Preamble](#preamble)
2. [Chapter II](#chapter-ii) \
	2.1. [General Rules](#general-rules)
3. [Chapter III](#chapter-iii) \
	3.1. [Exercise 00 — Surrender, You're Surrounded](#exercise-00-surrender-youre-surrounded)


# Chapter I 
### Preamble
Between 2000 and 2010, there were numerous problems in the throttle control software of a well-known automaker that resulted in 89 serious accidents.

The problem was not in the vehicle design, but in the low-quality software. It was spaghetti code that could not be detected by any testing method. 

Even NASA was involved in investigating the problem. 

Below is the number of defects found in the controller software:

![table_a.8-8](misc/images/table_a.8-8.png)

And this content is not safe to view:

![table_a.8-9](misc/images/table_a.8-9.png)

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
- Read the examples carefully. They may require things not specified in the subject.
- Use System.out for output.
- And may the Force be with you!
- Never leave for tomorrow what you can do today. ;)

# Chapter III
### Exercise 00 — Surrender, You're Surrounded

| Exercise 00: Surrender, You're Surrounded | |
| ------ | ------ |
| Turn-in directory | ex00 |
| Files to turn-in | Game-folder, ChaseLogic-folder |

Remember the good old Java games? In the early 2000s, they were in every phone. Today, Java developers are building scalable enterprise systems, but back then... 

Your goal today is to get a little nostalgic and implement a game where you run from artificial intelligence entities across a square field. 

The program should generate a random map with obstacles. Both the player and his enemies are randomly placed on the map. Each element of the map should have a specific color.

Example of a generated map:

![map](misc/images/map.png)

**Designations**: <br>
`o` — position of a player (program user) on the map;<br>
`#` — obstacle;<br>
`x` — enemy (artificial intelligence);<br>
`O` — target point the player must get to before the enemies reach the player. The player is considered to have reached the target cell if they stepped on its position.

**Game rules**:
1. Each participant (player and opponent) may make one move. Then it's another player's turn. The enemy is considered to have reached the player if he can step on the player's position with the current move.
2. Available directions are left, right, down, and up.
3. If an enemy cannot move forward (there are obstacles or other enemies around, or the player has reached the edge of the map), the enemy skips a turn.
4. The target point is an obstacle for an enemy.
5. If the player is unable to move forward (surrounded by obstacles, enemies, or has reached the edge of the map), the player loses the game.
6. The player loses if an enemy finds him before he reaches the target point.
7. The player who starts the game first.

**Implementation requirements**:
1. Field size, number of obstacles, and number of enemies are entered into the program using command-line parameters (their availability is guaranteed):<br>
`$ java -jar game.jar --enemiesCount=10 --wallsCount=10 --size=30 --profile=production`
2. Checks if it is possible to place the given number of enemies and obstacles on the map of the given size. If the input data is wrong, the program throws an unchecked IllegalParametersException and terminates.
3. Enemies, obstacles, the player and the destination are placed randomly on the map. 
4. When generating the map, enemies, player, obstacles and the target point should not overlap.
5. At the beginning of the game, the map must be generated in such a way that the player can reach the target point (the player must not be blocked by walls or the edge of the map).
6. To make a move, the player must enter a number into the console that corresponds to the direction of movement A, W, D, S (left, up, right, down).
7. If the player is unable to make a move in the given direction, they must enter another number (direction).
8. If the player realizes at the beginning or in the middle of the game that the destination is unreachable, they must end the game by entering 9 (player loses).
9. Once the player has made a move, it is the opponent's turn to make a move towards the player. 
10. In development mode, each move made by the opponent must be confirmed by the player by entering 8.
11. After each move of any participant, the map must be redrawn in the console. In development mode, the map is displayed without updating the screen.
12. The tracking algorithm must take into account the location of the target object at each step.

**Architecture requirements**:
1. Two projects will be implemented: Game (contains game logic, application entry point, output functionality, etc.) and ChaseLogic (contains tracking algorithm implementation).
2. Both are Maven projects, and ChaseLogic is to be added as a dependency to pom.xml within Game.
3. Make the Game.jar archive portable:  JCommander and JCDP must be included directly in the archive. At the same time, all libraries associated with the project must be declared as Maven dependencies.

It is also necessary to create a configuration file called application-production.properties. In this file you will specify your application settings. An example of this file is shown below:

enemy.char = X <br>
player.char = o <br>
wall.char = \# <br>
goal.char = O <br>
empty.char= <br>
enemy.color = RED <br>
player.color = GREEN <br>
wall.color = MAGENTA <br>
goal.color = BLUE <br>
empty.color = YELLOW

This configuration file is located in the resources folder of the launched jar archive.

In addition, an application-dev.properties file should be implemented. The structure of this file is similar to application.properties. Here you can specify parameters to distinguish the application start in development mode (for example, different colors/characters for map components).

It is important to keep in mind that the application can be started in other modes as well. For this purpose, the appropriate properties file can be added to the source project and the mode itself is passed via the --profile parameter.
