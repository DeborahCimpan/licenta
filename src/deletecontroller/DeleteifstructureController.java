/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deletecontroller;


import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
public class DeleteifstructureController implements Initializable {

    /**
     * Initializes the controller class.
     */
  
     @FXML
    private AnchorPane anchorPane;
     
       @FXML
    private ImageView cancelicon;

       @FXML
    private ImageView saveicon;

    @FXML
    private TextField cc;

  

    @FXML
    private Button savebtn;
    
    @FXML
    private TextField desc;

    @FXML
    private Button cancelbtn;
    Connection conn=null;  
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(DeleteifstructureController.class);
    
    @FXML
    void cancelFocus(MouseEvent event) {
        anchorPane.requestFocus();

    }
    
    public void SavingUpdate(String tablename,int id)
    {
         try {
             String user=System.getProperty("user.name");
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             
             Date date = new Date();
             //System.out.println(formatter.format(date));
             //java.sql.Date sql = new java.sql.Date(date.getTime());
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure  saveUpdate("+tablename+") from DB in DELETE IF STRUCTURE");
             CallableStatement ps = conn.prepareCall("{call saveUpdate(?,?,?,?)}");
             ps.setString(1,user);
             ps.setString(2,tablename);
             ps.setInt(3,id );
             ps.setString(4, formatter.format(date));
             if(ps.executeUpdate()!=0)
             {
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: The update in DELETE IF STRUCTURE has been saved to DB!\n\n");
             }
             else
             {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR]: The update couldn't been saved in DELETE IF STRUCTURE!");
             }
             
             
         } catch (SQLException ex) {
             logger.fatal("[FATAL]: DELETE IF STRUCTURE --- SQLException: "+ex+"\n\n");
         }
         
    }
    public void save()
    {
         logger.info(Log4jConfiguration.currentTime()+"[INFO]: The YES button from DELETE IF STRUCTURE was pressed!");
        try {
            
            int id=Data.id_if_structure;
            
            List<Integer> linesid=ConnectionDatabase.getLinesid(id);
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: The OUPTLIST OF LINES ID from the IF in DELETE IF STRUCTURE: \n");
            for(int i=0;i<linesid.size();i++)
            {
                logger.info("["+i+"]: "+String.valueOf(linesid.get(i))+"\n");
            }
            
            int i=0;
            for(Integer line : linesid)
            {
                logger.info("Record "+i+ ": \n");
                try {
                    conn=ConnectionDatabase.getConnection();
                    
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure deleteMachinesFromLine() in DELETE IF STRUCTURE");
                    
                    CallableStatement psMachine = conn.prepareCall("{call deleteMachinesFromLine(?)}");
                    psMachine.setInt(1, line);
                    if(psMachine.executeUpdate()!=0)
                    {
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The machines from line were deleted with success in DELETE IF STRUCTURE \n\n");
                    
                    }
                    else
                    {
                        logger.error(Log4jConfiguration.currentTime()+"[INFO]: Error while deleting the machines from line in DELETE IF STRUCTURE or the line doesn't have machines");
                    
                    }
                } catch (SQLException ex) {
                    logger.fatal("[FATAL]: DELETE IF STRUCTURE --- SQLException: "+ex+"\n\n");
                }
                i++;
            }
            
           logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure deleteLinesfromIFdeleted() in DELETE IF STRUCTURE");
           conn=ConnectionDatabase.getConnection();
            CallableStatement psl = conn.prepareCall("{call deleteLinesfromIFdeleted(?)}");
            psl.setInt(1, id);
            
            if(psl.executeUpdate()!=0)
            {
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The lines from IF were deleted with success in DELETE IF STRUCTURE \n");
                    
            }
            else
            {
                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while deleting the lines from IF or there are no lines in IF in DELETE IF STRUCTURE \n");
                    
            }
            //conn=ConnectionDatabase.getConnection();
            CallableStatement ps = conn.prepareCall("{call deleteIfStructure(?)}");
            ps.setInt(1, Data.id_if_structure);
            if(ps.executeUpdate()!=0)
            {
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: The data was deleted in DELETE IF STRUCTURE: \n"+
                                               "IF: "+desc.getText()+"\n"+
                                               "Cost center: "+ cc.getText()+"\n\n");
                                        //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/ok.png"));
                                        dialog.setTitle("Success");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Your data has been deleted!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       
                SavingUpdate("if_structure",Data.id_if_structure);
                Stage s = (Stage) savebtn.getScene().getWindow();
                s.close();
            }
            else
            {
                                       logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while deleting the data in DELETE IF STRUCTURE");
                                        //Creating a dialog
                                       Dialog<String> dialog = new Dialog<String>();
                                       //Setting the title
                                       Stage stage1 = (Stage) dialog.getDialogPane().getScene().getWindow();
                                        stage1.getIcons().add(new Image("images/error.png"));
                                        dialog.setTitle("Error");
                                       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                       //Setting the content of the dialog
                                       dialog.setContentText("Something went wrong while deleting the data in DELETE IF STRUCTURE!");
                                       //Adding buttons to the dialog pane
                                       dialog.getDialogPane().getButtonTypes().add(type);
                                       dialog.showAndWait();
                                       Stage s = (Stage) savebtn.getScene().getWindow();
                                       s.close();
            }
        } catch (SQLException ex) {
            logger.fatal("[FATAL]: DELETE IF STRUCTURE--- SQLException: "+ex+"\n\n");
        }
        
        
        
    }
    public void cancel()
    {
         logger.info(Log4jConfiguration.currentTime()+"[INFO]: NO button from DELETE IF STRUCTURE was pressed!\n\n");
         Stage s = (Stage) cancelbtn.getScene().getWindow();
         s.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: DELETE IF STRUCTURE STARTED");
        
        desc.setText(Data.if_desc.substring(17));
        cc.setText(String.valueOf(Data.if_cc));
        desc.setFocusTraversable(false);
        cc.setFocusTraversable(false);
        savebtn.setFocusTraversable(false);
        cancelbtn.setFocusTraversable(false);
        
        
       
        
        desc.setStyle("-fx-opacity: 1");
        cc.setStyle("-fx-opacity: 1");
        
        savebtn.setGraphic(saveicon);
        cancelbtn.setGraphic(cancelicon);
        
    }    
    
}
