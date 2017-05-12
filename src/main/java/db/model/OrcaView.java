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
@Table(name="good_sp",schema = "orca")
public class OrcaView implements Serializable{

    @Id
    private Long seq;
    
    @Column(name="real_line_name")
    private String subsurfaceLines;

    @Column(name="preplot_name")
    private String preplotNames;
    
    @Column(name="first_ffid")
    private Integer firstFFID;
    
    @Column(name="first_shot")
    private Integer firstSHOT;
    
    @Column(name="fg_ffid")
    private Integer fgFFID;
    
    @Column(name="fgsp")
    private Integer fgsp;
    
    @Column(name="last_ffid")
    private Integer lastFFID;
    
    @Column(name="last_shot")
    private Integer lastSHOT;
    
    @Column(name="lgsp")
    private Integer lgsp;
    
    
    
    @Column(name="lg_ffid")
    private Integer lgFFID;
    
    @Column(name="start_data")
    private String date;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }
    
  

    public String getSubsurfaceLines() {
        return subsurfaceLines;
    }

    public void setSubsurfaceLines(String subsurfaceLines) {
        this.subsurfaceLines = subsurfaceLines;
    }

    public String getPreplotNames() {
        return preplotNames;
    }

    public void setPreplotNames(String preplotNames) {
        this.preplotNames = preplotNames;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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
    
    
   
    public OrcaView() {
    }
    
    
    
    
    
    
}
