/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author grozd
 */
public class EvidencijaJOIN implements Serializable{
    private static final long SerialVersionUID = 34987236925643l;
    private Integer id;
    private String JMBG;
    private Timestamp vreme_dolaska;
    private Timestamp vreme_odlaska;

    public EvidencijaJOIN() {
    }

    public EvidencijaJOIN(Integer id, String JMBG, Timestamp vreme_dolaska, Timestamp vreme_odlaska) {
        this.id = id;
        this.JMBG = JMBG;
        this.vreme_dolaska = vreme_dolaska;
        this.vreme_odlaska = vreme_odlaska;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJMBG() {
        return JMBG;
    }

    public void setJMBG(String JMBG) {
        this.JMBG = JMBG;
    }

    public Timestamp getVreme_dolaska() {
        return vreme_dolaska;
    }

    public void setVreme_dolaska(Timestamp vreme_dolaska) {
        this.vreme_dolaska = vreme_dolaska;
    }

    public Timestamp getVreme_odlaska() {
        return vreme_odlaska;
    }

    public void setVreme_odlaska(Timestamp vreme_odlaska) {
        this.vreme_odlaska = vreme_odlaska;
    }
    
    
}
