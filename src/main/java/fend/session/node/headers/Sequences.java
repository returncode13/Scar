/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author naila0152
 */
public class Sequences implements Serializable{
    ArrayList<SubSurface> subsurfaces=new ArrayList<>();
    private Long sequenceNumber;   
    private String subsurface;
    private String timeStamp;
    private Long traceCount;
    private Long inlineMax;
    private Long inlineMin;
    private Long inlineInc;
    private Long xlineMax;
    private Long xlineMin;
    private Long xlineInc;
    private Long dugShotMax;
    private Long dugShotMin;
    private Long dugShotInc;
    private Long dugChannelMax;
    private Long dugChannelMin;
    private Long dugChannelInc;
    private Long offsetMax;
    private Long offsetMin;
    private Long offsetInc;
    private Long cmpMax;
    private Long cmpMin;
    private Long cmpInc;

    public ArrayList<SubSurface> getSubsurfaces() {
        return subsurfaces;
    }

    public void setSubsurfaces(ArrayList<SubSurface> subsurfaces) {
        this.subsurfaces = subsurfaces;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(String subsurface) {
        this.subsurface = subsurface;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getTraceCount() {
        return traceCount;
    }

    public void setTraceCount(Long traceCount) {
        this.traceCount = traceCount;
    }

    public Long getInlineMax() {
        return inlineMax;
    }

    public void setInlineMax(Long inlineMax) {
        this.inlineMax = inlineMax;
    }

    public Long getInlineMin() {
        return inlineMin;
    }

    public void setInlineMin(Long inlineMin) {
        this.inlineMin = inlineMin;
    }

    public Long getInlineInc() {
        return inlineInc;
    }

    public void setInlineInc(Long inlineInc) {
        this.inlineInc = inlineInc;
    }

    public Long getXlineMax() {
        return xlineMax;
    }

    public void setXlineMax(Long xlineMax) {
        this.xlineMax = xlineMax;
    }

    public Long getXlineMin() {
        return xlineMin;
    }

    public void setXlineMin(Long xlineMin) {
        this.xlineMin = xlineMin;
    }

    public Long getXlineInc() {
        return xlineInc;
    }

    public void setXlineInc(Long xlineInc) {
        this.xlineInc = xlineInc;
    }

    public Long getDugShotMax() {
        return dugShotMax;
    }

    public void setDugShotMax(Long dugShotMax) {
        this.dugShotMax = dugShotMax;
    }

    public Long getDugShotMin() {
        return dugShotMin;
    }

    public void setDugShotMin(Long dugShotMin) {
        this.dugShotMin = dugShotMin;
    }

    public Long getDugShotInc() {
        return dugShotInc;
    }

    public void setDugShotInc(Long dugShotInc) {
        this.dugShotInc = dugShotInc;
    }

    public Long getDugChannelMax() {
        return dugChannelMax;
    }

    public void setDugChannelMax(Long dugChannelMax) {
        this.dugChannelMax = dugChannelMax;
    }

    public Long getDugChannelMin() {
        return dugChannelMin;
    }

    public void setDugChannelMin(Long dugChannelMin) {
        this.dugChannelMin = dugChannelMin;
    }

    public Long getDugChannelInc() {
        return dugChannelInc;
    }

    public void setDugChannelInc(Long dugChannelInc) {
        this.dugChannelInc = dugChannelInc;
    }

    public Long getOffsetMax() {
        return offsetMax;
    }

    public void setOffsetMax(Long offsetMax) {
        this.offsetMax = offsetMax;
    }

    public Long getOffsetMin() {
        return offsetMin;
    }

    public void setOffsetMin(Long offsetMin) {
        this.offsetMin = offsetMin;
    }

    public Long getOffsetInc() {
        return offsetInc;
    }

    public void setOffsetInc(Long offsetInc) {
        this.offsetInc = offsetInc;
    }

    public Long getCmpMax() {
        return cmpMax;
    }

    public void setCmpMax(Long cmpMax) {
        this.cmpMax = cmpMax;
    }

    public Long getCmpMin() {
        return cmpMin;
    }

    public void setCmpMin(Long cmpMin) {
        this.cmpMin = cmpMin;
    }

    public Long getCmpInc() {
        return cmpInc;
    }

    public void setCmpInc(Long cmpInc) {
        this.cmpInc = cmpInc;
    }

   

   
    
    
    
    
    
}
