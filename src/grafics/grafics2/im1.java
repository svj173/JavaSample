package grafics.grafics2;


import java.io.*;
import java.util.*;
import java.lang.Integer;

//----------------------------------------------------------------------------
public class im1 {

//----------------------------------------------------------------------------

    public static void main (String args[]) throws Exception {

      Date dn1 = new Date();		// ����� ������� ���� (�� ��������)
      Date dn2 = new Date (1998, dn1.getMonth(), dn1.getDate(), 0, 0, 0);

	System.out.println ("ig = " + dn1.getYear() );
	System.out.println ("D1 = " + dn1.toString() );
	System.out.println ("D2 = " + dn2.toString() );
	System.out.println ("D1 gmt = " + dn1.toGMTString() );
	System.out.println ("D1 tm = " + dn1.getTimezoneOffset() );
/*
	float f1 = 13.4999f;
//	System.in.read (f1);
	int i1 = (int) f1;
	String s1 = i1 + "." + Math.round ( (f1 - i1) * 100 );
	System.out.println ("f1 = " + f1 + "         ii = " + s1);

//	byte b1[] = {71, 73, 70, 56,55, 97, 2,0,2,0, 128,0,0,0,0,0,
//		255, 255, 255, 44, 0,0,0,0,2,0,2,0, 64,2,2, 132, 81, 0, 59};
	byte b1[] = {71, 73, 70, 56,55, 97, 2,0,2,0, -1,0,0,0,0,0,
		-127, -127, -127, 44, 0,0,0,0,2,0,2,0, 64,2,2, -5, 81, 0, 59};
	byte b2 = 71;
//	System.out (b2);
	System.out.write (b1, 0, 34);
*/

    }

}