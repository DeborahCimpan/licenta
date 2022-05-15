/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editcontroller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import toolclass.FileChooserTemplate;
import tslessonslearneddatabase.ConnectionDatabase;
import static tslessonslearneddatabase.ConnectionDatabase.logger;
import tslessonslearneddatabase.Data;
import tslessonslearneddatabase.Log4jConfiguration;
import tslessonslearneddatabase.TSLessonsLearnedDatabase;
/**
 *
 * @author cimpde1
 */
public class EditIncidentHistory  implements Initializable{
    
     @FXML
    private TextField startdate;

    @FXML
    private TextField enddate;

    @FXML
    private TextField status;

   
    @FXML
    private TextField user;

    @FXML
    private TextField engineer;

    @FXML
    private TextArea spareparts;

    @FXML
    private TextArea shortsol;

    @FXML
    private TextField sap;

    @FXML
    private TextArea solcreator;

    @FXML
    private TextArea solescal;

     @FXML
    private Button addbtn;

    @FXML
    private Button savebtn;

    @FXML
    private Button cancelbtn;

    @FXML
    private ImageView saveicon;

    @FXML
    private ImageView cancelicon;
    
    
    @FXML
    private ImageView addicon;
    
     @FXML
    private AnchorPane anchorPane;
    String fisier=null;
    Connection conn=null;
    int id_EscTeam=0;
    String part_numbere="";
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(EditIncidentHistory.class);
    
    @FXML
    void cancelFocus(MouseEvent event) 
    {
        anchorPane.requestFocus();
    }
    
    public int compareDates(String d1,String d2)
    {
        int ok=1;
         try {
             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
             Date date1 = sdf.parse(d1);
             Date date2 = sdf.parse(d2);
             if(date1.after(date2) ){
                ok=0;
            }
         } catch (ParseException ex) {
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT NEW PROBLEM: ParseException: "+ex);
         }
         return ok;
    }
     public void SavingUpdate(String tablename,int id)
    {
         conn=ConnectionDatabase.getConnection();
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in EDIT INCIDENT HISTORY");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in EDIT INCIDENT HISTORY has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in EDIT INCIDENT HISTORY!");
             }
             
             
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: EDIT INCIDENT HISTORY --- SQLException: "+ex+"\n\n");
         }
         
        
    }
    
    public void setSpares()
    {
        if(!Data.spare_part_numbers.isEmpty())
        {
            if(spareparts.getText().isEmpty())
            {
                spareparts.setText(Data.spare_part_numbers);
            }
            else
            {
                spareparts.setText(spareparts.getText()+"\n"+Data.spare_part_numbers);
            }
            
            String mystring=spareparts.getText();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The PARRT NUMBERS from EDIT INCIDENT HISTORY: "+spareparts.getText());
            String[] items = mystring.split("\n");
            List<String> itemList = Arrays.asList(items);
            
            List<String> idspares=new ArrayList<>();
            for(String p:itemList)
            {
                int idspare=ConnectionDatabase.getSparePartsId(p);
                idspares.add(String.valueOf(idspare));
            }
            
            String listpartnumbers=idspares.stream().collect(Collectors.joining(";"));
            //lista de idSpareParts 4;4;3
            part_numbere=listpartnumbers;
            
            
        }
        
        
        
    }
    public String convertSpareForDb(String mystring)
    {
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The PARRT NUMBERS from EDIT INCIDENT HISTORY: "+spareparts.getText());
            String[] items = mystring.split("\n");
            List<String> itemList = Arrays.asList(items);
            
            List<String> idspares=new ArrayList<>();
            for(String p:itemList)
            {
                int idspare=ConnectionDatabase.getSparePartsId(p);
                idspares.add(String.valueOf(idspare));
            }
            
            String listpartnumbers=idspares.stream().collect(Collectors.joining(";"));
            //lista de idSpareParts 4;4;3
            part_numbere=listpartnumbers;
            return part_numbere;
    }
    
    
    public void open_spare_parts()
    {
        try {
            Stage primaryStage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/sparepartsadd.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("Add spare parts in text area");
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.setResizable(false);
            primaryStage.setOnHidden(e->{
                Data obiect_data=new Data();
                if(obiect_data.checkSparePartsNull()==false)
                {
                    setSpares();
                }
            });
            
            primaryStage.show();
        } catch (IOException ex) {
            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: EDIT INCIDENT HISTORY -- IOException: "+ex);
        }
    }
    
    
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from EDIT INCIDENT HISTORY was pressed!\n\n");
        Stage stage=(Stage)cancelbtn.getScene().getWindow();
        stage.close();
    }
    public void save()
    {
         logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button from EDIT INCIDENT HISTORY was pressed!");
         String regex="^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]$";
         try {
             conn=ConnectionDatabase.getConnection();
             
             if(startdate.getText().isEmpty()==true || enddate.getText().isEmpty()==true 
                     || user.getText().isEmpty()==true
                     || status.getText().isEmpty()==true || sap.getText().isEmpty()==true)
             {
                  logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are empty in EDIT INCIDENT HISTORY\n\n");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("images/warn.png"));
                dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Please complete all the fields!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
             }
             else if(startdate.getText().matches(regex)==false || enddate.getText().matches(regex)==false || user.getText().length() > 7 || 
                     status.getText().length() > 8 || solcreator.getText().length() > 250 || shortsol.getText().length() > 50)
             {
                  logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are incorrect in EDIT INCIDENT HISTORY\n\n");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("images/error.png"));
                dialog.setTitle("Error");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Incorrect inputs!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
             }
             else if(spareparts.getText().isEmpty()==false)
             {
                    if(spareparts.getText().length() > 450)
                    {
                        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are incorrect in EDIT INCIDENT HISTORY\n\n");
                        //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/error.png"));
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
                        //convert startdate,enddate in miliseconds
                       String st_date=startdate.getText();
                       String en_date=enddate.getText();
                       SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                       Date dates = sdf.parse(st_date);
                       Date datee=sdf.parse(en_date);
                       long sdmili=dates.getTime();
                       long edmili=datee.getTime();
                       String sd=String.valueOf(sdmili);
                       String ed=String.valueOf(edmili);


                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure editIncidentHistory() in EDIT INCIDENT HISTORY");
                       CallableStatement ps = conn.prepareCall("{call editIncidentHistory(?,?,?,?,?,?,?)}");
                       ps.setInt(1,Data.id_incident_history);
                       ps.setString(2,sd);
                       ps.setString(3,ed );
                       ps.setString(4,solcreator.getText());
                       ps.setString(5, solescal.getText());
                       ps.setString(6, convertSpareForDb(spareparts.getText()));
                       ps.setString(7, shortsol.getText());
                       //ps.execute();
                       if(ps.executeUpdate()!=0)
                       {
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was saved in EDIT INCIDENT HISTORY: \n"+
                                               "Start date: "+sd+"\n"+
                                               "End date: "+ed+"\n"+
                                               "Solution creator: "+solcreator.getText()+"\n"+
                                               "Solution escalation: "+solescal.getText()+"\n"+
                                               "Part numbere: "+spareparts.getText()+"\n"+
                                               "Short solution: "+shortsol.getText()+"\n\n");
                                       //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/ok.png"));
                                       dialog.setTitle("Success");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Your data has been saved!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       
                                       SavingUpdate("incident_list_history",Data.id_incident_history);
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();

                       }
                       else
                       {
                                       logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data in EDIT INCIDENT HISTORY");
                                       //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/error.png"));
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

                    }

             }
             else if(spareparts.getText().isEmpty())
             {
                  //convert startdate,enddate in miliseconds
                       String st_date=startdate.getText();
                       String en_date=enddate.getText();
                       SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                       Date dates = sdf.parse(st_date);
                       Date datee=sdf.parse(en_date);
                       long sdmili=dates.getTime();
                       long edmili=datee.getTime();
                       String sd=String.valueOf(sdmili);
                       String ed=String.valueOf(edmili);


                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure editIncidentHistory() in EDIT INCIDENT HISTORY");
                       CallableStatement ps = conn.prepareCall("{call editIncidentHistory(?,?,?,?,?,?,?)}");
                       ps.setInt(1,Data.id_incident_history);
                       ps.setString(2,sd);
                       ps.setString(3,ed );
                       ps.setString(4,solcreator.getText());
                       ps.setString(5, solescal.getText());
                       ps.setString(6, "");
                       ps.setString(7, shortsol.getText());
                       //ps.execute();
                       if(ps.executeUpdate()!=0)
                       {
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was saved in EDIT INCIDENT HISTORY: \n"+
                                               "Start date: "+sd+"\n"+
                                               "End date: "+ed+"\n"+
                                               "Solution creator: "+solcreator.getText()+"\n"+
                                               "Solution escalation: "+solescal.getText()+"\n"+
                                               "Part numbere: "+spareparts.getText()+"\n"+
                                               "Short solution: "+shortsol.getText()+"\n\n");
                                       //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/ok.png"));
                                       dialog.setTitle("Success");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Your data has been saved!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       
                                       SavingUpdate("incident_list_history",Data.id_incident_history);
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();

                       }
                       else
                       {
                                       logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data in EDIT INCIDENT HISTORY");
                                       //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/error.png"));
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
             }
             
             
             
             
         } catch (SQLException ex) {
              logger.fatal("[FATAL]: EDIT INCIDENT HISTORY--- SQLException: "+ex+"\n\n");
         } catch (ParseException ex) {
              logger.fatal("[FATAL]: EDIT INCIDENT HISTORY --- SQLException: "+ex+"\n\n");
         }
         
    
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT INCIDENT HISTORY STARTED");
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        addbtn.setGraphic(addicon);
        
        status.setStyle("-fx-opacity: 1");
        user.setStyle("-fx-opacity: 1");  
        sap.setStyle("-fx-opacity: 1");
        engineer.setStyle("-fx-opacity: 1");
                
        startdate.setText(Data.incident_Start_Date_hour);
        enddate.setText(Data.incident_End_Date_hour);
        status.setText(Data.incident_Status);
        user.setText(Data.incident_Creator_username);
        sap.setText(String.valueOf(Data.incident_SAP_Order_Nr));
        engineer.setText(Data.incident_history_engineer);
        solcreator.setText(Data.incident_Solution_Creator);
        solescal.setText(Data.incident_Solution_Escalation);
        spareparts.setText(Data.incident_history_spare_parts);
        shortsol.setText(Data.incident_short_solution);
        
        startdate.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     String regex="^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]$";
                     if (newValue.toString().matches(regex)) { 
                            
                             startdate.setStyle("-fx-border-width: 0.5px ;");
                             int ok=compareDates(newValue.toString(),enddate.getText());
                             //System.out.println(ok);
                             if(ok==0)
                             {
                                 startdate.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                             }
                             else
                             {
                                 startdate.setStyle("-fx-border-width: 0.5px ;");
                             }
                     }
                     else{
                         
                         startdate.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     
                       
           
                 }
                 
                 
             });
             
             enddate.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                 }
                 else
                 {
                     String regex="^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]$";
                     if (newValue.toString().matches(regex)) { 
                            
                             enddate.setStyle("-fx-border-width: 0.5px ;");
                             int ok=compareDates(startdate.getText(),newValue.toString());
                             //System.out.println(ok);
                             if(ok==0)
                             {
                                 enddate.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                             }
                             else
                             {
                                 enddate.setStyle("-fx-border-width: 0.5px ;");
                             }
                     }
                     else{
                         
                         enddate.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     
                       
           
                 }
                 
                 
             });
             
             
             
             sap.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     String regexd="\\d+";
                     if (newValue.toString().length()>20 || newValue.toString().matches(regexd)==false) { 
                            sap.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                            
                     }
                     else{
                         
                          sap.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                       
           
                 }
                 
                 
             });
            
                
            spareparts.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>450) { 
                            spareparts.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                            
                     }
                     else{
                         
                          spareparts.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                       
           
                 }
                 
                 
             });
            
            solescal.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>250) { 
                            
                         solescal.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");    
                     }
                     else{
                         solescal.setStyle("-fx-border-width: 0.5px ;");
                         
                     }
                     
                       
           
                 }
                 
                 
             });
            status.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>8) { 
                            
                         status.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");    
                     }
                     else{
                         status.setStyle("-fx-border-width: 0.5px ;");
                         
                     }
                     
                       
           
                 }
                 
                 
             });
            user.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>7) { 
                            
                         user.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");    
                     }
                     else{
                         user.setStyle("-fx-border-width: 0.5px ;");
                         
                     }
                     
                       
           
                 }
                 
                 
             });
             engineer.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>50) { 
                            
                         engineer.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");    
                     }
                     else{
                         engineer.setStyle("-fx-border-width: 0.5px ;");
                         
                     }
                     
                       
           
                 }
                 
                 
             });
            
            solcreator.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>250) { 
                            
                         solcreator.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");    
                     }
                     else{
                         solcreator.setStyle("-fx-border-width: 0.5px ;");
                         
                     }
                     
                       
           
                 }
                 
                 
             });
            shortsol.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>50) 
                     { 
                      shortsol.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");                                  
                                                 
                     }
                     else{
                         shortsol.setStyle("-fx-border-width: 0.5px ;");
 
                         
                     }
                     
                       
           
                 }
                 
                 
             });
            
        
    }
    
}
