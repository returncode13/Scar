/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.acquisition;

import core.Seq;
import core.Sub;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AcqHeaders {
    private Seq seq;
    private Long cable;
    private Long firstChannel;
    private Long lastChannel;
    private Long gun;
    private Long firstFFID;
    private Long lastFFID;
    private Long firstShot;
    private Long lastShot;
    private Long firstGoodFFID;
    private Long lastGoodFFID;
    private Long fgsp;
    private Long lgsp;
    private Sub sub;

    public Sub getSub() {
        return sub;
    }

    public void setSub(Sub sub) {
        this.sub = sub;
    }

    
    
    public Seq getSeq() {
        return seq;
    }

    public void setSeq(Seq seq) {
        this.seq = seq;
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
