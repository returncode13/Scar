/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.doubtoverride.entries.comments.confirm;

import db.handler.ObpManagerLogDatabaseHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    Logger logger=Logger.getLogger(ConfirmController.class.getName());
    ObpManagerLogDatabaseHandler obpManagerLogDatabaseHandler=new ObpManagerLogDatabaseHandler();
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
        try{
        this.model.setStatus(Boolean.FALSE);
            close();
        }catch(Exception ex){
            logger.severe(ex.getMessage());
        }
    }

    @FXML
    void confirmBtnHandle(ActionEvent event) {
        try{
        this.model.setStatus(Boolean.TRUE);
        close();
        }catch(Exception ex){
            logger.severe(ex.getMessage());
        }
    }

    public ConfirmController() {
        logger.addHandler(obpManagerLogDatabaseHandler);
        logger.setLevel(Level.SEVERE);
    }
    
    
    void setModel(ConfirmModel lsm) {
        try{
       this.model=lsm;
       textArea.setText(this.model.getConfirm());
        }catch(Exception ex){
            logger.severe(ex.getMessage());
        }
    }

    void setView(ConfirmNode aThis) {
        try{
        this.node=aThis;
        this.setTitle("Confirm");
        this.setScene(new Scene(node));
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
        }catch(Exception ex){
            logger.severe(ex.getMessage());
        }
    }
    
}
