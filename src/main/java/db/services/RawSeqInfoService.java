/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.RawSeqInfo;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface RawSeqInfoService {
    public RawSeqInfo getRawSeqInfo(Long seqid);
    public List<RawSeqInfo> getListOfRawSeqInfo();
}
