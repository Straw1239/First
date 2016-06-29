package main;



import static java.lang.Math.*;
import static java.util.stream.Collectors.toCollection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import utils.Random;
public class Main extends Application
{
	public static List<DoubleUnaryOperator> functions = new ArrayList<>();
	public static void main(String[] args) 
	{
		//functions.add(a -> );
		//functions.add(d -> 200*sin(20*d));
		functions.add(d -> 141 / pow(log(d), 2.72));
		functions.add(d -> 250.0);
		functions.add(d -> 200 / sin(d * 2));
		functions.add(d -> 100 * tan(d));
		functions.add(d -> 300 * sin(d));
		functions.add(d -> 300 / sin(d));
		functions.add(d -> log(d) * 200);
		functions.add(d -> 100 / tanh(d));
		functions.add(d -> 100 / sinh(d)); 
		functions.add(d -> 2 * sinh(d) + 40);
		functions.add(d -> 30 * pow(log(d), 1.5));
		functions.add(d -> 30 * pow(log(tan(d)), 3));
		functions.add(d -> 70 * log(tan(d)));
		functions.add(d -> 30 * pow(1 / cos(d), 1.3));
		functions.add(d ->  300* pow(sin(2 *d), 3));
		functions.add(d -> 30 * pow(1 / cos(d), 2));
		functions.add(d -> 30 * pow(tan(d), 2));
		functions.add(d -> 80 * sqrt(tan(d)));
		functions.add(d -> 200*cos(sin(d)));
		
		
		launch(args);
	}
	
	
	private void readParams(String string)
	{
		File inf = new File(string);
		if(inf.exists())
		try(Scanner in = new Scanner(inf);)
		{
			while(in.hasNextLine())
			{
				Scanner line = new Scanner(in.nextLine());
				String param = line.next();
				String equals = line.findInLine("\\s*=\\s*");
				if(!equals.trim().equals("=")) throw new IllegalArgumentException("Error: expecting = sign");
				setParameter(param, line);
				line.close();
			}
		}
		catch (FileNotFoundException e)
		{
			throw new IllegalArgumentException(e);
		}
		
		
	}

	int cycles = 128;
	long jumpTime = 0;
	double spinRate = 0;
	private void setParameter(String param, Scanner line)
	{
		switch(param)
		{
		case "samples": balls = divisions = line.nextInt(); break;
		case "colors" :
				line.useDelimiter("[\\s,]+");
				colors.clear();
				while(line.hasNext())
				{
					colors.add(Color.web(line.next()));
				}
				line.reset();
				break;
		case "cycles": cycles = line.nextInt(); break;
		case "time" : jumpTime = line.nextLong() * 1_000_000_000L; break;
		case "spinrate": spinRate = line.nextDouble(); break;
		case "pattern": mode = line.nextInt(); break;
		case "speed": speed = line.nextDouble(); break;
		default: System.err.println("Error: parameter \"" + param + "\" not recognized"); break;
		}
		
	}


	public static final Random rand = Random.create();
	private Stage stage;
	private Scene scene;
	private Renderer renderer;
	private BallSimulator simulation ;
	private int mode = 0;
	private int balls = 4096;
	private List<Image> snapshots = new ArrayList<>();
	private Rectangle2D bounds = Screen.getPrimary().getBounds();
	private double width = bounds.getWidth(), height = bounds.getHeight();
	private DoubleFunction<Color> colorProfile;
	public synchronized WritableImage snapshot()
	{
		paused = true;
		WritableImage result = scene.snapshot(null);
		paused = false;
		return result;
		
	}
	
	@Override
	public void start(Stage s) throws Exception 
	{
		readParams("rainbow.config");
		//Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		//width = bounds.getWidth();
		//height = bounds.getHeight();
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
			if(e.getCode() == KeyCode.F12)
			{
				snapshots.add(snapshot());
			}
			if(e.isControlDown() && e.getCode() == KeyCode.S)
			{
				saveImage();
				//saveImageGroups();
			}
			
		});
		initColors();
		renderer = new Renderer(width, height);
		//renderer.setEffect(new BoxBlur());
		BorderPane pane = new BorderPane();
		pane.setCenter(renderer);
		scene = new Scene(pane);
		stage.setScene(scene);
		restart();
		stage.show();
		runEngine();
		animate();
		
		//exactAnimate();
	}
	double speed = 1;
	static double calcPosition(double coord, double dim)
	{
		if(coord < 0)
		{
			coord += 2 * dim * (Math.ceil((-coord / (2 * dim))) + 2);
		}
		coord %= (2 * dim);
		if(coord > dim)
		{
			coord = 2*dim - coord;
		}
		
		return coord;
	}
	
	long startTime = System.nanoTime();
	int divisions = 256;
	void exactAnimate()
	{
		startTime = System.nanoTime();
		new AnimationTimer() 
		{
			@Override
			public void handle(long now)
			{
				long time = now - startTime + jumpTime; 
				//int divisions = (int)(time / 1_000_00000);
				//if(divisions <= (time * 50) / 1_000_000_000) divisions *= 2;
				double oldX = 0, oldY = 0;
				double oldD = 0;
				Color lastColor;
				{
					double angle = (divisions - 1) * 2 * Math.PI / divisions;
					double v = functions.get(mode).applyAsDouble(angle);
					double d = (v * time) / 1_000_000_0000l * 2.45 * speed;
					double x = d * cos(angle), y = d * sin(angle);
					x += width / 2;
					y += height / 2;
					x = calcPosition(x, width);
					y = calcPosition(y, height);
					oldD = d;
					oldX = x;
					oldY = y;
					lastColor = colorProfile.apply(angle);
				}
				for(int i = 0; i < divisions; i++)
				{
					
					double angle = i * 2 * Math.PI / divisions;
					double v = functions.get(mode).applyAsDouble(angle);
					double d = (v * time) / 1_000_000_0000l * 2.45 * speed;
					double x = d * cos(angle), y = d * sin(angle);
					x += width / 2;
					y += height / 2;
					x = calcPosition(x, width);
					y = calcPosition(y, height);
					
					GraphicsContext g = renderer.getGraphicsContext2D();
					double distance = Math.hypot(x - oldX, y - oldY);
					Color c = colorProfile.apply(angle);
					Paint p = new LinearGradient(oldX, oldY, x, y, false, CycleMethod.NO_CYCLE, new Stop(0, lastColor), new Stop(1, c));
					//g.setStroke(colors.get(i % colors.size()));
					g.setStroke(p);
					g.setLineWidth(4);
					g.beginPath();
					g.moveTo(oldX, oldY);
					g.lineTo(x, y);
					g.stroke();
					
					oldD = d;
					oldX = x;
					oldY = y;
					lastColor = c;
				}
				
			}
			
		}.start();
	}
	
	
	
	private synchronized void saveImage()
	{
		paused = true;
		double width = 0, height = snapshots.get(0).getHeight();
		for(Image i : snapshots) width += i.getWidth();
		WritableImage output = new WritableImage((int)width, (int)height);
		PixelWriter writer = output.getPixelWriter();
		int location = 0;
		for(Image i : snapshots)
		{
			writer.setPixels(location, 0, (int)i.getWidth(), (int) height, i.getPixelReader(), 0, 0);
			location += i.getWidth();
		}
		try
		{
			ImageIO.write(SwingFXUtils.fromFXImage(output, null), "png", new File("screenshot.png"));
		}
		catch (IOException e)
		{
			System.out.println("Failed to write image to file:");
			e.printStackTrace();
		}
		paused = false;
	}
	
	private synchronized void saveImageGroups()
	{
		paused = true;
		FXImages.writeInGroups(snapshots, 4);
		paused = false;
	}
	List<Color> colors = Arrays.asList("9211E9", "000000", "181EC3", "10C2FE").stream().map(Color::web).collect(toCollection(ArrayList::new));
	private void initColors()
	{
		colorProfile = a -> 
		{
			double start = spinRate * log(((System.nanoTime() + 1 - startTime) / 1_000_000_000.0)) % (2 * PI);
			double sectorAngle =  2 * PI / cycles;
			List<Color> bases = colors;
			a += start;
			a += sectorAngle * (round(a / sectorAngle) + 1);
			a %= sectorAngle;
			double section = a / sectorAngle;
			int startIndex = (((int) (section * bases.size())) + bases.size() - 1) % bases.size();
			Color c1 = bases.get(startIndex), c2 = bases.get((startIndex + 1) % bases.size());
			double inter = (section % (1.0 / bases.size())) * bases.size();
			return c1.interpolate(c2, inter);
			
		};
		//genColorProfile(colors,  , cycles).apply(a);
		
		int inter = 3;
		for(int i = 0; i < colors.size(); i++)
		{
			Color c1 = colors.get(i); 
			Color c2 = colors.get((i + 1) % colors.size());
			List<Color> between = new ArrayList<>();
			for(int j = 1; j <= inter; j++)
			{
				between.add(c1.interpolate(c2, j * 1.0 / (inter + 1)));
			}
			colors.addAll((i + 1) % colors.size(), between);
			i += inter;
		}
		
	}
	static DoubleFunction<Color> genColorProfile(List<Color> bases, double start, int repeats)
	{
		final double sectorAngle = 2 * PI / repeats;
		return (a -> 
		{
			a += start;
			a += sectorAngle * (round(a / sectorAngle) + 1);
			a %= sectorAngle;
			double section = a / sectorAngle;
			int startIndex = (((int) (section * bases.size())) + bases.size() - 1) % bases.size();
			Color c1 = bases.get(startIndex), c2 = bases.get((startIndex + 1) % bases.size());
			double inter = (section % (1.0 / bases.size())) * bases.size();
			return c1.interpolate(c2, inter);
			
		});
	}
	private void restart()
	{
		startTime = System.nanoTime();
		renderer.getGraphicsContext2D().clearRect(0, 0, width, height);
		paused = true;
		simulation = new BallSimulator(width * 4, height * 4);
		colors = new ArrayList<>(Arrays.asList(Color.RED, Color.ORANGERED, Color.ORANGE, Color.YELLOW, Color.YELLOWGREEN, Color.GREEN, Color.TURQUOISE, Color.BLUE, Color.BLUEVIOLET, Color.INDIGO, Color.VIOLET));
		//colors 
		
		List<Color> temp = new ArrayList<>(colors);
		Collections.reverse(temp);
		colors.addAll(temp);
		for(int i = 0; i < balls; i++)
		{
			double angle = i * 2 * Math.PI / balls;
			Vector v = Vector.fromPolar(functions.get(mode).applyAsDouble(angle) * speed, angle);
			Color c = /*olors.get(i % colors.size());*/ colorProfile.apply(angle);//new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
			simulation.addBall(new Ball(simulation.dimensions.scale(.5), v, 20, c));
		}
		paused = false;
	}
	private volatile boolean paused = false;
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
				//if(frames % 120 == 0 && snapshots.size() < 16)
				{
					//snapshots.add(snapshot());
				}
				frames++;
				//System.out.println((frames) / ((t - time) / 1_000_000_000.0));
			}
			
		}.start();
	}

}
