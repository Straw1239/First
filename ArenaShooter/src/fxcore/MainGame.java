package fxcore;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
import player.Player;
import utils.Utils;
import utils.XRandom;
import engine.Engine;
import engine.State;
import static utils.Utils.*;

public class MainGame extends Application
{
	/**
	 * Main random number generator for the game. Use instead of creating separate Random instances.
	 */
	public static final Random rand = new XRandom(((System.nanoTime() + Runtime.getRuntime().hashCode() * 7) + Thread.currentThread().hashCode()) * 31 + Calendar.getInstance().hashCode());
	
	private static MainGame app;
	
	/**
	 * Absolute x coordinate of the mouse
	 * @return
	 */
	public static double mouseX()
	{
		return app.mouse.x();
	}
	
	/**
	 * Absolute y coordinate of the mouse
	 * @return
	 */
	public static double mouseY()
	{
		return app.mouse.y();
	}
	
	/**
	 * In-game virtual x coordinate of the mouse
	 * @return
	 */
	public static double mouseGameX()
	{
		return app.mouse.gameX(app.engine.getState());
	}
	
	/**
	 * In-game virtual y coordinate of the mouse
	 * @return
	 */
	public static double mouseGameY()
	{
		return app.mouse.gameY(app.engine.getState());
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	/**
	 * Number of engine cycles so far, the current time of the game.
	 * @return
	 */
	public static long getTime()
	{
		return app.engine.getTime();
	}
	
	public static double getScreenHeight()
	{
		return app.renderer.height;
	}
	
	public static double getScreenWidth()
	{
		return app.renderer.width;
	}
	
	public static double getGameWidth()
	{
		return app.engine.width;
	}
	
	public static double getGameHeight()
	{
		return app.engine.height;
	}
	
	/**
	 * 
	 */
	public final long UPS = 60, FPS = 60;
	
	private Engine engine;
	private volatile boolean paused = false;
	private Stage stage;
	private KeyTracker keyTracker = new KeyTracker();
	private MouseTracker mouse = new MouseTracker();
	private Group root = new Group();
	private Scene scene;
	private Renderer renderer;
	
	
	@Override
	public void init()
	{
		Platform.setImplicitExit(true);
		app = this;
	}

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
			
			if(e.getCode() == KeyCode.R)
			{
				engine.reset();
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
		engine = new Engine(3000, 3000);
		//engine.bounds = Circle.of(0, 0, 300);
		
		root.getChildren().add(renderer.canvas);
		scene = new Scene(root);
	
		scene.setCursor(Cursor.NONE);
		stage.setScene(scene);
		stage.show();
		runMenu();
		
	}

	private void runMenu()
	{
		
		
	}

	private void runGame()
	{
		runEngine();
		runGraphics();
	}
	
	private void runEngine()
	{
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace(System.err));
		long frameNanoTime = (1_000_000_000L / UPS);
		
		compute.scheduleAtFixedRate(() -> 
		{
			engine.update();
		}, 0, frameNanoTime, TimeUnit.NANOSECONDS);
		
	}
	
	
	
	private void runGraphics()
	{
		new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				renderer.render(engine.getState());
			}
			
		}.start();
	}
	
	public static class Dimension
	{
		public final double width;
		public final double height;
		
		public Dimension(double width, double height)
		{
			this.width = width;
			this.height = height;
		}
	}

	public static void sleep(long time)
	{
		//app.engine.sleep(time); Currently sleep is disabled
	}
	
	

}
