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
public class AddifstructureController implements Initializable {

    /**
     * Initializes the controller class.
     */
  
     @FXML
    private ImageView cancelicon;
     
    @FXML
    private ImageView saveicon;

    @FXML
    private TextField field_cc;

    @FXML
    private TextField descfield;
    @FXML
    private Button savebtn;

    @FXML
    private Button cancelbtn;
    
    Connection conn=null;
    
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(AddifstructureController.class);

    public void SavingUpdate(String tablename,int id)
    {
        conn=ConnectionDatabase.getConnection();
        Log4jConfiguration.configureLog4j2();
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             //System.out.println(formatter.format(date));
             //java.sql.Date sql = new java.sql.Date(date.getTime());
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") in ADD IF STRUCTURE");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update data has been saved with success in DB from ADD IF STRUCTURE!\n\n");
             }
             else
             {
                 logger.info(Log4jConfiguration.currentTime()+"[ERROR]: The update data couldn't been saved in DB from ADD IF STRUCTURE: ");
             }
         } catch (SQLException ex) {
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: ADD IF STRUCTURE --- SQLExeption: "+ex+"\n\n");
         }
         
        
    }
    public void save()
    {
         logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button was pressed in ADD IF STRUCTURE");
          if(descfield.getText().isEmpty()==true || field_cc.getText().isEmpty()==true)
          { 
               logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields from ADD IF STRUCTURE are empty\n\n");
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
          else{         
              String regex="\\d+";
              if(descfield.getText().length() > 50 || field_cc.getText().length() > 4 || field_cc.getText().matches(regex) == false ||
                      descfield.getText().matches("\\d+")==false)
              {
                  logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields from ADD IF STRUCTURE are incorrect\n\n");
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
                 conn = ConnectionDatabase.getConnection();
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure addIfStructure() in ADD IF STRUCTURE");
                 CallableStatement ps = conn.prepareCall("{call addIfStructure(?, ? ,?)}");
                 ps.setString(1, "IF "+ descfield.getText());
                 ps.setString(2, "Internal Factory "+ descfield.getText());
                 
                 String cc=field_cc.getText().toString();
                 int callc=Integer.parseInt(cc);
                 
                 ps.setInt(3,callc);
                 
                
                 
                 if(ps.executeUpdate()!=0)
                 { 
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The IF was added with success in ADD IF STRUCTURE: \n"+
                                "IF: "+descfield.getText()+"\n"+
                                "Cost center: "+field_cc.getText()+"\n\n");
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
                        int id=ConnectionDatabase.getIfId("Internal Factory "+descfield.getText());
                        //System.out.println(id);
                        SavingUpdate("if_structure",id);

                        Stage s = (Stage) savebtn.getScene().getWindow();
                        s.close();
                 }
                 else
              {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data in ADD IF STRUCTURE: \n");
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
                  logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: ADD IF STRUCTURE --- SQLExeption: "+ex+"\n\n");

              }
          }
          }
         
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The cancel button was pressed in ADD IF STRUCTURE!\n\n");
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //savebtn.setFocusTraversable(false);
        //cancelbtn.setFocusTraversable(false);
        //field_cc.setFocusTraversable(false);
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: ADD IF STRUCTURE STARTED!");
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        descfield.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     String regex = "\\d+";
                     if(newValue.toString().length()>50 || newValue.toString().matches(regex)==false)
                     {
                         descfield.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        descfield.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        
        field_cc.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     String regex = "\\d+";
                     if(newValue.toString().length()>4 || newValue.toString().matches(regex)==false)
                     {
                         field_cc.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        field_cc.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        
    }    
    
}
