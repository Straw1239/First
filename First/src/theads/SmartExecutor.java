package theads;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableScheduledFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;

public class SmartExecutor implements ListeningScheduledExecutorService
{
	public static void main(String[] args)
	{
		Thread t = new Thread();
		t.getState();
	}
	@Override
	public void shutdown()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public List<Runnable> shutdownNow()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isShutdown()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTerminated()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(Runnable command)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <T> ListenableFuture<T> submit(Callable<T> task)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListenableFuture<?> submit(Runnable task)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> ListenableFuture<T> submit(Runnable task, T result)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListenableScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V> ListenableScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListenableScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListenableScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
