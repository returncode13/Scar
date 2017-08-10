/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sharath nair
 */
@Entity
@Table(name="QcType",schema="obpmanager")
public class QcType implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idQcType")
    private Long idQcType;
    
    @Column(name="name")
    private String name;    

    /*  @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="sessions_fk",nullable=false)
    private Sessions sessions;
    
    @OneToMany(mappedBy = "qctype",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<QcTable> qcMatrix;*/
    public Long getIdQcType() {
        return idQcType;
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* public Sessions getSessions() {
    return sessions;
    }
    
    public void setSessions(Sessions sessions) {
    this.sessions = sessions;
    }*/
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
       // hash = 71 * hash + Objects.hashCode(this.sessions);
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
        final QcType other = (QcType) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        /* if (!Objects.equals(this.sessions, other.sessions)) {
        return false;
        }*/
        return true;
    }
    
    
    
    
}
