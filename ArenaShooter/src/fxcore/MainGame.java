package fxcore;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import objects.Player;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import core.Engine;

public class MainGame extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public final long UPS = 60, FPS = 60;
	
	private Engine engine;
	private volatile boolean paused = false;
	private Stage stage;
	private KeyTracker keyTracker = new KeyTracker();
	private MouseTracker mouse = new MouseTracker();
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
	
	private void runEngine()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				long frameNanoTime = (long)((1/UPS) * 1000_000_000);
				long startFrameTime;
				while(true)
				{
					try
					{
						if(paused)
						{
							Thread.sleep(10);
						}
						else
						{
							startFrameTime = System.nanoTime();
							engine.setPlayerAction(getPlayerAction());
							engine.setCursorLocation(mouse.x(), mouse.y());
							engine.update();
							
							while(System.nanoTime() - startFrameTime < frameNanoTime)
							{
								Thread.sleep(1);	
							}
						}
					}
					catch (InterruptedException e)
					{
						throw new RuntimeException(e);
					}
				}
			}
			
		}).start();
	}
	
	private Player.Action getPlayerAction()
	{
		boolean up = keyTracker.isKeyPressed(KeyCode.W);
		boolean down = keyTracker.isKeyPressed(KeyCode.S);
		boolean right = keyTracker.isKeyPressed(KeyCode.D);
		boolean left = keyTracker.isKeyPressed(KeyCode.A);
		if(mouse.isPressed(MouseButton.PRIMARY))
		{
			return new Player.Action(up, down, left, right, mouse.x(), mouse.y());
		}
		return new Player.Action(up, down, left, right);
	}
	
	

}
