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
 */
public class DeletemachinestructureController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField ifname;

    @FXML
    private TextField linename;

    @FXML
    private TextField name;

   
    @FXML
    private TextField invn;

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
     
     @FXML
    void cancelFocus(MouseEvent event) {
        anchorPane.requestFocus();
    }


    Connection conn=null;
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(DeletemachinestructureController.class);
    public void SavingUpdate(String tablename,int id)
    {
        conn=ConnectionDatabase.getConnection();
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             //System.out.println(formatter.format(date));
             //java.sql.Date sql = new java.sql.Date(date.getTime());
             
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in DELETE MACHINE STRUCTURE");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
            if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in DELETE MACHINE STRUCTURE has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in DELETE MACHINE STRUCTURE!");
             }
             
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: DELETE MACHINE STRUCTURE --- SQLException: "+ex+"\n\n");
         }
        
        
    }
    
    public void save()
    {
         logger.info(Log4jConfiguration.currentTime()+"[INFO]: The YES button from DELETE MACHINE STRUCTURE was pressed!");
         try {
           conn = ConnectionDatabase.connectDb();
           
           logger.info(Log4jConfiguration.currentTime()+"[INFO]: The OUPTLIST OF LINES ID from the IF in DELETE MACHINE STRUCTURE: \n");
           CallableStatement ps = conn.prepareCall("{call deleteMachine(?)}");
           
           ps.setInt(1, Data.id_machine_structure);
           if(ps.executeUpdate()!=0)
                {
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was deleted in DELETE MACHINE STRUCTURE: \n"+
                                              "IF: "+ifname.getText()+"\n"+
                                               "Line: "+linename.getText()+"\n"+
                                               "Name: "+name.getText()+"\n"+
                                               "Inventory number: "+invn.getText()+"\n\n");
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
                                       SavingUpdate("machine_structure",Data.id_machine_structure);
                                        Stage s = (Stage) savebtn.getScene().getWindow();
                                        s.close();
                                       
                }
            else
            {
                                       logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while deleting the data in DELETE MACHINE STRUCTURE");
                                        //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/error.png"));
                                        dialog.setTitle("Error");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Something went wrong while deleting the data!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();
            }
           
           
           
           } catch (SQLException ex) {
          logger.fatal("[FATAL]: DELETE MACHINE STRUCTURE--- SQLException: "+ex+"\n\n");
           
        }
        
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: NO button from DELETE MACHINE STRUCTURE was pressed!\n\n");
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
    }
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE MACHINE STRUCTURE STARTED");
        ifname.setStyle("-fx-opacity: 1");
        linename.setStyle("-fx-opacity: 1");
        name  .setStyle("-fx-opacity: 1");
        
        invn.setStyle("-fx-opacity: 1");
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        String line=ConnectionDatabase.getLinenamefromMachine(Data.id_machine_structure);
        String IF=ConnectionDatabase.getIffromLine(line);
        ifname.setText(IF);
        linename.setText(line);
        name.setText(Data.machine_name);
        
        invn.setText(Data.machine_invn);
        
        savebtn.setFocusTraversable(false);
        cancelbtn.setFocusTraversable(false);
        
    }    
    
}
