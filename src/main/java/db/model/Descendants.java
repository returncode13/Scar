/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author sharath nair
 */
@Entity
@Table(name = "Descendants")
public class Descendants implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDescendants;
    
     @ManyToOne
     @JoinColumn(name = "node_fk")
    private SessionDetails sessionDetails;                                    //Here Primary key is the primary key in SessionsDetails table
    
   
     @Column(name="descendant")
     private Long descendant;

    public Long getDescendant() {
        return descendant;
    }

    public void setDescendant(Long descendant) {
        this.descendant = descendant;
    }
     
     
    
    public Descendants() {
    }

    public Descendants( SessionDetails sessionDetails,Long descendant) {
        this.descendant = descendant;
        this.sessionDetails = sessionDetails;
        
    }

    public Long getIdDescendants() {
        return idDescendants;
    }

    public void setIdDescendants(Long idDescendants) {
        this.idDescendants = idDescendants;
    }

    public SessionDetails getSessionDetails() {
        return sessionDetails;
    }

    public void setSessionDetails(SessionDetails sessionDetails) {
        this.sessionDetails = sessionDetails;
    }

    
    
}
