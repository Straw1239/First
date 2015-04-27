package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import util.ParallelRandom;

public class BucketCalc
{
	private static double predictedValue = (Math.E * Math.E - 1) / (2 * Math.E * Math.E);
	private static ParallelRandom rand = new ParallelRandom();
	private static final int THREADS = 4;
	private static ExecutorService compute = Executors.newFixedThreadPool(THREADS);

	public static void main(String[] args)
	{
		for(int i = 1; i <= 100_000_000; i *= 10)
		{
			//System.out.println(expectedValue(i) / i - predictedValue);
		}
		int buckets = 100000000;
		System.out.println(predictedValue);
		System.out.println(expectedValue(buckets) / buckets);
		System.out.println(multiExperiment(25, 4, buckets) / buckets);
		System.out.println(simulate(100));
	}
	
	private static double multiExperiment(int tries, int multi, int buckets)
	{
		double sum = 0;
		List<Future<Integer>> results = new ArrayList<>();
		for(int i = 0; i < multi; i++)
		{
			Future<Integer> result = compute.submit(() -> 
			{
				int s = 0;
				for(int j = tries; j > 0; j--)
				{
					s += simulate(buckets);
				}
				return s;
			});
			results.add(result);
		}
		
		for(Future<Integer> f : results)
		{
			try
			{
				sum += f.get();
			}
			catch (InterruptedException | ExecutionException e)
			{
				throw new RuntimeException(e);
			}
		}
		return sum / (multi * tries);
	}
	
	private static double experiment(int tries, int buckets)
	{
		double sum = 0;
		for(int i = 0; i < tries; i++)
		{
			sum += simulate(buckets);
		}
		return sum / tries;
	}

	private static double expectedValue(int n)
	{
		double fnm1 = 1;
		double fnm2 = 0;
		for(int i = 2; i <= n; i++)
		{
			double temp = fnm1;
			fnm1 = ((i-1) * fnm1 + 2 * fnm2 + 1) / i;
			fnm2 = temp;
		}
		return fnm1;
	}
	
	private static int simulate(int numBuckets)
	{
		if(numBuckets <= 0) return 0;
		if(numBuckets <= 2) return 1;
		int coin = rand.nextInt(numBuckets);
		return simulate(coin - 1) + simulate(numBuckets - coin - 2) + 1;
	}
	
	
}
