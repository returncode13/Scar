/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type1.qcTable.qcCheckBox.addQcType;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author adira0150
 */
public class AddQcTypeController extends Stage{
    private AddQcTypeModel model;
    private AddQcTypeNode node;
    
    
    
    @FXML
    private Button doneBtn;

    @FXML
    private TextField textf;

    @FXML
    void onDone(ActionEvent event) {
        String name=textf.getText();
        model.setQctypeName(name);
        close();
    }

    void setModel(AddQcTypeModel m) {
       this.model=m;
    }

    void setView(AddQcTypeNode aThis) {
        node=aThis;
        this.setScene(new Scene(node));
        this.showAndWait();
    }
    
    
   
}
