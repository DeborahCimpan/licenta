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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
public class EditshiftteamController implements Initializable {

    @FXML
    private TextField namefield;

     @FXML
    private TextField jobdesc;
      @FXML
    private TextField userfield;

    @FXML
    private TextField emailfield;

    @FXML
    private Button savebtn;

    @FXML
    private Button cancelbtn;
    
      @FXML
    private AnchorPane anchorPane;
      
      @FXML
    private ImageView cancelicon;
      
      @FXML
    private ImageView saveicon;



    Connection conn=null;
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(EditshiftteamController.class);
    
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
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in EDIT SHIFT TEAM");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in EDIT SHIFT TEAM has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in EDIT SHIFT TEAM!");
             }
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: EDIT SHIFT TEAM --- SQLException: "+ex+"\n\n");
         }
        
        
    }
    
    public void save()
    {
         logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button from EDIT SHIFT TEAM was pressed!");
         if(namefield.getText().isEmpty()==true || jobdesc.getText().isEmpty()==true || userfield.getText().isEmpty()==true || emailfield.getText().isEmpty()==true)
         {
             logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are empty in EDIT SHIFT TEAM\n\n");
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
         else if(namefield.getText().length() > 50 || jobdesc.getText().length() > 45 || userfield.getText().length() > 7 || 
                 emailfield.getText().length() > 45 || emailfield.getText().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")==false)
         {
              logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are incorrect in EDIT SHIFT TEAM\n\n");
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
           conn=ConnectionDatabase.getConnection();
           String name=namefield.getText();
           String job=jobdesc.getText();
           logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure updateTechnician() in EDIT SHIFT TEAM");
           CallableStatement ps = conn.prepareCall("{call updateTechnician(?,?,?,?,?)}");
           ps.setString(1, name);
           ps.setString(2, job);
           ps.setString(3, userfield.getText());
           ps.setString(4, emailfield.getText());
           ps.setInt(5, Data.id_sh_team);
           //ps.execute();
           
           
           if(ps.executeUpdate()!=0)
           {
                                       
                                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was saved in EDIT SHIFT TEAM: \n"+
                                               "Name: "+name+"\n"+
                                               "Job description: "+job+"\n"+
                                               "Username: "+userfield.getText()+"\n"+
                                               "Email: "+emailfield.getText()+"\n\n");
                                       
                                       
                                       
                                      
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
                                       SavingUpdate("shift_team",Data.id_sh_team);
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();
           }
           else
           {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data in EDIT SHIFT TEAM\n\n");
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
           logger.fatal("[FATAL]: EDIT SHIFT TEAM--- SQLException: "+ex+"\n\n");
           }
            
         }
        
       
        
        
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from EDIT SHIFT TEAM was pressed!\n\n");
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT SHIFT TEAM STARTED");
        namefield.setText(Data.tehncian);
        jobdesc.setText(Data.jobdescription);
        userfield.setText(Data.username);
        emailfield.setText(Data.email);
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        
        //cancelbtn.setFocusTraversable(false);
        //savebtn.setFocusTraversable(false);
        //jobdesc.setFocusTraversable(false);
        
        //userfield.setFocusTraversable(false);
        
        
        //emailfield.setFocusTraversable(false);
        
        
        namefield.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>50)
                     {
                         namefield.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        namefield.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        
        userfield.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>7)
                     {
                         userfield.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        userfield.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        userfield.setStyle("-fx-opacity: 1");
        jobdesc.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>45)
                     {
                         jobdesc.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        jobdesc.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        emailfield.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
                     if(newValue.toString().length()>45 || newValue.toString().matches(regex)==false)
                     {
                         emailfield.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        emailfield.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        
        
    }    
    
}
