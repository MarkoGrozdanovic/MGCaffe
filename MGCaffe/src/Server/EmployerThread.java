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
import static Server.Server.jTextArea1;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class EmployerThread extends Thread {
    
    Socket socket;
    Timestamp vreme;
    ServerSocket serverSocket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    
    public EmployerThread(Socket socket) {
        this.socket = socket;
        try {
            //Startujemo server inicijalizacijom serverSocketa
            serverSocket = new ServerSocket(4789);
            vreme = new Timestamp(System.currentTimeMillis());
            jTextArea1.append(vreme + " ------ " + "Server startovan!!!\n");
        } catch (IOException ex) {
            //Ukoliko je server već pokrenut,izbacujemo grešku
            jTextArea1.append(vreme + " ------ " + "Ne mozete pokrenuti SERVER jer je vec startovan!!!\n"+ex.getMessage());
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                //inicijalizujemo socket i prihvatamo serverSocket,takodje kreiramo ObjectInputStreamw i ObjectOutputStream
                socket = serverSocket.accept();
                ois = new ObjectInputStream(this.socket.getInputStream());
                oos = new ObjectOutputStream(this.socket.getOutputStream());
                //čitamo input pomoću kojeg raspoznajemo koju metodu u klasi program treba da izvrši
                String input = ois.readUTF();
                
                switch (input) {
                    case "jkgoq489jsdfns38jmsd":
                        log_in();
                        break;
                    case "asdwagtk345ksdaso":
                        izbrisiPice();
                        break;
                    case "sappdm234asdams2343mds":
                        delete_employer();
                        break;
                    case "udawj332asdu4a2sda":
                        dodajPice();
                        break;
                    case "fjeiomweiwe9089fsnjw":
                        insert_menu_availability();
                        break;
                    case "ndalwij213nasldaw634":
                        change_sektor();
                        break;
                    case "akswsm76asdwiekawsd94":
                        change_privilages();
                        break;
                    case "hjgt368ksdf90feedhsa2":
                        change_pincode();
                        break;
                    case "lfepds3498fasd876sdf":
                        add_new_employer();
                        break;
                    case "jngglr327hf3432n234jfd":
                        set_pincode();
                        break;
                    case "onr2fgh39mfseesa934":
                        pay_table();
                        break;
                    case "dnas87vsddsa0sadmalms":
                        insert_new_stavka();
                        break;
                    case "dmwapsdomqwp028asm56m":
                        update_drink();
                        break;
                    case "fkpoewmfks4mpr05sd1g":
                        prijavi_dolazak();
                        break;
                    case "pdsaw32fe90vsmawjiddh":
                        odjavi_se();
                        break;
                    case "mfksldfsdkmfs3349sdmfsd":
                        check_sektor_with_username();
                        break;
                    case "uinjwiembomazam342sjd":
                        check_id_narudzbenice();
                        break;
                    case "fewjkn233kjfs022ewfpo42":
                        check_max_id_employee();
                        break;
                    case "qniew0983dartmhkgdduyxvb":
                        insert_menu_drinks();
                        break;
                    case "oiqmfoewasdno49jdsnj87s":
                        check_availability();
                        break;
                    case "nweiphe903lep76kemqz":
                        check_id_pica();
                        break;
                    case "fnisuawldjasdawi2892dmss":
                        check_id_employer();
                        break;
                    case "wfn2876rejrdls7ejreksnd":
                        check_jmbg();
                        break;
                    case "opiwonewwnei478jnfwenoe":
                        update_availability();
                        break;
                    case "ijefkl9824nwfeauomle8ryt":
                        update_availability_to_one_coffe();
                        break;
                    case "pnhf64hidpzamwqrfuer3nfs":
                        update_availability_to_all();
                        break;
                    case "nifsdfnewiup924odasidwa":
                        insert_employee();
                        break;
                    case "nsfdianwdimasa923jjasnda":
                        insert_menu_records();
                        break;
                    case "oijwji4u3nytreenrigew3b4":
                        insert_menu_records_day();
                        break;
                    case "ioniu23984rnjwckmlgerop":
                        check_ID_Bill();
                        break;
                    case "finsid324op98sdjfsnacms":
                        proveri_pazar();
                        break;
                    case "gdoimrd9845grsmgreljde6g":
                        login_check();
                        break;
                    default:
                        jTextArea1.append("\n[" + vreme + "] ------ " + "Zaposleni " + input.toUpperCase());
                }
            } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                Logger.getLogger(EmployerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void writeUTF(String s) throws IOException {
        oos.writeUTF(s);
        oos.flush();
    }

    public void writeObject(Object o) throws IOException {
        oos.writeObject(o);
        oos.flush();
    }
    
    public void izbrisiPice() throws IOException {
        String zaposleni = ois.readUTF();
        String ime = ois.readUTF();
        if (Controller.getInstance().check_id_pica(ime) != -1) {
            writeUTF("Postoji");
            Controller.getInstance().delete_coffe(ime);
            jTextArea1.append("\n[" + vreme + "] ------ [" + zaposleni + "] Uspešno ste izbrisali piće: " + ime.toUpperCase());
        } else {
            writeUTF("Ne postoji");
        }
    }
    
    public void delete_employer() throws IOException {
        String zaposleni = ois.readUTF();
        int id = Integer.parseInt(ois.readUTF());
        Controller.getInstance().delete_employer(id);
        jTextArea1.append("\n[" + vreme + "] ------- [" + zaposleni + "] Uspešno ste izbrisali zaposlenog sa rednim brojem: " + id + ".!");
    }
    
    public void dodajPice() throws IOException, ClassNotFoundException {
        String zaposleni = ois.readUTF();
        Pice p = (Pice) ois.readObject();
        Controller.getInstance().insert_new_coffee(p);
        jTextArea1.append("\n[" + vreme + "] ------ [" + zaposleni + "] Uspešno ste dodali novo piće: " + p.getIme() + " ;Cena: " + p.getCena() + " ;Raspoloživost: " + p.getRaspolozivost());
    }
    
    public void change_sektor() throws IOException, ClassNotFoundException {
        String zaposleni = ois.readUTF();
        Korisnik u = (Korisnik) ois.readObject();
        Controller.getInstance().change_Sektor(u);
        jTextArea1.append("\n[" + vreme + "] ------ [" + zaposleni + "] Uspešno ste promenili sektor!!!");
    }
    
    public void check_sektor_with_username() throws IOException, ClassNotFoundException {
        Korisnik k = (Korisnik) ois.readObject();
        String usr = Controller.getInstance().check_sektor_with_username(k);
        if (usr != null) {
            writeUTF(usr);
        }
    }
    
    public void check_id_narudzbenice() throws IOException {
        writeUTF(Controller.getInstance().check_id_narudzbenice() + "");
    }
    
    public void change_privilages() throws IOException, ClassNotFoundException {
        String zaposleni = ois.readUTF();
        Korisnik u = (Korisnik) ois.readObject();
        Controller.getInstance().change_Privilages(u);
        jTextArea1.append("\n[" + vreme + "] ------ [" + zaposleni + "] Uspešno ste promenili privilegiju zaposlenog!!!");
    }
    
    public void change_pincode() throws IOException, ClassNotFoundException {
        String zaposleni = ois.readUTF();
        Korisnik u = (Korisnik) ois.readObject();
        Controller.getInstance().change_Pincode(u);
        jTextArea1.append("\n[" + vreme + "] ------ [" + zaposleni + "] Uspešno ste promenili pinkod zaposlenog!!!");
    }
    
    public void set_pincode() throws IOException, ClassNotFoundException {
        Korisnik u = (Korisnik) ois.readObject();
        Controller.getInstance().change_Pincode(u);
        jTextArea1.append("\n[" + vreme + "] ------ " + "Uspešno ste postavili pinkod zaposlenog!!!");
    }
    
    public void check_max_id_employee() throws IOException {
        writeUTF(String.valueOf(Controller.getInstance().check_max_id_employee()));
    }
    
    public void add_new_employer() throws IOException, ClassNotFoundException {
        String zaposleni = ois.readUTF();
        ZaposleniJOIN zj = (ZaposleniJOIN) ois.readObject();
        Controller.getInstance().insert_new_user(new Korisnik(zj.getId(), zj.getUsername(), zj.getPassword(), zj.getAdvanced_privilages(), zj.getPincode(), zj.getSektor()));
        Controller.getInstance().insert_new_employer(new Zaposleni(zj.getId(), zj.getIme(), zj.getPrezime(), zj.getJmbg(), zj.getTelefon()));
        jTextArea1.append("\n[" + vreme + "] ------ [" + zaposleni + "] Uspešno ste dodali novog zaposlenog!!!/n" + zj.toString());
    }
    
    public void log_in() throws IOException, ClassNotFoundException {
        Korisnik k = (Korisnik) ois.readObject();
        
        Korisnik u = Controller.getInstance().login_in(k);
        if (u != null) {
            writeUTF("log_in_accepted");
            if (Controller.getInstance().check_Privilages(k.getUsername(), k.getPassword())) {
                writeUTF("1");
                writeUTF(Controller.getInstance().check_Pincode(k));
            } else {
                writeUTF("0");
            }
            jTextArea1.append("\n[" + vreme + "] ------ " + "Korisnik pokušava da se uloguje!!!");
        } else {
            writeUTF("log_in_rejected");
        }
    }
    
    public void pay_table() throws IOException, ClassNotFoundException {
        String zaposleni = ois.readUTF();
        Narudzbina nar = (Narudzbina) ois.readObject();
        ois.close();
        Controller.getInstance().insert_new_racun(nar);
        jTextArea1.append("\n[" + vreme + "] ------ [" + zaposleni + "] " + "Uspešno ste naplatili uslugu!!!" + nar.toString());
    }
    
    public void insert_new_stavka() throws IOException, ClassNotFoundException, InterruptedException {
        String zaposleni = ois.readUTF();
        ArrayList<Stavka_narudzbenice> snList = (ArrayList<Stavka_narudzbenice>) ois.readObject();
        for (int i = 0; i < snList.size(); i++) {
            Controller.getInstance().insert_new_stavka_narudzbenice(snList.get(i));
            jTextArea1.append("\n[" + vreme + "] ------ [" + zaposleni + "] " + "Uspešno ste uneli novu stavku narudzbenice!!!" + snList.get(i).toString());
            Thread.sleep(1000);
        }
    }
    
    public void update_drink() throws IOException, ClassNotFoundException {
        String zaposleni = ois.readUTF();
        Pice p = (Pice) ois.readObject();
        
        if(p.getNovoIme()==null||p.getNovuCenu()==null||p.getNovuRaspolozivost()==null)
            return;
        
        Controller.getInstance().update_coffe(p);
        jTextArea1.append("\n[" + vreme + "] ------ [" + zaposleni + "] " + "Uspešno ste ažurirali piće!!! ");
        if (p.getNovoIme() != null) {
            jTextArea1.append(" Novo ime pica: " + p.getNovoIme());
        } else if (p.getNovuCenu() != null) {
            jTextArea1.append(" Nova cena pica: " + p.getNovuCenu());
        } else if (p.getNovuRaspolozivost() != null) {
            jTextArea1.append(" Nova raspolozivost pica: " + p.getNovuRaspolozivost());
        }
    }
    
    public void prijavi_dolazak() throws IOException, ClassNotFoundException {
        String username = ois.readUTF();
        String jmbg = Controller.getInstance().check_jmbg(username);
        Evidencija_dolaska ed = (Evidencija_dolaska) ois.readObject();
        ed.setJmbg(jmbg);
        Controller.getInstance().insert_new_arrival_records(ed);
        jTextArea1.append("\n[" + vreme + "] ------ [" + username + "] Uspešno ste prijavili dolazak u " + ed.getVreme_dolaska());
    }
    
    public void odjavi_se() throws IOException, ClassNotFoundException {
        String username = ois.readUTF();
        String jmbg = Controller.getInstance().check_jmbg(username);
        Evidencija_odlaska eo = (Evidencija_odlaska) ois.readObject();
        eo.setJmbg(jmbg);
        Controller.getInstance().insert_new_departure_records(eo);
        jTextArea1.append("\n[" + vreme + "] ------ [" + username + "] Uspešno ste se odjavili u " + eo.getVreme_odlaska() + ", i Vaš pazar iznosi: " + eo.getPazar());
    }
    
    public void insert_menu_drinks() throws IOException {
        LinkedList<Pice> pica = Controller.getInstance().insert_menu();
        writeObject(pica);
    }
    
    public void insert_menu_records() throws IOException {
        String JMBG = ois.readUTF();
        LinkedList<EvidencijaJOIN> evidencija = Controller.getInstance().insert_menu_records(JMBG);
        writeObject(evidencija);
    }

    public void insert_menu_records_day() throws IOException {
        String dan = ois.readUTF();
        String mesec = ois.readUTF();
        String jmbg = ois.readUTF();
        LinkedList<EvidencijaJOIN> evidencija = Controller.getInstance().insert_menu_records_day(dan, mesec, jmbg);
        writeObject(evidencija);
    }
    
    public void insert_employee() throws IOException {
        LinkedList<ZaposleniJOIN> zaposleni = Controller.getInstance().insert_employee();
        writeObject(zaposleni);
    }
    
    public void check_availability() throws IOException {
        String ime = ois.readUTF();
        int raspolozivost = Controller.getInstance().check_availability(ime);
        writeUTF(raspolozivost + "");
    }

    public void check_id_pica() throws IOException {
        String ime = ois.readUTF();
        int id_pica = Controller.getInstance().check_id_pica(ime);
        writeUTF(id_pica + "");
    }
    
    public void check_ID_Bill() throws IOException {
        int id_racuna = Controller.getInstance().check_ID_Bill();
        writeUTF(id_racuna + "");
    }
    
    public void check_id_employer() throws IOException {
        String username = ois.readUTF();
        int id_zaposlenog = Controller.getInstance().check_id_employer(username);
        writeUTF(id_zaposlenog + "");
    }
    
    public void check_jmbg() throws IOException {
        String username = ois.readUTF();
        String jmbg = Controller.getInstance().check_jmbg(username);
        writeUTF(jmbg);
    }
    
    public void update_availability() throws IOException {
        int kolicina = Integer.parseInt(ois.readUTF());
        int id_pica = Integer.parseInt(ois.readUTF());
        Controller.getInstance().update_availability(kolicina, id_pica);
    }
    
    public void update_availability_to_one_coffe() throws IOException, ClassNotFoundException {
        String username = ois.readUTF();
        Pice p = (Pice) ois.readObject();
        Controller.getInstance().update_availability(p);
        jTextArea1.append("\n[" + vreme + "] ------ [" + username + "] Piću '" + p.getIme() + "' je upravo postavljena nova raspoloživost-" + p.getNovuRaspolozivost());
    }
    
    public void proveri_pazar() throws IOException {
        String JMBG = ois.readUTF();
        LinkedList<Evidencija_odlaska> list = Controller.getInstance().proveri_pazar(JMBG);
        writeObject(list);
    }
    
    public void login_check() throws IOException {
        String vreme = ois.readUTF();
        String jmbg = ois.readUTF();
        boolean provera = Controller.getInstance().login_check(vreme, jmbg);
        oos.writeBoolean(provera);
        oos.flush();
    }
    
    private void insert_menu_availability() throws IOException {
        LinkedList<Pice> pica = Controller.getInstance().insert_menu();
        writeObject(pica);
    }
    
    private void update_availability_to_all() throws IOException {
        String username = ois.readUTF();
        Integer raspolozivost = Integer.parseInt(ois.readUTF());
        Controller.getInstance().update_availability_to_all(raspolozivost);
        jTextArea1.append("\n[" + vreme + "] ------ [" + username + "] Svakom piću je postavljena nova raspoloživost! Raspoloživost-" + raspolozivost);
    }
    
}
