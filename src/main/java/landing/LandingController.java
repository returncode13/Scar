/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing;

import collector.Collector;
import fend.session.SessionController;
import fend.session.SessionModel;
import fend.session.SessionNode;
import fend.session.edges.LinksModel;
import fend.session.node.jobs.JobStepModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author naila0152
 */
public class LandingController implements Initializable {

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
        System.out.println("LC: Saving session with Id: "+smodel.getId());
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

    }

    @FXML
    void loadSession(ActionEvent event) {

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
