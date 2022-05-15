/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolclass;

import javafx.scene.control.CheckBox;

/**
 *
 * @author cimpde1
 */
public class IncidentHistory {
    public int id;
    public String start_date;
    public String end_date;
    public String status;
    public String sap;
    public String sol_creator;
    public String sol_escal;
    public String user;
    public String engineer;
    public String part_number_spare_parts;
    public String short_sol;
    public CheckBox file;
    public String file_path;

    public IncidentHistory(int id,String start_date, String end_date, String status, String sap, String sol_creator, String sol_escal, String user, String engineer, String part_number_spare_parts, String short_sol, CheckBox file,String file_path) {
        this.id=id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.sap = sap;
        this.sol_creator = sol_creator;
        this.sol_escal = sol_escal;
        this.user = user;
        this.engineer = engineer;
        this.part_number_spare_parts = part_number_spare_parts;
        this.short_sol = short_sol;
        this.file = file;
        this.file_path=file_path;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public CheckBox getFile() {
        return file;
    }

    public void setFile(CheckBox file) {
        this.file = file;
    }
    
    

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSap() {
        return sap;
    }

    public void setSap(String sap) {
        this.sap = sap;
    }

    

    public String getSol_creator() {
        return sol_creator;
    }

    public void setSol_creator(String sol_creator) {
        this.sol_creator = sol_creator;
    }

    public String getSol_escal() {
        return sol_escal;
    }

    public void setSol_escal(String sol_escal) {
        this.sol_escal = sol_escal;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getPart_number_spare_parts() {
        return part_number_spare_parts;
    }

    public void setPart_number_spare_parts(String part_number_spare_parts) {
        this.part_number_spare_parts = part_number_spare_parts;
    }

    public String getShort_sol() {
        return short_sol;
    }

    public void setShort_sol(String short_sol) {
        this.short_sol = short_sol;
    }

    @Override
    public String toString() {
        return "IncidentHistory{" + "id=" + id + ", start_date=" + start_date + ", end_date=" + end_date + ", status=" + status + ", sap=" + sap + ", sol_creator=" + sol_creator + ", sol_escal=" + sol_escal + ", user=" + user + ", engineer=" + engineer + ", part_number_spare_parts=" + part_number_spare_parts + ", short_sol=" + short_sol + ", file=" + file + ", file_path=" + file_path + '}';
    }

    
    

    
    
    
    
}
