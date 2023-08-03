package comm.BlackBox;


import java.awt.Choice;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

class StopBits extends Choice implements ItemListener
{
	private SerialPortDisplay	owner;

	public StopBits(SerialPortDisplay	owner)
	{
		super();

		this.add("Unknown");
		this.add("1");
		this.add("1.5");
		this.add("2");
		this.addItemListener(this);

		this.owner = owner;
	}

	protected void showValue()
	{
		if (this.owner.open)
		{
			/*
			 *  Get the number of stop bits
			 */
	
			switch (this.owner.port.getStopBits())
			{
			case SerialPort.STOPBITS_1:
	
				this.select("1");
				this.owner.numDataBits += 1;
				break;
	
			case SerialPort.STOPBITS_2:
	
				this.select("2");
				this.owner.numDataBits += 2;
				break;
	
			case SerialPort.STOPBITS_1_5:
	
				this.select("1.5");
				this.owner.numDataBits += 2;
				break;
	
			default:
	
				this.select("Unknown");
				break;
			}
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

		if (sel.equals("1"))
		{
			value = SerialPort.STOPBITS_1;
		}

		else if (sel.equals("2"))
		{
			value = SerialPort.STOPBITS_2;
		}

		else if (sel.equals("1.5"))
		{
			value = SerialPort.STOPBITS_1_5;
		}

		else
		{
			this.showValue();
		}

		port = this.owner.port;

		if ((value > 0) && (port != null))
		{
			/*
			 *  Set the number of stop bits.
			 *
			 *  Note: we must set all of the parameters, not just 
			 *  the number of stop bits, hence the use of get*
			 */

			try
			{
				port.setSerialPortParams(port.getBaudRate(),
							 port.getDataBits(), 
							 value, 
							 port.getParity());
			}
		
			catch (UnsupportedCommOperationException e)
			{
				System.out.println("Cannot set stop bit size to "
						   + sel + " for port " 
						   + port.getName());
			}
		}

		this.owner.showValues();
	}
}

