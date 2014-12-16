package main;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.BitSet;
import java.util.concurrent.Executors;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;

import com.google.common.math.IntMath;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class Hyperspheres
{
	public static final ListeningScheduledExecutorService compute = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(4));
	
	public static void main(String[] args)
	{ 												//5.4515407626292366644144910618205968691910063996453928879526464063041378925954984369013557591167051393311397836804257694536112790725233993345490898695589084851528461888094231747705397150708271247711481E+136437630
		//System.out.println(factorial(8));			//5.4515407577350314219847939646266493830908001296224047490015428409409685961348812340117434089726140345987807735776860365141547316631264625239563259361250167715423051678475239340862338138542352621695561E+136437630
		System.out.println(calc(628318528, 10000)); //5.4515407688585306401595374928165844089952781461650098497412338055243577478483386308299023303218429252597408199183473412940714100952189566576345161000848737048032240159884176697862917988227940215957212E+136437630
	}
	static final BigDecimal DOUBLE_MAX = BigDecimal.valueOf(Double.MAX_VALUE);
	static final BigDecimal DOUBLE_MAX_SQUARED = DOUBLE_MAX.pow(2);
	static final BigDecimal SQRT_DOUBLE_MAX = BigDecimal.valueOf(Math.sqrt(Double.MAX_VALUE));
	static final MathContext MC = new MathContext(200, RoundingMode.HALF_UP);
	private static final BigDecimal FOUR = BigDecimal.valueOf(4);
	static final BigDecimal TWO = BigDecimal.valueOf(2);
	static final BigDecimal PI = calcPi();
	static final BigDecimal TWOPI = PI.multiply(BigDecimal.valueOf(2), MC);
	static final BigDecimal E = calcE();
	private static final double MAX_LOG = findMaxLog(E.pow(10, MC)) * 10;
	private static final int maxFactorial = findMaxFactorial();
	
	private static BigDecimal calc(int dimensions, int radius)
	{
		int factorial = dimensions / 2;
		final BigDecimal rBD = new BigDecimal(radius, MC);
		//final BigDecimal rBD2 = rBD.pow(2, MC);
		BigDecimal result = PI.pow(factorial, MC);
		int rPower = dimensions;
		BigDecimal minValue = ONE.divide(TEN.pow(MC.getPrecision() / 2, MC), MC);
		for(int i = factorial; i > 0; i--)
		{
			if(canFactorial(i))
			{
				result = result.divide(factorial(i), MC);
				break;
			}
			BigDecimal iBD = valueOf(i);
			result = result.divide(iBD, MC);
			
			if(result.compareTo(minValue) < 0) 
			for(int pow = rPower; pow > 1; pow /= 2)
			{
				try
				{
					result = result.multiply(rBD.pow(pow, MC), MC);
					//Pow worked
					rPower -= pow;
					break;
				}
				catch (ArithmeticException e)
				{
					continue;
				}
			}
			//result = result.multiply(rBD2, MC);
			if(i % 100_000 == 0) 
			{
				System.out.printf("%d : %s\n", rPower, result.toString());
			}
		}
		result = result.multiply(rBD.pow(rPower, MC), MC);
		return result;
	}
	
	private static int findMaxFactorial()
	{
		int lower = 0;
		for(int i = 2; canFactorial(i); i *= 2)
		{
			lower = i;
		}
		int upper = 2 * lower - 1;
		while(upper != lower)
		{
			int split = (upper + lower) / 2;
			if(canFactorial(split)) lower = split;
			else upper = split - 1;
		}
		return lower;
	}

	private static double findMaxLog(BigDecimal base)
	{
		int lower = 0;
		for(int n = 1;; n *= 2)
		{
			try
			{
				base.pow(n, MC);
			}
			catch (ArithmeticException e)
			{
				break;
			}
			lower = n;
		}
		//biggest number between e^lower and e^(2 * lower)!, binary search
		int upper = 2 * lower;
		BigDecimal biggestEPower = base;
		while(upper != lower)
		{
			int split = (upper + lower) / 2;
			if(lower == split)
			{
				upper = lower;
				break;
			}
			try
			{
				biggestEPower = base.pow(split, MC);
			}
			catch (ArithmeticException e)
			{
				upper = split;
				continue;
			}
			//else
			lower = split;
		}
		//lower and upper are max power of e we can get, now to maximize the multiplier
		double lowerM = 1;
		double upperM = 3; // Greater than e
		while(Double.compare(lowerM, upperM) != 0)
		{
			double split = (lowerM + upperM) / 2;
			try
			{
				biggestEPower.multiply(valueOf(split), MC);
			}
			catch(ArithmeticException e)
			{
				upperM = split;
				continue;
			}
			//else
			lowerM = split;
		}
		return lower + Math.log(lowerM);
	}

	private static BigDecimal calcE()
	{
		BigDecimal co = BigDecimal.ONE;
		BigDecimal old = BigDecimal.ZERO;
		BigDecimal result = BigDecimal.ONE;
		for(int i = 2; old.compareTo(result) != 0;i++)
		{
			BigDecimal newResult = result.add(co, MC);
			old = result;
			result = newResult;
			co = co.divide(BigDecimal.valueOf(i), MC);
		}
		return result;
	}
	
	private static BigDecimal calcPi()
	{
		MathContext MC = new MathContext(Hyperspheres.MC.getPrecision() + 10, Hyperspheres.MC.getRoundingMode());
		BigDecimal a = ONE;
		BigDecimal b = ONE.divide(sqrt(TWO), MC);
		BigDecimal t = new BigDecimal(0.25, MC);
		BigDecimal x = ONE;
		BigDecimal y;

		BigDecimal threshold = ONE.divide(BigDecimal.TEN.pow(MC.getPrecision() - 1));
		while (a.subtract(b, MC).abs().compareTo(threshold ) > 0) 
		{
			y = a;
			a = a.add(b).divide(TWO, MC);
			b = sqrt(b.multiply(y));
			t = t.subtract(x.multiply(y.subtract(a, MC).multiply(y.subtract(a, MC), MC), MC), MC);
			x = x.multiply(TWO, MC);
		}

		return a.add(b, MC).multiply(a.add(b, MC), MC).divide(t.multiply(FOUR, MC), MC);
	}
	

	private static BigDecimal fastCalc(int dimensions, int radius)
	{
		BigDecimal result = BigDecimal.valueOf(radius).pow(dimensions, MC);
		result = result.multiply(PI.pow(dimensions / 2, MC), MC);
		result = result.divide(approxFactorial(dimensions / 2), MC);
		return result;
	}
	private static double approxLogFactorial(int n)
	{
		return (n * Math.log(n) - n) + Math.log(Math.sqrt(Math.PI * 2 * n));
	}
	
	private static boolean canFactorial(int n)
	{
		double log = approxLogFactorial(n);
		return log < MAX_LOG;
	}
	private static BigDecimal approxFactorial(int n)
	{
		BigDecimal nBD = new BigDecimal(n, MC);
		BigDecimal result = nBD.divide(E, MC);
		result = result.pow(n, MC);
		result = result.multiply(ONE.add(ONE.divide(valueOf(n * 12), MC), MC).add(ONE.divide(valueOf(n * n * 288), MC), MC));
		result = result.multiply(sqrt(TWOPI.multiply(nBD, MC)), MC);
		return result;
	}
	//private static final int[] primes = primeSieve(268_609_190);
	private static BigDecimal factorial(int n)
	{
		int pow2 = 0;
		for(int i = 2; i <= n; i *= 2)
		{
			pow2 += n / i;
		}
		BigDecimal result = TWO.pow(pow2, MC);
		for(int i = n; i > 2; i /= 2)
		{
			for(int j = 3; j <= i; j += 2)
			{
				result = result.multiply(valueOf(j), MC);
			}
		}
		return result;
	}
	
	private static BigDecimal sqrt(BigDecimal m)
	{
		BigDecimal x0 = BigDecimal.ZERO;
		BigDecimal x1;
		if(m.compareTo(DOUBLE_MAX) < 0)
		{
			x1 = new BigDecimal(Math.sqrt(m.doubleValue()));
		}
		else if(m.compareTo(DOUBLE_MAX_SQUARED) < 0)
		{
			x1 = SQRT_DOUBLE_MAX;
		}
		else
		{
			x1 = DOUBLE_MAX;
		}
		while (x0.compareTo(x1) != 0) 
		{
			x0 = x1;
			x1 = m.divide(x0, MC);
			x1 = x1.add(x0, MC);
			x1 = x1.divide(TWO, MC);
		}
		return x1;
	}
	
	public static int[] primeSieve(int n)
	{
		BitSet composites;
		composites = new BitSet(n - 1);
		for (int i = 0; i < (Math.ceil(Math.sqrt(n))); i++)
		{
			if (!composites.get(i))
			{
				for (int a = ((i+2)*(i+2))-2; a < n-1; a += (i+2))
				{
					composites.set(a);
				}
			}
		}
		int primes = composites.length() - composites.cardinality();
		int[] data = new int[primes];
		int counter = 0;
		for (int i = 0; i < n-1; i++)
		{
			if (!composites.get(i))
			{
				data[counter] = i + 2;
				counter++;
			}
		}
		return data;
	}
}
