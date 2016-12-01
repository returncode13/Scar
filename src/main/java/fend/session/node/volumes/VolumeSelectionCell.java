/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

/**
 *
 * @author naila0152
 */
public class VolumeSelectionCell extends ListCell<VolumeSelectionModel> {
private static int i=0;

    public VolumeSelectionCell() {++i;
      //  System.out.println(" created a new VolumeSelectionCell "+i);
    }

    @Override
    protected void updateItem(VolumeSelectionModel item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
    
        
      //  System.out.println("VSCell: isEmpty:"+empty);
       //if(item!=null){System.out.println("VSCell: Item# "+i+" is NOT null: isInflated: "+item.isInflated());}
       // else
        {
           // System.out.println("VSCell: Item# "+i+" is null ");
        }
                
        
        if(item!=null && item.isInflated()){
            
          // System.out.println("VSCell: inflating xml "+i+" headerisDisabled: " + item.getHeaderButtonDisabledStatusProperty().get()+" label: "+item.getLabel() +" isEmpty: "+empty);
            URL location =getClass().getClassLoader().getResource("nodeResources/volumes/VolumeSelectionView_1.fxml");
            
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            
            try{
                Node root=(Node)fxmlLoader.load(location.openStream());
                VolumeSelectionController controller=(VolumeSelectionController)fxmlLoader.getController();
               if(item.getId()==null){
                setId(UUID.randomUUID().getMostSignificantBits()+"");
               }
               else{
                   setId(item.getId()+"");
               }
                controller.setId(Long.valueOf(getId()));
                
                controller.setModel(item);
                setGraphic(root);
                
            }catch(IOException e){
                throw new IllegalStateException(e);
                
            }
        }
    
    }
    
    
    
}
