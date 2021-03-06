/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dugex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.attribute.PosixFilePermission;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author thirteen
 */
public class DugioScripts implements Serializable{
    private File getSubsurfaces;
    private File dugioGetHeaderList;
    private File dugioHeaderValues;
    private File getTimeSubsurfaces;
    private File dugioGetTraces;
    private File subsurfaceLog;
    private File logStatusCompletedSuccessfully;
    private File logStatusErrored;
    private File logStatusCancelled;
    private File workflowExtractor;
    private File p190TimeStampLineNameExtractor;
    private File segdLoadNotesTxtTimeWorkflowExtractor;
    private File segdLoadLinenameTimeFromGCLogs;
    private File segdLoadSaillineInsightFromGCLogs;
    private File segdLoadCheckIfGCLogsFinished;
    
    
    private String getSubsurfacesContent="#!/bin/bash\nls $1|grep \"\\.0$\" | grep -o \".[[:alnum:]]*.[_[:alnum:]]*[^.]\"\n";
    private String dugioGetHeaderListContent="#!/bin/bash\n"
            + "module add prod\n"
            + "dugio md_list file=$1 line=$2";
    private String dugioHeaderValuesContent="#!/bin/bash\n"
            + "module add prod\n"
            + "dugio md_get file=$1 line=$2 key=$3";
    private String dugioTracesContent = "#!/bin/bash\n"
            + "dugio summary file=$1 line=$2 | grep  Traces |grep -oP [[:digit:]]+|head -1";
            
           // + "dugio summary file=$1 line=$2 | grep  Traces |grep -oP '\\d+\\s+'\n";
    
    //LEAVE this COMMENTED                                                               //private String getTimeSubsurfacesContent="#!/bin/bash\n"
                                                                                         //        + "ls -ltr --time-style=+%Y%m%d%H%M%S $1|grep \"\\.0$\" | grep -o \"[[:digit:]]\{14\}.[_[:alnum:]-]*[^.]\"\n";
    
    //LEAVE THIS COMMENTED                                                                /*  private String getTimeSubsurfacesContent="#!/bin/bash\n"
                                                                                         // + "ls -ltr --time-style=+%Y%m%d%H%M%S $1|grep \"\\.0$\" | grep -o \"[[:digit:]]\\{14\\}.[_[:alnum:]-]*[^.]\"\n";*/
    
    
    
     private String getTimeSubsurfacesContent="#!/bin/bash\n"
    + "ls -ltr --time-style=+%Y%m%d%H%M%S $1|grep \".[[:digit:]].single.idx\" | grep -o \"[[:digit:]]\\{14\\}.[_[:alnum:]-]*[^.]\" | sed s/2D-//\n";
     
     /*private String subsurfaceLogContent ="#!/bin/bash\n" +
     "grep -A 0 lineName $1 | awk '{ print $1\" \"$2\" \"$7}'";*/
    
     /*private String subsurfaceLogContent ="#!/bin/bash\n" +
     "for i in $1/*; do  grep -A 0 lineName $i /dev/null  ; done | awk '{ print $1 \" \" $2 \" \"  $7}' | sort -k 1,2";*/
    
     
     /*private String subsurfaceLogContent="#!/bin/bash\n" +
     "for i in $1/*; do awk '/lineName/VERSION/ {print FILENAME \" \"$0}' ORS=\" \" $i | awk '{print $1\" \"$2\" \"$3\" \"$8\" Version=\"$15\"-\"$16}'; done | sort -k 2,3";*/
    
   /*  private String subsurfaceLogContent="#!/bin/bash\n" +
"for i in $1/*; do awk '/lineName|VERSION/ { print FILENAME\" \" $0}' ORS=\" \" $i | awk '{$4=$5=$6=$7=$9=$10=$11=$12=$13=\"\"; print $0}';done | sort -k 2,3";*/
     
     
      private String subsurfaceLogContent="#!/bin/bash\n" +
"for i in $1/*; do sed '100q;2,100p;d' $i | awk '/lineName|VERSION/ {print  \"'$i' \"$0}' ORS=\" \"  | awk '{$4=$5=$6=$7=$9=$10=$11=$12=$13=\"\"; print $0}' ;done";
      
      private String logstatusContentCompletedSuccessfully="#!/bin/bash\n" +
" tail -100 $1 |awk '/JOB.*END.*STATUS=0/ {count++} END {if(count>0) print \"'$1' \"count}' ";
      
      private String logstatusContentErrored="#!/bin/bash\n" +
" tail -100 $1 |awk '/JOB.*END.*STATUS=1/ {count++} END {if(count>0) print \"'$1' \"count}' ";
      
      private String logstatusContentCancelled="#!/bin/bash\n" +
" tail -100 $1 |awk '/CANCELLED/ {count++} END {if(count>0) print \"'$1' \"count}' ";
      
      private String workflowExtractorContents="#!/bin/bash\n" +
"sed -n '/Process parameters:/,/Executing/p'  $1 |  awk '{$1=$2=$3=\"\";print $0}'";
     
      
      /*private String p190TimeStampLineNameExContents="#!/bin/bash\n" +
      "for i in $1; do  ls -ltr --time-style=+%Y%m%d%H%M%S $i | grep -o \"[[:digit:]]\\{14\\}.[[:alnum:]]*\"; done";*/
      /*   private String p190TimeStampLineNameExContents="#!/bin/bash\n" +
      "ls -ltr --time-style=+%Y%m%d%H%M%S $1 | awk '{print $6 \" \" $7}'|sed 's/....$//' | awk '{print $2 \" \" $1}'";*/
      
      
      private String p190TimeStampLineNameExContents="#!/bin/bash\n" +
"ls -ltr --time-style=+%Y%m%d%H%M%S $1 | awk '{print $7 \" \" $6}'";
       /*
       for extraction of workflow and times from notes.txt
       */
       private String segdLoadNotesTxtTimeContents="#!/bin/bash\n" +                
"var=$(grep -w Time $1/notes.txt | cut -c6-)\n" +
"content=$(cat $1/notes.txt)\n" +
"echo $var \"  Contents: \" $content";
       
       /*
       check if the gun_cable.logs under segdloadVolume/logs folder has finished updating
       */
       private String segdLoadCheckIfGCLogsFinishedContents="#!/bin/bash\n" +
"tail -1 $1| grep -q Finished ;echo $?";
       
       /*
       for extraction of time linename from the gun_cable.logs under segdloadVolume/logs folder
       */
       private String segdLoadLineNameTimeMappingFromGunCableLogsContents="#!/bin/bash\n" +
"grep Finished $1 | awk '{print $5\"-\"$6\"-\"$7\"T\" $8\" \"$13}'|sed 's/.$//'|sed 's/]//'";
       
     /*
       for extracting sailline-insight mapping from the gun_cable.logs under segdloadVolume/logs folder
       */
       private String segdLoadSaillineInsightFromGCLogsContents="#!/bin/bash\n" +
"grep -B 1 Started  $1 | awk '{print $2 $13}' | sed 's/://' |grep [[:alnum:]]|sed '$!N;s/\\n/ /' ";
       
     public DugioScripts()
    {
        try {
            getTimeSubsurfaces=File.createTempFile("getTimeSubsurfaces", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(getTimeSubsurfaces));
            bw.write(getTimeSubsurfacesContent);
            bw.close();
            getTimeSubsurfaces.setExecutable(true, false);
            
            
            
            getTimeSubsurfaces.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
        
        try {
            getSubsurfaces=File.createTempFile("getSubsurfaces", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(getSubsurfaces));
            bw.write(getSubsurfacesContent);
            bw.close();
            getSubsurfaces.setExecutable(true, false);
            
            
            
            getSubsurfaces.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            dugioGetHeaderList=File.createTempFile("dugioGetHeaderList", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(dugioGetHeaderList));
            bw.write(dugioGetHeaderListContent);
            bw.close();
            dugioGetHeaderList.setExecutable(true,false);
            
            
            dugioGetHeaderList.deleteOnExit();
            
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            dugioHeaderValues=File.createTempFile("dugioHeaderValues", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(dugioHeaderValues));
            bw.write(dugioHeaderValuesContent);
            bw.close();
            dugioHeaderValues.setExecutable(true,false);
            
            
            dugioHeaderValues.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            dugioGetTraces=File.createTempFile("dugioTracesContent", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(dugioGetTraces));
            bw.write(dugioTracesContent);
            bw.close();
            dugioGetTraces.setExecutable(true,false);
            
            
            dugioGetTraces.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            subsurfaceLog=File.createTempFile("subsurfaceLog", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(subsurfaceLog));
            bw.write(subsurfaceLogContent);
            bw.close();
            subsurfaceLog.setExecutable(true,false);
            
            
           subsurfaceLog.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            logStatusCompletedSuccessfully=File.createTempFile("logStatusCompletedSuccess", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(logStatusCompletedSuccessfully));
            bw.write(logstatusContentCompletedSuccessfully);
            bw.close();
            logStatusCompletedSuccessfully.setExecutable(true,false);
            
            logStatusCompletedSuccessfully.deleteOnExit();
           //subsurfaceLog.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            logStatusErrored=File.createTempFile("logStatusErrored", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(logStatusErrored));
            bw.write(logstatusContentErrored);
            bw.close();
            logStatusErrored.setExecutable(true,false);
            
            logStatusErrored.deleteOnExit();
           //subsurfaceLog.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            logStatusCancelled=File.createTempFile("logStatusCancelled", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(logStatusCancelled));
            bw.write(logstatusContentCancelled);
            bw.close();
            logStatusCancelled.setExecutable(true,false);
            
            logStatusCancelled.deleteOnExit();
           //subsurfaceLog.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            workflowExtractor=File.createTempFile("workflowExtractor", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(workflowExtractor));
            bw.write(workflowExtractorContents);
            bw.close();
            workflowExtractor.setExecutable(true,false);
            
            workflowExtractor.deleteOnExit();
           //subsurfaceLog.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            p190TimeStampLineNameExtractor=File.createTempFile("p190TimeStampLineNameExtractor", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(p190TimeStampLineNameExtractor));
            bw.write(p190TimeStampLineNameExContents);
            bw.close();
            p190TimeStampLineNameExtractor.setExecutable(true,false);
            
            p190TimeStampLineNameExtractor.deleteOnExit();
           //subsurfaceLog.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            segdLoadNotesTxtTimeWorkflowExtractor=File.createTempFile("segdLoadNotesTxtTimeWorkflowExtractor", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(segdLoadNotesTxtTimeWorkflowExtractor));
            bw.write(segdLoadNotesTxtTimeContents);
            bw.close();
            segdLoadNotesTxtTimeWorkflowExtractor.setExecutable(true,false);
            
            //segdLoadNotesTxtTimeWorkflowExtractor.deleteOnExit();
           //subsurfaceLog.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            segdLoadLinenameTimeFromGCLogs=File.createTempFile("segdLoadLinenameTimeFromGCLogs", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(segdLoadLinenameTimeFromGCLogs));
            bw.write(segdLoadLineNameTimeMappingFromGunCableLogsContents);
            bw.close();
            segdLoadLinenameTimeFromGCLogs.setExecutable(true,false);
            segdLoadLinenameTimeFromGCLogs.deleteOnExit();
            //segdLoadNotesTxtTimeWorkflowExtractor.deleteOnExit();
           //subsurfaceLog.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            segdLoadSaillineInsightFromGCLogs=File.createTempFile("segdLoadSaillineInsightFromGCLogs", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(segdLoadSaillineInsightFromGCLogs));
            bw.write(segdLoadSaillineInsightFromGCLogsContents);
            bw.close();
            segdLoadSaillineInsightFromGCLogs.setExecutable(true,false);
            segdLoadSaillineInsightFromGCLogs.deleteOnExit();
            //segdLoadNotesTxtTimeWorkflowExtractor.deleteOnExit();
           //subsurfaceLog.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            segdLoadCheckIfGCLogsFinished=File.createTempFile("segdLoadCheckIfGCLogsFinished", ".sh");
            BufferedWriter bw = new BufferedWriter(new FileWriter(segdLoadCheckIfGCLogsFinished));
            bw.write(segdLoadCheckIfGCLogsFinishedContents);
            bw.close();
            segdLoadCheckIfGCLogsFinished.setExecutable(true,false);
            segdLoadCheckIfGCLogsFinished.deleteOnExit();
            //segdLoadNotesTxtTimeWorkflowExtractor.deleteOnExit();
           //subsurfaceLog.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(DugioScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    
    public File getSubsurfacesSh()
    {//System.out.println("getSubSurfaces Executed: "+getSubsurfaces.getAbsolutePath());
        return getSubsurfaces;}
    public File getDugioGetHeaderListSh()
    {//System.out.println("dugioGetHeaderList executed: "+dugioGetHeaderList.getAbsolutePath());
        return dugioGetHeaderList;}
    public File getDugioHeaderValuesSh()
    {//System.out.println("dugioHeaderValues executed: "+dugioHeaderValues.getAbsolutePath());
        return dugioHeaderValues;}
    public File getGetTimeSubsurfaces() 
    {//System.out.println("getTimeSubSurfaces Executed: "+getTimeSubsurfaces.getAbsolutePath());
        return getTimeSubsurfaces;}

    public File getDugioGetTraces() {
        return dugioGetTraces;
    }

    public File getSubsurfaceLog() {
        return subsurfaceLog;
    }

    public File getLogStatusCompletedSuccessfully() {
        return logStatusCompletedSuccessfully;
    }

    public File getLogStatusErrored() {
        return logStatusErrored;
    }

    public File getLogStatusCancelled() {
        return logStatusCancelled;
    }

    public File getWorkflowExtractor() {
        return workflowExtractor;
    }

    public File getP190TimeStampLineNameExtractor() {
        return p190TimeStampLineNameExtractor;
    }

    public File getSegdLoadNotesTxtTimeWorkflowExtractor() {
        return segdLoadNotesTxtTimeWorkflowExtractor;
    }

    public File getSegdLoadLinenameTimeFromGCLogs() {
        return segdLoadLinenameTimeFromGCLogs;
    }

    public File getSegdLoadSaillineInsightFromGCLogs() {
        return segdLoadSaillineInsightFromGCLogs;
    }

    public File getSegdLoadCheckIfGCLogsFinished() {
        return segdLoadCheckIfGCLogsFinished;
    }
    
    
    
    
    
    
     
    
    
    
    
    
    
    
    
}
