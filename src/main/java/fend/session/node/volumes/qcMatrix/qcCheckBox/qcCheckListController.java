/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcMatrix.qcCheckBox;


import db.services.QcTypeService;
import db.services.QcTypeServiceImpl;
import fend.session.node.volumes.qcMatrix.qcCheckBox.addQcType.AddQcTypeModel;
import fend.session.node.volumes.qcMatrix.qcCheckBox.addQcType.AddQcTypeNode;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;

/**
 *
 * @author sharath nair
 */
public class qcCheckListController extends Stage{
    
    private qcCheckListModel model;
    private qcCheckListNode node;
            
    private ObservableList<String> items=FXCollections.observableArrayList();
    
    
    
    @FXML
    private CheckListView<String> checkListView=new CheckListView<>(items);
     
     @FXML
    private Button doneButton;
    
     @FXML
     private Button addButton;
    
    

    public ObservableList<String> getItems() {
        return items;
    }

    public void setItems(ObservableList<String> items) {
        this.items = items;
    }
    
    
    
    
    
    
    @FXML
     void handleOnDone(ActionEvent event){
        model.setCheckModel(checkListView.getCheckModel());
        model.setCheckedTypes(checkListView.getCheckModel().getCheckedItems());
        close();
        
        
    }
     
    @FXML
    void handleOnAdd(ActionEvent event){
        AddQcTypeModel acmodel=model.getAddQcTypeModel();
        AddQcTypeNode acnode=new AddQcTypeNode(acmodel);
        String nameToBeAdded=acmodel.getQctypeName();
        items.add(nameToBeAdded);
        
        
    }

    void setModel(qcCheckListModel m) {
        this.model=m;
        items=FXCollections.observableList(model.getQcTypes());
        
        checkListView.setItems(items);
        List<String> existingSelections=model.getCheckedTypes();
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

    void setView(qcCheckListNode aThis) {
        node=aThis;
        this.setScene(new Scene(node));
        this.showAndWait();
    }
}
