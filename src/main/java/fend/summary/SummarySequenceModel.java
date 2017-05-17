/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import fend.session.node.headers.Sequences;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author adira0150
 */
public class SummarySequenceModel {
    
    
    /* private final StringProperty run = new SimpleStringProperty(this,"run");
    private final StringProperty dep = new SimpleStringProperty(this,"dep");
    private final BooleanProperty ins = new SimpleBooleanProperty(this,"ins");
    private final StringProperty qcflag = new SimpleStringProperty(this,"qcflag");
    private final LongProperty wfversion = new SimpleLongProperty(this,"wfversion");
    
    
    
    
    public String getRun() {
    return run.get();
    }*/

    /* public void setRun(String value) {
    run.set(value);
    }*/
    /*
    public StringProperty runProperty() {
    return run;
    }
    
    
    public String getDep() {
    return dep.get();
    }*/

    /*public void setDep(String value) {
    dep.set(value);
    }*/
    /*
    public StringProperty depProperty() {
    return dep;
    }
    
    
    
    
    
    
    public String getQcflag() {
    return qcflag.get();
    }*/

    /*public void setQcflag(String value) {
    qcflag.set(value);
    }*/

    /*public StringProperty qcflagProperty() {
    return qcflag;
    }*/
    
    /*
    public long getWfversion() {
    return wfversion.get();
    }*/

    /*  public void setWfversion(long value) {
    wfversion.set(value);
    }*/

    /*public LongProperty wfversionProperty() {
    return wfversion;
    }*/
   
    /*
    public boolean isIns() {
    return ins.get();
    }*/
    /*public void setIns(boolean value) {
    ins.set(value);
    }*/
    
    /*public BooleanProperty insProperty() {
    return ins;
    }*/
    
    private Sequences sequence;

    public Sequences getSequence() {
        return sequence;
    }

    public void setSequence(Sequences sequence) {
        this.sequence = sequence;
        /*  this.run.set(this.sequence.getRun());
        this.dep.set(this.sequence.getDependency());
        this.ins.set(this.sequence.isInsightFlag());
        this.wfversion.set(this.sequence.getWorkflowVersion());
        this.qcflag.set(this.sequence.getQcStatus());*/
        this.seq.set(this.sequence.getSequenceNumber());
        
    }
    private final LongProperty seq = new SimpleLongProperty(this,"seq");

    public long getSeq() {
        return seq.get();
    }

    public void setSeq(long value) {
        seq.set(value);
    }

    public LongProperty seqProperty() {
        return seq;
    }
    
    
    
    
    

    
    private final StringProperty linename = new SimpleStringProperty(this,"linename");

    public String getLinename() {
        return linename.get();
    }

    public void setLinename(String value) {
        linename.set(value);
    }

    public StringProperty linenameProperty() {
        return linename;
    }
    private final ObjectProperty<DepthListModel> depthlist = new SimpleObjectProperty<>(this,"depthlist");

    public DepthListModel getDepthlist() {
        return depthlist.get();
    }

    public void setDepthlist(DepthListModel value) {
        depthlist.set(value);
    }

    public ObjectProperty depthlistProperty() {
        return depthlist;
    }

    public SummarySequenceModel() {
    }
    
    
    
}
