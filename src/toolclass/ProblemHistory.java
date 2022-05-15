/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolclass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.CheckBox;

/**
 *
 * @author cimpde1
 */
public class ProblemHistory {
   public int id;
   public String line;
   public String machine;
   public String user;
   public String status;
   public String hmi;
   public String engineer;
   public String problem_description;
   public String file_path;
   public CheckBox file;
   public int nr_incidents;
   
   
   

    public ProblemHistory(int id,String line, String machine, String user, String status, 
            String hmi, String engineer, String problem_description, CheckBox file,String file_path,
            int nr_incidents) 
    {
        this.line = line;
        this.machine = machine;
        this.user = user;
        this.status = status;
        this.hmi = hmi;
        this.engineer = engineer;
        this.problem_description = problem_description;
        this.file = file;
        this.nr_incidents = nr_incidents;
        this.id=id;
        this.file_path=file_path;
    }

   

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
    

   

    

    
     
   

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHmi() {
        return hmi;
    }

    public void setHmi(String hmi) {
        this.hmi = hmi;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getProblem_description() {
        return problem_description;
    }

    public void setProblem_description(String problem_description) {
        this.problem_description = problem_description;
    }

    public CheckBox getFile() {
        return file;
    }

    public void setFile(CheckBox file) {
        this.file = file;
    }

   

    public int getNr_incidents() {
        return nr_incidents;
    }

    public void setNr_incidents(int nr_incidents) {
        this.nr_incidents = nr_incidents;
    }
    
    public boolean checkEngineerNull()
    {
        String result = getEngineer();
        if (result != null) {
            // success
            return false;
        }
        else{
            // failure
            return true;
         }
    }
    
    public boolean checkLineNull()
    {
        String result =getLine();
        if (result != null) {
            // success
            return false;
        }
        else{
            // failure
            return true;
         }
    }
    public boolean checkMachineNull()
    {
        String result =getMachine();
        if (result != null) {
            // success
            return false;
        }
        else{
            // failure
            return true;
         }
    }
    
    

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProblemHistory{" + "id=" + id + ", line=" + line + ", machine=" + machine + ", user=" + user + ", status=" + status + ", hmi=" + hmi + ", engineer=" + engineer + ", problem_description=" + problem_description + ", file_path=" + file_path + ", file=" + file + ", nr_incidents=" + nr_incidents + '}';
    }
    
    
    
    
    
    
}
