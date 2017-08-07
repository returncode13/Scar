/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.doubtoverride;

import fend.session.node.headers.doubtoverride.entries.Entries;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class OverrideController extends Stage {

    OverrideModel model;
    OverrideNode node;
    Map<Entries,Boolean> commitMap=new HashMap<>();
    
   @FXML
    private TableView<Entries> tableView;

    @FXML
    private Button overrideBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void cancelBtnHandle(ActionEvent event) {
        close();
    }

    @FXML
    void overrideBtnHandle(ActionEvent event) {
            for (Map.Entry<Entries, Boolean> entry : commitMap.entrySet()) {
            Entries key = entry.getKey();
            Boolean value = entry.getValue();
            
                if(value) System.out.println("fend.session.node.headers.doubtoverride.OverrideController.overrideBtnHandle(): Overriding: "+key.getSubsurface()+" Dtype: "+key.getDoubtType()+" DStatus : "+key.getStatus()+" to override ");
            
        }
            close();
    }

   
    
   

    void setModel(OverrideModel lsm) {
        this.model=lsm;
        
        TableColumn subsurfaceCol=new TableColumn("Subsurface");
        TableColumn doubtMessageCol=new TableColumn("Message");
        TableColumn doubtStatusCol=new TableColumn("Status");
        TableColumn doubtTypeCol=new TableColumn("Type");
        TableColumn overrideButton=new TableColumn();
        
        
        subsurfaceCol.setCellValueFactory(new PropertyValueFactory("subsurface"));
        doubtMessageCol.setCellValueFactory(new PropertyValueFactory("errorMessage"));
        doubtStatusCol.setCellValueFactory(new PropertyValueFactory("status"));
        doubtTypeCol.setCellValueFactory(new PropertyValueFactory("doubtType"));
        
        
        overrideButton.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Entries,Boolean>,ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Entries, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue()!=null);
            }
        });
        
        
        overrideButton.setCellFactory(new Callback<TableColumn<Entries,Boolean>,TableCell<Entries,Boolean>>() {
            @Override
            public TableCell<Entries, Boolean> call(TableColumn<Entries, Boolean> param) {
                return new ButtonCell(tableView,commitMap);
            }
           
        });
        
        
        tableView.getColumns().addAll(subsurfaceCol,doubtTypeCol,doubtStatusCol,doubtMessageCol,overrideButton);
        ObservableList<Entries> dataForTable=this.model.getObsentries();
        
        tableView.setItems(dataForTable);
                
    }
    
    
    
     void setView(OverrideNode aThis){ 
            this.node=aThis;
        this.setTitle("Override");
        this.setScene(new Scene(node));
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }
    
}
