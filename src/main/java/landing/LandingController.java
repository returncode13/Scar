/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing;

import collector.Collector;
import com.sun.xml.internal.ws.util.Pool;
import db.handler.ObpManagerLogDatabaseHandler;
import db.model.Child;
import db.model.Headers;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.NodePropertyValue;
import db.model.NodeType;
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
import db.services.NodePropertyValueService;
import db.services.NodePropertyValueServiceImpl;
import db.services.NodeTypeService;
import db.services.NodeTypeServiceImpl;
import db.services.ParentService;
import db.services.ParentServiceImpl;
import db.services.SessionDetailsService;
import db.services.SessionDetailsServiceImpl;
import db.services.SessionsService;
import db.services.SessionsServiceImpl;

import fend.session.SessionController;
import fend.session.SessionModel;
import fend.session.SessionNode;
import fend.session.dialogs.DialogModel;
import fend.session.dialogs.DialogNode;
import fend.session.edges.LinksModel;
import fend.session.edges.anchor.AnchorModel;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.acquisitionType.AcquisitionJobStepModel;
//import fend.session.node.jobs.type1.JobStepType1Model;
//import fend.session.node.jobs.type1.JobStepType1NodeController;
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.nodeproperty.JobModelProperty;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type0.JobStepType0NodeController;
import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.jobs.types.type2.JobStepType2Model;
import fend.session.node.jobs.types.type4.JobStepType4Model;
import fend.session.node.volumes.acquisition.AcquisitionVolumeModel;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import fend.session.node.volumes.type2.VolumeSelectionModelType2;
import fend.session.node.volumes.type4.VolumeSelectionModelType4;
//import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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
import java.util.logging.LogManager;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
import landing.reporter.ReporterModel;
import landing.reporter.ReporterNode;
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
//import org.openide.util.Exceptions;

/**
 * FXML Controller class
 *
 * @author sharath nair
 * sharath.nair@polarcus.com
 */
public class LandingController extends Stage implements Initializable,Serializable {

    /**
     * Initializes the controller class.
     */
    
    //private static final String sshSettingXml="src/main/resources/landingResources/settings/ssh/settings.xml";
    //private static final String dbSettingXml="src/main/resources/landingResources/settings/database/databaseSettings.xml";
    
   // private static final String dbSettingXml="landingResources/settings/database/databaseSettings.xml";
    /*private static final String sshSettingXml="landingResources/settings/ssh/settings.xml";
    private static final String dbSettingXml="landingResources/settings/database/databaseSettings.xml";*/
    ObpManagerLogDatabaseHandler obpManagerLogDatabaseHandler=new ObpManagerLogDatabaseHandler();
    Logger logger=Logger.getLogger(LandingController.class.getName());
    
    private static final String sshSettingXml="settings.xml";
    private static final String dbSettingXml="databaseSettings.xml";
    
   // File file=new File("/d/home/adira0150/programming/php/submit.html");
    //URL url1;

    public LandingController() {
        //LogManager.getLogManager().reset();
        logger.addHandler(obpManagerLogDatabaseHandler);
        logger.setLevel(Level.ALL);
    }
    
    
    
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
    final WebView wv=new WebView(); 
    final WebEngine webEngine=wv.getEngine();
    
    private SShSettings settingsModel;
    private DataBaseSettings databaseSettingsModel;
    private AppProperties appproperties=new AppProperties();
    
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
    private Button bugReport;
     
     @FXML
    private MenuItem idAbout;

     
     @FXML
    void about(ActionEvent event) {
         DialogModel dmodel=new DialogModel();
         dmodel.setMessage("VERSION: "+appproperties.VERSION);
         DialogNode dnode=new DialogNode(dmodel);
                         
         
    }
     
     @FXML
    void handleBugReport(ActionEvent event) {
         ReporterModel rm=new ReporterModel();
         ReporterNode rnode=new ReporterNode(rm);
         
         
    }
     
     @FXML
    void dbsettings(ActionEvent event) {
        InputStream is=null;
        try {
            databaseSettingsModel=new DataBaseSettings();
            //    System.out.println("landing.LandingController.dbsettings(): looking for "+getClass().getClassLoader().getResource(dbSettingXml).getFile());
            //File dbFile=new File(dbSettingXml);
            /*ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
            InputStream is=classLoader.getResourceAsStream(dbSettingXml);*/
            // File dbFile=new File(getClass().getClassLoader().getResource(dbSettingXml).getFile());
            // File dbFile=
            System.out.println("landing.LandingController.settings() looking for "+System.getProperty("user.home")+ "  file: "+dbSettingXml);
            logger.info("looking for  file: "+dbSettingXml+" under "+ System.getProperty("user.home"));
            File dbFile=new File(System.getProperty("user.home"),dbSettingXml);
            is = new FileInputStream(dbFile);
            try {
                JAXBContext contextObj = JAXBContext.newInstance(DataBaseSettings.class);
                
                //try unmarshalling the file. if the fields are not null. populate settingsmodel
                
                Unmarshaller unm=contextObj.createUnmarshaller();
                // DataBaseSettings dbsett=(DataBaseSettings) unm.unmarshal(dbFile);
                DataBaseSettings dbsett=(DataBaseSettings) unm.unmarshal(is);
                System.out.println("landing.LandingController.settings():  unmarshalled: "+dbsett.getChosenDatabase());
                logger.info("unmarshalled: "+dbsett.getChosenDatabase());
                String parts[]=dbsett.getChosenDatabase().split("/");
                AppProperties.setProject(parts[parts.length-1]);
                databaseSettingsModel.setDbUser(dbsett.getDbUser());
                databaseSettingsModel.setDbPassword(dbsett.getDbPassword());
                databaseSettingsModel.setChosenDatabase(dbsett.getChosenDatabase());
                logger.info("chosen db : " + dbsett.getChosenDatabase()+" user: "+dbsett.getDbUser()+" pass: "+dbsett.getDbPassword());
                DataBaseSettingsNode dbnode=new DataBaseSettingsNode(databaseSettingsModel);
                DataBaseSettingsController dcontrl=dbnode.getDataBaseSettingsController();
                
                Marshaller marshallerObj = contextObj.createMarshaller();
                marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshallerObj.marshal(databaseSettingsModel, new File(System.getProperty("user.home"),dbSettingXml));
                
            } catch (JAXBException ex) {
                Logger.getLogger(LandingController.class.getName()).log(Level.SEVERE, null, ex);
                //logger.log(Level.SEVERE, null, ex);
                logger.severe("JAXBException: "+ex.getMessage());
            }
            
        } catch (FileNotFoundException ex) {
            //logger.log(Level.SEVERE, "File not found!: {0}", ex.getMessage());
            logger.severe("File not found");
            //logger.log(Level.SEVERE, null, ex);
            //Exceptions.printStackTrace(ex);'
            ex.printStackTrace();
            
            
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                logger.severe("Couldn't close file");
               // Exceptions.printStackTrace(ex);
               ex.printStackTrace();
            }
        }
    }
    
    @FXML
    void settings(ActionEvent event) throws URISyntaxException {
        
        InputStream is=null;
        try {
            settingsModel=new SShSettings();
            /*URL sshLocationURL=getClass().getClassLoader().getResource(sshSettingXml);
            File sFile=new File(sshSettingXml);*/
            //System.out.println("landing.LandingController.settings(): looking for "+sshLocationURL.getFile());
            //File sFile=new File(sshLocationURL.getFile());
            
            /* ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
            InputStream is=classLoader.getResourceAsStream(sshSettingXml);*/
            System.out.println("landing.LandingController.settings() looking for "+System.getProperty("user.home")+ "  file: "+sshSettingXml);
            logger.info("looking for "+System.getProperty("user.home")+ "  file: "+sshSettingXml);
            File sFile=new File(System.getProperty("user.home"),sshSettingXml);
            is = new FileInputStream(sFile);
            try {
                JAXBContext contextObj = JAXBContext.newInstance(SShSettings.class);
                
                //try unmarshalling the file. if the fields are not null. populate settingsmodel
                
                Unmarshaller unm=contextObj.createUnmarshaller();
                // SShSettings sett=(SShSettings) unm.unmarshal(sFile);
                SShSettings sett=(SShSettings) unm.unmarshal(is);
                System.out.println("landing.LandingController.settings():  unmarshalled: "+sett.getSshHost() );
                logger.info("unmarshalled: "+sett.getSshHost());
                
                
                if(sett.isPopulated()){
                    settingsModel.setDbPassword(sett.getDbPassword());
                    settingsModel.setDbUser(sett.getDbUser());
                    settingsModel.setId(sett.getId());
                    settingsModel.setSshHost(sett.getSshHost());
                    settingsModel.setSshPassword(sett.getSshPassword());
                    settingsModel.setSshUser(sett.getSshUser());
                    appproperties.setIrdbHost(settingsModel.getSshHost());
                }
                
                SShSettingsNode setnode=new SShSettingsNode(settingsModel);
                SShSettingsController sc=new SShSettingsController();
                
                //save the xml
                
                
                Marshaller marshallerObj = contextObj.createMarshaller();
                marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshallerObj.marshal(settingsModel, new File(System.getProperty("user.home"),sshSettingXml));
                
                
            } catch (JAXBException ex) {
                Logger.getLogger(LandingController.class.getName()).log(Level.SEVERE, null, ex);
                logger.severe( ex.getMessage());
            }
            
           
        } catch (FileNotFoundException ex) {
            logger.severe("file not found! : "+ ex.getMessage());
           // Exceptions.printStackTrace(ex);
           ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                logger.severe("Can't close file : "+ ex.getMessage());
               //Exceptions.printStackTrace(ex);
               ex.printStackTrace();
            }
        }
        
        
        
    }
    
    
    
    
    @FXML
    void startNewSession(ActionEvent event) {
        logger.info("Starting new session ");
            smodel = new SessionModel();
            snode = new SessionNode(smodel);
            scontr=snode.getSessionController();
            obsModL.add(smodel);
            basePane.getChildren().add(snode);
            this.setTitle("OBPManager-"+appproperties.VERSION+" Project: "+appproperties.getProject());
            
            
    }

    @FXML
    void saveCurrentSession(ActionEvent event) {
        if(smodel.getName()==null || smodel.getName().isEmpty()){
            SaveSessionModel ssm=new SaveSessionModel();
            SaveSessionNode sessnode=new SaveSessionNode(ssm);
            SaveSessionController sc=sessnode.getSaveSessionController();
            
                      
            String name=ssm.getName();
            smodel.setName(name);
            appproperties.setSessionName(smodel.getName());
        }
         
        
        System.out.println("landing.LandingController.saveCurrentSession(): sessionName: "+smodel.getName());
        logger.info("sessionName: "+smodel.getName());        
        
        //if smodel.name==null or empty open a dialogue box to save name. i.e call saveSessionAs(event)
        if(smodel.getName()==null || smodel.getName().isEmpty()){
        //    saveSessionAs(event);
        return;
        }
        else{
            
        }
        
        System.out.println("landing.LandingController.saveCurrentSession(): Saving session with Id: "+smodel.getId()+" and name: "+smodel.getName());
        logger.info("Saving session with Id: "+smodel.getId()+" and name: "+smodel.getName());
            scontr.setAllLinksAndJobsForCommit();
            
            ObservableList<JobStepType0Model> ajs= smodel.getListOfJobs();
            
            for (Iterator<JobStepType0Model> iterator = ajs.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
                System.out.println("landing.LandingController.saveCurrentSession(): Job id# "+next.getId()+" text: "+next.getJobStepText());
                logger.info("Job id# "+next.getId()+" text: "+next.getJobStepText());
        }
            ArrayList<LinksModel> alk =smodel.getListOfLinks();
            
            for (Iterator<LinksModel> iterator = alk.iterator(); iterator.hasNext();) {
            LinksModel next = iterator.next();
            System.out.println("landing.LandingController.saveCurrentSession(): Job id# "+next.getId()+" Parent:" +next.getParent().getJobStepText()+ " Child: "+next.getChild().getJobStepText());
            logger.info("Job id# "+next.getId()+" Parent:" +next.getParent().getJobStepText()+ " Child: "+next.getChild().getJobStepText());
        }
            try{
                collector.saveCurrentSession(smodel);
            }catch(Exception ex){
                logger.severe("NULL Encountered while trying to save session");
            }
            
            
            this.setTitle("OBPManager-"+appproperties.VERSION+" Project: "+appproperties.getProject()+" Session: "+appproperties.getSessionName());
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
            logger.info("saveSessionAs(): "+smodel.getName());
            saveCurrentSession(event);
             
           
    }

    @FXML
    void loadSession(ActionEvent event) {    //All of this needs to go to the "Controller" class in package controller. This is just POC.
       
        
        
            String  regex=".\\d*.\\d*-[a-zA-Z0-9_-]*";  //insight regex
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
        logger.info("Name of session thats about to be loaded: "+sessionFromDB.getNameSessions());
        
        //next get all jobs belonging to that session from the sessionDetails Table
        SessionDetailsService ssDserv=new SessionDetailsServiceImpl();
        List<SessionDetails> lsd=ssDserv.getSessionDetails(sessionFromDB);           //get all the sessionDetails which belong to the session = sessionFromDB;
        
        List<JobStep> js=new ArrayList<>();
        
        ParentService pserv=new ParentServiceImpl();
        ChildService cserv=new ChildServiceImpl();
        
        JobVolumeDetailsService jvdserv=new JobVolumeDetailsServiceImpl();
        HeadersService hdrServ=new HeadersServiceImpl(); 
        
        NodeTypeService nserv=new NodeTypeServiceImpl();
        NodePropertyValueService npvserve=new NodePropertyValueServiceImpl();
        
        
        
        
        
        Map<JobStep,JobStep> parentAndJobMap=new HashMap<>();   
        Map<JobStep,JobStep> childAndJobMap=new HashMap<>();                        //Use these maps to link the cubic curves
        
        for (Iterator<SessionDetails> iterator = lsd.iterator(); iterator.hasNext();) {
            SessionDetails next = iterator.next();
            
            Sessions beSessions=next.getSessions();
            JobStep beJobStep=next.getJobStep();                                    //beJobstep belongs to beSessions
            
            js.add(beJobStep);
            
            JobStepType0Model fejsm=null;//=new JobStepType0Model(null);
            
            NodeType ntype=beJobStep.getType();
            List<NodePropertyValue> npvList=npvserve.getNodePropertyValuesFor(beJobStep);
            List<JobModelProperty> jbprops=new ArrayList<>();
            for (Iterator<NodePropertyValue> iterator1 = npvList.iterator(); iterator1.hasNext();) {
                NodePropertyValue next1 = iterator1.next();
                JobModelProperty jb=new JobModelProperty();
                jb.setPropertyName(next1.getNodeProperty().getPropertyType().getName());
                jb.setPropertyValue(next1.getValue());
                jbprops.add(jb);
                
            }
            
            
            
            
           // Long type=beJobStep.getType();
           Long type=ntype.getActualnodeid();
           
           
            if(type.equals(1L)){
                fejsm=new JobStepType1Model(null,jbprops);
            }
            if(type.equals(2L)){
                fejsm=new JobStepType2Model(null,jbprops);
            }
            if(type.equals(3L)){
                fejsm=new AcquisitionJobStepModel(null,jbprops);
            }
            if(type.equals(4L)){
                fejsm=new JobStepType4Model(null,jbprops);
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
            
            
            
            
            
            //get all volumes related to beJobstep from the table JobVolumeDetails
            
            
            List<JobVolumeDetails> bejobVols= jvdserv.getJobVolumeDetails(beJobStep);        //this is the list of all the jobVolumeDetail entries related to beJobStep
            List<Volume> beVols=new ArrayList<>();                                           // A list to hold the volumes related to this beJobStep
           // List<VolumeSelectionModelType1> feVols=new ArrayList<>();                             // A list to hold the volume models corresponding to beVols.  Frontend equivalents
            List<VolumeSelectionModelType0> feVols=new ArrayList<>();                             // A list to hold the volume models corresponding to beVols.  Frontend equivalents
            for (Iterator<JobVolumeDetails> iterator1 = bejobVols.iterator(); iterator1.hasNext();) {
                JobVolumeDetails next1 = iterator1.next();
                
                Volume beV=next1.getVolume();
                beVols.add(beV);
                
                //Load headers for beV i.e the backend Volume.
                List<Headers> hl=hdrServ.getHeadersFor(beV);
                Set<SubSurfaceHeaders> sl=new HashSet<>();
                List<SequenceHeaders> seqList=new ArrayList<>();
                MultiMap<Long,SubSurfaceHeaders> seqSubMap=new MultiValueMap<>();                                             //for creating association between SequenceHeaders and Subsurfaces
      
                        for (Iterator<Headers> iteratornn = hl.iterator(); iteratornn.hasNext();) {
                            Headers beH = iteratornn.next();
                            SubSurfaceHeaders s= new SubSurfaceHeaders();
          
                            s.setSequenceNumber(beH.getSequence().getSequenceno());
                            s.setSubsurface(beH.getSubsurface().getSubsurface());
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
                            s.setNumberOfRuns(beH.getNumberOfRuns());
                            s.setInsightVersion(beH.getInsightVersion());
                            s.setWorkflowVersion(beH.getWorkflowVersion());
                            
          
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
                            SequenceHeaders sq=new SequenceHeaders();
                            ArrayList<SubSurfaceHeaders> ssubs=(ArrayList<SubSurfaceHeaders>) seqSubMap.get(seq_no);
                            sq.setSubsurfaces(ssubs);
                            seqList.add(sq);
                        }
      
      
                
                
                //VolumeSelectionModelType1 fv=new VolumeSelectionModelType1(beV.getVolumeType(),fejsm);
                Long typev=beV.getVolumeType();
                VolumeSelectionModelType0 fv=null;
                if(typev.equals(1L)){
                    fv=new VolumeSelectionModelType1(beV.getVolumeType(),fejsm);
                    fv.setVolumeChosen(new File(beV.getPathOfVolume()));
                    fv.setHeaderButtonStatus(!beV.getHeaderExtracted());     // if extracted is true then the status of disablity should be false
                    fv.setAlert(beV.getAlert());
                    fv.setLabel(beV.getNameVolume());
                    fv.setId(beV.getIdVolume());
                    fv.setVolumeType(beV.getVolumeType());
                    System.out.println("landing.LandingController.loadSession(): Volume name: "+beV.getNameVolume()+" : Volume Id: "+beV.getIdVolume());
                    logger.info("Volume name: "+beV.getNameVolume()+" : Volume Id: "+beV.getIdVolume());
                    fv.setInflated(true);
                    fv.setSubsurfaces(sl);
                } 
                if(typev.equals(2L)){
                    fv=new VolumeSelectionModelType2(beV.getVolumeType(),fejsm);
                    fv.setVolumeChosen(new File(beV.getPathOfVolume()));
                    fv.setHeaderButtonStatus(!beV.getHeaderExtracted());     // if extracted is true then the status of disablity should be false
                    fv.setAlert(beV.getAlert());
                    fv.setLabel(beV.getNameVolume());
                    fv.setId(beV.getIdVolume());
                    fv.setVolumeType(beV.getVolumeType());
                    System.out.println("landing.LandingController.loadSession(): Volume name: "+beV.getNameVolume()+" : Volume Id: "+beV.getIdVolume());
                    logger.info("Volume name: "+beV.getNameVolume()+" : Volume Id: "+beV.getIdVolume());
                    fv.setInflated(true);
                    fv.setSubsurfaces(sl);
                } 
                if(typev.equals(3L)){
                    fv= new AcquisitionVolumeModel();
                    fv.setVolumeChosen(new File(""));
                    fv.setId(beV.getIdVolume());
                    fv.setVolumeType(beV.getVolumeType());
                    fv.setSubsurfaces(sl);
                    fv.setLabel(beV.getNameVolume());
                }
                if(typev.equals(4L)){
                    fv=new VolumeSelectionModelType4(beV.getVolumeType(),fejsm);
                    fv.setVolumeChosen(new File(beV.getPathOfVolume()));
                    fv.setHeaderButtonStatus(!beV.getHeaderExtracted());     // if extracted is true then the status of disablity should be false
                    fv.setAlert(beV.getAlert());
                    fv.setLabel(beV.getNameVolume());
                    fv.setId(beV.getIdVolume());
                    fv.setVolumeType(beV.getVolumeType());
                    System.out.println("landing.LandingController.loadSession(): Volume name: "+beV.getNameVolume()+" : Volume Id: "+beV.getIdVolume());
                    logger.info("Volume name: "+beV.getNameVolume()+" : Volume Id: "+beV.getIdVolume());
                    fv.setInflated(true);
                    fv.setSubsurfaces(sl);
                } 
               
                
                
                
                            HeadersModel hmod=new HeadersModel(fv);
                            ObservableList<SequenceHeaders> obseq=FXCollections.observableArrayList(seqList);
                            hmod.setSequenceListInHeaders(obseq);
                
                
                fv.setHeadersModel(hmod);                                       //set the headersModel
                feVols.add(fv);
                
                //if(!typev.equals(3L)){
                if(typev.equals(1L)){                                           //Denoise etc
                    
                    ObservableList<VolumeSelectionModelType0> obv=FXCollections.observableArrayList(feVols);
                    ObservableList<VolumeSelectionModelType1> obv1=FXCollections.observableArrayList();
                    for (Iterator<VolumeSelectionModelType0> iterator2 = obv.iterator(); iterator2.hasNext();) {
                        VolumeSelectionModelType0 next2 = iterator2.next();
                        obv1.add((VolumeSelectionModelType1)next2);
                        
                    }
                    ((JobStepType1Model)fejsm).setVolList(obv1);
                    //fejsm.setVolList(obv1);
                }
                 if(typev.equals(2L)){                                      //SEGD load
                    
                    ObservableList<VolumeSelectionModelType0> obv=FXCollections.observableArrayList(feVols);
                    ObservableList<VolumeSelectionModelType2> obv2=FXCollections.observableArrayList();
                    for (Iterator<VolumeSelectionModelType0> iterator2 = obv.iterator(); iterator2.hasNext();) {
                        VolumeSelectionModelType0 next2 = iterator2.next();
                        obv2.add((VolumeSelectionModelType2)next2);
                        
                    }
                    ((JobStepType2Model)fejsm).setVolList(obv2);
                    //fejsm.setVolList(obv1);
                }
                
                if(typev.equals(3L)){                                       //Acquistion 
                    //no volume to set for acquisition node
                    ObservableList<VolumeSelectionModelType0> obv=FXCollections.observableArrayList(feVols);
                   
                    ObservableList<AcquisitionVolumeModel> obva=FXCollections.observableArrayList();
                     for (Iterator<VolumeSelectionModelType0> iterator2 = obv.iterator(); iterator2.hasNext();) {
                        VolumeSelectionModelType0 next2 = iterator2.next();
                        obva.add((AcquisitionVolumeModel)next2);
                    }
                     ((AcquisitionJobStepModel)fejsm).setVolList(obva);
                    
                }
                if(typev.equals(4L)){                                      //Text 
                    
                    ObservableList<VolumeSelectionModelType0> obv=FXCollections.observableArrayList(feVols);
                    ObservableList<VolumeSelectionModelType4> obv4=FXCollections.observableArrayList();
                    for (Iterator<VolumeSelectionModelType0> iterator2 = obv.iterator(); iterator2.hasNext();) {
                        VolumeSelectionModelType0 next2 = iterator2.next();
                        obv4.add((VolumeSelectionModelType4)next2);
                        
                    }
                    ((JobStepType4Model)fejsm).setVolList(obv4);
                    //fejsm.setVolList(obv1);
                }
                
                //ObservableList<VolumeSelectionModelType1> obv=FXCollections.observableArrayList(feVols);
               
            }
            
            
           
            
            
            
            jmodList.add(fejsm);                          //This list now contains all the jobs that belong to the session. (names and ids)..one job at a time in the loop
            
            
            
            
            
            
        }
       
        
        
        for (Iterator<SessionDetails> iterator = lsd.iterator(); iterator.hasNext();) {
            SessionDetails next = iterator.next();
            
            Sessions beSessions=next.getSessions();
            JobStep beJobStep=next.getJobStep();                                    //beJobstep belongs to beSessions
           
            NodeType ntype=beJobStep.getType();
            
            List<NodePropertyValue> npvList=npvserve.getNodePropertyValuesFor(beJobStep);
            List<JobModelProperty> jbprops=new ArrayList<>();
            for (Iterator<NodePropertyValue> iterator1 = npvList.iterator(); iterator1.hasNext();) {
                NodePropertyValue next1 = iterator1.next();
                JobModelProperty jb=new JobModelProperty();
                jb.setPropertyName(next1.getNodeProperty().getPropertyType().getName());
                jb.setPropertyValue(next1.getValue());
                jbprops.add(jb);
                
            }
            
            
           // Long type=beJobStep.getType();
           Long type=ntype.getActualnodeid();
            
            JobStepType0Model fejsm=null;
            if(type.equals(1L)){
                fejsm=new JobStepType1Model(null,jbprops);
            }if(type.equals(2L)){
                fejsm=new JobStepType2Model(null,jbprops);
            }if(type.equals(3L)){
                fejsm=new AcquisitionJobStepModel(null,jbprops);
            }if(type.equals(4L)){
                fejsm=new JobStepType4Model(null,jbprops);
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
                System.out.println(beJobStep.getNameJobStep()+" :has Child: "+ childssd.getJobStep().getNameJobStep());
                logger.info(beJobStep.getNameJobStep()+" :has Child: "+ childssd.getJobStep().getNameJobStep());
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
                logger.info("job : "+next.getJobStepText()+" :has child: "+next1.getJobStepText());
            }
            
            List<JobStepType0Model> parents=next.getJsParents();
            for (Iterator<JobStepType0Model> iterator1 = parents.iterator(); iterator1.hasNext();) {
                JobStepType0Model next1 = iterator1.next();
                System.out.println("landing.LandingController.loadSession(): job : "+next.getJobStepText()+" :has parent: "+next1.getJobStepText());
                logger.info("job : "+next.getJobStepText()+" :has parent: "+next1.getJobStepText());
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
            logger.info("jobs to be loaded "+next.getJobStepText());
        }
       
       scontr.setObsModelList(obj);
       scontr.setAllModelsForFrontEndDisplay();
       scontr.setAllLinksForFrontEnd();
       scontr.startWatching(); 
       appproperties.setSessionName(smodel.getName());
                   this.setTitle("OBPManager-"+appproperties.VERSION+" Project: "+appproperties.getProject()+" Session: "+appproperties.getSessionName());

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
                //save current session and exit
                
                saveCurrentSession(event);
                close();
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
       this.setTitle("OBPManager-"+appproperties.VERSION);
        this.setScene(new Scene(lnode));
        this.showAndWait();
    }
    
    
}
