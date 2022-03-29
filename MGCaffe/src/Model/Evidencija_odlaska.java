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
public class Evidencija_odlaska extends GenericObject implements Serializable{
    Integer id;
    String jmbg;
    Timestamp vreme_odlaska;
    Integer pazar;
    String datum;

    public Evidencija_odlaska(Integer id, String jmbg, Timestamp vreme_odlaska, Integer pazar) {
        this.id = id;
        this.jmbg = jmbg;
        this.vreme_odlaska = vreme_odlaska;
        this.pazar = pazar;
    }

    public Evidencija_odlaska(String datum,Integer pazar) {
        this.datum = datum;
        this.pazar = pazar;
    }

    
    
    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public Timestamp getVreme_odlaska() {
        return vreme_odlaska;
    }

    public void setVreme_odlaska(Timestamp vreme_odlaska) {
        this.vreme_odlaska = vreme_odlaska;
    }

    public Integer getPazar() {
        return pazar;
    }

    public void setPazar(Integer pazar) {
        this.pazar = pazar;
    }
    
            
    
    @Override
    public String getClassName() {
        return "evidencija_odlaska";
    }

    @Override
    public String getColumns() {
         return "id,jmbg,vreme_odlaska,pazar";
    }

    @Override
    public String getValues() {
     return "(select coalesce(max(ed.id),0)+1 from evidencija_dolaska ed),'"+jmbg+"','"+vreme_odlaska+"',"+pazar;   
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
