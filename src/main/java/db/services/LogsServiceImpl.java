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
import db.model.Workflow;
import java.util.List;

/**
 *
 * @author sharath nair
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

    @Override
    public List<Logs> getLogsFor(Volume v, Boolean completed, Boolean running, Boolean errored, Boolean cancelled) {
        return ldao.getLogsFor(v, completed, running, errored, cancelled);
    }

    @Override
    public List<Logs> getLogsFor(Volume v, String subline, Boolean completed, Boolean running, Boolean errored, Boolean cancelled) {
        return ldao.getLogsFor(v, subline, completed, running, errored, cancelled);
    }

    @Override
    public List<Logs> getLogsFor(Volume v, Workflow workflow) {
        return ldao.getLogsFor(v, workflow);
    }

    @Override
    public List<Logs> getLogsFor(Volume v, Long seq) {
        return ldao.getLogsFor(v, seq);
    }

    @Override
    public List<Logs> getSequencesFor(Volume v) {
        return ldao.getSequencesFor(v);
    }

    @Override
    public List<Logs> getSubsurfacesFor(Volume v, Long seq) {
        return ldao.getSubsurfacesFor(v, seq);
    }

   
    
}
