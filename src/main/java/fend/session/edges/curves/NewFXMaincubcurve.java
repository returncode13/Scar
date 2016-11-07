/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.edges.curves;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author naila0152
 */
public class NewFXMaincubcurve extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        CubCurveModel cm = new CubCurveModel();
        cm.getmStartXProperty().set(100.2);
        cm.getmStartYProperty().set(100.0);
        cm.getmEndXProperty().set(200.0);;
        cm.getmEndYProperty().set(256.0);
        
        CubCurve cc=new CubCurve(cm);
        Group root=new Group();
        root.getChildren().add(cc);
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
