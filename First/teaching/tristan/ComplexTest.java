package tristan;

public class ComplexTest
{

	public static void main(String[] args)
	{
		Complex q = new Complex (4 , 8);
		Complex k = new Complex (2 , -4);
		System.out.println(q + " , " + k);
		Complex a = q.add(k);
		System.out.println(a);
		Complex s = q.sub(k);
		System.out.println(s);
		System.out.println(q.conjugate());

	}

}
