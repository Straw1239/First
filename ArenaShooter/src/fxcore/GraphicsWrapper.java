package fxcore;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;

public class GraphicsWrapper extends Graphics2D
{
	GraphicsContext g;
	public GraphicsWrapper(GraphicsContext gc)
	{
		g = gc;
	}

	@Override
	public void draw(Shape s)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void drawRenderedImage(RenderedImage img, AffineTransform xform)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void drawRenderableImage(RenderableImage img, AffineTransform xform)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void drawString(String str, int x, int y)
	{
		g.fillText(str, x, y);
	}

	@Override
	public void drawString(String str, float x, float y)
	{
		g.fillText(str, x, y);
	}

	@Override
	public void drawString(AttributedCharacterIterator iterator, int x, int y)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void drawString(AttributedCharacterIterator iterator, float x,
			float y)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void drawGlyphVector(GlyphVector g, float x, float y)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void fill(Shape s)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hit(Rectangle rect, Shape s, boolean onStroke)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public GraphicsConfiguration getDeviceConfiguration()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setComposite(Composite comp)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPaint(Paint paint)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStroke(Stroke s)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setRenderingHint(Key hintKey, Object hintValue)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getRenderingHint(Key hintKey)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setRenderingHints(Map<?, ?> hints)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void addRenderingHints(Map<?, ?> hints)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public RenderingHints getRenderingHints()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void translate(int x, int y)
	{
		g.translate(x, y);
	}

	@Override
	public void translate(double tx, double ty)
	{
		throw new UnsupportedOperationException();

	}

	@Override
	public void rotate(double theta)
	{
		g.rotate(Math.toDegrees(theta));
	}

	@Override
	public void rotate(double theta, double x, double y)
	{
		translate(x, y);
		rotate(theta);
		translate(-x, -y);
	}

	@Override
	public void scale(double sx, double sy)
	{
		g.scale(sx, sy);
	}

	@Override
	public void shear(double shx, double shy)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void transform(AffineTransform Tx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTransform(AffineTransform Tx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public AffineTransform getTransform()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Paint getPaint()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Composite getComposite()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBackground(Color color)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Color getBackground()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Stroke getStroke()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void clip(Shape s)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public FontRenderContext getFontRenderContext()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Graphics create()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Color getColor()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setColor(Color c)
	{
		g.setFill(javafx.scene.paint.Color.rgb(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()));
	}

	@Override
	public void setPaintMode()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setXORMode(Color c1)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Font getFont()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFont(Font font)
	{
		g.setFont(new javafx.scene.text.Font(font.getFontName(), font.getSize()));
	}

	@Override
	public FontMetrics getFontMetrics(Font f)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Rectangle getClipBounds()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void clipRect(int x, int y, int width, int height)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setClip(int x, int y, int width, int height)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Shape getClip()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setClip(Shape clip)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void copyArea(int x, int y, int width, int height, int dx, int dy)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2)
	{
		g.strokeLine(x1, y1, x2, y2);
	}

	@Override
	public void fillRect(int x, int y, int width, int height)
	{
		g.fillRect(x, y, width, height);
	}

	@Override
	public void clearRect(int x, int y, int width, int height)
	{
		g.clearRect(x, y, width, height);
	}

	@Override
	public void drawRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void fillRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void drawOval(int x, int y, int width, int height)
	{
		g.strokeOval(x, y, width, height);
	}

	@Override
	public void fillOval(int x, int y, int width, int height)
	{
		g.fillOval(x, y, width, height);
	}

	@Override
	public void drawArc(int x, int y, int width, int height, int startAngle,
			int arcAngle)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void fillArc(int x, int y, int width, int height, int startAngle,
			int arcAngle)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean drawImage(Image img, int x, int y, ImageObserver observer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height,
			ImageObserver observer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, Color bgcolor,
			ImageObserver observer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height,
			Color bgcolor, ImageObserver observer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2, ImageObserver observer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2, Color bgcolor,
			ImageObserver observer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub

	}

}
