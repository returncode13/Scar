/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import java.util.List;
import java.util.Set;
import db.model.Headers;
import db.model.Subsurface;
import db.model.Volume;

/**
 *
 * @author sharath nair
 */
public interface HeadersService {
    public void createHeaders(Headers h);
    public Headers getHeaders(Long hid);
    public void updateHeaders(Long hid,Headers newH);
    public void deleteHeaders(Long hid);
    
    public List<Headers> getHeadersFor(Volume v);          //returns the list of headers records from the Header table that have their foreign key= v
    //public List<Headers> getHeadersFor(Volume v,String subsurface);       //returns the list of headers records from the Header table that have their foreign key= v and subsurface =s
    public List<Headers> getHeadersFor(Volume v,Subsurface subsurface);       //returns the list of headers records from the Header table that have their foreign key= v and subsurface =s
    public void setHeadersFor(Volume v,List<Headers> headers);  //insert into the headers table new records where foreign Key=v
    public void updateHeadersFor(Volume v,List<Headers> headers); //update existing header records in the headers table where foreign key =v
    public void deleteHeadersFor(Volume v);         //delete headers from teh headers table where foreign key =v;
    
    //public Set<Volume> getVolumesContaining(String subsurface);       //a convenience function. return the volume associated with the Subsurface=subsurface from the Headers Table
    public Set<Volume> getVolumesContaining(Subsurface subsurface);       //a convenience function. return the volume associated with the Subsurface=subsurface from the Headers Table

    public List<Subsurface> getSubsurfacesToBeSummarized(Volume next);
    
    
}
