package javafxgui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JavaFXTest extends Application
{
	public static void main(String[] args)
	{
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		stage.setFullScreen(true);
		Canvas canvas = new Canvas(1920, 1080);//new Canvas(stage.getWidth(), stage.getHeight());
		GraphicsContext g = canvas.getGraphicsContext2D();
		g.setFill(Color.GREEN);
		g.scale(1.5, .5);
		
		g.fillText("I'm a JavaFX application!", 500, 500);
		g.fillOval(50, 50, 50, 50);
		Group root = new Group();
		root.getChildren().add(canvas);
		stage.setScene(new Scene(root));
		stage.show();
		
		
		
	}
	

}
