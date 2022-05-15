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
public class ShTeam {
    
    int id;
    String nume;
    String job;
    String username;
    String email;

    public ShTeam(int id,String nume, String job,String username,String email) {
        this.nume = nume;
        this.job = job;
        this.username=username;
        this.email=email;
        this.id=id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNume() {
        return nume;
    }

    public String getJob() {
        return job;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setJob(String job) {
        this.job = job;
    }

    
    
    
    
}
