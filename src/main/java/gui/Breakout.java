package gui;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.concurrent.ScheduledService;

import level.*;

public class Breakout extends Application {

    private LevelView lv;
    private Level l;
    private LevelFactory lf;
    private int stage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primary) throws IOException {

        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/gui.fxml"));
        final Scene scene = new Scene(fxml.load());
        primary.setScene(scene);

        lf = new LevelFactory();
        lv = new LevelView((Canvas) fxml.getNamespace().get("canvas"));

        stage = 1;
        l = lf.getLevel(stage);
        lv.setLevel(l);

        KeyFrame render = new KeyFrame(Duration.millis(1000.0 / 30.0), ae -> {
            lv.redraw();
        });
        Timeline view = new Timeline(30, render);
        view.setCycleCount(Timeline.INDEFINITE);

        KeyFrame gameLogic = new KeyFrame(Duration.millis(1000.0 / 30.0), ae -> {

         









        });
        Timeline logic = new Timeline(30, gameLogic);
        logic.setCycleCount(Timeline.INDEFINITE);

    }
}


