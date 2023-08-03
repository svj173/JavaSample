package comm.BlackBox;


import java.awt.Choice;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

class BaudRate extends Choice implements ItemListener
{
	private SerialPortDisplay	owner;

	public BaudRate(SerialPortDisplay	owner)
	{
		super();

		this.add("Unknown");
		this.add("50");
		this.add("75");
		this.add("110");
		this.add("134");
		this.add("150");
		this.add("200");
		this.add("300");
		this.add("600");
		this.add("1200");
		this.add("1800");
		this.add("2400");
		this.add("4800");
		this.add("9600");
		this.add("19200");
		this.add("28800");  // This is a known bad value to test
		this.add("38400");
		this.add("57600");
		this.add("115200");
		this.addItemListener(this);

		this.owner = owner;
	}

	protected void showValue()
	{
		SerialPort	port = this.owner.port;

		if (this.owner.open)
		{
			/*
			 *  Get the baud rate
			 */
	
			this.select(new Integer(port.getBaudRate()).toString());
		}

		else
		{
			this.select("Unknown");
		}
	}

	public void itemStateChanged(ItemEvent	ev)
	{
		SerialPort	port;
		String 		sel = (String) ev.getItem();
		int		value = 0;

		if (sel.equals("Unknown"))
		{
			this.showValue();
			return;
		}

		else
		{
			value = (new Integer(sel)).intValue();
		}

		port = this.owner.port;

		if ((value > 0) && (port != null))
		{
			/*
			 *  Set the baud rate.
			 *
			 *  Note: we must set all of the parameters, not just 
			 *  the baud rate, hence the use of get*
			 */

			try
			{
				port.setSerialPortParams(value,
							 port.getDataBits(), 
							 port.getStopBits(), 
							 port.getParity());
			}
		
			catch (UnsupportedCommOperationException e)
			{
				System.out.println("Cannot set baud rate to "
						   + sel + " for port " 
						   + port.getName());
			}
		}

		this.owner.showValues();
	}
}

