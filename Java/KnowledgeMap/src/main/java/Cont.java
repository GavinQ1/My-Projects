/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *
 * @author Gavin
 */
@Entity
@Table(name = "cont")
public class Cont {
    private int i;
    
    public Cont() {
        this(0);
    }
    
    public Cont(int i) {
        this.i = i;
    }
    
    @Id
    public int getI() { return i; }
    public void setI(int i) { this.i = i; }
}
