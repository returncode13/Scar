/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author adira0150
 */
public class JobNodeListModel {

    private final ListProperty<SummaryJobNodeModel> joblist = new SimpleListProperty<>(this,"joblist");

    public ObservableList getJoblist() {
        return joblist.get();
    }

    public void setJoblist(ObservableList value) {
        joblist.set(value);
    }

    public ListProperty joblistProperty() {
        return joblist;
    }

    public JobNodeListModel() {
    }
    
    
}
