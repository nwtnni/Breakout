package gui;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.fxml.FXMLLoader;

import level.*;

public class Breakout extends Application {

    private LevelView lv;
    private Level l;
    private LevelFactory lf;

    private GraphicsRender gr;
    private GameLogic gl;
    private Canvas canvas;

    private int stage;
    private static boolean autoplay;


    public static void main(String[] args) {
        if (args[0].equals("auto")) {
            autoplay = true;
        }
        else {
            autoplay = false;
        }
        Application.launch(args);
    }

    @Override
    public void start(Stage primary) throws IOException {

        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/gui.fxml"));
        final Scene scene = new Scene(fxml.load());
        primary.setScene(scene);

        stage = 1;
        canvas = (Canvas) fxml.getNamespace().get("canvas");

        lf = new LevelFactory();
        l = lf.getLevel(stage, 0);
        lv = new LevelView(canvas, l);

        gl = new GameLogic(l);
        gr = new GraphicsRender(lv);

        if (!autoplay) {
            lv.setOnMouseMoved(me -> {
                l.movePaddle(me.getX());
            });
        }

        lv.setOnMouseClicked(me -> {
            l.startBall(me.getX(), me.getY());
        });

        gl.play();
        gr.play();
        primary.show();
    }

    private void advance(int prevScore) {
        gr.stop();
        gl.stop();

        l = lf.getLevel(stage++, prevScore);
        lv = new LevelView(canvas, l);
        gr = new GraphicsRender(lv);
        gl = new GameLogic(l);

        gr.play();
        gl.play();
    }

    private void gameOver() {
        gr.stop();
        gl.stop();
        lv.gameOver();
    }

    private class GraphicsRender {

        private Timeline gr;

        private GraphicsRender(LevelView lv) {

            KeyFrame render = new KeyFrame(Duration.millis(1000.0 / 60.0), ae -> {
                lv.redraw();
            });
            gr = new Timeline(30, render);
            gr.setCycleCount(Timeline.INDEFINITE);

        }

        private void play() {
            gr.play();
        }

        private void stop() {
            gr.stop();
        }
    }

    private class GameLogic {

        private Timeline gl;

        private GameLogic(Level level) {

            KeyFrame logic = new KeyFrame(Duration.millis(1000.0 / 60.0), ae -> {
                int result = level.step();

                if (autoplay) {
                    level.movePaddle(level.getBall()[0]);
                }

                // Level complete!
                if (result == 1) {
                    advance(level.getScore());
                }
                else if (result == 2) {
                    gameOver();
                }
            });
            gl = new Timeline(60, logic);
            gl.setCycleCount(Timeline.INDEFINITE);
        }

        private void play() {
            gl.play();
        }

        private void stop() {
            gl.stop();
        }
    }
}


