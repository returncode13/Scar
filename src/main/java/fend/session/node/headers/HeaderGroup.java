/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import java.io.File;
import java.util.UUID;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;

/**
 *
 * @author naila0152
 */
public class HeaderGroup extends StackPane{
    //private StackPane pane=new StackPane();
    SubSurfaceTable table=new SubSurfaceTable(new SubSurface());
    HeaderTableModel htm;
    
    public HeaderGroup(HeaderTableModel htm){
        this.htm=htm;
        table.addSubs(this.htm.getHeaderList());
       // pane.getChildren().add(table);
       // getChildren().add(pane);
        setMaxHeight(Double.MAX_VALUE);
        setMaxWidth(Double.MAX_VALUE);
        getChildren().add(table);
        setId(UUID.randomUUID().getMostSignificantBits()+"");
        htm.setId(Long.valueOf(getId()));
        File f=new File("table.css");
        getStylesheets().clear();
        System.out.println("HGroup: AbsPath: "+f.getAbsolutePath());
        //getStylesheets().add("file:///"+f.getAbsolutePath().replace("\\", "/"));
        getStylesheets().add(this.getClass().getResource("table.css").toExternalForm());
        
    }
}
