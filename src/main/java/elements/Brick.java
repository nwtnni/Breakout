package elements;

import javafx.scene.paint.Color;

public class Brick  {
    
    // Color mapping for bricks
    private static final Color[] colors = {
        Color.web("#CFE4AB"), // Green
        Color.web("#9FC957"),
        Color.web("#71A516"),
        Color.web("#44630E"), 
        Color.web("#FDE2B2"), // Orange
        Color.web("#FCC566"),
        Color.web("#E5A029"),
        Color.web("#896019"),
        Color.web("#FDC5A4"), // Red
        Color.web("#FC8C4A"),
        Color.web("#E55907"),
        Color.web("#893504"),
        Color.web("#000000")  // Black
    };

    public static final int H = 15;
    public static final int W = 45;
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

