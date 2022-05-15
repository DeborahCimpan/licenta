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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
public class AddlineController implements Initializable {

    /**
     * Initializes the controller class.
     */
     public org.apache.logging.log4j.Logger logger= LogManager.getLogger(AddlineController.class);
     
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
    Connection conn=null;
    
    ObservableList<String> itemscombo=FXCollections.observableArrayList();
     public void SavingUpdate(String tablename,int id)
    {
        
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") in ADD LINE STRUCTURE");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update data has been saved in DB from ADD LINE STRUCTURE\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update data couldn't been saved in ADD LINE STRUCTURE");
             }
             
         } catch (SQLException ex) {
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: ADD LINE STRUCTURE --- SQLException: "+ex+"\n\n");
         }
        
    }
    
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button was pressed in ADD LINE STRUCTURE!");
        if(linename.getText().isEmpty() || functionalplace.getText().isEmpty()  || cc.getText().isEmpty() )
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are empty in ADD LINE STRUCTURE\n\n");
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
        else if(linename.getText().length() > 45 || functionalplace.getText().length() > 50  || cc.getText().length() > 4 ||
                cc.getText().matches("\\d+")==false)
        {
               logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are incorrect in ADD LINE STRUCTURE\n\n");
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
        else if(comboif.getSelectionModel().isEmpty()==false && !comboif.getValue().toString().equals("Select...") && linename.getText().isEmpty()==false && 
                functionalplace.getText().isEmpty()==false && cc.getText().isEmpty()==false)
        {
            
        try {
           conn = ConnectionDatabase.connectDb();
           int ifid=ConnectionDatabase.getifid(comboif.getValue().toString());
           
           logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure addLine()");
           
           CallableStatement ps = conn.prepareCall("{call addLine(?, ? , ? ,?)}");
           ps.setInt(1, ifid);
           ps.setString(2, linename.getText());
           ps.setString(3,functionalplace.getText());
           ps.setInt(4, Integer.parseInt(cc.getText()));
           
           
           if(ps.executeUpdate()!=0)
           {
                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The line was added with success in ADD LINE STRUCTURE: \n"+
                                        "IF: "+comboif.getValue().toString()+"\n"+
                                        "Name: "+linename.getText()+"\n"
                                + "Functional place: "+functionalplace.getText()+"\n"+
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
                                
                                    int idline=ConnectionDatabase.getLineId(linename.getText().toString());
                                    SavingUpdate("line_structure",idline);

                                    Stage s = (Stage) savebtn.getScene().getWindow();
                                    s.close();

           }
           else
              {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while adding the line in ADD LINE STRUCTURE!");
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
          logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: ADD LINE STUCTURE----SQLException: "+ex+"\n\n");
           
        }
        }
        else if(comboif.getSelectionModel().isEmpty()==false && comboif.getValue().toString().equals("Select...") && linename.getText().isEmpty()==false && 
               functionalplace.getText().isEmpty()==false && cc.getText().isEmpty()==false)
        {
                                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The IF combobox in ADD LINE STRUCTURE is empty\n\n!");
                                
                                //Creating a dialog
                                Dialog<String> dialog = new Dialog<String>();
                                //Setting the title
                                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                //Setting the content of the dialog
                                dialog.setContentText("Please select an IF");
                                //Adding buttons to the dialog pane
                                dialog.getDialogPane().getButtonTypes().add(type);
                                dialog.showAndWait();
        }
            
    }
    public void cancel()
    {
         logger.info(Log4jConfiguration.currentTime()+"[INFO]: The cancel button was pressed in ADD LINE STRUCTURE!\n\n");
        //Data.comboifback=Data.comboifforline;
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
       
    }
    public void onActionCombo()
    {
        boolean isCombo=comboif.getSelectionModel().isEmpty();
        if(isCombo==false)
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The "+comboif.getValue().toString() + " was selected in ADD LINE STRUCTURE!\n\n");
            Data.comboifback=comboif.getValue().toString();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       logger.info(Log4jConfiguration.currentTime()+"[INFO]: ADD LINE STRUCTURE STARTED!");
       
       comboif.setValue("Select...");
       itemscombo=ConnectionDatabase.getIFCombo();
       if(!itemscombo.isEmpty())
       {
            
            Collections.sort(itemscombo);
            itemscombo.add(0, "Select...");
            comboif.setItems(itemscombo);    
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUPUT LIST OF IF in ADD LINE STRUCTURE: \n");
            for(int i=0;i<itemscombo.size();i++)
            {
                logger.info("["+i+"]: "+itemscombo.get(i)+"\n");
            }
            
       }
       
       logger.info("\n");
       
       savebtn.setGraphic(saveicon);
       cancelbtn.setGraphic(cancelicon);
      
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
