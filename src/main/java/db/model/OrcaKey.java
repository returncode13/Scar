/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Embeddable
    public class OrcaKey implements Serializable {
    
    @Column(name="sequence")
    private Long sequence;
    
    @Column(name="real_line_name")
    private String real_line_name;
    
    @Column(name="cable")
    private Long cable;
    
    
   @Column(name="gun")
    private Long gun;
    
    
    public Long getSequence() {
    return sequence;
    }
    
    public void setSequence(Long sequence) {
    this.sequence = sequence;
    }
    
   
    public String getReal_line_name() {
    return real_line_name;
    }
    
    public void setReal_line_name(String real_line_name) {
    this.real_line_name = real_line_name;
    }
    
    
    public Long getCable() {
    return cable;
    }
    
    public void setCable(Long cable) {
    this.cable = cable;
    }

    
    public Long getGun() {
        return gun;
    }

    public void setGun(Long gun) {
        this.gun = gun;
    }
    
    
    
    
    
    public OrcaKey() {
    }
    }
