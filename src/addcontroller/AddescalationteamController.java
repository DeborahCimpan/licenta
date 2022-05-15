/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addcontroller;

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
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class AddescalationteamController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField name_field_esc_team;

    @FXML
    private TextField user;

     @FXML
    private TextField jobdesc;

    @FXML
    private TextField email;

    @FXML
    private Button savebtn;
    
     @FXML
    private ImageView cancelicon;

     @FXML
    private ImageView saveicon;
     
    @FXML
    private Button cancelbtn;
    public org.apache.logging.log4j.Logger logger = LogManager.getLogger(AddescalationteamController.class);
    
    Connection conn=null;
    public void SavingUpdate(String tablename,int id)
    {
         conn=ConnectionDatabase.getConnection();
         org.apache.logging.log4j.Logger logger;
         logger = LogManager.getLogger(AddescalationteamController.class);
         
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in ADD ESCALATION TEAM");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in ADD ESCALATION TEAM has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in ADD ESCALATION TEAM!");
             }
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: ADD ESCALATION TEAM --- SQLException: "+ex+"\n\n");
         }
         
    }
    
    public void save()
    {
       
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button from ADD ESCALATION TEAM was pressed!");
        if(name_field_esc_team.getText().isEmpty() || user.getText().isEmpty() || jobdesc.getText().isEmpty() ||  email.getText().toString().isEmpty() )
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields from ADD ESCALATION TEAM are empty!\n\n");
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
        else if(name_field_esc_team.getText().length() > 50 || user.getText().length() > 7 || 
                jobdesc.getText().length() > 45 ||  email.getText().length() > 45 || email.getText().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")==false)
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields from ADD ESCALATION TEAM are incorrect!\n\n");
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
        else{
            try {
              conn = ConnectionDatabase.getConnection();
              logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure addEngineer() in ADD ESCALATION TEAM");
              CallableStatement ps = conn.prepareCall("{call addEngineer(?,?,?,? )}");
              ps.setString(1,name_field_esc_team.getText() );
              ps.setString(2,jobdesc.getText());
              ps.setString(3,user.getText());
              ps.setString(4, email.getText());
              //ps.execute();
              if(ps.executeUpdate()!=0)
              {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The engineer was added with success in ESCALATION TEAM: \n"+
                            "Name: "+name_field_esc_team.getText()+"\n"+
                            "Job description: "+jobdesc.getText()+"\n"+
                            "Username: "+user.getText()+"\n"+
                            "Email: "+email.getText()+"\n");
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure addUserAccess() in ADD ESCALATION TEAM");
                    CallableStatement addaccess = conn.prepareCall("{call addUserAccess(?,?)}");
                    addaccess.setString(1, user.getText());
                    addaccess.setString(2, "GUEST");
                    if(addaccess.executeUpdate()!=0)
                    {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The engineer "+user.getText()+" was added in ACCESS with success in ADD ESCALATION TEAM!\n\n");
                        int id=ConnectionDatabase.getEscId(user.getText());
                        int idaccess=ConnectionDatabase.getAccesId(user.getText());
                        SavingUpdate("escalation_team",id);
                        SavingUpdate("access",idaccess);
                            
                                //Creating a dialog
                                Dialog<String> dialog = new Dialog<String>();
                                //Setting the title
                                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/ok.png"));
                                        dialog.setTitle("Successs");
                                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                //Setting the content of the dialog
                                dialog.setContentText("Your data has been saved!");
                                //Adding buttons to the dialog pane
                                dialog.getDialogPane().getButtonTypes().add(type);
                                dialog.showAndWait();
                        Stage s = (Stage) savebtn.getScene().getWindow();
                        s.close();
                    }
                    
              }
              else
              {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data  in ADD ESCALATION TEAM: ");
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
              logger.fatal("[FATAL]: ADD ESCALATION TEAM --- SQLException: "+ex+"\n\n");

           }
        }
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from ADD ESCALATION TEAM was pressed!\n\n");
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: ADD ESCALATION TEAM STARTED!");
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
         name_field_esc_team.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>50)
                     {
                         name_field_esc_team.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        name_field_esc_team.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
         
         jobdesc.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>50)
                     {
                         jobdesc.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                       jobdesc.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
         
         
         
         
         
         user
         .textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>7)
                     {
                         user.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        user.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
         
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
