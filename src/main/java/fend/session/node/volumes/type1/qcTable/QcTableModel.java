/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type1.qcTable;

import fend.session.node.headers.Sequences;
import fend.session.node.jobs.type0.JobStepType0Model;
import fend.session.node.volumes.type1.qcTable.qcCheckBox.qcCheckListModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sharath nair
 */
public class QcTableModel {
    
    List<QcTableSequences> qcTableSequences=new ArrayList<>();
    
    
    List<QcTypeModel> qctypes=new ArrayList();                   //list of qctypes.
    QcMatrixModel qcMatrixModel;
    List<Sequences> sequences;
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
    
    

    public List<Sequences> getSequences() {
        return sequences;
    }

    public void setSequences(List<Sequences> sequences) {
        this.sequences = sequences;
        
    }

    public List<QcTableSequences> getQcTableSequences() {
        qcTableSequences.clear();
        qctypes=qcMatrixModel.getQcTypeModels();
        for (Iterator<QcTypeModel> iterator = qctypes.iterator(); iterator.hasNext();) {
            QcTypeModel next = iterator.next();
            System.out.println("fend.session.node.volumes.qcTable.QcTableModel.getQcTableSequences(): qctypes: "+next.getName());
        }
        
        
        for (Iterator<Sequences> iterator = sequences.iterator(); iterator.hasNext();) {
            Sequences next = iterator.next();
            QcTableSequences q=new QcTableSequences();;
          //  System.out.println("fend.session.node.volumes.qcTable.QcTableModel.getQcTableSequences(): adding seq: "+next.getSequenceNumber());
            q.setQcfields(qctypes);
            q.setSequence(next);
            qcTableSequences.add(q);
            
        }
        return qcTableSequences;
    }

    
    
   
    
    
    
    
}
