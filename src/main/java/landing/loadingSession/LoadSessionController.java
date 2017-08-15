/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.loadingSession;

import db.model.Sessions;
import java.util.Iterator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author sharath nair
 * sharath.nair@polarcus.com
 */
public class LoadSessionController extends Stage{

     private  LoadSessionModel lsmodel;
    private LoadSessionNode lsnode;
    private ObservableList<Sessions> sessionList;
    private Sessions selectedSession;
    
    @FXML
    private Button loadButton;
    
    @FXML
    private ListView<Sessions> listView=new ListView<>();
    
    @FXML
    void handleLoadButton(ActionEvent event) {
        
        lsmodel.setSessionToBeLoaded(selectedSession);
        System.out.println("landing.loadingSession.LoadSessionController.handleLoadButton() will load : "+selectedSession.getNameSessions());
        
        close();
    }
    
    
   
    
    void setModel(LoadSessionModel lsm) {
        this.lsmodel=lsm;
        sessionList=lsmodel.getList();
        listView.setItems(sessionList);
       listView.setCellFactory(lv -> {
           ListCell<Sessions> cell = new ListCell<Sessions>(){
               @Override
               protected void updateItem(Sessions item, boolean empty) {
                   super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                   if(empty){
                       setText(null);
                   }else{
                       setText(item.getNameSessions());
                   }
                   
                   
               }
               
           };
           
           cell.setOnMouseClicked(e->{
               if(cell.getItem()!=null){
                   System.out.println(cell.getItem().getNameSessions()+ " :hash: "+cell.getItem().getHashSessions());
                   selectedSession=cell.getItem();
               }
           });
            return cell;
           
       });
       /* for (Iterator<String> iterator = sessionList.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            System.out.println(next);
            
        }*/
        
        
       
    }

    void setView(LoadSessionNode aThis) {
       
        this.lsnode=aThis;
        this.setScene(new Scene(lsnode));
        this.showAndWait();
    }
    
}
