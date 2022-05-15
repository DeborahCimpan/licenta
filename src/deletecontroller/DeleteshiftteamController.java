/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deletecontroller;


import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class DeleteshiftteamController implements Initializable {

    @FXML
    private TextField namefield;

    @FXML
    private TextField jobfield;
    
    @FXML
    private TextField email;

    @FXML
    private TextField user;

    @FXML
    private Button yesbtn;

    @FXML
    private Button nobtn;
    
     @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView cancelicon;
    
     @FXML
    private ImageView saveicon;
    
     
    Connection conn=null;
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(DeleteshiftteamController.class);
    
    
    @FXML
    void cancelFocus(MouseEvent event) {
        anchorPane.requestFocus();
    }
     
    public void SavingUpdate(String tablename,int id)
    {
         conn=ConnectionDatabase.getConnection();
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in DELETE SHIFT TEAM");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
              if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in DELETE SHIFT TEAM has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in DELETE SHIFT TEAM!");
             }
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: DELETE SHIFT TEAM --- SQLException: "+ex+"\n\n");
         }
         
    }
       
    
    public void yes()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The YES button from DELETE SHIFT TEAM was pressed!");
         try {
           conn=ConnectionDatabase.getConnection();
           
           logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure deleteTechnician() in DELETE SHIFT TEAM");
           CallableStatement ps = conn.prepareCall("{call deleteTechnician(?)}");
           ps.setInt(1, Data.id_sh_team);
           if(ps.executeUpdate()!=0)
           {
                        //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/ok.png"));
                                        dialog.setTitle("Success");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("Your data has been deleted");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                        Stage s = (Stage) yesbtn.getScene().getWindow();
                        s.close();
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: The technician was deleted in DELETE SHIFT TEAM: \n"+
                                               "Name: "+namefield.getText()+"\n"+
                                               "Job description: "+jobfield.getText()+"\n"+
                                               "Username: "+user.getText()+"\n"+
                                               "Email: "+email.getText()+"\n");
             SavingUpdate("shift_team",Data.id_sh_team);
             int idaccess=ConnectionDatabase.getAccesId(user.getText());
             
             conn=ConnectionDatabase.getConnection();
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure deleteAccess() in DELETE SHIFT TEAM");
             CallableStatement dc = conn.prepareCall("{call deleteAccess(?)}");
             dc.setInt(1, idaccess);
                    if(dc.executeUpdate()!=0)
                    {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The user of the technician was deleted from ACCESS in DELETE SHIFT TEAM: \n"+
                        user.getText()+"\n\n");

                       
                        SavingUpdate("access",idaccess);
                        
                  }
                    else
                    {
                         logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while deleting the user "+user.getText()+" from ACCESS or "+
                         user.getText() + " is not in ACCESS in "
                                 + " DELETE SHIFT TEAM");
                    }
          
             
            
             }
             
           else
           {
                                        logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while deleting the technician in DELETE SHIFT TEAM");
                                        //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                      Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/error.png"));
                                        dialog.setTitle("Error");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Something went wrong while deleting the data in DELETE SHIFT TEAM!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       Stage s = (Stage) yesbtn.getScene().getWindow();
                                       s.close();
           }
           
            
           
           } catch (SQLException ex) {
                logger.fatal("[FATAL]: DELETE SHIFT TEAM--- SQLException: "+ex+"\n\n");
           }
         
        
    }
    public void no()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: NO button from DELETE SHIFT TEAM was pressed!\n\n");
        Stage s = (Stage) nobtn.getScene().getWindow();
        s.close();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE SHIFT STARTED");
        
        yesbtn.setGraphic(saveicon);
        nobtn.setGraphic(cancelicon);
        
        namefield.setStyle("-fx-opacity: 1");
        jobfield.setStyle("-fx-opacity: 1");
        user.setStyle("-fx-opacity: 1");
        email.setStyle("-fx-opacity: 1");
                
        namefield.setText(Data.tehncian);
        jobfield.setText(Data.jobdescription);
        user.setText(Data.username);
        email.setText(Data.email);
       jobfield.setFocusTraversable(false);
        email.setFocusTraversable(false);
        user.setFocusTraversable(false);
         yesbtn.setFocusTraversable(false);
         nobtn.setFocusTraversable(false);
        
        
        
        
        
    }    
    
}
