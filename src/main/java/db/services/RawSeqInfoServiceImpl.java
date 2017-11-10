/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.RawSeqInfoDAO;
import db.dao.RawSeqInfoDAOImpl;
import db.model.RawSeqInfo;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class RawSeqInfoServiceImpl implements RawSeqInfoService {

    RawSeqInfoDAO rseqDAO=new RawSeqInfoDAOImpl();
    
    @Override
    public RawSeqInfo getRawSeqInfo(Long seqid) {
        return rseqDAO.getRawSeqInfo(seqid);
    }

    @Override
    public List<RawSeqInfo> getListOfRawSeqInfo() {
        return rseqDAO.getListOfRawSeqInfo();
    }
    
}
