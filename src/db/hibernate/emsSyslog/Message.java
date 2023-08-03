package db.hibernate.emsSyslog;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.12.2013 15:06
 */
@Entity
@Table (name="Student")
public class Message
{
    private Long id;
       private String text;
       private Message nextMessage;
       private Message() {}
       public Message(String text) {
          this.text = text;
       }

        @Id
        @GeneratedValue (generator="increment")
        @GenericGenerator (name="increment", strategy = "increment")
        @Column (name="id")
       public Long getId() {
          return id;
       }
       private void setId(Long id) {
          this.id = id;
       }
        @Column(name="text")
       public String getText() {
          return text;
       }
       public void setText(String text) {
          this.text = text;
       }
       public Message getNextMessage() {
          return nextMessage;
       }
       public void setNextMessage(Message nextMessage) {
          this.nextMessage = nextMessage;
       }
}
