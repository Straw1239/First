package fxcore;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import core.Engine;

public class MainGame extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}
	
	private Engine engine;
	private volatile boolean paused = false;
	private Stage stage;
	private KeyTracker keyTracker = new KeyTracker();
	private Group root = new Group();
	private Renderer renderer;

	@Override
	public void start(Stage s) throws Exception
	{
		this.stage = s;
		stage.addEventFilter(KeyEvent.KEY_PRESSED, e -> 
		{
			if(e.getCode() == KeyCode.ESCAPE)
			{
				if(paused) System.exit(0);
				paused = true;
			}
			
			if(e.getCode() == KeyCode.P)
			{
				paused = !paused;
			}
		});
		stage.setFullScreenExitHint("");
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.setFullScreen(true);
		stage.setResizable(false);
		stage.addEventFilter(KeyEvent.ANY, keyTracker);
		stage.show();
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		renderer = new Renderer(width, height);
		
		
	}
	
	private static void runEngine()
	{
		
	}
	
	

}
