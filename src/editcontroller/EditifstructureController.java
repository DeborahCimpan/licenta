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
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import tslessonslearneddatabase.ConnectionDatabase;
import tslessonslearneddatabase.Data;
import tslessonslearneddatabase.Log4jConfiguration;
import tslessonslearneddatabase.TSLessonsLearnedDatabase;

/**
 * FXML Controller class
 *
 * @author cimpde1
 */
public class EditifstructureController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView cancelicon;

    @FXML
    private ImageView saveicon;
    @FXML
    private TextField cc;

    
    @FXML
    private Button savebtn;

    @FXML
    private Button cancelbtn;
    
    @FXML
    private TextField desc;
    Connection conn=null;
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(EditifstructureController.class);
    public void SavingUpdate(String tablename,int id)
    {
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             //System.out.println(formatter.format(date));
             //java.sql.Date sql = new java.sql.Date(date.getTime());
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in EDIT IF STRUCTURE");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in EDIT IF STRUCTURE has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in EDIT IF STRUCTURE!");
             }
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: EDIT IF STRUCTURE --- SQLException: "+ex+"\n\n");
         }
        
    }
    
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button from EDIT IF STRUCTURE was pressed!");
        if(desc.getText().isEmpty()==false && cc.getText().isEmpty()==false)
        {
            String regex="\\d+";
            if(cc.getText().length() > 4 || desc.getText().length() > 45 || cc.getText().matches(regex) ==false || desc.getText().matches("\\d+")==false)
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are incorrect in EDIT PROBLEM HISTORY\n\n");
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
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure updateIfStructure() in EDIT IF STRUCTURE");
                            CallableStatement ps = conn.prepareCall("{call updateIfStructure(?,?,?,?)}");
                            ps.setInt(1, Data.id_if_structure);
                            ps.setString(2, "IF "+desc.getText().toString());
                            ps.setString(3, "Internal Factory "+ desc.getText().toString());
                            ps.setInt(4, Integer.parseInt(cc.getText()));
                            //ps.execute();
                            
                            if(ps.executeUpdate()!=0)
                            {
                                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was saved in EDIT IF STRUCTURE: \n"+
                                               "IF: "+desc.getText()+"\n"+
                                               "Cost center: "+cc.getText()+"\n\n");
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
                                        SavingUpdate("if_structure",Data.id_if_structure);
                                        Stage s = (Stage) savebtn.getScene().getWindow();
                                        s.close();
                            }
                            else
                            {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data in EDIT IF STRUCTURE");
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
                     logger.fatal("[FATAL]: EDIT IF STRUCTURE--- SQLException: "+ex+"\n\n");
                 }
            }
        }
        else
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are empty in EDIT IF STRUCTURE\n\n");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Please complete all fields!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
        }
        
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from EDIT IF STRUCTURE was pressed!\n\n");
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT IF STRUCTURE STARTED");
        
        desc.setText(Data.if_name.substring(3));
        cc.setText(String.valueOf(Data.if_cc));
        //savebtn.setFocusTraversable(false);
        //cancelbtn.setFocusTraversable(false);
        //cc.setFocusTraversable(false);
        
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        
        desc.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     String regex="\\d+";
                     if(newValue.toString().length()>45 || newValue.toString().matches(regex)==false)
                     {
                         desc.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        desc.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        cc.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     String regex="\\d+";
                     if(newValue.toString().length()>4 || newValue.toString().matches(regex)==false)
                     {
                         cc.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        cc.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        
    }    
    
}
