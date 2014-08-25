package functions;

public abstract class Function 
{
	public abstract double f(double x);
	private static final Function minus1 = new Function()
	{
		@Override
		public double f(double x)
		{
			return -1;
		}
	};
	private static final Function oneOverX = new Function()
	{
		@Override
		public double f(double x)
		{
			return 1/x;
		}
	};
	public static final Function constant(double x)
	{
		return new Constant(x);
	}
	public static final Function negative(Function f)
	{
		return new Multiplier(f,minus1);
	}	
	public static final Function oneOverf(Function f)
	{
		return new Fogger(oneOverX,f);
	}
	public static final Function add(Function f, Function g)
	{
		return new Adder(f,g);
	}
	public static final Function multiply(Function f, Function g)
	{
		return new Multiplier(f,g);
	}
	public static final Function fog(Function f, Function g)
	{
		return new Fogger(f,g);
	}
	public static final Function divide(Function f, Function g)
	{
		return multiply(f,oneOverf(g));
	}
	public static final Function minus(Function f, Function g)
	{
		return add(f,negative(g));
	}
	private static abstract class FunctionOfFunctions extends Function
	{
		protected Function f,g;
		private FunctionOfFunctions(Function f, Function g)
		{
			this.f = f;
			this.g = g;
		}
	}
	private static class Adder extends FunctionOfFunctions
	{
		public Adder(Function f, Function g)
		{
			super(f,g);
		}
		@Override
		public double f(double x)
		{
			return f.f(x)+g.f(x);
		}
	}
	private static class Multiplier extends FunctionOfFunctions
	{
		public Multiplier(Function f, Function g)
		{
			super(f,g);
		}
		@Override
		public double f(double x)
		{
			return f.f(x) * g.f(x);
		}
	}
	private static class Fogger extends FunctionOfFunctions
	{
		public Fogger(Function f, Function g)
		{
			super(f,g);
		}
		@Override
		public double f(double x)
		{
			return f.f(g.f(x));
		}
	}
	private static class Constant extends Function
	{
		public Constant(double x)
		{
			constant = x;
		}
		private double constant;
		@Override
		public double f(double x)
		{
			return constant;
		}
	}
}
