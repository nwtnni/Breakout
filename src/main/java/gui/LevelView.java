package gui;

import elements.Brick;
import level.Level;

import javafx.event.EventHandler;
import javafx.scene.canvas.*;
import javafx.scene.input.MouseEvent;

public class LevelView {

    private Level level;
    private Canvas canvas;

    public LevelView(Canvas c) {
        canvas = c;
    }

    public void setLevel(Level l) {
        level = l;
    }

    public void redraw() {

        double x = 27.5;
        double y = 50;

        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (Brick[] col : level.getBricks()) {

            for (Brick b: col) {
                if (b != null) {
                    gc.setFill(b.getColor());
                    gc.fillRect(x, y, Brick.W, Brick.H);
                }
                x += Brick.W + 5;
            }

            x = 5;
            y += Brick.H + 10;
        }
    }

    public void setOnMouseMoved(EventHandler<? super MouseEvent> handler) {
        canvas.setOnMouseMoved(handler); 
    }
}
