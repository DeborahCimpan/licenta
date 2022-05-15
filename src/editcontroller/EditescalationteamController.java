/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editcontroller;


import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import tslessonslearneddatabase.ConnectionDatabase;
import static tslessonslearneddatabase.ConnectionDatabase.logger;
import tslessonslearneddatabase.Data;
import tslessonslearneddatabase.Log4jConfiguration;
import tslessonslearneddatabase.TSLessonsLearnedDatabase;

/**
 * FXML Controller class
 *
 * @author cimpde1
 */
public class EditescalationteamController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
      @FXML
    private AnchorPane anchorPane;
      
      @FXML
    private ImageView cancelicon;
      @FXML
    private ImageView saveicon;
      
        @FXML
    void cancelFocus(MouseEvent event) {
        anchorPane.requestFocus();
    }
      
      @FXML
    private TextField name;

    @FXML
    private Button savebtn;

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField job;

    @FXML
    private TextField user;

    @FXML
    private TextField email;
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(EditescalationteamController.class);
    Connection conn=null;
     
     public void SavingUpdate(String tablename,int id)
    {
         conn=ConnectionDatabase.getConnection();
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in EDIT ESCALATION TEAM");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in EDIT ESCALATION TEAM has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in EDIT ESCALATION TEAM!");
             }
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: EDIT ESCALATION TEAM --- SQLException: "+ex+"\n\n");
         }
         
    }
    
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button from EDIT ESCALATION TEAM was pressed!");
        if(name.getText().isEmpty()==true || job.getText().isEmpty() || user.getText().isEmpty() || email.getText().isEmpty())
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are empty in EDIT ESCALATION TEAM\n\n");
                 //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
               Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Please complete all the fields!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
        }
        else if(name.getText().length() > 50 || job.getText().length() > 45 || user.getText().length() > 7 || email.getText().length() > 45 ||
                email.getText().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")==false)
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are incorrect in EDIT ESCALATION TEAM\n\n");
                 //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/error.png"));
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
               conn = ConnectionDatabase.connectDb();
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure editEngineer() in EDIT ESCALATION TEAM");
               CallableStatement ps = conn.prepareCall("{call editEngineer(?,?,?,?,?)}");
               ps.setInt(1, Data.id_esc_team);
               ps.setString(2, name.getText());
                ps.setString(3,job.getText());
               ps.setString(4, user.getText());
               ps.setString(5, email.getText());
               
               if(ps.executeUpdate()!=0)
               {
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was saved in EDIT ESCALATION TEAM: \n"+
                                               "Name: "+name.getText()+"\n"+
                                               "Job description: "+job.getText()+"\n"+
                                               "Username: "+user.getText()+"\n"+
                                               "Email: "+email.getText()+"\n\n");
                                        //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/ok.png"));
                                        dialog.setTitle("Success");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Your data has been saved!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                        SavingUpdate("escalation_team",Data.id_esc_team);
                                        Stage s = (Stage) savebtn.getScene().getWindow();
                                        s.close();
               }
               else
               {
                   logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data in EDIT ESCALATION TEAM");
                    //Creating a dialog
                    Dialog<String> dialog = new Dialog<String>();
                    //Setting the title
                    Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/error.png"));
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
                    logger.fatal("[FATAL]: EDIT ESCALATION TEAM--- SQLException: "+ex+"\n\n");

            }
        }
        
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from EDIT ESCALATION TEAM was pressed!\n\n");
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT ESCALATION TEAM STARTED");
        
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        
        name.setText(Data.engineer_name);
        job.setText(Data.engineer_job_desc);
        user.setText(Data.engineer_user);
        email.setText(Data.engineer_email);
        
        
        name.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>50)
                     {
                         name.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        name.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        
        job.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>45)
                     {
                         job.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        job.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        
        
        user.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>45)
                     {
                         user.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        user.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        
        user.setStyle("-fx-opacity: 1");
        
        email.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                      String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
                     if(newValue.toString().length()>45 || newValue.toString().matches(regex)==false)
                     {
                         email.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        email.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        
        
        
    }    
    
}
