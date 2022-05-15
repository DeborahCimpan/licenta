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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
public class AddshiftteamController implements Initializable {

    @FXML
    private TextField technamefield;

    @FXML
    private TextField usernamefield;

    @FXML
    private TextField emailfield;
    
    @FXML
    private TextField jobdesc;
    
    @FXML
    private Button add_btn;

    @FXML
    private Button cancel_btn;
    
     @FXML
    private ImageView saveicon;
    
     @FXML
    private ImageView cancelicon;
   
    Connection conn=null;
    
 public org.apache.logging.log4j.Logger logger= LogManager.getLogger(AddshiftteamController.class);
    
    public void toManyChars()
    {
        
    }
    public void SavingUpdate(String tablename,int id)
    {
        conn=ConnectionDatabase.getConnection();
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             logger.info(Log4jConfiguration.currentTime()+ "[INFO]: calling the procedure  saveUpdate("+tablename+")) in ADD SHIFT TEAM");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update data has been saved in DB from ADD SHIFT TEAM\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update data couldn't been saved in ADD SHIFT TEAM");
             }
             
         } catch (SQLException ex) {
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: ADD SHIFT TEAM --- SQLException: "+ex+"\n\n");
         }
        
        
    }
    public void Save()
    {
           logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button was pressed in ADD SHIFT TEAM");
           if(technamefield.getText().isEmpty() || jobdesc.getText().isEmpty() || usernamefield.getText().isEmpty() || emailfield.getText().isEmpty())
           {
               logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields from ADD SHIFT TEAM are empty!\n\n");
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
           else if(technamefield.getText().length() > 50 || jobdesc.getText().length() > 45 || usernamefield.getText().length() > 7 || 
                   emailfield.getText().length() > 45 || emailfield.getText().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")==false)
           {
               logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields from ADD SHIFT TEAM are incorrect!\n\n");
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
                conn = ConnectionDatabase.connectDb();
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling insertTechnician() in ADD SHIFT TEAM");
                CallableStatement ps = conn.prepareCall("{call insertTechnician(?,?,?,?)}");
                ps.setString(1, technamefield.getText());
                ps.setString(2, jobdesc.getText());
                ps.setString(3,usernamefield.getText().toString());
                ps.setString(4, emailfield.getText());
                //ps.execute();
                if(ps.executeUpdate()!=0)
                {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The technician was added with success in ADD SHIFT TEAM: \n"+
                            "Nume: "+technamefield.getText()+"\n"+
                            "Job description: "+jobdesc.getText()+"\n"+
                            "Username: "+usernamefield.getText()+"\n"+
                            "Email: "+emailfield.getText()+"\n");
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling addUserAccess() in ADD SHIFT TEAM");
                    CallableStatement addaccess = conn.prepareCall("{call addUserAccess(?,?)}");
                    addaccess.setString(1, usernamefield.getText());
                    addaccess.setString(2, "GUEST");
                    if(addaccess.executeUpdate()!=0)
                    {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The engineer "+usernamefield.getText()+" was added in ACCESS with success in ADD SHIFT TEAM!\n\n");
                        int id=ConnectionDatabase.getShiftId(usernamefield.getText());
                                //Creating a dialog
                                Dialog<String> dialog = new Dialog<String>();
                                //Setting the title
                                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/ok.png"));
                                        dialog.setTitle("Success");
                                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                //Setting the content of the dialog
                                dialog.setContentText("Your data has been saved");
                                //Adding buttons to the dialog pane
                                dialog.getDialogPane().getButtonTypes().add(type);
                                dialog.showAndWait();
                        SavingUpdate("shift_team",id);
                        Stage s = (Stage) add_btn.getScene().getWindow();
                        s.close();
                    }
                }
                else
                {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data  in ADD SHIFT TEAM: ");
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
                                Stage s = (Stage) add_btn.getScene().getWindow();
                                s.close();
                }

                
               

               

                } catch (SQLException ex) {
                logger.fatal("[FATAL]: ADD SHIFT TEAM --- SQLException: "+ex+"\n\n");

             }
                
           }
           
           
           
           
        
    }
    public void Cancel()
    {
       logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from ADD SHIFT TEAM was pressed!\n\n");
        Stage s = (Stage) cancel_btn.getScene().getWindow();
        s.close();
         
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         
         logger.info(Log4jConfiguration.currentTime()+"[INFO]: ADD SHIFT TEAM STARTED!");
         add_btn.setGraphic(saveicon);
         cancel_btn.setGraphic(cancelicon);
         //cancel_btn.setFocusTraversable(false);
        // add_btn.setFocusTraversable(false);
         // usernamefield.setFocusTraversable(false);
         
          //emailfield.setFocusTraversable(false);
         
         //jobdesc.setFocusTraversable(false);
         
         technamefield.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>50)
                     {
                         technamefield.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        technamefield.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
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
         
         usernamefield.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>7)
                     {
                         usernamefield.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        usernamefield.setStyle("-fx-border-width: 0.5px ;");
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
