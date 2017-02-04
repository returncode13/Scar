/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.edges.curves;

import java.util.UUID;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author naila0152
 */
public class CubCurve extends CubicCurve{
    private CubCurveModel model;
    
    
    private final DoubleProperty mControloffX=new SimpleDoubleProperty();
    private final DoubleProperty mControloffY=new SimpleDoubleProperty();
    
    private final DoubleProperty mControlDirX1=new SimpleDoubleProperty();
    private final DoubleProperty mControlDirY1=new SimpleDoubleProperty();
    private final DoubleProperty mControlDirX2=new SimpleDoubleProperty();
    private final DoubleProperty mControlDirY2=new SimpleDoubleProperty();
    
    
    public CubCurve(CubCurveModel cm)
    {
        this.model=cm;
        startXProperty().bindBidirectional(cm.getmStartXProperty());
        startYProperty().bindBidirectional(cm.getmStartYProperty());
        endXProperty().bindBidirectional(cm.getmEndXProperty());
        endYProperty().bindBidirectional(cm.getmEndYProperty());
        
        /*startXProperty().bind(cm.getmStartXProperty());
        startYProperty().bind(cm.getmStartYProperty());
        endXProperty().bind(cm.getmEndXProperty());
        endYProperty().bind(cm.getmEndYProperty());
        */
        
        controlX1Property().bindBidirectional(cm.getmControlDirectionX1());
        controlX2Property().bindBidirectional(cm.getmControlDirectionX2());
        controlY1Property().bindBidirectional(cm.getmControlDirectionY1());
        controlY2Property().bindBidirectional(cm.getmControlDirectionY2());
        
        /*
        Curve smooth bending begin
        */
        mControloffX.set(100.0);
        mControloffY.set(50.0);
        
        
        mControlDirX1.bind(new When(
        startXProperty().greaterThan(endXProperty()))
        .then(-1.0).otherwise(1.0)
        );
        
        
        mControlDirX2.bind(new When(
        startXProperty().greaterThan(endXProperty()))
        .then(1.0).otherwise(-1.0)
        );
        
        controlX1Property().bind(
                Bindings.add(
                        startXProperty(), 
                        mControloffX.multiply(mControlDirX1)
                        )
        );
        
        controlX2Property().bind(
                Bindings.add(
                        endXProperty(), 
                        mControloffX.multiply(mControlDirX2)
                        )
        );
        
        controlY1Property().bind(
                Bindings.add(
                        startYProperty(), 
                        mControloffY.multiply(mControlDirY1)
                        )
        );
        
        controlY2Property().bind(
                Bindings.add(
                        endYProperty(), 
                        mControloffY.multiply(mControlDirY2)
                        )
        );
        
        
        /*
        Curve smooth bending end
        */
        
        
        setStroke(Color.BLACK);
        setStrokeWidth(2);
        setStrokeLineCap(StrokeLineCap.ROUND);
      //  setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));
        setFill(null);
        
        setId(UUID.randomUUID().getMostSignificantBits()+"");
        cm.setId(Long.valueOf(getId()));
        
        
        
    }

    public CubCurveModel getModel() {
        return model;
    }
    
    
    
    
    
}
