/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.edges.anchor;

import java.io.Serializable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import fend.session.node.jobs.JobStepModel;

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
    private JobStepModel job;                                               //the job step on which this anchor lies
    
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

    public JobStepModel getJob() {
        return job;
    }

    public void setJob(JobStepModel job) {
        System.out.println("fend.session.edges.anchor.AnchorModel.setJob(): setting job: "+job.getJobStepText());
        this.job = job;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
          
    
}
