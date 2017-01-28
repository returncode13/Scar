/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.overview;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sharath
 */
public class OverviewModel {
    List<OverviewItem> overviewItemList=new ArrayList<>();

    public List<OverviewItem> getOverviewItemList() {
        return overviewItemList;
    }

    public void setOverviewItemList(List<OverviewItem> overviewItemList) {
        this.overviewItemList = overviewItemList;
    }
    
    
}
