/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gavin
 */
import java.io.Serializable;
import javax.persistence.*;
 
/**
 * The persistent class for the contact database table.
 * 
 */
@Entity
@Table(name = "Contacts")
public class Contact implements Serializable {
    @Id
    @Basic
    private Integer id;
    @Basic
    private String name;
    @Basic
    private String email;
    @Embedded
    private Cont i;
 
    public Contact() {
 
    }
 
    public Contact(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.i = new Cont(id);
    }
    
    public Cont getCont() { return i; }
    
    public void setCont(int i) { this.i = new Cont(i); }
 
    public Integer getId() {
        return this.id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getName() {
        return this.name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
}