/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editcontroller;

import java.io.File;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
import toolclass.FileChooserTemplate;
import tslessonslearneddatabase.ConnectionDatabase;
import static tslessonslearneddatabase.ConnectionDatabase.logger;
import tslessonslearneddatabase.Data;
import tslessonslearneddatabase.Log4jConfiguration;
import tslessonslearneddatabase.TSLessonsLearnedDatabase;

/**
 *
 * @author cimpde1
 */
public class EditProblemHistory implements Initializable {
      

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
    private ImageView saveicon;


    
    
    @FXML
    private Button savebtn;

    @FXML
    private Button cancelbtn;
   
    @FXML
    private ImageView cancelicon;
   
    
    @FXML
    private TextField iftextfield;

    @FXML
    private TextField linetextfield;

    @FXML
    private TextField machinetextfield;
    
  
    
    @FXML
    private AnchorPane anchorPane;
    
    Connection conn=null;
    
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(EditProblemHistory.class);
    
    @FXML
    void cancelFocus(MouseEvent event) 
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
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in EDIT PROBLEM HISTORY");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in EDIT PROBLEM HISTORY has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in EDIT PROBLEM HISTORY!");
             }
             
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: EDIT PROBLEM HISTORY --- SQLException: "+ex+"\n\n");
         }
        
        
    }
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button from EDIT PROBLEM HISTORY was pressed!");
        if(hmi.getText().isEmpty()==false && pbdesc.getText().isEmpty()==false)
        {
            if(pbdesc.getText().length() > 100 || hmi.getText().length() > 45)
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are incorrect in EDIT PROBLEM HISTORY\n\n");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("images/error.png"));
                dialog.setTitle("Error");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Incorrect inputs!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
        }
        else
          {
            try {
            conn=ConnectionDatabase.getConnection();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure editProblemHistory() in EDIT INCIDENT HISTORY");
            CallableStatement ps = conn.prepareCall("{call editProblemHistory(?,?,?)}");
            int idpb=Data.id_pb_history;
            ps.setInt(1, idpb);
            ps.setString(2, hmi.getText());
            ps.setString(3, pbdesc.getText());
          
            //ps.execute();
            if(ps.executeUpdate()!=0)
                       {
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was saved in EDIT PROBLEM HISTORY: \n"+
                                               "HMI Error: "+hmi.getText()+"\n"+
                                               "Problem description: "+pbdesc.getText()+"\n\n");
                                       //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/ok.png"));
                                       dialog.setTitle("Success");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Your data has been saved!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       SavingUpdate("problem_list_history",Data.id_pb_history);
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();

                       }
             else
                       {
                                       logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data in EDIT PROBLEM HISTORY");
                                       //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/error.png"));
                                       dialog.setTitle("Error");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Something went wrong while saving the data!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();

                      }

            
          
            
        } catch (SQLException ex) {
             logger.fatal("[FATAL]: EDIT PROBLEM HISTORY--- SQLException: "+ex+"\n\n");
        }
          }
        }
        
        else
        {
             logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are empty in EDIT PROBLEM HISTORY\n\n");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("images/warn.png"));
                dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Please complete all the fields!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
        }
        
        
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from EDIT PROBLEM HISTORY was pressed!\n\n");
        Stage stage=(Stage)cancelbtn.getScene().getWindow();
        stage.close();
    }
            
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT PROBLEM HISTORY STARTED");
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
        hmi.setFocusTraversable(false);
        user.setStyle("-fx-opacity: 1");
        status.setStyle("-fx-opacity: 1");
        engineer.setStyle("-fx-opacity: 1");
        iftextfield.setStyle("-fx-opacity: 1");
        linetextfield.setStyle("-fx-opacity: 1");
        machinetextfield.setStyle("-fx-opacity: 1");
        
        hmi.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>45) { 
                            
                            hmi.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else{
                          hmi.setStyle("-fx-border-width: 0.5px ;");
                         
                     }
                     
                       
           
                 }
                 
                 
             });
        
        pbdesc.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>100) { 
                            
                         pbdesc.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");    
                     }
                     else{
                         pbdesc.setStyle("-fx-border-width: 0.5px ;");
                         
                     }
                     
                       
           
                 }
                 
                 
             });
                    
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        
         
    }
    
}
