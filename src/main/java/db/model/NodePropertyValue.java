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
 * Dynamically generated entries. Map between JobSteps and NodeProperty.
 * job#13 has its property"to" with value=1313
 * job#13 has its property"from" with value=2626
 */
@Entity
@Table(name="NodePropertyValue",schema="obpmanager")
public class NodePropertyValue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNodePropertyValue;
    
    @ManyToOne()
    @JoinColumn(name="jobstep_fk",nullable=false)
    private JobStep jobStep;
    
    
    @ManyToOne()
    @JoinColumn(name="nodepropertytype_fk",nullable=false)
    private NodeProperty nodeProperty;

    
    @Column(name="value",nullable=true)
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
    
    
    
    public JobStep getJobStep() {
        return jobStep;
    }

    public void setJobStep(JobStep jobStep) {
        this.jobStep = jobStep;
    }

    public NodeProperty getNodeProperty() {
        return nodeProperty;
    }

    public void setNodeProperty(NodeProperty nodeProperty) {
        this.nodeProperty = nodeProperty;
    }

    public Long getIdNodePropertyValue() {
        return idNodePropertyValue;
    }
    
    
    
    
    
}
