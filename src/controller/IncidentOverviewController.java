/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import toolclass.Incidents;
import toolclass.Overview;
import tslessonslearneddatabase.ConnectionDatabase;
import tslessonslearneddatabase.Data;
import tslessonslearneddatabase.Log4jConfiguration;
import tslessonslearneddatabase.TSLessonsLearnedDatabase;

/**
 *
 * @author cimpde1
 */
public class IncidentOverviewController implements Initializable {
    
     @FXML
    private Button btnincidentdetails;
     
     @FXML
    private ScrollPane scrollpane;
     
     ObservableList<Incidents> list_incidents;
     
       @FXML
    private TableView<Incidents> table_incidents_overview;

    @FXML
    private TableColumn<Incidents, String> col_status;

    @FXML
    private TableColumn<Incidents, String> col_short;

    @FXML
    private TableColumn<Incidents, String> col_start_date;

    @FXML
    private TableColumn<Incidents, String> col_end_date;

    @FXML
    private TableColumn<Incidents, Integer> col_sap;

    @FXML
    private TableColumn<Incidents, String> col_escalation;
    
    private Stage incident_details;
    
    public org.apache.logging.log4j.Logger logger = LogManager.getLogger(IncidentOverviewController.class);
    
    int index;
    public void onAnchorOverview()
    {
        table_incidents_overview.getSelectionModel().clearSelection();
    }
    
    
     
     public void openincidentsdetails()
     {
        
         if(table_incidents_overview.getSelectionModel().isSelected(index)==true)
         {
             if(incident_details==null)
             {
                  try {
                   incident_details=new Stage();
                   Parent root = FXMLLoader.load(getClass().getResource("/fxml/incidentdetails.fxml"));
                   Scene scene = new Scene(root);

                   incident_details.setTitle("Incidents details");
                   incident_details.setScene(scene);
                   incident_details.initStyle(StageStyle.UTILITY);
                   incident_details.setResizable(false);
                   incident_details.show();
                  
                   Stage s = (Stage) btnincidentdetails .getScene().getWindow();
                   //s.hide();
                   incident_details.setOnHidden(event->{
                       showOverview();
                   });
                   incident_details.setOnCloseRequest(e->{
                       s.toFront();
                       showOverview();
                       table_incidents_overview.getSelectionModel().clearSelection();
                   });
               } catch (IOException ex) {
                  logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: INCIDENT OVERVIEW----IOException: "+ex);
                  //ex.printStackTrace();
               }
             }
             else if(incident_details.isShowing())
             {
                 //incident_details.toFront();
                        //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Incident Details' is already open in the background. Please close it and select another incident from the table.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
             }
             else
             {
                 try {
                   incident_details=new Stage();
                   Parent root = FXMLLoader.load(getClass().getResource("/fxml/incidentdetails.fxml"));
                   Scene scene = new Scene(root);

                   incident_details.setTitle("Incidents details");
                   incident_details.setScene(scene);
                   incident_details.initStyle(StageStyle.UTILITY);
                   incident_details.setResizable(false);
                   incident_details.show();
                   
                   Stage s = (Stage) btnincidentdetails .getScene().getWindow();
                   //s.hide();
                   incident_details.setOnCloseRequest(e->{
                        s.show();
                        showOverview();
                        table_incidents_overview.getSelectionModel().clearSelection();
                   });
               } catch (IOException ex) {
                  logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: INCIDENT OVERVIEW---IOException: "+ex);
                  //ex.printStackTrace();
               }
             }
                   
         }
         else
         {
             if( incident_details!=null)
             {
                 if( incident_details.isShowing() == true)
                 {
                     //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Incident Details' is already open in the background. Please close it and select another incident from the table.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                 }
                 else
                 {
                     logger.error(Log4jConfiguration.currentTime()+"[ERROR]: No row from INCIDENTS OVERVIEW's table was selected");
                                //Creating a dialog
                                Dialog<String> dialog = new Dialog<String>();
                                //Setting the title
                                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                //Setting the content of the dialog
                                dialog.setContentText("No row was selected! Please select one.");
                                //Adding buttons to the dialog pane
                                dialog.getDialogPane().getButtonTypes().add(type);
                                dialog.showAndWait();
                 }
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[WARN]: No row from INCIDENTS OVERVIEW's table was selected");
                                //Creating a dialog
                                Dialog<String> dialog = new Dialog<String>();
                                //Setting the title
                                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                //Setting the content of the dialog
                                dialog.setContentText("No row was selected! Please select one.");
                                //Adding buttons to the dialog pane
                                dialog.getDialogPane().getButtonTypes().add(type);
                                dialog.showAndWait();
             }
                                
         }
          
        
        
     }
     public void onSelectIncident()
     {
        
        index=table_incidents_overview.getSelectionModel().getSelectedIndex();
        if(index <= -1)
        {
            return;
        }
        Incidents incident=table_incidents_overview.getSelectionModel().getSelectedItem();
        Data.idincident_list_history=incident.getId();
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The incident with id "+Data.idincident_list_history+" was selected from INCIDENTS OVERVIEW's table");
        
        //id incident_list_history pt incident-ul selectat
        int id_incident=incident.getId();
        
       
        
         for(int i=0;i<list_incidents.size();i++)
        {
            if(list_incidents.get(i).getId() == id_incident)
            {
                Data.id_incident_list=i;
            }
        }
        
         
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The "+Data.id_incident_list+" was selected from INCIDENT OVERVIEW");
        Data.last_index=list_incidents.size()-1;
        
        
        
     }
     public void showIncidentDetailsonDoubleClick()
     {
         if(incident_details==null)
             {
                  try {
                   incident_details=new Stage();
                   Parent root = FXMLLoader.load(getClass().getResource("/fxml/incidentdetails.fxml"));
                   Scene scene = new Scene(root);

                   incident_details.setTitle("Incidents details");
                   incident_details.setScene(scene);
                   incident_details.initStyle(StageStyle.UTILITY);
                   incident_details.setResizable(false);
                   incident_details.show();
                  
                   Stage s = (Stage) btnincidentdetails .getScene().getWindow();
                   //s.hide();
                   incident_details.setOnCloseRequest(e->{
                       s.toFront();
                       showOverview();
                       table_incidents_overview.getSelectionModel().clearSelection();
                   });
               } catch (IOException ex) {
                  logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: INCIDENT OVERVIEW----IOException: "+ex);
                  //ex.printStackTrace();
               }
             }
             else if(incident_details.isShowing())
             {
                 //incident_details.toFront();
                        //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Incident Details' is already open in the background. Please close it and select another incident from the table.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
             }
             else
             {
                 try {
                   incident_details=new Stage();
                   Parent root = FXMLLoader.load(getClass().getResource("/fxml/incidentdetails.fxml"));
                   Scene scene = new Scene(root);

                   incident_details.setTitle("Incidents details");
                   incident_details.setScene(scene);
                   incident_details.initStyle(StageStyle.UTILITY);
                   incident_details.setResizable(false);
                   incident_details.show();
                   
                   Stage s = (Stage) btnincidentdetails .getScene().getWindow();
                   //s.hide();
                   incident_details.setOnCloseRequest(e->{
                        s.show();
                        showOverview();
                        table_incidents_overview.getSelectionModel().clearSelection();
                   });
               } catch (IOException ex) {
                  logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: INCIDENT OVERVIEW---IOException: "+ex);
                  //ex.printStackTrace();
               }
             }
                   
     }
     public void showOverview()
     {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Showing the UPDATE INCIDENTS OVERVIEW in INCIDENTS OVERVIEW\n\n");
        col_status.setCellValueFactory(new PropertyValueFactory<Incidents,String>("status"));
        col_short.setCellValueFactory(new PropertyValueFactory<Incidents,String>("short_sol"));
        col_start_date.setCellValueFactory(new PropertyValueFactory<Incidents,String>("start_date"));
        col_end_date.setCellValueFactory(new PropertyValueFactory<Incidents,String>("end_date"));
        col_sap.setCellValueFactory(new PropertyValueFactory<Incidents,Integer>("sap"));
        col_escalation.setCellValueFactory(new PropertyValueFactory<Incidents,String>("escalation_status"));
        list_incidents=ConnectionDatabase.getIncidents(Data.id_pb);
        
        
        if(list_incidents.isEmpty()==false)
        {
            table_incidents_overview.setItems(list_incidents);
            Data.incidents=list_incidents;
        }
        else
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty list of incidents in INCIDENT OVERVIEW when INCIDENT DETAILS was closed!");
        }
         
     }
     public String msg(Incidents i)
     {
         return "Incident status: "+i.getStatus()+"\n"+
                 "Incident short solution: "+i.getShort_sol()+"\n"+
                 "Incident start date: "+i.getStart_date()+"\n"+
                 "Incident end date: "+i.getEnd_date()+"\n"+
                 "Incident SAP Order: "+i.getSap()+"\n"+
                 "Incident escalation status: "+i.getEscalation_status()+"\n";
     }
     public void setDoubleClickOnIncidentOverview()
     {
         /* table_incidents_overview.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent mouseEvent) {
                 if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                     System.out.println(mouseEvent.getClickCount());
                     /*if(mouseEvent.getClickCount() == 2){
                         //System.out.println("Double clicked");
                         //onSelectIncident();
                         //showIncidentDetailsonDoubleClick();
                         
                     }
                 }
             }
         });*/
     }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: INCIDENTS OVERVIEW STARTED\n");
        setDoubleClickOnIncidentOverview();
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: PROBLEM's ID: "+Data.id_pb+"in INCIDENTS OVERVIEW\n\n");
        logger.info("The OUTPUT list of incidents: \n");
        
       
        if(Data.incidents!=null)
        {
           list_incidents=Data.incidents;
           
           String messageinc="[0]: "+msg(list_incidents.get(0));
           
           for(int i=1;i<list_incidents.size();i++)
           {
               messageinc=messageinc +"["+i+"]: "+msg(list_incidents.get(i));
           }
           logger.info(messageinc);
           col_status.setCellValueFactory(new PropertyValueFactory<Incidents,String>("status"));
           col_short.setCellValueFactory(new PropertyValueFactory<Incidents,String>("short_sol"));
           col_start_date.setCellValueFactory(new PropertyValueFactory<Incidents,String>("start_date"));
           col_end_date.setCellValueFactory(new PropertyValueFactory<Incidents,String>("end_date"));
           col_sap.setCellValueFactory(new PropertyValueFactory<Incidents,Integer>("sap"));
           col_escalation.setCellValueFactory(new PropertyValueFactory<Incidents,String>("escalation_status"));
          
           table_incidents_overview.setItems(list_incidents);
          
       
           
           
        }
        else
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Empty list of incidents");
        }
        
    }
    
}
