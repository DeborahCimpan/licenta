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
public class Incidents {
    public int id;
    public String status;
    public String short_sol;
    public String start_date;
    public String end_date;
    public String sap;
    public String escalation_status;

    public Incidents(int id,String status, String short_sol, String start_date, String end_date, String sap, String ecalation_status) {
        this.id = id;
        
        this.short_sol = short_sol;
        this.start_date = start_date;
        this.end_date = end_date;
        this.sap = sap;
        this.escalation_status = ecalation_status;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShort_sol() {
        return short_sol;
    }

    public void setShort_sol(String short_sol) {
        this.short_sol = short_sol;
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

    public String getSap() {
        return sap;
    }

    public void setSap(String sap) {
        this.sap = sap;
    }

   

    public String getEscalation_status() {
        return escalation_status;
    }

    public void setEscalation_status(String escalation_status) {
        this.escalation_status = escalation_status;
    }

    @Override
    public String toString() {
        return "Incidents{" + "id=" + id + ", status=" + status + ", short_sol=" + short_sol + ", start_date=" + start_date + ", end_date=" + end_date + ", sap=" + sap + ", escalation_status=" + escalation_status + '}';
    }

    

    
    
}
