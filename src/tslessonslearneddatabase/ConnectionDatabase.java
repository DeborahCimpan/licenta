/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tslessonslearneddatabase;

import addcontroller.AddlineController;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import toolclass.Access;
import toolclass.EscalationTeam;
import toolclass.IfStruct;
import toolclass.IncidentEscalation;
import toolclass.IncidentHistory;
import toolclass.Incidents;
import toolclass.IncidentsDetails;
import toolclass.LineStruct;
import toolclass.MachineCombo;
import toolclass.MachineStruct;
import toolclass.Overview;
import toolclass.ParticularDetailsProblem;
import toolclass.ProblemHistory;
import toolclass.ShTeam;
import toolclass.SpParts;
import toolclass.Users;

/**
 *
 * @author cimpde1
 */
public class ConnectionDatabase {
     static Connection conn=null;
     public static org.apache.logging.log4j.Logger logger= LogManager.getLogger(ConnectionDatabase.class);
     public static Connection connectDb()
    {
        /*String username="root";
            String password="password";
            String url="jdbc:mysql://10.169.8.193:3306/maintenance_tool";*/
       
        //String time=currentTime();
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Connecting to database...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //String databaseName="maintenance_tool";
            String username="root";
            String password="11_Septembrie";
            String url="jdbc:mysql://localhost:3306/maintenance_tool";
            conn=(Connection) DriverManager.getConnection(url,username,password);
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Connected to DB");
            return conn;
            
        } catch (ClassNotFoundException ex) {
            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: Fatal error in connecting to DB -> ClassNotFoundException: "+ex);
            return null;
            
        } catch (SQLException ex) {
            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: Fatal error in connecting to DB -> SQLException: "+ex);
            return null;
        }
        
    }
    public static Connection getConnection()
    {
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Getting connection to DB...");
        if(conn!=null)
        {
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: Connected to DB");
            return conn;
           
        }
        else
        {
            conn=connectDb();
            return conn;
        }
        
    }
     
    
      public static int getifid(String ifdesc) throws SQLException
    {
        
       
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {
            
                try{
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getIfIdforaddLine \n");
               CallableStatement ps = conn.prepareCall("{call getIfIdforaddLine(?)}");
               ps.setString(1, ifdesc);
               ResultSet rs=ps.executeQuery();
               if(rs.next())
                       {
                            id=rs.getInt("idif_structure");
                       }

           } catch (SQLException ex) {
                logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE ----- SQLException: " + ex);
           }
        }
        else
        {
             logger.error(Log4jConfiguration.currentTime()+"[ERROR] : Error while connecting to DB for getting the IF id from DB in CONNECTION DATABASE!");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The IF id from CONNECTION DATABASE: "+id+"\n\n");
       
        return id; 
        
    }
     public static ObservableList<String> getLinesfromIf(int id)
    {
        
        
        conn=getConnection();
        ObservableList<String> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
            
                try{
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getLinesForCombo() \n");
               CallableStatement ps = conn.prepareCall("{call getLinesForCombo(?)}");
               ps.setInt(1, id);
               ResultSet rs=ps.executeQuery();
               int i=1;
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUPTUT LIST OF LINES NAME in CONNECTION DATABASE: \n");
               while(rs.next())
               {
                   logger.info("["+i+"]: "+ rs.getString("Line_Name")+"\n");
                   list.add(rs.getString("Line_Name"));
                   i++;
               }
           } catch (SQLException ex) {
                logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
           }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR] : Error while connecting to DB for getting the Lines from IF from DB in CONNECTION DATABASE!");
        }
       
       
        return list;
    }
     public static String msgif(IfStruct el)
     {
         return "IF ID: "+el.getId()+"\n"+
                 "IF Name: "+el.getName()+"\n"+
                 "IF Description: "+el.getDescription()+"\n"+
                 "IF CC: "+el.getCc()+"\n";
         
     }
   public static ObservableList<IfStruct> getIfStructure()
    {
        
       
        conn=getConnection();
        ObservableList<IfStruct> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getIfStructure()");
                    CallableStatement ps = conn.prepareCall("{call getIfStructure()}");
                    ResultSet rs=ps.executeQuery();
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF IFs STRUCTURE from CONNECTION DATABASE: \n");
                    int i=1;
                    while(rs.next())
                    {
                        IfStruct IF= new IfStruct(Integer.parseInt(rs.getString("idif_structure")),rs.getString("IF_Name"),rs.getString("IF_Description"),rs.getInt("IF_CC"));
                        list.add(IF);
                        logger.info("["+i+"]: "+msgif(IF));
                        i++;
                    }
                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
             logger.error(Log4jConfiguration.currentTime()+"[ERROR] : Error while connecting to DB for getting the IF Structure from DB");
        }
       
        logger.info("\n\n");
        
        return list;
        
        
    }
   public static int getIfId(String ifname)
    {
        
        
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {
                try{
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getIFID()");
               CallableStatement ps = conn.prepareCall("{call getIFID(?)}");
               ps.setString(1, ifname);
               ResultSet rs=ps.executeQuery();
               if(rs.next())
                       {
                            id=rs.getInt("idif_structure");
                       }

           } catch (SQLException ex) {
                logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE ---- SQLException: " + ex);
           }
        }
        else
        {
             logger.error(Log4jConfiguration.currentTime()+"[ERROR] : Error while connecting to DB for getting the id of IF from DB!");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: IF's ID in CONNECTION DATABASE : "+id+"\n\n");
        return id;
        
        
    }
    public static String msgline(LineStruct l)
    {
        return "Line ID: "+l.getId()+"\n"+
                "Line IF's ID: "+l.getIfid()+"\n"+
                "Line Functional Place: "+l.getFunctionalplace()+"\n"+
                "Line CC: "+l.getCc()+"\n";
    }
    public static ObservableList<LineStruct> getLineStructure()
    {
        
       
        conn=getConnection();
        ObservableList<LineStruct> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                try{
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getLineStructure()");
               CallableStatement ps = conn.prepareCall("{call getLineStructure()}");

               ResultSet rs=ps.executeQuery();
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF LINEs STRUCTURE in CONNECTION DATABASE: \n");
               int i=0;
               while(rs.next())
               {
                   LineStruct l=new LineStruct(Integer.parseInt(rs.getString("idline_structure")),rs.getInt("if_id"),rs.getString("Line_Name"),rs.getString("Line_Functional_Place"),rs.getInt("Line_CC"));
                   list.add(l);
                   logger.info("["+i+"]: "+msgline(l));
                   i++;
               }
           } catch (SQLException ex) {
                logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE ---- SQLException: " + ex);
           }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime() + "[ERROR] : Error while connecting to DB for getting the line structure from DB!");
        }
       
        return list;
        
        
    }
    public static ObservableList<LineStruct> getLineStructureByIfId(int idif)
    {
        
        
        conn=getConnection();
        ObservableList<LineStruct> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                try{
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getLineStructureByIf()");
                CallableStatement ps = conn.prepareCall("{call getLineStructureByIf(?)}");
                ps.setInt(1, idif);
                ResultSet rs=ps.executeQuery();
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF LINEs STRUCTURE in CONNECTION DATABASE: \n");
                int i=1;
                while(rs.next())
                {
                    LineStruct l=new LineStruct(Integer.parseInt(rs.getString("idline_structure")),rs.getInt("if_id"),rs.getString("Line_Name"),rs.getString("Line_Functional_Place"),rs.getInt("Line_CC"));
                    list.add(l);
                    logger.info("["+i+"]: "+msgline(l));
                    i++;
                }
            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime() + "[ERROR] : Error while connecting to DB for getting the line structure by IF from DB");
        }
       
        logger.info("\n\n");
        return list;
        
        
    }
     public static String getDescAfterIFID(int id)
    {
        
       
        conn=getConnection();
        String desc="";
        if(conn!=null)
        {
                try{
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getIfDescriptionAfterID()");
                CallableStatement ps = conn.prepareCall("{call getIfDescriptionAfterID(?)}");
                ps.setInt(1, id);
                ResultSet rs=ps.executeQuery();
                if(rs.next())
                        {
                             desc=rs.getString("IF_Description");
                        }

            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime() + "[ERROR] : Error while connecting to DB for getting the IF DESCRIPTION from DB");
        }
       
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The IF DESCRIPTION from DB : "+desc+"\n\n");
        return desc;
        
    }
    public static ObservableList<String> getIFCombo()
    {
        
        Connection conn=getConnection();
        ObservableList<String> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
            
                
                try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getIFCombo()");
                    CallableStatement ps = conn.prepareCall("{call getIFCombo()}");
                    ResultSet rs=ps.executeQuery();
                    int i=1;
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST of IFs DESCRIPTION in CONNECTION DATABASE: \n");
                    while(rs.next())
                            {
                                 list.add(rs.getString("IF_Description"));
                                 logger.info("["+i+"]: "+rs.getString("IF_Description")+"\n");
                                 i++;
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
             logger.error(Log4jConfiguration.currentTime()+"[ERROR] : Error while connecting to DB for getting the IF DESCRIPTION list for combobox");
        }
        
        logger.info("\n");
        return list;
        
    }
     public static int getLineId(String linename)
    {
        
        
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {
                try{
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure getLineId()");
                CallableStatement ps = conn.prepareCall("{call getLineId(?)}");
                ps.setString(1, linename);
                ResultSet rs=ps.executeQuery();
                if(rs.next())
                        {
                             id=rs.getInt("idline_structure");
                        }

            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR] : Error while connecting to DB for getting the line id");
        }
       
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The LINE ID : "+id+"\n\n");
        return id;
        
    }
     public static String msgmachine(MachineStruct m)
     {
         return "Machine ID: "+m.getId()+"\n"+
                 "Machine Name: "+m.getName()+"\n"+
                 "Machine Inventory Number: "+m.getInvnumber()+"\n";
     }
     public static ObservableList<MachineStruct> getMachineStructure(int ifid,int lid,String txt)
    {
        
        
        conn=getConnection();
        ObservableList<MachineStruct> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                try{
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure filteredMachines()");
                CallableStatement ps = conn.prepareCall("{call filteredMachines(?,?,?)}");
                ps.setInt(1,ifid);
                ps.setInt(2,lid);
                ps.setString(3, txt);
                ResultSet rs=ps.executeQuery();
                int i=1;
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF MACHINE STRUCTURE in CONNECTION DATABASE: \n");
                while(rs.next())
                {
                    MachineStruct m=new MachineStruct(Integer.parseInt(rs.getString("idmachine_structure")),
                            rs.getInt("line_id"),rs.getString("Machine_Name"),rs.getString("Machine_InventoryNumber"));
                    list.add(m);
                    logger.info("["+i+"]: "+msgmachine(m));
                    i++;
                    
                }
            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the MACHINE STRUCTURE\n");
        }
        
        logger.info("\n");
        return list;
        
        
    }
     //unused function
     public static ObservableList<MachineStruct> getMachineStructureNoFilter()
    {
        
        
        conn=getConnection();
        ObservableList<MachineStruct> list=FXCollections.observableArrayList();
        try{
            
            CallableStatement ps = conn.prepareCall("{call getMachinesNoFilter()}");
            
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
               // list.add(new MachineStruct(Integer.parseInt(rs.getString("idmachine_structure")),rs.getInt("line_id"),rs.getString("Machine_Description"),rs.getString("Machine_Name"),rs.getInt("Machine_CC"),rs.getString("Machine_InventoryNumber")));
            }
        } catch (SQLException ex) {
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
        }
        
        return list;
        
        
    }
     //unused function
     public static ObservableList<MachineStruct> getMachineStructureByLine(String linename)
    {
        
        org.apache.logging.log4j.Logger logger;
        logger = LogManager.getLogger(ConnectionDatabase.class);
        //String time=currentTime();
        conn=getConnection();
        ObservableList<MachineStruct> list=FXCollections.observableArrayList();
        try{
            
            CallableStatement ps = conn.prepareCall("{call getMachinesByLine(?)}");
            ps.setString(1,linename);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
               // list.add(new MachineStruct(Integer.parseInt(rs.getString("idmachine_structure")),rs.getInt("line_id"),rs.getString("Machine_Description"),rs.getString("Machine_Name"),rs.getInt("Machine_CC"),rs.getString("Machine_InventoryNumber")));
            }
        } catch (SQLException ex) {
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
        }
        return list;
        
        
    }
       public static int getMachineId(int lineid,String machname)
    {
        
        
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {
                    try{
                   logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getMachineId()");
                   CallableStatement ps = conn.prepareCall("{call getMachineId(?,?)}");
                   ps.setInt(1, lineid);
                   ps.setString(2, machname);

                   ResultSet rs=ps.executeQuery();
                   if(rs.next())
                           {
                                id=rs.getInt("idmachine_structure");
                           }

               } catch (SQLException ex) {
                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
               }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting MACHINE's ID\n");
        }
        
       logger.info(Log4jConfiguration.currentTime()+"[INFO]: MACHINE ID: "+id+"\n\n");
        return id;
        
    }
       
        public static String getLinenamefromMachine(int id)
    {
        
        
        conn=getConnection();
        String desc="";
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getLineNamefromMachine()");
                    CallableStatement ps = conn.prepareCall("{call getLineNamefromMachine(?)}");
                    ps.setInt(1, id);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next())
                            {
                                 desc=rs.getString("Line_Name");
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }

        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the LINE NAME\n");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Line NAME from DB: "+desc);
        return desc;
        
    }
     public static String getIffromLine(String linename)
    {
        
       
        conn=getConnection();
        String if_desc=null;
        if(conn!=null)
        {
                try{

                CallableStatement ps = conn.prepareCall("{call getIFfromLine(?)}");
                ps.setString(1, linename);
                ResultSet rs=ps.executeQuery();
                if(rs.next())
                        {
                             if_desc=rs.getString("IF_Description");
                        }

            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the IF DESCRIPTION ");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: IF DESCRIPTION from DB : "+if_desc);
        return if_desc;
        
    }
     public static String msgtechn(ShTeam sh)
     {
         return "Technician Name: "+sh.getNume()+"\n"+
                 "Technician Username: "+sh.getUsername()+"\n"+
                 "Technician Email: "+sh.getEmail()+"\n"+
                 "Technician Job Description: "+sh.getJob()+"\n";
     }
      public static ObservableList<ShTeam> getDatausers()
    {
        
        
        conn=getConnection();
        ObservableList<ShTeam> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                try{
                //select* from shift_team;
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure getTechnicians()");
                CallableStatement ps = conn.prepareCall("{call getTechnicians()}");
                ResultSet rs=ps.executeQuery();
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF TECHNICIANS in CONNECTION DATABASE: \n");
                int i=1;
                while(rs.next())
                {
                    ShTeam s=new ShTeam(rs.getInt("idshift_team"),rs.getString("Technician_Name"),rs.getString("Job_Description"),rs.getString("Technician_User"),rs.getString("Email_Adress"));
                    list.add(s);
                    logger.info("["+i+"]: "+msgtechn(s));
                    i++;
                }
            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the TECHNICIANS");
        }
        
        logger.info("\n");
        return list;
    }
      public static int getShiftId(String username)
    {
        
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {
           
                try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getShiftId()");
                    CallableStatement ps = conn.prepareCall("{call getShiftId(?)}");
                    ps.setString(1, username);

                    ResultSet rs=ps.executeQuery();
                    if(rs.next())
                            {
                                 id=rs.getInt("idshift_team");
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                } 
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the TECHNICIAN ID \n");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: THE TECHNICIAN's ID from DB: "+id);
        return id;
        
    }
      public static String imgescteam(EscalationTeam t)
      {
          return "Enginner's ID: "+t.getId()+"\n"+
                  "Engineer Name: "+t.getName()+"\n"+
                  "Engineer Job Description: "+t.getJob()+"\n"+
                  "Engineer User: "+t.getUser()+"\n"+
                  "Engineer Email: "+t.getEmail()+"\n";
      }
       public static ObservableList<EscalationTeam> getEscTeam()
    {
        
        conn=getConnection();
        ObservableList<EscalationTeam> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getEscTeam()");
                    CallableStatement ps = conn.prepareCall("{call getEscTeam()}");
                    ResultSet rs=ps.executeQuery();
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF ENGINEERS from DB: \n");
                    int i=1;
                    while(rs.next())
                    {
                        EscalationTeam t=new EscalationTeam(rs.getInt("idescalation_team"),rs.getString("Engineer_Name"),rs.getString("Job_Desc"),rs.getString("Engineer_User"),rs.getString("Email_Adress"));
                        list.add(t);
                        logger.info("["+i+"]: "+imgescteam(t));
                        i++;
                    }
                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.info(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the ESCALATION TEAM");
        }
       
        logger.info("\n");
        return list;
    }
       public static int getEscId(String username)
    {
        
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {
            
                try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getEscID()");
                    CallableStatement ps = conn.prepareCall("{call getEscID(?)}");
                    ps.setString(1, username);

                    ResultSet rs=ps.executeQuery();
                    if(rs.next())
                            {
                                 id=rs.getInt("idescalation_team");
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }

        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting Enginner's ID");
        }
       
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The ENGINEER's ID from DB: "+id+"\n\n");
        return id;
        
    }
       public  static String imgspare (SpParts sp)
       {
           return "Spare's ID: "+sp.getId()+"\n"+
                   "Spare's Part number: "+sp.getPart_number()+"\n"+
                   "Spare's description: "+sp.getSpare_desc()+"\n"+
                   "Spare's details: "+sp.getSpare_details()+"\n"+
                   "Spare's SPARE ID: "+sp.getSpare_id()+"\n";
       }
   public static ObservableList<SpParts> getSpareParts()
    {
        
        conn=getConnection();
        ObservableList<SpParts> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                try{
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getSpareParts()");
                 
                 CallableStatement ps = conn.prepareCall("{call getSpareParts()}");
                 
                 ResultSet rs=ps.executeQuery();
                 logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF SPARE PARTS's STRUCTURE from DB: \n");
                 int i=1;
                 while(rs.next())
                 {
                     SpParts sp=new SpParts(Integer.parseInt(rs.getString("idspare_parts")),rs.getString("Part_Number"),rs.getString("Spare_Id"),
                             rs.getString("Spare_Description"),rs.getString("Spare_Details"));
                     list.add(sp);
                     logger.info("["+i+"]: "+imgspare(sp));
                     i++;
                 }
             } catch (SQLException ex) {
                  logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
             } 
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting SPARE PARTS\n");
        }
        
        logger.info("\n");
        return list;
        
        
    }
   public static String imgaccess(Access a)
   {
       return "access_id: "+a.getId()+"\n"+
               "access_username: "+a.getUsername()+"\n"+
               "access_role: "+a.getRole()+"\n";
   }
   public static ObservableList<Access> getAccess()
    {
        
        conn=getConnection();
        ObservableList<Access> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
            
            try{
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure getAccess()");
                CallableStatement ps = conn.prepareCall("{call getAccess()}");
                ResultSet rs=ps.executeQuery();
                int i=1;
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF ACCESS STRUCTURE from DB: \n");
                while(rs.next())
                {
                    Access a=new Access(Integer.parseInt(rs.getString("idaccess")),rs.getString("access_username"),rs.getString("access_role"));
                    list.add(a);
                    logger.info("["+i+"]: "+imgaccess(a));
                    i++;
                    
                }
            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the ACCESS STRUCTURE\n");
        }
        
        
        logger.info("\n");
        return list;
        
        
    }
    public static int getAccesId(String username)
    {
        
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {
                try{
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getAccessID()");
                CallableStatement ps = conn.prepareCall("{call getAccessID(?)}");
                ps.setString(1, username);

                ResultSet rs=ps.executeQuery();
                if(rs.next())
                        {
                             id=rs.getInt("idaccess");
                        }

            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the ACCESS's ID");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The ID from ACCESS from DB: "+id);
        return id;
        
    }
    public static ObservableList<SpParts> getFilteredSpareParts(String txt)
    {
        
        
        conn=getConnection();
        ObservableList<SpParts> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
            
                    try{
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure filterSpareParts()");
                        CallableStatement ps = conn.prepareCall("{call filterSpareParts(?)}");
                        ps.setString(1, txt);
                        ResultSet rs=ps.executeQuery();
                        int i=1;
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF FILTERED SPARE PARTS: \n");
                        while(rs.next())
                        {
                            SpParts sp=new SpParts(Integer.parseInt(rs.getString("idspare_parts")),rs.getString("Part_Number"),rs.getString("Spare_Id"),rs.getString("Spare_Description"),rs.getString("Spare_Details"));
                            list.add(sp);
                            logger.info("["+i+"]: "+imgspare(sp));
                            i++;
                        }
                    } catch (SQLException ex) {
                         logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                    }

        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the FILTERED SPARE PARTS\n");
        }
        
        logger.info("\n");
        
        return list;
        
        
    }
    
    public static ObservableList<String> getMachineCombo(int id)
    {
        
        
        conn=getConnection();
        ObservableList<String> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getMachinefromLine()");
                    CallableStatement ps = conn.prepareCall("{call getMachinefromLine(?)}");
                    ps.setInt(1,id);
                    ResultSet rs=ps.executeQuery();
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF MACHINES from DB: \n");
                    int i=1;
                    while(rs.next())
                            {

                                 list.add(rs.getString("RESULT"));
                                 logger.info("["+i+"]: "+rs.getString("RESULT")+"\n");
                                 i++;
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting Machine's name\n");
        }
       
        
        return list;
        
    }
    public static String msgmcombo(MachineCombo mc)
    {
        return "Machine ID: "+mc.getMachine_id()+"\n"+
                "Machine Name: "+mc.getMachine_description()+"\n";
    }
    public static ObservableList<MachineCombo> getMachineIDName(int id)
    {
        
        
        conn=getConnection();
        ObservableList<MachineCombo> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                try{
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getMachineIDandNameCombo()");
                CallableStatement ps = conn.prepareCall("{call getMachineIDandNameCombo(?)}");
                ps.setInt(1,id);
                ResultSet rs=ps.executeQuery();
                int i=1;
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF MACHINE's NAME AND ID: \n");
                while(rs.next())
                        {
                             MachineCombo mc=new MachineCombo(rs.getInt("idmachine_structure"),rs.getString("Machine_Description"));
                             list.add(mc);
                             logger.info("["+i+"]: "+msgmcombo(mc));
                             i++;
                        }

            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the Machine's name and ID\n");
        }
        
        logger.info("\n");
        return list;
    }
    
     public static ObservableList<String> getEscName()
    {
        
        
        conn=getConnection();
        ObservableList<String> list=FXCollections.observableArrayList();
        if(conn!=null)
        {

                try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getEscName()");
                    CallableStatement ps = conn.prepareCall("{call getEscName()}");
                    ResultSet rs=ps.executeQuery();
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF ENGINEERS from DB: \n");
                    int i=1;
                    while(rs.next())
                            {
                                 list.add(rs.getString("RESULT"));
                                 logger.info("["+i+"]: "+rs.getString("RESULT")+"\n");
                                 i++;
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the ENGINEERS\n");
        }
        
        logger.info("\n");
        return list;
        
    }
     public static ObservableList<String> getFilteredEscName(String txt)
    {
        
        
        conn=getConnection();
        ObservableList<String> list=FXCollections.observableArrayList();
        if(conn!=null)
        {

                try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getFilteredEscName()");
                    CallableStatement ps = conn.prepareCall("{call getFilteredEscName(?)}");
                    ps.setString(1, txt);
                    ResultSet rs=ps.executeQuery();
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF ENGINEERS from DB that start with "+txt+": \n");
                    int i=1;
                    while(rs.next())
                            {
                                 list.add(rs.getString("RESULT"));
                                 logger.info("["+i+"]: "+rs.getString("RESULT")+"\n");
                                 i++;
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the ENGINEERS\n");
        }
        
        logger.info("\n");
        return list;
        
    }
     //unused function
      public static ObservableList<MachineStruct> getMachinesfromLine(int id)
    {
        
        
        conn=getConnection();
        ObservableList<MachineStruct> list=FXCollections.observableArrayList();
        try{
            
            CallableStatement ps = conn.prepareCall("{call getallMachinesFromLine(?)}");
            ps.setInt(1,id);
           
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
               // list.add(new MachineStruct(Integer.parseInt(rs.getString("idmachine_structure")),rs.getInt("line_id"),rs.getString("Machine_Description"),rs.getString("Machine_Name"),rs.getInt("Machine_CC"),rs.getString("Machine_InventoryNumber")));
            }
        } catch (SQLException ex) {
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
        }
       
        return list;
        
        
    }
      
      
      public static int getMachineId(String machdesc,String invn)
    {
        
        
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {
                try{

                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling getMachineId()");
                CallableStatement ps = conn.prepareCall("{call getMachineId(?,?)}");
                ps.setString(1, machdesc);
                ps.setString(2, invn);
                ResultSet rs=ps.executeQuery();

                if(rs.next())
                        {
                             id=rs.getInt("idmachine_structure");
                        }

            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the MACHINE's ID \n");
        }
       
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The MACHINE's ID from DB: "+id+"\n\n");
        return id;
        
    }
      public static int getProblemId(int ifid,int lineid,int machineid,String hmi,String status,String user,String desc,int idEsc,String file)
    {
        
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure getProblemID()");
                    CallableStatement ps = conn.prepareCall("{call getProblemID(?,?,?,?,?,?,?,?,?)}");
                    ps.setInt(1, ifid);
                    ps.setInt(2, lineid);
                    ps.setInt(3,machineid);
                    ps.setString(4,hmi);
                    ps.setString(5, status);
                    ps.setString(6, user);
                    ps.setString(7,desc );
                    ps.setInt(8, idEsc);
                    ps.setString(9, file);

                    ResultSet rs=ps.executeQuery();

                    if(rs.next())
                            {
                                 id=rs.getInt("idproblem_list_history");
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]:Error while connecting to DB for getting PROBLEM's ID\n");
        }
       
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: Problem's ID from DB: "+id+"\n\n");
        return id;
        
    }
      
      
       public static int getEscAssignId(int idesc,String sd)
    {
        
        
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {

                try{

                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure getEngineeAssignID()");
                    CallableStatement ps = conn.prepareCall("{call getEngineerAssignID(?,?)}");
                    ps.setInt(1, idesc);
                    ps.setString(2, sd);

                    ResultSet rs=ps.executeQuery();

                    if(rs.next())
                            {
                                 id=rs.getInt("idescalation_assign");
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.info(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting ESCALATION ASSIGN's ID \n");
        }
       
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The ESCALATION ASSIGN ID from DB: "+id);
        return id;
        
    }
       //unused function
       public static ObservableList<ProblemHistory> getProblemHistory(String date,int lineid,int machineid)
    {
        
        
        conn=getConnection();
        ObservableList<ProblemHistory> list=FXCollections.observableArrayList();
        try{
            
            CallableStatement ps = conn.prepareCall("{call getProblemHistory(?,?,?)}");
            ps.setString(1, date);
            ps.setInt(2, lineid);
            ps.setInt(3, machineid);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                //list.add(new ProblemHistory(rs.getString("Line_Name"),rs.getString("Machine_Name"),rs.getString("problem_Creator_Username"),rs.getString("problem_Status"),rs.getString("problem_HMI_Error"),rs.getString("Engineer"),rs.getString("problem_Description"),rs.getString("problem_File_Path"),rs.getInt("Number of incidents")));
            }
        } catch (SQLException ex) {
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
        }
        
        return list;
        
        
    }
       
       public static String imgover(Overview o)
       {
           return "ID Problem History: "+o.getId()+"\n"+
                   "Problem's Line Name: "+o.getLine()+"\n"+
                   "Problem's Machine Name: "+o.getMachine()+"\n"+
                   "Problem's description: "+o.getProblem_description()+"\n"+
                   "Problem's HMI Error: "+o.getHmi()+"\n"+
                   "Problem's Number of Incidents: "+o.getNr_incidents()+"\n";
       }
       
       public static ObservableList<Overview> getOverviewFilter(int ifid,int lineid,int machineid,String key)
    {
        
        
        conn=getConnection();
        ObservableList<Overview> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                try{
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure getOverviewFilter()");
                CallableStatement ps = conn.prepareCall("{call getOverviewFilter(?,?,?,?)}");
                ps.setInt(1, ifid);
                ps.setInt(2, lineid);
                ps.setInt(3, machineid);
                ps.setString(4, key);
                ResultSet rs=ps.executeQuery();
                int i=1;
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF FILTERED OVERVIEW from DB: \n");
                while(rs.next())
                {
                    Overview o=new Overview(rs.getInt("idproblem_list_history"),
                            rs.getString("Line_Name"),rs.getString("Machine_Name"),
                            rs.getString("problem_HMI_Error"),rs.getString("problem_Description"),rs.getString("Number of incidents"));
                    list.add(o);
                    logger.info("["+i+"]: "+imgover(o));
                    i++;
                    
                }
            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]:Error while connecting to DB for getting OVERVIEW \n");
        }
        
        logger.info("\n");
        return list;
        
    
        
    }
       //unused function
      public static ObservableList<MachineStruct> getMachineStructureAfterIF(int idif)
    {
        
        org.apache.logging.log4j.Logger logger;
        logger = LogManager.getLogger(ConnectionDatabase.class);
        //String time=currentTime();
        conn=getConnection();
        ObservableList<MachineStruct> list=FXCollections.observableArrayList();
        try{
            
            CallableStatement ps = conn.prepareCall("{call getMachinesFromIF(?)}");
            ps.setInt(1, idif);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
               // list.add(new MachineStruct(Integer.parseInt(rs.getString("idmachine_structure")),rs.getInt("line_id"),rs.getString("Machine_Description"),rs.getString("Machine_Name"),rs.getInt("Machine_CC"),rs.getString("Machine_InventoryNumber")));
            }
        } catch (SQLException ex) {
             logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
        }
        return list;
        
        
    }
       
      public static ObservableList<String> getLinesName()
    {
        
        
        conn=getConnection();
        ObservableList<String> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                
                try{
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling the procedure getAllLinesDescription()");
                CallableStatement ps = conn.prepareCall("{call getAllLinesDescription()}");
                ResultSet rs=ps.executeQuery();
                int i=1;
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF LINE's NAMES from DB: \n");
                while(rs.next())
                        {
                             list.add(rs.getString("Line_Name"));
                             logger.info("["+i+"]: "+rs.getString("Line_Name")+"\n");
                             i++;
                        }

            } catch (SQLException ex) {
                 logger.error(Log4jConfiguration.currentTime()+"[ERROR] : SQLException: "+ ex);
            }
        }
        else
        {
             logger.error(Log4jConfiguration.currentTime()+"[ERROR] : Error while connecting to DB for getting the lines for combobox ");
        }
        
        logger.info("\n");
        return list;
        
    }
      public static String msgproblem(ProblemHistory ph)
      {
          return "Problem's ID: "+ph.getId()+"\n"+
                  "Problem's Line Name: "+ph.getLine()+"\n"+
                  "Problem's Machine Name: "+ph.getMachine()+"\n"+
                  "Problem's Creator Username: "+ph.getUser()+"\n"+
                  "Problem's Status: "+ph.getStatus()+"\n"+
                  "Problem's HMI Error: "+ph.getHmi()+"\n"+
                  "Problem's Escalated Engineer: "+ph.getEngineer()+"\n"+
                  "Problem's Description: "+ph.getProblem_description()+"\n"+
                  "Problem's File: "+ph.getFile_path()+"\n"+
                  "Problem's Number of incidents: "+ph.getNr_incidents()+"\n";
      }
     
      public static ObservableList<ProblemHistory> getFilteredProblems(int ifid,int lid,int mid,String key,int idr)
    {
        
        
        conn=getConnection();
        ObservableList<ProblemHistory> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
            try{
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getFilteredProblems()");
            CallableStatement ps = conn.prepareCall("{call getFilteredProblems(?,?,?,?,?)}");
            ps.setInt(1, ifid);
            ps.setInt(2,lid);
            ps.setInt(3, mid);
            ps.setString(4, key);
            ps.setInt(5, idr);
            ResultSet rs=ps.executeQuery();
            int i=1;
            
            while(rs.next())
            {
                    if(!rs.getString("problem_File_Path").isEmpty())
                    {
                        CheckBox ch=new CheckBox();
                        ch.setSelected(true);
                        ch.setDisable(true);
                        ch.setStyle("-fx-opacity: 1");
                        ProblemHistory ph= new ProblemHistory(rs.getInt("idproblem_list_history"),
                                rs.getString("Line_Name"),rs.getString("Machine_Name"),
                                rs.getString("problem_Creator_Username"),rs.getString("problem_Status"),rs.getString("problem_HMI_Error"),rs.getString("Engineer"),
                                rs.getString("problem_Description"),ch,rs.getString("problem_File_Path"),rs.getInt("Number of incidents"));
                        list.add(ph);
                        logger.info("["+i+"]: "+msgproblem(ph));
                        i++;
                    }
                    else
                    {
                        ProblemHistory ph=new ProblemHistory(rs.getInt("idproblem_list_history"),rs.getString("Line_Name"),rs.getString("Machine_Name"),
                                rs.getString("problem_Creator_Username"),rs.getString("problem_Status"),rs.getString("problem_HMI_Error"),rs.getString("Engineer"),
                                rs.getString("problem_Description"),null,rs.getString("problem_File_Path"),rs.getInt("Number of incidents"));
                        list.add(ph);
                        logger.info("["+i+"]: "+msgproblem(ph));
                        i++;

                    }

                }
            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the filtered problems \n");
        }
        
        
        logger.info("\n");
        return list;
        
        
    }
      
     
      
       public static ObservableList<String> getMachinesDesc()
    {
        
        
        
        conn=getConnection();
        ObservableList<String> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                   
                    try{
                   logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getAllMachinesDescInv()");
                   CallableStatement ps = conn.prepareCall("{call getAllMachinesDescInv()}");
                   
                   ResultSet rs=ps.executeQuery();
                   
                   while(rs.next())
                           {
                                list.add(rs.getString("RESULT"));
                                
                           }

               } catch (SQLException ex) {
                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
               }
               logger.info(Log4jConfiguration.currentTime()+"[INFO]: The list of machines from DB have been loaded! \n");
        }
        else
        {
             logger.error(Log4jConfiguration.currentTime()+"[ERROR] : Error while getting the machines from DB for combobox");
        }
        
        logger.info("\n");
        return list;
        
    }
      
       
       public static String getUserEngineer(int idesc)
    {
        
        conn=getConnection();
        String user=null;
        if(conn!=null)
        {
                try{
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getEscUser()");
                CallableStatement ps = conn.prepareCall("{call getEscUser(?)}");
                ps.setInt(1, idesc);
                ResultSet rs=ps.executeQuery();
                int i=1;
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST ENGINEERS USER: \n");
                if(rs.next())
                        {
                             user=rs.getString("Engineer_User");
                             logger.info("["+i+"]: "+user);
                             i++;
                        }

            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"ERROR]: Error while connecting to DB for getting the MACHINES\n");
        }
        
        return user;
        
    }
       
       public static String msgincidents(Incidents inc)
       {
           return "Incident's ID: "+inc.getId()+"\n"+
                   "Incident's Status: "+inc.getStatus()+"\n"+
                   "Incident's Short solution: "+inc.getShort_sol()+"\n"+
                   "Incident's Start date: "+inc.getStart_date()+"\n"+
                   "Incident's End date: "+inc.getEnd_date()+"\n"+
                   "Incident's SAP Order: "+inc.getSap()+"\n"+
                   "Incident's Escalation status: "+inc.getEscalation_status()+"\n";
       }
       public static ObservableList<Incidents> getIncidents(int idpb)
    {
        
        
        
        conn=getConnection();
        ObservableList<Incidents> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
            try{
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getIncidentsforaProblem()");
            CallableStatement ps = conn.prepareCall("{call getIncidentsforaProblem(?)}");
            ps.setInt(1, idpb);
            ResultSet rs=ps.executeQuery();
            int i=1;
            logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF INCIDENTS: \n");
            while(rs.next())
            {
                        long milisd=Long.valueOf(rs.getString("incident_Start_Date_hour"));
                        Date start_date = new Date(milisd);
                        DateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
                        String sd=sdf1.format(start_date);

                        long milied=Long.valueOf(rs.getString("incident_End_Date_hour"));
                        Date end_date=new Date(milied);
                        String ed=sdf1.format(end_date);
                        
                        int sap=rs.getInt("incident_SAP_Order_Nr");
                        
                        
                        if(sap!=0)
                        {
                                    if(rs.getInt("idEscalation_Assign")!=0)
                                    {
                                        Incidents inc=new Incidents(rs.getInt("idincident_list_history"),rs.getString("incident_Status"),
                                                rs.getString("incident_short_solution"),
                                    sd,ed,rs.getString("incident_SAP_Order_Nr"),
                                    rs.getString("Engineer"));
                                        list.add(inc);
                                        logger.info("["+i+"]: "+inc);
                                        i++;
                                    }
                                    else
                                    {
                                         Incidents inc=new Incidents(rs.getInt("idincident_list_history"),rs.getString("incident_Status"),rs.getString("incident_short_solution"),
                                    sd,ed,rs.getString("incident_SAP_Order_Nr"),
                                    "N/A");
                                         list.add(inc);
                                         logger.info("["+i+"]: "+inc);
                                         i++;
                                    }
                        }
                        else 
                        {
                                    
                                    if(rs.getInt("idEscalation_Assign")!=0)
                                    {
                                        Incidents inc=new Incidents(rs.getInt("idincident_list_history"),rs.getString("incident_Status"),
                                                rs.getString("incident_short_solution"),
                                    sd,ed,"no SAP Order",
                                    rs.getString("Engineer"));
                                        list.add(inc);
                                        logger.info("["+i+"]: "+inc);
                                        i++;
                                    }
                                    else
                                    {
                                         Incidents inc=new Incidents(rs.getInt("idincident_list_history"),rs.getString("incident_Status"),rs.getString("incident_short_solution"),
                                    sd,ed,"no SAP Order",
                                    "N/A");
                                         list.add(inc);
                                         logger.info("["+i+"]: "+inc);
                                         i++;
                                    }
                        }

                        


                    }
                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the incidents \n");
        }
        
        logger.info("\n");
        return list;
        
        
    }
       public static String msgincidentdetails(IncidentsDetails i)
       {
           return "Incident's ID: "+i.getId()+"\n"+
                   "Incident's IF: "+i.getIF()+"\n"+
                   "Incident's Line: "+i.getLine()+"\n"+
                   "Incident's Machine: "+i.getMachine()+"\n"+
                   "Incident's Engineer's User: "+i.getUser()+"\n"+
                   "Incident's Creator Username: "+i.getUser()+"\n"+
                     "Incident's HMI Error: "+i.getHmi()+"\n"+
                       "Incident's Problem Description: "+i.getDesc()+"\n"+
                   "Incident's Solution creator: "+i.getSol_creator()+"\n";
       }
       
       
        public static ObservableList<IncidentsDetails> getIncidentDetails(int incid)
    {
        
        conn=getConnection();
        ObservableList<IncidentsDetails> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getIncidentDetails()");
                    CallableStatement ps = conn.prepareCall("{call getIncidentDetails(?)}");
                    ps.setInt(1,incid);
                    ResultSet rs=ps.executeQuery();
                    int i=1;
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF INCIDENTS DETAILS: \n");
                    while(rs.next())
                    {
                         if(rs.getString("Engineer Name")!=null)
                         {
                              IncidentsDetails inc=new IncidentsDetails(rs.getInt("idincident_list_history"),
                                   rs.getString("IF"),rs.getString("Line"),
                                   rs.getString("Machine"),rs.getString("User Engineer"),rs.getString("Engineer Name"),
                                   rs.getString("problem_HMI_Error"),rs.getString("problem_Description"),rs.getString("incident_Solution_Creator"));
                            list.add(inc);
                            logger.info("["+i+"]: "+msgincidentdetails(inc));
                         }
                         else if(rs.getString("Technician Name")!=null)
                         {
                              IncidentsDetails inc=new IncidentsDetails(rs.getInt("idincident_list_history"),
                                   rs.getString("IF"),rs.getString("Line"),
                                   rs.getString("Machine"),rs.getString("User Engineer"),rs.getString("Technician Name"),
                                   rs.getString("problem_HMI_Error"),rs.getString("problem_Description"),rs.getString("incident_Solution_Creator"));
                            list.add(inc);
                            logger.info("["+i+"]: "+msgincidentdetails(inc));
                         }
                         else
                         {
                             IncidentsDetails inc=new IncidentsDetails(rs.getInt("idincident_list_history"),
                                   rs.getString("IF"),rs.getString("Line"),
                                   rs.getString("Machine"),rs.getString("User Engineer"),"",
                                   rs.getString("problem_HMI_Error"),rs.getString("problem_Description"),rs.getString("incident_Solution_Creator"));
                            list.add(inc);
                            logger.info("["+i+"]: "+msgincidentdetails(inc));
                         }
                           

                    }
                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting details of incidents \n");
        }
       
        logger.info("\n");
        return list;
        
        
    }
     
        
        public static IncidentEscalation getIncidentDetailsFromTicket(String inctick)
    {
        
        
        
        conn=getConnection();
        IncidentEscalation o=null;
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getIncidentDetailsFromTicket()");
                    CallableStatement ps = conn.prepareCall("{call getIncidentDetailsFromTicket(?)}");
                    ps.setString(1,inctick);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next())
                    {
                            if(rs.getString("Engineer Name")!=null)
                            {
                                o=new  IncidentEscalation(
                                    rs.getInt("idincident_list_history"),
                                   rs.getString("IF"),rs.getString("Line"),rs.getString("Machine"),rs.getString("incident_Start_Date_hour"),rs.getString("incident_End_Date_hour"),
                                    rs.getString("incident_Status"),rs.getString("Engineer Name"),rs.getString("incident_SAP_Order_Nr"),rs.getString("User Engineer"),
                                    rs.getString("problem_Description"),rs.getString("problem_HMI_Error"),rs.getString("idSpare_Parts"),
                                    rs.getString("incident_Solution_Creator"),rs.getString("incident_Solution_Escalation"));
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: INCIDENT'S DETAILS FROM TICKET FROM DB: \n"+
                                    "Incident's ID: "+o.getId()+"\n"+
                                    "Incident's IF: "+o.getIF()+"\n"+
                                    "Incident's Line: "+o.getLine()+"\n"+
                                    "Incident's Machine: "+o.getMachine()+"\n"+
                                    "Incident's Start date: "+o.getStart_date()+"\n"+
                                    "Incident's End date: "+o.getEnd_date()+"\n"+
                                    "Incident's Status: "+o.getStatus()+"\n"+
                                    "Incident's Creator username: "+o.getCreator_user()+"\n"+
                                    "Incident's SAP Order: "+o.getSap_order()+"\n"+
                                    "Incident's User Engineer: "+o.getEscalation_user()+"\n"+
                                    "Incident's Problem description: "+o.getPbdesc()+"\n"+
                                    "Incident's HMI Error: "+o.getHmi()+"\n"+
                                    "Incident's idSpare_Parts: "+o.getIdSpareParts()+"\n"+
                                    "Incident's Solution creator: "+o.getSol_creator()+"\n"+
                                    "Incident's Solution escalation: "+o.getSol_escalation()+"\n\n");
                            }
                            else if(rs.getString("Technician Name")!=null)
                            {
                                o=new  IncidentEscalation(
                                    rs.getInt("idincident_list_history"),
                                   rs.getString("IF"),rs.getString("Line"),rs.getString("Machine"),rs.getString("incident_Start_Date_hour"),rs.getString("incident_End_Date_hour"),
                                    rs.getString("incident_Status"),rs.getString("Technician Name"),rs.getString("incident_SAP_Order_Nr"),rs.getString("User Engineer"),
                                    rs.getString("problem_Description"),rs.getString("problem_HMI_Error"),rs.getString("idSpare_Parts"),
                                    rs.getString("incident_Solution_Creator"),rs.getString("incident_Solution_Escalation"));
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: INCIDENT'S DETAILS FROM TICKET FROM DB: \n"+
                                    "Incident's ID: "+o.getId()+"\n"+
                                    "Incident's IF: "+o.getIF()+"\n"+
                                    "Incident's Line: "+o.getLine()+"\n"+
                                    "Incident's Machine: "+o.getMachine()+"\n"+
                                    "Incident's Start date: "+o.getStart_date()+"\n"+
                                    "Incident's End date: "+o.getEnd_date()+"\n"+
                                    "Incident's Status: "+o.getStatus()+"\n"+
                                    "Incident's Creator username: "+o.getCreator_user()+"\n"+
                                    "Incident's SAP Order: "+o.getSap_order()+"\n"+
                                    "Incident's User Engineer: "+o.getEscalation_user()+"\n"+
                                    "Incident's Problem description: "+o.getPbdesc()+"\n"+
                                    "Incident's HMI Error: "+o.getHmi()+"\n"+
                                    "Incident's idSpare_Parts: "+o.getIdSpareParts()+"\n"+
                                    "Incident's Solution creator: "+o.getSol_creator()+"\n"+
                                    "Incident's Solution escalation: "+o.getSol_escalation()+"\n\n");
                            }
                            else
                            {
                                o=new  IncidentEscalation(
                                    rs.getInt("idincident_list_history"),
                                   rs.getString("IF"),rs.getString("Line"),rs.getString("Machine"),rs.getString("incident_Start_Date_hour"),rs.getString("incident_End_Date_hour"),
                                    rs.getString("incident_Status"),"",rs.getString("incident_SAP_Order_Nr"),rs.getString("User Engineer"),
                                    rs.getString("problem_Description"),rs.getString("problem_HMI_Error"),rs.getString("idSpare_Parts"),
                                    rs.getString("incident_Solution_Creator"),rs.getString("incident_Solution_Escalation"));
                            logger.info(Log4jConfiguration.currentTime()+"[INFO]: INCIDENT'S DETAILS FROM TICKET FROM DB: \n"+
                                    "Incident's ID: "+o.getId()+"\n"+
                                    "Incident's IF: "+o.getIF()+"\n"+
                                    "Incident's Line: "+o.getLine()+"\n"+
                                    "Incident's Machine: "+o.getMachine()+"\n"+
                                    "Incident's Start date: "+o.getStart_date()+"\n"+
                                    "Incident's End date: "+o.getEnd_date()+"\n"+
                                    "Incident's Status: "+o.getStatus()+"\n"+
                                    "Incident's Creator username: "+o.getCreator_user()+"\n"+
                                    "Incident's SAP Order: "+o.getSap_order()+"\n"+
                                    "Incident's User Engineer: "+o.getEscalation_user()+"\n"+
                                    "Incident's Problem description: "+o.getPbdesc()+"\n"+
                                    "Incident's HMI Error: "+o.getHmi()+"\n"+
                                    "Incident's idSpare_Parts: "+o.getIdSpareParts()+"\n"+
                                    "Incident's Solution creator: "+o.getSol_creator()+"\n"+
                                    "Incident's Solution escalation: "+o.getSol_escalation()+"\n\n");
                            }
                             
                         
                            


                    }
                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the incident's details from ticket \n");
        }
        
        return o;
        
        
    }
        public static List<Integer> getLinesid(int ifid)
    {
        
        
        conn=getConnection();
        List<Integer> list=new ArrayList<>();
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getLinesID()\n");
                    CallableStatement ps = conn.prepareCall("{call getLinesID(?)}");
                    ps.setInt(1,ifid);
                    ResultSet rs=ps.executeQuery();
                    int i=1;
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF IDs of LINES: \n");
                    while(rs.next())
                    {

                            list.add(rs.getInt("idline_structure"));
                            logger.info("["+i+"]: "+rs.getInt("idline_structure")+"\n");
                            i++;

                    }
                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting IDs of lines \n");
        }
        
        logger.info("\n");
        return list;
        
        
    }
        
        
        public static int getSparePartsId(String pn)
    {
        
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getSparePartsID()");
                    CallableStatement ps = conn.prepareCall("{call getSparePartsID(?)}");

                    ps.setString(1, pn);

                    ResultSet rs=ps.executeQuery();

                    if(rs.next())
                            {
                                 id=rs.getInt("idspare_parts");
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the IDs of SPARE PARTS \n");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: IDs of SPARE PARTS from DB: "+id+"\n\n");
        return id;
        
    }
        
      public static String getPartNumber(int id)
    {
        
       
        
        conn=getConnection();
        String pn=null;
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getSparePN()");
                    CallableStatement ps = conn.prepareCall("{call getSparePN(?)}");
                    ps.setInt(1, id);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next())
                            {
                                 pn=rs.getString("Part_Number");
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting PART NUMBER \n");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: PART NUMBER OF SPARE PART: "+pn+"\n\n");
        return pn;
        
    }
      
    public static String getidPartNumberfromIncidents(int idinc)
    {
        
        
        
        conn=getConnection();
        String pn=null;
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getSpareFromIncidents() ");
                    CallableStatement ps = conn.prepareCall("{call getSparefromIncidents(?)}");
                    ps.setInt(1, idinc);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next())
                            {
                                 if(rs.getString("idSpare_Parts")!=null)
                                 {
                                     pn=rs.getString("idSpare_Parts");
                                 }
                                 else pn="";

                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting IDs of SPARE PARTS");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: IDs of SPARE PARTS from DB: "+pn+"\n\n");
        
        return pn;
        
    }  
    
     public static int getEscIdforIncident(int idinc)
    {
        
        
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getidEscAssignfromIncident()");
                    CallableStatement ps = conn.prepareCall("{call getidEscAssignfromIncident(?)}");

                    ps.setInt(1, idinc);

                    ResultSet rs=ps.executeQuery();

                    if(rs.next())
                            {
                                 id=rs.getInt("idEscalation_Assign");
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB ");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The ESCALATION ASSIGN's ID from DB: "+id+"\n\n");
        return id;
        
    }
    
    public static String getFilePathfromIncident(int idinc)
    {
        
       
        conn=getConnection();
        String f=null;
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getFilePath()");
                    CallableStatement ps = conn.prepareCall("{call getFilePath(?)}");
                    ps.setInt(1, idinc);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next())
                            {
                                 if(rs.getString("incident_File_Path")!=null)
                                 {
                                     f=rs.getString("incident_File_Path");
                                 }
                                 else
                                 {
                                     f="";
                                 }

                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting FILE of INCIDENT \n");
        }
       
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The FILE from INCIDENT: "+f+"\n\n");
        return f;
        
    }  
    
    public static String getFilePathfromProblem(int idpb)
    {
        
        
        conn=getConnection();
        String f=null;
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getFileProblemHistory()");
                    CallableStatement ps = conn.prepareCall("{call getFileProblemHistory(?)}");
                    ps.setInt(1, idpb);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next())
                            {
                                 if(rs.getString("problem_File_Path")!=null)
                                 {
                                     f=rs.getString("problem_File_Path");
                                 }
                                 else
                                 {
                                     f="";
                                 }

                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the file of the problem \n");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The PROBLEM'S FILE from DB: "+f+"\n\n");
        return f;
        
    }  
    public static String getIncidentTicket(int idinc)
    {
        
        conn=getConnection();
        String ticket=null;
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getTicket()");
                    CallableStatement ps = conn.prepareCall("{call getTicket(?)}");
                    ps.setInt(1, idinc);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next())
                            {
                                ticket=rs.getString("incident_ticket");
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]:Error while connecting to DB for getting the ticket of incident \n");
        }
        
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The TICKET OF INCIDENT from DB: "+ticket+"\n\n");
        return ticket;
        
    }
    public static String msginchistory(IncidentHistory inch)
    {
        return "Incident's ID: "+inch.getId()+"\n"+
                "Incident's Start date: "+inch.getStart_date()+"\n"+
                "Incident's End date: "+inch.getEnd_date()+"\n"+
                "Incident's Status: "+inch.getStatus()+"\n"+
                "Incident's SAP Order: "+inch.getSap()+"\n"+
                "Incident's Solution creator: "+inch.getSol_creator()+"\n"+
                "Incident's Solution escalation: "+inch.getSol_escal()+"\n"+
                "Incident's Creator username: "+inch.getUser()+"\n"+
                "Incident's Engineer: "+inch.getEngineer()+"\n"+
                "Incident's Spare Parts's Part Numbers: "+inch.getPart_number_spare_parts()+"\n"+
                "Incident's Short solution: "+inch.getSol_escal()+"\n"+
                "Incident's File: "+inch.getFile_path()+"\n";

    }
     public static ObservableList<IncidentHistory> getIncidentHistory(int idpb)
    {
        
        conn=getConnection();
        ObservableList<IncidentHistory> list=FXCollections.observableArrayList();
        if(conn!=null)
        {
                        try{
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getIncidentHistory()");
                        CallableStatement ps = conn.prepareCall("{call getIncidentHistory(?)}");
                        ps.setInt(1,idpb);
                        ResultSet rs=ps.executeQuery();
                        int i=1;
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF INCIDENT LIST HISTORY OF THE PROBLEM SELECTED: \n");
                        while(rs.next())
                        {
                               //prelucrare data
                                long milisd=Long.valueOf(rs.getString("incident_Start_Date_hour"));
                                Date start_date = new Date(milisd);
                                DateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
                                String sd=sdf1.format(start_date);

                                long milied=Long.valueOf(rs.getString("incident_End_Date_hour"));
                                Date end_date=new Date(milied);
                                String ed=sdf1.format(end_date);

                               //prelucrare spare_parts
                               String spareFromDatabase=rs.getString("idSpare_Parts");
                               String spare_rezultat;
                               if(spareFromDatabase.length()==1 && spareFromDatabase.isEmpty()==false)
                               {
                                   spare_rezultat=ConnectionDatabase.getPartNumber(Integer.parseInt(spareFromDatabase));

                               }
                               else if(spareFromDatabase.length() >1 && spareFromDatabase.isEmpty()==false )
                               {
                                     String[] items = spareFromDatabase.split(";");
                                     List<String> itemList = Arrays.asList(items);
                                     List<String> Part_Numbere=new ArrayList<>();
                                     for(String idsp : itemList)
                                     {
                                         String pn=ConnectionDatabase.getPartNumber(Integer.parseInt(idsp));
                                         Part_Numbere.add(pn);
                                     }

                                     spare_rezultat=Part_Numbere.stream().collect(Collectors.joining("\n"));
                               }
                               else
                               {
                                   spare_rezultat="";
                               }
                                //prelucrare fisier
                                  int sap=rs.getInt("incident_SAP_Order_Nr");
                                  if(sap!=0)
                                  {
                                           if(!rs.getString("incident_File_Path").equals(""))
                                           {
                                               CheckBox ch=new CheckBox();
                                               ch.setSelected(true);
                                               ch.setDisable(true);
                                               ch.setStyle("-fx-opacity: 1");

                                              IncidentHistory inch=new IncidentHistory(rs.getInt("idincident_list_history"),
                                              sd,ed,
                                              rs.getString("incident_Status"),rs.getString("incident_SAP_Order_Nr"),rs.getString("incident_Solution_Creator"),
                                              rs.getString("incident_Solution_Escalation"),rs.getString("incident_Creator_username"),rs.getString("Engineer"),
                                              spare_rezultat,rs.getString("incident_short_solution"),ch,rs.getString("incident_File_Path"));
                                              list.add(inch);
                                              logger.info("["+i+"]: "+msginchistory(inch));
                                              i++;

                                           }
                                            else if(rs.getString("incident_File_Path").equals(""))
                                           {
                                             IncidentHistory inch=new IncidentHistory(rs.getInt("idincident_list_history"),
                                             sd,ed,
                                             rs.getString("incident_Status"),rs.getString("incident_SAP_Order_Nr"),rs.getString("incident_Solution_Creator"),
                                             rs.getString("incident_Solution_Escalation"),rs.getString("incident_Creator_username"),rs.getString("Engineer"),
                                             spare_rezultat,rs.getString("incident_short_solution"),null,rs.getString("incident_File_Path"));
                                             list.add(inch);
                                             logger.info("["+i+"]: "+msginchistory(inch));
                                             i++;
                                           }
                                  }
                                  else if(sap == 0)
                                  {
                                      if(!rs.getString("incident_File_Path").equals(""))
                                           {
                                               CheckBox ch=new CheckBox();
                                               ch.setSelected(true);
                                               ch.setDisable(true);
                                               ch.setStyle("-fx-opacity: 1");

                                              IncidentHistory inch=new IncidentHistory(rs.getInt("idincident_list_history"),
                                              sd,ed,
                                              rs.getString("incident_Status"),"no SAP Order",rs.getString("incident_Solution_Creator"),
                                              rs.getString("incident_Solution_Escalation"),rs.getString("incident_Creator_username"),rs.getString("Engineer"),
                                              spare_rezultat,rs.getString("incident_short_solution"),ch,rs.getString("incident_File_Path"));
                                              list.add(inch);
                                              logger.info("["+i+"]: "+msginchistory(inch));
                                              i++;

                                           }
                                            else if(rs.getString("incident_File_Path").equals(""))
                                           {
                                             IncidentHistory inch=new IncidentHistory(rs.getInt("idincident_list_history"),
                                             sd,ed,
                                             rs.getString("incident_Status"),"no SAP Order",rs.getString("incident_Solution_Creator"),
                                             rs.getString("incident_Solution_Escalation"),rs.getString("incident_Creator_username"),rs.getString("Engineer"),
                                             spare_rezultat,rs.getString("incident_short_solution"),null,rs.getString("incident_File_Path"));
                                             list.add(inch);
                                             logger.info("["+i+"]: "+msginchistory(inch));
                                             i++;
                                           }
                                  }
                                  


                        }
                    } catch (SQLException ex) {
                         logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                    }

        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting INCIDENTS HISTORY \n");
        }
       
        logger.info("\n");
        return list;
        
        
    }
     
     public static String getEscNameByUser(String user)
    {
        
       
        
        conn=getConnection();
        String name=null;
        if(conn!=null)
        {
                    try{
                   logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getEscalationNameAfterUser()");
                   CallableStatement ps = conn.prepareCall("{call getEscalationNameAfterUser(?)}");
                   ps.setString(1, user);
                   ResultSet rs=ps.executeQuery();
                   if(rs.next())
                           {
                                name=rs.getString("Engineer_Name");
                           }

               } catch (SQLException ex) {
                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
               }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting ENGINEER'S NAME \n");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]:ENGINEER'S NAME FROM DB: "+name+"\n\n");
        return name;
        
    }  
     
     public static String getLineDesc(int id)
    {
        
        
        conn=getConnection();
        String desc="";
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getLineDescAfterId");
                    CallableStatement ps = conn.prepareCall("{call getLineDescAfterId(?)}");
                    ps.setInt(1, id);
                    ResultSet rs=ps.executeQuery();
                    
                    if(rs.next())
                            {
                                 desc=rs.getString("Line_Name");
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting LINE'S NAME");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The LINE'S NAME from DB: "+desc);
        return desc;
        
    }
     
     public static String getMachineDesc(int id)
    {
         
        conn=getConnection();
        String desc="";
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getMachineDescAfterID()");
                    CallableStatement ps = conn.prepareCall("{call getMahineDescAfterID(?)}");
                    ps.setInt(1, id);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next())
                            {
                                 desc=rs.getString("Machine_Name");
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[EEROR]: Error while connecting to DB for getting the MACHINE's NAME");
        }
       
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: MACHINE'S NAME FROM DB: "+desc+"\n\n");
        return desc;
        
    }
     
     public static String getLineDescription(int id)
    {
        
        
        
        conn=getConnection();
        String desc="";
        if(conn!=null)
        {

                try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getLine()");
                    CallableStatement ps = conn.prepareCall("{call getLine(?)}");
                    ps.setInt(1, id);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next())
                            {
                                 desc=rs.getString("Line_Name");
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting LINE'S NAME \n");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: LINE'S NAME from DB: "+desc+"\n\n");
        return desc;
        
    }
     
     public static String getIfDescription(int id)
    {
        
        
        
        conn=getConnection();
        String desc="";
        if(conn!=null)
        {
                    try{
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getIfDescriptionforMachineStructure()");
                    CallableStatement ps = conn.prepareCall("{call getIfDescriptionforMachineStructure(?)}");
                    ps.setInt(1, id);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next())
                            {
                                 desc=rs.getString("IF_Description");
                            }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting IF's DESCRIPTION \n");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: THE IF'S DESCRIPTION FROM DB: "+desc+"\n\n");
        return desc;
        
    }
     public static String imgusers(Users u)
     {
         return "ID: "+u.getId()+"\n"+
                 "USERNAME: "+u.getUser()+"\n"+
                 "ROLE: "+u.getRole()+"\n";
     }
     
     public static List<Users>  getlevelAcces()
    {
        
        
        
        List<Users> lista=new ArrayList<>();
        conn=getConnection();
        
        if(conn!=null)
        {
            
            
             try {
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getUserAcess()");
                CallableStatement ps = conn.prepareCall("{call getUserAccess()}");
                ResultSet rs=ps.executeQuery();
                int i=1;
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF USERS FROM DB: \n");
                while(rs.next())
                {
                   
                    Users us=new Users(Integer.parseInt(rs.getString("idaccess")),rs.getString("access_username"),rs.getString("access_role"));
                    lista.add(us);
                    logger.info("["+i+"]: "+imgusers(us));
                    i++;
                }
                 
            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                 
                
            }
             logger.info(Log4jConfiguration.currentTime()+"[INFO]: Users from access were loaded");
            
        }
        else
        {
                logger.error(Log4jConfiguration.currentTime()+"[ERROR] : Error while connecting to DB for getting the list of persons from Access");
               
        
        }
        
        logger.info("\n");
        return lista;
        
    }
    
      
     public static List<String>  getIncidentsFiles(int idpb)
    {
        
        List<String> lista=new ArrayList<>();
        conn=getConnection();
        if(conn!=null)
        {
                    try {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getIncidentsFile()");
                    CallableStatement ps = conn.prepareCall("{call getIncidentsFile(?)}");
                    ps.setInt(1, idpb);
                    ResultSet rs=ps.executeQuery();
                    int i=1;
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF INCIDENTS'S FILES FROM DB: \n");
                    while(rs.next())
                    {
                        
                        lista.add(rs.getString("incident_File_Path"));
                        logger.info("["+i+"]: "+rs.getString("incident_File_Path")+"\n");
                        i++;

                    }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the FILES of INCIDENTS: ");
        }
        
        logger.info("\n");
        return lista;
        
        
    }
     public static ParticularDetailsProblem getParticularDetailsProblem(int idpb)
    {
        
        
        ParticularDetailsProblem pb=null;
        conn=getConnection();
        if(conn!=null)
        {
                    try {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getIFLineMachineEngineerUser()");
                    CallableStatement ps = conn.prepareCall("{call getIFLineMachineEngineerUser(?)}");
                    ps.setInt(1, idpb);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next())
                    {
                        pb=new ParticularDetailsProblem(rs.getInt("if_id"),rs.getInt("line_id"),rs.getInt("machine_id"),rs.getString("Engineer_User"));
                       
                        
                    }

                } catch (SQLException ex) {
                     logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting DETAILS ABOUT PROBLEM \n");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: The DETAILS ABOUT PROBLEM: \n"+
                "IF's ID: "+pb.getIf_id()+"\n"+
                "Line's ID: "+pb.getLine_id()+"\n"+
                "Machine's ID: "+pb.getMachine_id()+"\n"+
                "Engineer's User: "+pb.getEngineer_user()+"\n\n");
        return pb;
        
        
    }
     
     public static int getProblemIdFolder(int ifid,int lineid,int machineid,String hmi,String status,String user,String desc)
    {
        
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {
                        try{
                        logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getPbIdForFolder()");
                        CallableStatement ps = conn.prepareCall("{call getPbIdForFolder(?,?,?,?,?,?,?)}");
                        ps.setInt(1, ifid);
                        ps.setInt(2, lineid);
                        ps.setInt(3,machineid);
                        ps.setString(4,hmi);
                        ps.setString(5, status);
                        ps.setString(6, user);
                        ps.setString(7,desc );


                        ResultSet rs=ps.executeQuery();

                        if(rs.next())
                        {
                            id=rs.getInt("idproblem_list_history");
                        }

                    } catch (SQLException ex) {
                         logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
                    }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the PROBLEM'S ID \n");
        }
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: THE PROBLEM'S ID FROM DB: "+id+"\n\n");
        return id;
        
    }
    public static int getIncidentId(String ticket,int pbid,String sd,String ed,String status,String sol_creator,String user,String short_sol)
    {
        
        
        conn=getConnection();
        int id=0;
        if(conn!=null)
        {
                    try{
                   logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getIncidentId()");
                   CallableStatement ps = conn.prepareCall("{call getIncidentId(?,?,?,?,?,?,?,?)}");
                   ps.setString(1, ticket);
                   ps.setInt(2, pbid);
                   ps.setString(3,sd);
                   ps.setString(4,ed);
                   ps.setString(5, status);
                   
                   ps.setString(6,sol_creator);
                   ps.setString(7, user);
                   ps.setString(8, short_sol);


                   ResultSet rs=ps.executeQuery();

                   if(rs.next())
                           {
                                id=rs.getInt("idincident_list_history");
                           }

               } catch (SQLException ex) {
                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
               }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting INCIDENT'S ID \n");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: THE INCIDENT'S ID FROM DB: "+id+"\n\n");
        return id;
        
    }
    public static String getProblemFileName(int idpb)
    {
        
        conn=getConnection();
        String file="";
        if(conn!=null)
        {
                    try{
                   logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getProblemFileName()");
                   CallableStatement ps = conn.prepareCall("{call getProblemFileName(?)}");
                   ps.setInt(1, idpb);
                   ResultSet rs=ps.executeQuery();
                   if(rs.next())
                           {
                                file=rs.getString("problem_File_Path");
                           }

               } catch (SQLException ex) {
                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
               }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting PROBLEM'S FILE");
        }
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: THE PROBLEM'S FILE FROM DB: "+file+"\n\n");
        return file;
        
    }
    
    public static String getRolefromAccess(String user)
    {
        
       
        conn=getConnection();
        String role=null;
        if(conn!=null)
        {
                try{
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getRoleFromUser()");
                CallableStatement ps = conn.prepareCall("{call getRoleFromUser(?)}");
                ps.setString(1, user);
                ResultSet rs=ps.executeQuery();
                if(rs.next())
                        {
                             role=rs.getString("access_role");
                        }

            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the ROLE OF USER FROM ACCESS ");
        }
       
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: USER'S ROLE from DB : "+role+"\n\n");
        return role;
        
    }
    public static String getAppEndDate()
    {
        
       
        conn=getConnection();
        String datedb=null;
        if(conn!=null)
        {
            try {
                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getAppEndDate()");
                    CallableStatement get = conn.prepareCall("{call getAppEndDate()}");
                    ResultSet rs=get.executeQuery();
                    if(rs.next())
                    {
                        datedb=rs.getString("End_Application_Date");
                    }
             } catch (SQLException ex) {
            logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: "+ex+"\n\n");
           }
            
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the DATE WHEN APP WAS CLOSED ");
        }
        
       logger.info(Log4jConfiguration.currentTime()+"[INFO]: LAST TIME WHEN APP has been CLOSED: "+datedb+"\n");
        return datedb;
            
    }
    
     public static String getPartNumberSpareCheck(String pn,String spid,String spdes,String spdet)
    {
        
        conn=getConnection();
        String partnumber=null;
        if(conn!=null)
        {
                try{ 
                logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure checkExistSpareGetAndInsert()");
                
                CallableStatement ps = conn.prepareCall("{call checkExistSpareGetAndInsert(?,?,?,?)}");
                ps.setString(1, pn);
                ps.setString(2, spid);
                ps.setString(3, spdes);
                ps.setString(4, spdet);
                ResultSet rs=ps.executeQuery();
                if(rs.next())
                        {
                             partnumber=rs.getString("Part_Number");
           
                        }

            } catch (SQLException ex) {
                 logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);
            }
        }
        else
        {
            logger.error(Log4jConfiguration.currentTime()+"ERROR]: Error while connecting to DB for getting the PART NUMBER\n");
        }
        
        logger.info(Log4jConfiguration.currentTime()+"[INFO]: PART NUMBER FROM DB: "+partnumber+"\n\n");
        return partnumber;
        
    }
     
    


public static ObservableList<String> getAllEscUser()

        {

            conn= ConnectionDatabase.getConnection();

            ObservableList<String> list=FXCollections.observableArrayList();

            if(conn!=null)

            {

 

                try{

                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getAllEscUser()");

                    CallableStatement ps = conn.prepareCall("{call getAllEscUser()}");

                    ResultSet rs=ps.executeQuery();

                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: OUTPUT LIST OF ENGINEERS from DB: \n");

                    int i=1;

                    while(rs.next())

                    {

                        list.add(rs.getString("Engineer_User"));

                        logger.info("["+i+"]: "+rs.getString("Engineer_User")+"\n");

                        i++;

                    }

 

                } catch (SQLException ex) {

                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);

                }

            }

            else

            {

                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the ENGINEERS\n");

            }

           

            logger.info("\n");

            return list;

 

        }

       public static  Map<String,HashMap<String,Integer>> getDataDiagram(int ifid)
       {
            conn= ConnectionDatabase.getConnection();
            
            HashMap<String,HashMap<String,Integer>> map=new HashMap<>();
            if(conn!=null)

            {

                try{

                    logger.info(Log4jConfiguration.currentTime()+"[INFO]: calling procedure getDataForDiagram()");

                    CallableStatement ps = conn.prepareCall("{call getDataForDiagram(?)}");
                    ps.setInt(1,ifid);
                    ResultSet rs=ps.executeQuery();
                    while(rs.next())

                    {
                        HashMap<String,Integer> week_data=new HashMap<>();
                        week_data.put("Monday", rs.getInt("Nr_Monday"));
                        week_data.put("Tuesday", rs.getInt("Nr_Tuesday"));
                        week_data.put("Wednesday", rs.getInt("Nr_Wednesday"));
                        week_data.put("Thursday", rs.getInt("Nr_Thursday"));
                        week_data.put("Friday", rs.getInt("Nr_Friday"));
                        week_data.put("Saturday", rs.getInt("Nr_Saturday"));
                        week_data.put("Sunday", rs.getInt("Nr_Sunday"));
                        map.put(rs.getString("Line_Name"),week_data);
                        
                        //System.out.println(week_data);
                        //System.out.println("FROM DB: "+rs.getString("Line_Name")+"\n"+
                          //      rs.getInt("Nr_Friday") + "\n"+
                            //    rs.getInt("Nr_Saturday"));
                        
                        
                        
                    }

                

                } catch (SQLException ex) {

                    logger.fatal(Log4jConfiguration.currentTime()+"[FATAL]: CONNECTION DATABASE --- SQLException: " + ex);

                }

            }

            else

            {

                logger.error(Log4jConfiguration.currentTime()+"[ERROR]: Error while connecting to DB for getting the NUMBER OF INCIDENTS PER WEEK\n");

            }

           

            logger.info("\n");

            return map;
         
           
       }

 
    
    
    
}
