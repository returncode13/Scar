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
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableSubsurfaces extends QcTableSequences{
    private final StringProperty subsurface = new SimpleStringProperty();
    private final LongProperty sequenceNumber = new SimpleLongProperty();
    SubSurfaceHeaders sub;
    SequenceHeaders sequence;
    List<QcTypeModel> qcfields=null;
    QcTableSequences qcTableSeq;
   // Map<QcTypeModel,BooleanProperty> qctypeMap=new HashMap<>();

    public QcTableSequences getQcTableSeq() {
        return qcTableSeq;
    }

    public void setQcTableSeq(QcTableSequences qcTableSeq) {
        this.qcTableSeq = qcTableSeq;
    }
    
    
    
    
    public SubSurfaceHeaders getSub() {
        System.out.println("fend.session.node.volumes.type1.qcTable.QcTableSubsurfaces.getSub(): seq: "+sub.getSequenceNumber()+" sub: "+sub.getSubsurface());
        return sub;
    }

    public void setSub(SubSurfaceHeaders sub) {
        this.sub = sub;
        sequenceNumber.setValue(sub.getSequenceNumber());
        subsurface.setValue(sub.getSubsurface());
        
        System.out.println("fend.session.node.volumes.type1.qcTable.QcTableSubsurfaces.setSub(): seq: "+sub.getSequenceNumber()+" sub: "+sub.getSubsurface());
        
    }
    
    @Override
    public void setSequence(SequenceHeaders seq){
        this.sequence=seq;
    }

    
    @Override
    public List<QcTypeModel> getQctypes() {
        return qcfields;
    }
    
    @Override
    public void setQctypes(List<QcTypeModel> qcfields) {
        this.qcfields = qcfields;
    }
    
    @Override
    public String getSubsurface() {
        subsurface.setValue(sub.getSubsurface());
        return subsurface.get();
    }

  
    @Override
    public StringProperty subsurfaceProperty() {
        return subsurface;
    }
   
    @Override
    public long getSequenceNumber() {
        sequenceNumber.set(sub.getSequenceNumber());
        return sequenceNumber.get();
    }

   
    @Override
    public LongProperty sequenceNumberProperty() {
        return sequenceNumber;
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
    
    
}
