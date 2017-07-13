/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

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
@Table(name="DoubtStatus",schema="obpmanager")
public class DoubtStatus {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idDoubtStatus;
    
    @ManyToOne
    @JoinColumn(name="headers_fk",nullable=false)
    private Headers headers;
    
    @ManyToOne
    @JoinColumn(name="parent_sessiondetails_fk",nullable=false)
    private SessionDetails parentSessionDetails;
    
    @ManyToOne
    @JoinColumn(name="doubtType_fk",nullable=false)
    private DoubtType doubtType;
    
    @ManyToOne
    @JoinColumn(name="user_fk",nullable=true)
    private User user;
    
    
    @Column(name="child_sessionDetails_id",nullable=false)
    private Long childSessionDetailsId;
    
    
    @Column(name="status")
    private String status;
    
    @Column(name="errorMessage")
    private String errorMessage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getChildSessionDetailsId() {
        return childSessionDetailsId;
    }

    public void setChildSessionDetailsId(Long childSessionDetailsId) {
        this.childSessionDetailsId = childSessionDetailsId;
    }
    
    
    
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    

    public Long getIdDoubtStatus() {
        return idDoubtStatus;
    }

    

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public SessionDetails getParentSessionDetails() {
        return parentSessionDetails;
    }

    public void setParentSessionDetails(SessionDetails parentSessionDetails) {
        this.parentSessionDetails = parentSessionDetails;
    }

    public DoubtType getDoubtType() {
        return doubtType;
    }

    public void setDoubtType(DoubtType doubtType) {
        this.doubtType = doubtType;
    }
    
    
}
