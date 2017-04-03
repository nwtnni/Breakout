package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class Breakout extends Application {

    private Stage primary;

	public static void main(String[] args) {
		Application.launch(args);
	}

    @Override
    public void start(Stage primary) throws IOException {
    
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/gui.fxml"));
        final Scene scene = new Scene(fxml.load());
        primary.setScene(scene);




        primary.show();


    }
    
    
}

    
