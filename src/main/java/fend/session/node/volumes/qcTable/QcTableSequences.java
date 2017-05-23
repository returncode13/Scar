/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcTable;

import fend.session.node.headers.Sequences;
import fend.session.node.headers.SubSurface;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableSequences {
    
    private final StringProperty subsurface = new SimpleStringProperty();
    private final LongProperty sequenceNumber = new SimpleLongProperty();
    Sequences sequence;
    List<QcTypeModel> qcfields=new ArrayList();
    List<QcTableSubsurfaces> qcSubs=new ArrayList<>();
    
    
    
    
    
    
    

    public Sequences getSequence() {
        return sequence;
    }

    public void setSequence(Sequences sequence) {
        this.sequence = sequence;
        sequenceNumber.set(this.sequence.getSequenceNumber());
    }

    public List<QcTypeModel> getQcfields() {
        return qcfields;
    }

    public void setQcfields(List<QcTypeModel> qcfields) {
        this.qcfields = qcfields;
    }
    

    public String getSubsurface() {
        subsurface.setValue(sequence.getSubsurface());
        return subsurface.get();
    }

  

    public StringProperty subsurfaceProperty() {
        return subsurface;
    }
   

    public long getSequenceNumber() {
        sequenceNumber.set(sequence.getSequenceNumber());
        return sequenceNumber.get();
    }

   

    public LongProperty sequenceNumberProperty() {
        return sequenceNumber;
    }

    public List<QcTableSubsurfaces> getQcSubs() {
        
        for(SubSurface subs: sequence.getSubsurfaces()){
            QcTableSubsurfaces qsub=new QcTableSubsurfaces();
            qsub.setQcfields(qcfields);
            qsub.setSub(subs);              //sequence and subsurface information is set within class
            qcSubs.add(qsub);
        }
        
        return qcSubs;
    }
    
    
    
    
    
    
}
