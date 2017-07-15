/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type1.qcTable;

import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableSequences {
    
    private final StringProperty subsurface = new SimpleStringProperty();
    private final LongProperty sequenceNumber = new SimpleLongProperty();
    SequenceHeaders sequence;
    List<QcTypeModel> qctypes=null;
    List<QcTableSubsurfaces> qcSubs=new ArrayList<>();
    
     Map<QcTypeModel,BooleanProperty> qctypeMap=new HashMap<>();
     
     public SequenceHeaders getSequence() {
         //System.out.println("fend.session.node.volumes.type1.qcTable.QcTableSequences.getSequence(): "+sequence.getSequenceNumber());
        return sequence;
    }

    public void setSequence(SequenceHeaders sequence) {
        this.sequence = sequence;
        sequenceNumber.set(this.sequence.getSequenceNumber());
        subsurface.set(this.sequence.getSubsurface());
        System.out.println("fend.session.node.volumes.type1.qcTable.QcTableSequences.setSequence(): added seq: "+sequenceNumber.get()+" and sub: "+subsurface.get());
        
        /* Map<QcTypeModel,BooleanProperty> fmap=new HashMap<>();
        for (Iterator<QcTypeModel> iteratorq = qctypes.iterator(); iteratorq.hasNext();) {
        QcTypeModel nextq = iteratorq.next();
        PassBP p=new PassBP();
        fmap.put(nextq,p.passProperty());
        }*/
       
        
        
       
    }
    
    

    public List<QcTypeModel> getQctypes() {
      //  System.out.println("fend.session.node.volumes.qcTable.QcTableSequences.getQcfields(): size: "+qctypes.size());
        return qctypes;
    }

    public void setQctypes(List<QcTypeModel> qctypes) {
        this.qctypes = qctypes;
    }
    

    public String getSubsurface() {
        subsurface.setValue(sequence.getSubsurface());
        return subsurface.get();
    }

  

    public StringProperty subsurfaceProperty() {
        return subsurface;
    }
   

    public long getSequenceNumber() {
        System.out.println("fend.session.node.volumes.type1.qcTable.QcTableSequences.getSequenceNumber(): setting property to "+sequence.getSequenceNumber());
        sequenceNumber.set(sequence.getSequenceNumber());
        return sequenceNumber.get();
    }

   

    public LongProperty sequenceNumberProperty() {
        return sequenceNumber;
    }

    public List<QcTableSubsurfaces> getQcSubs() {
       return qcSubs;
    }
    
    
    
    
    
    public void addToQcTypeMap(QcTypeModel q,Boolean b){
        PassBP ps=new PassBP();
        qctypeMap.put(q, ps.passProperty());
    }
    
    public void clearQcTypeMap(){
        qctypeMap.clear();
    }

    public Map<QcTypeModel, BooleanProperty> getQctypeMap() {
        return qctypeMap;
    }

    public void setQctypeMap(Map<QcTypeModel, BooleanProperty> qctypeMap) {
        this.qctypeMap = qctypeMap;
    }

    void loadQcTypes() {
        
        for(SubSurfaceHeaders subs: sequence.getSubsurfaces()){
            QcTableSubsurfaces qsub=new QcTableSubsurfaces();
            
            //    System.out.println("fend.session.node.volumes.type1.qcTable.QcTableSequences.setSequence()  qsub.getQctypes(): returned null");
            List<QcTypeModel> qctypescopy=new ArrayList<>();
            for (Iterator<QcTypeModel> iterator1 = qctypes.iterator(); iterator1.hasNext();) {
                QcTypeModel next1 = iterator1.next();
                QcTypeModel n=new QcTypeModel();
                n.setId(next1.getId());
                n.setName(next1.getName());
                n.setPassQc(next1.isPassQc());
                qctypescopy.add(n);
               // qctypescopy.add(next1);
                
            }
            
            qsub.setQctypes(qctypescopy);
                 
            
         //   qsub.setQctypeMap(fmap);
            qsub.setQcTableSeq(this);
            qsub.setSequence(this.sequence);
            qsub.setSub(subs);              //sequence and subsurface information is set within class
            qcSubs.add(qsub);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
