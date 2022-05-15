/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tslessonslearneddatabase;

import javafx.collections.ObservableList;
import toolclass.Incidents;

/**
 *
 * @author cimpde1
 */
public class Data {
   
    public static String comboifvalue="";
    public static String combolinevalue="";
    //----------------------------------------if_structure---------------------------------------------
    public static String if_name="";
    public static String if_desc="";
    public static int if_cc=-1;
    public static int id_if_structure=-1;
     //----------------------------------------line_structure--------------------------------------------
    public static int ifid=-1;
    public static String line_name="";
    public static String line_functional_place="";
    public static int line_cc=-1;
    public static int id_line_structure=-1;
    
    public static String comboifforline="";
    
    public static String comboifback="";
    //--------------------------------------machine_structure-------------------------------------------
     public static int machinelineid=-1;
    public static String machine_desc="";
    public static String machine_name="";
    public static int machine_cc=-1;
    public static String machine_invn="";
    public static int id_machine_structure=-1;
    public static int line_id_machine_structure=-1;
    
    //-----------------------------------------shift_team--------------------------------------------
    public static String tehncian="";
    public static String jobdescription="";
    public static int id_sh_team=-1; 
     public static String username="";
    public static String email="";
    
     //---------------------------------------escalation team-------------------------------------------------
    
        public static String engineer_name;
        public static String engineer_job_desc="";
        public static String engineer_user="";
        public static String engineer_email="";
        public static int id_esc_team=-1;
     //----------------------------------------spare_parts---------------------------------------------
    public static String part_number;
    public static String spare_id;
    public static String desc_spare_parts;
    public static String det_spare_parts;
    public static int id_spare_parts=-1;
    //---------------------------------------access--------------------------------------------------
    public static String usernameaccess;
    public static String roleaccess;
    public static int id_access;
    
    //new problem 
    public static String spare_part_numbers;
    //------------------------------------- overview--------------------------------------------------
    public static int id_pb=-1;
    public static String user_engineer_escalated="";
    public static ObservableList<Incidents> incidents;
    public static String problem_file_path="";
    
    //--------------------------------------incidents_details-----------------------------------------
    //id din lista pentru incident-ul selectat
    public static int id_incident_list=-1;
    //id din incident_list_history
    public static int idincident_list_history=-1;
    //din incident_list
    public static int last_index=-1;
    
    public static String sol_creator="";
    public static String sol_escal="";
    public static String creator_username="";
    public static int idEscaAssignIncident=-1;
    public static String status="";
    public static String file_path="";
    public static String short_sol="";
    public static int id_pb_history=-1;
    public static String incident_ticket="";
    
    //-----------------------------------------INCIDENT LIST HISTORY------------------------------------------------------------------------
    public static int id_incident_history=-1;
    public static String incident_Start_Date_hour=""; 
    public static String incident_End_Date_hour =""; 
    public static String incident_Status=""; 
    public static String incident_SAP_Order_Nr=""; 
    public static String incident_Solution_Creator=""; 
    public static String incident_Solution_Escalation =""; 
    public static String incident_Creator_username =""; 
    public static String incident_history_engineer="";  
    public static String incident_history_spare_parts =""; 
    public static String incident_File_Path =""; 
    public static String incident_short_solution="";
    
    //----------------------------------------PROBLEM LIST HISTORY--------------------------------------------------------------------------------
    public static int ifid__value_problem_history=-1;
    public static int lineid_value_problem_history=-1;
    public static int machineid_value_problem_history=-1;
    public static String user_problem_history="";
    public static String status_problem_history="";
    public static String engineer_problem_history="";
    public static String engineer_user_problem_history="";
    public static String problem_description_problem_history="";
    public static String hmi_problem_history="";
    public static String file_path_problem_history="";
    
    
    //NEW INCIDENT EXISTING PROBLEM
    //THE CONTOR FOR FOLDERS
    //public static int contor=1;
    
    
    
    
    
    
    
    
    
    

    public static String getSpare_part_numbers() {
        return spare_part_numbers;
    }

    public static void setSpare_part_numbers(String spare_part_numbers) {
        Data.spare_part_numbers = spare_part_numbers;
    }
    
    public boolean checkSparePartsNull()
    {
        String result = getSpare_part_numbers();
        if (result != null) {
            // success
            return false;
        }
        else{
            // failure
            return true;
         }
    }
    
    
    
    
}
