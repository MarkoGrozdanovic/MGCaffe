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
public class Korisnik extends GenericObject implements Serializable {
    private static final long SerialVersionUID = 12345623788l;  
    private Integer id;
    private String username;
    private String password;
    private String advanced_privilages;
    private String pincode;
    private String sektor;
    public String noviSektor;
    public String novaPrivilegija;
    public String noviPincode;

    public Korisnik() {

    }

    public Korisnik(Integer id, String username, String password, String advanced_privilages, String pincode, String sektor) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.advanced_privilages=advanced_privilages;
        this.pincode = pincode;
        this.sektor = sektor;
    }

    public Korisnik(Integer id, String username, String password, String advanced_privilages, String pincode, String sektor,String noviSektor,String novaPrivilegija,String noviPincode) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.advanced_privilages = advanced_privilages;
        this.pincode = pincode;
        this.sektor = sektor;
        this.noviSektor=noviSektor;
        this.novaPrivilegija=novaPrivilegija;
        this.noviPincode=noviPincode;
    }

    public Korisnik(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    
    public Korisnik(String username,String noviPincode){
        this.username=username;
        this.noviPincode=noviPincode;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

   

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Korisnik)) {
            return false;
        }
        Korisnik users = (Korisnik) o;
        return id == users.id;
    }

    @Override
    public String toString() {
        return "Korisnici{" + "id=" + id + ", username=" + username + ", password="
                + password + '}';
    }

    @Override
    public String getClassName() {
        return " Korisnici";
    }

    @Override
    public String getColumns() {
        return "id,username,password,advanced_privilages,pincode,sektor";
    }

    @Override
    public String getValues() {
        return id+", '"+username+"', '"+password+"', '"+advanced_privilages+"', '"+pincode+"', '"+sektor+"'";
    }

    @Override
    public String makeWhere() {
        String ret = "";
        if (id != null) // primary key
        {
            ret += " and id = " + id;
        } else {
            if (username != null && !username.isEmpty()) {
                ret += " and username like '%" + username + "%'";
            }
            if (password != null && !password.isEmpty()) {
                ret += " and password like '%" + password + "%'";
            }
            if (advanced_privilages != null && !advanced_privilages.isEmpty()) {
                ret += " and advanced_privilages like '%" + advanced_privilages + "%'";
            }
            if (pincode != null && !pincode.isEmpty()) {
                ret += " and pincode like '%" + pincode + "%'";
            }
            if (sektor != null && !sektor.isEmpty()) {
                ret += " and sektor like '%" + sektor + "%'";
            }
        }
        return ret;
    }

    @Override
    public String makeSet() {
        String ret = "";
        if (noviSektor != null && !noviSektor.isEmpty()) 
                ret += " sektor='" + noviSektor + "'";
        if(novaPrivilegija!=null && !novaPrivilegija.isEmpty())
                ret += "advanced_privilages='"+novaPrivilegija+"'";
        if(noviPincode!=null && !noviPincode.isEmpty())
                ret += "pincode='"+noviPincode+"'";
        return ret;
    }
}
