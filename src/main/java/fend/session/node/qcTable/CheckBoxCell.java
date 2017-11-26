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
import landing.AppProperties;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

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
          
          String passQcString=new String();
          if(newValue){
              passQcString=Boolean.TRUE.toString();
          }else{
              passQcString=Boolean.FALSE.toString();
          }
          
          String indString=QcTypeModel.isInDeterminate;
          
          int sel=getTreeTableRow().getIndex();
          String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
          
          selectedItem=this.param.getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
          selectedItem.getQctypes().get(index).getIndeterminate().set(false);
          selectedItem.getQctypes().get(index).getCheckUncheck().set(newValue);
          
          selectedItem.setUpdateTime(updateTime);
          selectedItem.getQctypes().get(index).setPassQc(passQcString);
          System.out.println("fend.session.node.qcTable.CheckBoxCell.<init>(): selectedProperty(): "+newValue);
          
          /* if(selectedItem.isParent()){                      //reflect parents selection to the children . update the time on the children.
          updateDownwards(passQcString,updateTime);
          }
          
          if(selectedItem.isChild()){
          updateUpwards(updateTime);
          
          }*/
       
          
        
          
      });
      
      
       checkBox.indeterminateProperty().addListener((observable,oldValue,newValue)->{
           //System.out.println("fend.session.node.qcTable.CheckBoxCell.<init>() indeterminate: old: "+oldValue+" new: "+newValue);
          String indeterminateString=QcTypeModel.isInDeterminate;
          int sel=getTreeTableRow().getIndex();
          String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
          selectedItem=this.param.getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
          selectedItem.setUpdateTime(updateTime);
          selectedItem.getQctypes().get(index).getIndeterminate().set(newValue);
          /* if(selectedItem.isParent()){
          //updateDownwards(updateTime);
          }
          if(selectedItem.isChild()){
          //updateUpwards(updateTime);
          }*/
          getTreeTableView().refresh();
      });
     
       
       
       checkBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
           @Override
           public void handle(MouseEvent event) {
               
             
               String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
               
               int sel=getTreeTableRow().getIndex();
               selectedItem=CheckBoxCell.this.param.getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
                
                if(selectedItem.isParent()){
                    selectedItem.updateChildren=true;
                    for(QcTableSubsurfaces child: selectedItem.getQcSubs()){
                        child.updateParent=false;
                    }
                    
                    updateDownwards(updateTime);
                }
                
                if(selectedItem.isChild()){
                    selectedItem.getQcTableSeq().updateChildren=false;
                    selectedItem.updateParent=true;
                    updateUpwards(updateTime);
                }
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
                
                if(qcstatus==null){
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
    
     private void updateUpwards(String updateTime){
             String indString=QcTypeModel.isInDeterminate;
             String passQcString=new String();
            if(selectedItem.updateParent){
              
            List<QcTableSubsurfaces> children=selectedItem.getQcSubs();
            int indeterminateCount=0;
            int selectedCount=0;
           
                QcTableSequences parent=children.get(0).getQcTableSeq();
                for(QcTableSubsurfaces child:children){
                    indeterminateCount+=child.getQctypes().get(index).getIndeterminate().get()?1:0;
                    selectedCount+=child.getQctypes().get(index).getCheckUncheck().get()?1:0;
                    
                }
                //System.out.println(selectedItem.getName().get()+" updating parent: indcount: "+indeterminateCount+" selectCount: "+selectedCount);    
                if(indeterminateCount>0) {
                    
                     parent.getQctypes().get(index).getIndeterminate().set(true);
                     parent.getQctypes().get(index).setPassQc(indString);
                     parent.setUpdateTime(updateTime);
                }
                else if(indeterminateCount==0 && selectedCount==children.size()){
                    passQcString=Boolean.TRUE.toString();
                    parent.getQctypes().get(index).getIndeterminate().set(false);
                    parent.getQctypes().get(index).getCheckUncheck().set(true);
                    parent.getQctypes().get(index).setPassQc(passQcString);
                    parent.setUpdateTime(updateTime);
                }else{
                    passQcString=Boolean.FALSE.toString();
                    parent.getQctypes().get(index).getIndeterminate().set(false);
                    parent.getQctypes().get(index).getCheckUncheck().set(false);
                    parent.getQctypes().get(index).setPassQc(passQcString);
                    parent.setUpdateTime(updateTime);
                }
             //   updateParent=false;
            }
            CheckBoxCell.this.param.getTreeTableView().refresh();
            
           
        }
     
     private void updateDownwards(String updateTime){
            List<QcTableSubsurfaces> children=selectedItem.getQcSubs();
            String passQcString=new String();
            if(!selectedItem.getQctypes().get(index).getIndeterminate().get()){
                    if(selectedItem.getQctypes().get(index).getCheckUncheck().get()){
                        passQcString=Boolean.TRUE.toString();
                    }else{
                        passQcString=Boolean.FALSE.toString();
                    }
                }else{
                passQcString=QcTypeModel.isInDeterminate;
            }
             if(selectedItem.isParent() && selectedItem.updateChildren ){
                     
                 for(QcTableSubsurfaces child:children){
                   child.getQctypes().get(index).getCheckUncheck().set(selectedItem.getQctypes().get(index).getCheckUncheck().get());
                   child.getQctypes().get(index).getIndeterminate().set(selectedItem.getQctypes().get(index).getIndeterminate().get());
                   child.getQctypes().get(index).setPassQc(passQcString);
                   child.setUpdateTime(updateTime);
                 }
                
              
             }
             CheckBoxCell.this.param.getTreeTableView().refresh();
             
        }
    
}
