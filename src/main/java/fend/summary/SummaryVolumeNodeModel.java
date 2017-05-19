/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import fend.session.node.volumes.VolumeSelectionModel;
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
 * @author adira0150
 */
public class SummaryVolumeNodeModel {
   
    static int count=0;
    private VolumeSelectionModel volumeSelectionModel;
    /*private final ObjectProperty<QCModel> qcmodel = new SimpleObjectProperty<>(this,"qcmodel");
    
    public QCModel getQcmodel() {
    return qcmodel.get();
    }
    
    public void setQcmodel(QCModel value) {
    qcmodel.set(value);
    }
    
    public ObjectProperty qcmodelProperty() {
    return qcmodel;
    }*/
    
    private final StringProperty run = new SimpleStringProperty(this,"run");
    private final StringProperty dep = new SimpleStringProperty(this,"dep");
    //private final BooleanProperty ins = new SimpleBooleanProperty(this,"ins");
    
    private final StringProperty qcflag = new SimpleStringProperty(this,"qcflag");
    private final LongProperty wfversion = new SimpleLongProperty(this,"wfversion");
    private final StringProperty ins = new SimpleStringProperty("");
    private SummaryStatusWatcher summaryStatusWatcher;
    

    public String getIns() {
        return ins.get();
    }

    public void setIns(String value) {
        ins.set(value);
    }

    public StringProperty insProperty() {
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
    
    
    private List<QCModel> listOfQcModels=new ArrayList<>();
    
    public List<QCModel> getListOfQcModels() {
    return listOfQcModels;
    }
    
    public void setListOfQcModels(List<QCModel> listOfQcModels) {
    this.listOfQcModels = listOfQcModels;
    }
    

    public VolumeSelectionModel getVolumeSelectionModel() {
        return volumeSelectionModel;
    }

    public void setVolumeSelectionModel(VolumeSelectionModel volumeSelectionModel, int depth,int jobind,int volin) {
        this.volumeSelectionModel = volumeSelectionModel;
      //  this.qcflag.set(new Boolean(this.volumeSelectionModel.getQcFlagProperty().get()).toString()+" :d: "+depth+" :j: "+jobind+" :v: "+volin+" "+this.volumeSelectionModel.getLabel());
      if(summaryStatusWatcher==null){
          System.out.println("fend.summary.SummaryVolumeNodeModel.setVolumeSelectionModel(): Starting summaryStatusWatcher : "+count);
          count++;
          summaryStatusWatcher=new SummaryStatusWatcher(volumeSelectionModel);
      }
    }
    
    
    
    public SummaryVolumeNodeModel() {
        
    }
    
    
}
