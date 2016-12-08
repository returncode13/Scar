 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.overview;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.GridView;

/**
 *
 * @author sharath
 */
public class OverviewController extends Stage{
       @FXML
    private GridView<OverviewItem> gridview;
       
       private OverviewModel model;
       private OverviewNode node;

    void setModel(OverviewModel lsm) {
        this.model=lsm;
        gridview=new GridView<>();
        
        gridview.getItems().addAll(model.getOverviewItemList());
        gridview.setCellFactory(new OverviewCellFactory());
    }

    void setView(OverviewNode aThis) {
        this.node=aThis;
        this.setScene(new Scene(node));
        this.showAndWait();
        
       
    }

}
