/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author grozd
 */
public class Pice extends GenericObject implements Serializable {
    private static final long SerialVersionUID = 2713319934254l;
    private Integer id;
    private String ime;
    private String novoIme;
    private Integer cena;
    private Integer novaCena;
    private Integer raspolozivost;
    private Integer novaRaspolozivost;

    public Pice() {

    }

    public Pice(String ime, Integer cena,Integer raspolozivost) {
        this.ime = ime;
        this.cena = cena;
        this.raspolozivost=raspolozivost;
    }
    
    public Pice(Integer id, String ime, Integer cena, Integer raspolozivost){
        this.id = id;
        this.ime = ime;
        this.cena = cena;
        this.raspolozivost = raspolozivost;
    }
    
    public Pice(Integer id, String ime, Integer cena, Integer raspolozivost,String novoIme,Integer novaCena,Integer novaRaspolozivost) {
        this.id = id;
        this.ime = ime;
        this.cena = cena;
        this.raspolozivost = raspolozivost;
        this.novoIme=novoIme;
        this.novaCena=novaCena;
        this.novaRaspolozivost=novaRaspolozivost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Integer getRaspolozivost() {
        return raspolozivost;
    }

    public void setRaspolozivost(int raspolozivost) {
        this.raspolozivost = raspolozivost;
    }
    
    public String getNovoIme(){
        return this.novoIme;
    }
    
    public Integer getNovuCenu(){
        return this.novaCena;
    }
    
    public Integer getNovuRaspolozivost(){
        return this.novaRaspolozivost;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pice)) {
            return false;
        }
        Pice pica = (Pice) o;
        return id == pica.id;
    }

    @Override
    public String toString() {
        return "Pice{" + "id=" + id + ", ime=" + ime + ", cena="
                + cena + ", raspolozivost=" + raspolozivost + '}';
    }

    @Override
    public String getClassName() {
        return "Pica";
    }

    @Override
    public String getColumns() {
        return "id, ime, cena, raspolozivost";
    }

    @Override
    public String getValues() {
        return " (select coalesce(max(p.id),0)+1 from pica p)" + ", '" + ime + "', " + cena + ", " + raspolozivost;
    }

    @Override
    public String makeWhere() {
        String ret = "";
        if (id != null) // primary key
        {
            ret += " and id = " + id;
        } else {
            if (ime != null && !ime.isEmpty()) {
                ret += " and ime like '%" + ime + "%'";
            }
            if (cena != null) {
                ret += " and cena=" + cena;
            }
            if (raspolozivost != null) {
                ret += " and raspolozivost=" + raspolozivost;
            }
        }
        return ret;
    }

    @Override
    public String makeSet() {
        String ret = "";
        if (novoIme != null && !novoIme.isEmpty()) {
            if(novaCena!=null||novaRaspolozivost!=null)
                ret += " ime='" + novoIme + "' ,";
            else
                ret += " ime='" + novoIme + "'";
        }
        if (novaCena != null) {
            if(novaRaspolozivost!=null)
                ret += " cena=" + novaCena+", ";
            else
                ret += " cena=" + novaCena;
        }
        if (novaRaspolozivost != null) {
            ret += " raspolozivost=" + novaRaspolozivost;
        }
        return ret;
    }
}
