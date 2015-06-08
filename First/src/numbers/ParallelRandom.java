package numbers;

import java.security.SecureRandom;

import com.google.common.primitives.Longs;

public class ParallelRandom implements Random
{
	private static final long c = 7319936632422683419L;
	
	private long bottom;
	private long top;
	private Lock lock = new Lock();
	
	public ParallelRandom()
	{
		SecureRandom r = new SecureRandom();
		top = Longs.fromByteArray(r.generateSeed(Long.BYTES));
		bottom = Longs.fromByteArray(r.generateSeed(Long.BYTES));
	}
	
	public ParallelRandom(long bottom, long top)
	{
		if(bottom == 0 & top == 0) throw new IllegalArgumentException();
		this.top = top;
		this.bottom = bottom;
	}
	
	public ParallelRandom(long seed)
	{
		this(seed, (seed >>> 32) ^ c);
	}
	
	@Override
	public long nextULong()
	{
		long x;
		bottom += c;
		x = top;
		top += c + (((~bottom & c) | ((~bottom | c) & (bottom - c))) >>> 31); // c + (bottom < c) (unsigned)
		x ^= x >>> 32;
		x *= c;
		x ^= x >>> 32;
		x *= c;
		return x + bottom;
	}

}
