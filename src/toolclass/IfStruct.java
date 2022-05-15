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
public class IfStruct {
    int id;
    String name;
    String description;
    int cc;
    

    public IfStruct(int id, String name, String description, int cc) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cc = cc;
        
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCc() {
        return cc;
    }

  
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    
    
    
    
}
