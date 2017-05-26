/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.settings.database;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adira0150
 */
@XmlRootElement
public class DataBaseSettings {
    private int id;
    private String dbUser=new String();
    private String dbPassword=new String();
    private String chosenDatabase=new String();
    private StringProperty chosenDatabaseProperty=new SimpleStringProperty();
    
    public DataBaseSettings(){}
    
    @XmlAttribute
    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    @XmlElement
    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }
    
    public String getDbUser() {
        return dbUser;
    }

    @XmlElement
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    @XmlElement
    public void setChosenDatabase(String chosenDatabase) {
        this.chosenDatabase = chosenDatabase;
    }
    public String getChosenDatabase() {
        return chosenDatabase;
    }
    /*
    public StringProperty getChosenDatabaseProperty() {
    return chosenDatabaseProperty;
    }
    
    public void setChosenDatabaseProperty(StringProperty chosenDatabaseProperty) {
    this.chosenDatabaseProperty = chosenDatabaseProperty;
    }
    
    public void setChosenDatabaseProperty(String chosenDatabase) {
    this.chosenDatabaseProperty.set(chosenDatabase);
    }*/

    
    
    
    
    
           
} 
