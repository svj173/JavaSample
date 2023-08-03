package swing.dataBinding;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

/**
 * Test model that has PropertyChangeSupport. PCE only used for the spypanel in Main demo.
 */
public class Person
{
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private Integer id;
    private String username;
    private Boolean advanced;
    private Date created;
    //private DateTime updated;
    private String password;
    private Person parent;

    public Person(String username) {
        this.username = username;
    }

    public Person(String username, String password, Boolean advanced) {
        this.username = username;
        this.password = password;
        this.advanced = advanced;
    }

    public String toString() {
        return
            " id: " + id + "\n" +
            " username: " + username + "\n" +
            " advanced: " + advanced + "\n" +
            " created: " + created + "\n" +
            //" updated: " + updated + "\n" +
            " password: " + password + "\n" +
            " parent: " + parent.getUsername();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person that = (Person) o;

        return !(advanced != null ? !advanced.equals(that.advanced) : that.advanced != null) && !(parent != null ? !parent.equals(that.parent) : that.parent != null) && !(password != null ? !password.equals(that.password) : that.password != null) && !(username != null ? !username.equals(that.username) : that.username != null);
    }

    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (advanced != null ? advanced.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        return result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        changeSupport.firePropertyChange("username", this.username, this.username = username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdvanced() {
        return advanced;
    }

    public void setAdvanced(Boolean advanced) {
        changeSupport.firePropertyChange("advanced", this.advanced, this.advanced = advanced);
    }

    public Person getParent() {
        return parent;
    }

    public void setParent(Person parent) {
        changeSupport.firePropertyChange("parent", this.parent, this.parent = parent);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        changeSupport.firePropertyChange("id", this.id, this.id = id);
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        changeSupport.firePropertyChange("created", this.created, this.created = created);
    }

    /*
    public DateTime getUpdated() {
        return updated;
    }

    public void setUpdated(DateTime updated) {
        changeSupport.firePropertyChange("updated", this.updated, this.updated = updated);
    }
    */

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    public PropertyChangeSupport getChangeSupport() {
        return changeSupport;
    }

    public void setChangeSupport(PropertyChangeSupport changeSupport) {
        this.changeSupport = changeSupport;
    }

}
