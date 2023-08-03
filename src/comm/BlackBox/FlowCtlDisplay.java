package comm.BlackBox;


import java.awt.Panel;
import java.awt.Label;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.GridLayout;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

public class FlowCtlDisplay extends Panel implements ItemListener
{
	SerialPort	port = null;
    	Label 		RTSLabel,
			XONLabel,
			RCVLabel,
			XMTLabel;
    	Checkbox	RCVRTS,
			XMTRTS,
			RCVXON,
			XMTXON;
	private	Color	onColor,
			offColor;

	public FlowCtlDisplay(SerialPort	port)
	{
		super();

		this.setPort(port);

		this.setLayout(new GridLayout(0, 3));

		this.add(new Label(""));

		RTSLabel = new Label("RTS/CTS");
		this.add(RTSLabel);

		XONLabel = new Label("XON/XOFF");
		this.add(XONLabel);

		RCVLabel = new Label("RCV");
		this.add(RCVLabel);

		RCVRTS = new Checkbox();
		RCVRTS.addItemListener(this);
		this.add(RCVRTS);

		RCVXON = new Checkbox();
		RCVXON.addItemListener(this);
		this.add(RCVXON);

		XMTLabel = new Label("XMT");
		this.add(XMTLabel);

		XMTRTS = new Checkbox();
		XMTRTS.addItemListener(this);
		this.add(XMTRTS);

		XMTXON = new Checkbox();
		XMTXON.addItemListener(this);
		this.add(XMTXON);

		onColor = Color.green;
		offColor = Color.black;
	}

	public void setPort(SerialPort	port)
	{
		this.port = port;
	}

	public void showValues()
	{
		if (this.port != null)
		{
			/*
			 *  Get the flow control mode
			 */
	
			int	mode = port.getFlowControlMode();
	
			if ((mode & SerialPort.FLOWCONTROL_RTSCTS_IN) > 0)
			{
				RCVRTS.setState(true);
				RCVRTS.setForeground(onColor);
			}
	
			else
			{
				RCVRTS.setState(false);
				RCVRTS.setForeground(offColor);
			}
	
			if ((mode & SerialPort.FLOWCONTROL_XONXOFF_IN) > 0)
			{
				RCVXON.setState(true);
				RCVXON.setForeground(onColor);
			}
	
			else
			{
				RCVXON.setState(false);
				RCVXON.setForeground(offColor);
			}
	
			if ((mode & SerialPort.FLOWCONTROL_RTSCTS_OUT) > 0)
			{
				XMTRTS.setState(true);
				XMTRTS.setForeground(onColor);
			}
	
			else
			{
				XMTRTS.setState(false);
				XMTRTS.setForeground(offColor);
			}
	
			if ((mode & SerialPort.FLOWCONTROL_XONXOFF_OUT) > 0)
			{
				XMTXON.setState(true);
				XMTXON.setForeground(onColor);
			}
	
			else
			{
				XMTXON.setState(false);
				XMTXON.setForeground(offColor);
			}
		}

		else
		{
			this.clearValues();
		}
	}

	public void itemStateChanged(ItemEvent	ev)
	{
		String 	sel = (String) ev.getItem();
		int	value = SerialPort.FLOWCONTROL_NONE;

		if (this.port != null)
		{
			if (this.RCVRTS.getState())
			{
				value |= SerialPort.FLOWCONTROL_RTSCTS_IN;
			}
	
			if (this.RCVXON.getState())
			{
				value |= SerialPort.FLOWCONTROL_XONXOFF_IN;
			}
	
			if (this.XMTRTS.getState())
			{
				value |= SerialPort.FLOWCONTROL_RTSCTS_OUT;
			}
	
			if (this.XMTXON.getState())
			{
				value |= SerialPort.FLOWCONTROL_XONXOFF_OUT;
			}
	
			/*
			 *  Set the flow control mode
			 */
			try {
			    this.port.setFlowControlMode(value);
			} catch (UnsupportedCommOperationException ucoe) {
			    ucoe.printStackTrace();
			}
			
		}

		this.showValues();
	}

	public void clearValues()
	{
		RCVRTS.setState(false);
		RCVRTS.setForeground(offColor);

		RCVXON.setState(false);
		RCVXON.setForeground(offColor);

		XMTRTS.setState(false);
		XMTRTS.setForeground(offColor);

		XMTXON.setState(false);
		XMTXON.setForeground(offColor);
	}
}
