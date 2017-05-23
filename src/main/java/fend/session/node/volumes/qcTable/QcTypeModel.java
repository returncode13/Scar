/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcTable;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTypeModel {
    String name;
    Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long idQcType) {
       this.id=idQcType;
    }

    public Long getId() {
        return id;
    }
    
    
}
