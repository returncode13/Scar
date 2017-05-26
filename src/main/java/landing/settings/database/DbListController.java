/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.settings.database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 *
 * @author adira0150
 */
public class DbListController extends Stage{

    private DbListModel model;
    private DbListNode node;
    
    @FXML
    private ListView<String> databaseList=new ListView<>();
     
     @FXML
    private Button doneButton;
    
    
    @FXML
    void onDone(ActionEvent event) {
        model.setSelectedDatabase(databaseList.getSelectionModel().getSelectedItem());
        close();
    }
    
    void setModel(DbListModel lsm) {
            model=lsm;
            databaseList.getItems().addAll(model.getAvailableDatabases());
    }

    void setView(DbListNode aThis) {
        node=aThis;
        this.setScene(new Scene(node));
       this.showAndWait();
    }
    
}
