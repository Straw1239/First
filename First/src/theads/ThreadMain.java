package theads;

public class ThreadMain {

	public static void main(String[] args) {
		Thread t = new Thread(new Run());
		t.run();
		
		

	}
	private static class Run implements Runnable
	{

		@Override
		public void run() 
		{
			
		}
		
	}
	
	private static class RunThread extends Thread
	{
		@Override
		public void run()
		{
			
		}
	}
}
