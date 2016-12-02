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
@Table(name="ImmediateParent")
public class Parent implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idParent;
    
    @ManyToOne
    @JoinColumn(name = "node_fk")       
    private SessionDetails sessionDetails;
    
    @Column(name="parent")
    private Long parent;  

    public Parent() {
    }

    
    
    public Parent(SessionDetails sessionDetails, Long parent) {
        this.sessionDetails = sessionDetails;
        this.parent = parent;
    }

    public Long getIdParent() {
        return idParent;
    }

    public void setIdParent(Long idParent) {
        this.idParent = idParent;
    }

    public SessionDetails getSessionDetails() {
        return sessionDetails;
    }

    public void setSessionDetails(SessionDetails sessionDetails) {
        this.sessionDetails = sessionDetails;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
    
    
}
