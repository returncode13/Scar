/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Entity
@Table(name="JobProperty",schema="obpmanager")
public class JobProperty implements Serializable {
    @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long idJobProperty;
    
   @ManyToOne()
   @JoinColumn(name = "propertyType_fk",nullable = true)
   private PropertyType propertytype;   
   
   @ManyToOne()
   @JoinColumn(name = "nodetype_fk",nullable = true)
   private NodeType nodetype;   
    
}
