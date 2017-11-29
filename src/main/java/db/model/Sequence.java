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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Entity
@Table(name="sequence",schema="public")
public class Sequence {
@Id
@GeneratedValue
private Long id;

@Column(name="sequenceno")
private Long sequenceno;

@Column(name="status")
private String status;

@Column(name="real_line_name")
private String realLineName;


 
@OneToMany(mappedBy = "sequence",cascade = CascadeType.ALL,orphanRemoval = true)
private Set<Subsurface> subsurfaces;

@OneToMany(mappedBy = "sequence",cascade = CascadeType.ALL,orphanRemoval = true)
private Set<Acquisition> acquisition;

@OneToMany(mappedBy = "sequence",cascade = CascadeType.ALL,orphanRemoval = true)
private Set<Headers> headers;



    public Set<Headers> getHeaders() {
        return headers;
    }

    public void setHeaders(Set<Headers> headers) {
        this.headers = headers;
    }


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRealLineName() {
        return realLineName;
    }

    public void setRealLineName(String realLineName) {
        this.realLineName = realLineName;
    }


    


}
