
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "Ancestors")
public class Ancestors implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAncestors;
    
    @ManyToOne
    @JoinColumn(name = "node_fk")       
    private SessionDetails sessionDetails;
    
    @Column(name="ancestor")
    private Long ancestor;  

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.idAncestors);
        hash = 71 * hash + Objects.hashCode(this.sessionDetails);
        hash = 71 * hash + Objects.hashCode(this.ancestor);
        return hash;
    }

    

    @Override
    public boolean equals(Object obj) {
        Ancestors a=(Ancestors) obj;
        System.out.println("equals called for "+a.getSessionDetails().getJobStep().getNameJobStep()+" SsdPK: "+a.getSessionDetails().getIdSessionDetails());
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ancestors other = (Ancestors) obj;
        if (!Objects.equals(this.idAncestors, other.idAncestors)) {
            return false;
        }
        if (!Objects.equals(this.sessionDetails, other.sessionDetails)) {
            return false;
        }
        if (!Objects.equals(this.ancestor, other.ancestor)) {
            return false;
        }
        return true;
    }

    
    
   
    public Ancestors() {
    }

    /*
    public Ancestors(Long idAncestors, SessionDetails sessionDetails) {
        this.idAncestors = idAncestors;
        this.sessionDetails = sessionDetails;
        
    }*/

    public Ancestors(SessionDetails sessionDetails, Long ancestor) {
        this.sessionDetails = sessionDetails;
        this.ancestor = ancestor;
    }

   
    
    
    public Long getIdAncestors() {
        return idAncestors;
    }

    public void setIdAncestors(Long idAncestors) {
        this.idAncestors = idAncestors;
    }

    public SessionDetails getSessionDetails() {
        return sessionDetails;
    }

    public void setSessionDetails(SessionDetails sessionDetails) {
        this.sessionDetails = sessionDetails;
    }

    public Long getAncestor() {
        return ancestor;
    }

    public void setAncestor(Long ancestor) {
        this.ancestor = ancestor;
        System.out.println("Ancs: AncestorValue: "+ancestor);
    }
    
    
}
