package numbers;

public class LongBitSet 
{
	private static final byte mask = 0B00000001;
	
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
		if(index < 0 || index > 15) throw new IndexOutOfBoundsException(Integer.toString(index));
		return  (byte)(((byte) (bits >>> (index << 2))) & (byte)0B00001111);
	}
	
	public boolean getBit(int index)
	{
		if(index < 0 || index > 63) throw new IndexOutOfBoundsException(Integer.toString(index));
		byte value = (byte) (((byte)bits >>> index) & mask);
		return value == 1;
		
	}
	
	public static void main(String[] args)
	{
		long test = 0B00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000L;
		LongBitSet bits = new LongBitSet(test);
		System.out.println(bits.getBit(63));
	}
}
