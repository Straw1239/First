package functions;

public class FunctionTest {

	public static void main(String[] args) {
		Function f = new Function()
		{
			@Override
			public double f(double x)
			{
				return Math.exp(x);
			}
		};
		Function g = new Function()
		{
			@Override
			public double f(double x)
			{
				return Math.sqrt(x);
			}
		};
		Function h = new Function()
		{
			@Override
			public double f(double x)
			{
				return x*x;
			}
		};
		Function ln = new Function()
		{
			@Override
			public double f(double x)
			{
				return Math.log(x);
			}
		};
		Function awesome = Function.fog(ln, new Function(){
			@Override
			public double f(double x)
			{
				return x*x*x*x*x;
			}
		});
		Function normal = Function.divide(Function.fog(f, Function.negative(h)),Function.constant(g.f(Math.PI)));
		for(int i = 0; i < 10;i++)
		{
			awesome = Function.fog(awesome, awesome);
			awesome = Function.minus(awesome, ln);
		}
		for(int i = 0; i < 10;i++)
		{
			System.out.println(awesome.f(i));
		}
	}

}
