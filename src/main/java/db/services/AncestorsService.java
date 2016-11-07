/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import java.util.List;
import java.util.Set;
import db.model.Ancestors;
import db.model.SessionDetails;

/**
 *
 * @author sharath nair
 */
public interface AncestorsService {
    public void addAncestor(Ancestors A);
    public Ancestors getAncestors(Long aid);
    public void updateAncestors(Long aid,Ancestors newAncestor);
    public void deleteAncestors(Long aid);
    
    
    public void getInitialAncestorsListFor(SessionDetails fkid,Set<Long> listOfAncestors);  //recursive call to generate descendants from existing User table
    public void makeAncestorsTableFor(SessionDetails fkid,Set<Long> listOfAncestors);  //delete existing user table to replace true descendant entries
    
    public void getAncestorsFor(SessionDetails fkid,Set<Long>listOfAncestors); //remember to run the above two first before calling this method
    public Ancestors getAncestorsFor(SessionDetails fkid,Long ancestor);     //get the entry where sessionsDetails=fkid and where the column ancestor = ancestor
}
