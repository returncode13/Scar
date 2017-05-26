/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.workflows;

import de.cismet.custom.visualdiff.WorkflowDifferenceModel;
import de.cismet.custom.visualdiff.WorkflowDifferenceNode;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;

/**
 *
 * @author adira0150
 */
public class WorkflowDifferenceFrameNode extends Stage {
    
    private WorkflowDifferenceFrameModel workflowDifferenceFrameModel;
    private WorkflowDifferenceNode workflowDifferenceNode;
    private WorkflowDifferenceModel workflowDifferenceModel;
    private final SwingNode swingnode;//=new SwingNode();
    
    public WorkflowDifferenceFrameNode(WorkflowDifferenceFrameModel workflowDifferenceFModel) {
        this.workflowDifferenceFrameModel = workflowDifferenceFModel;
        swingnode=new SwingNode();
        createSwingNode(swingnode);
        StackPane pane=new StackPane();
        pane.getChildren().add(swingnode);
        
        this.setTitle("Difference between workflow versions");
        this.setScene(new Scene(pane,750,700));
        this.showAndWait();
    }
    

    private void createSwingNode(SwingNode swingnode) {
        SwingUtilities.invokeLater(()->{
           
            WorkflowDifferenceModel differenceModel=workflowDifferenceFrameModel.getDifferenceModel();
            WorkflowDifferenceNode differenceNode=new WorkflowDifferenceNode(differenceModel);
            swingnode.setContent(differenceNode);
            swingnode.setStyle("workflows/landing.css");
            
        });
    }
    
    
   
}
