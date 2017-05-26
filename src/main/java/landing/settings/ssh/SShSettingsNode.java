/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.settings.ssh;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath
 */
public class SShSettingsNode extends AnchorPane{
    
    private FXMLLoader fXMLLoader;
    private final URL location;
    private SShSettingsController lsc;
    
    public SShSettingsNode(SShSettings lsm)
    {
       
        //this.location=LandingController.class.getResource("landingView/LandingView.fxml"); 
        this.location=getClass().getClassLoader().getResource("landingResources/settings/ssh/settings.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                lsc=(SShSettingsController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                lsc.setModel(lsm);
                lsc.setView(this) ;
                
               
               
                System.out.println("landing.loadingSession.LoadSessionNode.<init>()");
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }



    public SShSettingsController getSettingsController() {
        return lsc;
    }
    
}
