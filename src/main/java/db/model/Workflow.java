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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author adira0150
 */
@Entity
@Table(name="Workflow",schema="obpmanager")
public class Workflow implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idworkflows;
    
    @ManyToOne
    @JoinColumn(name="volume_fk",nullable=false)
    private Volume volume;
    
    @OneToMany(mappedBy="workflow",cascade=CascadeType.ALL,orphanRemoval = true)
    private Set<Logs> logs;
    
    @Column(name="wfversion")
    private Long wfversion;
    
    @Column(name="md5sum")
    private String md5sum;
    
    @Column(name="contents",length=600000)
    private String contents;
    
    @Column(name="time")
    private String time;
    
    
    public Long getIdworkflows() {
        return idworkflows;
    }

    public void setIdworkflows(Long idworkflows) {
        this.idworkflows = idworkflows;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public Long getWfversion() {
        return wfversion;
    }

    public void setWfversion(Long wfversion) {
        this.wfversion = wfversion;
    }

    public String getMd5sum() {
        return md5sum;
    }

    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Set<Logs> getLogs() {
        return logs;
    }

    public void setLogs(Set<Logs> logs) {
        this.logs = logs;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    
    
}
