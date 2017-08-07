/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.doubtoverride.entries.comments;

import fend.session.node.headers.doubtoverride.entries.comments.confirm.ConfirmModel;
import fend.session.node.headers.doubtoverride.entries.comments.confirm.ConfirmNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class CommentController extends Stage{

    CommentModel model;
    CommentNode node;
    
     @FXML
    private TextArea textArea;

    @FXML
    private Button okBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void cancelBtnHandle(ActionEvent event) {
        this.model.setStatus(Boolean.FALSE);
        close();
    }

    @FXML
    void okBtnHandle(ActionEvent event) {
        String comm=textArea.getText();
        
        ConfirmModel cmod=new ConfirmModel();
        ConfirmNode cnode=new ConfirmNode(cmod);
        
        if(cmod.getStatus() && comm.length()>0){
        
            System.out.println("fend.session.node.headers.doubtoverride.entries.comments.CommentController.okBtnHandle(): Proceeding with commit");
            this.model.setComment(comm);
            this.model.setStatus(Boolean.TRUE);
        close();
        
        }
        
    }
    
    
    void setModel(CommentModel lsm) {
        this.model=lsm;
        if(this.model.getComment().length()>0){
            textArea.setText(this.model.getComment());
        }
    }

    void setView(CommentNode aThis) {
           this.node=aThis;
        this.setTitle("Comment");
        this.setScene(new Scene(node));
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }
    
}
