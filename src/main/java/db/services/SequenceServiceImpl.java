/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.SequenceDAO;
import db.dao.SequenceDAOImpl;
import db.model.Sequence;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SequenceServiceImpl implements SequenceService{

    SequenceDAO seqDao=new SequenceDAOImpl();
            
    @Override
    public void createSequence(Sequence seq) {
        seqDao.createSequence(seq);
    }

    @Override
    public Sequence getSequence(Long sid) {
       return seqDao.getSequence(sid);
    }
    
    @Override
    public void deleteSequence(Long sid) {
        seqDao.deleteSequence(sid);
    }

    @Override
    public void updateSequence(Long sid, Sequence newseq) {
        seqDao.updateSequence(sid, newseq);
    }

    @Override
    public Sequence getSequenceObjByseqno(Long seqno) {
        return seqDao.getSequenceObjByseqno(seqno);
    }

    @Override
    public List<Sequence> getSequenceList() {
        return seqDao.getSequenceList();
    }
    
}
