package chapter9.interfaces;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HexagonTest 
{
	private static double increment;
	private static int numGons;
	private static Polygon[] polygons;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		increment = .1;
		numGons = 10;
		polygons = new Polygon[numGons];
	}

	@Before
	public void setUp() throws Exception 
	{
		for(int i = 0; i < numGons; i++)
		{
			polygons[i] = new Hexagon(increment * i);
		}
	}

	@Test
	public final void testArea() 
	{
		for(int i = 0; i < polygons.length; i++)
		{
			Polygon h = polygons[i];
			double d = i * increment;
			double p = 1.5 * Math.sqrt(3) * d * d;
			assertEquals(p, h.area(), 0);
		}
	}
	
	@Test
	public final void testPerimeter() 
	{
		for(int i = 0; i < polygons.length; i++)
		{
			Polygon h = polygons[i];
			double d = i * increment;
			double p = d * 6;
			assertEquals(p, h.perimeter(), 0);
		}
	}

}
