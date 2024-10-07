package edu.school21.game;

public class Enemies {
    private EnemySprite[] enemies;

    public Enemies(EnemySprite[] enemies) {
        this.enemies = enemies;
    }

    public boolean isCollision(Sprite obj) {
        for (EnemySprite es : enemies) {
            if (es.isCollision(obj)) {
                return true;
            }
        }
        return false;
    }

    public void move() {
        for (EnemySprite es : enemies) {
            es.move();
        }
    }
}
