package swing.dialog.visible;


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.01.2012 16:46:39
 */
public class PanelDialog extends JDialog
{
    /* Верхняя панель для вывода дополнительных сообщений об ошибках, которые не отображаются в ошибочном параметре.
    * Например, совокупные значения нескольких параметров (дополнительная валидация). */
    private JPanel    errorMsgPanel;
    private JTextArea errorMsgArea;
    private String    errorMsg;
    private JScrollPane errorScroll;
    private int ic = 0;

    public PanelDialog ( Frame owner, String title )
    {
        super ( owner, title, true );

        setLayout ( new BorderLayout() );
        setLocationRelativeTo ( null );  // располагать диалог в центре экрана
        setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
     
        errorMsgArea = new JTextArea(3,0);
        errorMsgArea.setForeground ( Color.RED );
        errorMsgArea.setEditable ( false );
        errorMsgArea.setText ( "Исходная позиция - проба пера" );

        errorMsgPanel = new JPanel ( new BorderLayout(5,5) );
        errorMsgPanel.setBorder ( BorderFactory.createEmptyBorder ( 5,10,5,10 ) );
        errorMsgPanel.add ( errorMsgArea, BorderLayout.CENTER );
        //errorMsgPanel.setVisible ( false );

        errorScroll = new JScrollPane ( errorMsgPanel );

        add ( errorScroll, BorderLayout.NORTH );

        // Панель с текстом
        JPanel panel, buttonPanel;
        panel = new JPanel();
        panel.add ( new JLabel("text") );
        add ( panel, BorderLayout.CENTER );

        buttonPanel = createButtonPanel();
        add ( buttonPanel, BorderLayout.SOUTH );


        // выключаем панель сообщений об ошибках
        unVisibleError();

        pack();
    }

    public void visibleError()
    {
        //errorMsgPanel.setVisible ( true );
        errorScroll.setVisible ( true );
        //errorScroll.revalidate();
        pack();
    }

    public void unVisibleError()
    {
        //errorMsgPanel.setVisible ( false );
        errorScroll.setVisible ( false );
        errorMsgArea.setText ( "" );
        pack();
    }

    public void setError ( String text )
    {
        //Logger.getInstance().debug ( "--- EltexValidateDialog.setError: Start. text = ", text );
        if ( text != null )
        {
            errorMsgArea.setText ( text );
            visibleError();
        }
    }

    private JPanel createButtonPanel ()
    {
        JPanel              bp, jPanel2;
        Icon                icon;
        JButton             jButtonOk, jButtonCancel, buttonExit;

        jPanel2 = new JPanel();
        jPanel2.setBorder( BorderFactory.createEtchedBorder ( EtchedBorder.RAISED ) );

        // делаем вторую панель - чтобы кнопки Принять/Отменить не растягивались на всю ширину диалога.
        bp   = new JPanel( new FlowLayout ( FlowLayout.CENTER,5,5) );

        jPanel2.setLayout ( new BorderLayout(5, 5) );

        jButtonOk       = new JButton();
        jButtonCancel   = new JButton();
        buttonExit      = new JButton();

        jButtonOk.setText("Показать");
        jButtonOk.addActionListener ( new ActionListener() {
            @Override
            public void actionPerformed ( ActionEvent evt) {
                doVisible();
            }
        });
        bp.add ( jButtonOk );

        jButtonCancel.setText("Скрыть");
        jButtonCancel.addActionListener ( new ActionListener() {
            @Override
            public void actionPerformed ( ActionEvent evt) {
                doClose();
            }
        });
        bp.add ( jButtonCancel );

        buttonExit.setText("Выход");
        buttonExit.addActionListener ( new ActionListener() {
            @Override
            public void actionPerformed ( ActionEvent evt) {
                doExit();
            }
        });
        bp.add ( buttonExit );


        jPanel2.add ( bp, BorderLayout.CENTER );

        return jPanel2;
    }

    public void doExit ()
    {
        setVisible ( false );
        dispose();
    }

    public void doVisible ()
    {
        setError ( "Показать " + ic );
        ic++;
    }

    public void doClose ()
    {
        unVisibleError();
    }

}
