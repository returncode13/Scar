/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author sharath nair
 */
@Entity
@Table(name="QcType",schema="obpmanager")
public class QcType {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idQcType")
    private Long idQcType;
    
    @Column(name="name")
    private String name;        

    public Long getIdQcType() {
        return idQcType;
    }

    public void setIdQcType(Long idQcType) {
        this.idQcType = idQcType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
