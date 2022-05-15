/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deletecontroller;


import editcontroller.EditescalationteamController;
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
 * 
 */
public class DeleteescalationteamController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button cancelbtn;

    @FXML
    private ImageView cancelicon;

    @FXML
    private TextField email;

    @FXML
    private TextField job;

    @FXML
    private TextField name;

    @FXML
    private Button savebtn;

    @FXML
    private ImageView saveicon;

    @FXML
    private TextField user;

    Connection conn=null;
    
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(DeleteescalationteamController.class);
    
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
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in DELETE ESCALATION TEAM");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
              if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in DELETE ESCALATION TEAM has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in DELETE ESCALATION TEAM!");
             }
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: DELETE ESCALATION TEAM --- SQLException: "+ex+"\n\n");
         }
         
    }
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The YES button from DELETE ESCALATION TEAM was pressed!");
        
        try {
             conn=ConnectionDatabase.getConnection();
             
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure deleteEngineer() in DELETE ESCALATION TEAM");
             CallableStatement ps = conn.prepareCall("{call deleteEngineer(?)}");
             ps.setInt(1, Data.id_esc_team);
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
                    SavingUpdate("escalation_team",Data.id_esc_team);
                    Stage s = (Stage) savebtn.getScene().getWindow();
                    s.close();
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The engineer was deleted in DELETE ESCALATION TEAM: \n"+
                                               "Name: "+name.getText()+"\n"+
                                               "Job description: "+job.getText()+"\n"+
                                               "Username: "+user.getText()+"\n"+
                                               "Email: "+email.getText()+"\n");
                 
                int idaccess=ConnectionDatabase.getAccesId(user.getText());
                
                conn=ConnectionDatabase.getConnection();
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure deleteAccess() in DELETE ESCALATION TEAM");
                CallableStatement dc = conn.prepareCall("{call deleteAccess(?)}");
                dc.setInt(1, idaccess);
                if(dc.executeUpdate()!=0)
                {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The user of the engineer was deleted from ACCESS in DELETE ESCALATION TEAM: \n"+
                            user.getText()+"\n\n");
                    
                    SavingUpdate("access",idaccess);
                    
                }
                else
                {
                    logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while deleting the user "+user.getText()+" from ACCESS or there is no "
                            + " username " + user.getText()
                            + " in ACCESS in "
                            + " DELETE ESCALATION TEAM ");
                }
                
                
                 
                                        
             }
             else
             {
                                       logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while deleting the engineer in DELETE ESCALATION TEAM");
                                        //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/error.png"));
                                        dialog.setTitle("Error");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Something went wrong while deleting the data in DELETE ESCALATION TEAM!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();
             }
             
             
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: DELETE ESCALATION TEAM--- SQLException: "+ex+"\n\n");
         }
        
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: NO button from DELETE ESCALATION TEAM was pressed!\n\n");
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE ESCALATION TEAM STARTED");
        name.setStyle("-fx-opacity: 1");
        job.setStyle("-fx-opacity: 1");
        user.setStyle("-fx-opacity: 1");
        email.setStyle("-fx-opacity: 1");
                
        name.setText(Data.engineer_name);
        job.setText(Data.engineer_job_desc);
        user.setText(Data.engineer_user);
        email.setText(Data.engineer_email);
        savebtn.setFocusTraversable(false);
        cancelbtn.setFocusTraversable(false);
        name.setFocusTraversable(false);
        job.setFocusTraversable(false);
        user.setFocusTraversable(false);
        email.setFocusTraversable(false);
        
        
       
    }    
    
}
