/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import java.util.Set;
import db.model.Descendants;
import db.model.SessionDetails;

/**
 *
 * @author sharath nair
 */
public interface DescendantsService {
    public void addDescendants(Descendants d);
    public Descendants getDescendants(Long did);
    public void updateDescendants(Long did,Descendants newD);
    public void deleteDescendants(Long did);
    
    
     public void getInitialDescendantsListFor(SessionDetails fkid,Set<Long> listOfDescendants);    //recursive call to generate descendants from existing User table
    public void makeDescendantsTableFor(SessionDetails fkid,Set<Long> listOfDescendants); //delete existing user table to replace true descendant entries
    
    
    public void getDescendantsFor(SessionDetails fkid,Set<Long>listOfDescendants);         //remember to run the above two first before calling this method
                                                                                        //listOfDescendants is now the actual descendant list.
    public Descendants getDescendantsFor(SessionDetails fkid,Long descendant);
}
