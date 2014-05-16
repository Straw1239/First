package main;

public class Reflecttest 
{

	public static void main(String[] args) throws ClassNotFoundException 
	{
		A a = new A();

	}
	
	static class A
	{
		public A() throws ClassNotFoundException
		{
			StackTraceElement[] stack = new RuntimeException().getStackTrace();
			for(StackTraceElement e : stack)
			{
				System.out.println(Class.forName(e.getClassName()));
			}
		}
	}

}
