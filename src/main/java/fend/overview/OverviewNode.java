/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.overview;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;
import landing.loadingSession.LoadSessionController;
import landing.loadingSession.LoadSessionModel;

/**
 *
 * @author sharath
 */
public class OverviewNode extends AnchorPane{
    private FXMLLoader fXMLLoader;
    private final URL location;
    private OverviewController lsc;
    
    public OverviewNode(OverviewModel lsm)
    {
       
        //this.location=LandingController.class.getResource("landingView/LandingView.fxml"); 
        this.location=getClass().getClassLoader().getResource("overviewResources/overview.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                lsc=(OverviewController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                lsc.setModel(lsm);
                lsc.setView(this) ;
                
               
               
                System.out.println("fend.overview.OverviewNode.<init>()");
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }



    public OverviewController getOverviewController() {
        return lsc;
    }
}
