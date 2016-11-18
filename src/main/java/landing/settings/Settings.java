/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.settings;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sharath
 */


@XmlRootElement
public class Settings {
    private int id;
    private String sshUser;
    private String sshPassword;
    private String sshHost;
    private String dbUser;
    private String dbPassword;

    public Settings(){}
    
    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
   
    @XmlElement
    public void setSshUser(String sshUser) {
        this.sshUser = sshUser;
    }
     public String getSshUser() {
        return sshUser;
    }

    @XmlElement
    public void setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword;
    } 
    public String getSshPassword() {
        return sshPassword;
    }

    @XmlElement
    public void setSshHost(String sshHost) {
        this.sshHost = sshHost;
    }
    public String getSshHost() {
        return sshHost;
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


    public boolean isPopulated(){
        if(sshUser==null || sshPassword==null ||sshHost==null || dbUser==null || dbPassword==null ) return false;
        else return true;
    }
 
    
    
}
