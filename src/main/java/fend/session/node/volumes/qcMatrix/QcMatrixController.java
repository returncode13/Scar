/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcMatrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair
 */
public class QcMatrixController extends Stage {
    QcMatrixModel model;
    QcMatrixNode node;
    @FXML
    private TreeTableView<QcMatrixSequences> treetable;

    void setModel(QcMatrixModel lsm) {
        this.model=lsm;
        TreeTableColumn<QcMatrixSequences,Long> sequenceNumber=new TreeTableColumn<>("Sequence");
        sequenceNumber.setCellValueFactory(new TreeItemPropertyValueFactory<>("sequenceNumber"));
        TreeTableColumn<QcMatrixSequences,String> subsurface=new TreeTableColumn<>("Subsurface");
        subsurface.setCellValueFactory(new TreeItemPropertyValueFactory<>("subsurface"));
        List<String> qctypes=model.getQctypes();
        List<TreeTableColumn<QcMatrixSequences,String>> cols=new ArrayList<>();
        for (Iterator<String> iterator = qctypes.iterator(); iterator.hasNext();) {
            String qctype = iterator.next();
            TreeTableColumn<QcMatrixSequences,String> qctypeCol=new TreeTableColumn<>(qctype);
            qctypeCol.setEditable(true);
            cols.add(qctypeCol);
            
        }
        treetable.getColumns().add(sequenceNumber);
        treetable.getColumns().add(subsurface);
        treetable.getColumns().addAll(cols);
        
        List<TreeItem<QcMatrixSequences>> treeSeq=new ArrayList<>();
        for(QcMatrixSequences qcMatrixSequences: model.getQcMatrixSequences()){
            List<String> qctypeValuesAtSeq=new ArrayList<>();
            TreeItem<QcMatrixSequences> seqroot=new TreeItem<>(qcMatrixSequences);
            List<QcMatrixSubsurfaces> qcsubs=qcMatrixSequences.getQcSubs();
            for(QcMatrixSubsurfaces qcsub:qcsubs){
                TreeItem<QcMatrixSequences> subItem=new TreeItem<>(qcsub);
                seqroot.getChildren().add(subItem);
            }
            treeSeq.add(seqroot);
        }
        TreeItem<QcMatrixSequences> root=new TreeItem<>();
        root.getChildren().addAll(treeSeq);
        treetable.setRoot(root);
        treetable.setShowRoot(false);
        
        
    }

    void setView(QcMatrixNode aThis) {
        node=aThis;
        this.setTitle("QC Matrix");
        this.setScene(new Scene(node));
        this.showAndWait();
    }

    
    
    
}
