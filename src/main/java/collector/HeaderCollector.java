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
import fend.session.node.headers.HeaderTableModel;
import fend.session.node.headers.SubSurface;
import fend.session.node.volumes.VolumeSelectionModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sharath nair
 */
public class HeaderCollector {
    //from frontEnd. user
    
    private VolumeSelectionModel feVolumeSelModel;
    
    private DugioHeaderValuesExtractor dugHve=new DugioHeaderValuesExtractor();
    
    //for db
    private ArrayList<Headers> dbHeaders=new ArrayList<>();
    
    //for db Transactions
    final private static HeadersService hdrServ=new HeadersServiceImpl();
    final private static VolumeService volServ=new VolumeServiceImpl();
    private Volume dbVolume;

    public void setFeVolumeSelModel(VolumeSelectionModel feVolumeSelModel) {
        this.feVolumeSelModel = feVolumeSelModel;
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
            
            
            for (Iterator<Headers> iterator = headerList.iterator(); iterator.hasNext();) {
                Headers next = iterator.next();
                next.setVolume(dbVolume);
                
                hdrServ.createHeaders(next);                             //commit to the db
                
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(HeaderCollector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(HeaderCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

  public List<SubSurface> getHeaderListForVolume(){
      List<Headers> hl=hdrServ.getHeadersFor(dbVolume);
      List<SubSurface> sl=new ArrayList<>();
      
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
          
          
          sl.add(s);
      }
      
      
      System.out.println("HColl: done setting the headerList here");
      return sl;
  }
    
    
    
}
