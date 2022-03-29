package Server;

import Model.EvidencijaJOIN;
import Model.Evidencija_dolaska;
import Model.Evidencija_odlaska;
import Model.Narudzbina;
import Model.Pice;
import Model.Stavka_narudzbenice;
import Model.Korisnik;
import Model.Zaposleni;
import Model.ZaposleniJOIN;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grozd
 */
public class Controller {

    private String url = "jdbc:mysql://localhost:3306/Kafic";
    private String user = "root";
    private String pass = "";
    private static Controller instance;

    private Controller() {

    }

    //Singleton pattern-definise klasu koja ima samo jednu instancu i koja joj obezbedjuje globalnu tačku pristupa
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
            return new Controller();
        }
        return instance;
    }

    public Korisnik login_in(Korisnik k) {
        Korisnik users = null;
        if (k.getUsername() == null || k.getUsername().isEmpty() || k.getPassword() == null || k.getPassword().isEmpty()) {
            return users;
        }
        //kreiranje upita pomoću generičke metode makeSelect() iz apstraktne klase GenericObject
        String query_select = k.makeSelect();
        
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {
            ResultSet rs;
            rs = ps.executeQuery();
            
            if (rs.next()) {
                users = new Korisnik(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }
   
    public boolean check_Privilages(String username, String password) {
        boolean provera = false;

        String query_select = "SELECT * FROM korisnici WHERE username=? and password=? and advanced_privilages='1'";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {

            ResultSet rs;
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                provera = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return provera;
    }
    
    public String check_Pincode(Korisnik k) {
        String pincode = "";

        String query_select =k.makeSelect();

        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {

            ResultSet rs;
            rs = ps.executeQuery();

            if (rs.next()) {
                //S'obzirom da imamo generički selekt,upit nam vraća tabelu sa svim kolonama,
                //mi uzimamo vrednost iz 5 kolone zato što se tu nalazi pinkod
                pincode = rs.getString(5);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pincode;
    }

    public String check_sektor_with_username(Korisnik k) {
        String sektor = "";
        String query_select = k.makeSelect();

        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {
            
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                sektor = rs.getString(6);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sektor;
    }

    public int check_id_narudzbenice() {
        //kreiranje upita koji nam vraća id sa kojim ćemo kreirati novu stavku narudžbenice
        String query_select = "select max(id)+1 from stavke_narudzbenice sn ";
        int rezultat = 1;
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {

            ResultSet rs;

            rs = ps.executeQuery();

            if (rs.next()) {
                rezultat = rs.getInt(1);
                return rezultat;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rezultat;

    }

    public int check_id_pica(String ime) {
        String query_select = "select id from pica where ime=? ";
        int rezultat = -1;
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {
            ps.setString(1, ime);
            ResultSet rs;

            rs = ps.executeQuery();

            if (rs.next()) {
                rezultat = rs.getInt(1);
                return rezultat;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rezultat;

    }

    public boolean check_drinks(String ime) {
        String query_select = "select id from pica where ime=? ";
        boolean rezultat = false;
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {
            ps.setString(1, ime);
            ResultSet rs;

            rs = ps.executeQuery();

            if (rs.next()) {
                rezultat = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rezultat;

    }

    
    public void insert_new_coffee(Pice p) {
        //pomoću generičke metode makeInsert() kreiramo novu naredbu za insert
        String query_insert = p.makeInsert();
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_insert);) {
            conn.setAutoCommit(false);
            ps.executeUpdate();
            conn.commit();
            conn.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //metoda koja nam vraća ID pica koji će nam trebati u metodi ispod
    public int uzmiIdPica(String ime){
        int id=-1;
        String query= "select id from pica where ime=?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query);) {
            conn.setAutoCommit(false);
            ps.setString(1, ime);
            ResultSet rs;
            rs=ps.executeQuery();
            
            if(rs.next()){
                id=rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public void delete_coffe(String ime) {
       
        try (Connection conn = DriverManager.getConnection(url, user, pass);) {
            conn.setAutoCommit(false);
            
            int id_pica=uzmiIdPica(ime);
            //Zbog problema sa stranim kljućevima,moraćemo prvo da udjemo u tabelu stavke_narudzbenice i izbrišemo
            //redove gde se nalazi piće koje brišemo
            PreparedStatement ps= conn.prepareStatement("delete from stavke_narudzbenice where id_pica=?");
            ps.setInt(1, id_pica);
            PreparedStatement ps1 = conn.prepareStatement("DELETE FROM PICA WHERE ime =?");
            ps1.setString(1, ime);
            ps.executeUpdate();
            ps1.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //metoda koja nam vraća JMBG zaposlenog koji će nam trebati u metodi ispod
    public String uzmiJMBG(int id){
        String JMBG="";
        String query= "select jmbg from zaposleni where id_korisnika =?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query);) {
            conn.setAutoCommit(false);
            ps.setInt(1, id);
            ResultSet rs;
            rs=ps.executeQuery();
            
            if(rs.next()){
                JMBG=rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return JMBG;
    }
    
    public void delete_employer(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, pass);) {
            conn.setAutoCommit(false);
            
            String jmbg=uzmiJMBG(id);
            //Zaposleni kojeg želimo da izbrižemo ima dosta stranih ključeva o kojim moramo voditi računa
            PreparedStatement ps = conn.prepareStatement("DELETE FROM evidencija_dolaska WHERE jmbg=?");
            ps.setString(1, jmbg);
            PreparedStatement ps1 = conn.prepareStatement("DELETE FROM evidencija_odlaska WHERE jmbg=?");
            ps1.setString(1, jmbg);
            PreparedStatement ps2 = conn.prepareStatement("DELETE FROM narudzbine WHERE id_zaposlenog =?");
            ps2.setInt(1, id);
            PreparedStatement ps3 = conn.prepareStatement("DELETE FROM ZAPOSLENI WHERE id_korisnika=?");
            ps3.setInt(1, id);
            PreparedStatement ps4 = conn.prepareStatement("DELETE FROM korisnici WHERE id=?");
            ps4.setInt(1, id);
            //Moramo voditi računa o redosledu izvršavanja
            ps.executeUpdate();
            ps1.executeUpdate();
            ps2.executeUpdate();
            ps3.executeUpdate();
            ps4.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert_new_stavka_narudzbenice(Stavka_narudzbenice sn) {
        //pomoću generičke metode makeInsert() kreiramo novu naredbu za insert
        String query_insert = sn.makeInsert();
        System.out.println(query_insert);
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_insert);) {
            conn.setAutoCommit(false);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void change_Sektor(Korisnik u) {
        //pomoću generičke metode makeUpdate() ažuriramo tabelu Korisnici
        String query_update = u.makeUpdate();
        System.out.println(query_update);
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_update);) {
            conn.setAutoCommit(false);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //metoda koju koristimo kako bih proverili da li se korisnik već prijavio u toku tog dana,ukoliko se 
    //korisnik vec prijavio tog dana,vraćamo boolean vrednost FALSE i onemogućujemo duplo prijavljivanje
    public boolean login_check(String vreme,String JMBG){
        String dan=vreme.substring(8,11);
        String mesec=vreme.substring(5,7);
        String godina=vreme.substring(0,4);
        boolean signal=true;
        String query_select ="select * " +
                             "from evidencija_dolaska ed " +
                             "where day(vreme_dolaska)=? and month(vreme_dolaska)=? and year(vreme_dolaska)=? and jmbg=?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {
            ps.setString(1,dan);
            ps.setString(2,mesec);
            ps.setString(3,godina);
            ps.setString(4,JMBG);
            ResultSet rs;
            rs = ps.executeQuery();
            
            if (rs.next()) {
                signal=false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return signal;
    }
    
    public void change_Privilages(Korisnik u) {
        String query_update = u.makeUpdate();
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_update);) {
            conn.setAutoCommit(false);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void change_Pincode(Korisnik u) {
        String query_update = u.makeUpdate();
        System.out.println(query_update);
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_update);) {
            conn.setAutoCommit(false);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update_coffe(Pice p) {
        String query_update = p.makeUpdate();
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_update);) {
            System.out.println(query_update);
            conn.setAutoCommit(false);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert_new_arrival_records(Evidencija_dolaska ed) {
        String query_insert = ed.makeInsert();
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_insert);) {
            conn.setAutoCommit(false);
            ps.executeUpdate();
            conn.commit();
            conn.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String check_jmbg(String username1) {
        String jmbg = "";
        //Proveravamo koji je JMBG zaposlenog ali pošto imamo samo username kao podatak,pravimo JOIN izmedju tabele
        //Zaposleni i Korisnici i vraćamo jmbg zaposlenog
        String query_select = "select z.jmbg from korisnici k join zaposleni z on k.id =z.id_korisnika where k.username=?;";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {

            ResultSet rs;
            ps.setString(1, username1);
            rs = ps.executeQuery();

            if (rs.next()) {
                jmbg = rs.getString(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jmbg;
    }

    public void insert_new_departure_records(Evidencija_odlaska eo) {
        String query_insert = eo.makeInsert();
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_insert);) {
            conn.setAutoCommit(false);
            ps.executeUpdate();
            conn.commit();
            conn.rollback();

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int check_ID_Bill() {
        int bill = 0;
        //Pravimo upit kako bi dobili id za kreiranej nove narudzbinu
        String query_select = "SELECT MAX(id)+1 from narudzbine";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                bill = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bill;
    }

    public int check_max_id_employee() {
        int id = 0;
        //pravimo upit kako bi dobili id za kreiranje novog zaposlenog
        String query_select = "SELECT MAX(id_korisnika)+1 from zaposleni";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {
            ResultSet rs;
            rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public void insert_new_user(Korisnik k) {
        String query_insert = k.makeInsert();
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_insert);) {
            conn.setAutoCommit(false);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert_new_employer(Zaposleni z) {

        String query_insert = z.makeInsert();
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_insert);) {
            conn.setAutoCommit(false);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update_availability(int kolicina, int id) {
        //pravimo kveri pomoću kojeg smanjujemo raspoloživost pića nakon prodaje 
        String query_update = "UPDATE pica SET raspolozivost=raspolozivost-? WHERE id=?;";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_update);) {

            conn.setAutoCommit(false);
            ps.setInt(1, kolicina);
            ps.setInt(2, id);
            ps.executeUpdate();

            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void update_availability(Pice p){
        String query_update=p.makeUpdate();
        try(Connection conn=DriverManager.getConnection(url,user,pass);
        PreparedStatement ps=conn.prepareStatement(query_update);){
            conn.setAutoCommit(false);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void update_availability_to_all(Integer raspolozivost){
        //postavljamo istu raspoloživost svakom piću
        String query_update="update pica " +
                            "set raspolozivost=? " +
                            "where id>-1;";
        try(Connection conn=DriverManager.getConnection(url,user,pass);
        PreparedStatement ps=conn.prepareStatement(query_update);){
            conn.setAutoCommit(false);
            ps.setInt(1, raspolozivost);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int check_availability(String ime) {
        String query_select = "select raspolozivost from pica where ime=?; ";
        int rezultat = 0;
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {
            ps.setString(1, ime);
            ResultSet rs;
            rs = ps.executeQuery();

            if (rs.next()) {
                rezultat = rs.getInt(1);
                return rezultat;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rezultat;
    }

    public void insert_new_racun(Narudzbina n) {
        String query_insert = n.makeInsert();
        System.out.println(query_insert);
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_insert);) {
            conn.setAutoCommit(false);
            ps.executeUpdate();
            conn.commit();
            conn.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int check_id_employer(String username) {
        int id = 0;

        String query_select = "SELECT id from korisnici where username=?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {
            ps.setString(1, username);
            ResultSet rs;
            rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }
    

    public LinkedList<Pice> insert_menu() {
        String query = "select * from pica";
        LinkedList<Pice> pica = new LinkedList<>();
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ResultSet rs;
            rs = ps.executeQuery();
            
            /*Pravimo objekte klase Pice i punimo listu 'pica' koju kasnije u klasi EmployerThread
              šaljemo dalje kako bi popunili tabelu*/
            while (rs.next()) {
                String ime = rs.getString("ime");
                Integer cena = rs.getInt("cena");
                Integer raspolozivost = rs.getInt("raspolozivost");
                
                Pice p = new Pice(ime, cena,raspolozivost);
                
                pica.add(p);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pica;
    }

    public LinkedList<ZaposleniJOIN> insert_employee() {
        LinkedList<ZaposleniJOIN> zaposleni = new LinkedList<>();
        //Pravimo upit sa JOIN-om pomoću koga uzimamo sve podatke iz tabele Korisnici i Zaposleni kako bi kasnije mogli da popunimo tabelu
        String query = "select id_korisnika ,ime,prezime,jmbg,telefon,username,password,sektor,advanced_privilages,pincode "
                + "from zaposleni z join korisnici k on z.id_korisnika=k.id ; ";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query);) {

            ResultSet rs;
            rs = ps.executeQuery();
            
            //Punimo listu zaposleni
            while (rs.next()) {
                Integer id_korisnika = rs.getInt("id_korisnika");
                String ime = rs.getString("ime");
                String prezime = rs.getString("prezime");
                String jmbg = rs.getString("jmbg");
                String telefon = rs.getString("telefon");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String sektor = rs.getString("sektor");
                String advanced_privilages = rs.getString("advanced_privilages");
                String pincode = rs.getString("pincode");
                
                ZaposleniJOIN radnik = new ZaposleniJOIN(id_korisnika, username, password, advanced_privilages, pincode, sektor, ime, prezime, jmbg, telefon);
                zaposleni.add(radnik);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zaposleni;
    }

    public LinkedList<EvidencijaJOIN> insert_menu_records(String jmbg) {
        LinkedList<EvidencijaJOIN> evidencija = new LinkedList<>();
                
        //Pravimo upit pomoću kojeg poredimo podatke iz dve tabele
        String query = "select ed.* , eo.vreme_odlaska "
                + "from evidencija_dolaska ed join evidencija_odlaska eo on ed.jmbg =eo.jmbg "
                + "where ( DAY(ed.vreme_dolaska)=day(eo.vreme_odlaska) ) and ed.jmbg=? ;";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, jmbg);
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String jmbg1 = rs.getString(2);
                Timestamp vreme_dolaska = rs.getTimestamp(3);
                Timestamp vreme_odlaska = rs.getTimestamp(4);

                EvidencijaJOIN evj = new EvidencijaJOIN(id, jmbg1, vreme_dolaska, vreme_odlaska);
                evidencija.add(evj);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return evidencija;
    }

    public LinkedList<EvidencijaJOIN> insert_menu_records_day(String dan, String mesec, String jmbg) {
        LinkedList<EvidencijaJOIN> evidencija = new LinkedList<>();

        //Pravimo upit pomoću kojeg poredimo podatke iz dve tabele za odredjeni datum
        String query = "select ed.* , eo.vreme_odlaska from evidencija_dolaska ed join evidencija_odlaska eo on ed.jmbg =eo.jmbg \n"
                + "                where (DAY(ed.vreme_dolaska)=? and DAY(eo.vreme_odlaska)=? and month(ed.vreme_dolaska)=? and month(eo.vreme_odlaska)=?) and ed.jmbg = ? ;";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, dan);
            ps.setString(2, dan);
            ps.setString(3, mesec);
            ps.setString(4, mesec);
            ps.setString(5, jmbg);
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String jmbg1 = String.valueOf(rs.getString(2));
                Timestamp vreme_dolaska = rs.getTimestamp(3);
                Timestamp vreme_odlaska = rs.getTimestamp(4);

                EvidencijaJOIN evj = new EvidencijaJOIN(id, jmbg1, vreme_dolaska, vreme_odlaska);
                evidencija.add(evj);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return evidencija;
    }
    
    public LinkedList<Evidencija_odlaska> proveri_pazar(String JMBG) {
        int pazar = 0;
        LinkedList<Evidencija_odlaska> ll=new LinkedList<>();
        
        String query_select = "select concat(DAY(vreme_odlaska),\".\",month(vreme_odlaska),'.',year(vreme_odlaska)) as DATUM,pazar "
                + "from evidencija_odlaska"
                + " where JMBG=?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement(query_select);) {
            ResultSet rs;
            ps.setString(1, JMBG);
            rs = ps.executeQuery();
            System.out.println(query_select);
            String datum;
            while(rs.next()) {
                datum=rs.getString(1);
                pazar = rs.getInt(2);
                
                Evidencija_odlaska eo=new Evidencija_odlaska(datum, pazar);
                ll.add(eo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ll;
    }
    
    
}
