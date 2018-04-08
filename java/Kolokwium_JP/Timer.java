package scondProjectKolokium;

import java.awt.Window;

public class Timer extends Thread 
{
	public int time;
	private boolean off;
	WinApp parent;
	
	@Override
	public void run() {
		while(!off)
		{
			super.run();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			time++;
			parent.observerNotify(time);
			System.out.println(time);
		}
	}
	
	Timer(WinApp parent)
	{
		time = 0;
		off = false;
		this.parent = parent;
	}
	
	public synchronized void reset()
	{
		time = 0;
		
	}
	
	void takeOff()
	{
		off = true;
	}
}
