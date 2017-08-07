/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.doubtoverride;

import fend.session.node.headers.doubtoverride.entries.Entries;
import fend.session.node.headers.doubtoverride.entries.comments.CommentModel;
import fend.session.node.headers.doubtoverride.entries.comments.CommentNode;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ButtonCell extends TableCell<Entries, Boolean> {

    final Button btn=new Button("Override");
    
    public ButtonCell(final TableView<Entries> tableView,Map<Entries,Boolean> commitMap) {
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               int selectedIndex=getTableRow().getIndex();
               Entries entry=(Entries)tableView.getItems().get(selectedIndex);
               
                System.out.println("fend.session.node.headers.doubtoverride.ButtonCell(): sub "+entry.getSubsurface()+" : status:  "+entry.getStatus()+" errMessage: "+entry.getErrorMessage());
               
               CommentModel cm=entry.getComment();
                CommentNode cn=new CommentNode(cm);
                if(cm.getStatus()){
                    commitMap.put(entry, Boolean.TRUE);
                }else{
                    //commitMap.put(entry, Boolean.FALSE);
                }
                
            }
        });
        
        
    }
    
    @Override
    protected void updateItem(Boolean item,boolean empty){
        super.updateItem(item,empty);
        if(!empty){
            setGraphic(btn);
        }
    }
    
}
