/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import com.sun.org.apache.xpath.internal.axes.SubContextList;
import db.model.Headers;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
//import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair
 */
public class HeadersModel {
   
    private List<Sequences> seqList=new ArrayList<>();
    private ObservableList<Sequences> sequenceListInHeaders=FXCollections.observableList(seqList);
    private Long id;
   // private VolumeSelectionModelType1 volmodel;
    private VolumeSelectionModelType0 volmodel;

    public ObservableList<Sequences> getSequenceListInHeaders() {
        return sequenceListInHeaders;
    }

    public void setSequenceListInHeaders(ObservableList<Sequences> sequenceListInHeaders) {
        this.sequenceListInHeaders = FXCollections.observableList(sequenceListInHeaders);
    }

    /*public HeadersModel(VolumeSelectionModelType1 volmodel) {
    this.volmodel = volmodel;
    }*/
    public HeadersModel(VolumeSelectionModelType0 volmodel) {
        this.volmodel = volmodel;
    }
    
    
    
    
    void setId(Long id) {
        this.id=id;
    }

    /*public VolumeSelectionModelType1 getVolmodel() {
    if(volmodel==null){
    System.out.println("fend.session.node.headers.HeadersModel.getVolmodel()  NULL returned");
    }
    return volmodel;
    }*/
    public VolumeSelectionModelType0 getVolmodel() {
        if(volmodel==null){
            System.out.println("fend.session.node.headers.HeadersModel.getVolmodel()  NULL returned");
        }
        return volmodel;
    }

    /*public void setVolmodel(VolumeSelectionModelType1 volmodel) {
    this.volmodel = volmodel;
    }*/
    public void setVolmodel(VolumeSelectionModelType0 volmodel) {
    this.volmodel = volmodel;
    }
    
    public Sequences getSequenceObjBySequenceNumber(Long seqno){
        for (Iterator<Sequences> iterator = sequenceListInHeaders.iterator(); iterator.hasNext();) {
            Sequences seq = iterator.next();
            if(seq.getSequenceNumber().equals(seqno)){
                return seq;
            }
        }
        return null;
    }
    
    
    
}
