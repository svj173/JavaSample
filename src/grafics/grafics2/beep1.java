package grafics.grafics2;


import java.util.*;
import java.lang.*;
import java.awt.Toolkit;

public class beep1  {

    public static void main (String args[])    {

	int i;
	int idl = 10;
	String s1 = "" + (char)7 + (char)7 + (char)7 + (char)7;

	System.out.println ( s1 );
	pause1 (1000);
	System.out.println ( s1 );
	pause1 (100);
	System.out.println ( s1 );
	pause1 (1000);
	System.out.println ( s1 );
	pause1 (100);
	System.out.println ( s1 );
	pause1 (1000);
	System.out.println ( s1 );
	pause1 (100);

//	Graphics g;
	//java.awt.Toolkit.beep();
    }

    static void pause1 (int ip1) {
	int i1, i2 = 0;

	for (i1=0; i1<ip1; i1++) i2 = i2 + 1;
    }
}
