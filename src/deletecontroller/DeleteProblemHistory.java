/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deletecontroller;

import editcontroller.EditProblemHistory;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.AnchorPane;
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
public class DeleteProblemHistory implements Initializable {
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField user;

    @FXML
    private TextField status;

    @FXML
    private TextField hmi;

    @FXML
    private TextField engineer;

    @FXML
    private TextArea pbdesc;

   
    @FXML
    private Button savebtn;

    @FXML
    private Button cancelbtn;

    
    @FXML
    private TextField iftextfield;

    @FXML
    private TextField linetextfield;

    @FXML
    private TextField machinetextfield;
    
    @FXML
    private ImageView saveicon;
    
     @FXML
    private ImageView cancelicon;
    
    Connection conn=null;
    
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(DeleteProblemHistory.class);
    
    
    public void cancelFocus() 
    {
        anchorPane.requestFocus();
    }
    
     public void SavingUpdate(String tablename,int id)
    {
         conn=ConnectionDatabase.getConnection();
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in DELETE PROBLEM HISTORY");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in DELETE PROBLEM HISTORY has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in DELETE PROBLEM HISTORY!");
             }
             
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: DELETE PROBLEM HISTORY --- SQLException: "+ex+"\n\n");
         }
         
    }
    
    @FXML
    void cancel(ActionEvent event) {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: NO button from DELETE PROBLEM HISTORY was pressed!\n\n");
            Stage stage=(Stage)cancelbtn.getScene().getWindow();
            stage.close();
    }
    
    @FXML
    void save(ActionEvent event) {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The YES button from DELETE PROBLEM HISTORY was pressed!");
        try {
            int idproblem_history=Data.id_pb_history;
            //delete all the incidents for that problem
            conn=ConnectionDatabase.getConnection();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure deleteIncidentsAfterPbId() in DELETE PROBLEM HISTORY");
            CallableStatement deleteincidents = conn.prepareCall("{call deleteIncidentsAfterPbId(?)}");
            deleteincidents.setInt(1, idproblem_history);
            
            //se sterg si incidente si probleme
            if(deleteincidents.executeUpdate()!=0)
            {
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The problem selected with his incidents  was deleted with successs in DELETE PROBLEM HISTORY");
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
                 SavingUpdate("problem_list_history",idproblem_history);
                 Stage stage=(Stage)savebtn.getScene().getWindow();
                 stage.close();
                 
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was deleted in DELETE PROBLEM HISTORY: \n"+
                                               "IF: "+iftextfield.getText()+"\n"+
                                               "Line: "+linetextfield.getText()+"\n"+
                                               "Machine: "+machinetextfield.getText()+"\n"+
                                               "Username: "+user.getText()+"\n"+
                                               "Status: "+status.getText()+"\n"+
                                               "Engineer: "+engineer.getText()+"\n"+
                                               "Problem description: "+pbdesc.getText()+"\n"+
                                               "HMI Error: "+hmi.getText()+"\n\n");
                /*
                //delete the problem
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure deleteProblem() in DELETE PROBLEM HISTORY");
                CallableStatement deleteproblem = conn.prepareCall("{call deleteProblem(?)}");
                deleteproblem.setInt(1, idproblem_history);
                
                if(deleteproblem.executeUpdate()!=0)
                {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was deleted in DELETE PROBLEM HISTORY: \n"+
                                               "IF: "+iftextfield.getText()+"\n"+
                                               "Line: "+linetextfield.getText()+"\n"+
                                               "Machine: "+machinetextfield.getText()+"\n"+
                                               "Username: "+user.getText()+"\n"+
                                               "Status: "+status.getText()+"\n"+
                                               "Engineer: "+engineer.getText()+"\n"+
                                               "Problem description: "+pbdesc.getText()+"\n"+
                                               "HMI Error: "+hmi.getText()+"\n\n");
                                       //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       dialog.setTitle("Success message");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Your data has been deleted!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                    SavingUpdate("problem_list_history",idproblem_history);
                    Stage stage=(Stage)savebtn.getScene().getWindow();
                    stage.close();
                }
                else
                {
                                       logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while deleting the data in DELETE PROBLEM HISTORY");
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
                */
                
            }
            else
            {
                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while deleting the problem selected with his incidents in DELETE PROBLEM HISTORY");
            }
            
            
            
        } catch (SQLException ex) {
           logger.fatal("[FATAL]: DELETE PROBLEM HISTORY--- SQLException: "+ex+"\n\n");
        }
        
    }

   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE PROBLEM HISTORY STARTED");
        
        /*status.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
        user.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");        
        pbdesc.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");  
        engineer.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
        hmi.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
        iftextfield.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
        linetextfield.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2; ");
        machinetextfield.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");*/
        
        
        
        status.setFocusTraversable(false);
        user.setFocusTraversable(false);   
        pbdesc.setFocusTraversable(false);
        engineer.setFocusTraversable(false);
        hmi.setFocusTraversable(false);
        iftextfield.setFocusTraversable(false);
        linetextfield.setFocusTraversable(false);
        machinetextfield.setFocusTraversable(false);
        
        savebtn.setFocusTraversable(false);
        cancelbtn.setFocusTraversable(false);
        
        
        user.setText(Data.user_problem_history);
        status.setText(Data.status_problem_history);
        engineer.setText(Data.engineer_problem_history);
        pbdesc.setText(Data.problem_description_problem_history);
        hmi.setText(Data.hmi_problem_history);
        
        String IF=ConnectionDatabase.getDescAfterIFID(Data.ifid__value_problem_history);
        iftextfield.setText(IF);
        
        String Line=ConnectionDatabase.getLineDesc(Data.lineid_value_problem_history);
        linetextfield.setText(Line);
        
        String Machine=ConnectionDatabase.getMachineDesc(Data.machineid_value_problem_history);
        machinetextfield.setText(Machine);
        
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
       
    }
    
}
