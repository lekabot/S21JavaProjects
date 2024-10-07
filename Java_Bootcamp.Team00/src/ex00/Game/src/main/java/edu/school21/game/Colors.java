package edu.school21.game;

import javax.management.RuntimeErrorException;
import java.util.Map;

public class Colors {
    private static Map<String, int[]> colors = Map.of(
            "WHITE",  new int[] {255, 255, 255},
            "BLACK",  new int[] {0, 0, 0},
            "BLUE",   new int[] {0, 0, 255},
            "RED",    new int[] {255, 0, 0},
            "GREEN",  new int[] {0, 255, 0},
            "PINK",   new int[] {255, 0, 255},
            "SAND",   new int[] {255, 204, 153},
            "BROWN",  new int[] {102, 51, 0},
            "YELLOW", new int[] {255, 255, 0});

    public static int[] getColorRGB(String color) {
        if (!colors.containsKey(color)) {
            throw new RuntimeErrorException(null, "No such color");
        }
        return colors.get(color);
    }
}
