/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author sharath nair
 */
@Entity
@Table(name="Child",schema = "obpmanager")
public class Child implements Serializable{
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idChild;
    
    @ManyToOne
    @JoinColumn(name = "node_fk")       
    private SessionDetails sessionDetails;
    
    @Column(name="child")
    private Long child;

    public Child() {
    }

    
    
    public Child(SessionDetails sessionDetails, Long child) {
        this.sessionDetails = sessionDetails;
        this.child = child;
    }

    public Long getIdChild() {
        return idChild;
    }

    public void setIdChild(Long idChild) {
        this.idChild = idChild;
    }

    public SessionDetails getSessionDetails() {
        return sessionDetails;
    }

    public void setSessionDetails(SessionDetails sessionDetails) {
        this.sessionDetails = sessionDetails;
    }

    public Long getChild() {
        return child;
    }

    public void setChild(Long child) {
        this.child = child;
    }
    
    
}
