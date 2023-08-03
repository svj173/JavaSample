package comm.BlackBox;



import java.awt.Label;
import java.awt.Color;

public class ToggleLabel extends Label
{
	private  Color		onColor,
				offColor;
	private boolean		state;

	public ToggleLabel(String		text)
	{
		super(text);

		this.onColor = Color.green;
		this.offColor = Color.black;

		this.state = false;

		this.setForeground(this.offColor);
	}

	public boolean getState()
	{
		return this.state;
	}

	public void setState(boolean	value)
	{
		this.state = value;

		this.setForeground(value ? this.onColor : this.offColor);
	}
}
