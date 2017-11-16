/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.qcTable;

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
    TreeTableView<QcTableSequences> ttv;
    QcTableSequences selectedItem;
    int index;
    final CheckBox checkBox;
   // private ObservableValue<Boolean> objSelProperty;
    //private ObservableValue<Boolean> objIndProperty;
    
     private ObjectProperty<Boolean> objSelProperty;
    private ObjectProperty<Boolean> objIndProperty;
    
    private BooleanProperty booleanSel;
    private BooleanProperty booleanInd;
    
    ChangeListener<Boolean> listenerA;
    ChangeListener<Boolean> listenerB;
    
    

    CheckBoxCell(TreeTableColumn<QcTableSequences, Boolean> param, int ind) {

       checkBox=new CheckBox();
       checkBox.setAllowIndeterminate(true);
        index=ind;
       
       
      listenerA =new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            updateSel(newValue);
            
        }
    };
      listenerB =new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            updateInd(newValue);
            
        }

           
    };
      
      checkBox.selectedProperty().addListener(listenerA);
       checkBox.indeterminateProperty().addListener(listenerB);
       
       checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               
             //  index=getTreeTableRow().getIndex();
            //int sel=getTreeTableRow().getIndex();
                //selectedItem=param.getTreeTableView().getTreeItem(sel).getValue();
                selectedItem=getTreeTableRow().getItem();
               //System.out.println("fend.session.node.qcTable.CheckBoxCell.<init>(): selectedrow:  "+sel);
               
               
               System.out.println("fend.session.node.qcTable.CheckBoxCell.<init>(): selectedSeq:  "+selectedItem.getSequenceNumber());
               System.out.println("fend.session.node.qcTable.CheckBoxCell.<init>(): selectedSub:  "+selectedItem.getSubsurface());
               
             
               
           }
       }
       );
    }

    
    
    
    @Override
    protected void updateItem(Boolean qcstatus,boolean empty){
       
        super.updateItem(qcstatus, empty);
            if(empty ){
            setGraphic(null);
            }else{
                
                //selectedItem= getTreeTableRow().getItem();
               if(selectedItem!=null){
                   
              
               System.out.println("fend.session.node.qcTable.CheckBoxCell.updateItem(): called on "+selectedItem.getSequenceNumber()+" ,  "+selectedItem.getSubsurface()+" : "+qcstatus);
                System.out.println("fend.session.node.qcTable.CheckBoxCell.updateItem(): qcstatus is : "+qcstatus);
                  
                 checkBox.selectedProperty().bindBidirectional(selectedItem.getQctypes().get(index).getCheckUncheckProperty());
             //    checkBox.indeterminateProperty().bindBidirectional(selectedItem.getQctypes().get(index).getFailProperty());
                   }
                //checkBox.indeterminateProperty().bindBidirectional(selectedItem.getQctypes().get(index).getTriStateProperty());
              
               
               
               setGraphic(checkBox);
            
            }
    }
    
    
    private void updateSel(Boolean newValue){
         selectedItem=getTreeTableRow().getItem();
          
        if(selectedItem!=null){
                        
            selectedItem.getQctypes().get(index).setCheckUncheckProperty(newValue);
            checkBox.setIndeterminate(false);
            
        }
        
    }
    
    private void updateInd(Boolean newValue) {
        if(selectedItem!=null){
             selectedItem.getQctypes().get(index).setFailProperty(newValue);
        }
              
           }
    
}
