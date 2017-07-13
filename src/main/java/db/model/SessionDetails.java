/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sharath nair
 */
@Entity
@Table(name = "SessionDetails",schema = "obpmanager")
public class SessionDetails implements Serializable{
    
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long idSessionDetails;
   
    
   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "jobStep_sessionDetails_fk",nullable = true)
   private JobStep jobStep;                                                 //mappedBy property in JobStep class definition // JoinColumn 1 in Dependencies
    
   
   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "sessions_sessionDetails_fk",nullable = true)                        //refers to the idSessions member in Sessions class.
   private Sessions sessions;                                               //mappedBy property in Sessions class definition //JoinColumn 2 in Dependencies class def

   
   @OneToMany(mappedBy = "sessionDetails",cascade = CascadeType.ALL,orphanRemoval = true)
   private Set<Descendants> descendants;
   
   @OneToMany(mappedBy = "sessionDetails",cascade = CascadeType.ALL,orphanRemoval = true)
   private Set<Ancestors> ancestors;

    @OneToMany(mappedBy = "sessionDetails",cascade = CascadeType.ALL,orphanRemoval = true)
   private Set<Parent> parents;
    
     @OneToMany(mappedBy = "sessionDetails",cascade = CascadeType.ALL,orphanRemoval = true)
   private Set<Child> children;
     
      @OneToMany(mappedBy = "sessionDetails",cascade = CascadeType.ALL,orphanRemoval = true)
   private Set<QcMatrix> qcMatrix;
      
      @OneToMany(mappedBy = "sessionDetails",cascade = CascadeType.ALL,orphanRemoval = true)
   private Set<DoubtStatus> doubtstatus;
    
    public SessionDetails() {
    }

   
    public SessionDetails(JobStep jobStep, Sessions sessions) {
        this.jobStep = jobStep;
        this.sessions = sessions;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.idSessionDetails);
        hash = 41 * hash + Objects.hashCode(this.jobStep);
        hash = 41 * hash + Objects.hashCode(this.sessions);
      //  hash = 41 * hash + Objects.hashCode(this.descendants);
       // hash = 41 * hash + Objects.hashCode(this.ancestors);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final SessionDetails other = (SessionDetails) obj;
        if (!Objects.equals(this.idSessionDetails, other.idSessionDetails)) {
            return false;
        }
        if (!Objects.equals(this.jobStep, other.jobStep)) {
            return false;
        }
        if (!Objects.equals(this.sessions, other.sessions)) {
            return false;
        }
        return true;
    }


    
    
    
    public Set<Descendants> getDescendants() {
        return descendants;
    }

    public void setDescendants(Set<Descendants> descendants) {
        this.descendants = descendants;
    }

    public Set<Ancestors> getAncestors() {
        return ancestors;
    }

    public void setAncestors(Set<Ancestors> ancestors) {
        this.ancestors = ancestors;
    }

    public Long getIdSessionDetails() {
        return idSessionDetails;
    }

    public void setIdSessionDetails(Long idSessionDetails) {
        this.idSessionDetails = idSessionDetails;
    }
   
   
   
  
   
    public JobStep getJobStep() {
        return jobStep;
    }

    public void setJobStep(JobStep jobStep) {
        this.jobStep = jobStep;
    }

    public Sessions getSessions() {
        return sessions;
    }

    public void setSessions(Sessions sessions) {
        this.sessions = sessions;
    }

    public Set<Parent> getParents() {
        return parents;
    }

    public void setParents(Set<Parent> parents) {
        this.parents = parents;
    }

    public Set<Child> getChildren() {
        return children;
    }

    public void setChildren(Set<Child> children) {
        this.children = children;
    }

    

    

   

    
}
