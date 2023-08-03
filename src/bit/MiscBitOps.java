package bit;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.12.2012 16:41
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MiscBitOps extends JFrame {
   private JTextField input1, input2, bits1, bits2;
   private int val1, val2;

   public MiscBitOps()
   {
      super( "Bitwise operators" );

      JPanel inputPanel = new JPanel();
      inputPanel.setLayout( new GridLayout( 4, 2 ) );

      inputPanel.add( new JLabel( "Enter 2 ints" ) );
      inputPanel.add( new JLabel( "" ) );

      inputPanel.add( new JLabel( "Value 1" ) );
      input1 = new JTextField( 8 );
      inputPanel.add( input1 );

      inputPanel.add( new JLabel( "Value 2" ) );
      input2 = new JTextField( 8 );
      inputPanel.add( input2 );

      inputPanel.add( new JLabel( "Result" ) );
      final JTextField result = new JTextField( 8 );
      result.setEditable( false );
      inputPanel.add( result );

      JPanel bitsPanel = new JPanel();
      bitsPanel.setLayout( new GridLayout( 4, 1 ) );
      bitsPanel.add( new JLabel( "Bit representations" ) );

      bits1 = new JTextField( 33 );
      bits1.setEditable( false );
      bitsPanel.add( bits1 );

      bits2 = new JTextField( 33 );
      bits2.setEditable( false );
      bitsPanel.add( bits2 );

      final JTextField bits3 = new JTextField( 33 );
      bits3.setEditable( false );
      bitsPanel.add( bits3 );

      JPanel buttonPanel = new JPanel();
      JButton and = new JButton( "AND" );
      and.addActionListener(
         new ActionListener() {
            public void actionPerformed( ActionEvent e )
            {
               setFields();
               result.setText( Integer.toString( val1 & val2 ) );
               bits3.setText( getBits( val1 & val2 ) );
            }
         }
      );
      buttonPanel.add( and );

      JButton inclusiveOr = new JButton( "Inclusive OR" );
      inclusiveOr.addActionListener(
         new ActionListener() {
            public void actionPerformed( ActionEvent e )
            {
               setFields();
               result.setText( Integer.toString( val1 | val2 ) );
               bits3.setText( getBits( val1 | val2 ) );
            }
         }
      );
      buttonPanel.add( inclusiveOr );

      JButton exclusiveOr = new JButton( "Exclusive OR" );
      exclusiveOr.addActionListener(
         new ActionListener() {
            public void actionPerformed( ActionEvent e )
            {
               setFields();
               result.setText( Integer.toString( val1 ^ val2 ) );
               bits3.setText( getBits( val1 ^ val2 ) );
            }
         }
      );
      buttonPanel.add( exclusiveOr );

      JButton complement = new JButton( "Complement" );
      complement.addActionListener(
         new ActionListener() {
            public void actionPerformed( ActionEvent e )
            {
               input2.setText( "" );
               bits2.setText( "" );
               int val = Integer.parseInt( input1.getText() );
               result.setText( Integer.toString( ~val ) );
               bits1.setText( getBits( val ) );
               bits3.setText( getBits( ~val ) );
            }
         }
      );
      buttonPanel.add( complement );

      Container c = getContentPane();
      c.setLayout( new BorderLayout() );
      c.add( inputPanel, BorderLayout.WEST );
      c.add( bitsPanel, BorderLayout.EAST );
      c.add( buttonPanel, BorderLayout.SOUTH );

      setSize( 600, 150 );
      show();
   }

   private void setFields()
   {
      val1 = Integer.parseInt( input1.getText() );
      val2 = Integer.parseInt( input2.getText() );

      bits1.setText( getBits( val1 ) );
      bits2.setText( getBits( val2 ) );
   }

   private String getBits( int value )
   {
      int displayMask = 1 << 31;
      StringBuffer buf = new StringBuffer( 35 );

      for ( int c = 1; c <= 32; c++ ) {
         buf.append(
            ( value & displayMask ) == 0 ? '0' : '1' );
         value <<= 1;

         if ( c % 8 == 0 )
            buf.append( ' ' );
      }

      return buf.toString();
   }

   public static void main( String args[] )
   {
      MiscBitOps app = new MiscBitOps();
      app.addWindowListener(
         new WindowAdapter() {
            public void windowClosing( WindowEvent e )
            {
               System.exit( 0 );
            }
         }
      );
   }
}