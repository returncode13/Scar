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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

     String[] metaHeaders={inlineMax,inlineMin,inlineInc,xlineMax,xlineMin,xlineInc,dugShotMax,dugShotMin,dugShotInc,dugChannelMax,dugChannelMin,dugChannelInc,offsetMax,offsetMin,offsetInc,cmpMax,cmpMin,cmpInc};
}

class LongHolder{
                         Long cmpMax;
                         Long cmpMin;
                         Long cmpInc;
                         Long inlineMax;
                         Long inlineMin;
                         Long inlineInc;
                         Long xlineMax;
                         Long xlineMin;
                         Long xlineInc;
                         Long dugShotMax;
                         Long dugShotMin;
                         Long dugShotInc;
                         Long dugChannelMax;
                         Long dugChannelMin;
                         Long dugChannelInc;
                         Long offsetMax;
                         Long offsetMin;
                         Long offsetInc;
                         Map<String,Long> keyValueMap;
                         DugioMetaHeaders ddmh=new DugioMetaHeaders();

    LongHolder() {
        this.keyValueMap = new HashMap<>();
        keyValueMap.put(ddmh.cmpMax, cmpMax);
        keyValueMap.put(ddmh.cmpMin, cmpMin);
        keyValueMap.put(ddmh.cmpInc, cmpInc);
        keyValueMap.put(ddmh.dugChannelInc, dugChannelInc);
        keyValueMap.put(ddmh.dugChannelMax, dugChannelMax);
        keyValueMap.put(ddmh.dugChannelMin, dugChannelMin);
        keyValueMap.put(ddmh.dugShotInc,dugShotInc);
        keyValueMap.put(ddmh.dugShotMax,dugShotMax);
        keyValueMap.put(ddmh.dugShotMin,dugShotMin);
        keyValueMap.put(ddmh.inlineInc, inlineInc);
        keyValueMap.put(ddmh.inlineMax, inlineMax);
        keyValueMap.put(ddmh.inlineMin, inlineMin);
        keyValueMap.put(ddmh.offsetInc, offsetInc);
        keyValueMap.put(ddmh.offsetMax, offsetMax);
        keyValueMap.put(ddmh.offsetMin, offsetMin);
        keyValueMap.put(ddmh.xlineInc, xlineInc);
        keyValueMap.put(ddmh.xlineMax, xlineMax);
        keyValueMap.put(ddmh.xlineMin, xlineMin);
                
    }
}


public class DugioHeaderValuesExtractor {
    private DugioScripts ds=new DugioScripts();
    private DugioMetaHeaders dmh=new DugioMetaHeaders();
    private File volume;
    private ArrayList<Headers> headers=new ArrayList<>();;
    
    public DugioHeaderValuesExtractor(){
        
    }

    public void setVolume(File volume) {
        this.volume = volume;
    }
    
    
    
    
    public ArrayList<Headers> calculatedHeaders(final Map<String,Headers> subsurfaceTimestamp,final List<Headers> existingHeaders) throws InterruptedException, ExecutionException{
        calculateSubsurfaceLines(subsurfaceTimestamp,existingHeaders);
       
       /* for (Iterator<Headers> iterator = headers.iterator(); iterator.hasNext();) {
            Headers next = iterator.next();
            System.out.println("DHVEx: for volume "+volume+" sub: "+next.getSubsurface()+" time: "+next.getTimeStamp());
        }*/
        
       System.out.println("dugex.DugioHeaderValuesExtractor.calculatedHeaders running in "+Thread.currentThread().getName()+"joining");
       Long startTime=System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                System.out.println("dugex.DugioHeaderValuesExtractor.calculatedHeaders ..calling calculateRemainingHeaders in "+Thread.currentThread().getName()+" forking");
                return calculateRemainingHeaders();
               // System.out.println("dugex.DugioHeaderValuesExtractor.calculatedHeaders running in"+Thread.currentThread().getName()+"joining");
            }
        }).get();
        System.out.println("dugex.DugioHeaderValuesExtractor.calculatedHeaders running in "+Thread.currentThread().getName()+"joining");
        Long endTime=System.currentTimeMillis();
        Long deltaTime=endTime-startTime;
        System.out.println("Time Taken: "+deltaTime+" headers.size(): "+headers.size());
         
         
        
        return headers;
    }
    
    private void calculateSubsurfaceLines(final Map<String,Headers> subsurfaceTimestamp,final List<Headers> existingHeaders){
        headers.clear();
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
                        
                       // if(headers.size()==1) break;
                        
                       // System.out.println("dugex.DugioHeaderValuesExtractor.calculateSubsurfaceLines: Found Subsurface "+lineName);
                        
                        System.out.println("dugex.DugioHeaderValuesExtractor.calculateSubsurfaceLines: Map contains "+lineName+" ? "+subsurfaceTimestamp.containsKey(lineName));
                        if(!subsurfaceTimestamp.isEmpty() && subsurfaceTimestamp.containsKey(lineName) && subsurfaceTimestamp.get(lineName).getTimeStamp().equals(time)){
                            System.out.println("dugex.DugioHeaderValuesExtractor.calculateSubsurfaceLines:  Subsurface "+lineName+" with the same timestamp "+time+" exists in the database. I will not be extracting the headers for this line");
                            
                        continue;
                        }
                        if(!subsurfaceTimestamp.isEmpty() && subsurfaceTimestamp.containsKey(lineName) && !subsurfaceTimestamp.get(lineName).getTimeStamp().equals(time)){
                            System.out.println("dugex.DugioHeaderValuesExtractor.calculateSubsurfaceLines:  Subsurface "+lineName+" exists in the database but with timestamp "+subsurfaceTimestamp.get(lineName).getTimeStamp()+" And the latest timestamp is: "+time);
                           Headers h=subsurfaceTimestamp.get(lineName);
                            h.setModified(Boolean.TRUE);
                            Long ver=h.getVersion();
                            h.setVersion(++ver);
                            h.setTimeStamp(time);
                            existingHeaders.remove(h);
                            headers.add(h);
                            continue;
                        //continue; //Comment this out later when implementing
                        }
                       System.out.println("dugex.DugioHeaderValuesExtractor.calculateSubsurfaceLines:  Setting Subsurface "+lineName);

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
            /*  ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.submit(new Callable<Void>() {
            
            @Override
            public Void call() throws Exception {
            synchronized(this){*/
           // synchronized(this){
           List<Future> futures=new ArrayList<>();
           int count=0;
                     for (Iterator<Headers> iterator = headers.iterator(); iterator.hasNext();) {
                         count++;
                       // Long traceCount=Long.valueOf(forEachKey(hdr,dmh.traceCount));
                       //  System.out.println("Value from forTraces:"+forTraces(hdr));
                         
                    
                      
                       
ExecutorService executorService1 = Executors.newFixedThreadPool(50);
futures.add(
         executorService1.submit(new Callable<Void>() {

             @Override
             public Void call() throws Exception {
             
                 
                // synchronized(this){
                 Headers hdr = iterator.next();
                         //if(hdr.getTimeStamp().)
                         System.out.println("dugex.DugioHeaderValuesExtractor.calculateRemainingHeaders for "+hdr.getSubsurface() +" on thread: "+Thread.currentThread().getName());
                         
                 Long traceCount=Long.valueOf(forTraces(hdr));
                                     
                                     /* Long[] values=new Long[dmh.metaHeaders.length];
                                     keyValueExtractor(hdr, values);*/
                                     
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
                                     
                                     
                                     
                                     /*Long inlineMax=values[0];
                                     Long inlineMin=values[1];
                                     Long inlineInc=values[2];
                                     Long xlineMax=values[3];
                                     Long xlineMin=values[4];
                                     Long xlineInc=values[5];
                                     Long dugShotMax=values[6];
                                     Long dugShotMin=values[7];
                                     Long dugShotInc=values[8];
                                     Long dugChannelMax=values[9];
                                     Long dugChannelMin=values[10];
                                     Long dugChannelInc=values[11];
                                     Long offsetMax=values[12];
                                     Long offsetMin=values[13];
                                     Long offsetInc=values[14];
                                     Long cmpMax=values[15];
                                     Long cmpMin=values[16];
                                     Long cmpInc=values[17];
                                     
                                     
                                     
                                     Long inlineMax=new Long(0);
                                     Long inlineMin=new Long(0);
                                     Long inlineInc=new Long(0);
                                     Long xlineMax=new Long(0);
                                     Long xlineMin=new Long(0);
                                     Long xlineInc=new Long(0);
                                     Long dugShotMax=new Long(0);
                                     Long dugShotMin=new Long(0);
                                     Long dugShotInc=new Long(0);
                                     Long dugChannelMax=new Long(0);
                                     Long dugChannelMin=new Long(0);
                                     Long dugChannelInc=new Long(0);
                                     Long offsetMax=new Long(0);
                                     Long offsetMin=new Long(0);
                                     Long offsetInc=new Long(0);
                                     Long cmpMax=new Long(0);
                                     Long cmpMin=new Long(0);
                                     Long cmpInc=new Long(0);
                                     */
                                     /*LongHolder lholder=new LongHolder();
                                     keyValueThreadExtractor(hdr,lholder);*/
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
                                
                         System.out.println("dugex.DugioHeaderValuesExtractor.calculateRemainingHeaders(): finished storing headers for : "+hdr.getSubsurface()+" on Thread: "+Thread.currentThread().getName());
                           return null;
             }
        }));//.get();
               if(count%50==0){
                   for(Future f:futures){
                       f.get();
                   }
               }         
             }
       // }
                 /*return null;
                 }
                 }).get();*/
         
                            
                                 
                                 
                                     
                        /* Long traceCount=Long.valueOf(forTraces(hdr));*/
                         
                         /* Long[] values=new Long[dmh.metaHeaders.length];
                         keyValueExtractor(hdr, values);*/
                        /*
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
                         Long offsetInc=Long.valueOf(forEachKey(hdr,dmh.offsetInc));*/
                         
                        
                        
                         /*Long inlineMax=values[0];
                         Long inlineMin=values[1];
                         Long inlineInc=values[2];
                         Long xlineMax=values[3];
                         Long xlineMin=values[4];
                         Long xlineInc=values[5];
                         Long dugShotMax=values[6];
                         Long dugShotMin=values[7];
                         Long dugShotInc=values[8];
                         Long dugChannelMax=values[9];
                         Long dugChannelMin=values[10];
                         Long dugChannelInc=values[11];
                         Long offsetMax=values[12];
                         Long offsetMin=values[13];
                         Long offsetInc=values[14];
                         Long cmpMax=values[15];
                         Long cmpMin=values[16];
                         Long cmpInc=values[17];
                        
                         
                         
                        Long inlineMax=new Long(0);
                        Long inlineMin=new Long(0);
                        Long inlineInc=new Long(0);
                        Long xlineMax=new Long(0);
                        Long xlineMin=new Long(0);
                        Long xlineInc=new Long(0);
                        Long dugShotMax=new Long(0);
                        Long dugShotMin=new Long(0);
                        Long dugShotInc=new Long(0);
                        Long dugChannelMax=new Long(0);
                        Long dugChannelMin=new Long(0);
                        Long dugChannelInc=new Long(0);
                        Long offsetMax=new Long(0);
                        Long offsetMin=new Long(0);
                        Long offsetInc=new Long(0);
                        Long cmpMax=new Long(0);
                        Long cmpMin=new Long(0);
                        Long cmpInc=new Long(0);
                         */
                         /*LongHolder lholder=new LongHolder();
                         keyValueThreadExtractor(hdr,lholder);*/
                        
                         /*hdr.setTraceCount(traceCount);
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
                        hdr.setOffsetInc(offsetInc);*/
                        
                        /*hdr.setCmpMax(lholder.cmpMax);
                        hdr.setCmpMin(lholder.cmpMin);
                        hdr.setCmpInc(lholder.cmpInc);
                        hdr.setInlineMax(lholder.inlineMax);
                        hdr.setInlineMin(lholder.inlineMin);
                        hdr.setInlineInc(lholder.inlineInc);
                        hdr.setXlineMax(lholder.xlineMax);
                        hdr.setXlineMin(lholder.xlineMin);
                        hdr.setXlineInc(lholder.xlineInc);
                        hdr.setDugShotMax(lholder.dugShotMax);
                        hdr.setDugShotMin(lholder.dugShotMin);
                        hdr.setDugShotInc(lholder.dugShotInc);
                        hdr.setDugChannelMax(lholder.dugChannelMax);
                        hdr.setDugChannelMin(lholder.dugChannelMin);
                        hdr.setDugChannelInc(lholder.dugChannelInc);
                        hdr.setOffsetMax(lholder.offsetMax);
                        hdr.setOffsetMin(lholder.offsetMin);
                        hdr.setOffsetInc(lholder.offsetInc);*/
                  
                        
                        
                        
                        for(Future f: futures){
                            f.get();
                        }
                         
        } /*catch (InterruptedException ex) {
        Logger.getLogger(DugioHeaderValuesExtractor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
        Logger.getLogger(DugioHeaderValuesExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }*/catch (Exception ex) {
        Logger.getLogger(DugioHeaderValuesExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
             
                    
                     /*
                     
                     }
                     return null;
                     }
                     }).get();*/
         
                     /*} catch (InterruptedException ex) {
                     Logger.getLogger(DugioHeaderValuesExtractor.class.getName()).log(Level.SEVERE, null, ex);
                     } catch (ExecutionException ex) {
                     Logger.getLogger(DugioHeaderValuesExtractor.class.getName()).log(Level.SEVERE, null, ex);
                     }
                     return null;*/
                     
                     
                     
                     
                     return null;
    }
    
    private String forEachKey(Headers hdr,String key) throws IOException {
                       /// System.out.println("Inside forEach key with key ="+key);
        
         
                 Process process=new ProcessBuilder(ds.getDugioHeaderValuesSh().getAbsolutePath(),volume.getAbsolutePath(),hdr.getSubsurface(),key).start();
                        InputStream is = process.getInputStream();
                        InputStreamReader isr=new InputStreamReader(is);
                        BufferedReader br=new BufferedReader(isr);
                        
                        String value;
                        while((value=br.readLine())!=null){
                        //    System.out.println("DHVEx: forEachKey Volume: "+volume+" sub: "+hdr.getSubsurface()+" key: "+key+" = "+value);
                            return value;
                        }
                       
           return null;
                 
                       
                        
    }
    
    private String forTraces(Headers hdr) throws IOException{
                      //  System.out.println("Inside forTraces key with NO key");
                        Process process=new ProcessBuilder(ds.getDugioGetTraces().getAbsolutePath(),volume.getAbsolutePath(),hdr.getSubsurface()).start();
                        InputStream is = process.getInputStream();
                        InputStreamReader isr=new InputStreamReader(is);
                        BufferedReader br=new BufferedReader(isr);
                        
                        String value;
                        while((value=br.readLine())!=null){
                         //   System.out.println("DHVEx: forTraces Volume: "+volume+" sub: "+hdr.getSubsurface()+" Traces ="+value+"");
                            return value;
                        }
                       
                        return null;
                        
    }
    
    
    
    private void keyValueExtractor(Headers hdr,Long[] headers) throws IOException{
        int keycount=0;
        for(String key:dmh.metaHeaders){
            
                        Process process=new ProcessBuilder(ds.getDugioHeaderValuesSh().getAbsolutePath(),volume.getAbsolutePath(),hdr.getSubsurface(),key).start();
                        InputStream is = process.getInputStream();
                        InputStreamReader isr=new InputStreamReader(is);
                        BufferedReader br=new BufferedReader(isr);
                        
                        String value;
                        while((value=br.readLine())!=null){
                        //    System.out.println("DHVEx: forEachKey Volume: "+volume+" sub: "+hdr.getSubsurface()+" key: "+key+" = "+value);
                            headers[keycount]=Long.valueOf(value);
                        }
          
                        keycount++;
        }
    }
    
    
    private void keyValueThreadExtractor(Headers hdr,LongHolder hdrValue) throws InterruptedException, ExecutionException{
        
        
        
        for(String key:hdrValue.ddmh.metaHeaders){
            
        
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
         executorService.submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                Process process=new ProcessBuilder(ds.getDugioHeaderValuesSh().getAbsolutePath(),volume.getAbsolutePath(),hdr.getSubsurface(),key).start();
                        InputStream is = process.getInputStream();
                        InputStreamReader isr=new InputStreamReader(is);
                        BufferedReader br=new BufferedReader(isr);
                        
                        String stringValue;
                        while((stringValue=br.readLine())!=null){
                        //    System.out.println("DHVEx: forEachKey Volume: "+volume+" sub: "+hdr.getSubsurface()+" key: "+key+" = "+value);
                            //return value;
//                     /       hdrValue=Long.valueOf(stringValue);
                            hdrValue.keyValueMap.put(key, Long.valueOf(stringValue));
                            
                            
                        }
                        return null;
            }
         }).get();
        }
    }
}





