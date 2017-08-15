/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.doubtoverride;


import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class OverrideNode extends AnchorPane{
    
    private FXMLLoader fXMLLoader;
    private final URL location;
    private OverrideController lsc;
    
    public OverrideNode(OverrideModel lsm)
    {
       
        //this.location=LandingController.class.getResource("landingView/LandingView.fxml"); 
        this.location=getClass().getClassLoader().getResource("nodeResources/headers/override/override.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                lsc=(OverrideController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                lsc.setModel(lsm);
                lsc.setView(this) ;
                
               
               
                System.out.println("fend.session.node.headers.doubtoverride.OverrideNode.<init>()");
                
            }catch(IOException e){
                throw new RuntimeException(e);
            } 
}
}
