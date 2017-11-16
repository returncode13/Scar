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
 * @author naila0152
 */
@Entity
@Table(name="Logs",schema = "obpmanager")

public class Logs implements Serializable{
    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idLogs;
    
    @ManyToOne
    @JoinColumn(name="headers_fk",nullable = true)
    private Headers headers;
    
    @ManyToOne
    @JoinColumn(name="volume_headers_fk",nullable = false)
    private Volume volume;
    
    @ManyToOne
    @JoinColumn(name="workflow_fk",nullable = true)
    private Workflow workflow=null;
    /* @Column(name="numberOfRuns")
    private Long numberOfRuns;*/

    @Column(name="logPath",length=4096)
    private String logpath;
    
    @Column(name="timeStamp",length =1024)
    private String timestamp;
    
    @Column(name="subsurfaces",length=1024)
    private String subsurfaces;
    
    @Column(name="sequence")
    private Long sequence;
    
    @Column(name="insightVersion",length=1024)
    private String insightVersion;
    
    @Column(name="completedsuccessfully")
    private Boolean completedsuccessfully=false;
    
    @Column(name="errored")
    private Boolean errored=false;
    
    @Column(name="running")
    private Boolean running=true;
    
    @Column(name="cancelled")
    private Boolean cancelled=false;
    
    @Column(name="UpdateTime")
    private String updateTime;
    
    @Column(name="SummaryTime")
    private String summaryTime;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSummaryTime() {
        return summaryTime;
    }

    public void setSummaryTime(String summaryTime) {
        this.summaryTime = summaryTime;
    }
    
    
    
    
    public String getInsightVersion() {
        return insightVersion;
    }

    public void setInsightVersion(String insightVersion) {
        this.insightVersion = insightVersion;
    }
    
    
    
    
    public String getSubsurfaces() {
        return subsurfaces;
    }

    public void setSubsurfaces(String sublines) {
        this.subsurfaces = sublines;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }
    
    
    
    /*public Long getNumberOfRuns() {
    return numberOfRuns;
    }
    
    public void setNumberOfRuns(Long version) {
    this.numberOfRuns = version;
    }*/

    public String getLogpath() {
        return logpath;
    }

    public void setLogpath(String logpath) {
        this.logpath = logpath;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }
    
    
    
    
    
    
    public Long getIdLogs() {
        return idLogs;
    }

    /* public void setIdLogs(Long idLogs) {
    this.idLogs = idLogs;
    }*/

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Boolean getCompletedsuccessfully() {
        return completedsuccessfully;
    }

    public void setCompletedsuccessfully(Boolean completedsuccessfully) {
        this.completedsuccessfully = completedsuccessfully;
    }

    public Boolean getErrored() {
        return errored;
    }

    public void setErrored(Boolean errored) {
        this.errored = errored;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }
    
    
    
    
}
