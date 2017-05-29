/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type1.qcTable;

import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
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
    SequenceHeaders sequence;
    List<QcTypeModel> qcfields=new ArrayList();
    List<QcTableSubsurfaces> qcSubs=new ArrayList<>();
    
    
    
    
    
    
    

    public SequenceHeaders getSequence() {
        return sequence;
    }

    public void setSequence(SequenceHeaders sequence) {
        this.sequence = sequence;
        sequenceNumber.set(this.sequence.getSequenceNumber());
        subsurface.set(this.sequence.getSubsurface());
        for(SubSurfaceHeaders subs: sequence.getSubsurfaces()){
            QcTableSubsurfaces qsub=new QcTableSubsurfaces();
            qsub.setQcfields(qcfields);
            qsub.setSub(subs);              //sequence and subsurface information is set within class
            qcSubs.add(qsub);
        }
    }
    
    

    public List<QcTypeModel> getQcfields() {
      //  System.out.println("fend.session.node.volumes.qcTable.QcTableSequences.getQcfields(): size: "+qcfields.size());
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
          
        
        /* for(SubSurfaceHeaders subs: sequence.getSubsurfaces()){
        QcTableSubsurfaces qsub=new QcTableSubsurfaces();
        qsub.setQcfields(qcfields);
        qsub.setSub(subs);              //sequence and subsurface information is set within class
        qcSubs.add(qsub);
        }*/
        
        return qcSubs;
    }
    
    
    
    
    
    
}
