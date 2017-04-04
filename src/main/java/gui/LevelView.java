package gui;

import elements.Ball;
import elements.Brick;
import elements.Paddle;

import level.Level;

import javafx.event.EventHandler;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class LevelView {

    private Level level;
    private Canvas canvas;

    private static final int SCRH = 800;
    private static final int SCRW = 800;

    public LevelView(Canvas c, Level l) {
        canvas = c;
        level = l;
    }

    public void redraw() {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Brick[][] bricks = level.getBricks();
        int cols = bricks.length;
        int rows = bricks[0].length;

        for (double y = 50, c = 0; c < cols; c++, y += Brick.H + 10) {
            for (double x = 27.5, r = 0; r < rows; r++, x += Brick.W + 5) {
                Brick b = bricks[(int) c][(int) r];
                if (b == null) continue;
                gc.setFill(b.getColor());
                gc.fillRect(x, y, Brick.W, Brick.H);
            }
        }

        // Draw score
        String score = "Score: " + level.getScore();
        gc.strokeText(score, SCRW - 75, 20, 75);

        // Draw lives
        gc.setFill(Ball.C);
        for (int x = Ball.R, l = 0; l < level.getLives(); x += Ball.R * 2, l++) {
             gc.fillOval(x, Ball.R, Ball.R, Ball.R);
        }

        // Draw ball
        double[] ball = level.getBall();
        gc.fillOval(ball[0] - Ball.R, ball[1] - Ball.R, Ball.R * 2, Ball.R * 2);

        // Draw paddle
        double[] paddle = level.getPaddle();
        gc.setFill(Paddle.C);
        gc.fillRect(paddle[0], paddle[1], Paddle.W, Paddle.H);
    }

    public void gameOver() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Image gameover = new Image(getClass().getResourceAsStream("gameover.png"));
        gc.drawImage(gameover, 0, 0, SCRW, SCRH);
    }

    public void setOnMouseMoved(EventHandler<? super MouseEvent> handler) {
        canvas.setOnMouseMoved(handler); 
    }

    public void setOnMouseClicked(EventHandler<? super MouseEvent> handler) {
        canvas.setOnMouseClicked(handler); 
    }
}
