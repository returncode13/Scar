/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type4.listFiles;


import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ListFilesNode extends AnchorPane {
    private FXMLLoader fXMLLoader;
    private final URL location;
    private ListFilesController isc;
    
    public ListFilesNode(ListFilesModel ism){
         this.location=getClass().getClassLoader().getResource("nodeResources/volumes/type4/listFiles/fileList.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                isc=(ListFilesController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                isc.setModel(ism);
                isc.setView(this) ;
                
               
               
              
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
}
