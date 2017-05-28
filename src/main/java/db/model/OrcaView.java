/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author sharath
 */

@Entity 
@Table(name="subline",schema = "public")
public class OrcaView implements Serializable{
    
   
    /*@Column(name="sequence")
    @Id private Long sequence;
    
    
    @Column(name="real_line_name")
    @Id private String real_line_name;
    
    
    @Column(name="cable")
    @Id private Long cable;*/

    public Long getSequences() {
        return orcaid.getSequence();
    }

    public String getSubsurfaceLineNames() {
        return orcaid.getReal_line_name();
    }

    public Long getCables() {
        return orcaid.getCable();
    }
    
    public Long getGuns() {
    return orcaid.getGun();
    }
    
    
    
    
   @Id
   private OrcaKey orcaid;

    public OrcaKey getOrcaid() {
        return orcaid;
    }

    public void setOrcaid(OrcaKey orcaid) {
        this.orcaid = orcaid;
    }
   
   
   @Column(name="first_channel")
   private Long firstChannel;
   
   @Column(name="last_channel")
   private Long lastChannel;
   
   
   /* @Column(name="gun")
   @Id private Long gun;*/
   
    
    @Column(name="first_ffid")
    private Long firstFFID;
    
    @Column(name="last_ffid")
    private Long lastFFID;
    
    @Column(name="first_shot")
    private Long firstSHOT;
    
     @Column(name="last_shot")
    private Long lastSHOT;
    
    @Column(name="fg_ffid")
    private Long fgFFID;
    
     @Column(name="lg_ffid")
    private Long lgFFID;
    
    @Column(name="fgsp")
    private Long fgsp;
    
    @Column(name="lgsp")
    private Long lgsp;
    
    
    
   
    /*
    @Column(name="start_data")
    private String date;*/

    /*public Long getSequences() {
    return sequence;
    }
    
    public void setSequence(Long sequence) {
    this.sequence = sequence;
    }
    
    
    
    public String getSubsurfaceLineNames() {
    return real_line_name;
    }
    
    public void setSubsurfaceLineName(String real_line_name) {
    this.real_line_name = real_line_name;
    }*/
   

    public Long getFgsp() {
        return fgsp;
    }

    public void setFgsp(Long fgsp) {
        this.fgsp = fgsp;
    }

    public Long getLgsp() {
        return lgsp;
    }

    public void setLgsp(Long lgsp) {
        this.lgsp = lgsp;
    }

    public Long getFgFFID() {
        return fgFFID;
    }

    public void setFgFFID(Long fgFFID) {
        this.fgFFID = fgFFID;
    }

    public Long getLgFFID() {
        return lgFFID;
    }

    public void setLgFFID(Long lgFFID) {
        this.lgFFID = lgFFID;
    }

    /*public String getDate() {
    return date;
    }
    
    public void setDate(String date) {
    this.date = date;
    }*/

    public Long getFirstFFID() {
        return firstFFID;
    }

    public void setFirstFFID(Long firstFFID) {
        this.firstFFID = firstFFID;
    }

    public Long getFirstSHOT() {
        return firstSHOT;
    }

    public void setFirstSHOT(Long firstSHOT) {
        this.firstSHOT = firstSHOT;
    }

    public Long getLastFFID() {
        return lastFFID;
    }

    public void setLastFFID(Long lastFFID) {
        this.lastFFID = lastFFID;
    }

    public Long getLastSHOT() {
        return lastSHOT;
    }

    public void setLastSHOT(Long lastSHOT) {
        this.lastSHOT = lastSHOT;
    }
    /*
    public Long getCables() {
    return cable;
    }
    
    public void setCable(Long cable) {
    this.cable = cable;
    }*/
    public Long getFirstChannel() {
        return firstChannel;
    }

    public void setFirstChannel(Long firstChannel) {
        this.firstChannel = firstChannel;
    }

    public Long getLastChannel() {
        return lastChannel;
    }

    public void setLastChannel(Long lastChannel) {
        this.lastChannel = lastChannel;
    }

    /* public Long getGuns() {
    return gun;
    }
    
    public void setGun(Long gun) {
    this.gun = gun;
    }*/
    
    public String getDugSubsurface(){
        String sub=orcaid.getReal_line_name();
        Long cab=orcaid.getCable();
        Long gn=orcaid.getGun();
        String dugsub=sub+"_cable"+cab+"_gun"+gn;
        return dugsub;
    }

    /* @Override
    public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.sequence);
    hash = 97 * hash + Objects.hashCode(this.real_line_name);
    hash = 97 * hash + Objects.hashCode(this.cable);
    hash = 97 * hash + Objects.hashCode(this.gun);
    return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
    if (this == obj) {
    return true;
    }
    if (obj == null) {
    return false;
    }
    if (getClass() != obj.getClass()) {
    return false;
    }
    final OrcaView other = (OrcaView) obj;
    if (!Objects.equals(this.real_line_name, other.real_line_name)) {
    return false;
    }
    if (!Objects.equals(this.sequence, other.sequence)) {
    return false;
    }
    if (!Objects.equals(this.cable, other.cable)) {
    return false;
    }
    if (!Objects.equals(this.gun, other.gun)) {
    return false;
    }
    return true;
    }*/
    
    
    
    
  
    public OrcaView() {
    }

    
    
    
    
    
    
    
}



