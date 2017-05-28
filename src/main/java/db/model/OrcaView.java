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
@Table(name="subline",schema = "public")
public class OrcaView implements Serializable{

    @Id
    @Column(name="sequence")
    private Long sequence;
    
    @Column(name="real_line_name")
    private String subsurfaceLines;

   @Column(name="cable")
   private Long cable;
   
   @Column(name="first_channel")
   private Long firstChannel;
   
   @Column(name="last_channel")
   private Long lastChannel;
   
   @Column(name="gun")
   private Long gun;
   
    
    @Column(name="first_ffid")
    private Integer firstFFID;
    
    @Column(name="last_ffid")
    private Integer lastFFID;
    
    @Column(name="first_shot")
    private Integer firstSHOT;
    
     @Column(name="last_shot")
    private Integer lastSHOT;
    
    @Column(name="fg_ffid")
    private Integer fgFFID;
    
     @Column(name="lg_ffid")
    private Integer lgFFID;
    
    @Column(name="fgsp")
    private Integer fgsp;
    
    @Column(name="lgsp")
    private Integer lgsp;
    
    
    
   
    /*
    @Column(name="start_data")
    private String date;*/

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }
    
  

    public String getSubsurfaceLines() {
        return subsurfaceLines;
    }

    public void setSubsurfaceLines(String subsurfaceLines) {
        this.subsurfaceLines = subsurfaceLines;
    }

   

    public Integer getFgsp() {
        return fgsp;
    }

    public void setFgsp(Integer fgsp) {
        this.fgsp = fgsp;
    }

    public Integer getLgsp() {
        return lgsp;
    }

    public void setLgsp(Integer lgsp) {
        this.lgsp = lgsp;
    }

    public Integer getFgFFID() {
        return fgFFID;
    }

    public void setFgFFID(Integer fgFFID) {
        this.fgFFID = fgFFID;
    }

    public Integer getLgFFID() {
        return lgFFID;
    }

    public void setLgFFID(Integer lgFFID) {
        this.lgFFID = lgFFID;
    }

    /*public String getDate() {
    return date;
    }
    
    public void setDate(String date) {
    this.date = date;
    }*/

    public Integer getFirstFFID() {
        return firstFFID;
    }

    public void setFirstFFID(Integer firstFFID) {
        this.firstFFID = firstFFID;
    }

    public Integer getFirstSHOT() {
        return firstSHOT;
    }

    public void setFirstSHOT(Integer firstSHOT) {
        this.firstSHOT = firstSHOT;
    }

    public Integer getLastFFID() {
        return lastFFID;
    }

    public void setLastFFID(Integer lastFFID) {
        this.lastFFID = lastFFID;
    }

    public Integer getLastSHOT() {
        return lastSHOT;
    }

    public void setLastSHOT(Integer lastSHOT) {
        this.lastSHOT = lastSHOT;
    }

    public Long getCable() {
        return cable;
    }

    public void setCable(Long cable) {
        this.cable = cable;
    }

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

    public Long getGun() {
        return gun;
    }

    public void setGun(Long gun) {
        this.gun = gun;
    }
    
    
   
    public OrcaView() {
    }
    
    
    
    
    
    
}
