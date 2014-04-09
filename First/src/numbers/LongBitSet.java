package numbers;

public class LongBitSet 
{
	private long bits;
	public LongBitSet()
	{
		bits = 0L;
		bits ^= bits;
	}
	
	public LongBitSet(long bits)
	{
		this.bits = bits;
	}
	
	public byte getNibble(int index)
	{
		assert(index >= 0 && index < 16);
		return  (byte)(((byte) (bits >> (index << 2))) & (byte)0B00001111);
	}
	
	public boolean getBit(int index)
	{
		assert(index >= 0 && index < 64);
		
	}
}
