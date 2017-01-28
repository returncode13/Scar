/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author sharath
 */

@Entity
@Table(name="Acquisition")
public class Acquisition implements Serializable{

    @Id
    private Long id;
    
    @Column(name="SubSurfaceLines")
    private String subsurfaceLines;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubsurfaceLines() {
        return subsurfaceLines;
    }

    public void setSubsurfaceLines(String subsurfaceLines) {
        this.subsurfaceLines = subsurfaceLines;
    }
    
    
    public Acquisition() {
    }
    
    
    
    
    
    
}
