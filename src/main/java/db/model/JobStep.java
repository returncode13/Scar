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
@Table(name="JobStep",uniqueConstraints = {@UniqueConstraint(columnNames = {"idJobStep"})})

public class JobStep implements Serializable{
    
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "idJobStep",nullable = false,unique = true,length = 10)
    private Long idJobStep;
    
    @Column(name = "nameJobStep",nullable = true,length = 256)
    private String nameJobStep;
    
    @Column(name = "alert",nullable = true)
    private Boolean alert;

    @OneToMany(mappedBy = "jobStep",cascade = CascadeType.ALL,orphanRemoval = true)                              //create a member named "jobStep" in the JobVolumeDetails class definition
    private Set<JobVolumeDetails> jobVolumeDetails;
    
    @OneToMany(mappedBy = "jobStep",cascade = CascadeType.ALL,orphanRemoval = true)                             //create a member named "jobStep" in the SessionDetails class definition
    private Set<SessionDetails> sessionDetails;

    public JobStep(String nameJobStep, Boolean alert) {
        this.nameJobStep = nameJobStep;
        this.alert = alert;
    }

    public JobStep() {
    }
    

    
    

  
    
    
    public Long getIdJobStep() {
        return idJobStep;
    }

    public void setIdJobStep(Long idJobStep) {
        this.idJobStep = idJobStep;
    }

    public String getNameJobStep() {
        return nameJobStep;
    }

    public void setNameJobStep(String nameJobStep) {
        this.nameJobStep = nameJobStep;
    }

    public Boolean isAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        this.alert = alert;
    }

    public Set<JobVolumeDetails> getJobVolumeDetails() {
        return jobVolumeDetails;
    }

    public void setJobVolumeDetails(Set<JobVolumeDetails> jobVolumeDetails) {
        this.jobVolumeDetails = jobVolumeDetails;
    }

    public Set<SessionDetails> getSessionDetails() {
        return sessionDetails;
    }

    public void setSessionDetails(Set<SessionDetails> sessionDetails) {
        this.sessionDetails = sessionDetails;
    }
    
    
    
    
}
