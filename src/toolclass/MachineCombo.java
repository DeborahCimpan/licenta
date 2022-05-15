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
public class MachineCombo {
    int machine_id;
    String machine_description;

    public MachineCombo(int machine_id, String machine_description) {
        this.machine_id = machine_id;
        this.machine_description = machine_description;
    }

    public int getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(int machine_id) {
        this.machine_id = machine_id;
    }

    public String getMachine_description() {
        return machine_description;
    }

    public void setMachine_description(String machine_description) {
        this.machine_description = machine_description;
    }
    
    
}
