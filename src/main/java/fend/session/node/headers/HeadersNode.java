/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath
 */
public class HeadersNode extends AnchorPane{
  private FXMLLoader fXMLLoader;
    private final URL location;
    private HeadersViewController lsc;
    
    public HeadersNode(HeadersModel lsm,int selectionrow)
    {
       
        //this.location=LandingController.class.getResource("landingView/LandingView.fxml"); 
        this.location=getClass().getClassLoader().getResource("nodeResources/headers/headersView.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                lsc=(HeadersViewController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                lsc.setModel(lsm,selectionrow);
                lsc.setView(this) ;
                
               
               
                System.out.println("fend.session.node.headers.HeadersNode.<init>()");
                
            }catch(IOException e){
                throw new RuntimeException(e);
            } 
}

    public HeadersViewController getHeadersViewController() {
        return lsc;
    }
     
    
}
