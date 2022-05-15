/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tslessonslearneddatabase;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import static tslessonslearneddatabase.ConnectionDatabase.logger;
import static tslessonslearneddatabase.MaintenanceController.Time;

/**
 *
 * @author cimpde1
 */
public class TSLessonsLearnedDatabase extends Application {

    /**
     * @param args the command line arguments
     */
    //public org.apache.logging.log4j.Logger logger= LogManager.getLogger(TSLessonsLearnedDatabase.class);
    
    public static void configureLog4j2()
    {
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
            //appenders
            AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
            
            builder.add(console);
            
            AppenderComponentBuilder file = builder.newAppender("log", "File");
            file.addAttribute("fileName", "TS_Lessons_Learned_Database_Logs/TSLessonsLearnedDatabase.log");
            
            builder.add(file);
            
            //layouts
            LayoutComponentBuilder standard = builder.newLayout("PatternLayout");
            standard.addAttribute("pattern", "[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1} - %msg%n");
            
            console.add(standard);
            file.add(standard);
            
            //root logger
            RootLoggerComponentBuilder rootLogger = builder.newRootLogger(org.apache.logging.log4j.Level.TRACE);
            rootLogger.add(builder.newAppenderRef("log"));
            
            builder.add(rootLogger);
            
            
            Configurator.initialize(builder.build());
            
    }
    public static String currentTime()
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dateobj = new Date();
        String str=df.format(dateobj);
        return str;
    }
    public static void main(String[] args) {
        launch(args);
    }
    
     public static String Time()
    {
        DateFormat df = new SimpleDateFormat("HH:mm");
        java.util.Date dateobj = new java.util.Date();
        String str=df.format(dateobj);
        return str;
    }
    Connection conn=null;
    
    @Override
    public void start(Stage primaryStage)  {
        
        configureLog4j2();
        org.apache.logging.log4j.Logger logger;
        logger = LogManager.getLogger(TSLessonsLearnedDatabase.class);
        String time=currentTime();
        
        try {
           
            
            logger.info(Log4jConfiguration.currentTime()+" [INFO] : TS Lessons Learned Database app has started!" +"\n");
            Parent root = FXMLLoader.load(getClass().getResource("beautifulmaintenance.fxml"));
            Scene scene = new Scene(root);
            
            scene.widthProperty().addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldSceneWidth, Number newSceneWidth) {
                    
                }
            
            });
            
            
            primaryStage.setTitle("TS Lessons Learned Database");
            Image image = new Image("/images/icon.png");
            primaryStage.getIcons().add(image);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            primaryStage.setOnCloseRequest(e->{
                Platform.exit();
                        
                logger.info(Log4jConfiguration.currentTime()+" [INFO] :TS Lessons Learned Database app is closed!");
                
            });
        } catch (IOException ex) {
           //logger.error(Log4jConfiguration.currentTime()+" [ERROR] : The app couldn't been launched because of this error: "+"\n"+ex);
           ex.printStackTrace();
           
        }
            
    }
    @Override
    public void stop()
    {
        configureLog4j2();
        org.apache.logging.log4j.Logger logger;
        logger = LogManager.getLogger(TSLessonsLearnedDatabase.class);
        String time=currentTime();
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: CLOSING CONNECTION TO DB...\n");
        
        conn=ConnectionDatabase.getConnection();
        try {
            conn.close();
            if(conn.isClosed()==true)
            {
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: Connection to DB is closed!");
            }
            else
            {
                logger.warn(Log4jConfiguration.currentTime()+"[ERROR]: Error while closing the connection to DB");
            }


        } catch (SQLException ex) {
            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: "+ex);
        }
        
       
                        

    }
    
}
