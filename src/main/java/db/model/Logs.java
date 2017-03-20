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
    
    @Column(name="version")
    private Long version;

    @Column(name="logPath",length=4096)
    private String logpath;
    
    @Column(name="timeStamp",length =1024)
    private String timestamp;
    
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getLogpath() {
        return logpath;
    }

    public void setLogpath(String logpath) {
        this.logpath = logpath;
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
    
    
    
    
}
