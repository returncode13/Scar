/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.LogsDAO;
import db.dao.LogsDAOImpl;
import db.model.Headers;
import db.model.Logs;
import db.model.Volume;
import java.util.List;

/**
 *
 * @author naila0152
 */
public class LogsServiceImpl implements LogsService{

    LogsDAO ldao=new LogsDAOImpl();
    @Override
    public void createLogs(Logs l) {
        ldao.createLogs(l);
    }

    @Override
    public Logs getLogs(Long lid) {
       return ldao.getLogs(lid);
    }

    @Override
    public void updateLogs(Long lid, Logs newL) {
        ldao.updateLogs(lid, newL);
    }

    @Override
    public void deleteLogs(Long lid) {
        ldao.deleteLogs(lid);
    }

    @Override
    public List<Logs> getLogsFor(Headers h) {
        return ldao.getLogsFor(h);
    }

    @Override
    public List<Logs> getLogsFor(Volume v) {
        return ldao.getLogsFor(v);
    }

    @Override
    public List<Logs> getLogsFor(Volume v, String subline) {
        return ldao.getLogsFor(v, subline);
    }

    @Override
    public Logs getLatestLogFor(Volume v, String subline) {
        return ldao.getLatestLogFor(v, subline);
    }

   
    
}
