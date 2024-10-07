package edu.school21.game;

import static com.diogonunes.jcolor.Attribute.*;

import com.diogonunes.jcolor.Attribute;


public class Sprite {
    protected int cordX;
    protected int cordY;
    protected String sign;
    protected Attribute colorRGB;

    public Sprite(int cordX, int cordY, String sign, String color) {
        this.cordX = cordX;
        this.cordY = cordY;
        this.sign = sign;
        this.colorRGB = BACK_COLOR(
                Colors.getColorRGB(color)[0],
                Colors.getColorRGB(color)[1],
                Colors.getColorRGB(color)[2]);
    }

    public int getPositionX() {
        return cordX;
    }

    public int getPositionY() {
        return cordY;
    }

    public String getSign() {
        return sign;
    }

    public Attribute getColor() {
        return colorRGB;
    }

    protected boolean isCollision(Sprite obj) {
        return cordX == obj.getPositionX()
                && cordY == obj.getPositionY();
    }
}
