/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcMatrix;

import fend.session.node.headers.Sequences;
import fend.session.node.volumes.qcMatrix.qcCheckBox.qcCheckListModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sharath nair
 */
public class QcMatrixModel {
    
    List<QcMatrixSequences> qcMatrixSequences=new ArrayList<>();
    
    
    List<String> qctypes=new ArrayList();                   //list of qctypes.
    List<Sequences> sequences;

    public List<String> getQctypes() {
        return qctypes;
    }

    public void setQctypes(List<String> qctypes) {
        this.qctypes = qctypes;
    }

    public List<Sequences> getSequences() {
        return sequences;
    }

    public void setSequences(List<Sequences> sequences) {
        this.sequences = sequences;
        
    }

    public List<QcMatrixSequences> getQcMatrixSequences() {
        qcMatrixSequences.clear();
        for (Iterator<Sequences> iterator = sequences.iterator(); iterator.hasNext();) {
            Sequences next = iterator.next();
            QcMatrixSequences q=new QcMatrixSequences();;
            q.setQcfields(qctypes);
            q.setSequence(next);
            qcMatrixSequences.add(q);
            
        }
        return qcMatrixSequences;
    }

    
    
   
    
    
    
    
}
