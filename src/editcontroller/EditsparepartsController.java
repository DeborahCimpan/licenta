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
import javafx.scene.control.TextArea;
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
public class EditsparepartsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private Button cancelbtn;

    @FXML
    private ImageView cancelicon;

    @FXML
    private TextArea fielddesc;

    @FXML
    private TextArea fielddetails;

    @FXML
    private TextField fieldpartnr;

    @FXML
    private TextField fieldspareid;

    @FXML
    private Button savebtn;

    @FXML
    private ImageView saveicon;
    
    Connection conn=null;
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(EditsparepartsController.class);
    
     public void SavingUpdate(String tablename,int id)
    {
         conn=ConnectionDatabase.getConnection();
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in EDIT SPARE PARTS");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in EDIT SPARE PARTS has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in EDIT SPARE PARTS!");
             }
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: EDIT SPARE PARTS --- SQLException: "+ex+"\n\n");
         }
         
        
    }
    
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button from EDIT SPARE PARTS was pressed!");
        if(fieldpartnr.getText().isEmpty()==true || fieldspareid.getText().isEmpty()==true || fielddesc.getText().isEmpty()==true || fielddetails.getText().isEmpty()==true)
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are empty in EDIT SPARE PARTS\n\n");
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
        else
        {
             try {
                            conn=ConnectionDatabase.getConnection();
                            String part_number=fieldpartnr.getText();
                            String spare_id=fieldspareid.getText();
                            String description=fielddesc.getText();
                            String details=fielddetails.getText();
                            
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure updateSparePart() in EDIT SPARE PARTS");
                            CallableStatement ps = conn.prepareCall("{call updateSparePart(?,?, ?, ? ,?)}");
                            ps.setInt(1, Data.id_spare_parts);
                            ps.setString(2, part_number);
                            ps.setString(3, spare_id);
                            ps.setString(4, description);
                            ps.setString(5, details);
                            //ps.execute();
                            
                            if(ps.executeUpdate()!=0)
                            {
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was saved in EDIT SPARE PARTS: \n"+
                                               "Part number: "+fieldpartnr.getText()+"\n"+
                                        "Spare ID: "+fieldspareid.getText()+"\n"+
                                        "Description spare part: "+fielddesc.getText()+"\n"+
                                        "Details spare part: "+fielddetails.getText()+"\n\n");
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
                                       SavingUpdate("spare_parts",Data.id_spare_parts);
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();
                            }
                            else
                            {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data in EDIT SPARE PARTS");
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
                     logger.fatal("[FATAL]: EDIT SPARE PARTS--- SQLException: "+ex+"\n\n");
                 }
               
        }
        
        
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from EDIT SPARE PARTS was pressed!\n\n");
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT SPARE PARTS STARTED");
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        fieldpartnr.setText(Data.part_number);
        fieldspareid.setText(Data.spare_id);
        fielddesc.setText(Data.desc_spare_parts);
        fielddetails.setText(Data.det_spare_parts);
        
        fieldpartnr.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>45)
                     {
                         fieldpartnr.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        fieldpartnr.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        fieldspareid.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>45)
                     {
                         fieldspareid.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        fieldspareid.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        fielddesc.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>100)
                     {
                         fielddesc.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        fielddesc.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        fielddetails.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>200)
                     {
                         fielddetails.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        fielddetails.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        
        
        
        
        
        
    }    
    
}
