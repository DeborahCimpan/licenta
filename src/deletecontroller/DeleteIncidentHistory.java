/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deletecontroller;

import editcontroller.EditIncidentHistory;
import java.io.File;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import tslessonslearneddatabase.ConnectionDatabase;
import static tslessonslearneddatabase.ConnectionDatabase.logger;
import tslessonslearneddatabase.Data;
import tslessonslearneddatabase.Log4jConfiguration;
import tslessonslearneddatabase.TSLessonsLearnedDatabase;

/**
 *
 * @author cimpde1
 */
public class DeleteIncidentHistory implements Initializable{
    
    @FXML
    private TextField startdate;

    @FXML
    private TextField enddate;

    @FXML
    private TextField status;

   

    @FXML
    private TextField user;

    @FXML
    private TextField engineer;

    @FXML
    private TextArea spareparts;

    @FXML
    private TextArea shortsol;

    @FXML
    private TextField sap;

    @FXML
    private TextArea solcreator;

    @FXML
    private TextArea solescal;

   
    @FXML
    private Button savebtn;

    @FXML
    private Button cancelbtn;

    @FXML
    private ImageView saveicon;

    @FXML
    private ImageView cancelicon;
    
   
     @FXML
    private AnchorPane anchorPane;
    String fisier=null;
    Connection conn=null;
    int id_EscTeam=0;
    String part_numbere="";
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(DeleteIncidentHistory.class);
    public void SavingUpdate(String tablename,int id)
    {
         conn=ConnectionDatabase.getConnection();
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in DELETE INCIDENT HISTORY");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in DELETE INCIDENT HISTORY has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in DELETE INCIDENT HISTORY!");
             }
             
             
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: DELETE INCIDENT HISTORY --- SQLException: "+ex+"\n\n");
         }
         
        
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: NO button from DELETE INCIDENT HISTORY was pressed!\n\n");
        Stage stage=(Stage)cancelbtn.getScene().getWindow();
        stage.close();
    }
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The YES button from DELETE INCIDENT HISTORY was pressed!");
        try {
            int id_incident=Data.id_incident_history;
            conn=ConnectionDatabase.getConnection();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure deleteIncidentHistory() in DELETE INCIDENT HISTORY");
            CallableStatement delete = conn.prepareCall("{call deleteIncidentHistory(?)}");
            delete.setInt(1, id_incident);
            
           if(delete.executeUpdate()!=0)
                       {
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was deleted in DELETE INCIDENT HISTORY: \n"+
                                               "Start date: "+startdate.getText()+"\n"+
                                               "End date: "+enddate.getText()+"\n"+
                                               "Solution creator: "+solcreator.getText()+"\n"+
                                               "Solution escalation: "+solescal.getText()+"\n"+
                                               "Part numbere: "+spareparts.getText()+"\n"+
                                               "Short solution: "+shortsol.getText()+"\n\n");
                                       //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage1.getIcons().add(new Image("images/ok.png"));
                                       dialog.setTitle("Success");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Your data has been deleted!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       SavingUpdate("incident_list_history",id_incident);
                                       Stage stage=(Stage)savebtn.getScene().getWindow();
                                       stage.close();
                       }
           else
           {
                                       logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while deleting the data in DELETE INCIDENT HISTORY");
                                       //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       dialog.setTitle("Error message");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Something went wrong while deleting the data!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();
           }
            
            
        } catch (SQLException ex) {
            logger.fatal("[FATAL]: DELETE INCIDENT HISTORY--- SQLException: "+ex+"\n\n");
        }
        
    }
    @FXML
    void cancelFocus(MouseEvent event) 
    {
            anchorPane.requestFocus();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE INCIDENT HISTORY STARTED");
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
     
        /*startdate.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
        enddate.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
        status.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
        user.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");        
        sap.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");  
        engineer.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
        solcreator.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
        solescal.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
        spareparts.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2; ");
        shortsol.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");*/
        
        startdate.setFocusTraversable(false);
        enddate.setFocusTraversable(false);
        status.setFocusTraversable(false);
        user.setFocusTraversable(false);
        sap.setFocusTraversable(false);
        engineer.setFocusTraversable(false);
        solcreator.setFocusTraversable(false);
        solescal.setFocusTraversable(false);
        spareparts.setFocusTraversable(false);
        shortsol.setFocusTraversable(false);
        
        savebtn.setFocusTraversable(false);
        cancelbtn.setFocusTraversable(false);
                
        startdate.setText(Data.incident_Start_Date_hour);
        enddate.setText(Data.incident_End_Date_hour);
        status.setText(Data.incident_Status);
        user.setText(Data.incident_Creator_username);
        sap.setText(String.valueOf(Data.incident_SAP_Order_Nr));
        engineer.setText(Data.incident_history_engineer);
        solcreator.setText(Data.incident_Solution_Creator);
        solescal.setText(Data.incident_Solution_Escalation);
        spareparts.setText(Data.incident_history_spare_parts);
        shortsol.setText(Data.incident_short_solution);
        
        
       
        
        
        
    }
    
}
