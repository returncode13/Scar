/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.overview;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.controlsfx.control.GridCell;

/**
 *
 * @author sharath
 */
public class OverviewItemCell extends GridCell<OverviewItem>{
    
    @Override
    protected void updateItem(OverviewItem item,boolean empty){
        super.updateItem(item, empty);
        
        if(empty || item==null){
            setText("Empty");
            setGraphic(null);
        }
        else{
            VBox vb= new VBox();
            vb.setAlignment(Pos.CENTER);
            Label jobName=new Label(item.getName());
            CheckBox pCheckBox=new CheckBox();
            pCheckBox.setSelected(item.getpFlag());
            CheckBox qCheckBox=new CheckBox();
            qCheckBox.setSelected(item.getqFlag());
            vb.getChildren().addAll(jobName,pCheckBox,qCheckBox);
            setGraphic(vb);
            setStyle("-fx-border-volor: red; -fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-width: 0; -fx-padding: 10; -fx-pref-width: 145; -fx-max-width: 145; -fx-max-width: 145; -fx-pref-height: 130; -fx-max-height: 130; -fx-effect: dropshadow(three-pass-box, #93948d, 10, 0, 0, 0);");

            }
        }
    }

