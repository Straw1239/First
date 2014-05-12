package subtitles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		
		primaryStage.setTitle("MEDIA");
		Media media = new Media("file:///d:/git/First/First/media/music.mp4");
		MediaPlayer player = new MediaPlayer(media);
		player.setAutoPlay(true);
		player.startTimeProperty().set(Duration.seconds(15));
		MediaView view = new MediaView(player);
		primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, (e) ->
		{
			if(e.getCode() == KeyCode.R)
			{
				player.seek(Duration.ZERO);
			}
		});
	
		player.rateProperty().set(.9);
	
		
		StackPane p = new StackPane();
		p.getChildren().add(view);
		
		Scene scene = new Scene(p, 500, 500);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}

}
