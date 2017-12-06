package org.personal.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FastConsumeTask
{
	private BlockingQueue<Worker> queue = new LinkedBlockingQueue<Worker>();

	private String name = "FastConsumeTask ";
	
	public FastConsumeTask() 
	{

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void start()
	{
		start(1);
	}

	public void start(int threadPoolSize) 
	{
		for (int i = 0; i < threadPoolSize; i++) 
		{
			Thread thread = new Thread(new Runnable() 
			{
				@Override
				public void run() 
				{
					while (true) 
					{
						try 
						{
							Runnable worker = queue.take();
							worker.run();
							Thread.sleep(1);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				}
			});
			thread.setName(name + " - " + (i + 1));
			thread.start();
		}
	}

	public int hasCount() 
	{
		return queue.size();
	}

	public void produce(TaskHandler taskHandler) 
	{
		queue.offer(new Worker(taskHandler));
	}

	private static class Worker implements Runnable {

		private TaskHandler taskHandler;
		

		public Worker(TaskHandler taskHandler) 
		{
			super();
			this.taskHandler = taskHandler;
		}

		@Override
		public void run() 
		{
			try 
			{
				if(taskHandler != null)
				{
					taskHandler.onEvent();
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}

	}
}
