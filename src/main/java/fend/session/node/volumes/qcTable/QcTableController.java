/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcTable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author sharath nair
 */
public class QcTableController extends Stage {
    QcTableModel model;
    QcTableNode node;
    @FXML
    private TreeTableView<QcTableSequences> treetable;

    void setModel(QcTableModel lsm) {
        this.model=lsm;
        TreeTableColumn<QcTableSequences,Long> sequenceNumber=new TreeTableColumn<>("Sequence");
        sequenceNumber.setCellValueFactory(new TreeItemPropertyValueFactory<>("sequenceNumber"));
        TreeTableColumn<QcTableSequences,String> subsurface=new TreeTableColumn<>("Subsurface");
        subsurface.setCellValueFactory(new TreeItemPropertyValueFactory<>("subsurface"));
        List<QcTypeModel> qctypes=model.getQctypes();
        List<TreeTableColumn<QcTableSequences,String>> cols=new ArrayList<>();
        for (Iterator<QcTypeModel> iterator = qctypes.iterator(); iterator.hasNext();) {
            QcTypeModel qctype = iterator.next();
            TreeTableColumn<QcTableSequences,String> qctypeCol=new TreeTableColumn<>(qctype.getName());
           // qctypeCol.setEditable(true);
            
           /*qctypeCol.setCellFactory(new Callback<TreeTableColumn<QcMatrixSequences, String>, TreeTableCell<QcMatrixSequences, String>>() {
           @Override
           public TreeTableCell<QcMatrixSequences, String> call(TreeTableColumn<QcMatrixSequences, String> param) {
           // return new CheckBoxCell(treetable);
           
           }
           });*/
           /*qctypeCol.setCellFactory(new Callback<TreeTableColumn<QcTableSequences, String>, TreeTableCell<QcTableSequences, String>>() {
           @Override
           public TreeTableCell<QcTableSequences, String> call(TreeTableColumn<QcTableSequences, String> param) {
           final CheckBoxTableCell<QcTableSequences,String> cell=new CheckBoxTableCell<>();
           final BooleanProperty selected= new SimpleBooleanProperty();
           cell.setSelectedStateCallback(new Callback<Integer, ObservableValue<Boolean>>() {
           @Override
           public ObservableValue<Boolean> call(Integer param) {
           
           }
           });
           }
           });*/
            
            cols.add(qctypeCol);
            
        }
        treetable.getColumns().add(sequenceNumber);
        treetable.getColumns().add(subsurface);
        treetable.getColumns().addAll(cols);
        
        List<TreeItem<QcTableSequences>> treeSeq=new ArrayList<>();
        for(QcTableSequences qcMatrixSequences: model.getQcMatrixSequences()){
            List<String> qctypeValuesAtSeq=new ArrayList<>();
            TreeItem<QcTableSequences> seqroot=new TreeItem<>(qcMatrixSequences);
            List<QcTableSubsurfaces> qcsubs=qcMatrixSequences.getQcSubs();
            for(QcTableSubsurfaces qcsub:qcsubs){
                TreeItem<QcTableSequences> subItem=new TreeItem<>(qcsub);
                seqroot.getChildren().add(subItem);
            }
            treeSeq.add(seqroot);
        }
        TreeItem<QcTableSequences> root=new TreeItem<>();
        root.getChildren().addAll(treeSeq);
        treetable.setRoot(root);
        treetable.setShowRoot(false);
        
        
    }

    void setView(QcTableNode aThis) {
        node=aThis;
        this.setTitle("QC Matrix");
        this.setScene(new Scene(node));
        this.showAndWait();
    }

    
    
    
}
