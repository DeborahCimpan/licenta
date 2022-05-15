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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
public class DeletesparepartsController implements Initializable {

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
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(DeletesparepartsController.class);
 public void SavingUpdate(String tablename,int id)
    {
         conn=ConnectionDatabase.getConnection();
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
            
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in DELETE SPARE PARTS");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in DELETE SPARE PARTS has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in DELETE SPARE PARTS!");
             }
             
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: DELETE SPARE PARTS --- SQLException: "+ex+"\n\n");
         }
        
    }
    @FXML
    void cancelFocus(MouseEvent event) {
        anchorPane.requestFocus();
    }
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The YES button from DELETE SPARE PARTS was pressed!");
         try {
           conn=ConnectionDatabase.getConnection();
           logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure deleteSparePart() in DELETE SPARE PARTS");
           CallableStatement ps = conn.prepareCall("{call deleteSparePart(?)}");
           ps.setInt(1, Data.id_spare_parts);
           if(ps.executeUpdate()!=0)
           {
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was deleted in DELETE SPARE PARTS: \n"+
                                                 "Part number: "+fieldpartnr.getText()+"\n"+
                                        "Spare ID: "+fieldspareid.getText()+"\n"+
                                        "Description spare part: "+fielddesc.getText()+"\n"+
                                        "Details spare part: "+fielddetails.getText() + "\n\n");
               //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       dialog.setTitle("Success message");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Your data has been deleted!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
               SavingUpdate("spare_parts",Data.id_spare_parts);
           
               Stage s = (Stage) savebtn.getScene().getWindow();
               s.close();
           }
           else
           {
                                       logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while deleting the data in DELETE SPARE PARTS");
                                        //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       dialog.setTitle("Error message");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Something went wrong while deleting the data in DELETE SPARE PARTS!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();
           }
           
           
           } catch (SQLException ex) {
           logger.fatal("[FATAL]: DELETE SPARE PARTS--- SQLException: "+ex+"\n\n");
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
        // TODO
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE SPARE PARTS STARTED");
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        //fieldpartnr.setStyle("-fx-opacity: 1");
        //fieldspareid.setStyle("-fx-opacity: 1");
        //fielddesc.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
        //fielddetails.setStyle("-fx-focus-color: transparent; -fx-background-insets: -1.4, 0, 1, 2;");
        
        fieldpartnr.setText(Data.part_number);
        fieldspareid.setText(Data.spare_id);
        fielddesc.setText(Data.desc_spare_parts);
        fielddetails.setText(Data.det_spare_parts);
        
        savebtn.setFocusTraversable(false);
        cancelbtn.setFocusTraversable(false);
    }    
    
}
