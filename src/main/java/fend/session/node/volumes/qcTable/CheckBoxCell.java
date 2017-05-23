/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcTable;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableView;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class CheckBoxCell extends TreeTableCell<QcTableSequences, String> {

    TreeTableView<QcTableSequences> ttv;
    final CheckBox checkBox=new CheckBox();
    CheckBoxCell(TreeTableView<QcTableSequences> treetable) {
        this.ttv=treetable;
        checkBox.setIndeterminate(true);
        
    }
    
}
