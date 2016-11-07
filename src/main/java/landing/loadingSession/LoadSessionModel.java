/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.loadingSession;

import db.model.Sessions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath
 */
public class LoadSessionModel {
    
    private ObservableList<Sessions> listOfSessions;
    private Sessions sessionToBeLoaded;
    
    
    public LoadSessionModel(ObservableList<Sessions> listOfSessions) {
        this.listOfSessions = listOfSessions;
    }

    public Sessions getSessionToBeLoaded() {
        return sessionToBeLoaded;
    }

    public void setSessionToBeLoaded(Sessions sessionToBeLoaded) {
        this.sessionToBeLoaded = sessionToBeLoaded;
    }
    
    
    

    ObservableList<Sessions> getList() {
        return listOfSessions;
    }

    
    
    
    
    
    
}
