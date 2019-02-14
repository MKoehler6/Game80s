import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class RenderHandler 
{
	private BufferedImage view;
	private int[] pixels;
	public Rectangle camera;

	public RenderHandler(int width, int height) 
	{
		//Create a BufferedImage that will represent our view.
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		camera = new Rectangle(-16, -16, width, height);

		//Create an array for pixels
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
	}

//	render our array of pixels to the screen
	public void render(Graphics graphics)
	{
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}

	public void renderBackground()
	{
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = Game.himmelblau;
	}
//	render our image to our array of pixels
	public void renderImage(BufferedImage image, int xPos, int yPos, int xZoom, int yZoom)
	{
		int [] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		renderArray(imagePixels, image.getWidth(), image.getHeight(), xPos, yPos, xZoom, yZoom);
	}
	
	public void renderSprite(Sprite sprite, int xPosition, int yPosition, int xZoom, int yZoom) {
		renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), xPosition, yPosition, xZoom, yZoom);
	}
	
	public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom)
	{
		int[] rectanglePixels = rectangle.getPixels();
		if (rectanglePixels != null)
			renderArray(rectanglePixels, rectangle.w, rectangle.h, rectangle.x, rectangle.y, xZoom, yZoom);
	}
	
	private void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPos, int yPos, int xZoom, int yZoom)
	{
		for (int y = 0; y < renderHeight; y++)
			for (int x = 0; x < renderWidth; x++)
				for (int yZoomPos = 0; yZoomPos < yZoom; yZoomPos++)
					for (int xZoomPos = 0; xZoomPos < xZoom; xZoomPos++)
						setPixel(renderPixels[y * renderWidth + x], x * xZoom + xZoomPos + xPos, y * yZoom + yZoomPos + yPos);
	}
	
	private void setPixel(int pixel, int x, int y)
	{
		if (x>= camera.x && y>= camera.y && x <= camera.x + camera.w && y <= camera.x + camera.h)
		{
			int pixelIndex = (x - camera.x) + (y - camera.y) * view.getWidth();
			if (pixels.length > pixelIndex && pixel != Game.alphaARGB && pixel != Game.alpha) // Transparenz
				pixels[pixelIndex] = pixel;		
//			else // der überlappende Teil erscheint heller 
//				if (pixels.length > pixelIndex && pixel == Game.alpha && pixels[pixelIndex] != 0)
//					pixels[pixelIndex] = pixels[pixelIndex] + 0x333333;					
		}
	}
	
	public Rectangle getCamera() 
	{
		return camera;
	}

	public void clear()
	{
		for(int i = 0; i < pixels.length; i++)
			pixels[i] = 0;
	}
	
}