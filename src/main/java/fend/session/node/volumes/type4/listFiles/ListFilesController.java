/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type4.listFiles;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import fend.session.node.jobs.nodeproperty.JobModelProperty;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import fend.session.node.volumes.type4.VolumeSelectionModelType4;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ListFilesController extends Stage{

   
    ListFilesModel model;
    ListFilesNode node;
    ObservableList<Text> textList;
    Integer from;
    Integer to;
    
    @FXML
    private ListView<String> fileListView;

    @FXML
    private TextFlow textFlow;

    @FXML
    private TextField seqFromTF;

    @FXML
    private TextField seqToTF;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;
    
    
    
    

    @FXML
    void handleOK(ActionEvent event) {
            model.setFrom(from);
            model.setTo(to);
             System.out.println("fend.session.node.volumes.type4.listFiles.ListFilesController.handleOK(): setting values inside model of From: "+from+" : TO: "+to);
             List<JobModelProperty> jobProps=((VolumeSelectionModelType4)this.model.getVmodel0()).getParentjob().getJobProperties();
             for (Iterator<JobModelProperty> iterator = jobProps.iterator(); iterator.hasNext();) {
            JobModelProperty jp = iterator.next();
            if(jp.getPropertyName().equals("from")){
                jp.setPropertyValue(new String(""+this.from));
            }
            if(jp.getPropertyName().equals("to")){
                jp.setPropertyValue(new String(""+this.to));
            }
        }
             
             
             close();
    }

    @FXML
    void onCancel(ActionEvent event) {
            close();
    }

    @FXML
    void seqFromKeyReleased(KeyEvent event) {
        try{
        from=Integer.valueOf(seqFromTF.getText());
        //from--;               //BB
        }catch(NumberFormatException nfe){
            from=null;
            List<Node> nodL=textFlow.getChildren();
            for (Node node1 : nodL) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
        }
        
        
        System.out.println("fend.session.node.volumes.type4.listFiles.ListFilesController.seqFromKeyReleased()  from: "+from);
        
        if(from!=null && to==null){
            List<Node> nodL=textFlow.getChildren();
            for (Node node1 : nodL) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
            
            ((Text)textFlow.getChildren().get(from)).setFill(Color.PURPLE);
        }
        
        if(from!=null && to!=null){
            List<Node> nodL=textFlow.getChildren();
            for (Node node1 : nodL) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
       
        
        if(to>=from){
            
            int i=from;
            while(i<=to){
                try{
                    ((Text)textFlow.getChildren().get(i++)).setFill(Color.PURPLE);
                }catch(ArrayIndexOutOfBoundsException ai){
                    List<Node> nodL2=textFlow.getChildren();
                    for (Node node1 : nodL2) {
                        if(node1 instanceof Text){
                            ((Text) node1).setFill(Color.BLACK);
                        }
                    }
                }
                
            }
                  
            
            
        }
        
        if(to<from){
            
           // ((Text)textFlow.getChildren().get(from)).setFill(Color.PURPLE);
            System.out.println("fend.session.node.volumes.type4.listFiles.ListFilesController.seqToKeyReleased(): \"to\" value cannot be less than \"from\"");
            List<Node> nodLl=textFlow.getChildren();
            for (Node node1 : nodLl) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
        }
        
         }
        
        
    }
    

    @FXML
    void seqToKeyReleased(KeyEvent event) {
        try{
        to=Integer.valueOf(seqToTF.getText());
        //to--;   //BB
        }catch(NumberFormatException nfe){
            to=null;
            List<Node> nodL=textFlow.getChildren();
            for (Node node1 : nodL) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
        }
       
        if(to!=null && from==null){
            List<Node> nodL=textFlow.getChildren();
            for (Node node1 : nodL) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
            
            ((Text)textFlow.getChildren().get(to)).setFill(Color.PURPLE);
        }
        
        
        if(to!=null && from!=null){
        System.out.println("fend.session.node.volumes.type4.listFiles.ListFilesController.seqToKeyReleased() to: "+to);
        List<Node> tnodes=new ArrayList<>();
        /*Node nodeFrom=textFlow.getChildren().get(from);
        Node nodeTo=textFlow.getChildren().get(to);*/
        
        List<Node> nodL=textFlow.getChildren();
            for (Node node1 : nodL) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
            
        
        if(to>=from){
            
            int i=from;
            while(i<=to){
                try{
                    ((Text)textFlow.getChildren().get(i++)).setFill(Color.PURPLE);
                }catch(ArrayIndexOutOfBoundsException ai){
                    List<Node> nodL2=textFlow.getChildren();
                    for (Node node1 : nodL2) {
                        if(node1 instanceof Text){
                            ((Text) node1).setFill(Color.BLACK);
                        }
                    }
                }
            }
                  
            
            
        }
        if(to<from)
        {
           // ((Text)textFlow.getChildren().get(to)).setFill(Color.PURPLE);
            List<Node> nodLl=textFlow.getChildren();
            for (Node node1 : nodLl) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
            System.out.println("fend.session.node.volumes.type4.listFiles.ListFilesController.seqToKeyReleased(): \"to\" value cannot be less than \"from\"");
        }
        }

    }
    
    
    
    void setModel(ListFilesModel m) {
        this.model=m;
        fileListView.setItems(model.getFileobs());
        List<String> charsForTF=model.getCharsForTextFlow();
        textList=FXCollections.observableArrayList();
        for (String s : charsForTF) {
            textList.add(new Text(s));
            
        }
        textFlow.getChildren().addAll(textList);
        
        
    }

    void setView(ListFilesNode aThis) {
        this.node=aThis;
        this.setTitle("Determine sequence from file name");
        this.setScene(new Scene(node));
        this.showAndWait();
    }

    
    
    
    
    
}
