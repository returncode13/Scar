/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Entity
@Table(name="Sequence",schema="obpmanager")
public class Sequence {
@Id
private Long id;

@Column(name="sequenceno")
private Long sequenceno;

 
@OneToMany(mappedBy = "sequence",cascade = CascadeType.ALL,orphanRemoval = true)
private Set<Subsurface> subsurfaces;

@OneToMany(mappedBy = "sequence",cascade = CascadeType.ALL,orphanRemoval = true)
private Set<Acquisition> acquisition;

    public Set<Acquisition> getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(Set<Acquisition> acquisition) {
        this.acquisition = acquisition;
    }


    public Set<Subsurface> getSubsurfaces() {
        return subsurfaces;
    }

    public void setSubsurfaces(Set<Subsurface> subsurfaces) {
        this.subsurfaces = subsurfaces;
    }
    
     public Long getSequenceno() {
        return sequenceno;
    }

    public void setSequenceno(Long sequenceno) {
        this.sequenceno = sequenceno;
    }

    public Long getId() {
        return id;
    }





}
