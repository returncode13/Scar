/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import db.model.Headers;
import db.model.Logs;
import db.model.Volume;
import db.model.Workflow;
import db.services.HeadersService;
import db.services.HeadersServiceImpl;
import db.services.LogsService;
import db.services.LogsServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.session.node.headers.logger.LogsModel;
import fend.session.node.headers.logger.LogsNode;
import fend.session.node.headers.logger.VersionLogsModel;
import fend.session.node.headers.workflows.WorkflowVersionModel;
import fend.session.node.headers.workflows.WorkflowVersionNode;
import fend.session.node.headers.workflows.WorkflowVersionTabModel;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.annotations.common.util.impl.Log;
import watcher.LogWatcher;

/**
 * FXML Controller class
 *
 * @author sharath
 */
public class HeadersViewController extends Stage implements Initializable {

     private Map<Integer,TreeItem<Sequences>> idxForTree=new HashMap<>();
    
    private HeadersModel hmodel;
    private HeadersNode hnode;
    ObservableList<Sequences> seqListObs;
    HeadersService hdserv=new HeadersServiceImpl();
    VolumeService vserv=new VolumeServiceImpl();
    LogsService lserv=new LogsServiceImpl();
    String vname=new String();        
   
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

    void setModel(HeadersModel lsm,int seqSelection) {
        if(lsm==null){
            System.out.println("fend.session.node.headers.setModel: lsm is NULL");
        }
      hmodel=lsm;  
      vname=hmodel.getVolmodel().getVolumeChosen().getName();
     seqListObs=hmodel.getSequenceListInHeaders();
     /*
     treetableView.setRowFactory(tv-> new TreeTableRow<Sequences>(){
     @Override
     protected void updateItem(Sequences item,boolean empty){
     super.updateItem(item,empty);
     if(item==null || empty){
     setText(null);
     setStyle("");
     }else if(item.getQcAlert()){
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
         MenuItem showLogsMenuItem=new MenuItem("Logs");
         MenuItem showWorkFlowVersion=new MenuItem("Workflow Versions");
         contextMenu.getItems().add(showLogsMenuItem);
         contextMenu.getItems().add(showWorkFlowVersion);
         TreeTableRow<Sequences> row=new TreeTableRow<Sequences>(){
             
             @Override
            protected void updateItem(Sequences item,boolean empty){
                super.updateItem(item,empty);
                if(item==null || empty){
                    setText(null);
                    setStyle("");
                    setContextMenu(null);
                }else if(item.getQcAlert()){
                    setStyle("-fx-background-color:orange");
                    setTooltip(new Tooltip(item.getErrorMessage()));
                    setContextMenu(contextMenu);
                }else
                {
                    //setStyle("-fx-background-color:green");
                   // setStyle(../landingResources/landing.css)
                    setContextMenu(contextMenu);
                }
            }
         };
         
         showLogsMenuItem.setOnAction(evt->{
             Sequences seq=row.getItem();
             List<VersionLogsModel> verslogsmodel=new ArrayList<>();
             System.out.println("Sub: "+seq.getSubsurface()+" : alert is : "+seq.getQcAlert());
             System.out.println(""+lsm.getVolmodel().getLabel()+"  id: "+lsm.getVolmodel().getId());
             Volume v=vserv.getVolume(lsm.getVolmodel().getId());
            
             List<Headers> h=hdserv.getHeadersFor(v, seq.getSubsurface());
             // List<Logs> logs=lserv.getLogsFor(h.get(0));
             if(h.size()==1){
                 
                 System.out.println("fend.session.node.headers.setRowFactory(): Headers : sub: "+h.get(0).getSubsurface()+" id: "+h.get(0).getIdHeaders());
                 List<Logs> loglist=lserv.getLogsFor(h.get(0));
                 if(loglist.isEmpty()){
                     /* String logLocation=v.getPathOfVolume();
                     System.out.println("fend.session.node.headers.setRowFactory(): LogList is empty");
                     logLocation= logLocation+"/../../000scratch/logs";
                     ExecutorService execService=Executors.newFixedThreadPool(1);
                     final VolumeSelectionModelType1 vmd=lsm.getVolmodel();
                     final String lloc=new String(logLocation);
                     try{
                     execService.submit(new Callable<Void>(){
                     
                     @Override
                     public Void call() throws Exception {
                     // LogWatcher lw=  new LogWatcher(lloc,"", vmd,Boolean.TRUE);
                     LogWatcher lw=  new LogWatcher(lloc,"", vmd);
                     return null;
                     }
                     }).get();
                     loglist=lserv.getLogsFor(h.get(0));
                     
                     }catch(NullPointerException npe){
                     System.out.println("fend.session.node.headers.setRowFactory(): unable to find logs!!!");
                     } catch (InterruptedException ex) {
                     Logger.getLogger(HeadersViewController.class.getName()).log(Level.SEVERE, null, ex);
                     } catch (ExecutionException ex) {
                     Logger.getLogger(HeadersViewController.class.getName()).log(Level.SEVERE, null, ex);
                     }*/
                     
                 }
                 else{
                     /* String logLocation=v.getPathOfVolume();
                     logLocation= logLocation+"/../../000scratch/logs";
                     //System.out.println("fend.session.node.headers.setRowFactory(): Nonempty LogList");
                     LogWatcher lw=  new LogWatcher(logLocation,"", lsm.getVolmodel(),Boolean.TRUE);
                     System.out.println("fend.session.node.headers.setRowFactory(): non empty log list!");
                     for (Iterator<Logs> iterator = loglist.iterator(); iterator.hasNext();) {
                     Logs logs = iterator.next();
                     Long numberOfRuns=logs.getVersion();
                     String timestamp=logs.getTimestamp();
                     String logfilePath=logs.getLogpath();
                     VersionLogsModel lmod=new VersionLogsModel(numberOfRuns,timestamp,logfilePath);
                     verslogsmodel.add(lmod);
                     }*/
                     
                     
                 }
                 
                 for (Iterator<Logs> iterator = loglist.iterator(); iterator.hasNext();) {
                         Logs logs = iterator.next();
                         Long version=logs.getHeaders().getNumberOfRuns();
                         String timestamp=logs.getTimestamp();
                         String logfilePath=logs.getLogpath();
                         VersionLogsModel lmod=new VersionLogsModel(version,timestamp,logfilePath);
                         verslogsmodel.add(lmod);
                     }      
                 
             }
             LogsModel logsmodel=new LogsModel();
             logsmodel.setLogsmodel(verslogsmodel);
             LogsNode logsnode=new LogsNode(logsmodel);
             
         });
         
         
         
          showWorkFlowVersion.setOnAction(evt->{
              Sequences seq=row.getItem();
              String subName=seq.getSubsurface();
             List<VersionLogsModel> verslogsmodel=new ArrayList<>();
             System.out.println("Sub: "+seq.getSubsurface()+" : alert is : "+seq.getQcAlert());
             System.out.println(""+lsm.getVolmodel().getLabel()+"  id: "+lsm.getVolmodel().getId());
             Volume v=vserv.getVolume(lsm.getVolmodel().getId());
            List<Headers> h=hdserv.getHeadersFor(v, seq.getSubsurface());
            System.out.println("fend.session.node.headers.HeadersViewController.setModel(): extracting headers size: "+h.size());
            
            if(h.size()!=1){
                System.out.println("fend.session.node.headers.HeadersViewController.setModel(): Something's unusual. more than more header entry found for :Volume: "+v.getNameVolume()+" : sub: "+seq.getSubsurface());
            }
            if(h!=null){
                List<Logs> loglist=lserv.getLogsFor(h.get(0));
                System.out.println("fend.session.node.headers.HeadersViewController.setModel(): loglist size: "+loglist.size());
                Logs latestlog= lserv.getLatestLogFor(v, subName);
                Workflow latestwf=latestlog.getWorkflow();
                WorkflowVersionTabModel wtabm=new WorkflowVersionTabModel();
                wtabm.setVersion(latestwf.getWfversion());
                wtabm.setWorkflowvContent(latestwf.getContents());
                
                Set<WorkflowVersionTabModel> wtabList=new HashSet<>();
                wtabList.add(wtabm);
                
                List<Workflow> wflist=new ArrayList<>();
                
                for (Iterator<Logs> iterator = loglist.iterator(); iterator.hasNext();) {
                    Logs llog = iterator.next();
                    Workflow wf=llog.getWorkflow();
                    WorkflowVersionTabModel wtabm1=new WorkflowVersionTabModel();
                    wtabm1.setVersion(wf.getWfversion());
                    wtabm1.setWorkflowvContent(wf.getContents());
                    wtabList.add(wtabm);
                }
                WorkflowVersionModel wfmodel=new WorkflowVersionModel((VolumeSelectionModelType1) lsm.getVolmodel());
                List<WorkflowVersionTabModel> lwflist=new ArrayList<>(wtabList);
                System.out.println("fend.session.node.headers.HeadersViewController.setModel(): about to set Wf model: lwlist size: "+lwflist.size());
                wfmodel.setWfmodel(lwflist);
                WorkflowVersionNode wfnode=new WorkflowVersionNode(wfmodel);
                
            }
            else{
                System.out.println("fend.session.node.headers.HeadersViewController.setModel(): No header entry found! :Volume: "+v.getNameVolume()+" : sub: "+seq.getSubsurface());
            }
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
        TreeTableColumn<Sequences,Long>  numberOfRuns=new TreeTableColumn<>("numberOfRuns");
        TreeTableColumn<Sequences,Boolean>  modified=new TreeTableColumn<>("modified");
        TreeTableColumn<Sequences,Boolean>  deleted=new TreeTableColumn<>("deleted");
        TreeTableColumn<Sequences,Long> workflowVersion=new TreeTableColumn<>("workflowVersion");
     
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
        numberOfRuns.setCellValueFactory(new TreeItemPropertyValueFactory<>("numberOfRuns"));
        modified.setCellValueFactory(new TreeItemPropertyValueFactory<>("modified"));
        deleted.setCellValueFactory(new TreeItemPropertyValueFactory<>("deleted"));
        workflowVersion.setCellValueFactory(new TreeItemPropertyValueFactory<>("workflowVersion"));
        numberOfRuns.setCellFactory((TreeTableColumn<Sequences,Long> p)->{
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
        
        
        
        treetableView.getColumns().addAll(sequenceNumber,subsurfaceName,alert,numberOfRuns,modified,deleted,timeStamp,tracecount,inlineMax,inlineMin,inlineInc,xlineMax,xlineMin,xlineInc,dugShotMax,dugShotMin,dugShotInc,dugChannelMax,dugChannelMin,dugChannelInc,offsetMax,offsetMin,offsetInc,cmpMax,cmpMin,cmpInc,insightVersion,workflowVersion);
        
     
     
     List<TreeItem<Sequences>> treeSeq = new ArrayList<>();
     Map<String,Long> gunShotMap;
     idxForTree.clear();
     
     for(Sequences s:seqListObs){
         gunShotMap=new HashMap<>();
         
         List<SubSurface> subs=s.getSubsurfaces();
        TreeItem<Sequences> seqroot=new TreeItem<>(s);
        String tempTimeStamp=subs.get(0).getTimeStamp();
        Long tempTrcCnt=0L;
        Long tempInlineMax=subs.get(0).getInlineMax();
        Long tempInlineMin=subs.get(0).getInlineMin();
        Long tempXlineMin=subs.get(0).getXlineMin();
        Long tempXlineMax=subs.get(0).getXlineMax();
        Long tempDugShotMax=subs.get(0).getDugShotMax();
        Long tempDugShotMin=subs.get(0).getDugShotMin();
        Long tempDugShotInc=subs.get(0).getDugShotInc();
        Long tempDugChannelMax=subs.get(0).getDugChannelMax();
        Long tempDugChannelMin=subs.get(0).getDugChannelMin();
        Long tempDugChannelInc=subs.get(0).getDugChannelInc();
        Long tempOffsetMax=subs.get(0).getOffsetMax();
        Long tempOffsetMin=subs.get(0).getOffsetMin();
        Long tempOffsetInc=subs.get(0).getOffsetInc();
        Long tempCmpMin=subs.get(0).getCmpMin();
        Long tempCmpMax=subs.get(0).getCmpMax();
        Long tempCmpInc=subs.get(0).getCmpInc();
        
        
        
         for(SubSurface sub:subs){
             TreeItem<Sequences> subItem=new TreeItem<>(sub);
             tempTrcCnt+=sub.getTraceCount();
             
             if(Long.valueOf(tempTimeStamp).longValue()<=Long.valueOf(sub.getTimeStamp()).longValue()){
             tempTimeStamp=sub.getTimeStamp();
             }
            
             
             if(tempInlineMin>=sub.getInlineMin()){
                 tempInlineMin=sub.getInlineMin();
             }
             if(tempInlineMax<=sub.getInlineMax()){
                 tempInlineMax=sub.getInlineMax();
             }
             
             if(tempXlineMin>=sub.getXlineMin()){
                 tempXlineMin=sub.getXlineMin();
             }
             if(tempXlineMax<=sub.getXlineMax()){
                 tempXlineMax=sub.getXlineMax();
             }
             
             String subname=sub.getSubsurface();
             String gun=subname.substring(subname.lastIndexOf("_")+1,subname.length());
             
             
             
             Long shots=(sub.getDugShotMax()-sub.getDugShotMin()+sub.getDugShotInc())/sub.getDugShotInc();
           //  System.out.println("fend.session.node.headers.setRowFactory():  shots = "+shots+" for gun: "+gun);
             if(gunShotMap.containsKey(gun)&& gunShotMap.get(gun)<=shots){
                 gunShotMap.put(gun, shots);
             }else{
                 if(!gunShotMap.containsKey(gun)){
                    gunShotMap.put(gun, shots); 
                 }
                 
             }
             
             
             if(tempOffsetMin>=sub.getOffsetMin()){
                 tempOffsetMin=sub.getOffsetMin();
             }
             if(tempOffsetMax<=sub.getOffsetMax()){
                 tempOffsetMax=sub.getOffsetMax();
             }
             
             if(tempOffsetInc!=sub.getOffsetInc()){
                 System.out.println("fend.session.node.headers.setRowFactory(): Offset inc is not the same across the sequence!: check the table");
             };
             
             
             if(tempCmpMin>=sub.getCmpMin()){
                 tempCmpMin=sub.getCmpMin();
             }
             if(tempCmpMax<=sub.getCmpMax()){
                 tempCmpMax=sub.getCmpMax();
             }
             if(tempCmpInc!=sub.getCmpInc()){
                 System.out.println("fend.session.node.headers.setRowFactory(): CMP inc is not the same across the sequence!: check the table");

             }
             Integer seqSel=new Integer(sub.getSequenceNumber()+"");
             if(seqSel==seqSelection){
                 idxForTree.put(seqSel, seqroot);
                 
             }
             
             seqroot.getChildren().add(subItem);
         }
         
         Long totalShots=0L;
         for (Map.Entry<String, Long> entrySet : gunShotMap.entrySet()) {
             String key = entrySet.getKey();
             Long value = entrySet.getValue();
           //  System.out.println(key+": "+value);
             totalShots+=value;
         }
         
         s.setTimeStamp(tempTimeStamp);
         s.setCmpMax(tempCmpMax);
         s.setCmpMin(tempCmpMin);
         s.setCmpInc(tempCmpInc);
         s.setDugShotInc(tempDugShotInc);
         s.setDugShotMax(totalShots);
         
         s.setInlineMax(tempInlineMax);
         s.setInlineMin(tempInlineMin);
         s.setXlineMax(tempXlineMax);
         s.setXlineMin(tempXlineMin);
         s.setTraceCount(tempTrcCnt);
         treeSeq.add(seqroot);
         
     }
     
     Sequences seqZero=new Sequences();
     TreeItem<Sequences> rootOfAllseq=new TreeItem<>();
     
     rootOfAllseq.getChildren().addAll(treeSeq);
     
     treetableView.setRoot(rootOfAllseq);
     treetableView.setShowRoot(false);
     treetableView.requestFocus();
     if(seqSelection!=0)
     {
         int rww=treetableView.getRow(idxForTree.get(seqSelection));
         treetableView.getSelectionModel().select(rww);
         treetableView.getFocusModel().focus(rww);
         treetableView.scrollTo(rww);
         idxForTree.get(seqSelection).setExpanded(true);
     }
     else{
         treetableView.getSelectionModel().selectFirst();
     }
     
     
   
     
    }

    void setView(HeadersNode aThis) {
        hnode=aThis;
        this.setTitle("Results for "+vname);
        this.setScene(new Scene(hnode));
        this.showAndWait();
    }

    
    
}
