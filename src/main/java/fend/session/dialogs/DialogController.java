/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.dialogs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair
 * sharath.nair@polarcus.com
 */
public class DialogController extends Stage {
    DialogModel model;
    DialogNode node;

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button okButton;
    
    
    @FXML
    void onOKClick(ActionEvent event) {
        close();
    }
    
    void setModel(DialogModel item) {
        this.model=item;
        textArea.setText(model.getMessage());
                
    }

    void setView(DialogNode aThis) {
        this.node=aThis;
        this.setTitle("Message");
        this.setScene(new Scene(node));
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
        
    }
}
