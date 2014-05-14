package main;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.Random;

public class Main extends Application
{

	public static void main(String[] args) 
	{
		launch(args);
	}
	
	private static final Random rand = Random.create();
	private Stage stage;
	private Renderer renderer;
	private BallSimulator simulation ;
	@Override
	public void start(Stage arg0) throws Exception 
	{
		stage = arg0;
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("");
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.addEventFilter(KeyEvent.KEY_PRESSED, (e) ->
		{
			if(e.getCode() == KeyCode.ESCAPE)
			{
				System.exit(0);
			}
		});
		renderer = new Renderer(1920, 1080);
		BorderPane pane = new BorderPane();
		pane.setCenter(renderer);
		stage.setScene(new Scene(pane));
		simulation = new BallSimulator(1920 * 4, 1080 * 4);
		int balls = 2048;
		List<Color> colors = new ArrayList<>(Arrays.asList(Color.RED, Color.ORANGERED, Color.ORANGE, Color.YELLOW, Color.YELLOWGREEN, Color.GREEN, Color.TURQUOISE, Color.BLUE, Color.BLUEVIOLET, Color.INDIGO, Color.VIOLET));
		List<Color> temp = new ArrayList<>(colors);
		Collections.reverse(temp);
		colors.addAll(temp);
		for(int i = 0; i < balls; i++)
		{
			double angle = i * 2 * Math.PI / balls;
			Vector v = Vector.fromPolar(1 / Math.sin(angle) * 100, angle);
			Color c = colors.get(i % colors.size());//new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
			simulation.addBall(new Ball(simulation.dimensions.scale(.5), v, 20, c));
		}
		stage.show();
		runEngine();
		animate();
	}
	
	private void runEngine()
	{
		new Thread(() ->
		{
			long lastTime = System.nanoTime();
			while(true)
			{
				long time = System.nanoTime();
				simulation.update(1.0 / 60);
				try
				{
					Thread.sleep((long) (1000.0 / 60));
				}
				catch (Exception e)
				{
					throw new RuntimeException(e);
				}
				lastTime = time;
			}	
		}).start();
	}
	
	public void animate()
	{
		new AnimationTimer()
		{
			long time = System.nanoTime();
			long frames = 0;	
			@Override
			public void handle(long t) 
			{
				renderer.render(simulation.getState(), simulation.dimensions);	
				frames++;
				System.out.println((frames) / ((t - time) / 1_000_000_000.0));
			}
			
		}.start();
	}

}
