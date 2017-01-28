package fend.overview;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

/**
 *
 * @author sharath
 */
public class OverviewCellFactory implements Callback<GridView<OverviewItem>,GridCell<OverviewItem>>{

    @Override
    public GridCell<OverviewItem> call(GridView<OverviewItem> param) {
        OverviewItemCell cell = new OverviewItemCell();
        cell.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.SECONDARY){
                    System.out.println("OverviewCellFactory: right click pending implementation");
                }
            }
            
        });
        return cell;
    }
    
    
}