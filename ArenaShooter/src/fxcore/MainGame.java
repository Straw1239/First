package fxcore;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Calendar;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.Player;
import utils.XRandom;

public class MainGame extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}
	
	private static MainGame app;
	
	public static long getTime()
	{
		return app.engine.getTime();
	}
	
	public static final Random rand = new XRandom(((System.nanoTime() + Runtime.getRuntime().hashCode() * 7) + Thread.currentThread().hashCode()) * 31 + Calendar.getInstance().hashCode());
	
	
	public final long UPS = 60, FPS = 60;
	
	private Engine engine;
	private volatile boolean paused = false;
	private Stage stage;
	private KeyTracker keyTracker = new KeyTracker();
	private MouseTracker mouse = new MouseTracker();
	private Group root = new Group();
	private Scene scene;
	private Renderer renderer;
	
	public void init()
	{
		Platform.setImplicitExit(true);
	}

	@Override
	public void start(Stage s) throws Exception
	{
		app = this;
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
			
			if(e.getCode() == KeyCode.R)
			{
				engine = new Engine(renderer.width, renderer.height);
			}
		});
		stage.addEventFilter(KeyEvent.ANY, keyTracker);
		stage.addEventFilter(MouseEvent.ANY, mouse);
		stage.setFullScreenExitHint("");
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.setFullScreen(true);
		stage.setResizable(false);
		
		
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		renderer = new Renderer(width, height);
		engine = new Engine(width, height);
		root.getChildren().add(renderer.canvas);
		scene = new Scene(root);
		scene.setCursor(Cursor.NONE);
		stage.setScene(scene);
		runEngine();
		runGraphics();
		stage.show();
	}
	
	private void runEngine()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				long frameNanoTime = (1_000_000_000L / UPS);
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
	
	private void runGraphics()
	{
		new AnimationTimer()
		{

			@Override
			public void handle(long now)
			{
				renderer.render(engine.getDisplay());
			}
			
		}.start();
	}
	
	

}
