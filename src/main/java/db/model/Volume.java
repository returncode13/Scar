/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author sharath nair
 */

@Entity
@Table(name = "Volume",schema = "obpmanager",uniqueConstraints = {@UniqueConstraint(columnNames = {"idVolume"})})
public class Volume implements Serializable {
   @Id
   //@GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "idVolume")
   private long idVolume;
   
   @Column(name = "nameVolume",length = 255)
   private String nameVolume;
   
   @Column(name = "path",length=2056)
   private String pathOfVolume;
   
   @Column(name = "md5Hash",length = 1025)
   private String md5Hash;
   
   @Column(name = "headerExStatus")
   private Boolean headerExtracted;

   
   
   @Column(name = "alert")
   private Boolean alert;

    
   
   
   @OneToMany(mappedBy = "volume",cascade = CascadeType.ALL,orphanRemoval = true)
   private Set<Headers> headers;
   
   @OneToMany(mappedBy="volume",cascade = CascadeType.ALL,orphanRemoval = true)
   private Set<JobVolumeDetails> jobVolumeDetails;
   
   
   
   
   public Volume(){}

    public Volume( String nameVolume, String md5Hash) {
        
        this.nameVolume = nameVolume;
        this.md5Hash = md5Hash;
       
      
       
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (int) (this.idVolume ^ (this.idVolume >>> 32));
        hash = 73 * hash + Objects.hashCode(this.nameVolume);
        hash = 73 * hash + Objects.hashCode(this.pathOfVolume);
        hash = 73 * hash + Objects.hashCode(this.md5Hash);
        hash = 73 * hash + Objects.hashCode(this.alert);
        hash = 73 * hash + Objects.hashCode(this.headers);
        hash = 73 * hash + Objects.hashCode(this.jobVolumeDetails);
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
        final Volume other = (Volume) obj;
        if (this.idVolume != other.idVolume) {
            return false;
        }
        if (!Objects.equals(this.nameVolume, other.nameVolume)) {
            return false;
        }
        if (!Objects.equals(this.pathOfVolume, other.pathOfVolume)) {
            return false;
        }
        if (!Objects.equals(this.md5Hash, other.md5Hash)) {
            return false;
        }
        if (!Objects.equals(this.alert, other.alert)) {
            return false;
        }
        if (!Objects.equals(this.headers, other.headers)) {
            return false;
        }
        if (!Objects.equals(this.jobVolumeDetails, other.jobVolumeDetails)) {
            return false;
        }
        return true;
    }

   
    
    
    
    public long getIdVolume() {
        return idVolume;
    }

    public void setIdVolume(long idVolume) {
        this.idVolume = idVolume;
    }
    
    
    public Boolean getAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        this.alert = alert;
    }
    

    public String getNameVolume() {
        return nameVolume;
    }

    public void setNameVolume(String nameVolume) {
        this.nameVolume = nameVolume;
    }

    public String getMd5Hash() {
        return md5Hash;
    }

    public void setMd5Hash(String md5Hash) {
        this.md5Hash = md5Hash;
    }

    public Set<Headers> getHeaders() {
        return headers;
    }

    public void setHeaders(Set<Headers> headers) {
        this.headers = headers;
    }

    public Set<JobVolumeDetails> getJobVolumeDetails() {
        return jobVolumeDetails;
    }

    public void setJobVolumeDetails(Set<JobVolumeDetails> jobVolumeDetails) {
        this.jobVolumeDetails = jobVolumeDetails;
    }

    public void startAlert() {
        this.setAlert(Boolean.TRUE);
    }

    public void stopAlert() {
        this.setAlert(Boolean.FALSE);
    }

    public String getPathOfVolume() {
        return pathOfVolume;
    }

    public void setPathOfVolume(String pathOfVolume) {
        this.pathOfVolume = pathOfVolume;
    }

    public Boolean getHeaderExtracted() {
        return headerExtracted;
    }

    public void setHeaderExtracted(Boolean headerExtracted) {
        this.headerExtracted = headerExtracted;
    }
   
    
   
   
    
}
