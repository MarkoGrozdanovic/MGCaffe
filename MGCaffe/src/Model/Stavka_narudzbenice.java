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
public class Stavka_narudzbenice extends GenericObject implements Serializable {

    private static final long SerialVersionUID = 123435640l;
    private Integer id;
    private Integer id_pica;
    private Timestamp vreme;
    private Integer kolicina;

    public Stavka_narudzbenice() {
    }

    public Stavka_narudzbenice(Integer id, Integer id_pica, Timestamp vreme, Integer kolicina) {
        this.id = id;
        this.id_pica = id_pica;
        this.vreme=vreme;
        this.kolicina = kolicina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pica() {
        return id_pica;
    }

    public void setId_pica(int id_pica) {
        this.id_pica = id_pica;
    }

    public Timestamp getVreme() {
        return vreme;
    }

    public void setVreme(Timestamp vreme) {
        this.vreme = vreme;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Stavka_narudzbenice)) {
            return false;
        }
        Stavka_narudzbenice stavka_narudzbenice = (Stavka_narudzbenice) o;
        return id == stavka_narudzbenice.id;
    }

    @Override
    public String toString() {
        return "Stavka narudzbenice{" + "id=" + id + ", id_pica=" + id_pica +",vreme="+vreme+ ", kolicina="
                + kolicina + '}';
    }

    @Override
    public String getClassName() {
        return "stavke_narudzbenice";
    }

    @Override
    public String getColumns() {
        return "id,id_pica,vreme,kolicina";
    }

    @Override
    public String getValues() {
        return id+", "+id_pica+", '"+vreme+"' ,"+kolicina;
    }

    @Override
    public String makeWhere() {
        String ret = " 1=1";
        if (id != null) // primary key
        {
            ret += " and id = " + id;
        } else {
            if (id_pica != null) {
                ret += " and id_pica=" + id_pica;
            }
            if (vreme != null ) {
                ret += " and vreme='" + vreme+"'";
            }
            if (kolicina != null) {
                ret += " and kolicina=" + kolicina;
            }
        }
        return ret;
    }

    @Override
    public String makeSet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
