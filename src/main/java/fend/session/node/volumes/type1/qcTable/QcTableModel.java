/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type1.qcTable;

import fend.session.node.headers.SequenceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.volumes.type1.qcTable.qcCheckBox.qcCheckListModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 *
 * @author sharath nair
 */
public class QcTableModel {
    
    List<QcTableSequences> qcTableSequences=new ArrayList<>();
    
    
    List<QcTypeModel> qctypes=new ArrayList();                   //list of qctypes.
    QcMatrixModel qcMatrixModel=null;
    List<SequenceHeaders> sequences;
    JobStepType0Model jobmodel;

    public JobStepType0Model getJobmodel() {
        return jobmodel;
    }

    public void setJobmodel(JobStepType0Model jobmodel) {
        this.jobmodel = jobmodel;
    }
    
    
    
    
    
    public List<QcTypeModel> getQctypes() {
        qctypes=qcMatrixModel.getQcTypeModels();
    return qctypes;
    }
   /* 
    public void setQctypes(List<QcTypeModel> qctypes) {
    this.qctypes = qctypes;
    }*/

    public QcMatrixModel getQcMatrixModel() {
        return qcMatrixModel;
    }

    public void setQcMatrixModel(QcMatrixModel qcMatrixModel) {
        this.qcMatrixModel = qcMatrixModel;
         qctypes=this.qcMatrixModel.getQcTypeModels();
    }
    
    

    public List<SequenceHeaders> getSequences() {
        return sequences;
    }

    public void setSequences(List<SequenceHeaders> sequences) {
        this.sequences = sequences;
        
    }

    ;public List<QcTableSequences> getQcTableSequences() {
        qcTableSequences.clear();
        qctypes=qcMatrixModel.getQcTypeModels();
        Map<QcTypeModel,BooleanProperty> fmap=new HashMap<>();
        for (Iterator<QcTypeModel> iteratorq = qctypes.iterator(); iteratorq.hasNext();) {
                QcTypeModel nextq = iteratorq.next();
                PassBP p=new PassBP();
                fmap.put(nextq,p.passProperty());
        }
        
        
        for (Iterator<QcTypeModel> iterator = qctypes.iterator(); iterator.hasNext();) {
            QcTypeModel next = iterator.next();
            System.out.println("fend.session.node.volumes.qcTable.QcTableModel.getQcTableSequences(): qctypes: "+next.getName());
        }
        
        
        for (Iterator<SequenceHeaders> iterator = sequences.iterator(); iterator.hasNext();) {
            SequenceHeaders next = iterator.next();
            QcTableSequences q=new QcTableSequences();;
          //  System.out.println("fend.session.node.volumes.qcTable.QcTableModel.getQcTableSequences(): adding seq: "+next.getSequenceNumber());
            
          List<QcTypeModel> qctypescopy=new ArrayList<>();
            for (Iterator<QcTypeModel> iterator1 = qctypes.iterator(); iterator1.hasNext();) {
                QcTypeModel next1 = iterator1.next();
                QcTypeModel n=new QcTypeModel();
                n.setId(next1.getId());
                n.setName(next1.getName());
                n.setPassQc(next1.isPassQc());
                qctypescopy.add(n);
                
            }
            
          q.setQctypes(qctypescopy);
           // ObservableList<QcTypeModel> o=FXCollections.observableList(qctypes);
           
        
            //q.setQcs(o);
            System.out.println("fend.session.node.volumes.type1.qcTable.QcTableModel.getQcTableSequences(): Adding seq: "+next.getSequenceNumber());
            q.setSequence(next);
            q.setQctypeMap(fmap);
            
            qcTableSequences.add(q);
            
        }
        return qcTableSequences;
    }

    
    
   
    
    
    
    
}
