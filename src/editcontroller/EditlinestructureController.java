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
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
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
 * FXML Controller class
 *
 * @author cimpde1
 */
public class EditlinestructureController implements Initializable {

    /**
     * Initializes the controller class.
     * 
     * 
     */
     @FXML
    private ImageView cancelicon;
     
     @FXML
    private ImageView saveicon;
     
    @FXML
    private ComboBox comboif;

    @FXML
    private TextField linename;

    @FXML
    private TextField functionalplace;

    @FXML
    private TextField cc;

   
    @FXML
    private Button savebtn;

    @FXML
    private Button cancelbtn;
    
    ObservableList<String> itemscomboif= FXCollections.observableArrayList();
    
    Connection conn=null;
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(EditlinestructureController.class);
     public void SavingUpdate(String tablename,int id)
    {
         conn=ConnectionDatabase.getConnection();
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in EDIT LINE STRUCTURE");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in EDIT LINE STRUCTURE has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in EDIT LINE STRUCTURE!");
             }
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: EDIT LINE STRUCTURE --- SQLException: "+ex+"\n\n");
         }
        
        
    }
    public void onIF()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The IF selected from EDIT LINE STRUCTURE: "+comboif.getValue().toString()+"\n\n");
    }
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button from EDIT LINE STRUCTURE was pressed!\n\n");
        
        if(comboif.getSelectionModel().isEmpty()==false && linename.getText().isEmpty()==false && functionalplace.getText().isEmpty()==false && cc.getText().isEmpty()==false
                && !comboif.getValue().toString().equals("Select..."))
        {
            if(linename.getText().length() > 45 || functionalplace.getText().length() > 50 || cc.getText().length() > 4
                || cc.getText().matches("\\d+")==false)
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are incorrect in EDIT LINE STRUCTURE\n\n");
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
                conn = ConnectionDatabase.connectDb();
                int ifid=ConnectionDatabase.getIfId(comboif.getValue().toString());
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure updateLine() in EDIT LINE STRUCTURE");
                CallableStatement ps = conn.prepareCall("{call updateLine(?,?,?,?,?)}");
                ps.setInt(1,Data.id_line_structure);
                ps.setInt(2, ifid);
                ps.setString(3, linename.getText());
                ps.setString(4,functionalplace.getText());
                ps.setInt(5, Integer.parseInt(cc.getText()));
                //ps.execute();
                
                if(ps.executeUpdate()!=0)
                {
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was saved in EDIT LINE STRUCTURE: \n"+
                                               "IF: "+comboif.getValue().toString()+"\n"+
                                               "Name: "+linename.getText()+"\n"+
                                               "Functional place: "+functionalplace.getText()+"\n"+
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
                                        SavingUpdate("line_structure",Data.id_line_structure);
                                        Stage s = (Stage) savebtn.getScene().getWindow();
                                        s.close();
                }
                else
                {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data in EDIT LINE STRUCTURE");
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
               logger.fatal("[FATAL]: EDIT LINE STRUCTURE--- SQLException: "+ex+"\n\n");
            }
            }
        }
        else if(comboif.getSelectionModel().isEmpty()==false && linename.getText().isEmpty()==false && functionalplace.getText().isEmpty()==false && cc.getText().isEmpty()==false
                && comboif.getValue().toString().equals("Select..."))
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The IF is empty in EDIT LINE STRUCTURE\n\n");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
               Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Please select an IF!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
        }
        else if(comboif.getSelectionModel().isEmpty()==true && linename.getText().isEmpty()==false && functionalplace.getText().isEmpty()==false && cc.getText().isEmpty()==false)
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The IF is empty in EDIT LINE STRUCTURE\n\n");
            //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Please select an IF!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
        }
        else if( linename.getText().isEmpty()==true || functionalplace.getText().isEmpty()==true || cc.getText().isEmpty()==true)
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are empty in EDIT LINE STRUCTURE\n\n");
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
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from EDIT LINE STRUCTURE was pressed!\n\n");
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT LINE STRUCTURE STARTED");
        comboif.setValue(Data.if_desc);
        itemscomboif=ConnectionDatabase.getIFCombo();
                    if(!itemscomboif.isEmpty())
                    {
                        Collections.sort(itemscomboif);
                        itemscomboif.add(0, "Select...");
                        comboif.setItems(itemscomboif); 
                        

                    }
                    else
                    {
                        comboif.getItems().clear();
                        comboif.setValue("");
                    }
        linename.setText(Data.line_name);
        functionalplace.setText(Data.line_functional_place);
        cc.setText(String.valueOf(Data.line_cc));
        
        
        linename.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>45)
                     {
                         linename.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        linename.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
        functionalplace.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>50)
                     {
                         functionalplace.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        functionalplace.setStyle("-fx-border-width: 0.5px ;");
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
