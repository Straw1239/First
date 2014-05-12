package main;



import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
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
		renderer = new Renderer(1080, 1080);
		BorderPane pane = new BorderPane();
		pane.setCenter(renderer);
		stage.setScene(new Scene(pane));
		simulation = new BallSimulator(1000,1000);
		
		for(int i = 0; i < 8192; i++)
		{
			Vector v = new Vector(rand.nextDouble() - .5, rand.nextDouble() - .5).normalized().scale(250);
			Color c = new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
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
				simulation.update((time - lastTime) / 1_000_000_000.0);
				lastTime = time;
			}	
		}).start();
	}
	
	public void animate()
	{
		new AnimationTimer()
		{
			@Override
			public void handle(long t) 
			{
				renderer.render(simulation.getState(), simulation.dimensions);	
			}
			
		}.start();
	}

}
