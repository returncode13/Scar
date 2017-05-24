/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcTable;

import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class CheckBoxCell extends TreeTableCell<QcTableSequences, String> {

    //TreeTableView<QcTableSequences> ttv;
    final CheckBox checkBox=new CheckBox(){{
        setIndeterminate(true);
        allowIndeterminateProperty().set(true);
    }};
    CheckBoxCell() {
       
        checkBox.allowIndeterminateProperty().set(true);
        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               final String stat=checkBox.isIndeterminate()?"NotQc'd":checkBox.isSelected()?"OK":"FQ";
                System.out.println(".handle(): "+stat);
                QcTableSequences item=getTreeTableRow().getItem();
               
                System.out.println(".handle(): selected QcSeq "+item.getSequence().getSequenceNumber()+" sub: "+item.getSubsurface());
                
                //getTreeTableRow().getItem().getQcfields().get(selected).setValue(stat);
            }
        });
    }

    @Override
    protected void updateItem(String qcstatus,boolean empty){
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
            setGraphic(checkBox);
            }
    }
    
}
