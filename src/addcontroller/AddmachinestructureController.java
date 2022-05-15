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
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class AddmachinestructureController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox comboif;

    @FXML
    private ComboBox comboline;

    @FXML
    private TextField namefield;
    
      @FXML
    private TextField invn;
      
    @FXML
    private Button savebtn;

    @FXML
    private Button cancelbtn;
    
    @FXML
    private ImageView cancelicon;
    
    @FXML
    private ImageView saveicon;

    
    Connection conn=null;
    
    ObservableList<String> itemscombo=FXCollections.observableArrayList();
    
    
    ObservableList<String> itemscomboline=FXCollections.observableArrayList();
    ObservableList<String> itemscomboline1=FXCollections.observableArrayList();
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(AddmachinestructureController.class);
    
     public void SavingUpdate(String tablename,int id)
    {
         conn=ConnectionDatabase.getConnection();
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             logger.info(Log4jConfiguration.currentTime()+ "[INFO]: calling the procedure  saveUpdate("+tablename+") in ADD MACHINE STRUCTURE");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update data has been saved in DB from ADD MACHINE STRUCTURE\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update data couldn't been saved in ADD MACHINE STRUCTURE");
             }
             
         } catch (SQLException ex) {
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: ADD MACHINE STRUCTURE --- SQLException: "+ex+"\n\n");
         }
         
         
    }
    
    
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button was pressed in ADD MACHINE STRUCTURE");
        if( namefield.getText().isEmpty()==true ||  invn.getText().isEmpty()==true || 
                comboline.getSelectionModel().isEmpty()==false && comboline.getValue().toString().equals("Select...") ||
                comboline.getSelectionModel().isEmpty()==true || comboif.getSelectionModel().isEmpty()==true || 
                comboif.getValue().toString().equals("Select..."))
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are empty in ADD MACHINE STRUCTURE\n\n");
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
        
        
        else {
            if( namefield.getText().length() >45  ||  invn.getText().length() > 50)
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are incorrect in ADD MACHINE STRUCTURE\n\n");
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
            conn=ConnectionDatabase.getConnection();
        try {
            int lineid=ConnectionDatabase.getLineId(comboline.getValue().toString());
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure addMachine() in ADD MACHINE STRUCTURE");
            CallableStatement ps = conn.prepareCall("{call addMachine(?,?,?)}");
            ps.setInt(1, lineid);
            
            ps.setString(2, namefield.getText());
            
            ps.setString(3, invn.getText());
            
            if(ps.executeUpdate()!=0)
            {
                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The machine was add with success in ADD MACHINE STRUCTURE: \n"+
                                        "Name: "+namefield.getText()+"\n"+
                                        "Inventory number: "+invn.getText()+"\n\n");
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
                int idmachine=ConnectionDatabase.getMachineId(lineid, namefield.getText());
                SavingUpdate("machine_structure",idmachine);

                Stage s = (Stage) savebtn.getScene().getWindow();
                s.close();
            }
            else
              {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data in ADD MACHINE STRUCTURE");
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
            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: ADD MACHINE STUCTURE----SQLException: "+ex+"\n\n");
        }
        
        }
        }
        
    }
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The cancel button was pressed in ADD MACHINE STRUCTURE\n\n");
        Stage s = (Stage) cancelbtn.getScene().getWindow();
        s.close();
    }
    
    public void actionComboIf()
    {
               Pattern p = Pattern.compile("\\d+$");
                                    Comparator<String> c = new Comparator<String>() {
                                        @Override
                                        public int compare(String object1, String object2) {
                                            Matcher m = p.matcher(object1);
                                            Integer number1 = null;
                                            if (!m.find()) {
                                                return object1.compareTo(object2);
                                            }
                                            else {
                                                Integer number2 = null;
                                                number1 = Integer.parseInt(m.group());
                                                m = p.matcher(object2);
                                                if (!m.find()) {
                                                    return object1.compareTo(object2);
                                                }
                                                else {
                                                    number2 = Integer.parseInt(m.group());
                                                    int comparison = number1.compareTo(number2);
                                                    if (comparison != 0) {
                                                        return comparison;
                                                    }
                                                    else {
                                                        return object1.compareTo(object2);
                                                    }
                                                }
                                            }
                                        }
               };
               boolean isMyComboBoxEmpty = comboif.getSelectionModel().isEmpty();
               if(isMyComboBoxEmpty == false && !comboif.getValue().toString().equals("Select..."))
               {
                   
                   logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+comboif.getValue().toString()+" was selected in ADD MACHINE STRUCTURE");
                   int ifid=ConnectionDatabase.getIfId(comboif.getValue().toString());
                   if(ifid!=0)
                   {
                       
                        itemscomboline=ConnectionDatabase.getLinesfromIf(ifid);
                        if(!itemscomboline.isEmpty())
                        {
                             
                             comboline.setValue("Select...");
                             Collections.sort(itemscomboline);
                             itemscomboline.add(0, "Select...");
                             comboline.setItems(itemscomboline);
                             for(String el: itemscomboline)
                             {
                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF LINES from the IF SELECTED in ADD MACHINE STRUCTURE: \n"+
                                     el+"\n");
                             }
                             logger.info("\n");
                             


                        }
                        else{
                            
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The list of lines from the IF selected is empty!\n\n");
                            comboline.getItems().clear();
                            
                            
                        }
                   }

               }
               else if(isMyComboBoxEmpty == false && comboif.getValue().toString().equals("Select..."))
               {
                   logger.info(Log4jConfiguration.currentTime()+"[INFO]: The 'Select...' option was choose for IF combobox in ADD MACHINE STRUCTURE\n\n");
                   comboline.getItems().clear();
                   comboline.setValue("Select...");
               }
               
    }
    public void actionLine()
    {
        //to do for line
        if(comboline.getSelectionModel().isEmpty()==false && comboline.getValue().toString().equals("Select..."))
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The line selected in ADD MACHINE STRUCTURE: "+comboline.getValue().toString()+"\n\n");
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: ADD MACHINE STRUCTURE STARTED!");
            
            Pattern p = Pattern.compile("\\d+$");
                                    Comparator<String> c = new Comparator<String>() {
                                        @Override
                                        public int compare(String object1, String object2) {
                                            Matcher m = p.matcher(object1);
                                            Integer number1 = null;
                                            if (!m.find()) {
                                                return object1.compareTo(object2);
                                            }
                                            else {
                                                Integer number2 = null;
                                                number1 = Integer.parseInt(m.group());
                                                m = p.matcher(object2);
                                                if (!m.find()) {
                                                    return object1.compareTo(object2);
                                                }
                                                else {
                                                    number2 = Integer.parseInt(m.group());
                                                    int comparison = number1.compareTo(number2);
                                                    if (comparison != 0) {
                                                        return comparison;
                                                    }
                                                    else {
                                                        return object1.compareTo(object2);
                                                    }
                                                }
                                            }
                                        }
            };
            comboif.setValue("Select...");
            comboline.setValue("Select...");
            itemscombo=ConnectionDatabase.getIFCombo();
            if(!itemscombo.isEmpty())
             {
                 Collections.sort(itemscombo,c);
                 itemscombo.add(0, "Select...");
                 comboif.setItems(itemscombo);        
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF IFs in ADD MACHINE STRUCTURE: \n");
                 for(int i=0;i<itemscombo.size();i++)
                 {
                     logger.info("["+i+"]: "+itemscombo.get(i)+"\n");
                 }
                 logger.info("\n");
                 
             }
             else
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: LIST OF IFs is empty \n\n");
                 //comboif.getItems().clear();
                 
             }
            
            savebtn.setGraphic(saveicon);
            cancelbtn.setGraphic(cancelicon);
            
            namefield.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     if(newValue.toString().length()>45)
                     {
                         namefield.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        namefield.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
            
            invn.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else{
                     
                     if(newValue.toString().length()>50)
                     {
                         invn.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else
                     {
                        invn.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                 }
                 
                 
             });
       
        
        
      
        
        
        
    }
    
}
