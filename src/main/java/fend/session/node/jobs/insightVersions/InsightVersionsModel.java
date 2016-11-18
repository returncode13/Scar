/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.insightVersions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import org.controlsfx.control.CheckModel;

/**
 *
 * @author sharath
 */
public class InsightVersionsModel {
    private List<String> versions=new ArrayList<>();
    private List<String> checkedVersions=new ArrayList<>();
    private CheckModel checkModel;
    
    public InsightVersionsModel(List<String> versions) {
        
        
        this.versions = versions;
        
      
        
        for (Iterator<String> iterator = versions.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            System.out.println(next);
            
        }
    }

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
        
       
    }

    public List<String> getCheckedVersions() {
        return checkedVersions;
    }

    public void setCheckedVersions(List<String> checkedVersions) {
        this.checkedVersions = checkedVersions;
    }

    public CheckModel getCheckModel() {
        return checkModel;
    }

    public void setCheckModel(CheckModel checkModel) {
        this.checkModel = checkModel;
    }
    
    
    
}
