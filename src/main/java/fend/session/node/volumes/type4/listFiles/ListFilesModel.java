/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type4.listFiles;

import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ListFilesModel {
     VolumeSelectionModelType0 vmodel0;
    List<String> fileNames=new ArrayList<>();
    Integer from;
    Integer to;
    List<String> charsForTextFlow=new ArrayList<>();  // each char is placed inside this array. "1234P15678"  => charsForText[0]="1",  charsForText[1]="2"....
    ObservableList<String> obs;
    ObservableList<String> fileobs;
    
    public ListFilesModel(List<String> fileNL,VolumeSelectionModelType0 vmodel0) {
        this.fileNames=fileNL;
        fileobs=FXCollections.observableArrayList(this.fileNames);
        String textFlowStr=this.fileNames.get(0);
        //setCharsForTextFlow(textFlowStr);
        charsForTextFlow=new ArrayList<>();
        char[] a=textFlowStr.toCharArray();
        for(int i=0;i<a.length;i++){
            charsForTextFlow.add(new String(""+a[i]));
        }
        obs=FXCollections.observableArrayList(charsForTextFlow);
        
        
        
        this.vmodel0 = vmodel0;
    }

    

    
    
    public ObservableList<String> getFileobs() {
        return fileobs;
    }

    
    
    
    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
        String textFlowStr=this.fileNames.get(0);
        this.setCharsForTextFlow(textFlowStr);
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public List<String> getCharsForTextFlow() {
        return charsForTextFlow;
    }

    public void setCharsForTextFlow(String s) {
        charsForTextFlow=new ArrayList<>();
        char[] a=s.toCharArray();
        for(int i=0;i<a.length;i++){
            charsForTextFlow.add(new String(""+a[i]));
        }
        obs=FXCollections.observableArrayList(charsForTextFlow);
    }

    public ObservableList<String> getObs() {
        return obs;
    }

    public VolumeSelectionModelType0 getVmodel0() {
        return vmodel0;
    }

    
    
    
}
