/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.qcTable;

import fend.session.node.headers.SequenceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.qcTable.qcCheckBox.qcCheckListModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    QcMatrixModel qcMatrixModel=new QcMatrixModel();            //what types are present in this qctable.
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
    
    
    
    
    public List<QcTableSequences> getQcTableSequences() {
        
        return qcTableSequences;
    }

    public void loadQcTypes() {
        //save existing qctype values
        List<QcTableSequences> qcTableSeqSave=new ArrayList<>();
        for (Iterator<QcTableSequences> iterator = qcTableSequences.iterator(); iterator.hasNext();) {
            QcTableSequences next = iterator.next();
                    
            qcTableSeqSave.add(next);
           
            
        }
        
        qcTableSequences.clear();
        qctypes=qcMatrixModel.getQcTypeModels();
        
        System.out.println("fend.session.node.qcTable.QcTableModel.loadQcTypes(): size of qctypes: "+qctypes.size());
        System.out.println("fend.session.node.qcTable.QcTableModel.loadQcTypes(): size of sequences: "+sequences.size());
         for (Iterator<SequenceHeaders> iterator1 = sequences.iterator(); iterator1.hasNext();) {
             SequenceHeaders seq=iterator1.next();
             /*  boolean present=false;                         //flag to check if the qcTableSequence is present in the qcTable
             for (Iterator<QcTableSequences> iterator2 = qcTableSeqSave.iterator(); iterator2.hasNext();) {
             QcTableSequences next1 = iterator2.next();
             if(next1.getSequence().equals(seq)){                //seq present in
             present=true;
             break;
             }
             
             }*/
                            
                            
          //   if(!present){              //during the initial load, present=false;
                QcTableSequences qctabSeq=new QcTableSequences();
                 List<QcTypeModel> qctypescopy=new ArrayList<>();
            for (Iterator<QcTypeModel> iterator3 = qctypes.iterator(); iterator3.hasNext();) {
                
                QcTypeModel next1 = iterator3.next();
                System.out.println("fend.session.node.qcTable.QcTableModel.loadQcTypes(): qctype: "+next1.getName());
                 QcTypeModel n=new QcTypeModel();
                n.setId(next1.getId());
                n.setName(next1.getName());
                n.setPassQc(next1.isPassQc());
                qctypescopy.add(n);
                //qctypescopy.add(next1);
                
            }
            /*
             sort based on the id. 
            Due to the removal/insertion/reinsert operations, the qctypescopy list here needn't correlate with the one used to set up the qctable views columns. i.e. QcTableSequences variable qctypes
            so both lists ought to be sorted by ids.
            */
            Collections.sort(qctypescopy,new Comparator<QcTypeModel>(){
                 @Override
                 public int compare(QcTypeModel o1, QcTypeModel o2) {
                     return (int) (o1.getId() - o2.getId());
                 }
                
            });
            
            
          qctabSeq.setQctypes(qctypescopy);
           
           // ObservableList<QcTypeModel> o=FXCollections.observableList(qctypes);
           
        
            //q.setQcs(o);
            System.out.println("fend.session.node.qcTable.QcTableModel.loadQcTypes(): Adding seq: "+seq.getSequenceNumber());
            qctabSeq.setSequence(seq);
            qctabSeq.setJobModel(jobmodel);
            qctabSeq.loadQcTypes();
            
            //q.setQctypeMap(fmap);
            
            qcTableSequences.add(qctabSeq);
            /*  }else{
            System.out.println("fend.session.node.qcTable.QcTableModel.loadQcTypes(): Present : "+present);
            } */              
         }
                     
        
        
        
        //qcTableSequences.clear();
        
        
        
        
        /*  for (Iterator<SequenceHeaders> iterator = sequences.iterator(); iterator.hasNext();) {
        SequenceHeaders next = iterator.next();
        QcTableSequences qctabSeq=new QcTableSequences();;
        //  System.out.println("fend.session.node.volumes.qcTable.QcTableModel.getQcTableSequences(): adding seq: "+next.getSequenceNumber());
        
        //System.out.println("fend.session.node.volumes.type1.qcTable.QcTableModel.getQcTableSequences(): qctabSeq.getQcTypes() is NULL");
        
        
        }*/
        
       
        
    }

    
    
   
    
    
    
    
}
