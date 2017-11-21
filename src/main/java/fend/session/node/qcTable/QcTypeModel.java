/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.qcTable;

import java.util.Comparator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * 
 * This class holds the name and id for each of the users "qctypes"..shot, stack ,cdp etc.
 * A list of this class' objects are used in the QCTableModel class: which is a class holding the qc table entries. shot checked(unchecked) for subs seq1_cab2_gun1
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
    //System.out.println("fend.session.node.volumes.qcTable.QcTypeModel.passQcProperty(): Returning: "+passQc.get()+" for type: "+name+" id: "+this);
    return passQc;
    }
    
    
    public int compareTo(QcTypeModel q1){
        return (this.id).compareTo(q1.id);
    }
    
    private final BooleanProperty checkUncheckProperty=new SimpleBooleanProperty(false);
    public void setCheckUncheckProperty(Boolean value){
        checkUncheckProperty.set(value);
    }
    
    public BooleanProperty getCheckUncheckProperty(){
        return checkUncheckProperty;
        
    }
    
    public Boolean getCheckUncheck(){
        return checkUncheckProperty.get();
    }
    
    private final BooleanProperty  failProperty=new SimpleBooleanProperty(false);
    
    public void setFailProperty(Boolean value){
        failProperty.set(value);
    }
    
    public BooleanProperty getFailProperty() {
        return failProperty;
    }
    
    public Boolean getFail(){
        return failProperty.get();
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
