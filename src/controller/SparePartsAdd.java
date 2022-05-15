/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import toolclass.SpParts;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import toolclass.SparePartsPinguin;

import tslessonslearneddatabase.ConnectionDatabase;

import tslessonslearneddatabase.Data;
import tslessonslearneddatabase.Log4jConfiguration;
import tslessonslearneddatabase.TSLessonsLearnedDatabase;
/**
 *
 * @author cimpde1
 */
public class SparePartsAdd implements Initializable{
    @FXML
    private Button addbtn;
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
    private TextField filtertext;
    
    @FXML
    private ImageView spareicon;
            
     ObservableList<SpParts> dataListSpParts;
     int index = -1;
     
     public static org.apache.logging.log4j.Logger logger= LogManager.getLogger(SparePartsAdd.class);
    
     public void view_spare_parts()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: SPARE PARTS ADD STARTED!");
         
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Get the SPARE PARTS from PINGUIN in SPARE PARTS ADD");
        
        dataListSpParts=ConnectionDatabase.getSpareParts();
        
        if(dataListSpParts.isEmpty()==false)
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: PUT THE DATA IN TABEL in SPARE PARTS ADD \n");

            col_name_spare.setCellValueFactory(new PropertyValueFactory<SpParts,String>("part_number"));
            col_spare_id.setCellValueFactory(new PropertyValueFactory<SpParts,String>("spare_id"));
            col_des_spare.setCellValueFactory(new PropertyValueFactory<SpParts,String>("spare_desc"));
            col_det_spare.setCellValueFactory(new PropertyValueFactory<SpParts,String>("spare_details"));
            table_spare_parts.setItems(dataListSpParts);
            
            //make filter
            FilteredList<SpParts> filteredData = new FilteredList<>(dataListSpParts,b->true);
            filtertext.textProperty().addListener((observable,oldValue,newValue) -> 
            {
                filteredData.setPredicate(spare ->
                {
                    if(newValue == null || newValue.isEmpty())
                    {
                        return true;
                    }

                    String lowerCaseFilter=newValue.toLowerCase();
                    String pn=spare.getPart_number();
                    String spareid=spare.getSpare_id();
                    String spare_des=spare.getSpare_desc();
                    String spare_details=spare.getSpare_details();

                    if(pn.toLowerCase().indexOf(lowerCaseFilter)!=-1)
                    {
                        return true;
                    }
                    else if(spareid.toLowerCase().indexOf(lowerCaseFilter)!=-1)
                    {
                        return true;
                    }
                    else if(spare_des.toLowerCase().indexOf(lowerCaseFilter)!=-1)
                    {
                        return true;
                    }
                    else if(spare_details.toLowerCase().indexOf(lowerCaseFilter)!=-1)
                    {
                        return true;
                    }
                    
                    return false;
                    


                });

            });

            SortedList<SpParts> sortedData=new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table_spare_parts.comparatorProperty());
            table_spare_parts.setItems(sortedData);
            
            table_spare_parts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            
            
        }
        else
        {
            logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The spare parts list from pinguin is empty!");
        }
        
        Data.spare_part_numbers="";
       
        
        
    }
     public void onSelectSpare()
     {
         index=table_spare_parts.getSelectionModel().getSelectedIndex();
         if(index <= -1)
        {
            return;
        }
        // Data.spare_part_numbers="";
         SpParts spare=table_spare_parts.getSelectionModel().getSelectedItem();
            String pn=spare.getPart_number();
            String spid=spare.getSpare_id();
            String spdes=spare.getSpare_desc();
            String spdet=spare.getSpare_details();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The DETAILS about THE SPARE PART selected in SPARE PARTS ADD: \n"+
                    "Part Number: "+pn+"\n"+
                            "Spare ID: "+spid+"\n"+
                                    "Spare Description: "+spdes+"\n"+
                                            "Spare Details: "+spdet+"\n"); 
     }
    
    public void add()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: ADD BUTTON WAS PRESSED IN SPARE PARTS ADD \n");
        if(table_spare_parts.getSelectionModel().isSelected(index))
        {
            SpParts spare=table_spare_parts.getSelectionModel().getSelectedItem();
            int id=spare.getId();
            String pn=spare.getPart_number();
            String spid=spare.getSpare_id();
            String spdes=spare.getSpare_desc();
            String spdet=spare.getSpare_details();
            
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The DETAILS about THE SPARE PART selected in SPARE PARTS ADD when ADD button was pressed: \n"+
                    "Part Number: "+pn+"\n"+
                            "Spare ID: "+spid+"\n"+
                                    "Spare Description: "+spdes+"\n"+
                                            "Spare Details: "+spdet+"\n");
            
            String regex="^PN[0-9]{8}$";
            
            
            if(pn.matches(regex)==true)
            {
               
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The selected one HAS A PART NUMBER");
                
                        String part_number=ConnectionDatabase.getPartNumber(id);

                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The PART NUMBER selected in SPARE PARTS ADD is: "+part_number);

                        Data.spare_part_numbers=part_number;
                   
            }
            else if(pn.matches(regex)==false)
            {
                logger.warn(Log4jConfiguration.currentTime()+"[WARN]: The selected one DOESN'T HAVE A PART NUMBER");
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                //Setting the title
                Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/warn.png"));
                                        dialog.setTitle("Warning");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Setting the content of the dialog
                dialog.setContentText("The selected one doesn't match with a PART NUMBER!");
                //Adding buttons to the dialog pane
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
                
                
                
                Data.spare_part_numbers="";
                
            }
            
            
            Stage s = (Stage) addbtn .getScene().getWindow();
            s.close();
        }
        else
        {
                Data.spare_part_numbers="";
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
    public void onAnchorSpare()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Clear selection from table in SPARE PARTS ADD");
        table_spare_parts.getSelectionModel().clearSelection();
        Data.spare_part_numbers="";
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        view_spare_parts();
        addbtn.setGraphic(spareicon);
        
        
        
        
    }
    
}
