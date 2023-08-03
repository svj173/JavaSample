package comm.BlackBox;


import java.awt.Choice;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

class DataBits extends Choice implements ItemListener
{
	private SerialPortDisplay	owner;

	public DataBits(SerialPortDisplay	owner)
	{
		super();

		this.add("Unknown");
		this.add("5");
		this.add("6");
		this.add("7");
		this.add("8");
		this.addItemListener(this);

		this.owner = owner;
	}

	protected void showValue()
	{
		if (this.owner.open)
		{
			/*
			 *  Get the number of data bits
			 */
	
			switch (this.owner.port.getDataBits())
			{
			case SerialPort.DATABITS_5:
	
				this.select("5");
				this.owner.numDataBits += 5;
				break;
	
			case SerialPort.DATABITS_6:
	
				this.select("6");
				this.owner.numDataBits += 6;
				break;
	
			case SerialPort.DATABITS_7:
	
				this.select("7");
				this.owner.numDataBits += 7;
				break;
	
			case SerialPort.DATABITS_8:
	
				this.select("8");
				this.owner.numDataBits += 8;
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

		if (sel.equals("5"))
		{
			value = SerialPort.DATABITS_5;
		}

		else if (sel.equals("6"))
		{
			value = SerialPort.DATABITS_6;
		}

		else if (sel.equals("7"))
		{
			value = SerialPort.DATABITS_7;
		}

		else if (sel.equals("8"))
		{
			value = SerialPort.DATABITS_8;
		}

		else
		{
			this.showValue();
		}

		port = this.owner.port;

		if ((value > 0) && (port != null))
		{
			/*
			 *  Set the number of data bits.
			 *
			 *  Note: we must set all of the parameters, not just 
			 *  the number of data bits, hence the use of get*
			 */

			try
			{
				port.setSerialPortParams(port.getBaudRate(),
							 value, 
							 port.getStopBits(), 
							 port.getParity());
			}
		
			catch (UnsupportedCommOperationException e)
			{
				System.out.println("Cannot set data bit size to "
						   + sel + " for port " 
						   + port.getName());
			}
		}

		this.owner.showValues();
	}
}

