package edu.school21.game;

import com.beust.jcommander.JCommander;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
        public static void main(String[] args) {
        Args arguments = new Args();
        JCommander.newBuilder().addObject(arguments).build().parse(args);
        arguments.checkArgs();

        Map<String, String> configMap = getConfig(arguments.profile);
        GameMap gameMap = new GameMap(arguments.size, arguments.profile,
                configMap.get("empty.char"), configMap.get("empty.color"));

        gameMap.setWallChar(configMap.get("wall.char"));
        gameMap.setPlayerChar(configMap.get("player.char"));
        gameMap.setEnemyChar(configMap.get("enemy.char"));
        gameMap.setGoalChar(configMap.get("goal.char"));

        SpritesFactory spritesFactory = new SpritesFactory(gameMap);

        PlayerSprite playerSprite = spritesFactory.createPlayer(
                configMap.get("player.char"), configMap.get("player.color"));
        TargetSprite targetSprite = spritesFactory.createTarget(
                configMap.get("goal.char"), configMap.get("goal.color"));
        EnemySprite[] enemySprites = spritesFactory.createEnemies(
                arguments.enemiesCount, configMap.get("enemy.char"),
                configMap.get("enemy.color"));
        Enemies enemies = new Enemies(enemySprites);
        WallSprite[] wallSprites = spritesFactory.createWalls(
                arguments.wallsCount, configMap.get("wall.char"),
                configMap.get("wall.color"));

        gameMap.attachPlayer(playerSprite);
        gameMap.attachTarget(targetSprite);
        gameMap.attachEnemies(enemySprites);
        gameMap.attachWalls(wallSprites);
        gameMap.print();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            playerSprite.move();
            gameMap.update();

            if (playerSprite.isCollision(targetSprite)) {
                System.out.println("Congratulations! You've reached the target point!");
                break;
            }

            if ("dev".equals(arguments.profile)) {
                System.out.println("Press 8 to confirm enemy move");
                String input = scanner.nextLine();
                while (!"8".equals(input)) {
                    System.out.println("Invalid input, press 8 to confirm enemy move");
                    input = scanner.nextLine();
                }
                enemies.move();
                gameMap.update();
            } else {
                enemies.move();
                gameMap.update();
            }

            if (enemies.isCollision(playerSprite)) {
                System.out.println("Game Over! You've been caught by an enemy.");
                break;
            }
        }
    }

    private static Map<String, String> getConfig(String profile) {
        Map<String, String> configMap = new HashMap<>();

        try (InputStream is = App.class.getResourceAsStream(
                "/application-" + profile + ".properties")) {

            Path outputPath = Files.createTempFile("tmp-", ".tmp");
            Files.copy(is, outputPath, StandardCopyOption.REPLACE_EXISTING);
            FileReader fr = new FileReader(outputPath.toFile().getAbsolutePath());
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] splittedLine = line.split("=");
                if (splittedLine.length >= 2) {
                    configMap.put(splittedLine[0].strip(), splittedLine[1].strip());
                }
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Wrong properties file: application-"
                    + profile + ".properties");
            System.exit(-1);
        }
        return configMap;
    }
}
