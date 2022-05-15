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
public class MachineStruct {
    public int id;
    public int line_id;
    public String name;
    public String invnumber;

    public MachineStruct(int id, int line_id, String name, String invnumber) {
        this.id = id;
        this.line_id = line_id;
        
        this.name = name;
        
        this.invnumber = invnumber;
    }

    @Override
    public String toString() {
        return "MachineStruct{" + "id=" + id + ", line_id=" + line_id + ", name=" + name + ", invnumber=" + invnumber + '}';
    }

    
    

    public int getId() {
        return id;
    }

    public int getLine_id() {
        return line_id;
    }

   


    public String getName() {
        return name;
    }

   
  

    public void setId(int id) {
        this.id = id;
    }

    public void setLine_id(int line_id) {
        this.line_id = line_id;
    }

  
  
   

    public void setName(String name) {
        this.name = name;
    }

   

    public String getInvnumber() {
        return invnumber;
    }

    public void setInvnumber(String invnumber) {
        this.invnumber = invnumber;
    }
    
    
    
    
    
}
