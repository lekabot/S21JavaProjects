package edu.school21.game;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

import com.diogonunes.jcolor.Attribute;

public class GameMap {
    private int size;
    private int playerCordX;
    private int playerCordY;
    private Attribute color;
    private String emptyChar;
    private String wallChar;
    private String playerChar;
    private String enemyChar;
    private String goalChar;
    private Sprite[][] map;
    private PlayerSprite player;
    private WallSprite[] walls;
    private EnemySprite[] enemies;
    private TargetSprite target;
    private boolean devProfile;


    public GameMap(int size, String profile, String emptyChar, String emptyColor) {
        this.size = size;
        this.color = BACK_COLOR(
                Colors.getColorRGB(emptyColor)[0],
                Colors.getColorRGB(emptyColor)[1],
                Colors.getColorRGB(emptyColor)[2]
        );
        this.map = new Sprite[size][size];
        this.emptyChar = (emptyChar == null) ? " " : emptyChar;
        this.devProfile = (profile.equals("dev")) ? true : false;
    }

    public void setWallChar(String wallChar) {
        this.wallChar = wallChar;
    }

    public void setPlayerChar(String playerChar) {
        this.playerChar = playerChar;
    }

    public void setEnemyChar(String enemyChar) {
        this.enemyChar = enemyChar;
    }

    public void setGoalChar(String goalChar) {
        this.goalChar = goalChar;
    }

    public void attachPlayer(PlayerSprite player) {
        this.player = player;
        this.playerCordX = player.getPositionX();
        this.playerCordY = player.getPositionY();
        map[this.playerCordY][this.playerCordX] = player;
    }

    public void attachEnemies(EnemySprite[] enemies) {
        this.enemies = enemies;
        for (EnemySprite es : enemies) {
            map[es.getPositionY()][es.getPositionX()] = es;
        }
    }

    public void attachWalls(WallSprite[] walls) {
        this.walls = walls;
        for (WallSprite ws : walls) {
            map[ws.getPositionY()][ws.getPositionX()] = ws;
        }
    }

    public void attachTarget(TargetSprite target) {
        this.target = target;
        map[target.getPositionY()][target.getPositionX()] = target;
    }


    public int getSize() {
        return size;
    }

    public Sprite[][] getMap() {
        return map;
    }

    public PlayerSprite getPlayer() {
        return this.player;
    }

    public EnemySprite[] getEnemies() {
        return this.enemies;
    }

    public char[][] getCharMap() {
        char[][] charMap = new char[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (map[row][col] == null) {
                    charMap[row][col] = emptyChar.charAt(0);
                } else if (map[row][col] instanceof WallSprite) {
                    charMap[row][col] = wallChar.charAt(0);
                } else if (map[row][col] instanceof PlayerSprite) {
                    charMap[row][col] = playerChar.charAt(0);
                } else if (map[row][col] instanceof EnemySprite) {
                    charMap[row][col] = enemyChar.charAt(0);
                } else if (map[row][col] instanceof TargetSprite) {
                    charMap[row][col] = goalChar.charAt(0);
                }
            }
        }
        return charMap;
    }

    public void update() {
        map[playerCordY][playerCordX] = null;
        playerCordX = player.getPositionX();
        playerCordY = player.getPositionY();
        map[playerCordY][playerCordX] = player;

        for (EnemySprite es : enemies) {
            map[es.getPreviousY()][es.getPreviousX()] = null;
        }
        for (EnemySprite es : enemies) {
            map[es.getPositionY()][es.getPositionX()] = es;
        }
        this.print();
    }

    public void print() {
        if (!devProfile) {
            System.out.print("\033[H\033[J");
        }
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (map[row][col] == null) {
                    System.out.print(colorize(emptyChar, color));
                } else {
                    System.out.print(colorize(map[row][col].getSign(),
                            map[row][col].getColor()));
                }
            }
            System.out.println();
        }
    }
}
