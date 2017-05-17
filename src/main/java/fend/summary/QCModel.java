/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import fend.session.node.headers.Sequences;
import impl.org.controlsfx.i18n.SimpleLocalizedStringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author adira0150
 */
public class QCModel {

    private final StringProperty run = new SimpleStringProperty(this,"run");
    private final StringProperty dep = new SimpleStringProperty(this,"dep");
    private final BooleanProperty ins = new SimpleBooleanProperty(this,"ins");
    private final StringProperty qcflag = new SimpleStringProperty(this,"qcflag");
    private final LongProperty wfversion = new SimpleLongProperty(this,"wfversion");

    public QCModel() {
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

    public StringProperty depProperty() {
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
   
    
    public boolean isIns() {
        return ins.get();
    }

    /*public void setIns(boolean value) {
    ins.set(value);
    }*/
    
    public BooleanProperty insProperty() {
        return ins;
    }
    
    private Sequences sequence;

    public Sequences getSequence() {
        return sequence;
    }

    public void setSequence(Sequences sequence) {
        this.sequence = sequence;
        this.run.set(this.sequence.getRunStatus());
        this.dep.set(this.sequence.getDependencyStatus());
        this.ins.set(this.sequence.getInsightFlag());
        this.wfversion.set(this.sequence.getWorkflowVersion());
        this.qcflag.set(this.sequence.getQcStatus());
        
        
    }
    
    
    
    
    
    
}
