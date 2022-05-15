/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import addcontroller.AddescalationteamController;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.sun.javafx.scene.control.behavior.TextAreaBehavior;

import toolclass.MachineStruct;
import static java.awt.SystemColor.desktop;
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
import java.util.zip.ZipOutputStream;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
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
import toolclass.MachineCombo;
import tslessonslearneddatabase.ConnectionDatabase;
import static tslessonslearneddatabase.ConnectionDatabase.logger;
import tslessonslearneddatabase.Data;
import tslessonslearneddatabase.Log4jConfiguration;
import tslessonslearneddatabase.TSLessonsLearnedDatabase;
/**
 *
 * @author cimpde1
 */
public class NewIncidentNewProblemController implements Initializable{
    
    public org.apache.logging.log4j.Logger logger = LogManager.getLogger(NewIncidentNewProblemController.class);
    
    private Stage openspareadd;
    
    public int idPb=-1;
    public int idInc=-1;
    
     @FXML
    private AnchorPane anchorPaneoverview;
     
    @FXML
    private Button btnchoosefile;
    
    @FXML
    private Button addsparepartsbtn;
    @FXML
    private ImageView iconfile;
    
    @FXML
    private TextArea textareasol;

    @FXML
    private TextArea textareashortdesc;

    @FXML
    private TextArea textareadesc;
    
    
    
    @FXML
    private Button savebtn;
     @FXML
    private Button cancelbtn;
     
     @FXML
    private ImageView saveicon;

    @FXML
    private ImageView cancelicon;
    
     @FXML
    private ImageView addiconspare;
     
     @FXML
    private ImageView deleteicon;
     
    ObservableList<String> itemscombo=FXCollections.observableArrayList();
    ObservableList<String> itemscomboline=FXCollections.observableArrayList();
    ObservableList<String> itemscombomachine=FXCollections.observableArrayList();
    
    ObservableList<MachineCombo> itemsmachinecombo=FXCollections.observableArrayList();
    
    @FXML
    private ComboBox<String> comboif;

    @FXML
    private ComboBox<String> comboline;
    
    @FXML
    private ComboBox<String> combomachine;
    @FXML
    private ComboBox<String> combostatus;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private TextField enddatefield;

    @FXML
    private TextField startdatefield;
    
    @FXML
    private ComboBox<String> escalstatuscombo;
    
    @FXML
    private ComboBox<String> escalationcombo;
    
    @FXML
    private TextField saporder;
    
    
    
     @FXML
    private TextArea sparepartstextarea;
     
     @FXML
    private TextField hmierror;
     
     @FXML
    private ListView<String> listviewfiles;
     
      @FXML
    private Button deletefilesbtn;
      
      @FXML
    private ScrollPane scrollPane;

    
    ObservableList<String> itemscombostatus=FXCollections.observableArrayList("Select...","solved","unsolved");
    
    ObservableList<String> itemscomboescalstatus=FXCollections.observableArrayList("Select...","yes","no");
    ObservableList<String> itemscomboescal=FXCollections.observableArrayList();
    List<File> selected_files=new ArrayList<>();
    
    String fisier=null;
    int id_machine=-1;
    int id_IF=-1;
    int id_Line=-1;
    int id_EscTeam=-1;
    
    String part_numbere=null;
    
    
    Connection conn=null;
    
    public void onSpareTextArea(KeyEvent event)
    {
         if (event.getCode() == KeyCode.TAB) 
         {
             //System.out.println("key tab");
         }
    }
    
   
    
    public void cancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from NEW INCIDENT NEW PROBLEM was pressed!\n\n");
        Stage s = (Stage) cancelbtn .getScene().getWindow();
        s.close();
    }
    public void setSpares()
    {
        
        if(Data.spare_part_numbers.isEmpty()==false)
        {
            if(sparepartstextarea.getText().isEmpty())
            {
                sparepartstextarea.setText(Data.spare_part_numbers);
            }
            else
            {
                sparepartstextarea.setText(sparepartstextarea.getText()+"\n"+Data.spare_part_numbers);
            }
            
            /*String mystring=sparepartstextarea.getText();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The PART NUMBERS from NEW INCIDENT NEW PROBLEM: \n"+mystring+"\n\n");
            
            String[] items = mystring.split("\n");
            List<String> itemList = Arrays.asList(items);
            List<String> idspares=new ArrayList<>();
            for(String p:itemList)
            {
                int idspare=ConnectionDatabase.getSparePartsId(p);
                idspares.add(String.valueOf(idspare));
            }
            
            String listpartnumbers=idspares.stream().collect(Collectors.joining(";"));
            part_numbere=listpartnumbers;
            */
           
        }
        
    }
    public void convertSparePartsToDb()
    {
            String  mystring=sparepartstextarea.getText();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The PART NUMBERS from NEW INCIDENT NEW PROBLEM: \n"+mystring+"\n\n");
            
            String[] items = mystring.split("\n\\s+");
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
    public void openaddspare()
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
                     logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- IOException: "+ex+"\n\n");
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
                   logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- IOException: "+ex+"\n\n");
               }
        }
    }
    public void actioncomboIF()
    {
               
               boolean isMyComboBoxEmpty = comboif.getSelectionModel().isEmpty();
               if(isMyComboBoxEmpty == false && !comboif.getValue().toString().equals("Select..."))
               {
                   
                   int ifid=ConnectionDatabase.getIfId(comboif.getValue().toString());
                   id_IF=ifid;
                   logger.info(Log4jConfiguration.currentTime()+"[INFO]: The IF selected in NEW INCIDENT NEW PROBLEM: \n"
                           +comboif.getValue().toString() + "\n"+ "IF id: "+ifid);
                   if(ifid!=0)
                   {
                        comboline.setValue("Select...");
                        itemscomboline=ConnectionDatabase.getLinesfromIf(ifid);
                        if(!itemscomboline.isEmpty())
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
                             Collections.sort(itemscomboline,c);
                             itemscomboline.add(0, "Select...");
                             comboline.setItems(itemscomboline);
                             
                             String line="OUTPUT LIST of lines from the IF selected from NEW INCIDENT NEW PROBLEM: \n"+
                                     "[1]: "+itemscomboline.get(1)+"\n";
                             for(int i=2;i<itemscomboline.size();i++)
                             {
                                 line=line+"["+i+"]: "+itemscomboline.get(i)+"\n";
                             }
                             logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+line);
                             logger.info("\n");

                         }
                        else{
                            
                            comboline.getItems().clear();
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Empty list of lines in the IF selected in NEW INCIDENT NEW PROBLEM!\n\n");
                            
                        }
                   }
                 
               }
               else if(comboif.getValue().toString().equals("Select..."))
               {
                   //comboline.setValue("Select");
                   comboline.getItems().clear();
                   combomachine.getItems().clear();
                   logger.info(Log4jConfiguration.currentTime()+"[INFO]: 'Select...' option from IF was selected and the list of lines and machines "
                           + " have been reloaded in NEW INCIDENT NEW PROBLEM!\n\n");
               }
               
         
    }
    public void onactionLine()
    {
        
        
        boolean isMyLine=comboline.getSelectionModel().isEmpty();
        if(isMyLine == false && !comboline.getValue().toString().equals("Select..."))
        {
            int idline=ConnectionDatabase.getLineId(comboline.getValue().toString());
            id_Line=idline;
            if(idline!=0)
            {
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The line selected from NEW INCIDENT NEW PROBLEM is: \n"
                        +comboline.getValue().toString()+"\n"
                +"Line id: "+idline);
                itemscombomachine=ConnectionDatabase.getMachineCombo(idline);
                if(!itemscombomachine.isEmpty())
                {
                    combomachine.setValue("Select...");
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
                    Collections.sort(itemscombomachine,c);
                    itemscombomachine.add(0, "Select...");
                    combomachine.setItems(itemscombomachine);
                    
                    String machine="OUTPUT LIST of machines from the line selected from NEW INCIDENT NEW PROBLEM:\n"+
                                     "[1]: "+itemscombomachine.get(1)+"\n";
                             for(int i=2;i<itemscombomachine.size();i++)
                             {
                                 machine=machine+"["+i+"]: "+itemscombomachine.get(i)+"\n";
                             }
                             logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+machine);
                             logger.info("\n");
                    
                    
                }
                else
                {
                    combomachine.getItems().clear();
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: Empty list of machines in the LINE selected in NEW INCIDENT NEW PROBLEM\n\n");
                    
                }
            }
            
        }
        else if(comboline.getSelectionModel().isEmpty()==false && comboline.getValue().toString().equals("Select..."))
        {
            //combomachine.setValue("Select...");
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Clearing list of machines because 'Select...' option from line was selected in "
                    + " NEW INCIDENT NEW PRBLEM");
            combomachine.getItems().clear();
            
        }
    }
    public void onMachine()
    {
                   
                    
                    boolean isMyComboBox=combomachine.getSelectionModel().isEmpty();
                    if(isMyComboBox==false && !combomachine.getValue().toString().equals(" ") && !combomachine.getValue().toString().equals("Select..."))
                    {
                        String machine=combomachine.getValue();
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The machine selected from NEW INCIDENT NEW PROBLEM: \n"+
                                machine);
                        int index1=machine.indexOf("(");
                        int index2=machine.indexOf(")"); 
                        String invname=machine.substring(index1+1, index2);
                        String mach=machine.substring(0,index1);
                        id_machine=ConnectionDatabase.getMachineId(mach, invname);
                        
                    }
                    
      
    }
   
    public void chooseFile()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Choose file button from NEW INCIDENT NEW PROBLEM was pressed!\n\n");
        FileChooser filechooser=new FileChooser();
        filechooser.setTitle("Open File Dialog");
        filechooser.getExtensionFilters().addAll(new ExtensionFilter("All files","*.*"));
        Stage stage=(Stage)anchorPane.getScene().getWindow();
        
        
        
        List<File> files=filechooser.showOpenMultipleDialog(stage);
        FileChooserTemplate files_object=new FileChooserTemplate(files);
        
        String filemsg="OUTPUT LIST of files selected from NEW INCIDENT NEW PROBLEM: \n";
        
        
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
                                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The "+ i +" file is too big in NEW INCIDENT NEW PROBLEM");
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
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The files from LIST VIEW: "+listviewfiles);
        }
        
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+filemsg+"\n");
        
        
        
    }
    
    
    
       
        
        
        
    
    public void deleteFiles()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Delete files button from NEW INCIDENT NEW PROBLEM was pressed!\n\n");
        ObservableList<String> list = listviewfiles.getSelectionModel().getSelectedItems();
        listviewfiles.getItems().removeAll(list);
        
                    
         logger.info(Log4jConfiguration.currentTime()+"[INFO]: The files that were deleted in NEW INCIDENT NEW PROBLEM: \n"+list+"\n\n");
        
      
    }
    
    public void onEscal()
    {
        if(escalstatuscombo.getValue().equals("yes"))
        {
                     
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: Escalation status YES in NEW INCIDENT NEW PROBLEM \n");
                     
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
    
    public void onSelectEscalation()
    {
           
                
            boolean isCombo=escalationcombo.getSelectionModel().isEmpty();
            if(isCombo==false && escalationcombo.getValue().toString().isEmpty()==false)
            {
                
                String valueesc=escalationcombo.getValue().toString();
                
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The engineer selected for escalation in NEW INCIDENT NEW PROBLEM: \n"
                        +valueesc);
                int index1=valueesc.indexOf("(");
                int index2=valueesc.indexOf(")"); 
                String user=valueesc.substring(index1+1, index2);
                id_EscTeam=ConnectionDatabase.getEscId(user);
            }
            
            
    }
    public  void zip(File path,String directoryName,String ProblemFolder) throws FileNotFoundException, IOException
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: ZIPING the FIRST INCIDENT folder in NEW INCIDENT NEW PROBLEM... \n\n");
        File[] files=path.listFiles();
        if(files.length == 0)
             //new IllegalArgumentException("No files in path "+path.getAbsolutePath());
            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT NEW PROBLEM---IllegalArgumentException: NO FILES IN THE PATH");
        
        String address_stream="C:\\Data\\"+ProblemFolder+"\\"+directoryName+".zip";
        
       
        
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
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The following file is for FIRST INCIDENT of the problem: \n"
                +directoryName+".zip\n\n");
        
        
    }
    public  void zipProblem(File path,String directoryName) throws FileNotFoundException, IOException
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: ZIPING the PROBLEM folder in NEW INCIDENT NEW PROBLEM...\n");
        File[] files=path.listFiles();
        if(files.length == 0)
          logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT NEW PROBLEM---IllegalArgumentException: NO FILES IN THE PATH");
        
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
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The following file is for PROBLEM: \n"
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
                   logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- FileNotFoundException: "+ex);
                } catch (IOException ex) {
                    logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- IOException: "+ex);
                }

           }   catch (FileNotFoundException ex) {
               logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- IOException: "+ex);
            } finally {
                try {
                    filestream.close();
                } catch (IOException ex) {
                    logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- IOException: "+ex+"\n\n");
                }
          }
        }
        else
        {
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: The arguments for addingFile must be : FILE,DIRECTORY in NEW INCIDENT NEW PROBLEM\n\n");
        }
        
    }
     public void sendOnServer(String source,String destination)
     {
          logger.info(Log4jConfiguration.currentTime()+"[INFO]: Connecting to SFTP SERVER in NEW INCIDENT NEW PROBLEM...\n");
          try {
             String SFTPHOOST="192.168.168.208";
             int SFTPORT=2222;
             String SFTPUSER="tester";
             String password="password";
             
             JSch jSch=new JSch();
             Session session=null;
             Channel channel =null;
             
              logger.info(Log4jConfiguration.currentTime()+"[INFO]: Creating session in NEW INCIDENT NEW PROBLEM...\n");
             
             session=jSch.getSession(SFTPUSER, SFTPHOOST, SFTPORT);
             session.setPassword(password);
             
              logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session created in NEW INCIDENT NEW PROBLEM \n");
             
             java.util.Properties config=new java.util.Properties();
             config.put("StrictHostKeyChecking", "no");
             session.setConfig(config);
             session.connect();
             if(session.isConnected()==true)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session is connected in NEW INCIDENT NEW PROBLEM \n");
                 
                  logger.info(Log4jConfiguration.currentTime()+"[INFO]: Open channel in NEW INCIDENT NEW PROBLEM...\n");
                 channel=session.openChannel("sftp");
                 channel.connect();
                 if(channel.isConnected()==true)
                 {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Channel is connected in NEW INCIDENT NEW PROBLEM \n");
                        
                        ChannelSftp sftpChannel = (ChannelSftp) channel;
                        
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Sending file on server  in NEW INCIDENT NEW PROBLEM...\n");
                        
                        sftpChannel.put(source, destination);
                        
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+source+ " was send on SFTP server in NEW INCIDENT NEW PROBLEM!\n");
                        
                        channel.disconnect();
                        if (session != null) {
                              session.disconnect();
                              logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session disconnected  in NEW INCIDENT NEW PROBLEM\n");
                        
                              
                        }

                        sftpChannel.exit();
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Channel disconnected in NEW INCIDENT NEW PROBLEM \n\n");
                        
                 }
                 else
                 {
                     logger.info(Log4jConfiguration.currentTime()+"[INFO]: The channel connection has failed  in NEW INCIDENT NEW PROBLEM\n\n");
                        
                 }

                
             }
             else
             {
                  logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The connection with session has failed in NEW INCIDENT NEW PROBLEM!\n\n");
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
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT NEW PROBLEM ---- SftpException: "+ex);
         } catch (JSchException ex) { 
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT NEW PROBLEM ---- JSchException: "+ex);
         }
         
         
     }
    private static int BINARY_FILE_TYPE;
    
    
   
    
    public void saveIfIsEscalationNoFile(String SpareParts,String SolCreator)
    {
                
              
               String regex="^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]$";
               String regexd="\\d+";
               int ok=compareDates(startdatefield.getText(),enddatefield.getText());
               //sap e empty
               
              if(saporder.getText().isEmpty()==true)
              {
                    if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 
                       || sparepartstextarea.getText().length() > 450 ||  textareashortdesc.getText().length() >  50 || hmierror.getText().length() > 45
                       || textareadesc.getText().length() > 400 || textareasol.getText().length() > 250)
                    {
                                     logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Incorrect inputs in NEW INCIDENT NEW PROBLEM");
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
                                                
                                                conn = ConnectionDatabase.getConnection();
                                                
                                                
                                                
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
                                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure assignEngineer()");
                                                CallableStatement psengineer = conn.prepareCall("{call assignEngineer(?,?)}");
                                                psengineer.setInt(1,id_EscTeam);
                                                psengineer.setString(2, sd);
                                                psengineer.execute();



                                                int id_ea=ConnectionDatabase.getEscAssignId(id_EscTeam, sd);

                                                conn=ConnectionDatabase.getConnection();
                                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure addNewProblemNewIncident()");
                                                CallableStatement ps = conn.prepareCall("{call addNewProblemNewIncident(?,?,?,?,?,?,?,?,?)}");
                                                ps.setInt(1,id_IF);
                                                ps.setInt(2, id_Line);
                                                ps.setInt(3, id_machine);
                                                ps.setString(4, user);
                                                ps.setString(5, combostatus.getValue().toString());
                                                ps.setString(6, hmierror.getText());
                                                ps.setString(7, textareadesc.getText());
                                                ps.setInt(8, id_ea);
                                                ps.setString(9,"");

                                                String pb="IF: "+String.valueOf(id_IF)+"\n"+
                                                          "Line: "+String.valueOf(id_Line)+"\n"+
                                                          "Machine: "+String.valueOf(id_machine)+"\n"+
                                                          "Creator user: "+user+"\n"
                                                          +"Problem status: "+combostatus.getValue().toString()+"\n"+
                                                          "HMI Error: "+hmierror.getText()+"\n"+
                                                          "Problem description: "+textareadesc.getText()+"\n"+
                                                           "Escalation assign: "+String.valueOf(id_ea)+","+escalationcombo.getValue().toString()+"\n"+
                                                        "Escalation status: "+escalstatuscombo.getValue().toString()+"\n"+
                                                        "File name: "+"null"+"\n";



                                                if(ps.executeUpdate()!=0)
                                                {


                                                         //setare ticket pentru incident
                                                         long milliSec = System.currentTimeMillis();  
                                                         String current_milisec=String.valueOf(milliSec);
                                                         String ticket="#INC_"+current_milisec;

                                                          //get the problem id
                                                          int pbid=ConnectionDatabase.getProblemId(
                                                          id_IF, id_Line, id_machine, hmierror.getText(), combostatus.getValue().toString(),user,
                                                                  textareadesc.getText(),id_ea,"");



                                                          idPb=pbid;

                                                          logger.info(Log4jConfiguration.currentTime()
                                                                  +"[INFO]: Details about the problem added: \n"+pb+"Problem's ID: "+pbid);

                                                          conn=ConnectionDatabase.getConnection();
                                                          //add in incident history
                                                          logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure addIncidentNewProblem()");
                                                          CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                                          psincident.setString(1, ticket);
                                                          psincident.setInt(2, pbid);
                                                          psincident.setString(3,sd);
                                                          psincident.setString(4, ed);
                                                          psincident.setString(5, combostatus.getValue().toString());
                                                          psincident.setInt(6, 0);
                                                          psincident.setString(7, SolCreator);
                                                          psincident.setString(8,"" );
                                                          psincident.setString(9, user);
                                                          psincident.setInt(10, id_ea);
                                                          psincident.setString(11,SpareParts);
                                                          psincident.setString(12,"");
                                                          psincident.setString(13,textareashortdesc.getText());


                                                          String incmsg="Details about the first incident of the problem added with the "+pbid+" id:\n"+
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
                                                                  "Incident's file name: "+"null"+"\n"+
                                                                  "Incident's short description: "+textareashortdesc.getText()+"\n";


                                                          if(psincident.executeUpdate()!=0)
                                                          {
                                                                 logger.info(Log4jConfiguration.currentTime()
                                                                         +"[INFO]: The first incident for the problem with id "+pbid 
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

                                                                 //clear fields
                                                                 //clear_fields();


                                                          }
                                                          else
                                                          {
                                                              logger.error(Log4jConfiguration.currentTime()
                                                                      +"[ERROR]: Something went wrong while saving the first incident of the problem");
                                                          }




                                                }
                                                else
                                                {
                                                    logger.error(Log4jConfiguration.currentTime()
                                                            +"[ERROR]: Something went wrong while saving the problem in NEW INCIDENT NEW PROBLEM!");
                                                }



                                            } catch (SQLException ex) {
                                                 logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- SQLException: "+ex+"\n\n");
                                            }catch (ParseException ex) {
                                                     logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- ParseException: "+ex+"\n\n");
                                            }
                          }
              }
              //sap nu e empty
              else
              {
               
               if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 || saporder.getText().matches(regexd)==false
                       || sparepartstextarea.getText().length() > 450 ||  textareashortdesc.getText().length() >  50 || hmierror.getText().length() > 45
                       || textareadesc.getText().length() > 400 || textareasol.getText().length() > 250
                       || saporder.getText().length() > 8)
               {
                                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Incorrect inputs in NEW INCIDENT NEW PROBLEM");
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
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure assignEngineer()");
                                       CallableStatement psengineer = conn.prepareCall("{call assignEngineer(?,?)}");
                                       psengineer.setInt(1,id_EscTeam);
                                       psengineer.setString(2, sd);
                                       psengineer.execute();
                                       
                                                                    

                                       int id_ea=ConnectionDatabase.getEscAssignId(id_EscTeam, sd);

                                       conn=ConnectionDatabase.getConnection();
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure addNewProblemNewIncident()");
                                       CallableStatement ps = conn.prepareCall("{call addNewProblemNewIncident(?,?,?,?,?,?,?,?,?)}");
                                       ps.setInt(1,id_IF);
                                       ps.setInt(2, id_Line);
                                       ps.setInt(3, id_machine);
                                       ps.setString(4, user);
                                       ps.setString(5, combostatus.getValue().toString());
                                       ps.setString(6, hmierror.getText());
                                       ps.setString(7, textareadesc.getText());
                                       ps.setInt(8, id_ea);
                                       ps.setString(9,"");
                                       
                                       String pb="IF: "+String.valueOf(id_IF)+"\n"+
                                                 "Line: "+String.valueOf(id_Line)+"\n"+
                                                 "Machine: "+String.valueOf(id_machine)+"\n"+
                                                 "Creator user: "+user+"\n"
                                                 +"Problem status: "+combostatus.getValue().toString()+"\n"+
                                                 "HMI Error: "+hmierror.getText()+"\n"+
                                                 "Problem description: "+textareadesc.getText()+"\n"+
                                                  "Escalation assign: "+String.valueOf(id_ea)+","+escalationcombo.getValue().toString()+"\n"+
                                               "Escalation status: "+escalstatuscombo.getValue().toString()+"\n"+
                                               "File name: "+"null"+"\n";
                                       
                                       

                                       if(ps.executeUpdate()!=0)
                                       {
                                                 
                                                 
                                                //setare ticket pentru incident
                                                long milliSec = System.currentTimeMillis();  
                                                String current_milisec=String.valueOf(milliSec);
                                                String ticket="#INC_"+current_milisec;

                                                 //get the problem id
                                                 int pbid=ConnectionDatabase.getProblemId(
                                                 id_IF, id_Line, id_machine, hmierror.getText(), combostatus.getValue().toString(),user,
                                                         textareadesc.getText(),id_ea,"");
                                                 
                                                 
                                                 
                                                 idPb=pbid;
                                                 
                                                 logger.info(Log4jConfiguration.currentTime()
                                                         +"[INFO]: Details about the problem added: \n"+pb+"Problem's ID: "+pbid);

                                                 conn=ConnectionDatabase.getConnection();
                                                 //add in incident history
                                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure addIncidentNewProblem()");
                                                 CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                                 psincident.setString(1, ticket);
                                                 psincident.setInt(2, pbid);
                                                 psincident.setString(3,sd);
                                                 psincident.setString(4, ed);
                                                 psincident.setString(5, combostatus.getValue().toString());
                                                 psincident.setString(6, saporder.getText());
                                                 psincident.setString(7, SolCreator);
                                                 psincident.setString(8,"" );
                                                 psincident.setString(9, user);
                                                 psincident.setInt(10, id_ea);
                                                 psincident.setString(11,SpareParts);
                                                 psincident.setString(12,"");
                                                 psincident.setString(13,textareashortdesc.getText());
                                                 
                                                 
                                                 String incmsg="Details about the first incident of the problem added with the "+pbid+" id:\n"+
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
                                                         "Incident's file name: "+"null"+"\n"+
                                                         "Incident's short description: "+textareashortdesc.getText()+"\n";


                                                 if(psincident.executeUpdate()!=0)
                                                 {
                                                        logger.info(Log4jConfiguration.currentTime()
                                                                +"[INFO]: The first incident for the problem with id "+pbid 
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

                                                        //clear fields
                                                        //clear_fields();


                                                 }
                                                 else
                                                 {
                                                     logger.error(Log4jConfiguration.currentTime()
                                                             +"[ERROR]: Something went wrong while saving the first incident of the problem");
                                                 }
                                                



                                       }
                                       else
                                       {
                                           logger.error(Log4jConfiguration.currentTime()
                                                   +"[ERROR]: Something went wrong while saving the problem in NEW INCIDENT NEW PROBLEM!");
                                       }
                                       


                                   } catch (SQLException ex) {
                                        logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- SQLException: "+ex+"\n\n");
                                   }catch (ParseException ex) {
                                            logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- ParseException: "+ex+"\n\n");
                                   }
                 }
                
               }
               
           
    }
     public void saveIfIsEscalationFile(String SpareParts,String SolCreator)
    {
                
              
               String regex="^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]$";
               String regexd="\\d+";
               int ok=compareDates(startdatefield.getText(),enddatefield.getText());
               
               //nu e sap
               if(saporder.getText().isEmpty()==true)
               {
                            if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 
                                || sparepartstextarea.getText().length() > 450 ||  textareashortdesc.getText().length() >  50 || hmierror.getText().length() > 45
                                || textareadesc.getText().length() > 400 || textareasol.getText().length() > 250)
                        {
                                         logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Incorrect inputs in NEW INCIDENT NEW PROBLEM");
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
                                                conn = ConnectionDatabase.getConnection();
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
                                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure assignEngineer()");
                                                CallableStatement psengineer = conn.prepareCall("{call assignEngineer(?,?)}");
                                                psengineer.setInt(1,id_EscTeam);
                                                psengineer.setString(2, sd);
                                                psengineer.execute();


                                                int id_ea=ConnectionDatabase.getEscAssignId(id_EscTeam, sd);

                                                conn=ConnectionDatabase.getConnection();
                                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure addNewProblemNewIncident()");
                                                CallableStatement ps = conn.prepareCall("{call addNewProblemNewIncident(?,?,?,?,?,?,?,?,?)}");
                                                ps.setInt(1,id_IF);
                                                ps.setInt(2, id_Line);
                                                ps.setInt(3, id_machine);
                                                ps.setString(4, user);
                                                ps.setString(5, combostatus.getValue().toString());
                                                ps.setString(6, hmierror.getText());
                                                ps.setString(7, textareadesc.getText());
                                                ps.setInt(8, id_ea);
                                                ps.setString(9,"DummyProblem");



                                                if(ps.executeUpdate()!=0)
                                                {


                                                         //setare ticket pentru incident
                                                         long milliSec = System.currentTimeMillis();  
                                                         String current_milisec=String.valueOf(milliSec);
                                                         String ticket="#INC_"+current_milisec;

                                                          //get the problem id
                                                          int pbid=ConnectionDatabase.getProblemId(
                                                          id_IF, id_Line, id_machine, hmierror.getText(), combostatus.getValue().toString(),user,
                                                                  textareadesc.getText(),id_ea,"DummyProblem");

                                                          idPb=pbid;

                                                          DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
                                                          Calendar obj = Calendar.getInstance();

                                                          String date = formatter.format(obj.getTime());
                                                          String Directoryname="Problem_"+idPb+"_"+date+"_"+"Incident_"+idInc+"_"+date;



                                                          conn=ConnectionDatabase.getConnection();

                                                         logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure addIncidentNewProblem()");

                                                          //add in incident history
                                                          CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                                          psincident.setString(1, ticket);
                                                          psincident.setInt(2, pbid);
                                                          psincident.setString(3,sd);
                                                          psincident.setString(4, ed);
                                                          psincident.setString(5, combostatus.getValue().toString());
                                                          psincident.setInt(6, 0);
                                                          psincident.setString(7, SolCreator);
                                                          psincident.setString(8,"" );
                                                          psincident.setString(9, user);
                                                          psincident.setInt(10, id_ea);
                                                          psincident.setString(11,SpareParts);
                                                          psincident.setString(12,"DummyIncident");
                                                          psincident.setString(13,textareashortdesc.getText());



                                                          if(psincident.executeUpdate()!=0)
                                                          {


                                                                 int IncidentId=ConnectionDatabase.getIncidentId(ticket, pbid, sd, ed, 
                                                                         combostatus.getValue().toString(), 
                                                                         SolCreator, user, textareashortdesc.getText());
                                                                 idInc=IncidentId;

                                                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: ID Incident: " + idInc);

                                                                 String fileIncident="Problem_"+idPb+"_"+date+"_"+"Incident_"+idInc+"_"+date;

                                                                 //update problem file
                                                                 conn=ConnectionDatabase.getConnection();
                                                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure updateFileName()");
                                                                 CallableStatement updateFile = conn.prepareCall("{call updateFileName(?,?)}");
                                                                 updateFile.setInt(1, idPb);
                                                                 updateFile.setString(2, fileIncident);
                                                                 if(updateFile.executeUpdate()!=0)
                                                                 {
                                                                     logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the problem was saved in DB in NEW INCIDENT NEW PROBLEM!");
                                                                 }
                                                                 else
                                                                 {
                                                                     logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the problem "
                                                                             + " of the file in DB in NEW INCIDENT NEW PROBLEM!");
                                                                 }


                                                                 conn=ConnectionDatabase.getConnection();
                                                                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure updateFileIncident()");
                                                                 CallableStatement updateFileInc = conn.prepareCall("{call updateFileIncident(?,?)}");
                                                                 updateFileInc.setInt(1, IncidentId);
                                                                 updateFileInc.setString(2, fileIncident);

                                                                 if(updateFileInc.executeUpdate()!=0)
                                                                 {
                                                                     logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the first incident was saved in DB in NEW INCIDENT NEW PROBLEM!");
                                                                 }
                                                                 else
                                                                 {
                                                                     logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the "
                                                                             + " file of the first incident in DB in NEW INCIDENT NEW PROBLEM!");
                                                                 }

                                                                 String pb="IF: "+String.valueOf(id_IF)+"\n"+
                                                                 "Line: "+String.valueOf(id_Line)+"\n"+
                                                                 "Machine: "+String.valueOf(id_machine)+"\n"+
                                                                 "Creator user: "+user+"\n"
                                                                 +"Problem status: "+combostatus.getValue().toString()+"\n"+
                                                                 "HMI Error: "+hmierror.getText()+"\n"+
                                                                 "Problem description: "+textareadesc.getText()+"\n"+
                                                                  "Escalation assign: "+String.valueOf(id_ea)+","+escalationcombo.getValue().toString()+"\n"+
                                                               "Escalation status: "+escalstatuscombo.getValue().toString()+"\n"+
                                                               "File name: "+Directoryname+"\n";

                                                                 logger.info(Log4jConfiguration.currentTime()
                                                                         +"[INFO]: Details about the problem added: \n"+pb+"Problem's ID: "+pbid);

                                                                 String incmsg="Details about the first incident of the problem added with the "+pbid+" id:\n"+
                                                                  "Incident's ticket: "+ticket+"\n"+
                                                                  "Incident's start date: "+st_date+"\n"+
                                                                  "Incident's end date: "+en_date+"\n"+
                                                                  "Incident's status: "+combostatus.getValue().toString()+"\n"
                                                                  +"Incident's SAP order: "+saporder.getText()+"\n"+
                                                                  "Incident's solution creator: "+SolCreator+"\n"+
                                                                  "Incident's creator user: "+user+"\n"+
                                                                  "Incident's escalation assign: "+String.valueOf(id_ea)+","+escalationcombo.getValue().toString()+"\n"+
                                                                  "Incident's escalation status: "+escalstatuscombo.getValue().toString()+"\n"+    
                                                                  "Incident's spare parts: "+SpareParts+"\n"+
                                                                  "Incident's file name: "+fileIncident+"\n"+
                                                                  "Incident's short description: "+textareashortdesc.getText()+"\n";

                                                                 logger.info(Log4jConfiguration.currentTime()
                                                                         +"[INFO]: The first incident for the problem with id "+pbid 
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

                                                                 //clear fields
                                                                 //clear_fields();


                                                          }
                                                          else
                                                          {
                                                              logger.error(Log4jConfiguration.currentTime()
                                                                      +"[ERROR]: Something went wrong while saving the first incident of the problem");
                                                          }




                                                }
                                                else
                                                {
                                                    logger.error(Log4jConfiguration.currentTime()
                                                            +"[ERROR]: Something went wrong while saving the problem!");
                                                }



                                            } catch (SQLException ex) {
                                                 logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- SQLException: "+ex+"\n\n");
                                            }catch (ParseException ex) {
                                                 logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- ParseException: "+ex+"\n\n");
                                            }
                        }
               }
               //e sap
               else
               {
               
               if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 || saporder.getText().matches(regexd)==false
                       || sparepartstextarea.getText().length() > 450 ||  textareashortdesc.getText().length() >  50 || hmierror.getText().length() > 45
                       || textareadesc.getText().length() > 400 || textareasol.getText().length() > 250 || saporder.getText().length() > 8)
               {
                                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Incorrect inputs in NEW INCIDENT NEW PROBLEM");
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
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure assignEngineer()");
                                       CallableStatement psengineer = conn.prepareCall("{call assignEngineer(?,?)}");
                                       psengineer.setInt(1,id_EscTeam);
                                       psengineer.setString(2, sd);
                                       psengineer.execute();
                                       
                                                                   
                                       int id_ea=ConnectionDatabase.getEscAssignId(id_EscTeam, sd);

                                       conn=ConnectionDatabase.getConnection();
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure addNewProblemNewIncident()");
                                       CallableStatement ps = conn.prepareCall("{call addNewProblemNewIncident(?,?,?,?,?,?,?,?,?)}");
                                       ps.setInt(1,id_IF);
                                       ps.setInt(2, id_Line);
                                       ps.setInt(3, id_machine);
                                       ps.setString(4, user);
                                       ps.setString(5, combostatus.getValue().toString());
                                       ps.setString(6, hmierror.getText());
                                       ps.setString(7, textareadesc.getText());
                                       ps.setInt(8, id_ea);
                                       ps.setString(9,"DummyProblem");
                                       
                                       

                                       if(ps.executeUpdate()!=0)
                                       {
                                                 
                                                 
                                                //setare ticket pentru incident
                                                long milliSec = System.currentTimeMillis();  
                                                String current_milisec=String.valueOf(milliSec);
                                                String ticket="#INC_"+current_milisec;

                                                 //get the problem id
                                                 int pbid=ConnectionDatabase.getProblemId(
                                                 id_IF, id_Line, id_machine, hmierror.getText(), combostatus.getValue().toString(),user,
                                                         textareadesc.getText(),id_ea,"DummyProblem");
                                                 
                                                 idPb=pbid;
                                                 
                                                 DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
                                                 Calendar obj = Calendar.getInstance();
                                                 
                                                 String date = formatter.format(obj.getTime());
                                                 String Directoryname="Problem_"+idPb+"_"+date+"_"+"Incident_"+idInc+"_"+date;
                                                 
                                                 
                                                 
                                                 conn=ConnectionDatabase.getConnection();
                                                 
                                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure addIncidentNewProblem()");

                                                 //add in incident history
                                                 CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                                 psincident.setString(1, ticket);
                                                 psincident.setInt(2, pbid);
                                                 psincident.setString(3,sd);
                                                 psincident.setString(4, ed);
                                                 psincident.setString(5, combostatus.getValue().toString());
                                                 psincident.setString(6, saporder.getText());
                                                 psincident.setString(7, SolCreator);
                                                 psincident.setString(8,"" );
                                                 psincident.setString(9, user);
                                                 psincident.setInt(10, id_ea);
                                                 psincident.setString(11,SpareParts);
                                                 psincident.setString(12,"DummyIncident");
                                                 psincident.setString(13,textareashortdesc.getText());
                                                 
                                                 

                                                 if(psincident.executeUpdate()!=0)
                                                 {
                                                        
                                                     
                                                        int IncidentId=ConnectionDatabase.getIncidentId(ticket, pbid, sd, ed, 
                                                                combostatus.getValue().toString(), 
                                                                SolCreator, user, textareashortdesc.getText());
                                                        idInc=IncidentId;
                                                        
                                                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: ID Incident: " + idInc);
                                                        
                                                        String fileIncident="Problem_"+idPb+"_"+date+"_"+"Incident_"+idInc+"_"+date;
                                                        
                                                        //update problem file
                                                        conn=ConnectionDatabase.getConnection();
                                                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure updateFileName()");
                                                        CallableStatement updateFile = conn.prepareCall("{call updateFileName(?,?)}");
                                                        updateFile.setInt(1, idPb);
                                                        updateFile.setString(2, fileIncident);
                                                        if(updateFile.executeUpdate()!=0)
                                                        {
                                                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the problem was saved in DB in NEW INCIDENT NEW PROBLEM!");
                                                        }
                                                        else
                                                        {
                                                            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the problem "
                                                                    + " of the file in DB in NEW INCIDENT NEW PROBLEM!");
                                                        }
                                                        
                                                        
                                                        conn=ConnectionDatabase.getConnection();
                                                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure updateFileIncident()");
                                                        CallableStatement updateFileInc = conn.prepareCall("{call updateFileIncident(?,?)}");
                                                        updateFileInc.setInt(1, IncidentId);
                                                        updateFileInc.setString(2, fileIncident);
                                                        
                                                        if(updateFileInc.executeUpdate()!=0)
                                                        {
                                                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the first incident was saved in DB in NEW INCIDENT NEW PROBLEM!");
                                                        }
                                                        else
                                                        {
                                                            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the "
                                                                    + " file of the first incident in DB in NEW INCIDENT NEW PROBLEM!");
                                                        }
                                                        
                                                        String pb="IF: "+String.valueOf(id_IF)+"\n"+
                                                        "Line: "+String.valueOf(id_Line)+"\n"+
                                                        "Machine: "+String.valueOf(id_machine)+"\n"+
                                                        "Creator user: "+user+"\n"
                                                        +"Problem status: "+combostatus.getValue().toString()+"\n"+
                                                        "HMI Error: "+hmierror.getText()+"\n"+
                                                        "Problem description: "+textareadesc.getText()+"\n"+
                                                         "Escalation assign: "+String.valueOf(id_ea)+","+escalationcombo.getValue().toString()+"\n"+
                                                      "Escalation status: "+escalstatuscombo.getValue().toString()+"\n"+
                                                      "File name: "+Directoryname+"\n";

                                                        logger.info(Log4jConfiguration.currentTime()
                                                                +"[INFO]: Details about the problem added: \n"+pb+"Problem's ID: "+pbid);
                                                        
                                                        String incmsg="Details about the first incident of the problem added with the "+pbid+" id:\n"+
                                                         "Incident's ticket: "+ticket+"\n"+
                                                         "Incident's start date: "+st_date+"\n"+
                                                         "Incident's end date: "+en_date+"\n"+
                                                         "Incident's status: "+combostatus.getValue().toString()+"\n"
                                                         +"Incident's SAP order: "+saporder.getText()+"\n"+
                                                         "Incident's solution creator: "+SolCreator+"\n"+
                                                         "Incident's creator user: "+user+"\n"+
                                                         "Incident's escalation assign: "+String.valueOf(id_ea)+","+escalationcombo.getValue().toString()+"\n"+
                                                         "Incident's escalation status: "+escalstatuscombo.getValue().toString()+"\n"+    
                                                         "Incident's spare parts: "+SpareParts+"\n"+
                                                         "Incident's file name: "+fileIncident+"\n"+
                                                         "Incident's short description: "+textareashortdesc.getText()+"\n";
                                                        
                                                        logger.info(Log4jConfiguration.currentTime()
                                                                +"[INFO]: The first incident for the problem with id "+pbid 
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

                                                        //clear fields
                                                        //clear_fields();


                                                 }
                                                 else
                                                 {
                                                     logger.error(Log4jConfiguration.currentTime()
                                                             +"[ERROR]: Something went wrong while saving the first incident of the problem");
                                                 }
                                                



                                       }
                                       else
                                       {
                                           logger.error(Log4jConfiguration.currentTime()
                                                   +"[ERROR]: Something went wrong while saving the problem!");
                                       }
                                       


                                   } catch (SQLException ex) {
                                        logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- SQLException: "+ex+"\n\n");
                                   }catch (ParseException ex) {
                                            logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- ParseException: "+ex+"\n\n");
                                   }
               }
               }
               
           
    }
    
    public void saveIfNoEscalationFile(String SpareParts,String SolCreator)
    {
        
        try {
               String user=System.getProperty("user.name");
               conn = ConnectionDatabase.connectDb();
               
               String regex="^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]$";
               String regexd="\\d+";
               int ok=compareDates(startdatefield.getText(),enddatefield.getText());
               
               
               if(saporder.getText().isEmpty()==true)
               {
                            if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 
                                || sparepartstextarea.getText().length() > 450 ||  textareashortdesc.getText().length() >  50 || hmierror.getText().length() > 45
                                || textareadesc.getText().length() > 400 || textareasol.getText().length() > 250)
                        {
                                         logger.warn(Log4jConfiguration.currentTime()+"[WARN]: "+"Incorrect inputs in NEW INCIDENT NEW PROBLEM!");
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

                        conn=ConnectionDatabase.getConnection();

                        CallableStatement ps = conn.prepareCall("{call addNewProblemNewIncident(?,?,?,?,?,?,?,?,?)}");
                        ps.setInt(1,id_IF);
                        ps.setInt(2, id_Line);
                        ps.setInt(3, id_machine);
                        ps.setString(4, user);
                        ps.setString(5, combostatus.getValue().toString());
                        ps.setString(6, hmierror.getText());
                        ps.setString(7, textareadesc.getText());
                        ps.setInt(8, 0);
                        ps.setString(9,"DummyProblem");


                        if(ps.executeUpdate()!=0)
                        {


                                 long milliSec = System.currentTimeMillis();  
                                 String current_milisec=String.valueOf(milliSec);
                                 String ticket="#INC_"+current_milisec;

                                  //get the problem id
                                  int pbid=ConnectionDatabase.getProblemId(
                                  id_IF, id_Line, id_machine, hmierror.getText(), combostatus.getValue().toString(),user,textareadesc.getText(),0,
                                          "DummyProblem");


                                  idPb=pbid;


                                  DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
                                  Calendar obj = Calendar.getInstance();

                                  String date = formatter.format(obj.getTime());
                                  String Directoryname="Problem_"+idPb+"_"+date+"_"+"Incident_"+idInc+"_"+date;



                                  conn=ConnectionDatabase.getConnection();

                                  logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure addIncidentNewProblem()");
                                  //add in incident history
                                  CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                                  psincident.setString(1, ticket);
                                  psincident.setInt(2, pbid);
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
                                         int IncidentId=ConnectionDatabase.getIncidentId(ticket, pbid, sd, ed, 
                                         combostatus.getValue().toString(),  
                                         SolCreator, user, textareashortdesc.getText());
                                         idInc=IncidentId;

                                         logger.info(Log4jConfiguration.currentTime()+"[INFO]: ID Incident : " + idInc);

                                         String fileIncident="Problem_"+idPb+"_"+date+"_"+"Incident_"+idInc+"_"+date;

                                         conn=ConnectionDatabase.getConnection();
                                         logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure updateFileName()");
                                         CallableStatement updateFile = conn.prepareCall("{call updateFileName(?,?)}");
                                         updateFile.setInt(1, idPb);
                                         updateFile.setString(2, fileIncident);
                                         if(updateFile.executeUpdate()!=0)
                                         {
                                             logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the problem was saved in DB in NEW INCIDENT NEW PROBLEM!");
                                         }
                                         else
                                         {
                                             logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the problem "
                                                     + " of the file in DB in NEW INCIDENT NEW PROBLEM!");
                                         }


                                         conn=ConnectionDatabase.getConnection();
                                         logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure updateFileIncident()");
                                         CallableStatement updateFileInc = conn.prepareCall("{call updateFileIncident(?,?)}");
                                         updateFileInc.setInt(1, IncidentId);
                                         updateFileInc.setString(2, fileIncident);

                                         if(updateFileInc.executeUpdate()!=0)
                                         {
                                             logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the first incident was saved in DB in NEW INCIDENT NEW PROBLEM!");
                                         }
                                         else
                                         {
                                             logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the "
                                                     + " file of the first incident in DB in NEW INCIDENT NEW PROBLEM!");
                                         }
                                         String pb="IF: "+String.valueOf(id_IF)+"\n"+
                                                          "Line: "+String.valueOf(id_Line)+"\n"+
                                                          "Machine: "+String.valueOf(id_machine)+"\n"+
                                                          "Creator user: "+user+"\n"
                                                          +"Problem status: "+combostatus.getValue().toString()+"\n"+
                                                          "HMI Error: "+hmierror.getText()+"\n"+
                                                          "Problem description: "+textareadesc.getText()+"\n"+
                                                           "Escalation assign: 0"+"\n"+
                                                        "Escalation status: "+escalstatuscombo.getValue().toString()+"\n"+
                                                        "File name: "+Directoryname+"\n";

                                  logger.info(Log4jConfiguration.currentTime()+"[INFO]: Details about the problem added: \n"+pb+"\nProblem's ID: "+pbid);

                                         String incmsg="Details about the first incident of the problem added with the "+pbid+" id:\n"+
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

                                         logger.info(Log4jConfiguration.currentTime()+"[INFO]: The first incident for the problem with id "+pbid 
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
                                  else
                                  {
                                              logger.error(Log4jConfiguration.currentTime()
                                                      +"[ERROR]: Something went wrong while saving the first incident of the problem");
                                   }



                         }
                        else
                          {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong while saving the problem!");
                           }
                        }

               }
               else
               {
              
               if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 || saporder.getText().matches(regexd)==false
                       || sparepartstextarea.getText().length() > 450 ||  textareashortdesc.getText().length() >  50 || hmierror.getText().length() > 45
                       || textareadesc.getText().length() > 400 || textareasol.getText().length() > 250 || saporder.getText().length() > 8)
               {
                                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: "+"Incorrect inputs in NEW INCIDENT NEW PROBLEM!");
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
                   
               conn=ConnectionDatabase.getConnection();
               
               CallableStatement ps = conn.prepareCall("{call addNewProblemNewIncident(?,?,?,?,?,?,?,?,?)}");
               ps.setInt(1,id_IF);
               ps.setInt(2, id_Line);
               ps.setInt(3, id_machine);
               ps.setString(4, user);
               ps.setString(5, combostatus.getValue().toString());
               ps.setString(6, hmierror.getText());
               ps.setString(7, textareadesc.getText());
               ps.setInt(8, 0);
               ps.setString(9,"DummyProblem");
               
               
               if(ps.executeUpdate()!=0)
               {
                        
                        
                        long milliSec = System.currentTimeMillis();  
                        String current_milisec=String.valueOf(milliSec);
                        String ticket="#INC_"+current_milisec;
                          
                         //get the problem id
                         int pbid=ConnectionDatabase.getProblemId(
                         id_IF, id_Line, id_machine, hmierror.getText(), combostatus.getValue().toString(),user,textareadesc.getText(),0,
                                 "DummyProblem");
                         
                         
                         idPb=pbid;
                         
                         
                         DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
                         Calendar obj = Calendar.getInstance();
                         
                         String date = formatter.format(obj.getTime());
                         String Directoryname="Problem_"+idPb+"_"+date+"_"+"Incident_"+idInc+"_"+date;
                         
                         
                         
                         conn=ConnectionDatabase.getConnection();
                         
                         logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure addIncidentNewProblem()");
                         //add in incident history
                         CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                         psincident.setString(1, ticket);
                         psincident.setInt(2, pbid);
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
                                int IncidentId=ConnectionDatabase.getIncidentId(ticket, pbid, sd, ed, 
                                combostatus.getValue().toString(),  
                                SolCreator, user, textareashortdesc.getText());
                                idInc=IncidentId;
                                
                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: ID Incident : " + idInc);
                                
                                String fileIncident="Problem_"+idPb+"_"+date+"_"+"Incident_"+idInc+"_"+date;
                                
                                conn=ConnectionDatabase.getConnection();
                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure updateFileName()");
                                CallableStatement updateFile = conn.prepareCall("{call updateFileName(?,?)}");
                                updateFile.setInt(1, idPb);
                                updateFile.setString(2, fileIncident);
                                if(updateFile.executeUpdate()!=0)
                                {
                                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the problem was saved in DB in NEW INCIDENT NEW PROBLEM!");
                                }
                                else
                                {
                                    logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the problem "
                                            + " of the file in DB in NEW INCIDENT NEW PROBLEM!");
                                }
                                
                                
                                conn=ConnectionDatabase.getConnection();
                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure updateFileIncident()");
                                CallableStatement updateFileInc = conn.prepareCall("{call updateFileIncident(?,?)}");
                                updateFileInc.setInt(1, IncidentId);
                                updateFileInc.setString(2, fileIncident);
                                
                                if(updateFileInc.executeUpdate()!=0)
                                {
                                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The file of the first incident was saved in DB in NEW INCIDENT NEW PROBLEM!");
                                }
                                else
                                {
                                    logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the "
                                            + " file of the first incident in DB in NEW INCIDENT NEW PROBLEM!");
                                }
                                String pb="IF: "+String.valueOf(id_IF)+"\n"+
                                                 "Line: "+String.valueOf(id_Line)+"\n"+
                                                 "Machine: "+String.valueOf(id_machine)+"\n"+
                                                 "Creator user: "+user+"\n"
                                                 +"Problem status: "+combostatus.getValue().toString()+"\n"+
                                                 "HMI Error: "+hmierror.getText()+"\n"+
                                                 "Problem description: "+textareadesc.getText()+"\n"+
                                                  "Escalation assign: 0"+"\n"+
                                               "Escalation status: "+escalstatuscombo.getValue().toString()+"\n"+
                                               "File name: "+Directoryname+"\n";
                         
                         logger.info(Log4jConfiguration.currentTime()+"[INFO]: Details about the problem added: \n"+pb+"\nProblem's ID: "+pbid);
                         
                                String incmsg="Details about the first incident of the problem added with the "+pbid+" id:\n"+
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
                                
                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The first incident for the problem with id "+pbid 
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
                         else
                         {
                                     logger.error(Log4jConfiguration.currentTime()
                                             +"[ERROR]: Something went wrong while saving the first incident of the problem");
                          }
                         
                        
                         
                }
               else
                 {
                       logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong while saving the problem!");
                  }
               }
               
               }
               
               
           } catch (SQLException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- SQLException: "+ex+"\n");
           }catch (ParseException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- ParseException: "+ex+"\n\n");
           }
           
    }
    public void saveIfNoEscalationNoFile(String SpareParts,String SolCreator)
    {
        
        try {
               String user=System.getProperty("user.name");
               conn = ConnectionDatabase.connectDb();
               
               String regex="^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]$";
               String regexd="\\d+";
               int ok=compareDates(startdatefield.getText(),enddatefield.getText());
               
               if(saporder.getText().isEmpty()==true)
               {
                        if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 
                            || sparepartstextarea.getText().length() > 450 ||  textareashortdesc.getText().length() >  50 || hmierror.getText().length() > 45
                            || textareadesc.getText().length() > 400 || textareasol.getText().length() > 250)
                    {
                                     logger.warn(Log4jConfiguration.currentTime()+"[WARN]: "+"Incorrect inputs in NEW INCIDENT NEW PROBLEM!");
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



                    CallableStatement ps = conn.prepareCall("{call addNewProblemNewIncident(?,?,?,?,?,?,?,?,?)}");
                    ps.setInt(1,id_IF);
                    ps.setInt(2, id_Line);
                    ps.setInt(3, id_machine);
                    ps.setString(4, user);
                    ps.setString(5, combostatus.getValue().toString());
                    ps.setString(6, hmierror.getText());
                    ps.setString(7, textareadesc.getText());
                    ps.setInt(8, 0);
                    ps.setString(9,"");


                    String pb="IF: "+String.valueOf(id_IF)+"\n"+
                                                      "Line: "+String.valueOf(id_Line)+"\n"+
                                                      "Machine: "+String.valueOf(id_machine)+"\n"+
                                                      "Creator user: "+user+"\n"
                                                      +"Problem status: "+combostatus.getValue().toString()+"\n"+
                                                      "HMI Error: "+hmierror.getText()+"\n"+
                                                      "Problem description: "+textareadesc.getText()+"\n"+
                                                       "Escalation assign: 0"+"\n"+
                                                    "Escalation status: "+escalstatuscombo.getValue().toString()+"\n"+
                                                    "File name: "+"null"+"\n";


                    if(ps.executeUpdate()!=0)
                    {


                             long milliSec = System.currentTimeMillis();  
                             String current_milisec=String.valueOf(milliSec);
                             String ticket="#INC_"+current_milisec;

                              //get the problem id
                              int pbid=ConnectionDatabase.getProblemId(
                              id_IF, id_Line, id_machine, hmierror.getText(), combostatus.getValue().toString(),user,textareadesc.getText(),0,
                                      "");

                              //
                              idPb=pbid;

                              logger.info(Log4jConfiguration.currentTime()+"[INFO]: Details about the problem added: \n"+pb+"\nProblem's ID: "+pbid);

                              conn=ConnectionDatabase.getConnection();

                              //add in incident history
                              CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                              psincident.setString(1, ticket);
                              psincident.setInt(2, pbid);
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

                              String incmsg="Details about the first incident of the problem added with the "+pbid+" id:\n"+
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
                                     logger.info(Log4jConfiguration.currentTime()+"[INFO]: The first incident for the problem with id "+pbid 
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
                              else
                              {
                                          logger.error(Log4jConfiguration.currentTime()
                                                  +"[ERROR]: Something went wrong while saving the first incident of the problem");
                               }



                     }
                    else
                      {
                            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong while saving the problem!");
                       }
                    }

               }
               //e sap
               else
               {
               if(startdatefield.getText().matches(regex)==false || enddatefield.getText().matches(regex)==false || ok==0 || saporder.getText().matches(regexd)==false
                       || sparepartstextarea.getText().length() > 450 ||  textareashortdesc.getText().length() >  50 || hmierror.getText().length() > 45
                       || textareadesc.getText().length() > 400 || textareasol.getText().length() > 250 || saporder.getText().length() > 8)
               {
                                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: "+"Incorrect inputs in NEW INCIDENT NEW PROBLEM!");
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
                   
              
               
               CallableStatement ps = conn.prepareCall("{call addNewProblemNewIncident(?,?,?,?,?,?,?,?,?)}");
               ps.setInt(1,id_IF);
               ps.setInt(2, id_Line);
               ps.setInt(3, id_machine);
               ps.setString(4, user);
               ps.setString(5, combostatus.getValue().toString());
               ps.setString(6, hmierror.getText());
               ps.setString(7, textareadesc.getText());
               ps.setInt(8, 0);
               ps.setString(9,"");
               
               
               String pb="IF: "+String.valueOf(id_IF)+"\n"+
                                                 "Line: "+String.valueOf(id_Line)+"\n"+
                                                 "Machine: "+String.valueOf(id_machine)+"\n"+
                                                 "Creator user: "+user+"\n"
                                                 +"Problem status: "+combostatus.getValue().toString()+"\n"+
                                                 "HMI Error: "+hmierror.getText()+"\n"+
                                                 "Problem description: "+textareadesc.getText()+"\n"+
                                                  "Escalation assign: 0"+"\n"+
                                               "Escalation status: "+escalstatuscombo.getValue().toString()+"\n"+
                                               "File name: "+"null"+"\n";
               
               
               if(ps.executeUpdate()!=0)
               {
                        
                        
                        long milliSec = System.currentTimeMillis();  
                        String current_milisec=String.valueOf(milliSec);
                        String ticket="#INC_"+current_milisec;
                          
                         //get the problem id
                         int pbid=ConnectionDatabase.getProblemId(
                         id_IF, id_Line, id_machine, hmierror.getText(), combostatus.getValue().toString(),user,textareadesc.getText(),0,
                                 "");
                         
                         //
                         idPb=pbid;
                         
                         logger.info(Log4jConfiguration.currentTime()+"[INFO]: Details about the problem added: \n"+pb+"\nProblem's ID: "+pbid);
                         
                         conn=ConnectionDatabase.getConnection();
                         
                         //add in incident history
                         CallableStatement psincident = conn.prepareCall("{call addIncidentNewProblem(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                         psincident.setString(1, ticket);
                         psincident.setInt(2, pbid);
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
                         
                         String incmsg="Details about the first incident of the problem added with the "+pbid+" id:\n"+
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
                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The first incident for the problem with id "+pbid 
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
                         else
                         {
                                     logger.error(Log4jConfiguration.currentTime()
                                             +"[ERROR]: Something went wrong while saving the first incident of the problem");
                          }
                         
                        
                         
                }
               else
                 {
                       logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong while saving the problem!");
                  }
               }
               
               
               }
               
           } catch (SQLException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- SQLException: "+ex+"\n");
           }catch (ParseException ex) {
                logger.fatal("[FATAL]: NEW INCIDENT NEW PROBLEM --- ParseException: "+ex+"\n\n");
           }
           
    }
    public void savingFiles(List<File> listFiles,String idProblem,String idIncident)
    {
            List<String> list=new ArrayList<>();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: SAVING FILE of the PROBLEM in NEW INCIDENT NEW PROBLEM\n");     
        
             DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
             Calendar obj = Calendar.getInstance();
             String date = formatter.format(obj.getTime());
             
             LocalTime timeObj = LocalTime.now();
             String currentTime = timeObj.toString();
             
             String time=currentTime.substring(0,5);
            //String finaldate=date+"_"+currentTime.substring(0,2)+"_"+currentTime.substring(3,5)+"_"+currentTime.substring(6,8);
             //creare folder
             String finaldate=date+"_";
             String name="Problem_"+idProblem+"_"+date+"_Incident_"+idInc+"_"+date;
             String url="./";
             File directory=new File(url,name);
             
             if(directory.mkdir())
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+directory.getName()+" was created in NEW INCIDENT NEW PROBLEM\n");
                 
             
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while creating the folder "+directory.getName()+" in NEW INCIDENT NEW PROBLEM\n");
             }
             //odata creat verificam daca exista si adaugam fisierele ce tin de problem si adaugam un alt folder Incident_1 care va contine aceste fisiere
             if(directory.exists())
             {
                 list.add(directory.getName());
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+directory.getName()+" exists in NEW INCIDENT NEW PROBLEM");
                 int i=0;
                 //in listFiles avem fisierele selectate si le adaugam
                 
                 for(File f:listFiles)
                 {
                     addingFile(f,directory);
                     
                     
                 }
                 
                  
                  
                     try {
                         
                         
                         
                         
                         //arhivam folderu de problema
                         zipProblem(directory,directory.getName());
                         
                         String urlArchive="./"+directory.getName()+".zip";
                         String destinationArchive=directory.getName()+".zip";
                         
                         //trimitem pe server
                         sendOnServer(urlArchive,destinationArchive);
                         
                         //eliberam memorie
                         File fil=new File(urlArchive);
                         FileUtils.deleteDirectory(directory);
                         FileUtils.delete(fil);

                         
                         
                     } catch (IOException ex) {
                        logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: NEW INCIDENT NEW PROBLEM --- IOException: "+ex);
                     }
                      
                      
                  
                  
                  
                  
                  
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: "+directory.getName()+" doesn't exists in NEW INCIDENT NEW PROBLEM");
             }
         
    }
    public void onStatus()
    {
        if(combostatus.getSelectionModel().isEmpty()==false && combostatus.getValue().toString().equals("unsolved"))
        {
            
            if(textareasol.getText().isEmpty()==false)
            {
                textareasol.clear();
            }
            textareasol.setEditable(false);
        }
        else if(combostatus.getSelectionModel().isEmpty()==false && combostatus.getValue().toString().equals("solved"))
        {
            
            textareasol.setEditable(true);
        }
    }
    
    
    public void save() throws IOException
    {
        
       logger.info(Log4jConfiguration.currentTime()+"[INFO]: SAVE button in NEW INCIDENT NEW PROBLEM WAS PRESSED!");
        
       boolean isIF=comboif.getSelectionModel().isEmpty();
       boolean isLine=comboline.getSelectionModel().isEmpty();
       boolean isMachine=combomachine.getSelectionModel().isEmpty();
       boolean isStartDate=startdatefield.getText().isEmpty();
       boolean isEndDate=enddatefield.getText().isEmpty();
       boolean isSap=saporder.getText().isEmpty();
       boolean isShortDesc=textareashortdesc.getText().isEmpty();
       boolean isHMI=hmierror.getText().isEmpty();
       boolean isStatus=combostatus.getSelectionModel().isEmpty();
       boolean isEscalation=escalstatuscombo.getSelectionModel().isEmpty();
       boolean isSpare=sparepartstextarea.getText().isEmpty();
       boolean isDesc=textareadesc.getText().isEmpty();
       boolean isSolCreator=textareasol.getText().isEmpty();
       
       List<String> FilesListView=new ArrayList<>();
       for(String el: listviewfiles.getItems())
       {
           FilesListView.add(el);
       }
       convertSparePartsToDb();
      
                    
       //campurile obligatorii
       if(isIF==true || isLine==true || isMachine==true || isStartDate==true || isEndDate==true ||  isShortDesc==true || isHMI==true
               || comboif.getValue().toString().equals("Select...") || comboline.getValue().toString().equals("Select...")
               || combomachine.getValue().toString().equals("Select...") ||isDesc==true || combostatus.getValue().toString().equals("Select...")
               || escalstatuscombo.getValue().toString().equals("Select..."))
       {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty fields in NEW INCIDENT NEW PROBLEM!");
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
                    logger.error(Log4jConfiguration.currentTime()+"[ERROR]: No engineer was selected and the status of escalation is 'YES' in NEW INCIDENT NEW PROBLEM");
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
                   
                    if(isSolCreator==true && combostatus.getSelectionModel().isEmpty()==false && combostatus.getValue().toString().equals("solved"))
                    {
                        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: A 'solved' problem status must have a solution in NEW INCIDENT NEW PROBLEM");
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
                                         else if(FilesListView.isEmpty()==false)
                                        {
                                            //solved,escalare,no spare,files
                                            List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            saveIfIsEscalationFile("",textareasol.getText());
                                            savingFiles(filelist,String.valueOf(idPb),String.valueOf(idInc));
                                            
                                            
                                            
                                        }



                            }
                            else if(isSpare==false)
                            {

                                    if(FilesListView.isEmpty()==true)
                                    {
                                        //solved,escalare,spare,no files
                                        saveIfIsEscalationNoFile(part_numbere,textareasol.getText());
                                    }
                                    else if(FilesListView.isEmpty()==false)
                                    {
                                        //solved,escalare,spare,files
                                        
                                            List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            
                                            saveIfIsEscalationFile(part_numbere,textareasol.getText());
                                            savingFiles(filelist,String.valueOf(idPb),String.valueOf(idInc));
                                    }


                            }
                    }
                    
               }
         }
           else if(isEscalation==false && escalstatuscombo.getValue().toString().equals("no"))
           {
                    if(isSolCreator==true && combostatus.getSelectionModel().isEmpty()==false && combostatus.getValue().toString().equals("solved"))
                    {
                        logger.error(Log4jConfiguration.currentTime()+"[ERROR]: A solved status of a problem must have  a solution in NEW INCIDENT NEW PROBLEM");
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
                                        else if(FilesListView.isEmpty()==false)
                                        {
                                            //solved,no spare,no escalare,files
                                            List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            saveIfNoEscalationFile("",textareasol.getText());
                                            savingFiles(filelist,String.valueOf(idPb),String.valueOf(idInc));
                                            
                                        }



                            }
                            else if(isSpare==false)
                            {

                                    if(FilesListView.isEmpty()==true)
                                    {
                                        //solved,spare,no escalare,no files
                                        saveIfNoEscalationNoFile(part_numbere,textareasol.getText());
                                         
                                    }
                                    else if(FilesListView.isEmpty()==false)
                                    {
                                         //solved,spare,no escalare,files
                                         List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            saveIfNoEscalationFile(part_numbere,textareasol.getText());
                                            savingFiles(filelist,String.valueOf(idPb),String.valueOf(idInc));
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
                   logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No engineer was selected and the status of escalation is 'YES' in NEW INCIDENT NEW PROBLEM");
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
                                            //solved,escalare,no spare,no files
                                            saveIfIsEscalationNoFile("","");
                                        }
                                         else if(FilesListView.isEmpty()==false)
                                        {
                                            //solved,escalare,no spare,files
                                            List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            saveIfIsEscalationFile("","");
                                            savingFiles(filelist,String.valueOf(idPb),String.valueOf(idInc));
                                            
                                        }



                            }
                            else if(isSpare==false)
                            {

                                    if(FilesListView.isEmpty()==true)
                                    {
                                        //solved,escalare,spare,no files
                                        saveIfIsEscalationNoFile(part_numbere,"");
                                    }
                                    else if(FilesListView.isEmpty()==false)
                                    {
                                        //solved,escalare,spare,files
                                        
                                            List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                           
                                            saveIfIsEscalationFile(part_numbere,"");
                                            savingFiles(filelist,String.valueOf(idPb),String.valueOf(idInc));
                                            
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
                                            //no spare,solved,no files,no escalare
                                            saveIfNoEscalationNoFile("","");
                                            
                                        }
                                        else if(FilesListView.isEmpty()==false)
                                        {
                                            //solved,no spare,no escalare,files
                                            List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            saveIfNoEscalationFile("","");
                                            savingFiles(filelist,String.valueOf(idPb),String.valueOf(idInc));
                                            
                                        }



                            }
                            else if(isSpare==false)
                            {

                                    if(FilesListView.isEmpty()==true)
                                    {
                                        //solved,spare,no escalare,no files
                                        saveIfNoEscalationNoFile(part_numbere,"");
                                         
                                    }
                                    else if(FilesListView.isEmpty()==false)
                                    {
                                         //solved,spare,no escalare,files
                                         List<File> filelist=new ArrayList<>();
                                            for(String f: FilesListView)
                                            {
                                                File file=new File(f);
                                                filelist.add(file);
                                            }
                                            
                                            saveIfNoEscalationFile(part_numbere,"");
                                            savingFiles(filelist,String.valueOf(idPb),String.valueOf(idInc));
                                    }


                            }
                            
           }

            
       }
       
       
        
    }
    
    public void clear_fields()
    {
        saporder.clear();
        sparepartstextarea.clear();
        textareashortdesc.clear();
        hmierror.clear();
        textareadesc.clear();
        textareasol.clear();
        
        comboif.setValue(" ");
        comboline.setValue(" ");
        combomachine.setValue(" ");
        combostatus.setValue(" ");
        escalstatuscombo.setValue(" ");
        escalationcombo.setValue(" ");
        
        
        comboline.getItems().clear();
        combomachine.getItems().clear();
        
        escalationcombo.getItems().clear();
        listviewfiles.getItems().clear();
            
        
        
        
    }
    
    public void cancelSelection()
    {
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: The selection of files has been cleared in NEW INCIDENT NEW PROBLEM!\n");
             listviewfiles.getSelectionModel().clearSelection();
       
       
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
        
        
       
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: NEW INCIDENT NEW PROBLEM STARTED!");
        
       
        
        btnchoosefile.setGraphic(iconfile);
        deletefilesbtn.setGraphic(deleteicon);
        
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        addsparepartsbtn.setGraphic(addiconspare);
        combostatus.setItems(itemscombostatus);
        escalstatuscombo.setItems(itemscomboescalstatus);
        
        comboif.setValue("Select...");
        //comboline.setValue("Select...");
        //combomachine.setValue("Select...");
        combostatus.setValue("Select...");
        escalstatuscombo.setValue("Select...");
        
        itemscombo=ConnectionDatabase.getIFCombo();
             if(!itemscombo.isEmpty())
             {
                 Collections.sort(itemscombo);
                 itemscombo.add(0,"Select...");
                 comboif.setItems(itemscombo);        

             }
             else
             {
                 comboif.getItems().clear();
                 comboif.setValue("");
             }
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
            
            //System.out.println(compareDates(startdatefield.getText(),enddatefield.getText()));
             
             
             startdatefield.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     
                             
                     String regex="^(3[01]|0[1-9]|[12][0-9])\\/(1[0-2]|0[1-9])\\/(-?(?:[1-9][0-9]*)?[0-9]{4})( (?:2[0-3]|[01][0-9]):[0-5][0-9])?$";
                     String regexd="\\d+";
                     if (newValue.toString().matches(regex)==true) { 
                            
                             startdatefield.setStyle("-fx-border-width: 0.5px ;");
                             int ok=compareDates(newValue.toString(),enddatefield.getText());
                             //System.out.println(ok);
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
     
            hmierror.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>45) { 
                            
                            hmierror.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                     }
                     else{
                          hmierror.setStyle("-fx-border-width: 0.5px ;");
                         
                     }
                     
                       
           
                 }
                 
                 
             });
                    
            textareasol.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>250) { 
                            
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
                     if (newValue.toString().length()>50) 
                     { 
                      textareashortdesc.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");                                  
                                                 
                     }
                     else{
                         textareashortdesc.setStyle("-fx-border-width: 0.5px ;");
 
                         
                     }
                     
                       
           
                 }
                 
                 
             });
            
            
            
            
            textareadesc.textProperty().addListener((observable,oldValue,newValue) ->{
                 if(newValue == null || newValue.isEmpty()){
                            return;
                        }
                 else
                 {
                     if (newValue.toString().length()>400) { 
                            
                         textareadesc.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");    
                     }
                     else{
                         textareadesc.setStyle("-fx-border-width: 0.5px ;");
                         
                     }
                     
                       
           
                 }
                 
                 
             });
            
            
            listviewfiles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            
            
           
            
            listviewfiles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            
            
        
            
        
    }
    
}
