package javaec;

import static org.junit.Assert.*;

import org.junit.Test;

public class MetricLengthTest
{

	@Test
	public void test()
	{
		MetricLength m1 = new MetricLength(10, 37, 5);
		assertEquals(m1.toString(), "10m 37cm 5mm");
	}
	
	@Test
	public void testShifting()
	{
		MetricLength m1 = new MetricLength(10, 37, 5);
		m1.add(m1);
		assertEquals(m1.toString(), "20m 75cm 0mm");
		m1.subtract(m1);
		assertEquals(m1.toString(), "0m 0cm 0mm");
	}
	
	@Test
	public void testScaling()
	{
		MetricLength m1 = new MetricLength(10, 37, 5);
		m1.multiply(2);
		assertEquals(m1.toString(), "20m 75cm 0mm");
		m1.multiply(0);
		assertEquals(m1.toString(), "0m 0cm 0mm");
		m1 = new MetricLength(10, 36, 4);
		m1.divide(2);
		assertEquals(m1.toString(), "5m 18cm 2mm");	
	}

}
