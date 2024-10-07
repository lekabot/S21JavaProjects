package edu.school21.game;

import java.util.Random;

public class SpritesFactory {
    private boolean[][] occupiedCoords;
    private Random randomizer;
    private GameMap gameMap;

    public SpritesFactory(GameMap gameMap) {
        this.occupiedCoords = new boolean[gameMap.getSize()][gameMap.getSize()];
        this.randomizer = new Random();
        this.gameMap = gameMap;
    }

    public PlayerSprite createPlayer(String sign, String color) {
        int[] coords = getCoords();
        return new PlayerSprite(gameMap, coords[0], coords[1], sign, color);
    }

    public TargetSprite createTarget(String sign, String color) {
        int[] coords = getCoords();
        return new TargetSprite(coords[0], coords[1], sign, color);
    }

    public WallSprite[] createWalls(int wallsCount, String sign, String color) {
        WallSprite[] walls = new WallSprite[wallsCount];
        int[] coords;

        for (int i = 0; i < wallsCount; i++) {
            coords = getCoords();
            walls[i] = new WallSprite(coords[0], coords[1], sign, color);
        }
        return walls;
    }

    public EnemySprite[] createEnemies(int enemiesCount, String sign, String color) {
        EnemySprite[] enemies = new EnemySprite[enemiesCount];
        int [] coords;

        for (int i = 0; i < enemiesCount; i++) {
            coords = getCoords();
            enemies[i] = new EnemySprite(gameMap, coords[0], coords[1], sign,
                    color);
        }
        return enemies;
    }

    private boolean isOccupied(int xCoord, int yCoord) {
        return this.occupiedCoords[yCoord][xCoord];
    }

    private int[] getCoords() {
        int xCoord = 0;
        int yCoord = 0;
        do {
            xCoord = randomizer.nextInt(this.gameMap.getSize());
            yCoord = randomizer.nextInt(this.gameMap.getSize());
        } while (this.isOccupied(xCoord, yCoord));
        this.occupiedCoords[yCoord][xCoord] = true;
        return new int[] {xCoord, yCoord};
    }
}
