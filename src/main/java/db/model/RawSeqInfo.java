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
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Entity
@Table(name="raw_seq_info",schema = "public")
public class RawSeqInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    
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
    
    @Column(name="real_line_name")
   private String realLineName;
   
   @Column(name="preplot_name")
   private String preplotName;
   
   @Column(name="start_data")
   private String startDate;

    public Long getFirstFFID() {
        return firstFFID;
    }

    public void setFirstFFID(Long firstFFID) {
        this.firstFFID = firstFFID;
    }

    public Long getLastFFID() {
        return lastFFID;
    }

    public void setLastFFID(Long lastFFID) {
        this.lastFFID = lastFFID;
    }

    public Long getFirstSHOT() {
        return firstSHOT;
    }

    public void setFirstSHOT(Long firstSHOT) {
        this.firstSHOT = firstSHOT;
    }

    public Long getLastSHOT() {
        return lastSHOT;
    }

    public void setLastSHOT(Long lastSHOT) {
        this.lastSHOT = lastSHOT;
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

    public String getRealLineName() {
        return realLineName;
    }

    public void setRealLineName(String realLineName) {
        this.realLineName = realLineName;
    }

    public String getPreplotName() {
        return preplotName;
    }

    public void setPreplotName(String preplotName) {
        this.preplotName = preplotName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
   
   
   
}
