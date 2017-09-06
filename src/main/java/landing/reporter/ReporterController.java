/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.reporter;

import db.model.ObpManagerLog;
import db.services.ObpManagerLogService;
import db.services.ObpManagerLogServiceImpl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import landing.reporter.page.ReportPage;
//import org.openide.util.Exceptions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ReporterController extends Stage{
    private final String htmlLocation="/d/home/dubai0197/programming/php/submit_out.html";
    final File file=new File(htmlLocation);
    private URL url;
    //private String url="http://10.11.1.180/obpmanager/report.html";
    ReportPage page=new ReportPage();
    
    @FXML
    private WebView webView;

    ReporterModel model;
    ReporterNode node;
    ObpManagerLogService oserv=new ObpManagerLogServiceImpl();
    
    void setModel(ReporterModel lsm,ReporterNode aThis) throws FileNotFoundException {
       this.model=lsm;
      List<ObpManagerLog> logs=oserv.getObpManagerLogs();
      List<String> contents=new ArrayList<>();
        for (Iterator<ObpManagerLog> iterator = logs.iterator(); iterator.hasNext();) {
            ObpManagerLog next = iterator.next();
            String s=next.getTimeEntered()+" : "+next.getLevel()+" : "+next.getSourceClass()+" : "+next.getSourceMethod()+" : "+next.getMessage();
            contents.add(s);
        }
        String logContent=new String();
        System.out.println("landing.reporter.ReporterController.setModel(): Printing log contents");
        /*for (String content : contents) {
        System.out.println(content);
        content+="\n\'";
        logContent+=content;
        //htmlcontent+="\n";
        }*/
        for(int i=0;i<contents.size();i++){
            
            if(i==contents.size()-1){
               String content=contents.get(i);
           // content+="\'";
            logContent+="\'"+content; 
            break;
            }
            
            if(i==0){
                String content=contents.get(i);
            content+="\\n\' +\n";
            logContent+=content;
            continue;
            }
            
            
            String content=contents.get(i);
            content+="\\n\' +\n";
            logContent+="\'"+content;
            
            
        }
        
        
        page.setLogcontent(logContent);
        /*try {
        url=file.toURI().toURL();
        } catch (MalformedURLException ex) {
        Exceptions.printStackTrace(ex);
        }*/
        System.out.println("landing.reporter.ReporterController.setModel(): finished setting logContent to : "+logContent);
        Writer writer=null;
        try{
        writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        writer.write(page.getHtmlContent());
        }catch(IOException ioe){
        ioe.printStackTrace();
        }finally{
        try {
        writer.close();
        } catch (IOException ex) {
        // Exceptions.printStackTrace(ex);
        ex.printStackTrace();
        }
        }
        
         this.node=aThis;
         setView();
       
    }

    void setView(ReporterNode aThis) {
        node=aThis;
        final WebEngine webengine=webView.getEngine();
        //webengine.load(url);
        System.out.println("landing.reporter.ReporterController.setView(): Loading "+file.getAbsolutePath());
        //webengine.load(file.getAbsolutePath());
        webengine.load(page.getHtmlContent());
        this.setTitle("Reporter");
        this.setScene(new Scene(node));
        this.showAndWait();
    }

    private void setView() {
        final WebEngine webengine=webView.getEngine();
        //webengine.load(url);
        System.out.println("landing.reporter.ReporterController.setView(): Loading "+file.getAbsolutePath());
        webengine.loadContent(page.getHtmlContent());
       // webengine.load(page.getHtmlContent());
        this.setTitle("Reporter");
        this.setScene(new Scene(node));
        this.showAndWait();
    }
    
}
