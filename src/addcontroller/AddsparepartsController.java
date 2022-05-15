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
public class AddsparepartsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button cancelbtn;

    @FXML
    private ImageView cancelicon;

    @FXML
    private TextArea des_spare;

    @FXML
    private TextArea details_spare;

    @FXML
    private TextField field_part;

    @FXML
    private TextField id_spare;

    @FXML
    private Button savebtn;

    @FXML
    private ImageView saveicon;
    Connection conn=null;
     public org.apache.logging.log4j.Logger logger= LogManager.getLogger(AddsparepartsController.class);
     public void SavingUpdate(String tablename,int id)
    {
         conn=ConnectionDatabase.getConnection();
         org.apache.logging.log4j.Logger logger;
         logger = LogManager.getLogger(AddescalationteamController.class);
         
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in ADD SPARE PARTS");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in ADD SPARE PARTS has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in ADD SPARE PARTS!");
             }
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: ADD SPARE PARTS --- SQLException: "+ex +"\n\n");
         }
        
        
    }
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button from ADD SPARE PARTS was pressed!");
        if(field_part.getText().isEmpty() || id_spare.getText().isEmpty() || des_spare.getText().isEmpty() || details_spare.getText().isEmpty())
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are empty in ADD SPARE PARTS\n\n");
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
            try {
               conn = ConnectionDatabase.connectDb();
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure addSparePart() in ADD SPARE PARTS");
               CallableStatement ps = conn.prepareCall("{call addSparePart(?,?,?,?)}");
               ps.setString(1, field_part.getText());
               ps.setString(2, id_spare.getText());
               ps.setString(3, des_spare.getText());
               ps.setString(4, details_spare.getText());
               //ps.execute();
               if(ps.executeUpdate()!=0)
               {
                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Spare part added with success in ADD SPARE PARTS: \n"+
                                        "Part number: "+field_part.getText()+"\n"+
                                        "Spare ID: "+id_spare.getText()+"\n"+
                                        "Description spare part: "+des_spare.getText()+"\n"+
                                        "Details spare part: "+details_spare.getText()+"\n\n");
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
                                int id=ConnectionDatabase.getSparePartsId(field_part.getText());
                                SavingUpdate("spare_parts",id);
                                Stage s = (Stage) savebtn.getScene().getWindow();
                                s.close();
               }
               else
               {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data  in ADD SPARE PARTS: ");
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
               logger.fatal("[FATAL]: ADD SPARE PARTS --- SQLException: "+ex+"\n\n");

            }
        }
        
       
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from ADD SPARE PARTS was pressed!\n\n");
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: ADD SPARE PARTS STARTED");
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        field_part.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>45)
                     {
                         field_part.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        field_part.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        id_spare.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>45)
                     {
                         id_spare.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        id_spare.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        des_spare.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>100)
                     {
                         des_spare.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        des_spare.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        details_spare.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>200)
                     {
                         details_spare.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        details_spare.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        
    }    
    
}
