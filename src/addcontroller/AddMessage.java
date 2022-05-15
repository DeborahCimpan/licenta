/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addcontroller;


import deletecontroller.DeleteescalationteamController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import tslessonslearneddatabase.Log4jConfiguration;


/**
 *
 * @author cimpde1
 */
public class AddMessage implements Initializable{

       @FXML
    private Button okbtn;
       
    public org.apache.logging.log4j.Logger logger= LogManager.getLogger(AddMessage.class);
    public void close()
    {
        Stage s = (Stage) okbtn.getScene().getWindow();
        s.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The dialog error message for adding something that already exists has apperead! ");
       
    }
    
}
