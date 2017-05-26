/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.type0;

import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.type1.JobStepType1Model;
import java.io.File;
import javafx.collections.ObservableList;

/**
 *
 * @author adira0150
 */
public interface JobStepType0NodeController {

    public static File insightLocation=new File("/d/sw/Insight");   

    public JobStepType0Model getModel();

    public void setObsList(ObservableList obvolist);

    public void setInsightVersionsModel(InsightVersionsModel insVerModel);

    public void setVolumeModelsForFrontEndDisplay();

    public void setInsightListForFrontEndDisplay();
    
    
    
}
