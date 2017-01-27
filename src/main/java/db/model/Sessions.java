/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author sharath nair
 */
@Entity
@Table(name = "Sessions",schema = "obpmanager",uniqueConstraints = {@UniqueConstraint(columnNames = {"idSessions"})})

public class Sessions implements Serializable{
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSessions")
    private Long idSessions;
    
    @Column(name = "nameSessions",length = 1025)
    private String nameSessions;
    
    @Column(name = "hashSessions",length = 1025)
    private String hashSessions;
    
    
   @OneToMany(mappedBy = "sessions",cascade = CascadeType.ALL,orphanRemoval = true)
   private Set<SessionDetails> sessionDetails;

    public Sessions(String nameSessions, String hashSessions) {
        this.nameSessions = nameSessions;
        this.hashSessions = hashSessions;
    }

    public Sessions() {
    }

   
   
    public Long getIdSessions() {
        return idSessions;
    }

    public void setIdSessions(Long idSessions) {
        this.idSessions = idSessions;
    }

    public String getNameSessions() {
        return nameSessions;
    }

    public void setNameSessions(String nameSessions) {
        this.nameSessions = nameSessions;
    }

    public String getHashSessions() {
        return hashSessions;
    }

    public void setHashSessions(String hashSessions) {
        this.hashSessions = hashSessions;
    }

    public Set<SessionDetails> getSessionDetails() {
        return sessionDetails;
    }

    public void setSessionDetails(Set<SessionDetails> sessionDetails) {
        this.sessionDetails = sessionDetails;
    }

    @Override
    public String toString() {
        return "Sessions{" + "idSessions=" + idSessions + ", nameSessions=" + nameSessions + '}';
    }

    
   
   
   
    
}
