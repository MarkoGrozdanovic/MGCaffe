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
public class ZaposleniJOIN implements Serializable {
    private static final long SerialVersionUID = 11928364550l;
    private int id;
    private String username;
    private String password;
    private String advanced_privilages;
    private String pincode;
    private String sektor;
    private String ime;
    private String prezime;
    private String jmbg;
    private String telefon;

    public ZaposleniJOIN(){
        
    }
    
    public ZaposleniJOIN(int id, String username, String password, String advanced_privilages, String pincode, String sektor, String ime, String prezime, String jmbg, String telefon) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.advanced_privilages = advanced_privilages;
        this.pincode = pincode;
        this.sektor = sektor;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdvanced_privilages() {
        return advanced_privilages;
    }

    public void setAdvanced_privilages(String advanced_privilages) {
        this.advanced_privilages = advanced_privilages;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getSektor() {
        return sektor;
    }

    public void setSektor(String sektor) {
        this.sektor = sektor;
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
    public String toString() {
        return "ZaposleniJOIN{" + "id=" + id + ", username=" + username + ", password=" + password + ", advanced_privilages=" + advanced_privilages + ", pincode=" + pincode + ", sektor=" + sektor + ", ime=" + ime + ", prezime=" + prezime + ", jmbg=" + jmbg + ", telefon=" + telefon + '}';
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ZaposleniJOIN)) {
            return false;
        }
        ZaposleniJOIN zaposleni= (ZaposleniJOIN) o;
        return id == zaposleni.id;
    }
    
    

    
    
    
    
}
