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
public class IncidentEscalation {
    int id;
    String IF;
    String line;
    String machine;
    String start_date;
    String end_date;
    String status;
    String creator_user;
    String sap_order;
    String escalation_user;
    String pbdesc;
    String hmi;
    String idSpareParts;
    String sol_creator;
    String sol_escalation;

    public IncidentEscalation(int id,String IF, String line, String machine, String start_date, String end_date, String status, String creator_user, String sap_order, String escalation_user, String pbdesc, String hmi, String idSpareParts, String sol_creator, String sol_escalation) {
        this.IF = IF;
        this.line = line;
        this.machine = machine;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.creator_user = creator_user;
        this.sap_order = sap_order;
        this.escalation_user = escalation_user;
        this.pbdesc = pbdesc;
        this.hmi = hmi;
        this.idSpareParts = idSpareParts;
        this.sol_creator = sol_creator;
        this.sol_escalation = sol_escalation;
        this.id=id;
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

    public String getCreator_user() {
        return creator_user;
    }

    public void setCreator_user(String creator_user) {
        this.creator_user = creator_user;
    }

    public String getSap_order() {
        return sap_order;
    }

    public void setSap_order(String sap_order) {
        this.sap_order = sap_order;
    }

    public String getEscalation_user() {
        return escalation_user;
    }

    public void setEscalation_user(String escalation_user) {
        this.escalation_user = escalation_user;
    }

    public String getPbdesc() {
        return pbdesc;
    }

    public void setPbdesc(String pbdesc) {
        this.pbdesc = pbdesc;
    }

    public String getHmi() {
        return hmi;
    }

    public void setHmi(String hmi) {
        this.hmi = hmi;
    }

    public String getIdSpareParts() {
        return idSpareParts;
    }

    public void setIdSpareParts(String idSpareParts) {
        this.idSpareParts = idSpareParts;
    }

    public String getSol_creator() {
        return sol_creator;
    }

    public void setSol_creator(String sol_creator) {
        this.sol_creator = sol_creator;
    }

    public String getSol_escalation() {
        return sol_escalation;
    }

    public void setSol_escalation(String sol_escalation) {
        this.sol_escalation = sol_escalation;
    }

    @Override
    public String toString() {
        return "IncidentEscalation{" + "id=" + id + ", IF=" + IF + ", line=" + line + ", machine=" + machine + ", start_date=" + start_date + ", end_date=" + end_date + ", status=" + status + ", creator_user=" + creator_user + ", sap_order=" + sap_order + ", escalation_user=" + escalation_user + ", pbdesc=" + pbdesc + ", hmi=" + hmi + ", idSpareParts=" + idSpareParts + ", sol_creator=" + sol_creator + ", sol_escalation=" + sol_escalation + '}';
    }

    
    
    
    
}
