/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import com.sun.org.apache.xpath.internal.axes.SubContextList;
import db.model.Headers;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author naila0152
 */
public class HeadersModel {
   
    private List<Sequences> seqList=new ArrayList<>();
    private ObservableList<Sequences> sequenceListInHeaders=FXCollections.observableList(seqList);
    private Long id;

    public ObservableList<Sequences> getSequenceListInHeaders() {
        return sequenceListInHeaders;
    }

    public void setSequenceListInHeaders(ObservableList<Sequences> sequenceListInHeaders) {
        this.sequenceListInHeaders = FXCollections.observableList(sequenceListInHeaders);
    }
    
    
    
    
    void setId(Long id) {
        this.id=id;
    }
    
    
    
}
