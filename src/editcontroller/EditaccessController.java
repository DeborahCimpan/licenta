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
import java.util.Collections;
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
 *
 * @author cimpde1
 */
public class EditaccessController implements Initializable{
    
    @FXML
    private TextField user;
 
     @FXML
    private ImageView cancelicon;
     
      @FXML
    private ImageView saveicon;


    
     @FXML
    private ComboBox<String> role;
             
     ObservableList<String> items=FXCollections.observableArrayList("ADMIN","GUEST","USER");
     
     
    @FXML
    private Button savebtn;

    @FXML
    private Button cancelbtn;
    Connection conn=null;
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(EditaccessController.class);
    public void SavingUpdate(String tablename,int id)
    {
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in EDIT ACCESS");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in EDIT ACCESS has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in EDIT ACCESS!");
             }
             
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: EDIT ACCESS --- SQLException: "+ex+"\n\n");
         }
        
    }
    public void onRole()
    {
        if(role.getSelectionModel().isEmpty()==false)
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The role selected in EDIT ACCESS: "+role.getValue().toString()+"\n\n");
        }
    }
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button from EDIT ACCESS was pressed!");
        boolean iscombo=role.getSelectionModel().isEmpty();
        try {
            conn=ConnectionDatabase.getConnection();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure updateAccess() in EDIT ACCESS");
            CallableStatement ps = conn.prepareCall("{call updateAccess(?,?,?)}");
            
            if(iscombo==false && !role.getValue().toString().equals("Select...") && user.getText().isEmpty()==false)
            {
                ps.setInt(1, Data.id_access);
                ps.setString(2,user.getText());
                ps.setString(3,role.getValue().toString());
                
                if(ps.executeUpdate()!=0)
                {
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was saved in EDIT ACCESS: \n"+
                                               "Username: "+user.getText()+"\n"+
                                               "Role: "+role.getValue().toString()+"\n\n");
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
                                        SavingUpdate("access",Data.id_access);
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();
                                       
                }
                else
                {
                                       logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data in EDIT ACCESS");
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
               
            }
            else
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are empty in EDIT ACCESS\n\n");
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
            
            
            
            
            
            
            Stage s = (Stage) savebtn.getScene().getWindow();
            s.close();
        } catch (SQLException ex) {
            logger.fatal("[FATAL]: EDIT ACCESS--- SQLException: "+ex+"\n\n");
        }
        
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from EDIT ACCESS was pressed!\n\n");
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT ACCESS STARTED");
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        user.setText(Data.usernameaccess);
        role.setValue("Select...");
        Collections.sort(items);
        items.add(0, "Select...");
        role.setItems(items);
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF ROLES: \n");
        for(int i=0;i<items.size();i++)
        {
            logger.info("["+i+"]: "+items.get(i)+"\n");
        }
        logger.info("\n");
        
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
       
        
    }
    
}
