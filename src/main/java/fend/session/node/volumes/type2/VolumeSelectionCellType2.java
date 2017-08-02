/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type2;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class VolumeSelectionCellType2 extends ListCell<VolumeSelectionModelType2>{
    private static int i=0;

    public VolumeSelectionCellType2() {++i;
      //  System.out.println(" created a new VolumeSelectionCellType1 "+i);
    }

    @Override
    protected void updateItem(VolumeSelectionModelType2 item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
    
        
      //  System.out.println("VSCell: isEmpty:"+empty);
       //if(item!=null){System.out.println("VSCell: Item# "+i+" is NOT null: isInflated: "+item.isInflated());}
       // else
        {
           // System.out.println("VSCell: Item# "+i+" is null ");
        }
                
        
        if(item!=null && item.isInflated()){
            
          // System.out.println("VSCell: inflating xml "+i+" headerisDisabled: " + item.getHeaderButtonDisabledStatusProperty().get()+" label: "+item.getLabel() +" isEmpty: "+empty);
            URL location =getClass().getClassLoader().getResource("nodeResources/volumes/type2/VolumeSelectionViewType2_1.fxml");
            
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            
            try{
                Node root=(Node)fxmlLoader.load(location.openStream());
                VolumeSelectionControllerType2 controller=(VolumeSelectionControllerType2)fxmlLoader.getController();
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
