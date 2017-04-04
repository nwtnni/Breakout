package gui;

import elements.Ball;
import elements.Brick;
import elements.Paddle;

import level.Level;

import javafx.event.EventHandler;
import javafx.scene.canvas.*;
import javafx.scene.input.MouseEvent;

public class LevelView {

    private Level level;
    private Canvas canvas;

    private static final int SCRW = 800;

    public LevelView(Canvas c) {
        canvas = c;
    }

    public void setLevel(Level l) {
        level = l;
    }

    public void redraw() {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Brick[][] bricks = level.getBricks();
        int cols = bricks.length;
        int rows = bricks[0].length;

        for (double y = 50, c = 0; c < cols; c++, y += Brick.H + 10) {
            
            for (double x = 27.5, r = 0; r < rows; r++, y += Brick.W + 5) {
                Brick b = bricks[(int) c][(int) r];
                gc.setFill(b.getColor());
                gc.fillRect(x, y, Brick.W, Brick.H);
            }
        }

        // Draw score
        String score = "Score: " + level.getScore();
        gc.strokeText(score, SCRW - 200, Ball.R, 200);

        // Draw lives
        gc.setFill(Ball.C);
        for (int x = Ball.R, l = 0; l < level.getLives(); x += Ball.R * 2, l++) {
             gc.fillOval(x, Ball.R, Ball.R, Ball.R);
        }

        // Draw ball
        double[] ball = level.getBall();
        gc.fillOval(ball[0], ball[1], Ball.R * 2, Ball.R * 2);

        // Draw paddle
        double[] paddle = level.getPaddle();
        gc.setFill(Paddle.C);
        gc.fillRect(paddle[0], paddle[1], Paddle.W, Paddle.H);
    }

    public void setOnMouseMoved(EventHandler<? super MouseEvent> handler) {
        canvas.setOnMouseMoved(handler); 
    }
}
