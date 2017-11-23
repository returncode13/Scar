/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.qcTable;

import java.util.Iterator;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTablePosition;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class CheckBoxCell extends TreeTableCell<QcTableSequences, Boolean> {
    TreeTableColumn<QcTableSequences, Boolean> param;
    
    QcTableSequences selectedItem;
    int index;
    final CheckBox checkBox;
   
    

    CheckBoxCell(TreeTableColumn<QcTableSequences, Boolean> param, int ind) {
        
       this.param=param;
       checkBox=new CheckBox();
       checkBox.setAllowIndeterminate(true);
       index=ind;
       
      
      
      checkBox.selectedProperty().addListener((observable,oldValue,newValue)->{
          int sel=getTreeTableRow().getIndex();
          selectedItem=this.param.getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
          
          selectedItem.getQctypes().get(index).getCheckUncheck().set(newValue);
          selectedItem.getQctypes().get(index).getIndeterminate().set(false);
          
          if(selectedItem instanceof QcTableSequences){
            
                List<QcTableSubsurfaces> subs=selectedItem.getQcSubs();
                for (Iterator<QcTableSubsurfaces> iterator = subs.iterator(); iterator.hasNext();) {
                    QcTableSubsurfaces next = iterator.next();
                    next.getQctypes().get(index).getCheckUncheck().set(newValue);
                    next.getQctypes().get(index).getIndeterminate().set(false);
                }
          
          }
          
          
          
      });
      
      
       checkBox.indeterminateProperty().addListener((observable,oldValue,newValue)->{
          int sel=getTreeTableRow().getIndex();
          selectedItem=this.param.getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
         // selectedItem.getQctypes().get(index).getIndeterminate().set(newValue);
         
          /*if(selectedItem instanceof QcTableSequences){
          // selectedItem.getQctypes().get(index).getIndeterminate().set(newValue);
          }*/
          if(newValue && selectedItem instanceof QcTableSubsurfaces){
              ((QcTableSubsurfaces)selectedItem).getQcTableSeq().getQctypes().get(index).getIndeterminate().set(newValue);
              selectedItem.getQctypes().get(index).getIndeterminate().set(newValue);
          }
      });
       
    }

    
    
    
    @Override
    protected void updateItem(Boolean qcstatus,boolean empty){
       super.updateItem(qcstatus, empty);
            
            if(empty){
                setGraphic(null);
            }else{
              
             Boolean value=getItem();
                
                if(value==null){
                    checkBox.setIndeterminate(true);
                   
                }
                else{
                    checkBox.setIndeterminate(false);
                    checkBox.setSelected(qcstatus);
                 
                }
               setGraphic(checkBox);
                getStylesheets().add("styles/checkcustom.css");
            }
    }
    
    
    
}
