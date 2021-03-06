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
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Entity
@Table(name="subsurface",schema="public")

public class Subsurface implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="subsurface")
    private String subsurface;
    
    @ManyToOne
    @JoinColumn(name="seq_fk",nullable=false)
    private Sequence sequence;
    
    @OneToMany(mappedBy = "subsurface",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Acquisition> acquisition;
    
    @OneToMany(mappedBy = "subsurface",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Headers> headers;

    public Set<Acquisition> getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(Set<Acquisition> acquisition) {
        this.acquisition = acquisition;
    }

    public Set<Headers> getHeaders() {
        return headers;
    }

    public void setHeaders(Set<Headers> headers) {
        this.headers = headers;
    }

    
    
    public String getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(String subsurface) {
        this.subsurface = subsurface;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Long getId() {
        return id;
    }
    
    
}
