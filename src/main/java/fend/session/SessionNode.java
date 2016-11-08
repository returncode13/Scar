/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session;

import java.io.IOException;
import java.net.URL;
import java.rmi.server.UID;
import java.util.UUID;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author naila0152
 */
public class SessionNode extends AnchorPane{
    
    private SessionController snc;
    private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public SessionNode(SessionModel item){
        this.location=getClass().getClassLoader().getResource("sessionResources/SessionView.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                snc=(SessionController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
                if(item.getId()==null)
                {
                setId(UUID.randomUUID().getMostSignificantBits()+"");
                
                }
                else
                {
                    setId(item.getId()+"");
                    
                }
                snc.setId(Long.valueOf(getId()));
                snc.setModel(item);
                snc.setView(this) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }

    public SessionController getSessionController() {
        return snc;
    }
    
    
    
}
