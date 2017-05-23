/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcTable.qcCheckBox;

import fend.session.node.volumes.qcTable.qcCheckBox.addQcType.AddQcTypeModel;
import java.util.ArrayList;
import java.util.List;
import org.controlsfx.control.CheckModel;

/**
 *
 * @author sharath nair
 */
public class qcCheckListModel {
    private List<String> qcTypes=new ArrayList<>();
    private List<String> checkedTypes=new ArrayList<>();
    private CheckModel checkModel;
    private AddQcTypeModel acmodel;
    
    public qcCheckListModel(){}

    public List<String> getQcTypes() {
        return qcTypes;
    }

    public void setQcTypes(List<String> qcTypes) {
        this.qcTypes = qcTypes;
    }

    public List<String> getCheckedTypes() {
        return checkedTypes;
    }

    public void setCheckedTypes(List<String> checkedTypes) {
        this.checkedTypes = checkedTypes;
    }

    public CheckModel getCheckModel() {
        return checkModel;
    }

    public void setCheckModel(CheckModel checkModel) {
        this.checkModel = checkModel;
    }

    AddQcTypeModel getAddQcTypeModel() {
         acmodel=new AddQcTypeModel();
         return acmodel;
    }
    
    
    
}
