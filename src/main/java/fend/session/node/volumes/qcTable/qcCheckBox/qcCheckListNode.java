/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcTable.qcCheckBox;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair
 */
public class qcCheckListNode extends AnchorPane{
     private FXMLLoader fXMLLoader;
    private final URL location;
    private qcCheckListController isc;
    
    public qcCheckListNode(qcCheckListModel ism){
         this.location=getClass().getClassLoader().getResource("nodeResources/volumes/qctable/qcCheckBox/qcCheckBox.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                isc=(qcCheckListController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                isc.setModel(ism);
                isc.setView(this) ;
                
               
               
                System.out.println("fend.session.node.volumes.qctable.qcCheckBox.qcCheckListNode.<init>()");
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
}
