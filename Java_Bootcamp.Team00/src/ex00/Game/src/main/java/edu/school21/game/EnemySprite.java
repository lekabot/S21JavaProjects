package edu.school21.game;

import edu.school21.chaselogic.ChaseLogic;
import edu.school21.chaselogic.Point;

public class EnemySprite extends Sprite {
    private GameMap map;
    private int prevX;
    private int prevY;

    public EnemySprite(GameMap map, int cordX, int cordY, String sign, String color) {
        super(cordX, cordY, sign, color);
        this.map = map;
        this.prevX = cordX;
        this.prevY = cordY;
    }

    public void move() {
        Point enemyPos = new Point(cordX, cordY);
        Point playerPos = new Point(map.getPlayer().getPositionX(), map.getPlayer().getPositionY());
        Point nextMove = ChaseLogic.getNextMove(enemyPos, playerPos, map.getCharMap());

        if (isAvailable(nextMove.x, nextMove.y)) {
            this.prevX = this.cordX;
            this.prevY = this.cordY;
            this.cordX = nextMove.x;
            this.cordY = nextMove.y;
        }
    }

        private boolean isAvailable(int xCoord, int yCoord) {
        if ((xCoord < 0 || xCoord >= this.map.getSize()) || (yCoord < 0 || yCoord >= this.map.getSize())) {
            return false;
        }
        Sprite[][] map = this.map.getMap();
        if (map[yCoord][xCoord] != null) {
            String spriteName = map[yCoord][xCoord].getClass().getSimpleName();
            if (spriteName.equals("WallSprite") || spriteName.equals("EnemySprite") || spriteName.equals("TargetSprite")) {
                return false;
            }
        }
        for (EnemySprite enemy : this.map.getEnemies()) {
            if (enemy != this && enemy.getPositionX() == xCoord && enemy.getPositionY() == yCoord) {
                return false;
            }
        }
        return true;
    }

    public int getPreviousX() {
        return prevX;
    }

    public int getPreviousY() {
        return prevY;
    }

}
