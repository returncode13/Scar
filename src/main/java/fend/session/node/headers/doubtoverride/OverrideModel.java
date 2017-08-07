/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.doubtoverride;

import fend.session.node.headers.doubtoverride.entries.Entries;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class OverrideModel {
   
    ObservableList<Entries> obsentries=FXCollections.observableArrayList();

    public ObservableList<Entries> getObsentries() {
        return obsentries;
    }

    public void setObsentries(ObservableList<Entries> obsentries) {
        this.obsentries = obsentries;
    }
    public void setObsentries(List<Entries> entries) {
        this.obsentries = FXCollections.observableArrayList(entries);
    }

    
    
   
    
}
