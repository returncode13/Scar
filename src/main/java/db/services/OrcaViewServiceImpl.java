/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.OrcaViewDAO;
import db.dao.OrcaViewDAOImpl;
import db.model.OrcaView;
import db.model.Sequence;
import java.util.List;

/**
 *
 * @author naila0152
 */
public class OrcaViewServiceImpl implements OrcaViewService{
    OrcaViewDAO orcaDAO=new OrcaViewDAOImpl();

    @Override
    public List<OrcaView> getOrcaView() {
     return orcaDAO.getOrcaView();
    }

    @Override
    public List<OrcaView> getOrcaViewsForSeq(Sequence seq) {
        return orcaDAO.getOrcaViewsForSeq(seq);
    }

    @Override
    public List<Long> getSeqOrcaView() {
        return orcaDAO.getSeqOrcaView();
    }
    
}
