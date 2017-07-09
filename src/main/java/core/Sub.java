/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.Objects;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class Sub {
    private Seq seq;
    private String subsurfaceName;

    public Seq getSeq() {
        return seq;
    }

    public void setSeq(Seq seq) {
        this.seq = seq;
    }

    public String getSubsurfaceName() {
        return subsurfaceName;
    }

    public void setSubsurfaceName(String subsurfaceName) {
        this.subsurfaceName = subsurfaceName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.seq);
        hash = 41 * hash + Objects.hashCode(this.subsurfaceName);
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
        final Sub other = (Sub) obj;
        if (!Objects.equals(this.subsurfaceName, other.subsurfaceName)) {
            return false;
        }
        if (!Objects.equals(this.seq, other.seq)) {
            return false;
        }
        return true;
    }
    
    
}
