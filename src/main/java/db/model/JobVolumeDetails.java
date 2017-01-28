/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
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
@Table(name = "JobVolumeDetails")
public class JobVolumeDetails implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJobVolumeDetails;
    
    @ManyToOne
    @JoinColumn(name= "jobStep_jvdetails_fk",nullable = false)
    private JobStep jobStep;
    
    @ManyToOne
    @JoinColumn(name = "volume_jvdetails_fk",nullable = false)
    private Volume volume;

    public JobVolumeDetails() {
    }

    public JobVolumeDetails(JobStep jobStep, Volume volume) {
        this.jobStep = jobStep;
        this.volume = volume;
    }
    
    
    

    public Long getIdJobVolumeDetails() {
        return idJobVolumeDetails;
    }

    public void setIdJobVolumeDetails(Long idJobVolumeDetails) {
        this.idJobVolumeDetails = idJobVolumeDetails;
    }

    public JobStep getJobStep() {
        return jobStep;
    }

    public void setJobStep(JobStep jobStep) {
        this.jobStep = jobStep;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    
    
    
    
    
    
}
