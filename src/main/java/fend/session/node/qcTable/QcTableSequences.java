/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.qcTable;

import db.model.Headers;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.QcTable;
import db.model.Subsurface;
import db.model.Volume;
import db.services.HeadersService;
import db.services.HeadersServiceImpl;
import db.services.JobStepService;
import db.services.JobStepServiceImpl;
import db.services.JobVolumeDetailsService;
import db.services.JobVolumeDetailsServiceImpl;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
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
    private SubsurfaceService subserv=new SubsurfaceServiceImpl();
    private JobVolumeDetailsService jvserv=new JobVolumeDetailsServiceImpl();
    private JobStepService jobserv=new JobStepServiceImpl();
    private HeadersService hserv=new HeadersServiceImpl();
    private QcTableService qctserv=new QcTableServiceImpl();
    Map<QcTypeModel,BooleanProperty> qctypeMap=new HashMap<>();
    JobStepType0Model jobModel; 
    Boolean loading=true;
    
    public JobStepType0Model getJobModel() {
        return jobModel;
    }

    public void setJobModel(JobStepType0Model jobModel) {
        this.jobModel = jobModel;
    }
     
    
     
     
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
            
            //    System.out.println("fend.session.node.volumes.type1.qcTable.QcTableSequences.setSequence()  qsub.getQctypes(): returned null");
            if(!loading){
                QcTableSubsurfaces qsub=new QcTableSubsurfaces();
            qsub.setQcTableSeq(this);
            qsub.setSequence(this.sequence);
            qsub.setSub(subs); 
            List<QcTypeModel> qctypescopy=new ArrayList<>();
                System.out.println("fend.session.node.volumes.type1.qcTable.QcTableSequences.loadQcTypes(): loading is "+loading);
            
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
                    qcSubs.add(qsub);
             
             }
             
             
             if(loading){
                 QcTableSubsurfaces qsub=new QcTableSubsurfaces();
            qsub.setQcTableSeq(this);
            qsub.setSequence(this.sequence);
            qsub.setSub(subs); 
            List<QcTypeModel> qctypescopy=new ArrayList<>();
              System.out.println("fend.session.node.volumes.type1.qcTable.QcTableSequences.loadQcTypes(): loading is "+loading);
             ///-->Start
            /* 
             
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
                    qcSubs.add(qsub);
             ///--->End
           
             */
             //check if an entry exists in the database
            
           SubSurfaceHeaders sub=qsub.getSub();
            Subsurface subobj=subserv.getSubsurfaceObjBysubsurfacename(sub.getSubsurface());
            JobStep js=jobserv.getJobStep(jobModel.getId());
            List<JobVolumeDetails> jvlist=jvserv.getJobVolumeDetails(js);
            for (Iterator<JobVolumeDetails> iterator = jvlist.iterator(); iterator.hasNext();) {
                JobVolumeDetails jv = iterator.next();
                Volume v=jv.getVolume();
                List<Headers> hlist=hserv.getHeadersFor(v, subobj);
                if(hlist.isEmpty()){        //current volume model doesnt contain the sub
                    
                    continue;
                }
                else if(hlist.size()==1){
                    
                    qctypescopy.clear();
                    
                    
                    Headers h=hlist.get(0);
                    System.out.println("fend.session.node.qcTable.QcTableSequences.loadQcTypes()");
                    List<QcTable> qctableObjList=qctserv.getQcTableFor(h);
                    
                    if(qctableObjList.isEmpty()){
                         
                        System.out.println("fend.session.node.qcTable.QcTableSequences.loadQcTypes(): list is empty. creating copies of qctypes");
            
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
                    qcSubs.add(qsub);
                        
                    }else{
                        
                   
                    
                    
                    
                    for (Iterator<QcTable> iterator1 = qctableObjList.iterator(); iterator1.hasNext();) {
                        QcTable next = iterator1.next();
                        QcTypeModel n=new QcTypeModel();
                        System.out.println("fend.session.node.volumes.type1.qcTable.QcTableSequences.loadQcTypes(): loading for sub: "+h.getSubsurface().getSubsurface()+" id: "+next.getQcmatrix().getQctype().getIdQcType()+
                                "  name: "+next.getQcmatrix().getQctype().getName()+" ticked: "+ next.getResult());
                n.setId(next.getQcmatrix().getQctype().getIdQcType());
                n.setName(next.getQcmatrix().getQctype().getName());
                n.setPassQc(next.getResult());
                qctypescopy.add(n);
                        
                    }
                     qsub.setQctypes(qctypescopy);
                    qcSubs.add(qsub);
                     }

                }else{
                    
                }
                
                }
                
            }
            
            
            
           
            
           
                 
            
         //   qsub.setQctypeMap(fmap);
                         //sequence and subsurface information is set within class
            
        }
        loading=false;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
