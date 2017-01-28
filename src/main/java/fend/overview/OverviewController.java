 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.overview;

import java.util.Iterator;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
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
        
        System.out.println("fend.overview.OverviewController.setModel(): Models contents: ");
        List<OverviewItem> ls=model.getOverviewItemList();
        for (Iterator<OverviewItem> iterator = ls.iterator(); iterator.hasNext();) {
            OverviewItem next = iterator.next();
            System.out.println("fend.overview.OverviewController.setModel():  name= "+next.getName());
            
        }
        gridview.getItems().addAll(model.getOverviewItemList());
        gridview.setCellFactory(new OverviewCellFactory());
    }

    void setView(OverviewNode aThis) {
        this.node=aThis;
        VBox vb=new VBox();
        
        this.setScene(new Scene(vb,400,600));
        vb.getChildren().add(gridview);
        vb.setStyle("-fx-background-color: black");
        this.showAndWait();
        
       
    }

}
