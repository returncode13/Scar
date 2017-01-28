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
            
            
            //dugioGetTraces.deleteOnExit();
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
    
     
    
    
    
    
    
    
    
    
}
