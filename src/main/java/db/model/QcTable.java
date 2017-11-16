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
@Table(name="QcTable",schema="obpmanager")
public class QcTable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idQcTable;
    /*
    @ManyToOne
    @JoinColumn(name="volume_fk",nullable=false)
    private Volume volume;
    
    @ManyToOne
    @JoinColumn(name="qctype_fk",nullable=false)
    private QcType qctype;*/
    
    
    @ManyToOne
    @JoinColumn(name="qcmatrix_fk",nullable=false)
    private QcMatrix qcmatrix;
    
    @ManyToOne
    @JoinColumn(name="headers_fk",nullable=false)
    private Headers headers;
    
    
    /*  @Column(name="Seq")
    private Long sequenceNumber;
    
    @Column(name="Subsurface",length=1025)
    private String subsurface;*/
    
    @Column(name="time")
    private String time;
    
    @Column(name="result")
    private Boolean result;
    
    @Column(name="comment",length=100000)
    private String comment;

    /*public Volume getVolume() {
    return volume;
    }
    
    public void setVolume(Volume volume) {
    this.volume = volume;
    }
    
    public QcType getQctype() {
    return qctype;
    }
    
    public void setQctype(QcType qctype) {
    this.qctype = qctype;
    }*/
    /* public Long getSequenceNumber() {
    return sequenceNumber;
    }
    
    public void setSequenceNumber(Long sequenceNumber) {
    this.sequenceNumber = sequenceNumber;
    }
    
    public String getSubsurface() {
    return subsurface;
    }
    
    public void setSubsurface(String subsurface) {
    this.subsurface = subsurface;
    }*/
    
    
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
    
    
    

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

   
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public QcTable() {
    }

    public QcMatrix getQcmatrix() {
        return qcmatrix;
    }

    public void setQcmatrix(QcMatrix qcmatrix) {
        this.qcmatrix = qcmatrix;
    }

    public Long getIdQcTable() {
        return idQcTable;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }
    
    
    
    
    
    
    
    
}
