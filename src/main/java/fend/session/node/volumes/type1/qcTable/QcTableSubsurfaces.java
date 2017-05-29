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
public class QcTableSubsurfaces extends QcTableSequences{
    private final StringProperty subsurface = new SimpleStringProperty();
    private final LongProperty sequenceNumber = new SimpleLongProperty();
    SubSurfaceHeaders sub;
    List<QcTypeModel> qcfields=new ArrayList();

    public SubSurfaceHeaders getSub() {
        return sub;
    }

    public void setSub(SubSurfaceHeaders sub) {
        this.sub = sub;
        sequenceNumber.setValue(sub.getSequenceNumber());
        subsurface.setValue(sub.getSubsurface());
        
    }

    
    @Override
    public List<QcTypeModel> getQcfields() {
        return qcfields;
    }
    
    @Override
    public void setQcfields(List<QcTypeModel> qcfields) {
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
}
