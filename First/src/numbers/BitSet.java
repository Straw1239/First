package numbers;

public class BitSet 
{
	private long[] bytes;
	private int size;
	public BitSet(int capacity)
	{
		bytes = new long[(capacity/64) + 1];
		size = 0;
	}
}
