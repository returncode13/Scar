/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.edges;

import fend.session.edges.anchor.AnchorModel;
import fend.session.edges.curves.CubCurveModel;
import fend.session.node.jobs.type0.JobStepType0Model;
import java.io.Serializable;
import fend.session.node.jobs.type1.JobStepType1Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author naila0152
 */
public class LinksModel implements Serializable{
    private AnchorModel mStart=new AnchorModel();
    private AnchorModel mEnd=new AnchorModel();
    private CubCurveModel mCurve=new CubCurveModel();
    private BooleanProperty visibility=new SimpleBooleanProperty(Boolean.TRUE);
    /*private JobStepType1Model parent;
    private JobStepType1Model child;*/
    private JobStepType0Model parent;
    private JobStepType0Model child;
       
    Long id;
    
    public LinksModel(){
        
    }
    
    public LinksModel(AnchorModel ms,AnchorModel me,CubCurveModel cm){
        mStart=ms;
        mEnd=me;
        mCurve=cm;
        ms.setLmodel(this);
        me.setLmodel(this);
        
        
      
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    

    public AnchorModel getmStart() {
        return mStart;
    }

    public AnchorModel getmEnd() {
        return mEnd;
    }

    public CubCurveModel getmCurve() {
        return mCurve;
    }

    public void setmStart(AnchorModel mStart) {
        this.mStart = mStart;
    }

    public void setmEnd(AnchorModel mEnd) {
        this.mEnd = mEnd;
    }

    public void setmCurve(CubCurveModel mCurve) {
        this.mCurve = mCurve;
    }

    public JobStepType0Model getParent() {
        parent=mStart.getJob();
        return parent;
    }

    public JobStepType0Model getChild() {
        child=mEnd.getJob();
        return child;
    }

    public void setParent(JobStepType0Model parent) {
        this.parent = parent;
        this.mStart.setJob(this.parent);
    }

    public void setChild(JobStepType0Model child) {
        this.child = child;
        this.mEnd.setJob(this.child);
    }

    public BooleanProperty getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility.set(visibility);
    }
    
    
    
}
