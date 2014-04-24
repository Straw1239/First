package fxcore;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MainGame extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}
	
	private Stage stage;
	private KeyTracker keyTracker = new KeyTracker();
	Group root = new Group();

	@Override
	public void start(Stage s) throws Exception
	{
		this.stage = s;
		stage.setFullScreenExitHint("");
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.setFullScreen(true);
		stage.setResizable(false);
		stage.addEventFilter(KeyEvent.ANY, keyTracker);
		stage.show();
		
		
		
		
		
		
	}

}
