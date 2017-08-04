package tools;

public class ThreadFactory {
	public static Thread startThread(Runnable runnable)
	{
		Thread thread = new Thread(runnable);
		thread.start();
		return thread;
	}
}
