package kevin;

public class K
{

	public static void main(String[] args)
	{
	
		int k = 5;
		int j = 10; 
		for (int i = 1; i < 10; i++)
		{
			System.out.println("k is " + k);
			System.out.println("j is " + j);
			k += j;
			j += j + i;
		}
		System.out.println(k);
		System.out.println(j);
		
	}
	

}
