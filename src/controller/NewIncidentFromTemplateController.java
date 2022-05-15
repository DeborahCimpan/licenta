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
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
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
public class NewIncidentFromTemplateController implements Initializable{
    
     @FXML
    private Button sparepartsaddbtn;
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
    private TextField startdate;
     
     @FXML
    private TextField enddate;
     
     @FXML
    private TextField sap;
     
     @FXML
    private TextArea spare_parts;
     
     @FXML
    private ListView<String> listviewfiles;

    @FXML
    private Button btnchooseFile;

    @FXML
    private Button deletefilesbtn;

    @FXML
    private ImageView fileicon;

    @FXML
    private ImageView deleteicon;
    
    private Stage openspareadd;
     
     String part_numbere;
     Connection conn=null;
     
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The CANCEL button was pressed in NEW INCIDENT FROM TEMPLATE!");
        Stage s = (Stage) cancelbtn .getScene().getWindow();
        s.close();
    }
    @FXML
    private ScrollPane scrollPane;
    
    public org.apache.logging.log4j.Logger logger = LogManager.getLogger(NewIncidentFromTemplateController.class);
    
        @FXML
    private AnchorPane anchorPane;
        
    public int idInc=-1;
       
     public void convertSparePartstoDB()
    {
        String mystring=spare_parts.getText();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The PART NUMBERS from NEW INCIDENT FROM TEMPLATE: \n"+mystring+"\n\n");
            
            String[] items = mystring.split("\n\\s+");
            List<String> itemList = Arrays.asList(items);
            List<String> idspares=new ArrayList<>();
            for(String p:itemList)
            {
                int idspare=ConnectionDatabase.getSparePartsId(p);
                if(idspare!=0)
                {
                    idspares.add(String.valueOf(idspare));
                }
                
            }
            
            String listpartnumbers=idspares.stream().collect(Collectors.joining(";"));
            part_numbere=listpartnumbers;
    }
    @FXML
    void deleteFiles(ActionEvent event) {
        
        ObservableList<String> list = listviewfiles.getSelectionModel().getSelectedItems();
        listviewfiles.getItems().removeAll(list);
        
        logger.info(Log4jConfiguration.currentTime()+"The deleted files from NEW INCIDENT FROM TEMPLATE: \n"+list);

    }
    public void cancelSelection()
    {
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: The selection of files has been cleared in NEW INCIDENT FROM TEMPLATE!\n");
             listviewfiles.getSelectionModel().clearSelection();
       
       
    }
    
    @FXML
    void chooseFile(ActionEvent event) {
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Choose file button from NEW INCIDENT FROM TEMPLATE was pressed!\n\n");
            
            FileChooser filechooser=new FileChooser();
            filechooser.setTitle("Open File Dialog");
            filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All files","*.*"));
            Stage stage=(Stage)anchorPane.getScene().getWindow();
            
            String filemsg="OUTPUT LIST of files selected in NEW INCIDENT FROM TEMPLATE: \n";
            int i=0;
            List<File> files=filechooser.showOpenMultipleDialog(stage);
            FileChooserTemplate files_object=new FileChooserTemplate(files);
            if(files_object.checkFileNull()==false)
            {
                for(File f:files_object.getFiles())
                {
                    if(f.isFile()==true )
                    {
                        double size=f.length()/(1024*1024);

                        if(size <= 5)
                        {
                                filemsg=filemsg+"["+i+"]: "+files_object.getFiles().get(i)+"\n";
                                i++;
                                listviewfiles.getItems().add(f.getAbsolutePath());



                        }
                        else
                        {
                                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The "+ i +" file is too big in NEW INCIDENT FROM TEMPLATE");
                                    //Creating a dialog
                                    Dialog<String> dialog = new Dialog<String>();
                                    //Setting the title
                                    Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                    //Setting the content of the dialog
                                    dialog.setContentText("The file is too big!");
                                    //Adding buttons to the dialog pane
                                    dialog.getDialogPane().getButtonTypes().add(type);
                                    dialog.showAndWait();
                        }

                    }


                }
        }
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+filemsg+"\n");
        
        
        
    }
     public  void zip(File path,String directoryName) throws FileNotFoundException, IOException
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: ZIPING the INCIDENT folder in NEW INCIDENT FROM TEMPLATE...\n");
        File[] files=path.listFiles();
        if(files.length == 0)
          logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT FROM TEMPLATE---IllegalArgumentException: NO FILES IN THE PATH");
        
        String address_stream="./"+directoryName+".zip";
        FileOutputStream fos=new FileOutputStream(address_stream);
        ZipOutputStream zipOut=new ZipOutputStream(fos);
        
        for(File zipThis : files)
        {
            FileInputStream fis = new FileInputStream(zipThis);
            ZipEntry zipEntry = new ZipEntry(zipThis.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes=new byte[2048*2048];
            int length;
            while((length=fis.read(bytes))>=0)
            {
                zipOut.write(bytes,0,length);
            }
            fis.close();
            
            
        }
        zipOut.flush();
        fos.flush();
        zipOut.close(); 
        fos.close();
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The following file is for INCIDENT: \n"
                +directoryName+".zip\n\n");
        
        
    }
     
      public void addingFile(File file,File directory)
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Adding file "+file.getName()+ " in " + directory.getName() + "...\n");
        
         if(directory.isDirectory() && file.isFile())
        {
                FileInputStream filestream=null;
            try{
                filestream = new FileInputStream(file);
                byte[] buffer = new byte[1024*1024];
                int count;
                String filename=file.getName();
                
                File myFile = new File(directory, filename);
                try(FileOutputStream fStream = new FileOutputStream(myFile);
                        DataOutputStream data0 = new DataOutputStream(fStream)) {
                    
                    while((count=filestream.read(buffer)) >0)
                    {
                        data0.write(buffer, 0, count);
                    }
                    data0.flush();
                    data0.close();

                }   catch (FileNotFoundException ex) {
                   logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- FileNotFoundException: "+ex);
                } catch (IOException ex) {
                    logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- IOException: "+ex);
                }

           }   catch (FileNotFoundException ex) {
               logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- IOException: "+ex);
            } finally {
                try {
                    filestream.close();
                } catch (IOException ex) {
                    logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- IOException: "+ex+"\n\n");
                }
          }
        }
        else
        {
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: The arguments for addingFile must be : FILE,DIRECTORY in NEW INCIDENT FROM TEMPLATE\n\n");
        }
        
    }
      public void sendOnServer(String source,String destination)
     {
          
          logger.info(Log4jConfiguration.currentTime()+"[INFO]: Sending UPDATE PROBLEM file on SFTP server in NEW INCIDENT FROM TEMPLATE...\n");
          
          logger.info(Log4jConfiguration.currentTime()+"[INFO]: Connecting to SFTP SERVER in NEW INCIDENT FROM TEMPLATE...\n");
          try {
             String SFTPHOOST="192.168.168.208";
             int SFTPORT=2222;
             String SFTPUSER="tester";
             String password="password";
             
             JSch jSch=new JSch();
             Session session=null;
             Channel channel =null;
             
              logger.info(Log4jConfiguration.currentTime()+"[INFO]: Creating session in NEW INCIDENT FROM TEMPLATE...\n");
             
             session=jSch.getSession(SFTPUSER, SFTPHOOST, SFTPORT);
             session.setPassword(password);
             
              logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session created in NEW INCIDENT FROM TEMPLATE \n");
             
             java.util.Properties config=new java.util.Properties();
             config.put("StrictHostKeyChecking", "no");
             session.setConfig(config);
             session.connect();
             if(session.isConnected()==true)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session is connected in NEW INCIDENT FROM TEMPLATE \n");
                 
                  logger.info(Log4jConfiguration.currentTime()+"[INFO]: Open channel in NEW INCIDENT FROM TEMPLATE...\n");
                 channel=session.openChannel("sftp");
                 channel.connect();
                 if(channel.isConnected()==true)
                 {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Channel is connected in NEW INCIDENT FROM TEMPLATE \n");
                        
                        ChannelSftp sftpChannel = (ChannelSftp) channel;
                        
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Sending file on server  in NEW INCIDENT FROM TEMPLATE...\n");
                        
                        sftpChannel.put(source, destination);
                        
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+source+ " was send on SFTP server in NEW INCIDENT FROM TEMPLATE!\n");
                        
                        channel.disconnect();
                        if (session != null) {
                              session.disconnect();
                              logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session disconnected  in NEW INCIDENT FROM TEMPLATE\n");
                        
                              
                        }

                        sftpChannel.exit();
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Channel disconnected in NEW INCIDENT FROM TEMPLATE \n\n");
                        
                 }
                 else
                 {
                     logger.info(Log4jConfiguration.currentTime()+"[INFO]: The channel connection has failed  in NEW INCIDENT FROM TEMPLATE\n\n");
                        
                 }

                
             }
             else
             {
                  logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The connection with session has failed in NEW INCIDENT FROM TEMPLATE!\n\n");
                    //Creating a dialog
                   Dialog<String> dialog = new Dialog<String>();
                   //Setting the title
                  Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/error.png"));
                                        dialog.setTitle("Error");
                   ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                   //Setting the content of the dialog
                   dialog.setContentText("Something went wrong while saving the files on the remote location!");
                   //Adding buttons to the dialog pane
                   dialog.getDialogPane().getButtonTypes().add(type);
                   dialog.showAndWait();
             }
             
             
            
         } catch (SftpException ex) {
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT FROM TEMPLATE ---- SftpException: "+ex);
         } catch (JSchException ex) { 
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT FROM TEMPLATE ---- JSchException: "+ex);
         }
     }
   
    
    public void savingFiles(List<File> listFiles,String idIncident) 
    {
            
            List<String> list=new ArrayList<>();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: SAVING FILE of the PROBLEM in NEW INCIDENT FROM TEMPLATE\n");     
             
             
             DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
             Calendar obj = Calendar.getInstance();
             String date = formatter.format(obj.getTime());
             
             LocalTime timeObj = LocalTime.now();
             String currentTime = timeObj.toString();
             
             String time=currentTime.substring(0,5);
            //String finaldate=date+"_"+currentTime.substring(0,2)+"_"+currentTime.substring(3,5)+"_"+currentTime.substring(6,8);
             //creare folder
             String finaldate=date+"_";
             
             
             
             //cream folderul incidentului
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: Creating the folder for the FIRST INCIDENT in NEW INCIDENT FROM TEMPLATE\n");
                  File incident=new File("./","Problem_"+Data.id_pb+"_"+date+"_"+"Incident_"+idIncident+"_"+date);
                  if(incident.mkdir()==true)
                  {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+incident.getName()+" was created in NEW INCIDENT FROM TEMPLATE\n");
                  }
                  else
                  {
                         logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while creating the folder "+incident.getName()+" in NEW INCIDENT FROM TEMPLATE\n");
                  }
                  //adaugam in el fisierele
                  if(incident.exists()==true)
                  {
                try {
                    list.add(incident.getName());
                    
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+incident.getName()+" exists in NEW INCIDENT FROM TEMPLATE");
                    int j=0;
                    for(File f:listFiles)
                    {
                        addingFile(f,incident);
                       
                    }
                    
                    
                    //arhivam folderu de incidentu unu creat
                    zip(incident,incident.getName());
                    
                    
                    
                    //il trimitem pe server pe folderul cu fisierele noi aparute de la incident
                    String urlArchive="./"+incident.getName()+".zip";
                    String destinationArchive=incident.getName()+".zip";
                         
                   //trimitem pe server
                    sendOnServer(urlArchive,destinationArchive);
                         
                    //eliberam memorie
                    File fil=new File(urlArchive);
                    FileUtils.deleteDirectory(incident);
                    FileUtils.delete(fil);
                    
                    
                    

                    } catch (IOException ex) {
                        logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT FROM TEMPLATE --- IOException: "+ex);
                    }

                         
                      
             }
             else
             {
                      logger.error(Log4jConfiguration.currentTime()+"[ERROR]: "+incident.getName()+" doesn't exists in NEW INCIDENT FROM TEMPLATE");
             }
                  
                  
                  
    }
    
    
    public void save()
    {
        
        boolean isstartdate=startdate.getText().isEmpty();
        boolean isenddate=enddate.getText().isEmpty();
        boolean issap=sap.getText().isEmpty();
        boolean isspare=spare_parts.getText().isEmpty();
        
        String regex="^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]$";
        int ok=compareDates(startdate.getText(),enddate.getText());
        
        List<String> FilesListView=new ArrayList<>();
       for(String el: listviewfiles.getItems())
       {
           FilesListView.add(el);
       }
       convertSparePartstoDB();
       
       String user=System.getProperty("user.name");
       
       //nu e sap
       if(issap==true)
       {
           
        if(isstartdate==true || isenddate==true)
        {
              
               logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty fields in NEW INCIDENT FROM TEMPLATE!");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
               Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Please complete all the required fields!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
                clearField();
        }
        else if(spare_parts.getText().length() > 450 || startdate.getText().matches(regex)==false || enddate.getText().matches(regex)==false || ok==0)
        {
               logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Incorrect inputs in NEW INCIDENT FROM TEMPLATE!");
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
                clearField();
        }
        
        //caz in care e fisier
        else if(isstartdate==false && isenddate==false  && isspare==false && FilesListView.isEmpty()==false)
        {
            
            try {
                    String st_date=startdate.getText();
                    String en_date=enddate.getText();
                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    Date dates = sdf.parse(st_date);
                    Date datee=sdf.parse(en_date);
                    long sdmili=dates.getTime();
                    long edmili=datee.getTime();
                    String sd=String.valueOf(sdmili);
                    String ed=String.valueOf(edmili);
                    
                    //setare ticket pentru incident
                    long milliSec = System.currentTimeMillis();  
                    String current_milisec=String.valueOf(milliSec);
                    String ticket="#INC_"+current_milisec;
                    
                    conn = ConnectionDatabase.getConnection();
                    CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                    psincident.setString(1,ticket);
                    psincident.setInt(2, Data.id_pb);
                    psincident.setString(3,sd);
                    psincident.setString(4, ed);
                    psincident.setString(5, Data.status);
                    psincident.setInt(6,0);
                    psincident.setString(7, Data.sol_creator);
                    psincident.setString(8, Data.sol_escal);
                    psincident.setString(9, user);
                    psincident.setInt(10, Data.idEscaAssignIncident);
                    psincident.setString(11,part_numbere);
                    psincident.setString(12,"DummyIncident");
                    psincident.setString(13,Data.short_sol);
                    
                    //System.out.println(sd);
                    
                    /*
                    System.out.println("\nFrom template: ");
                    System.out.println(Data.id_pb);
                    System.out.println(Data.status);
                    System.out.println(Data.sol_escal);
                    System.out.println(Data.sol_creator);
                    System.out.println(Data.short_sol);
                    
                    System.out.println(ticket);
                    System.out.println(part_numbere);*/
                    
                    if(psincident.executeUpdate()!=0)
                    {
                               int IncidentId=ConnectionDatabase.getIncidentId(ticket ,Data.id_pb, sd, ed, 
                                                                Data.status, 
                                                                Data.sol_creator, user, Data.short_sol);
                                
                                idInc=IncidentId;
                                
                                logger.info("Ticket: "+ticket+"\n"+
                                           "ID PROBLEM: "+Data.id_pb+"\n"+
                                        "Start date in miliseconds: "+sd+"\n"+
                                        "End date in miliseconds: "+ed+"\n"+
                                        "Status: "+Data.status+"\n"+
                                        "SAP: "+sap.getText()+"\n"+
                                        "Sol creator: "+Data.sol_creator+"\n"+
                                        "Creator username: "+user+"\n"+
                                        "Short sol: "+Data.short_sol+"\n\n");
                                
                                logger.info("INCIDENT'S ID in NEW INCIDENT FROM TEMPLATE: "+idInc+"\n");
                                                        
                                DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
                                Calendar obj = Calendar.getInstance();
                                String date = formatter.format(obj.getTime());
                                                        
                                                        
                                                        
                                String fileIncident="Problem_"+Data.id_pb+"_"+date+"_Incident_"+idInc+"_"+date;
                                
                                
                                conn=ConnectionDatabase.getConnection();
                                CallableStatement updateFileInc = conn.prepareCall("{call updateFileIncident(?,?)}");
                                updateFileInc.setInt(1, IncidentId);
                                updateFileInc.setString(2, fileIncident);
                                
                                if(updateFileInc.executeUpdate()!=0)
                                {
                                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the incident was saved in DB in NEW INCIDENT FROM TEMPLATE!");
                                }
                                else
                                {
                                    logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the "
                                            + " file of the incident in DB in NEW INCIDENT FROM TEMPLATE!");
                                }
                                
                                String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                "Incident's ticket: "+ticket+"\n"+
                                 "Incident's start date: "+st_date+"\n"+
                                 "Incident's end date: "+en_date+"\n"+
                                 "Incident's status: "+Data.status+"\n"
                                 + "Incident's SAP order: "+sap.getText()+"\n"+
                                 "Incident's solution creator: "+Data.sol_creator+"\n"+
                                 "Incident's creator user: "+user+"\n"+
                                  "Incident's escalation assign: "+Data.idEscaAssignIncident+"\n"+
                                 "Incident's spare parts: "+spare_parts.getText()+"\n"+
                                 "Incident's file name: "+fileIncident+"\n"+
                                 "Incident's short description: "+Data.short_sol+"\n";
                                 
                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The incident for the problem with id "+Data.id_pb
                                        + " was added with success and these are the details: \n"+incmsg);
                                 //trimitem pe server
                                 List<File> filelist=new ArrayList<>();
                                 for(String f: FilesListView)
                                 {
                                                File file=new File(f);
                                                filelist.add(file);
                                 }
                                savingFiles(filelist,String.valueOf(idInc));
                                
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
                 
                    
            } catch (SQLException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- ParseException: "+ex+"\n\n");
            } catch (ParseException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- SQLException: "+ex+"\n\n");
            }
            
        }
        
        else if(isstartdate==false && isenddate==false  && isspare==true && FilesListView.isEmpty()==false)
        {
            
            try {
                    String st_date=startdate.getText();
                    String en_date=enddate.getText();
                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    Date dates = sdf.parse(st_date);
                    Date datee=sdf.parse(en_date);
                    long sdmili=dates.getTime();
                    long edmili=datee.getTime();
                    String sd=String.valueOf(sdmili);
                    String ed=String.valueOf(edmili);
                    conn = ConnectionDatabase.connectDb();
                    CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                    
                    //setare ticket pentru incident
                    long milliSec = System.currentTimeMillis();  
                    String current_milisec=String.valueOf(milliSec);
                    String ticket="#INC_"+current_milisec;
                    
                    psincident.setString(1, ticket);
                    psincident.setInt(2, Data.id_pb);
                    psincident.setString(3,sd);
                    psincident.setString(4, ed);
                    psincident.setString(5, Data.status);
                    psincident.setInt(6, 0);
                    psincident.setString(7, Data.sol_creator);
                    psincident.setString(8, Data.sol_escal);
                    psincident.setString(9, user);
                    psincident.setInt(10, Data.idEscaAssignIncident);
                    psincident.setString(11,"");
                    psincident.setString(12,"Dummy Incident");
                    psincident.setString(13,Data.short_sol);
                    if(psincident.executeUpdate()!=0)
                    {
                                int IncidentId=ConnectionDatabase.getIncidentId(ticket , Data.id_pb, sd, ed, 
                                                                Data.status, 
                                                                Data.sol_creator, user, Data.short_sol);
                                logger.info("Ticket: "+ticket+"\n"+
                                           "ID PROBLEM: "+Data.id_pb+"\n"+
                                        "Start date in miliseconds: "+sd+"\n"+
                                        "End date in miliseconds: "+ed+"\n"+
                                        "Status: "+Data.status+"\n"+
                                        "SAP: "+sap.getText()+"\n"+
                                        "Sol creator: "+Data.sol_creator+"\n"+
                                        "Creator username: "+user+"\n"+
                                        "Short sol: "+Data.short_sol+"\n\n");
                                idInc=IncidentId;
                                
                                logger.info("INCIDENT'S ID in NEW INCIDENT FROM TEMPLATE: "+idInc+"\n");
                                                        
                                DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
                                Calendar obj = Calendar.getInstance();
                                String date = formatter.format(obj.getTime());
                                                        
                                                        
                                                        
                                String fileIncident="Problem_"+Data.id_pb+"_"+date+"_Incident_"+idInc+"_"+date;
                                
                                
                                conn=ConnectionDatabase.getConnection();
                                CallableStatement updateFileInc = conn.prepareCall("{call updateFileIncident(?,?)}");
                                updateFileInc.setInt(1, IncidentId);
                                updateFileInc.setString(2, fileIncident);
                                
                                if(updateFileInc.executeUpdate()!=0)
                                {
                                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the incident was saved in DB in NEW INCIDENT FROM TEMPLATE!");
                                }
                                else
                                {
                                    logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the "
                                            + " file of the incident in DB in NEW INCIDENT FROM TEMPLATE!");
                                }
                                
                                String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                "Incident's ticket: "+ticket+"\n"+
                                 "Incident's start date: "+st_date+"\n"+
                                 "Incident's end date: "+en_date+"\n"+
                                 "Incident's status: "+Data.status+"\n"
                                 + "Incident's SAP order: "+sap.getText()+"\n"+
                                 "Incident's solution creator: "+Data.sol_creator+"\n"+
                                 "Incident's creator user: "+user+"\n"+
                                  "Incident's escalation assign: "+Data.idEscaAssignIncident+"\n"+
                                 "Incident's spare parts: "+spare_parts.getText()+"\n"+
                                 "Incident's file name: "+fileIncident+"\n"+
                                 "Incident's short description: "+Data.short_sol+"\n";
                                 
                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The incident for the problem with id "+Data.id_pb
                                        + " was added with success and these are the details: \n"+incmsg);
                                 
                                 //trimitem pe server
                                List<File> filelist=new ArrayList<>();
                                 for(String f: FilesListView)
                                 {
                                                File file=new File(f);
                                                filelist.add(file);
                                 }
                                savingFiles(filelist,String.valueOf(idInc));
                               
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
                 
                    
            } catch (SQLException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- ParseException: "+ex+"\n\n");
            } catch (ParseException ex) {
                 logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- SQLException: "+ex+"\n\n");
            }
            
            
        }
        
        //caz in care nu e fisier
        else if(isstartdate==false && isenddate==false && isspare==false && FilesListView.isEmpty()==true)
        {
            
            try {
                    String st_date=startdate.getText();
                    String en_date=enddate.getText();
                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    Date dates = sdf.parse(st_date);
                    Date datee=sdf.parse(en_date);
                    long sdmili=dates.getTime();
                    long edmili=datee.getTime();
                    String sd=String.valueOf(sdmili);
                    String ed=String.valueOf(edmili);
                    
                    //setare ticket pentru incident
                    long milliSec = System.currentTimeMillis();  
                    String current_milisec=String.valueOf(milliSec);
                    String ticket="#INC_"+current_milisec;
                    
                    conn = ConnectionDatabase.connectDb();
                    CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                    psincident.setString(1,ticket);
                    psincident.setInt(2, Data.id_pb);
                    psincident.setString(3,sd);
                    psincident.setString(4, ed);
                    psincident.setString(5, Data.status);
                    psincident.setInt(6, 0);
                    psincident.setString(7, Data.sol_creator);
                    psincident.setString(8, Data.sol_escal);
                    psincident.setString(9, user);
                    psincident.setInt(10, Data.idEscaAssignIncident);
                    psincident.setString(11,part_numbere);
                    psincident.setString(12,"");
                    psincident.setString(13,Data.short_sol);
                    if(psincident.executeUpdate()!=0)
                    {
                               int IncidentId=ConnectionDatabase.getIncidentId(ticket, Data.id_pb, sd, ed, 
                                                                Data.status,  
                                                                Data.sol_creator, user, Data.short_sol);
                                idInc=IncidentId;
                                
                                logger.info("Ticket: "+ticket+"\n"+
                                           "ID PROBLEM: "+Data.id_pb+"\n"+
                                        "Start date in miliseconds: "+sd+"\n"+
                                        "End date in miliseconds: "+ed+"\n"+
                                        "Status: "+Data.status+"\n"+
                                        "SAP: "+sap.getText()+"\n"+
                                        "Sol creator: "+Data.sol_creator+"\n"+
                                        "Creator username: "+user+"\n"+
                                        "Short sol: "+Data.short_sol+"\n\n");
                                
                                logger.info("INCIDENT'S ID in NEW INCIDENT FROM TEMPLATE: "+idInc+"\n");
                                                        
                                
                                String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                "Incident's ticket: "+ticket+"\n"+
                                 "Incident's start date: "+st_date+"\n"+
                                 "Incident's end date: "+en_date+"\n"+
                                 "Incident's status: "+Data.status+"\n"
                                 + "Incident's SAP order: "+sap.getText()+"\n"+
                                 "Incident's solution creator: "+Data.sol_creator+"\n"+
                                 "Incident's creator user: "+user+"\n"+
                                  "Incident's escalation assign: "+Data.idEscaAssignIncident+"\n"+
                                 "Incident's spare parts: "+spare_parts.getText()+"\n"+
                                 "Incident's file name: "+""+"\n"+
                                 "Incident's short description: "+Data.short_sol+"\n";
                                 
                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The incident for the problem with id "+Data.id_pb
                                        + " was added with success and these are the details: \n"+incmsg);
                                 
                                
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
                 
                    
            } catch (SQLException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- ParseException: "+ex+"\n\n");
            } catch (ParseException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- SQLException: "+ex+"\n\n");
            }
            
        }
        
        else if(isstartdate==false && isenddate==false  && isspare==true && FilesListView.isEmpty()==true)
        {
            
            try {
                    String st_date=startdate.getText();
                    String en_date=enddate.getText();
                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    Date dates = sdf.parse(st_date);
                    Date datee=sdf.parse(en_date);
                    long sdmili=dates.getTime();
                    long edmili=datee.getTime();
                    String sd=String.valueOf(sdmili);
                    String ed=String.valueOf(edmili);
                    conn = ConnectionDatabase.connectDb();
                    CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                    
                    //setare ticket pentru incident
                    long milliSec = System.currentTimeMillis();  
                    String current_milisec=String.valueOf(milliSec);
                    String ticket="#INC_"+current_milisec;
                    
                    psincident.setString(1, ticket);
                    psincident.setInt(2, Data.id_pb);
                    psincident.setString(3,sd);
                    psincident.setString(4, ed);
                    psincident.setString(5, Data.status);
                    psincident.setInt(6, 0);
                    psincident.setString(7, Data.sol_creator);
                    psincident.setString(8, Data.sol_escal);
                    psincident.setString(9, user);
                    psincident.setInt(10, Data.idEscaAssignIncident);
                    psincident.setString(11,"");
                    psincident.setString(12,"");
                    psincident.setString(13,Data.short_sol);
                    if(psincident.executeUpdate()!=0)
                    {
                                int IncidentId=ConnectionDatabase.getIncidentId(ticket , Data.id_pb, sd, ed, 
                                                                Data.status, 
                                                                Data.sol_creator, user, Data.short_sol);
                                logger.info("Ticket: "+ticket+"\n"+
                                           "ID PROBLEM: "+Data.id_pb+"\n"+
                                        "Start date in miliseconds: "+sd+"\n"+
                                        "End date in miliseconds: "+ed+"\n"+
                                        "Status: "+Data.status+"\n"+
                                        "SAP: "+sap.getText()+"\n"+
                                        "Sol creator: "+Data.sol_creator+"\n"+
                                        "Creator username: "+user+"\n"+
                                        "Short sol: "+Data.short_sol+"\n\n");
                                idInc=IncidentId;
                                
                                logger.info("INCIDENT'S ID in NEW INCIDENT FROM TEMPLATE: "+idInc+"\n");
                                                        
                                
                                
                                String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                "Incident's ticket: "+Data.incident_ticket+"\n"+
                                 "Incident's start date: "+st_date+"\n"+
                                 "Incident's end date: "+en_date+"\n"+
                                 "Incident's status: "+Data.status+"\n"
                                 + "Incident's SAP order: "+sap.getText()+"\n"+
                                 "Incident's solution creator: "+Data.sol_creator+"\n"+
                                 "Incident's creator user: "+user+"\n"+
                                  "Incident's escalation assign: "+Data.idEscaAssignIncident+"\n"+
                                 "Incident's spare parts: "+spare_parts.getText()+"\n"+
                                 "Incident's file name: "+""+"\n"+
                                 "Incident's short description: "+Data.short_sol+"\n";
                                 
                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The incident for the problem with id "+Data.id_pb
                                        + " was added with success and these are the details: \n"+incmsg);
                                 
                                
                               
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
                 
                    
            } catch (SQLException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- ParseException: "+ex+"\n\n");
            } catch (ParseException ex) {
                 logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- SQLException: "+ex+"\n\n");
            }
        }
       
       }
       
       
       //e sap
       
       
       else
       {
        
        if(isstartdate==true || isenddate==true)
        {
               logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty fields in NEW INCIDENT FROM TEMPLATE!");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Please complete all the required fields!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
                clearField();
        }
        else if(sap.getText().matches("\\d+")==false || spare_parts.getText().length() > 450 || sap.getText().length() > 8
                || startdate.getText().matches(regex)==false || enddate.getText().matches(regex)==false || ok==0)
        {
             logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Incorrect inputs in NEW INCIDENT FROM TEMPLATE!");
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
                clearField();
        }
        
        //caz in care e fisier
        else if(isstartdate==false && isenddate==false && issap == false && isspare==false && FilesListView.isEmpty()==false)
        {
            
            try {
                    String st_date=startdate.getText();
                    String en_date=enddate.getText();
                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    Date dates = sdf.parse(st_date);
                    Date datee=sdf.parse(en_date);
                    long sdmili=dates.getTime();
                    long edmili=datee.getTime();
                    String sd=String.valueOf(sdmili);
                    String ed=String.valueOf(edmili);
                    
                    //setare ticket pentru incident
                    long milliSec = System.currentTimeMillis();  
                    String current_milisec=String.valueOf(milliSec);
                    String ticket="#INC_"+current_milisec;
                    
                    conn = ConnectionDatabase.connectDb();
                    CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                    psincident.setString(1,ticket);
                    psincident.setInt(2, Data.id_pb);
                    psincident.setString(3,sd);
                    psincident.setString(4, ed);
                    psincident.setString(5, Data.status);
                    psincident.setString(6, sap.getText());
                    psincident.setString(7, Data.sol_creator);
                    psincident.setString(8, Data.sol_escal);
                    psincident.setString(9, user);
                    psincident.setInt(10, Data.idEscaAssignIncident);
                    psincident.setString(11,part_numbere);
                    psincident.setString(12,"DummyIncident");
                    psincident.setString(13,Data.short_sol);
                    if(psincident.executeUpdate()!=0)
                    {
                               int IncidentId=ConnectionDatabase.getIncidentId(ticket , Data.id_pb, sd, ed, 
                                                                Data.status, 
                                                                Data.sol_creator, user, Data.short_sol);
                                idInc=IncidentId;
                                
                                logger.info("Ticket: "+ticket+"\n"+
                                           "ID PROBLEM: "+Data.id_pb+"\n"+
                                        "Start date in miliseconds: "+sd+"\n"+
                                        "End date in miliseconds: "+ed+"\n"+
                                        "Status: "+Data.status+"\n"+
                                        "SAP: "+sap.getText()+"\n"+
                                        "Sol creator: "+Data.sol_creator+"\n"+
                                        "Creator username: "+user+"\n"+
                                        "Short sol: "+Data.short_sol+"\n\n");
                                
                                logger.info("INCIDENT'S ID in NEW INCIDENT FROM TEMPLATE: "+idInc+"\n");
                                                        
                                DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
                                Calendar obj = Calendar.getInstance();
                                String date = formatter.format(obj.getTime());
                                                        
                                                        
                                                        
                                String fileIncident="Problem_"+Data.id_pb+"_"+date+"_Incident_"+idInc+"_"+date;
                                
                                
                                conn=ConnectionDatabase.getConnection();
                                CallableStatement updateFileInc = conn.prepareCall("{call updateFileIncident(?,?)}");
                                updateFileInc.setInt(1, IncidentId);
                                updateFileInc.setString(2, fileIncident);
                                
                                if(updateFileInc.executeUpdate()!=0)
                                {
                                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the incident was saved in DB in NEW INCIDENT FROM TEMPLATE!");
                                }
                                else
                                {
                                    logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the "
                                            + " file of the incident in DB in NEW INCIDENT FROM TEMPLATE!");
                                }
                                
                                String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                "Incident's ticket: "+ticket+"\n"+
                                 "Incident's start date: "+st_date+"\n"+
                                 "Incident's end date: "+en_date+"\n"+
                                 "Incident's status: "+Data.status+"\n"
                                 + "Incident's SAP order: "+sap.getText()+"\n"+
                                 "Incident's solution creator: "+Data.sol_creator+"\n"+
                                 "Incident's creator user: "+user+"\n"+
                                  "Incident's escalation assign: "+Data.idEscaAssignIncident+"\n"+
                                 "Incident's spare parts: "+spare_parts.getText()+"\n"+
                                 "Incident's file name: "+fileIncident+"\n"+
                                 "Incident's short description: "+Data.short_sol+"\n";
                                 
                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The incident for the problem with id "+Data.id_pb
                                        + " was added with success and these are the details: \n"+incmsg);
                                 //trimitem pe server
                                 List<File> filelist=new ArrayList<>();
                                 for(String f: FilesListView)
                                 {
                                                File file=new File(f);
                                                filelist.add(file);
                                 }
                                savingFiles(filelist,String.valueOf(idInc));
                                
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
                 
                    
            } catch (SQLException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- ParseException: "+ex+"\n\n");
            } catch (ParseException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- SQLException: "+ex+"\n\n");
            }
            
        }
        
        else if(isstartdate==false && isenddate==false && issap == false && isspare==true && FilesListView.isEmpty()==false)
        {
            
            try {
                    String st_date=startdate.getText();
                    String en_date=enddate.getText();
                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    Date dates = sdf.parse(st_date);
                    Date datee=sdf.parse(en_date);
                    long sdmili=dates.getTime();
                    long edmili=datee.getTime();
                    String sd=String.valueOf(sdmili);
                    String ed=String.valueOf(edmili);
                    conn = ConnectionDatabase.connectDb();
                    CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                    
                    //setare ticket pentru incident
                    long milliSec = System.currentTimeMillis();  
                    String current_milisec=String.valueOf(milliSec);
                    String ticket="#INC_"+current_milisec;
                    
                    psincident.setString(1, ticket);
                    psincident.setInt(2, Data.id_pb);
                    psincident.setString(3,sd);
                    psincident.setString(4, ed);
                    psincident.setString(5, Data.status);
                    psincident.setString(6, sap.getText());
                    psincident.setString(7, Data.sol_creator);
                    psincident.setString(8, Data.sol_escal);
                    psincident.setString(9, user);
                    psincident.setInt(10, Data.idEscaAssignIncident);
                    psincident.setString(11,"");
                    psincident.setString(12,"Dummy Incident");
                    psincident.setString(13,Data.short_sol);
                    if(psincident.executeUpdate()!=0)
                    {
                                int IncidentId=ConnectionDatabase.getIncidentId(ticket , Data.id_pb, sd, ed, 
                                                                Data.status, 
                                                                Data.sol_creator, user, Data.short_sol);
                                logger.info("Ticket: "+ticket+"\n"+
                                           "ID PROBLEM: "+Data.id_pb+"\n"+
                                        "Start date in miliseconds: "+sd+"\n"+
                                        "End date in miliseconds: "+ed+"\n"+
                                        "Status: "+Data.status+"\n"+
                                        "SAP: "+sap.getText()+"\n"+
                                        "Sol creator: "+Data.sol_creator+"\n"+
                                        "Creator username: "+user+"\n"+
                                        "Short sol: "+Data.short_sol+"\n\n");
                                idInc=IncidentId;
                                
                                logger.info("INCIDENT'S ID in NEW INCIDENT FROM TEMPLATE: "+idInc+"\n");
                                                        
                                DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
                                Calendar obj = Calendar.getInstance();
                                String date = formatter.format(obj.getTime());
                                                        
                                                        
                                                        
                                String fileIncident="Problem_"+Data.id_pb+"_"+date+"_Incident_"+idInc+"_"+date;
                                
                                
                                conn=ConnectionDatabase.getConnection();
                                CallableStatement updateFileInc = conn.prepareCall("{call updateFileIncident(?,?)}");
                                updateFileInc.setInt(1, IncidentId);
                                updateFileInc.setString(2, fileIncident);
                                
                                if(updateFileInc.executeUpdate()!=0)
                                {
                                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the incident was saved in DB in NEW INCIDENT FROM TEMPLATE!");
                                }
                                else
                                {
                                    logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the "
                                            + " file of the incident in DB in NEW INCIDENT FROM TEMPLATE!");
                                }
                                
                                String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                "Incident's ticket: "+ticket+"\n"+
                                 "Incident's start date: "+st_date+"\n"+
                                 "Incident's end date: "+en_date+"\n"+
                                 "Incident's status: "+Data.status+"\n"
                                 + "Incident's SAP order: "+sap.getText()+"\n"+
                                 "Incident's solution creator: "+Data.sol_creator+"\n"+
                                 "Incident's creator user: "+user+"\n"+
                                  "Incident's escalation assign: "+Data.idEscaAssignIncident+"\n"+
                                 "Incident's spare parts: "+spare_parts.getText()+"\n"+
                                 "Incident's file name: "+fileIncident+"\n"+
                                 "Incident's short description: "+Data.short_sol+"\n";
                                 
                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The incident for the problem with id "+Data.id_pb
                                        + " was added with success and these are the details: \n"+incmsg);
                                 
                                 //trimitem pe server
                                List<File> filelist=new ArrayList<>();
                                 for(String f: FilesListView)
                                 {
                                                File file=new File(f);
                                                filelist.add(file);
                                 }
                                savingFiles(filelist,String.valueOf(idInc));
                               
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
                 
                    
            } catch (SQLException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- ParseException: "+ex+"\n\n");
            } catch (ParseException ex) {
                 logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- SQLException: "+ex+"\n\n");
            }
            
            
        }
        
        //caz in care nu e fisier
        else if(isstartdate==false && isenddate==false && issap == false && isspare==false && FilesListView.isEmpty()==true)
        {
            
            try {
                    String st_date=startdate.getText();
                    String en_date=enddate.getText();
                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    Date dates = sdf.parse(st_date);
                    Date datee=sdf.parse(en_date);
                    long sdmili=dates.getTime();
                    long edmili=datee.getTime();
                    String sd=String.valueOf(sdmili);
                    String ed=String.valueOf(edmili);
                    
                    //setare ticket pentru incident
                    long milliSec = System.currentTimeMillis();  
                    String current_milisec=String.valueOf(milliSec);
                    String ticket="#INC_"+current_milisec;
                    
                    conn = ConnectionDatabase.connectDb();
                    CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                    psincident.setString(1,ticket);
                    psincident.setInt(2, Data.id_pb);
                    psincident.setString(3,sd);
                    psincident.setString(4, ed);
                    psincident.setString(5, Data.status);
                    psincident.setString(6, sap.getText());
                    psincident.setString(7, Data.sol_creator);
                    psincident.setString(8, Data.sol_escal);
                    psincident.setString(9, user);
                    psincident.setInt(10, Data.idEscaAssignIncident);
                    psincident.setString(11,part_numbere);
                    psincident.setString(12,"");
                    psincident.setString(13,Data.short_sol);
                    if(psincident.executeUpdate()!=0)
                    {
                               int IncidentId=ConnectionDatabase.getIncidentId(ticket, Data.id_pb, sd, ed, 
                                                                Data.status,  
                                                                Data.sol_creator, user, Data.short_sol);
                                idInc=IncidentId;
                                
                                logger.info("Ticket: "+ticket+"\n"+
                                           "ID PROBLEM: "+Data.id_pb+"\n"+
                                        "Start date in miliseconds: "+sd+"\n"+
                                        "End date in miliseconds: "+ed+"\n"+
                                        "Status: "+Data.status+"\n"+
                                        "SAP: "+sap.getText()+"\n"+
                                        "Sol creator: "+Data.sol_creator+"\n"+
                                        "Creator username: "+user+"\n"+
                                        "Short sol: "+Data.short_sol+"\n\n");
                                
                                logger.info("INCIDENT'S ID in NEW INCIDENT FROM TEMPLATE: "+idInc+"\n");
                                                        
                                
                                String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                "Incident's ticket: "+ticket+"\n"+
                                 "Incident's start date: "+st_date+"\n"+
                                 "Incident's end date: "+en_date+"\n"+
                                 "Incident's status: "+Data.status+"\n"
                                 + "Incident's SAP order: "+sap.getText()+"\n"+
                                 "Incident's solution creator: "+Data.sol_creator+"\n"+
                                 "Incident's creator user: "+user+"\n"+
                                  "Incident's escalation assign: "+Data.idEscaAssignIncident+"\n"+
                                 "Incident's spare parts: "+spare_parts.getText()+"\n"+
                                 "Incident's file name: "+""+"\n"+
                                 "Incident's short description: "+Data.short_sol+"\n";
                                 
                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The incident for the problem with id "+Data.id_pb
                                        + " was added with success and these are the details: \n"+incmsg);
                                 
                                
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
                 
                    
            } catch (SQLException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- ParseException: "+ex+"\n\n");
            } catch (ParseException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- SQLException: "+ex+"\n\n");
            }
            
        }
        
        else if(isstartdate==false && isenddate==false && issap == false && isspare==true && FilesListView.isEmpty()==true)
        {
            
            try {
                    String st_date=startdate.getText();
                    String en_date=enddate.getText();
                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    Date dates = sdf.parse(st_date);
                    Date datee=sdf.parse(en_date);
                    long sdmili=dates.getTime();
                    long edmili=datee.getTime();
                    String sd=String.valueOf(sdmili);
                    String ed=String.valueOf(edmili);
                    conn = ConnectionDatabase.connectDb();
                    CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                    
                    //setare ticket pentru incident
                    long milliSec = System.currentTimeMillis();  
                    String current_milisec=String.valueOf(milliSec);
                    String ticket="#INC_"+current_milisec;
                    
                    psincident.setString(1, ticket);
                    psincident.setInt(2, Data.id_pb);
                    psincident.setString(3,sd);
                    psincident.setString(4, ed);
                    psincident.setString(5, Data.status);
                    psincident.setString(6, sap.getText());
                    psincident.setString(7, Data.sol_creator);
                    psincident.setString(8, Data.sol_escal);
                    psincident.setString(9, user);
                    psincident.setInt(10, Data.idEscaAssignIncident);
                    psincident.setString(11,"");
                    psincident.setString(12,"");
                    psincident.setString(13,Data.short_sol);
                    if(psincident.executeUpdate()!=0)
                    {
                                int IncidentId=ConnectionDatabase.getIncidentId(ticket , Data.id_pb, sd, ed, 
                                                                Data.status, 
                                                                Data.sol_creator, user, Data.short_sol);
                                logger.info("Ticket: "+ticket+"\n"+
                                           "ID PROBLEM: "+Data.id_pb+"\n"+
                                        "Start date in miliseconds: "+sd+"\n"+
                                        "End date in miliseconds: "+ed+"\n"+
                                        "Status: "+Data.status+"\n"+
                                        "SAP: "+sap.getText()+"\n"+
                                        "Sol creator: "+Data.sol_creator+"\n"+
                                        "Creator username: "+user+"\n"+
                                        "Short sol: "+Data.short_sol+"\n\n");
                                idInc=IncidentId;
                                
                                logger.info("INCIDENT'S ID in NEW INCIDENT FROM TEMPLATE: "+idInc+"\n");
                                                        
                                
                                
                                String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                "Incident's ticket: "+Data.incident_ticket+"\n"+
                                 "Incident's start date: "+st_date+"\n"+
                                 "Incident's end date: "+en_date+"\n"+
                                 "Incident's status: "+Data.status+"\n"
                                 + "Incident's SAP order: "+sap.getText()+"\n"+
                                 "Incident's solution creator: "+Data.sol_creator+"\n"+
                                 "Incident's creator user: "+user+"\n"+
                                  "Incident's escalation assign: "+Data.idEscaAssignIncident+"\n"+
                                 "Incident's spare parts: "+spare_parts.getText()+"\n"+
                                 "Incident's file name: "+""+"\n"+
                                 "Incident's short description: "+Data.short_sol+"\n";
                                 
                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The incident for the problem with id "+Data.id_pb
                                        + " was added with success and these are the details: \n"+incmsg);
                                 
                                
                               
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
                 
                    
            } catch (SQLException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- ParseException: "+ex+"\n\n");
            } catch (ParseException ex) {
                 logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- SQLException: "+ex+"\n\n");
            }
        }
       }
        
    }
    
    public void clearField()
    {
        sap.setText("");
        spare_parts.setText("");
        
    }
    
    public void setSpares()
    {
        if(!Data.spare_part_numbers.isEmpty())
        {
            if(spare_parts.getText().isEmpty())
            {
                spare_parts.setText(Data.spare_part_numbers+"\n");
            }
            else
            {
                spare_parts.setText(spare_parts.getText()+Data.spare_part_numbers+"\n");
            }
            
            /*String mystring=spare_parts.getText();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The PART NUMBERS from NEW INCIDENT FROM TEMPLATE: \n"+mystring+"\n\n");
            
            String[] items = mystring.split("\n");
            List<String> itemList = Arrays.asList(items);
            List<String> idspares=new ArrayList<>();
            for(String p:itemList)
            {
                int idspare=ConnectionDatabase.getSparePartsId(p);
                idspares.add(String.valueOf(idspare));
            }
            
            String listpartnumbers=idspares.stream().collect(Collectors.joining(";"));
            part_numbere=listpartnumbers;*/
            
           
            
        }
        
        
        
    }
   
     public void opensparepartsadd()
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
                     logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- IOException: "+ex+"\n\n");
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
                   logger.fatal("[FATAL]: NEW INCIDENT FROM TEMPLATE --- IOException: "+ex+"\n\n");
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
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: NEW INCIDENT FROM TEMPLATE STARTED!");
        
        sparepartsaddbtn.setGraphic(addicon);
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        
        btnchooseFile.setGraphic(fileicon);
        deletefilesbtn.setGraphic(deleteicon);
        
        
        //setare data 
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Calendar obj = Calendar.getInstance();
            String date = formatter.format(obj.getTime());
            LocalTime timeObj = LocalTime.now();
            String currentTime = timeObj.toString();
            String time=currentTime.substring(0,5);
            String finaldate=date+" "+time;
           
             //setare startdate
            startdate.setText(finaldate);
        
             //setare enddate
            enddate.setText(finaldate);
            
            
           startdate.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     String regex="^(3[01]|0[1-9]|[12][0-9])\\/(1[0-2]|0[1-9])\\/(-?(?:[1-9][0-9]*)?[0-9]{4})( (?:2[0-3]|[01][0-9]):[0-5][0-9])?$";
                     if (newValue.toString().matches(regex)==true) 
                     { 
                            
                             startdate.setStyle("-fx-border-width: 0.5px ;");
                             int ok=compareDates(newValue.toString(),enddate.getText());
                             
                             if(ok==0)
                             {
                                 startdate.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                             }
                             else
                             {
                                 
                                 startdate.setStyle("-fx-border-width: 0.5px ;");
                                 enddate.setStyle("-fx-border-width: 0.5px ;");
                             }
                            
                     }
                     else if(newValue.toString().matches(regex)==false){
                         
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
                     String regex="^(3[01]|0[1-9]|[12][0-9])\\/(1[0-2]|0[1-9])\\/(-?(?:[1-9][0-9]*)?[0-9]{4})( (?:2[0-3]|[01][0-9]):[0-5][0-9])?$";
                     if (newValue.toString().matches(regex)==true) { 
                            
                             enddate.setStyle("-fx-border-width: 0.5px ;");
                             int ok=compareDates(startdate.getText(),newValue.toString());
                            
                             if(ok==0)
                             {
                                 enddate.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                             }
                             else
                             {
                                 enddate.setStyle("-fx-border-width: 0.5px ;");
                                 startdate.setStyle("-fx-border-width: 0.5px ;");
                             }
                     }
                     else if(newValue.toString().matches(regex)==false)
                     {
                         
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
                     if (newValue.toString().length()>8 || newValue.toString().matches(regexd)==false) { 
                            sap.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                            
                     }
                     else{
                          sap.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                       
           
                 }
                 
                 
             });
             
             
              spare_parts.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>450) { 
                            spare_parts.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                            
                     }
                     else{
                         
                          spare_parts.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                       
           
                 }
                 
                 
             });
            
        
        
    }
    
}
