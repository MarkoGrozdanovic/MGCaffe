/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;



/**
 *
 * @author grozd
 */
public class Zaposleni extends GenericObject{
    private int id;
    private String ime;
    private String prezime;
    private String jmbg;
    private String telefon;

    public Zaposleni() {
    }

    public Zaposleni(int id, String ime, String prezime, String jmbg, String telefon) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.telefon = telefon;
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

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Zaposleni)) {
            return false;
        }
        Zaposleni zaposleni = (Zaposleni) o;
        return id == zaposleni.id;
    }

    @Override
    public String toString() {
        return "Korisnici{" + "id=" + id + ", ime=" + ime + ", prezime="
                + prezime +", jmbg:"+jmbg+"telefom"+telefon+ '}';
    }

    @Override
    public String getClassName() {
        return " zaposleni";
    }

    @Override
    public String getColumns() {
        return "id_korisnika,ime,prezime,jmbg,telefon";
    }

    @Override
    public String getValues() {
        return id+",'"+ime+"','"+prezime+"','"+jmbg+"','"+telefon+"'";
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
