/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.overview;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
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
        this.getGridView().setStyle("-fx-background-color: black");
        if( empty||item==null){
            setText("NMpty");
            setGraphic(null);
        }
        else{
            
            
            VBox vb= new VBox();
           // HBox vb=new HBox();
            vb.setAlignment(Pos.CENTER);
            setText(null);
            this.getGridView().setCellWidth(300);
            Label jobName=new Label(item.getName());
            /* CheckBox pCheckBox=new CheckBox();
            pCheckBox.setDisable(Boolean.TRUE);
            pCheckBox.setText("P");*/
            boolean pf=item.getpFlag();
            /* pCheckBox.setSelected(pf);
            
            CheckBox qCheckBox=new CheckBox();
            qCheckBox.setDisable(Boolean.TRUE);
            qCheckBox.setText("Q");*/
            boolean qf=item.getqFlag();
            /* qCheckBox.setSelected(qf);
            
            vb.getChildren().addAll(jobName,pCheckBox,qCheckBox);*/
            vb.getChildren().add(jobName);
            setGraphic(vb);
            
            if(pf && !qf){
             setStyle(" -fx-background-color: yellow; -fx-background-radius: 10;  -fx-border-width: 0; -fx-padding: 2; -fx-pref-width: 1000; -fx-max-width: 1000; -fx-pref-height: 1000; -fx-max-height: 1000; -fx-effect: dropshadow(three-pass-box, #93948d, 10, 0, 0, 0);");

            }
            if(!pf && qf){
             setStyle(" -fx-background-color: red; -fx-background-radius: 10;  -fx-border-width: 0; -fx-padding: 2; -fx-pref-width: 1000; -fx-max-width: 1000; -fx-pref-height: 1000; -fx-max-height: 1000; -fx-effect: dropshadow(three-pass-box, #93948d, 10, 0, 0, 0);");

            }
            if(!pf && !qf){
             setStyle(" -fx-background-color: green; -fx-background-radius: 10;  -fx-border-width: 0; -fx-padding: 2; -fx-pref-width: 1000; -fx-max-width: 1000; -fx-pref-height: 1000; -fx-max-height: 1000; -fx-effect: dropshadow(three-pass-box, #93948d, 10, 0, 0, 0);");

            }
             if(pf && qf){
             setStyle(" -fx-background-color: red; -fx-border-color: yellow ;-fx-background-radius: 10; -fx-border-radius: 5; -fx-border-width: 5; -fx-padding: 2; -fx-pref-width: 300; -fx-max-width: 300; -fx-pref-height: 1000; -fx-max-height: 1000; -fx-effect: dropshadow(three-pass-box, #93948d, 10, 0, 0, 0);");

            }
            
            
            

            }
        }
    }

