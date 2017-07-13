/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.List;
import java.util.Objects;

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
    /*
    @Override
    public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.seqno);
    hash = 37 * hash + Objects.hashCode(this.subsurfaceList);
    return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
    if (this == obj) {
    return true;
    }
    if (obj == null) {
    return false;
    }
    if (getClass() != obj.getClass()) {
    return false;
    }
    final Seq other = (Seq) obj;
    if (!Objects.equals(this.seqno, other.seqno)) {
    return false;
    }
    if (!Objects.equals(this.subsurfaceList, other.subsurfaceList)) {
    return false;
    }
    return true;
    }*/
    
    
}
