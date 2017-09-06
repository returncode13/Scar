/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.dialogs;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair 
 * sharath.nair@polarcus.com
 */
public class DialogNode extends AnchorPane{
    private DialogController snc;
    private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public DialogNode(DialogModel item){
        this.location=getClass().getClassLoader().getResource("sessionResources/dialog/dialog.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                snc=(DialogController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
                
                
                snc.setModel(item);
                snc.setView(this) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
    
}
