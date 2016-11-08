/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.edges;

import fend.session.edges.anchor.AnchorModel;
import fend.session.edges.curves.CubCurveModel;
import java.io.Serializable;
import fend.session.node.jobs.JobStepModel;

/**
 *
 * @author naila0152
 */
public class LinksModel implements Serializable{
    private AnchorModel mStart;
    private AnchorModel mEnd;
    private CubCurveModel mCurve;
    
    private JobStepModel parent;
    private JobStepModel child;
       
    Long id;
    
    public LinksModel(){
        
    }
    
    public LinksModel(AnchorModel ms,AnchorModel me,CubCurveModel cm){
        mStart=ms;
        mEnd=me;
        mCurve=cm;
        
        
        
      
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

    public JobStepModel getParent() {
        parent=mStart.getJob();
        return parent;
    }

    public JobStepModel getChild() {
        child=mEnd.getJob();
        return child;
    }

    public void setParent(JobStepModel parent) {
        this.parent = parent;
    }

    public void setChild(JobStepModel child) {
        this.child = child;
    }
    
    
    
}
