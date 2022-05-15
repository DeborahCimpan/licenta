/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolclass;

/**
 *
 * @author cimpde1
 */
public class IncidentsDetails {
    public int id;
    public String IF;
    public String line;
    public String machine;
    public String escal_user;
    public String user;
    public String hmi;
    public String desc;
    public String sol_creator;

    public IncidentsDetails(int id, String IF, String line, String machine, String escal_user, String user, String hmi, String desc, String sol_creator) {
        this.id = id;
        this.IF = IF;
        this.line = line;
        this.machine = machine;
        this.escal_user = escal_user;
        this.user = user;
        this.hmi = hmi;
        this.desc = desc;
        this.sol_creator = sol_creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIF() {
        return IF;
    }

    public void setIF(String IF) {
        this.IF = IF;
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

    public String getEscal_user() {
        return escal_user;
    }

    public void setEscal_user(String escal_user) {
        this.escal_user = escal_user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHmi() {
        return hmi;
    }

    public void setHmi(String hmi) {
        this.hmi = hmi;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSol_creator() {
        return sol_creator;
    }

    public void setSol_creator(String sol_creator) {
        this.sol_creator = sol_creator;
    }

    @Override
    public String toString() {
        return "IncidentsDetails{" + "id=" + id + ", IF=" + IF + ", line=" + line + ", machine=" + machine + ", escal_user=" + escal_user + ", user=" + user + ", hmi=" + hmi + ", desc=" + desc + ", sol_creator=" + sol_creator + '}';
    }

   
    
    
    
}
