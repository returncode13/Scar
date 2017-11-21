/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.qcTable;

import db.model.Headers;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.QcMatrix;
import db.model.QcTable;
import db.model.SessionDetails;
import db.model.Subsurface;
import db.model.Volume;
import db.services.HeadersService;
import db.services.HeadersServiceImpl;
import db.services.JobStepService;
import db.services.JobStepServiceImpl;
import db.services.JobVolumeDetailsService;
import db.services.JobVolumeDetailsServiceImpl;
import db.services.QcMatrixService;
import db.services.QcMatrixServiceImpl;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import db.services.SessionDetailsService;
import db.services.SessionDetailsServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.openide.util.Exceptions;

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
    private QcMatrixService qcMatServ=new QcMatrixServiceImpl();
    private SessionDetailsService ssdServ=new SessionDetailsServiceImpl();
    
    Map<QcTypeModel,BooleanProperty> qctypeMap=new HashMap<>();
    JobStepType0Model jobModel; 
    Boolean loading=true;
    String updateTime;
    
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
        System.out.println("fend.session.node.qcTable.QcTableSequences.setSequence(): added seq: "+sequenceNumber.get()+" and sub: "+subsurface.get());
        
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
        System.out.println("fend.session.node.qcTable.QcTableSequences.getSequenceNumber(): setting property to "+sequence.getSequenceNumber());
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
        JobStep js=jobserv.getJobStep(jobModel.getId());
        List<SessionDetails> sdList=ssdServ.getSessionDetails(js);
        SessionDetails sessDetails=null;
        if(sdList.size()>1){
            try {
                throw new Exception("More than one session details entries encountered for job"+js.getNameJobStep());
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }else{
            sessDetails=sdList.get(0);
        }
        
        List<JobVolumeDetails> jvlist=jvserv.getJobVolumeDetails(js);
        
        for(SubSurfaceHeaders subs: sequence.getSubsurfaces()){
            
            //    System.out.println("fend.session.node.volumes.type1.qcTable.QcTableSequences.setSequence()  qsub.getQctypes(): returned null");
            if(!loading){
                QcTableSubsurfaces qsub=new QcTableSubsurfaces();
            qsub.setQcTableSeq(this);
            qsub.setSequence(this.sequence);
            qsub.setSub(subs);
            qsub.getSub().resetPassQC();
            List<QcTypeModel> qctypescopy=new ArrayList<>();
                System.out.println("fend.session.node.qcTable.QcTableSequences.loadQcTypes(): loading is "+loading);
            
             for (Iterator<QcTypeModel> iterator1 = qctypes.iterator(); iterator1.hasNext();) {
                QcTypeModel next1 = iterator1.next();
                QcTypeModel n=new QcTypeModel();
                n.setId(next1.getId());
                n.setName(next1.getName());
                n.setPassQc(next1.isPassQc());
                qsub.getSub().qcStatus(next1.isPassQc());
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
             System.out.println("fend.session.node.qcTable.QcTableSequences.loadQcTypes(): loading is "+loading);
             ///-->Start
            /* 
             
             for (Iterator<QcTypeModel> iterator0 = qctypes.iterator(); iterator0.hasNext();) {
                QcTypeModel next1 = iterator0.next();
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
           sub.resetPassQC();
            Subsurface subobj=subserv.getSubsurfaceObjBysubsurfacename(sub.getSubsurface());
            /* JobStep js=jobserv.getJobStep(jobModel.getId());
            
            List<JobVolumeDetails> jvlist=jvserv.getJobVolumeDetails(js);*/
            
            for (Iterator<JobVolumeDetails> iterator = jvlist.iterator(); iterator.hasNext();) {
                JobVolumeDetails jv = iterator.next();
                Volume v=jv.getVolume();
                List<Headers> hlist=hserv.getHeadersFor(v, subobj);
                if(hlist.isEmpty()){        //current volume model doesnt contain the sub
                    
                    continue;
                }
                else if(hlist.size()==1){
                   
                   /* 
                    
                    qctypescopy.clear();
                    
                    
                    Headers h=hlist.get(0);
                    System.out.println("fend.session.node.qcTable.QcTableSequences.loadQcTypes()");
                    List<QcMatrix> qcmatList=qcMatServ.getQcMatrixForSessionDetails(sessDetails,true);    //get the checked items from the qcmatrix.  shot, stack
                    for (Iterator<QcMatrix> iterator0 = qcmatList.iterator(); iterator0.hasNext();) {
                    QcMatrix qcmat = iterator0.next();
                    List<QcTable> qctableForQmxHdr=qctserv.getQcTableFor(qcmat,h);                //get qctable entry for qcmat and header.
                        List<QcTable> qctableForQmxHdr=qctserv.getQcTableFor(h);  
                        
                          if(qctableForQmxHdr.isEmpty()){
                         
                        System.out.println("fend.session.node.qcTable.QcTableSequences.loadQcTypes(): list is empty. creating copies of qctypes");
                        qsub.getSub().resetPassQC();
                        for (Iterator<QcTypeModel> iterator1 = qctypes.iterator(); iterator1.hasNext();) {
                           QcTypeModel next1 = iterator1.next();
                           QcTypeModel n=new QcTypeModel();
                           n.setId(next1.getId());
                           n.setName(next1.getName());
                           n.setPassQc(next1.isPassQc());
                           qsub.getSub().qcStatus(next1.isPassQc());
                           
                           qctypescopy.add(n);
                          // qctypescopy.add(next1);

                       }

                        qsub.setQctypes(qctypescopy);
                    qcSubs.add(qsub);
                        
                    }else{
                        
                  // List<QcTypeModel> existingQcTypeModels=new ArrayList<>();
                    
                    qsub.getSub().resetPassQC();
                    
                    for (Iterator<QcTable> iterator1 = qctableForQmxHdr.iterator(); iterator1.hasNext();) {
                        QcTable next = iterator1.next();
                        QcTypeModel n=new QcTypeModel();
                        System.out.println("fend.session.node.qcTable.QcTableSequences.loadQcTypes(): loading for sub: "+h.getSubsurface().getSubsurface()+" id: "+next.getQcmatrix().getQctype().getIdQcType()+
                                "  name: "+next.getQcmatrix().getQctype().getName()+" ticked: "+ next.getResult()+" Utime: "+next.getUpdateTime());
                        n.setId(next.getQcmatrix().getQctype().getIdQcType());
                        n.setName(next.getQcmatrix().getQctype().getName());
                        n.setPassQc(next.getResult());

                        qctypescopy.add(n);

                        qsub.getSub().qcStatus(next.getResult());
                        qsub.getSub().setUpdateTime(next.getUpdateTime());
                        qsub.getSub().setSummaryTime(next.getSummaryTime());
                        qsub.setUpdateTime(next.getUpdateTime());
                
                        
                    }
                    
                    //check qctypescopy against qctypes. 
                    // if qctypes has more 
                    
                     qsub.setQctypes(qctypescopy);
                    qcSubs.add(qsub);
                     }
                        
               */
                          
                          
                          //
                          
                          qctypescopy.clear();
                    
                    
                    Headers h=hlist.get(0);
                    System.out.println("fend.session.node.qcTable.QcTableSequences.loadQcTypes()");
                    List<QcMatrix> qcmatList=qcMatServ.getQcMatrixForSessionDetails(sessDetails,true);    //get the checked items from the qcmatrix.  shot, stack
                    for (Iterator<QcMatrix> iterator0 = qcmatList.iterator(); iterator0.hasNext();) {
                                QcMatrix qcmat = iterator0.next();
                                QcTable qctableForQmxHdr=null;
                                          try {
                                              qctableForQmxHdr = qctserv.getQcTableFor(qcmat,h); //get qctable entry for qcmat and header.
                                              // QcTable qctableForQmxHdr=qctserv.getQcTableFor(h);
                                          } catch (Exception ex) {
                                              Exceptions.printStackTrace(ex);
                                          }

                                      if(qctableForQmxHdr==null){

                                    System.out.println("fend.session.node.qcTable.QcTableSequences.loadQcTypes(): no entry found for hdr id: "+h.getIdHeaders()+" sub "+h.getSubsurface().getSubsurface()+" qcmatrix: "+qcmat.getIdqcmatrix());
                                    System.out.println("fend.session.node.qcTable.QcTableSequences.loadQcTypes(): creating a new entry for display");
                                            for (Iterator<QcTypeModel> iterator1 = qctypes.iterator(); iterator1.hasNext();) {
                                              QcTypeModel next = iterator1.next();
                                              if(next.getName().equalsIgnoreCase(qcmat.getQctype().getName())){
                                                    QcTypeModel newQtm=new QcTypeModel();
                                                    newQtm.setId(qcmat.getQctype().getIdQcType());
                                                    newQtm.setName(qcmat.getQctype().getName());
                                                    newQtm.setPassQc(false);   //initial state is false
                                                    qsub.getSub().qcStatus(false); 
                                                  //  qsub.setUpdateTime();
                                                    qctypescopy.add(newQtm);
                                              }
                                              
                                          }
                                    

                                    /*
                                    for (Iterator<QcTypeModel> iterator1 = qctypes.iterator(); iterator1.hasNext();) {
                                    QcTypeModel next1 = iterator1.next();
                                    QcTypeModel n=new QcTypeModel();
                                    n.setId(next1.getId());
                                    n.setName(next1.getName());
                                    n.setPassQc(next1.isPassQc());
                                    qsub.getSub().qcStatus(next1.isPassQc());

                                    qctypescopy.add(n);
                                    // qctypescopy.add(next1);

                                    }

                                    qsub.setQctypes(qctypescopy);
                                    qcSubs.add(qsub);*/

                                }else{

                             // List<QcTypeModel> existingQcTypeModels=new ArrayList<>();

                               // qsub.getSub().resetPassQC();
                               System.out.println("fend.session.node.qcTable.QcTableSequences.loadQcTypes(): loading for sub: "+h.getSubsurface().getSubsurface()+" id: "+qctableForQmxHdr.getQcmatrix().getQctype().getIdQcType()+
                                            "  name: "+qctableForQmxHdr.getQcmatrix().getQctype().getName()+" ticked: "+ qctableForQmxHdr.getResult()+" Utime: "+qctableForQmxHdr.getUpdateTime());
                                QcTypeModel newQt=new QcTypeModel();
                                newQt.setId(qctableForQmxHdr.getQcmatrix().getQctype().getIdQcType());
                                newQt.setName(qctableForQmxHdr.getQcmatrix().getQctype().getName());
                                newQt.setPassQc(qctableForQmxHdr.getResult());
                                qctypescopy.add(newQt);
                                qsub.getSub().qcStatus(qctableForQmxHdr.getResult());
                                qsub.getSub().setSummaryTime(qctableForQmxHdr.getSummaryTime());
                                
                                String maxUpdateTimeForThisSub=qsub.getSub().getUpdateTime();
                                if(maxUpdateTimeForThisSub!=null && qctableForQmxHdr.getUpdateTime()!=null){
                                    if(qctableForQmxHdr.getUpdateTime().compareTo(maxUpdateTimeForThisSub)>0){
                                        maxUpdateTimeForThisSub=new String(qctableForQmxHdr.getUpdateTime());
                                    }
                                }else{
                                    maxUpdateTimeForThisSub=new String("19700101010101");
                                }
                                
                                qsub.getSub().setUpdateTime(maxUpdateTimeForThisSub);
                                qsub.setUpdateTime(maxUpdateTimeForThisSub);
                                



                                        /*for (Iterator<QcTable> iterator1 = qctableForQmxHdr.iterator(); iterator1.hasNext();) {
                                        QcTable next = iterator1.next();
                                        QcTypeModel n=new QcTypeModel();
                                        System.out.println("fend.session.node.qcTable.QcTableSequences.loadQcTypes(): loading for sub: "+h.getSubsurface().getSubsurface()+" id: "+next.getQcmatrix().getQctype().getIdQcType()+
                                        "  name: "+next.getQcmatrix().getQctype().getName()+" ticked: "+ next.getResult()+" Utime: "+next.getUpdateTime());
                                        n.setId(next.getQcmatrix().getQctype().getIdQcType());
                                        n.setName(next.getQcmatrix().getQctype().getName());
                                        n.setPassQc(next.getResult());

                                        qctypescopy.add(n);

                                        qsub.getSub().qcStatus(next.getResult());
                                        qsub.getSub().setUpdateTime(next.getUpdateTime());
                                        qsub.getSub().setSummaryTime(next.getSummaryTime());
                                        qsub.setUpdateTime(next.getUpdateTime());


                                        }*/

                                //check qctypescopy against qctypes. 
                                // if qctypes has more 

                                /* qsub.setQctypes(qctypescopy);
                                qcSubs.add(qsub);*/
                                 }
                          
                          
                          
                          //
                          
                      
                          
                          
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
                    qsub.setQctypes(qctypescopy);
                    qcSubs.add(qsub);
                          
                } 
                
                }
            
            
                
            }
            
            
            
           
            
           
                 
            
         //   qsub.setQctypeMap(fmap);
                         //sequence and subsurface information is set within class
            
        }
        loading=false;
    }

    void setUpdateTime(String updateTime) {
        this.updateTime=updateTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
