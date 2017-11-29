/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.SubsurfaceDAO;
import db.dao.SubsurfaceDAOImpl;
import db.model.Sequence;
import db.model.Subsurface;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SubsurfaceServiceImpl implements SubsurfaceService{
    
    SubsurfaceDAO sDao=new SubsurfaceDAOImpl();
    
    @Override
    public void createSubsurface(Subsurface sub) {
        sDao.createSubsurface(sub);
    }

    @Override
    public Subsurface getSubsurface(Long sid) {
        return sDao.getSubsurface(sid);
    }

    @Override
    public void deleteSubsurface(Long sid) {
        sDao.deleteSubsurface(sid);
    }

    @Override
    public void updateSubsurface(Long sid, Subsurface newsub) {
        sDao.updateSubsurface(sid, newsub);
    }

    @Override
    public Subsurface getSubsurfaceObjBysubsurfacename(String dugSubsurface) {
        return sDao.getSubsurfaceObjBysubsurfacename(dugSubsurface);
    }

    @Override
    public List<Subsurface> getSubsurfaceList() {
        return sDao.getSubsurfaceList();
    }

    @Override
    public List<Subsurface> getSubsurfaceForSequence(Sequence seq) {
        return sDao.getSubsurfaceForSequence(seq);
    }
    
}
