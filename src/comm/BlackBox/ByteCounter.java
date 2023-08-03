package comm.BlackBox;


import java.awt.Panel;
import java.awt.Label;
import java.awt.TextField;
import java.awt.BorderLayout;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class ByteCounter extends Panel implements MouseListener
{
	private long		value,
				defaultValue;
	private Label		countLabel;
	private TextField	counter;

	public ByteCounter(String	text,
			   int		size,
			   int		defaultValue)
	{
		super();

		this.setLayout(new BorderLayout());

		this.countLabel = new Label(text);
		this.countLabel.addMouseListener(this);
		this.add("West", this.countLabel);

		this.counter = new TextField(new Integer(defaultValue).toString(), 
					     size);
		this.add("East", this.counter);

		this.value = defaultValue;
		this.defaultValue = defaultValue;
	}

	public ByteCounter(String	text,
			   int		size)
	{
		this(text, size, 0);
	}

	public long getValue()
	{
		return this.value;
	}

	public void setValue(long	val)
	{
		this.value = val;

		this.counter.setText(new Long(this.value).toString());
	}

	public void setDefaultValue(long	val)
	{
		this.defaultValue = val;
	}

	public void incrementValue(long	val)
	{
		this.value += val;

		this.counter.setText(new Long(this.value).toString());
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
