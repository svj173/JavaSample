package comm.BlackBox;


import java.awt.Panel;
import java.awt.Label;
import java.awt.TextField;
import java.awt.BorderLayout;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

public class ReceiveFraming extends Panel implements MouseListener, ActionListener
{
	private int		value,
				defaultValue;
	private Label		label;
	private TextField	data;
	private SerialPort	port = null;
	private boolean		inputBuffer;

	public ReceiveFraming(int		size,
			      SerialPort	port)
	{
		super();

		this.setPort(port);

		this.inputBuffer = inputBuffer;

		this.setLayout(new BorderLayout());

		this.label = new Label("Framing");
		this.label.addMouseListener(this);
		this.add("West", this.label);

		this.data = new TextField(new Integer(defaultValue).toString(), 
					     size);
		this.data.addActionListener(this);
		this.add("East", this.data);

		this.showValue();

		this.defaultValue = this.value;
	}

	public void setPort(SerialPort	port)
	{
		this.port = port;
	}

	public int getValue()
	{
		/*
		 *  Get the current framing.
		 */

		if ((port != null) && port.isReceiveFramingEnabled())
		{
			this.value = port.getReceiveFramingByte();
		}

		else
		{
			this.value = 0;
		}

		return this.value;
	}

	public void showValue()
	{
		this.data.setText("0x" + Integer.toString(this.getValue(), 16));
	}

	public void setValue(int	val)
	{
		/*
		 *  Set the new framing.
		 */

		if (port != null)
		{
			if (val > 0)
			{
			    try {
				port.enableReceiveFraming(val);
			    } catch (UnsupportedCommOperationException ucoe) {
				ucoe.printStackTrace();
			    }

			}
	
			else
			{
				port.disableReceiveFraming();
			}
		}

		this.showValue();
	}

	public void setDefaultValue(int	val)
	{
		this.defaultValue = val;
	}

	public void actionPerformed(ActionEvent e)
	{
		String	s = e.getActionCommand();

		try
		{
			this.setValue(Integer.parseInt(s, 16));
		}

		catch (NumberFormatException ex)
		{
			System.out.println("Bad value = 0x"
					  + e.getActionCommand());

			this.showValue();
		}
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
		this.setValue(this.defaultValue);
	}

	public void mouseReleased(MouseEvent e)
	{
	}
}
