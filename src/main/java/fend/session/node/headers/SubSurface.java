/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import java.io.Serializable;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author naila0152
 */
public class SubSurface extends Sequences implements Serializable{
    private   LongProperty sequenceNumber=new SimpleLongProperty();   
    private   StringProperty subsurface=new SimpleStringProperty();
    private   StringProperty timeStamp=new SimpleStringProperty();
    private   LongProperty traceCount=new SimpleLongProperty();
    private   LongProperty inlineMax=new SimpleLongProperty();
    private   LongProperty inlineMin=new SimpleLongProperty();
    private   LongProperty inlineInc=new SimpleLongProperty();
    private   LongProperty xlineMax=new SimpleLongProperty();
    private   LongProperty xlineMin=new SimpleLongProperty();
    private   LongProperty xlineInc= new SimpleLongProperty();
    private   LongProperty dugShotMax= new SimpleLongProperty();
    private   LongProperty dugShotMin=new SimpleLongProperty();
    private   LongProperty dugShotInc= new SimpleLongProperty();
    private   LongProperty dugChannelMax= new SimpleLongProperty();
    private   LongProperty dugChannelMin= new SimpleLongProperty();
    private   LongProperty dugChannelInc= new SimpleLongProperty();
    private   LongProperty offsetMax= new SimpleLongProperty();
    private   LongProperty offsetMin= new SimpleLongProperty();
    private   LongProperty offsetInc= new SimpleLongProperty();
    private   LongProperty cmpMax= new SimpleLongProperty();
    private   LongProperty cmpMin= new SimpleLongProperty();
    private   LongProperty cmpInc= new SimpleLongProperty();
    
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    public Long getSequenceNumber() {
        return sequenceNumber.get();
    }

    public String getSubsurface() {
        return subsurface.get();
    }

    public String getTimeStamp() {
        return timeStamp.get();
    }

    public Long getTraceCount() {
        return traceCount.get();
    }

    public Long getInlineMax() {
        return inlineMax.get();
    }

    public Long getInlineMin() {
        return inlineMin.get();
    }

    public Long getInlineInc() {
        return inlineInc.get();
    }

    public Long getXlineMax() {
        return xlineMax.get();
    }

    public Long getXlineMin() {
        return xlineMin.get();
    }

    public Long getXlineInc() {
        return xlineInc.get();
    }

    public Long getDugShotMax() {
        return dugShotMax.get();
    }

    public Long getDugShotMin() {
        return dugShotMin.get();
    }

    public Long getDugShotInc() {
        return dugShotInc.get();
    }

    public Long getDugChannelMax() {
        return dugChannelMax.get();
    }

    public Long getDugChannelMin() {
        return dugChannelMin.get();
    }

    public Long getDugChannelInc() {
        return dugChannelInc.get();
    }

    public Long getOffsetMax() {
        return offsetMax.get();
    }

    public Long getOffsetMin() {
        return offsetMin.get();
    }

    public Long getOffsetInc() {
        return offsetInc.get();
    }

    public Long getCmpMax() {
        return cmpMax.get();
    }

    public Long getCmpMin() {
        return cmpMin.get();
    }

    public Long getCmpInc() {
        return cmpInc.get();
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber.set(sequenceNumber);
    }

    public void setSubsurface(String subsurface) {
        this.subsurface.set(subsurface);
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp.set(timeStamp);
    }

    public void setTraceCount(Long traceCount) {
        this.traceCount.set(traceCount);
    }

    public void setInlineMax(Long inlineMax) {
        this.inlineMax.set(inlineMax);
    }

    public void setInlineMin(Long inlineMin) {
        this.inlineMin.set(inlineMin);
    }

    public void setInlineInc(Long inlineInc) {
        this.inlineInc.set(inlineInc);
    }

    public void setXlineMax(Long xlineMax) {
        this.xlineMax.set(xlineMax);
    }

    public void setXlineMin(Long xlineMin) {
        this.xlineMin.set(xlineMin);
    }

    public void setXlineInc(Long xlineInc) {
        this.xlineInc.set(xlineInc);
    }

    public void setDugShotMax(Long dugShotMax) {
        this.dugShotMax.set(dugShotMax);
    }

    public void setDugShotMin(Long dugShotMin) {
        this.dugShotMin.set(dugShotMin);
    }

    public void setDugShotInc(Long dugShotInc) {
        this.dugShotInc.set(dugShotInc);
    }

    public void setDugChannelMax(Long dugChannelMax) {
        this.dugChannelMax.set(dugChannelMax);
    }

    public void setDugChannelMin(Long dugChannelMin) {
        this.dugChannelMin.set(dugChannelMin);
    }

    public void setDugChannelInc(Long dugChannelInc) {
        this.dugChannelInc.set(dugChannelInc);
    }

    public void setOffsetMax(Long offsetMax) {
        this.offsetMax.set(offsetMax);
    }

    public void setOffsetMin(Long offsetMin) {
        this.offsetMin.set(offsetMin);
    }

    public void setOffsetInc(Long offsetInc) {
        this.offsetInc.set(offsetInc);
    }

    public void setCmpMax(Long cmpMax) {
        this.cmpMax.set(cmpMax);
    }

    public void setCmpMin(Long cmpMin) {
        this.cmpMin.set(cmpMin);
    }

    public void setCmpInc(Long cmpInc) {
        this.cmpInc.set(cmpInc);
    }

   
    
     
    
    
    
}
