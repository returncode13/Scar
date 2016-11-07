/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.edges.curves;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author naila0152
 */
public class CubCurveModel {
    private final DoubleProperty mControlOffsetX = new SimpleDoubleProperty();
    private final DoubleProperty mControlOffsetY = new SimpleDoubleProperty();
    private final DoubleProperty mControlDirectionX1 = new SimpleDoubleProperty();
    private final DoubleProperty mControlDirectionY1 = new SimpleDoubleProperty(); 
    private final DoubleProperty mControlDirectionX2 = new SimpleDoubleProperty(); 
    private final DoubleProperty mControlDirectionY2 = new SimpleDoubleProperty(); 
    private final DoubleProperty mStartXProperty=new SimpleDoubleProperty();
    private final DoubleProperty mStartYProperty=new SimpleDoubleProperty();
    private final DoubleProperty mEndXProperty=new SimpleDoubleProperty();
    private final DoubleProperty mEndYProperty=new SimpleDoubleProperty(); 
    
    private Long id;
    private Long parentJobId;
    private Long childJobId;

    public Long getParentJobId() {
        return parentJobId;
    }

    public void setParentJobId(Long parentJobId) {
        this.parentJobId = parentJobId;
    }

    public Long getChildJobId() {
        return childJobId;
    }

    public void setChildJobId(Long childJobId) {
        this.childJobId = childJobId;
    }
    
    
    
    
    
    public DoubleProperty getmControlOffsetX() {
        return mControlOffsetX;
    }

    public DoubleProperty getmControlOffsetY() {
        return mControlOffsetY;
    }

    public DoubleProperty getmControlDirectionX1() {
        return mControlDirectionX1;
    }

    public DoubleProperty getmControlDirectionY1() {
        return mControlDirectionY1;
    }

    public DoubleProperty getmControlDirectionX2() {
        return mControlDirectionX2;
    }

    public DoubleProperty getmControlDirectionY2() {
        return mControlDirectionY2;
    }

    public DoubleProperty getmStartXProperty() {
        return mStartXProperty;
    }

    public DoubleProperty getmStartYProperty() {
        return mStartYProperty;
    }

    public DoubleProperty getmEndXProperty() {
        return mEndXProperty;
    }

    public DoubleProperty getmEndYProperty() {
        return mEndYProperty;
    }

    void setId(Long valueOf) {
        this.id=valueOf;
    }

    public Long getId() {
        return id;
    }
    
    
}
