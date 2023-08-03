package comm.BlackBox;


import java.io.IOException;

import java.lang.Character;

import java.awt.Panel;
import java.awt.TextArea;
import java.awt.BorderLayout;

import javax.comm.SerialPort;

public class Receiver extends Panel implements Runnable
{
	private TextArea		text;
	private ReceiveOptions		options;
	private ByteStatistics		counter;
	private SerialPortDisplay	owner;
	private byte[]			buffer;
	private int			textCount;
	private int			delay;
	private boolean			silentReceive;

	public Receiver(SerialPortDisplay	owner,
			   int			rows,
			   int			cols)
	{
		super();

		this.owner = owner;

		this.buffer = new byte[2048];

		this.setLayout(new BorderLayout());

		this.options = new ReceiveOptions(owner.port);
		this.add("North", this.options);

		this.counter = new ByteStatistics("Bytes Received", 10, 
						  owner.port, true);
		this.add("South", this.counter);

		this.text = new TextArea(rows, cols);
		this.add("Center", text);

		this.textCount = 0;
		this.delay = 0;

		this.silentReceive = false;
	}

	public Receiver(SerialPortDisplay	owner,
			   int			rows,
			   int			cols,
			   int			delay)
	{
		this(owner, rows, cols);

		this.delay = delay;
	}

	public Receiver(SerialPortDisplay	owner,
			   int			rows,
			   int			cols,
			   int			delay,
			   boolean		silentReceive)
	{
		this(owner, rows, cols);

		this.silentReceive = silentReceive;
	}

	public void setPort(SerialPort	port)
	{
		this.counter.setPort(port);

		this.options.setPort(port);
	}

	public void showValues()
	{
		this.options.showValues();
		this.counter.showValues();
	}

	public void clearValues()
	{
		this.text.setText("");

		this.counter.clearValues();

		this.options.clearValues();
	}

	public void setBitsPerCharacter(int	val)
	{
		this.counter.setBitsPerCharacter(val);
	}

	public void run()
	{
		while (this.owner.open)
		{
			try
			{
				synchronized (this) {
					wait(10000);
				}
			}

			catch (InterruptedException e)
			{
			}

			if (this.owner.ctlSigs.DA)
			{
				this.readData();
			}
		}
	}

	private String displayText(byte[]	bytes,
				   int		byteCount)
	{
		String	str;
		int	i,
			idx;
		byte[]	nb;

		/*  Don't let the text area get too big! */

		if (this.textCount > 5000)
		{
			this.text.setText("");

			this.textCount = 0;
		}

		nb = new byte[byteCount * 4];

		for (i = 0, idx = 0; i < byteCount; i++)
		{
			/*  Wrap any control characters	*/

			if (Character.isISOControl((char) bytes[i])
			 && !Character.isWhitespace((char) bytes[i]))
			{
				nb[idx++] = (byte) '<';				
				nb[idx++] = (byte) '^';				
				nb[idx++] = (byte) (bytes[i] + 64);
				nb[idx++] = (byte) '>';				
			}

			else
			{
				nb[idx++] = bytes[i];
			}
		}

		if (!silentReceive)
		{
			str = new String(nb, 0, idx);

			this.text.append(str);
		}

		else
		{
			str = "";
		}

		this.counter.incrementValue((long) byteCount);

		this.textCount += byteCount;

		return(str);
	}

	public void readData()
	{
		String	str;
		int	bytes;
		long	endTime,
			now;

		try
		{
		    	while (this.owner.open
			 && (this.owner.in.available() > 0 ))
			{
	    	    		bytes = this.owner.in.read(this.buffer);

				if (bytes > 0)
				{
					if (bytes > this.buffer.length)
					{
						    System.out.println(owner.port.getName()
							 + ": Input buffer overflow!");
					}
	
					str =  
						this.displayText(this.buffer, bytes);

					if (this.owner.lineMonitor)
					{
						this.owner.transmitter.sendString(str);
					}
				}

				if (this.delay > 0)
				{
					endTime = System.currentTimeMillis()
						  + (long) delay;

					now = 0;

					while (now < endTime)
					{
						try
						{
							Thread.sleep(delay);
						}
			
						catch (InterruptedException e)
						{
						}

						now = 
						   System.currentTimeMillis();
					}
				}
		    	} 

			owner.ctlSigs.DA = false;

			owner.ctlSigs.showErrorValues();
		}

		catch (IOException ex)
		{
		    	System.out.println(owner.port.getName()
					   + ": Cannot read input stream");
		}
	} 
}
