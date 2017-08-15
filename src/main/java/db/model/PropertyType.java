/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Entity
@Table(name="PropertyType",schema="obpmanager")
public class PropertyType implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idForProperty")
    private Long idNodeType;
    
    @Column(name="Name")
    private String name;
    
    
    @OneToMany(mappedBy="propertyType",cascade=CascadeType.ALL,orphanRemoval = true)
    private Set<NodeProperty> nodePropertyType;
    

    public Long getIdNodeType() {
        return idNodeType;
    }

   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
    
}
