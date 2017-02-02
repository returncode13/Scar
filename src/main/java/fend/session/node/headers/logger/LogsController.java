/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author naila0152
 */
public class LogsController extends Stage implements Initializable{
    
    private LogsModel lmodel;
    private LogsNode lnode;
    ObservableList<Long> versionsObsList;
    
    
    @FXML
    private TabPane tabPane;

    void setModel(LogsModel lsm) {
        lmodel=lsm;
        List<VersionLogsModel> tabContents=lmodel.getLogsmodel();
        
        for (Iterator<VersionLogsModel> iterator = tabContents.iterator(); iterator.hasNext();) {
            VersionLogsModel versionsTab = iterator.next();
            File logfile=versionsTab.getLogfile();
            System.out.println("fend.session.node.headers.logger:  Am trying to read the logfile  "+logfile.getAbsolutePath());
            FileReader fr=null;
            BufferedReader br= null;
            List<String> contents=new ArrayList<>();
            try {
                br = new BufferedReader(new BufferedReader(new FileReader(logfile)));
                
                String lines;
                while((lines=br.readLine())!=null){
                    contents.add(lines+"\n");
                };
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LogsController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LogsController.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try{
                    if(br!=null)br.close();
                    if(fr!=null)fr.close();
                    
                } catch (IOException ex) {
                    Logger.getLogger(LogsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            Tab tab= new Tab();
            tab.setText("      "+versionsTab.getVersion().toString()+"      ");
            HBox hbox=new HBox();
            //hbox.getChildren().add(new Label(""+contents));
            TextArea ta=new TextArea();
            
            ta.wrapTextProperty().setValue(Boolean.TRUE);
            String ss=new String();
            for(String s:contents){
                //if(s.matches("\\d{4}-\\d{2}-\\d{2}"))ss+="\n";
                ss+=s;
            }
            ta.setText(ss);
            ta.setEditable(Boolean.FALSE);
            ta.prefHeightProperty().bind(hbox.heightProperty());
            ta.prefWidthProperty().bind(hbox.widthProperty());
            hbox.getChildren().add(ta);
            ta.prefHeightProperty().bind(hbox.heightProperty());
            hbox.setAlignment(Pos.CENTER);
            tab.setContent(hbox);
            tabPane.getTabs().add(tab);
        }
        
    }

    void setView(LogsNode aThis) {
       lnode=aThis;
       this.setScene(new Scene(lnode));
       this.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
