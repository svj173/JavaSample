package db.hibernate.emsSyslog;


/**
 * <BR/> http://hibernate.org/
 * <BR/>
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.12.2013 15:07
 */
public class GetSyslogMain
{
    public static void main ( String[] args )
    {
        Message message;

        message = new Message("Hello World");
        System.out.println( message.getText() );

        /*
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        message = new Message("Hello World");
        session.save(message);
        tx.commit();
        session.close();


        Session newSession = getSessionFactory().openSession();
        Transaction newTransaction = newSession.beginTransaction();
        List messages =
              newSession.find ( "from Message as m order by m.text asc");
        System.out.println( messages.size() + " message(s) found:" );
        for ( Iterator iter = messages.iterator(); iter.hasNext(); )
        {
           message = (Message) iter.next();
           System.out.println( message.getText() );
        }
        newTransaction.commit();
        newSession.close();

        // Updating a message

        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        // 1 is the generated id of the first message
        message = (Message) session.load( Message.class, new Long(1) );
        message.setText("Greetings Earthling");
        Message nextMessage = new Message("Take me to your leader (please)");
        message.setNextMessage( nextMessage );
        tx.commit();
        session.close();
        */
    }

}
