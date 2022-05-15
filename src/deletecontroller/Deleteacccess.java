/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deletecontroller;


import editcontroller.EditaccessController;
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
import tslessonslearneddatabase.Data;
import tslessonslearneddatabase.Log4jConfiguration;
import tslessonslearneddatabase.TSLessonsLearnedDatabase;
/**
 *
 * @author cimpde1
 */
public class Deleteacccess implements Initializable {
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button cancelbtn;

    @FXML
    private ImageView cancelicon;

    @FXML
    private TextField role;

    @FXML
    private Button savebtn;

    @FXML
    private ImageView saveicon;

    @FXML
    private TextField user;

    Connection conn=null;
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(Deleteacccess.class);
    
     @FXML
    void cancelFocus(MouseEvent event) {
        anchorPane.requestFocus();
    }
    
    public void SavingUpdate(String tablename,int id)
    {
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in DELETE ACCESS");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in DELETE ACCESS has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in DELETE ACCESS!");
             }
             
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: DELETE ACCESS --- SQLException: "+ex+"\n\n");
         }
        
    }
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The YES button from DELETE ACCESS was pressed!");
        try {
            conn=ConnectionDatabase.getConnection();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure deleteAccess() in DELETE ACCESS");
            CallableStatement ps = conn.prepareCall("{call deleteAccess(?)}");
            ps.setInt(1, Data.id_access);
            if(ps.executeUpdate()!=0)
                {
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was deleted in DELETE ACCESS: \n"+
                                               "Username: "+user.getText()+"\n"+
                                               "Role: "+role.getText()+"\n\n");
                                        //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/ok.png"));
                                        dialog.setTitle("Success");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Your data has been deleted!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       SavingUpdate("access",Data.id_access);  
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();
                                       
                }
            else
            {
                                       logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while deleting the data in DELETE ACCESS");
                                        //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/error.png"));
                                        dialog.setTitle("Error");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Something went wrong while deleting the data in DELETE ACCESS!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();
            }
            
        } catch (SQLException ex) {
            logger.fatal("[FATAL]: DELETE ACCESS--- SQLException: "+ex+"\n\n");
        }
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: NO button from DELETE ACCESS was pressed!\n\n");
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE ACCESS STARTED");
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        user.setStyle("-fx-opacity: 1");
        role.setStyle("-fx-opacity: 1");
        user.setText(Data.usernameaccess);
        role.setText(Data.roleaccess);
        savebtn.setFocusTraversable(false);
        cancelbtn.setFocusTraversable(false);
        
    }
    
}
