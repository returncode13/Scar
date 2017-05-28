/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.types.type2;

import fend.session.edges.Links;
import fend.session.edges.LinksModel;
import fend.session.edges.anchor.Anchor;
import fend.session.edges.anchor.AnchorModel;
import fend.session.edges.curves.CubCurve;
import fend.session.edges.curves.CubCurveModel;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type0.JobStepType0Node;
import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.jobs.types.type1.JobStepType1Node;
import fend.session.node.jobs.types.type1.JobStepType1NodeController;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author adira0150
 */
public class JobStepType2Node extends AnchorPane implements JobStepType0Node {
    
    private final Long type=2L;
    private JobStepType2NodeController jsnc;
    private FXMLLoader fXMLLoader;
    private final URL location;
    private final ContextMenu menu=new ContextMenu();
    
    private EventHandler<MouseEvent> mOnDragDetected;
   private EventHandler<DragEvent> mOnDragOver;
   private EventHandler<DragEvent> mOnDragDropped;
   private JobStepType2Model jmodel;
    
    
    
    static int i=0;
    public JobStepType2Node(JobStepType2Model item) {
      
        this.jmodel=item;
        this.location=getClass().getClassLoader().getResource("nodeResources/jobs/types/type2/JobStepType2NodeView_1.fxml"); 
        //URL location = JobStepNodeController.class.getResource("JobStepNodeView.fxml");
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                jsnc=(JobStepType2NodeController)fXMLLoader.getController();
             //i++;
               // this.setId(new Integer(i) +"");
               if(item.getId()==null){
                 //  System.out.println("fend.session.node.jobs.JobStepNode.<init>(): A new jobstepNode: "   );
                   setId(UUID.randomUUID().getMostSignificantBits()+"");
               }
               else{
                //    System.out.println("fend.session.node.jobs.JobStepNode.<init>(): jobstepnode from existing jobstepmodel : "+item.getId()   );
                   setId(item.getId()+"");
               }
                
              //  System.out.println("fend.session.node.jobs.JobStepNode.<init>() MAXY: "+this.boundsInLocalProperty().get().getMaxY());
                
                jsnc.setId(Long.valueOf(getId()));
                jsnc.setView(this) ;
                jsnc.setModel(item);
                
               
                
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
                    
       buildNodeDragHandlers();
       buildMenuHandlers();
        
        
        
       
    }
    
    private void buildNodeDragHandlers(){
        
        mOnDragDetected=new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Dragboard dragboard=startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content=new ClipboardContent();
          //  setId(this.getClass().getSimpleName()+System.currentTimeMillis());
          //  System.out.println("JSN: ID= "+getId());
               // System.out.println("JobStepNode :mOnDragDetected: ");
            content.putString(getId());
            dragboard.setContent(content);
            event.consume();
            }
        };
        
        mOnDragOver=new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
          //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               // System.out.println("JSN: Drag Over "+getId()+" xy "+event.getScreenX()+","+event.getScreenY());
              // System.out.println("JobStepNode :mOnDragOver: ");
                JobStepType2Node.this.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                event.acceptTransferModes(TransferMode.LINK);
                event.consume();
            }
        };
        
        mOnDragDropped=new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
          //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              //  System.out.println("JSN: Drag Dropped on "+getId()+" xy "+event.getScreenX()+","+event.getScreenY());
                // event.acceptTransferModes(TransferMode.LINK);
               // System.out.println("JobStepNode :mOnDragDropped: ");
                event.setDropCompleted(true);
        event.consume();
            }
        };
        /*
         setOnDragDetected((MouseEvent event)->{
            Dragboard dragboard=startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content=new ClipboardContent();
            setId(this.getClass().getSimpleName()+System.currentTimeMillis());
            System.out.println("JSN: ID= "+getId());
            content.putString(getId());
            dragboard.setContent(content);
            event.consume();
        });
        */
        
        setOnDragDetected(mOnDragDetected);
        setOnMouseDragEntered(new EventHandler<MouseDragEvent>(){

            @Override
            public void handle(MouseDragEvent event) {
         //       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               // startFullDrag();
              //  System.out.println("JSN: Mouse Drag Entered on "+getId());
               // System.out.println("JobStepNode :setOnMouseDragEntered: ");
            }
        });
        
        setOnMouseDragReleased(new EventHandler<MouseDragEvent>(){
            
            @Override
            public void handle(MouseDragEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               
                
              //  System.out.println("JobStepNode :setOnMouseDragReleased: ");
               List<LinksModel> llm=JobStepType2Node.this.jmodel.getListOfLinkModels(); 
                
               
               Anchor anc=(Anchor) event.getGestureSource();
               AnchorModel ancModel=anc.getModel();
               
               LinksModel lm=ancModel.getLmodel();
               lm.setChild(JobStepType2Node.this.jmodel);                        //the node the anchor is dropped on
               
               //JobStepType1Node jsource=(JobStepType1Node) event.getSource();
               
              // anc.getModel().setJob(JobStepNode.this.getJsnc().getModel());            //set child job
               
               
             //  System.out.println("JSN: Mouse Drag Released on "+getId()+ " source "+event.getSource().getClass().getSimpleName()+ " SourceID: "+ jsource.getId()+" GSource: "+event.getGestureSource().getClass().getSimpleName()+ " Target: "+event.getTarget().getClass().getSimpleName());
               
               Links link=anc.getLink();
               CubCurve curve=link.getCurve();
               curve.endXProperty().bind(Bindings.add(JobStepType2Node.this.layoutXProperty(),JobStepType2Node.this.boundsInLocalProperty().get().getMinX()));// JobStepNode.this.getWidth()/2.0));
               curve.endYProperty().bind(Bindings.add(JobStepType2Node.this.layoutYProperty(),JobStepType2Node.this.boundsInLocalProperty().get().getMaxY()/2));//JobStepNode.this.getWidth()/2.0));
             //  System.out.println("JSN: the anchor dropped has the id "+anc.getAId());
//                System.out.println("Link # "+link.getId()+ "now connects Parent : "+link.getStart().getModel().getJob().getJsId() +" to Child :"+link.getEnd().getModel().getJob().getJsId());
           
                CubCurveModel curveModel=curve.getModel();
                //LinksModel lmod=new LinksModel(ancModel, ancModel, curveModel);
                
               System.out.println("JSN: Link # "+link.getId()+ "now connects Parent : "+link.getLmodel().getParent().getId() +" to Child :"+link.getLmodel().getChild().getId());
           
           
            //    System.out.println("JSN: Adding to current job as a parent");
                jsnc.addToLineage(link.getLmodel().getParent());
            }
            
        });
        
        setOnMouseDragOver(new EventHandler<MouseDragEvent>() {

            @Override
            public void handle(MouseDragEvent event) {
         //       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       //  System.out.println("JSN: Mouse Drag Over on "+getId());
            }
        });
        setOnMouseEntered(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            //  System.out.println("JSN: Mouse Entered on "+getId()+ "  eventType: "+event.getEventType().toString());
            }
            
        });
        
        setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
         //       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              //  System.out.println("JSN: Mouse Released on "+getId());
            }
        });
        
       setOnDragOver(mOnDragOver);
        setOnDragDropped(mOnDragDropped);
        //move this function elsewhere ...not a drag event
       
       
    }
    
    private void buildLinkDragHandlers(){
        
    }
    
    
    
    
    
    private void buildMenuHandlers(){
         MenuItem addAChildJob=new MenuItem("+link to a child job");
        MenuItem deleteThisJob=new MenuItem("-delete this job node");
        menu.getItems().addAll(addAChildJob,deleteThisJob);
        
        setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

             @Override
             public void handle(ContextMenuEvent event) {
                System.out.println("fend.session.node.jobs.JobStepNode: context menu requested for "+jsnc.getModel().getId());
            menu.show(JobStepType2Node.this, event.getScreenX(), event.getScreenY());
            
             }
         });
        /*
        setOnContextMenuRequested(t->{
            System.out.println("context menu requested for "+jsnc.getModel().getId());
            menu.show(this, t.getScreenX(), t.getScreenY());
        });
        */
        addAChildJob.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent event) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                 double startX=getScene().getX();
                    double startY=getScene().getY();
                   System.out.println("fend.session.node.jobs.JobStepNode.   MAXY: "+JobStepType2Node.this.boundsInLocalProperty().get().getMaxY());
                   System.out.println("Curve will start @(x,y) = ("+startX+","+startY+")");
                   
                   
                    AnchorModel mStart=new AnchorModel();
                    mStart.setCenterX(startX);
                    mStart.setCenterY(startY);
                    mStart.setJob(JobStepType2Node.this.jmodel);
       
                    AnchorModel mEnd=new AnchorModel();
                    mEnd.setCenterX(startX+10.0);
                    mEnd.setCenterY(startY);
       
                    CubCurveModel cmod=new CubCurveModel();

                    LinksModel lm = new LinksModel(mStart, mEnd, cmod);
                   
                   JobStepType2Model jmod=JobStepType2Node.this.jmodel;
                   jmod.addToListOfLinksModel(lm);
                   
                   jsnc.startNewLink(jmod);
             //      jsnc.testIfButtonCanBeAdded(startX, startY);
             }
         });
        
        deleteThisJob.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent event) {
                 System.out.println("fend.session.node.jobs.JobStepNode. This will delete this node with id: "+JobStepType2Node.this.getId()+" session: "+JobStepType2Node.this.jmodel.getSessionModel().getName()+" : with sessionID: "+JobStepType2Node.this.jmodel.getSessionModel().getId());
                 JobStepType2Node.this.jmodel.getSessionModel().removeJobfromSession(jmodel);
                 JobStepType2Node.this.jmodel.getSessionModel().addToDeleteList(jmodel);
                 List<LinksModel> lmlist=JobStepType2Node.this.jmodel.getListOfLinkModels();
                 
                 List<JobStepType0Model> parents=JobStepType2Node.this.jmodel.getJsParents();
                 for (Iterator<JobStepType0Model> iterator = parents.iterator(); iterator.hasNext();) {
                     JobStepType0Model parent = iterator.next();
                     List<LinksModel> lmodp=parent.getListOfLinkModels();
                     for (Iterator<LinksModel> iterator1 = lmodp.iterator(); iterator1.hasNext();) {
                         LinksModel lmp = iterator1.next();
                         if(lmp.getChild().getId().equals(JobStepType2Node.this.jmodel.getId()))
                         {
                             lmp.setVisibility(Boolean.FALSE);
                         }
                     }
                     
                     
                 }
                 
                 
                 
                 List<JobStepType0Model> children=JobStepType2Node.this.jmodel.getJsChildren();
                 for (Iterator<JobStepType0Model> iterator = children.iterator(); iterator.hasNext();) {
                     JobStepType0Model child = iterator.next();
                     List<LinksModel> lmodc=child.getListOfLinkModels();
                     for (Iterator<LinksModel> iterator1 = lmodc.iterator(); iterator1.hasNext();) {
                         LinksModel lmc = iterator1.next();
                         if(lmc.getChild().getId().equals(JobStepType2Node.this.jmodel.getId()))
                         {
                             lmc.setVisibility(Boolean.FALSE);
                         }
                         
                     }
                 }
                 
                 
                 
                 for (Iterator<LinksModel> iterator = lmlist.iterator(); iterator.hasNext();) {
                     LinksModel next = iterator.next();
                     
                     
                     next.setVisibility(Boolean.FALSE);
                 }
                 JobStepType2Node.this.setVisible(false);
             }
         });
        /*
        addAChildJob.setOnAction(e->{
            System.out.println("adding a child ");
                    
                    
                    double startX=getScene().getX();
                    double startY=getScene().getY();
                   System.out.println("Curve will start @(x,y) = ("+startX+","+startY+")");
                 
                   
                   jsnc.testIfButtonCanBeAdded(startX, startY);
                      //curve.
        });*/
        
        
        
        
    }
    
     public void relocateToPoint(Point2D xx){
        Point2D coords=getParent().sceneToLocal(xx);
        
        relocate(
                (int)(coords.getX() -(getBoundsInLocal().getWidth()/2)),
                (int)(coords.getY() -(getBoundsInLocal().getHeight()/2))
                );
        
    }

    public EventHandler<MouseEvent> getmOnDragDetected() {
        return mOnDragDetected;
    }

    public void setmOnDragDetected(EventHandler<MouseEvent> mOnDragDetected) {
        this.mOnDragDetected = mOnDragDetected;
    }
    
    
   
    
    
   
    public JobStepType2NodeController getJsnc() {                                                            //for debug only ..remove 
        return jsnc;
    }
    
   
    
    
    
    
}
