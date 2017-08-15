/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.ObpManagerLogDAO;
import db.dao.ObpManagerLogDAOImpl;
import db.model.ObpManagerLog;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ObpManagerLogServiceImpl implements ObpManagerLogService{
    ObpManagerLogDAO odao=new ObpManagerLogDAOImpl();

    @Override
    public void createObpManagerLog(ObpManagerLog o) {
        odao.createObpManagerLog(o);
    }

    @Override
    public ObpManagerLog getObpManagerLog(Long oid) {
        return odao.getObpManagerLog(oid);
    }

    @Override
    public void updateObpManagerLog(Long oid, ObpManagerLog newO) {
        updateObpManagerLog(oid, newO);
    }

    @Override
    public void deleteObpManagerLog(Long oid) {
        odao.deleteObpManagerLog(oid);
    }

    @Override
    public List<ObpManagerLog> getObpManagerLogs() {
        return odao.getObpManagerLogs();
    }
    
}
