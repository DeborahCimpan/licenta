/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tslessonslearneddatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

/**
 *
 * @author cimpde1
 */
public class Log4jConfiguration {
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
    
}
