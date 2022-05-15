
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import toolclass.AutoCompleteComboBoxListener;
import toolclass.Incidents;
import toolclass.IncidentsDetails;
import toolclass.Users;
import tslessonslearneddatabase.ConnectionDatabase;
import tslessonslearneddatabase.Data;
import tslessonslearneddatabase.Log4jConfiguration;
import tslessonslearneddatabase.TSLessonsLearnedDatabase;

/**
 *
 * @author cimpde1
 */
public class IncidentDetails implements Initializable {
    
     @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Button cancelbtn;
    
    @FXML
    private ScrollPane scrollPane;
    
    private Stage openspareadd;
    
    @FXML
    private TextField linetextfield;
    
    @FXML
    private TextField iftextfield;

    @FXML
    private Text part_number_text;
    
     @FXML
    private Text sap_text;

    @FXML
    private ComboBox<String> escal_combo;

    @FXML
    private ComboBox<String> escal_to_combo;

    @FXML
    private Text escal_text;

    @FXML
    private Text escal_to_text;

    @FXML
    private Text hmi_text;
    
     @FXML
    private Text pb_desc_text;
     
     @FXML
    private Text esc_name_text;

    @FXML
    private TextField machinetextfield;

    @FXML
    private TextField escalation_user_textfield;
    
    @FXML
    private Button btnnewfromtemplate;
    
    @FXML
    private Button downloadbtn;
    
    @FXML
    private Button leftarrow;

    @FXML
    private Button rightarrow;

    @FXML
    private ImageView foldericon;

    @FXML
    private ImageView lefticon;

    @FXML
    private ImageView righticon;
    
    @FXML
    private TextField startdatefield;

    @FXML
    private TextField user;

    @FXML
    private TextField statusfield;

    @FXML
    private TextField enddatefield;

    @FXML
    private TextField sapfield;
   
       @FXML
    private TextArea problem_desc;
   
    @FXML
    private TextField hmi;
    
    @FXML
    private TextArea sol_creator;
    
    @FXML
    private TextArea spareparts;

    @FXML
    private TextArea sol_esc;
    
    @FXML
    private Text sol_escal_text;
    
    @FXML
    private Text sol_creator_text;
    
    @FXML
    private Button addbtnspare;

    @FXML
    private ImageView addicon;
    
    
    @FXML
    private Button savebtn;
    
    int id_EscTeam = -1;
    int idIncident=-1;
    
    Connection conn=null;
    
    @FXML
    private ImageView saveicon;
    ObservableList<String> itemscomboescalstatus=FXCollections.observableArrayList("Select...","yes","no");
    ObservableList<String> itemscomboescal=FXCollections.observableArrayList();
    
    ObservableList<IncidentsDetails> incident_details=FXCollections.observableArrayList();
    String part_numbere=null;
    
    //luam id-ul actual
    public int id_lista_incident_selectat;
    ObservableList<Incidents> incident_list=FXCollections.observableArrayList();
    
    //the archive of files for the incident
    
    
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(IncidentDetailsController.class);
    
   public String fileIncident=null;
   
   private Stage new_inc_from_template;
   
   public void cancelFocus()
   {
       anchorPane.requestFocus();
   }
    
    public void newincfromtemplate()
    {
             if(new_inc_from_template==null)
             {
                          try {
                          new_inc_from_template=new Stage();
                          Parent root = FXMLLoader.load(getClass().getResource("/fxml/newincfromtemplate.fxml"));
                          Scene scene = new Scene(root);

                          new_inc_from_template.setTitle("New incident from template");
                          new_inc_from_template.setScene(scene);
                          new_inc_from_template.initStyle(StageStyle.UTILITY);
                          new_inc_from_template.setResizable(false);
                          new_inc_from_template.show();
                          
                          Stage s = (Stage) btnnewfromtemplate.getScene().getWindow();
                          
                          new_inc_from_template.setOnCloseRequest(e->
                          {
                              s.show();
                          });
                          

                      } catch (IOException ex) {
                          logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: INCIDENT DETAILS---IOException: "+ex);
                      }
             }
             else if(new_inc_from_template.isShowing())
             {
                 //incident_details.toFront();
                        //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'New incident from template' with unsaved changes is already open in the background! "
                                     + "Close it if"
                                     + " you want to ignore it and open a new panel for creating an incident.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
             }
             else
             {
                        try {
                   new_inc_from_template=new Stage();
                   Parent root = FXMLLoader.load(getClass().getResource("/fxml/newincfromtemplate.fxml"));
                   Scene scene = new Scene(root);

                   new_inc_from_template.setTitle("New incident from template");
                   new_inc_from_template.setScene(scene);
                   new_inc_from_template.initStyle(StageStyle.UTILITY);
                   new_inc_from_template.setResizable(false);
                   new_inc_from_template.show();

               } catch (IOException ex) {
                   logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: INCIDENT DETAILS---IOException: "+ex);
               }
             }
        
    }
    //-------------------------------------------------------------left arrow--------------------------------------------------------------------------
    public void actionLeftArrow()
    {
        setForGuest();
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: LEFT ARROW was pressed in INCIDENT DETAILS\n\n");
        //luam id-ul dinainte
        id_lista_incident_selectat=id_lista_incident_selectat-1;
        //luam lista
        incident_list=Data.incidents;
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The "+ id_lista_incident_selectat+ " incident was selected in INCIDENT DETAILS");

        if(id_lista_incident_selectat>=0)
        {
            
            int id_incident_history=incident_list.get(id_lista_incident_selectat).getId();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The ID from DB of the incident selected in INCIDENT DETAILS: "+id_incident_history+"\n\n");
            
            incident_details=ConnectionDatabase.getIncidentDetails(id_incident_history);
            if(incident_details.isEmpty()==true)
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list for incident details is empty in INCIDENT DETAILS");
            }
            else
            {
                IncidentsDetails element=incident_details.get(0);
                setDetailsInFields(element.getIF(),element.getLine(),element.getMachine(),element.getEscal_user(),element.getUser(),
                element.getHmi(), element.getDesc(),element.getSol_creator());
            }
            
            if(incident_list.isEmpty()==true)
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of incidents from INCIDENTS OVERVIEW is empty in INCIDENT DETAILS!");
            }
            else
            {
                Incidents incident_previous=incident_list.get(id_lista_incident_selectat);
                //set from overview
                setFromOverviewInField(incident_previous.getStart_date(),incident_previous.getEnd_date(),incident_previous.getStatus(),
                        String.valueOf(incident_previous.getSap()));
                
                if(incident_previous.getStatus().equals("unsolved"))
                {

                    setFieldsForUnsolved();
                    refreshForUnsolved();
                    setForGuest();

                }
                else if(incident_previous.getStatus().equals("solved"))
                {
                    setFieldsForSolved();
                    setForGuest();
                }
                
                //get data for creating template
                 //create the template
                transferDatatoTemplate( incident_previous.getShort_sol());
                
            }
            String filePath=ConnectionDatabase.getFilePathfromIncident(id_incident_history);
            if(filePath.equals("")==false)
            {
                fileIncident=filePath;
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Incident's file in INCIDENT DETAILS is: "+fileIncident);
            }
            else
            {
                fileIncident="";
            }
            
            
            //spare parts
            setSparePartsField(id_incident_history);
            
        }
        else if(id_lista_incident_selectat < 0)
        {
            
            id_lista_incident_selectat=Data.last_index;
            int id_incident_history=incident_list.get(id_lista_incident_selectat).getId();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The ID from DB of the incident selected in INCIDENT DETAILS: "+id_incident_history+"\n\n");
            incident_details=ConnectionDatabase.getIncidentDetails(id_incident_history);
            
            if(incident_details.isEmpty()==true)
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list for incident details is empty in INCIDENT DETAILS");
            }
            else
            {
                
                IncidentsDetails element=incident_details.get(0);
                setDetailsInFields(element.getIF(),element.getLine(),element.getMachine(),element.getEscal_user(),element.getUser(),
                element.getHmi(), element.getDesc(),element.getSol_creator());
            }
            
            
            if(incident_list.isEmpty()==true)
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of incidents from INCIDENTS OVERVIEW is empty in INCIDENT DETAILS!");
            }
            else
            {
                Incidents incident_previous=incident_list.get(id_lista_incident_selectat);
                //set from overview
                setFromOverviewInField(incident_previous.getStart_date(),incident_previous.getEnd_date(),incident_previous.getStatus(),
                String.valueOf(incident_previous.getSap()));
                
                if(incident_previous.getStatus().equals("unsolved"))
                {

                    setFieldsForUnsolved();
                    refreshForUnsolved();
                    setForGuest();

                }
                else if(incident_previous.getStatus().equals("solved"))
                {
                    setFieldsForSolved();
                    setForGuest();
                }

                transferDatatoTemplate( incident_previous.getShort_sol());
            }
            String filePath=ConnectionDatabase.getFilePathfromIncident(id_incident_history);
            if(filePath.equals("")==false)
            {
                fileIncident=filePath;
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Incident's file in INCIDENT DETAILS is: "+fileIncident);
            }
            else
            {
                fileIncident="";
            }
            //spare parts
            setSparePartsField(id_incident_history);
        }
        //---------------------------logging---------------------------------------------------------------------------------------------------------
        String msg="IF: "+iftextfield.getText()+"\n"+
                      "Line: "+linetextfield.getText()+"\n"+
                     "Machine: "+machinetextfield.getText()+"\n"+
                    "Start date: "+startdatefield.getText()+"\n"+
                    "End date: "+enddatefield.getText()+"\n"+
                    "Status: "+statusfield.getText()+"\n"+
                    "Creator user: "+user.getText()+"\n"+
                    "SAP Order: "+sapfield.getText()+"\n"+
                    "Escalation User: "+escalation_user_textfield.getText()+"\n"+
                    "HMI Error: "+hmi.getText()+"\n"+
                    "Problem description: "+problem_desc.getText()+"\n"+
                    "Part number spare parts: "+spareparts.getText()+"\n"+
                    "Solution creator: "+sol_creator.getText()+"\n"+
                    "Solution escalation: "+sol_esc.getText()+"\n"+
                    "Incident's file: "+fileIncident+"\n\n";
                    
           logger.info(Log4jConfiguration.currentTime() + "[INFO]: These are details of the incident when the LEFT arrow was pressed in INCIDENT DETAILS: \n"+msg);
        
    }
    //-----------------------------------------------------------------right arrow-----------------------------------------------------------------------------------------
    public void actionRightArrow()
    {
        
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: RIGHT ARROW was pressed in INCIDENT DETAILS\n\n");
   
         //luam id-ul de dupa
        id_lista_incident_selectat=id_lista_incident_selectat+1;
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The "+ id_lista_incident_selectat+ " incident was selected in INCIDENT DETAILS");
        //luam lista
        incident_list=Data.incidents;
        
        if(id_lista_incident_selectat<=incident_list.size()-1 )
        {
           
            int id_incident_history=incident_list.get(id_lista_incident_selectat).getId();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The ID from DB of the incident selected in INCIDENT DETAILS: "+id_incident_history+"\n\n");
            
            incident_details=ConnectionDatabase.getIncidentDetails(id_incident_history);
            if(incident_details.isEmpty()==true)
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list for incident details is empty in INCIDENT DETAILS");
            }
            else
            {
                IncidentsDetails element=incident_details.get(0);
                setDetailsInFields(element.getIF(),element.getLine(),element.getMachine(),element.getEscal_user(),element.getUser(),
                element.getHmi(), element.getDesc(),element.getSol_creator());
                
            }
            
            
            if(incident_list.isEmpty()==true)
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of incidents from INCIDENTS OVERVIEW is empty in INCIDENT DETAILS!");
            }
            else
            {
                Incidents incident_next=incident_list.get(id_lista_incident_selectat);
                setFromOverviewInField(incident_next.getStart_date(),incident_next.getEnd_date(),incident_next.getStatus(),
                String.valueOf(incident_next.getSap()));
                
                if(incident_next.getStatus().equals("unsolved"))
                {

                    setFieldsForUnsolved();
                    refreshForUnsolved();
                    setForGuest();

                }
                else if(incident_next.getStatus().equals("solved"))
                {
                    setFieldsForSolved();
                    setForGuest();
                }
                transferDatatoTemplate( incident_next.getShort_sol());
            }
            String filePath=ConnectionDatabase.getFilePathfromIncident(id_incident_history);
            if(filePath.equals("")==false)
            {
                fileIncident=filePath;
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Incident's file in INCIDENT DETAILS is: "+fileIncident);
            }
            else
            {
                fileIncident="";
            }
            
           //spare parts
           setSparePartsField(id_incident_history);
        }
        else if(id_lista_incident_selectat > incident_list.size()-1 )
        {
            
            id_lista_incident_selectat=0;
            
            int id_incident_history=incident_list.get(id_lista_incident_selectat).getId();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The ID from DB of the incident selected in INCIDENT DETAILS: "+id_incident_history+"\n\n");
            
            incident_details=ConnectionDatabase.getIncidentDetails(id_incident_history);
            
            if(incident_details.isEmpty()==true)
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list for incident details is empty in INCIDENT DETAILS");
            }
            else
            {
                IncidentsDetails element=incident_details.get(0);
                setDetailsInFields(element.getIF(),element.getLine(),element.getMachine(),element.getEscal_user(),element.getUser(),
                element.getHmi(), element.getDesc(),element.getSol_creator());
            }
            
            
            
            if(incident_list.isEmpty()==true)
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of incidents from INCIDENTS OVERVIEW is empty in INCIDENT DETAILS!");
            }
            else
            {
                
                    Incidents incident_next=incident_list.get(id_lista_incident_selectat);
                    setFromOverviewInField(incident_next.getStart_date(),incident_next.getEnd_date(),incident_next.getStatus(),
                    String.valueOf(incident_next.getSap()));
                    
                    if(incident_next.getStatus().equals("unsolved"))
                    {

                        setFieldsForUnsolved();
                        refreshForUnsolved();
                        setForGuest();

                    }
                    else if(incident_next.getStatus().equals("solved"))
                    {
                        setFieldsForSolved();
                        setForGuest();
                    }

                    transferDatatoTemplate( incident_next.getShort_sol());
            }
            String filePath=ConnectionDatabase.getFilePathfromIncident(id_incident_history);
            if(filePath.equals("")==false)
            {
                fileIncident=filePath;
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Incident's file in INCIDENT DETAILS is: "+fileIncident);
            }
            else
            {
                fileIncident="";
            }
            
            //spare parts
            setSparePartsField(id_incident_history);
            
        }
        
        
        //-----------------------------------------------------------logging-------------------------------------------------------------------------------
        String msg="IF: "+iftextfield.getText()+"\n"+
                      "Line: "+linetextfield.getText()+"\n"+
                     "Machine: "+machinetextfield.getText()+"\n"+
                    "Start date: "+startdatefield.getText()+"\n"+
                    "End date: "+enddatefield.getText()+"\n"+
                    "Status: "+statusfield.getText()+"\n"+
                    "Creator user: "+user.getText()+"\n"+
                    "SAP Order: "+sapfield.getText()+"\n"+
                    "Escalation User: "+escalation_user_textfield.getText()+"\n"+
                    "HMI Error: "+hmi.getText()+"\n"+
                    "Problem description: "+problem_desc.getText()+"\n"+
                    "Part number spare parts: "+spareparts.getText()+"\n"+
                    "Solution creator: "+sol_creator.getText()+"\n"+
                    "Solution escalation: "+sol_esc.getText()+"\n"+
                    "Incident's file: "+fileIncident+"\n\n";
                    
           logger.info(Log4jConfiguration.currentTime() + "[INFO]: These are details of the incident when the RIGHT arrow was pressed in INCIDENT DETAILS: \n"+msg);
    }
    public void downloadFiles()
    {
       if(fileIncident.equals(""))
       {
           //Creating a dialog
             Dialog<String> dialog = new Dialog<String>();
             //Setting the title
            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
             ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
             //Setting the content of the dialog
             dialog.setContentText("There are no files to be download");
             //Adding buttons to the dialog pane
             dialog.getDialogPane().getButtonTypes().add(type);
             dialog.showAndWait();
       }
       else
       {
                String fileName=fileIncident+".zip";
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Connecting to SFTP SERVER for downloading the problem folder in INCIDENT DETAILS");
                  try {
                      String SFTPHOOST="192.168.168.208";
                      int SFTPORT=2222;
                      String SFTPUSER="tester";
                      String password="password";
                      JSch jSch=new JSch();
                      Session session=null;
                      Channel channel =null;

                      ChannelSftp channelSftp = null;

                      logger.info(Log4jConfiguration.currentTime()+"[INFO]: Creating session in INCIDENT DETAILS...\n");

                      session=jSch.getSession(SFTPUSER, SFTPHOOST, SFTPORT);
                      session.setPassword(password);

                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session created in INCIDENT DETAILS \n");

                      java.util.Properties config=new java.util.Properties();
                      config.put("StrictHostKeyChecking", "no");
                      session.setConfig(config);
                      session.connect();
                      if(session.isConnected()==true)
                      {
                             logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session is connected in INCIDENT DETAILS \n");

                             logger.info(Log4jConfiguration.currentTime()+"[INFO]: Open channel in INCIDENT DETAILS...\n");

                             channel=session.openChannel("sftp");
                             channel.connect();

                             if(channel.isConnected()==true)
                             {
                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: Channel is connected in INCIDENT DETAILS \n");
                                 ChannelSftp sftpChannel = (ChannelSftp) channel;

                                 //download
                                 String source1=fileName;
                                 String destination1="C:\\Data\\"+fileName;

                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: Downloading file from server  in INCIDENT DETAILS...\n");

                                 sftpChannel.get(source1, destination1);


                                 if(destination1.isEmpty()==false)
                                 {
                                             logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+fileName+ " was download from SFTP server in INCIDENT DETAILS!\n");
                                             //Creating a dialog
                                             Dialog<String> dialog = new Dialog<String>();
                                             //Setting the title
                                             Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/ok.png"));
                                        dialog.setTitle("Success");
                                             ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                             //Setting the content of the dialog
                                             dialog.setContentText(fileIncident+".zip was downloaded with success on C://Data");
                                             //Adding buttons to the dialog pane
                                             dialog.getDialogPane().getButtonTypes().add(type);
                                             dialog.showAndWait();
                                 }
                                 else
                                 {
                                     logger.error(Log4jConfiguration.currentTime()+"[ERROR]: File's path is null in INCIDENT DETAILS!\n\n");
                                 }

                                 //cand e gata incheiem conexiunea
                                 channel.disconnect();
                                 if (session != null) {
                                     session.disconnect();
                                     logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session disconnected  in INCIDENT DETAILS\n");
                                 }
                                 sftpChannel.exit();
                             }
                             else
                             {
                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The channel connection has failed  in INCIDENT DETAILS\n\n");
                             }


                      }
                      else
                      {
                          logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The connection with session has failed in INCIDENT DETAILS!\n\n");
                             //Creating a dialog
                            Dialog<String> dialog = new Dialog<String>();
                            //Setting the title
                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/error.png"));
                                        dialog.setTitle("Error");
                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                            //Setting the content of the dialog
                            dialog.setContentText("Something went wrong while downloading the files!");
                            //Adding buttons to the dialog pane
                            dialog.getDialogPane().getButtonTypes().add(type);
                            dialog.showAndWait();
                      }



                  } catch (JSchException ex) {
                      logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: INCIDENT DETAILS ---- SftpException: "+ex);
                  } catch (SftpException ex) {
                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: INCIDENT DETAILS ---- JSchException: "+ex);
                }

       }
         
    }
    
    public void setForGuest()
    {
        String compuser=System.getProperty("user.name");
        String RoleCompUser=ConnectionDatabase.getRolefromAccess(compuser);
         if(RoleCompUser!=null)
        {
           if(RoleCompUser.equals("GUEST"))
           {
               btnnewfromtemplate.setVisible(false);
           }
        }
    }
            
     
    public void setDetailsInFields(String IF,String line,String machine,String escal_person,String User,String Hmi,String desc,String Sol_creator)
    {
            iftextfield.setText(IF);
            linetextfield.setText(line);
            machinetextfield.setText(machine);
            escalation_user_textfield.setText(escal_person);
            user.setText(User);
            hmi.setText(Hmi);
            problem_desc.setText(desc);
            sol_creator.setText(Sol_creator);
            
    }
    
    public void setFromOverviewInField(String sd,String ed,String status,String sap)
    {
            startdatefield.setText(sd);
            enddatefield.setText(ed);
            statusfield.setText(status);
            sapfield.setText(sap);
    }
    public void transferDatatoTemplate(String short_sol)
    {
        //------------------------------------get the data from incident to parse for making a new template---------------------------------------------
            //int id_problem_list_history=Data.id_pb;
            String status_incident=statusfield.getText();
            String solution_creator=sol_creator.getText();
            //String creator_username=user.getText();
            String sol_escalation=sol_esc.getText();
            String short_solution=short_sol;
            //String filePath=ConnectionDatabase.getFilePathfromIncident(Data.idincident_list_history);
            
            int idEscalationAssign=ConnectionDatabase.getEscIdforIncident(Data.idincident_list_history);
            Data.status=status_incident;
            Data.sol_creator=solution_creator;
            Data.sol_escal=sol_escalation;
            Data.idEscaAssignIncident=idEscalationAssign;
            //Data.file_path=filePath;
            Data.short_sol=short_solution;
            Data.incident_ticket=ConnectionDatabase.getIncidentTicket(Data.idincident_list_history);
    }
    
    public void setSparePartsField(int id_incident_list_history)
    {
        String idspare_parts=ConnectionDatabase.getidPartNumberfromIncidents(id_incident_list_history);
        
        
        if(idspare_parts.equals("")==false)
        {
            
                if(idspare_parts.length()==1)
                {
                    String pn=ConnectionDatabase.getPartNumber(Integer.parseInt(idspare_parts));
                    spareparts.setText(pn);
                }
                else
                {

                    String[] items = idspare_parts.split(";");
                    List<String> itemList = Arrays.asList(items);
                    List<String> Part_Numbere=new ArrayList<>();
                    for(String idsp : itemList)
                    {
                        String pn=ConnectionDatabase.getPartNumber(Integer.parseInt(idsp));
                        Part_Numbere.add(pn);
                    }

                    String part_numbere=Part_Numbere.stream().collect(Collectors.joining("\n"));
                    spareparts.setText(part_numbere);
                }
        }
        else
        {
            spareparts.setText("");
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]:No IDs for SPARE PARTS were found in INCIDENT DETAILS!");
        }
        
    }
    public void cancelFocusSolutionCreator()
    {
        sol_creator.setOnMouseClicked( e->{
            cancelFocus();
        });
    }
    public void enableFocus()
    {
        sol_creator.focusTraversableProperty();
        
    }
    public void setFieldsForSolved()
    {
                //statusfield.setEditable(false);
                //cancelFocusSolutionCreator();
        
                sol_creator.setEditable(false);
                spareparts.setEditable(false);
                sol_esc.setVisible(true);
                downloadbtn.setVisible(true);
                foldericon.setVisible(true);
                sol_escal_text.setVisible(true);
                btnnewfromtemplate.setVisible(true);
                addbtnspare.setVisible(false);
                addicon.setVisible(false);
                enddatefield.setEditable(false);
                escalation_user_textfield.setVisible(true);
                esc_name_text.setVisible(true);
                savebtn.setVisible(false);
                saveicon.setVisible(false);
                sapfield.setEditable(false);
                escal_combo.setVisible(false);
                escal_to_combo.setVisible(false);
                escal_text.setVisible(false);
                escal_to_text.setVisible(false);
                
                
                sol_creator.setLayoutX(596);
                sol_creator.setLayoutY(221);
                sol_creator.setPrefWidth(624);
                sol_creator_text.setLayoutX(595);
                sol_creator_text.setLayoutY(205);
                
                
                pb_desc_text.setLayoutX(9); 
                pb_desc_text.setLayoutY(206);
                problem_desc.setLayoutX(10);
                problem_desc.setLayoutY(221);
                problem_desc.setPrefWidth(310);
                
                part_number_text.setLayoutX(330);
                part_number_text.setLayoutY(205);
                spareparts.setLayoutX(328);
                spareparts.setLayoutY(221);
                spareparts.setPrefHeight(141);
                spareparts.setPrefWidth(258);
                
                
                hmi_text.setLayoutX(803);
                hmi_text.setLayoutY(150);
                hmi.setLayoutX(1009);
                hmi.setLayoutY(121);
                hmi.setPrefWidth(212);
                sap_text.setLayoutX(8);
                sap_text.setLayoutY(150);
                sapfield.setLayoutX(121);
                sapfield.setLayoutY(121);
                
                
                
    }
    public void setFieldsForUnsolved()
    {
                //statusfield.setEditable(true);
                //enableFocus();
                
                
                
                sol_esc.setVisible(false);
                downloadbtn.setVisible(false);
                foldericon.setVisible(false);
                sol_escal_text.setVisible(false);
                
                sol_creator.setEditable(true);
                
                sol_creator.setLayoutX(580);
                sol_creator.setLayoutY(377);
                sol_creator.setPrefWidth(643);
                
                sol_creator_text.setLayoutX(580);
                sol_creator_text.setLayoutY(368);
                
                part_number_text.setLayoutX(8);
                part_number_text.setLayoutY(150);
                
                pb_desc_text.setLayoutX(15); 
                pb_desc_text.setLayoutY(368);
                
                problem_desc.setLayoutX(13);
                problem_desc.setLayoutY(377);
                
                problem_desc.setPrefWidth(550);
                
                spareparts.setEditable(true);
                spareparts.setLayoutX(270);
                spareparts.setPrefHeight(176);
                spareparts.setPrefWidth(280);
                spareparts.setLayoutY(130);
                
                btnnewfromtemplate.setVisible(false);
                
                addbtnspare.setVisible(true);
                addicon.setVisible(true);
                addbtnspare.setGraphic(addicon);
                addbtnspare.setLayoutX(23);
                addbtnspare.setLayoutY(165);
                enddatefield.setEditable(true);
                
                escalation_user_textfield.setVisible(false);
                esc_name_text.setVisible(false);
                
                savebtn.setVisible(true);
                saveicon.setVisible(true);
                savebtn.setGraphic(saveicon);
                
                savebtn.setLayoutX(998);
                savebtn.setLayoutY(550);
                
                hmi_text.setLayoutX(554);
                hmi_text.setLayoutY(220);
                
                hmi.setLayoutX(776);
                hmi.setLayoutY(190);
                hmi.setPrefWidth(446);
                
                sap_text.setLayoutX(554);
                sap_text.setLayoutY(150);
                
                sapfield.setLayoutX(674);
                sapfield.setLayoutY(125);
                sapfield.setEditable(true);
                
                escal_combo.setVisible(true);
                escal_to_combo.setVisible(true);
                escal_text.setVisible(true);
                escal_to_text.setVisible(true);
                
                escal_text.setLayoutX(554);
                escal_text.setLayoutY(297);
                
                escal_combo.setLayoutX(664);
                escal_combo.setLayoutY(273);
                
                escal_to_text.setLayoutX(790);
                escal_to_text.setLayoutY(297);
                
                escal_to_combo.setLayoutX(890);
                escal_to_combo.setLayoutY(273);
                escal_to_combo.setPrefWidth(332);
                
    }
    public void refreshForUnsolved()
    {
   
        escal_to_combo.getSelectionModel().clearSelection();
        escal_to_combo.getItems().clear();
        escal_combo.setValue("Select...");
        
       
        
    }
    public void setonFalseForUnsolved()
    {
        addbtnspare.setVisible(false);
        addicon.setVisible(false);
        savebtn.setVisible(false);
        saveicon.setVisible(false);
        escal_combo.setVisible(false);
        escal_to_combo.setVisible(false);
        escal_text.setVisible(false);
        escal_to_text.setVisible(false);
        
    }
    
    
    
    public void onOpenAddSpare()
    {
        if(openspareadd==null)
        {
                     try {
                     openspareadd=new Stage();
                     Parent root = FXMLLoader.load(getClass().getResource("/fxml/sparepartsadd.fxml"));
                     Scene scene = new Scene(root);

                     openspareadd.setTitle("Add spare parts in text area");
                     openspareadd.initStyle(StageStyle.UTILITY);
                     openspareadd.setResizable(false);
                     openspareadd.setScene(scene);
                     openspareadd.setOnHidden(e->{
                         Data obiect_data=new Data();
                         if(obiect_data.checkSparePartsNull()==false)
                         {
                             setSpares();
                         }

                     });

                     openspareadd.show();

                 } catch (IOException ex) {
                     logger.fatal("[FATAL]: INCIDENT DETAILS --- IOException: "+ex+"\n\n");
                 }
        }
        else if(openspareadd.isShowing())
        {
                        //newincpbstage.toFront();
                        //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Add spare parts in text area' is already open in the background! "
                                + "Close it if"
                                + " you want to ignore it and open a new one.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
            
        }
        else
        {
                    try {
                   openspareadd=new Stage();
                   Parent root = FXMLLoader.load(getClass().getResource("/fxml/sparepartsadd.fxml"));
                   Scene scene = new Scene(root);

                   openspareadd.setTitle("Add spare parts in text area");
                   openspareadd.initStyle(StageStyle.UTILITY);
                   openspareadd.setResizable(false);
                   openspareadd.setScene(scene);
                   openspareadd.setOnHidden(e->{
                       Data obiect_data=new Data();
                       if(obiect_data.checkSparePartsNull()==false)
                       {
                           setSpares();
                       }

                   });

                   openspareadd.show();

               } catch (IOException ex) {
                   logger.fatal("[FATAL]: INCIDENT DETAILS --- IOException: "+ex+"\n\n");
               }
        }
        /*
        try {
            Stage primaryStage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/sparepartsadd.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("Add spare parts in text area");
            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setOnHidden(e->{
                Data obiect_data=new Data();
                if(obiect_data.checkSparePartsNull()==false)
                {
                    setSpares();
                }
                 
            });
            
            primaryStage.show();
            
        } catch (IOException ex) {
            logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- IOException: "+ex+"\n\n");
        }*/
    }
    
    public void setSpares()
    {
        
        if(Data.spare_part_numbers.isEmpty()==false)
        {
            if(spareparts.getText().isEmpty())
            {
                spareparts.setText(Data.spare_part_numbers);
            }
            else
            {
                spareparts.setText(spareparts.getText()+"\n"+Data.spare_part_numbers);
            }
            
          
        }
        
        
    }
    
    public void onEscal()
    {
        //System.out.println(escal_combo.getSelectionModel().isEmpty());
        if(escal_combo.getSelectionModel().isEmpty()==false)
        {
            if(escal_combo.getValue().equals("yes"))
            {
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Escalation status YES in INCIDENT DETAILS \n");
                     
                     itemscomboescal=ConnectionDatabase.getEscName();
                     
                    if(!itemscomboescal.isEmpty())
                    {
                          //escalationcombo.setValue("Select...");
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
                          Collections.sort(itemscomboescal,c);
                          //itemscomboescal.add(0, "Select...");
                          escal_to_combo.setItems(itemscomboescal); 
                          
                          new AutoCompleteComboBoxListener<>(escal_to_combo);

                          String engineer="OUTPUT LIST of engineers from INCIDENT DETAILS: \n"+
                          "[1]: "+itemscomboescal.get(1)+"\n";

                          for(int i=2;i<itemscomboescal.size();i++)
                          {
                              engineer=engineer+"["+i+"]: "+itemscomboescal.get(i)+"\n";
                          }
                          logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+engineer+"\n\n");


                     }
                     else
                     {
                         logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty list of engineers for escalation in NEW INCIDENT NEW PROBLEM");
                     }

                        
            }
            else if(escal_combo.getValue().equals("no"))
            {
                escal_to_combo.getItems().clear();
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Escalation status NO in INCIDENT DETAILS \n\n");
                    
            }
        }
        else
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Empty escalation combobox in INCIDENT DETAILS \n");
        }
        
        
        
    }
    public void convertSparePartsToDb()
    {
            String  mystring=spareparts.getText();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The PART NUMBERS from INCIDENT DETAILS: \n"+mystring+"\n\n");
            
            String[] items = mystring.split("\n");
            List<String> itemList = Arrays.asList(items);
            List<String> idspares=new ArrayList<>();
            
            //System.out.println(itemList);
            
            for(String p:itemList)
            {
                int idspare=ConnectionDatabase.getSparePartsId(p);
                if(idspare != 0)
                {
                     idspares.add(String.valueOf(idspare));
                }
               
            }
            
            //System.out.println(idspares);
            
            String listpartnumbers=idspares.stream().collect(Collectors.joining(";"));
            part_numbere=listpartnumbers;
            
            //System.out.println(part_numbere);
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: the ID of SPARE PARTS from the text area: "+part_numbere);
            
    }
     public void onSelectEscalation()
    {
           
                
            boolean isCombo=escal_to_combo.getSelectionModel().isEmpty();
            if(isCombo==false && escal_to_combo.getValue().toString().isEmpty()==false)
            {
                
                String valueesc=escal_to_combo.getValue().toString();
                
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The engineer selected for escalation in NEW INCIDENT NEW PROBLEM: \n"
                        +valueesc);
                int index1=valueesc.indexOf("(");
                int index2=valueesc.indexOf(")"); 
                String user=valueesc.substring(index1+1, index2);
                id_EscTeam=ConnectionDatabase.getEscId(user);
            }
            else
            {
                id_EscTeam=-1;
            }
            
            
            
    }
     public void callUpdate(int sap,String spare)
     {
        try {
            //GET READY END DATE
            String st_date=startdatefield.getText();
            String en_date=enddatefield.getText();
            SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

            Date dates = sdf.parse(st_date);
            Date datee=sdf.parse(en_date);
            long sdmili=dates.getTime();
            long edmili=datee.getTime();
            String sd=String.valueOf(sdmili);
            String ed=String.valueOf(edmili);
            
            conn=ConnectionDatabase.getConnection();
            int id_ea = 0;
            
            if(id_EscTeam!=-1)
            {
                //assign engineer
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure assignEngineer()");
                CallableStatement psengineer = conn.prepareCall("{call assignEngineer(?,?)}");
                psengineer.setInt(1,id_EscTeam);
                psengineer.setString(2, sd);
                psengineer.execute();
                id_ea=ConnectionDatabase.getEscAssignId(id_EscTeam, sd);
            }
            
            
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure updateUnsolvedIncident()");
            CallableStatement ps = conn.prepareCall("{call updateUnsolvedIncident(?,?,?,?,?,?)}");
            ps.setInt(1,idIncident);
            ps.setString(2, ed);
            ps.setInt(3, sap);
            ps.setString(4, sol_creator.getText());
            ps.setInt(5, id_ea);
            ps.setString(6,spare);
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
                dialog.setContentText("Your data has been saved!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
                
                Stage s = (Stage) scrollPane .getScene().getWindow();
                s.close();
            }
            else
            {
                logger.error(Log4jConfiguration.currentTime()
                +"[ERROR]: Something went wrong while saving the data about UNSOLVED incident in INCIDENT DETAILS");
            }
            
            
        } catch (ParseException ex) {
            logger.fatal("[FATAL]: INCIDENT DETAILS --- ParseException: "+ex+"\n\n");
        } catch (SQLException ex) {
            logger.fatal("[FATAL]: INCIDENT DETAILS --- SQLException: "+ex+"\n\n");
        }
     }
    
    public void onSave()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: SAVE button was pressed in INCIDENT DETAILS \n");
        
        convertSparePartsToDb();
        
        if(enddatefield.getText().isEmpty()==true || escal_combo.getValue().toString().equals("Select...") || sol_creator.getText().isEmpty()==true)
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty fields in INCIDENT DETAILS!");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Please complete 'End date' , 'Escalation' and 'Solution creator'!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
        }
        else
        {
            //System.out.println(idIncident);
            if(spareparts.getText().isEmpty()==true)
            {
                //no spare
                //System.out.println(part_numbere);
                //System.out.println(id_EscTeam);
                if(sapfield.getText().isEmpty()==false && sapfield.getText().equals("no SAP Order")==false)
                {
                    //e sap
                    callUpdate(Integer.parseInt(sapfield.getText()),"");
                }
                else
                {
                    //nu e sap
                    callUpdate(0,"");
                }
            }
            else
            {
                //spare
                //System.out.println(part_numbere);
                //System.out.println(id_EscTeam);
                if(sapfield.getText().isEmpty()==false && sapfield.getText().equals("no SAP Order")==false)
                {
                    //e sap
                    callUpdate(Integer.parseInt(sapfield.getText()),part_numbere);
                    
                }
                else
                {
                    //nu e sap
                    callUpdate(0,part_numbere);
                }
                
            }
        }
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
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]:INCIDENT DETAILS started\n\n");
        
        
        idIncident=Data.idincident_list_history;
        String compuser=System.getProperty("user.name");
        String RoleCompUser=ConnectionDatabase.getRolefromAccess(compuser);
        
        if(RoleCompUser!=null)
        {
           if(RoleCompUser.equals("GUEST"))
           {
               btnnewfromtemplate.setVisible(false);
           }
        }
        
        setonFalseForUnsolved();
        
        escal_combo.setItems(itemscomboescalstatus);
        escal_combo.setValue("Select...");
        
        leftarrow.setGraphic(lefticon);
        rightarrow.setGraphic(righticon);
        downloadbtn.setGraphic(foldericon);
        
        id_lista_incident_selectat=Data.id_incident_list;
        incident_details=ConnectionDatabase.getIncidentDetails(Data.idincident_list_history);        
        if(incident_details.isEmpty()==true)
         {
              logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list for incident details is empty in INCIDENT DETAILS");
         }
         else
         {
             //set the data for fields
            IncidentsDetails element=incident_details.get(0);
            setDetailsInFields(element.getIF(),element.getLine(),element.getMachine(),element.getEscal_user(),element.getUser(),
            element.getHmi(), element.getDesc(),element.getSol_creator());
           
         }
        incident_list=Data.incidents;
        
         if(incident_list.isEmpty()==true)
         {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of incidents from INCIDENTS OVERVIEW is empty in INCIDENT DETAILS!");
         }
         else
         {
             //------------------------------------------set the incident details from overview-------------------------------------------------------------
            Incidents incident=incident_list.get(id_lista_incident_selectat);
            setFromOverviewInField(incident.getStart_date(),incident.getEnd_date(),incident.getStatus(),String.valueOf(incident.getSap()));
            String filePath=ConnectionDatabase.getFilePathfromIncident(Data.idincident_list_history);
            
            if(filePath.equals("")==false)
            {
                fileIncident=filePath;
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Incident's file in INCIDENT DETAILS is: "+fileIncident);
            }
            else
            {
                fileIncident="";
            }
            
            if(incident.getStatus().equals("unsolved"))
            {
                
                setFieldsForUnsolved();
                
            }
            
           //create the template
           transferDatatoTemplate( incident.getShort_sol());
         
            
           
         }
        
        
        //----------------------------------------------------set the spare numbers---------------------------------------------------------------------------
        //System.out.println(Data.idincident_list_history);
        setSparePartsField(Data.idincident_list_history);
        
       //-------------------------------------------------logging---------------------------------------------------------------------------------------------
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The ID from DB of the incident selected when INCIDENT DETAILS STARTED: "+Data.idincident_list_history+"\n\n");
        
        String msg="IF: "+iftextfield.getText()+"\n"+
                      "Line: "+linetextfield.getText()+"\n"+
                     "Machine: "+machinetextfield.getText()+"\n"+
                    "Start date: "+startdatefield.getText()+"\n"+
                    "End date: "+enddatefield.getText()+"\n"+
                    "Status: "+statusfield.getText()+"\n"+
                    "Creator user: "+user.getText()+"\n"+
                    "SAP Order: "+sapfield.getText()+"\n"+
                    "Escalation User: "+escalation_user_textfield.getText()+"\n"+
                    "HMI Error: "+hmi.getText()+"\n"+
                    "Problem description: "+problem_desc.getText()+"\n"+
                    "Part number spare parts: "+spareparts.getText()+"\n"+
                    "Solution creator: "+sol_creator.getText()+"\n"+
                    "Solution escalation: "+sol_esc.getText()+"\n"+
                    "Incident's file: "+fileIncident+"\n\n";
                    
           logger.info(Log4jConfiguration.currentTime() + "[INFO]: These are details of the incident when the INCIDENT DETAILS has started: \n"+msg);
         enddatefield.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     String regex="^(3[01]|0[1-9]|[12][0-9])\\/(1[0-2]|0[1-9])\\/(-?(?:[1-9][0-9]*)?[0-9]{4})( (?:2[0-3]|[01][0-9]):[0-5][0-9])?$";
                     if (newValue.toString().matches(regex)) { 
                            
                             enddatefield.setStyle("-fx-border-width: 0.5px ;");
                             int ok=compareDates(startdatefield.getText(),newValue.toString());
                             //System.out.println(ok);
                             if(ok==0)
                             {
                                 enddatefield.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                             }
                             else
                             {
                                 startdatefield.setStyle("-fx-border-width: 0.5px ;");
                                 enddatefield.setStyle("-fx-border-width: 0.5px ;");
                             }
                     }
                     else if(newValue.toString().matches(regex)==false){
                         
                         enddatefield.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     
                       
           
                 }
                 
                 
             });
             
        
    }
    
    
   
    
}
