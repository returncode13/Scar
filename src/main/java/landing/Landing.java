
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing;


import db.handler.ObpManagerLogDatabaseHandler;
import fend.session.SessionModel;
import fend.session.SessionNode;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author naila0152
 */
public class Landing extends Application {
    
    Logger logger=Logger.getLogger(Landing.class.getName());
    ObpManagerLogDatabaseHandler obpManagerLogDatabaseHandler=new ObpManagerLogDatabaseHandler();
    
    
    
    @Override 
    public void start(Stage primaryStage) {
        System.out.println("landing.Landing.start(): clearing previous logs..");
        obpManagerLogDatabaseHandler.clear();  //clear existing logs. start a new log entry in db
        
        LogManager.getLogManager().reset();
        logger.addHandler(obpManagerLogDatabaseHandler);
        logger.setLevel(Level.ALL);
        
        try{
       LandingNode ln=new LandingNode(new LandingModel());
        }catch(NullPointerException npe){
            logger.severe("Null pointer exception caught ");
        }
        catch(Exception ex){
            logger.severe(ex.getMessage());
        }
       /* Scene scene = new Scene(ln);
       
       primaryStage.setScene(scene);
       primaryStage.show();*/
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
