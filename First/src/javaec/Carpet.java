package javaec;

public class Carpet
{
	public MetricLength length, width;
	public MetricWeight weight;
	
	private static int carpets = 1;
	
	public Carpet(MetricLength length, MetricLength width, MetricWeight weight)
	{
		this.length = length;
		this.width = width;
		this.weight = weight;
	}
	
	public static void main(String[] args)
	{
		Carpet carpet1 = new Carpet(new MetricLength(4, 0, 0), new MetricLength(2, 9, 0), new MetricWeight(0, 10, 450));
		Carpet carpet2 = new Carpet(new MetricLength(3, 57, 0), new MetricLength(5, 0, 0), new MetricWeight(0, 18, 743));
		printCarpet(carpet1, 200);
		System.out.println();
		printCarpet(carpet2, 60);
	}
	
	public static void printCarpet(Carpet c, int count)
	{
		System.out.println("Carpet " + carpets + ": Size = " + c.length + " by " + c.width);
		double SM = MetricLength.areaInM2(c.length, c.width);
		System.out.println("\t\t Weight per sq. m. = " + (c.weight.toKG() / SM));
		System.out.println("\t\t Area = " + SM + " sq. m.");
		System.out.println("\t\t Weight = " + c.weight);
		c.weight.multiply(count);
		System.out.println("\t\t Weight of " + count + " carpets = " + c.weight);
		carpets++;
	}

}
