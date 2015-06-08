package numbers;

import java.security.SecureRandom;

import com.google.common.primitives.Longs;

public class MyRandom implements Random
{
	private static final long c = 7319936632422683419L;
	
	private long bottom;
	private long top;
	
	public MyRandom()
	{
		SecureRandom r = new SecureRandom();
		top = Longs.fromByteArray(r.generateSeed(Long.BYTES));
		bottom = Longs.fromByteArray(r.generateSeed(Long.BYTES));
	}
	
	public MyRandom(long bottom, long top)
	{
		if(bottom == 0 & top == 0) throw new IllegalArgumentException();
		this.top = top;
		this.bottom = bottom;
	}
	
	public MyRandom(long seed)
	{
		this(seed, (seed >>> 32) ^ c);
	}
	
	@Override
	public long nextULong()
	{
		long x;
		bottom += 1;
		x = top ^ bottom;
		top += (((~bottom & 1) | ((~bottom | 1) & (bottom - 1))) >>> 31); 
		x ^= x >>> 32;
		x *= c;
		x ^= x >>> 32;
		x *= c;
		return x;
	}

}
