/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import com.sun.org.apache.xpath.internal.axes.SubContextList;
import db.model.Headers;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author naila0152
 */
public class HeaderTableModel {
   
    private List<SubSurface> headerList=new ArrayList<>();
    private ObservableList<SubSurface> obsHList=FXCollections.observableList(headerList);
    private Long id;
    
    public List<SubSurface> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<SubSurface> headerList) {
        System.out.println("HTM: headerList set");
        this.headerList = headerList;
       // noOfcols=
    }

    void setId(Long id) {
        this.id=id;
    }
    
    
    
}
