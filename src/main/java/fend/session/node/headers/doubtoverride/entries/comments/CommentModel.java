/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.doubtoverride.entries.comments;

import fend.session.node.headers.doubtoverride.entries.Entries;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * called from ButtonCell()
 */
public class CommentModel {
    String comment=new String();
    Entries entry;
    Boolean status=false;
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Entries getEntry() {
        return entry;
    }

   
    public CommentModel(Entries entry) {
        this.entry = entry;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

   
    
    
}
