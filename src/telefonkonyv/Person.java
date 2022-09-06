package telefonkonyv;

import javafx.beans.property.SimpleStringProperty;

public class Person {
    
    private final SimpleStringProperty firstName; 
    private final SimpleStringProperty lastName; 
    private final SimpleStringProperty email; 
    private final SimpleStringProperty id; 
    
    public Person ()
    {
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.id = new SimpleStringProperty("");
    }
    
    public Person (String lastNamek, String firstNamek, String emailk)
    {
        this.firstName = new SimpleStringProperty(firstNamek);
        this.lastName = new SimpleStringProperty(lastNamek);
        this.email = new SimpleStringProperty(emailk);
        this.id = new SimpleStringProperty("");
    }
    
    public Person (Integer idk, String lastNamek, String firstNamek, String emailk)
    {
        this.firstName = new SimpleStringProperty(firstNamek);
        this.lastName = new SimpleStringProperty(lastNamek);
        this.email = new SimpleStringProperty(emailk);
        this.id = new SimpleStringProperty(String.valueOf(idk));
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getEmail() {
        return email.get();
    }
    
    public void setEmail(String emailk) {
        email.set(emailk);
    }
    
    public void setFirstName(String firstNamek) {
        firstName.set(firstNamek);
    }
    
    public void setLastName(String lastNamek) {
        lastName.set(lastNamek);
    }
    
    public String getId() {
        return id.get();
    }
    
    public void setId(String idk) {
        id.set(idk);
    }
}
