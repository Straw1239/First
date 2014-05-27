package main;



import static java.lang.Math.sin;
import static java.lang.Math.sinh;
import static java.lang.Math.tan;
import static java.lang.Math.tanh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import utils.Random;
public class Main extends Application
{
	public static List<UnaryOperator<Double>> functions = new ArrayList<>();
	public static void main(String[] args) 
	{
		functions.add((d) -> 250.0);
		functions.add((d) -> 200 / sin(d * 2));
		functions.add((d) -> 100 * tan(d));
		functions.add((d) -> 300 * sin(d));
		functions.add((d) -> 300 / sin(d));
		functions.add((d) -> 100 / tanh(d));
		functions.add((d) -> 100 / sinh(d));
		functions.add((d) -> 2 * sinh(d) + 40);
		launch(args);
	}
	
	private static final Random rand = Random.create();
	private Stage stage;
	private Renderer renderer;
	private BallSimulator simulation ;
	private int mode = 0;
	private int balls = 4096;
	
	private double width = 1920, height = 1080;
	@Override
	public void start(Stage s) throws Exception 
	{
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		width = bounds.getWidth();
		height = bounds.getHeight();
		stage = s;
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("");
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.addEventFilter(KeyEvent.KEY_PRESSED, (e) ->
		{
			if(e.getCode() == KeyCode.ESCAPE)
			{
				System.exit(0);
			}
			
			if(e.getCode() == KeyCode.R)
			{
				restart();
			}
			
			if(e.getCode() == KeyCode.SPACE)
			{
				mode = (mode + 1) % functions.size();
				restart();
			}
			
			if(e.getCode() == KeyCode.ADD)
			{
				balls *= 2;
				restart();
			}
			
			if(e.getCode() == KeyCode.SUBTRACT)
			{
				balls /= 2;
				restart();
			}
			
		});
		renderer = new Renderer(width, height);
		BorderPane pane = new BorderPane();
		pane.setCenter(renderer);
		stage.setScene(new Scene(pane));
		restart();
		stage.show();
		runEngine();
		animate();
	}
	
	private void restart()
	{
		renderer.getGraphicsContext2D().clearRect(0, 0, width, height);
		paused = true;
		simulation = new BallSimulator(width * 4, height * 4);
		List<Color> colors = new ArrayList<>(Arrays.asList(Color.RED, Color.ORANGERED, Color.ORANGE, Color.YELLOW, Color.YELLOWGREEN, Color.GREEN, Color.TURQUOISE, Color.BLUE, Color.BLUEVIOLET, Color.INDIGO, Color.VIOLET));
		List<Color> temp = new ArrayList<>(colors);
		Collections.reverse(temp);
		colors.addAll(temp);
		for(int i = 0; i < balls; i++)
		{
			double angle = i * 2 * Math.PI / balls;
			double sin = Math.sin(angle);
			double cos = Math.cos(angle);
			Vector v = Vector.fromPolar(functions.get(mode).apply(angle), angle);
			Color c = colors.get(i % colors.size());//new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
			simulation.addBall(new Ball(simulation.dimensions.scale(.5), v, 20, c));
		}
		paused = false;
	}
	private volatile boolean paused = false;;
	private void runEngine()
	{
		new Thread(() ->
		{
			long lastTime = System.nanoTime();
			try
			{
				while(true)
				{
					if(paused)
					{
						Thread.sleep(1);
					}
					else
					{
						long time = System.nanoTime();
						simulation.update(1.0 / 60);
						Thread.sleep((long) (1000.0 / 60));
						lastTime = time;
					}
				}	
			}
			catch(Throwable t)
			{
				throw new InternalError(t);
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
				//System.out.println((frames) / ((t - time) / 1_000_000_000.0));
			}
			
		}.start();
	}

}
