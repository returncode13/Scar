/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private   StringProperty insightVersion=new SimpleStringProperty("to be implemented");             //get from notes.txt
            
    private   BooleanProperty alert=new SimpleBooleanProperty();
    private   BooleanProperty modified=new SimpleBooleanProperty(Boolean.FALSE);
    private   BooleanProperty deleted=new SimpleBooleanProperty(Boolean.FALSE);
    private   LongProperty version=new SimpleLongProperty(0L);
    
    
            
    
    
    
    
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

    public Boolean getAlert() {
        return alert.get();
    }

    public void setAlert(Boolean alert) {
        this.alert=new SimpleBooleanProperty(alert);
    }

    
    /*public String getInsightVersion() {
    return insightVersion.get();
    }
    
    public void setInsightVersion(String insightVersion) {
    this.insightVersion = new SimpleStringProperty(insightVersion);
    }*/

    public Boolean getModified() {
        return modified.get();
    }

    public void setModified(Boolean modified) {
        this.modified.set(modified);
    }

    public Boolean getDeleted() {
        return deleted.get();
    }

    public void setDeleted(Boolean deleted) {
        this.deleted.set(deleted);
    }

    public Long getVersion() {
        return version.get();
    }

    public void setVersion(Long version) {
        this.version.set(version);
    }

    
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.sequenceNumber.get());
        hash = 37 * hash + Objects.hashCode(this.subsurface.get());
        /*hash = 37 * hash + Objects.hashCode(this.timeStamp);
        hash = 37 * hash + Objects.hashCode(this.traceCount);
        hash = 37 * hash + Objects.hashCode(this.inlineMax);
        hash = 37 * hash + Objects.hashCode(this.inlineMin);
        hash = 37 * hash + Objects.hashCode(this.inlineInc);
        hash = 37 * hash + Objects.hashCode(this.xlineMax);
        hash = 37 * hash + Objects.hashCode(this.xlineMin);
        hash = 37 * hash + Objects.hashCode(this.xlineInc);
        hash = 37 * hash + Objects.hashCode(this.dugShotMax);
        hash = 37 * hash + Objects.hashCode(this.dugShotMin);
        hash = 37 * hash + Objects.hashCode(this.dugShotInc);
        hash = 37 * hash + Objects.hashCode(this.dugChannelMax);
        hash = 37 * hash + Objects.hashCode(this.dugChannelMin);
        hash = 37 * hash + Objects.hashCode(this.dugChannelInc);
        hash = 37 * hash + Objects.hashCode(this.offsetMax);
        hash = 37 * hash + Objects.hashCode(this.offsetMin);
        hash = 37 * hash + Objects.hashCode(this.offsetInc);
        hash = 37 * hash + Objects.hashCode(this.cmpMax);
        hash = 37 * hash + Objects.hashCode(this.cmpMin);
        hash = 37 * hash + Objects.hashCode(this.cmpInc);
        hash = 37 * hash + Objects.hashCode(this.id);*/
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
        final SubSurface other = (SubSurface) obj;
        if (!Objects.equals(this.sequenceNumber.get(), other.sequenceNumber.get())) {
            return false;
        }
        if (!Objects.equals(this.subsurface.get(), other.subsurface.get())) {
        return false;
        }
        /*if (!Objects.equals(this.timeStamp, other.timeStamp)) {
        return false;
        }
        if (!Objects.equals(this.traceCount, other.traceCount)) {
        return false;
        }
        if (!Objects.equals(this.inlineMax, other.inlineMax)) {
        return false;
        }
        if (!Objects.equals(this.inlineMin, other.inlineMin)) {
        return false;
        }
        if (!Objects.equals(this.inlineInc, other.inlineInc)) {
        return false;
        }
        if (!Objects.equals(this.xlineMax, other.xlineMax)) {
        return false;
        }
        if (!Objects.equals(this.xlineMin, other.xlineMin)) {
        return false;
        }
        if (!Objects.equals(this.xlineInc, other.xlineInc)) {
        return false;
        }
        if (!Objects.equals(this.dugShotMax, other.dugShotMax)) {
        return false;
        }
        if (!Objects.equals(this.dugShotMin, other.dugShotMin)) {
        return false;
        }
        if (!Objects.equals(this.dugShotInc, other.dugShotInc)) {
        return false;
        }
        if (!Objects.equals(this.dugChannelMax, other.dugChannelMax)) {
        return false;
        }
        if (!Objects.equals(this.dugChannelMin, other.dugChannelMin)) {
        return false;
        }
        if (!Objects.equals(this.dugChannelInc, other.dugChannelInc)) {
        return false;
        }
        if (!Objects.equals(this.offsetMax, other.offsetMax)) {
        return false;
        }
        if (!Objects.equals(this.offsetMin, other.offsetMin)) {
        return false;
        }
        if (!Objects.equals(this.offsetInc, other.offsetInc)) {
        return false;
        }
        if (!Objects.equals(this.cmpMax, other.cmpMax)) {
        return false;
        }
        if (!Objects.equals(this.cmpMin, other.cmpMin)) {
        return false;
        }
        if (!Objects.equals(this.cmpInc, other.cmpInc)) {
        return false;
        }
        if (!Objects.equals(this.id, other.id)) {
        return false;
        }*/
        return true;
    }

   
   
     
    
    
    
}
