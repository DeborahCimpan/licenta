/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolclass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cimpde1
 */
public class LineStruct  {
    int id;
    int ifid;
    String name,functionalplace;
    int cc;
    

    public LineStruct(int id, int ifid, String name, String functionalplace, int cc) {
        this.id = id;
        this.ifid = ifid;
        this.name = name;
        this.functionalplace= functionalplace;
        this.cc = cc;
        
    }

    public int getId() {
        return id;
    }

    public int getIfid() {
        return ifid;
    }

    public String getName() {
        return name;
    }

    

    public int getCc() {
        return cc;
    }

    

    public void setId(int id) {
        this.id = id;
    }

    public void setIfid(int ifid) {
        this.ifid = ifid;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public void setCc(int cc) {
        this.cc = cc;
    }

    public String getFunctionalplace() {
        return functionalplace;
    }

    public void setFunctionalplace(String functionalplace) {
        this.functionalplace = functionalplace;
    }

    @Override
    public String toString() {
        return "LineStruct{" + "id=" + id + ", ifid=" + ifid + ", name=" + name + ", functionalplace=" + functionalplace + ", cc=" + cc + '}';
    }
    
    
    

  
}
