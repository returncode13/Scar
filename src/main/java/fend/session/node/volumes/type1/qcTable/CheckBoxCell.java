/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type1.qcTable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTablePosition;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class CheckBoxCell extends TreeTableCell<QcTableSequences, Boolean> {
    TreeTableColumn<QcTableSequences, String> param;
    TreeTableView<QcTableSequences> ttv;
    final CheckBox checkBox=new CheckBox(){{
        setIndeterminate(true);
        allowIndeterminateProperty().set(true);
    }};
    /*  CheckBoxCell(TreeTableColumn<QcTableSequences, String> param1) {
    param=param1;
    checkBox.allowIndeterminateProperty().set(true);
    checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
    final String stat=checkBox.isIndeterminate()?"NotQc'd":checkBox.isSelected()?"OK":"FQ";
    System.out.println(".handle(): "+stat);
    System.out.println(".handle(): isEditing()"+isEditing());
    System.out.println(".handle(): QSeq: "+param.getTreeTableView());
    
    
    }
    });
    }
    
    CheckBoxCell(TreeTableView<QcTableSequences> treetable,final int ind) {
    checkBox.allowIndeterminateProperty().set(true);
    checkBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent event) {
    int selectedidx=getTreeTableRow().getIndex();
    QcTableSequences selectedItem= treetable.getTreeItem(selectedidx).getValue();
    final StringProperty stat=new SimpleStringProperty(checkBox.isIndeterminate()?"NotQc'd":checkBox.isSelected()?"OK":"FQ");
    //System.out.println(".handle(): "+stat+" slindex: "+selectedidx+" qcSeq: "+selectedItem.getSequenceNumber()+" sub: "+selectedItem.getSubsurface()+" field {name,value: "+selectedItem.getQcfields().get(ind).getName()+" , "+selectedItem.getQcfields().get(ind).getValue()+"}");
    //    selectedItem.getQcfields().get(ind).setValue(stat.get());
    //      System.out.println(".handle(): "+stat+" slindex: "+selectedidx+" qcSeq: "+selectedItem.getSequenceNumber()+" sub: "+selectedItem.getSubsurface()+" field {name,value: "+selectedItem.getQcfields().get(ind).getName()+" , "+selectedItem.getQcfields().get(ind).getValue()+"}");
    }
    });
    
    }
    
    CheckBoxCell(TreeTableColumn<QcTableSequences, String> param, final int ind) {
    
    checkBox.allowIndeterminateProperty().set(true);
    
    
    checkBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
    
    
    @Override
    public void handle(MouseEvent event) {
    //int selectedidx=getTreeTableRow().getIndex();
    // QcTableSequences selectedItem= param.getTreeTableView().getTreeItem(selectedidx).getValue();
    
    //final StringProperty stat=new SimpleStringProperty(checkBox.isIndeterminate()?"NotQc'd":checkBox.isSelected()?"OK":"FQ");
    //System.out.println(".handle(): "+stat+" slindex: "+selectedidx+" qcSeq: "+selectedItem.getSequenceNumber()+" sub: "+selectedItem.getSubsurface()+" field {name,value: "+selectedItem.getQcfields().get(ind).getName()+" , "+selectedItem.getQcfields().get(ind).getValue()+"}");
    // selectedItem.getQcfields().get(ind).setValue(stat.get());
    
    int sel=getTreeTableRow().getIndex();
    System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): selected: "+sel);
    QcTableSequences selectedItem=param.getTreeTableView().getTreeItem(sel).getValue();
    //checkBox.indeterminateProperty().bind(selectedItem.getQcfields().get(ind).notQcdProperty());
    //checkBox.selectedProperty().bind(selectedItem.getQcfields().get(ind).passQcProperty());
    selectedItem.getQcfields().get(ind).notQcdProperty().bindBidirectional(checkBox.indeterminateProperty());
    selectedItem.getQcfields().get(ind).passQcProperty().bindBidirectional(checkBox.selectedProperty());
    if(checkBox.isIndeterminate()){
    selectedItem.getQcfields().get(ind).setQcStatus("NQ");
    selectedItem.getQcfields().get(ind).setNotQcd(true);
    
    }else{
    if(checkBox.isSelected()){
    selectedItem.getQcfields().get(ind).setQcStatus("OK");
    selectedItem.getQcfields().get(ind).setPassQc(true);
    }else{
    selectedItem.getQcfields().get(ind).setQcStatus("FQ");
    selectedItem.getQcfields().get(ind).setPassQc(false);
    }
    }
    
    
    System.out.println(".handle(): seq: "+selectedItem.getSequenceNumber()+" sub: "+selectedItem.getSubsurface()+" field: "+selectedItem.getQcfields().get(ind).getName()+" nq: "+selectedItem.getQcfields().get(ind).getQcStatus());
    }
    });
    }*/
    BooleanProperty isselected=new SimpleBooleanProperty(false);
    BooleanProperty isindeterminate=new SimpleBooleanProperty(true);
    
    CheckBoxCell(TreeTableColumn<QcTableSequences, Boolean> param, int ind) {
        System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): new called: with params: "+param+" and iii: "+ind);
       checkBox.allowIndeterminateProperty().set(true);
       checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               int sel=getTreeTableRow().getIndex();
               System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): selectedrow:  "+sel);
               QcTableSequences selectedItem=param.getTreeTableView().getTreeItem(sel).getValue();
               System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): selectedSeq:  "+selectedItem.getSequenceNumber());
               System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): selectedSub:  "+selectedItem.getSubsurface());
               isselected.bindBidirectional(selectedItem.getQcfields().get(ind).passQcProperty());
               isindeterminate.bindBidirectional(selectedItem.getQcfields().get(ind).notQcdProperty());
               
               if(selectedItem.getQcfields().get(ind).isNotQcd()){
                   checkBox.setIndeterminate(true);
               }
               if(selectedItem.getQcfields().get(ind).isPassQc()){
                   checkBox.setSelected(false);
               }
               
               if(checkBox.isIndeterminate()){
                   isindeterminate.set(true);
                   isselected.set(false);
                   selectedItem.getQcfields().get(ind).setQcStatus("NQ");
                   selectedItem.getQcfields().get(ind).setNotQcd(true);
                   selectedItem.getQcfields().get(ind).setPassQc(false);
               }
               if(checkBox.isSelected()){
                   isselected.set(true);
                   isindeterminate.set(false);
                   selectedItem.getQcfields().get(ind).setQcStatus("OK");
                   selectedItem.getQcfields().get(ind).setPassQc(true);
                   selectedItem.getQcfields().get(ind).setNotQcd(false);
               }
               if(!checkBox.isSelected()){
                   isselected.set(false);
                   isindeterminate.set(false);
                   selectedItem.getQcfields().get(ind).setQcStatus("FQ");
                   selectedItem.getQcfields().get(ind).setPassQc(false);
                   selectedItem.getQcfields().get(ind).setNotQcd(false);
               }
           }
       }
       );
    }

    @Override
    protected void updateItem(Boolean qcstatus,boolean empty){
        super.updateItem(qcstatus, empty);
            if(empty){
            setGraphic(null);
            }else{
                /*switch(qcstatus){
                case "OK":
                checkBox.setSelected(true);
                break;
                case "FQ":
                checkBox.setSelected(false);
                break;
                case "NotQC'd":
                checkBox.setIndeterminate(true);
                break;
                }*/
               //checkBox.selectedProperty().bindBidirectional(isselected);
               //checkBox.indeterminateProperty().bindBidirectional(isindeterminate);
               checkBox.setSelected(isselected.get());
               checkBox.setIndeterminate(isindeterminate.get());
            setGraphic(checkBox);
            }
    }
    
}
