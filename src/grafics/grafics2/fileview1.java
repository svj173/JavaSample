package grafics.grafics2;


// ������� �������� � FileSystemView - ����������� ���������� � ������ � �.�.
//  �� ����� ��� �����. ������-�� ������� ��������� ���� (4 ��) ����������� ��� �������.
//  getFiles - ������ ������ ������ �������� ����������, �� ���������� � �������������

import java.util.*;
import java.lang.*;
import java.io.*;
import javax.swing.filechooser.FileSystemView;

//abstract 
class fv extends FileSystemView {


  public int Sz (String dir1) {
//	FileSystemView fsv = new FileSystemView();
//	File[] fl = fsv.getFiles("g:\\1", true);
//	File[] fl = (new FileSystemView()).getFiles(new File(dir1), true);
//	System.out.println("\nKol-vo files = " + fl.length );
//	return fl.length;
	return 1;
  }

  public File createNewFolder (File fcc ) {
	return fcc;
  }

  public boolean isRoot (File fcc ) {
	return true;
  }

  public File[] getRoots () {
	File[] froot = new File[2];
	return froot;
  }

  public boolean isHiddenFile (File fcc ) {
	return true;
  }

}


public class fileview1  {

  public static void main (String args[]) {

//	final JFileChooser fch = new JFileChooser("g:\\1");

//	FileSystemView f1 = getFileSystemView(new fv());
//	File fff = new File("g:\\1");
//	fff.getFileSystemView();
//	fv fch = new fv();

	fv f1 = new fv();
//	f1.getFileSystemView();
	File[] fl = f1.getFiles(new File("g:\\1\\"), false );	// True - ������ ���� ������� ����� !!!
//	File[] fl = f1.getRoots();
	int i, isize = fl.length;
	System.out.println("\nKol-vo files = " + isize );
	System.out.println("Home dir = " + f1.getHomeDirectory() );

	for (i=0; i<isize; i++) {
	  System.out.println(" " + i + "\t" + fl[i].toString() );
	}
  }

}
