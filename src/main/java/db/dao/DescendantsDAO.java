/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Descendants;
import db.model.SessionDetails;

/**
 *
 * @author sharath nair
 */
public interface DescendantsDAO  {
    public void addDescendants(Descendants d);
    public Descendants getDescendants(Long did);
    public void updateDescendants(Long did,Descendants newD);
    public void deleteDescendants(Long did);

    public Descendants getDescendantsFor(SessionDetails fkid, Long descendant);
    
}
