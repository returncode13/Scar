/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author adira0150
 */
public class DepthListModel {
    List<DepthModel> listOfDepthModel=new ArrayList<>();
    
    public List<DepthModel> getListOfDepthModel() {
    return listOfDepthModel;
    }
    
    public void setListOfDepthModel(List<DepthModel> listOfDepthModel) {
    this.listOfDepthModel = listOfDepthModel;
    }
   
    
    public void addToDepthModel(DepthModel d){
        this.listOfDepthModel.add(d);
    }
    
    
    
    private final ListProperty<DepthModel> depths = new SimpleListProperty<>(this,"depths");
    
    public ObservableList getDepths() {
    return depths.get();
    }
    
    public void setDepths(ObservableList value) {
    depths.set(value);
    }
    
    public ListProperty depthsProperty() {
    return depths;
    }

    public DepthListModel() {
    }
    
    
}
