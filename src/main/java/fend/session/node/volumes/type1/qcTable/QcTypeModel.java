/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type1.qcTable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTypeModel {
    String name;
    Long id;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long idQcType) {
       this.id=idQcType;
    }

    public Long getId() {
        return id;
    }
    
    
    
    private final BooleanProperty passQc = new SimpleBooleanProperty(false);
    
    public boolean isPassQc() {
    return passQc.get();
    }
    
    public void setPassQc(boolean value) {
    passQc.set(value);
    }
    
    public BooleanProperty passQcProperty() {
    System.out.println("fend.session.node.volumes.qcTable.QcTypeModel.passQcProperty(): Returning: "+passQc.get()+" for seq: "+name);
    return passQc;
    }
    
    
    
    
    
    
    
    /*private final BooleanProperty notQcd = new SimpleBooleanProperty(true);
    
    public boolean isNotQcd() {
    return notQcd.get();
    }
    
    public void setNotQcd(boolean value) {
    notQcd.set(value);
    }
    
    public BooleanProperty notQcdProperty() {
    return notQcd;
    }
    private final BooleanProperty passQc = new SimpleBooleanProperty(false);
    
    public boolean isPassQc() {
    return passQc.get();
    }
    
    public void setPassQc(boolean value) {
    passQc.set(value);
    }
    
    public BooleanProperty passQcProperty() {
    System.out.println("fend.session.node.volumes.qcTable.QcTypeModel.passQcProperty(): Returning: "+passQc.get()+" for seq: "+name);
    return passQc;
    }
    
    
    */
    
    /*private final StringProperty qcStatus = new SimpleStringProperty("NQ");
    
    public String getQcStatus() {
    return qcStatus.get();
    }
    
    public void setQcStatus(String value) {
    qcStatus.set(value);
    if(value.equals("FQ")){
    setPassQc(false);
    setNotQcd(false);
    }
    if(value.equals("OK")){
    setPassQc(true);
    setNotQcd(false);
    }
    if(value.equals("NQ")){
    setPassQc(false);
    setNotQcd(true);
    }
    }
    
    public StringProperty qcStatusProperty() {
    return qcStatus;
    }
    */
    
    
}
