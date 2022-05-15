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
public class Overview {
   public int id;
   public String line;
   public String machine;
   public String hmi;
   public String problem_description;
   public String nr_incidents;

    public Overview(int id,  String line, String machine, String hmi, String problem_description, String nr_incidents) {
        this.id = id;
        this.line = line;
        this.machine = machine;
        this.hmi = hmi;
        this.problem_description = problem_description;
        this.nr_incidents = nr_incidents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getHmi() {
        return hmi;
    }

    public void setHmi(String hmi) {
        this.hmi = hmi;
    }

    public String getProblem_description() {
        return problem_description;
    }

    public void setProblem_description(String problem_description) {
        this.problem_description = problem_description;
    }

    public String getNr_incidents() {
        return nr_incidents;
    }

    public void setNr_incidents(String nr_incidents) {
        this.nr_incidents = nr_incidents;
    }

   
    @Override
    public String toString() {
        return "Overview{" + "id=" + id + ", line=" + line + ", machine=" + machine + ", hmi=" + hmi + ", problem_description=" + problem_description + ", nr_incidents=" + nr_incidents + '}';
    }

    
   
   
   

   
}
