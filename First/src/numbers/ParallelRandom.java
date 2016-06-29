package numbers;

import java.security.SecureRandom;

import com.google.common.primitives.Longs;

public class ParallelRandom implements Random
{
	private static final long c = 7319936632422683419L;
	
	private long bottom;
	private long top;
	
	private static long s = 0;
	
	public static synchronized ParallelRandom nextRandom()
	{
		long seed = s;
		s++;
		return new ParallelRandom(s, 0);
	}
	
	
	public ParallelRandom()
	{
		SecureRandom r = new SecureRandom();
		top = Longs.fromByteArray(r.generateSeed(Long.BYTES));
		bottom = Longs.fromByteArray(r.generateSeed(Long.BYTES));
	}
	
	public ParallelRandom(long bottom, long top)
	{
		this.top = top;
		this.bottom = bottom;
	}
	
	public ParallelRandom(long seed)
	{
		this(seed, 0);
	}
	
	@Override
	public long nextULong()
	{
		long x = top ^ bottom;
		bottom += c;
		
		top += c + ((bottom < c) ? 1 : 0);
		x ^= x >>> 32;
		x *= c;
		x ^= x >>> 32;
		x *= c;
		return x + top;
	}

}
