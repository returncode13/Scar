/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import db.model.Headers;
import db.model.Logs;
import db.model.Volume;
import db.services.HeadersService;
import db.services.HeadersServiceImpl;
import db.services.LogsService;
import db.services.LogsServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.session.node.headers.logger.LogsModel;
import fend.session.node.headers.logger.LogsNode;
import fend.session.node.headers.logger.VersionLogsModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import watcher.LogWatcher;

/**
 * FXML Controller class
 *
 * @author sharath
 */
public class HeadersViewController extends Stage implements Initializable {

    
    private HeadersModel hmodel;
    private HeadersNode hnode;
    ObservableList<Sequences> seqListObs;
    HeadersService hdserv=new HeadersServiceImpl();
    VolumeService vserv=new VolumeServiceImpl();
    LogsService lserv=new LogsServiceImpl();
            
    
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
        if(lsm==null){
            System.out.println("fend.session.node.headers.setModel: lsm is NULL");
        }
      hmodel=lsm;  
     seqListObs=hmodel.getSequenceListInHeaders();
     /*
     treetableView.setRowFactory(tv-> new TreeTableRow<Sequences>(){
     @Override
     protected void updateItem(Sequences item,boolean empty){
     super.updateItem(item,empty);
     if(item==null || empty){
     setText(null);
     setStyle("");
     }else if(item.getAlert()){
     setStyle("-fx-background-color:tomato");
     }else
     {
     setStyle("-fx-background-color:green");
     }
     }
     });
     */
     
     treetableView.setRowFactory(ttv->{
         ContextMenu contextMenu = new ContextMenu();
         MenuItem menuItem=new MenuItem("Show Logs");
         contextMenu.getItems().add(menuItem);
         TreeTableRow<Sequences> row=new TreeTableRow<Sequences>(){
             
             @Override
            protected void updateItem(Sequences item,boolean empty){
                super.updateItem(item,empty);
                if(item==null || empty){
                    setText(null);
                    setStyle("");
                    setContextMenu(null);
                }else if(item.getAlert()){
                    setStyle("-fx-background-color:tomato");
                    setContextMenu(contextMenu);
                }else
                {
                    setStyle("-fx-background-color:green");
                    setContextMenu(contextMenu);
                }
            }
         };
         
         menuItem.setOnAction(evt->{
             Sequences seq=row.getItem();
             List<VersionLogsModel> verslogsmodel=new ArrayList<>();
             System.out.println("Sub: "+seq.getSubsurface()+" : alert is : "+seq.getAlert());
             System.out.println(""+lsm.getVolmodel().getLabel()+"  id: "+lsm.getVolmodel().getId());
             Volume v=vserv.getVolume(lsm.getVolmodel().getId());
             List<Headers> h=hdserv.getHeadersFor(v, seq.getSubsurface());
             if(h.size()==1){
                 
                 System.out.println("fend.session.node.headers.setRowFactory(): Headers : sub: "+h.get(0).getSubsurface()+" id: "+h.get(0).getIdHeaders());
                 List<Logs> loglist=lserv.getLogsFor(h.get(0));
                 if(loglist.isEmpty()){
                     String logLocation=v.getPathOfVolume();
                     logLocation= logLocation+"/../../000scratch/logs";
                     
                   LogWatcher lw=  new LogWatcher(logLocation,"", lsm.getVolmodel(),Boolean.TRUE);
                     
                     
                 }
                 else{
                     String logLocation=v.getPathOfVolume();
                     logLocation= logLocation+"/../../000scratch/logs";
                     LogWatcher lw=  new LogWatcher(logLocation,"", lsm.getVolmodel(),Boolean.TRUE);
                     System.out.println("fend.session.node.headers.setRowFactory(): non empty log list!");
                     for (Iterator<Logs> iterator = loglist.iterator(); iterator.hasNext();) {
                         Logs logs = iterator.next();
                         Long version=logs.getVersion();
                         String timestamp=logs.getTimestamp();
                         String logfilePath=logs.getLogpath();
                         VersionLogsModel lmod=new VersionLogsModel(version,timestamp,logfilePath);
                         verslogsmodel.add(lmod);
                     }
                     
                     
                 }
                 
                       
                 
             }
             LogsModel logsmodel=new LogsModel();
             logsmodel.setLogsmodel(verslogsmodel);
             LogsNode logsnode=new LogsNode(logsmodel);
             
         });
                 return row;
     });
     
     
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
        TreeTableColumn<Sequences,Long>  insightVersion=new TreeTableColumn<>("insightVersion");
        TreeTableColumn<Sequences,Boolean>  alert=new TreeTableColumn<>("alert");
        TreeTableColumn<Sequences,Long>  version=new TreeTableColumn<>("version");
        TreeTableColumn<Sequences,Boolean>  modified=new TreeTableColumn<>("modified");
        TreeTableColumn<Sequences,Boolean>  deleted=new TreeTableColumn<>("deleted");
     
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
        insightVersion.setCellValueFactory(new TreeItemPropertyValueFactory<>("insightVersion"));
        alert.setCellValueFactory(new TreeItemPropertyValueFactory<>("alert"));
        version.setCellValueFactory(new TreeItemPropertyValueFactory<>("version"));
        modified.setCellValueFactory(new TreeItemPropertyValueFactory<>("modified"));
        deleted.setCellValueFactory(new TreeItemPropertyValueFactory<>("deleted"));
        
        version.setCellFactory((TreeTableColumn<Sequences,Long> p)->{
            TreeTableCell cell=new TreeTableCell<Sequences,Long>(){
               
                @Override
                protected void updateItem(Long item, boolean empty){
                  super.updateItem(item, empty);
                    TreeTableRow<Sequences> seqTreeRow=getTreeTableRow();
                    if(item==null || empty ){
                        setText(null);
                        seqTreeRow.setStyle("");
                        setStyle("");
                    }else{
                        seqTreeRow.setStyle(item.longValue() > 0 ? "":"");
                        setText(item.toString());
                        setStyle(item.longValue() > 0 ? "-fx-background-color:orange":"");
                    }
              }  
            };
                    return cell;
        });
        
        alert.setCellFactory((TreeTableColumn<Sequences,Boolean> p)->{
            TreeTableCell cell=new TreeTableCell<Sequences,Boolean>(){
               
                @Override
                protected void updateItem(Boolean item, boolean empty){
                  super.updateItem(item, empty);
                    TreeTableRow<Sequences> seqTreeRow=getTreeTableRow();
                    if(item==null || empty ){
                        setText(null);
                        seqTreeRow.setStyle("");
                        setStyle("");
                    }else{
                        seqTreeRow.setStyle(item ? "-fx-background-color:red":"");
                        setText(item.toString());
                        setStyle(item? "-fx-background-color:red":"-fx-background-color:green");
                    }
              }  
            };
                    return cell;
        });
        
        
        
        treetableView.getColumns().addAll(sequenceNumber,subsurfaceName,alert,version,modified,deleted,timeStamp,tracecount,inlineMax,inlineMin,inlineInc,xlineMax,xlineMin,xlineInc,dugShotMax,dugShotMin,dugShotInc,dugChannelMax,dugChannelMin,dugChannelInc,offsetMax,offsetMin,offsetInc,cmpMax,cmpMin,cmpInc,insightVersion);
        
     
     
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
