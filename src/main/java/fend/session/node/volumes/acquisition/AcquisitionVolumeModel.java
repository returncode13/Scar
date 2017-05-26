/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.acquisition;

import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.SubSurface;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import java.io.File;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AcquisitionVolumeModel implements VolumeSelectionModelType0{
    final private Long type=3L;

    @Override
    public Long getType() {
        return type;
    }
   
    
    
}
