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

public class BufferSize extends Panel implements MouseListener, ActionListener
{
	private int		value,
				defaultValue;
	private Label		label;
	private TextField	data;
	private SerialPort	port = null;
	private boolean		inputBuffer;

	public BufferSize(int		size,
			   SerialPort	port,
			   boolean	inputBuffer)
	{
		super();

		this.setPort(port);

		this.inputBuffer = inputBuffer;

		this.setLayout(new BorderLayout());

		this.label = new Label("Buffer Size");
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
		if (this.port != null)
		{
			/*
			 *  Get the buffer size.
			 */
	
			if (inputBuffer)
			{
				this.value = port.getInputBufferSize();
			}
	
			else
			{
				this.value = port.getOutputBufferSize();
			}
	
			return this.value;
		}

		else
		{
			return(0);
		}
	}

	public void showValue()
	{
		this.data.setText(new Integer(this.getValue()).toString());
	}

	public void setValue(int	val)
	{
		if (this.port != null)
		{
			/*
			 *  Set the new buffer size.
			 */
	
			if (inputBuffer)
			{
				port.setInputBufferSize(val);
			}
	
			else
			{
				port.setOutputBufferSize(val);
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
