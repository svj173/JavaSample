package thread.worker;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 07.10.2015 13:43
 */
public class MyWork  implements Work
{
	private Thread owner;

	public MyWork(Thread thread)
    {
		this.owner = thread;
	}

	public void doWork()
    {
		System.out.println("This thread#" + owner.getId() + " is owner of Work implementation");
		System.out.println("This thread#" + Thread.currentThread().getId() + " invokes Work implementation");
	}

}