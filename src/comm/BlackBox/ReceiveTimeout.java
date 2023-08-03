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

public class ReceiveTimeout extends Panel implements MouseListener, ActionListener
{
	private int		value,
				defaultValue;
	private Label		label;
	private TextField	data;
	private SerialPort	port = null;
	private boolean		inputBuffer;

	public ReceiveTimeout(int		size,
			      SerialPort	port)
	{
		super();

		this.setPort(port);

		this.inputBuffer = inputBuffer;

		this.setLayout(new BorderLayout());

		this.label = new Label("Timeout");
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
		 *  Get the current timeout.
		 */

		if ((port != null) && port.isReceiveTimeoutEnabled())
		{
			this.value = port.getReceiveTimeout();
		}

		else
		{
			this.value = 0;
		}

		return this.value;
	}

	public void showValue()
	{
		this.data.setText(new Integer(this.getValue()).toString());
	}

	public void setValue(int	val)
	{
		/*
		 *  Set the new timeout.
		 */

		if (port != null)
		{
			if (val > 0)
			{
			    try {
				port.enableReceiveTimeout(val);
			    } catch (UnsupportedCommOperationException ucoe) {
				ucoe.printStackTrace();
			    }

			}
	
			else
			{
				port.disableReceiveTimeout();
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
			Integer	newValue = new Integer(s);
	
			this.setValue(newValue.intValue());
		}

		catch (NumberFormatException ex)
		{
			System.out.println("Bad value = "
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
