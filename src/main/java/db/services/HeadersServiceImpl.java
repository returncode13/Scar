/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.HeadersDAO;
import db.dao.HeadersDAOImpl;
import java.util.List;
import java.util.Set;
import db.model.Headers;
import db.model.Subsurface;
import db.model.Volume;

/**
 *
 * @author sharath nair
 */
public class HeadersServiceImpl implements HeadersService{

    HeadersDAO hDao=new HeadersDAOImpl();
    
    @Override
    public void createHeaders(Headers h) {
       hDao.createHeaders(h);
    }

    @Override
    public Headers getHeaders(Long hid) {
        return hDao.getHeaders(hid);
    }

    @Override
    public void updateHeaders(Long hid, Headers newH) {
        hDao.updateHeaders(hid, newH);
    }

    @Override
    public void deleteHeaders(Long hid) {
        hDao.deleteHeaders(hid);
    }

    @Override
    public List<Headers> getHeadersFor(Volume v) {
        return hDao.getHeadersFor(v);
    }

    @Override
    public void setHeadersFor(Volume v, List<Headers> headers) {
        hDao.setHeadersFor(v,headers);
    }

    @Override
    public void updateHeadersFor(Volume v, List<Headers> headers) {
       hDao.updateHeaders(v, headers);
    }

    @Override
    public void deleteHeadersFor(Volume v) {
        hDao.deleteHeadersFor(v);
    }

    /*@Override
    public Set<Volume> getVolumesContaining(String subsurface) {
    System.out.println("sub: "+subsurface);
    return hDao.getVolumesContaining(subsurface);
    }
    
    @Override
    public List<Headers> getHeadersFor(Volume v, String subsurface) {
    return hDao.getHeadersFor(v, subsurface);
    }*/
    @Override
    public Set<Volume> getVolumesContaining(Subsurface subsurface) {
        System.out.println("sub: "+subsurface.getSubsurface());
        return hDao.getVolumesContaining(subsurface);
    }

    @Override
    public List<Headers> getHeadersFor(Volume v, Subsurface subsurface) {
        return hDao.getHeadersFor(v, subsurface);
    }
    
}
