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
public class ParticularDetailsProblem {
    int if_id;
    int line_id;
    int machine_id;
    String engineer_user;

    public ParticularDetailsProblem(int if_id, int line_id, int machine_id, String engineer_user) {
        this.if_id = if_id;
        this.line_id = line_id;
        this.machine_id = machine_id;
        this.engineer_user = engineer_user;
    }

    public int getIf_id() {
        return if_id;
    }

    public void setIf_id(int if_id) {
        this.if_id = if_id;
    }

    public int getLine_id() {
        return line_id;
    }

    public void setLine_id(int line_id) {
        this.line_id = line_id;
    }

    public int getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(int machine_id) {
        this.machine_id = machine_id;
    }

    public String getEngineer_user() {
        return engineer_user;
    }

    public void setEngineer_user(String engineer_user) {
        this.engineer_user = engineer_user;
    }

    @Override
    public String toString() {
        return "ParticularDetailsProblem{" + "if_id=" + if_id + ", line_id=" + line_id + ", machine_id=" + machine_id + ", engineer_user=" + engineer_user + '}';
    }
    
    
    
}
