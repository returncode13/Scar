/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midend.doubt;

import java.util.HashMap;
import java.util.Map;
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
public class Doubt {
    
    public static final String doubtTime="time";
    public static final String doubtTraces="traces";
    
    Map<String,String> doubtmap=new HashMap<>();
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
    }

    public BooleanProperty doubtProperty() {
        if(doubtmap.size()!=0)doubt.set(true);
        return doubt;
    }

    public Map<String, String> getDoubtmap() {
        return doubtmap;
    }

    public void setDoubtmap(Map<String, String> doubtmap) {
        this.doubtmap = doubtmap;
    }
    
    
    public void addToDoubtMap(String type, String errorMessage){
        doubtmap.put(type, errorMessage);
        if(doubtmap.size()!=0)doubt.set(true);
        else doubt.set(false);
    }
    
    public void removeFromDoubtMap(String type){
        doubtmap.remove(type);
        if(doubtmap.size()!=0)doubt.set(true);
        else doubt.set(false);
    }
    
    
    public String getErrorMessage(){
        String err=new String();
        for (Map.Entry<String, String> entry : doubtmap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            err+="\n"+value;
        }
        return err;
    }
    
    
}
