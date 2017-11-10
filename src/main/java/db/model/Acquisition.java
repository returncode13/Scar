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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author sharath
 */

@Entity
@Table(name="acquisition",schema = "public")
public class Acquisition implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="seq_fk",nullable=false)
    private Sequence sequence;
    
    @ManyToOne
    @JoinColumn(name="sub_fk",nullable=false)
    private Subsurface subsurface;
   
    @Column(name="cable")
    private Long cable;
    
    @Column(name="firstChannel")
    private Long firstChannel;
    
    @Column(name="lastChannel")
    private Long lastChannel;
    
    @Column(name="gun")
    private Long gun;
    
    @Column(name="firstFFID")
    private Long firstFFID;
    
    @Column(name="lastFFID")
    private Long lastFFID;
    
    @Column(name="firstShot")
    private Long firstShot;
    
    @Column(name="lastShot")
    private Long lastShot;
    
    @Column(name="FGFFID")
    private Long firstGoodFFID;
    
    @Column(name="LGFFID")
    private Long lastGoodFFID;
    
    @Column(name="fgsp")
    private Long fgsp;
    
    @Column(name="lgsp")
    private Long lgsp;
    
    
    public Long getId() {
        return id;
    }
    
    
    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Subsurface getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
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

    public Long getFirstShot() {
        return firstShot;
    }

    public void setFirstShot(Long firstShot) {
        this.firstShot = firstShot;
    }

    public Long getLastShot() {
        return lastShot;
    }

    public void setLastShot(Long lastShot) {
        this.lastShot = lastShot;
    }

    public Long getFirstGoodFFID() {
        return firstGoodFFID;
    }

    public void setFirstGoodFFID(Long firstGoodFFID) {
        this.firstGoodFFID = firstGoodFFID;
    }

    public Long getLastGoodFFID() {
        return lastGoodFFID;
    }

    public void setLastGoodFFID(Long lastGoodFFID) {
        this.lastGoodFFID = lastGoodFFID;
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
    
    
    
    
    
    
    
}
