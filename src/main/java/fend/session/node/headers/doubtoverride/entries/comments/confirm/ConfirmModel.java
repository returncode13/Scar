/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.doubtoverride.entries.comments.confirm;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ConfirmModel {
    String confirm=new String("This will now commit the override.\n Confirm?");
    Boolean status=false;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
    
    
    
}
