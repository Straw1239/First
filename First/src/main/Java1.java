package main;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Java1 {
	
	public static void main(String[] args) {
		int[] foo = {5,9,1,3,2,6,4,8,5,6,1,187,155};
		int[] rPrimes = primeSieve(10000);
		System.out.println("Done Sieving");
		int temp;

		for (int i = 0; i < rPrimes.length/2; i++)
		{
		     temp = rPrimes[i];
		     rPrimes[i] = rPrimes[rPrimes.length-1 - i];
		     rPrimes[rPrimes.length-1 - i] = temp;
		
		}
		foo = rPrimes;
		
		foo = mergeSort(foo);
		System.out.println(Arrays.toString(foo));
		int[] arg = {5,4,3,2,1};
		System.out.println(Arrays.toString(iSort(arg)));
		System.out.println((-5)%6);
		
		/*JFrame frame = new JFrame("FrameDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(500, 500));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true); */
		
	}
	public static int[] iSort(int[] n)
	{
		int temp = 0;
		int j;
		for (int i = 1; i < n.length; i++)
		{
			temp = n[i];
			for (j = i-1; j >= 0; j--)
			{
				
				if (temp < n[j])
				{
					n[j+1] = n[j];
				}
				else
				{
					n[j+1] = temp;
					j = -1;
				}
				if (j == 0)
				{
					n[0] = temp;
				}
			}
			
		}
		return n;
	}
	
	public static int[] mergeSort (int[] n)
	{
		
		if (n.length <= 8)
		{
			return iSort(n);
		}
		int[] split1 = java.util.Arrays.copyOfRange(n,0,n.length / 2);
		int[] split2 = java.util.Arrays.copyOfRange(n, split1.length ,n.length);
		split1 = mergeSort(split1);
		split2 = mergeSort(split2);
		return merge(split1,split2);
	}
	
	public static int[] merge (int[] a, int[] b)
	{
		int[] output = new int[a.length + b.length];
		int ac = 0;
		int bc = 0;		
		for (int i = 0; i < output.length; i++)
		{
			if (!((ac == a.length) || (bc == b.length)))
			{
				if (a[ac] < b[bc])
				{
					output[i] = a[ac];
					ac++;
				}
				else
				{
					output[i] = b[bc];
					bc++;
				}
			}
			else
			{
				if (ac != a.length)
				{
					output[i] = a[ac];
					ac++;
				}
				else
				{
					output[i] = b[bc];
					bc++;
				}
			}
		}
		return output;
	}	
	
	public static long[] trialDivision(long n)
	{
		int[] primes = primeSieve((int) Math.ceil(Math.sqrt(n)));
		long[] factors = new long[ (int) Math.floor((Math.log(n))/(Math.log(2)))];
		int counter = 0;
		for (int c = 0; c < primes.length; c++)
		{
			while (n % primes[c] == 0)
			{
				n /= primes[c];
				factors[counter] = primes[c];
				counter++;
			}
		}
		factors[counter] = n;
		counter++;
		long[] output = new long[counter];
		for (int i = 0; i < counter; i++)
		{
			output[i] = factors[i];
		}
		return output;
	}
	
	public static int[] primeSieve(int n)
	{
		boolean[] primes;
		primes = new boolean[n-1];
		for (int i = 0; i < (Math.ceil(Math.sqrt(n))); i++)
		{
			if (!primes[i])
			{
				for (int a = ((i+2)*(i+2))-2; a < n-1; a += (i+2))
				{
					primes[a] = true;
				}
			}
			
		}
		int[] data = new int[n/2];
		int counter = 0;
		for (int i = 0; i < n-1; i++)
		{
			if (!primes[i])
			{
				data[counter] = i + 2;
				counter++;
			}
		}
		int[] output = new int[counter];
		for (int i = 0; i < counter ; i++ )
		{
			output[i] = data[i];
		}
		return output;
	}

}
