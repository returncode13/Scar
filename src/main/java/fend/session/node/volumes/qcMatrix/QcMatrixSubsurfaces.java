/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcMatrix;

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
public class QcMatrixSubsurfaces extends QcMatrixSequences{
    private final StringProperty subsurface = new SimpleStringProperty();
    private final LongProperty sequenceNumber = new SimpleLongProperty();
    SubSurface sub;
    List<String> qcfields=new ArrayList();

    public SubSurface getSub() {
        return sub;
    }

    public void setSub(SubSurface sub) {
        this.sub = sub;
        sequenceNumber.set(sub.getSequenceNumber());
        subsurface.setValue(sub.getSubsurface());
        
    }

    

    public List<String> getQcfields() {
        return qcfields;
    }

    public void setQcfields(List<String> qcfields) {
        this.qcfields = qcfields;
    }
    

    public String getSubsurface() {
        subsurface.setValue(sub.getSubsurface());
        return subsurface.get();
    }

  

    public StringProperty subsurfaceProperty() {
        return subsurface;
    }
   

    public long getSequenceNumber() {
        sequenceNumber.set(sub.getSequenceNumber());
        return sequenceNumber.get();
    }

   

    public LongProperty sequenceNumberProperty() {
        return sequenceNumber;
    }
}
