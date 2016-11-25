/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collector;

import db.model.Headers;
import db.model.Volume;
import db.services.HeadersService;
import db.services.HeadersServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import dugex.DugioHeaderValuesExtractor;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.Sequences;
import fend.session.node.headers.SubSurface;
import fend.session.node.volumes.VolumeSelectionModel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

/**
 *
 * @author sharath nair
 */
public class HeaderCollector {
    //from frontEnd. user
    
    private VolumeSelectionModel feVolumeSelModel;
    private HeadersModel headersModel;                                                                      //the headers model corresponding to this volume
    Set<SubSurface> sl;                                                                                     // the SET of subsurfaces in the volume. Note this DOES NOT account for more than one occurrence of the sub
    List<Sequences> seqList;
    
    
    
    private DugioHeaderValuesExtractor dugHve=new DugioHeaderValuesExtractor();
    
    //for db
    private ArrayList<Headers> dbHeaders=new ArrayList<>();
    
    //for db Transactions
    final private static HeadersService hdrServ=new HeadersServiceImpl();
    final private static VolumeService volServ=new VolumeServiceImpl();
    private Volume dbVolume;

    public void setFeVolumeSelModel(VolumeSelectionModel feVolumeSelModel) {
        this.feVolumeSelModel = feVolumeSelModel;
        this.headersModel=this.feVolumeSelModel.getHeadersModel();
        dbVolume = volServ.getVolume(feVolumeSelModel.getId());                                 //retrieve the correct dbVolume from the db. This would mean that the dbVolume table needs to exist before Headers are retrieved
        
        
        System.out.println("HeaderColl: Set the volume Sel model "+feVolumeSelModel.getLabel());
        System.out.println("HeaderColl: volume retrieved from db id:  "+dbVolume.getIdVolume()+ " name: "+dbVolume.getNameVolume());
        calculateAndCommitHeaders();
    }
    
    private void calculateAndCommitHeaders(){
        try {
            File volume=feVolumeSelModel.getVolumeChosen();
            System.out.println("HeaderColl: calculating headers for "+volume.getAbsolutePath());
            dugHve.setVolume(volume);
            
            ArrayList<Headers> headerList=dugHve.calculatedHeaders();     //  <<<<  The workhorse
            
            /*sl=new ArrayList<>();*/
            sl=new HashSet<>();
           seqList=new ArrayList<>();
            MultiMap<Long,SubSurface> seqSubMap=new MultiValueMap<>();                                             //for creating association between Sequences and Subsurfaces
            
            
            for (Iterator<Headers> iterator = headerList.iterator(); iterator.hasNext();) {
                Headers next = iterator.next();
                next.setVolume(dbVolume);
                
                /// Code up a method to set id of headers based on the hash generated from fields (subsurface,tracecount,.....) of the headers. 
              
                hdrServ.createHeaders(next);                             //commit to the db
                
                
                SubSurface s= new SubSurface();
          
                s.setSequenceNumber(next.getSequenceNumber());
                s.setSubsurface(next.getSubsurface());
                s.setTimeStamp(next.getTimeStamp());
          
                s.setCmpInc(next.getCmpInc());
                s.setCmpMax(next.getCmpMax());
                s.setCmpMin(next.getCmpMin());
                s.setDugChannelInc(next.getDugChannelInc());
                s.setDugChannelMax(next.getDugChannelMax());
                s.setDugChannelMin(next.getDugChannelMin());
                s.setDugShotInc(next.getDugShotInc());
                s.setDugShotMax(next.getDugShotMax());
                s.setDugShotMin(next.getDugShotMin());
                s.setInlineInc(next.getInlineInc());
                s.setInlineMax(next.getInlineMax());
                s.setInlineMin(next.getInlineMin());
                s.setOffsetInc(next.getOffsetInc());
                s.setOffsetMax(next.getOffsetMax());
                s.setOffsetMin(next.getOffsetMin());
          
                s.setTraceCount(next.getTraceCount());
                s.setXlineInc(next.getXlineInc());
                s.setXlineMax(next.getXlineMax());
                s.setXlineMin(next.getXlineMin());
          
                seqSubMap.put(s.getSequenceNumber(), s);
                
                
                 sl.add(s);
                
                
                
                
                
            }
            
            
            Set<Long> seqNos=seqSubMap.keySet();
      
      
      for (Iterator<Long> iterator = seqNos.iterator(); iterator.hasNext();) {
          Long next = iterator.next();
          Sequences sq=new Sequences();
          ArrayList<SubSurface> ssubs=(ArrayList<SubSurface>) seqSubMap.get(next);
          sq.setSubsurfaces(ssubs);
          seqList.add(sq);
      }
            
       feVolumeSelModel.setSubsurfaces(sl);
       
      ObservableList<Sequences> obseq=FXCollections.observableArrayList(seqList);
      headersModel.setObsHList(obseq);                                     //set the headersModel that will be used to launch the header table
      dbVolume.setHeaderExtracted(Boolean.TRUE);
      volServ.setHeaderExtractionFlag(dbVolume);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(HeaderCollector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(HeaderCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

  public List<Sequences> getHeaderListForVolume(VolumeSelectionModel vm){
    /*  List<Headers> hl=hdrServ.getHeadersFor(dbVolume);
      List<SubSurface> sl=new ArrayList<>();
      List<Sequences> seqList=new ArrayList<>();
      MultiMap<Long,SubSurface> seqSubMap=new MultiValueMap<>();                                             //for creating association between Sequences and Subsurfaces
      
      for (Iterator<Headers> iterator = hl.iterator(); iterator.hasNext();) {
          Headers next = iterator.next();
          SubSurface s= new SubSurface();
          
          s.setSequenceNumber(next.getSequenceNumber());
          s.setSubsurface(next.getSubsurface());
          s.setTimeStamp(next.getTimeStamp());
          
          s.setCmpInc(next.getCmpInc());
          s.setCmpMax(next.getCmpMax());
          s.setCmpMin(next.getCmpMin());
          s.setDugChannelInc(next.getDugChannelInc());
          s.setDugChannelMax(next.getDugChannelMax());
          s.setDugChannelMin(next.getDugChannelMin());
          s.setDugShotInc(next.getDugShotInc());
          s.setDugShotMax(next.getDugShotMax());
          s.setDugShotMin(next.getDugShotMin());
          s.setInlineInc(next.getInlineInc());
          s.setInlineMax(next.getInlineMax());
          s.setInlineMin(next.getInlineMin());
          s.setOffsetInc(next.getOffsetInc());
          s.setOffsetMax(next.getOffsetMax());
          s.setOffsetMin(next.getOffsetMin());
          
          s.setTraceCount(next.getTraceCount());
          s.setXlineInc(next.getXlineInc());
          s.setXlineMax(next.getXlineMax());
          s.setXlineMin(next.getXlineMin());
          
          seqSubMap.put(s.getSequenceNumber(), s);
         
          
          sl.add(s);
          
          
          
      }
      
      Set<Long> seqNos=seqSubMap.keySet();
      
      
      for (Iterator<Long> iterator = seqNos.iterator(); iterator.hasNext();) {
          Long next = iterator.next();
          Sequences sq=new Sequences();
          ArrayList<SubSurface> ssubs=(ArrayList<SubSurface>) seqSubMap.get(next);
          sq.setSubsurfaces(ssubs);
          seqList.add(sq);
      }
      
      feVolumeSelModel.setSubsurfaces(sl);
      ObservableList<Sequences> obseq=FXCollections.observableArrayList(seqList);
      headersModel.setObsHList(obseq);
      
      System.out.println("HColl: done setting the headerList here");
*/
      headersModel=vm.getHeadersModel();
      return headersModel.getObsHList();                                                //the observable List is the list of sequences. which contains all the header information
  }
    
    
    
}
