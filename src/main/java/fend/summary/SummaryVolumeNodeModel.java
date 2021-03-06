/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

//import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import watcher.SummaryStatusWatcher;

/**
 *
 * @author sharath nair
 * sharath.nair@polarcus.com
 */
public class SummaryVolumeNodeModel {
   
    static int count=0;
    //private VolumeSelectionModelType1 volumeSelectionModel;
    private VolumeSelectionModelType0 volumeSelectionModel;
   
    private final StringProperty run = new SimpleStringProperty(this,"run");
    private final StringProperty dep = new SimpleStringProperty(this,"dep");
    //private final BooleanProperty ins = new SimpleBooleanProperty(this,"ins");
    
    private final StringProperty qcflag = new SimpleStringProperty(this,"qcflag");
    private final LongProperty wfversion = new SimpleLongProperty(this,"wfversion");
    private final BooleanProperty ins = new SimpleBooleanProperty();
    private SummaryStatusWatcher summaryStatusWatcher;
    
    
    private  StringProperty insightString=new SimpleStringProperty();
    
    public StringProperty insightStringProperty(){
        return insightString;
    }
    
    public void setInsightString(String insightVersion){
        this.insightString.set(insightVersion);
    }
    
    public String getInsightString(){
        return insightString.get();
    }

    public Boolean getIns() {
        return ins.get();
    }

    public void setIns(Boolean value) {
        System.out.println("fend.summary.SummaryVolumeNodeModel.setIns(): setting InsightProp to: "+value);
        ins.set(value);
    }

    public BooleanProperty insProperty() {
        return ins;
    }

    
    
    
    public String getRun() {
        return run.get();
    }

    /* public void setRun(String value) {
    run.set(value);
    }*/

    public StringProperty runProperty() {
        return run;
    }
        

    public String getDep() {
        return dep.get();
    }

    /*public void setDep(String value) {
    dep.set(value);
    }*/

    public StringProperty dependencyProperty() {
        return dep;
    }
   
    

    
    

    public String getQcflag() {
        return qcflag.get();
    }

    /*public void setQcflag(String value) {
    qcflag.set(value);
    }*/

    public StringProperty qcflagProperty() {
        return qcflag;
    }
    

    public long getWfversion() {
        return wfversion.get();
    }

    /*  public void setWfversion(long value) {
    wfversion.set(value);
    }*/

    public LongProperty wfversionProperty() {
        return wfversion;
    }
   
    
    /*  public boolean isIns() {
    return ins.get();
    }
    
    /*public void setIns(boolean value) {
    ins.set(value);
    }*/
    
    /*
    public BooleanProperty insProperty() {
        return ins;
    }*/
    
    
  

    /*public VolumeSelectionModelType1 getVolumeSelectionModel() {
    return volumeSelectionModel;
    }*/
    
    public VolumeSelectionModelType0 getVolumeSelectionModel() {
        return volumeSelectionModel;
    }

    /*public void setVolumeSelectionModel(VolumeSelectionModelType1 volumeSelectionModel, int depth,int jobind,int volin) {
    this.volumeSelectionModel = volumeSelectionModel;
    //  this.qcflag.set(new Boolean(this.volumeSelectionModel.getQcFlagProperty().get()).toString()+" :d: "+depth+" :j: "+jobind+" :v: "+volin+" "+this.volumeSelectionModel.getLabel());
    if(summaryStatusWatcher==null){
    System.out.println("fend.summary.SummaryVolumeNodeModel.setVolumeSelectionModel(): Starting summaryStatusWatcher : "+count);
    count++;
    summaryStatusWatcher=new SummaryStatusWatcher(volumeSelectionModel);
    }
    }*/
    
    public void setVolumeSelectionModel(VolumeSelectionModelType0 volumeSelectionModel, int depth,int jobind,int volin) {
        this.volumeSelectionModel = volumeSelectionModel;
      //  this.qcflag.set(new Boolean(this.volumeSelectionModel.getQcFlagProperty().get()).toString()+" :d: "+depth+" :j: "+jobind+" :v: "+volin+" "+this.volumeSelectionModel.getLabel());
      if(summaryStatusWatcher==null){
          System.out.println("fend.summary.SummaryVolumeNodeModel.setVolumeSelectionModel(): Starting summaryStatusWatcher : "+count);
          count++;
          summaryStatusWatcher=new SummaryStatusWatcher(this.volumeSelectionModel);
      }
    }
    
    
    
    
    
    public SummaryVolumeNodeModel() {
        
    }
    
    
}
