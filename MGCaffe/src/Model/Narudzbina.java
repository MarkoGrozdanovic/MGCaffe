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
public class Narudzbina extends GenericObject implements Serializable{
    private static final long SerialVersionUID = 19676453320l;
    private Integer id;
    private Integer id_stavke_narudzbenice;
    private Integer id_zaposlenog;
    private Integer ukupnaCena;

    public int getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(int ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Narudzbina() {
    }

    public Narudzbina(Integer id_stavke_narudzbenice, Integer id_zaposlenog,Integer ukupnaCena) {
        this.id_stavke_narudzbenice = id_stavke_narudzbenice;
        this.id_zaposlenog = id_zaposlenog;
        this.ukupnaCena=ukupnaCena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_stavke_narudzbenice() {
        return id_stavke_narudzbenice;
    }

    public void setId_stavke_narudzbenice(int id_stavke_narudzbenice) {
        this.id_stavke_narudzbenice = id_stavke_narudzbenice;
    }

    public int getId_zaposlenog() {
        return id_zaposlenog;
    }

    public void setId_zaposlenog(int id_zaposlenog) {
        this.id_zaposlenog = id_zaposlenog;
    }
 
    
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Narudzbina)) {
            return false;
        }
        Narudzbina narudzbina = (Narudzbina) o;
        return id == narudzbina.id;
    }

    @Override
    public String toString() {
        return "Korisnici{" +"Id_stavke_narudzbenice=" + id_stavke_narudzbenice + ", id_zaposlenog="
                + id_zaposlenog +", Ukupna cena: "+ukupnaCena+'}';
    }

    @Override
    public String getClassName() {
        return " Narudzbine";
    }

    @Override
    public String getColumns() {
        return " id_stavke_narudzbenice,id_zaposlenog,ukupna_cena";
    }

    @Override
    public String getValues() {
        return id_stavke_narudzbenice+","+id_zaposlenog+","+ukupnaCena;
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
