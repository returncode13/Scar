package fend.session.edges;


import java.util.UUID;
import fend.session.edges.anchor.Anchor;
import fend.session.edges.anchor.AnchorModel;
import fend.session.edges.curves.CubCurve;
import fend.session.edges.curves.CubCurveModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author naila0152
 */
public class Links extends Group{
    Anchor start;
    Anchor end;
    CubCurve curve;
    LinksModel lmodel;
    BooleanProperty visibility=new SimpleBooleanProperty(Boolean.TRUE);
   
    public Links(LinksModel lm)
    {
        lmodel=lm;
        start = new Anchor(Color.AQUA,lm.getmStart());
        end = new Anchor(Color.RED, lm.getmEnd());
        curve=new CubCurve(lm.getmCurve());
        
        
        curve.startXProperty().bindBidirectional(start.centerXProperty());
        curve.startYProperty().bindBidirectional(start.centerYProperty());
        curve.endXProperty().bindBidirectional(end.centerXProperty());
        curve.endYProperty().bindBidirectional(end.centerYProperty());
        visibility.bindBidirectional(lmodel.getVisibility());
        visibility.addListener(new ChangeListener<Boolean>(){

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                
                updateVisibility();
            }
        });
        curve.setVisible(visibility.get());
        
        /* curve.startXProperty().bind(start.centerXProperty());
        curve.startYProperty().bind(start.centerYProperty());
        curve.endXProperty().bind(end.centerXProperty());
        curve.endYProperty().bind(end.centerYProperty());*/
        
        getChildren().addAll(start,curve,end);
        setId(UUID.randomUUID().getMostSignificantBits()+"");
        lm.setId(Long.valueOf(getId()));
          start.setLink(this);
        end.setLink(this);
    }

    public LinksModel getLmodel() {
        return lmodel;
    }

    public Anchor getStart() {
        return start;
    }

    public Anchor getEnd() {
        return end;
    }

    public void setStart(Anchor start) {
        this.start = start;
    }

    
    public void setEnd(Anchor end) {
        this.end = end;
    }

    
    public CubCurve getCurve() {
        return curve;
    }
    
    private void updateVisibility() {
                curve.setVisible(lmodel.getVisibility().get());
                start.setVisible(lmodel.getVisibility().get());
                end.setVisible(lmodel.getVisibility().get());
            }
    
    
    
}
