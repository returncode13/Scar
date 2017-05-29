/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Sequence;
import db.model.Subsurface;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface SubsurfaceDAO {
    public void createSubsurface(Subsurface sub);
    public Subsurface getSubsurface(Long sid);
    public void deleteSubsurface(Long sid);
    public void updateSubsurface(Long sid, Subsurface newsub);
    
    
    public List<Subsurface> getSubsurfaceForSequence(Sequence seq);

    public Subsurface getSubsurfaceObjBysubsurfacename(String dugSubsurface);

    public List<Subsurface> getSubsurfaceList();
}
