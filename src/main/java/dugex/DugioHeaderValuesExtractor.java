/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dugex;

import db.model.Headers;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author naila0152
 */





class DugioMetaHeaders{                      /*
                                                Class of MetaHeaders only. 
                                                */
     
     String timeStamp;
     String subsurface;
     //String traceCount="_HDR:P_MAX_FOLD";   //No longer found in the metavalues! Use dugio summary and grep to extract
     String inlineMax="_HDR:INLINE_MAX";
     String inlineMin="_HDR:INLINE_MIN";
     String inlineInc="_HDR:INLINE_INC";
     String xlineMax="_HDR:CROSSLINE_MAX";
     String xlineMin="_HDR:CROSSLINE_MIN";
     String xlineInc="_HDR:CROSSLINE_INC";
     String dugShotMax="_HDR:SHOT_MAX";
     String dugShotMin="_HDR:SHOT_MIN";
     String dugShotInc="_HDR:SHOT_INC";
     String dugChannelMax="_HDR:CHANNEL_MAX";
     String dugChannelMin="_HDR:CHANNEL_MIN";
     String dugChannelInc="_HDR:CHANNEL_INC";
     String offsetMax="_HDR:OFFSET_MAX";
     String offsetMin="_HDR:OFFSET_MIN";
     String offsetInc="_HDR:OFFSET_INC";
     String cmpMax="_HDR:CMP_MAX";
     String cmpMin="_HDR:CMP_MIN";
     String cmpInc="_HDR:CMP_INC";

}


public class DugioHeaderValuesExtractor {
    private DugioScripts ds=new DugioScripts();
    private DugioMetaHeaders dmh=new DugioMetaHeaders();
    private File volume;
    private ArrayList<Headers> headers=new ArrayList<>();
    
    public DugioHeaderValuesExtractor(){
        
    }

    public void setVolume(File volume) {
        this.volume = volume;
    }
    
    
    
    
    public ArrayList<Headers> calculatedHeaders() throws InterruptedException, ExecutionException{
        calculateSubsurfaceLines();
       
       /* for (Iterator<Headers> iterator = headers.iterator(); iterator.hasNext();) {
            Headers next = iterator.next();
            System.out.println("DHVEx: for volume "+volume+" sub: "+next.getSubsurface()+" time: "+next.getTimeStamp());
        }*/
        
        
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executorService.submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                return calculateRemainingHeaders();
            }
        }).get();
        
        
         
         
         
        return headers;
    }
    
    private void calculateSubsurfaceLines(){
        try{
            ExecutorService executorService= Executors.newFixedThreadPool(1);
            executorService.submit(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    synchronized(this){
                        Process process=new ProcessBuilder(ds.getGetTimeSubsurfaces().getAbsolutePath(),volume.getAbsolutePath()).start();
                    InputStream is = process.getInputStream();
                    InputStreamReader isr=new InputStreamReader(is);
                    BufferedReader br=new BufferedReader(isr);
                    String line;
                    
                    while((line=br.readLine())!=null){
                        String time = line.substring(0,line.indexOf(" "));
                        String lineName= line.substring(line.indexOf(" ")+1,line.length());
                        String seq=line.substring(line.indexOf("_")-3,line.indexOf("_"));
                        Headers hdr=new Headers();
                        hdr.setSubsurface(lineName);
                        hdr.setTimeStamp(time);
                        hdr.setSequenceNumber(Long.valueOf(seq));
                        headers.add(hdr);
                        
                    }
                    return null;
                  }
                    
                }
            }).get();
        }catch(ExecutionException ex){
            ex.printStackTrace();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(DugioHeaderValuesExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    private Void calculateRemainingHeaders(){
        try{
         ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
         executorService.submit(new Callable<Void>() {

             @Override
             public Void call() throws Exception {
                 synchronized(this){
                     for (Iterator<Headers> iterator = headers.iterator(); iterator.hasNext();) {
                         Headers hdr = iterator.next();
                         System.out.println("DHVEx: Inside calculateRemainingHeaders for "+hdr.getSubsurface());
                         
                       // Long traceCount=Long.valueOf(forEachKey(hdr,dmh.traceCount));
                       //  System.out.println("Value from forTraces:"+forTraces(hdr));
                        Long traceCount=Long.valueOf(forTraces(hdr)).longValue();
                        Long cmpMax=Long.valueOf(forEachKey(hdr,dmh.cmpMax));
                        Long cmpMin=Long.valueOf(forEachKey(hdr,dmh.cmpMin));
                        Long cmpInc=Long.valueOf(forEachKey(hdr,dmh.cmpInc));
                        
                        Long inlineMax=Long.valueOf(forEachKey(hdr,dmh.inlineMax));
                        Long inlineMin=Long.valueOf(forEachKey(hdr,dmh.inlineMin));
                        Long inlineInc=Long.valueOf(forEachKey(hdr,dmh.inlineInc));
                        Long xlineMax=Long.valueOf(forEachKey(hdr,dmh.xlineMax));
                        Long xlineMin=Long.valueOf(forEachKey(hdr,dmh.xlineMin));
                        Long xlineInc=Long.valueOf(forEachKey(hdr,dmh.xlineInc));
                        Long dugShotMax=Long.valueOf(forEachKey(hdr,dmh.dugShotMax));
                        Long dugShotMin=Long.valueOf(forEachKey(hdr,dmh.dugShotMin));
                        Long dugShotInc=Long.valueOf(forEachKey(hdr,dmh.dugShotInc));
                        Long dugChannelMax=Long.valueOf(forEachKey(hdr,dmh.dugChannelMax));
                        Long dugChannelMin=Long.valueOf(forEachKey(hdr,dmh.dugChannelMin));
                        Long dugChannelInc=Long.valueOf(forEachKey(hdr,dmh.dugChannelInc));
                        Long offsetMax=Long.valueOf(forEachKey(hdr,dmh.offsetMax));
                        Long offsetMin=Long.valueOf(forEachKey(hdr,dmh.offsetMin));
                        Long offsetInc=Long.valueOf(forEachKey(hdr,dmh.offsetInc));
                        
                        
                        hdr.setTraceCount(traceCount);
                        hdr.setCmpMax(cmpMax);
                        hdr.setCmpMin(cmpMin);
                        hdr.setCmpInc(cmpInc);
                        hdr.setInlineMax(inlineMax);
                        hdr.setInlineMin(inlineMin);
                        hdr.setInlineInc(inlineInc);
                        hdr.setXlineMax(xlineMax);
                        hdr.setXlineMin(xlineMin);
                        hdr.setXlineInc(xlineInc);
                        hdr.setDugShotMax(dugShotMax);
                        hdr.setDugShotMin(dugShotMin);
                        hdr.setDugShotInc(dugShotInc);
                        hdr.setDugChannelMax(dugChannelMax);
                        hdr.setDugChannelMin(dugChannelMin);
                        hdr.setDugChannelInc(dugChannelInc);
                        hdr.setOffsetMax(offsetMax);
                        hdr.setOffsetMin(offsetMin);
                        hdr.setOffsetInc(offsetInc);
                        
                        
                         
                     }
                    
                        
                     
                 }
                return null; 
             }
         }).get();
         
        } catch (InterruptedException ex) {
            Logger.getLogger(DugioHeaderValuesExtractor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(DugioHeaderValuesExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private String forEachKey(Headers hdr,String key) throws IOException{
                        System.out.println("Inside forEach key with key ="+key);
                        Process process=new ProcessBuilder(ds.getDugioHeaderValuesSh().getAbsolutePath(),volume.getAbsolutePath(),hdr.getSubsurface(),key).start();
                        InputStream is = process.getInputStream();
                        InputStreamReader isr=new InputStreamReader(is);
                        BufferedReader br=new BufferedReader(isr);
                        
                        String value;
                        while((value=br.readLine())!=null){
                            System.out.println("DHVEx: forEachKey Volume: "+volume+" sub: "+hdr.getSubsurface()+" key: "+key+" = "+value);
                            return value;
                        }
                       
                        return null;
                        
    }
    
    private String forTraces(Headers hdr) throws IOException{
                        System.out.println("Inside forTraces key with NO key");
                        Process process=new ProcessBuilder(ds.getDugioGetTraces().getAbsolutePath(),volume.getAbsolutePath(),hdr.getSubsurface()).start();
                        InputStream is = process.getInputStream();
                        InputStreamReader isr=new InputStreamReader(is);
                        BufferedReader br=new BufferedReader(isr);
                        
                        String value;
                        while((value=br.readLine())!=null){
                            System.out.println("DHVEx: forTraces Volume: "+volume+" sub: "+hdr.getSubsurface()+" Traces ="+value+"");
                            return value;
                        }
                       
                        return null;
                        
    }
}



