/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.CheckBoxTreeCell;
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
        
        List<CheckBoxTreeItem<QcTableSequences>> treeSeq=new ArrayList<>();
        List<QcTableSequences> qcTableSequences=model.getQcTableSequences();
        for(QcTableSequences qcTableSequence: qcTableSequences){
            List<String> qctypeValuesAtSeq=new ArrayList<>();
            CheckBoxTreeItem<QcTableSequences> seqroot=new CheckBoxTreeItem<>(qcTableSequence);
          //  qcTableSequence.setQcfields(qctypes);
            List<QcTableSubsurfaces> qcsubs=qcTableSequence.getQcSubs();
            for(QcTableSubsurfaces qcsub:qcsubs){
                CheckBoxTreeItem<QcTableSequences> subItem=new CheckBoxTreeItem<>(qcsub);
                seqroot.getChildren().add(subItem);
                //seqroot.setIndependent(true);
                /* for(int i=0;i<qctypes.size();i++){
                seqroot.selectedProperty().bindBidirectional(qcsub.getQcfields().get(i).passQcProperty());
                seqroot.indeterminateProperty().bindBidirectional(qcsub.getQcfields().get(i).notQcdProperty());
                }*/
               // seqroot.selectedProperty().bindBidirectional(qcTableSequence.getQcfields().get(0));
            }
            treeSeq.add(seqroot);
        }
        
        
         //List<TreeTableColumn<QcTableSequences,String>> cols=new ArrayList<>();
         List<TreeTableColumn<QcTableSequences,QcTypeModel>> cols=new ArrayList<>();
        
         
         
         
         
        for(int i=0;i<qctypes.size();i++){
            QcTypeModel qctype=qctypes.get(i);
            //TreeTableColumn<QcTableSequences,String> qctypeCol=new TreeTableColumn<>(qctype.getName());
            TreeTableColumn<QcTableSequences,QcTypeModel> qctypeCol=new TreeTableColumn<>(qctype.getName());
            final int iii=i;
            qctypeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<QcTableSequences, QcTypeModel>, ObservableValue<QcTypeModel>>() {
                @Override
                public ObservableValue<QcTypeModel> call(TreeTableColumn.CellDataFeatures<QcTableSequences, QcTypeModel> param) {
                    return (ObservableValue<QcTypeModel>) param.getValue().getValue().getQcfields().get(iii);


                }
            });
            
            
            //qctypeCol.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(qctypeCol));
           //qctypeCol.setCellValueFactory(param->param.getValue().getValue().getQcfields().get(iii).valueProperty());
          /* qctypeCol.setCellFactory(new Callback<TreeTableColumn<QcTableSequences, String>, TreeTableCell<QcTableSequences, String>>() {
           
           @Override
           public TreeTableCell<QcTableSequences, String> call(TreeTableColumn<QcTableSequences, String> param) {
           
           //  return new CheckBoxCell(treetable,iii);
           return new CheckBoxCell(param,iii);
           }
           });
           
          */
           /*qctypeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<QcTableSequences, String>, ObservableValue<String>>() {
           @Override
           public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<QcTableSequences, String> param) {
           System.out.println(".call(): seq: "+param.getValue().getValue().getSequenceNumber()+" sub: "+param.getValue().getValue().getSubsurface()+" = "+param.getValue().getValue().getQcfields().get(iii).getName()+" : "+param.getValue().getValue().getQcfields().get(iii).getQcStatus());
           return param.getValue().getValue().getQcfields().get(iii).qcStatusProperty();
           }
           });*/
           
           /*   qctypeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<QcTableSequences, Boolean>, ObservableValue<Boolean>>() {
           @Override
           public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<QcTableSequences, Boolean> param) {
           
           return param.getValue().getValue().getQcfields().get(iii).passQcProperty();
           }
           });*/
              //qctypeCol.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(qctypeCol));
           /*  qctypeCol.setCellFactory(new Callback<TreeTableColumn<QcTableSequences, Boolean>, TreeTableCell<QcTableSequences, Boolean>>() {
           @Override
           public TreeTableCell<QcTableSequences, Boolean> call(TreeTableColumn<QcTableSequences, Boolean> param) {
           param.
           return new CheckBoxCell(param, iii);
           }
           });*/
           //qctypeCol.setCellFactory();
            
            /*qctypeCol.setCellFactory(param->new TreeTableCell(){
            final CheckBox checkbox=new CheckBox(){
            {
            setAllowIndeterminate(true);
            }
            };
            
            public void updateItem(String qcstatus,boolean empty){
            
            }
            
            
            
            });*/
            
            
            //qctypeCol.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(qctypeCol));
          // qctypeCol.setCellValueFactory(param-> param.getValue().getValue().getQcfields().get(iii).passQcProperty());
          /* qctypeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<QcTableSequences, Boolean>, ObservableValue<Boolean>>() {
          @Override
          public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<QcTableSequences, Boolean> param) {
          
          
          QcTableSequences qs=param.getValue().getValue();
          System.out.println(".call(): seq: "+param.getValue().getValue().getSequenceNumber()+" sub: "+param.getValue().getValue().getSubsurface()+" = "+param.getValue().getValue().getQcfields().get(iii).getName()+" : "+param.getValue().getValue().getQcfields().get(iii).isPassQc());
          SimpleBooleanProperty bp= (SimpleBooleanProperty) qs.getQcfields().get(iii).passQcProperty();
          
          bp.addListener(new ChangeListener<Boolean>() {
          @Override
          public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
          System.out.println(".changed(): seq: "+qs.getSequenceNumber()+" sub: "+qs.getSubsurface()+" field: "+qs.getQcfields().get(iii).getName()+" from "+oldValue+" to "+newValue);
          param.getValue().getValue().getQcfields().get(iii).setPassQc(newValue);
          }
          }
          );
          return bp;
          }
          });
          qctypeCol.setCellFactory(new Callback<TreeTableColumn<QcTableSequences, Boolean>, TreeTableCell<QcTableSequences, Boolean>>() {
          @Override
          public TreeTableCell<QcTableSequences, Boolean> call(TreeTableColumn<QcTableSequences, Boolean> param) {
          CheckBoxTreeTableCell<QcTableSequences,Boolean> cell=new CheckBoxTreeTableCell<>();
          return cell;
          }
          });*/
            /* qctypeCol.setCellFactory(param->new TreeTableCell(){
            final CheckBox cb=new CheckBox(){{
            setAllowIndeterminate(true);
            //allowIndeterminateProperty().set(true);
            }};
            
            @Override
            public void updateItem(Boolean item,boolean empty){
            
            }
            });*/
            
            cols.add(qctypeCol);
            
        }
        treetable.getColumns().add(sequenceNumber);
        treetable.getColumns().add(subsurface);
        treetable.getColumns().addAll(cols);
        
        
        CheckBoxTreeItem<QcTableSequences> root=new CheckBoxTreeItem<>();
        root.getChildren().addAll(treeSeq);
        root.setIndependent(true);
        treetable.setRoot(root);
        treetable.setShowRoot(false);
        treetable.setEditable(true);
        
    }

    void setView(QcTableNode aThis) {
        node=aThis;
        this.setTitle("QC Matrix");
        this.setScene(new Scene(node));
        this.showAndWait();
    }

    
    
    
}
