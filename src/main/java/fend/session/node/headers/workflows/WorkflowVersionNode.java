/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.workflows;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author adira0150
 */
public class WorkflowVersionNode extends AnchorPane{
     private FXMLLoader fXMLLoader;
    private final URL location;
    private WorkflowVersionController workflowVersionController;
    
    public WorkflowVersionNode(WorkflowVersionModel lsm)
    {
       
        //this.location=LandingController.class.getResource("landingView/LandingView.fxml"); 
        this.location=getClass().getClassLoader().getResource("workflows/workflowWindow1.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                workflowVersionController=(WorkflowVersionController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                workflowVersionController.setModel(lsm);
                workflowVersionController.setView(this) ;
                
               
               
                System.out.println("fend.session.node.headers.WorkflowVersionNode.<init>()");
                
            }catch(IOException e){
                throw new RuntimeException(e);
            } 
    }

    public WorkflowVersionController getWorkflowVersionController() {
        return workflowVersionController;
    }
    
    
}
