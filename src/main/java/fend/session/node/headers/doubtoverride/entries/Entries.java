/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.doubtoverride.entries;

import db.model.DoubtStatus;
import fend.session.node.headers.doubtoverride.entries.comments.CommentModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class Entries {
    String errorMessage=new String();
    String doubtType=new String();
    String subsurface=new String();
    String status=new String();
    CommentModel comment=new CommentModel(this);
    DoubtStatus doubtStatusObject;

    public DoubtStatus getDoubtStatusObject() {
        return doubtStatusObject;
    }

    public void setDoubtStatusObject(DoubtStatus doubtStatusObject) {
        this.doubtStatusObject = doubtStatusObject;
    }
    
    
    
    
    

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(String subsurface) {
        this.subsurface = subsurface;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CommentModel getComment() {
        return comment;
    }

    public void setComment(CommentModel comment) {
        this.comment = comment;
    }

    public String getDoubtType() {
        return doubtType;
    }

    public void setDoubtType(String doubtType) {
        this.doubtType = doubtType;
    }
    
    
    
    
}
