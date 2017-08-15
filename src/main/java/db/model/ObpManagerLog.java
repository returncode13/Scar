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
 * @author sharath nair <sharath.nair@polarcus.com>
 */


@Entity
@Table(name="ObpmanagerLog",schema="obpmanager")
public class ObpManagerLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idObpManagerLog;
    
    @Column(name="level",nullable=false)
    private Long level;
    
    @Column(name="logger",nullable=false)
    private String logger;
    
    @Column(name="message",nullable=false,length = 8192)
    private String message;
    
    @Column(name="sourceclass",nullable=false)
    private String sourceClass;
    
    @Column(name="sourcemethod",nullable=false)
    private String sourceMethod;
    
    @Column(name="threadid",nullable=false)
    private Long threadId;
    
    @Column(name="timeentered",nullable=false)
    private String timeEntered;
    
    /* @ManyToOne
    @JoinColumn(name="sessions_fk",nullable=true)
    private Sessions sessions;*/

    public Long getIdObpManagerLog() {
        return idObpManagerLog;
    }

    /* public Sessions getSessions() {
    return sessions;
    }
    
    public void setSessions(Sessions sessions) {
    this.sessions = sessions;
    }*/

    
    
    
    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(String sourceClass) {
        this.sourceClass = sourceClass;
    }

    public String getSourceMethod() {
        return sourceMethod;
    }

    public void setSourceMethod(String sourceMethod) {
        this.sourceMethod = sourceMethod;
    }

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public String getTimeEntered() {
        return timeEntered;
    }

    public void setTimeEntered(String timeEntered) {
        this.timeEntered = timeEntered;
    }
    
    
    
}
