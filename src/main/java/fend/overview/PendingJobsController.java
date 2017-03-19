/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.overview;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 *
 * @author naila0152
 */
public class PendingJobsController extends Stage{

    PendingJobsModel pmodel;
    PendingJobsNode pnode;
    @FXML
    private ListView<String> pendingList;

    
    void setModel(PendingJobsModel lsm) {
            pmodel=lsm;
            pendingList.getItems().addAll(pmodel.getPendingjobs());
    }

    void setView(PendingJobsNode aThis) {
        pnode=aThis;
        this.setScene(new Scene(pnode));
       this.showAndWait();
    }
    
}
