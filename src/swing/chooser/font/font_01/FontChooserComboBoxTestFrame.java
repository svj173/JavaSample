package com.xenoage.util.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class FontChooserComboBoxTestFrame
	extends JFrame
{
	
	FontChooserComboBox fccb;
	JLabel lblResult;
	JTextField txtPreview;
	JSlider sliderPreview;
	
	
	public FontChooserComboBoxTestFrame()
	{
		super("FontChooserComboBox Demo");
		
		setLayout(new BorderLayout());
		//setPreferredSize(new Dimension(400, 300));
		//setSize(getPreferredSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JToolBar toolbar = new JToolBar();
		add(toolbar, BorderLayout.NORTH);
		
		//font chooser
		fccb = new FontChooserComboBox();
		fccb.setPreviewFontSize((int) (new JLabel().getFont().getSize2D() * 1.5));
		fccb.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				String fontName = fccb.getSelectedFontName();
				lblResult.setText(fontName != null ? fontName : "");
			}
		});
		toolbar.add(fccb);
		
		//main panel
		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		
		//content of main panel
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(0, 2, 16, 16));
		mainPanel.add(contentPanel);
		
		//show the selected font
		contentPanel.add(new JLabel("Selected font: "));
		lblResult = new JLabel();
		contentPanel.add(lblResult);
		
		//edit preview text
		contentPanel.add(new JLabel("Show fonts with: "));
		txtPreview = new JTextField(fccb.getPreviewString());
		txtPreview.addKeyListener(new KeyAdapter()
		{
			@Override public void keyReleased(KeyEvent e)
			{
				fccb.setPreviewString(txtPreview.getText());
				pack();
			}
		});
		contentPanel.add(txtPreview);
		
		//number of recent fonts
		contentPanel.add(new JLabel("Show recent fonts: "));
		sliderPreview = new JSlider(JSlider.HORIZONTAL);
		sliderPreview.setMinimum(0);
		sliderPreview.setMaximum(5);
		sliderPreview.setValue(fccb.getRecentFontsCount());
		sliderPreview.setMinorTickSpacing(1);
		sliderPreview.setSnapToTicks(true);
		sliderPreview.setPaintLabels(true);
		sliderPreview.setPaintTicks(true);
		//sliderPreview.setPreferredSize(new Dimension(160, 50));
		Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
    table.put(0, new JLabel("none"));
    table.put(1, new JLabel("1"));
    table.put(2, new JLabel("2"));
    table.put(3, new JLabel("3"));
    table.put(4, new JLabel("4"));
    table.put(5, new JLabel("5"));
		sliderPreview.setLabelTable(table);
		sliderPreview.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				fccb.setRecentFontsCount(sliderPreview.getValue());
			}
		});
		contentPanel.add(sliderPreview);
		
		pack();
	}
	
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				try {
					//UIManager.setLookAndFeel(new NimbusLookAndFeel());
					//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		      //UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
		      //UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
		      //UIManager.setLookAndFeel(new SubstanceOfficeSilver2007LookAndFeel());
		    } catch (Exception e) {
		    }
				
		    FontChooserComboBoxTestFrame frame = new FontChooserComboBoxTestFrame();
		    frame.setVisible(true);
				//mainFrame.setExtendedState(MAXIMIZED_BOTH);
			}
		});
	}

}
