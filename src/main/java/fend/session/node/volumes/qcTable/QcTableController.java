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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
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
       
        /*for (Iterator<QcTypeModel> iterator = qctypes.iterator(); iterator.hasNext();) {
        QcTypeModel qctype = iterator.next();*/
        
        List<TreeItem<QcTableSequences>> treeSeq=new ArrayList<>();
        List<QcTableSequences> qcTableSequences=model.getQcTableSequences();
        for(QcTableSequences qcTableSequence: qcTableSequences){
            List<String> qctypeValuesAtSeq=new ArrayList<>();
            TreeItem<QcTableSequences> seqroot=new TreeItem<>(qcTableSequence);
            qcTableSequence.setQcfields(qctypes);
            List<QcTableSubsurfaces> qcsubs=qcTableSequence.getQcSubs();
            for(QcTableSubsurfaces qcsub:qcsubs){
                TreeItem<QcTableSequences> subItem=new TreeItem<>(qcsub);
                seqroot.getChildren().add(subItem);
            }
            treeSeq.add(seqroot);
        }
        
        
         List<TreeTableColumn<QcTableSequences,String>> cols=new ArrayList<>();
        
        for(int i=0;i<qctypes.size();i++){
            QcTypeModel qctype=qctypes.get(i);
            TreeTableColumn<QcTableSequences,String> qctypeCol=new TreeTableColumn<>(qctype.getName());
            /*qctypeCol.setCellFactory(new Callback<TreeTableColumn<QcTableSequences, String>, TreeTableCell<QcTableSequences, String>>() {
            @Override
            public TreeTableCell<QcTableSequences, String> call(TreeTableColumn<QcTableSequences, String> param) {
            return new ButtonCell(treetable);
            }
            });*/
            final int iii=i;
            System.out.println("fend.session.node.volumes.qcTable.QcTableController.setModel(): ");
           // qctypeCol.setCellValueFactory(param->param.getValue().getValue().getQcfields().get(iii).valueProperty());
            qctypeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<QcTableSequences, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<QcTableSequences, String> param) {
                    System.out.println(".call(): "+param.getValue().getValue().getQcfields().get(iii).valueProperty());
                return param.getValue().getValue().getQcfields().get(iii).valueProperty();
                }
            });
            qctypeCol.setCellFactory(new Callback<TreeTableColumn<QcTableSequences, String>, TreeTableCell<QcTableSequences, String>>() {
                
                /*  final CheckBox checkBox=new CheckBox(){
                {
                setIndeterminate(true);
                }
                };
                */
                @Override
                public TreeTableCell<QcTableSequences, String> call(TreeTableColumn<QcTableSequences, String> param) {
                   return new CheckBoxCell();
                }
            });
            
            
            /*qctypeCol.setCellFactory(param->new TreeTableCell(){
            final CheckBox checkbox=new CheckBox(){
            {
            setAllowIndeterminate(true);
            }
            };
            
            public void updateItem(String qcstatus,boolean empty){
            
            }
            
            
            
            });*/
            
            
            cols.add(qctypeCol);
            
        }
        treetable.getColumns().add(sequenceNumber);
        treetable.getColumns().add(subsurface);
        treetable.getColumns().addAll(cols);
        
        
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
