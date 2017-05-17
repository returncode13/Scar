/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private String insightVersion;
    private BooleanProperty alert= new SimpleBooleanProperty(Boolean.FALSE);
    private Boolean modified;
    private Boolean deleted;
    private Long numberOfRuns;
    private StringProperty errorMessage=new SimpleStringProperty();
   // private Long workflowVersion=6L;
   //private Boolean insightFlag=Boolean.FALSE;
    //private String dependencyStatus=new String("Good");
    //private String runStatus=new String("Great!");
    //private String qcStatus=new String("Amazing");
    private final StringProperty run = new SimpleStringProperty(this,"run");
    private final StringProperty dependency = new SimpleStringProperty(this,"dependency");
    private final BooleanProperty insightFlag = new SimpleBooleanProperty(this,"insightFlag");
    private final LongProperty workflowVersion = new SimpleLongProperty(this,"workflowVersion");
    private final StringProperty qcStatus = new SimpleStringProperty(this,"qcStatus");

    public String getQcStatus() {
        return qcStatus.get();
    }

    public void setQcStatus(String value) {
        qcStatus.set(value);
    }

    public StringProperty qcStatusProperty() {
        return qcStatus;
    }
    

    public long getWorkflowVersion() {
        return workflowVersion.get();
    }

    public void setWorkflowVersion(long value) {
        workflowVersion.set(value);
    }

    public LongProperty workflowVersionProperty() {
        return workflowVersion;
    }
    

    public boolean isInsightFlag() {
        return insightFlag.get();
    }

    public void setInsightFlag(boolean value) {
        insightFlag.set(value);
    }

    public BooleanProperty insightFlagProperty() {
        return insightFlag;
    }
    
    public String getDependency() {
        return dependency.get();
    }

    public void setDependency(String value) {
        dependency.set(value);
    }

    public StringProperty dependencyProperty() {
        return dependency;
    }
    
    public String getRun() {
        return run.get();
    }

    public void setRun(String value) {
        run.set(value);
    }

    public StringProperty runProperty() {
        return run;
    }
    
    
    public ArrayList<SubSurface> getSubsurfaces() {
        return subsurfaces;
    }

    public void setSubsurfaces(ArrayList<SubSurface> subsurfaces) {
        this.subsurfaces = subsurfaces;
        this.sequenceNumber=Collections.min(subsurfaces, (SubSurface o1, SubSurface o2) -> {
            return o1.getSequenceNumber().compareTo(o2.getSequenceNumber());
        }).getSequenceNumber();
        
        
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

    public String getInsightVersion() {
        return insightVersion;
    }

    public void setInsightVersion(String insightVersion) {
        this.insightVersion = insightVersion;
    }

    public Boolean getAlert() {
        return alert.get();
    }

    public void setAlert(Boolean alert) {
        this.alert.set(alert);
    }

    public Boolean getModified() {
        return modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    
    public Long getNumberOfRuns() {
        return numberOfRuns;
    }

    public void setNumberOfRuns(Long numberOfRuns) {
        this.numberOfRuns=numberOfRuns;
    }
   

   
    public String getErrorMessage() {
        return errorMessage.get();
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.set(errorMessage);
    }
    /*
    public Long getWorkflowVersion() {
    return workflowVersion;
    }
    
    public void setWorkflowVersion(Long workflowVersion) {
    this.workflowVersion = workflowVersion;
    }
    
    public Boolean getInsightFlag() {
    return insightFlag;
    }
    
    public void setInsightFlag(Boolean insightFlag) {
    this.insightFlag = insightFlag;
    }
    
    public String getDependencyStatus() {
    return dependencyStatus;
    }
    
    public void setDependencyStatus(String dependencyStatus) {
    this.dependencyStatus = dependencyStatus;
    }
    
    public String getRunStatus() {
    return runStatus;
    }
    
    public void setRunStatus(String runStatus) {
    this.runStatus = runStatus;
    }
    
    public String getQcStatus() {
        return qcStatus;
    }
    
    public void setQcStatus(String qcStatus) {
        this.qcStatus = qcStatus;
    }
    */

    public boolean getInsightFlag() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
