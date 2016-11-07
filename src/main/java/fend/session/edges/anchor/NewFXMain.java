/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.edges.anchor;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author naila0152
 */
public class NewFXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
      
        
        AnchorModel ancM=new AnchorModel();
        ancM.setCenterX(100.0);
        ancM.setCenterY(100.0);
       
        
        
        Anchor anc=new Anchor(Color.AQUA, ancM);
       
       
       
       
       
       
       
Group root =new Group();
      root.getChildren().add(anc);
        
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
      
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
