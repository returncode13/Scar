/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.overview;

import java.util.List;
import javafx.scene.layout.GridPane;

/**
 *
 * @author sharath
 */
public class OverviewItem extends GridPane {
    private String name=null;             //name of job
    private List<String> subsurfaceList=null;    //list of strings with names of subsurfaces in the job
    private List<String> vols=null;             //list of strings with names of vols in the job;
    private List<List<String>> volsubs=null;        //list of list of strings with names of subs in the constituent vols 
    private Boolean pFlag=false;               //p-flag status
    private Boolean qFlag=false;                //q-flag status

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSubsurfaceList() {
        return subsurfaceList;
    }

    public void setSubsurfaceList(List<String> subsurfaceList) {
        this.subsurfaceList = subsurfaceList;
    }

    public List<String> getVols() {
        return vols;
    }

    public void setVols(List<String> vols) {
        this.vols = vols;
    }

    public List<List<String>> getVolsubs() {
        return volsubs;
    }

    public void setVolsubs(List<List<String>> volsubs) {
        this.volsubs = volsubs;
    }

    public Boolean getpFlag() {
        return pFlag;
    }

    public void setpFlag(Boolean pFlag) {
        this.pFlag = pFlag;
    }

    public Boolean getqFlag() {
        return qFlag;
    }

    public void setqFlag(Boolean qFlag) {
        this.qFlag = qFlag;
    }
    
    
    
}
