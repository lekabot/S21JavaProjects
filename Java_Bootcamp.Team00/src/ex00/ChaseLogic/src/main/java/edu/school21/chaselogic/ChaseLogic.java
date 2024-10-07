package edu.school21.chaselogic;

public class ChaseLogic {
    public static Point getNextMove(Point enemyPos, Point playerPos, char[][] map) {
        Point primaryDirection;
        Point alternativeDirection;

        int distanceDiffX = playerPos.x - enemyPos.x;
        int distanceDiffY = playerPos.y - enemyPos.y;

        if (Math.abs(distanceDiffX) > Math.abs(distanceDiffY)) {
            if (distanceDiffX > 0) {
                primaryDirection = new Point(1, 0);
            } else {
                primaryDirection = new Point(-1, 0);
            }
            if (distanceDiffY > 0) {
                alternativeDirection = new Point(0, 1);
            } else {
                alternativeDirection = new Point(0, -1);
            }
        } else {
            if (distanceDiffY > 0) {
                primaryDirection = new Point(0, 1);
            } else {
                primaryDirection = new Point(0, -1);
            }
            if (distanceDiffX > 0) {
                alternativeDirection = new Point(1, 0);
            } else {
                alternativeDirection = new Point(-1, 0);
            }
        }

        Point primaryMove = new Point(enemyPos.x + primaryDirection.x, enemyPos.y + primaryDirection.y);
        if (isWithinBounds(primaryMove, map)) {
            return primaryMove;
        }

        Point alternativeMove = new Point(enemyPos.x + alternativeDirection.x, enemyPos.y + alternativeDirection.y);
        if (isWithinBounds(alternativeMove, map)) {
            return  alternativeMove;
        }
        return enemyPos;
    }

    private static boolean isWithinBounds(Point point, char[][] map) {
        return point.x >= 0 && point.x < map.length && point.y >= 0 && point.y < map[0].length;
    }
}
