package mail.activation;


/**
 * Sample Code to Fetch E-Mail
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.07.2011 11:13:49
 */
import javax.mail.*;
import javax.mail.search.SubjectTerm;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;


public class FetchMailUsage {

    public static void main(String[] args) {

        // SUBSTITUTE YOUR ISP's POP3 SERVER HERE!!!
        String host = "pop.yourisp.net";
        // SUBSTITUTE YOUR USERNAME AND PASSWORD TO ACCESS E-MAIL HERE!!!
        String user = "your_username";
        String password = "your_password";
        // SUBSTITUTE YOUR SUBJECT SUBSTRING TO SEARCH HERE!!!
        String subjectSubstringToSearch = "Test E-Mail through Java";

        // Get a session.  Use a blank Properties object.
        Session session = Session.getInstance(new Properties());

        try {

            // Get a Store object
            Store store = session.getStore("pop3");
            store.connect(host, user, password);

            // Get "INBOX"
            Folder fldr = store.getFolder("INBOX");
            fldr.open(Folder.READ_WRITE);
            int count = fldr.getMessageCount();
            System.out.println(count  + " total messages");

            // Message numebers start at 1
            for(int i = 1; i <= count; i++) {
								// Get  a message by its sequence number
                Message m = fldr.getMessage(i);

                // Get some headers
                Date date = m.getSentDate();
                Address [] from = m.getFrom();
                String subj = m.getSubject();
                String mimeType = m.getContentType();
                System.out.println(date + "\t" + from[0] + "\t" +
                                    subj + "\t" + mimeType);
            }

            // Search for e-mails by some subject substring
            String pattern = subjectSubstringToSearch;
            SubjectTerm st = new SubjectTerm(pattern);
            // Get some message references
            Message [] found = fldr.search(st);

            System.out.println(found.length +
                                " messages matched Subject pattern \"" +
                                pattern + "\"");

            for (int i = 0; i < found.length; i++) {
                Message m = found[i];
                // Get some headers
                Date date = m.getSentDate();
                Address [] from = m.getFrom();
                String subj = m.getSubject();
                String mimeType = m.getContentType();
                System.out.println(date + "\t" + from[0] + "\t" +
                                    subj + "\t" + mimeType);

                Object o = m.getContent();
                if (o instanceof String) {
                    System.out.println("**This is a String Message**");
                    System.out.println((String)o);
                }
                else if (o instanceof Multipart) {
                    System.out.print("**This is a Multipart Message.  ");
                    Multipart mp = (Multipart)o;
                    int count3 = mp.getCount();
                    System.out.println("It has " + count3 +
                        " BodyParts in it**");
                    for (int j = 0; j < count3; j++) {
                        // Part are numbered starting at 0
                        BodyPart b = mp.getBodyPart(j);
                        String mimeType2 = b.getContentType();
                        System.out.println( "BodyPart " + (j + 1) +
                                            " is of MimeType " + mimeType);

                        Object o2 = b.getContent();
                        if (o2 instanceof String) {
                            System.out.println("**This is a String BodyPart**");
                            System.out.println((String)o2);
                        }
                        else if (o2 instanceof Multipart) {
                            System.out.print(
                                "**This BodyPart is a nested Multipart.  ");
                            Multipart mp2 = (Multipart)o2;
                            int count2 = mp2.getCount();
                            System.out.println("It has " + count2 +
                                "further BodyParts in it**");
                        }
                        else if (o2 instanceof InputStream) {
                            System.out.println(
                                "**This is an InputStream BodyPart**");
                        }
                    } //End of for
                }
                else if (o instanceof InputStream) {
                    System.out.println("**This is an InputStream message**");
                    InputStream is = (InputStream)o;
                    // Assumes character content (not binary images)
                    int c;
                    while ((c = is.read()) != -1) {
                        System.out.write(c);
                    }
                }

                // Uncomment to set "delete" flag on the message
                //m.setFlag(Flags.Flag.DELETED,true);

            } //End of for

            // "true" actually deletes flagged messages from folder
            fldr.close(true);
            store.close();

        }
        catch (MessagingException mex) {
            // Prints all nested (chained) exceptions as well
            mex.printStackTrace();
        }
        catch (IOException ioex) {
            ioex.printStackTrace();
        }

    }


} //End of class
