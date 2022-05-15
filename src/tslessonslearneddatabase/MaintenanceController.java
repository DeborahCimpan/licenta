/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tslessonslearneddatabase;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;

import deletecontroller.Deleteacccess;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import toolclass.Access;
import toolclass.AutoCompleteComboBoxListener;
import toolclass.EscalationTeam;
import toolclass.IfStruct;
import toolclass.IncidentEscalation;
import toolclass.IncidentHistory;
import toolclass.Incidents;
import toolclass.LineStruct;
import toolclass.MachineStruct;
import toolclass.Overview;
import toolclass.ParticularDetailsProblem;
import toolclass.ProblemHistory;
import toolclass.ShTeam;
import toolclass.SpParts;
import toolclass.SparePartsPinguin;
import toolclass.Users;
import static tslessonslearneddatabase.ConnectionDatabase.conn;
import static tslessonslearneddatabase.ConnectionDatabase.logger;

/**
 * FXML Controller class
 *
 * @author cimpde1
 */
public class MaintenanceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(MaintenanceController.class);
    
    @FXML
    private TabPane tabpane;
    
    private Stage newincpbstage;
    
    private Stage newinexpbstage;
    
    private Stage incidentsOverview;
    
    
    private Stage editPbHistory;
    
    private Stage deletePbHistory;
    
    
    private Stage editIncidentHistory;
    
    private Stage deleteIncidentHistory;
    
    
    private Stage addIf;
    
    private Stage editIf;
    
    private Stage deleteIf;
    
    
    private Stage addLine;
    
    private Stage editLine;
    
    private Stage deleteLine;
    
    
    private Stage addMachine;
    
    private Stage editMachine;
    
    private Stage deleteMachine;
    
    
    private Stage addTech;
    private Stage editTech;
    private Stage deleteTech;
    
    private Stage addEngineer;
    private Stage editEngineer;
    private Stage deleteEngineer; 
    
    private Stage addSpare;
    private Stage editSpare;
    private Stage deleteSpare;
    
    private Stage editAccess;
    private Stage deleteAccess;
    
    public void onAnchorPaneProblemHistory()
    {
        table_problem_history.getSelectionModel().clearSelection();
    }
     public void onAnchorPaneIncidentHistory()
    {
        table_incident_history.getSelectionModel().clearSelection();
    }
     public void onAnchorPaneIfStructure()
    {
        table_if_structure.getSelectionModel().clearSelection();
    }
     public void onAnchorPaneLineStructure()
    {
        table_line_structure.getSelectionModel().clearSelection();
    }
     public void onAnchorPaneMachineStructure()
    {
        table_machine_structure.getSelectionModel().clearSelection();
    }
     
      public void onAnchorPaneTechnicians()
    {
        table_shift_team.getSelectionModel().clearSelection();
    }
     public void onAnchorPaneEngineers()
    {
        table_escalation_team.getSelectionModel().clearSelection();
    }
     public void onAnchorPaneSpareParts()
    {
        table_spare_parts.getSelectionModel().clearSelection();
    }
     public void onAnchorPaneAccess()
    {
        table_access.getSelectionModel().clearSelection();
    }
     public void onAnchorPaneOverview()
     {
         table_overview.getSelectionModel().clearSelection();
         table_problem_history.getSelectionModel().clearSelection();
         table_incident_history.getSelectionModel().clearSelection();
         table_if_structure.getSelectionModel().clearSelection();
         table_line_structure.getSelectionModel().clearSelection();
         table_machine_structure.getSelectionModel().clearSelection();
         table_shift_team.getSelectionModel().clearSelection();
         table_escalation_team.getSelectionModel().clearSelection();
         table_spare_parts.getSelectionModel().clearSelection();
         table_access.getSelectionModel().clearSelection();
         //Data.id_pb=-1;
     }
    
   
    //----------------------------------------------------------------------------OVERVIEW-----------------------------------------------------------------
     @FXML
    private ComboBox<String> comboifover;

    @FXML
    private ComboBox<String> combolineover;

    @FXML
    private ComboBox<String> combomachineover;
    
   
    @FXML
    private TextField keywordsover;
    @FXML
    private ImageView searchicon;
   
    @FXML
    private ImageView donwloadfileicon;
    
    @FXML
    private Button searchbtn;

    @FXML
    private Button newincfornewproblem;

    @FXML
    private Button btnnewinexpb;

    @FXML
    private Button downloadbtn;

    @FXML
    private Button btnincidentoverview;
    @FXML
    private TableView<Overview> table_overview;

    @FXML
    private TableColumn<Overview,String> col_line_over;
    
    @FXML
    private TableColumn<Overview,String> col_machine_over;

    @FXML
    private TableColumn<Overview,String> col_hmi_over;

    @FXML
    private TableColumn<Overview,String> col_pb_over;
    
    @FXML
    private TableColumn<Overview,String> col_nr_incidents;
    
    

    @FXML
    private ScrollPane scrollpaneoverview;

    
    ObservableList<Overview> dataProblemOverview;
    Connection connp=null;
    ObservableList<Incidents> incidents_list;
     int id_machine_over=-1;
    int id_IF_over=-1;
    int id_Line_over=-1;
    int index=-1;
    
    int idPb=-1;
    String Directory=null;
    List<String> filesIncidents=new ArrayList<>();
    
    ObservableList<String> itemscomboifover=FXCollections.observableArrayList();
    ObservableList<String> itemscombolineover=FXCollections.observableArrayList();
    ObservableList<String> itemscombomachineover=FXCollections.observableArrayList();
    
      
     public void onIFOverview()
    {
                
                
               boolean isMyComboBoxEmpty = comboifover.getSelectionModel().isEmpty();
               if(isMyComboBoxEmpty == false && !comboifover.getValue().toString().equals("Select..."))
               {
                   
                   int ifid=ConnectionDatabase.getIfId(comboifover.getValue().toString());
                   id_IF_over=ifid;
                   if(ifid!=-1)
                   {
                        combolineover.setValue("Select...");
                        itemscombolineover=ConnectionDatabase.getLinesfromIf(ifid);
                        if(!itemscombolineover.isEmpty())
                        {
                            id_Line_over=-1;
                             //combolineover.setValue(itemscombolineover.get(0));
                             
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
                                Collections.sort(itemscombolineover,c);
                                itemscombolineover.add(0, "Select...");
                                combolineover.setItems(itemscombolineover);
                                String lineinit="[1] : "+itemscombolineover.get(1);
                                String msgline="OUTPUT LIST OF Lines in OVERVIEW from the "+comboifover.getValue().toString()+ " \n"+lineinit+" \n";
                                for(int i=2;i<itemscombolineover.size();i++)
                                {
                                    msgline=msgline+ "[" + i +"]" + " : " + itemscombolineover.get(i) + "\n";

                                }
                                logger.info(Log4jConfiguration.currentTime()+"[INFO] : "+msgline);
                             
                        }

                         
                        else{
                            
                            combolineover.getItems().clear();
                            logger.warn(Log4jConfiguration.currentTime()+"[WARN] : Empty list of Lines in OVERVIEW!");
                            
                        }
                   }
                 
               }
               //caz in care la IF e Select...
               else if(isMyComboBoxEmpty == false && comboifover.getValue().toString().equals("Select..."))
               {   
                        combolineover.setValue("Select...");
                        combomachineover.setValue("Select...");
                        itemscombolineover=ConnectionDatabase.getLinesName();
                        id_IF_over=-1;
                        if(!itemscombolineover.isEmpty())
                        {
                                    id_Line_over=-1;
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
                             Collections.sort(itemscombolineover,c);
                             itemscombolineover.add(0, "Select...");
                             combolineover.setItems(itemscombolineover);
                             
                            String lineinit="[1] : "+itemscomboifover.get(1);
                            String msgline="OUTPUT LIST OF Lines from OVERVIEW when 'Select...' option from IF was selected: "+" \n"+lineinit+" \n";
                            for(int i=2;i<itemscombolineover.size();i++)
                            {
                                msgline=msgline+ "[" + i +"]" + " : " + itemscombolineover.get(i) + "\n";

                            }
                            logger.info(Log4jConfiguration.currentTime()+"[INFO] : "+msgline);
                        }
                        else
                        {
                            combolineover.getItems().clear();
                            combolineover.setValue("");
                            logger.warn(Log4jConfiguration.currentTime()+"[WARN] : Empty list of Lines in OVERVIEW!");
                        }
                        
                itemscombomachineover=ConnectionDatabase.getMachinesDesc();
                if(!itemscombomachineover.isEmpty())
                        {
                            id_machine_over=-1;
                            Pattern p = Pattern.compile("\\d+$");
                            Comparator<String> c = new Comparator<String>() {
                                @Override
                                public int compare(String object1, String object2) {
                                    Matcher m = p.matcher(object1);
                                    Integer number1 = null;
                                    if (!m.find()) 
                                    {
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
                            Collections.sort(itemscombomachineover,c);
                            itemscombomachineover.add(0, "Select...");
                            combomachineover.setItems(itemscombomachineover);
                            
                            logger.info(Log4jConfiguration.currentTime()+" [INFO]: The list of all machines have been loaded in OVERVIEW");
                            
                            
                        }
                        else
                        {
                            combomachineover.getItems().clear();
                            combomachineover.setValue("");
                            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty list of machines in OVERVIEW");
                        }
               }
               
    }
    public void onLineOverview()
    {
        
        
        boolean isMyLine=combolineover.getSelectionModel().isEmpty();
        if(isMyLine == false && !combolineover.getValue().toString().equals("Select..."))
        {
            int idline=ConnectionDatabase.getLineId(combolineover.getValue().toString());
            id_Line_over=idline;
            if(idline!=-1)
            {
                itemscombomachineover=ConnectionDatabase.getMachineCombo(idline);
                if(!itemscombomachineover.isEmpty())
                {
                    id_machine_over=-1;
                    //combomachineover.setValue(itemscombomachineover.get(0));
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
                    Collections.sort(itemscombomachineover,c);
                    itemscombomachineover.add(0, "Select...");
                    combomachineover.setItems(itemscombomachineover);
                    String machineinit="[1] : "+itemscombomachineover.get(1);
                    String msgmachine="OUTPUT LIST OF Machines from OVERVIEW when "+
                    combolineover.getValue().toString()+ " line was selected: "+" \n"+machineinit+" \n";
                    for(int i=2;i<itemscombomachineover.size();i++)
                    {
                        msgmachine=msgmachine+ "[" + i +"]" + " : " + itemscombomachineover.get(i) + "\n";

                    }
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+msgmachine);
                    
                }
                else
                {
                    combomachineover.getItems().clear();
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: Empty list of machines in OVERVIEW!");
                }
            }
            
        }
        else if(isMyLine == false && combolineover.getValue().toString().equals("Select..."))
        {
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: 'Select...' option was selected from line combobox ");
                combomachineover.setValue("Select...");
                itemscombomachineover=ConnectionDatabase.getMachinesDesc();
                id_Line_over=-1;
                if(!itemscombomachineover.isEmpty())
                        {
                            id_machine_over=-1;
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
                            Collections.sort(itemscombomachineover,c);
                            itemscombomachineover.add(0, "Select...");
                            combomachineover.setItems(itemscombomachineover);
                            
                            logger.info(Log4jConfiguration.currentTime()+" [INFO]: The list of all machines have been loaded in OVERVIEW");
                            
                        }
                        else
                        {
                            combomachineover.getItems().clear();
                            combomachineover.setValue("");
                            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty list of machines in OVERVIEW!");
                        }
                
        }
    }
     
    public void onMachineOverview()
    {
                    
                    
                    boolean isMyComboBox=combomachineover.getSelectionModel().isEmpty();
                    if(isMyComboBox==false && !combomachineover.getValue().toString().equals(" ") && !combomachineover.getValue().toString().equals("Select..."))
                    {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Machine selected: "+combomachineover.getValue().toString()+" in OVERVIEW \n");
                        String machine=combomachineover.getValue();
                        int index1=machine.indexOf("(");
                        int index2=machine.indexOf(")"); 
                        String invname=machine.substring(index1+1, index2);
                        String mach=machine.substring(0,index1);
                        id_machine_over=ConnectionDatabase.getMachineId(mach, invname);
                  
                    }
                    else if(isMyComboBox==false && combomachineover.getValue().toString().equals("Select..."))
                    {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+"'Select...' option was selected from machine combobox in OVERVIEW.");
                        id_machine_over=-1;
                    }
                    
    }
    public void onOverview()
    {
                
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Setting things for OVERVIEW TAB");
                
               
                
                //setting for table
                
                table_overview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                col_line_over.setMaxWidth(1f * Integer.MAX_VALUE * 20);
                col_machine_over.setMaxWidth(1f * Integer.MAX_VALUE * 20);
                col_hmi_over.setMaxWidth(1f * Integer.MAX_VALUE * 20);
                col_pb_over.setMaxWidth(1f * Integer.MAX_VALUE * 20);
                col_nr_incidents.setMaxWidth(1f * Integer.MAX_VALUE * 20);
                
                

                col_line_over.setCellFactory(new Callback<TableColumn<Overview, String>,TableCell<Overview,String>>() {

                        @Override

                        public TableCell<Overview, String> call(TableColumn<Overview, String> param) {

                            TableCell<Overview, String> cell = new TableCell<>();

                            Text text=new Text();

                            cell.setGraphic(text);

                            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                            text.wrappingWidthProperty().bind(col_line_over.widthProperty());

                            text.textProperty().bind(cell.itemProperty());

                            return cell;

                        }
                });
                
                
                col_machine_over.setCellFactory(new Callback<TableColumn<Overview, String>,TableCell<Overview,String>>() {

                        @Override

                        public TableCell<Overview, String> call(TableColumn<Overview, String> param) {

                            TableCell<Overview, String> cell = new TableCell<>();

                            Text text=new Text();

                            cell.setGraphic(text);

                            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                            text.wrappingWidthProperty().bind(col_machine_over.widthProperty());

                            text.textProperty().bind(cell.itemProperty());

                            return cell;
                        }
                });
                
                col_hmi_over.setCellFactory(new Callback<TableColumn<Overview, String>,TableCell<Overview,String>>() {

                        @Override

                        public TableCell<Overview, String> call(TableColumn<Overview, String> param) {

                            TableCell<Overview, String> cell = new TableCell<>();

                            Text text=new Text();

                            cell.setGraphic(text);

                            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                            text.wrappingWidthProperty().bind(col_hmi_over.widthProperty());

                            text.textProperty().bind(cell.itemProperty());

                            return cell;
                        }
                });
                
                col_pb_over.setCellFactory(new Callback<TableColumn<Overview, String>,TableCell<Overview,String>>() {

                        @Override

                        public TableCell<Overview, String> call(TableColumn<Overview, String> param) {

                            TableCell<Overview, String> cell = new TableCell<>();

                            Text text=new Text();

                            cell.setGraphic(text);

                            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                            text.wrappingWidthProperty().bind(col_pb_over.widthProperty());

                            text.textProperty().bind(cell.itemProperty());

                            return cell;
                        }
                });
                
                col_nr_incidents.setCellFactory(new Callback<TableColumn<Overview, String>,TableCell<Overview,String>>() {

                        @Override

                        public TableCell<Overview, String> call(TableColumn<Overview, String> param) {

                            TableCell<Overview, String> cell = new TableCell<>();

                            Text text=new Text();

                            cell.setGraphic(text);

                            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                            text.wrappingWidthProperty().bind(col_nr_incidents.widthProperty());

                            text.textProperty().bind(cell.itemProperty());

                            return cell;
                        }
                });
                
                
                
                
                
                if(comboifover.getSelectionModel().isEmpty()==true)
                {
                       comboifover.setValue("Select...");
                       itemscomboifover=ConnectionDatabase.getIFCombo();
                       if(!itemscomboifover.isEmpty())
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
                           Collections.sort(itemscomboifover,c);
                           itemscomboifover.add(0, "Select...");
                           comboifover.setItems(itemscomboifover); 
                           
                           String ifinit="[1] : "+itemscomboifover.get(1);
                           String msgif="OUTPUT LIST OF IFs from OVERVIEW: "+" \n"+ifinit+" \n";
                           for(int i=2;i<itemscomboifover.size();i++)
                           {
                               msgif=msgif+ "[" + i +"]" + " : " + itemscomboifover.get(i) + "\n";
                               
                           }
                           logger.info(Log4jConfiguration.currentTime() + "[INFO] : "+msgif);

                       }

                }
                combolineover.setValue("Select...");
                itemscombolineover=ConnectionDatabase.getLinesName();
                        if(!itemscombolineover.isEmpty())
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
                             Collections.sort(itemscombolineover,c);
                             itemscombolineover.add(0, "Select...");
                             combolineover.setItems(itemscombolineover);
                                String lineinit="[1] : "+itemscombolineover.get(1);
                                String msgline="OUTPUT LIST OF Lines from OVERVIEW: "+" \n"+lineinit+" \n";
                                for(int i=2;i<itemscombolineover.size();i++)
                                {
                                    msgline=msgline+ "[" + i +"]" + " : " + itemscombolineover.get(i) + "\n";

                                }
                                logger.info(Log4jConfiguration.currentTime()+ "[INFO] : "+msgline);
                        }
                        else
                        {
                            combolineover.getItems().clear();
                            combolineover.setValue("");
                            logger.warn(Log4jConfiguration.currentTime()+"[WARN] : Empty list of lines in OVERVIEW!");
                        }
                combomachineover.setValue("Select...");
                itemscombomachineover=ConnectionDatabase.getMachinesDesc();
                if(!itemscombomachineover.isEmpty())
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
                            Collections.sort(itemscombomachineover,c);
                            itemscombomachineover.add(0, "Select...");
                            combomachineover.setItems(itemscombomachineover);
                            
                            logger.info(Log4jConfiguration.currentTime()+" [INFO]: The list of all machines have been loaded in OVERVIEW");
                        }
                        else
                        {
                            combomachineover.getItems().clear();
                            combomachineover.setValue("");
                            logger.warn(Log4jConfiguration.currentTime()+"[WARN] : Empty list of machines in OVERVIEW!");
                        }
                
    }
    public void onTabOverview()
    {
        //logger.info(Log4jConfiguration.currentTime()+"[INFO]: Clear selection in OVERVIEW! \n");
        table_overview.getItems().clear();
        
        comboifover.getSelectionModel().clearSelection();
        comboifover.setValue("Select...");
        
        combolineover.getSelectionModel().clearSelection();
        combolineover.setValue("Select...");
        
        combomachineover.getSelectionModel().clearSelection();
        combomachineover.setValue("Select...");
        
        keywordsover.setText("");
        
    }
    public void getDataForDouble()
    {
        Overview over=table_overview.getSelectionModel().getSelectedItem();
        
        Data.id_pb=over.getId();
        
        String problem_file=ConnectionDatabase.getProblemFileName(over.getId());
        logger.info("PROBLEM FILE: "+problem_file);
        
        Data.problem_file_path=problem_file;
        
        logger.info(Log4jConfiguration.currentTime() + " [INFO]: The id of the problem selected from OVERVIEW: "+Data.id_pb);
        
        idPb=over.getId();
        
        String file=ConnectionDatabase.getFilePathfromProblem(over.getId());
        logger.info(Log4jConfiguration.currentTime()+" [INFO]: The file of the problem selected in OVERVIEW: "+file);
        
        Directory=file;
        filesIncidents=ConnectionDatabase.getIncidentsFiles(over.getId());
        if(filesIncidents.isEmpty()==false)
        {
            logger.info(Log4jConfiguration.currentTime()+" [INFO]: The files for all incidents of the problem in OVERVIEW: "+filesIncidents);
        }
        else
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty list of files for incidents of the problem selected in OVERVIEW!");
        }
        
        incidents_list=ConnectionDatabase.getIncidents(Data.id_pb);
        if(!incidents_list.isEmpty())
        {
            Data.incidents=incidents_list;
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: List of incidents overview were transfered on INCIDENTS OVERVIEW");
        }
        else
        {
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: There are no incidents for the problem selected!");
        }
       
    }
    
    
    public void onSelectProblem()
    {
        
        index=table_overview.getSelectionModel().getSelectedIndex();
        if(index <= -1)
        {
            logger.error(Log4jConfiguration.currentTime()+" [ERROR]: Out of index error in OVERVIEW table!");
            return;
            
        }
        Overview over=table_overview.getSelectionModel().getSelectedItem();
        
        Data.id_pb=over.getId();
        
        String problem_file=ConnectionDatabase.getProblemFileName(over.getId());
        logger.info("PROBLEM FILE: "+problem_file);
        
        Data.problem_file_path=problem_file;
        
        logger.info(Log4jConfiguration.currentTime() + " [INFO]: The id of the problem selected from OVERVIEW: "+Data.id_pb);
        
        idPb=over.getId();
        
        String file=ConnectionDatabase.getFilePathfromProblem(over.getId());
        logger.info(Log4jConfiguration.currentTime()+" [INFO]: The file of the problem selected in OVERVIEW: "+file);
        
        Directory=file;
        filesIncidents=ConnectionDatabase.getIncidentsFiles(over.getId());
        if(filesIncidents.isEmpty()==false)
        {
            logger.info(Log4jConfiguration.currentTime()+" [INFO]: The files for all incidents of the problem in OVERVIEW: "+filesIncidents);
        }
        else
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty list of files for incidents of the problem selected in OVERVIEW!");
        }
        
        incidents_list=ConnectionDatabase.getIncidents(Data.id_pb);
        if(!incidents_list.isEmpty())
        {
            Data.incidents=incidents_list;
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: List of incidents overview were transfered on INCIDENTS OVERVIEW");
        }
        else
        {
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: There are no incidents for the problem selected!");
        }
       
        
    }
    
    
     public String overmsg(Overview el)
    {
        String msg=" Line Name: "+el.getLine()+"\n"+
                "Machine Name: "+el.getMachine()+"\n"+
                "HMI Error: "+el.getHmi()+"\n"
                +"Problem description: "+el.getProblem_description()+"\n"+
                "Number of incidents: "+el.getNr_incidents()+"\n";
                
        return msg;
        
    }
     
    public void onSearchOverview()
    {
        
        
        boolean isif=comboifover.getSelectionModel().isEmpty();
        boolean isline=combolineover.getSelectionModel().isEmpty();
        boolean ismachine=combomachineover.getSelectionModel().isEmpty();
        if(isif==true || isline==true || ismachine==true)
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty IF/Line/Machine in OVERVIEW");
        }
        else
        {
            //System.out.println(id_IF_over +" "+ id_Line_over+" "+id_machine_over);
            dataProblemOverview=ConnectionDatabase.getOverviewFilter(id_IF_over,id_Line_over,id_machine_over,keywordsover.getText());
            //System.out.println(dataProblemOverview);
            if(dataProblemOverview.isEmpty()==false)
            {
                
                String over="[0]:" + overmsg(dataProblemOverview.get(0))+"\n";
                for(int i=1;i<dataProblemOverview.size();i++)
                {
                    over=over+"["+i+"]: "+overmsg(dataProblemOverview.get(i))+"\n";
                }
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF THE PROBLEMS filtered after IF,LINE,MACHINE,key words from HMI Error and problem's description: \n"+over);
                col_line_over.setCellValueFactory(new PropertyValueFactory<Overview,String>("line"));
                col_machine_over.setCellValueFactory(new PropertyValueFactory<Overview,String>("machine"));
                col_hmi_over.setCellValueFactory(new PropertyValueFactory<Overview,String>("hmi"));
                col_pb_over.setCellValueFactory(new PropertyValueFactory<Overview,String>("problem_description"));
                col_nr_incidents.setCellValueFactory(new PropertyValueFactory<Overview,String>("nr_incidents"));
                table_overview.setItems(dataProblemOverview);
                
            }
            else
            {
                table_overview.getItems().clear();
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Empty list of problems in OVERVIEW!");
            }
            
        }
        
        
        
    }
     
     public void newinfornewpb()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: NEW INCIDENT FOR NEW PROBLEM button was pressed in OVERVIEW");
        if(newincpbstage==null)
        {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/newincnewprb.fxml"));
                        newincpbstage=new Stage();


                        Scene scene = new Scene(root);

                        newincpbstage.setTitle("New incident for a new problem");
                        newincpbstage.setScene(scene);
                        newincpbstage.initStyle(StageStyle.UTILITY);
                        newincpbstage.setResizable(false);
                        newincpbstage.show();
                        

                        

                } catch (IOException ex) {
                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);
                }
        }
        else if(newincpbstage.isShowing())
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
                        dialog.setContentText("A panel 'New incident for a new problem' with unsaved changes is already open in the background! "
                                + "Close it if"
                                + " you want to ignore it and make a new incident.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
            
        }
        else
        {
             try {
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/newincnewprb.fxml"));
                        newincpbstage=new Stage();


                        Scene scene = new Scene(root);

                        newincpbstage.setTitle("New incident for a new problem");
                        newincpbstage.setScene(scene);
                        newincpbstage.initStyle(StageStyle.UTILITY);
                        newincpbstage.setResizable(false);
                        newincpbstage.show();

                        

                } catch (IOException ex) {
                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);
                }
        }
        
    }
    
     public void storeFile(String destination)
    {
        try {
            //nu exista si il stocam
            
            String SFTPHOOST="192.168.168.208";
            int SFTPORT=2222;
            String SFTPUSER="tester";
            String password="password";
            
            JSch jSch=new JSch();
            Session session=null;
            Channel channel =null;
            
            session=jSch.getSession(SFTPUSER, SFTPHOOST, SFTPORT);
            session.setPassword(password);
            //System.out.println("Session created...");
            
            java.util.Properties config=new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            
            if(session.isConnected()==true)
            {
                channel=session.openChannel("sftp");
                channel.connect();
                
                ChannelSftp sftpChannel = (ChannelSftp) channel;
                
                for(String f: filesIncidents)
                {
                    sftpChannel.get(f+".zip", destination);
                }
                
                
                channel.disconnect();
                if (session != null)
                {
                    
                    
                }
                
                sftpChannel.exit();
                
                
                
                session.disconnect();
            }   
        } catch (JSchException ex) {
            Logger.getLogger(MaintenanceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SftpException ex) {
            Logger.getLogger(MaintenanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void storeFromServer(String destination)
     {
         
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Connecting to SFTP SERVER for downloading the files of the problem selected in OVERVIEW");
         try {
             String SFTPHOOST="192.168.168.208";
             int SFTPORT=2222;
             String SFTPUSER="tester";
             String password="password";
             JSch jSch=new JSch();
             Session session=null;
             Channel channel =null;
             
             ChannelSftp channelSftp = null;
             
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: Creating session in OVERVIEW...\n");
             
             session=jSch.getSession(SFTPUSER, SFTPHOOST, SFTPORT);
             session.setPassword(password);
             
              logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session created in OVERVIEW \n");
              
             java.util.Properties config=new java.util.Properties();
             config.put("StrictHostKeyChecking", "no");
             session.setConfig(config);
             session.connect();
             if(session.isConnected()==true)
             {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session is connected in OVERVIEW \n");
                    
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: Open channel in OVERVIEW...\n");
                    
                    channel=session.openChannel("sftp");
                    channel.connect();
                    
                    if(channel.isConnected()==true)
                    {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Channel is connected in OVERVIEW \n");
                        ChannelSftp sftpChannel = (ChannelSftp) channel;

                        
                        
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Downloading files from server in "+destination+" in OVERVIEW...\n");
                        for(String f: filesIncidents)
                        {
                            sftpChannel.get(f+".zip", destination);
                        }
                        
                        
                        File downloadFile=new File(destination);
                        if(downloadFile.listFiles().length!=0)
                        {
                             logger.info(Log4jConfiguration.currentTime()+"[INFO]: Folder from "+destination+ " has all files from SFTP server  in OVERVIEW!\n");
                            //Creating a dialog
                            Dialog<String> dialog = new Dialog<String>();
                            //Setting the title
                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/ok.png"));
                                        dialog.setTitle("Success");
                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                            //Setting the content of the dialog
                            dialog.setContentText(destination+" was downloaded with success!");
                            //Adding buttons to the dialog pane
                            dialog.getDialogPane().getButtonTypes().add(type);
                            dialog.showAndWait();
                            table_overview.getSelectionModel().clearSelection();
                        }
                        else
                        {
                             logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The file from"+destination+" doesn't have the files from server in OVERVIEW!\n\n");
                        }
                       

                        //cand e gata
                        channel.disconnect();
                        if (session != null) {
                            session.disconnect();
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Session disconnected  in OVERVIEW\n");
                        }
                        sftpChannel.exit();
                    }
                    else
                    {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The channel connection has failed  in OVERVIEW\n\n");
                    }

                    
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The connection with session has failed in OVERVIEW!\n\n");
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
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: OVERVIEW ---- SftpException: "+ex);
             logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Downloading the files has failed in OVERVIEW!\n\n");
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
         } catch (SftpException ex) {
           logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: OVERVIEW ---- JSchException: "+ex);
       }
         
          
     }
     public File createFolderForDownload(String idPb)
     {
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: Creating the folder where the files of the problem will be stored! \n");
             String name="Problem_"+idPb;
             String url="C:\\Data";
             File directory=new File(url,name);
             
             
             if(directory.mkdir())
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+directory.getName()+" was created on C://Data in OVERVIEW\n");
                 
             
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while creating the folder "+directory.getName()+"on C://Data in OVERVIEW\n");
             }
             return directory;
    }
     
    public void downloadFile()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DOWNLOAD FILE button was pressed in OVERVIEW");
        boolean isSelected=table_overview.getSelectionModel().isSelected(index);
         if(table_overview.getItems().isEmpty()==false)
        {
             if(isSelected==true)
            {
                if(filesIncidents.isEmpty()==false)
                {
                    File folder=new File("C:\\Data\\Problem_"+idPb);
                    if(folder.exists()==true)
                    {
                        storeFromServer(folder.getPath());

                    }
                    else
                    {
                        File d=createFolderForDownload(String.valueOf(idPb));
                        storeFromServer(d.getPath());
                    }
                }
                else
                {
                    logger.warn(Log4jConfiguration.currentTime()+"[WARNING]: There are no files for the problem selected in OVERVIEW");
                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in OVERVIEW");
                    //Creating a dialog
                    Dialog<String> dialog = new Dialog<String>();
                    //Setting the title
                    Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    //Setting the content of the dialog
                    dialog.setContentText("There are no files for the problem selected!");
                    //Adding buttons to the dialog pane
                    dialog.getDialogPane().getButtonTypes().add(type);
                    dialog.showAndWait();
                    
                    table_overview.getSelectionModel().clearSelection();
                }
                
                
                
               
            }
             else
             {
                   logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in OVERVIEW");
                   //Creating a dialog
                   Dialog<String> dialog = new Dialog<String>();
                   //Setting the title
                   Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                   ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                   //Setting the content of the dialog
                   dialog.setContentText("No row was selected! Please select one.");
                   //Adding buttons to the dialog pane
                   dialog.getDialogPane().getButtonTypes().add(type);
                   dialog.showAndWait();
             }
            
        }
         else
         {
                   logger.info(Log4jConfiguration.currentTime()+"[WARN]: The table is empty in OVERVIEW");
                   //Creating a dialog
                   Dialog<String> dialog = new Dialog<String>();
                   //Setting the title
                   Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                   ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                   //Setting the content of the dialog
                   dialog.setContentText("The table is empty! Please choose a filter.");
                   //Adding buttons to the dialog pane
                   dialog.getDialogPane().getButtonTypes().add(type);
                   dialog.showAndWait();
         }
       
    }
    
    public void newinforexpb()
    {
          logger.info(Log4jConfiguration.currentTime()+"[INFO]: NEW INCIDENT FROM AN EXISTING PROBLEM button was pressed in OVERVIEW");
           boolean isSelected=table_overview.getSelectionModel().isSelected(index);
           
           if(table_overview.getItems().isEmpty()==false)
           {
                if(isSelected==true)
           {
                   if(newinexpbstage==null)
                   {
                                    try {
                                newinexpbstage=new Stage();
                                Parent root = FXMLLoader.load(getClass().getResource("/fxml/newincexprb.fxml"));
                                Scene scene = new Scene(root);

                                newinexpbstage.setTitle("New incident from an existing problem");
                                newinexpbstage.setScene(scene);
                                newinexpbstage.initStyle(StageStyle.UTILITY);
                                newinexpbstage.setResizable(false);
                                newinexpbstage.show();

                                newinexpbstage.setOnHidden(e->{
                                    onSearchOverview();
                                });
                                    
                                
                            } catch (IOException ex) {
                                logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);
                            }
                   }
                   else if(newinexpbstage.isShowing())
                   {
                        //newinexpbstage.toFront();
                        //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'New incident from existing problem' with unsaved changes is already open in the background! "
                                + "Close it if"
                                + " you want to ignore it and make a new incident.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                   }
                   else
                   {
                        try {
                                newinexpbstage=new Stage();
                                Parent root = FXMLLoader.load(getClass().getResource("/fxml/newincexprb.fxml"));
                                Scene scene = new Scene(root);

                                newinexpbstage.setTitle("New incident from an existing problem");
                                newinexpbstage.setScene(scene);
                                newinexpbstage.initStyle(StageStyle.UTILITY);
                                newinexpbstage.setResizable(false);
                                newinexpbstage.show();

                                newinexpbstage.setOnHidden(e->{
                                    onSearchOverview();
                                });
                                    
                                
                            } catch (IOException ex) {
                                logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);
                            }
                   }
                   
           }
           else
           {
               if(newinexpbstage!=null)
               {
                        if(newinexpbstage.isShowing()==true)
                     {
                         //Creating a dialog
                             Dialog<String> dialog = new Dialog<String>();
                             //Setting the title
                             Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                             ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                             //Setting the content of the dialog
                             dialog.setContentText("A panel 'New incident from existing problem' with unsaved changes is already open in the background! "
                                     + "Close it if"
                                     + " you want to ignore it and open a new panel for creating an incident.");
                             //Adding buttons to the dialog pane
                             dialog.getDialogPane().getButtonTypes().add(type);
                             dialog.showAndWait();
                     }
                        else
                    {


                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in OVERVIEW");
                       //Creating a dialog
                       Dialog<String> dialog = new Dialog<String>();
                       //Setting the title
                       Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                       //Setting the content of the dialog
                       dialog.setContentText("No row was selected! Please select one.");
                       //Adding buttons to the dialog pane
                       dialog.getDialogPane().getButtonTypes().add(type);
                       dialog.showAndWait();
                    }
               }
                
                else
                {
                    
                
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in OVERVIEW");
                   //Creating a dialog
                   Dialog<String> dialog = new Dialog<String>();
                   //Setting the title
                   Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                   ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                   //Setting the content of the dialog
                   dialog.setContentText("No row was selected! Please select one.");
                   //Adding buttons to the dialog pane
                   dialog.getDialogPane().getButtonTypes().add(type);
                   dialog.showAndWait();
                }
            }
       
           }
           else
           {
               logger.info(Log4jConfiguration.currentTime()+"[WARN]: The table is empty in OVERVIEW");
                   //Creating a dialog
                   Dialog<String> dialog = new Dialog<String>();
                   //Setting the title
                   Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                   ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                   //Setting the content of the dialog
                   dialog.setContentText("The table is empty! Please choose a filter.");
                   //Adding buttons to the dialog pane
                   dialog.getDialogPane().getButtonTypes().add(type);
                   dialog.showAndWait();
           }
          
          
       
        
    }
    
    public void incidentoverview()
    {
                
                
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: INCIDENTS OVERVIEW button was pressed in OVERVIEW");
                boolean isSelected=table_overview.getSelectionModel().isSelected(index);
                //System.out.println(isSelected);
                //System.out.println(index);
                if(table_overview.getItems().isEmpty()==false)
                {
                    
                if(isSelected==true)
                {
                   
                    if(incidentsOverview==null)
                    {
                                try {
                                incidentsOverview=new Stage();
                                Parent root = FXMLLoader.load(getClass().getResource("/fxml/incidentoverview.fxml"));
                                Scene scene = new Scene(root);

                                incidentsOverview.setTitle("Incidents overview");
                                incidentsOverview.initStyle(StageStyle.UTILITY);
                                incidentsOverview.setScene(scene);
                                incidentsOverview.setResizable(false);
                                incidentsOverview.show();
                                incidentsOverview.setOnHidden(e->{
                                   onSearchOverview();
                                   table_overview.getSelectionModel().clearSelection();

                                });
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                    }
                    else if(incidentsOverview.isShowing())
                    {
                        //incidentsOverview.toFront();
                         //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Incidents Overview' with unsaved actions on him is already open in the background. Please close it and select another problem from the table.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                        
                    }
                    else
                    {
                       try {
                                incidentsOverview=new Stage();
                                Parent root = FXMLLoader.load(getClass().getResource("/fxml/incidentoverview.fxml"));
                                Scene scene = new Scene(root);

                                incidentsOverview.setTitle("Incidents overview");
                                incidentsOverview.initStyle(StageStyle.UTILITY);
                                incidentsOverview.setScene(scene);
                                incidentsOverview.setResizable(false);
                                incidentsOverview.show();
                                incidentsOverview.setOnHidden(e->{
                                   onSearchOverview();

                                });
                            } catch (IOException ex) {
                                logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);
                                
                            }
                    }
                    
                        
                }
                else
                {
                    if(incidentsOverview!=null)
                    {
                         if(incidentsOverview.isShowing()==true)
                        {
                             Dialog<String> dialog = new Dialog<String>();
                            //Setting the title
                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                            //Setting the content of the dialog
                            dialog.setContentText("A panel 'Incidents Overview' with unsaved actions on him is already open in the background. Please close it and select another problem from the table.");
                            //Adding buttons to the dialog pane
                            dialog.getDialogPane().getButtonTypes().add(type);
                            dialog.showAndWait();

                        }
                        else  
                        {
                                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in OVERVIEW");
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: No row from OVERVIEW's table was selected");
                                //Creating a dialog
                                Dialog<String> dialog = new Dialog<String>();
                                //Setting the title
                                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                //Setting the content of the dialog
                                dialog.setContentText("No row was selected! Please select one.");
                                //Adding buttons to the dialog pane
                                dialog.getDialogPane().getButtonTypes().add(type);
                                dialog.showAndWait();
                         }
                    }
                    
                    else  
                    {
                        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in OVERVIEW");
                        logger.error(Log4jConfiguration.currentTime()+"[ERROR]: No row from OVERVIEW's table was selected");
                        //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("No row was selected! Please select one.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                    }
                     
                }
                
                }
                else
                {
                   
                    logger.info(Log4jConfiguration.currentTime()+"[WARN]: The table is empty in OVERVIEW");
                    //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("The table is empty! Please choose a filter.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                }

        
        
                
       
    }
     
    
     
     
     
     //--------------------------------------------------------------------------------ADMIN TAB--------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------PROBLEM LIST HISTORY------------------------------------------------------------------------------
    @FXML
    private Button searchbtnprbhistory;

    @FXML
    private ImageView searchiconprbhistory;
    
     @FXML
    private ComboBox<String> responsablecombo;
    
     @FXML
    private ComboBox<String> comboifpbhistory;
     
    @FXML
    private ComboBox<String> combomachinepbhistory;

    @FXML
    private ComboBox<String> combolinepbhistory;

    @FXML
    private TextField keywordspbhistory;

    

    

    @FXML
    private TableView<ProblemHistory> table_problem_history;

    @FXML
    private TableColumn<ProblemHistory, String> col_line_pbhistory;

    @FXML
    private TableColumn<ProblemHistory, String> col_mach_pbhistory;

    @FXML
    private TableColumn<ProblemHistory, String> col_user_pbhistory;

    @FXML
    private TableColumn<ProblemHistory, String> col_status_pbhistory;

    @FXML
    private TableColumn<ProblemHistory, String> col_hmi_pbhistory;

    @FXML
    private TableColumn<ProblemHistory, String> col_enginner_pbhistory;

    @FXML
    private TableColumn<ProblemHistory, String> col_pbdesc_pbhistory;

    @FXML
    private TableColumn<ProblemHistory, CheckBox> col_file_pbhistory;

    @FXML
    private TableColumn<ProblemHistory, Integer> col_nrincidents_pbhistory;
    
    @FXML
    private Button editbtnproblemhistory;
    
     @FXML
    private Button deletebtn_problem_history;
    
    int id_machine=-1;
    int id_IF=-1;
    int id_Line=-1;
    int id_escTeam=-1;
    
    Connection conn=null;
    
    ObservableList<String> itemscomboifpbhistory=FXCollections.observableArrayList();
    ObservableList<String> itemscombolinepbhistory=FXCollections.observableArrayList();
    ObservableList<String> itemscombomachinepbhistory=FXCollections.observableArrayList();
    ObservableList<String> itemsresponsable=FXCollections.observableArrayList();
    ObservableList<ProblemHistory> dataProblemHistory;
    
    ObservableList<ProblemHistory> dataproblemfiltred;
    ObservableList<ProblemHistory> dataProblemnonull;
   
    public void onResponsable()
    {
        if(responsablecombo.getSelectionModel().isEmpty()==false && !responsablecombo.getValue().toString().equals("Select..."))
        {
                      String valueesc=responsablecombo.getValue().toString();
                      int index1=valueesc.indexOf("(");
                      int index2=valueesc.indexOf(")"); 
                      String user=valueesc.substring(index1+1, index2);
                      id_escTeam=ConnectionDatabase.getEscId(user);
                      logger.info(Log4jConfiguration.currentTime()+"[INFO]: The engineer selected in PROBLEM LIST HISTORY is: "+responsablecombo.getValue().toString()+"\n");
        }
        else if(responsablecombo.getSelectionModel().isEmpty()==false && responsablecombo.getValue().toString().equals("Select..."))
        {
            id_escTeam=-1;
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: 'Select...' option was choosed for responsable in PROBLEM LIST HISTORY");
        }
    }
   
    
    public void onIfPbHistory()
    {
               
               
               boolean isMyComboBoxEmpty = comboifpbhistory.getSelectionModel().isEmpty();
               if(isMyComboBoxEmpty == false && !comboifpbhistory.getValue().toString().equals("Select..."))
               {
                   
                   int ifid=ConnectionDatabase.getIfId(comboifpbhistory.getValue().toString());
                   id_IF=ifid;
                   if(ifid!=0)
                   {
                        combolinepbhistory.setValue("Select...");
                        itemscombolinepbhistory=ConnectionDatabase.getLinesfromIf(ifid);
                        if(!itemscombolinepbhistory.isEmpty())
                        {
                             //combolineover.setValue(itemscombolineover.get(0));
                             
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
                                Collections.sort(itemscombolinepbhistory,c);
                                itemscombolinepbhistory.add(0, "Select...");
                                combolinepbhistory.setItems(itemscombolinepbhistory);
                                String lineinit="[1] : "+itemscombolinepbhistory.get(1);
                                String msgline="OUTPUT LIST OF Lines from the "+comboifpbhistory.getValue().toString()+ " \n"+lineinit+" \n";
                                for(int i=2;i<itemscombolinepbhistory.size();i++)
                                {
                                    msgline=msgline+ "[" + i +"]" + " : " + itemscombolinepbhistory.get(i) + "\n";

                                }
                                logger.info(Log4jConfiguration.currentTime()+"[INFO] : "+msgline);
                             
                        }

                         
                        else{
                            
                            combolinepbhistory.getItems().clear();
                            logger.warn(Log4jConfiguration.currentTime()+"[WARN] : Empty list of Lines in PROBLEM LIST HISTORY!");
                            
                        }
                   }
                 
               }
               //caz in care la IF e Select...
               else if(isMyComboBoxEmpty == false && comboifpbhistory.getValue().toString().equals("Select..."))
               {   
                        combolinepbhistory.setValue("Select...");
                        combomachinepbhistory.setValue("Select...");
                        itemscombolinepbhistory=ConnectionDatabase.getLinesName();
                        id_IF=-1;
                        if(!itemscombolinepbhistory.isEmpty())
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
                             Collections.sort(itemscombolinepbhistory,c);
                             itemscombolinepbhistory.add(0, "Select...");
                             combolinepbhistory.setItems(itemscombolinepbhistory);
                             
                            String lineinit="[1] : "+itemscomboifpbhistory.get(1);
                            String msgline="OUTPUT LIST OF Lines from PROBLEM LIST HISTORY when 'Select...' option from IF was selected: "+" \n"+lineinit+" \n";
                            for(int i=2;i<itemscombolinepbhistory.size();i++)
                            {
                                msgline=msgline+ "[" + i +"]" + " : " + itemscombolinepbhistory.get(i) + "\n";

                            }
                            logger.info(Log4jConfiguration.currentTime()+"[INFO] : "+msgline);
                        }
                        else
                        {
                            combolinepbhistory.getItems().clear();
                            combolinepbhistory.setValue("");
                            logger.warn(Log4jConfiguration.currentTime()+"[WARN] : Empty list of Lines in PROBLEM LIST HISTORY!");
                        }
                        
                itemscombomachinepbhistory=ConnectionDatabase.getMachinesDesc();
                if(!itemscombomachinepbhistory.isEmpty())
                        {
                            Pattern p = Pattern.compile("\\d+$");
                            Comparator<String> c = new Comparator<String>() {
                                @Override
                                public int compare(String object1, String object2) {
                                    Matcher m = p.matcher(object1);
                                    Integer number1 = null;
                                    if (!m.find()) 
                                    {
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
                            Collections.sort(itemscombomachinepbhistory,c);
                            itemscombomachinepbhistory.add(0, "Select...");
                            combomachinepbhistory.setItems(itemscombomachinepbhistory);
                            
                            String machineinit="[1] : "+itemscombomachinepbhistory.get(1);
                            String msgmachine="OUTPUT LIST OF Machines from PROBLEM LIST HISTORY when 'Select...' option from IF was selected: "+" \n"+machineinit+" \n";
                            for(int i=2;i<itemscombomachinepbhistory.size();i++)
                            {
                                msgmachine=msgmachine+ "[" + i +"]" + " : " + itemscombomachinepbhistory.get(i) + "\n";

                            }
                            logger.info(Log4jConfiguration.currentTime()+" [INFO]: "+msgmachine);
                        }
                        else
                        {
                            combomachinepbhistory.getItems().clear();
                            combomachinepbhistory.setValue("");
                            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty list of machines in PROBLEM LIST HISTORY");
                        }
               }
               
               
    }
    
    
    public void onLineHistory()
    {
        boolean isMyLine=combolinepbhistory.getSelectionModel().isEmpty();
        if(isMyLine == false && !combolinepbhistory.getValue().toString().equals("Select..."))
        {
            int idline=ConnectionDatabase.getLineId(combolinepbhistory.getValue().toString());
            id_Line=idline;
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The line selected in PROBLEM LIST HISTORY: "+combolinepbhistory.getValue());
            if(idline!=-1)
            {
                itemscombomachinepbhistory=ConnectionDatabase.getMachineCombo(idline);
                combomachinepbhistory.setValue("Select...");
                if(!itemscombomachinepbhistory.isEmpty())
                {
                    //combomachinepbhistory.setValue(itemscombomachinepbhistory.get(0));
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
                    Collections.sort(itemscombomachinepbhistory,c);
                    itemscombomachinepbhistory.add(0,"Select...");
                    combomachinepbhistory.setItems(itemscombomachinepbhistory);
                    
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]:OUTPUT LIST OF MACHINES from the selected line in PROBLEM LIST HISTORY: \n");
                    for(int i=1;i< itemscombomachinepbhistory.size();i++)
                    {
                        logger.info("["+i+"]: "+itemscombomachinepbhistory.get(i)+"\n");
                    }
                    
                }
                else
                {
                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of machines from the selected line is empty in PROBLEM LIST HISTORY");
                    combomachinepbhistory.getItems().clear();
                }
            }
            
        }
        //caz in care e Select...
        else if(isMyLine == false && combolinepbhistory.getValue().toString().equals("Select..."))
        {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The 'Select...' option was choosed from line in PROBLEM LIST HISTORY");
                    id_Line=-1;
                    combomachinepbhistory.setValue("Select...");
                    itemscombomachinepbhistory=ConnectionDatabase.getMachinesDesc();
                    if(!itemscombomachinepbhistory.isEmpty())
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
                        Collections.sort(itemscombomachinepbhistory,c);
                        itemscombomachinepbhistory.add(0,"Select...");
                        combomachinepbhistory.setItems(itemscombomachinepbhistory);
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The list of machines have been loaded with success in PROBLEM LIST HISTORY");
                    }
                    else
                    {
                        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of machines is empty in PROBLEM LIST HISTORY");
                    }
                
        }
    }
    public void onMachineHistory()
    {
                    boolean isMyComboBox=combomachinepbhistory.getSelectionModel().isEmpty();
                    if(isMyComboBox==false && !combomachinepbhistory.getValue().toString().equals(" ") && !combomachinepbhistory.getValue().toString().equals("Select..."))
                    {
                        String machine=combomachinepbhistory.getValue();
                        int index1=machine.indexOf("(");
                        int index2=machine.indexOf(")"); 
                        String invname=machine.substring(index1+1, index2);
                        String mach=machine.substring(0,index1);
                        id_machine=ConnectionDatabase.getMachineId(mach, invname);
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The machine selected in PROBLEM LIST HISTORY is: "+ combomachinepbhistory.getValue().toString()+"\n");
                  
                    }
                    else if(isMyComboBox==false &&  combomachinepbhistory.getValue().toString().equals("Select..."))
                    {
                        id_machine=-1;
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The 'Select...' option was choosed from machine in PROBLEM LIST HISTORY");
                    }
    }
    
    public void onProblemHistory()
    {
                //if
                if(comboifpbhistory.getSelectionModel().isEmpty()==true)
                {
                       comboifpbhistory.setValue("Select...");
                       itemscomboifpbhistory=ConnectionDatabase.getIFCombo();
                       if(!itemscomboifpbhistory.isEmpty())
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
                            
                           Collections.sort(itemscomboifpbhistory,c);
                           itemscomboifpbhistory.add(0, "Select...");
                           comboifpbhistory.setItems(itemscomboifpbhistory);
                           logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF IFS FROM PROBLEM LIST HISTORY TAB: \n");
                           for(int i=1;i<itemscomboifpbhistory.size();i++)
                           {
                               logger.info("["+i+"]: "+itemscomboifpbhistory.get(i)+"\n");
                           }

                       }
                       else
                       {
                           logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of IFs for combobox is empty in PROBLEM LIST HISTORY");
                       }

                }
                if(combolinepbhistory.getSelectionModel().isEmpty()==true)
                {
                    combolinepbhistory.setValue("Select...");
                    itemscombolinepbhistory=ConnectionDatabase.getLinesName();
                    if(!itemscombolinepbhistory.isEmpty())
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
                        Collections.sort(itemscombolinepbhistory,c);
                        itemscombolinepbhistory.add(0,"Select...");
                        combolinepbhistory.setItems(itemscombolinepbhistory);
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The lines have been loaded with success in PROBLEM LIST HISTORY");
                        
                    }
                    else
                    {
                        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of Lines for combobox is empty in PROBLEM LIST HISTORY");
                    }
                }
                
                
                if(combomachinepbhistory.getSelectionModel().isEmpty()==true)
                {
                    combomachinepbhistory.setValue("Select...");
                    itemscombomachinepbhistory=ConnectionDatabase.getMachinesDesc();
                    if(!itemscombomachinepbhistory.isEmpty())
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
                        Collections.sort(itemscombomachinepbhistory,c);
                        itemscombomachinepbhistory.add(0,"Select...");
                        combomachinepbhistory.setItems(itemscombomachinepbhistory);
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The machines have been loaded with success in PROBLEM LIST HISTORY");
                    }
                     else
                    {
                        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of machines for combobox is empty in PROBLEM LIST HISTORY");
                    }
                }
               
                
                
                itemsresponsable=ConnectionDatabase.getEscName();
                responsablecombo.setValue("Select...");
                if(!itemsresponsable.isEmpty())
                {
                      
                      Collections.sort(itemsresponsable);
                      itemsresponsable.add(0,"Select...");
                      responsablecombo.setItems(itemsresponsable);        
                      logger.info(Log4jConfiguration.currentTime()+"[INFO]: The engineers(responsables) have been loaded with success in PROBLEM LIST HISTORY");
                }
                else
                {
                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of responsables for combobox is empty in PROBLEM LIST HISTORY");
                }
                //setare data
                 DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Calendar obj = Calendar.getInstance();
                String date = formatter.format(obj.getTime());
                LocalTime timeObj = LocalTime.now();
                String currentTime = timeObj.toString();
                String time=currentTime.substring(0,5);
                String finaldate=date;
                
             
             
             
    }
    public void onPbHistoryTab()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Clearing selection in PROBLEM LIST HISTORY");
        table_problem_history.getItems().clear();
        
        comboifpbhistory.getSelectionModel().clearSelection();
        comboifpbhistory.setValue("Select...");
        
        combolinepbhistory.getSelectionModel().clearSelection();
        combolinepbhistory.setValue("Select...");
        
        combomachinepbhistory.getSelectionModel().clearSelection();
        combomachinepbhistory.setValue("Select...");
        
        responsablecombo.getSelectionModel().clearSelection();
        responsablecombo.setValue("Select...");
        
        keywordspbhistory.setText("");
        
    }
    public int convertBooleantoInt(boolean b)
    {
        int i = b ? 0 : 1;
        return i;
    }
    
    public void onSearchPbHistory() throws ParseException
    {
        
        
        boolean isif=comboifpbhistory.getSelectionModel().isEmpty();
        boolean isline=combolinepbhistory.getSelectionModel().isEmpty();
        boolean ismachine=combomachinepbhistory.getSelectionModel().isEmpty();
        boolean isresponsable=responsablecombo.getSelectionModel().isEmpty();
        
        
        
       if(isif == true || isline==true || ismachine==true || isresponsable==true)
       {
           logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty IF/Line/Machine in PROBLEM LIST HISTORY");
       }
       else if(isif == false && isline==false && ismachine==false && isresponsable==false)
       {
            dataProblemHistory=ConnectionDatabase.getFilteredProblems(id_IF, id_Line, id_machine, keywordspbhistory.getText(), id_escTeam);
            if(dataProblemHistory.isEmpty()==false)
            {
                col_line_pbhistory.setCellValueFactory(new PropertyValueFactory<ProblemHistory,String>("line"));
                col_mach_pbhistory.setCellValueFactory(new PropertyValueFactory<ProblemHistory,String>("machine"));
                col_user_pbhistory.setCellValueFactory(new PropertyValueFactory<ProblemHistory,String>("user"));
                col_status_pbhistory.setCellValueFactory(new PropertyValueFactory<ProblemHistory,String>("status"));
                col_hmi_pbhistory.setCellValueFactory(new PropertyValueFactory<ProblemHistory,String>("hmi"));
                col_enginner_pbhistory.setCellValueFactory(new PropertyValueFactory<ProblemHistory,String>("engineer"));
                col_pbdesc_pbhistory.setCellValueFactory(new PropertyValueFactory<ProblemHistory,String>("problem_description"));
                col_file_pbhistory.setCellValueFactory(new PropertyValueFactory<ProblemHistory,CheckBox>("file"));
                col_nrincidents_pbhistory.setCellValueFactory(new PropertyValueFactory<ProblemHistory,Integer>("nr_incidents"));
                table_problem_history.setItems(dataProblemHistory);
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data has been loaded in table in PROBLEM LIST HISTORY");
            }
            else
            {
                table_problem_history.getItems().clear();
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty list of problems in PROBLEM LIST HISTORY");
            }
            
       }
                                                         
        
            
    }
    
    public void onSelectProblemHistory()
    {
        index=table_problem_history.getSelectionModel().getSelectedIndex();
        if(index <= -1)
        {
            return;
        }
        ProblemHistory problem_history=table_problem_history.getSelectionModel().getSelectedItem();
        ParticularDetailsProblem pb=ConnectionDatabase.getParticularDetailsProblem(problem_history.getId());
        if(pb!=null)
        {
            Data.id_pb_history=problem_history.getId();
            Data.ifid__value_problem_history=pb.getIf_id();
            Data.lineid_value_problem_history=pb.getLine_id();
            Data.machineid_value_problem_history=pb.getMachine_id();

            Data.user_problem_history=problem_history.getUser();
            Data.status_problem_history=problem_history.getStatus();
            Data.engineer_problem_history=problem_history.getEngineer();

            Data.engineer_user_problem_history=pb.getEngineer_user();

            Data.problem_description_problem_history=problem_history.getProblem_description();
            Data.hmi_problem_history=problem_history.getHmi();
            Data.file_path_problem_history=problem_history.getFile_path();
            
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Details about the problem selected in PROBLEM LIST HISTORY: \n");
            logger.info("ID: "+Data.id_pb_history+"\n"+
                        "IF ID: "+Data.ifid__value_problem_history+"\n"+
                        "Line's ID: "+Data.lineid_value_problem_history+"\n"+
                        "Machine's ID: "+Data.machineid_value_problem_history+"\n"+
                        "Creator User: "+Data.user_problem_history+"\n"+
                        "Status: "+Data.status_problem_history+"\n"+
                        "Engineer's User: "+Data.engineer_user_problem_history+"\n"+
                        "Problem description: "+Data.problem_description_problem_history+"\n"+
                        "HMI Error: "+Data.hmi_problem_history+"\n"+
                        "File: "+Data.file_path_problem_history+"\n"
                        );
            
        }
        else
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The problem list history object is null in PROBLEM LIST HISTORY \n");
        }
       
        
        
       
        
        
        
    }
    
    
    public TabPane tabAdmin;
    
    
    public void rightClick()
    {
        
        table_problem_history.setRowFactory(new Callback<TableView<ProblemHistory>,TableRow<ProblemHistory>>(){
            
            @Override
            public TableRow<ProblemHistory> call(TableView<ProblemHistory> param) {
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Right click from mouse was pressed in PROBLEM LIST HISTORY");
                final TableRow<ProblemHistory> row=new TableRow<>();
                
                //creare popupmenu
                final ContextMenu contextMenu=new ContextMenu();
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Showing incident history of the problem selected in PROBLEM LIST HISTORY");
                final MenuItem menuitem=new MenuItem("Show incidents history");
                
                //actiune pe popupmenuitem
                menuitem.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        //System.out.println(tabAdmin);
                        if(tabAdmin!=null)
                        {
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Going to INCIDENT LIST HISTORY TAB");
                            tabAdmin.getSelectionModel().selectNext();
                            
                            
                            int idpb=Data.id_pb_history;
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The problem selected from PROBLEM LIST HISTORY has ID: \n"+idpb);
                            incident_history=ConnectionDatabase.getIncidentHistory(idpb);
                            if(incident_history.isEmpty()==false)
                            {
                                //System.out.println(incident_history);
                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Loading incidents corresponding to the problem...");
                                

                                col_ih_start_date.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("start_date"));
                                col_ih_enddate.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("end_date"));
                                col_ih_status.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("status"));
                                col_ih_sap.setCellValueFactory(new PropertyValueFactory<IncidentHistory,Integer>("sap"));        
                                col_ih_solcreator.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("sol_creator"));
                                col_ih_solescal.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("sol_escal"));
                                col_ih_user.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("user"));
                                col_ih_engineer.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("engineer"));
                                col_ih_spare.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("part_number_spare_parts"));
                                col_ih_sh_sol.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("short_sol"));
                                col_ih_file.setCellValueFactory(new PropertyValueFactory<IncidentHistory,CheckBox>("file"));
                                table_incident_history.setItems(incident_history);
                                
                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data has been loaded in table in INCIDENT LIST HISTORY \n");
                                
                            }
                            else
                            {
                                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of incidents is empty in PROBLEM LIST HISTORY \n");
                            }
                            
                        }
                        else
                        {
                            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The ADMIN tab is null! \n");
                        }
                       
                        
                        
                        
                        
                    }
                    
                });
                
                //afisare popup
                contextMenu.getItems().add(menuitem); 
                
                row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu)null).otherwise(contextMenu));
                return row;
               
            }
            
        });
        
        
    }
    public void editProblemHistory()
    {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT PROBLEM button in PROBLEM LIST HISTORY TAB");
            boolean isSelected= table_problem_history.getSelectionModel().isSelected(index); 
            if(table_problem_history.getItems().isEmpty()==false)
            {
                if(isSelected == true)
              {
                if(editPbHistory==null)
                {
                            try {
                            editPbHistory=new Stage();
                            Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editproblemhistory.fxml"));
                            Scene scene = new Scene(root);

                            editPbHistory.setTitle("Edit Problem List History");
                            editPbHistory.initStyle(StageStyle.UTILITY);
                            editPbHistory.setScene(scene);
                            editPbHistory.setResizable(false);
                            editPbHistory.show();

                            

                             editPbHistory.setOnHidden(e->{
                                try {
                                    
                                    onSearchPbHistory();


                                } catch (ParseException ex) {
                                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- ParseException: "+ex);
                                }
                            });


                        } catch (IOException ex) {
                            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);
                        }

                }
                else if(editPbHistory.isShowing())
                {
                    //editPbHistory.toFront();
                    //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        dialog.setTitle("Warning");
                        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image("images/warn.png"));
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Edit Problem List History' with unsaved changes is already open in the background! "
                                + "Close it if"
                                + " you want to ignore it and open a new one for another problem.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                }
                else
                {
                     try {
                            editPbHistory=new Stage();
                            Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editproblemhistory.fxml"));
                            Scene scene = new Scene(root);

                            editPbHistory.setTitle("Edit Problem List History");
                            editPbHistory.initStyle(StageStyle.UTILITY);
                            editPbHistory.setScene(scene);
                            editPbHistory.setResizable(false);
                            editPbHistory.show();

                            

                             editPbHistory.setOnHidden(e->{
                                try {
                                    
                                    onSearchPbHistory();


                                } catch (ParseException ex) {
                                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- ParseException: "+ex);
                                }
                            });


                        } catch (IOException ex) {
                            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);
                        }
                }

            }
            else 
            {
                if(editPbHistory!=null)
                {
                    if(editPbHistory.isShowing()==true)
                    {
                         //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        dialog.setTitle("Warning");
                        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image("images/warn.png"));
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Edit Problem History' with unsaved changes is already open in the background! "
                                + "Close it if"
                                + " you want to ignore it and open a new one for another problem.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                    }
                    else
                    {
                       logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in PROBLEM LIST HISTORY TAB");
                        //Creating a dialog
                       Dialog<String> dialog = new Dialog<String>();
                       //Setting the title
                       dialog.setTitle("Warning");
                        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image("images/warn.png"));
                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                       //Setting the content of the dialog
                       dialog.setContentText("No row was selected! Please select one.");
                       //Adding buttons to the dialog pane
                       dialog.getDialogPane().getButtonTypes().add(type);
                       dialog.showAndWait();
                
                    }
                }
                else
                {
                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in PROBLEM LIST HISTORY TAB");
                    //Creating a dialog
                   Dialog<String> dialog = new Dialog<String>();
                   //Setting the title
                   dialog.setTitle("Warning");
                        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image("images/warn.png"));
                   ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                   //Setting the content of the dialog
                   dialog.setContentText("No row was selected! Please select one.");
                   //Adding buttons to the dialog pane
                   dialog.getDialogPane().getButtonTypes().add(type);
                   dialog.showAndWait();
                
                }
                
            }
            }
            else
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in PROBLEM LIST HISTORY TAB");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                dialog.setTitle("Warning");
                        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image("images/warn.png"));
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("The table is empty! Please fill it by pressing the 'Search' button.");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
            }
        
        
    }
    public void deleteProblemHistory()
    {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE PROBLEM button was pressed in PROBLEM LIST HISTORY TAB");
            boolean isSelected= table_problem_history.getSelectionModel().isSelected(index);
            if(table_problem_history.getItems().isEmpty()==false)
            {
            if(isSelected == true)
            {
                
                if(deletePbHistory==null)
                {
                            try {
                            deletePbHistory=new Stage();
                            Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deleteproblemhistory.fxml"));
                            Scene scene = new Scene(root);

                            deletePbHistory.setTitle("Delete Problem List History");
                            deletePbHistory.initStyle(StageStyle.UTILITY);
                            deletePbHistory.setScene(scene);
                            deletePbHistory.setOnHidden(e->{
                                try {
                                    onSearchPbHistory();
                                } catch (ParseException ex) {
                                  logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- ParseException: "+ex);
                                }
                            });
                            deletePbHistory.setResizable(false);
                            deletePbHistory.show();


                        } catch (IOException ex) {
                            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);
                        }
       
                }
                else if(deletePbHistory.isShowing())
                {
                    //deletePbHistory.toFront();
                    //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Delete Problem List History' with unsaved changes is already open in the background! "
                                + "Close it if"
                                + " you want to ignore it and open a new one for another problem.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                    
                }
                else
                {
                    try {
                            deletePbHistory=new Stage();
                            Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deleteproblemhistory.fxml"));
                            Scene scene = new Scene(root);

                            deletePbHistory.setTitle("Delete Problem List History");
                            deletePbHistory.initStyle(StageStyle.UTILITY);
                            deletePbHistory.setScene(scene);
                            deletePbHistory.setOnHidden(e->{
                                try {
                                    onSearchPbHistory();
                                } catch (ParseException ex) {
                                   logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- ParseException: "+ex);
                                }
                            });
                            deletePbHistory.setResizable(false);
                            deletePbHistory.show();


                        } catch (IOException ex) {
                            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);
                        }
                }
                
            }
            else 
            {
                if(deletePbHistory!=null)
                {
                    if(deletePbHistory.isShowing()==true)
                    {
                         //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Delete Problem List History' with unsaved changes is already open in the background! "
                                + "Close it if"
                                + " you want to ignore it and open a new one for another problem.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                    }
                    else
                    {
                                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in PROBLEM LIST HISTORY TAB");
                         //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("No row was selected! Please select one.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                    }
                   
                }
                else
                {
                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in PROBLEM LIST HISTORY TAB");
                 //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("No row was selected! Please select one.");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
                }
                
                
                
                
            }
           }
            else
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The table is empty in PROBLEM LIST HISTORY TAB");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("The table is empty! Please fill it by pressing the 'Search' button.");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
            }
        
        
    }
    
    
    //---------------------------------------------------------------------  INCIDENT LIST HISTORY-----------------------------------------------------------------------------------
    @FXML
    private Tab tabincidentshistory;
    
    @FXML
    private Button editbtn_incident;
    
    @FXML
    private Button deletebtn_incident;
    
    @FXML
    private TableView<IncidentHistory> table_incident_history;

    @FXML
    private TableColumn<IncidentHistory, String> col_ih_start_date;

    @FXML
    private TableColumn<IncidentHistory, String> col_ih_enddate;

    @FXML
    private TableColumn<IncidentHistory, String> col_ih_status;

    @FXML
    private TableColumn<IncidentHistory, Integer> col_ih_sap;

    @FXML
    private TableColumn<IncidentHistory, String> col_ih_solcreator;

    @FXML
    private TableColumn<IncidentHistory, String> col_ih_solescal;

    @FXML
    private TableColumn<IncidentHistory, String> col_ih_user;

    @FXML
    private TableColumn<IncidentHistory, String> col_ih_engineer;

    @FXML
    private TableColumn<IncidentHistory,String> col_ih_spare;

    @FXML
    private TableColumn<IncidentHistory, String> col_ih_sh_sol;

    @FXML
    private TableColumn<IncidentHistory, CheckBox> col_ih_file;
    int index_incidents;
    
    ObservableList<IncidentHistory> incident_history=FXCollections.observableArrayList();
    
    public void onIncidentHistory()
    {
        table_incident_history.getItems().clear();
    }
    public void showIncidents()
    {                   
                        int idpb=Data.id_pb_history;
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Loading incidents for problem with ID "+idpb+ " in table in INCIDENT LIST HISTORY TAB \n");
                        incident_history=ConnectionDatabase.getIncidentHistory(idpb);
                        
                        if(incident_history.isEmpty()==false)
                        {
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data has been loaded in table in INCIDENT LIST HISTORY TAB \n");
                            col_ih_start_date.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("start_date"));
                            col_ih_enddate.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("end_date"));
                            col_ih_status.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("status"));
                            col_ih_sap.setCellValueFactory(new PropertyValueFactory<IncidentHistory,Integer>("sap"));        
                            col_ih_solcreator.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("sol_creator"));
                            col_ih_solescal.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("sol_escal"));
                            col_ih_user.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("user"));
                            col_ih_engineer.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("engineer"));
                            col_ih_spare.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("part_number_spare_parts"));
                            col_ih_sh_sol.setCellValueFactory(new PropertyValueFactory<IncidentHistory,String>("short_sol"));
                            col_ih_file.setCellValueFactory(new PropertyValueFactory<IncidentHistory,CheckBox>("file"));
                            table_incident_history.setItems(incident_history);
                        }
                        else
                        {
                            table_incident_history.getItems().clear();
                            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The incident list history is empty for problem with ID "+idpb+"\n");
                        }
                       
                        
    }
    public void onIncidentHistoryTable()
    {
        index_incidents=table_incident_history.getSelectionModel().getSelectedIndex();
        if(index <= -1)
        {
            return;
        }
        IncidentHistory incident_history=table_incident_history.getSelectionModel().getSelectedItem();
        if(incident_history!=null)
        {
            Data.id_incident_history=incident_history.getId();
            Data.incident_Creator_username=incident_history.getUser();
            Data.incident_File_Path=incident_history.getFile_path();
            Data.incident_SAP_Order_Nr=incident_history.getSap();
            Data.incident_Solution_Creator=incident_history.getSol_creator();
            Data.incident_Solution_Escalation=incident_history.getSol_escal();
            Data.incident_Status=incident_history.getStatus();
            Data.incident_history_engineer=incident_history.getEngineer();
            Data.incident_history_spare_parts=incident_history.getPart_number_spare_parts();
            Data.incident_short_solution=incident_history.getShort_sol();
            Data.incident_Start_Date_hour= incident_history.getStart_date();
            Data.incident_End_Date_hour=incident_history.getEnd_date();
            
            String engineer_user=incident_history.getEngineer();
            String engineer_name=ConnectionDatabase.getEscNameByUser(engineer_user);
            if(engineer_user!=null && engineer_name!=null)
            {
                Data.incident_history_engineer=engineer_name+ "("+engineer_user+")";
            }
            else
            {
                Data.incident_history_engineer="";
            }
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Details about the incident selected in INCIDENT LIST HISTORY TAB: \n");
            logger.info("ID: "+Data.id_incident_history+"\n"+
                        "Creator username: "+Data.incident_Creator_username+"\n"+
                        "File: "+Data.incident_File_Path+"\n"+
                       "SAP Order: "+Data.incident_SAP_Order_Nr+"\n"+
                      "Solution creator: "+Data.incident_Solution_Creator+"\n"+
                      "Solution escalation: "+Data.incident_Solution_Escalation+"\n"+
                      "Engineer escalated: "+Data.incident_history_engineer+"\n"+
                      "Status: "+Data.incident_Status+"\n"+
                     "Spare parts: "+Data.incident_history_spare_parts+"\n"+
                     "Short solution: "+Data.incident_short_solution+"\n"+
                     "Start date: "+Data.incident_Start_Date_hour+"\n"+
                     "End date: "+Data.incident_End_Date_hour+"\n");
            
        }
        else
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The incident selected is empty in INCIDENT LIST HISTORY TAB! \n");
        }
        
        
        
        
        
    }
    
    public void EditIncidentHistory()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT INCIDENT button was pressed in INCIDENT LIST HISTORY TAB \n");
        boolean isSelected= table_incident_history.getSelectionModel().isSelected(index_incidents);
        if(table_incident_history.getItems().isEmpty()==false)
        {
            if(isSelected == true)
            {
                if(editIncidentHistory==null)
                {
                                try {
                                editIncidentHistory=new Stage();
                                Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editincidenthistory.fxml"));
                                Scene scene = new Scene(root);

                                editIncidentHistory.setTitle("Edit Incident List History");
                                editIncidentHistory.initStyle(StageStyle.UTILITY);
                                editIncidentHistory.setScene(scene);
                                editIncidentHistory.setOnHidden(e->{
                                    showIncidents();
                                });
                                editIncidentHistory.setResizable(false);
                                editIncidentHistory.show();


                            } catch (IOException ex) {
                                logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);
                            }

                  }
                else if(editIncidentHistory.isShowing())
                {
                    //editIncidentHistory.toFront();
                    //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/warn.png"));
                                       dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Edit Incident List History' with unsaved changes is already open in the background! "
                                + "Close it if"
                                + " you want to ignore it and open a new one for another incident.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                }
                else
                {
                     try {
                                editIncidentHistory=new Stage();
                                Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editincidenthistory.fxml"));
                                Scene scene = new Scene(root);

                                editIncidentHistory.setTitle("Edit Incident List History");
                                editIncidentHistory.initStyle(StageStyle.UTILITY);
                                editIncidentHistory.setScene(scene);
                                editIncidentHistory.setOnHidden(e->{
                                    showIncidents();
                                });
                                editIncidentHistory.setResizable(false);
                                editIncidentHistory.show();


                            } catch (IOException ex) {
                                logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);
                            }
                }
                        
             }
            else if(isSelected == false)
            {
                 if(editIncidentHistory!=null)
                 {
                     if(editIncidentHistory.isShowing()==true)
                     {
                         //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/warn.png"));
                                       dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Edit Incident List History' with unsaved changes is already open in the background! "
                                + "Close it if"
                                + " you want to ignore it and open a new one for another incident.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                     }
                     else
                     {
                         logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in INCIDENT LIST HISTORY STRUCTURE TAB");
                             //Creating a dialog
                            Dialog<String> dialog = new Dialog<String>();
                            //Setting the title
                            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/warn.png"));
                                       dialog.setTitle("Warning");
                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                            //Setting the content of the dialog
                            dialog.setContentText("No row was selected! Please select one.");
                            //Adding buttons to the dialog pane
                            dialog.getDialogPane().getButtonTypes().add(type);
                            dialog.showAndWait();
                     }
                     
                 }
                 else
                 {
                     logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in INCIDENT LIST HISTORY STRUCTURE TAB");
                             //Creating a dialog
                            Dialog<String> dialog = new Dialog<String>();
                            //Setting the title
                            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/warn.png"));
                                       dialog.setTitle("Warning");
                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                            //Setting the content of the dialog
                            dialog.setContentText("No row was selected! Please select one.");
                            //Adding buttons to the dialog pane
                            dialog.getDialogPane().getButtonTypes().add(type);
                            dialog.showAndWait();
                 }
                            

            }
                
        }
        else
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The table is empty in INCIDENT LIST HISTORY TAB");
               //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/warn.png"));
                                       dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("The table is empty! Please fill it by pressing a rick click on a problem from 'Problem History' tab, "
                        + "but only if the problem has incidents.");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
        }
        
        
    }
    public void DeleteIncidentHistory()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE INCIDENT button was pressed in INCIDENT LIST HISTORY TAB \n");
        boolean isSelected= table_incident_history.getSelectionModel().isSelected(index_incidents);
        if(table_incident_history.getItems().isEmpty()==false)
        {
            if(isSelected == true)
            {
                if(deleteIncidentHistory==null)
                {
                                try {
                           deleteIncidentHistory=new Stage();
                           Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deleteincidenthistory.fxml"));
                           Scene scene = new Scene(root);

                           deleteIncidentHistory.setTitle("Delete Incident List History");
                           deleteIncidentHistory.initStyle(StageStyle.UTILITY);
                           deleteIncidentHistory.setScene(scene);
                           deleteIncidentHistory.setOnHidden(e->{
                               showIncidents();
                           });
                           deleteIncidentHistory.setResizable(false);
                           deleteIncidentHistory.show();


                       } catch (IOException ex) {
                           logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);
                       }
                }
                else if(deleteIncidentHistory.isShowing())
                {
                    //deleteIncidentHistory.toFront();
                    //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/warn.png"));
                                       dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Delete Incident List History' with unsaved changes is already open in the background! "
                                + "Close it if"
                                + " you want to ignore it and open a new one for another incident.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                }
                else
                {
                     try {
                           deleteIncidentHistory=new Stage();
                           Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deleteincidenthistory.fxml"));
                           Scene scene = new Scene(root);

                           deleteIncidentHistory.setTitle("Delete Incident List History");
                           deleteIncidentHistory.initStyle(StageStyle.UTILITY);
                           deleteIncidentHistory.setScene(scene);
                           deleteIncidentHistory.setOnHidden(e->{
                               showIncidents();
                           });
                           deleteIncidentHistory.setResizable(false);
                           deleteIncidentHistory.show();


                       } catch (IOException ex) {
                           logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);
                       }
                }
               
       
            }
            else if(isSelected == false)
            {
                if(deleteIncidentHistory!=null)
                {
                    if(deleteIncidentHistory.isShowing()==true)
                    {
                        //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                       Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/warn.png"));
                                       dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Delete Incident List History' with unsaved changes is already open in the background! "
                                + "Close it if"
                                + " you want to ignore it and open a new one for another incident.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                    }
                    else
                    {
                        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in INCIDENT LIST HISTORY TAB");
                        //Creating a dialog
                       Dialog<String> dialog = new Dialog<String>();
                       //Setting the title
                       Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/warn.png"));
                                       dialog.setTitle("Warning");
                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                       //Setting the content of the dialog
                       dialog.setContentText("No row was selected! Please select one.");
                       //Adding buttons to the dialog pane
                       dialog.getDialogPane().getButtonTypes().add(type);
                       dialog.showAndWait();
                    }
                }
                else
                {
                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in INCIDENT LIST HISTORY TAB");
                    //Creating a dialog
                   Dialog<String> dialog = new Dialog<String>();
                   //Setting the title
                   Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/warn.png"));
                                       dialog.setTitle("Warning");
                   ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                   //Setting the content of the dialog
                   dialog.setContentText("No row was selected! Please select one.");
                   //Adding buttons to the dialog pane
                   dialog.getDialogPane().getButtonTypes().add(type);
                   dialog.showAndWait();
                }
                
                
            }
        }
        else
        {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The table is empty in INCIDENT LIST HISTORY TAB");
                 //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                                       stage.getIcons().add(new Image("images/warn.png"));
                                       dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("The table is empty! Please fill it by pressing a rick click on a problem from 'Problem History' tab, "
                        + "but only if the problem has incidents.");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
                
        }
    }
    
    
    
    
    //--------------------------------------------------------------------------------IF STRUCTURE---------------------------------------------------------------------------------
    @FXML
    private Button add_btn_if_struct;
      
      @FXML
    private ImageView addiconif;
       
    @FXML
    private Button edit_btn_if_struct;
    
    @FXML
    private ImageView editiconif;
    
    @FXML
    private Button deletebtnif;
    
    @FXML
    private ImageView deleteiconif;
    
    @FXML
    private TableView<IfStruct> table_if_structure;

    @FXML
    private TableColumn<IfStruct, String> col_name_if;

    @FXML
    private TableColumn<IfStruct, String> col_desc_if;

    @FXML
    private TableColumn<IfStruct, Integer> col_cc_if;
    
    
    ObservableList<IfStruct> dataListIfStruct;
    
    
    @FXML
    private Tab tabif;
    
    @FXML
    void search_if_structure()
    {
        if(tabif.isSelected()==true)
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Loading data in table in IF STRUCTURE TAB \n");
            col_name_if.setCellValueFactory(new PropertyValueFactory<IfStruct,String>("name"));
            col_desc_if.setCellValueFactory(new PropertyValueFactory<IfStruct,String>("description"));
            col_cc_if.setCellValueFactory(new PropertyValueFactory<IfStruct,Integer>("cc"));
            dataListIfStruct=ConnectionDatabase.getIfStructure();
            if(dataListIfStruct.isEmpty()==false)
            {
                Comparator<IfStruct> comparator = Comparator.comparing(IfStruct::getDescription); 
                comparator.reversed();
                FXCollections.sort(dataListIfStruct, comparator);
                table_if_structure.setItems(dataListIfStruct);
            }
            else
            {
                table_if_structure.getItems().clear();
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of IFs is empty in IF STRUCTURE TAB");
            }
        }
        else if(tabif.isSelected()==false)
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: IF STRUCTURE TAB has been released!");
        }
        
        
        
        
    }
    public void getSelectedIfStruct()
    {
        
        index=table_if_structure.getSelectionModel().getSelectedIndex();
        if(index <= -1)
        {
            return;
        }
        IfStruct if_struct=table_if_structure.getSelectionModel().getSelectedItem();
        if(if_struct!=null)
        {
            Data.if_name=if_struct.getName();
            Data.if_desc=if_struct.getDescription();
            Data.if_cc=if_struct.getCc();
            Data.id_if_structure=if_struct.getId();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Details about the IF STRUCTURE selected: \n"+
                                                                  "ID: "+Data.id_if_structure+"\n"+
                                                                  "Name: "+Data.if_name+"\n"+
                                                                  "Description: "+Data.if_desc+"\n"+
                                                                   "CC: "+Data.if_cc+"\n");
            
            
        }
        else
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The if structure selected is empty in IF STRUCTURE TAB \n");
        }
        
        
    }
    public void add_if_structure()

    {

      

        logger.info(Log4jConfiguration.currentTime()+"[INFO]: ADD IF button was pressed in IF STRUCTURE TAB");

        boolean tableselect=table_if_structure.getSelectionModel().isSelected(index);

        if(tableselect==true)

        {

            if(addIf!=null)

            {

                if(addIf.isShowing()==true)

                {

                    Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Add IF Structure' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                }

                else

                {

                    Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("Can't add on a row! Unselect the row from the table and press Add button!");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                }

            }

            else

            {

                Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("Can't add on a row! Unselect the row from the table and press Add button!");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

            }

            //System.out.println("tabel selectat");

           

        }

        else

            //tabel neselectat

        {

            //System.out.println("tabel neselectat");

            if(addIf==null)

            {

                //System.out.println("null stage ");

                try {

 

                   addIf=new Stage();

                   Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addifstructure"

                           + ".fxml"));

                   Scene scene = new Scene(root);

 

                   addIf.setTitle("Add IF Structure");

                   addIf.initStyle(StageStyle.UTILITY);

                   addIf.setScene(scene);

                   addIf.setOnHidden(e->{

                                search_if_structure();

                   });

                   addIf.setResizable(false);

                   addIf.show();

                   /*primaryStage.setOnCloseRequest(event -> {

                       search_if_structure();

 

                   });*/

 

               } catch (IOException ex) {

                   logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);

               }

               

            }

            else if(addIf.isShowing()==true)

            {

                       // System.out.println("something in the background");

                        Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Add IF Structure' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

            }

            else

            {

                try {

 

                   addIf=new Stage();

                   Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addifstructure"

                           + ".fxml"));

                   Scene scene = new Scene(root);

 

                   addIf.setTitle("Add IF Structure");

                   addIf.initStyle(StageStyle.UTILITY);

                   addIf.setScene(scene);

                   addIf.setOnHidden(e->{

                                search_if_structure();

                   });

                   addIf.setResizable(false);

                   addIf.show();

                   /*primaryStage.setOnCloseRequest(event -> {

                       search_if_structure();

 

                   });*/

 

               } catch (IOException ex) {

                   logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);

               }

               

            }

           

        }

       

        

        

       

    }

    public void edit_if_structure()

    {

        logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT IF button was pressed in IF STRUCTURE TAB");

        boolean tableselect=table_if_structure.getSelectionModel().isSelected(index);

        if(tableselect == true)

       {

            if(editIf==null)

            {

                    try {

                            editIf=new Stage();

                            Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editifstructure.fxml"));

                            Scene scene = new Scene(root);

 

                            editIf.setTitle("Edit IF Structure");

                            editIf.initStyle(StageStyle.UTILITY);

                            editIf.setScene(scene);

                            editIf.setOnHidden(e->{

                                search_if_structure();

                            });

                            editIf.setResizable(false);

                            editIf.show();

                           

 

                        } catch (IOException ex) {

                            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);

                        }

            }

            else if(editIf.isShowing())

            {

                Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Edit IF Structure' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

            }

            else

            {

                try {

                            editIf=new Stage();

                            Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editifstructure.fxml"));

                            Scene scene = new Scene(root);

 

                            editIf.setTitle("Edit IF Structure");

                            editIf.initStyle(StageStyle.UTILITY);

                            editIf.setScene(scene);

                            editIf.setOnHidden(e->{

                                search_if_structure();

                            });

                            editIf.setResizable(false);

                            editIf.show();

                           

 

                        } catch (IOException ex) {

                            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);

                        }

            }

      

        }

        else

        {

                if(editIf!=null)

                {

                    if(editIf.isShowing())

                    {

                        Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Edit IF Structure' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                    }

                    else

                    {

                        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in IF STRUCTURE TAB");

                        //Creating a dialog

                        Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("No row was selected! Please select one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                    }

                }

                else

                {

                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in IF STRUCTURE TAB");

                    //Creating a dialog

                    Dialog<String> dialog = new Dialog<String>();

                    //Setting the title

                    Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("images/warn.png"));
                        dialog.setTitle("Warning");

                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                    //Setting the content of the dialog

                    dialog.setContentText("No row was selected! Please select one.");

                    //Adding buttons to the dialog pane

                    dialog.getDialogPane().getButtonTypes().add(type);

                    dialog.showAndWait();

                }

              

                

        }

       

    }

     public void delete_if_structure()

    {

        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE IF button was pressed in IF STRUCTURE TAB \n");

        boolean tableselect=table_if_structure.getSelectionModel().isSelected(index);

        if(tableselect == true)

        {

            if(deleteIf==null)

            {

                try {

                    deleteIf=new Stage();

                    Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deleteifstructure.fxml"));

                    Scene scene = new Scene(root);

 

                    deleteIf.setTitle("Delete IF Structure");

                    deleteIf.setScene(scene);

                    deleteIf.initStyle(StageStyle.UTILITY);

                    deleteIf.setOnHidden(e->{

                        search_if_structure();

                       

                    });

                    deleteIf.setResizable(false);

                    deleteIf.show();

                   

 

                } catch (IOException ex) {

                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);

                }

            }

            else if(deleteIf.isShowing())

            {

                Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Delete IF Structure' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

            }

            else

            {

                try {

                    deleteIf=new Stage();

                    Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deleteifstructure.fxml"));

                    Scene scene = new Scene(root);

 

                    deleteIf.setTitle("Delete IF Structure");

                    deleteIf.setScene(scene);

                    deleteIf.initStyle(StageStyle.UTILITY);

                    deleteIf.setOnHidden(e->{

                        search_if_structure();

                       

                    });

                    deleteIf.setResizable(false);

                    deleteIf.show();

                   

 

                } catch (IOException ex) {

                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);

                }

            }

          

        }

        else

        {

            if(deleteIf!=null)

            {

                if(deleteIf.isShowing()==true)

                {

                    Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Delete IF Structure' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                }

                else

                {

                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in IF STRUCTURE TAB");

                    //Creating a dialog

                    Dialog<String> dialog = new Dialog<String>();

                    //Setting the title

                    Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                    //Setting the content of the dialog

                    dialog.setContentText("No row was selected! Please select one.");

                    //Adding buttons to the dialog pane

                    dialog.getDialogPane().getButtonTypes().add(type);

                    dialog.showAndWait();

                }

            }

            else

            {

                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in IF STRUCTURE TAB");

                //Creating a dialog

                Dialog<String> dialog = new Dialog<String>();

                //Setting the title

                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                //Setting the content of the dialog

                dialog.setContentText("No row was selected! Please select one.");

                //Adding buttons to the dialog pane

                dialog.getDialogPane().getButtonTypes().add(type);

                dialog.showAndWait();

            }

               

                

        }

       

    }
   
    //------------------------------------------------------------------------------- LINE STRUCTURE--------------------------------------------------------------------------------
    @FXML
    private Button addbtn_line;

    @FXML
    private Button editbtn_line;

    @FXML
    private Button deletebtn_line;
    @FXML
    private ImageView addiconline;

    @FXML
    private ImageView editiconline;

    @FXML
    private ImageView deleteiconline;
    @FXML
    private TableView<LineStruct> table_line_structure;
    @FXML
    private TableColumn<LineStruct, String> col_name_line;

    @FXML
    private TableColumn<LineStruct, String> col_desc_line;

    @FXML
    private TableColumn<LineStruct, Integer> col_cc_line;
    
     @FXML
    private ComboBox<String> comboiflinestruct;
     
     @FXML
    private Tab tabLine;
    
    
    ObservableList<LineStruct> dataListLineStruct;
    ObservableList<String> itemscomboif= FXCollections.observableArrayList();
    
     @FXML
    void search_line_structure()
    {
                    if(tabLine.isSelected()==true)
                    {
                        itemscomboif=ConnectionDatabase.getIFCombo();
                        Collections.sort(itemscomboif);

                        if(!itemscomboif.isEmpty())
                        {
                            comboiflinestruct.setValue("Select...");
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
                            Collections.sort(itemscomboif,c);
                            itemscomboif.add(0,"Select...");
                            comboiflinestruct.setItems(itemscomboif);  

                            String ifinit=itemscomboif.get(1);
                            for(int i=2;i< itemscomboif.size();i++)
                            {
                                ifinit=ifinit+"["+i+"]: "+itemscomboif.get(i)+"\n";
                            }

                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF IFs in LINE STRUCTURE TAB: \n"+ifinit+"\n");

                        }
                        else
                        {
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The 'Select...' option was choose from IF combobox in LINE STRUCTURE TAB \n");
                            comboiflinestruct.getItems().clear();
                            comboiflinestruct.setValue("");
                        }
                   
                }
                else if(tabLine.isSelected()==false)
                {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The LINE STRUCTURE TAB is released");
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: Clear selection in line structure...");
                    table_line_structure.getItems().clear();
                    comboiflinestruct.getSelectionModel().clearSelection();
                    comboiflinestruct.setValue("Select...");
                    
                    
                }
                    
                    
                    
    }
    public void view_lines()
    {
        if(comboiflinestruct.getSelectionModel().isEmpty()==false)
        {
            if(comboiflinestruct.getValue().toString().equals("Select..."))
               {
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Loading all lines in table because 'Select...' option from IF was choosed in LINE STRUCTURE TAB \n");
                col_name_line.setCellValueFactory(new PropertyValueFactory<LineStruct,String>("name"));
                col_desc_line.setCellValueFactory(new PropertyValueFactory<LineStruct,String>("functionalplace"));
                col_cc_line.setCellValueFactory(new PropertyValueFactory<LineStruct,Integer>("cc"));
                dataListLineStruct=ConnectionDatabase.getLineStructure();
                if(dataListLineStruct.isEmpty()==false)
                {
                    
                    //Comparator<LineStruct> comparator = Comparator.comparing(LineStruct::getDescription); 
                    //comparator.reversed();

                   dataListLineStruct=dataListLineStruct
                    .stream()
                    .sorted(Comparator.comparing(LineStruct::getName))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

                    //FXCollections.sort(dataListLineStruct, comparator);
                    table_line_structure.setItems(dataListLineStruct); 
                }
                else
                {
                    table_line_structure.getItems().clear();
                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of lines is empty in LINE STRUCTURE TAB ");
                }
                
               }
               else
               {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: Loading the lines from "+comboiflinestruct+" in table in LINE STRUCTURE TAB \n");
                    
                    int ifid=ConnectionDatabase.getIfId(comboiflinestruct.getValue().toString());
                     col_name_line.setCellValueFactory(new PropertyValueFactory<LineStruct,String>("name"));
                     col_desc_line.setCellValueFactory(new PropertyValueFactory<LineStruct,String>("functionalplace"));
                     col_cc_line.setCellValueFactory(new PropertyValueFactory<LineStruct,Integer>("cc"));
                     dataListLineStruct=ConnectionDatabase.getLineStructureByIfId(ifid);
                     
                     if(dataListLineStruct.isEmpty()==false)
                     {
                         
                     

                        dataListLineStruct=dataListLineStruct
                         .stream()
                         .sorted(Comparator.comparing(LineStruct::getName))
                         .collect(Collectors.toCollection(FXCollections::observableArrayList));

                         table_line_structure.setItems(dataListLineStruct); 
                     }
                     else
                     {
                         table_line_structure.getItems().clear();
                         logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of lines is empty in LINE STRUCTURE TAB ");
                     }
                     
               }
        }
        else
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The IF combobox is empty in LINE STRUCTURE TAB! \n");
        }
               
                
    }
    public void onActionCombo()
    {
        boolean isMyComboBoxEmpty = comboiflinestruct.getSelectionModel().isEmpty();
        if(isMyComboBoxEmpty == false && !comboiflinestruct.getValue().toString().equals("Select..."))
        {
            Data.comboifforline=comboiflinestruct.getValue().toString();
            int ifid=ConnectionDatabase.getIfId(comboiflinestruct.getValue().toString());
            //logger.info(comboiflinestruct.getValue().toString() + " selected in Admin,Line Structure");
            
            if(ifid!=0)
            {
                col_name_line.setCellValueFactory(new PropertyValueFactory<LineStruct,String>("name"));
                col_desc_line.setCellValueFactory(new PropertyValueFactory<LineStruct,String>("functionalplace"));
                col_cc_line.setCellValueFactory(new PropertyValueFactory<LineStruct,Integer>("cc"));
                dataListLineStruct=ConnectionDatabase.getLineStructureByIfId(ifid);
                if(dataListLineStruct.isEmpty()==false)
                {
                    LineStruct line=dataListLineStruct.get(0);
                    String msgline="OUTPUT LIST of Lines from "+comboiflinestruct.getValue().toString()+" in LINE STRUCTURE TAB :\n"+line.toString()+"\n";
                    for(int i=1;i<dataListLineStruct.size();i++)
                    {
                        msgline=msgline+"["+i+"]:"+dataListLineStruct.get(i).toString()+"\n";
                    }
                    //logger.info(msgline);
                    Comparator<LineStruct> comparator = Comparator.comparing(LineStruct::getName); 
                    FXCollections.sort(dataListLineStruct, comparator);
                    table_line_structure.setItems(dataListLineStruct); 
                }
                else
                {
                    table_line_structure.getItems().clear();
                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty list of lines for table in LINE STRUCTURE TAB");
                    
                }
                
                
                
                        
            }
            
        }
        else if(isMyComboBoxEmpty == false && comboiflinestruct.getValue().toString().equals("Select..."))
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The 'Select...' option was choose from IF in LINE STRUCTURE TAB \n");
            table_line_structure.getItems().clear();
            //logger.info("Line structure table was cleared after selecting the 'Select...' option from IF \n");
        }
        
                        
        
    }
    public void getSelectedLineStruct()
    {
        index=table_line_structure.getSelectionModel().getSelectedIndex();
        if(index <= -1)
        {
            return;
        }
        LineStruct linie=table_line_structure.getSelectionModel().getSelectedItem();
        if(linie!=null)
        {
            Data.line_name=linie.getName();
            Data.line_functional_place=linie.getFunctionalplace();
            Data.line_cc=linie.getCc();
            Data.id_line_structure=linie.getId();
            Data.ifid=linie.getIfid();
            Data.if_desc=ConnectionDatabase.getDescAfterIFID(Data.ifid);
            String linedetails="About the line selected from table in LINE STRUCTURE TAB: \n"
                    +"IF: " + Data.if_desc+ "\n"+
                    "Line Name: " + Data.line_name + 
                    "\nLine Functional Place: " + Data.line_functional_place +"\n"+
                    "Line_CC: "+Data.line_cc+"\n";
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: "+linedetails);
        }
        else
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The line structure selected is null in LINE STRUCTURE TAB \n");
        }
        
        
        
        
        
        

    }
     public void addLine()

    {

       logger.info(Log4jConfiguration.currentTime()+"[INFO]: ADD LINE button was pressed from LINE STRUCTURE TAB");

        boolean isCline = comboiflinestruct.getSelectionModel().isEmpty();

        boolean tableselect=table_line_structure.getSelectionModel().isSelected(index);

        //System.out.println(tableselect);

        if(tableselect==true)

        {

            if(addLine!=null)

            {

                    if(addLine.isShowing()==true)

                    {

                        Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Add Line Structure' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                    }

                    else

                    {

                        Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                           Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("Can't add on a row! Unselect the row from the table and press Add button!");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                    }

            }

            else

            {

                Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("Can't add on a row! Unselect the row from the table and press Add button!");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

            }

          

        }

         //nu e selectat

            else

            {

                if(addLine==null)

                {

                        try {

 

                       addLine=new Stage();

                       Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addline.fxml"));

                       Scene scene = new Scene(root);

 

                       addLine.setTitle("Add Line Structure");

                       addLine.initStyle(StageStyle.UTILITY);

                       addLine.setScene(scene);

                       addLine.setOnHidden(e->{

                            view_lines();

 

                        });

                       addLine.setResizable(false);

                       addLine.show();

 

 

                   } catch (IOException ex) {

                       logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);

                   }

                }

                else if(addLine.isShowing()==true)

                {

                    // System.out.println("something in the background");

                        Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                       Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Add Line Structure' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                }

                else

                {

                        try {

 

                       addLine=new Stage();

                       Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addline.fxml"));

                       Scene scene = new Scene(root);

 

                       addLine.setTitle("Add Line Structure");

                       addLine.initStyle(StageStyle.UTILITY);

                       addLine.setScene(scene);

                       addLine.setOnHidden(e->{

                            view_lines();

 

                        });

                       addLine.setResizable(false);

                       addLine.show();

 

 

                   } catch (IOException ex) {

                       logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);

                   }

                }

            }

          

            

    }

      public void editLine()

    {

        logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT LINE button was pressed in LINE STRUCTURE TAB \n");

        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The EDIT LINE button was pressed in LINE STRUCTURE TAB \n");

        boolean isCline = comboiflinestruct.getSelectionModel().isEmpty();

        if(table_line_structure.getItems().isEmpty()==true)

        {

            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The table is empty in LINE STRUCTURE TAB");

                 //Creating a dialog

                Dialog<String> dialog = new Dialog<String>();

                //Setting the title

                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                //Setting the content of the dialog

                dialog.setContentText("The table is empty! Please select an 'IF'.");

                //Adding buttons to the dialog pane

                dialog.getDialogPane().getButtonTypes().add(type);

                dialog.showAndWait();

        }

        else if(table_line_structure.getItems().isEmpty()==false)

       {

        boolean tableselect=table_line_structure.getSelectionModel().isSelected(index);

        if(tableselect == true)

        {

            if(editLine==null)

            {

                try {

                    editLine=new Stage();

                    Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editlinestructure.fxml"));

                    Scene scene = new Scene(root);

 

                    editLine.setTitle("Edit Line Structure");

                    editLine.initStyle(StageStyle.UTILITY);

                    editLine.setScene(scene);

                    editLine.setOnHidden(e->{

                       view_lines();

                       

                    });

                    editLine.setResizable(false);

                    editLine.show();

                   

 

                } catch (IOException ex) {

                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                }

            }

            else if(editLine.isShowing())

            {

                Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Edit Line Structure' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

            }

            else

            {

                try {

                    editLine=new Stage();

                    Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editlinestructure.fxml"));

                    Scene scene = new Scene(root);

 

                    editLine.setTitle("Edit Line Structure");

                    editLine.initStyle(StageStyle.UTILITY);

                    editLine.setScene(scene);

                    editLine.setOnHidden(e->{

                       view_lines();

                       

                    });

                    editLine.setResizable(false);

                    editLine.show();

                   

 

                } catch (IOException ex) {

                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                }

            }

           

         }

        else

        {

            if(editLine!=null)

            {

                if(editLine.isShowing()==true)

                {

                    Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Edit Line Structure' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                }

                else

                {

                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in LINE STRUCTURE TAB");

                    //Creating a dialog

                    Dialog<String> dialog = new Dialog<String>();

                    //Setting the title

                    Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                    //Setting the content of the dialog

                    dialog.setContentText("No row was selected! Please select one.");

                    //Adding buttons to the dialog pane

                    dialog.getDialogPane().getButtonTypes().add(type);

                    dialog.showAndWait();

                }

            }

            else

            {

                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in LINE STRUCTURE TAB");

                //Creating a dialog

                Dialog<String> dialog = new Dialog<String>();

                //Setting the title

                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                //Setting the content of the dialog

                dialog.setContentText("No row was selected! Please select one.");

                //Adding buttons to the dialog pane

                dialog.getDialogPane().getButtonTypes().add(type);

                dialog.showAndWait();

            }

           

                

          }

        }

       

    }

      public void deleteLine()

    {

        boolean isCline = comboiflinestruct.getSelectionModel().isEmpty();

       

        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE LINE button was pressed in LINE STRUCTURE TAB \n");

       

        if(table_line_structure.getItems().isEmpty()==true)

        {

                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The table is empty in LINE STRUCTURE TAB");

                 //Creating a dialog

                Dialog<String> dialog = new Dialog<String>();

                //Setting the title

                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                //Setting the content of the dialog

                dialog.setContentText("The table is empty! Please select an 'IF'.");

                //Adding buttons to the dialog pane

                dialog.getDialogPane().getButtonTypes().add(type);

                dialog.showAndWait();

        }

        else if(!comboiflinestruct.getValue().isEmpty()&& !comboiflinestruct.getValue().toString().equals("Select..."))

        {

       

        boolean tableselect=table_line_structure.getSelectionModel().isSelected(index);

        if(tableselect == true)

        {

            if(deleteLine==null)

            {

                 try {

                    deleteLine=new Stage();

                    Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deletelinestructure.fxml"));

                    Scene scene = new Scene(root);

 

                    deleteLine.setTitle("Delete Line Structure");

                    deleteLine.initStyle(StageStyle.UTILITY);

                    deleteLine.setScene(scene);

                    deleteLine.setOnHidden(e->{

                        view_lines();

                       

                    });

                    deleteLine.setResizable(false);

                    deleteLine.show();

                   

                } catch (IOException ex) {

                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                }

            }

            else if(deleteLine.isShowing())

            {

                 Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                       Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Delete IF Structure' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

            }

            else

            {

                try {

                    deleteLine=new Stage();

                    Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deletelinestructure.fxml"));

                    Scene scene = new Scene(root);

 

                    deleteLine.setTitle("Delete");

                    deleteLine.initStyle(StageStyle.UTILITY);

                    deleteLine.setScene(scene);

                    deleteLine.setOnHidden(e->{

                        view_lines();

                       

                    });

                    deleteLine.setResizable(false);

                    deleteLine.show();

                   

                } catch (IOException ex) {

                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                }

            }

                

        }

        else

        {

            if(deleteLine!=null)

            {

                if(deleteLine.isShowing()==true)

                {

                    Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Delete IF Structure' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                }

                else

                {

                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in LINE STRUCTURE TAB");

                //Creating a dialog

                Dialog<String> dialog = new Dialog<String>();

                //Setting the title

               Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                //Setting the content of the dialog

                dialog.setContentText("No row was selected! Please select one.");

                //Adding buttons to the dialog pane

                dialog.getDialogPane().getButtonTypes().add(type);

                dialog.showAndWait();

               

                }

            }

            else

            {

                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected in LINE STRUCTURE TAB");

                //Creating a dialog

                Dialog<String> dialog = new Dialog<String>();

                //Setting the title

                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                //Setting the content of the dialog

                dialog.setContentText("No row was selected! Please select one.");

                //Adding buttons to the dialog pane

                dialog.getDialogPane().getButtonTypes().add(type);

                dialog.showAndWait();

               

            }

              

         }

        }

       

    }

 
    //-------------------------------------------------------------------------------MACHINE STRUCTURE-----------------------------------------------------------------------------
     @FXML
    private Button addbtn_machine;

    @FXML
    private Button editbtnmachine;

    @FXML
    private Button deletemachine;
    
    @FXML
    private ImageView addiconmachine;

    @FXML
    private ImageView editiconmachine;

    @FXML
    private ImageView deleteiconmachine;
    
   @FXML
    private Button search_button_machine;
   
   @FXML
    private ImageView searchiconmachine;

    @FXML
    private TextField search_field_machine_structure;

    
    @FXML
    private TableView<MachineStruct> table_machine_structure;

   @FXML
    private TableColumn<MachineStruct, String> col_name_machine;

    @FXML
    private TableColumn<MachineStruct, String> col_invnr_machine;
    @FXML
    private ComboBox<String> comboif;

    @FXML
    private ComboBox<String> comboline;
    
    ObservableList<MachineStruct> dataListMachineStruct;
    ObservableList<String> itemscombo=FXCollections.observableArrayList();
    ObservableList<String> itemscomboline=FXCollections.observableArrayList();
    @FXML
    private ImageView machinestructsearchicon;
    
    private int if_id_machine_structure=-1;
    private int line_id_machine_structure=-1;
    
    @FXML
    private Tab tabMachine;

    
    
     public void onSelectMachineTab()
    {
                    //boolean isMyComboBoxEmpty = comboif.getSelectionModel().isEmpty();
                    //if(isMyComboBoxEmpty == false)
                    //{
                       if(tabMachine.isSelected()==true)
                       {
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Load things for MACHINE STRUCTURE TAB");
                        
                            itemscombo=ConnectionDatabase.getIFCombo();
                            comboif.setValue("Select...");
                            if(!itemscombo.isEmpty())
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
                                Collections.sort(itemscombo,c);
                                itemscombo.add(0, "Select...");
                                comboif.setItems(itemscombo);

                                String ifinit="[1]: "+itemscombo.get(1)+"\n";
                                for(int i=2;i<itemscombo.size();i++)
                                {
                                    ifinit=ifinit+ "[" + i +"]" + ": " + itemscombo.get(i) + "\n";

                                }
                               logger.info(Log4jConfiguration.currentTime()+"[INFO] : OUTPUT LIST OF IFs in MACHINE TAB: \n"+ifinit+"\n");

                            }
                            else
                            {
                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The list of IFs is empty in MACHINE TAB! \n");
                                comboif.getItems().clear();
                                comboif.setValue("");
                            }
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
                            itemscomboline=ConnectionDatabase.getLinesName();
                            comboline.setValue("Select...");
                            if(!itemscomboline.isEmpty())
                            {

                                Collections.sort(itemscomboline,c);
                                itemscomboline.add(0,"Select...");
                                comboline.setItems(itemscomboline);
                                String line="[1]: "+itemscomboline.get(1)+"\n";
                                for(int i=2;i<itemscomboline.size();i++)
                                {
                                    line=line+ "[" + i +"]" + ": " + itemscomboline.get(i) + "\n";

                                }
                               logger.info(Log4jConfiguration.currentTime()+"[INFO] : OUTPUT LIST OF lines in MACHINE TAB: \n"+line+"\n");
                            }
                            else
                            {
                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The list of lines is empty in MACHINE TAB! \n");
                                comboline.getItems().clear();
                                comboline.setValue("");
                            }
                        //}
                    }
                    else if(tabMachine.isSelected()==false)
                    {
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Clearing selection in MACHINE STRUCTURE TAB ");
                    
                            table_machine_structure.getItems().clear();
                            comboif.getSelectionModel().clearSelection();
                            comboif.setValue("Select...");

                            comboline.getSelectionModel().clearSelection();
                            comboline.setValue("Select...");
                    }
                           
               
    }
     public void actionComboIFMachine()
     {
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: The IF combobox from MACHINE TAB was pressed! ");
               boolean isMyComboBoxEmpty = comboif.getSelectionModel().isEmpty();
               if(isMyComboBoxEmpty == false && !comboif.getValue().toString().equals("Select...") )
               {
                   logger.info(Log4jConfiguration.currentTime()+"[INFO]:The "+comboif.getValue().toString()+" was selected! ");
                   //Data.comboifvalue=comboif.getValue().toString();
                   int ifid=ConnectionDatabase.getIfId(comboif.getValue().toString());
                   if(ifid!=0)
                   {
                        if_id_machine_structure=ifid;
                        itemscomboline=ConnectionDatabase.getLinesfromIf(ifid);
                        
                        if(!itemscomboline.isEmpty())
                        {
                            comboline.setValue("Select...");
                             //comboline.setValue(itemscomboline.get(0));
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
                             //Data.combolinevalue=comboline.getValue().toString();
                             
                            String line="[1]: "+itemscomboline.get(1)+"\n";
                            for(int i=2;i<itemscomboline.size();i++)
                            {
                                line=line+ "[" + i +"]" + ": " + itemscomboline.get(i) + "\n";

                            }
                           logger.info(Log4jConfiguration.currentTime()+"[INFO] : OUTPUT LIST OF lines from the selected IF in MACHINE TAB: \n"+line+"\n");
                             
                             
                        }
                        else{
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The list of lines from the selected IF is empty in MACHINE TAB! \n");
                            comboline.getItems().clear();
                            //Data.combolinevalue="";
                            
                        }
                   }
                   //boolean isMyComboBoxEmptyline = comboline.getSelectionModel().isEmpty();
                   //if(isMyComboBoxEmptyline==false && !comboline.getValue().toString().equals("Select..."))
                   //{
                        //viewallMachines();
                   //}
                   

               }
               else if(isMyComboBoxEmpty == false && comboif.getValue().toString().equals("Select...") )
               {
                   logger.info(Log4jConfiguration.currentTime()+"[INFO]: The 'Select...' option was choose from IF combobox in MACHINE STRUCTURE TAB");
                   if_id_machine_structure=-1;
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
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The list of lines have been reset it");
                        itemscomboline=ConnectionDatabase.getLinesName();
                        comboline.setValue("Select...");
                        if(!itemscomboline.isEmpty())
                        {
                            
                            Collections.sort(itemscomboline,c);
                            itemscomboline.add(0,"Select...");
                            comboline.setItems(itemscomboline);
                            
                            String line="[1]: "+itemscomboline.get(1)+"\n";
                            for(int i=2;i<itemscomboline.size();i++)
                            {
                                line=line+ "[" + i +"]" + ": " + itemscomboline.get(i) + "\n";

                            }
                           logger.info(Log4jConfiguration.currentTime()+"[INFO] : OUTPUT LIST OF lines in MACHINE TAB: \n"+line+"\n");
                        }
                        else
                        {
                            comboline.getItems().clear();
                            comboline.setValue("");
                        }
                   //Data.comboifvalue="";
               }
         
              
     }
     public void onActionComboLine()
     {
         boolean isMyComboBoxEmpty = comboline.getSelectionModel().isEmpty();
         if(isMyComboBoxEmpty==false && !comboline.getValue().toString().equals("Select..."))
         {
             //Data.combolinevalue=comboline.getValue().toString();
             int lid=ConnectionDatabase.getLineId(comboline.getValue().toString());
             line_id_machine_structure=lid;
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: The selected line in MACHINE TAB: \n"+comboline.getValue().toString()+"\n");
         }
         else if(isMyComboBoxEmpty==false && comboline.getValue().toString().equals("Select..."))
         {
             line_id_machine_structure=-1;
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: The 'Select...' option was choose from line combobox in MACHINE STRUCTURE TAB");
         }
         
     }
     
     public void view_machines()
     {
         
        
       logger.info(Log4jConfiguration.currentTime()+"[INFO]: Loading the filtered MACHINE STRUCTURE...");
        
        boolean isline = comboline.getSelectionModel().isEmpty();
        boolean isif=comboif.getSelectionModel().isEmpty();
        String text=search_field_machine_structure.getText();
        
        if(isif==true || isline==true)
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty IF/Line in MACHINE STRUCTURE TAB");
        }
        else if(isif==false && isline==false)
        {
            dataListMachineStruct=ConnectionDatabase.getMachineStructure(if_id_machine_structure, line_id_machine_structure, text);
            if(dataListMachineStruct.isEmpty()==false)
            {
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The filtered data of machines in MACHINE STRUCTURE TAB");
                col_name_machine.setCellValueFactory(new PropertyValueFactory<MachineStruct,String>("name"));
                col_invnr_machine.setCellValueFactory(new PropertyValueFactory<MachineStruct,String>("invnumber"));
                Comparator<MachineStruct> comparator = Comparator.comparing(MachineStruct::getName); 
                FXCollections.sort(dataListMachineStruct, comparator);

                table_machine_structure.setItems(dataListMachineStruct);
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The filtered data of machines was loaded in MACHINE STRUCTURE TAB");
            }
            else
            {
                table_machine_structure.getItems().clear();
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of filtered machines is empty in MACHINE STRUCTURE TAB");
            }
            
            
            
        }
        
        
        
     }
     public void getSelectedMachineStruct()
    {
        
       index=table_machine_structure.getSelectionModel().getSelectedIndex();
        if(index <= -1)
        {
            return;
        }
        MachineStruct machine=table_machine_structure.getSelectionModel().getSelectedItem();
        if(machine!=null)
        {
            Data.machine_name=machine.getName();
            Data.machine_invn=machine.getInvnumber();
            Data.id_machine_structure=machine.getId();
            Data.line_id_machine_structure=machine.getLine_id();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Details about the selected machine in MACHINE TAB: \n"+
                    "ID: "+Data.id_machine_structure+"\n"+
                    "Machine Name: "+Data.machine_name+"\n"+
                    "Machine Inventory Number: "+Data.machine_invn+"\n"+
                    "Line ID: "+Data.line_id_machine_structure+"\n");
        
        }
        else
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The machine structure selected is empty in MACHINE TAB! \n");
        }
        
        
        
        
    }
    
      public void addMachine()

    {

        boolean isMyComboLine = comboline.getSelectionModel().isEmpty();

        boolean isComboIF=comboif.getSelectionModel().isEmpty();

       

        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The ADD MACHINE button was pressed in MACHINE STRUCTURE TAB");

        boolean tableselect=table_machine_structure.getSelectionModel().isSelected(index);

        if(tableselect == true)

        {

            if(addMachine!=null)

            {

                    if(addMachine.isShowing()==true)

                    {

                        Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Add Machine Structure' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                    }

                    else

                    {

                        Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("Can't add on a row! Unselect the row from the table and press Add button!");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                    }

            }

            else

            {

                Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("Can't add on a row! Unselect the row from the table and press Add button!");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

            }

          

        }

        //nu e selectat

        else

        {

            if(addMachine==null)

            {

                try {

 

                       addMachine=new Stage();

                       Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addmachinestructure.fxml"));

                       Scene scene = new Scene(root);

 

                       addMachine.setTitle("Add Machine Structure");

                       addMachine.initStyle(StageStyle.UTILITY);

                       addMachine.setScene(scene);

                      addMachine.setOnHidden(e->{

                                     //viewallMachines();

                                     view_machines();

 

                               });

                       addMachine.setResizable(false);

                       addMachine.show();

 

 

                   } catch (IOException ex) {

                       logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                   }

            }

            else if(addMachine.isShowing())

            {

                // System.out.println("something in the background");

                        Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Add Machine Structure' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

            }

            else

            {

                try {

 

                       addMachine=new Stage();

                       Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addmachinestructure.fxml"));

                       Scene scene = new Scene(root);

 

                       addMachine.setTitle("Add Machine Structure");

                       addMachine.initStyle(StageStyle.UTILITY);

                       addMachine.setScene(scene);

                      addMachine.setOnHidden(e->{

                                     //viewallMachines();

                                     view_machines();

 

                               });

                       addMachine.setResizable(false);

                       addMachine.show();

 

 

                   } catch (IOException ex) {

                       logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                   }

            }

        }

       

        

    }

       public void editMachine()

    {

        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The EDIT MACHINE button was pressed in MACHINE STRUCTURE TAB");

        if(table_machine_structure.getItems().isEmpty()==true)

        {

                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The table is empty in MACHINE STRUCTURE TAB");

                //Creating a dialog

                Dialog<String> dialog = new Dialog<String>();

                //Setting the title

                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                //Setting the content of the dialog

                dialog.setContentText("Please choose a filter!");

                //Adding buttons to the dialog pane

                dialog.getDialogPane().getButtonTypes().add(type);

                dialog.showAndWait();

        }

        else

        {

                boolean tableselect=table_machine_structure.getSelectionModel().isSelected(index);

                if(tableselect == true)

                {

                    if(editMachine==null)

                    {

                               try {

 

                               editMachine=new Stage();

                               Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editmachine.fxml"));

                               Scene scene = new Scene(root);

 

                               editMachine.setTitle("Edit Machine Structure");

                               editMachine.initStyle(StageStyle.UTILITY);

                               editMachine.setScene(scene);

                               editMachine.setOnHidden(e->{

                                          view_machines();

 

                               });

                               editMachine.setResizable(false);

                               editMachine.show();

 

 

                           } catch (IOException ex) {

                               logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                           }

                    }

                    else if (editMachine.isShowing())

                    {

                        Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Edit Machine Structure' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                    }

                    else

                    {

                        try {

 

                               editMachine=new Stage();

                               Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editmachine.fxml"));

                               Scene scene = new Scene(root);

 

                               editMachine.setTitle("Edit Machine Structure");

                               editMachine.initStyle(StageStyle.UTILITY);

                               editMachine.setScene(scene);

                               editMachine.setOnHidden(e->{

                                           view_machines();

 

                               });

                               editMachine.setResizable(false);

                               editMachine.show();

 

 

                           } catch (IOException ex) {

                               logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                           }

                       

                    }

               

                

                }

                else

                {

                      if(editMachine!=null)

                      {

                          if(editMachine.isShowing()==true)

                          {

                                        Dialog<String> dialog = new Dialog<String>();

                                  //Setting the title

                                  Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                                  ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                                  //Setting the content of the dialog

                                  dialog.setContentText("A panel 'Edit Machine Structure' with unsaved changes is already open in the background! "

                                          + "Close it if"

                                          + " you want to ignore it and open a new one.");

                                  //Adding buttons to the dialog pane

                                  dialog.getDialogPane().getButtonTypes().add(type);

                                  dialog.showAndWait();

                          }

                          else

                          {

                              logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in MACHINE STRUCTURE TAB");

                              //Creating a dialog

                              Dialog<String> dialog = new Dialog<String>();

                              //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                              ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                              //Setting the content of the dialog

                              dialog.setContentText("No row was selected! Please select one.");

                              //Adding buttons to the dialog pane

                              dialog.getDialogPane().getButtonTypes().add(type);

                              dialog.showAndWait();

                          }

                      }

                      else

                      {

                            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in MACHINE STRUCTURE TAB");

                            //Creating a dialog

                            Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                           Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("No row was selected! Please select one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                      }

                      

 

                }

        }

       

    }

        public void deleteMachine()

    {

        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE MACHINE button was pressed in MACHINE STRUCTURE TAB");

        if(table_machine_structure.getItems().isEmpty()==true)

        {

               logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The table is empty in MACHINE STRUCTURE TAB");

                //Creating a dialog

                Dialog<String> dialog = new Dialog<String>();

                //Setting the title

                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                //Setting the content of the dialog

                dialog.setContentText("Please choose a filter!");

                //Adding buttons to the dialog pane

                dialog.getDialogPane().getButtonTypes().add(type);

                dialog.showAndWait();

        }

        else

        {

                boolean tableselect=table_machine_structure.getSelectionModel().isSelected(index);

                if(tableselect == true)

                {

                    if(deleteMachine==null)

                    {

                               try {

 

                               deleteMachine=new Stage();

                               Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/Deletemachinestructure.fxml"));

                               Scene scene = new Scene(root);

 

                               deleteMachine.setTitle("Delete Machine Structure");

                               deleteMachine.initStyle(StageStyle.UTILITY);

                               deleteMachine.setScene(scene);

                               deleteMachine.setOnHidden(e->{

                                          view_machines();

 

                               });

                               deleteMachine.setResizable(false);

                               deleteMachine.show();

 

 

                           } catch (IOException ex) {

                               logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                           }

                    }

                    else if (deleteMachine.isShowing())

                    {

                        Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Delete Machine Structure' with unsaved changes is already open in the background! "

                               + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                    }

                    else

                    {

                       try {

 

                               deleteMachine=new Stage();

                               Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/Deletemachinestructure.fxml"));

                               Scene scene = new Scene(root);

 

                               deleteMachine.setTitle("Delete Machine Structure");

                               deleteMachine.initStyle(StageStyle.UTILITY);

                               deleteMachine.setScene(scene);

                               deleteMachine.setOnHidden(e->{

                                          view_machines();

 

                               });

                               deleteMachine.setResizable(false);

                               deleteMachine.show();

 

 

                           } catch (IOException ex) {

                              logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                           }

                       

                    }

               

                

                }

                else

                {

                        if(deleteMachine!=null)

                        {

                            if(deleteMachine.isShowing()==true)

                            {

                                        Dialog<String> dialog = new Dialog<String>();

                                //Setting the title

                                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                                //Setting the content of the dialog

                                dialog.setContentText("A panel 'Delete Machine Structure' with unsaved changes is already open in the background! "

                                        + "Close it if"

                                        + " you want to ignore it and open a new one.");

                                //Adding buttons to the dialog pane

                                dialog.getDialogPane().getButtonTypes().add(type);

                                dialog.showAndWait();

                            }

                            else

                            {

                                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in MACHINE STRUCTURE TAB");

                                //Creating a dialog

                                Dialog<String> dialog = new Dialog<String>();

                                //Setting the title

                                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                                //Setting the content of the dialog

                                dialog.setContentText("No row was selected! Please select one.");

                                //Adding buttons to the dialog pane

                                dialog.getDialogPane().getButtonTypes().add(type);

                                dialog.showAndWait();

 

                            }

                        }

                        else

                        {

                            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in MACHINE STRUCTURE TAB");

                            //Creating a dialog

                            Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("No row was selected! Please select one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

 

                        }

                       

                }

        }

   }
   //--------------------------------------------------------------------------------TECHNICIANS----------------------------------------------------------------------------------
     @FXML
    private Button add_button_shiftteam;

    @FXML
    private Button edit_btn_shiftteam;

    @FXML
    private Button del_btnshiftteam;
    
    @FXML
    private ImageView addicontech;

    @FXML
    private ImageView editicontech;

    @FXML
    private ImageView deleteicontech;
    @FXML
    private TableView<ShTeam> table_shift_team;
   
   @FXML
    private TableColumn<ShTeam, String> colu_numeteh_shifteam;

    @FXML
    private TableColumn<ShTeam, String> col_job_desc;
    
     @FXML
    private TableColumn<ShTeam, String> col_shift_username;

    @FXML
    private TableColumn<ShTeam, String> col_email_shift_team;
    
     ObservableList<ShTeam> dataList;
     
     @FXML
    private Tab tabTech;
    
    public void getSelected()
    {
        
       index=table_shift_team.getSelectionModel().getSelectedIndex();
        if(index <= -1)
        {
            return;
        }
        ShTeam tech=table_shift_team.getSelectionModel().getSelectedItem();
        if(tech!=null)
        {
            Data.tehncian=tech.getNume();
            Data.jobdescription=tech.getJob();
            Data.username=tech.getUsername();
            Data.email=tech.getEmail();
            Data.id_sh_team=tech.getId();
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The details about the selected TECHNICIAN in TECHNICIANS TAB: \n"+
                                                               "ID: "+Data.id_sh_team+"\n"+
                                                               "Nume: "+Data.tehncian+"\n"+
                                                               "Job Description: "+Data.jobdescription+"\n"+
                                                               "Username: "+Data.username+"\n"+
                                                               "Email: "+Data.email+"\n");
        }
        else
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The TECHNICIAN object is null! \n");
        }
        
        
    }
    @FXML
    void search_team()
    {
        if(tabTech.isSelected()==true)
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Loading data in table in TECHNICIANS TAB \n");


                dataList=ConnectionDatabase.getDatausers();
                if(dataList.isEmpty()==false)
                {
                    colu_numeteh_shifteam.setCellValueFactory(new PropertyValueFactory<ShTeam,String>("nume"));
                    col_job_desc.setCellValueFactory(new PropertyValueFactory<ShTeam,String>("job"));
                    col_shift_username.setCellValueFactory(new PropertyValueFactory<ShTeam,String>("username"));
                    col_email_shift_team.setCellValueFactory(new PropertyValueFactory<ShTeam,String>("email"));
                    Comparator<ShTeam> comparator = Comparator.comparing(ShTeam::getNume); 
                    comparator.reversed();
                    FXCollections.sort(dataList, comparator);
                    table_shift_team.setItems(dataList);

                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data has been loaded \n");
                }
                else
                {
                    table_shift_team.getItems().clear();
                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of technicians is empty in TECHNICIANS TAB \n");
                }
        
        }
        else
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The TECHNICIANS TAB has been released!");
        }
        
    }
    public void add_shteam()

    {

       logger.info(Log4jConfiguration.currentTime()+"[INFO]: ADD TECHNICIAN button was pressed in TECHNICIANS TAB \n");

       boolean tableselect=table_shift_team.getSelectionModel().isSelected(index);

       if(tableselect == true)

       {

           if(addTech!=null)

            {

                    if(addTech.isShowing()==true)

                    {

                        Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Add Shift Team' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                           //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                    }

                    else

                    {

                        Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("Can't add on a row! Unselect the row from the table and press Add button!");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                    }

            }

            else

            {

                Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("Can't add on a row! Unselect the row from the table and press Add button!");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

            }

          

       }

       //nu e selectat

       else

       {

           if(addTech==null)

            {

                            try {

 

                       addTech=new Stage();

                       Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addshiftteam.fxml"));

                       Scene scene = new Scene(root);

 

                       addTech.setTitle("Add Shift Team");

                       addTech.initStyle(StageStyle.UTILITY);

                       addTech.setScene(scene);

                       addTech.setOnHidden(e->{

                           search_team();

                       });

                       addTech.setResizable(false);

                       addTech.show();

 

 

                   } catch (IOException ex) {

                       logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                   }

            }

            else if(addTech.isShowing())

            {

                Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Add Shift Team' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

            }

            else

            {

                try {

 

                       addTech=new Stage();

                       Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addshiftteam.fxml"));

                       Scene scene = new Scene(root);

 

                       addTech.setTitle("Add Shift Team");

                       addTech.initStyle(StageStyle.UTILITY);

                       addTech.setScene(scene);

                       addTech.setOnHidden(e->{

                           search_team();

                       });

                       addTech.setResizable(false);

                       addTech.show();

 

 

                   } catch (IOException ex) {

                       logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                   }

            }

       }

       

       

       

    }

    public void Edit()

    {

                logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT TECHNICIAN button was pressed in TECHNICIANS TAB \n");              

                boolean tableselect=table_shift_team.getSelectionModel().isSelected(index);

                if(tableselect == true)

                {

                    if(editTech==null)

                    {

                                    try {

                                editTech=new Stage();

                                Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editshiftteam.fxml"));

                                Scene scene = new Scene(root);

 

                                editTech.setTitle("Edit Shift Team");

                                editTech.initStyle(StageStyle.UTILITY);

                                editTech.setScene(scene);

                                editTech.setOnHidden(e->{

                                    search_team();

 

                                });

                                editTech.setResizable(false);

                                editTech.show();

 

 

                            } catch (IOException ex) {

                                logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                            }

                    }

                    else if(editTech.isShowing())  

                    {

                         Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Edit Shift Team' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                    }

                    else

                    {

                        try {

                                editTech=new Stage();

                                Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editshiftteam.fxml"));

                                Scene scene = new Scene(root);

 

                                editTech.setTitle("Edit Shift Team");

                                editTech.initStyle(StageStyle.UTILITY);

                                editTech.setScene(scene);

                                editTech.setOnHidden(e->{

                                    search_team();

 

                                });

                                editTech.setResizable(false);

                                editTech.show();

 

 

                            } catch (IOException ex) {

                                logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                            }

                       

                    }

               

                }

        else

        {

               if(editTech!=null)

               {

                   if(editTech.isShowing()==true)

                   {

                       Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Edit Shift Team' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                   }

                   else

                   {

                       logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in TECHNICIANS TAB");

                    //Creating a dialog

                    Dialog<String> dialog = new Dialog<String>();

                    //Setting the title

                    Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                    //Setting the content of the dialog

                    dialog.setContentText("No row was selected! Please select one.");

                    //Adding buttons to the dialog pane

                    dialog.getDialogPane().getButtonTypes().add(type);

                    dialog.showAndWait();

                   }

               }

               else

               {

                   logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in TECHNICIANS TAB");

                    //Creating a dialog

                    Dialog<String> dialog = new Dialog<String>();

                    //Setting the title

                   Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                    //Setting the content of the dialog

                    dialog.setContentText("No row was selected! Please select one.");

                    //Adding buttons to the dialog pane

                    dialog.getDialogPane().getButtonTypes().add(type);

                    dialog.showAndWait();

               }

              

                

        }

       

     }

       public void Delete()

    {

     logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE TECHNICIAN button was pressed in TECHNICIANS TAB \n");

      boolean tableselect=table_shift_team.getSelectionModel().isSelected(index);

     

      if(tableselect == true)

      {

          if(deleteTech==null)

          {

                        try {

                     deleteTech=new Stage();

                     Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deleteshiftteam.fxml"));

                     Scene scene = new Scene(root);

 

                     deleteTech.setTitle("Delete Shift Team");

                     deleteTech.setScene(scene);

                     deleteTech.initStyle(StageStyle.UTILITY);

                     deleteTech.setOnHidden(e->{

                        search_team();

                     });

                     deleteTech.setResizable(false);

                     deleteTech.show();

                    

 

                 } catch (IOException ex) {

                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                 }

          }

          else if(deleteTech.isShowing())

          {

             Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Delete Shift Team' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

          }

          else

          {

              try {

                     deleteTech=new Stage();

                     Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deleteshiftteam.fxml"));

                     Scene scene = new Scene(root);

 

                     deleteTech.setTitle("Delete Shift Team");

                     deleteTech.setScene(scene);

                     deleteTech.initStyle(StageStyle.UTILITY);

                     deleteTech.setOnHidden(e->{

                        search_team();

                     });

                     deleteTech.setResizable(false);

                     deleteTech.show();

                    

 

                 } catch (IOException ex) {

                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                 }

             

          }

      

        }

        else

        {

                if(deleteTech!=null)

                {

                    if(deleteTech.isShowing()==true)

                    {

                        Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Delete Shift Team' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                    }

                }

                else

                {

                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in TECHNICIANS TAB");

                    //Creating a dialog

                    Dialog<String> dialog = new Dialog<String>();

                    //Setting the title

                    Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                    //Setting the content of the dialog

                    dialog.setContentText("No row was selected! Please select one.");

                    //Adding buttons to the dialog pane

                    dialog.getDialogPane().getButtonTypes().add(type);

                    dialog.showAndWait();

                }

               

                

        }

       

        

    }
    //--------------------------------------------------------------------------------ENGINEERS------------------------------------------------------------------------------------
       @FXML
    private Button add_btn_escalation_team;

    @FXML
    private Button edit_btn_escalation_team;

    @FXML
    private Button delete_btn_escalation_team;
    
    @FXML
    private ImageView addiconesc;

    @FXML
    private ImageView editiconesc;

    @FXML
    private ImageView deleteiconesc;
    
     @FXML
    private TableView<EscalationTeam> table_escalation_team;

    @FXML
    private TableColumn<EscalationTeam, String> col_name_escalation_team;

    @FXML
    private TableColumn<EscalationTeam, String> col_job_escalation_team;
    
    
    @FXML
    private TableColumn<EscalationTeam, String> col_user_escalation_team;

    @FXML
    private TableColumn<EscalationTeam, String> col_email_escalation_team;
    ObservableList<EscalationTeam> dataListEscTeam;
    @FXML
    private Tab tabEscalation;
    
    @FXML
    void view_escalation_team() 
    {
        if(tabEscalation.isSelected()==true)
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Loading ESCALATION TEAM in table in ENGINEERS TAB");
        
            dataListEscTeam=ConnectionDatabase.getEscTeam();
            if(dataListEscTeam.isEmpty()==false)
            {
                col_name_escalation_team.setCellValueFactory(new PropertyValueFactory<EscalationTeam,String>("name"));
                col_job_escalation_team.setCellValueFactory(new PropertyValueFactory<EscalationTeam,String>("job"));
                col_user_escalation_team.setCellValueFactory(new PropertyValueFactory<EscalationTeam,String>("user"));
                col_email_escalation_team.setCellValueFactory(new PropertyValueFactory<EscalationTeam,String>("email"));
                Comparator<EscalationTeam> comparator = Comparator.comparing(EscalationTeam::getName); 
                comparator.reversed();
                FXCollections.sort(dataListEscTeam, comparator);
                table_escalation_team.setItems(dataListEscTeam);
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The DATA has been loaded in table in ENGINEERS TAB \n");
            }
            else
            {
                table_escalation_team.getItems().clear();
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list of engineers is empty in ENGINEERS TAB \n");
            }
        }
        else if(tabEscalation.isSelected()==false)
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The ENGINEERS TAB is released!");
        }
        
        
        
        
    }
     public void getSelectedEscTeam()
    {
        
        index=table_escalation_team.getSelectionModel().getSelectedIndex();
        if(index <= -1)
        {
            return;
        }
        EscalationTeam escteam=table_escalation_team.getSelectionModel().getSelectedItem();
        if(escteam!=null)
        {
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Details about the selected engineer in ENGINEERS TAB: \n");
                Data.engineer_name=escteam.getName();
                Data.engineer_job_desc=escteam.getJob();
                Data.engineer_user=escteam.getUser();
                Data.engineer_email=escteam.getEmail();
                Data.id_esc_team=escteam.getId();
                logger.info("ID: "+Data.id_esc_team+"\n"+
                            "Name: "+Data.engineer_name+"\n"+
                            "Job description: "+Data.engineer_job_desc+"\n"+
                            "Username: "+Data.engineer_user+"\n"+
                            "Email: "+Data.engineer_email+"\n");
        }
        else
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The escalation team object is null! \n");
        }
        
        
    }
     public void addEnginner()

     {

        logger.info(Log4jConfiguration.currentTime()+"[INFO]: ADD ESCALATION TEAM button was PRESSED in ENGINEERS TAB! ");

        boolean tableselect=table_escalation_team.getSelectionModel().isSelected(index);

        if(tableselect == true)

        {

            if(addEngineer!=null)

            {

                    if(addEngineer.isShowing()==true)

                    {

                        Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Add Escalation Team' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                    }

                    else

                    {

                        Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                           Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("Can't add on a row! Unselect the row from the table and press Add button!");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                    }

            }

            else

            {

                Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("Can't add on a row! Unselect the row from the table and press Add button!");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

            }

        }

        //nu e selectat

        else

        {

            if(addEngineer==null)

           {

                        try {

 

                        addEngineer=new Stage();

                        Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addescalationteam.fxml"));

                        Scene scene = new Scene(root);

 

                        addEngineer.setTitle("Add Escalation Team");

                        addEngineer.initStyle(StageStyle.UTILITY);

                        addEngineer.setScene(scene);

                        addEngineer.setOnHidden(e->{

                                    view_escalation_team();

 

                        });

                        addEngineer.setResizable(false);

                        addEngineer.show();

 

                    } catch (IOException ex) {

                        logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                    }

           }

            else if(addEngineer.isShowing())

            {

                Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Add Escalation Team' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

            }

            else

            {

                try {

 

                        addEngineer=new Stage();

                        Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addescalationteam.fxml"));

                        Scene scene = new Scene(root);

 

                        addEngineer.setTitle("Add Escalation Team");

                        addEngineer.initStyle(StageStyle.UTILITY);

                        addEngineer.setScene(scene);

                        addEngineer.setOnHidden(e->{

                                    view_escalation_team();

 

                        });

                        addEngineer.setResizable(false);

                        addEngineer.show();

 

                    } catch (IOException ex) {

                        logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                    }

            }

        }

       

     }

      public void editEngineer()

     {

        logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT ESCALATION button was pressed in ENGINEERS TAB! \n");

        boolean tableselect=table_escalation_team.getSelectionModel().isSelected(index);

        if(tableselect == true)

        {

            if(editEngineer==null)

            {

                            try {

 

                       editEngineer=new Stage();

                       Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editescalationteam.fxml"));

                       Scene scene = new Scene(root);

 

                       editEngineer.setTitle("Edit Escalation Team");

                       editEngineer.initStyle(StageStyle.UTILITY);

                       editEngineer.setScene(scene);

                       editEngineer.setOnHidden(e->{

                                   view_escalation_team();

 

                       });

                       editEngineer.setResizable(false);

                       editEngineer.show();

 

 

                   } catch (IOException ex) {

                       logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                   }

            }

            else if(editEngineer.isShowing())

            {

                Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Edit Escalation Team' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

            }

            else

            {

                try {

 

                       editEngineer=new Stage();

                       Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editescalationteam.fxml"));

                       Scene scene = new Scene(root);

 

                       editEngineer.setTitle("Edit Escalation Team");

                       editEngineer.initStyle(StageStyle.UTILITY);

                       editEngineer.setScene(scene);

                       editEngineer.setOnHidden(e->{

                                   view_escalation_team();

 

                       });

                       editEngineer.setResizable(false);

                       editEngineer.show();

 

 

                   } catch (IOException ex) {

                       logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                   }

            }

        

         }

        else

        {

                if(editEngineer!=null)

                {

                    if(editEngineer.isShowing()==true)

                    {

                        Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Edit Escalation Team' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                    }

                    else

                    {

                        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in ENGINEERS TAB");

                        //Creating a dialog

                        Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("No row was selected! Please select one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                    }

               }

                else

                {

                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in ENGINEERS TAB");

                    //Creating a dialog

                    Dialog<String> dialog = new Dialog<String>();

                    //Setting the title

                    Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                    //Setting the content of the dialog

                   dialog.setContentText("No row was selected! Please select one.");

                    //Adding buttons to the dialog pane

                    dialog.getDialogPane().getButtonTypes().add(type);

                    dialog.showAndWait();

                }

               

                

        }

       

         

     }

     public void deleteEngineer()

     {

        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE ESCALATION TEAM button was PRESSED in ENGINEERS TAB\n");

        boolean tableselect=table_escalation_team.getSelectionModel().isSelected(index);

        if(tableselect == true)

        {

            if(deleteEngineer==null)

            {

                            try {

 

                       deleteEngineer=new Stage();

                       Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deleteescalationteam.fxml"));

                       Scene scene = new Scene(root);

 

                       deleteEngineer.setTitle("Delete Escalation Team");

                       deleteEngineer.initStyle(StageStyle.UTILITY);

                       deleteEngineer.setScene(scene);

                       deleteEngineer.setOnHidden(e->{

                                   view_escalation_team();

 

                       });

                       deleteEngineer.setResizable(false);

                       deleteEngineer.show();

 

 

                   } catch (IOException ex) {

                       logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                   }

            }

            else if(deleteEngineer.isShowing())

            {

                            Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Delete Escalation Team' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

            }

            else

            {

                try {

 

                       deleteEngineer=new Stage();

                       Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deleteescalationteam.fxml"));

                       Scene scene = new Scene(root);

 

                       deleteEngineer.setTitle("Delete Escalation Team");

                       deleteEngineer.initStyle(StageStyle.UTILITY);

                       deleteEngineer.setScene(scene);

                       deleteEngineer.setOnHidden(e->{

                                   view_escalation_team();

 

                       });

                       deleteEngineer.setResizable(false);

                       deleteEngineer.show();

 

 

                   } catch (IOException ex) {

                       logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                   }

            }

        

         }

        else

        {

               if(deleteEngineer!=null)

               {

                   if(deleteEngineer.isShowing()==true)

                   {

                            Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Delete Escalation Team' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                   }

                   else

                   {

                        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in ENGINEERS TAB");

                        //Creating a dialog

                        Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("No row was selected! Please select one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                   }

               }

               else

               {

                   logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in ENGINEERS TAB");

                //Creating a dialog

                Dialog<String> dialog = new Dialog<String>();

                //Setting the title

                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                //Setting the content of the dialog

                dialog.setContentText("No row was selected! Please select one.");

                //Adding buttons to the dialog pane

                dialog.getDialogPane().getButtonTypes().add(type);

                dialog.showAndWait();

               }

               

                

        }

       

         

     }
    
    
    //--------------------------------------------------------------------------------SPARE PARTS----------------------------------------------------------------------------------
    
   @FXML
    private Button addbtnspare;

    @FXML
    private Button editbtnspare;

    @FXML
    private Button deletebtnspare;

    @FXML
    private Button searchbtnspare;

    @FXML
    private ImageView addiconspare;

    @FXML
    private ImageView editiconspare;

    @FXML
    private ImageView deleteiconspare;

    @FXML
    private ImageView searchiconspare;
    
       @FXML
    private TableView<SpParts> table_spare_parts;

    
    @FXML
    private TableColumn<SpParts, String> col_name_spare;

    @FXML
    private TableColumn<SpParts, String> col_spare_id;
    
    @FXML
    private TableColumn<SpParts, String> col_des_spare;

    @FXML
    private TableColumn<SpParts, String> col_det_spare;
     @FXML
    private Button loadbtn;
    @FXML
    private TextField searchfield_spare_parts;
    ObservableList<SpParts> dataListSpParts;
    
    
    
    
   
    
    public void search_key_words_spare_parts()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: SEARCH BUTTON WAS PRESSED in SPARE PARTS TAB \n");
        if(searchfield_spare_parts.getText().isEmpty())
        {
               logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The key word field is empty \n");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                dialog.setTitle("Error message");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("The key word field is empty!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
        }
        else
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Loading FILTERED data in table in SPARE PARTS TAB \n");
            String txt=searchfield_spare_parts.getText();
            col_name_spare.setCellValueFactory(new PropertyValueFactory<SpParts,String>("part_number"));
            col_spare_id.setCellValueFactory(new PropertyValueFactory<SpParts,String>("spare_id"));
            col_des_spare.setCellValueFactory(new PropertyValueFactory<SpParts,String>("spare_desc"));
            col_det_spare.setCellValueFactory(new PropertyValueFactory<SpParts,String>("spare_details"));
            dataListSpParts=ConnectionDatabase.getFilteredSpareParts(txt);
            Comparator<SpParts> comparator = Comparator.comparing(SpParts::getPart_number); 
            comparator.reversed();
            FXCollections.sort(dataListSpParts, comparator);
            table_spare_parts.setItems(dataListSpParts);
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The DATA is loaded in SPARE PARTS TAB \n");
        }
    }
    
    public void clear()
    {
       /* if(!table_spare_parts.getSelectionModel().getSelectedItems().isEmpty())
        {
             table_spare_parts.getSelectionModel().getSelectedItems().clear();
        }*/
       
    }
    
    public void view_spare_parts()
    {
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Loading spare parts in table in SPARE PARTS TAB \n");
        dataListSpParts=ConnectionDatabase.getSpareParts();
        if(dataListSpParts.isEmpty()==false)
        {
            
            col_name_spare.setCellValueFactory(new PropertyValueFactory<SpParts,String>("part_number"));
            col_spare_id.setCellValueFactory(new PropertyValueFactory<SpParts,String>("spare_id"));
            col_des_spare.setCellValueFactory(new PropertyValueFactory<SpParts,String>("spare_desc"));
            col_det_spare.setCellValueFactory(new PropertyValueFactory<SpParts,String>("spare_details"));

            Comparator<SpParts> comparator = Comparator.comparing(SpParts::getPart_number); 
                comparator.reversed();
                FXCollections.sort(dataListSpParts, comparator);
            table_spare_parts.setItems(dataListSpParts);
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data has been loaded in table in SPARE PARTS TAB \n");
        }
        else
        {
            table_spare_parts.getItems().clear();
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list from spare parts is empty in SPARE PARTS TAB \n");
        }
        
     
        
        
    }
    
    public void onSparePartsTab()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Clearing selection in SPARE PARTS TAB");
        
        table_spare_parts.getItems().clear();
        searchfield_spare_parts.setText("");
        
        
    }
     public void getSelectedSpareParts()
    {
        index=table_spare_parts.getSelectionModel().getSelectedIndex();
        if(index <= -1)
        {
            return;
        }
        SpParts spare=table_spare_parts.getSelectionModel().getSelectedItem();
        if(spare!=null)
        {
            Data.id_spare_parts=spare.getId();
            Data.part_number=spare.getPart_number();
            Data.desc_spare_parts=spare.getSpare_desc();
            Data.det_spare_parts=spare.getSpare_details();
            Data.spare_id=spare.getSpare_id();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The selected spare part from TABLE in SPARE PARTS TAB is: \n");
            logger.info("ID: "+Data.id_spare_parts+"\n"+
                    "Part Number: "+Data.part_number+"\n"+
                    "Spare Part's Description: "+Data.desc_spare_parts+"\n"+
                    "Spare Part's Details: "+Data.det_spare_parts+"\n"+
                    "Spare Part's SPARE ID: "+Data.spare_id+"\n");
            
        }
        else
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The spare part object is NULL");
        }
        
        

   }
   
   public void AddSpParts()
   {
       
         boolean tableselect=table_spare_parts.getSelectionModel().isSelected(index);
         if(tableselect==true)

         {

            if(addSpare!=null)

            {

                    if(addSpare.isShowing()==true)

                    {

                        Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Add Spare Parts' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                    }

                    else

                    {

                        Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("Can't add on a row! Unselect the row from the table and press Add button!");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                    }

            }

            else
            {

                Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("Can't add on a row! Unselect the row from the table and press Add button!");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

            }

          

        }

         //nu e selectat

            else

            {

                if(addSpare==null)

                {

                         try {

                               addSpare=new Stage();
                               Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addspareparts.fxml"));
                               Scene scene = new Scene(root);

                               addSpare.setTitle("Add Spare Parts");
                               addSpare.setScene(scene);
                               addSpare.initStyle(StageStyle.UTILITY);

                               addSpare.setOnHidden(e->{
                                   view_spare_parts();
                               });
                               addSpare.setResizable(false);
                               addSpare.show();


                           } catch (IOException ex) {
                               logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);
                           }

                }

                else if(addSpare.isShowing()==true)

                {

                    // System.out.println("something in the background");

                        Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("A panel 'Add Spare Parts' with unsaved changes is already open in the background! "

                                + "Close it if"

                                + " you want to ignore it and open a new one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                }

                else

                {
                             try {

                               addSpare=new Stage();
                               Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addspareparts.fxml"));
                               Scene scene = new Scene(root);

                               addSpare.setTitle("Add Spare Parts");
                               addSpare.setScene(scene);
                               addSpare.initStyle(StageStyle.UTILITY);

                               addSpare.setOnHidden(e->{
                                   view_spare_parts();
                               });
                               addSpare.setResizable(false);
                               addSpare.show();


                           } catch (IOException ex) {
                               logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);
                           }
                }

            }

          

            
                 /*
                 if(tableselect == true)
                 {
                     try {

                        Stage secondStage=new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("/addfxml/messageadd.fxml"));
                        Scene scene = new Scene(root);

                        secondStage.setTitle("Message");
                        secondStage.initStyle(StageStyle.UTILITY);
                        secondStage.setScene(scene);
                        secondStage.setOnHidden(e->{
                                     view_spare_parts();
                        });
                        secondStage.setResizable(false);
                        secondStage.show();
                        
                    } catch (IOException ex) {
                        logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);
                    }
                 }
                 else{
                     if(addSpare==null)
                     {
                               try {

                               addSpare=new Stage();
                               Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addspareparts.fxml"));
                               Scene scene = new Scene(root);

                               addSpare.setTitle("New");
                               addSpare.setScene(scene);
                               addSpare.initStyle(StageStyle.UTILITY);

                               addSpare.setOnHidden(e->{
                                   view_spare_parts();
                               });
                               addSpare.setResizable(false);
                               addSpare.show();


                           } catch (IOException ex) {
                               logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);
                           }
                     }
                     else if(addSpare.isShowing())
                     {
                         addSpare.toFront();
                     }
                     else
                     {
                         try {

                               addSpare=new Stage();
                               Parent root = FXMLLoader.load(getClass().getResource("/addfxml/addspareparts.fxml"));
                               Scene scene = new Scene(root);

                               addSpare.setTitle("New");
                               addSpare.setScene(scene);
                               addSpare.initStyle(StageStyle.UTILITY);

                               addSpare.setOnHidden(e->{
                                   view_spare_parts();
                               });
                               addSpare.setResizable(false);
                               addSpare.show();


                           } catch (IOException ex) {
                               logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);
                           }
                     }
                     
                 }

       
       
       
      */
         
      
       
   }
   public void EditSpParts()
   {
       if(table_spare_parts.getItems().isEmpty()==false)
       {
           boolean tableselect=table_spare_parts.getSelectionModel().isSelected(index);
        if(tableselect == true)
        {
            if(editSpare==null)
            {
                try {
                    editSpare=new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editspareparts.fxml"));
                    Scene scene = new Scene(root);

                    editSpare.setTitle("Edit Spare Parts");
                    editSpare.setScene(scene);
                    editSpare.initStyle(StageStyle.UTILITY);
                    editSpare.setOnHidden(e->{
                        view_spare_parts();
                    });
                    editSpare.setResizable(false);
                    editSpare.show();
                    

                } catch (IOException ex) {
                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);
                }
            }
            else if(editSpare.isShowing())
            {
                Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                           Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Edit Spare Parts' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();
            }
            else
            {
                try {
                    editSpare=new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editspareparts.fxml"));
                    Scene scene = new Scene(root);

                    editSpare.setTitle("Edit Spare Parts");
                    editSpare.setScene(scene);
                    editSpare.initStyle(StageStyle.UTILITY);
                    editSpare.setOnHidden(e->{
                        view_spare_parts();
                    });
                    editSpare.setResizable(false);
                    editSpare.show();
                    

                } catch (IOException ex) {
                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);
                }
            }
        
         }
        else
        {
                if(editSpare!=null)

                     {

                         if(editSpare.isShowing()==true)

                         {

                             Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Edit Spare Parts' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                         }

                         else

                         {

                             logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in SPARE PARTS TAB");

                            //Creating a dialog

                            Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("No row was selected! Please select one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

 

                         }

                     }

                     else

                     {

                         logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in SPARE PARTS TAB");

                        //Creating a dialog

                        Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("No row was selected! Please select one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

 

                     }

                    
                
        }
        
       }
       else
       {
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
               Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("The table is empty! Please choose a filter!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
       }
        
        
   }
   public void DeleteSpParts()

   {

       logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE SPARE PART BUTTON was PRESSED! \n");

       if(table_spare_parts.getItems().isEmpty()==false)

       {

            boolean tableselect=table_spare_parts.getSelectionModel().isSelected(index);

            if(tableselect == true)

            {

                if(deleteSpare==null)

                {

                     try {

                         deleteSpare=new Stage();

                         Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deletespareparts.fxml"));

                         Scene scene = new Scene(root);

 

                         deleteSpare.setTitle("Delete Spare Parts");

                         deleteSpare.setScene(scene);

                         deleteSpare.initStyle(StageStyle.UTILITY);

                         deleteSpare.setOnHidden(e->{

                             view_spare_parts();

                         });

                         deleteSpare.setResizable(false);

                         deleteSpare.show();

 

 

                     } catch (IOException ex) {

                         logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                     }

                }

                else if(deleteSpare.isShowing())

                {

                            Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Delete Spare Parts' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                }

                else

                {

                    try {

                         deleteSpare=new Stage();

                         Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deletespareparts.fxml"));

                         Scene scene = new Scene(root);

 

                         deleteSpare.setTitle("Delete Spare Parts");

                         deleteSpare.setScene(scene);

                         deleteSpare.initStyle(StageStyle.UTILITY);

                         deleteSpare.setOnHidden(e->{

                             view_spare_parts();

                         });

                         deleteSpare.setResizable(false);

                         deleteSpare.show();

 

 

                     } catch (IOException ex) {

                         logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                     }

                }

               

            

              }

             else

             {

                     if(deleteSpare!=null)

                     {

                         if(deleteSpare.isShowing()==true)

                         {

                             Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Delete Spare Parts' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                         }

                         else

                         {

                             logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in SPARE PARTS TAB");

                            //Creating a dialog

                            Dialog<String> dialog = new Dialog<String>();

                            //Setting the title
                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("No row was selected! Please select one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

 

                         }

                     }

                     else

                     {

                         logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from table in SPARE PARTS TAB");

                        //Creating a dialog

                        Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("No row was selected! Please select one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

 

                     }

                    

             }

 

       }

       else

       {

                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: TABLE is empty in SPARE PARTS TAB! \n");

                        //Creating a dialog

                     Dialog<String> dialog = new Dialog<String>();

                     //Setting the title

                     Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                     ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                     //Setting the content of the dialog

                     dialog.setContentText("The table is empty! Please choose a filter!");

                     //Adding buttons to the dialog pane

                     dialog.getDialogPane().getButtonTypes().add(type);

                     dialog.showAndWait();

       }

      

        

   }
    //--------------------------------------------------------------------------ACCESS--------------------------------------------------------------------------------------------
    
    @FXML
    private Button editbtnacces;

    @FXML
    private Button deletebtnaccess;

    
    @FXML
    private ImageView editiconacces;

    @FXML
    private ImageView deleteiconaccess;

     @FXML
    private TableView<Access> table_access;

    @FXML
    private TableColumn<Access, String> col_user_access;

    @FXML
    private TableColumn<Access, String> col_role_access;
    ObservableList<Access> dataListAccess;
    
    @FXML
    private Tab tabAccess;
   
   public void view_acces()
   {
       if(tabAccess.isSelected()==true)
       {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Setting things for ACCESS TAB \n");
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Loading the data for ACCESS TABLE in ACCESS TAB \n");
            dataListAccess=ConnectionDatabase.getAccess();
            if(dataListAccess.isEmpty()==false)
            {
                 col_user_access.setCellValueFactory(new PropertyValueFactory<Access,String>("username"));
                 col_role_access.setCellValueFactory(new PropertyValueFactory<Access,String>("role"));

                 Comparator<Access> comparator = Comparator.comparing(Access::getUsername); 
                 comparator.reversed();
                 FXCollections.sort(dataListAccess, comparator);
                 table_access.setItems(dataListAccess);
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data has been loaded for ACCESS TABLE in ACCESS TAB \n");
            }
            else
            {
                table_access.getItems().clear();
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The list from access is empty in ACCESS TAB \n");
            }

       }
       else if(tabAccess.isSelected()==false)
       {
           logger.info(Log4jConfiguration.currentTime()+"[INFO]: The ACCESS TAB have been released!");
           table_access.getItems().clear();
       }
       
       
       
    
   }
   public void getSelectedAccess()
   {
        index=table_access.getSelectionModel().getSelectedIndex();
        if(index <= -1)
        {
            return;
        }
       Access user=table_access.getSelectionModel().getSelectedItem();
       Data.usernameaccess=user.getUsername();
       Data.roleaccess=user.getRole();
       Data.id_access=user.getId();
       
       if(Data.usernameaccess.isEmpty()==true ||  Data.roleaccess.isEmpty()==true ||   Data.id_access==0)
       
       {
           logger.warn(Log4jConfiguration.currentTime()+"[WARN]: One of the TABLE COLUMNS for the selected row is empty in ACCESS TAB \n");
       }
       else
       {
           logger.info(Log4jConfiguration.currentTime()+"[INFO]: Details about the SELECTED ROW OF TABLE in ACCESS TAB: \n");
           logger.info("ID: "+String.valueOf(Data.id_access)+"\n"+
                      "Username: "+Data.usernameaccess+"\n"+
                      "Role: "+Data.roleaccess+"\n\n");
       }
   }
public void editAccess()

   {

       logger.info(Log4jConfiguration.currentTime()+"[INFO]: EDIT ACCESS button WAS PRESSED in ACCESS TAB! \n");

       if(table_access.getItems().isEmpty()==false)

       {

           boolean tableselect=table_access.getSelectionModel().isSelected(index);

       if(tableselect == true)

       {

           if(editAccess==null)

           {

                try {

                    editAccess=new Stage();

                    Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editaccess.fxml"));

                    Scene scene = new Scene(root);

 

                    editAccess.setTitle("Edit Access");

                    editAccess.setScene(scene);

                    editAccess.initStyle(StageStyle.UTILITY);

                    editAccess.setOnHidden(e->{

                        view_acces();

                    });

                    editAccess.setResizable(false);

                    editAccess.show();

                   

 

                } catch (IOException ex) {

                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                }

           }

           else if(editAccess.isShowing())

           {

               Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                           Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Edit Access' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

           }

           else

           {

               try {

                    editAccess=new Stage();

                    Parent root = FXMLLoader.load(getClass().getResource("/editfxml/editaccess.fxml"));

                    Scene scene = new Scene(root);

                    editAccess.setTitle("Edit Access");

                    editAccess.setScene(scene);

                    editAccess.initStyle(StageStyle.UTILITY);

                    editAccess.setOnHidden(e->{

                        view_acces();

                    });

                    editAccess.setResizable(false);

                    editAccess.show();

                   

 

                } catch (IOException ex) {

                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                }

           }

     

        }

        else

        {

            if(editAccess!=null)

               {

                   if(editAccess.isShowing()==true)

                   {

                       Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Edit Access' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                   }

                   else

                   {

                        logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from TABLE in ACCESS TAB \n");

                //Creating a dialog

                Dialog<String> dialog = new Dialog<String>();

                //Setting the title

                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                //Setting the content of the dialog

                dialog.setContentText("No row was selected! Please select one.");

                //Adding buttons to the dialog pane

                dialog.getDialogPane().getButtonTypes().add(type);

                dialog.showAndWait();

                   }

               }

               else

               {

                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from TABLE in ACCESS TAB \n");

                    //Creating a dialog

                    Dialog<String> dialog = new Dialog<String>();

                    //Setting the title

                    Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                    //Setting the content of the dialog

                    dialog.setContentText("No row was selected! Please select one.");

                    //Adding buttons to the dialog pane

                    dialog.getDialogPane().getButtonTypes().add(type);

                    dialog.showAndWait();

               }

               

                

        }

       

       }

       else

       {

              

               logger.warn(Log4jConfiguration.currentTime()+"[WARN]: TABLE is empty in ACCESS TAB! \n");

                    //Creating a dialog

                    Dialog<String> dialog = new Dialog<String>();

                    //Setting the title

                    Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                    //Setting the content of the dialog

                    dialog.setContentText("The table is empty!");

                    //Adding buttons to the dialog pane

                    dialog.getDialogPane().getButtonTypes().add(type);

                    dialog.showAndWait();

              

       }

      

       

   }

   public void deleteAccess()

   {

       logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE ACCESS button WAS PRESSED in ACCESS TAB! \n");

       if(table_access.getItems().isEmpty()==false)

       {

           boolean tableselect=table_access.getSelectionModel().isSelected(index);

       if(tableselect == true)

       {

           if(deleteAccess==null)

           {

               try {

                    deleteAccess=new Stage();

                    Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deleteaccess.fxml"));

                    Scene scene = new Scene(root);

 

                    deleteAccess.setTitle("Delete Access");

                    deleteAccess.setScene(scene);

                    deleteAccess.initStyle(StageStyle.UTILITY);

                    deleteAccess.setOnHidden(e->{

                        view_acces();

                    });

                    deleteAccess.setResizable(false);

                    deleteAccess.show();

                   

 

                } catch (IOException ex) {

                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                }

           }

           else if(deleteAccess.isShowing())

           {

              Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Delete Access' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

           }

           else

           {

               try {

                    deleteAccess=new Stage();

                    Parent root = FXMLLoader.load(getClass().getResource("/deletefxml/deleteaccess.fxml"));

                    Scene scene = new Scene(root);

 

                    deleteAccess.setTitle("Delete Access");

                    deleteAccess.setScene(scene);

                    deleteAccess.initStyle(StageStyle.UTILITY);

                    deleteAccess.setOnHidden(e->{

                        view_acces();

                    });

                    deleteAccess.setResizable(false);

                    deleteAccess.show();

                   

 

                } catch (IOException ex) {

                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER -- IOException: "+ex);

                }

           }

       

        }

        else

        {

               if(deleteAccess!=null)

               {

                   if(deleteAccess.isShowing()==true)

                   {

                       Dialog<String> dialog = new Dialog<String>();

                            //Setting the title

                            Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                            //Setting the content of the dialog

                            dialog.setContentText("A panel 'Delete Access' with unsaved changes is already open in the background! "

                                    + "Close it if"

                                    + " you want to ignore it and open a new one.");

                            //Adding buttons to the dialog pane

                            dialog.getDialogPane().getButtonTypes().add(type);

                            dialog.showAndWait();

                   }

                   else

                   {

                       logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from TABLE in ACCESS TAB \n");

                        //Creating a dialog

                        Dialog<String> dialog = new Dialog<String>();

                        //Setting the title

                        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                        //Setting the content of the dialog

                        dialog.setContentText("No row was selected! Please select one.");

                        //Adding buttons to the dialog pane

                        dialog.getDialogPane().getButtonTypes().add(type);

                        dialog.showAndWait();

                   }

               }

               else

               {

                   logger.warn(Log4jConfiguration.currentTime()+"[WARN]: No row was selected from TABLE in ACCESS TAB \n");

                    //Creating a dialog

                    Dialog<String> dialog = new Dialog<String>();

                    //Setting the title

                   Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                    //Setting the content of the dialog

                    dialog.setContentText("No row was selected! Please select one.");

                    //Adding buttons to the dialog pane

                    dialog.getDialogPane().getButtonTypes().add(type);

                    dialog.showAndWait();

               }

              

                

        }

       }

       else

           

       {

           logger.warn(Log4jConfiguration.currentTime()+"[WARN]: TABLE is empty in ACCESS TAB! \n");

           //Creating a dialog

                Dialog<String> dialog = new Dialog<String>();

                //Setting the title

                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                //Setting the content of the dialog

                dialog.setContentText("The table is empty!");

                //Adding buttons to the dialog pane

                dialog.getDialogPane().getButtonTypes().add(type);

                dialog.showAndWait();

       }

      

       

        

   }
     @FXML
    private ImageView pbh_editimage;

    @FXML
    private ImageView pbhistory_deleteicon;
    
    @FXML
    private ImageView editiconincident;

    @FXML
    private ImageView deleteiconincident;
    
    @FXML
    private Tab admintab;
    
    
    @FXML
    private Tab esctab;
    
   
     
    
   
    
    //-----------------------------------------------------------------------------ESCALATION-----------------------------------------------------------------------------
    @FXML
    private TextField ticket_incident;

    @FXML
    private TextField if_escalation;

    @FXML
    private TextField line_escalation;

    @FXML
    private TextField machine_escalation;

    @FXML
    private TextField start_date_escalation;

    @FXML
    private TextField end_date_escalation;

    @FXML
    private TextField status_escalation;

    @FXML
    private TextField creator_user_escalation;

    @FXML
    private TextField saporder_escalation;

    @FXML
    private TextField escalation_user;

    @FXML
    private TextArea pbdesc_escalation;

    @FXML
    private TextField hmi_pbescalation;

    @FXML
    private TextArea part_number_escalation;

    @FXML
    private TextArea sol_creator_escalation;

    @FXML
    private TextArea sol_escalation;

    @FXML
    private Button savebtn_escalation;

    @FXML
    private ImageView saveicon_escalation;
    
     @FXML
    private Text if_text;
     
     @FXML
    private Text line_text;
     
     @FXML
    private Text machine_text;
     
     @FXML
    private Text sd_text;
     
     
     @FXML
    private Text ed_text;
     
      @FXML
    private Text status_text;
      
      @FXML
    private Text creator_user_text;
      
      @FXML
    private Text sap_text;
      
      @FXML
    private Text escal_user_text;
      
      @FXML
    private Text pbdesc_text;
      
       @FXML
    private Text hmi_text;
       
       @FXML
    private Text part_numbere_text;
       
       @FXML
    private Text sol_creator_text;
       
       @FXML
    private Text sol_escal_text;
       
       @FXML
    private Button show_btn;
       @FXML
    private Button cancelbtn_escalation;
       @FXML
    private ImageView cancelicon_escalation;
       
    public void onEscalation()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: THE ESCALATION TAB WAS PRESSED/ANOTHER TAB WAS PRESSED!");
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Hide unnecessary fields in ESCALATION TAB...");
        
        
        savebtn_escalation.setVisible(false);
        saveicon_escalation.setVisible(false);
        cancelbtn_escalation.setVisible(false);
        cancelicon_escalation.setVisible(false);
        if_escalation.setVisible(false);
        line_escalation.setVisible(false);
        machine_escalation.setVisible(false);
        start_date_escalation.setVisible(false);
        end_date_escalation.setVisible(false);
        status_escalation.setVisible(false);
        creator_user_escalation.setVisible(false);
        saporder_escalation.setVisible(false);
        escalation_user.setVisible(false);
        pbdesc_escalation.setVisible(false);
        hmi_pbescalation.setVisible(false);
        part_number_escalation.setVisible(false);
        sol_creator_escalation.setVisible(false);
        sol_escalation.setVisible(false);
        if_text.setVisible(false);
        line_text.setVisible(false);  
        machine_text.setVisible(false);
        sd_text.setVisible(false);
        ed_text.setVisible(false);
        status_text.setVisible(false);
        creator_user_text.setVisible(false);
        sap_text.setVisible(false);
        escal_user_text.setVisible(false);
        pbdesc_text.setVisible(false);
        hmi_text.setVisible(false);
        part_numbere_text.setVisible(false);
        sol_creator_text.setVisible(false);
        sol_escal_text.setVisible(false);
        
         logger.info(Log4jConfiguration.currentTime()+"[INFO]: Show TICKET field and SHOW INCIDENT DETAILS button...");
        
        show_btn.setVisible(true);
        ticket_incident.setVisible(true);
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Clear the TICKET field...");
        ticket_incident.clear();
        
        
     
    }
    public void onCancel()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: CANCEL BUTTON WAS PRESSED IN ESCALATION TAB \n");
        logger.info(Directory);
        ticket_incident.setVisible(true);
        savebtn_escalation.setVisible(false);
        saveicon_escalation.setVisible(false);
        
        cancelbtn_escalation.setVisible(false);
        cancelicon_escalation.setVisible(false);
        
       if_escalation.setVisible(false);

       line_escalation.setVisible(false);

       machine_escalation.setVisible(false);

     start_date_escalation.setVisible(false);

    end_date_escalation.setVisible(false);

     status_escalation.setVisible(false);

     creator_user_escalation.setVisible(false);

     saporder_escalation.setVisible(false);

     escalation_user.setVisible(false);

     pbdesc_escalation.setVisible(false);

     hmi_pbescalation.setVisible(false);

     part_number_escalation.setVisible(false);

     sol_creator_escalation.setVisible(false);

     sol_escalation.setVisible(false);
     if_text.setVisible(false);
     
     line_text.setVisible(false);  
     machine_text.setVisible(false);
     sd_text.setVisible(false);
     
     ed_text.setVisible(false);
     
     
     status_text.setVisible(false);
     
     
     creator_user_text.setVisible(false);
     
     
     sap_text.setVisible(false);
     
     
     escal_user_text.setVisible(false);
     
     
     pbdesc_text.setVisible(false);
     
     
     hmi_text.setVisible(false);
     
     
     part_numbere_text.setVisible(false);
     
     
     sol_creator_text.setVisible(false);
     
     
     sol_escal_text.setVisible(false);
     
      show_btn.setVisible(true);
      ticket_incident.clear();
    }
       
    public void onShowIncidentDetailsEscal()
    {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: SHOW INCIDENT DETAILS WAS PRESSED IN ESCALATION TAB \n");
            String inc_ticket=ticket_incident.getText();
            String regex="^#INC_[0-9]{13}";
            if(inc_ticket.isEmpty()==true)
            {
                   logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The TICKET FIELD IS EMPTY in ESCALATION TAB \n");
                   //Creating a dialog
                   Dialog<String> dialog = new Dialog<String>();
                   //Setting the title
                   Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                   ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                   //Setting the content of the dialog
                   dialog.setContentText("Please complete the field!");
                   //Adding buttons to the dialog pane
                   dialog.getDialogPane().getButtonTypes().add(type);
                   dialog.showAndWait();
            }
            else if(inc_ticket.isEmpty()==false && inc_ticket.matches(regex)==true) 
            {
                
                IncidentEscalation iesc=ConnectionDatabase.getIncidentDetailsFromTicket(inc_ticket);
                
                if(iesc!=null)
                {
                
                //eliminam butonul si text field -ul de incident_ticket
                show_btn.setVisible(false);
                ticket_incident.setVisible(false);
                
                
                //afisam datele
                savebtn_escalation.setVisible(true);
                cancelicon_escalation.setVisible(true);
                cancelbtn_escalation.setVisible(true);
                cancelbtn_escalation.setGraphic(cancelicon_escalation);
                
                saveicon_escalation.setVisible(true);
                savebtn_escalation.setGraphic(saveicon_escalation);

                if_escalation.setVisible(true);

                line_escalation.setVisible(true);

                machine_escalation.setVisible(true);

                start_date_escalation.setVisible(true);

                end_date_escalation.setVisible(true);

                status_escalation.setVisible(true);

                creator_user_escalation.setVisible(true);

                saporder_escalation.setVisible(true);

                escalation_user.setVisible(true);

                pbdesc_escalation.setVisible(true);

                hmi_pbescalation.setVisible(true);

                part_number_escalation.setVisible(true);

                sol_creator_escalation.setVisible(true);
                sol_escalation.setVisible(true);
                if_text.setVisible(true);
                line_text.setVisible(true);
                machine_text.setVisible(true);
                sd_text.setVisible(true);
                ed_text.setVisible(true);


                status_text.setVisible(true);

                creator_user_text.setVisible(true);

                sap_text.setVisible(true);

                escal_user_text.setVisible(true);

                pbdesc_text.setVisible(true);

                hmi_text.setVisible(true);

                part_numbere_text.setVisible(true);

                sol_creator_text.setVisible(true);

                sol_escal_text.setVisible(true);
                
                
                //set opacity
                if_escalation.setStyle("-fx-opacity: 1");

                line_escalation.setStyle("-fx-opacity: 1");

                machine_escalation.setStyle("-fx-opacity: 1");

                start_date_escalation.setStyle("-fx-opacity: 1");

                end_date_escalation.setStyle("-fx-opacity: 1");

                status_escalation.setStyle("-fx-opacity: 1");

                creator_user_escalation.setStyle("-fx-opacity: 1");

                saporder_escalation.setStyle("-fx-opacity: 1");

                escalation_user.setStyle("-fx-opacity: 1");
                
                
                hmi_pbescalation.setStyle("-fx-opacity: 1");

                
                
                
                
                
                if_escalation.setText(iesc.getIF());
                line_escalation.setText(iesc.getLine());
                machine_escalation.setText(iesc.getMachine());
                
                long milisd=Long.valueOf(iesc.getStart_date());
                java.util.Date start_date = new java.util.Date(milisd);
                DateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
                String sd=sdf1.format(start_date);
                
                long milied=Long.valueOf(iesc.getEnd_date());
                java.util.Date end_date=new java.util.Date(milied);
                String ed=sdf1.format(end_date);
                
                start_date_escalation.setText(sd);
                end_date_escalation.setText(ed);
                
                status_escalation.setText(iesc.getStatus());
                creator_user_escalation.setText(iesc.getCreator_user());
                int sap=Integer.parseInt(iesc.getSap_order());
                if(sap!=0)
                {
                    saporder_escalation.setText(iesc.getSap_order());
                }
                else
                {
                    saporder_escalation.setText("no SAP Order");
                }
                
                
                escalation_user.setText(iesc.getEscalation_user());
                hmi_pbescalation.setText(iesc.getHmi());
                sol_creator_escalation.setText(iesc.getSol_creator());
                pbdesc_escalation.setText(iesc.getPbdesc());
                
                String idspare_parts=iesc.getIdSpareParts();
                if(!idspare_parts.equals(""))
               {
            
                    if(idspare_parts.length()==1)
                    {
                        String pn=ConnectionDatabase.getPartNumber(Integer.parseInt(idspare_parts));
                        part_number_escalation.setText(pn);
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
                        part_number_escalation.setText(part_numbere);
                    }
             }
             else
             {
                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]:No IDs for SPARE PARTS were found in ESCALATION TAB!\n");
             }
             
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The input TICKET is correct and here are the details about the incident in ESCALATION TAB: \n");
            logger.info("IF: "+if_escalation.getText()+"\n"+
                    "Line: "+line_escalation.getText()+"\n"+
                    "Machine: "+machine_escalation.getText()+"\n"+
                    "Start date: "+start_date_escalation.getText()+"\n"+
                    "End date: "+end_date_escalation.getText()+"\n"+
                    "Status: "+status_escalation.getText()+"\n"+
                    "Creator username: "+creator_user_escalation.getText()+"\n"+
                    "SAP Order: "+saporder_escalation.getText()+"\n"+
                    "Escalation user: "+escalation_user.getText()+"\n"+
                    "HMI Error: "+hmi_pbescalation.getText()+"\n"+
                    "Problem description: "+pbdesc_escalation.getText()+"\n"+
                    "Part Numbers: "+part_number_escalation.getText()+"\n"+
                    "Solution Creator: "+sol_creator_escalation.getText()+"\n"+
                    "Solution Escalation: "+sol_escalation.getText()+"\n\n");
                
                }
                else
                {
                    logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The INPUT FROM TICKET FIELD DOESN'T MATCH WITH AN INCIDENT TICKET in FROM DB \n");
                   //Creating a dialog
                   Dialog<String> dialog = new Dialog<String>();
                   //Setting the title
                   Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                   ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                   //Setting the content of the dialog
                   dialog.setContentText("The ticket doesn't match with an existing incident!");
                   //Adding buttons to the dialog pane
                   dialog.getDialogPane().getButtonTypes().add(type);
                   dialog.showAndWait();
                }
                
                
                
            }
            else if(inc_ticket.isEmpty()==false && inc_ticket.matches(regex)==false)
            {
                   logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The INPUT FROM TICKET FIELD DOESN'T MATCH WITH AN INCIDENT TICKET in ESCALATION TAB \n");
                   //Creating a dialog
                   Dialog<String> dialog = new Dialog<String>();
                   //Setting the title
                   Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                   ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                   //Setting the content of the dialog
                   dialog.setContentText("Your text doesn't match with an incident ticket!");
                   //Adding buttons to the dialog pane
                   dialog.getDialogPane().getButtonTypes().add(type);
                   dialog.showAndWait();
            }
            
     
    }
    public void saveEscalation()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: SAVE BUTTON WAS PRESSED IN ESCALATION TAB! \n");
        if(sol_escalation.getText().isEmpty()==false)
        {
            
                String inc_ticket=ticket_incident.getText();
                conn=ConnectionDatabase.getConnection();
                
                if(conn!=null)
                {
                                try{

                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure updateSolEscalation() in ESCALATION TAB");
                            CallableStatement ps = conn.prepareCall("{call updateSolEscalation(?,?)}");
                            ps.setString(1,inc_ticket);
                            ps.setString(2, sol_escalation.getText());
                            if(ps.executeUpdate()!=0)
                            {
                                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The UPDATED DETAILS about the INCIDENT were added with success in DB in ESCALATION TAB!");
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
                               ticket_incident.setVisible(true);
                                savebtn_escalation.setVisible(false);
                                saveicon_escalation.setVisible(false);

                                cancelbtn_escalation.setVisible(false);
                                cancelicon_escalation.setVisible(false);

                               if_escalation.setVisible(false);

                               line_escalation.setVisible(false);

                               machine_escalation.setVisible(false);

                             start_date_escalation.setVisible(false);

                            end_date_escalation.setVisible(false);

                             status_escalation.setVisible(false);

                             creator_user_escalation.setVisible(false);

                             saporder_escalation.setVisible(false);

                             escalation_user.setVisible(false);

                             pbdesc_escalation.setVisible(false);

                             hmi_pbescalation.setVisible(false);

                             part_number_escalation.setVisible(false);

                             sol_creator_escalation.setVisible(false);

                             sol_escalation.setVisible(false);
                             if_text.setVisible(false);

                             line_text.setVisible(false);  
                             machine_text.setVisible(false);
                             sd_text.setVisible(false);

                             ed_text.setVisible(false);


                             status_text.setVisible(false);


                             creator_user_text.setVisible(false);


                             sap_text.setVisible(false);


                             escal_user_text.setVisible(false);


                             pbdesc_text.setVisible(false);


                             hmi_text.setVisible(false);


                             part_numbere_text.setVisible(false);


                             sol_creator_text.setVisible(false);


                             sol_escal_text.setVisible(false);

                              show_btn.setVisible(true);
                              ticket_incident.clear();


                            }
                            else
                            {
                                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong while adding the UPDATED DETAILS ABOUT THE incident in ESCALATION TAB \n");
                            }

                        } catch (SQLException ex) {
                            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: ESCALATION TAB ---- SQLException: "+ex);
                        }
                }
                else
                {
                    logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the INCIDENT DETAILS FROM TICKET in ESCALATION TAB \n");
                }
                
                
        }
        else
        {
                   logger.warn(Log4jConfiguration.currentTime()+"[WARN]: THE SOLUTION ESCALATION FIELD IS EMPTY in ESCALATION TAB \n");
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
        
    }
    
      //-----------------------------------------------------------------REPORTS-----------------------------------------------------------------------------

   

 

  @FXML

    private Text textfieldChooseReport;

    @FXML

    private JFXCheckBox checkboxmtbf;

    @FXML

    private JFXCheckBox checkboxincover;

    @FXML

    private JFXCheckBox checkspareover;

    @FXML

    private JFXCheckBox checkescalover;

    @FXML

    private JFXCheckBox checkboxmttr;

    @FXML

    private DatePicker datepickerstartreports;

    @FXML

    private DatePicker datepickerendreports;

    @FXML

    private Text startdatetextfieldreports;

    @FXML

    private Text enddatetextfieldreports;

    @FXML

    private ComboBox<String> comboifreports;

    @FXML

    private ComboBox<String> combolinereports;

    @FXML

    private ComboBox<String> combomachinereports;

    @FXML

    private Text iftextreports;

    @FXML

    private Text linetextreports;

    @FXML

    private Text machinetextreports;

    @FXML

    private RadioButton firstoptiontopreports;

    @FXML

    private Text topreports;

    @FXML

    private RadioButton secondoptiontopreports;

    @FXML

    private RadioButton thirdoptiontopreports;

    @FXML

    private RadioButton alloptiontopreports;

    @FXML

    private RadioButton nooptiondetailsreports;

    @FXML

    private RadioButton yesoptiondetailsreports;

    @FXML

    private Text detailsreports;

    @FXML

    private ComboBox<String> comboescuserreports;

    @FXML

    private Text escalusertextreports;

    @FXML

    private Button button_generate_reports;

    @FXML

    private Group grouptop;

    @FXML

    private Group detbtnrad1;

    @FXML

    private ToggleGroup detbtnrad;

    @FXML

    private ToggleGroup topbtnradio;

    @FXML

    private ImageView star1;

    @FXML

    private ImageView star2;

 

    ObservableList<String> itemscomboreport = FXCollections.observableArrayList();

    ObservableList<String> itemscombolinereport = FXCollections.observableArrayList();

    ObservableList<String> itemscombomachinereport = FXCollections.observableArrayList();

    ObservableList<String> itemscomboescalreports = FXCollections.observableArrayList();

    int id_IF_report = -1;

    int id_Line_report = -1;

    int id_Machine_report = -1;

    int id_EscTeam_report = -1;

    int id_User = -1;

    String selectreport = null;

    boolean exceptie=false;

    int rowCount=0;

 

    public void setIf() {

        comboifreports.setValue("Select...");

        itemscomboreport = ConnectionDatabase.getIFCombo();

        if (!itemscomboreport.isEmpty()) {

            Collections.sort(itemscomboreport);

            itemscomboreport.add(0, "Select...");

            comboifreports.setItems(itemscomboreport);

        } else

        {

            comboifreports.getItems().clear();

            comboifreports.setValue("");

        }

    }

 

    public void onIFRaport()

    {

        boolean isMyComboBoxEmpty = comboifreports.getSelectionModel().isEmpty();

        if (isMyComboBoxEmpty == false && !comboifreports.getValue().toString().equals("Select...")) {

 

            int ifid = ConnectionDatabase.getIfId(comboifreports.getValue().toString());

            id_IF_report = ifid;

            logger.info(Log4jConfiguration.currentTime() + "[INFO]: The IF selected in REPORTS TAB: \n"

                    + comboifreports.getValue().toString() + "\n" + "IF id: " + ifid);

            if (ifid != -1)

            {

                combolinereports.setValue("Select...");

 

                itemscombolinereport = ConnectionDatabase.getLinesfromIf(ifid);

                if (!itemscombolinereport.isEmpty()) {

                    //combomachinereports.setValue("Select...");

 

                    Pattern p = Pattern.compile("\\d+$");

                    Comparator<String> c = new Comparator<String>() {

                        @Override

                        public int compare(String object1, String object2) {

                            Matcher m = p.matcher(object1);

                            Integer number1 = null;

                            if (!m.find()) {

                                return object1.compareTo(object2);

                            } else {

                                Integer number2 = null;

                                number1 = Integer.parseInt(m.group());

                                m = p.matcher(object2);

                                if (!m.find()) {

                                    return object1.compareTo(object2);

                                } else {

                                    number2 = Integer.parseInt(m.group());

                                    int comparison = number1.compareTo(number2);

                                    if (comparison != 0) {

                                        return comparison;

                                    } else {

                                        return object1.compareTo(object2);

                                    }

                                }

                            }

                        }

                    };

                    Collections.sort(itemscombolinereport, c);

                    itemscombolinereport.add(0, "Select...");

                    combolinereports.setItems(itemscombolinereport);

                    new AutoCompleteComboBoxListener<>(combolinereports);

                    String line = "OUTPUT LIST of lines from the IF selected from REPORTS: \n" +

                            "[1]: " + itemscombolinereport.get(1) + "\n";

                    for (int i = 2; i < itemscombolinereport.size(); i++) {

                        line = line + "[" + i + "]: " + itemscombolinereport.get(i) + "\n";

                    }

                    logger.info(Log4jConfiguration.currentTime() + "[INFO]: " + line);

                    logger.info("\n");

 

 

                } else {

 

                    combolinereports.getItems().clear();

                    logger.info(Log4jConfiguration.currentTime() + "[INFO]: Empty list of lines in the IF selected in REPORTS TAB!\n\n");

 

                }

            }

 

        }

        else if (comboifreports.getSelectionModel().isEmpty() == false && comboifreports.getValue().toString().equals("Select..."))

        {

            //comboline.setValue("Select");

            id_IF_report = -1;

            id_Line_report=-1;

            id_Machine_report=-1;

            combolinereports.getItems().clear();

            combomachinereports.getItems().clear();

            logger.info(Log4jConfiguration.currentTime() + "[INFO]: 'Select...' option from IF was selected and the list of lines and machines "

                    + " have been reloaded in REPORTS TAB!\n\n");

        }

    }

 

    public void onEscUser() {

 

        boolean isCombo=comboescuserreports.getSelectionModel().isEmpty();

        if(isCombo==false && !comboescuserreports.getValue().toString().equals("Select..."))

        {

            String valueesc=comboescuserreports.getValue().toString();

            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The engineer selected for escalation in TAB REPORTS: \n"

                    +valueesc);

            id_User=ConnectionDatabase.getEscId(valueesc);

            //System.out.println(id_User);

        }

        else  {

            id_User=-1;

        }

 

    }

 

 

 

    public void onLineReport() {

        boolean isMyLine = combolinereports.getSelectionModel().isEmpty();

        if (isMyLine == false && !combolinereports.getValue().toString().equals("Select...")) {

            int idline = ConnectionDatabase.getLineId(combolinereports.getValue().toString());

            id_Line_report = idline;

            if (idline != 0) {

                logger.info(Log4jConfiguration.currentTime() + "[INFO]: The line selected from REPORTS is: \n"

                        + combolinereports.getValue().toString() + "\n"

                        + "Line id: " + idline);

                itemscombomachinereport = ConnectionDatabase.getMachineCombo(idline);

                if (!itemscombomachinereport.isEmpty()) {

                    combomachinereports.setValue("Select...");

                    Pattern p = Pattern.compile("\\d+$");

                    Comparator<String> c = new Comparator<String>() {

                        @Override

                        public int compare(String object1, String object2) {

                            Matcher m = p.matcher(object1);

                            Integer number1 = null;

                            if (!m.find()) {

                                return object1.compareTo(object2);

                            } else {

                                Integer number2 = null;

                                number1 = Integer.parseInt(m.group());

                                m = p.matcher(object2);

                                if (!m.find()) {

                                    return object1.compareTo(object2);

                                } else {

                                    number2 = Integer.parseInt(m.group());

                                    int comparison = number1.compareTo(number2);

                                    if (comparison != 0) {

                                        return comparison;

                                    } else {

                                        return object1.compareTo(object2);

                                    }

                                }

                            }

                        }

                    };

                    Collections.sort(itemscombomachinereport, c);

                    itemscombomachinereport.add(0, "Select...");

                    combomachinereports.setItems(itemscombomachinereport);

                    new AutoCompleteComboBoxListener<>(combomachinereports);

                    String machine = "OUTPUT LIST of machines from the line selected from REPORTS TAB:\n" +

                            "[1]: " + itemscombomachinereport.get(1) + "\n";

                    for (int i = 2; i < itemscombomachinereport.size(); i++) {

                        machine = machine + "[" + i + "]: " + itemscombomachinereport.get(i) + "\n";

                    }

                    logger.info(Log4jConfiguration.currentTime() + "[INFO]: " + machine);

                    logger.info("\n");

                } else {

                    combomachinereports.getItems().clear();

                    logger.info(Log4jConfiguration.currentTime() + "[INFO]: Empty list of machines in the LINE selected in REPORTS TAB\n\n");

                }

            }

        } else if (combolinereports.getSelectionModel().isEmpty() == false && combolinereports.getValue().toString().equals("Select...")) {

//combomachine.setValue("Select...");

            logger.info(Log4jConfiguration.currentTime() + "[INFO]: Clearing list of machines because 'Select...' option from line was selected in "

                    + " REPORTS TAB");

            id_Line_report = -1;

            id_Machine_report=-1;

            combomachinereports.getItems().clear();

        }

    }

 

    public void onMachineReport() {

        boolean isMyComboBox = combomachinereports.getSelectionModel().isEmpty();

        if (isMyComboBox == false && !combomachinereports.getValue().toString().equals(" ") && !combomachinereports.getValue().toString().equals("Select...")) {

            logger.info(Log4jConfiguration.currentTime() + "[INFO]: Machine selected: " + combomachinereports.getValue().toString() + " in REPORTS \n");

            String machine = combomachinereports.getValue();

            int index1 = machine.indexOf("(");

            int index2 = machine.indexOf(")");

            String invname = machine.substring(index1 + 1, index2);

            String mach = machine.substring(0, index1);

            id_Machine_report = ConnectionDatabase.getMachineId(mach, invname);

 

        } else if (isMyComboBox == false && combomachinereports.getValue().toString().equals("Select...")) {

            logger.info(Log4jConfiguration.currentTime() + "[INFO]: " + "'Select...' option was selected from machine combobox in REPORTS.");

            id_Machine_report = -1;

        }

    }

    public void onReports()

   {

       comboifreports.setValue("Select...");

        itemscomboreport = ConnectionDatabase.getIFCombo();

        if (!itemscomboreport.isEmpty()) {

            Collections.sort(itemscomboreport);

            itemscomboreport.add(0, "Select...");

            comboifreports.setItems(itemscomboreport);

        }

        else

        {

            comboifreports.getItems().clear();

            comboifreports.setValue("");

        }

       

        setonFalseFields();

       

   }
    public void setonFalseFields()

    {

        iftextreports.setVisible(false);

        linetextreports.setVisible(false);

        machinetextreports.setVisible(false);

        comboifreports.setVisible(false);

        combolinereports.setVisible(false);

        combomachinereports.setVisible(false);

        datepickerstartreports.setVisible(false);

        datepickerendreports.setVisible(false);

        startdatetextfieldreports.setVisible(false);

       enddatetextfieldreports.setVisible(false);

        firstoptiontopreports.setVisible(false);

        topreports.setVisible(false);

        secondoptiontopreports.setVisible(false);

        thirdoptiontopreports.setVisible(false);

        alloptiontopreports.setVisible(false);

        nooptiondetailsreports.setVisible(false);

        yesoptiondetailsreports.setVisible(false);

        detailsreports.setVisible(false);

        comboescuserreports.setVisible(false);

        escalusertextreports.setVisible(false);

        button_generate_reports.setVisible(false);

        star1.setVisible(false);

        star2.setVisible(false);

        checkboxmttr.setSelected(false);

        checkboxmtbf.setSelected(false);

        checkboxincover.setSelected(false);

        checkspareover.setSelected(false);

        checkescalover.setSelected(false);

       

   }

    public void setEscalReport() {

        logger.info(Log4jConfiguration.currentTime() + "[INFO]: Escalation status YES in REPORTS TAB \n");

        itemscomboescalreports = ConnectionDatabase.getAllEscUser();

        if (!itemscomboescalreports.isEmpty()) {

            comboescuserreports.setValue("Select...");

            Pattern p = Pattern.compile("\\d+$");

            Comparator<String> c = new Comparator<String>() {

                @Override

                public int compare(String object1, String object2) {

                    Matcher m = p.matcher(object1);

                    Integer number1 = null;

                    if (!m.find()) {

                        return object1.compareTo(object2);

                    } else {

                        Integer number2 = null;

                        number1 = Integer.parseInt(m.group());

                        m = p.matcher(object2);

                        if (!m.find()) {

                            return object1.compareTo(object2);

                        } else {

                            number2 = Integer.parseInt(m.group());

                            int comparison = number1.compareTo(number2);

                            if (comparison != 0) {

                                return comparison;

                            } else {

                                return object1.compareTo(object2);

                            }

                        }

                    }

                }

            };

            Collections.sort(itemscomboescalreports, c);

            itemscomboescalreports.add(0, "Select...");

            comboescuserreports.setItems(itemscomboescalreports);

 

            String engineer = "OUTPUT LIST of engineers from REPORTS TAB: \n" +

                    "[1]: " + itemscomboescalreports.get(1) + "\n";

 

            for (int i = 2; i < itemscomboescalreports.size(); i++) {

                engineer = engineer + "[" + i + "]: " + itemscomboescalreports.get(i) + "\n";

            }

            logger.info(Log4jConfiguration.currentTime() + "[INFO]: " + engineer + "\n\n");

 

 

        } else {

            comboescuserreports.getItems().clear();

            logger.info(Log4jConfiguration.currentTime() + "[INFO]: Escalation status NO in REPORTS TAB \n\n");

 

        }

 

    }

 

    public void setThingsforReports()

    {

        iftextreports.setVisible(false);

        linetextreports.setVisible(false);

        machinetextreports.setVisible(false);

        comboifreports.setVisible(false);

        combolinereports.setVisible(false);

        combomachinereports.setVisible(false);

        datepickerstartreports.setVisible(false);

        datepickerendreports.setVisible(false);

        startdatetextfieldreports.setVisible(false);

        enddatetextfieldreports.setVisible(false);

        firstoptiontopreports.setVisible(false);

        topreports.setVisible(false);

        secondoptiontopreports.setVisible(false);

        thirdoptiontopreports.setVisible(false);

        alloptiontopreports.setVisible(false);

        nooptiondetailsreports.setVisible(false);

        yesoptiondetailsreports.setVisible(false);

        detailsreports.setVisible(false);

        comboescuserreports.setVisible(false);

        escalusertextreports.setVisible(false);

        button_generate_reports.setVisible(false);

        star1.setVisible(false);

        star2.setVisible(false);

    }

   

    public void setLineMachine()

    {

        combolinereports.setEditable(false);

        combomachinereports.setEditable(false);

        combomachinereports.getItems().clear();

        combolinereports.getItems().clear();

        combomachinereports.setValue("");

       

    }

 

 

    public void checkMTTR()

    {

        if (checkboxmttr.isSelected())

        {

          

           if(datepickerstartreports.getValue() == null)

           {

               //complete start

           }

           else

           {

               datepickerstartreports.setValue(null);

           }

           if(datepickerendreports.getValue() == null)

           {

               //complete end

           }

           else

           {

               datepickerendreports.setValue(null);

           }

          

            

            checkboxmtbf.setSelected(false);

            checkboxincover.setSelected(false);

            checkspareover.setSelected(false);

            checkescalover.setSelected(false);

            iftextreports.setVisible(true);

            comboifreports.setVisible(true);

            linetextreports.setVisible(true);

            combolinereports.setVisible(true);

            machinetextreports.setVisible(true);

            combomachinereports.setVisible(true);

            startdatetextfieldreports.setVisible(true);

            datepickerstartreports.setVisible(true);

            enddatetextfieldreports.setVisible(true);

            datepickerendreports.setVisible(true);

            topreports.setVisible(true);

            firstoptiontopreports.setVisible(true);

            secondoptiontopreports.setVisible(true);

            thirdoptiontopreports.setVisible(true);

            alloptiontopreports.setVisible(true);

            button_generate_reports.setVisible(true);

            detailsreports.setVisible(false);

            yesoptiondetailsreports.setVisible(false);

            nooptiondetailsreports.setVisible(false);

            yesoptiondetailsreports.setSelected(false);

            nooptiondetailsreports.setSelected(false);

            escalusertextreports.setVisible(false);

            comboescuserreports.setVisible(false);

            setIf();

            setLineMachine();

            selectreport = "MTTR";

            star1.setVisible(true);

            star2.setVisible(true);

            firstoptiontopreports.setSelected(true);

            detailsreports.setLayoutX(177);

            detbtnrad1.setLayoutX(262);

 

        }

        checkbox(checkboxmttr);

    }

 

    public void checkMTBF()

    {

        if (checkboxmtbf.isSelected())

        {

            if(datepickerstartreports.getValue() == null)

           {

               //complete start

           }

           else

           {

               datepickerstartreports.setValue(null);

           }

           if(datepickerendreports.getValue() == null)

           {

               //complete end

           }

           else

           {

               datepickerendreports.setValue(null);

           }

            checkboxmttr.setSelected(false);

            checkboxincover.setSelected(false);

            checkspareover.setSelected(false);

            checkescalover.setSelected(false);

            iftextreports.setVisible(true);

            comboifreports.setVisible(true);

            linetextreports.setVisible(true);

            combolinereports.setVisible(true);

            machinetextreports.setVisible(true);

            combomachinereports.setVisible(true);

            startdatetextfieldreports.setVisible(true);

            datepickerstartreports.setVisible(true);

            enddatetextfieldreports.setVisible(true);

            datepickerendreports.setVisible(true);

            topreports.setVisible(true);

            firstoptiontopreports.setVisible(true);

            secondoptiontopreports.setVisible(true);

            thirdoptiontopreports.setVisible(true);

            alloptiontopreports.setVisible(true);

            button_generate_reports.setVisible(true);

            detailsreports.setVisible(false);

            yesoptiondetailsreports.setVisible(false);

            nooptiondetailsreports.setVisible(false);

            escalusertextreports.setVisible(false);

            comboescuserreports.setVisible(false);

            escalusertextreports.setVisible(false);

            comboescuserreports.setVisible(false);

            setIf();

            setLineMachine();

            selectreport = "MTBF";

            star1.setVisible(true);

            star2.setVisible(true);

            firstoptiontopreports.setSelected(true);

        }

        checkbox(checkboxmtbf);

    }

 

    public void checkIncOver() {

        if (checkboxincover.isSelected()) {

            if(datepickerstartreports.getValue() == null)

           {

               //complete start

           }

           else

           {

               datepickerstartreports.setValue(null);

           }

           if(datepickerendreports.getValue() == null)

           {

               //complete end

           }

           else

           {

               datepickerendreports.setValue(null);

           }

            checkboxmtbf.setSelected(false);

            checkboxmttr.setSelected(false);

            checkspareover.setSelected(false);

            checkescalover.setSelected(false);

            iftextreports.setVisible(true);

            comboifreports.setVisible(true);

            linetextreports.setVisible(true);

            combolinereports.setVisible(true);

            machinetextreports.setVisible(true);

            combomachinereports.setVisible(true);

            startdatetextfieldreports.setVisible(true);

            datepickerstartreports.setVisible(true);

            enddatetextfieldreports.setVisible(true);

            datepickerendreports.setVisible(true);

            topreports.setVisible(false);

            firstoptiontopreports.setVisible(false);

            secondoptiontopreports.setVisible(false);

            thirdoptiontopreports.setVisible(false);

            alloptiontopreports.setVisible(false);

            button_generate_reports.setVisible(true);

            detailsreports.setVisible(true);

            yesoptiondetailsreports.setVisible(true);

            nooptiondetailsreports.setVisible(true);

            yesoptiondetailsreports.setSelected(false);

            nooptiondetailsreports.setSelected(true);

            escalusertextreports.setVisible(false);

            comboescuserreports.setVisible(false);

            setIf();

            setLineMachine();

            selectreport = "IncOver";

            star1.setVisible(true);

            star2.setVisible(true);

            detailsreports.setLayoutX(13);

            detbtnrad1.setLayoutX(94);

 

        }

        checkbox(checkboxincover);

    }

 

    public void checkSpareparts() {

        if (checkspareover.isSelected()) {

            if(datepickerstartreports.getValue() == null)

           {

               //complete start

           }

           else

           {

               datepickerstartreports.setValue(null);

           }

           if(datepickerendreports.getValue() == null)

           {

               //complete end

           }

           else

           {

               datepickerendreports.setValue(null);

           }

            checkboxmttr.setSelected(false);

            checkboxmtbf.setSelected(false);

            checkboxincover.setSelected(false);

            checkescalover.setSelected(false);

            iftextreports.setVisible(true);

            comboifreports.setVisible(true);

            linetextreports.setVisible(true);

            combolinereports.setVisible(true);

            machinetextreports.setVisible(true);

            combomachinereports.setVisible(true);

            startdatetextfieldreports.setVisible(true);

            datepickerstartreports.setVisible(true);

            enddatetextfieldreports.setVisible(true);

            datepickerendreports.setVisible(true);

            topreports.setVisible(false);

            firstoptiontopreports.setVisible(false);

            secondoptiontopreports.setVisible(false);

            thirdoptiontopreports.setVisible(false);

            alloptiontopreports.setVisible(false);

            button_generate_reports.setVisible(true);

            detailsreports.setVisible(true);

            yesoptiondetailsreports.setVisible(true);

            nooptiondetailsreports.setVisible(true);

            yesoptiondetailsreports.setSelected(false);

            nooptiondetailsreports.setSelected(true);

            escalusertextreports.setVisible(false);

            comboescuserreports.setVisible(false);

            setIf();

            setLineMachine();

            selectreport = "Spareparts";

            star1.setVisible(true);

            star2.setVisible(true);

            detailsreports.setLayoutX(13);

            detbtnrad1.setLayoutX(94);

        }

        checkbox(checkspareover);

    }

 

    public void checkEscOver() {

        if (checkescalover.isSelected()) {

            if(datepickerstartreports.getValue() == null)

           {

               //complete start

           }

           else

           {

               datepickerstartreports.setValue(null);

           }

           if(datepickerendreports.getValue() == null)

           {

               //complete end

           }

           else

           {

               datepickerendreports.setValue(null);

           }

            setEscalReport();

            checkboxmttr.setSelected(false);

            checkboxmtbf.setSelected(false);

            checkboxincover.setSelected(false);

            checkspareover.setSelected(false);

            iftextreports.setVisible(true);

            comboifreports.setVisible(true);

            linetextreports.setVisible(true);

            combolinereports.setVisible(true);

            machinetextreports.setVisible(true);

            combomachinereports.setVisible(true);

            startdatetextfieldreports.setVisible(true);

            datepickerstartreports.setVisible(true);

            enddatetextfieldreports.setVisible(true);

            datepickerendreports.setVisible(true);

            topreports.setVisible(false);

            firstoptiontopreports.setVisible(false);

            secondoptiontopreports.setVisible(false);

            thirdoptiontopreports.setVisible(false);

            alloptiontopreports.setVisible(false);

            button_generate_reports.setVisible(true);

            escalusertextreports.setVisible(true);

            comboescuserreports.setVisible(true);

            detailsreports.setVisible(false);

            yesoptiondetailsreports.setVisible(false);

            nooptiondetailsreports.setVisible(false);

            setIf();

            setLineMachine();

            selectreport = "EscOver";

            star1.setVisible(true);

            star2.setVisible(true);

        }

        checkbox(checkescalover);

    }

 

    public String convertMili(long mili)

    {

        long minute = TimeUnit.MILLISECONDS.toMinutes(mili);

        long days = TimeUnit.MILLISECONDS.toDays(mili);

        long ore = minute / 60;

        if(ore > 24)

        {

            ore=ore-24*days;

        }

        long minuts = minute % 60;

        return days + " Days " + " " + ore + " Hours " + minuts + " Minutes ";

    }

 

    public String getDateTicket(String ticket) {

        //verificare daca ticket e null sau nu

        String s=null;

          if(ticket !=null) {

              int index = ticket.indexOf("_");

              long milis = Long.valueOf(ticket.substring(index + 1));

              java.util.Date start_date = new Date(milis);

              DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd ");

              String sd = sdf1.format(start_date);

              s=sd;

          }

          else

          {

              logger.info(Log4jConfiguration.currentTime()+"[WARNING]: There is no ticket ");

          }

          return s;

 

    }

    public String getDate(String datedb) {

        long milis = Long.valueOf(datedb);

        java.util.Date start_date = new Date(milis);

        DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd ");

        String sd = sdf1.format(start_date);

        return sd;

    }

 

    public String dateMessage(int d, int h, int m) {

        return d + " Days " + h + " Hours " + m + " Minutes ";

    }

 

    public String getEsc(int id) {

        if (id != 0) {

            return "yes";

 

        } else return "no";

    }

 

    public void generateReportForAllOptionReport1() {

        conn=ConnectionDatabase.getConnection();

        if (conn == null) {

            //System.out.println("Error DB");

        }

 

        String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

 

        try  {

            CallableStatement ps = conn.prepareCall("{call StatusReport1()}");

            ResultSet result = ps.executeQuery();

 

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Reports");

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

            sheet.setColumnWidth(4, (47 * 334));

            sheet.setColumnWidth(5, (23 * 256));

            sheet.setColumnWidth(6, (23 * 256));

            sheet.setColumnWidth(7, (23 * 256));

 

 

            writeHeaderLine(sheet);

 

            writeDataLines(result, workbook, sheet);

 

            CallableStatement pstb2 = conn.prepareCall("{call getOpt(?,?,?,?)}");

            pstb2.setString(1, "all");

            pstb2.setInt(2, id_IF_report);

            pstb2.setInt(3, id_Line_report);

            pstb2.setInt(4, id_Machine_report);

            ResultSet resulttb2 = pstb2.executeQuery();

 

            writeHeaderLineTab2(sheet);

 

            writeDataLinesTab2(resulttb2, workbook, sheet);

 

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);

            workbook.write(outputStream);

            ps.close();

            pstb2.close();

 

            outputStream.close();

        } catch (SQLException e) {

            //System.out.println("Datababse error:");

            e.printStackTrace();

 

        } catch (FileNotFoundException e) {

            exceptie=true;

        } catch (IOException e) {

            //System.out.println("File IO error:");

            e.printStackTrace();

        }

    }

public void generateTab2Report3Yes(){

    conn=ConnectionDatabase.getConnection();

    if (conn == null) {

        //System.out.println("Error DB");

    }

 

    String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

 

    try  {

        CallableStatement ps = conn.prepareCall("{call getTab2Report3()}");

        ResultSet result = ps.executeQuery();

 

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("Reports");

 

        sheet.setColumnWidth(0, (23 * 256));

        sheet.setColumnWidth(1, (25 * 270));

        sheet.setColumnWidth(2, (25 * 270));

        sheet.setColumnWidth(3, (23 * 256));

        sheet.setColumnWidth(4, (25 * 270));

        sheet.setColumnWidth(5, (25 * 270));

        sheet.setColumnWidth(6, (23 * 256));

        sheet.setColumnWidth(7, (25 * 270));

        sheet.setColumnWidth(8, (25 * 270));

        sheet.setColumnWidth(9, (23 * 256));

        sheet.setColumnWidth(10, (25 * 270));

        sheet.setColumnWidth(11, (25 * 270));

        sheet.setColumnWidth(12, (23 * 256));

        sheet.setColumnWidth(13, (25 * 270));

        sheet.setColumnWidth(14, (25 * 270));

        sheet.setColumnWidth(15, (25 * 270));

        sheet.setColumnWidth(16, (25 * 270));

 

 

        writeHeaderLineTab2Report3Yes(sheet);

 

        writeDataLinesTab2Report3Yes(result,workbook,sheet);

 

 

        FileOutputStream outputStream = new FileOutputStream(excelFilePath);

        workbook.write(outputStream);

        ps.close();

        outputStream.close();

    } catch (SQLException e) {

        //System.out.println("Datababse error:");

        e.printStackTrace();

    } catch (FileNotFoundException e) {

        exceptie=true;

    } catch (IOException e) {

        //System.out.println("File IO error:");

        e.printStackTrace();

    }

}

    public void generateTab1Report3() {

        conn=ConnectionDatabase.getConnection();

        if (conn == null) {

            //System.out.println("Error DB");

        }

 

        String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

 

        try  {

            CallableStatement ps = conn.prepareCall("{call getTabReport3()}");

            ResultSet result = ps.executeQuery();

 

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Reports");

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (23 * 256));

            sheet.setColumnWidth(4, (25 * 270));

            sheet.setColumnWidth(5, (25 * 270));

            sheet.setColumnWidth(6, (23 * 256));

            sheet.setColumnWidth(7, (25 * 270));

            sheet.setColumnWidth(8, (25 * 270));

            sheet.setColumnWidth(9, (23 * 256));

            sheet.setColumnWidth(10, (25 * 270));

            sheet.setColumnWidth(11, (25 * 270));

            sheet.setColumnWidth(12, (23 * 256));

            sheet.setColumnWidth(13, (25 * 270));

            sheet.setColumnWidth(14, (25 * 270));

            sheet.setColumnWidth(15, (25 * 270));

 

 

            writeHeaderLinetab1Report3(sheet);

 

            writeDataLinesTab1Report3(result, workbook, sheet);

 

            CallableStatement ps1 = conn.prepareCall("{call getTab2Report3(?,?,?)}");

            ps1.setInt(1, id_IF_report);

            ps1.setInt(2, id_Line_report);

            ps1.setInt(3, id_Machine_report);

            ResultSet resulttb2 = ps1.executeQuery();

 

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (23 * 256));

            sheet.setColumnWidth(4, (25 * 270));

            sheet.setColumnWidth(5, (25 * 270));

            sheet.setColumnWidth(6, (23 * 256));

            sheet.setColumnWidth(7, (25 * 270));

            sheet.setColumnWidth(8, (25 * 270));

            sheet.setColumnWidth(9, (23 * 256));

            sheet.setColumnWidth(10, (25 * 270));

            sheet.setColumnWidth(11, (25 * 270));

            sheet.setColumnWidth(12, (23 * 256));

            sheet.setColumnWidth(13, (25 * 270));

            sheet.setColumnWidth(14, (25 * 270));

            sheet.setColumnWidth(15, (25 * 270));

            sheet.setColumnWidth(16, (25 * 270));

 

 

            writeHeaderLineTab2Report3Yes(sheet);

 

            writeDataLinesTab2Report3Yes(resulttb2,workbook,sheet);

 

 

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);

            workbook.write(outputStream);

            ps.close();

            outputStream.close();

        } catch (SQLException e) {

            //System.out.println("Datababse error:");

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            exceptie=true;

        } catch (IOException e) {

            //System.out.println("File IO error:");

            e.printStackTrace();

        }

    }

 

    public void generateTab2Report3() {

        conn=ConnectionDatabase.getConnection();

        if (conn == null) {

            //System.out.println("Error DB");

        }

 

        String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

 

        try  {

            CallableStatement ps = conn.prepareCall("{call StatusReport1()}");

            ResultSet result = ps.executeQuery();

 

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Reports");

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

 

 

            writeHeaderLine(sheet);

 

            writeDataLines(result, workbook, sheet);

 

            CallableStatement pstb2 = conn.prepareCall("{call getOpt(?,?,?,?)}");

            pstb2.setString(1, "all");

            pstb2.setInt(2, id_IF_report);

            pstb2.setInt(3, id_Line_report);

            pstb2.setInt(4, id_Machine_report);

            ResultSet resulttb2 = pstb2.executeQuery();

 

            writeHeaderLineTab2Report3(sheet);

 

            writeDataLinesTab2Rep3(resulttb2, workbook, sheet);

 

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);

            workbook.write(outputStream);

            ps.close();

            pstb2.close();

            outputStream.close();

        } catch (SQLException e) {

            //System.out.println("Datababse error:");

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            exceptie=true;

        } catch (IOException e) {

            //System.out.println("File IO error:");

            e.printStackTrace();

        }

    }

 

    public void generateReportForFirstOptionReport1() {

        conn=ConnectionDatabase.getConnection();

       

 

        String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

        //System.out.println(excelFilePath);

       

        try  {

            CallableStatement ps = conn.prepareCall("{call StatusReport1()}");

            ResultSet result = ps.executeQuery();

           

            

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Reports");

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

            sheet.setColumnWidth(4, (47 * 334));

            sheet.setColumnWidth(5, (23 * 256));

            sheet.setColumnWidth(6, (23 * 256));

            sheet.setColumnWidth(7, (23 * 256));

 

 

            writeHeaderLine(sheet);

 

            writeDataLines(result, workbook, sheet);

 

            CallableStatement pstb2 = conn.prepareCall("{call getOpt(?,?,?,?)}");

            pstb2.setString(1, "3");

            pstb2.setInt(2, id_IF_report);

            pstb2.setInt(3, id_Line_report);

            pstb2.setInt(4, id_Machine_report);

            ResultSet resulttb2 = pstb2.executeQuery();

 

            writeHeaderLineTab2(sheet);

 

            writeDataLinesTab2(resulttb2, workbook, sheet);

 

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);

            workbook.write(outputStream);

            ps.close();

            pstb2.close();

            outputStream.close();

        } catch (SQLException e) {

            //System.out.println("Datababse error:");

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            exceptie=true;

        } catch (IOException e) {

            //System.out.println("File IO error:");

            e.printStackTrace();

        }

       

    }

 

    public void writeHeaderLineTab2(HSSFSheet sheet) {

       

        rowCount+=3;

        Row headerRow = sheet.createRow(rowCount++);

 

        Cell headerCell = headerRow.createCell(0);

        ((org.apache.poi.ss.usermodel.Cell) headerCell).setCellValue("Duration of the incident");

 

        headerCell = (Cell) headerRow.createCell(1);

        headerCell.setCellValue("Creation date");

        headerCell = headerRow.createCell(2);

        headerCell.setCellValue("IF");

        headerCell = headerRow.createCell(3);

        headerCell.setCellValue("Line");

        headerCell = headerRow.createCell(4);

        headerCell.setCellValue("Machine");

        headerCell = headerRow.createCell(5);

        headerCell.setCellValue("Short Description");

        headerCell = headerRow.createCell(6);

        headerCell.setCellValue("Escalation");

        headerCell = headerRow.createCell(7);

        headerCell.setCellValue("Status");

 

    }

    public void writeHeaderLineTab2Report2(HSSFSheet sheet) {

       

        rowCount+=3;

 

        Row headerRow = sheet.createRow(rowCount++);

 

        Cell headerCell = headerRow.createCell(0);

        ((org.apache.poi.ss.usermodel.Cell) headerCell).setCellValue("Duration between incidents");

 

        headerCell = headerRow.createCell(1);

        headerCell.setCellValue("IF");

        headerCell = headerRow.createCell(2);

        headerCell.setCellValue("Line");

        headerCell = headerRow.createCell(3);

        headerCell.setCellValue("Machine");

        headerCell = headerRow.createCell(4);

        headerCell.setCellValue("End date incident");

        headerCell = headerRow.createCell(5);

        headerCell.setCellValue("Start date incident");

 

 

    }

 

    public void writeHeaderLineTab2Report3Yes(HSSFSheet sheet) {

       

        rowCount+=3;

 

        Row headerRow = sheet.createRow(rowCount++);

 

        Cell headerCell = headerRow.createCell(0);

        ((org.apache.poi.ss.usermodel.Cell) headerCell).setCellValue("Duration of the incident");

 

        headerCell = (Cell) headerRow.createCell(1);

        headerCell.setCellValue("Creation date");

        headerCell = headerRow.createCell(2);

        headerCell.setCellValue("IF");

        headerCell = headerRow.createCell(3);

        headerCell.setCellValue("Line");

        headerCell = headerRow.createCell(4);

        headerCell.setCellValue("Machine");

        headerCell = headerRow.createCell(5);

        headerCell.setCellValue("Short Description");

        headerCell = headerRow.createCell(6);

        headerCell.setCellValue("Escalation");

        headerCell = headerRow.createCell(7);

        headerCell.setCellValue("Status");

 

        headerCell = headerRow.createCell(8);

        headerCell.setCellValue("Spare Parts");

 

    }

    public void writeHeaderLineTab3Report4(HSSFSheet sheet)

    {

        rowCount+=3;

 

        Row headerRow = sheet.createRow(rowCount++);

 

        Cell headerCell = headerRow.createCell(0);

        ((org.apache.poi.ss.usermodel.Cell) headerCell).setCellValue("Duration of the incident");

 

        headerCell = (Cell) headerRow.createCell(1);

        headerCell.setCellValue("Creation date");

        headerCell = headerRow.createCell(2);

        headerCell.setCellValue("IF");

        headerCell = headerRow.createCell(3);

        headerCell.setCellValue("Line");

        headerCell = headerRow.createCell(4);

        headerCell.setCellValue("Machine");

        headerCell = headerRow.createCell(5);

        headerCell.setCellValue("Short Description");

        headerCell = headerRow.createCell(6);

        headerCell.setCellValue("Escalation");

        headerCell = headerRow.createCell(7);

        headerCell.setCellValue("Status");

 

        headerCell = headerRow.createCell(8);

        headerCell.setCellValue("Spare Parts");

 

    }

    public void writeHeaderLineTab2Report3(HSSFSheet sheet) {

        rowCount+=3;

        Row headerRow = sheet.createRow(rowCount++);

 

        Cell headerCell = headerRow.createCell(0);

        ((org.apache.poi.ss.usermodel.Cell) headerCell).setCellValue("IF");

 

        headerCell = (Cell) headerRow.createCell(1);

        headerCell.setCellValue("Line");

        headerCell = headerRow.createCell(2);

        headerCell.setCellValue("Machine");

 

    }

public Object checkrep(int i){

     String  t="";

        if(i !=-1)

        {

            return i;

        }

 

      else  if(i==-1){

         return  t;

        }

     return 0;

}

 

    public boolean checkPickDate(String dinc, String start, String end) {

        if( dinc == null || dinc.isEmpty())

            return true;

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            sdf.setLenient(false);

 

            Date d1 =  sdf.parse(start);

            Date d2 = sdf.parse(dinc);

            Date d3 =  sdf.parse(end);

 

            if (d2.compareTo(d1) >= 0) {

                if (d2.compareTo(d3) <= 0) {

                    return true;

                } else {

                    return false;

                }

            } else {

                return false;

            }

 

        } catch (ParseException pe) {

            pe.printStackTrace();

        }

        return false;

    }

 

    public String checkPN (String idspare_parts) {

 

        if(!idspare_parts.equals(""))

        {

            if(idspare_parts.length()==1)

            {

                String pn=ConnectionDatabase.getPartNumber(Integer.parseInt(idspare_parts));

                return pn;

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

                //System.out.println(part_numbere);

                return part_numbere;

            }

        }

        else

        {

           return "N/A";

        }

 

    }

 

 

 

 

        public void writeDataLinesTab2(ResultSet result, HSSFWorkbook workbook,

                                   HSSFSheet sheet) throws SQLException {

       

 

        while (result.next()) {

            String datacreeareinc = getDateTicket((result.getString("incident_ticket")));

            String datecreearepick1 = datepickerstartreports.getValue().toString();

            String datacreearepick2 = datepickerendreports.getValue().toString();

            //System.out.println(datacreeareinc + " " + datecreearepick1 + " " + datacreearepick2);

            if (checkPickDate(datacreeareinc, datecreearepick1, datacreearepick2) == true) {

                String durataincident = dateMessage(result.getInt("Days"), result.getInt("Hour"), result.getInt("Minutes"));

                String datacreeare = getDateTicket((result.getString("incident_ticket")));

                String ifr = result.getString("IF_Name");

                String linie = result.getString("Line_Name");

                String statie = result.getString("Machine_Name");

                String shortdesc = result.getString("incident_short_solution");

                String esc = getEsc(result.getInt("idEscalation_Assign"));

                String status = result.getString("incident_Status");

 

 

                Row row = sheet.createRow(rowCount++);

 

                int columnCount = 0;

                Cell cell = row.createCell(columnCount++);

                cell.setCellValue(durataincident);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(datacreeare);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(ifr);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(linie);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(statie);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(shortdesc);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(esc);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(status);

 

                CellStyle cellStyle = workbook.createCellStyle();

                CreationHelper creationHelper = workbook.getCreationHelper();

                cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

                cell.setCellStyle(cellStyle);

            }

        }

    }

    public void writeDataLinesTab2Report2(ResultSet result, HSSFWorkbook workbook,

                                   HSSFSheet sheet) throws SQLException {

       

 

        while (result.next()) {

            String datacreeareinc = getDateTicket((result.getString("incident_ticket")));

            String datecreearepick1 = datepickerstartreports.getValue().toString();

            String datacreearepick2 = datepickerendreports.getValue().toString();

            //System.out.println(datacreeareinc + " " + datecreearepick1 + " " + datacreearepick2);

            if (checkPickDate(datacreeareinc, datecreearepick1, datacreearepick2) == true)

            {

                String durataintreincident =  convertMili((result.getLong("Timpul intre incident")));

                String ifr = result.getString("IF_Name");

                String linie = result.getString("Line_Name");

                String statie = result.getString("Machine_Name");

                String endhourinc = getDate((result.getString("incident_End_Date_hour")));

                String starthourinc = getDate((result.getString("incident_Start_Date_hour")));

 

                Row row = sheet.createRow(rowCount++);

 

                int columnCount = 0;

                Cell cell = row.createCell(columnCount++);

                cell.setCellValue(durataintreincident);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(ifr);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(linie);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(statie);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(endhourinc);

 

                cell = row.createCell(columnCount++);

               cell.setCellValue(starthourinc);

 

 

 

                CellStyle cellStyle = workbook.createCellStyle();

                cell.setCellStyle(cellStyle);

            }

        }

    }

    public void writeDataLinesTab3Report4(ResultSet result, HSSFWorkbook workbook,

                                             HSSFSheet sheet) throws SQLException {

       

 

        while (result.next()) {

            String datacreeareinc = getDateTicket((result.getString("incident_ticket")));

            String datecreearepick1 = datepickerstartreports.getValue().toString();

            String datacreearepick2 = datepickerendreports.getValue().toString();

            //System.out.println(datacreeareinc + " " + datecreearepick1 + " " + datacreearepick2);

            if (checkPickDate(datacreeareinc, datecreearepick1, datacreearepick2) == true) {

                String durataincident = dateMessage(result.getInt("Days"), result.getInt("Hour"), result.getInt("Minutes"));

                String datacreeare = getDateTicket((result.getString("incident_ticket")));

                String ifr = result.getString("IF_Name");

                String linie = result.getString("Line_Name");

                String statie = result.getString("Machine_Name");

                String shortdesc = result.getString("incident_short_solution");

                String esc = getEsc(result.getInt("idEscalation_Assign"));

                String status = result.getString("incident_Status");

                String spare = checkPN((result.getString("idSpare_Parts")));

 

 

                Row row = sheet.createRow(rowCount++);

 

                int columnCount = 0;

                Cell cell = row.createCell(columnCount++);

                cell.setCellValue(durataincident);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(datacreeare);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(ifr);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(linie);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(statie);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(shortdesc);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(esc);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(status);

               

                

                cell = row.createCell(columnCount++);

                cell.setCellValue(spare);

               

 

                CellStyle cellStyle = workbook.createCellStyle();

                cellStyle.setWrapText(true);

                CreationHelper creationHelper = workbook.getCreationHelper();

                cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

                cell.setCellStyle(cellStyle);

            }

        }

    }

    public void writeDataLinesTab2Report3Yes(ResultSet result, HSSFWorkbook workbook,

                                   HSSFSheet sheet) throws SQLException {

       

 

        while (result.next()) {

           String datacreeareinc = getDateTicket((result.getString("incident_ticket")));

            String datecreearepick1 = datepickerstartreports.getValue().toString();

            String datacreearepick2 = datepickerendreports.getValue().toString();

           //System.out.println(datacreeareinc + " " + datecreearepick1 + " " + datacreearepick2);

            if (checkPickDate(datacreeareinc, datecreearepick1, datacreearepick2) == true) {

                String durataincident = dateMessage(result.getInt("Days"), result.getInt("Hour"), result.getInt("Minutes"));

                String datacreeare = getDateTicket((result.getString("incident_ticket")));

                String ifr = result.getString("IF_Name");

                String linie = result.getString("Line_Name");

                String statie = result.getString("Machine_Name");

                String shortdesc = result.getString("incident_short_solution");

                String esc = getEsc(result.getInt("idEscalation_Assign"));

                String status = result.getString("incident_Status");

                String spare = checkPN((result.getString("idSpare_Parts")));

 

 

                Row row = sheet.createRow(rowCount++);

 

                int columnCount = 0;

                Cell cell = row.createCell(columnCount++);

                cell.setCellValue(durataincident);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(datacreeare);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(ifr);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(linie);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(statie);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(shortdesc);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(esc);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(status);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(spare);

 

                CellStyle cellStyle = workbook.createCellStyle();

                CreationHelper creationHelper = workbook.getCreationHelper();

                cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

                cell.setCellStyle(cellStyle);

            }

        }

    }

 

    public void writeDataLinesTab2Rep3(ResultSet result, HSSFWorkbook workbook,

                                       HSSFSheet sheet) throws SQLException {

       

 

        while (result.next()) {

            String datacreeareinc = getDateTicket((result.getString("incident_ticket")));

            String datecreearepick1 = datepickerstartreports.getValue().toString();

            String datacreearepick2 = datepickerendreports.getValue().toString();

            //System.out.println(datacreeareinc + " " + datecreearepick1 + " " + datacreearepick2);

            if (checkPickDate(datacreeareinc, datecreearepick1, datacreearepick2) == true) {

                String ifr = result.getString("IF_Name");

                String linie = result.getString("Line_Name");

                String statie = result.getString("Machine_Name");

 

 

 

                Row row = sheet.createRow(rowCount++);

 

                int columnCount = 0;

 

               Cell cell = row.createCell(columnCount++);

                cell.setCellValue(ifr);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(linie);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(statie);

 

 

 

                CellStyle cellStyle = workbook.createCellStyle();

                CreationHelper creationHelper = workbook.getCreationHelper();

                cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

                cell.setCellStyle(cellStyle);

            }

        }

 

    }

 

    public void writeHeaderLinetab2Report5(HSSFSheet sheet) {

        rowCount+=3;

 

        Row headerRow = sheet.createRow(rowCount++);

 

        Cell headerCell = headerRow.createCell(0);

        headerCell.setCellValue("Count");

 

        headerCell = headerRow.createCell(1);

        headerCell.setCellValue("User");

 

        headerCell = headerRow.createCell(2);

        headerCell.setCellValue("Solved");

 

        headerCell = headerRow.createCell(3);

        headerCell.setCellValue("Unsolved");

 

        headerCell = headerRow.createCell(4);

        headerCell.setCellValue("Escalation solution");

 

        headerCell = headerRow.createCell(5);

        headerCell.setCellValue("No escalation solution");

 

    }

 

    public void writeDataLinesTab2Report5(ResultSet result, HSSFWorkbook workbook,

                                          HSSFSheet sheet) throws SQLException {

       

 

        while (result.next())

        {

            String datacreeareinc = getDateTicket((result.getString("incident_ticket")));

            String datecreearepick1 = datepickerstartreports.getValue().toString();

            String datacreearepick2 = datepickerendreports.getValue().toString();

            //System.out.println(datacreeareinc + " " + datecreearepick1 + " " + datacreearepick2);

            if (checkPickDate(datacreeareinc, datecreearepick1, datacreearepick2) == true) {

                int count = result.getInt("Count");

                String userengineer = result.getString("User Engineer");

                String solved = result.getString("Solved");

                String unsolved = result.getString("Unsolved");

                String escsol = result.getString("Escalation solution");

                String noescsol = result.getString("No Escalation solution");

 

 

                Row row = sheet.createRow(rowCount++);

 

                int columnCount = 0;

                Cell cell = row.createCell(columnCount++);

                cell.setCellValue(count);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(userengineer);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(solved);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(unsolved);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(escsol);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(noescsol);

 

                CellStyle cellStyle = workbook.createCellStyle();

                CreationHelper creationHelper = workbook.getCreationHelper();

                cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

                cell.setCellStyle(cellStyle);

            }

 

        }

    }

 

    public void writeHeaderLinetab3Report5(HSSFSheet sheet) {

        rowCount+=3;

 

        Row headerRow = sheet.createRow(rowCount++);

 

        Cell headerCell = headerRow.createCell(0);

        headerCell.setCellValue("IF");

 

        headerCell = headerRow.createCell(1);

        headerCell.setCellValue("Line");

 

        headerCell = headerRow.createCell(2);

        headerCell.setCellValue("Machine");

 

        headerCell = headerRow.createCell(3);

        headerCell.setCellValue("Escalation user");

 

    }

    public void writeDataLinesTab3Report5(ResultSet result, HSSFWorkbook workbook,

                                          HSSFSheet sheet) throws SQLException {

       

 

        while (result.next())

        {

            String datacreeareinc = getDateTicket((result.getString("incident_ticket")));

            String datecreearepick1 = datepickerstartreports.getValue().toString();

            String datacreearepick2 = datepickerendreports.getValue().toString();

            //System.out.println(datacreeareinc + " " + datecreearepick1 + " " + datacreearepick2);

            if (checkPickDate(datacreeareinc, datecreearepick1, datacreearepick2) == true) {

                String ifname = result.getString("IF_Name");

                String linenname = result.getString("Line_Name");

                String machinename = result.getString("Machine_Name");

                String escuser = result.getString("User Engineer");

 

 

 

                Row row = sheet.createRow(rowCount++);

 

                int columnCount = 0;

                Cell cell = row.createCell(columnCount++);

                cell.setCellValue(ifname);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(linenname);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(machinename);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(escuser);

 

 

                CellStyle cellStyle = workbook.createCellStyle();

                CreationHelper creationHelper = workbook.getCreationHelper();

                cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

                cell.setCellStyle(cellStyle);

            }

 

        }

    }

 

 

    public void writeHeaderLinetab2Report4(HSSFSheet sheet) {

        rowCount+=3;

 

        Row headerRow = sheet.createRow(rowCount++);

 

        Cell headerCell = headerRow.createCell(0);

        headerCell.setCellValue("Usage");

 

        headerCell = headerRow.createCell(1);

        headerCell.setCellValue("Part Number");

 

        headerCell = headerRow.createCell(2);

        headerCell.setCellValue("Spare parts description");

 

    }

 

    public void writeDataLinesTab2Report4(ResultSet result, HSSFWorkbook workbook,

                                          HSSFSheet sheet) throws SQLException {

       

 

        while (result.next())

        {

            String datacreeareinc = getDateTicket((result.getString("incident_ticket")));

            String datecreearepick1 = datepickerstartreports.getValue().toString();

            String datacreearepick2 = datepickerendreports.getValue().toString();

            //System.out.println(datacreeareinc + " " + datecreearepick1 + " " + datacreearepick2);

            if (checkPickDate(datacreeareinc, datecreearepick1, datacreearepick2) == true) {

                int usg = result.getInt("Usages");

                String partnb = result.getString("Part_Number");

                String spdesc = result.getString("Spare_Description");

 

 

 

                Row row = sheet.createRow(rowCount++);

 

                int columnCount = 0;

                Cell cell = row.createCell(columnCount++);

                cell.setCellValue(usg);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(partnb);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(spdesc);

 

                CellStyle cellStyle = workbook.createCellStyle();

                CreationHelper creationHelper = workbook.getCreationHelper();

 

                cell.setCellStyle(cellStyle);

            }

 

        }

    }

 

 

    public void writeHeaderLinetab1Report4(HSSFSheet sheet) {

        rowCount=0;

        Row headerRow = sheet.createRow(rowCount++);

 

        Cell headerCell = headerRow.createCell(0);

        headerCell.setCellValue("Incidents that have used spare parts");

 

        headerCell = headerRow.createCell(1);

        headerCell.setCellValue("Total spare parts used");

 

        headerCell = headerRow.createCell(2);

        headerCell.setCellValue(" Average spare parts used");

 

    }

 

 

    public void writeDataLinesTab1Report4(ResultSet result, HSSFWorkbook workbook,

                                          HSSFSheet sheet) throws SQLException {

       

 

        while (result.next())

        {

            //System.out.println(result.getString("incident_ticket"));

            String datacreeareinc = getDateTicket((result.getString("incident_ticket")));

            String datecreearepick1 = datepickerstartreports.getValue().toString();

            String datacreearepick2 = datepickerendreports.getValue().toString();

            //System.out.println(datacreeareinc + " " + datecreearepick1 + " " + datacreearepick2);

            if (checkPickDate(datacreeareinc, datecreearepick1, datacreearepick2) == true) {

                int nrincidentesp = result.getInt("Nr. incidente in care s-a folosit spare parts");

                int nrtotspfolo = result.getInt("Nr_total_spare_parts_folosite");

                int avgsp = result.getInt("Avg spare parts folosite per incident");

 

 

 

                Row row = sheet.createRow(rowCount++);

 

                int columnCount = 0;

                Cell cell = row.createCell(columnCount++);

                cell.setCellValue(nrincidentesp);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(nrtotspfolo);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(avgsp);

 

                CellStyle cellStyle = workbook.createCellStyle();

                CreationHelper creationHelper = workbook.getCreationHelper();

 

                cell.setCellStyle(cellStyle);

            }

 

        }

    }

 

 

    public void writeHeaderLinetab1Report5(HSSFSheet sheet) {

        rowCount=0;

 

        Row headerRow = sheet.createRow(rowCount++);

 

        Cell headerCell = headerRow.createCell(0);

        headerCell.setCellValue("No.total incidents");

 

        headerCell = headerRow.createCell(1);

        headerCell.setCellValue("No. incidents with escalation");

 

        headerCell = headerRow.createCell(2);

        headerCell.setCellValue("No. incidents no escalation");

 

        headerCell = headerRow.createCell(3);

        headerCell.setCellValue("No. incidents solved");

 

        headerCell = headerRow.createCell(4);

        headerCell.setCellValue("No. incidents unsolved");

 

        headerCell = headerRow.createCell(5);

        headerCell.setCellValue("No. incidents with solution escalation");

 

        headerCell = headerRow.createCell(6);

        headerCell.setCellValue("No. incidents no solution escalation ");

 

    }

 

 

 

    public void writeDataLinesTab1Report5(ResultSet result, HSSFWorkbook workbook,

                                          HSSFSheet sheet) throws SQLException {

       

 

        while (result.next())

        {

            String datacreeareinc = getDateTicket((result.getString("incident_ticket")));

            String datecreearepick1 = datepickerstartreports.getValue().toString();

            String datacreearepick2 = datepickerendreports.getValue().toString();

            //System.out.println(datacreeareinc + " " + datecreearepick1 + " " + datacreearepick2);

            if (checkPickDate(datacreeareinc, datecreearepick1, datacreearepick2) == true) {

                int nrincidente = result.getInt("Nr.Total Incidente");

                int nrinccuesc = result.getInt("Nr.incidente cu escaladare");

                int nrincfaraesc = result.getInt("Nr.incidente fara escaladare");

                String nrincsolved = result.getString("Nr.incidente solved");

                String nrincunsolved = result.getString("Nr.incidente unsolved");

                String nrinccusolesc = result.getString("Nr incidente cu sol esc");

                String nrincfarasolesc = result.getString("Nr incidente fara sol esc");

 

 

 

                Row row = sheet.createRow(rowCount++);

 

                int columnCount = 0;

                Cell cell = row.createCell(columnCount++);

                cell.setCellValue(nrincidente);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(nrinccuesc);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(nrincfaraesc);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(nrincsolved);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(nrincunsolved);

 

 

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(nrinccusolesc);

 

                cell = row.createCell(columnCount++);

                cell.setCellValue(nrincfarasolesc);

 

 

 

                CellStyle cellStyle = workbook.createCellStyle();

                CreationHelper creationHelper = workbook.getCreationHelper();

                cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

                cell.setCellStyle(cellStyle);

            }

 

        }

    }

 

    public void writeHeaderLinetab1Report3(HSSFSheet sheet) {

        rowCount=0;

 

        Row headerRow = sheet.createRow(rowCount++);

 

        Cell headerCell = headerRow.createCell(0);

        headerCell.setCellValue("No. total incidents");

 

        headerCell = headerRow.createCell(1);

        headerCell.setCellValue("Maximum duration");

 

        headerCell = headerRow.createCell(2);

        headerCell.setCellValue("Minimum duration");

 

        headerCell = headerRow.createCell(3);

        headerCell.setCellValue("Average duration");

 

        headerCell = headerRow.createCell(4);

        headerCell.setCellValue("No. incidents with escalation");

 

        headerCell = headerRow.createCell(5);

        headerCell.setCellValue("No. incidents no escalation");

 

        headerCell = headerRow.createCell(6);

        headerCell.setCellValue("Average duration of the incidents with escalation");

 

        headerCell = headerRow.createCell(7);

        headerCell.setCellValue("Minimum duration of the incidents with escalation");

 

        headerCell = headerRow.createCell(8);

        headerCell.setCellValue("Maximum duration of the incidents with escalation");

 

 

        headerCell = headerRow.createCell(9);

        headerCell.setCellValue("Average duration of the incidents no escalation");

 

        headerCell = headerRow.createCell(10);

        headerCell.setCellValue("Minimum duration of the incidents no escalation");

 

        headerCell = headerRow.createCell(11);

        headerCell.setCellValue("Maximum duration of the incidents no escalation");

 

        headerCell = headerRow.createCell(12);

        headerCell.setCellValue("No. incidents with spare parts");

 

        headerCell = headerRow.createCell(13);

        headerCell.setCellValue("No. incidents no spare parts");

 

        headerCell = headerRow.createCell(14);

        headerCell.setCellValue("No. incidents solved");

 

        headerCell = headerRow.createCell(15);

        headerCell.setCellValue("No. incidents unsolved");

 

    }

 

    public void writeDataLinesTab1Report3(ResultSet result, HSSFWorkbook workbook,

                                          HSSFSheet sheet) throws SQLException {

       

 

        while (result.next())

             {

                 String datacreeareinc = getDateTicket((result.getString("incident_ticket")));

                 String datecreearepick1 = datepickerstartreports.getValue().toString();

                 String datacreearepick2 = datepickerendreports.getValue().toString();

                 //System.out.println(datacreeareinc + " " + datecreearepick1 + " " + datacreearepick2);

                 if (checkPickDate(datacreeareinc, datecreearepick1, datacreearepick2) == true) {

                     int nrincidente = result.getInt("Nr.Total Incidente");

                     String durata_maxima = convertMili(result.getLong("Durata maxima"));

                     String durata_minima = convertMili(result.getLong("Durata minima"));

                     String durata_medie = convertMili(result.getLong("Durata medie"));

                     int nrinccuesc = result.getInt("Nr.incidente cu escaladare");

                     int nrincfaraesc = result.getInt("Nr.incidente fara escaladare");

                     String durmedcuesc = convertMili(result.getLong("Durata medie incidente cu escaladare"));

                     String durmincuesc = convertMili(result.getLong("Durata minima incidente cu escaladare"));

                     String durmaxcuesc = convertMili(result.getLong("Durata maxima incidente cu escaladare"));

                     String durmedfaraesc = convertMili(result.getLong("Durata medie incidente fara escaladare"));

                     String durminfaraesc = convertMili(result.getLong("Durata minima incidente fara escaladare"));

                     String durmaxfaraesc = convertMili(result.getLong("Durata maxima incidente fara escaladare"));

                     String nrinccuspare = result.getString("Nr.incidente cu spare parts");

                     String nrincfaraspare = result.getString("Nr.incidente fara spare parts");

                     String nrincsolved = result.getString("Nr.incidente solved");

                     String nrincunsolved = result.getString("Nr.incidente unsolved");

 

 

                     Row row = sheet.createRow(rowCount++);

 

                     int columnCount = 0;

                     Cell cell = row.createCell(columnCount++);

                     cell.setCellValue(nrincidente);

 

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(durata_maxima);

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(durata_minima);

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(durata_medie);

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(nrinccuesc);

                    

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(nrincfaraesc);

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(durmedcuesc);

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(durmincuesc);

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(durmaxcuesc);

 

                   

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(durmedfaraesc);

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(durminfaraesc);

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(durmaxfaraesc);

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(nrinccuspare);

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(nrincfaraspare);

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(nrincsolved);

 

                     cell = row.createCell(columnCount++);

                     cell.setCellValue(nrincunsolved);

 

                     CellStyle cellStyle = workbook.createCellStyle();

                     CreationHelper creationHelper = workbook.getCreationHelper();

                     cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

                     cell.setCellStyle(cellStyle);

                 }

 

        }

    }

public void generateTab1Report4(){

    conn=ConnectionDatabase.getConnection();

    if(conn== null){

        //System.out.println("Error DB");

    }

 

    String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

 

    try  {

        CallableStatement ps = conn.prepareCall("{call getTab1Report4()}");

        ResultSet result = ps.executeQuery();

 

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("Reports");

 

        sheet.setColumnWidth(0, (23 * 256));

        sheet.setColumnWidth(1, (25 * 270));

        sheet.setColumnWidth(2, (25 * 270));

 

        writeHeaderLinetab1Report4(sheet);

 

        writeDataLinesTab1Report4(result, workbook, sheet);

 

 

        CallableStatement ps1 = conn.prepareCall("{call getTab2Report4()}");

        ResultSet resulttb2 = ps1.executeQuery();

 

        sheet.setColumnWidth(0, (23 * 256));

        sheet.setColumnWidth(1, (25 * 270));

        sheet.setColumnWidth(2, (25 * 270));

 

        writeHeaderLinetab2Report4(sheet);

 

        writeDataLinesTab2Report4(resulttb2, workbook, sheet);

 

        FileOutputStream outputStream = new FileOutputStream(excelFilePath);

        workbook.write(outputStream);

        ps.close();

        outputStream.close();

    } catch (SQLException e) {

        //System.out.println("Datababse error:");

        e.printStackTrace();

    } catch (FileNotFoundException e) {

        exceptie=true;

    } catch (IOException e) {

        //System.out.println("File IO error:");

        e.printStackTrace();

    }

 

}

 

 

    public void generateTab3Report4(){

        conn=ConnectionDatabase.getConnection();

        if(conn== null){

            //System.out.println("Error DB");

        }

 

        String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

 

        try {

 

 

            CallableStatement ps = conn.prepareCall("{call getTab1Report4()}");

            ResultSet result = ps.executeQuery();

 

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Reports");

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

 

            writeHeaderLinetab1Report4(sheet);

 

            writeDataLinesTab1Report4(result, workbook, sheet);

 

 

            CallableStatement ps1 = conn.prepareCall("{call getTab2Report4()}");

            ResultSet resulttb2 = ps1.executeQuery();

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

 

            writeHeaderLinetab2Report4(sheet);

 

            writeDataLinesTab2Report4(resulttb2, workbook, sheet);

 

 

            CallableStatement pstb2 = conn.prepareCall("{call getTab3Report4(?,?,?)}");

            pstb2.setInt(1,id_IF_report);

            pstb2.setInt(2,id_Line_report);

            pstb2.setInt(3,id_Machine_report);

            ResultSet resultttb2 = pstb2.executeQuery();

 

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (23 * 256));

            sheet.setColumnWidth(4, (25 * 270));

            sheet.setColumnWidth(5, (25 * 270));

            sheet.setColumnWidth(6, (23 * 256));

            sheet.setColumnWidth(7, (25 * 270));

            sheet.setColumnWidth(8, (25 * 270));

 

 

            writeHeaderLineTab3Report4(sheet);

 

            writeDataLinesTab3Report4(resultttb2, workbook, sheet);

 

 

 

 

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);

            workbook.write(outputStream);

            pstb2.close();

            outputStream.close();

        } catch (SQLException e) {

            //System.out.println("Datababse error:");

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            exceptie=true;

        } catch (IOException e) {

            //System.out.println("File IO error:");

            e.printStackTrace();

        }

 

    }

 

    public void writeHeaderLine (HSSFSheet sheet){

        rowCount=0;

        Row headerRow = sheet.createRow(rowCount++);

 

        Cell headerCell = headerRow.createCell(0);

        headerCell.setCellValue("No.incidents");

 

        headerCell = headerRow.createCell(1);

        headerCell.setCellValue("Maximum duration");

        headerCell = headerRow.createCell(2);

        headerCell.setCellValue("Minimum duration");

        headerCell = headerRow.createCell(3);

        headerCell.setCellValue("Average duration");

 

 

    }

    public void writeHeaderLineTab1Report2 (HSSFSheet sheet){

        rowCount=0;

 

        Row headerRow = sheet.createRow(rowCount++);

 

        Cell headerCell = headerRow.createCell(0);

        headerCell.setCellValue("No.incidents");

 

        headerCell = headerRow.createCell(1);

        headerCell.setCellValue("Average duration");

        headerCell = headerRow.createCell(2);

        headerCell.setCellValue("Minimum duration");

        headerCell = headerRow.createCell(3);

        headerCell.setCellValue("Maximum duration");

    }

 

 

    public void writeDataLinesTab1Report2 (ResultSet result, HSSFWorkbook workbook,

                                HSSFSheet sheet) throws SQLException {

        

 

        while (result.next()) {

            int nrincidente = result.getInt("Nrincidente");

            String durata_medie = convertMili(result.getLong("Medie"));

            String durata_minima = convertMili(result.getLong("Minim"));

            String durata_maxima = convertMili(result.getLong("Max"));

 

 

            Row row = sheet.createRow(rowCount++);

 

            int columnCount = 0;

            Cell cell = row.createCell(columnCount++);

            cell.setCellValue(nrincidente);

 

 

            cell = row.createCell(columnCount++);

            cell.setCellValue(durata_medie);

 

            cell = row.createCell(columnCount++);

            cell.setCellValue(durata_minima);

 

            cell = row.createCell(columnCount++);

            cell.setCellValue(durata_maxima);

 

            CellStyle cellStyle = workbook.createCellStyle();

            cell.setCellStyle(cellStyle);

 

 

        }

    }

    public void writeDataLines (ResultSet result, HSSFWorkbook workbook,

                                HSSFSheet sheet) throws SQLException {

      

 

        while (result.next()) {

            int nrincidente = result.getInt("Nrincidente");

            String durata_maxima = convertMili(result.getLong("Durata maxima"));

            String durata_minima = convertMili(result.getLong("Durata minima"));

            String durata_medie = convertMili(result.getLong("Durata medie"));

 

 

            Row row = sheet.createRow(rowCount++);

 

            int columnCount = 0;

            Cell cell = row.createCell(columnCount++);

            cell.setCellValue(nrincidente);

 

 

            cell = row.createCell(columnCount++);

            cell.setCellValue(durata_maxima);

 

            cell = row.createCell(columnCount++);

            cell.setCellValue(durata_minima);

 

            cell = row.createCell(columnCount++);

            cell.setCellValue(durata_medie);

 

            CellStyle cellStyle = workbook.createCellStyle();

            CreationHelper creationHelper = workbook.getCreationHelper();

            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

            cell.setCellStyle(cellStyle);

 

 

        }

    }

    public void generateReportForSecondOptionReport1(){

        conn=ConnectionDatabase.getConnection();

       

        //System.out.println(conn);

        String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

 

        try  {

            CallableStatement ps = conn.prepareCall("{call StatusReport1()}");

            ResultSet result = ps.executeQuery();

 

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Reports");

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

            sheet.setColumnWidth(4, (47 * 334));

            sheet.setColumnWidth(5, (23 * 256));

            sheet.setColumnWidth(6, (23 * 256));

            sheet.setColumnWidth(7, (23 * 256));

 

 

            writeHeaderLine(sheet);

 

            writeDataLines(result, workbook, sheet);

 

            CallableStatement pstb2 = conn.prepareCall("{call getOpt(?,?,?,?)}");

            pstb2.setString(1,"5");

            pstb2.setInt(2,id_IF_report);

            pstb2.setInt(3,id_Line_report);

            pstb2.setInt(4,id_Machine_report);

            ResultSet resulttb2 = pstb2.executeQuery();

 

            writeHeaderLineTab2(sheet);

 

            writeDataLinesTab2(resulttb2, workbook, sheet);

 

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);

            workbook.write(outputStream);

            ps.close();

            pstb2.close();

            outputStream.close();

        } catch (SQLException e) {

            //System.out.println("Datababse error:");

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            exceptie=true;

        } catch (IOException e) {

            //System.out.println("File IO error:");

            e.printStackTrace();

        }

 

    }

 

    public void generateReportForThirdOptionReport1(){

        conn=ConnectionDatabase.getConnection();

        if(conn== null){

            //System.out.println("Error DB");

        }

 

        String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

 

        try  {

            CallableStatement ps = conn.prepareCall("{call StatusReport1()}");

            ResultSet result = ps.executeQuery();

 

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Reports");

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

            sheet.setColumnWidth(4, (47 * 334));

            sheet.setColumnWidth(5, (23 * 256));

            sheet.setColumnWidth(6, (23 * 256));

            sheet.setColumnWidth(7, (23 * 256));

 

 

            writeHeaderLine(sheet);

 

            writeDataLines(result, workbook, sheet);

 

            CallableStatement pstb2 = conn.prepareCall("{call getOpt(?,?,?,?)}");

            pstb2.setString(1,"10");

            pstb2.setInt(2,id_IF_report);

            pstb2.setInt(3,id_Line_report);

            pstb2.setInt(4,id_Machine_report);

            ResultSet resulttb2 = pstb2.executeQuery();

 

            writeHeaderLineTab2(sheet);

 

            writeDataLinesTab2(resulttb2, workbook, sheet);

 

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);

            workbook.write(outputStream);

            ps.close();

            pstb2.close();

            outputStream.close();

        } catch (SQLException e) {

            //System.out.println("Datababse error:");

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            exceptie=true;

        } catch (IOException e) {

            //System.out.println("File IO error:");

            e.printStackTrace();

        }

 

    }

 

 

    public void generateReportForThirdOptionReport2(){

        conn=ConnectionDatabase.getConnection();

        if(conn== null){

            //System.out.println("Error DB");

        }

 

        String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

 

        try  {

            CallableStatement ps = conn.prepareCall("{call getTab2Report2(?,?,?,?)}");

            ps.setInt(1,10);

            ps.setInt(2,id_IF_report);

            ps.setInt(3,id_Line_report);

            ps.setInt(4,id_Machine_report);

            ResultSet result = ps.executeQuery();

 

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Reports");

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

            sheet.setColumnWidth(4, (47 * 334));

            sheet.setColumnWidth(5, (23 * 256));

            sheet.setColumnWidth(6, (23 * 256));

 

 

            writeHeaderLineTab2Report2(sheet);

 

            writeDataLinesTab2Report2(result, workbook, sheet);

 

            CallableStatement ps1 = conn.prepareCall("{call getTab1Report2()}");

            ResultSet resultt = ps1.executeQuery();

 

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

 

 

            writeHeaderLineTab1Report2(sheet);

 

            writeDataLinesTab1Report2(resultt, workbook, sheet);

 

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);

            workbook.write(outputStream);

            ps.close();

            ps1.close();

            outputStream.close();

        } catch (SQLException e) {

            //System.out.println("Datababse error:");

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            exceptie=true;

        } catch (IOException e) {

            //System.out.println("File IO error:");

            e.printStackTrace();

        }

 

    }

 

    public void generateReportForSecondOptionReport2(){

        conn=ConnectionDatabase.getConnection();

        if(conn== null){

            //System.out.println("Error DB");

        }

 

        String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

 

        try  {

            CallableStatement ps = conn.prepareCall("{call getTab2Report2(?,?,?,?)}");

            ps.setInt(1,5);

            ps.setInt(2,id_IF_report);

            ps.setInt(3,id_Line_report);

            ps.setInt(4,id_Machine_report);

            ResultSet result = ps.executeQuery();

 

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Reports");

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

            sheet.setColumnWidth(4, (47 * 334));

            sheet.setColumnWidth(5, (23 * 256));

            sheet.setColumnWidth(6, (23 * 256));

 

 

            writeHeaderLineTab2Report2(sheet);

 

            writeDataLinesTab2Report2(result, workbook, sheet);

 

            CallableStatement ps1 = conn.prepareCall("{call getTab1Report2()}");

            ResultSet resultt = ps1.executeQuery();

 

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

 

 

            writeHeaderLineTab1Report2(sheet);

 

            writeDataLinesTab1Report2(resultt, workbook, sheet);

 

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);

            workbook.write(outputStream);

            ps.close();

            ps1.close();

            outputStream.close();

        } catch (SQLException e) {

            //System.out.println("Datababse error:");

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            exceptie=true;

        } catch (IOException e) {

            //System.out.println("File IO error:");

            e.printStackTrace();

        }

 

    }

 

    public void generateReportForAllOptionReport2(){

        conn=ConnectionDatabase.getConnection();

        if(conn== null){

            //System.out.println("Error DB");

        }

 

        String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

 

        try  {

            CallableStatement ps = conn.prepareCall("{call getTab2Report2(?,?,?,?)}");

            ps.setInt(1,-1);

            ps.setInt(2,id_IF_report);

            ps.setInt(3,id_Line_report);

            ps.setInt(4,id_Machine_report);

            ResultSet result = ps.executeQuery();

 

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Reports");

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

            sheet.setColumnWidth(4, (47 * 334));

            sheet.setColumnWidth(5, (23 * 256));

            sheet.setColumnWidth(6, (23 * 256));

 

 

            writeHeaderLineTab2Report2(sheet);

 

            writeDataLinesTab2Report2(result, workbook, sheet);

 

            CallableStatement ps1 = conn.prepareCall("{call getTab1Report2()}");

            ResultSet resultt = ps1.executeQuery();

 

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

 

 

            writeHeaderLineTab1Report2(sheet);

 

            writeDataLinesTab1Report2(resultt, workbook, sheet);

 

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);

            workbook.write(outputStream);

            ps.close();

            ps1.close();

            outputStream.close();

        } catch (SQLException e) {

            //System.out.println("Datababse error:");

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            exceptie=true;

        } catch (IOException e) {

            //System.out.println("File IO error:");

            e.printStackTrace();

        }

 

    }

    public void generateReportForFirstOptionReport2(){

        conn=ConnectionDatabase.getConnection();

        if(conn== null){

            //System.out.println("Error DB");

        }

 

        String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

 

        try  {

            CallableStatement ps = conn.prepareCall("{call getTab2Report2(?,?,?,?)}");

            ps.setInt(1,3);

            ps.setInt(2,id_IF_report);

            ps.setInt(3,id_Line_report);

            ps.setInt(4,id_Machine_report);

            ResultSet result = ps.executeQuery();

 

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Reports");

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

            sheet.setColumnWidth(4, (47 * 334));

            sheet.setColumnWidth(5, (23 * 256));

            sheet.setColumnWidth(6, (23 * 256));

 

 

            writeHeaderLineTab2Report2(sheet);

 

            writeDataLinesTab2Report2(result, workbook, sheet);

 

            CallableStatement ps1 = conn.prepareCall("{call getTab1Report2()}");

            ResultSet resultt = ps1.executeQuery();

 

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

 

 

            writeHeaderLineTab1Report2(sheet);

 

            writeDataLinesTab1Report2(resultt, workbook, sheet);

 

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);

            workbook.write(outputStream);

            ps.close();

            ps1.close();

            outputStream.close();

        } catch (SQLException e) {

            //System.out.println("Datababse error:");

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            exceptie=true;

        } catch (IOException e) {

            //System.out.println("File IO error:");

            e.printStackTrace();

        }

 

    }

 

    public void createDialog(){

        //Creating a dialog

        Dialog<String> dialog = new Dialog<String>();

        //Setting the title

        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/ok.png"));
                                        dialog.setTitle("Success");

        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

        //Setting the content of the dialog

        dialog.setContentText("Your report has been generated on C:\\Data!");

        //Adding buttons to the dialog pane

        dialog.getDialogPane().getButtonTypes().add(type);

        dialog.showAndWait();

 

    }

 

    public void checkbox(JFXCheckBox s){

        if(!s.isSelected()){

            s.setSelected(true);

        } else {

            id_IF_report=-1;

            id_Line_report=-1;

            id_Machine_report=-1;

        }

    }

 

    public void createDialogReports(){

        //Creating a dialog

        Dialog<String> dialog = new Dialog<String>();

        //Setting the title

        Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");

        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

        //Setting the content of the dialog

        dialog.setContentText("There is no IF!");

        //Adding buttons to the dialog pane

        dialog.getDialogPane().getButtonTypes().add(type);

        dialog.showAndWait();

 

    }

    public void DialogReportCheckFile(){

        //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Excel file is already open! Please close it before generating another one.");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
                
        
        return;

    }

 

    public void generateReport5() {

    conn=ConnectionDatabase.getConnection();

  

    String excelFilePath = "C:\\Data\\ "  + nameReport(selectreport)+ "_" + datepickerstartreports.getValue().toString() + "-" + datepickerendreports.getValue().toString() + "_"+ (id_IF_report == -1 ? "" : "IF" ) + checkrep(id_IF_report) + "_" + (id_Line_report == -1 ? "" : "Line" )+ checkrep(id_Line_report) + "_" + (id_Machine_report == -1 ? "" : "Machine" ) + checkrep(id_Machine_report) + ".xls";

 

    try  {

        if(conn!=null)

        {

            CallableStatement ps = conn.prepareCall("{call getTab1Report5()}");

            ResultSet result = ps.executeQuery();

 

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("Reports");

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

            sheet.setColumnWidth(4, (47 * 334));

            sheet.setColumnWidth(5, (23 * 256));

            sheet.setColumnWidth(6, (23 * 256));

 

            writeHeaderLinetab1Report5(sheet);

            writeDataLinesTab1Report5(result, workbook, sheet);

 

            CallableStatement ps1 = conn.prepareCall("{call getTab2Report5()}");

            ResultSet resulttb2 = ps1.executeQuery();

 

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

            sheet.setColumnWidth(4, (47 * 334));

            sheet.setColumnWidth(5, (23 * 256));

 

 

            writeHeaderLinetab2Report5(sheet);

 

            writeDataLinesTab2Report5(resulttb2, workbook, sheet);

 

 

            CallableStatement pstb2 = conn.prepareCall("{call getTab3Report5(?,?,?,?)}");

            pstb2.setInt(1,id_IF_report);

            pstb2.setInt(2,id_Line_report);

            pstb2.setInt(3,id_Machine_report);

            pstb2.setInt(4,id_User);

            ResultSet resultttb2 = pstb2.executeQuery();

 

            sheet.setColumnWidth(0, (23 * 256));

            sheet.setColumnWidth(1, (25 * 270));

            sheet.setColumnWidth(2, (25 * 270));

            sheet.setColumnWidth(3, (25 * 270));

 

            writeHeaderLinetab3Report5(sheet);

 

            writeDataLinesTab3Report5(resultttb2, workbook, sheet);

 

 

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);

            workbook.write(outputStream);

            ps.close();

            ps1.close();

            outputStream.close();

        }

        else

        {

            //System.out.println("neok");

        }

       

       

        

        } catch (SQLException e) {

            //System.out.println("Datababse error:");

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            exceptie=true;

        } catch (IOException e) {

            //System.out.println("File IO error:");

            e.printStackTrace();

        }

    }

 

 

    public String nameReport(String namerepo)

    {

            if(selectreport.equals("MTTR") || selectreport.equals("IncOver") || selectreport.equals("EscOver")

                    || selectreport.equals("Spareparts")|| selectreport.equals("MTBF")) {

                return namerepo;

            }

                return null;

    }

 

 

    public void onGenerateReport()

    {

       

        

        if (selectreport.equals("MTTR"))

        {

            //setIf();

 

            //System.out.println("MTTR REPORT");

            if(datepickerstartreports.getEditor().getText().isEmpty() || datepickerendreports.getEditor().getText().isEmpty())

            {

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

                return;

            }

           if(datepickerstartreports.getValue().isAfter(datepickerendreports.getValue())){

               //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Start date is bigger than end date!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();

                return;

            }

            if (comboifreports.getSelectionModel().isEmpty()==true)

            {

                createDialogReports();

            }

            else

            {

                //System.out.println(id_IF_report);

 

                if (firstoptiontopreports.isSelected() == false && secondoptiontopreports.isSelected() == false && thirdoptiontopreports.isSelected() == false && alloptiontopreports.isSelected() == false)

                {

                    generateReportForAllOptionReport1();

                    //System.out.println("NO if, NO top");

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

 

                } else if (firstoptiontopreports.isSelected() == true && topbtnradio.getSelectedToggle().isSelected() == true)

                {

                    generateReportForFirstOptionReport1();

                    //System.out.println("NO if, Yes 3");

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

                }

              else if (secondoptiontopreports.isSelected() == true && topbtnradio.getSelectedToggle().isSelected() == true) {

                    generateReportForSecondOptionReport1();

                    //System.out.println("NO if, Yes 5");

                    if(exceptie == true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else if(exceptie==false) {

                        createDialog();

                    }

                } else if (thirdoptiontopreports.isSelected() == true && topbtnradio.getSelectedToggle().isSelected() && topbtnradio.getSelectedToggle().isSelected() == true) {

                    generateReportForThirdOptionReport1();

                    //System.out.println("NO if, Yes 10");

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

                } else if (alloptiontopreports.isSelected() == true && topbtnradio.getSelectedToggle().isSelected()) {

                    generateReportForAllOptionReport1();

                    //System.out.println("NO if, Yes all");

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

                }

            }

 

        }

 

        if (selectreport.equals("IncOver"))

        {

            //setIf();

            //System.out.println("IncOver");

            if(datepickerstartreports.getEditor().getText().isEmpty() || datepickerendreports.getEditor().getText().isEmpty())

            {

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

                return;

            }

            if(datepickerstartreports.getValue().isAfter(datepickerendreports.getValue())){

                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Start date is bigger than end date!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();

                return;

            }

            if (comboifreports.getSelectionModel().isEmpty()==true)

           {

               createDialogReports();

            }

            else {

                if(nooptiondetailsreports.isSelected()==true){

                    generateTab2Report3();

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

                } else if(yesoptiondetailsreports.isSelected()==true) {

                    generateTab1Report3();

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

                }

                else if(nooptiondetailsreports.isSelected()==false && nooptiondetailsreports.isSelected()==false)

                {

                    generateTab2Report3();

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

                }

            }

 

 

        }

 

        if(selectreport.equals("EscOver"))

        {

            //setIf();

            //System.out.println("EscOver");

            if(datepickerstartreports.getEditor().getText().isEmpty() || datepickerendreports.getEditor().getText().isEmpty())

            {

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

                return;

            }

            if(datepickerstartreports.getValue().isAfter(datepickerendreports.getValue())){

                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Start date is bigger than end date!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();

                return;

            }

            if (comboifreports.getSelectionModel().isEmpty()==true)

            {

                if(exceptie ==true)

                {

                    DialogReportCheckFile();

                    exceptie=false;

                }

                else

                {

                    createDialog();

                }

            }

            else

            {

                generateReport5();

                if(exceptie ==true)

                {

                    DialogReportCheckFile();

                    exceptie=false;

                }

                else

                {

                    createDialog();

                }

            }

 

        }

 

        if (selectreport.equals("Spareparts"))

        {

           

            //setIf();

            //System.out.println("Spareparts");

            if(datepickerstartreports.getEditor().getText().isEmpty() || datepickerendreports.getEditor().getText().isEmpty())

            {

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

                return;

            }

            if(datepickerstartreports.getValue().isAfter(datepickerendreports.getValue())){

                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Start date is bigger than end date!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();

                return;

            }

            if (comboifreports.getSelectionModel().isEmpty()==true)

            {

                createDialogReports();

            }

            else {

                if(nooptiondetailsreports.isSelected()==true){

                    generateTab1Report4();

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

                } else if(yesoptiondetailsreports.isSelected()==true) {

                    generateTab3Report4();

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

 

                }

                else if(nooptiondetailsreports.isSelected()==false && nooptiondetailsreports.isSelected()==false)

                {

                    generateTab1Report4();

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

                }

            }

 

        }

        if (selectreport.equals("MTBF"))

        {

            //setIf();

            //comboifreports.setValue("testare");

            //System.out.println("MTBF");

            if(datepickerstartreports.getEditor().getText().isEmpty() || datepickerendreports.getEditor().getText().isEmpty())

            {

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

                return;

            }

            if(datepickerstartreports.getValue().isAfter(datepickerendreports.getValue())){

                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("Start date is bigger than end date!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();

                return;

            }

            if (comboifreports.getSelectionModel().isEmpty()==true)

 

            {   //createDialog();- empty line,if

                createDialogReports();

            }

            else

            {

                //System.out.println(id_IF_report);

 

                if (firstoptiontopreports.isSelected() == false && secondoptiontopreports.isSelected() == false && thirdoptiontopreports.isSelected() == false && alloptiontopreports.isSelected() == false)

                {

                    generateReportForAllOptionReport2();

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

                } else if (firstoptiontopreports.isSelected() == true && topbtnradio.getSelectedToggle().isSelected() == true)

                {

                    generateReportForFirstOptionReport2();

                    //System.out.println("NO if, Yes 3");

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

                }

                else if (secondoptiontopreports.isSelected() == true && topbtnradio.getSelectedToggle().isSelected() == true) {

 

                    generateReportForSecondOptionReport2();

                    //System.out.println("NO if, Yes 5");

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

                } else if (thirdoptiontopreports.isSelected() == true && topbtnradio.getSelectedToggle().isSelected() && topbtnradio.getSelectedToggle().isSelected() == true) {

 

                    generateReportForThirdOptionReport2();

                    //System.out.println("NO if, Yes 10");

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        createDialog();

                    }

                } else if (alloptiontopreports.isSelected() == true && topbtnradio.getSelectedToggle().isSelected()) {

 

                    generateReportForAllOptionReport2();

                    //System.out.println("NO if, Yes all");

                    if(exceptie ==true)

                    {

                        DialogReportCheckFile();

                        exceptie=false;

                    }

                    else

                    {

                        if(exceptie ==true)

                        {

                            DialogReportCheckFile();

                            exceptie=false;

                        }

                        else

                        {

                            createDialog();

                        }

                    }

                }

            }

 

        }

    }
    //-------------------------------------------------------------------DIAGRAMS--------------------------------------------------------------------------------
    CategoryAxis xAxis = new CategoryAxis(); 
    NumberAxis yAxis = new NumberAxis(0, 110, 1); 
     @FXML
    private Tab tabDiagram;
    
     @FXML
    private AreaChart< String, Number> areachart=new AreaChart(xAxis, yAxis);
     
     ObservableList<String> itemscomboifdiagram=FXCollections.observableArrayList();
     ObservableList<String> itemsdiagram=FXCollections.observableArrayList("ok","neok");
     Map<String,HashMap<String,Integer>> data_diagram=new HashMap<>();
     
      @FXML
    private ComboBox<String> comboifd;
    XYChart.Series series1;
    XYChart.Series series2;
    int idIf_diagram=-1;
      
       @FXML
    void onComboIfD(ActionEvent event) 
    {
        //System.out.println("combo");
        //int ifid=ConnectionDatabase.getIfId(comboifd.getValue().toString());
        if(comboifd.getValue().toString().equals("Internal Factory 1") && comboifd.getSelectionModel().isEmpty()==false)
        {
            makingChartIF1(1);
        }
        else if(comboifd.getValue().toString().equals("Internal Factory 2")&& comboifd.getSelectionModel().isEmpty()==false)
        {
            areachart.getData().clear();
            makingChartIF1(2);
        }
        else if(comboifd.getValue().toString().equals("Internal Factory 3")&& comboifd.getSelectionModel().isEmpty()==false)
        {
            areachart.getData().clear();
            makingChartIF1(3);
        }
        else if(comboifd.getValue().toString().equals("Internal Factory 4")&& comboifd.getSelectionModel().isEmpty()==false)
        {
            areachart.getData().clear();
            makingChartIF1(4);
        }
        else if(comboifd.getValue().toString().equals("Internal Factory 5")&& comboifd.getSelectionModel().isEmpty()==false)
        {
            areachart.getData().clear();
            makingChartIF1(5);
        }
        
        
    }
     
     public void makingChartIF1(int ifid)
     {
            data_diagram =ConnectionDatabase.getDataDiagram(ifid);
            
            if(data_diagram.isEmpty()==false)
            {
                
                areachart.setVisible(true);
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run() {
                            List<XYChart.Series> series=new ArrayList<>();


                            areachart.setTitle("Number of incidents during one week for every line");   

                             for (Map.Entry<String,HashMap<String,Integer>> entry : data_diagram.entrySet()) 
                             {
                                //System.out.println(entry.getKey() + ":" + entry.getValue());
                                XYChart.Series serie=new XYChart.Series();
                                serie.setName(entry.getKey());
                               //System.out.println("LINE: "+entry.getKey() +" DATA FOR LINE: \n");
                                for(Map.Entry<String,Integer> entry1 : entry.getValue().entrySet())
                                {
                                    serie.getData().add(new XYChart.Data(entry1.getKey(), entry1.getValue())); 
                                    //System.out.println(entry1.getValue());
                                }

                                series.add(serie);
                            }

                            //System.out.println(series);
                            for(XYChart.Series s:series)
                            {
                                areachart.getData().add(s);
                            }

                            }
                    
                    
                });
                

            }
            
            
            
            
     }

  
     public void onDiagramView()
     {
          if(tabDiagram.isSelected()==true)
          {
              
                
                
                areachart.setVisible(false);
              
         
                if(comboifd.getSelectionModel().isEmpty()==true)
                      {
                             comboifd.setValue("Select...");
                             itemscomboifdiagram=ConnectionDatabase.getIFCombo();
                             if(!itemscomboifdiagram.isEmpty())
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
                                 Collections.sort(itemscomboifdiagram,c);
                                 itemscomboifdiagram.add(0, "Select...");
                                 comboifd.setItems(itemscomboifdiagram); 

                                 String ifinit="[1] : "+itemscomboifover.get(1);
                                 String msgif="OUTPUT LIST OF IFs from DIAGRAM VIEW: "+" \n"+ifinit+" \n";
                                 for(int i=2;i<itemscomboifdiagram.size();i++)
                                 {
                                     msgif=msgif+ "[" + i +"]" + " : " + itemscomboifdiagram.get(i) + "\n";

                                 }
                                 logger.info(Log4jConfiguration.currentTime() + "[INFO] : "+msgif);

                             }

                      }
                }
          else if(tabDiagram.isSelected()==false)
          {
              //comboifd.getSelectionModel().clearSelection();
              //comboifd.setValue("Select...");
              areachart.getData().clear();
          }

     }
    
    public void setFieldsForEscalation()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Setting things for ESCALATION TAB \n");
        savebtn_escalation.setVisible(false);
        saveicon_escalation.setVisible(false);
        if_escalation.setVisible(false);
        line_escalation.setVisible(false);
        machine_escalation.setVisible(false);
        start_date_escalation.setVisible(false);
        end_date_escalation.setVisible(false);
        status_escalation.setVisible(false);
        creator_user_escalation.setVisible(false);
        saporder_escalation.setVisible(false);
        escalation_user.setVisible(false);
        pbdesc_escalation.setVisible(false);
        hmi_pbescalation.setVisible(false); 
        part_number_escalation.setVisible(false);
        sol_creator_escalation.setVisible(false);
        sol_escalation.setVisible(false);
        if_text.setVisible(false);
        line_text.setVisible(false);  
        machine_text.setVisible(false);
        sd_text.setVisible(false);
        ed_text.setVisible(false);
        status_text.setVisible(false);
        creator_user_text.setVisible(false);
        sap_text.setVisible(false);
        escal_user_text.setVisible(false);
        pbdesc_text.setVisible(false);
        hmi_text.setVisible(false);
        part_numbere_text.setVisible(false);
        sol_creator_text.setVisible(false);
        sol_escal_text.setVisible(false);
    }
    public void setLayoutThingsForIncidentHistoryTable()
    {
        table_incident_history.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        col_ih_start_date.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_ih_enddate.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_ih_status.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_ih_sap.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_ih_solcreator.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_ih_solescal.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_ih_user.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_ih_engineer.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_ih_spare.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        col_ih_sh_sol.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_ih_file.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        
        col_ih_start_date.setCellFactory(new Callback<TableColumn<IncidentHistory, String>,TableCell<IncidentHistory,String>>() {

                @Override

                public TableCell<IncidentHistory, String> call(TableColumn<IncidentHistory, String> param) {

                    TableCell<IncidentHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_ih_start_date.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }
        });
        
        col_ih_enddate.setCellFactory(new Callback<TableColumn<IncidentHistory, String>,TableCell<IncidentHistory,String>>() {

                @Override

                public TableCell<IncidentHistory, String> call(TableColumn<IncidentHistory, String> param) {

                    TableCell<IncidentHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_ih_enddate.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        
        col_ih_status.setCellFactory(new Callback<TableColumn<IncidentHistory, String>,TableCell<IncidentHistory,String>>() {

                @Override

                public TableCell<IncidentHistory, String> call(TableColumn<IncidentHistory, String> param) {

                    TableCell<IncidentHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_ih_status.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        
        
        col_ih_solcreator.setCellFactory(new Callback<TableColumn<IncidentHistory, String>,TableCell<IncidentHistory,String>>() {

                @Override

                public TableCell<IncidentHistory, String> call(TableColumn<IncidentHistory, String> param) {

                    TableCell<IncidentHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_ih_solcreator.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        
        
         col_ih_solescal.setCellFactory(new Callback<TableColumn<IncidentHistory, String>,TableCell<IncidentHistory,String>>() {

                @Override

                public TableCell<IncidentHistory, String> call(TableColumn<IncidentHistory, String> param) {

                    TableCell<IncidentHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_ih_solescal.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
         
         
         col_ih_user.setCellFactory(new Callback<TableColumn<IncidentHistory, String>,TableCell<IncidentHistory,String>>() {

                @Override

                public TableCell<IncidentHistory, String> call(TableColumn<IncidentHistory, String> param) {

                    TableCell<IncidentHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_ih_user.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
         
         
         col_ih_engineer.setCellFactory(new Callback<TableColumn<IncidentHistory, String>,TableCell<IncidentHistory,String>>() {

                @Override

                public TableCell<IncidentHistory, String> call(TableColumn<IncidentHistory, String> param) {

                    TableCell<IncidentHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_ih_engineer.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
         
         col_ih_spare.setCellFactory(new Callback<TableColumn<IncidentHistory, String>,TableCell<IncidentHistory,String>>() {

                @Override

                public TableCell<IncidentHistory, String> call(TableColumn<IncidentHistory, String> param) {

                    TableCell<IncidentHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_ih_spare.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
         
         col_ih_sh_sol.setCellFactory(new Callback<TableColumn<IncidentHistory, String>,TableCell<IncidentHistory,String>>() {

                @Override

                public TableCell<IncidentHistory, String> call(TableColumn<IncidentHistory, String> param) {

                    TableCell<IncidentHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_ih_sh_sol.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
         
         
        
        
        
        
    }
    public void setLayoutThingsForProblemHistoryTable()
    {
        //setting for table
        table_problem_history.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        col_line_pbhistory.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_mach_pbhistory.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_user_pbhistory.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_status_pbhistory.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_hmi_pbhistory.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_enginner_pbhistory.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_pbdesc_pbhistory.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_file_pbhistory.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_nrincidents_pbhistory.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        
        col_line_pbhistory.setCellFactory(new Callback<TableColumn<ProblemHistory, String>,TableCell<ProblemHistory,String>>() {

                @Override

                public TableCell<ProblemHistory, String> call(TableColumn<ProblemHistory, String> param) {

                    TableCell<ProblemHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_line_pbhistory.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

 

        });
        col_mach_pbhistory.setCellFactory(new Callback<TableColumn<ProblemHistory, String>,TableCell<ProblemHistory,String>>() {

                @Override

                public TableCell<ProblemHistory, String> call(TableColumn<ProblemHistory, String> param) {

                    TableCell<ProblemHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_mach_pbhistory.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

 

 

        });
        col_user_pbhistory.setCellFactory(new Callback<TableColumn<ProblemHistory, String>,TableCell<ProblemHistory,String>>() {

                @Override

                public TableCell<ProblemHistory, String> call(TableColumn<ProblemHistory, String> param) {

                    TableCell<ProblemHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_user_pbhistory.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

 

 

        });
        col_status_pbhistory.setCellFactory(new Callback<TableColumn<ProblemHistory, String>,TableCell<ProblemHistory,String>>() {

                @Override

                public TableCell<ProblemHistory, String> call(TableColumn<ProblemHistory, String> param) {

                    TableCell<ProblemHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_status_pbhistory.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

 

 

        });
        col_hmi_pbhistory.setCellFactory(new Callback<TableColumn<ProblemHistory, String>,TableCell<ProblemHistory,String>>() {

                @Override

                public TableCell<ProblemHistory, String> call(TableColumn<ProblemHistory, String> param) {

                    TableCell<ProblemHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_hmi_pbhistory.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

 

 

        });
        col_enginner_pbhistory.setCellFactory(new Callback<TableColumn<ProblemHistory, String>,TableCell<ProblemHistory,String>>() {

                @Override

                public TableCell<ProblemHistory, String> call(TableColumn<ProblemHistory, String> param) {

                    TableCell<ProblemHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_enginner_pbhistory.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

 

 

        });
        col_pbdesc_pbhistory.setCellFactory(new Callback<TableColumn<ProblemHistory, String>,TableCell<ProblemHistory,String>>() {

                @Override

                public TableCell<ProblemHistory, String> call(TableColumn<ProblemHistory, String> param) {

                    TableCell<ProblemHistory, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_pbdesc_pbhistory.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }


        });
    }
    
    public void setLayoutThingsForIfStructureTable()
    {
        table_if_structure.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        col_name_if.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_desc_if.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_cc_if.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        
        col_name_if.setCellFactory(new Callback<TableColumn<IfStruct, String>,TableCell<IfStruct,String>>() {

                @Override

                public TableCell<IfStruct, String> call(TableColumn<IfStruct, String> param) {

                    TableCell<IfStruct, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_name_if.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        col_desc_if.setCellFactory(new Callback<TableColumn<IfStruct, String>,TableCell<IfStruct,String>>() {

                @Override

                public TableCell<IfStruct, String> call(TableColumn<IfStruct, String> param) {

                    TableCell<IfStruct, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_desc_if.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
       
        
        
    }
    
    public void setLayoutThingsForLineStructTable()
    {
        table_line_structure.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        col_name_line.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_desc_line.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        
        col_name_line.setCellFactory(new Callback<TableColumn<LineStruct, String>,TableCell<LineStruct,String>>() {

                @Override

                public TableCell<LineStruct, String> call(TableColumn<LineStruct, String> param) {

                    TableCell<LineStruct, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_name_line.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        
         col_desc_line.setCellFactory(new Callback<TableColumn<LineStruct, String>,TableCell<LineStruct,String>>() {

                @Override

                public TableCell<LineStruct, String> call(TableColumn<LineStruct, String> param) {

                    TableCell<LineStruct, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_desc_line.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        
    }
    public void setLayoutThingsForMachineStructTable()
    {
        table_machine_structure.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        col_name_machine.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_invnr_machine.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_name_machine.setCellFactory(new Callback<TableColumn<MachineStruct, String>,TableCell<MachineStruct,String>>() {

                @Override

                public TableCell<MachineStruct, String> call(TableColumn<MachineStruct, String> param) {

                    TableCell<MachineStruct, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_name_machine.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        
        col_invnr_machine.setCellFactory(new Callback<TableColumn<MachineStruct, String>,TableCell<MachineStruct,String>>() {

                @Override

                public TableCell<MachineStruct, String> call(TableColumn<MachineStruct, String> param) {

                    TableCell<MachineStruct, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_invnr_machine.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
       
    }
    
    public void setForLayoutTableTech()
    {
        table_shift_team.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colu_numeteh_shifteam.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_job_desc.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_shift_username.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_email_shift_team.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        
        colu_numeteh_shifteam.setCellFactory(new Callback<TableColumn<ShTeam, String>,TableCell<ShTeam,String>>() {

                @Override

                public TableCell<ShTeam, String> call(TableColumn<ShTeam, String> param) {

                    TableCell<ShTeam, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(colu_numeteh_shifteam.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        
        colu_numeteh_shifteam.setCellFactory(new Callback<TableColumn<ShTeam, String>,TableCell<ShTeam,String>>() {

                @Override

                public TableCell<ShTeam, String> call(TableColumn<ShTeam, String> param) {

                    TableCell<ShTeam, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(colu_numeteh_shifteam.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        
        col_shift_username.setCellFactory(new Callback<TableColumn<ShTeam, String>,TableCell<ShTeam,String>>() {

                @Override

                public TableCell<ShTeam, String> call(TableColumn<ShTeam, String> param) {

                    TableCell<ShTeam, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_shift_username.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        
        col_email_shift_team.setCellFactory(new Callback<TableColumn<ShTeam, String>,TableCell<ShTeam,String>>() {

                @Override

                public TableCell<ShTeam, String> call(TableColumn<ShTeam, String> param) {

                    TableCell<ShTeam, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_email_shift_team.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
                
        
    }
    public void setLayoutThingsForEngineersTable()
    {
        table_escalation_team.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        col_name_escalation_team.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_job_escalation_team.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_user_escalation_team.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_email_escalation_team.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        
        col_email_escalation_team.setCellFactory(new Callback<TableColumn<EscalationTeam, String>,TableCell<EscalationTeam,String>>() {

                @Override

                public TableCell<EscalationTeam, String> call(TableColumn<EscalationTeam, String> param) {

                    TableCell<EscalationTeam, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_email_escalation_team.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        col_job_escalation_team.setCellFactory(new Callback<TableColumn<EscalationTeam, String>,TableCell<EscalationTeam,String>>() {

                @Override

                public TableCell<EscalationTeam, String> call(TableColumn<EscalationTeam, String> param) {

                    TableCell<EscalationTeam, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_job_escalation_team.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        col_user_escalation_team.setCellFactory(new Callback<TableColumn<EscalationTeam, String>,TableCell<EscalationTeam,String>>() {

                @Override

                public TableCell<EscalationTeam, String> call(TableColumn<EscalationTeam, String> param) {

                    TableCell<EscalationTeam, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_user_escalation_team.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        col_email_escalation_team.setCellFactory(new Callback<TableColumn<EscalationTeam, String>,TableCell<EscalationTeam,String>>() {

                @Override

                public TableCell<EscalationTeam, String> call(TableColumn<EscalationTeam, String> param) {

                    TableCell<EscalationTeam, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_email_escalation_team.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
    }
    public void setThingsForSpareParts()
    {
        table_spare_parts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        col_name_spare.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_spare_id.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_des_spare.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_det_spare.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        
        col_name_spare.setCellFactory(new Callback<TableColumn<SpParts, String>,TableCell<SpParts,String>>() {

                @Override

                public TableCell<SpParts, String> call(TableColumn<SpParts, String> param) {

                    TableCell<SpParts, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_name_spare.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        col_spare_id.setCellFactory(new Callback<TableColumn<SpParts, String>,TableCell<SpParts,String>>() {

                @Override

                public TableCell<SpParts, String> call(TableColumn<SpParts, String> param) {

                    TableCell<SpParts, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_spare_id.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        
        col_des_spare.setCellFactory(new Callback<TableColumn<SpParts, String>,TableCell<SpParts,String>>() {

                @Override

                public TableCell<SpParts, String> call(TableColumn<SpParts, String> param) {

                    TableCell<SpParts, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_des_spare.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;

                }

        });
        
        col_det_spare.setCellFactory(new Callback<TableColumn<SpParts, String>,TableCell<SpParts,String>>() {

                @Override

                public TableCell<SpParts, String> call(TableColumn<SpParts, String> param) {

                    TableCell<SpParts, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_det_spare.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;
                }
        });
        
    }
    public void setLayoutForAccess()
    {
        table_access.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        col_user_access.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        col_role_access.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        
         col_user_access.setCellFactory(new Callback<TableColumn<Access, String>,TableCell<Access,String>>() {

                @Override

                public TableCell<Access, String> call(TableColumn<Access, String> param) {

                    TableCell<Access, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_user_access.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;
                }
        });
         
        col_role_access.setCellFactory(new Callback<TableColumn<Access, String>,TableCell<Access,String>>() {

                @Override

                public TableCell<Access, String> call(TableColumn<Access, String> param) {

                    TableCell<Access, String> cell = new TableCell<>();

                    Text text=new Text();

                    cell.setGraphic(text);

                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);

                    text.wrappingWidthProperty().bind(col_role_access.widthProperty());

                    text.textProperty().bind(cell.itemProperty());

                    return cell;
                }
        });
        
    }
    
    
    
    public void setForAdmin()
    {
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Setting things for ADMIN TAB \n");
        
        setLayoutThingsForProblemHistoryTable();
        setLayoutThingsForIncidentHistoryTable();
        setLayoutThingsForIfStructureTable();
        setLayoutThingsForLineStructTable();
        setLayoutThingsForMachineStructTable();
        setForLayoutTableTech();
        setLayoutThingsForEngineersTable();
        setThingsForSpareParts();
        setLayoutForAccess();
                
        editbtnproblemhistory.setGraphic(pbh_editimage);
        deletebtn_problem_history.setGraphic(pbhistory_deleteicon);
        editbtn_incident.setGraphic(editiconincident);
        deletebtn_incident.setGraphic(deleteiconincident);
        searchbtn.setGraphic(searchicon);
        searchbtnprbhistory.setGraphic(searchiconprbhistory);
        
        add_btn_if_struct.setGraphic(addiconif);
        edit_btn_if_struct.setGraphic(editiconif);
        deletebtnif.setGraphic(deleteiconif);
        
        addbtn_line.setGraphic(addiconline);
        editbtn_line.setGraphic(editiconline);
        deletebtn_line.setGraphic(deleteiconline);
        addbtn_machine.setGraphic(addiconmachine);
        
        editbtnmachine.setGraphic(editiconmachine);
        deletemachine.setGraphic(deleteiconmachine);
        addbtn_machine.setGraphic(addiconmachine);
        editbtnmachine.setGraphic(editiconmachine);
        deletemachine.setGraphic(deleteiconmachine);
        search_button_machine.setGraphic(searchiconmachine);
        
        //addbtnspare.setVisible(false);
        //editbtnspare.setVisible(false);
        //addiconspare.setVisible(false);
        //editiconspare.setVisible(false);
        
        add_button_shiftteam.setGraphic(addicontech);
        edit_btn_shiftteam.setGraphic(editicontech);
        del_btnshiftteam.setGraphic(deleteicontech);
        
        add_btn_escalation_team.setGraphic(addiconesc);
        edit_btn_escalation_team.setGraphic(editiconesc);
        delete_btn_escalation_team.setGraphic(deleteiconesc);
        
        searchbtnspare.setGraphic(searchiconspare);
        addbtnspare.setGraphic(addiconspare);
        editbtnspare.setGraphic(editiconspare);
        deletebtnspare.setGraphic(deleteiconspare);
        
       
        editbtnacces.setGraphic(editiconacces);
        deletebtnaccess.setGraphic(deleteiconaccess);
        
        search_button_machine.setGraphic(machinestructsearchicon);
        downloadbtn.setGraphic(donwloadfileicon);
        
        rightClick();
        onProblemHistory();
        
    }
    public void setThingsForOverview()
    {
        onOverview();
    }
    
    public static String Time()
    {
        DateFormat df = new SimpleDateFormat("HH:mm");
        java.util.Date dateobj = new java.util.Date();
        String str=df.format(dateobj);
        return str;
    }
    public void doubleClickOverview()
    {
        if(incidentsOverview==null)
                    {
                                try {
                                incidentsOverview=new Stage();
                                Parent root = FXMLLoader.load(getClass().getResource("/fxml/incidentoverview.fxml"));
                                Scene scene = new Scene(root);

                                incidentsOverview.setTitle("Incidents overview");
                                incidentsOverview.initStyle(StageStyle.UTILITY);
                                incidentsOverview.setScene(scene);
                                incidentsOverview.setResizable(false);
                                incidentsOverview.show();
                                incidentsOverview.setOnHidden(e->{
                                   onSearchOverview();
                                   table_overview.getSelectionModel().clearSelection();

                                });
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                    }
                    else if(incidentsOverview.isShowing())
                    {
                        //incidentsOverview.toFront();
                         //Creating a dialog
                        Dialog<String> dialog = new Dialog<String>();
                        //Setting the title
                        dialog.setTitle("Warn message");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Setting the content of the dialog
                        dialog.setContentText("A panel 'Incidents Overview' with unsaved actions on him is already open in the background. Please close it and select another problem from the table.");
                        //Adding buttons to the dialog pane
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.showAndWait();
                        
                    }
                    else
                    {
                       try {
                                incidentsOverview=new Stage();
                                Parent root = FXMLLoader.load(getClass().getResource("/fxml/incidentoverview.fxml"));
                                Scene scene = new Scene(root);

                                incidentsOverview.setTitle("Incidents overview");
                                incidentsOverview.initStyle(StageStyle.UTILITY);
                                incidentsOverview.setScene(scene);
                                incidentsOverview.setResizable(false);
                                incidentsOverview.show();
                                incidentsOverview.setOnHidden(e->{
                                   onSearchOverview();

                                });
                            } catch (IOException ex) {
                                logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCE TOOL CONTROLLER --- IOException: "+ex);
                                
                            }
                    }
                    
                        
                
    }
    public void setDoubleClickOnTableOverview()
    {
        
        //table_overview.setOnMouseClicked(new EventHandler<MouseEvent>() {
           //  @Override
            // public void handle(MouseEvent mouseEvent) {
              //   if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                     
                     //System.out.println("Numar: "+ mouseEvent.getClickCount());  
                     /*if(mouseEvent.getClickCount() == 2 && mouseEvent.getClickCount()!=1)
                     {
                         //System.out.println("unu");
                         System.out.println("Numar if2: "+ mouseEvent.getClickCount());  
                         
                     }*/
                     
                    /* if(mouseEvent.getClickCount() >= 1)
                     {
                              
                              if(mouseEvent.getClickCount() == 1)
                               {
                                   System.out.println("Numar if1: "+ mouseEvent.getClickCount());
                               }
                              else if(mouseEvent.getClickCount() == 2)
                              {
                                    System.out.println("Numar if2: "+ mouseEvent.getClickCount());  
                              }
                              
                         
                     }
                     
                 }
             }
         });*/
                    
         
         
         //TRIES
         /*table_overview.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent mouseEvent) {
                 if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                     
                     //System.out.println("Numar: "+ mouseEvent.getClickCount());  
                     if(mouseEvent.getClickCount() == 1)
                     {
                         System.out.println("unu");
                         
                     }
                     
                     else if(mouseEvent.getClickCount() == 2)
                     {
                        
                              System.out.println("al doilea click");
                        
                         
                         //onSelectProblem();
                         
                     }
                     //System.out.println("Numar: "+ mouseEvent.getClickCount());
                         
                     
                     //if(mouseEvent.getClickCount() == 1){
                      //   System.out.println("one clicked");
                         //onSelectProblem();
                         

                     //}
                    /* if(mouseEvent.getClickCount() == 2){
                         System.out.println("Double clicked");
                         //getDataForDouble();
                         //doubleClickOverview();

                     }
                     else 
                     {
                            //onSelectProblem();
                            //incidentoverview();
                            System.out.println("one click");
                     }*/
              //   }
            // }
         //});
        /*
        table_overview.setRowFactory( tv -> {
        TableRow<Overview> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Overview rowData = row.getItem();
                    System.out.println(rowData);
                }
                else
                {
                    //Overview rowData = row.getItem();
                   //System.out.println(rowData);
                    //index=table_overview.getSelectionModel().getSelectedIndex();
                    //System.out.println(index);
                    //onSelectProblem();
                    index=table_overview.getSelectionModel().getSelectedIndex();
                    if(index <= -1)
                    {
                        logger.error(Log4jConfiguration.currentTime()+" [ERROR]: Out of index error in OVERVIEW table!");
                        return;

                    }
                    Overview over=table_overview.getSelectionModel().getSelectedItem();

                    Data.id_pb=over.getId();

                    String problem_file=ConnectionDatabase.getProblemFileName(over.getId());
                    
                    logger.info("PROBLEM FILE: "+problem_file);
        
                    Data.problem_file_path=problem_file;

                    logger.info(Log4jConfiguration.currentTime() + " [INFO]: The id of the problem selected from OVERVIEW: "+Data.id_pb);

                    idPb=over.getId();

                    String file=ConnectionDatabase.getFilePathfromProblem(over.getId());
                    logger.info(Log4jConfiguration.currentTime()+" [INFO]: The file of the problem selected in OVERVIEW: "+file);

                    Directory=file;
                    
                    //filesIncidents=ConnectionDatabase.getIncidentsFiles(over.getId());
                    //if(filesIncidents.isEmpty()==false)
                    //{
                        //logger.info(Log4jConfiguration.currentTime()+" [INFO]: The files for all incidents of the problem in OVERVIEW: "+filesIncidents);
                   // }
                    //else
                    //{
                        //logger.warn(Log4jConfiguration.currentTime()+"[WARN]: Empty list of files for incidents of the problem selected in OVERVIEW!");
                    //}
                    
                }
            });
            return row ;
        });*/
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: MAINTENANCE TOOL CONTROLLER STARTED! \n\n");
        
       
        String finalrole=null;
        
        String compuser=System.getProperty("user.name");
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The USERNAME OF THIS COMPUTER IS: "+compuser+"\n");
        
        String RoleCompUser=ConnectionDatabase.getRolefromAccess(compuser);
        
        if(RoleCompUser!=null)
        {
           finalrole=RoleCompUser;
           logger.info(Log4jConfiguration.currentTime()+"[INFO]: The ROLE OF THE USER OF THIS COMPUTER IS: "+finalrole+"\n\n");
        }
        
        else
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The USERNAME DOESN'T EXIST IN DATABASE AND HIS ROLE WILL BE BY DEFAULT: 'GUEST' ");
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Adding the USER IN DB WITH HIS ROLE AS 'GUEST'... \n");
            conn=ConnectionDatabase.getConnection();
            if(conn!=null)
            {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure addUserAccess()");
                    CallableStatement ps;
                    try {
                        ps = conn.prepareCall("{call addUserAccess(?,?)}");
                        ps.setString(1, compuser);
                        ps.setString(2, "GUEST");
                        if(ps.executeUpdate()!=0)
                        {
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: THE USER WAS ADDED WITH SUCCESS: \n"+
                                    "USERNAME: "+compuser+"\n"+
                                    "ROLE: GUEST"+"\n\n");
                            finalrole="GUEST";
                        }
                        else
                        {
                            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went WRONG while adding the USER IN DB \n");
                        }


                    } catch (SQLException ex) {
                        logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: MAINTENANCETOOLCONTROLLER --- SQLException: "+ex+"\n\n");
                    }
                    
                   

            }
            else
            {
                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for adding the USER \n\n");
            }
            
            
            
       }
       if(finalrole!=null)
       {
           logger.info(Log4jConfiguration.currentTime()+"[INFO]: Giving the RIGHTS FOR USER in order of HIS ROLE!...");
           if(finalrole.equals("ADMIN"))
           {
               //over+admin+escal
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: ADMIN MODE....OVERVIEW & ADMIN & ESCALATION & REPORTS & DIAGRAM SHOULD BE VISIBLE!\n\n");
               setThingsForOverview();
               setDoubleClickOnTableOverview();
               setForAdmin();
               setFieldsForEscalation();
               
           }
           else if(finalrole.equals("USER"))
           {
               
               //over+escal
               //System.out.println(admintab);
               //System.out.println(tabpane);
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: USER MODE...JUST OVERVIEW PANEL,ESCALATION,REPORTS,DIAGRAM SHOULD BE REACHABLE!\n\n");
               tabpane.getTabs().remove(admintab);
               setThingsForOverview();
               setDoubleClickOnTableOverview();
               setFieldsForEscalation();
               
           }
           
           else if(finalrole.equals("GUEST"))
           {
               //over+without NEW INCIDENT,NEW PROBLEM ETC...
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: GUEST MODE...ONLY OVERVIEW WITHOUT NEW INCIDENT/NEW PROBLEM BUTTONS SHOULD BE REACHABLE \n\n");
               setThingsForOverview();
               downloadbtn.setGraphic(donwloadfileicon);
               setDoubleClickOnTableOverview();
               newincfornewproblem.setVisible(false);
               btnnewinexpb.setVisible(false); 
               tabpane.getTabs().remove(esctab);
               tabpane.getTabs().remove(admintab);
           }
       }
       else
       {
           logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Something went wrong and no role was found for user \n\n");
       }
       
       
       
          
       
       
       
          
    }    
    
    
}
