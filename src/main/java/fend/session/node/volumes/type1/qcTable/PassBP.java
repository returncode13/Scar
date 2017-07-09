/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type1.qcTable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class PassBP {

    private final BooleanProperty pass = new SimpleBooleanProperty(Boolean.FALSE);

    public boolean isPass() {
        return pass.get();
    }

    public void setPass(boolean value) {
        pass.set(value);
    }

    public BooleanProperty passProperty() {
        return pass;
    }
    
    
}
