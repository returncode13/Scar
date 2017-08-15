/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.workflows;

import db.handler.ObpManagerLogDatabaseHandler;
import db.model.Volume;
import db.model.Workflow;
import db.services.LogsService;
import db.services.LogsServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import db.services.WorkflowService;
import db.services.WorkflowServiceImpl;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import de.cismet.custom.visualdiff.VersionHolder;
import de.cismet.custom.visualdiff.WorkflowDifferenceModel;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.StackPane;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.openide.util.Exceptions;
/**
 *
 * @author sharath nair
 * sharath.nair@polarcus.com
 */
public class WorkflowVersionController extends Stage implements Initializable{
    
    Logger logger=Logger.getLogger(WorkflowVersionController.class.getName());
    ObpManagerLogDatabaseHandler obpManagerLogDatabaseHandler=new ObpManagerLogDatabaseHandler();
    private WorkflowVersionModel model;
    private WorkflowVersionNode node;
    ObservableList<Long> versionsObsList;
    List<VersionHolder> sublineVersions=new ArrayList<>();
    List<VersionHolder> volumeVersions=new ArrayList<>();
    //private LogsService lserv=new LogsServiceImpl();
    private WorkflowService wserv=new WorkflowServiceImpl();
    private VolumeService vserv=new VolumeServiceImpl();
            
    
    @FXML
    private TabPane tabpane;
    
     @FXML
    private MenuItem compareVersions;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    
     @FXML
    void compare(ActionEvent event) {
        try {
            WorkflowDifferenceModel wfdm=new WorkflowDifferenceModel();
            wfdm.setlVersionHolder(sublineVersions);
            wfdm.setrVersionHolder(volumeVersions);
            WorkflowDifferenceFrameModel wfdFrameModel=new WorkflowDifferenceFrameModel();
            wfdFrameModel.setDifferenceModel(wfdm);
            
            WorkflowDifferenceFrameNode wfdFrameNode=new WorkflowDifferenceFrameNode(wfdFrameModel);
            
            
           
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            Exceptions.printStackTrace(ex);
        }
    }

    void setModel(WorkflowVersionModel lsm) {
        try{
        model=lsm;
        List<WorkflowVersionTabModel> tabContents=model.getWfmodel();
        Volume v= vserv.getVolume(lsm.getVolumeSelectionModel().getId());
        List<Workflow> wflist=wserv.getWorkFlowsFor(v);
        for (Iterator<Workflow> iterator = wflist.iterator(); iterator.hasNext();) {
            
            Workflow wf = iterator.next();
            VersionHolder vh=new VersionHolder();
            vh.setVersion(wf.getWfversion()+"");
            vh.setContent(wf.getContents());
            volumeVersions.add(vh);
        }
        
      // System.out.println("fend.session.node.headers.workflows.WorkflowVersionController.setModel(): tabListsize: "+tabContents.size());
        for (Iterator<WorkflowVersionTabModel> iterator = tabContents.iterator(); iterator.hasNext();) {
            WorkflowVersionTabModel versionsTab = iterator.next();
            String wfcontent=versionsTab.getWorkflowvContent();
            Long wfv=versionsTab.getVersion();
            VersionHolder vhs=new VersionHolder();
            vhs.setContent(wfcontent);
            vhs.setVersion(wfv+"");
            sublineVersions.add(vhs);
            //System.out.println("fend.session.node.headers.workflows.WorkflowVersionController.setModel(): contents: "+wfcontent);
            Tab tab=new Tab();
            tab.setText("     "+wfv.toString()+"     " );
            
            HBox hbox=new HBox();
            TextArea ta=new TextArea();
            ta.wrapTextProperty().setValue(Boolean.TRUE);
            ta.setText(wfcontent);
            ta.setEditable(Boolean.FALSE);
            ta.prefHeightProperty().bind(hbox.heightProperty());
            ta.prefWidthProperty().bind(hbox.widthProperty());
            hbox.getChildren().add(ta);
            hbox.setAlignment(Pos.CENTER);
            tab.setContent(hbox);
            tabpane.getTabs().add(tab);
            
        }
        }catch(Exception ex){
            logger.severe(ex.getMessage());
        }
    }

    void setView(WorkflowVersionNode aThis) {
        try{
        this.node=aThis;
        this.setScene(new Scene(node));
        this.showAndWait();
        }catch(Exception ex){
            logger.severe(ex.getMessage());
        }
    }

    public WorkflowVersionController() {
    logger.addHandler(obpManagerLogDatabaseHandler);
    logger.setLevel(Level.SEVERE);
    }
    
    
    
    
}
