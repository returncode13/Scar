/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 *
 * @author naila0152
 */
public class SubSurfaceTable extends TableView<SubSurface>{
    TableColumn<SubSurface,String> subSurfaceCol=new TableColumn<>("subsurface");
    TableColumn<SubSurface,String> timestampCol=new TableColumn<>("timeStamp");
    TableColumn<SubSurface,String> traceCol=new TableColumn<>("traces");
    TableColumn<SubSurface,String> sequenceNumberCol=new TableColumn<>("seq");
    TableColumn<SubSurface,String> inlineMaxCol=new TableColumn<>("inlineMax");
    TableColumn<SubSurface,String> inlineMinCol=new TableColumn<>("inlineMin");
    TableColumn<SubSurface,String> inlineIncCol=new TableColumn<>("inlineInc");
    TableColumn<SubSurface,String> xlineMaxCol=new TableColumn<>("xlineMax");
    TableColumn<SubSurface,String> xlineMinCol=new TableColumn<>("xlineMin");
    TableColumn<SubSurface,String> xlineIncCol=new TableColumn<>("xlineInc");
    TableColumn<SubSurface,String> dugShotMaxCol=new TableColumn<>("dugShotMax");
    TableColumn<SubSurface,String> dugShotMinCol=new TableColumn<>("dugShotMin");
    TableColumn<SubSurface,String> dugShotIncCol=new TableColumn<>("dugShotInc");
    TableColumn<SubSurface,String> dugChannelMaxCol=new TableColumn<>("dugChannelMax");
    TableColumn<SubSurface,String> dugChannelMinCol=new TableColumn<>("dugChannelMin");
    TableColumn<SubSurface,String> dugChannelIncCol=new TableColumn<>("dugChannelInc");
    TableColumn<SubSurface,String> offsetMaxCol=new TableColumn<>("offsetMax");
    TableColumn<SubSurface,String> offsetMinCol=new TableColumn<>("offsetMin");
    TableColumn<SubSurface,String> offsetIncCol=new TableColumn<>("offsetInc");
    TableColumn<SubSurface,String> cmpMaxCol=new TableColumn<>("cmpMax");    
    TableColumn<SubSurface,String> cmpMinCol=new TableColumn<>("cmpMin"); 
    TableColumn<SubSurface,String> cmpIncCol=new TableColumn<>("cmpInc"); 
    
   
     
     
    public SubSurfaceTable(SubSurface s){
        
        subSurfaceCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("subsurface"));
        timestampCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("timeStamp"));
        traceCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("traceCount"));
        sequenceNumberCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("sequenceNumber"));
        inlineMaxCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("inlineMax"));
        inlineMinCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("inlineMin"));
        inlineIncCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("inlineInc"));
        xlineMaxCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("xlineMax"));
        xlineMinCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("xlineMin"));
        xlineIncCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("xlineInc"));
        dugShotMaxCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("dugShotMax"));
        dugShotMinCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("dugShotMin"));
        dugChannelIncCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("dugShotInc"));
        dugChannelMaxCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("dugChannelMax"));
        dugChannelMinCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("dugChannelMin"));
        dugChannelIncCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("dugChannelInc"));
        offsetMaxCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("offsetMax"));
        offsetMinCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("offsetMin"));
        offsetIncCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("offsetInc"));
        cmpMaxCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("cmpMax"));
        cmpMinCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("cmpMin"));
        cmpIncCol.setCellValueFactory(new PropertyValueFactory<SubSurface,String>("cmpInc"));
        
        /*
        
        subSurfaceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SubSurface, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SubSurface, String> param) {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              return new ReadOnlyStringWrapper(param.getValue().getSubsurface());
            }
        });
        
        timestampCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SubSurface, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SubSurface, String> param) {
          //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return new ReadOnlyStringWrapper(param.getValue().getTimeStamp());
            }
        }
        );
                */
        ArrayList<TableColumn<SubSurface,String>> cols=new ArrayList<>();
        cols.add(sequenceNumberCol);
        cols.add(subSurfaceCol);
        cols.add(timestampCol);
        cols.add(traceCol);
        
        cols.add(inlineMaxCol);
        cols.add(inlineMinCol);
        cols.add(inlineIncCol);
        cols.add(xlineMaxCol);
        cols.add(xlineMinCol);
        cols.add(xlineIncCol);
        cols.add(dugShotMaxCol);
        cols.add(dugShotMinCol);
        cols.add(dugShotIncCol);
        cols.add(dugChannelMaxCol);
        cols.add(dugChannelMinCol);
        cols.add(dugChannelIncCol);
        cols.add(offsetMaxCol);
        cols.add(offsetMinCol);
        cols.add(offsetIncCol);
        cols.add(cmpMaxCol);
        cols.add(cmpMinCol);
        cols.add(cmpIncCol);
       
        
                
        
        
        this.getColumns().addAll(cols);
    }
    
    public void addSubs(List<SubSurface> sl){
        this.getItems().addAll(sl);
        
    }
}
