/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.doubtoverride.entries.comments.confirm;

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
public class ConfirmController extends Stage{

    ConfirmModel model;
    ConfirmNode node;
    
    
    @FXML
    private TextArea textArea;
    
     @FXML
    private Button confirmBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void cancelBtnHandle(ActionEvent event) {
        this.model.setStatus(Boolean.FALSE);
            close();
    }

    @FXML
    void confirmBtnHandle(ActionEvent event) {
        this.model.setStatus(Boolean.TRUE);
        close();
    }
    
    void setModel(ConfirmModel lsm) {
       this.model=lsm;
       textArea.setText(this.model.getConfirm());
    }

    void setView(ConfirmNode aThis) {
        this.node=aThis;
        this.setTitle("Confirm");
        this.setScene(new Scene(node));
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();       
    }
    
}
