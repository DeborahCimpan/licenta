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
public class SparePartsPinguin {
    
    String part_number;
    String spare_id;
    String spare_desc;
    String spare_details;

    public SparePartsPinguin(String part_number, String spare_id, String spare_desc, String spare_details) {
        this.part_number = part_number;
        this.spare_id = spare_id;
        this.spare_desc = spare_desc;
        this.spare_details = spare_details;
    }

    public String getPart_number() {
        return part_number;
    }

    public void setPart_number(String part_number) {
        this.part_number = part_number;
    }

    public String getSpare_id() {
        return spare_id;
    }

    public void setSpare_id(String spare_id) {
        this.spare_id = spare_id;
    }

    public String getSpare_desc() {
        return spare_desc;
    }

    public void setSpare_desc(String spare_desc) {
        this.spare_desc = spare_desc;
    }

    public String getSpare_details() {
        return spare_details;
    }

    public void setSpare_details(String spare_details) {
        this.spare_details = spare_details;
    }

    @Override
    public String toString() {
        return "SparePartsPinguin{" + "part_number=" + part_number + ", spare_id=" + spare_id + ", spare_desc=" + spare_desc + ", spare_details=" + spare_details + '}';
    }
    
    
    
    
    
}
