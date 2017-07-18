/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mid.doubt;

import fend.session.node.jobs.types.type0.JobStepType0Model;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * Holds the doubts that a node/sub has.
 * A flag that is set to true if the map.size()>0
 * A status property that show whether the doubt is set to "Y","O" or "N"
 */

class JobPair{
    JobStepType0Model parent;
    JobStepType0Model child;
    String type;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.parent.getId());
        hash = 31 * hash + Objects.hashCode(this.child.getId());
        hash = 31 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JobPair other = (JobPair) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.parent.getId(), other.parent.getId())) {
            return false;
        }
        if (!Objects.equals(this.child.getId(), other.child.getId())) {
            return false;
        }
        return true;
    }
    
    
}



public class Doubt {
    
    public static final String doubtTime="time";
    public static final String doubtTraces="traces";
    public static final String doubtQc="qc";
    
    Map<JobPair,String> doubtmap=new HashMap<>();
    private  BooleanProperty doubt = new SimpleBooleanProperty();
    private  StringProperty status = new SimpleStringProperty(this,"N");

    public static String getDoubtTime() {
        return doubtTime;
    }

    public static String getDoubtTraces() {
        return doubtTraces;
    }

    public Doubt() {
        status.set("N");
    }
    
    
    
    
    
    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }
    
    
    
    public boolean isDoubt() {
        return doubt.get();
    }

    public void setDoubt(boolean value) {
        doubt.set(value);
        if(!doubtmap.isEmpty()){
            doubt.set(true);
        }
        
    }

    public BooleanProperty doubtProperty() {
        if(!doubtmap.isEmpty())doubt.set(true);
        return doubt;
    }

    public Map<JobPair, String> getDoubtmap() {
        return doubtmap;
    }

    public void setDoubtmap(Map<JobPair, String> doubtmap) {
        this.doubtmap = doubtmap;
    }
    
    
    public void addToDoubtMap(JobStepType0Model parent,JobStepType0Model child,String type, String errorMessage){
        
        JobPair jp=new JobPair();
        jp.parent=parent;
        jp.child=child;
        jp.type=type;
        
        doubtmap.put(jp, errorMessage);
        if(!doubtmap.isEmpty()){
            doubt.set(true);
            //status.set("Y");
        }
        else {
            doubt.set(false);
           // status.set("N");
        }
    }
    
    public void removeFromDoubtMap(JobStepType0Model parent,JobStepType0Model child,String type){
        
        JobPair jp=new JobPair();
        jp.parent=parent;
        jp.child=child;
        jp.type=type;
        
        
        doubtmap.remove(jp);
        if(!doubtmap.isEmpty()){
            doubt.set(true);
           // status.set("Y");
        }
        else {
            doubt.set(false);
            status.set("N");
        }
    }
    
    
    public String getErrorMessage(){
        String err=new String();
        for (Map.Entry<JobPair, String> entry : doubtmap.entrySet()) {
            JobPair key = entry.getKey();
            String value = entry.getValue();
            err+="\n"+value;
        }
        return err;
    }
    
    
}
