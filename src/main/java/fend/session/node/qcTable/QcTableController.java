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
import landing.AppProperties;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

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
            System.out.println("fend.session.node.qcTable.QcTableController.setModel(): creating treeItem seq: "+qcTableSequence.getSequence().getSequenceNumber());
            TreeItem<QcTableSequences> seqroot=new TreeItem<>(qcTableSequence);
          //  qcTableSequence.setQcfields(qctypes);
            List<QcTableSubsurfaces> qcsubs=qcTableSequence.getQcSubs();
            for(QcTableSubsurfaces qcsub:qcsubs){
               // CheckBoxTreeItem<QcTableSequences> subItem=new CheckBoxTreeItem<>(qcsub);
               System.out.println("fend.session.node.qcTable.QcTableController.setModel(): creating subtreeItem seq: "+qcsub.getSub().getSequenceNumber() +" sub: "+qcsub.getSub().getSubsurface());
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
            
            System.out.println("fend.session.node.qcTable.QcTableController.setModel(): Column name : "+qctype.getName()+" iii: "+iii);
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
                    
                //     SimpleStringProperty bprop=new SimpleStringProperty();
                     //System.out.println(".call(): bprop: is "+((bprop==null)?"is NULL":" not null"));
                    
                     QcTableSequences qseq=param.getValue().getValue();
                     qctypeCol.setText(qseq.getQctypes().get(iii).getName());
                     SimpleBooleanProperty checkUncheck=new SimpleBooleanProperty();
                     SimpleBooleanProperty indeterminate=new SimpleBooleanProperty();
                     
                     checkUncheck.bindBidirectional(qseq.getQctypes().get(iii).getCheckUncheck());
                     indeterminate.bindBidirectional(qseq.getQctypes().get(iii).getIndeterminate());
                     
                     
                     checkUncheck.addListener(new ChangeListener<Boolean>(){
                         @Override
                         public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                             qseq.getQctypes().get(iii).indeterminateProperty().set(false);
                             qseq.getQctypes().get(iii).checkUncheckProperty().set(newValue);
                         }
                         
                     });
                     
                     
                     indeterminate.addListener(new ChangeListener<Boolean>(){
                         @Override
                         public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            // if(newValue){
                                // System.out.println("Indeterminate.changed(): ");
                                 qseq.getQctypes().get(iii).indeterminateProperty().set(newValue);
                                 //System.out.println("Indeterminate.changed(): "+qseq.getQctypes().get(iii).indeterminateProperty().get());
                                 //bprop.set(QcTypeModel.isInDeterminate);
                         //    }
                         }
                         
                     });
                     
                    
                     
                     
                     /*
                     if(qseq instanceof QcTableSubsurfaces){
                     qctypeCol.setText(((QcTableSubsurfaces)qseq).getQctypes().get(iii).getName());
                     bprop= (SimpleStringProperty) ((QcTableSubsurfaces)qseq).getQctypes().get(iii).passQcProperty();
                     System.out.println("subObj: "+((QcTableSubsurfaces)qseq).getSub().getSubsurface());
                     System.out.println("subSeqNO: "+((QcTableSubsurfaces)qseq).getSub().getSequenceNumber());
                     System.out.println(" and subSurface : "+((QcTableSubsurfaces)qseq).getSub().getSubsurface());
                     System.out.println("iii: "+iii+" NAME: "+((QcTableSubsurfaces)qseq).getQctypes().get(iii).getName()+" colName: "+qctypeCol.getText());
                     System.out.println("iii: "+iii+" Ticked: "+(bprop==null?" NULL ":bprop.getValue()));
                     System.out.println("UpdateTime: "+((QcTableSubsurfaces)qseq).getUpdateTime());
                     
                     System.out.println("BProp.call(): BPropValue is : "+(bprop==null?" NULL ":bprop.getValue()));
                     }
                     else
                     if(qseq instanceof QcTableSequences){
                     qctypeCol.setText(qseq.getQctypes().get(iii).getName());
                     // System.out.println("seqObj: "+qseq.getSequence());
                     bprop= (SimpleStringProperty) qseq.getQctypes().get(iii).passQcProperty();
                     System.out.println("BProp.call(): BPropValue for QSeq is : "+(bprop==null?" NULL ":bprop.getValue()));
                     //                     System.out.println("seqNO: "+qseq.getSequence().getSequenceNumber());
                     //                   System.out.println(" and sub : "+qseq.getSequence().getSubsurface());
                     }
                     */
                     /* bprop.addListener(new ChangeListener<String>(){
                     @Override
                     public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                     
                     if(qseq instanceof QcTableSubsurfaces){
                     System.out.println(".changed(): from : "+oldValue+" to : "+newValue+" for subseq: "+((QcTableSubsurfaces)qseq).getSub().getSubsurface()+ " with seqNO: "+((QcTableSubsurfaces)qseq).getSub().getSequenceNumber()+" sub: "+((QcTableSubsurfaces)qseq).getSub().getSubsurface());
                     
                     ((QcTableSubsurfaces)qseq).getQctypes().get(iii).setPassQc(newValue);
                     ((QcTableSubsurfaces)qseq).setUpdateTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                     ((QcTableSubsurfaces)qseq).getSub().setUpdateTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                     
                     List<QcTableSubsurfaces> qcss=((QcTableSubsurfaces)qseq).getQcTableSeq().getQcSubs();
                     Boolean alltrue=true;           //are all the subs true?
                     for (Iterator<QcTableSubsurfaces> iterator = qcss.iterator(); iterator.hasNext();) {
                     QcTableSubsurfaces next = iterator.next();
                     Boolean thisQcCheckValue;
                     if(next.getQctypes().get(iii).isPassQc().equals(QcTypeModel.isInDeterminate)){
                     thisQcCheckValue=null;
                     alltrue=null;
                     break;
                     }else  if(next.getQctypes().get(iii).isPassQc().equals(Boolean.TRUE.toString())){
                     thisQcCheckValue=true;
                     }else{
                     thisQcCheckValue=false;
                     }
                     alltrue= alltrue && thisQcCheckValue;
                     }
                     if(alltrue==null){
                     ((QcTableSubsurfaces)qseq).getQcTableSeq().getQctypes().get(iii).setPassQc(QcTypeModel.isInDeterminate);
                     ((QcTableSubsurfaces)qseq).getQcTableSeq().setUpdateTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                     }
                     else if(alltrue){
                     ((QcTableSubsurfaces)qseq).getQcTableSeq().getQctypes().get(iii).setPassQc(Boolean.TRUE.toString());
                     ((QcTableSubsurfaces)qseq).getQcTableSeq().setUpdateTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                     }else{
                     ((QcTableSubsurfaces)qseq).getQcTableSeq().getQctypes().get(iii).setPassQc(Boolean.FALSE.toString());
                     ((QcTableSubsurfaces)qseq).getQcTableSeq().setUpdateTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                     }
                     }
                     else
                     if(qseq instanceof QcTableSequences){
                     System.out.println(".changed(): from : "+oldValue+" to : "+newValue+" for seq: "+qseq.getSequence()+" with seqNO: "+qseq.getSequenceNumber());
                     
                     if(newValue.equals(Boolean.TRUE.toString())){                  //if true clicked for a seq, then set all the subs as true
                     qseq.getQctypes().get(iii).setPassQc(newValue);
                     qseq.setUpdateTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                     qseq.getSequence().setUpdateTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                     List<QcTableSubsurfaces> qseqsubs=qseq.getQcSubs();
                     for (Iterator<QcTableSubsurfaces> iterator = qseqsubs.iterator(); iterator.hasNext();) {
                     QcTableSubsurfaces next = iterator.next();
                     next.getQctypes().get(iii).setPassQc(newValue);
                     next.setUpdateTime(qseq.getUpdateTime());
                     next.getSub().setUpdateTime(qseq.getUpdateTime());
                     
                     }
                     }
                     
                     if(newValue.equals(Boolean.FALSE.toString()) || newValue.equals(QcTypeModel.isInDeterminate)){                 //if false clicked for a seq, first check if the subs are all true in which case dont uncheck the seq but
                     // if any of them are unchecked then allow uncheck  for seq
                     Boolean alltrue=true;
                     List<QcTableSubsurfaces> qseqsubs=qseq.getQcSubs();
                     for (Iterator<QcTableSubsurfaces> iterator = qseqsubs.iterator(); iterator.hasNext();) {
                     QcTableSubsurfaces next = iterator.next();
                     Boolean thisQcCheckValue;
                     if(next.getQctypes().get(iii).isPassQc().equals(QcTypeModel.isInDeterminate)){
                     thisQcCheckValue=null;
                     alltrue=null;
                     break;
                     }else  if(next.getQctypes().get(iii).isPassQc().equals(Boolean.TRUE.toString())){
                     thisQcCheckValue=true;
                     }else{
                     thisQcCheckValue=false;
                     }
                     alltrue= alltrue && thisQcCheckValue;
                     
                     }
                     if(alltrue==null){
                     qseq.getQctypes().get(iii).setPassQc(QcTypeModel.isInDeterminate);
                     qseq.setUpdateTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                     qseq.getSequence().setUpdateTime(qseq.getUpdateTime());
                     }
                     else if(alltrue){
                     qseq.getQctypes().get(iii).setPassQc(Boolean.TRUE.toString());
                     qseq.setUpdateTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                     qseq.getSequence().setUpdateTime(qseq.getUpdateTime());
                     }else{
                     qseq.getQctypes().get(iii).setPassQc(newValue);
                     qseq.setUpdateTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                     qseq.getSequence().setUpdateTime(qseq.getUpdateTime());
                     }
                     }
                     
                     }
                     
                     
                     }
                     
                     });
                     */
                     
                     
                    if(indeterminate.get()){
                        return null;
                    }else{
                        return checkUncheck;
                    }
                
                }
            });
        
          qctypeCol.setCellFactory((param) -> {
              return new CheckBoxCell(param, iii);
          });
          
          
            System.out.println("fend.session.node.qcTable.QcTableController.setModel(): inside for loop for qctype: "+qctype.getName());
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
