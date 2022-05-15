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
public class EscalationTeam {
    int id;
    String name;
    String job;
    String user;
    String email;

    public EscalationTeam(int id, String name, String job,String user,String email) {
        this.id = id;
        this.name = name;
        this.job=job;
        this.user=user;
        this.email=email;
        
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

   
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   
}
