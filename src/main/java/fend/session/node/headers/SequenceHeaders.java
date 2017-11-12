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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mid.doubt.Doubt;

/**
 *
 * @author sharath nair
 * sharath.nair@polarcus.com
 */
public class SequenceHeaders implements Serializable{

    private Doubt doubt=new Doubt();
    
    ArrayList<SubSurfaceHeaders> subsurfaces=new ArrayList<>();
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
    //private StringProperty insightVersion=new SimpleStringProperty();
    private BooleanProperty qcalert= new SimpleBooleanProperty(Boolean.FALSE);
    
    private Boolean modified;
    private Boolean deleted;
    private Long numberOfRuns;
    private StringProperty errorMessage=new SimpleStringProperty();
   // private Long workflowVersion=6L;
   //private Boolean insightFlag=Boolean.FALSE;
    
    private final StringProperty run = new SimpleStringProperty(this,"run");
   // private final StringProperty dependency = new SimpleStringProperty(this,"dependency");
    private final BooleanProperty insightFlag = new SimpleBooleanProperty(this,"insightFlag");
    private final StringProperty workflowSeqProperty = new SimpleStringProperty();

    
    private final StringProperty qcStatus = new SimpleStringProperty(this,"qcStatus");
   
    
    public String getQcStatus() {
        return qcStatus.get();
    }

    public void setQcStatus(String value) {
        qcStatus.set(value);
    }

    public StringProperty qcStatusProperty() {
        this.isPassQC();
        if(this.passQC){
            qcStatus.set("OK");
        }else{
            qcStatus.set("QC");
        }
        return qcStatus;
    }
    
     private boolean passQC =true;

    public boolean isPassQC() {
        this.passQC=true;
        for (Iterator<SubSurfaceHeaders> iterator = subsurfaces.iterator(); iterator.hasNext();) {
            SubSurfaceHeaders next = iterator.next();
            this.passQC=this.passQC && next.isPassQC();
            
        }
         System.out.println("fend.session.node.headers.SequenceHeaders.setPassQC(): seq: "+this.sequenceNumber+" QCSTATUS: "+this.passQC);
        return passQC;
    }
    
    /* public void setPassQC(boolean passQC) {
    this.passQC = this.passQC && passQC;
    
    }
    
    public void resetPassQC(){
    this.passQC=true;
    }*/
     
    private final BooleanProperty pendingalert = new SimpleBooleanProperty(Boolean.FALSE);
    private Set<Long> wfversionSet=new HashSet<>();                                                         //use this set to check if there are more than one versions of the workflow present in the seq
    
    private final BooleanProperty passedQC = new SimpleBooleanProperty();
    
    private final BooleanProperty dependency = new SimpleBooleanProperty(Boolean.TRUE);
    
    private Set<String> insightSet=new HashSet<>();
    
    /*private List<String> errorMessageList=new ArrayList<>();
    
    public List<String> getErrorMessageList() {
    return errorMessageList;
    }
    
    public void setErrorMessageList(List<String> errorMessageList) {
    this.errorMessageList = errorMessageList;
    }*/
    
    

    public boolean isDependency() {
        return dependency.get();
    }

    public void setDependency(boolean value) {
        dependency.set(value);
    }

    public BooleanProperty dependencyProperty() {
        return dependency;
    }

    public Doubt getDoubt() {
        return doubt;
    }

    public void setDoubt(Doubt doubt) {
        this.doubt = doubt;
    }
    private final StringProperty insightVersion = new SimpleStringProperty(this,"insightVersion");

    public String getInsightVersion() {
        if(insightSet.size()>1){
            insightVersion.set(new String(">1"));
        }else{
            insightVersion.set(new ArrayList<String>(insightSet).get(0));
        }
        
        return insightVersion.get();
    }

    public void setInsightVersion(String value) {
        insightVersion.set(value);
    }

    public StringProperty insightVersionProperty() {
        if(insightSet.size()>1){
            insightVersion.set(new String(">1"));
        }else{
            insightVersion.set(new ArrayList<String>(insightSet).get(0));
        }
        return insightVersion;
    }
    
    
    
    
   
    
    
    

    public boolean isPassedQC() {
        return passedQC.get();
    }

    public void setPassedQC(boolean value) {
        passedQC.set(value);
    }

    public BooleanProperty passedQCProperty() {
        return passedQC;
    }
    
    
    
    
    
    
    
    
    
    
    public void addTowfVersionSet(Long ver){
        wfversionSet.add(ver);
        if(wfversionSet.size()>1){
            setWorkflowSeqProperty(">1");
        }else{
            List<Long> ll=new ArrayList<>(wfversionSet);
            setWorkflowSeqProperty("v"+Long.valueOf(ll.get(0)));
        }
    }
    
   
    public String getWorkflowSeqProperty() {
        
        return workflowSeqProperty.get();
    }

    public void setWorkflowSeqProperty(String value) {
        workflowSeqProperty.set(value);
    }

    public StringProperty workflowSeqPropertyProperty() {
        return workflowSeqProperty;
    }
    
    
    
        
   
    
    
    
    
        
    
    public boolean isPendingalert() {
        return pendingalert.get();
    }

    public void setPendingalert(boolean value) {
        pendingalert.set(value);
    }

    public BooleanProperty pendingalertProperty() {
        return pendingalert;
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
    
    /* public String getDependency() {
    return dependency.get();
    }
    
    public void setDependency(String value) {
    dependency.set(value);
    }
    
    public StringProperty dependencyProperty() {
    return dependency;
    }W*/
    
    public String getRun() {
        return run.get();
    }

    public void setRun(String value) {
        run.set(value);
    }

    public StringProperty runProperty() {
        return run;
    }
    
    
    public ArrayList<SubSurfaceHeaders> getSubsurfaces() {
        return subsurfaces;
    }

    public void setSubsurfaces(ArrayList<SubSurfaceHeaders> subsurfaces) {
        
        this.subsurfaces = subsurfaces;
        for (Iterator<SubSurfaceHeaders> iterator = this.subsurfaces.iterator(); iterator.hasNext();) {
            fend.session.node.headers.SubSurfaceHeaders next = iterator.next();
            next.setSequenceHeader(this);
            this.addTowfVersionSet(next.getWorkflowVersion());
            //this.
        }
        this.sequenceNumber=Collections.min(subsurfaces, (SubSurfaceHeaders o1, SubSurfaceHeaders o2) -> {
            return o1.getSequenceNumber().compareTo(o2.getSequenceNumber());
        }).getSequenceNumber();
        
        
        for (Iterator<SubSurfaceHeaders> iterator = subsurfaces.iterator(); iterator.hasNext();) {
            SubSurfaceHeaders next = iterator.next();
            this.insightSet.add(next.getInsightVersion());
            
        }
        
        /* String ins1=Collections.min(subsurfaces,(SubSurfaceHeaders o1,SubSurfaceHeaders o2)->{
        return o1.getInsightVersion().compareTo(o2.getInsightVersion());
        }).getInsightVersion();
        
        String ins2=Collections.max(subsurfaces,(SubSurfaceHeaders o1,SubSurfaceHeaders o2)->{
        return o1.getInsightVersion().compareTo(o2.getInsightVersion());
        }).getInsightVersion();
        
        if(ins1.equals(ins2)){
        this.insightVersion.set(ins1);
        }else{
        this.insightVersion.set(new String(">1"));
        }*/
        
        
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

    /*public String getInsightVersion() {
    return insightVersion;
    }
    
    public void setInsightVersion(String insightVersion) {
    this.insightVersion = insightVersion;
    }*/

    /* public Boolean getQcAlert() {
    return qcalert.get();
    }
    
    public void setQcAlert(Boolean alert) {
    this.qcalert.set(alert);
    }*/

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
    

    public boolean getInsightFlag() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
