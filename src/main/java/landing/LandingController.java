/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing;

import collector.Collector;
import db.model.Child;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.Parent;
import db.model.SessionDetails;
import db.model.Sessions;
import db.model.Volume;
import db.services.AncestorsService;
import db.services.AncestorsServiceImpl;
import db.services.ChildService;
import db.services.ChildServiceImpl;
import db.services.JobStepService;
import db.services.JobStepServiceImpl;
import db.services.JobVolumeDetailsService;
import db.services.JobVolumeDetailsServiceImpl;
import db.services.ParentService;
import db.services.ParentServiceImpl;
import db.services.SessionDetailsService;
import db.services.SessionDetailsServiceImpl;
import db.services.SessionsService;
import db.services.SessionsServiceImpl;
import fend.session.SessionController;
import fend.session.SessionModel;
import fend.session.SessionNode;
import fend.session.edges.LinksModel;
import fend.session.node.jobs.JobStepModel;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import landing.loadingSession.LoadSessionController;
import landing.loadingSession.LoadSessionModel;
import landing.loadingSession.LoadSessionNode;
import landing.saveSession.SaveSessionController;
import landing.saveSession.SaveSessionModel;
import landing.saveSession.SaveSessionNode;

/**
 * FXML Controller class
 *
 * @author naila0152
 */
public class LandingController implements Initializable,Serializable {

    /**
     * Initializes the controller class.
     */
    
    private Long id;
    private LandingNode lnode;
    private LandingModel model;
    private Collector collector=new Collector();
    private ArrayList<SessionModel> sessModList=new ArrayList<>();
    private ObservableList<SessionModel> obsModL=FXCollections.observableArrayList(sessModList);
    private SessionModel smodel;
    private SessionNode snode;
    private SessionController scontr;
     @FXML
    private StackPane basePane;

    
    @FXML
    private MenuItem saveSessionAs;

    @FXML
    private MenuItem loadSession;

    @FXML
    private MenuBar menubar;

    @FXML
    private MenuItem exit;

    @FXML
    private MenuItem startSession;

    @FXML
    private MenuItem saveCurrentSession;

    @FXML
    void startNewSession(ActionEvent event) {
            smodel = new SessionModel();
            snode = new SessionNode(smodel);
            scontr=snode.getSessionController();
            obsModL.add(smodel);
            basePane.getChildren().add(snode);
            
            
    }

    @FXML
    void saveCurrentSession(ActionEvent event) {
        
        //if smodel.name==null or empty open a dialogue box to save name. i.e call saveSessionAs(event)
        if(smodel.getName()==null || smodel.getName().isEmpty()){
            saveSessionAs(event);
        }
        else{
            
        }
        
        System.out.println("LC: Saving session with Id: "+smodel.getId()+" and name: "+smodel.getName());
            scontr.setAllLinksAndJobsForCommit();
            
            ArrayList<JobStepModel> ajs= smodel.getListOfJobs();
            
            for (Iterator<JobStepModel> iterator = ajs.iterator(); iterator.hasNext();) {
            JobStepModel next = iterator.next();
                System.out.println("LC: Job id# "+next.getId()+" text: "+next.getJobStepText());
            
        }
            ArrayList<LinksModel> alk =smodel.getListOfLinks();
            
            for (Iterator<LinksModel> iterator = alk.iterator(); iterator.hasNext();) {
            LinksModel next = iterator.next();
            System.out.println("LC: Job id# "+next.getId()+" Parent:" +next.getParent().getJobStepText()+ " Child: "+next.getChild().getJobStepText());
            
        }
            collector.saveCurrentSession(smodel);
    }

    @FXML
    void saveSessionAs(ActionEvent event) {
            //open a dialogue box here
            SaveSessionModel ssm=new SaveSessionModel();
            SaveSessionNode sessnode=new SaveSessionNode(ssm);
            SaveSessionController sc=sessnode.getSaveSessionController();
            
                      
            String name=ssm.getName();
            smodel.setName(name);
            System.out.println("landing.LandingController.saveSessionAs(): "+smodel.getName());
            
            saveCurrentSession(event);
             
           
    }

    @FXML
    void loadSession(ActionEvent event) {
        
        /*
        query the database for existing entries in the table "Sessions"
        */
        
        SessionsService sserv=new SessionsServiceImpl();
        List<Sessions> list=sserv.listSessions();
        
        
        
        
        ObservableList<Sessions> obsList=FXCollections.observableArrayList();
        
        for (Iterator<Sessions> iterator = list.iterator(); iterator.hasNext();) {
            Sessions next = iterator.next();
            obsList.add(next);
            
        }
        
        
        
        LoadSessionModel lsm=new LoadSessionModel(obsList);
        LoadSessionNode lnode=new LoadSessionNode(lsm);
        LoadSessionController lc=lnode.getLoadSessionController();  // when this closes. lsm.sessionToBeLoaded will be the session that needs to be loaded from the database.
        
        Sessions sessionToBeLoaded=lsm.getSessionToBeLoaded();
        
        Sessions sessionFromDB=sserv.getSessions(sessionToBeLoaded.getIdSessions());
        
        //next get all jobs belonging to that session from the sessionDetails Table
        SessionDetailsService ssDserv=new SessionDetailsServiceImpl();
        List<SessionDetails> lsd=ssDserv.getSessionDetails(sessionFromDB);           //get all the sessionDetails which belong to the session = sessionFromDB;
        
        List<JobStep> js=new ArrayList<>();
        
        ParentService pserv=new ParentServiceImpl();
        ChildService cserv=new ChildServiceImpl();
        
        Map<JobStep,JobStep> parentAndJobMap=new HashMap<>();   
        Map<JobStep,JobStep> childAndJobMap=new HashMap<>();                        //Use these maps to link the cubic curves
        
        for (Iterator<SessionDetails> iterator = lsd.iterator(); iterator.hasNext();) {
            SessionDetails next = iterator.next();
            js.add(next.getJobStep());
            
            //get the parents of this jobstep
            
            List<Parent> lParent=pserv.getParentsFor(next);
            
            for (Iterator<Parent> iterator1 = lParent.iterator(); iterator1.hasNext();) {
                Parent next1 = iterator1.next();
                Long parentjobId=next1.getParent();
                SessionDetails parentJobssd=ssDserv.getSessionDetails(parentjobId);
                System.out.println(next.getJobStep().getNameJobStep()+" :has Parent: "+ parentJobssd.getJobStep().getNameJobStep());
                parentAndJobMap.put(next.getJobStep(), parentJobssd.getJobStep());
                
            }
            
            //get children of this jobstep
            
            List<Child> lChild=cserv.getChildrenFor(next);
            for (Iterator<Child> iterator1 = lChild.iterator(); iterator1.hasNext();) {
                Child next1 = iterator1.next();
                Long childjobId=next1.getChild();
                SessionDetails childssd=ssDserv.getSessionDetails(childjobId);
                System.out.println(next.getJobStep().getNameJobStep()+" :has Child: "+ childssd.getJobStep().getNameJobStep());
                childAndJobMap.put(next.getJobStep(), childssd.getJobStep());
            }
            
        }
       
       
        
        
        
        
        
        //next get the list of jobVolumeDetails associated with each of the jobs from the jobVolumeDetails table
        
        JobVolumeDetailsService jvdserv=new JobVolumeDetailsServiceImpl();
        List<List<JobVolumeDetails>> ljvd = new ArrayList<>();
          
                
        for (Iterator<JobStep> iterator = js.iterator(); iterator.hasNext();) {
            JobStep next = iterator.next();
            ljvd.add(jvdserv.getJobVolumeDetails(next));                      //This is a list of jobvolumedetail entries related to one job. Many to Many relation
            
            
        }
        
        System.err.println("");
        System.out.println("Session Loaded: "+sessionFromDB.getNameSessions()+" :with ID: "+sessionFromDB.getIdSessions());
        System.err.println("");
        System.out.println("The following jobsteps are present in the session");
         for (Iterator<JobStep> iterator = js.iterator(); iterator.hasNext();) {
            JobStep next = iterator.next();
             System.out.println(next.getNameJobStep()+" :with ID: "+next.getIdJobStep());
            
        }
        System.out.println("");
        System.out.println("The following jobstep:Volumes are present");
       
        //next extract the list of volumes belonging to each job from the jobVolumeDetail list
         
        for (Iterator<List<JobVolumeDetails>> iterator = ljvd.iterator(); iterator.hasNext();) {
            List<JobVolumeDetails> next = iterator.next();
            
                for (Iterator<JobVolumeDetails> iterator1 = next.iterator(); iterator1.hasNext();) {
                JobVolumeDetails next1 = iterator1.next();
                System.out.println(next1.getJobStep().getNameJobStep()+"-   Volume: "+next1.getVolume().getNameVolume()+ ":with ID: "+next1.getVolume().getIdVolume());
                
                
                
                /*
                EXTRACT HEADERS HERE inside a further for loop.
                */
            }
           
       }
        
        
        
            
    }

    @FXML
    void exitTheProgram(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setId(Long valueOf) {
        this.id=valueOf;
    }

    public Long getId() {
        return id;
    }
    
    

    void setModel(LandingModel lm) {
        this.model=lm;
    }

    void setView(LandingNode aThis) {
       this.lnode=aThis;
    }
    
}
