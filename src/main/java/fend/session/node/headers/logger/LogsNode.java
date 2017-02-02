/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.logger;


import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author naila0152
 */
public class LogsNode extends AnchorPane{
    private FXMLLoader fXMLLoader;
    private final URL location;
    private LogsController lsc;
    
    public LogsNode(LogsModel lsm)
    {
       
        //this.location=LandingController.class.getResource("landingView/LandingView.fxml"); 
        this.location=getClass().getClassLoader().getResource("loggerResources/LogView.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                lsc=(LogsController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                lsc.setModel(lsm);
                lsc.setView(this) ;
                
               
               
                System.out.println("fend.session.node.headers.HeadersNode.<init>()");
                
            }catch(IOException e){
                throw new RuntimeException(e);
            } 
}

    public LogsController getLogsController() {
        return lsc;
    }
}
