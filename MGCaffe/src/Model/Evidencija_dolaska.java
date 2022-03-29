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
public class Evidencija_dolaska extends GenericObject implements Serializable{
    Integer id;
    String jmbg;
    Timestamp vreme_dolaska;

    public Evidencija_dolaska(Integer id, String jmbg, Timestamp vreme_dolaska) {
        this.id = id;
        this.jmbg = jmbg;
        this.vreme_dolaska = vreme_dolaska;
    }

    public Evidencija_dolaska() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public Timestamp getVreme_dolaska() {
        return vreme_dolaska;
    }

    public void setVreme_dolaska(Timestamp vreme_dolaska) {
        this.vreme_dolaska = vreme_dolaska;
    }
    
     @Override
    public boolean equals(Object o) {
        if (!(o instanceof Evidencija_dolaska)) {
            return false;
        }
        Evidencija_dolaska evidencija = (Evidencija_dolaska) o;
        return id == evidencija.id;
    }

    @Override
    public String toString() {
        return "Korisnici{" + "id=" + id + ", JMBG=" + jmbg + ", vreme dolaska="
                + vreme_dolaska + '}';
    }

    @Override
    public String getClassName() {
        return "evidencija_dolaska";
    }

    @Override
    public String getColumns() {
        return "id,jmbg,vreme_dolaska";
    }

    @Override
    public String getValues() {
        return "(select coalesce(max(ed.id),0)+1 from evidencija_dolaska ed),'"+jmbg+"','"+vreme_dolaska+"'";
    }

    @Override
    public String makeWhere() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String makeSet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
