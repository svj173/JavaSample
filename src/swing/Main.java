package swing;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.07.2010 11:19:53
 */

import org.apache.logging.log4j.LogManager;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

/**
* Вот пример, который имитирует простую загрузку. Для примера фоновые потоки просто ждут некоторое время,
 * а потом возвращают случайное число. Спят они тоже случайное время. Во время работы видно как значения
 * постепенно появляются в ячейках. Конечно это просто пример и его следует красивее оформить.
* @author cy6ergn0m
*/
public class Main {

   private static final Random rnd = new Random( ~System.currentTimeMillis() );

   private static final ExecutorService exec = Executors.newCachedThreadPool();

   private static class MyModel implements TableModel {

       private ArrayList<Future<Integer>> values = new ArrayList<Future<Integer>>();

       public MyModel() {
           for( int i = 0; i < 100; ++i )
               values.add( null );
       }

       public int getRowCount() {
           return 10;
       }

       public int getColumnCount() {
           return 10;
       }

       public String getColumnName( int columnIndex ) {
           return Integer.toString( columnIndex );
       }

       public Class<?> getColumnClass( int columnIndex ) {
           return String.class;
       }

       public boolean isCellEditable( int rowIndex, int columnIndex ) {
           return false;
       }

       public Object getValueAt( int rowIndex, int columnIndex ) {
           int idx = rowIndex * 10 + columnIndex;
           Future<Integer> f = values.get( idx );
           if( f == null ) {
               f = exec.submit( new LoaderTask( columnIndex, this ) );
               values.set( idx, f );
               return "";
           } else if( !f.isDone() || f.isCancelled() ) {
               return "";
           } else {
               try {
                   return f.get().toString();
               } catch( InterruptedException ex ) {
                   LogManager.getFormatterLogger ( Main.class.getName () ).error ( ex );
               } catch( ExecutionException ex ) {
                   LogManager.getFormatterLogger ( Main.class.getName () ).error(  ex );
               }
               return "";
           }

       }

       public void setValueAt( Object aValue, int rowIndex, int columnIndex ) {
       }

       private final ArrayList<TableModelListener> ll = new ArrayList<TableModelListener>();

       public void addTableModelListener( TableModelListener l ) {
           ll.add( l );
       }

       public void removeTableModelListener( TableModelListener l ) {
           ll.remove( l );
       }

       public void setCellValue( final int cellIndex, final int value ) {
           SwingUtilities.invokeLater( new Runnable() {

               public void run() {
                   Future<Integer> v = values.get( cellIndex );
                   if( v != null ) {
                       try {
                           v.get( 3, TimeUnit.SECONDS );
                           int row = cellIndex / 10;
                           int col = cellIndex % 10;
                           TableModelEvent e = new TableModelEvent( MyModel.this, row, row, col, col );
                           for( TableModelListener l : ll ) {
                               l.tableChanged( e );
                           }
                       } catch( InterruptedException ex ) {
                           LogManager.getFormatterLogger ( Main.class.getName () ).error ( ex );
                       } catch( ExecutionException ex ) {
                           LogManager.getFormatterLogger ( Main.class.getName () ).error ( ex );
                       } catch( TimeoutException ex ) {
                           LogManager.getFormatterLogger ( Main.class.getName () ).error ( ex );
                       }
                   }
               }

           } );
       }

   }

   private static class LoaderTask implements Callable<Integer> {

       private final int cellNumber;

       private final MyModel model;

       public LoaderTask( int cellNumber, MyModel model ) {
           this.cellNumber = cellNumber;
           this.model = model;
       }

       public Integer call() throws Exception {
           Thread.sleep( rnd.nextInt( 2000 ) );
           int v = rnd.nextInt( 20 );
           model.setCellValue( cellNumber, v );
           return v;
       }

   }

   /**
    * @param args the command line arguments
    */
   public static void main( String[] args ) {
       new NewJFrame( new MyModel() ).setVisible( true );
   }

}
