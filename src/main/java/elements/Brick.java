package elements;

import javafx.scene.paint.Color;

public class Brick  {
    
    // Color mapping for bricks
    private static final Color[] colors = {
        Color.web("#DAEFD1"), // Green
        Color.web("#D6EDCB"),
        Color.web("#D1EBC5"),
        Color.web("#CDEAC0"), 
        Color.web("#FFC2A3"), // Orange
        Color.web("#FFBB97"),
        Color.web("#FFB38C"),
        Color.web("#FFAC81"),
        Color.web("#FFAFFA"), // Red
        Color.web("#FFA5A0"),
        Color.web("#FF9B95"),
        Color.web("#FF928B"),
        Color.web("#180EOD")  // Black
    };

    public static final int H = 25;
    public static final int W = 40;
    private int layer;

    public Brick(int n) {
        layer = n;
    }

    /*
     * Brick collision counter.
     * Returns true for destroyed brick; otherwise false.
     */
    public boolean hit() {
        if (layer == 0) return true;
        else if (layer != 12) layer--;
        return false;
    }

    /*
     * Returns the color of this brick.
     */
    public Color getColor() {
        return colors[layer];
    }
}

