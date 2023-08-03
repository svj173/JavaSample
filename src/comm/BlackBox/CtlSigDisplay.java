package comm.BlackBox;


import java.awt.Panel;
import java.awt.Label;
import java.awt.Color;
import java.awt.FlowLayout;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.comm.SerialPort;

public class CtlSigDisplay extends Panel
{
	SerialPort	port = null;
	ToggleLabel	RTSLabel,
			DTRLabel,
			OELabel,
			FELabel,
			PELabel,
			BILabel;
	Label		CTSLabel,
			DSRLabel,
			RILabel,
			CDLabel,
			DALabel,
			BELabel;
	boolean		DA,
			BE;
	private Color	onColor,
			offColor;

	public CtlSigDisplay(SerialPort	port)
	{
		super();

		this.setPort(port);

		this.setLayout(new FlowLayout());

		RTSLabel = new ToggleLabel("RTS");
		RTSLabel.addMouseListener(new ToggleMouseListener(this));
		this.add(RTSLabel);

		CTSLabel = new Label("CTS");
		this.add(CTSLabel);

		DTRLabel = new ToggleLabel("DTR");
		DTRLabel.addMouseListener(new ToggleMouseListener(this));
		this.add(DTRLabel);

		DSRLabel = new Label("DSR");
		this.add(DSRLabel);

		RILabel = new Label("RI");
		this.add(RILabel);

		CDLabel = new Label("CD");
		this.add(CDLabel);

		OELabel = new ToggleLabel("OE");
		OELabel.addMouseListener(new OneWayMouseListener(this));
		this.add(OELabel);

		FELabel = new ToggleLabel("FE");
		FELabel.addMouseListener(new OneWayMouseListener(this));
		this.add(FELabel);

		PELabel = new ToggleLabel("PE");
		PELabel.addMouseListener(new OneWayMouseListener(this));
		this.add(PELabel);

		BILabel = new ToggleLabel("BI");
		BILabel.addMouseListener(new OneWayMouseListener(this));
		this.add(BILabel);

		DALabel = new Label("DA");
		this.add(DALabel);
		DA = false;

		BELabel = new Label("BE");
		this.add(BELabel);
		BE = true;

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
			 *  Get the state of the control signals for this port
			 */
	
			RTSLabel.setState(port.isRTS());
			CTSLabel.setForeground(port.isCTS()
						 ? onColor : offColor);
	
			DTRLabel.setState(port.isDTR());
			DSRLabel.setForeground(port.isDSR()
						 ? onColor : offColor);
	
			RILabel.setForeground(port.isRI() ? onColor : offColor);
			CDLabel.setForeground(port.isCD() ? onColor : offColor);
		}
	}

	public void showErrorValues()
	{
		if (this.port != null)
		{
			/*
			 *  Get the state of the error conditions for this port
			 */
	
			OELabel.setForeground(OELabel.getState()
						 ? onColor : offColor);

			FELabel.setForeground(FELabel.getState()
						 ? onColor : offColor);

			PELabel.setForeground(PELabel.getState()
						 ? onColor : offColor);

			BILabel.setForeground(BILabel.getState()
						 ? onColor : offColor);

			DALabel.setForeground(DA ? onColor : offColor);

			BELabel.setForeground(BE ? onColor : offColor);
		}
	}

	public void clearValues()
	{
		RTSLabel.setState(false);
		CTSLabel.setForeground(offColor);

		DTRLabel.setState(false);
		DSRLabel.setForeground(offColor);

		RILabel.setForeground(offColor);
		CDLabel.setForeground(offColor);
	}

	public void clearErrorValues()
	{
		OELabel.setForeground(offColor);
		FELabel.setForeground(offColor);
		PELabel.setForeground(offColor);
		BILabel.setForeground(offColor);
		DALabel.setForeground(offColor);
		BELabel.setForeground(offColor);
	}
}

class ToggleMouseListener implements MouseListener
{
	CtlSigDisplay	owner;

	public ToggleMouseListener(CtlSigDisplay	owner)
	{
		super();

		this.owner = owner;
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
		ToggleLabel	label = (ToggleLabel) e.getComponent();

		if (this.owner.port != null)
		{
			/*
			 *  Toggle the state of the control signal
			 */
	
			label.setState(!label.getState());
	
			if (label == this.owner.RTSLabel)
			{
				this.owner.port.setRTS(label.getState());
				label.setState(this.owner.port.isRTS());
			}
	
			else if (label == this.owner.DTRLabel)
			{
				this.owner.port.setDTR(label.getState());
				label.setState(this.owner.port.isDTR());
			}
		}
	}

	public void mouseReleased(MouseEvent e)
	{
	}
}

class OneWayMouseListener implements MouseListener
{
	CtlSigDisplay	owner;

	public OneWayMouseListener(CtlSigDisplay	owner)
	{
		super();

		this.owner = owner;
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
		ToggleLabel	label = (ToggleLabel) e.getComponent();

		label.setState(false);
	}

	public void mouseReleased(MouseEvent e)
	{
	}
}
