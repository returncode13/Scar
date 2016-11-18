/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sharath
 */
public class HeadersViewController extends Stage implements Initializable {

    
    private HeadersModel hmodel;
    private HeadersNode hnode;
    ObservableList<Sequences> seqListObs;
    
       @FXML
    private TreeTableView<Sequences> treetableView;
       
     
    
    /**
     * Initializes the controller class.
     * 
     * 
     */
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setModel(HeadersModel lsm) {
      hmodel=lsm;  
     seqListObs=hmodel.getObsHList();
     
     
        TreeTableColumn<Sequences,Long>  sequenceNumber= new TreeTableColumn<>("SEQUENCE");
        TreeTableColumn<Sequences,String> subsurfaceName= new TreeTableColumn<>("SAILLINE");
        TreeTableColumn<Sequences,String>  timeStamp=new TreeTableColumn<>("TIMESTAMP");
        TreeTableColumn<Sequences,Long>  tracecount=new TreeTableColumn<>("Traces");
        TreeTableColumn<Sequences,Long>  inlineMax=new TreeTableColumn<>("inlineMax");
        TreeTableColumn<Sequences,Long>  inlineMin=new TreeTableColumn<>("inlineMin");
        TreeTableColumn<Sequences,Long>  inlineInc=new TreeTableColumn<>("inlineInc");
        TreeTableColumn<Sequences,Long>  xlineMax=new TreeTableColumn<>("xlineMax");
        TreeTableColumn<Sequences,Long>  xlineMin =new TreeTableColumn<>("xlineMin");
        TreeTableColumn<Sequences,Long>  xlineInc =new TreeTableColumn<>("xlineInc");
        TreeTableColumn<Sequences,Long>  dugShotMax=new TreeTableColumn<>("dugShotMax");
        TreeTableColumn<Sequences,Long>  dugShotMin=new TreeTableColumn<>("dugShotMin");
        TreeTableColumn<Sequences,Long>  dugShotInc=new TreeTableColumn<>("dugShotInc");
        TreeTableColumn<Sequences,Long>  dugChannelMax=new TreeTableColumn<>("dugChannelMax");
        TreeTableColumn<Sequences,Long>  dugChannelMin=new TreeTableColumn<>("dugChannelMin");
        TreeTableColumn<Sequences,Long>  dugChannelInc=new TreeTableColumn<>("dugChannelInc");
        TreeTableColumn<Sequences,Long>  offsetMax=new TreeTableColumn<>("offsetMax");
        TreeTableColumn<Sequences,Long>  offsetMin=new TreeTableColumn<>("offsetMin");
        TreeTableColumn<Sequences,Long>  offsetInc=new TreeTableColumn<>("offsetInc");
        TreeTableColumn<Sequences,Long>  cmpMax=new TreeTableColumn<>("cmpMax");
        TreeTableColumn<Sequences,Long>  cmpMin=new TreeTableColumn<>("cmpMin");
        TreeTableColumn<Sequences,Long>  cmpInc=new TreeTableColumn<>("cmpInc");
        
     
        sequenceNumber.setCellValueFactory(new TreeItemPropertyValueFactory<>("sequenceNumber"));
        subsurfaceName.setCellValueFactory(new TreeItemPropertyValueFactory<>("subsurface"));
        timeStamp.setCellValueFactory(new TreeItemPropertyValueFactory<>("timeStamp"));
        tracecount.setCellValueFactory(new TreeItemPropertyValueFactory<>("traceCount"));
        inlineMax.setCellValueFactory(new TreeItemPropertyValueFactory<>("inlineMax"));
        inlineMin.setCellValueFactory(new TreeItemPropertyValueFactory<>("inlineMin"));
        inlineInc.setCellValueFactory(new TreeItemPropertyValueFactory<>("inlineInc"));
        xlineMax.setCellValueFactory(new TreeItemPropertyValueFactory<>("xlineMax"));
        xlineMin.setCellValueFactory(new TreeItemPropertyValueFactory<>("xlineMin"));
        xlineInc.setCellValueFactory(new TreeItemPropertyValueFactory<>("xlineInc"));
        dugShotMax.setCellValueFactory(new TreeItemPropertyValueFactory<>("dugShotMax"));
        dugShotMin.setCellValueFactory(new TreeItemPropertyValueFactory<>("dugShotMin"));
        dugShotInc.setCellValueFactory(new TreeItemPropertyValueFactory<>("dugShotInc"));
        dugChannelMax.setCellValueFactory(new TreeItemPropertyValueFactory<>("dugChannelMax"));
        dugChannelMin.setCellValueFactory(new TreeItemPropertyValueFactory<>("dugChannelMin"));
        dugChannelInc.setCellValueFactory(new TreeItemPropertyValueFactory<>("dugChannelInc"));
        offsetMax.setCellValueFactory(new TreeItemPropertyValueFactory<>("offsetMax"));
        offsetMin.setCellValueFactory(new TreeItemPropertyValueFactory<>("offsetMin"));
        offsetInc.setCellValueFactory(new TreeItemPropertyValueFactory<>("offsetInc"));
        cmpMax.setCellValueFactory(new TreeItemPropertyValueFactory<>("cmpMax"));
        cmpMin.setCellValueFactory(new TreeItemPropertyValueFactory<>("cmpMin"));
        cmpInc.setCellValueFactory(new TreeItemPropertyValueFactory<>("cmpInc"));
        
        treetableView.getColumns().addAll(sequenceNumber,subsurfaceName,timeStamp,tracecount,inlineMax,inlineMin,inlineInc,xlineMax,xlineMin,xlineInc,dugShotMax,dugShotMin,dugShotInc,dugChannelMax,dugChannelMin,dugChannelInc,offsetMax,offsetMin,offsetInc,cmpMax,cmpMin,cmpInc);
        
     
     
     List<TreeItem<Sequences>> treeSeq = new ArrayList<>();
     
     for(Sequences s:seqListObs){
         List<SubSurface> subs=s.getSubsurfaces();
         TreeItem<Sequences> seqroot=new TreeItem<>(s);
         
         for(SubSurface sub:subs){
             TreeItem<Sequences> subItem=new TreeItem<>(sub);
             
             seqroot.getChildren().add(subItem);
         }
         treeSeq.add(seqroot);
     }
     
     Sequences seqZero=new Sequences();
     TreeItem<Sequences> rootOfAllseq=new TreeItem<>();
     
     rootOfAllseq.getChildren().addAll(treeSeq);
     
     treetableView.setRoot(rootOfAllseq);
     treetableView.setShowRoot(false);
     
    }

    void setView(HeadersNode aThis) {
        hnode=aThis;
        this.setScene(new Scene(hnode));
        this.showAndWait();
    }

    
    
}
