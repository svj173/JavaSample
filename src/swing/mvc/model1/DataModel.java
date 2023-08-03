package swing.mvc.model1;


import javax.swing.event.*;

public class DataModel {

  private String m_data;

  private EventListenerList m_listenerList = new EventListenerList();

  private ChangeEvent m_chnEvent = null;

  public void addData(String p_str){

    m_data = p_str;

    fireChange();

  }

  public String getData() {

    return m_data;

  }

  public void addChangeListener (ChangeListener l){

    m_listenerList.add(ChangeListener.class, l);

  }

  public void removeChangeListener(ChangeListener l) {

    m_listenerList.remove(ChangeListener.class, l);

  }

  // Notify all listeners that have registered interest for

  // notification on this event type.  The event instance

  // is lazily created using the parameters passed into

  // the fire method.

  protected void fireChange() {

    // Guaranteed to return a non-null array

    Object[] a_listeners = m_listenerList.getListenerList();

    // Process the listeners last to first, notifying

    // those that are interested in this event

    for (int i = a_listeners.length-2; i>=0; i-=2) {

      if (a_listeners[i]==ChangeListener.class) {

        // Lazily create the event:

        if (m_chnEvent == null)

        m_chnEvent = new ChangeEvent(this);

        ((ChangeListener)a_listeners[i+1]).stateChanged(m_chnEvent);

      }

    }

  }

}
