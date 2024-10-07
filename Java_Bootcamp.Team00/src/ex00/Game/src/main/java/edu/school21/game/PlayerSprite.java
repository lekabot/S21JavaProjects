package edu.school21.game;

import java.util.Scanner;

public class PlayerSprite extends Sprite {
    private Scanner scan;
    private GameMap gameMap;

    public  PlayerSprite(GameMap map, int cordX, int cordY, String sign, String color) {
        super(cordX, cordY, sign, color);
        this.scan = new Scanner(System.in);
        this.gameMap = map;
    }

    public void move() {
        String direction = null;
        boolean wrongDir = true;

        while (wrongDir) {
            direction = this.scan.nextLine();
            switch (direction) {
                case "w":
                    if (isAvailable(cordX, cordY - 1)) {
                        this.cordY--;
                    }
                    wrongDir = false;
                    break;
                case "a":
                    if (isAvailable(cordX - 1, cordY)) {
                        cordX--;
                    }
                    wrongDir = false;
                    break;
                case "s":
                    if (isAvailable(cordX, cordY + 1)) {
                        cordY++;
                    }
                    wrongDir = false;
                    break;
                case "d":
                    if (isAvailable(cordX + 1, cordY)) {
                        cordX++;
                    }
                    wrongDir = false;
                    break;
                case "9":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong direction!");
                    break;
            }
        }
    }

    private boolean isAvailable(int cordX, int cordY) {
        if (cordX < 0 || cordX >= this.gameMap.getSize() || cordY < 0 || cordY >= this.gameMap.getSize()) {
            return false;
        }

        if (gameMap.getMap()[cordY][cordX] instanceof WallSprite) {
            return false;
        }

        return true;
    }
}
