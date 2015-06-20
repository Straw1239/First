package main;

public class SLI
{
	
	public static void main(String[] args)
	{
		for(double d = 0; d <= 4.5; d += 0.001)
		{
			System.out.printf("%.12f\n", ELL(GLL(d)));
		}
		
	}
	
	
	static double GE(double mag)
	{
		double result = mag - Math.floor(mag);
		for(int i = 1; i < mag; i++)
		{
			result = Math.exp(result);
		}
		return result;
	}
	
	static double GL(double num)
	{
		int n = 0;
		while(num > 1)
		{
			num = Math.log(num);
			n++;
		}
		return n + num;
	}
	
	static long GLL(double num)
	{
		int sign = Double.compare(num, 0);
		if(sign == 0) return 0;
		sign = Integer.signum(sign);
		num *= sign;
		sign = (1 - sign) / 2;
		int recip = Integer.signum(Double.compare(1, num));
		recip = (recip + 1) / 2;
		if(recip == 1) num = 1 / num;
		long result = 0;
		while(num > 1)
		{
			result++;
			num = Math.log(num);
		}
		result <<= 58;
		result += Math.round(num * (1L << 58));
		result |= (0L | recip) << 62 | (0L | sign) << 63; 
		return result;
	}
	
	static double ELL(long num)
	{
		long temp = (num << 2) >>> 2;
		double result = GE(temp * 1.0 / (1L << 58));
		return Long.signum(num) * Math.pow(result, Long.signum(num << 1));
	}
}
