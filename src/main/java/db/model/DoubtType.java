/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

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
@Table(name="DoubtType",schema="obpmanager")
public class DoubtType {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long idDoubtType;

@OneToMany(mappedBy = "doubtType",cascade = CascadeType.ALL,orphanRemoval = true)
private Set<DoubtStatus> doubtstatus;

@Column(name="name")
private String name;

    public Long getIdDoubtType() {
        return idDoubtType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    
    
    
}
