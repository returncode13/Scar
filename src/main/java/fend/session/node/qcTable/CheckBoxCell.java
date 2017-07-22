/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.qcTable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TreeItem;
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
    TreeTableColumn<QcTableSequences, Boolean> param;
    TreeTableView<QcTableSequences> ttv;
    QcTableSequences selectedItem;
    int index;
    final CheckBox checkBox=new CheckBox(){{
        setIndeterminate(true);
        allowIndeterminateProperty().set(true);
    }};
    
    BooleanProperty isselected=new SimpleBooleanProperty(false);
    BooleanProperty isindeterminate=new SimpleBooleanProperty(true);
    
    
    
    /*CheckBoxCell(TreeTableColumn<QcTableSequences, String> param, int ind) {
    System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): new called: with params: "+param.getCellData(ind)+" and iii: "+ind);
    checkBox.allowIndeterminateProperty().set(true);
    checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
    int sel=getTreeTableRow().getIndex();
    index=getTreeTableRow().getIndex();
    System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): selectedrow:  "+sel);
    selectedItem=param.getTreeTableView().getTreeItem(sel).getValue();
    
    
    /* if(selectedItem.getQcfields().get(ind).isPassQc()){
    checkBox.setSelected(true);
    }
    if(!selectedItem.getQcfields().get(ind).isPassQc()){
    checkBox.setSelected(false);
    }
    if(selectedItem.getQcfields().get(ind).isNotQcd()){
    checkBox.setIndeterminate(true);
    }*/
    /*
    if(checkBox.isIndeterminate()){
        selectedItem.getQcfields().get(ind).setQcStatus("NQ");
    }
    if(checkBox.isSelected()){
        selectedItem.getQcfields().get(ind).setQcStatus("OK");
    }
    if(!checkBox.isSelected() && !checkBox.isIndeterminate()){
        selectedItem.getQcfields().get(ind).setQcStatus("FQ");
    }
    
    
    System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): selectedSeq:  "+selectedItem.getSequenceNumber());
    System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): selectedSub:  "+selectedItem.getSubsurface());
    System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): qcstatus:  "+selectedItem.getQcfields().get(ind).getQcStatus());
    System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): passQc:  "+selectedItem.getQcfields().get(ind).isPassQc());
    System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): notQc:  "+selectedItem.getQcfields().get(ind).isNotQcd());
    isselected.bindBidirectional(selectedItem.getQcfields().get(ind).passQcProperty());
    isindeterminate.bindBidirectional(selectedItem.getQcfields().get(ind).notQcdProperty());
}
}
);
}*/

    CheckBoxCell(TreeTableColumn<QcTableSequences, Boolean> param, int ind) {
//        System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): new called: with params: "+param.get+" and iii: "+ind);
       checkBox.allowIndeterminateProperty().set(true);
       checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               int sel=getTreeTableRow().getIndex();
               index=getTreeTableRow().getIndex();
               System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): selectedrow:  "+sel);
               selectedItem=param.getTreeTableView().getTreeItem(sel).getValue();
                              
               
              
               if(checkBox.isIndeterminate()){
                   //selectedItem.getQcfields().get(ind).setQcStatus("NQ");
                }
               if(checkBox.isSelected()){
                  // selectedItem.setTp(true);
                }
               if(!checkBox.isSelected() && !checkBox.isIndeterminate()){
                   //selectedItem.setTp(false);
               }
               
               
               System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): selectedSeq:  "+selectedItem.getSequenceNumber());
               System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): selectedSub:  "+selectedItem.getSubsurface());
               /*  System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): qcstatus:  "+selectedItem.getQctypes().get(ind).getQcStatus());
               System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): passQc:  "+selectedItem.getQctypes().get(ind).isPassQc());
               System.out.println("fend.session.node.volumes.qcTable.CheckBoxCell.<init>(): notQc:  "+selectedItem.getQctypes().get(ind).isNotQcd());*/
               //isselected.bindBidirectional(selectedItem.getQcfields().get(ind).passQcProperty());
               //isindeterminate.bindBidirectional(selectedItem.getQcfields().get(ind).notQcdProperty());
               /*checkBox.setSelected(selectedItem.isTp());
               isselected.bind(selectedItem.tpProperty());*/
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
                /* switch(qcstatus){
                case "OK":
                checkBox.setSelected(true);
                break;
                case "FQ":
                checkBox.setSelected(false);
                break;
                case "NQ":
                checkBox.setIndeterminate(true);
                break;
                }*/
            //  isselected.bind(checkBox.);
               //checkBox.indeterminateProperty().bindBidirectional(isindeterminate);
               /*checkBox.setSelected(isselected.get());
               checkBox.setIndeterminate(isindeterminate.get());*/
                Boolean treeItem=getItem();
               System.out.println("fend.session.node.volumes.type1.qcTable.CheckBoxCell.updateItem(): called on "+qcstatus);
               /*if(qcstatus)checkBox.setSelected(true);
               else checkBox.setSelected(false);*/
               checkBox.setSelected(isselected.get());
            setGraphic(checkBox);
            }
    }
    
}
