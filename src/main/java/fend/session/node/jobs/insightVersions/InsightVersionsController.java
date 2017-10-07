/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.insightVersions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.controlsfx.control.CheckListView;

/**
 *
 * @author sharath
 */

 





public class InsightVersionsController extends Stage{
     private ObservableList<String> items=FXCollections.observableArrayList();
    
    @FXML
    private CheckListView<String> checkListView=new CheckListView<>(items);
     
     @FXML
    private Button doneButton;
    
    
    private InsightVersionsNode isnode; 
    private InsightVersionsModel ismodel;
     
   
   

   

    public ObservableList<String> getItems() {
        return items;
    }

    public void setItems(ObservableList<String> items) {
        this.items = items;
        checkListView.setItems(items);
    }

    void setModel(InsightVersionsModel m) {
        ismodel=m;
        items=FXCollections.observableArrayList(ismodel.getVersions());
        Collections.sort(items);
        for (Iterator<String> iterator = items.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            System.out.println("fend.session.node.jobs.insightVersions.InsightVersionsController.setModel():   Versions "+next);
            
        }
        
        
        checkListView.setItems(items);
        
        List<String> existingSelections = ismodel.getCheckedVersions();
        if(existingSelections!=null){
                                     for (Iterator<String> iterator = existingSelections.iterator(); iterator.hasNext();) {
                                        String next = iterator.next();
                                        checkListView.getCheckModel().check(next);
            
                                    }
        }
        
        checkListView.getItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                c.next();
                if (c.wasAdded()) {
                    checkListView.getSelectionModel().select(c.getAddedSubList().get(0));
                }
            }
        });

        // On CheckBox event
        checkListView.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                c.next();
                if(c.wasAdded()) {
                    System.out.println("Item Checked : " + c.getAddedSubList().get(0));
                } else if (c.wasRemoved()) {
                    System.out.println("Item Unchecked : " + c.getRemoved().get(0));
                }
            }
        });
        
        
        
    }
    
    
     void setView(InsightVersionsNode aThis) {
        isnode=aThis;
        
        this.setScene(new Scene(isnode));
        this.showAndWait();
        
    }
    
    
     @FXML
    void handleOnDone(ActionEvent event) {
        ismodel.setCheckModel(checkListView.getCheckModel());
        ismodel.setCheckedVersions(checkListView.getCheckModel().getCheckedItems());
        
         close();
    }
     
    
}
