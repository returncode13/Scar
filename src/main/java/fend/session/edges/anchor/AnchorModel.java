/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.edges.anchor;

import fend.session.edges.LinksModel;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import java.io.Serializable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import fend.session.node.jobs.types.type1.JobStepType1Model;

/**
 *
 * @author naila0152
 */
public class AnchorModel implements Serializable{
    
    final private DoubleProperty centerX;
    final private DoubleProperty centerY;
    final private DoubleProperty radius;
    private Color color;
    private Long id;
    private JobStepType0Model job;                                               //the job step on which this anchor lies
    private LinksModel lmodel;
    
    public AnchorModel() {
        centerX=new SimpleDoubleProperty();
        centerY=new SimpleDoubleProperty();
        radius =new SimpleDoubleProperty();
        color=new Color(0.6, 0.2, 0.3, 0.7);
        
    }

    public DoubleProperty getCenterX() {
        return centerX;
    }

    public DoubleProperty getCenterY() {
        return centerY;
    }

    public DoubleProperty getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

   
    public void setColor(Color color) {
        this.color = color;
    }
    
    public void setCenterX(Double d)
    {
        System.out.println("fend.session.edges.anchor.AnchorModel.setCenterX(): value of d= "+d);
//        System.out.println("fend.session.edges.anchor.AnchorModel.setCenterX(): job: "+job.getJobStepText()+" centreX="+d);
        centerX.set(d);
    }
    
    public void setCenterY(Double d){
   //     System.out.println("fend.session.edges.anchor.AnchorModel.setCenterY(): job: "+job.getJobStepText()+" centreY="+d);
        centerY.set(d);
    }
    
    public void setRadius(Double d){
        radius.set(d);
    }

    public JobStepType0Model getJob() {
        return job;
    }

    public void setJob(JobStepType0Model job) {
        System.out.println("fend.session.edges.anchor.AnchorModel.setJob(): setting job: "+job.getJobStepText());
        this.job = job;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LinksModel getLmodel() {
        return lmodel;
    }

    public void setLmodel(LinksModel lmodel) {
        this.lmodel = lmodel;
    }
          
    
}
