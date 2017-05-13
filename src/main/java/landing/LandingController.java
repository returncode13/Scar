/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing;

import collector.Collector;
import com.sun.xml.internal.ws.util.Pool;
import db.model.Child;
import db.model.Headers;
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
import db.services.HeadersService;
import db.services.HeadersServiceImpl;
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
import fend.overview.OverviewItem;
import fend.session.SessionController;
import fend.session.SessionModel;
import fend.session.SessionNode;
import fend.session.edges.LinksModel;
import fend.session.edges.anchor.AnchorModel;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.Sequences;
import fend.session.node.headers.SubSurface;
//import fend.session.node.jobs.type1.JobStepType1Model;
//import fend.session.node.jobs.type1.JobStepType1NodeController;
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.type0.JobStepType0Model;
import fend.session.node.jobs.type0.JobStepType0NodeController;
import fend.session.node.jobs.type1.JobStepType1Model;
import fend.session.node.jobs.type2.JobStepType2Model;
import fend.session.node.volumes.VolumeSelectionModel;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import landing.loadingSession.LoadSessionController;
import landing.loadingSession.LoadSessionModel;
import landing.loadingSession.LoadSessionNode;
import landing.saveSession.SaveSessionController;
import landing.saveSession.SaveSessionModel;
import landing.saveSession.SaveSessionNode;
import landing.settings.database.DataBaseSettings;
import landing.settings.database.DataBaseSettingsController;
import landing.settings.database.DataBaseSettingsNode;
import landing.settings.ssh.SShSettings;
import landing.settings.ssh.SShSettingsController;
import landing.settings.ssh.SShSettingsNode;
import landing.settings.ssh.SShSettingsWrapper;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.controlsfx.control.GridView;

/**
 * FXML Controller class
 *
 * @author naila0152
 */
public class LandingController implements Initializable,Serializable {

    /**
     * Initializes the controller class.
     */
    
    private static final String sshSettingXml="src/main/resources/landingResources/settings/ssh/settings.xml";
    private static final String dbSettingXml="src/main/resources/landingResources/settings/database/databaseSettings.xml";

    public static String getSshSettingXml() {
        return sshSettingXml;
    }
    
    public static String getDbSettingXml(){
        return dbSettingXml;
    }
    
    private Long id;
    private LandingNode lnode;
    private LandingModel model;
    private Collector collector=new Collector();
    private ArrayList<SessionModel> sessModList=new ArrayList<>();
    private ObservableList<SessionModel> obsModL=FXCollections.observableArrayList(sessModList);
    private SessionModel smodel;
    private SessionNode snode;
    private SessionController scontr;
    
    
    private SShSettings settingsModel;
    private DataBaseSettings databaseSettingsModel;
    
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
    private MenuItem settings;

     @FXML
    private MenuItem dbsettings;
     
     @FXML
    void dbsettings(ActionEvent event) {
        databaseSettingsModel=new DataBaseSettings();
        File dbFile=new File(dbSettingXml);
        
        try {
            JAXBContext contextObj = JAXBContext.newInstance(DataBaseSettings.class);
            
            //try unmarshalling the file. if the fields are not null. populate settingsmodel
            
            Unmarshaller unm=contextObj.createUnmarshaller();
            DataBaseSettings dbsett=(DataBaseSettings) unm.unmarshal(dbFile);
            System.out.println("landing.LandingController.settings():  unmarshalled: "+dbsett.getChosenDatabase());
            
            databaseSettingsModel.setDbUser(dbsett.getDbUser());
            databaseSettingsModel.setDbPassword(dbsett.getDbPassword());
            databaseSettingsModel.setChosenDatabase(dbsett.getChosenDatabase());
            
            DataBaseSettingsNode dbnode=new DataBaseSettingsNode(databaseSettingsModel);
            DataBaseSettingsController dcontrl=dbnode.getDataBaseSettingsController();
            
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(databaseSettingsModel, new File(dbSettingXml));
            
        } catch (JAXBException ex) {
            Logger.getLogger(LandingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void settings(ActionEvent event) {
        
         settingsModel=new SShSettings();
         File sFile=new File(sshSettingXml);
        
        try {
            JAXBContext contextObj = JAXBContext.newInstance(SShSettings.class);
            
            //try unmarshalling the file. if the fields are not null. populate settingsmodel
            
            Unmarshaller unm=contextObj.createUnmarshaller();
            SShSettings sett=(SShSettings) unm.unmarshal(sFile);
            System.out.println("landing.LandingController.settings():  unmarshalled: "+sett.getSshHost() );
            
            
            
            if(sett.isPopulated()){
                settingsModel.setDbPassword(sett.getDbPassword());
                settingsModel.setDbUser(sett.getDbUser());
                settingsModel.setId(sett.getId());
                settingsModel.setSshHost(sett.getSshHost());
                settingsModel.setSshPassword(sett.getSshPassword());
                settingsModel.setSshUser(sett.getSshUser());
            }
            
            SShSettingsNode setnode=new SShSettingsNode(settingsModel);
            SShSettingsController sc=new SShSettingsController();
            
            //save the xml
            
            
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(settingsModel, new File(sshSettingXml));
            
           
        } catch (JAXBException ex) {
            Logger.getLogger(LandingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    
    
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
        
        System.out.println("landing.LandingController.saveCurrentSession(): sessionName: "+smodel.getName());
                
        
        //if smodel.name==null or empty open a dialogue box to save name. i.e call saveSessionAs(event)
        if(smodel.getName()==null || smodel.getName().isEmpty()){
            saveSessionAs(event);
        }
        else{
            
        }
        
        System.out.println("LC: Saving session with Id: "+smodel.getId()+" and name: "+smodel.getName());
            scontr.setAllLinksAndJobsForCommit();
            
            ObservableList<JobStepType0Model> ajs= smodel.getListOfJobs();
            
            for (Iterator<JobStepType0Model> iterator = ajs.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
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
    void loadSession(ActionEvent event) {    //All of this needs to go to the "Controller" class in package controller. This is just POC.
       
        
        
            String  regex=".\\d*.\\d*-[a-zA-Z0-9_-]*";  
        Pattern pattern=Pattern.compile(regex);
        
       File[] versions= JobStepType0NodeController.insightLocation.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pattern.matcher(pathname.getName()).matches();
            }
        });
        
        List<String> allAvailableVs=new ArrayList<>();
        for (File version:versions) {
            
            String n=version.getName();
            allAvailableVs.add(n);
        }
        
        
        
        
        
        
        
       
        
        
        ArrayList<JobStepType0Model> jmodList=new ArrayList<>();
        
        
        
        
        
        
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
        
        System.out.println("landing.LandingController.loadSession(): Name of session thats about to be loaded: "+sessionFromDB.getNameSessions());
        
        
        //next get all jobs belonging to that session from the sessionDetails Table
        SessionDetailsService ssDserv=new SessionDetailsServiceImpl();
        List<SessionDetails> lsd=ssDserv.getSessionDetails(sessionFromDB);           //get all the sessionDetails which belong to the session = sessionFromDB;
        
        List<JobStep> js=new ArrayList<>();
        
        ParentService pserv=new ParentServiceImpl();
        ChildService cserv=new ChildServiceImpl();
        
        JobVolumeDetailsService jvdserv=new JobVolumeDetailsServiceImpl();
        HeadersService hdrServ=new HeadersServiceImpl(); 
        
        Map<JobStep,JobStep> parentAndJobMap=new HashMap<>();   
        Map<JobStep,JobStep> childAndJobMap=new HashMap<>();                        //Use these maps to link the cubic curves
        
        for (Iterator<SessionDetails> iterator = lsd.iterator(); iterator.hasNext();) {
            SessionDetails next = iterator.next();
            
            Sessions beSessions=next.getSessions();
            JobStep beJobStep=next.getJobStep();                                    //beJobstep belongs to beSessions
            
            js.add(beJobStep);
            
            JobStepType0Model fejsm=null;//=new JobStepType0Model(null);
            Long type=beJobStep.getType();
            if(type.equals(1L)){
                fejsm=new JobStepType1Model(null);
            }
            if(type.equals(2L)){
                fejsm=new JobStepType2Model(null);
            }
            fejsm.setJobStepText(beJobStep.getNameJobStep());
            fejsm.setId(beJobStep.getIdJobStep());
            
          
            String concatString=beJobStep.getInsightVersions();                     //will return a string of the format v1;v2;v3;..vn;
            
            String[] tokens=concatString.split(";");
            
        
            List<String> selectedversions=new ArrayList<>(Arrays.asList(tokens));
            
        
            
            
            System.out.println("landing.LandingController.loadSession() Versions found: ");
            
            
           InsightVersionsModel ivm=new InsightVersionsModel(allAvailableVs);
           ivm.setCheckedVersions(selectedversions);
           fejsm.setInsightVersionsModel(ivm);
            
            
            
            
            //get the parents of this jobstep
            
            /* List<Parent> lParent=pserv.getParentsFor(next);
            
            for (Iterator<Parent> iterator1 = lParent.iterator(); iterator1.hasNext();) {
            Parent next1 = iterator1.next();
            Long parentjobId=next1.getParent();
            SessionDetails parentJobssd=ssDserv.getSessionDetails(parentjobId);
            //System.out.println(beJobStep.getNameJobStep()+" :has Parent: "+ parentJobssd.getJobStep().getNameJobStep());
            parentAndJobMap.put(beJobStep, parentJobssd.getJobStep());
            
            Long parentId=parentJobssd.getJobStep().getIdJobStep();
            
            boolean parentExists=false;
            
            for (Iterator<JobStepModel> iterator2 = jmodList.iterator(); iterator2.hasNext();) {
            JobStepModel next2 = iterator2.next();
            if(next2.getId().equals(parentId)){
            parentExists=true;
            fejsm.addToParent(next2);
            }
            
            }
            
            if(!parentExists){
            JobStepModel pjobStepModel=new JobStepModel();
            pjobStepModel.setJobStepText(parentJobssd.getJobStep().getNameJobStep());
            pjobStepModel.setId(parentJobssd.getJobStep().getIdJobStep());
            
            
            
            fejsm.addToParent(pjobStepModel);
            }
            
            
            }*/
            
            //get children of this jobstep
            
            /*  List<Child> lChild=cserv.getChildrenFor(next);
            for (Iterator<Child> iterator1 = lChild.iterator(); iterator1.hasNext();) {
            Child next1 = iterator1.next();
            Long childjobId=next1.getChild();
            SessionDetails childssd=ssDserv.getSessionDetails(childjobId);
            //System.out.println(beJobStep.getNameJobStep()+" :has Child: "+ childssd.getJobStep().getNameJobStep());
            childAndJobMap.put(beJobStep, childssd.getJobStep());
            
            
            Long childId=childssd.getJobStep().getIdJobStep();
            
            //in jmodList find the job that has the same id as the childId
            boolean childExists=false;
            
            for (Iterator<JobStepModel> iterator2 = jmodList.iterator(); iterator2.hasNext();) {
            JobStepModel next2 = iterator2.next();
            if(next2.getId().equals(childId))
            {
            childExists=true;
            fejsm.addToChildren(next2);
            }
            
            }
            if(!childExists)
            {
            JobStepModel cJobStepModel=new JobStepModel();
            cJobStepModel.setJobStepText(childssd.getJobStep().getNameJobStep());
            cJobStepModel.setId(childssd.getJobStep().getIdJobStep());
            
            fejsm.addToChildren(cJobStepModel);
            }
            
            
            
            }*/
            
            
            
            //get all volumes related to beJobstep from the table JobVolumeDetails
            
            
            List<JobVolumeDetails> bejobVols= jvdserv.getJobVolumeDetails(beJobStep);        //this is the list of all the jobVolumeDetail entries related to beJobStep
            List<Volume> beVols=new ArrayList<>();                                           // A list to hold the volumes related to this beJobStep
            List<VolumeSelectionModel> feVols=new ArrayList<>();                             // A list to hold the volume models corresponding to beVols.  Frontend equivalents
            
            for (Iterator<JobVolumeDetails> iterator1 = bejobVols.iterator(); iterator1.hasNext();) {
                JobVolumeDetails next1 = iterator1.next();
                
                Volume beV=next1.getVolume();
                beVols.add(beV);
                
                //Load headers for beV i.e the backend Volume.
                List<Headers> hl=hdrServ.getHeadersFor(beV);
                Set<SubSurface> sl=new HashSet<>();
                List<Sequences> seqList=new ArrayList<>();
                MultiMap<Long,SubSurface> seqSubMap=new MultiValueMap<>();                                             //for creating association between Sequences and Subsurfaces
      
                        for (Iterator<Headers> iteratornn = hl.iterator(); iteratornn.hasNext();) {
                            Headers beH = iteratornn.next();
                            SubSurface s= new SubSurface();
          
                            s.setSequenceNumber(beH.getSequenceNumber());
                            s.setSubsurface(beH.getSubsurface());
                            s.setTimeStamp(beH.getTimeStamp());

                            s.setCmpInc(beH.getCmpInc());
                            s.setCmpMax(beH.getCmpMax());
                            s.setCmpMin(beH.getCmpMin());
                            s.setDugChannelInc(beH.getDugChannelInc());
                            s.setDugChannelMax(beH.getDugChannelMax());
                            s.setDugChannelMin(beH.getDugChannelMin());
                            s.setDugShotInc(beH.getDugShotInc());
                            s.setDugShotMax(beH.getDugShotMax());
                            s.setDugShotMin(beH.getDugShotMin());
                            s.setInlineInc(beH.getInlineInc());
                            s.setInlineMax(beH.getInlineMax());
                            s.setInlineMin(beH.getInlineMin());
                            s.setOffsetInc(beH.getOffsetInc());
                            s.setOffsetMax(beH.getOffsetMax());
                            s.setOffsetMin(beH.getOffsetMin());

                            s.setTraceCount(beH.getTraceCount());
                            s.setXlineInc(beH.getXlineInc());
                            s.setXlineMax(beH.getXlineMax());
                            s.setXlineMin(beH.getXlineMin());
                            s.setModified(beH.getModified());
                            s.setDeleted(beH.getDeleted());
                            s.setVersion(beH.getNumberOfRuns());
                            s.setInsightVersion(beH.getInsightVersion());
                
                            
          
                            //
                            //
                            //
                            //}
                            /*
                            List<Logs> logslist=logserv.getLogsFor(beh);
                            LogsModel lm=new LogsModel()
                            for(Logs l:logslist){
                                Long v=l.getVersion();
                                String logPath=l.getLogpath();
                                File logfile=new File(logPath);
                                lm.addToVersionLogFileMap(v,logfile);
                            }
                            s.setLogsModel(lm);
                            */
                            
                            
                            seqSubMap.put(s.getSequenceNumber(), s);


                            sl.add(s);
          
                        }
      
                        Set<Long> seqNos=seqSubMap.keySet();


                        for (Iterator<Long> iteratorSeq = seqNos.iterator(); iteratorSeq.hasNext();) {
                            Long seq_no = iteratorSeq.next();
                            Sequences sq=new Sequences();
                            ArrayList<SubSurface> ssubs=(ArrayList<SubSurface>) seqSubMap.get(seq_no);
                            sq.setSubsurfaces(ssubs);
                            seqList.add(sq);
                        }
      
      
                
                
                VolumeSelectionModel fv=new VolumeSelectionModel(beV.getVolumeType());
                fv.setVolumeChosen(new File(beV.getPathOfVolume()));
                fv.setHeaderButtonStatus(!beV.getHeaderExtracted());     // if extracted is true then the status of disablity should be false
                fv.setAlert(beV.getAlert());
                fv.setLabel(beV.getNameVolume());
                fv.setId(beV.getIdVolume());
                fv.setVolumeType(beV.getVolumeType());
                System.out.println("landing.LandingController.loadSession(): Volume name: "+beV.getNameVolume()+" : Volume Id: "+beV.getIdVolume());
                fv.setInflated(true);
                fv.setSubsurfaces(sl);
                
                            HeadersModel hmod=new HeadersModel(fv);
                            ObservableList<Sequences> obseq=FXCollections.observableArrayList(seqList);
                            hmod.setSequenceListInHeaders(obseq);
                
                
                fv.setHeadersModel(hmod);                                       //set the headersModel
                feVols.add(fv);
                
                ObservableList<VolumeSelectionModel> obv=FXCollections.observableArrayList(feVols);
                
                
                fejsm.setVolList(obv);
            }
            
            
           
            
            
            
            jmodList.add(fejsm);                          //This list now contains all the jobs that belong to the session. (names and ids)..one job at a time in the loop
            
            
            
            
            
            
        }
       
        
        
        for (Iterator<SessionDetails> iterator = lsd.iterator(); iterator.hasNext();) {
            SessionDetails next = iterator.next();
            
            Sessions beSessions=next.getSessions();
            JobStep beJobStep=next.getJobStep();                                    //beJobstep belongs to beSessions
            Long type=beJobStep.getType();
            
            JobStepType0Model fejsm=null;
            if(type.equals(1L)){
                fejsm=new JobStepType1Model(null);
            }if(type.equals(2L)){
                fejsm=new JobStepType2Model(null);
            }
           
            
            for (Iterator<JobStepType0Model> iterator1 = jmodList.iterator(); iterator1.hasNext();) {
                JobStepType0Model next1 = iterator1.next();
                if(next1.getId().equals(beJobStep.getIdJobStep())){
                    fejsm=next1;
                }
                
                
            }
            
            List<Child> lChild=cserv.getChildrenFor(next);
            for (Iterator<Child> iterator1 = lChild.iterator(); iterator1.hasNext();) {
                Child next1 = iterator1.next();
                Long childjobId=next1.getChild();
                SessionDetails childssd=ssDserv.getSessionDetails(childjobId);
                //System.out.println(beJobStep.getNameJobStep()+" :has Child: "+ childssd.getJobStep().getNameJobStep());
                childAndJobMap.put(beJobStep, childssd.getJobStep());
                
                
                Long childId=childssd.getJobStep().getIdJobStep();
                
                //in jmodList find the job that has the same id as the childId
               
                
                    for (Iterator<JobStepType0Model> iterator2 = jmodList.iterator(); iterator2.hasNext();) {
                    JobStepType0Model next2 = iterator2.next();
                    if(next2.getId().equals(childId))
                    {
                       
                        fejsm.addToChildren(next2);
                    }
                    
                }
                   
                
                
               
            }
            
             List<Parent> lParent=pserv.getParentsFor(next);
            
            for (Iterator<Parent> iterator1 = lParent.iterator(); iterator1.hasNext();) {
                Parent next1 = iterator1.next();
                Long parentjobId=next1.getParent();
                SessionDetails parentJobssd=ssDserv.getSessionDetails(parentjobId);
                //System.out.println(beJobStep.getNameJobStep()+" :has Parent: "+ parentJobssd.getJobStep().getNameJobStep());
                parentAndJobMap.put(beJobStep, parentJobssd.getJobStep());
                
                Long parentId=parentJobssd.getJobStep().getIdJobStep();
                
              
                
                for (Iterator<JobStepType0Model> iterator2 = jmodList.iterator(); iterator2.hasNext();) {
                    JobStepType0Model next2 = iterator2.next();
                    if(next2.getId().equals(parentId)){
                       
                        fejsm.addToParent(next2);
                    }
                    
                }
               
                
            }
            
        }
        
        for (Iterator<JobStepType0Model> iterator = jmodList.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            List<JobStepType0Model> children=next.getJsChildren();
            for (Iterator<JobStepType0Model> iterator1 = children.iterator(); iterator1.hasNext();) {
                JobStepType0Model next1 = iterator1.next();
                System.out.println("landing.LandingController.loadSession(): job : "+next.getJobStepText()+" :has child: "+next1.getJobStepText());
            }
            
            List<JobStepType0Model> parents=next.getJsParents();
            for (Iterator<JobStepType0Model> iterator1 = parents.iterator(); iterator1.hasNext();) {
                JobStepType0Model next1 = iterator1.next();
                System.out.println("landing.LandingController.loadSession(): job : "+next.getJobStepText()+" :has parent: "+next1.getJobStepText());
            }
        }

        
        
        //Parents and Children for links
        
        ObservableList<LinksModel> oLink=FXCollections.observableArrayList();
        
        for (Iterator<JobStepType0Model> iterator = jmodList.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            
            List<JobStepType0Model> parentList = next.getJsParents();
            List<JobStepType0Model> childList = next.getJsChildren();
            
            for (Iterator<JobStepType0Model> iterator1 = parentList.iterator(); iterator1.hasNext();) {
                JobStepType0Model next1 = iterator1.next();
                
                
                
                    for (Iterator<JobStepType0Model> iterator2 = childList.iterator(); iterator2.hasNext();) {
                    JobStepType0Model next2 = iterator2.next();
                    LinksModel lm=new LinksModel();
                    lm.setParent(next1);
                    lm.setChild(next2);
                    next2.addToListOfLinksModel(lm);
                    next1.addToListOfLinksModel(lm);
                    oLink.add(lm);
                }
                
                 
                 
                    
                 
                
            }
            
            
           
            
        }
        
       
        
        
        
        
        //Front end 
       smodel = new SessionModel();
       smodel.setName(sessionFromDB.getNameSessions());            //front end sessionModel name is the same as the backends
       smodel.setId(sessionFromDB.getIdSessions());                //front end sessionModel id is the same as the backends
       ObservableList<JobStepType0Model> otemp=FXCollections.observableArrayList(jmodList);
        for (Iterator<JobStepType0Model> iterator = otemp.iterator(); iterator.hasNext();) {
            JobStepType0Model jsmodel = iterator.next();
            jsmodel.setSessionModel(smodel);
            
        }
       smodel.setListOfJobs(otemp);
       snode=new SessionNode(smodel);                               //same id as the one in the database
       scontr=snode.getSessionController();
       obsModL.clear();                                             //clear the previous model from the list
       obsModL.add(smodel);
       basePane.getChildren().clear();                              //clear previous setup... this will not save! fix this. Save the current session before loading a new one.
       //saveCurrentSession(event);
       basePane.getChildren().add(snode);
       ObservableList<JobStepType0Model> obj=FXCollections.observableArrayList(jmodList);
       
        for (Iterator<JobStepType0Model> iterator = obj.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            System.out.println("landing.LandingController.loadSession(): jobs to be loaded "+next.getJobStepText());
            
        }
       
       scontr.setObsModelList(obj);
       scontr.setAllModelsForFrontEndDisplay();
       scontr.setAllLinksForFrontEnd();
       scontr.startWatching(); 
        /*
        
        
        //next get the list of jobVolumeDetails associated with each of the jobs from the jobVolumeDetails table
        
        
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
                
                
                
               
            }
           
       }
       */ 
    
        
     
     
     
        
            
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
