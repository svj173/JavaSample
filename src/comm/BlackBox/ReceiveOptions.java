package comm.BlackBox;


import java.awt.Panel;
import java.awt.FlowLayout;

import javax.comm.SerialPort;

public class ReceiveOptions extends Panel
{
	private ReceiveTimeout		timeout;
	private ReceiveThreshold	threshold;
	private ReceiveFraming		framing;

	public ReceiveOptions(SerialPort	port)
	{
		super();

		this.setLayout(new FlowLayout());

		timeout = new ReceiveTimeout(6, port);
		this.add(timeout);

		threshold = new ReceiveThreshold(6, port);
		this.add(threshold);

		framing = new ReceiveFraming(6, port);
		this.add(framing);
	}

	public void setPort(SerialPort	port)
	{
		this.timeout.setPort(port);
		this.threshold.setPort(port);
		this.framing.setPort(port);
	}

	public void showValues()
	{
		this.timeout.showValue();
		this.threshold.showValue();
		this.framing.showValue();
	}

	public void clearValues()
	{
		this.timeout.setValue(0);
		this.threshold.setValue(0);
		this.framing.setValue(0);
	}
}
