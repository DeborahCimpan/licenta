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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
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
import toolclass.AutoCompleteComboBoxListener;
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
public class NewIncidentExistProblemController implements Initializable {
    
    private Stage openspareadd;
    
   public org.apache.logging.log4j.Logger logger = LogManager.getLogger(NewIncidentExistProblemController.class);
     @FXML
    private Button cancelbtn;
    
     @FXML
    private Button savebtn;
     
    @FXML
    private ImageView saveicon;

    @FXML
    private ImageView cancelicon;
    
    @FXML
    private Button btnchoosefile;

   @FXML
    private ImageView iconfile;

    @FXML
    private Button deletefilesbtn;

    @FXML
    private ImageView addicon;
    @FXML
    private Button addspare;
    
    
    @FXML
    private TextField startdatefield;

    @FXML
    private TextField enddatefield;
    
     @FXML
    private TextField saporder;
     
     
     
     @FXML
    private TextArea textareasol;
     
      @FXML
    private TextArea sparepartstextarea;
     
      @FXML
    private ComboBox<String> combostatus;

    @FXML
    private ComboBox<String> escalationcombo;
    
    @FXML
    private ComboBox<String> escalstatuscombo;
    @FXML
    private AnchorPane anchorPane;
    
      @FXML
    private ListView<String> listviewfiles;
        
        
    @FXML
    private TextArea textareashortdesc;
    
    @FXML
    private ImageView deleteicon;
    
    ObservableList<String> itemscombostatus=FXCollections.observableArrayList("Select...","solved","unsolved");
    
    ObservableList<String> itemscomboescalstatus=FXCollections.observableArrayList("Select...","yes","no");
    ObservableList<String> itemscomboescal=FXCollections.observableArrayList();
    String fisier=null;
    int id_EscTeam=0;
    String part_numbere;
    Connection conn=null;
    
    public int idInc=-1;
    
    @FXML
    private ScrollPane scrollPane;
    
     public void onStatus()
     {
          if(combostatus.getSelectionModel().isEmpty()==false && combostatus.getValue().toString().equals("unsolved"))
        {
            
            if(textareasol.getText().isEmpty()==false)
            {
                textareasol.clear();
            }
            textareasol.setEditable(false);
            //anchorPane.requestFocus();
        }
        else if(combostatus.getSelectionModel().isEmpty()==false && combostatus.getValue().toString().equals("solved"))
        {
            
            textareasol.setEditable(true);
            //scrollPane.requestFocus();
        }
     }
    
     public void chooseFile()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Choose file button from NEW INCIDENT EXISTING PROBLEM was pressed!\n\n");
        FileChooser filechooser=new FileChooser();
        filechooser.setTitle("Open File Dialog");
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All files","*.*"));
        Stage stage=(Stage)anchorPane.getScene().getWindow();
        
        
        
        List<File> files=filechooser.showOpenMultipleDialog(stage);
        FileChooserTemplate files_object=new FileChooserTemplate(files);
        
        String filemsg="OUTPUT LIST of files selected from NEW INCIDENT EXISTING PROBLEM: \n";
        
        
        int i=0;
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
                                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The "+ i +" file is too big in NEW INCIDENT EXISTING PROBLEM");
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

    public void deleteFiles()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Delete files button from NEW INCIDENT EXISTING PROBLEM was pressed!\n\n");
        ObservableList<String> list = listviewfiles.getSelectionModel().getSelectedItems();
        listviewfiles.getItems().removeAll(list);
        
                    
         logger.info(Log4jConfiguration.currentTime()+"[INFO]: The files that were deleted in NEW INCIDENT  EXISTING PROBLEM: \n"+list+"\n\n");
        
      
    }
    
    public void cancelSelection()
    {
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: The selection of files has been cleared in NEW INCIDENT EXISTING PROBLEM!\n");
             listviewfiles.getSelectionModel().clearSelection();
       
       
    }
    
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from NEW INCIDENT EXISTING PROBLEM was pressed!\n\n");
        Stage s = (Stage) cancelbtn .getScene().getWindow();
        s.close();
    }
    public void openspare()
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
                     logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- IOException: "+ex+"\n\n");
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
                   logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- IOException: "+ex+"\n\n");
               }
        }
    }
    public void onSelectEscalation()
    {
            
            boolean isCombo=escalationcombo.getSelectionModel().isEmpty();
            if(isCombo==false && escalationcombo.getValue().toString().isEmpty()==false)
            {
                String valueesc=escalationcombo.getValue().toString();
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The engineer selected for escalation in NEW INCIDENT EXISTING PROBLEM IS: "+valueesc);
                int index1=valueesc.indexOf("(");
                int index2=valueesc.indexOf(")"); 
                String user=valueesc.substring(index1+1, index2);
                id_EscTeam=ConnectionDatabase.getEscId(user);
            }
            
            
    }
    public  void zip(File path,String directoryName) throws FileNotFoundException, IOException
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: ZIPING the INCIDENT folder in NEW INCIDENT EXISTING PROBLEM...\n");
        File[] files=path.listFiles();
        if(files.length == 0)
          logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT EXISTING PROBLEM---IllegalArgumentException: NO FILES IN THE PATH");
        
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
     public  void zipProblem(File path,String directoryName) throws FileNotFoundException, IOException
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: ZIPING the NEW PROBLEM folder in NEW INCIDENT EXISTING PROBLEM...\n");
        File[] files=path.listFiles();
        if(files.length == 0)
          logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT EXISTING PROBLEM---IllegalArgumentException: NO FILES IN THE PATH");
        
        String address_stream="C:\\"+directoryName+".zip";
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
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The following file is for PROBLEM updated: \n"
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
                   logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- FileNotFoundException: "+ex);
                } catch (IOException ex) {
                    logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- IOException: "+ex);
                }

           }   catch (FileNotFoundException ex) {
               logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- IOException: "+ex);
            } finally {
                try {
                    filestream.close();
                } catch (IOException ex) {
                    logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- IOException: "+ex+"\n\n");
                }
          }
        }
        else
        {
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: The arguments for addingFile must be : FILE,DIRECTORY in NEW INCIDENT EXISTING PROBLEM\n\n");
        }
        
    }
      public void sendOnServer(String source,String destination)
     {
          
          logger.info(Log4jConfiguration.currentTime()+"[INFO]: Sending UPDATE PROBLEM file on SFTP server in NEW INCIDENT EXISTING PROBLEM...\n");
          
          logger.info(Log4jConfiguration.currentTime()+"[INFO]: Connecting to SFTP SERVER in NEW INCIDENT EXISTING PROBLEM...\n");
          try {
             String SFTPHOOST="192.168.168.208";
             int SFTPORT=2222;
             String SFTPUSER="tester";
             String password="password";
             
             JSch jSch=new JSch();
             Session session=null;
             Channel channel =null;
             
              logger.info(Log4jConfiguration.currentTime()+"[INFO]: Creating session in NEW INCIDENT EXISTING PROBLEM...\n");
             
             session=jSch.getSession(SFTPUSER, SFTPHOOST, SFTPORT);
             session.setPassword(password);
             
              logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session created in NEW INCIDENT EXISTING PROBLEM \n");
             
             java.util.Properties config=new java.util.Properties();
             config.put("StrictHostKeyChecking", "no");
             session.setConfig(config);
             session.connect();
             if(session.isConnected()==true)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session is connected in NEW INCIDENT EXISTING PROBLEM \n");
                 
                  logger.info(Log4jConfiguration.currentTime()+"[INFO]: Open channel in NEW INCIDENT EXISTING PROBLEM...\n");
                 channel=session.openChannel("sftp");
                 channel.connect();
                 if(channel.isConnected()==true)
                 {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Channel is connected in NEW INCIDENT EXISTING PROBLEM \n");
                        
                        ChannelSftp sftpChannel = (ChannelSftp) channel;
                        
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Sending file on server  in NEW INCIDENT EXISTING PROBLEM...\n");
                        
                        sftpChannel.put(source, destination);
                        
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+source+ " was send on SFTP server in NEW INCIDENT EXISTING PROBLEM!\n");
                        
                        channel.disconnect();
                        if (session != null) {
                              session.disconnect();
                              logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session disconnected  in NEW INCIDENT EXISTING PROBLEM\n");
                        
                              
                        }

                        sftpChannel.exit();
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Channel disconnected in NEW INCIDENT EXISTING PROBLEM \n\n");
                        
                 }
                 else
                 {
                     logger.info(Log4jConfiguration.currentTime()+"[INFO]: The channel connection has failed  in NEW INCIDENT EXISTING PROBLEM\n\n");
                        
                 }

                
             }
             else
             {
                  logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The connection with session has failed in NEW INCIDENT EXISTING PROBLEM!\n\n");
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
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT EXISTING PROBLEM ---- SftpException: "+ex);
         } catch (JSchException ex) { 
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT EXISTING PROBLEM ---- JSchException: "+ex);
         }
     }
    private static int BINARY_FILE_TYPE;
    
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
    
    public void saveIfIsEscalationNoFile(String SpareParts,String SolCreator)
    {
               
               
               String regex="^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]$";
               String regexd="\\d+";
               int ok=compareDates(startdatefield.getText(),enddatefield.getText());
               //nu e sap
               if(saporder.getText().isEmpty()==true)
               {
                            if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 
                                || sparepartstextarea.getText().length() > 450 ||  textareashortdesc.getText().length() >  300 
                                 || textareasol.getText().length() > 500 )
                        {
                                         logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Incorrect inputs in NEW INCIDENT FROM EXISTING PROBLEM");
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
                                                String user=System.getProperty("user.name");
                                                conn = ConnectionDatabase.connectDb();
                                                //convert startdate,enddate in miliseconds
                                                String st_date=startdatefield.getText();
                                                String en_date=enddatefield.getText();
                                                SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                                                Date dates = sdf.parse(st_date);
                                                Date datee=sdf.parse(en_date);
                                                long sdmili=dates.getTime();
                                                long edmili=datee.getTime();
                                                String sd=String.valueOf(sdmili);
                                                String ed=String.valueOf(edmili);

                                                //assign engineer
                                                CallableStatement psengineer = conn.prepareCall("{call assignEngineer(?,?)}");
                                                psengineer.setInt(1,id_EscTeam);
                                                psengineer.setString(2, sd);
                                                psengineer.execute();






                                                int id_ea=ConnectionDatabase.getEscAssignId(id_EscTeam, sd);

                                                conn=ConnectionDatabase.getConnection();

                                                //setare ticket pentru incident
                                                long milliSec = System.currentTimeMillis();  
                                                String current_milisec=String.valueOf(milliSec);
                                                String ticket="#INC_"+current_milisec;

                                                //add in incident history
                                                            CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                                            psincident.setString(1,ticket);
                                                            psincident.setInt(2, Data.id_pb);
                                                            psincident.setString(3,sd);
                                                            psincident.setString(4, ed);
                                                            psincident.setString(5, combostatus.getValue().toString());
                                                            psincident.setInt(6, 0);
                                                            psincident.setString(7, SolCreator);
                                                            psincident.setString(8, "");
                                                            psincident.setString(9, user);
                                                            psincident.setInt(10, id_ea);
                                                            psincident.setString(11,SpareParts);
                                                            psincident.setString(12,"");
                                                            psincident.setString(13,textareashortdesc.getText());
                                                String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                                                  "Incident's ticket: "+ticket+"\n"+
                                                                  "Incident's start date: "+st_date+"\n"+
                                                                  "Incident's end date: "+en_date+"\n"+
                                                                  "Incident's status: "+combostatus.getValue().toString()+"\n"
                                                                  + "Incident's SAP order: "+saporder.getText()+"\n"+
                                                                  "Incident's solution creator: "+SolCreator+"\n"+
                                                                  "Incident's creator user: "+user+"\n"+
                                                                   "Incident's escalation assign: "+String.valueOf(id_ea)+","+escalationcombo.getValue().toString()+"\n"+
                                                                  "Incident's escalation status: "+escalstatuscombo.getValue().toString()+"\n"+  
                                                                  "Incident's spare parts: "+SpareParts+"\n"+
                                                                  "Incident's file name: "+null+"\n"+
                                                                  "Incident's short description: "+textareashortdesc.getText()+"\n";

                                                if(psincident.executeUpdate()!=0)
                                                {
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
                                                                   //clear_fields();
                                                }
                                                else
                                                          {
                                                              logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong while saving the incident of the problem with id "+
                                                                      Data.id_pb +" in NEW INCIDENT EXISTING PROBLEM");
                                                          }

                                            } catch (ParseException ex) {
                                                logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- SQLException: "+ex+"\n\n");
                                            } catch (SQLException ex) {
                                                logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- ParseException: "+ex+"\n\n");
                                            }
                        }
               }
               //e sap
               else
               {
               
               if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 || saporder.getText().matches(regexd)==false
                       || sparepartstextarea.getText().length() > 450 
                       || textareashortdesc.getText().length() > 300 || textareasol.getText().length() > 500 ||
                                    saporder.getText().length() > 8)
               {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Incorrect inputs in NEW INCIDENT FROM EXISTING PROBLEM");
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
                                       String user=System.getProperty("user.name");
                                       conn = ConnectionDatabase.connectDb();
                                       //convert startdate,enddate in miliseconds
                                       String st_date=startdatefield.getText();
                                       String en_date=enddatefield.getText();
                                       SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                                       Date dates = sdf.parse(st_date);
                                       Date datee=sdf.parse(en_date);
                                       long sdmili=dates.getTime();
                                       long edmili=datee.getTime();
                                       String sd=String.valueOf(sdmili);
                                       String ed=String.valueOf(edmili);

                                       //assign engineer
                                       CallableStatement psengineer = conn.prepareCall("{call assignEngineer(?,?)}");
                                       psengineer.setInt(1,id_EscTeam);
                                       psengineer.setString(2, sd);
                                       psengineer.execute();
                                       
                                                                    


                                     

                                       int id_ea=ConnectionDatabase.getEscAssignId(id_EscTeam, sd);
                                       
                                       conn=ConnectionDatabase.getConnection();
                                       
                                       //setare ticket pentru incident
                                       long milliSec = System.currentTimeMillis();  
                                       String current_milisec=String.valueOf(milliSec);
                                       String ticket="#INC_"+current_milisec;

                                       //add in incident history
                                                   CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                                   psincident.setString(1,ticket);
                                                   psincident.setInt(2, Data.id_pb);
                                                   psincident.setString(3,sd);
                                                   psincident.setString(4, ed);
                                                   psincident.setString(5, combostatus.getValue().toString());
                                                   psincident.setString(6, saporder.getText());
                                                   psincident.setString(7, SolCreator);
                                                   psincident.setString(8, "");
                                                   psincident.setString(9, user);
                                                   psincident.setInt(10, id_ea);
                                                   psincident.setString(11,SpareParts);
                                                   psincident.setString(12,"");
                                                   psincident.setString(13,textareashortdesc.getText());
                                       String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                                         "Incident's ticket: "+ticket+"\n"+
                                                         "Incident's start date: "+st_date+"\n"+
                                                         "Incident's end date: "+en_date+"\n"+
                                                         "Incident's status: "+combostatus.getValue().toString()+"\n"
                                                         + "Incident's SAP order: "+saporder.getText()+"\n"+
                                                         "Incident's solution creator: "+SolCreator+"\n"+
                                                         "Incident's creator user: "+user+"\n"+
                                                          "Incident's escalation assign: "+String.valueOf(id_ea)+","+escalationcombo.getValue().toString()+"\n"+
                                                         "Incident's escalation status: "+escalstatuscombo.getValue().toString()+"\n"+  
                                                         "Incident's spare parts: "+SpareParts+"\n"+
                                                         "Incident's file name: "+null+"\n"+
                                                         "Incident's short description: "+textareashortdesc.getText()+"\n";

                                       if(psincident.executeUpdate()!=0)
                                       {
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
                                                          //clear_fields();
                                       }
                                       else
                                                 {
                                                     logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong while saving the incident of the problem with id "+
                                                             Data.id_pb +" in NEW INCIDENT EXISTING PROBLEM");
                                                 }

                                   } catch (ParseException ex) {
                                       logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- SQLException: "+ex+"\n\n");
                                   } catch (SQLException ex) {
                                       logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- ParseException: "+ex+"\n\n");
                                   }
               }
               }
               
             
         
    }
    public void saveIfIsEscalationFile(String SpareParts,String SolCreator)
    {
               
               
               String regex="^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]$";
               String regexd="\\d+";
               int ok=compareDates(startdatefield.getText(),enddatefield.getText());
               
               if(saporder.getText().isEmpty()==true)
               {
                   
               
               if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 
                       || sparepartstextarea.getText().length() > 450 
                       || textareashortdesc.getText().length() > 300 || textareasol.getText().length() > 500)
               {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Incorrect inputs in NEW INCIDENT FROM EXISTING PROBLEM");
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
                                       String user=System.getProperty("user.name");
                                       conn = ConnectionDatabase.connectDb();
                                       //convert startdate,enddate in miliseconds
                                       String st_date=startdatefield.getText();
                                       String en_date=enddatefield.getText();
                                       SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                                       Date dates = sdf.parse(st_date);
                                       Date datee=sdf.parse(en_date);
                                       long sdmili=dates.getTime();
                                       long edmili=datee.getTime();
                                       String sd=String.valueOf(sdmili);
                                       String ed=String.valueOf(edmili);

                                       //assign engineer
                                       CallableStatement psengineer = conn.prepareCall("{call assignEngineer(?,?)}");
                                       psengineer.setInt(1,id_EscTeam);
                                       psengineer.setString(2, sd);
                                       psengineer.execute();
                                       
                                       

                                       int id_ea=ConnectionDatabase.getEscAssignId(id_EscTeam, sd);

                                       //setare ticket pentru incident
                                       long milliSec = System.currentTimeMillis();  
                                       String current_milisec=String.valueOf(milliSec);
                                       String ticket="#INC_"+current_milisec;
                                       conn=ConnectionDatabase.getConnection();
                                       //add in incident history
                                                   CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                                   psincident.setString(1,ticket);
                                                   psincident.setInt(2, Data.id_pb);
                                                   psincident.setString(3,sd);
                                                   psincident.setString(4, ed);
                                                   psincident.setString(5, combostatus.getValue().toString());
                                                   psincident.setInt(6, 0);
                                                   psincident.setString(7, SolCreator);
                                                   psincident.setString(8, "");
                                                   psincident.setString(9, user);
                                                   psincident.setInt(10, id_ea);
                                                   psincident.setString(11,SpareParts);
                                                   psincident.setString(12,"DummyIncident");
                                                   psincident.setString(13,textareashortdesc.getText());
                                       

                                       if(psincident.executeUpdate()!=0)
                                       {
                                                         
                                                        int IncidentId=ConnectionDatabase.getIncidentId(ticket, Data.id_pb, sd, ed, 
                                                                combostatus.getValue().toString(), 
                                                                SolCreator, user, textareashortdesc.getText());
                                                        idInc=IncidentId;
                                                        
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
                                                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the incident was saved in DB in NEW INCIDENT NEW PROBLEM!");
                                                        }
                                                        else
                                                        {
                                                            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the "
                                                                    + " file of the incident in DB in NEW INCIDENT EXISTING PROBLEM!");
                                                        }
                                                        
                                                        String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                                         "Incident's ticket: "+ticket+"\n"+
                                                         "Incident's start date: "+st_date+"\n"+
                                                         "Incident's end date: "+en_date+"\n"+
                                                         "Incident's status: "+combostatus.getValue().toString()+"\n"
                                                         + "Incident's SAP order: "+saporder.getText()+"\n"+
                                                         "Incident's solution creator: "+SolCreator+"\n"+
                                                         "Incident's creator user: "+user+"\n"+
                                                          "Incident's escalation assign: "+String.valueOf(id_ea)+","+escalationcombo.getValue().toString()+"\n"+
                                                         "Incident's escalation status: "+escalstatuscombo.getValue().toString()+"\n"+  
                                                         "Incident's spare parts: "+SpareParts+"\n"+
                                                         "Incident's file name: "+fileIncident+"\n"+
                                                         "Incident's short description: "+textareashortdesc.getText()+"\n";
                                                        
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
                                                          //clear_fields();
                                       }
                                       else
                                                 {
                                                     logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong while saving the incident of the problem with id "+
                                                             Data.id_pb +" in NEW INCIDENT EXISTING PROBLEM");
                                                 }

                                   } catch (ParseException ex) {
                                       logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- SQLException: "+ex+"\n\n");
                                   } catch (SQLException ex) {
                                       logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- ParseException: "+ex+"\n\n");
                                   }
               }
               }
               //e sap
               else
               {
               
               if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 || saporder.getText().matches(regexd)==false
                       || sparepartstextarea.getText().length() > 450
                       || textareashortdesc.getText().length() > 300 || textareasol.getText().length() > 500 ||
                                    saporder.getText().length() > 8)
               {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Incorrect inputs in NEW INCIDENT FROM EXISTING PROBLEM");
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
                                       String user=System.getProperty("user.name");
                                       conn = ConnectionDatabase.connectDb();
                                       //convert startdate,enddate in miliseconds
                                       String st_date=startdatefield.getText();
                                       String en_date=enddatefield.getText();
                                       SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");

                                       Date dates = sdf.parse(st_date);
                                       Date datee=sdf.parse(en_date);
                                       long sdmili=dates.getTime();
                                       long edmili=datee.getTime();
                                       String sd=String.valueOf(sdmili);
                                       String ed=String.valueOf(edmili);

                                       //assign engineer
                                       CallableStatement psengineer = conn.prepareCall("{call assignEngineer(?,?)}");
                                       psengineer.setInt(1,id_EscTeam);
                                       psengineer.setString(2, sd);
                                       psengineer.execute();
                                       
                                       

                                       int id_ea=ConnectionDatabase.getEscAssignId(id_EscTeam, sd);

                                       //setare ticket pentru incident
                                       long milliSec = System.currentTimeMillis();  
                                       String current_milisec=String.valueOf(milliSec);
                                       String ticket="#INC_"+current_milisec;
                                       conn=ConnectionDatabase.getConnection();
                                       //add in incident history
                                                   CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                                   psincident.setString(1,ticket);
                                                   psincident.setInt(2, Data.id_pb);
                                                   psincident.setString(3,sd);
                                                   psincident.setString(4, ed);
                                                   psincident.setString(5, combostatus.getValue().toString());
                                                   psincident.setString(6, saporder.getText());
                                                   psincident.setString(7, SolCreator);
                                                   psincident.setString(8, "");
                                                   psincident.setString(9, user);
                                                   psincident.setInt(10, id_ea);
                                                   psincident.setString(11,SpareParts);
                                                   psincident.setString(12,"DummyIncident");
                                                   psincident.setString(13,textareashortdesc.getText());
                                       

                                       if(psincident.executeUpdate()!=0)
                                       {
                                                         
                                                        int IncidentId=ConnectionDatabase.getIncidentId(ticket, Data.id_pb, sd, ed, 
                                                                combostatus.getValue().toString(), 
                                                                SolCreator, user, textareashortdesc.getText());
                                                        idInc=IncidentId;
                                                        
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
                                                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the incident was saved in DB in NEW INCIDENT NEW PROBLEM!");
                                                        }
                                                        else
                                                        {
                                                            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the "
                                                                    + " file of the incident in DB in NEW INCIDENT EXISTING PROBLEM!");
                                                        }
                                                        
                                                        String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                                         "Incident's ticket: "+ticket+"\n"+
                                                         "Incident's start date: "+st_date+"\n"+
                                                         "Incident's end date: "+en_date+"\n"+
                                                         "Incident's status: "+combostatus.getValue().toString()+"\n"
                                                         + "Incident's SAP order: "+saporder.getText()+"\n"+
                                                         "Incident's solution creator: "+SolCreator+"\n"+
                                                         "Incident's creator user: "+user+"\n"+
                                                          "Incident's escalation assign: "+String.valueOf(id_ea)+","+escalationcombo.getValue().toString()+"\n"+
                                                         "Incident's escalation status: "+escalstatuscombo.getValue().toString()+"\n"+  
                                                         "Incident's spare parts: "+SpareParts+"\n"+
                                                         "Incident's file name: "+fileIncident+"\n"+
                                                         "Incident's short description: "+textareashortdesc.getText()+"\n";
                                                        
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
                                                          //clear_fields();
                                       }
                                       else
                                                 {
                                                     logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong while saving the incident of the problem with id "+
                                                             Data.id_pb +" in NEW INCIDENT EXISTING PROBLEM");
                                                 }

                                   } catch (ParseException ex) {
                                       logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- SQLException: "+ex+"\n\n");
                                   } catch (SQLException ex) {
                                       logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- ParseException: "+ex+"\n\n");
                                   }
               }
               }
               
             
    }
    public void saveIfNoEscalationNoFile(String SpareParts,String SolCreator)
    {
                
               String regex="^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]$";
               String regexd="\\d+";
               int ok=compareDates(startdatefield.getText(),enddatefield.getText());
               
               if(saporder.getText().isEmpty()==true)
               {
                   
                        if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 
                                || sparepartstextarea.getText().length() > 450 ||  textareashortdesc.getText().length() >  50 
                                || textareashortdesc.getText().length() > 300 || textareasol.getText().length() > 500)
                        {
                                         logger.error(Log4jConfiguration.currentTime()+"[ERROR]: "+"Incorrect inputs in NEW INCIDENT FROM EXISTING PROBLEM!");
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
                                                String user=System.getProperty("user.name");
                                                conn = ConnectionDatabase.connectDb();
                                                //convert startdate,enddate in miliseconds
                                                String st_date=startdatefield.getText();
                                                String en_date=enddatefield.getText();
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


                                                //add in incident history
                                                            CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                                            psincident.setString(1, ticket);
                                                            psincident.setInt(2, Data.id_pb);
                                                            psincident.setString(3,sd);
                                                            psincident.setString(4, ed);
                                                            psincident.setString(5, combostatus.getValue().toString());
                                                            psincident.setInt(6, 0);
                                                            psincident.setString(7, SolCreator);
                                                            psincident.setString(8, "");
                                                            psincident.setString(9, user);
                                                            psincident.setInt(10, 0);
                                                            psincident.setString(11,SpareParts);
                                                            psincident.setString(12,"");
                                                            psincident.setString(13,textareashortdesc.getText());

                                                 String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                                                  "Incident's ticket: "+ticket+"\n"+
                                                                  "Incident's start date: "+st_date+"\n"+
                                                                  "Incident's end date: "+en_date+"\n"+
                                                                  "Incident's status: "+combostatus.getValue().toString()+"\n"
                                                                  + "Incident's SAP order: "+saporder.getText()+"\n"+
                                                                  "Incident's solution creator: "+SolCreator+"\n"+
                                                                  "Incident's creator user: "+user+"\n"+
                                                                   "Incident's escalation assign: 0"+"\n"+
                                                                  "Incident's escalation status: "+escalstatuscombo.getValue().toString()+"\n"+  
                                                                  "Incident's spare parts: "+SpareParts+"\n"+
                                                                  "Incident's file name: "+"null"+"\n"+
                                                                  "Incident's short description: "+textareashortdesc.getText()+"\n";

                                                if(psincident.executeUpdate()!=0)
                                                {
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
                                                                   //clear_fields();
                                                }
                                                else
                                                {
                                                         logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong while saving the incident of the problem with id "+
                                                         Data.id_pb);
                                                 }

                                            } catch (ParseException ex) {
                                                logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- ParseException: "+ex+"\n\n");
                                            } catch (SQLException ex) {
                                                 logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- SQLException: "+ex+"\n");
                                            }
                        }
            
               }
               //e sap
               else
               {
               
               if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 || saporder.getText().matches(regexd)==false
                       || sparepartstextarea.getText().length() > 450 
                       || textareashortdesc.getText().length() > 300 || textareasol.getText().length() > 500 ||
                                    saporder.getText().length() > 8)
               {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: "+"Incorrect inputs in NEW INCIDENT FROM EXISTING PROBLEM!");
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
                                       String user=System.getProperty("user.name");
                                       conn = ConnectionDatabase.connectDb();
                                       //convert startdate,enddate in miliseconds
                                       String st_date=startdatefield.getText();
                                       String en_date=enddatefield.getText();
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


                                       //add in incident history
                                                   CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                                   psincident.setString(1, ticket);
                                                   psincident.setInt(2, Data.id_pb);
                                                   psincident.setString(3,sd);
                                                   psincident.setString(4, ed);
                                                   psincident.setString(5, combostatus.getValue().toString());
                                                   psincident.setString(6, saporder.getText());
                                                   psincident.setString(7, SolCreator);
                                                   psincident.setString(8, "");
                                                   psincident.setString(9, user);
                                                   psincident.setInt(10, 0);
                                                   psincident.setString(11,SpareParts);
                                                   psincident.setString(12,"");
                                                   psincident.setString(13,textareashortdesc.getText());
                                                   
                                        String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                                         "Incident's ticket: "+ticket+"\n"+
                                                         "Incident's start date: "+st_date+"\n"+
                                                         "Incident's end date: "+en_date+"\n"+
                                                         "Incident's status: "+combostatus.getValue().toString()+"\n"
                                                         + "Incident's SAP order: "+saporder.getText()+"\n"+
                                                         "Incident's solution creator: "+SolCreator+"\n"+
                                                         "Incident's creator user: "+user+"\n"+
                                                          "Incident's escalation assign: 0"+"\n"+
                                                         "Incident's escalation status: "+escalstatuscombo.getValue().toString()+"\n"+  
                                                         "Incident's spare parts: "+SpareParts+"\n"+
                                                         "Incident's file name: "+"null"+"\n"+
                                                         "Incident's short description: "+textareashortdesc.getText()+"\n";

                                       if(psincident.executeUpdate()!=0)
                                       {
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
                                                          //clear_fields();
                                       }
                                       else
                                       {
                                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong while saving the incident of the problem with id "+
                                                Data.id_pb);
                                        }

                                   } catch (ParseException ex) {
                                       logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- ParseException: "+ex+"\n\n");
                                   } catch (SQLException ex) {
                                        logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- SQLException: "+ex+"\n");
                                   }
               }
               }
                
    }
     public void saveIfNoEscalationFile(String SpareParts,String SolCreator)
    {
                
               String regex="^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]$";
               String regexd="\\d+";
               int ok=compareDates(startdatefield.getText(),enddatefield.getText());
               
               if(saporder.getText().isEmpty()==true)
               {
                   if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 
                       || sparepartstextarea.getText().length() > 450 
                       || textareashortdesc.getText().length() > 300 || textareasol.getText().length() > 500)
               {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: "+"Incorrect inputs in NEW INCIDENT FROM EXISTING PROBLEM!");
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
                                       String user=System.getProperty("user.name");
                                       conn = ConnectionDatabase.connectDb();
                                       //convert startdate,enddate in miliseconds
                                       String st_date=startdatefield.getText();
                                       String en_date=enddatefield.getText();
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


                                       //add in incident history
                                                   CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                                   psincident.setString(1, ticket);
                                                   psincident.setInt(2, Data.id_pb);
                                                   psincident.setString(3,sd);
                                                   psincident.setString(4, ed);
                                                   psincident.setString(5, combostatus.getValue().toString());
                                                   psincident.setInt(6, 0);
                                                   psincident.setString(7, SolCreator);
                                                   psincident.setString(8, "");
                                                   psincident.setString(9, user);
                                                   psincident.setInt(10, 0);
                                                   psincident.setString(11,SpareParts);
                                                   psincident.setString(12,"DummyIncident");
                                                   psincident.setString(13,textareashortdesc.getText());
                                                   
                                       

                                       if(psincident.executeUpdate()!=0)
                                       {
                                                        int IncidentId=ConnectionDatabase.getIncidentId(ticket, Data.id_pb, sd, ed, 
                                                                combostatus.getValue().toString(), 
                                                                SolCreator, user, textareashortdesc.getText());
                                                        idInc=IncidentId;
                                                        
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
                                                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the incident was saved in DB in NEW INCIDENT NEW PROBLEM!");
                                                        }
                                                        else
                                                        {
                                                            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the "
                                                                    + " file of the incident in DB in NEW INCIDENT EXISTING PROBLEM!");
                                                        }
                                                        
                                                         String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                                         "Incident's ticket: "+ticket+"\n"+
                                                         "Incident's start date: "+st_date+"\n"+
                                                         "Incident's end date: "+en_date+"\n"+
                                                         "Incident's status: "+combostatus.getValue().toString()+"\n"
                                                         + "Incident's SAP order: "+saporder.getText()+"\n"+
                                                         "Incident's solution creator: "+SolCreator+"\n"+
                                                         "Incident's creator user: "+user+"\n"+
                                                          "Incident's escalation assign: 0"+"\n"+
                                                         "Incident's escalation status: "+escalstatuscombo.getValue().toString()+"\n"+  
                                                         "Incident's spare parts: "+SpareParts+"\n"+
                                                         "Incident's file name: "+fileIncident+"\n"+
                                                         "Incident's short description: "+textareashortdesc.getText()+"\n";
                                                         
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
                                                          //clear_fields();
                                       }
                                       else
                                       {
                                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong while saving the incident of the problem with id "+
                                                Data.id_pb);
                                        }

                                   } catch (ParseException ex) {
                                       logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- ParseException: "+ex+"\n\n");
                                   } catch (SQLException ex) {
                                        logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- SQLException: "+ex+"\n");
                                   }
               }
               }
               else
               {
               
               
               if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 || saporder.getText().matches(regexd)==false
                       || sparepartstextarea.getText().length() > 450 
                       || textareashortdesc.getText().length() > 300 || textareasol.getText().length() > 500 ||
                                    saporder.getText().length() > 8)
               {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: "+"Incorrect inputs in NEW INCIDENT FROM EXISTING PROBLEM!");
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
                                       String user=System.getProperty("user.name");
                                       conn = ConnectionDatabase.connectDb();
                                       //convert startdate,enddate in miliseconds
                                       String st_date=startdatefield.getText();
                                       String en_date=enddatefield.getText();
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


                                       //add in incident history
                                                   CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                                   psincident.setString(1, ticket);
                                                   psincident.setInt(2, Data.id_pb);
                                                   psincident.setString(3,sd);
                                                   psincident.setString(4, ed);
                                                   psincident.setString(5, combostatus.getValue().toString());
                                                   psincident.setString(6, saporder.getText());
                                                   psincident.setString(7, SolCreator);
                                                   psincident.setString(8, "");
                                                   psincident.setString(9, user);
                                                   psincident.setInt(10, 0);
                                                   psincident.setString(11,SpareParts);
                                                   psincident.setString(12,"DummyIncident");
                                                   psincident.setString(13,textareashortdesc.getText());
                                                   
                                       

                                       if(psincident.executeUpdate()!=0)
                                       {
                                                        int IncidentId=ConnectionDatabase.getIncidentId(ticket, Data.id_pb, sd, ed, 
                                                                combostatus.getValue().toString(), 
                                                                SolCreator, user, textareashortdesc.getText());
                                                        idInc=IncidentId;
                                                        
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
                                                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the incident was saved in DB in NEW INCIDENT NEW PROBLEM!");
                                                        }
                                                        else
                                                        {
                                                            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the "
                                                                    + " file of the incident in DB in NEW INCIDENT EXISTING PROBLEM!");
                                                        }
                                                        
                                                         String incmsg="Details about the incident added for the problem with "+Data.id_pb+" id:\n"+
                                                         "Incident's ticket: "+ticket+"\n"+
                                                         "Incident's start date: "+st_date+"\n"+
                                                         "Incident's end date: "+en_date+"\n"+
                                                         "Incident's status: "+combostatus.getValue().toString()+"\n"
                                                         + "Incident's SAP order: "+saporder.getText()+"\n"+
                                                         "Incident's solution creator: "+SolCreator+"\n"+
                                                         "Incident's creator user: "+user+"\n"+
                                                          "Incident's escalation assign: 0"+"\n"+
                                                         "Incident's escalation status: "+escalstatuscombo.getValue().toString()+"\n"+  
                                                         "Incident's spare parts: "+SpareParts+"\n"+
                                                         "Incident's file name: "+fileIncident+"\n"+
                                                         "Incident's short description: "+textareashortdesc.getText()+"\n";
                                                         
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
                                                          //clear_fields();
                                       }
                                       else
                                       {
                                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong while saving the incident of the problem with id "+
                                                Data.id_pb);
                                        }

                                   } catch (ParseException ex) {
                                       logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- ParseException: "+ex+"\n\n");
                                   } catch (SQLException ex) {
                                        logger.fatal("[FATAL]: NEW INCIDENT EXISTING PROBLEM --- SQLException: "+ex+"\n");
                                   }
               }
               }
            
               
    }
    public void clear_fields()
    {
        saporder.clear();
        sparepartstextarea.clear();
        textareashortdesc.clear();
        
        textareashortdesc.clear();
        textareasol.clear();
        
       
        combostatus.setValue(" ");
        escalstatuscombo.setValue(" ");
        escalationcombo.setValue(" ");
        
        
        
        
        escalationcombo.getItems().clear();
        listviewfiles.getItems().clear();
            
        
        
        
    }
    public void deleteTheOld(String filename)
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Connecting to SFTP SERVER for deleting the OLD problem folder in NEW INCIDENT EXISTING PROBLEM");
         try {
             String SFTPHOOST="10.169.8.193";
             int SFTPORT=22;
             String SFTPUSER="tester";
             String password="password";
             JSch jSch=new JSch();
             Session session=null;
             Channel channel =null;
             
             ChannelSftp channelSftp = null;
             
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: Creating session in NEW INCIDENT EXISTING PROBLEM...\n");
             
             session=jSch.getSession(SFTPUSER, SFTPHOOST, SFTPORT);
             session.setPassword(password);
             
              logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session created in NEW INCIDENT EXISTING PROBLEM \n");
              
             java.util.Properties config=new java.util.Properties();
             config.put("StrictHostKeyChecking", "no");
             session.setConfig(config);
             session.connect();
             if(session.isConnected()==true)
             {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session is connected in NEW INCIDENT EXISTING PROBLEM \n");
                    
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: Open channel in NEW INCIDENT EXISTING PROBLEM...\n");
                    
                    channel=session.openChannel("sftp");
                    channel.connect();
                    
                    if(channel.isConnected()==true)
                    {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Channel is connected in NEW INCIDENT EXISTING PROBLEM \n");
                        ChannelSftp sftpChannel = (ChannelSftp) channel;

                        //delete
                        
                        
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Deleting file from server  in NEW INCIDENT EXISTING PROBLEM...\n");
                        
                        sftpChannel.rm(filename);
                        
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+filename+ " was deleted from SFTP server in NEW INCIDENT EXISTING PROBLEM!\n");

                        //cand e gata
                        channel.disconnect();
                        if (session != null) {
                            session.disconnect();
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session disconnected  in NEW INCIDENT EXISTING PROBLEM\n");
                        }
                        sftpChannel.exit();
                    }
                    else
                    {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The channel connection has failed  in NEW INCIDENT EXISTING PROBLEM\n\n");
                    }

                    
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The connection with session has failed in NEW INCIDENT EXISTING PROBLEM!\n\n");
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
             
             
             
         } catch (JSchException ex) {
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT EXISTING PROBLEM ---- SftpException: "+ex);
         } catch (SftpException ex) {
           logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT EXISTING PROBLEM ---- JSchException: "+ex);
       }
         
    }
    
    public void downloadProblemFile(String fileName)
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Connecting to SFTP SERVER for downloading the problem folder in NEW INCIDENT EXISTING PROBLEM");
         try {
             String SFTPHOOST="10.169.8.193";
             int SFTPORT=22;
             String SFTPUSER="tester";
             String password="password";
             JSch jSch=new JSch();
             Session session=null;
             Channel channel =null;
             
             ChannelSftp channelSftp = null;
             
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: Creating session in NEW INCIDENT EXISTING PROBLEM...\n");
             
             session=jSch.getSession(SFTPUSER, SFTPHOOST, SFTPORT);
             session.setPassword(password);
             
              logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session created in NEW INCIDENT EXISTING PROBLEM \n");
              
             java.util.Properties config=new java.util.Properties();
             config.put("StrictHostKeyChecking", "no");
             session.setConfig(config);
             session.connect();
             if(session.isConnected()==true)
             {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session is connected in NEW INCIDENT EXISTING PROBLEM \n");
                    
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: Open channel in NEW INCIDENT EXISTING PROBLEM...\n");
                    
                    channel=session.openChannel("sftp");
                    channel.connect();
                    
                    if(channel.isConnected()==true)
                    {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Channel is connected in NEW INCIDENT EXISTING PROBLEM \n");
                        ChannelSftp sftpChannel = (ChannelSftp) channel;

                        //download
                        String source1=fileName;
                        String destination1="C:\\"+fileName;
                        
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Downloading file from server  in NEW INCIDENT EXISTING PROBLEM...\n");
                        
                        sftpChannel.get(source1, destination1);
                        
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+fileName+ " was download from SFTP server in NEW INCIDENT EXISTING PROBLEM!\n");

                        //cand e gata
                        channel.disconnect();
                        if (session != null) {
                            session.disconnect();
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session disconnected  in NEW INCIDENT EXISTING PROBLEM\n");
                        }
                        sftpChannel.exit();
                    }
                    else
                    {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The channel connection has failed  in NEW INCIDENT EXISTING PROBLEM\n\n");
                    }

                    
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The connection with session has failed in NEW INCIDENT EXISTING PROBLEM!\n\n");
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
             
             
             
         } catch (JSchException ex) {
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT EXISTING PROBLEM ---- SftpException: "+ex);
         } catch (SftpException ex) {
           logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT EXISTING PROBLEM ---- JSchException: "+ex);
       }
         
         
    }
        public void unzipFolder(Path source, Path target) throws IOException {
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: UNZIPPING PROBLEM FOLDER in NEW INCIDENT EXISTING PROBLEM...");
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source.toFile()))) {

            // list files in zip
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {

                boolean isDirectory = false;
                
                if (zipEntry.getName().endsWith(File.separator)) {
                    isDirectory = true;
                }
                
                Path newPath = zipSlipProtect(zipEntry, target);

                if (isDirectory) {
                    Files.createDirectories(newPath);
                } else {

                    
                    if (newPath.getParent() != null) {
                        if (Files.notExists(newPath.getParent())) {
                            Files.createDirectories(newPath.getParent());
                        }
                    }

                    // copy files, nio
                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The problem folder was unzipped with success in NEW INCIDENT EXISTING PROBLEM: \n"+target);
 
                }

                zipEntry = zis.getNextEntry();

            }
            zis.closeEntry();
            

        }

    }

    // protect zip slip attack
    public Path zipSlipProtect(ZipEntry zipEntry, Path targetDir)
        throws IOException {

        // test zip slip vulnerability
        // Path targetDirResolved = targetDir.resolve("../../" + zipEntry.getName());

        Path targetDirResolved = targetDir.resolve(zipEntry.getName());

        // make sure normalized file still has targetDir as its prefix
        // else throws exception
        Path normalizePath = targetDirResolved.normalize();
        if (!normalizePath.startsWith(targetDir)) {
            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT EXISTING PROBLEM ---- IOException: "+"Bad zip entry: "+zipEntry.getName());
            //throw new IOException("Bad zip entry: " + zipEntry.getName());
        }

        return normalizePath;
    }


     public void savingFiles(List<File> listFiles,String idIncident) 
    {
            
            List<String> list=new ArrayList<>();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: SAVING FILE of the PROBLEM in NEW INCIDENT EXISTING PROBLEM\n");     
             
             
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
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: Creating the folder for the INCIDENT in NEW INCIDENT NEW PROBLEM\n");
                  File incident=new File("./","Problem_"+Data.id_pb+"_"+date+"_"+"Incident_"+idIncident+"_"+date);
                  if(incident.mkdir()==true)
                  {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+incident.getName()+" was created in NEW INCIDENT EXISTING PROBLEM\n");
                  }
                  else
                  {
                         logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while creating the folder "+incident.getName()+" in NEW INCIDENT EXISTING PROBLEM\n");
                  }
                  //adaugam in el fisierele
                  if(incident.exists()==true)
                  {
                try {
                    list.add(incident.getName());
                    
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+incident.getName()+" exists in NEW INCIDENT EXISTING PROBLEM");
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
                        logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT EXISTING PROBLEM --- IOException: "+ex);
                    }

                         
                      
             }
             else
             {
                      logger.error(Log4jConfiguration.currentTime()+"[ERROR]: "+incident.getName()+" doesn't exists in NEW INCIDENT EXISTING PROBLEM");
             }
                  
                  
                  
    }
    
    
    public void onSave()
    {
        
       boolean isStartDate=startdatefield.getText().isEmpty();
       boolean isEndDate=enddatefield.getText().isEmpty();
       boolean isSap=saporder.getText().isEmpty();
       boolean isShortDesc=textareashortdesc.getText().isEmpty();
       boolean isStatus=combostatus.getSelectionModel().isEmpty();
       boolean isEscalation=escalstatuscombo.getSelectionModel().isEmpty();
       boolean isSpare=sparepartstextarea.getText().isEmpty();
       boolean isDesc=textareashortdesc.getText().isEmpty();
       boolean isSolCreator=textareasol.getText().isEmpty();
       
       List<String> FilesListView=new ArrayList<>();
       for(String el: listviewfiles.getItems())
       {
           FilesListView.add(el);
       }
       
       convertSparePartstoDB();
       
       //campurile obligatorii
       if(isStartDate==true || isEndDate==true ||  isShortDesc==true 
               ||isDesc==true || combostatus.getValue().toString().equals("Select...")
               || escalstatuscombo.getValue().toString().equals("Select..."))
       {
               logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty fields in NEW INCIDENT FROM EXISTING PROBLEM!");
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
           
       }
       //solved,spare,escaladare,fisiere-vf
       
       else if(isStatus==false && combostatus.getValue().toString().equals("solved"))
       {
           
           
           if(isEscalation==false && escalstatuscombo.getValue().toString().equals("yes"))
           {
                        
               if(escalationcombo.getSelectionModel().isEmpty()==false && escalationcombo.getValue().toString().equals("Select..."))
               {
                   logger.error(Log4jConfiguration.currentTime()+"[ERROR]: No engineer was selected and the status of escalation is 'YES' in NEW INCIDENT FROM EXISTING PROBLEM");
                     //Creating a dialog
                     Dialog<String> dialog = new Dialog<String>();
                     //Setting the title
                     Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                     ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                     //Setting the content of the dialog
                     dialog.setContentText("Please select a person for escalation!");
                     //Adding buttons to the dialog pane
                     dialog.getDialogPane().getButtonTypes().add(type);
                     dialog.showAndWait();
               }
               else
               {
                   
                    if(isSolCreator==true)
                    {
                        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: A 'solved' problem status must have a solution in NEW INCIDENT FROM EXISTING PROBLEM");
                         //Creating a dialog
                         Dialog<String> dialog = new Dialog<String>();
                         //Setting the title
                         Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                         ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                         //Setting the content of the dialog
                         dialog.setContentText("Please write down a solution!");
                         //Adding buttons to the dialog pane
                         dialog.getDialogPane().getButtonTypes().add(type);
                         dialog.showAndWait();
                    }
                    else
                    {
                            if(isSpare==true)
                            {


                                         if(FilesListView.isEmpty()==true)
                                        {
                                            //solved,escalare,no spare,no files
                                            saveIfIsEscalationNoFile("",textareasol.getText());
                                        }
                                         else 
                                        {
                                            //solved,escalare,no spare,files
                                            List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            saveIfIsEscalationFile("",textareasol.getText());
                                            savingFiles(filelist,String.valueOf(idInc));
                                            
                                            
                                        }



                            }
                            else if(isSpare==false)
                            {

                                    if(FilesListView.isEmpty()==true)
                                    {
                                        //solved,escalare,spare,no files
                                        saveIfIsEscalationNoFile(part_numbere,textareasol.getText());
                                    }
                                    else
                                    {
                                        //solved,escalare,spare,files
                                        
                                            List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            saveIfIsEscalationFile(part_numbere,textareasol.getText());
                                            savingFiles(filelist,String.valueOf(idInc));
                                           
                                    }


                            }
                    }
                    
               }
         }
           else if(isEscalation==false && escalstatuscombo.getValue().toString().equals("no"))
           {
                    if(isSolCreator==true)
                    {
                        logger.error(Log4jConfiguration.currentTime()+"[ERROR]: A solved status of a problem must have  a solution in NEW INCIDENT FROM EXISTING PROBLEM");
                           //Creating a dialog
                         Dialog<String> dialog = new Dialog<String>();
                         //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                         ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                         //Setting the content of the dialog
                         dialog.setContentText("Please write down a solution!");
                         //Adding buttons to the dialog pane
                         dialog.getDialogPane().getButtonTypes().add(type);
                         dialog.showAndWait();
                    }
                    else
                    {
                           if(isSpare==true)
                            {


                                         if(FilesListView.isEmpty()==true)
                                        {
                                            //no spare,solved,no files,no escalare
                                            saveIfNoEscalationNoFile("",textareasol.getText());
                                            
                                        }
                                        else
                                        {
                                            //solved,no spare,no escalare,files
                                            List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            saveIfNoEscalationFile("",textareasol.getText());
                                            savingFiles(filelist,String.valueOf(idInc));
                                            
                                        }



                            }
                            else if(isSpare==false)
                            {

                                    if(FilesListView.isEmpty()==true)
                                    {
                                        //solved,spare,no escalare,no files
                                        saveIfNoEscalationNoFile(part_numbere,textareasol.getText());
                                         
                                    }
                                    else
                                    {
                                         //solved,spare,no escalare,files
                                         List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            saveIfNoEscalationFile(part_numbere,textareasol.getText());
                                            savingFiles(filelist,String.valueOf(idInc));
                                    }


                            }
                    }
           }
           
     }
       
       
       //unsolved,escalare,solcreator,spare,files-vf
       
       else if(isStatus==false &&  combostatus.getValue().toString().equals("unsolved"))
       {
           
            
           if(isEscalation==false && escalstatuscombo.getValue().toString().equals("yes"))
           {
               if(escalationcombo.getSelectionModel().isEmpty()==false && escalationcombo.getValue().toString().equals("Select..."))
               {
                   logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No engineer was selected and the status of escalation is 'YES' in NEW INCIDENT FROM EXISTING PROBLEM");
                   //Creating a dialog
                     Dialog<String> dialog = new Dialog<String>();
                     //Setting the title
                     Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                     ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                     //Setting the content of the dialog
                     dialog.setContentText("Please select a person for escalation!");
                     //Adding buttons to the dialog pane
                     dialog.getDialogPane().getButtonTypes().add(type);
                     dialog.showAndWait();
               }
               else
               {
                   
                    if(isSpare==true)
                            {


                                         if(FilesListView.isEmpty()==true)
                                        {
                                            //unsolved,escalare,no spare,no files
                                            saveIfIsEscalationNoFile("","");
                                        }
                                         else 
                                        {
                                            //unsolved,escalare,no spare,files
                                            List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            saveIfIsEscalationFile("","");
                                            savingFiles(filelist,String.valueOf(idInc));
                                            
                                        }



                            }
                            else if(isSpare==false)
                            {

                                    if(FilesListView.isEmpty()==true)
                                    {
                                        //unsolved,escalare,spare,no files
                                        saveIfIsEscalationNoFile(part_numbere,"");
                                    }
                                    else
                                    {
                                        //unsolved,escalare,spare,files
                                        
                                            List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            saveIfIsEscalationFile(part_numbere,"");
                                            savingFiles(filelist,String.valueOf(idInc));
                                            
                                    }


                            }
                    
                            
                    
               }
         }
           else if(isEscalation==false && escalstatuscombo.getValue().toString().equals("no"))
           {
                   
                            if(isSpare==true)
                            {


                                         if(FilesListView.isEmpty()==true)
                                        {
                                            //no spare,unsolved,no files,no escalare
                                            saveIfNoEscalationNoFile("","");
                                            
                                        }
                                        else
                                        {
                                            //unsolved,no spare,no escalare,files
                                            List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            saveIfNoEscalationFile("","");
                                            savingFiles(filelist,String.valueOf(idInc));
                                            
                                        }



                            }
                            else if(isSpare==false)
                            {

                                    if(FilesListView.isEmpty()==true)
                                    {
                                        //solved,spare,no escalare,no files
                                        saveIfNoEscalationNoFile(part_numbere,"");
                                         
                                    }
                                    else
                                    {
                                         //solved,spare,no escalare,files
                                         List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            saveIfNoEscalationFile(part_numbere,"");
                                            savingFiles(filelist,String.valueOf(idInc));
                                    }


                            }
                            
           }

            
       }
       
       
        
               
    }
    
    public void setSpares()
    {
        if(!Data.spare_part_numbers.isEmpty())
        {
            if(sparepartstextarea.getText().isEmpty())
            {
                sparepartstextarea.setText(Data.spare_part_numbers);
            }
            else
            {
                sparepartstextarea.setText(sparepartstextarea.getText()+"\n"+Data.spare_part_numbers);
            }
            
           /* String mystring=sparepartstextarea.getText();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The PART NUMBERS from NEW INCIDENT EXISTING PROBLEM: \n"+mystring+"\n\n");
            
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
    
    public void convertSparePartstoDB()
    {
            String mystring=sparepartstextarea.getText();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The PART NUMBERS from NEW INCIDENT EXISTING PROBLEM: \n"+mystring+"\n\n");
            
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
    
    
    public void onEscal()
    {
        if(escalstatuscombo.getValue().equals("yes"))
        {
                     
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: Escalation status YES in NEW INCIDENT FROM EXISTING PROBLEM \n");
                     
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
                          escalationcombo.setItems(itemscomboescal); 
                          
                          new AutoCompleteComboBoxListener<>(escalationcombo);

                          String engineer="OUTPUT LIST of engineers from NEW INCIDENT NEW PROBLEM: \n"+
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
        else
        {
            escalationcombo.setEditable(false);
            escalationcombo.getItems().clear();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Escalation status NO in NEW INCIDENT NEW PROBLEM \n\n");
                    
        } 
       
    }
    
     
  
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: NEW INCIDENT FROM EXISTING PROBLEM STARTED!");
        
        btnchoosefile.setGraphic(iconfile);
        deletefilesbtn.setGraphic(deleteicon);
        
        cancelbtn.setGraphic(cancelicon);
        savebtn.setGraphic(saveicon);
        
        addspare.setGraphic(addicon);
        
        combostatus.setValue("Select...");
        escalstatuscombo.setValue("Select...");
        combostatus.setItems(itemscombostatus);
        escalstatuscombo.setItems(itemscomboescalstatus);
        
        
        
        //setare data 
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Calendar obj = Calendar.getInstance();
            String date = formatter.format(obj.getTime());
            LocalTime timeObj = LocalTime.now();
            String currentTime = timeObj.toString();
            String time=currentTime.substring(0,5);
            String finaldate=date+" "+time;
           
             //setare startdate
            startdatefield.setText(finaldate);
        
             //setare enddate
            enddatefield.setText(finaldate);
             
             
             startdatefield.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     String regex="^(3[01]|0[1-9]|[12][0-9])\\/(1[0-2]|0[1-9])\\/(-?(?:[1-9][0-9]*)?[0-9]{4})( (?:2[0-3]|[01][0-9]):[0-5][0-9])?$";
                     if (newValue.toString().matches(regex)==true) 
                     { 
                            
                             startdatefield.setStyle("-fx-border-width: 0.5px ;");
                             int ok=compareDates(newValue.toString(),enddatefield.getText());
                             
                             if(ok==0)
                             {
                                 startdatefield.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                             }
                             else
                             {
                                 
                                 startdatefield.setStyle("-fx-border-width: 0.5px ;");
                                 enddatefield.setStyle("-fx-border-width: 0.5px ;");
                             }
                            
                     }
                     else if(newValue.toString().matches(regex)==false){
                         
                         startdatefield.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     
                       
           
                 }
                 
                 
             });
             
             enddatefield.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     String regex="^(3[01]|0[1-9]|[12][0-9])\\/(1[0-2]|0[1-9])\\/(-?(?:[1-9][0-9]*)?[0-9]{4})( (?:2[0-3]|[01][0-9]):[0-5][0-9])?$";
                     if (newValue.toString().matches(regex)==true) { 
                            
                             enddatefield.setStyle("-fx-border-width: 0.5px ;");
                             int ok=compareDates(startdatefield.getText(),newValue.toString());
                            
                             if(ok==0)
                             {
                                 enddatefield.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                             }
                             else
                             {
                                 enddatefield.setStyle("-fx-border-width: 0.5px ;");
                                 startdatefield.setStyle("-fx-border-width: 0.5px ;");
                             }
                     }
                     else if(newValue.toString().matches(regex)==false)
                     {
                         
                         enddatefield.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     
                       
           
                 }
                 
                 
             });
             
             
             
             saporder.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     String regexd="\\d+";
                     if (newValue.toString().length()>8 || newValue.toString().matches(regexd)==false) { 
                            saporder.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                            
                     }
                     else{
                         
                          saporder.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                       
           
                 }
                 
                 
             });
            
                
            sparepartstextarea.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>450) { 
                            sparepartstextarea.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                            
                     }
                     else{
                         
                          sparepartstextarea.setStyle("-fx-border-width: 0.5px ;");
                     }
                     
                       
           
                 }
                 
                 
             });
            
            textareasol.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>500) { 
                            
                         textareasol.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");    
                     }
                     else{
                         textareasol.setStyle("-fx-border-width: 0.5px ;");
                         
                     }
                     
                       
           
                 }
                 
                 
             });
            
            
            
            textareashortdesc.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>300) 
                     { 
                      textareashortdesc.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");                                  
                                                 
                     }
                     else{
                         textareashortdesc.setStyle("-fx-border-width: 0.5px ;");
 
                         
                     }
                     
                       
           
                 }
                 
                 
             });
            
            
    }
    
}
