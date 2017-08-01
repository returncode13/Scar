/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type4.listFiles;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import java.util.ArrayList;
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
    }

    @FXML
    void onCancel(ActionEvent event) {
            close();
    }

    @FXML
    void seqFromKeyReleased(KeyEvent event) {
        from=Integer.valueOf(seqFromTF.getText());
        
        System.out.println("fend.session.node.volumes.type4.listFiles.ListFilesController.seqFromKeyReleased()  from: "+from);
        Node node=textFlow.getChildren().get(from);
        if(to>=from){
            
            int i=from;
            while(i<=to){
                ((Text)textFlow.getChildren().get(i++)).setFill(Color.PURPLE);
            }
                  
            
            
        }else{
            
            ((Text)textFlow.getChildren().get(from)).setFill(Color.PURPLE);
            System.out.println("fend.session.node.volumes.type4.listFiles.ListFilesController.seqToKeyReleased(): \"to\" value cannot be less than \"from\"");
        }
        
        
    }

    @FXML
    void seqToKeyReleased(KeyEvent event) {
        to=Integer.valueOf(seqToTF.getText());
       
        System.out.println("fend.session.node.volumes.type4.listFiles.ListFilesController.seqToKeyReleased() to: "+to);
        List<Node> tnodes=new ArrayList<>();
        Node nodeFrom=textFlow.getChildren().get(from);
        Node nodeTo=textFlow.getChildren().get(to);
        if(to>=from){
            
            int i=from;
            while(i<=to){
                ((Text)textFlow.getChildren().get(i++)).setFill(Color.PURPLE);
            }
                  
            
            
        }else{
            ((Text)textFlow.getChildren().get(to)).setFill(Color.PURPLE);
            System.out.println("fend.session.node.volumes.type4.listFiles.ListFilesController.seqToKeyReleased(): \"to\" value cannot be less than \"from\"");
        }

    }
    
    void setModel(ListFilesModel m) {
        this.model=m;
        fileListView.setItems(model.getObs());
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
