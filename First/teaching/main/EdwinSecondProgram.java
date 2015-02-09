package main;

public class EdwinSecondProgram
{

	public static void main(String[] args)
	{
		System.out.println(isPrime (777777777777773l));	

	}
	public static boolean isPrime(long n)
	{
		if (n % 2 == 0) return false;
		for (long i = 3; i * i <= n; i += 2)
		{
			if (n % i == 0) return false;
		}
		return true;
	}

}
