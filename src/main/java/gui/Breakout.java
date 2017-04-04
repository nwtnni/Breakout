package gui;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

    private static int stage;
    private static boolean autoplay;
    private static boolean mouse;
    private static boolean keyboard;

    // Take command-line args
    public static void main(String[] args) {

        if (args.length != 2) {
            usage();
            System.exit(1);
        }

        switch (args[0]) {
            case "-a":
                autoplay = true;
                break;
            case "-k":
                keyboard = true;
                break;
            case "-m":
                mouse = true;
                break;
            default:
                usage();
                System.exit(1);
        }
        switch (args[1]) {
            case "1":
                stage = 1;
                break;
            case "2":
                stage = 2;
                break;
            case "3":
                stage = 3;
                break;
            case "4":
                stage = 4;
                break;
            default:
                usage();
                System.exit(1);
        }
        Application.launch(args);
    }

    // Helper method for printing usage
    private static void usage() {
        System.out.println("Usage: java -jar Breakout.jar -<CONTROL> <STAGE>");
        System.out.println("<CONTROL> must be one of the following:");
        System.out.println("a for autoplay");
        System.out.println("m for mouse control");
        System.out.println("k for keyboard control");
        System.out.println("<STAGE> must be one of the following:");
        System.out.println("1 for 1-4 layer bricks");
        System.out.println("2 for 1-8 layer bricks");
        System.out.println("3 for 1-12 layer bricks");
        System.out.println("4 for 1-13 layer bricks");
    }

    @Override
    public void start(Stage primary) throws IOException {

        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/gui.fxml"));
        final Scene scene = new Scene(fxml.load());
        primary.setScene(scene);

        canvas = (Canvas) fxml.getNamespace().get("canvas");

        lf = new LevelFactory();
        l = lf.getLevel(stage, 0);
        lv = new LevelView(canvas, l);

        gl = new GameLogic(l);
        gr = new GraphicsRender(lv);

        // Control setup
        if (autoplay) {
            l.startBall(800, 0);
        }
        else {
            lv.setOnMouseClicked(me -> {
                l.startBall(me.getX(), me.getY());
            });
        }

        if (mouse) {
            lv.setOnMouseMoved(me -> {
                l.movePaddle(me.getX());
            });
        }

        if (keyboard) {
            scene.setOnKeyTyped(kp -> {
                switch (kp.getCharacter()) {
                    case "a":
                        l.slidePaddle(-1);
                        break;
                    case "d":
                        l.slidePaddle(1);
                    default:
                }
            });
        }

        gl.play();
        gr.play();
        primary.show();
    }

    // Next level!
    private void advance(int prevScore) {
        gr.stop();
        gl.stop();

        l = lf.getLevel(stage++, prevScore);
        lv = new LevelView(canvas, l);
        gr = new GraphicsRender(lv);
        gl = new GameLogic(l);

        if (autoplay) {
            l.startBall(800, 0); 
        }

        gr.play();
        gl.play();
    }

    // Game over screen
    private void gameOver() {
        gr.stop();
        gl.stop();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
            lv.gameOver();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // Helper class for rendering graphics
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

    // Helper class for main game loop
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


