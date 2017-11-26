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
    public static final String isInDeterminate="fend.session.node.qcTable.INDETERMINATE";
    
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
    
    
    
    /* private final BooleanProperty passQc = new SimpleBooleanProperty(false);
    
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
    }*/
    
    
     BooleanProperty checkUncheck=new SimpleBooleanProperty();
    BooleanProperty indeterminate=new SimpleBooleanProperty();

    public BooleanProperty getCheckUncheck() {
        return checkUncheck;
    }

    public void setCheckUncheck(BooleanProperty checkUncheck) {
        this.checkUncheck = checkUncheck;
    }

    public BooleanProperty getIndeterminate() {
        return indeterminate;
    }

    public void setIndeterminate(BooleanProperty indeterminate) {
        this.indeterminate = indeterminate;
    }
    
    public BooleanProperty checkUncheckProperty(){
        return checkUncheck;
    }
    
    public BooleanProperty indeterminateProperty(){
        return indeterminate;
    }
    
    private final StringProperty passQc = new SimpleStringProperty();
    
    public String isPassQc() {
     if(indeterminate.get()){
         passQc.set(isInDeterminate);
        // return isInDeterminate;
     }else{
         passQc.set(checkUncheckProperty().getValue().toString());
         //return checkUncheckProperty().getValue().toString();
     }
     return passQc.get();
    }
    
    public void setPassQc(String value) {
         if(value.equals(isInDeterminate)){
             indeterminate.set(true);
         }else{
             indeterminate.set(false);
             if(value.equals(Boolean.TRUE.toString())){
                 checkUncheck.set(true);
             }else{
                 checkUncheck.set(false);
             }
         }
    }
    
    public StringProperty passQcProperty() {
        /*   if(indeterminate.get()){
        return null;
        }else{
        return checkUncheckProperty();
        }*/
        if(indeterminate.get()){
         passQc.set(isInDeterminate);
        // return isInDeterminate;
     }else{
         passQc.set(checkUncheckProperty().getValue().toString());
         //return checkUncheckProperty().getValue().toString();
     }
        return passQc;
    }
    

    

    
    
}
