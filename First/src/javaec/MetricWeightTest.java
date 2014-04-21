package javaec;

import static org.junit.Assert.*;

import org.junit.Test;

public class MetricWeightTest
{

	@Test
	public void test()
	{
		MetricWeight w1 = new MetricWeight(5, 257, 692);
		System.out.println(w1.toString());
		assertEquals(w1.toString(), "5t 257kg 692g");
		
	}
	
	@Test
	public void testShifting()
	{
		MetricWeight m1 = new MetricWeight(5, 257, 300);
		m1.add(m1);
		assertEquals(m1.toString(), "10t 514kg 600g");
		m1.subtract(m1);
		assertEquals(m1.toString(), "0t 0kg 0g");
	}
	
	@Test
	public void testScaling()
	{
		MetricWeight m1 = new MetricWeight(10, 37, 5);
		m1.multiply(2);
		assertEquals(m1.toString(), "20t 74kg 10g");
		m1.multiply(0);
		assertEquals(m1.toString(), "0t 0kg 0g");
		m1 = new MetricWeight(10, 36, 4);
		m1.divide(2);
		assertEquals(m1.toString(), "5t 18kg 2g");	
	}

}
