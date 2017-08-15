/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.qcTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
        
        //List<CheckBoxTreeItem<QcTableSequences>> treeSeq=new ArrayList<>();
        List<TreeItem<QcTableSequences>> treeSeq=new ArrayList<>();
        List<QcTableSequences> qcTableSequences=model.getQcTableSequences();
        for(QcTableSequences qcTableSequence: qcTableSequences){
            
            //CheckBoxTreeItem<QcTableSequences> seqroot=new CheckBoxTreeItem<>(qcTableSequence);
            System.out.println("fend.session.node.volumes.type1.qcTable.QcTableController.setModel(): creating treeItem seq: "+qcTableSequence.getSequence().getSequenceNumber());
            TreeItem<QcTableSequences> seqroot=new TreeItem<>(qcTableSequence);
          //  qcTableSequence.setQcfields(qctypes);
            List<QcTableSubsurfaces> qcsubs=qcTableSequence.getQcSubs();
            for(QcTableSubsurfaces qcsub:qcsubs){
               // CheckBoxTreeItem<QcTableSequences> subItem=new CheckBoxTreeItem<>(qcsub);
               System.out.println("fend.session.node.volumes.type1.qcTable.QcTableController.setModel(): creating subtreeItem seq: "+qcsub.getSub().getSequenceNumber() +" sub: "+qcsub.getSub().getSubsurface());
                TreeItem<QcTableSequences> subItem=new TreeItem<>(qcsub);
                seqroot.getChildren().add(subItem);
                
            }
            treeSeq.add(seqroot);
        }
        
       
         List<TreeTableColumn<QcTableSequences,Boolean>> cols=new ArrayList<>();
        //List<TreeTableColumn<QcTableSequences,QcTypeModel>> cols=new ArrayList<>();
        
         
         
         
         
        for(int i=0;i<qctypes.size();i++){
            QcTypeModel qctype=qctypes.get(i);
            //TreeTableColumn<QcTableSequences,Boolean> qctypeCol=new TreeTableColumn<>(qctype.getName());
           //TreeTableColumn<QcTableSequences,QcTypeModel> qcval=new TreeTableColumn<>(qctype.getName());
            final int iii=i;
            
            System.out.println("fend.session.node.volumes.type1.qcTable.QcTableController.setModel(): Column name : "+qctype.getName());
            TreeTableColumn<QcTableSequences,Boolean> qctypeCol=new TreeTableColumn<>();//   <<<This is the culprit!!
            
           //qctypeCol.setCellValueFactory(cellData->cellData.getValue().getValue().tpProperty());
          // qctypeCol.setCellValueFactory(cellData->cellData.getValue().getValue().qctypeMap.get(qctype));
          
          
          /*    <--Working instance
           qctypeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<QcTableSequences, Boolean>, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<QcTableSequences, Boolean> param) {
                     SimpleBooleanProperty bprop=new SimpleBooleanProperty();
                     //System.out.println(".call(): bprop: is "+((bprop==null)?"is NULL":" not null"));
                     QcTableSequences qseq=param.getValue().getValue();
                     
                     if(qseq instanceof QcTableSubsurfaces){
                         System.out.println("subObj: "+((QcTableSubsurfaces)qseq).getSub());
                     System.out.println("subSeqNO: "+((QcTableSubsurfaces)qseq).getSub().getSequenceNumber());
                     System.out.println(" and subSurface : "+((QcTableSubsurfaces)qseq).getSub().getSubsurface());
                     bprop=(SimpleBooleanProperty) ((QcTableSubsurfaces)qseq).getSub().passedQCProperty();
                     }
                     else        
                     if(qseq instanceof QcTableSequences){
                         System.out.println("seqObj: "+qseq.getSequence());
                         bprop=(SimpleBooleanProperty) qseq.getSequence().passedQCProperty();
//                     System.out.println("seqNO: "+qseq.getSequence().getSequenceNumber());
  //                   System.out.println(" and sub : "+qseq.getSequence().getSubsurface());
                     }
                     
                     bprop.addListener(new ChangeListener<Boolean>(){
                         @Override
                         public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                             if(qseq instanceof QcTableSubsurfaces){
                                 System.out.println(".changed(): from : "+oldValue+" to : "+newValue+" for subseq: "+((QcTableSubsurfaces)qseq).getSub()+ " with seqNO: "+((QcTableSubsurfaces)qseq).getSub().getSequenceNumber()+" sub: "+((QcTableSubsurfaces)qseq).getSub().getSubsurface());
                                 ((QcTableSubsurfaces)qseq).getSub().setPassedQC(newValue);
                             }
                             else
                             if(qseq instanceof QcTableSequences){
                                 System.out.println(".changed(): from : "+oldValue+" to : "+newValue+" for seq: "+qseq.getSequence()+" with seqNO: "+qseq.getSequenceNumber());
                                 qseq.getSequence().setPassedQC(newValue);
                             }
                             
                             
                            // param.getValue().getValue().addToQcTypeMap(qctype, newValue);
                             
                             
                             
                         }
                         
                     });
                     return bprop;
                }
            });
                    <--Working block
          */
           
               qctypeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<QcTableSequences, Boolean>, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<QcTableSequences, Boolean> param) {
                     SimpleBooleanProperty bprop=new SimpleBooleanProperty();
                     //System.out.println(".call(): bprop: is "+((bprop==null)?"is NULL":" not null"));
                     QcTableSequences qseq=param.getValue().getValue();
                     
                     if(qseq instanceof QcTableSubsurfaces){
                         qctypeCol.setText(((QcTableSubsurfaces)qseq).getQctypes().get(iii).getName());
                         System.out.println("subObj: "+((QcTableSubsurfaces)qseq).getSub());
                     System.out.println("subSeqNO: "+((QcTableSubsurfaces)qseq).getSub().getSequenceNumber());
                     System.out.println(" and subSurface : "+((QcTableSubsurfaces)qseq).getSub().getSubsurface());
                         System.out.println("iii: "+iii+" NAME: "+((QcTableSubsurfaces)qseq).getQctypes().get(iii).getName()+" colName: "+qctypeCol.getText());
                         System.out.println("iii: "+iii+" Ticked: "+((QcTableSubsurfaces)qseq).getQctypes().get(iii).passQcProperty().get());
                     bprop=(SimpleBooleanProperty) ((QcTableSubsurfaces)qseq).getQctypes().get(iii).passQcProperty();
                     
                     }
                     else        
                     if(qseq instanceof QcTableSequences){
                         qctypeCol.setText(qseq.getQctypes().get(iii).getName());
                         System.out.println("seqObj: "+qseq.getSequence());
                         bprop=(SimpleBooleanProperty) qseq.getQctypes().get(iii).passQcProperty();
//                     System.out.println("seqNO: "+qseq.getSequence().getSequenceNumber());
  //                   System.out.println(" and sub : "+qseq.getSequence().getSubsurface());
                     }
                     
                     bprop.addListener(new ChangeListener<Boolean>(){
                         @Override
                         public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                             if(qseq instanceof QcTableSubsurfaces){
                                 System.out.println(".changed(): from : "+oldValue+" to : "+newValue+" for subseq: "+((QcTableSubsurfaces)qseq).getSub()+ " with seqNO: "+((QcTableSubsurfaces)qseq).getSub().getSequenceNumber()+" sub: "+((QcTableSubsurfaces)qseq).getSub().getSubsurface());
                                 ((QcTableSubsurfaces)qseq).getQctypes().get(iii).setPassQc(newValue);
                                 if(!newValue){
                                   ((QcTableSubsurfaces)qseq).getQcTableSeq().getQctypes().get(iii).setPassQc(newValue);
                                 }
                                 List<QcTableSubsurfaces> qcss=((QcTableSubsurfaces)qseq).getQcTableSeq().getQcSubs();
                                 Boolean alltrue=true;
                                 for (Iterator<QcTableSubsurfaces> iterator = qcss.iterator(); iterator.hasNext();) {
                                     QcTableSubsurfaces next = iterator.next();
                                     alltrue= alltrue && next.getQctypes().get(iii).isPassQc();
                                 }
                                 if(alltrue){
                                     ((QcTableSubsurfaces)qseq).getQcTableSeq().getQctypes().get(iii).setPassQc(alltrue);
                                 }
                             }
                             else
                             if(qseq instanceof QcTableSequences){
                                 System.out.println(".changed(): from : "+oldValue+" to : "+newValue+" for seq: "+qseq.getSequence()+" with seqNO: "+qseq.getSequenceNumber());
                                
                                 
                                 if(newValue){                  //if true clicked for a seq, then set all the subs as true
                                     qseq.getQctypes().get(iii).setPassQc(newValue);
                                     List<QcTableSubsurfaces> qseqsubs=qseq.getQcSubs();
                                 for (Iterator<QcTableSubsurfaces> iterator = qseqsubs.iterator(); iterator.hasNext();) {
                                     QcTableSubsurfaces next = iterator.next();
                                     next.getQctypes().get(iii).setPassQc(newValue);
                                     
                                 }
                                 }
                                 
                                 if(!newValue){                 //if false clicked for a seq, first check if the subs are all true in which case dont uncheck the seq but
                                                                // if any of them are unchecked then allow uncheck  for seq
                                     Boolean alltrue=true;
                                     List<QcTableSubsurfaces> qseqsubs=qseq.getQcSubs();
                                     for (Iterator<QcTableSubsurfaces> iterator = qseqsubs.iterator(); iterator.hasNext();) {
                                         QcTableSubsurfaces next = iterator.next();
                                         alltrue= alltrue && next.getQctypes().get(iii).isPassQc();
                                         
                                     }
                                     
                                     if(alltrue){
                                          qseq.getQctypes().get(iii).setPassQc(!newValue);
                                     }else{
                                         qseq.getQctypes().get(iii).setPassQc(newValue);
                                     }
                                 }
                                 
                             }
                             
                             
                            // param.getValue().getValue().addToQcTypeMap(qctype, newValue);
                             
                             
                             
                         }
                         
                     });
                     return bprop;
                }
            });
           qctypeCol.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(qctypeCol));
          
          
            System.out.println("fend.session.node.volumes.type1.qcTable.QcTableController.setModel(): inside for loop for qctype: "+qctype.getName());
            /* qctypeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<QcTableSequences, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<QcTableSequences, Boolean> param) {
            //System.out.println("fend.session.node.volumes.type1.qcTable.QcTableController.setModel().call(): seq: "+param.getValue().getValue().getSequenceNumber()+" sub: "+param.getValue().getValue().getSubsurface()+" = "+param.getValue().getValue().getQcfields().get(iii).getName()+" : "+param.getValue().getValue().getQcfields().get(iii).getQcStatus());
            QcTypeModel qctmd=param.getValue().getValue().getQcfields().get(iii);
            SimpleStringProperty strProp=new SimpleStringProperty(qctmd.getQcStatus());
            SimpleBooleanProperty boolProp=new SimpleBooleanProperty(qctmd.isPassQc());
            
            boolProp.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            System.out.println(".changed(): from :"+oldValue+ " to : "+newValue+" for  "+param.getValue().getValue().getSubsurface()+ " and type: "+param.getValue().getValue().getQcfields().get(iii).getName());
            qctmd.setPassQc(newValue);
            }
            
            });
            
            return boolProp;
            //return param.getValue().getValue().getQcfields().get(iii).qcStatusProperty();
            }
            });*/
           
           
           //qctypeCol.setCellFactory(CheckBoxTreeTableCell.);
           
           /* qctypeCol.setCellFactory(new Callback<TreeTableColumn<QcTableSequences, Boolean>, TreeTableCell<QcTableSequences, Boolean>>() {
           @Override
           public TreeTableCell<QcTableSequences, Boolean> call(TreeTableColumn<QcTableSequences, Boolean> param) {*/
               /*CheckBoxTreeTableCell<QcTableSequences,Boolean> cell=new CheckBoxTreeTableCell<>();
               cell.setAlignment(Pos.CENTER);
               return cell;*/
         /* return new CheckBoxCell(param, iii);
           }
           });
*/
            
           
          
            //qcval.getColumns().add(qctypeCol);
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
        this.setTitle("QC Table");
        this.setScene(new Scene(node));
        this.showAndWait();
    }

    
    
    
}
