import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;
import java.lang.Runnable;
import java.lang.Thread;

import javax.swing.JFrame;
import javax.imageio.*;
import java.io.File;

public class Game extends JFrame implements Runnable
{
	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
	private BufferedImage testImage;
	private Sprite testSprite;
	private SpriteSheet sheet;
	private Rectangle testRectangle = new Rectangle(100, 100, 60, 60); // int x, int y, int w, int h
	int zaehler = 0;
	
	private Tiles tiles;
	private Map map;
	
	private int xZoom = 3;
	private int yZoom = 3;

	public static int dunkelblau = 0x3366ff;
	public static int himmelblau = 0x33bbff;
	public static int weiss = 0xffffff;
	public static int hellblau = 0x3399ff;
	public static int alpha = 0xff00dc; // zum Erzeugen von Transparenz, damit wird das Objekt gefuellt
	public static int alphaARGB = 0xffff00dc; // 
										// und diese Pixel mit der Farbe dann beim Kopieren in
										// das große Array weggelassen
	public Game() 
	{
		//Make our program shutdown when we exit out.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set the position and size of our frame.
		setBounds(0,0, 1000, 600);

		//Put our frame in the center of the screen.
		setLocationRelativeTo(null);

		//Add our graphics compoent
		add(canvas);

		//Make our frame visible.
		setVisible(true);

		//Create our object for buffer strategy.
		canvas.createBufferStrategy(3);

		renderer = new RenderHandler(getWidth(), getHeight());
		
		BufferedImage sheetImage = loadImage("Tiles1.png");
		sheet = new SpriteSheet(sheetImage);
		sheet.loadSprites(16, 16);
//		testSprite = sheet.getSprite(8, 1);
		
		//Load Tiles
		tiles = new Tiles(new File("Tiles.txt"), sheet);
		
		//Load Map
		map = new Map(new File("Map.txt"), tiles);
		
//		testImage = loadImage("GrassTile.png");
		testRectangle.generateGraphics(1, weiss);

	}

	public void update() 
	{
		
	}
	
	private BufferedImage loadImage(String path)
	{
		try 
		{
			BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
			return formattedImage;
		} catch (IOException e) {
			System.out.println("Bild kann nicht geladen werden");
			return null;
		}
	}

	public void render() 
	{
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);
		
		map.render(renderer, xZoom, yZoom);
		
//		renderer.renderBackground();
//		renderer.renderSprite(testSprite, 500, 100, 5, 5);
		
		renderer.render(graphics);

		graphics.dispose();
		bufferStrategy.show();
		renderer.clear();
	}

	public void run() 
	{
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		int i = 0;
		int x = 0;
		
		long lastTime = System.nanoTime(); //long 2^63
		double nanoSecondConversion = 1000000000.0 / 60; //Nanosek. pro Frame, 60 frames per second
		double changeInSeconds = 0;

		while(true) 
		{
			long now = System.nanoTime();

			changeInSeconds += (now - lastTime) / nanoSecondConversion; // Anzahl Frames
			while(changeInSeconds >= 1) 
			{ // wenn mehr als 1 Frame vergangen
				update(); // pro Frame ein update
				changeInSeconds--;
			}

			render(); // wird so oft der Computer es kann ausgeführt
			lastTime = now;
		}
	}

	public static void main(String[] args) 
	{
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}

}