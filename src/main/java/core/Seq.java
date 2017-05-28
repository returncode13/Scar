/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class Seq {
    private Long seqno;
    List<Sub> subsurfaceList;

    public Long getSeqno() {
        return seqno;
    }

    public void setSeqno(Long seqno) {
        this.seqno = seqno;
    }

    public List<Sub> getSubsurfaceList() {
        return subsurfaceList;
    }

    public void setSubsurfaceList(List<Sub> subsurfaceList) {
        this.subsurfaceList = subsurfaceList;
    }
    
}
