
public class Rectangle {
	
	public int x, y, w, h;
	private int[] pixels;
	
	Rectangle(int x, int y, int w, int h) 
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	Rectangle()
	{
		this(0, 0, 0, 0);
	}
	
	public void generateGraphics(int color) // zeichnet ausgefülltes Rechteck
	{
		pixels = new int[w*h];
		for (int y = 0; y < h; y++)
			for (int x = 0; x < w; x++)
				pixels[x + y * w] = color;
	}
	
	public void generateGraphics(int borderWidth, int color) // zeichnet Rahmen eines Rechtecks
	{
		pixels = new int[w*h];
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = Game.alpha; //  fuellen mit alpha Farbe, später dann dort transparent
		for (int y = 0; y < borderWidth; y++) // obere Kante
			for (int x = 0; x < w; x++)
				pixels[x + y * w] = color;
		for (int y = h - borderWidth; y < h; y++) // untere Kante
			for (int x = 0; x < w; x++)
				pixels[x + y * w] = color;
		for (int y = borderWidth; y < h - borderWidth; y++) // linke Kante
			for (int x = 0; x < borderWidth; x++)
				pixels[x + y * w] = color;
		for (int y = borderWidth; y < h - borderWidth; y++) // rechte Kante
			for (int x = w - borderWidth; x < w; x++)
				pixels[x + y * w] = color;
	}
	
	public int[] getPixels()
	{
		if (pixels != null)
			return pixels;
		else
		{
			System.out.println("Rechteck enthält kein Graphics");
			return null;
		}
		
					
	}

}
