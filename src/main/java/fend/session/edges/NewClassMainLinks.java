/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.edges;

import fend.session.edges.anchor.AnchorModel;
import fend.session.edges.curves.CubCurveModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author naila0152
 */
public class NewClassMainLinks extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        AnchorModel startM= new AnchorModel();
        startM.setCenterX(100.0);
        startM.setCenterY(100.0);
        
        AnchorModel endM= new AnchorModel();
        endM.setCenterX(200.0);
        endM.setCenterY(86.3);
        
        CubCurveModel cm=new CubCurveModel();
        
        
        LinksModel lm=new LinksModel(startM, endM, cm);
        
        
        
       // Links ln=new Links(startM, endM, cm);
        Links ln = new Links(lm);
        
        Scene scene = new Scene(ln,400.0, 200.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
