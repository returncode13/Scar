/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.workflows;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author adira0150
 */
public class WorkflowVersionController extends Stage implements Initializable{
    private WorkflowVersionModel model;
    private WorkflowVersionNode node;
    ObservableList<Long> versionsObsList;

    @FXML
    private TabPane tabpane;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }

    void setModel(WorkflowVersionModel lsm) {
        model=lsm;
        List<WorkflowVersionTabModel> tabContents=model.getWfmodel();
        System.out.println("fend.session.node.headers.workflows.WorkflowVersionController.setModel(): tabListsize: "+tabContents.size());
        for (Iterator<WorkflowVersionTabModel> iterator = tabContents.iterator(); iterator.hasNext();) {
            WorkflowVersionTabModel versionsTab = iterator.next();
            String wfcontent=versionsTab.getWorkflowvContent();
            System.out.println("fend.session.node.headers.workflows.WorkflowVersionController.setModel(): contents: "+wfcontent);
            Tab tab=new Tab();
            tab.setText("     "+versionsTab.getVersion().toString()+"     " );
            HBox hbox=new HBox();
            TextArea ta=new TextArea();
            ta.wrapTextProperty().setValue(Boolean.TRUE);
            ta.setText(wfcontent);
            ta.setEditable(Boolean.FALSE);
            ta.prefHeightProperty().bind(hbox.heightProperty());
            ta.prefWidthProperty().bind(hbox.widthProperty());
            hbox.getChildren().add(ta);
            hbox.setAlignment(Pos.CENTER);
            tab.setContent(hbox);
            tabpane.getTabs().add(tab);
            
        }
    }

    void setView(WorkflowVersionNode aThis) {
        this.node=aThis;
        this.setScene(new Scene(node));
        this.showAndWait();
    }
    
}
