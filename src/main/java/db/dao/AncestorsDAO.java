/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import java.util.List;
import db.model.Ancestors;
import db.model.SessionDetails;

/**
 *
 * @author sharath nair
 */
public interface AncestorsDAO {
    public void addAncestor(Ancestors A);
    public Ancestors getAncestors(Long aid);
    public void updateAncestors(Long aid,Ancestors newAncestor);
    public void deleteAncestors(Long aid);
    
    public Ancestors getAncestorsFor(SessionDetails fkid,Long ancestor);     //get the entry where sessionsDetails=fkid and where the column ancestor = ancestor
   
   
    
    
}
