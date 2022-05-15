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
public class EditmachineController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox comboif;

    @FXML
    private ComboBox comboline;

    @FXML
    private TextField name;

   
   
    @FXML
    private Button savebtn;
    
     

    @FXML
    private TextField invn;

    @FXML
    private Button cancelbtn;
    Connection conn=null;
    
     @FXML
    private ImageView cancelicon;
     
     @FXML
    private ImageView saveicon;
    
    
    ObservableList<String> itemscombo=FXCollections.observableArrayList();
    
    ObservableList<String> itemscomboline=FXCollections.observableArrayList();
     ObservableList<String> itemscomboline1=FXCollections.observableArrayList();
   public org.apache.logging.log4j.Logger logger= LogManager.getLogger(EditmachineController.class);
   public void SavingUpdate(String tablename,int id)
    {
        conn=ConnectionDatabase.getConnection();
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             //System.out.println(formatter.format(date));
             //java.sql.Date sql = new java.sql.Date(date.getTime());
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in EDIT MACHINE STRUCTURE");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in EDIT MACHINE STRUCTURE has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in EDIT MACHINE STRUCTURE!");
             }
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: EDIT MACHINE STRUCTURE --- SQLException: "+ex+"\n\n");
         }
        
        
    }
    
    public void save()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The save button from EDIT MACHINE STRUCTURE was pressed!");
        /*if(name.getText().length() > 45 || invn.getText().length() > 50)
            {
                        
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are incorrect in EDIT MACHINE STRUCTURE\n\n");
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
        */
        
        if(name.getText().isEmpty()==false && invn.getText().isEmpty()==false 
                &&    comboline.getSelectionModel().isEmpty()==false && !comboline.getValue().toString().equals("Select..."))
        {
            if(name.getText().length() > 45 || invn.getText().length() > 50)
            {
                        
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are incorrect in EDIT MACHINE STRUCTURE\n\n");
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
                try {
                int lineid=ConnectionDatabase.getLineId(comboline.getValue().toString());
                conn = ConnectionDatabase.connectDb();
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure updateLine() in EDIT MACHINE STRUCTURE");
                CallableStatement ps = conn.prepareCall("{call updateMachine(?,?,?,?)}");
                ps.setInt(1,Data.id_machine_structure);
                ps.setInt(2, lineid);
                
                ps.setString(3, name.getText().toString());
               
                ps.setString(4, invn.getText().toString());
                //ps.execute();
                
                if(ps.executeUpdate()!=0)
                {
                                       logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was saved in EDIT MACHINE STRUCTURE: \n"+
                                               "IF: "+comboif.getValue().toString()+"\n"+
                                               "Line: "+comboline.getValue().toString()+"\n"+
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
                                       dialog.setContentText("Your data has been saved!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       SavingUpdate("machine_structure",Data.id_machine_structure);

                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();
                }
                else
                {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while saving the data in EDIT MACHINE STRUCTURE");
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
                logger.fatal("[FATAL]: EDIT MACHINE STRUCTURE--- SQLException: "+ex+"\n\n");
            }
        }
        else if(name.getText().isEmpty()==false && invn.getText().isEmpty()==false 
                &&    comboline.getSelectionModel().isEmpty()==false && comboline.getValue().toString().equals("Select..."))
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The line is empty in EDIT MACHINE STRUCTURE\n\n");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                //Adding buttons to the dialog pane
                dialog.setContentText("Please select a 'Line'!");
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
        }
        else if (name.getText().isEmpty()==false && invn.getText().isEmpty()==false 
                &&    comboline.getSelectionModel().isEmpty()==true)
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: There are no lines or an IF wasn't selected in EDIT MACHINE STRUCTURE\n\n");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("There are no lines or an IF wasn't selected!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
        }
        else if(name.getText().isEmpty()==true  || invn.getText().isEmpty()==true )
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The fields are empty in EDIT MACHINE STRUCTURE\n\n");
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
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Cancel button from EDIT MACHINE STRUCTURE was pressed!\n\n");
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
               if(isMyComboBoxEmpty == false && comboif.getValue().toString().equals("Select...")==false)
               {
                  logger.info(Log4jConfiguration.currentTime()+"[INFO]: The IF selected from EDIT MACHINE STRUCTURE: "+comboif.getValue().toString()+"\n");
                  int ifid=ConnectionDatabase.getIfId(comboif.getValue().toString());
                  
                  
                  itemscomboline=ConnectionDatabase.getLinesfromIf(ifid);
                  if(!itemscomboline.isEmpty())
                   {
                            comboline.setValue("Select...");
                            
                            Collections.sort(itemscomboline,c);
                            itemscomboline.add(0, "Select...");
                            comboline.setItems(itemscomboline);
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF LINES from selected IF in EDIT MACHINE STRUCTURE: \n");
                            for(String el:itemscomboline)
                            {
                                logger.info(el+"\n");
                            }
                            logger.info("\n");
                   
                   }
                        
                             
                  else {
                            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: There are no lines in the selected IF in EDIT MACHINE STRUCTURE\n\n");
                            comboline.getItems().clear();
                            
                            
                    }
                  
                  
               }
               else if(isMyComboBoxEmpty == false && comboif.getValue().toString().equals("Select...")==true)
               {
                   logger.info(Log4jConfiguration.currentTime()+"[INFO]: 'Select...' option was selected in IF: \n\n");
                   comboline.getItems().clear();
                   comboline.setValue("Select...");
               }
               
               
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT MACHINE STRUCTURE STARTED");
            String line_desc=ConnectionDatabase.getLineDescription(Data.line_id_machine_structure);
            String if_desc=ConnectionDatabase.getIfDescription(Data.line_id_machine_structure);
            comboline.setValue(line_desc);
            comboif.setValue(if_desc);
            savebtn.setGraphic(saveicon);
            cancelbtn.setGraphic(cancelicon);
            
            int idif=ConnectionDatabase.getifid(comboif.getValue().toString());
            itemscomboline1=ConnectionDatabase.getLinesfromIf(idif);
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
            Collections.sort(itemscomboline1,c);
            comboline.setItems(itemscomboline1);
            itemscomboline1.add(0, "Select...");
            
            itemscombo=ConnectionDatabase.getIFCombo();
            if(!itemscombo.isEmpty())
            {
                Collections.sort(itemscombo,c);
                itemscombo.add(0, "Select...");
                comboif.setItems(itemscombo);
                
            }
            else
            {
                comboif.getItems().clear();
                comboif.setValue("");
            }
            name.setText(Data.machine_name);
            
            invn.setText(Data.machine_invn);
            
            name.textProperty().addListener((observable,oldValue,newValue) ->{
                if(newValue == null || newValue.isEmpty()){
                    return;
                }
                else{
                    
                    if(newValue.toString().length()>50)
                    {
                        name.setStyle("-fx-border-color: red ; -fx-border-width: 1.5px ;");
                    }
                    else
                    {
                        name.setStyle("-fx-border-width: 0.5px ;");
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
        } catch (SQLException ex) {
            Logger.getLogger(EditmachineController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    
}
