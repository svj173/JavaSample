package comm.BlackBox;


import java.awt.Choice;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

class Parity extends Choice implements ItemListener
{
	private SerialPortDisplay	owner;

	public Parity(SerialPortDisplay	owner)
	{
		super();

		this.add("Unknown");
		this.add("None");
		this.add("Odd");
		this.add("Even");
		this.addItemListener(this);

		this.owner = owner;
	}

	protected void showValue()
	{
		if (this.owner.open)
		{
			/*
			 *  Get the parity setting
			 */
	
			switch (this.owner.port.getParity())
			{
			case SerialPort.PARITY_NONE:
	
				this.select("None");
				break;
	
			case SerialPort.PARITY_ODD:
	
				this.select("Odd");
				break;
	
			case SerialPort.PARITY_EVEN:
	
				this.select("Even");
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

		if (sel.equals("None"))
		{
			value = SerialPort.PARITY_NONE;
		}

		else if (sel.equals("Odd"))
		{
			value = SerialPort.PARITY_ODD;
		}

		else if (sel.equals("Even"))
		{
			value = SerialPort.PARITY_EVEN;
		}

		else
		{
			this.showValue();
		}

		port = this.owner.port;

		if ((value > 0) && (port != null))
		{
			/*
			 *  Set the parity.
			 *
			 *  Note: we must set all of the parameters, not just 
			 *  the parity, hence the use of get*
			 */

			try
			{
				port.setSerialPortParams(port.getBaudRate(),
							 port.getDataBits(), 
							 port.getStopBits(), 
							 value);
			}
		
			catch (UnsupportedCommOperationException e)
			{
				System.out.println("Cannot set parity to "
						   + sel + " for port " 
						   + port.getName());
			}
		}

		this.owner.showValues();
	}
}

