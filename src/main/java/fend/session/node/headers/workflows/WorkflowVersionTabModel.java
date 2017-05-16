/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.workflows;

import java.util.Objects;

/**
 *
 * @author adira0150
 */
public class WorkflowVersionTabModel {
    Long version;
    
    String workflowvContent;

    public WorkflowVersionTabModel() {
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }


    public String getWorkflowvContent() {
        return workflowvContent;
    }

    public void setWorkflowvContent(String workflowvContent) {
        this.workflowvContent = workflowvContent;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.version);
        hash = 29 * hash + Objects.hashCode(this.workflowvContent);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WorkflowVersionTabModel other = (WorkflowVersionTabModel) obj;
        return true;
    }
    
    
    
}
