/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.EvidencijaJOIN;
import Model.Evidencija_dolaska;
import Model.Evidencija_odlaska;
import Model.Narudzbina;
import Model.Pice;
import Model.Stavka_narudzbenice;
import Model.Korisnik;
import Model.ZaposleniJOIN;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author grozd
 */
public class EmployeeForm extends javax.swing.JFrame implements Runnable {
    
    int hours = 0, minutes = 0, seconds = 0;
    String timeString = "";
    Thread t = new Thread(this);
    String tekst = "";
    int ukupnaCena = 0;
    int pazar = 0;
    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    private final static Logger logger = Logger.getLogger(EmployeeForm.class.getName());
    
    public EmployeeForm() {
        initComponents();
        hide_fields_drinks();
        Border watch = BorderFactory.createMatteBorder(3, 3, 3, 3, Color.red);
        jLabel_Time.setBorder(watch);
        jPanel_KafeMeni.setVisible(false);
        jPanel_Zaposleni.setVisible(false);
        this.setLocationRelativeTo(null);
        jLabel_Close.setForeground(Color.gray);
        t.start();
        
    }
    
    public void writeUTF(String s) throws IOException {
        oos.writeUTF(s);
        oos.flush();
    }
    
    public void writeObject(Object o) throws IOException {
        oos.writeObject(o);
        oos.flush();
    }
    
    public void hide_fields_drinks() {
        jTextField_Ime_Pica.setVisible(false);
        jTextField_Cena_Pica.setVisible(false);
        jTextField_Raspolozivost_Pica.setVisible(false);
        jButton_Dodaj_Pice.setVisible(false);
        jButton_Izbrisi_Pice.setVisible(false);
        jButton_Promeni_Pice.setVisible(false);
        jLabel_Ime_Pica.setVisible(false);
        jLabel_Cena_Pica.setVisible(false);
        jLabel_Raspolozivost_Pica.setVisible(false);
        jLabel_Check_Availability.setVisible(false);
    }
    
    public void show_fields_drinks() {
        jTextField_Ime_Pica.setVisible(true);
        jTextField_Cena_Pica.setVisible(true);
        jTextField_Raspolozivost_Pica.setVisible(true);
        jButton_Dodaj_Pice.setVisible(true);
        jButton_Izbrisi_Pice.setVisible(true);
        jButton_Promeni_Pice.setVisible(true);
        jLabel_Ime_Pica.setVisible(true);
        jLabel_Cena_Pica.setVisible(true);
        jLabel_Raspolozivost_Pica.setVisible(true);
        jLabel_Check_Availability.setVisible(true);
    }
    
    public void clear_table(JTable jta) {
        DefaultTableModel dm = (DefaultTableModel) jta.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
    }
    
    public int check_availability(String ime) {
        int raspolozivost = 0;
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("oiqmfoewasdno49jdsnj87s");
            writeUTF(ime);
            
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            raspolozivost = Integer.parseInt(ois.readUTF());
            System.out.println(raspolozivost);
            
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return raspolozivost;
    }
    
    public void izaberi_pice() {
        int kolona = 0;
        int red = jTable_MeniPica.getSelectedRow();
        
        if (red == -1 || jTextField_Kolicina_Pica.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Morate izabrati piće i uneti odredjenu kolicinu koju želite", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int cena = Integer.parseInt(jTable_MeniPica.getModel().getValueAt(red, 1).toString());
        int kolicina = Integer.parseInt(jTextField_Kolicina_Pica.getText());
        String ime = jTable_MeniPica.getModel().getValueAt(red, kolona).toString();
        
        if (check_availability(ime) - kolicina < 0) {
            JOptionPane.showMessageDialog(this, "Odabranog pica trenutno nema na stanju");
        } else {
            tekst += ime + " x " + jTextField_Kolicina_Pica.getText() + " , ";
            jTextArea_IzabranaPica.setText(tekst);
            ukupnaCena += cena * kolicina;
        }
    }
    
    public void insert_menu(JTable table) {
        LinkedList<Pice> pica = new LinkedList<>();
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("qniew0983dartmhkgdduyxvb");
            
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            pica = (LinkedList<Pice>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
        for (Pice p : pica) {
            String ime = p.getIme();
            String cena = String.valueOf(p.getCena());
            
            String tbData[] = {ime, cena};
            tblModel.addRow(tbData);
        }
    }
    
    public void insert_menu_availability(JTable table) {
        LinkedList<Pice> pica = new LinkedList<>();
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("fjeiomweiwe9089fsnjw");
            
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            pica = (LinkedList<Pice>) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
        for (Pice p : pica) {
            String ime = p.getIme();
            Integer raspolozivost = p.getRaspolozivost();
            
            String tbData[] = {ime, String.valueOf(raspolozivost)};
            tblModel.addRow(tbData);
        }
    }
    
    public void insert_employee() {
        LinkedList<ZaposleniJOIN> zaposleni = new LinkedList<>();
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("nifsdfnewiup924odasidwa");
            
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            zaposleni = (LinkedList<ZaposleniJOIN>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (ZaposleniJOIN z : zaposleni) {
            String id_korisnika = String.valueOf(z.getId());
            String tbData[] = {id_korisnika, z.getIme(), z.getPrezime(), z.getJmbg(), z.getTelefon(),
                z.getUsername(), z.getPassword(), z.getSektor(), z.getAdvanced_privilages(), z.getPincode()};
            
            DefaultTableModel tblModel = (DefaultTableModel) jTable_Zaposleni.getModel();
            tblModel.addRow(tbData);
        }
        
    }
    
    public void insert_menu_records(String jmbg) {
        LinkedList<EvidencijaJOIN> evidencija = new LinkedList<>();
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("nsfdianwdimasa923jjasnda");
            writeUTF(jmbg);
            
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            evidencija = (LinkedList<EvidencijaJOIN>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (EvidencijaJOIN evj : evidencija) {
            String tbData[] = {String.valueOf(evj.getId()), evj.getJMBG(), String.valueOf(evj.getVreme_dolaska()), String.valueOf(evj.getVreme_odlaska())};
            DefaultTableModel tblModel = (DefaultTableModel) jTable_Records.getModel();
            
            tblModel.addRow(tbData);
        }
        
    }
    
    public void insert_menu_records_day(String dan, String mesec, String jmbg) {
        LinkedList<EvidencijaJOIN> evidencija = new LinkedList<>();
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("oijwji4u3nytreenrigew3b4");
            writeUTF(dan);
            writeUTF(mesec);
            writeUTF(jmbg);
            
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            evidencija = (LinkedList<EvidencijaJOIN>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (EvidencijaJOIN evj : evidencija) {
            String tbData[] = {String.valueOf(evj.getId()), evj.getJMBG(), String.valueOf(evj.getVreme_dolaska()), String.valueOf(evj.getVreme_odlaska())};
            DefaultTableModel tblModel = (DefaultTableModel) jTable_Records.getModel();
            
            tblModel.addRow(tbData);
        }
    }
    
    public void ocisti_Stavke() {
        jTextField_UkupnaCena.setText("");
        jTextArea_Pica.setText("");
        jTextArea_RACUN.setText("");
    }
    
    public int get_id_stavke_narudzbenice(JTextArea jta) {
        char[] niz = jta.getText().toCharArray();
        String id = "";
        for (int i = 18; i < jta.getText().length(); i++) {
            if (niz[i] == '\n') {
                break;
            }
            id += niz[i];
        }
        int id_stavke_narudzbenice = Integer.parseInt(id);
        return id_stavke_narudzbenice;
    }
    
    public int check_id_employer(String username) {
        Integer id = -1;
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("fnisuawldjasdawi2892dmss");
            writeUTF(username);
            
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            id = Integer.parseInt(ois.readUTF());
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public String check_jmbg() {
        String jmbg = "";
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("wfn2876rejrdls7ejreksnd");
            writeUTF(jTextField_Username.getText());
            System.out.println("username poslat");
            
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            jmbg = ois.readUTF();
            System.out.println("jmbg primljen");
            oos.close();
            ois.close();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jmbg;
    }
    
    public void pay_table(JTextArea jta, JTextField jtf) {
        String[] arr = jta.getText().split(":", 2);
        int racun = Integer.parseInt(arr[1]);
        pazar += racun;
        jTextField_Pazar.setText(String.valueOf(pazar));
        int id_stavke_narudzbenice = get_id_stavke_narudzbenice(jta);
        
        Narudzbina nar = new Narudzbina(id_stavke_narudzbenice, check_id_employer(jTextField_Username.getText()), racun);
        
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("onr2fgh39mfseesa934");
            writeUTF(jTextField_Username.getText().toUpperCase());
            writeObject(nar);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jta.setText("");
        jtf.setBackground(Color.GREEN);
        ocisti_Stavke();
    }
    
    public void show_dialog_stavke() {
        ukupnaCena = 0;
        jDialog_Stavke.setVisible(true);
        ocisti_Stavke();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog_Pica = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_MeniPica = new javax.swing.JTable();
        jButton_Izaberi = new javax.swing.JButton();
        jButton_Dodaj = new javax.swing.JButton();
        jLabel_Kolicina = new javax.swing.JLabel();
        jTextField_Kolicina_Pica = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea_IzabranaPica = new javax.swing.JTextArea();
        jDialog_Stavke = new javax.swing.JDialog();
        jLabel_IzaberiPice = new javax.swing.JLabel();
        jLabel_UkupnaCena = new javax.swing.JLabel();
        jTextField_UkupnaCena = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea_RACUN = new javax.swing.JTextArea();
        jButtonIzaberi = new javax.swing.JButton();
        jButton_KreirajRacun = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea_Pica = new javax.swing.JTextArea();
        jButton_Potvrdi = new javax.swing.JButton();
        jLabel_Number_Of_Table = new javax.swing.JLabel();
        jButton_UnesiRacun = new javax.swing.JButton();
        jDialog_Prijava = new javax.swing.JDialog();
        jPanel_PrijaviDolazak = new javax.swing.JPanel();
        jButton_PrijaviDolazak = new javax.swing.JButton();
        jLabel_Datum_Vreme = new javax.swing.JLabel();
        jLabel_VremeDolaska = new javax.swing.JLabel();
        jLabel_CloseDialogLogIN = new javax.swing.JLabel();
        jDialog_Dodaj_Zaposlenog = new javax.swing.JDialog();
        jTextField_ID = new javax.swing.JTextField();
        jTextField_Ime = new javax.swing.JTextField();
        jTextField_Prezime = new javax.swing.JTextField();
        jTextField_JMBG = new javax.swing.JTextField();
        jTextField_Telefon = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField_addUsername = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextField_addPassword = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jComboBox_addSektor = new javax.swing.JComboBox<>();
        jButton_Add_new_Employer = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTextField_addPincode = new javax.swing.JTextField();
        jCheckBox_Privileges = new javax.swing.JCheckBox();
        jDialog_Odjava = new javax.swing.JDialog();
        jPanel_OdjaviSe = new javax.swing.JPanel();
        jButton_OdjaviSe = new javax.swing.JButton();
        jLabel_Vreme = new javax.swing.JLabel();
        jLabel_VremeDolaska1 = new javax.swing.JLabel();
        jLabel_CloseDialogLogOff = new javax.swing.JLabel();
        jLabel_OdjavaPAZAR = new javax.swing.JLabel();
        jDialog_Check_Availability = new javax.swing.JDialog();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable__Availability = new javax.swing.JTable();
        jButton_PostaviRaspolozivost = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField_PostaviRaspolozivost = new javax.swing.JTextField();
        jButton_PostaviRaspolozivostSvima = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField_PostaviRaspolozivostSvima = new javax.swing.JTextField();
        jPanel_Main = new javax.swing.JPanel();
        jLabel_logo = new javax.swing.JLabel();
        jPanel_Monitoring = new javax.swing.JPanel();
        jLabel_Table_1 = new javax.swing.JLabel();
        jLabel_SektorA1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel_Table_4 = new javax.swing.JLabel();
        jLabel_SektorB1 = new javax.swing.JLabel();
        jLabel_Table_2 = new javax.swing.JLabel();
        jLabel_Table_3 = new javax.swing.JLabel();
        jLabel_SektorA3 = new javax.swing.JLabel();
        jLabel_SektorB3 = new javax.swing.JLabel();
        jLabel_Table_6 = new javax.swing.JLabel();
        jLabel_Table_5 = new javax.swing.JLabel();
        jLabel_SektorB2 = new javax.swing.JLabel();
        jLabel_SektorA2 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea_Table5 = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea_Table3 = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea_Table4 = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea_Table6 = new javax.swing.JTextArea();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea_Table1 = new javax.swing.JTextArea();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextArea_Table2 = new javax.swing.JTextArea();
        jButton_Naplati_Table1 = new javax.swing.JButton();
        jButton_Naplati_Table2 = new javax.swing.JButton();
        jButton_Naplati_Table3 = new javax.swing.JButton();
        jButton_Naplati_Table6 = new javax.swing.JButton();
        jButton_Naplati_Table5 = new javax.swing.JButton();
        jButton_Naplati_Table4 = new javax.swing.JButton();
        jTextField_Status_Table1 = new javax.swing.JTextField();
        jTextField_Status_Table2 = new javax.swing.JTextField();
        jTextField_Status_Table3 = new javax.swing.JTextField();
        jTextField_Status_Table6 = new javax.swing.JTextField();
        jTextField_Status_Table4 = new javax.swing.JTextField();
        jTextField_Status_Table5 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField_Pazar = new javax.swing.JTextField();
        jLabel_Time = new javax.swing.JLabel();
        jLabel_Close = new javax.swing.JLabel();
        jLabel_Monitoring = new javax.swing.JLabel();
        jLabel_Meni = new javax.swing.JLabel();
        jPanel_KafeMeni = new javax.swing.JPanel();
        jLabel_Slika = new javax.swing.JLabel();
        jPanel_Meni = new javax.swing.JPanel();
        jLabel_KafeMeni = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Drinks = new javax.swing.JTable();
        jButton_Dodaj_Pice = new javax.swing.JButton();
        jButton_Izbrisi_Pice = new javax.swing.JButton();
        jButton_Promeni_Pice = new javax.swing.JButton();
        jLabel_DodajIzmene = new javax.swing.JLabel();
        jCheckBox_AddChanges = new javax.swing.JCheckBox();
        jLabel_Ime_Pica = new javax.swing.JLabel();
        jTextField_Ime_Pica = new javax.swing.JTextField();
        jLabel_Cena_Pica = new javax.swing.JLabel();
        jTextField_Cena_Pica = new javax.swing.JTextField();
        jLabel_Raspolozivost_Pica = new javax.swing.JLabel();
        jTextField_Raspolozivost_Pica = new javax.swing.JTextField();
        jLabel_Check_Availability = new javax.swing.JLabel();
        jLabel_LogOFF = new javax.swing.JLabel();
        jLabel_Zaposleni = new javax.swing.JLabel();
        jLabel_Sektor = new javax.swing.JLabel();
        jTextField_Sektor = new javax.swing.JTextField();
        jPanel_Zaposleni = new javax.swing.JPanel();
        Tabela_Zaposleni = new javax.swing.JScrollPane();
        jTable_Zaposleni = new javax.swing.JTable();
        jButton_Promeni_Sektor = new javax.swing.JButton();
        jButton_ProveriEvidenciju = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTable_Records = new javax.swing.JTable();
        jButton_Promeni_Privilegiju = new javax.swing.JButton();
        jButton_Promeni_Pincode = new javax.swing.JButton();
        jButton_ProveriEvidenciju_DAN = new javax.swing.JButton();
        jTextField_Dan = new javax.swing.JTextField();
        jButton_addNewEmoloyee = new javax.swing.JButton();
        jButton_Drop_Emoloyer = new javax.swing.JButton();
        jLabel_ProveraPazara = new javax.swing.JLabel();
        jButton_ProveriPazar = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable_Pazar = new javax.swing.JTable();
        jTextField_Username = new javax.swing.JTextField();
        jLabel_PrijaviSe = new javax.swing.JLabel();
        jLabel_OdjaviSe = new javax.swing.JLabel();

        jDialog_Pica.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog_Pica.setModal(true);
        jDialog_Pica.setSize(new java.awt.Dimension(390, 700));
        jDialog_Pica.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                jDialog_PicaWindowActivated(evt);
            }
        });

        jTable_MeniPica.setFont(new java.awt.Font("Dubai", 1, 24)); // NOI18N
        jTable_MeniPica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ime", "Cena"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_MeniPica.setRowHeight(30);
        jTable_MeniPica.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable_MeniPicaFocusGained(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_MeniPica);

        jButton_Izaberi.setText("Izaberi");
        jButton_Izaberi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_IzaberiActionPerformed(evt);
            }
        });

        jButton_Dodaj.setText("Dodaj");
        jButton_Dodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DodajActionPerformed(evt);
            }
        });

        jLabel_Kolicina.setText("Kolicina");

        jTextArea_IzabranaPica.setEditable(false);
        jTextArea_IzabranaPica.setColumns(20);
        jTextArea_IzabranaPica.setRows(5);
        jScrollPane4.setViewportView(jTextArea_IzabranaPica);

        javax.swing.GroupLayout jDialog_PicaLayout = new javax.swing.GroupLayout(jDialog_Pica.getContentPane());
        jDialog_Pica.getContentPane().setLayout(jDialog_PicaLayout);
        jDialog_PicaLayout.setHorizontalGroup(
            jDialog_PicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_PicaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog_PicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog_PicaLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jDialog_PicaLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel_Kolicina)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_Kolicina_Pica, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_Dodaj, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog_PicaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Izaberi, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jDialog_PicaLayout.setVerticalGroup(
            jDialog_PicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_PicaLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog_PicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Dodaj, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_Kolicina_Pica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Kolicina, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jDialog_PicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog_PicaLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jButton_Izaberi, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(86, Short.MAX_VALUE))
                    .addGroup(jDialog_PicaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4)
                        .addContainerGap())))
        );

        jDialog_Stavke.setModal(true);
        jDialog_Stavke.setSize(new java.awt.Dimension(640, 400));

        jLabel_IzaberiPice.setText("Izaberi pice:");

        jLabel_UkupnaCena.setText("Ukupna cena:");

        jTextField_UkupnaCena.setEditable(false);

        jTextArea_RACUN.setEditable(false);
        jTextArea_RACUN.setColumns(20);
        jTextArea_RACUN.setRows(5);
        jScrollPane3.setViewportView(jTextArea_RACUN);

        jButtonIzaberi.setText("Izaberi");
        jButtonIzaberi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonIzaberiMouseClicked(evt);
            }
        });

        jButton_KreirajRacun.setText("KREIRAJ RACUN");
        jButton_KreirajRacun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_KreirajRacunActionPerformed(evt);
            }
        });

        jTextArea_Pica.setEditable(false);
        jTextArea_Pica.setColumns(20);
        jTextArea_Pica.setRows(5);
        jScrollPane5.setViewportView(jTextArea_Pica);

        jButton_Potvrdi.setText("Potvrdi");
        jButton_Potvrdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PotvrdiActionPerformed(evt);
            }
        });

        jLabel_Number_Of_Table.setFont(new java.awt.Font("Arial Unicode MS", 1, 14)); // NOI18N
        jLabel_Number_Of_Table.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jButton_UnesiRacun.setText("Unesi racun u sistem");
        jButton_UnesiRacun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_UnesiRacunActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog_StavkeLayout = new javax.swing.GroupLayout(jDialog_Stavke.getContentPane());
        jDialog_Stavke.getContentPane().setLayout(jDialog_StavkeLayout);
        jDialog_StavkeLayout.setHorizontalGroup(
            jDialog_StavkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_StavkeLayout.createSequentialGroup()
                .addGroup(jDialog_StavkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog_StavkeLayout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(jButton_KreirajRacun))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog_StavkeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jDialog_StavkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jDialog_StavkeLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton_UnesiRacun, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jDialog_StavkeLayout.createSequentialGroup()
                                .addComponent(jLabel_IzaberiPice, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jDialog_StavkeLayout.createSequentialGroup()
                                .addGroup(jDialog_StavkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel_Number_Of_Table, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jDialog_StavkeLayout.createSequentialGroup()
                                        .addComponent(jLabel_UkupnaCena)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_UkupnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonIzaberi)
                        .addGap(6, 6, 6)))
                .addGroup(jDialog_StavkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog_StavkeLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog_StavkeLayout.createSequentialGroup()
                        .addComponent(jButton_Potvrdi, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85))))
        );
        jDialog_StavkeLayout.setVerticalGroup(
            jDialog_StavkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_StavkeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog_StavkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jDialog_StavkeLayout.createSequentialGroup()
                        .addComponent(jLabel_Number_Of_Table, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jDialog_StavkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDialog_StavkeLayout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jButtonIzaberi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog_StavkeLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jDialog_StavkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog_StavkeLayout.createSequentialGroup()
                                        .addComponent(jLabel_IzaberiPice, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(51, 51, 51))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog_StavkeLayout.createSequentialGroup()
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)))))
                        .addGroup(jDialog_StavkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_UkupnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_UkupnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_KreirajRacun)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_UnesiRacun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(34, 34, 34))
                    .addGroup(jDialog_StavkeLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_Potvrdi, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(50, Short.MAX_VALUE))))
        );

        jDialog_Prijava.setBackground(new java.awt.Color(0, 51, 204));
        jDialog_Prijava.setUndecorated(true);
        jDialog_Prijava.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                jDialog_PrijavaWindowLostFocus(evt);
            }
        });

        jPanel_PrijaviDolazak.setBackground(new java.awt.Color(0, 102, 0));

        jButton_PrijaviDolazak.setBackground(new java.awt.Color(0, 102, 0));
        jButton_PrijaviDolazak.setFont(new java.awt.Font("sansserif", 2, 14)); // NOI18N
        jButton_PrijaviDolazak.setText("PRIJAVI DOLAZAK");
        jButton_PrijaviDolazak.setBorder(new javax.swing.border.MatteBorder(null));
        jButton_PrijaviDolazak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PrijaviDolazakActionPerformed(evt);
            }
        });

        jLabel_Datum_Vreme.setFont(new java.awt.Font("Dosis ExtraBold", 1, 20)); // NOI18N
        jLabel_Datum_Vreme.setForeground(new java.awt.Color(0, 153, 102));
        jLabel_Datum_Vreme.setText("   Trenutni datum i vreme");

        jLabel_VremeDolaska.setFont(new java.awt.Font("Arial Unicode MS", 1, 18)); // NOI18N

        jLabel_CloseDialogLogIN.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel_CloseDialogLogIN.setText(" X");
        jLabel_CloseDialogLogIN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_CloseDialogLogINMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel_PrijaviDolazakLayout = new javax.swing.GroupLayout(jPanel_PrijaviDolazak);
        jPanel_PrijaviDolazak.setLayout(jPanel_PrijaviDolazakLayout);
        jPanel_PrijaviDolazakLayout.setHorizontalGroup(
            jPanel_PrijaviDolazakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_PrijaviDolazakLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel_Datum_Vreme, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel_CloseDialogLogIN)
                .addContainerGap())
            .addGroup(jPanel_PrijaviDolazakLayout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jButton_PrijaviDolazak, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_PrijaviDolazakLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_VremeDolaska, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105))
        );
        jPanel_PrijaviDolazakLayout.setVerticalGroup(
            jPanel_PrijaviDolazakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_PrijaviDolazakLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_PrijaviDolazakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Datum_Vreme, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addGroup(jPanel_PrijaviDolazakLayout.createSequentialGroup()
                        .addComponent(jLabel_CloseDialogLogIN)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_VremeDolaska, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jButton_PrijaviDolazak, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jDialog_PrijavaLayout = new javax.swing.GroupLayout(jDialog_Prijava.getContentPane());
        jDialog_Prijava.getContentPane().setLayout(jDialog_PrijavaLayout);
        jDialog_PrijavaLayout.setHorizontalGroup(
            jDialog_PrijavaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_PrijavaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_PrijaviDolazak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDialog_PrijavaLayout.setVerticalGroup(
            jDialog_PrijavaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_PrijaviDolazak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTextField_ID.setEditable(false);

        jTextField_Ime.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_ImeFocusLost(evt);
            }
        });

        jTextField_Prezime.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_PrezimeFocusLost(evt);
            }
        });

        jTextField_JMBG.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_JMBGFocusLost(evt);
            }
        });

        jTextField_Telefon.setForeground(new java.awt.Color(171, 171, 235));
        jTextField_Telefon.setText("000/000-0000");
        jTextField_Telefon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_TelefonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_TelefonFocusLost(evt);
            }
        });

        jLabel13.setText("ID");

        jLabel14.setText("Ime: ");

        jLabel16.setText("Prezime: ");

        jLabel18.setText("Telefon: ");

        jLabel21.setText("JMBG: ");

        jLabel22.setText("Username:");

        jLabel23.setText("Password:");

        jLabel24.setText("Sektor: ");

        jComboBox_addSektor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B" }));

        jButton_Add_new_Employer.setText("DODAJ NOVOG ZAPOSLENOG");
        jButton_Add_new_Employer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Add_new_EmployerActionPerformed(evt);
            }
        });

        jTextField_addPincode.setForeground(new java.awt.Color(204, 204, 255));

        jCheckBox_Privileges.setText("Privilegije");
        jCheckBox_Privileges.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox_PrivilegesStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox_Privileges)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField_addPincode, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_addPincode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox_Privileges, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jDialog_Dodaj_ZaposlenogLayout = new javax.swing.GroupLayout(jDialog_Dodaj_Zaposlenog.getContentPane());
        jDialog_Dodaj_Zaposlenog.getContentPane().setLayout(jDialog_Dodaj_ZaposlenogLayout);
        jDialog_Dodaj_ZaposlenogLayout.setHorizontalGroup(
            jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_Dodaj_ZaposlenogLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jDialog_Dodaj_ZaposlenogLayout.createSequentialGroup()
                        .addGroup(jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_Ime, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_Prezime, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_JMBG, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_Telefon, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jDialog_Dodaj_ZaposlenogLayout.createSequentialGroup()
                                .addComponent(jTextField_addUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addGroup(jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jDialog_Dodaj_ZaposlenogLayout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_addPassword))))))
                    .addGroup(jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jDialog_Dodaj_ZaposlenogLayout.createSequentialGroup()
                            .addComponent(jLabel24)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox_addSektor, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButton_Add_new_Employer, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jDialog_Dodaj_ZaposlenogLayout.setVerticalGroup(
            jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_Dodaj_ZaposlenogLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(24, 24, 24)
                .addGroup(jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Ime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(24, 24, 24)
                .addGroup(jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Prezime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(24, 24, 24)
                .addGroup(jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_JMBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(24, 24, 24)
                .addGroup(jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Telefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(24, 24, 24)
                .addGroup(jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jDialog_Dodaj_ZaposlenogLayout.createSequentialGroup()
                        .addGroup(jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_addUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(jTextField_addPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addGap(25, 25, 25)
                        .addGroup(jDialog_Dodaj_ZaposlenogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jComboBox_addSektor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Add_new_Employer)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jDialog_Odjava.setBackground(new java.awt.Color(0, 51, 204));
        jDialog_Odjava.setUndecorated(true);
        jDialog_Odjava.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                jDialog_OdjavaWindowLostFocus(evt);
            }
        });

        jPanel_OdjaviSe.setBackground(new java.awt.Color(204, 51, 0));

        jButton_OdjaviSe.setBackground(new java.awt.Color(204, 51, 0));
        jButton_OdjaviSe.setFont(new java.awt.Font("sansserif", 2, 14)); // NOI18N
        jButton_OdjaviSe.setText("ODJAVI SE");
        jButton_OdjaviSe.setBorder(new javax.swing.border.MatteBorder(null));
        jButton_OdjaviSe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_OdjaviSeActionPerformed(evt);
            }
        });

        jLabel_Vreme.setFont(new java.awt.Font("Dosis ExtraBold", 1, 20)); // NOI18N
        jLabel_Vreme.setForeground(new java.awt.Color(0, 153, 102));
        jLabel_Vreme.setText("   Trenutni datum i vreme");

        jLabel_VremeDolaska1.setFont(new java.awt.Font("Arial Unicode MS", 1, 18)); // NOI18N

        jLabel_CloseDialogLogOff.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel_CloseDialogLogOff.setText(" X");
        jLabel_CloseDialogLogOff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_CloseDialogLogOffMouseClicked(evt);
            }
        });

        jLabel_OdjavaPAZAR.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel_OdjaviSeLayout = new javax.swing.GroupLayout(jPanel_OdjaviSe);
        jPanel_OdjaviSe.setLayout(jPanel_OdjaviSeLayout);
        jPanel_OdjaviSeLayout.setHorizontalGroup(
            jPanel_OdjaviSeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_OdjaviSeLayout.createSequentialGroup()
                .addGroup(jPanel_OdjaviSeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_OdjaviSeLayout.createSequentialGroup()
                        .addGroup(jPanel_OdjaviSeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_OdjaviSeLayout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(jButton_OdjaviSe, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel_OdjaviSeLayout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(jLabel_OdjavaPAZAR, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 93, Short.MAX_VALUE))
                    .addGroup(jPanel_OdjaviSeLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel_Vreme, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(jLabel_CloseDialogLogOff)))
                .addContainerGap())
            .addGroup(jPanel_OdjaviSeLayout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jLabel_VremeDolaska1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_OdjaviSeLayout.setVerticalGroup(
            jPanel_OdjaviSeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_OdjaviSeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_OdjaviSeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_CloseDialogLogOff)
                    .addComponent(jLabel_Vreme, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_VremeDolaska1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel_OdjavaPAZAR, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_OdjaviSe, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        javax.swing.GroupLayout jDialog_OdjavaLayout = new javax.swing.GroupLayout(jDialog_Odjava.getContentPane());
        jDialog_Odjava.getContentPane().setLayout(jDialog_OdjavaLayout);
        jDialog_OdjavaLayout.setHorizontalGroup(
            jDialog_OdjavaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_OdjavaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_OdjaviSe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDialog_OdjavaLayout.setVerticalGroup(
            jDialog_OdjavaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_OdjaviSe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialog_Check_Availability.setModal(true);
        jDialog_Check_Availability.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                jDialog_Check_AvailabilityWindowActivated(evt);
            }
        });

        jTable__Availability.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jTable__Availability.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pice", "Raspolozivost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable__Availability.setRowHeight(25);
        jScrollPane14.setViewportView(jTable__Availability);
        if (jTable__Availability.getColumnModel().getColumnCount() > 0) {
            jTable__Availability.getColumnModel().getColumn(0).setResizable(false);
            jTable__Availability.getColumnModel().getColumn(1).setResizable(false);
        }

        jButton_PostaviRaspolozivost.setText("POSTAVI");
        jButton_PostaviRaspolozivost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PostaviRaspolozivostActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial Unicode MS", 1, 13)); // NOI18N
        jLabel1.setText("Označi piće i postavi mu odredjenu raspoloživost");

        jButton_PostaviRaspolozivostSvima.setText("POSTAVI");
        jButton_PostaviRaspolozivostSvima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PostaviRaspolozivostSvimaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial Unicode MS", 1, 13)); // NOI18N
        jLabel2.setText("Postavi svakom piću raspoloživost na : ");

        javax.swing.GroupLayout jDialog_Check_AvailabilityLayout = new javax.swing.GroupLayout(jDialog_Check_Availability.getContentPane());
        jDialog_Check_Availability.getContentPane().setLayout(jDialog_Check_AvailabilityLayout);
        jDialog_Check_AvailabilityLayout.setHorizontalGroup(
            jDialog_Check_AvailabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_Check_AvailabilityLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog_Check_AvailabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDialog_Check_AvailabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jDialog_Check_AvailabilityLayout.createSequentialGroup()
                            .addComponent(jTextField_PostaviRaspolozivostSvima, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton_PostaviRaspolozivostSvima, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jDialog_Check_AvailabilityLayout.createSequentialGroup()
                            .addComponent(jTextField_PostaviRaspolozivost, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton_PostaviRaspolozivost, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDialog_Check_AvailabilityLayout.setVerticalGroup(
            jDialog_Check_AvailabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_Check_AvailabilityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog_Check_AvailabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_PostaviRaspolozivost, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_PostaviRaspolozivost, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog_Check_AvailabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_PostaviRaspolozivostSvima, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_PostaviRaspolozivostSvima, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel_Main.setBackground(new java.awt.Color(102, 51, 0));
        jPanel_Main.setMaximumSize(null);

        jLabel_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/coffee.jpg"))); // NOI18N

        jPanel_Monitoring.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Monitoring.setFont(new java.awt.Font("Segoe Print", 1, 32)); // NOI18N

        jLabel_Table_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table.jpg"))); // NOI18N
        jLabel_Table_1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_Table_1MouseClicked(evt);
            }
        });

        jLabel_SektorA1.setFont(new java.awt.Font("Segoe Print", 1, 32)); // NOI18N
        jLabel_SektorA1.setText("A1");

        jLabel_Table_4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table.jpg"))); // NOI18N
        jLabel_Table_4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_Table_4MouseClicked(evt);
            }
        });

        jLabel_SektorB1.setFont(new java.awt.Font("Segoe Print", 1, 32)); // NOI18N
        jLabel_SektorB1.setText("B1");

        jLabel_Table_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table.jpg"))); // NOI18N
        jLabel_Table_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_Table_2MouseClicked(evt);
            }
        });

        jLabel_Table_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table.jpg"))); // NOI18N
        jLabel_Table_3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_Table_3MouseClicked(evt);
            }
        });

        jLabel_SektorA3.setFont(new java.awt.Font("Segoe Print", 1, 32)); // NOI18N
        jLabel_SektorA3.setText("A3");

        jLabel_SektorB3.setFont(new java.awt.Font("Segoe Print", 1, 32)); // NOI18N
        jLabel_SektorB3.setText("B3");

        jLabel_Table_6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table.jpg"))); // NOI18N
        jLabel_Table_6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_Table_6MouseClicked(evt);
            }
        });

        jLabel_Table_5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table.jpg"))); // NOI18N
        jLabel_Table_5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_Table_5MouseClicked(evt);
            }
        });

        jLabel_SektorB2.setFont(new java.awt.Font("Segoe Print", 1, 32)); // NOI18N
        jLabel_SektorB2.setText("B2");

        jLabel_SektorA2.setFont(new java.awt.Font("Segoe Print", 1, 32)); // NOI18N
        jLabel_SektorA2.setText("A2");

        jTextArea_Table5.setColumns(20);
        jTextArea_Table5.setRows(5);
        jTextArea_Table5.setEnabled(false);
        jScrollPane6.setViewportView(jTextArea_Table5);

        jTextArea_Table3.setColumns(20);
        jTextArea_Table3.setRows(5);
        jTextArea_Table3.setEnabled(false);
        jScrollPane7.setViewportView(jTextArea_Table3);

        jTextArea_Table4.setColumns(20);
        jTextArea_Table4.setRows(5);
        jTextArea_Table4.setEnabled(false);
        jScrollPane8.setViewportView(jTextArea_Table4);

        jTextArea_Table6.setColumns(20);
        jTextArea_Table6.setRows(5);
        jTextArea_Table6.setEnabled(false);
        jScrollPane9.setViewportView(jTextArea_Table6);

        jTextArea_Table1.setEditable(false);
        jTextArea_Table1.setColumns(20);
        jTextArea_Table1.setRows(5);
        jTextArea_Table1.setEnabled(false);
        jScrollPane10.setViewportView(jTextArea_Table1);

        jTextArea_Table2.setColumns(20);
        jTextArea_Table2.setRows(5);
        jTextArea_Table2.setEnabled(false);
        jScrollPane11.setViewportView(jTextArea_Table2);

        jButton_Naplati_Table1.setText("NAPLATI");
        jButton_Naplati_Table1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Naplati_Table1ActionPerformed(evt);
            }
        });

        jButton_Naplati_Table2.setText("NAPLATI");
        jButton_Naplati_Table2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Naplati_Table2ActionPerformed(evt);
            }
        });

        jButton_Naplati_Table3.setText("NAPLATI");
        jButton_Naplati_Table3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Naplati_Table3ActionPerformed(evt);
            }
        });

        jButton_Naplati_Table6.setText("NAPLATI");
        jButton_Naplati_Table6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Naplati_Table6ActionPerformed(evt);
            }
        });

        jButton_Naplati_Table5.setText("NAPLATI");
        jButton_Naplati_Table5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Naplati_Table5ActionPerformed(evt);
            }
        });

        jButton_Naplati_Table4.setText("NAPLATI");
        jButton_Naplati_Table4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Naplati_Table4ActionPerformed(evt);
            }
        });

        jTextField_Status_Table1.setEditable(false);
        jTextField_Status_Table1.setBackground(new java.awt.Color(51, 255, 51));

        jTextField_Status_Table2.setEditable(false);
        jTextField_Status_Table2.setBackground(new java.awt.Color(51, 255, 51));

        jTextField_Status_Table3.setEditable(false);
        jTextField_Status_Table3.setBackground(new java.awt.Color(51, 255, 51));

        jTextField_Status_Table6.setEditable(false);
        jTextField_Status_Table6.setBackground(new java.awt.Color(51, 255, 51));

        jTextField_Status_Table4.setEditable(false);
        jTextField_Status_Table4.setBackground(new java.awt.Color(51, 255, 51));

        jTextField_Status_Table5.setEditable(false);
        jTextField_Status_Table5.setBackground(new java.awt.Color(51, 255, 51));

        jLabel12.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 0));
        jLabel12.setText("PAZAR");

        jTextField_Pazar.setEditable(false);

        javax.swing.GroupLayout jPanel_MonitoringLayout = new javax.swing.GroupLayout(jPanel_Monitoring);
        jPanel_Monitoring.setLayout(jPanel_MonitoringLayout);
        jPanel_MonitoringLayout.setHorizontalGroup(
            jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MonitoringLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jButton_Naplati_Table4)
                .addGap(284, 284, 284)
                .addComponent(jButton_Naplati_Table5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_Naplati_Table6)
                .addGap(224, 224, 224))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MonitoringLayout.createSequentialGroup()
                .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jButton_Naplati_Table1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(269, 269, 269)
                        .addComponent(jButton_Naplati_Table2))
                    .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel_SektorB1)
                        .addGap(309, 309, 309)
                        .addComponent(jLabel_SektorB2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton_Naplati_Table3)
                    .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                        .addComponent(jTextField_Status_Table6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_SektorB3)
                        .addGap(17, 17, 17)))
                .addGap(232, 232, 232))
            .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jTextField_Status_Table1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_SektorA1))
                    .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel_Table_1))
                    .addComponent(jLabel_Table_4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                        .addComponent(jLabel_Table_5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_Table_6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                        .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_Table_2)
                            .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jTextField_Status_Table2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_SektorA2)))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_Table_3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 32, Short.MAX_VALUE))
                            .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MonitoringLayout.createSequentialGroup()
                                .addComponent(jTextField_Status_Table3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel_SektorA3)
                                .addGap(244, 244, 244))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MonitoringLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Pazar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(663, 663, 663))
            .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(jTextField_Status_Table4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1016, Short.MAX_VALUE)))
            .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                    .addGap(380, 380, 380)
                    .addComponent(jTextField_Status_Table5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(661, Short.MAX_VALUE)))
        );
        jPanel_MonitoringLayout.setVerticalGroup(
            jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                        .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel11)
                                .addGap(33, 33, 33))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MonitoringLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_SektorA2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_Status_Table2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Table_2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                        .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel_SektorA3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_Status_Table3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MonitoringLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel_SektorA1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_Status_Table1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_Table_3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Table_1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                        .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_MonitoringLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jButton_Naplati_Table1)
                                .addGap(31, 31, 31)
                                .addComponent(jLabel_SektorB1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_MonitoringLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                                        .addComponent(jButton_Naplati_Table3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_SektorB3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField_Status_Table6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                                        .addComponent(jButton_Naplati_Table2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel_SektorB2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel_Table_4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel_Table_5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel_Table_6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_MonitoringLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_Naplati_Table6)
                    .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton_Naplati_Table4)
                        .addComponent(jButton_Naplati_Table5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_Pazar, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MonitoringLayout.createSequentialGroup()
                    .addContainerGap(317, Short.MAX_VALUE)
                    .addComponent(jTextField_Status_Table4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(277, 277, 277)))
            .addGroup(jPanel_MonitoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MonitoringLayout.createSequentialGroup()
                    .addContainerGap(323, Short.MAX_VALUE)
                    .addComponent(jTextField_Status_Table5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(271, 271, 271)))
        );

        jLabel_Time.setBackground(new java.awt.Color(0, 0, 0));
        jLabel_Time.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel_Time.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Time.setBorder(new javax.swing.border.MatteBorder(null));

        jLabel_Close.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_Close.setFont(new java.awt.Font("sansserif", 1, 30)); // NOI18N
        jLabel_Close.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Close.setText("  X");
        jLabel_Close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_CloseMouseClicked(evt);
            }
        });

        jLabel_Monitoring.setBackground(new java.awt.Color(102, 102, 0));
        jLabel_Monitoring.setFont(new java.awt.Font("sansserif", 1, 16)); // NOI18N
        jLabel_Monitoring.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Monitoring.setText("   MONITORING");
        jLabel_Monitoring.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        jLabel_Monitoring.setOpaque(true);
        jLabel_Monitoring.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_MonitoringMouseClicked(evt);
            }
        });

        jLabel_Meni.setBackground(new java.awt.Color(102, 102, 0));
        jLabel_Meni.setFont(new java.awt.Font("sansserif", 1, 16)); // NOI18N
        jLabel_Meni.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Meni.setText("          MENI");
        jLabel_Meni.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        jLabel_Meni.setOpaque(true);
        jLabel_Meni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_MeniMouseClicked(evt);
            }
        });

        jPanel_KafeMeni.setBackground(new java.awt.Color(0, 0, 0));

        jLabel_Slika.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/coffemenuRESIZE2.jpg"))); // NOI18N
        jLabel_Slika.setText("jLabel5");

        jPanel_Meni.setBackground(new java.awt.Color(0, 0, 0));

        jLabel_KafeMeni.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel_KafeMeni.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_KafeMeni.setText("KAFE MENI");
        jLabel_KafeMeni.setToolTipText("");

        jTable_Drinks.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jTable_Drinks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IME", "CENA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_Drinks.setFillsViewportHeight(true);
        jTable_Drinks.setPreferredSize(new java.awt.Dimension(200, 0));
        jTable_Drinks.setRowHeight(30);
        jScrollPane1.setViewportView(jTable_Drinks);

        jButton_Dodaj_Pice.setText("DODAJ");
        jButton_Dodaj_Pice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Dodaj_PiceActionPerformed(evt);
            }
        });

        jButton_Izbrisi_Pice.setText("IZBRISI");
        jButton_Izbrisi_Pice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Izbrisi_PiceActionPerformed(evt);
            }
        });

        jButton_Promeni_Pice.setText("PROMENI");
        jButton_Promeni_Pice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Promeni_PiceActionPerformed(evt);
            }
        });

        jLabel_DodajIzmene.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel_DodajIzmene.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_DodajIzmene.setText("Dodaj izmene");

        jCheckBox_AddChanges.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBox_AddChangesMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel_MeniLayout = new javax.swing.GroupLayout(jPanel_Meni);
        jPanel_Meni.setLayout(jPanel_MeniLayout);
        jPanel_MeniLayout.setHorizontalGroup(
            jPanel_MeniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MeniLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_KafeMeni)
                .addGap(56, 56, 56))
            .addGroup(jPanel_MeniLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_MeniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBox_AddChanges, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_DodajIzmene, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_Dodaj_Pice, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton_Izbrisi_Pice, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Promeni_Pice, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(jPanel_MeniLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel_MeniLayout.setVerticalGroup(
            jPanel_MeniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_MeniLayout.createSequentialGroup()
                .addComponent(jLabel_KafeMeni)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_MeniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_Izbrisi_Pice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Promeni_Pice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Dodaj_Pice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel_MeniLayout.createSequentialGroup()
                        .addComponent(jCheckBox_AddChanges)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_DodajIzmene, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jLabel_Ime_Pica.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel_Ime_Pica.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Ime_Pica.setText("IME");

        jLabel_Cena_Pica.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel_Cena_Pica.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Cena_Pica.setText("CENA");

        jLabel_Raspolozivost_Pica.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel_Raspolozivost_Pica.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Raspolozivost_Pica.setText("RASPOLOŽIVOST");

        jLabel_Check_Availability.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel_Check_Availability.setForeground(new java.awt.Color(204, 153, 255));
        jLabel_Check_Availability.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/CheckAvailability.jpg"))); // NOI18N
        jLabel_Check_Availability.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_Check_AvailabilityMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel_KafeMeniLayout = new javax.swing.GroupLayout(jPanel_KafeMeni);
        jPanel_KafeMeni.setLayout(jPanel_KafeMeniLayout);
        jPanel_KafeMeniLayout.setHorizontalGroup(
            jPanel_KafeMeniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_KafeMeniLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel_Ime_Pica)
                .addGap(18, 18, 18)
                .addComponent(jTextField_Ime_Pica, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Cena_Pica)
                .addGap(18, 18, 18)
                .addComponent(jTextField_Cena_Pica, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Raspolozivost_Pica, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Raspolozivost_Pica, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Check_Availability, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jPanel_Meni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel_KafeMeniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_KafeMeniLayout.createSequentialGroup()
                    .addComponent(jLabel_Slika, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 416, Short.MAX_VALUE)))
        );
        jPanel_KafeMeniLayout.setVerticalGroup(
            jPanel_KafeMeniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Meni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_KafeMeniLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_KafeMeniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_KafeMeniLayout.createSequentialGroup()
                        .addGroup(jPanel_KafeMeniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_Ime_Pica, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Cena_Pica, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Ime_Pica)
                            .addComponent(jTextField_Cena_Pica, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Raspolozivost_Pica))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_KafeMeniLayout.createSequentialGroup()
                        .addGroup(jPanel_KafeMeniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField_Raspolozivost_Pica, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Check_Availability))
                        .addGap(16, 16, 16))))
            .addGroup(jPanel_KafeMeniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_KafeMeniLayout.createSequentialGroup()
                    .addComponent(jLabel_Slika, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 72, Short.MAX_VALUE)))
        );

        jLabel_LogOFF.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel_LogOFF.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_LogOFF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logoff.jpg"))); // NOI18N
        jLabel_LogOFF.setText("IZLOGUJ SE");
        jLabel_LogOFF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_LogOFFMouseClicked(evt);
            }
        });

        jLabel_Zaposleni.setBackground(new java.awt.Color(102, 102, 0));
        jLabel_Zaposleni.setFont(new java.awt.Font("sansserif", 1, 16)); // NOI18N
        jLabel_Zaposleni.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Zaposleni.setText("   ZAPOSLENI");
        jLabel_Zaposleni.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        jLabel_Zaposleni.setOpaque(true);
        jLabel_Zaposleni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_ZaposleniMouseClicked(evt);
            }
        });

        jLabel_Sektor.setFont(new java.awt.Font("sansserif", 2, 18)); // NOI18N
        jLabel_Sektor.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Sektor.setText("Sektor");

        jTextField_Sektor.setFont(new java.awt.Font("Arial Unicode MS", 1, 18)); // NOI18N
        jTextField_Sektor.setEnabled(false);

        jTable_Zaposleni.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jTable_Zaposleni.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ime", "Prezime", "JMBG", "telefon", "username", "password", "sektor", "advanced_privilages", "pincode"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_Zaposleni.setRowHeight(30);
        Tabela_Zaposleni.setViewportView(jTable_Zaposleni);

        jButton_Promeni_Sektor.setText("Promeni sektor");
        jButton_Promeni_Sektor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Promeni_SektorActionPerformed(evt);
            }
        });

        jButton_ProveriEvidenciju.setText("Proveri evidenciju zaposlenog");
        jButton_ProveriEvidenciju.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ProveriEvidencijuActionPerformed(evt);
            }
        });

        jTable_Records.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "JMBG", "vreme_dolaska", "vreme_odlaska"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_Records.setRowHeight(24);
        jScrollPane13.setViewportView(jTable_Records);

        jButton_Promeni_Privilegiju.setText("Promeni privilegiju");
        jButton_Promeni_Privilegiju.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_Promeni_PrivilegijuMouseClicked(evt);
            }
        });

        jButton_Promeni_Pincode.setText("Promeni pincode");
        jButton_Promeni_Pincode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_Promeni_PincodeMouseClicked(evt);
            }
        });

        jButton_ProveriEvidenciju_DAN.setText("Proveri evidenciju zaposlenog za dan:");
        jButton_ProveriEvidenciju_DAN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_ProveriEvidenciju_DANMouseClicked(evt);
            }
        });

        jTextField_Dan.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jTextField_Dan.setForeground(new java.awt.Color(204, 204, 255));
        jTextField_Dan.setText("01-01");
        jTextField_Dan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_DanFocusGained(evt);
            }
        });

        jButton_addNewEmoloyee.setText("Dodaj novog zaposlenog");
        jButton_addNewEmoloyee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addNewEmoloyeeActionPerformed(evt);
            }
        });

        jButton_Drop_Emoloyer.setText("Izbriši zaposlenog iz sistema");
        jButton_Drop_Emoloyer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Drop_EmoloyerActionPerformed(evt);
            }
        });

        jLabel_ProveraPazara.setFont(new java.awt.Font("Arial Unicode MS", 2, 14)); // NOI18N
        jLabel_ProveraPazara.setText("Označite zaposlenog i proverite njegov pazar");

        jButton_ProveriPazar.setText("PROVERI PAZAR");
        jButton_ProveriPazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ProveriPazarActionPerformed(evt);
            }
        });

        jTable_Pazar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DATUM", "PAZAR"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane12.setViewportView(jTable_Pazar);

        javax.swing.GroupLayout jPanel_ZaposleniLayout = new javax.swing.GroupLayout(jPanel_Zaposleni);
        jPanel_Zaposleni.setLayout(jPanel_ZaposleniLayout);
        jPanel_ZaposleniLayout.setHorizontalGroup(
            jPanel_ZaposleniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ZaposleniLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_ZaposleniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Tabela_Zaposleni)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ZaposleniLayout.createSequentialGroup()
                        .addGroup(jPanel_ZaposleniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_ZaposleniLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel_ZaposleniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton_Drop_Emoloyer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton_addNewEmoloyee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel_ZaposleniLayout.createSequentialGroup()
                                .addGroup(jPanel_ZaposleniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_ZaposleniLayout.createSequentialGroup()
                                        .addComponent(jButton_Promeni_Sektor)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton_Promeni_Privilegiju)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton_Promeni_Pincode))
                                    .addGroup(jPanel_ZaposleniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(jButton_ProveriPazar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel_ProveraPazara, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)))
                                .addGap(0, 87, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_ZaposleniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel_ZaposleniLayout.createSequentialGroup()
                                .addComponent(jButton_ProveriEvidenciju)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_ProveriEvidenciju_DAN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_Dan, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel_ZaposleniLayout.setVerticalGroup(
            jPanel_ZaposleniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ZaposleniLayout.createSequentialGroup()
                .addComponent(Tabela_Zaposleni, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_ZaposleniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_ProveriEvidenciju)
                    .addComponent(jButton_Promeni_Pincode)
                    .addComponent(jButton_Promeni_Privilegiju)
                    .addComponent(jButton_Promeni_Sektor)
                    .addComponent(jButton_ProveriEvidenciju_DAN)
                    .addComponent(jTextField_Dan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_ZaposleniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_ZaposleniLayout.createSequentialGroup()
                        .addComponent(jButton_addNewEmoloyee, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_Drop_Emoloyer, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_ProveraPazara, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_ProveriPazar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jTextField_Username.setFont(new java.awt.Font("Arial Unicode MS", 2, 14)); // NOI18N
        jTextField_Username.setEnabled(false);

        jLabel_PrijaviSe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/prijaviSe.jpg"))); // NOI18N
        jLabel_PrijaviSe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_PrijaviSeMouseClicked(evt);
            }
        });

        jLabel_OdjaviSe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OdjaviSe.jpg"))); // NOI18N
        jLabel_OdjaviSe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_OdjaviSeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel_MainLayout = new javax.swing.GroupLayout(jPanel_Main);
        jPanel_Main.setLayout(jPanel_MainLayout);
        jPanel_MainLayout.setHorizontalGroup(
            jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_PrijaviSe)
                    .addComponent(jLabel_OdjaviSe, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_MainLayout.createSequentialGroup()
                        .addComponent(jLabel_Monitoring, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_Meni, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_Zaposleni, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119)
                        .addComponent(jTextField_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_Sektor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_Sektor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_LogOFF, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel_Time, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_Close, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(jPanel_MainLayout.createSequentialGroup()
                        .addComponent(jPanel_Monitoring, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MainLayout.createSequentialGroup()
                    .addContainerGap(66, Short.MAX_VALUE)
                    .addComponent(jPanel_KafeMeni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MainLayout.createSequentialGroup()
                    .addContainerGap(68, Short.MAX_VALUE)
                    .addComponent(jPanel_Zaposleni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_MainLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(jLabel_logo)
                    .addContainerGap(1100, Short.MAX_VALUE)))
        );
        jPanel_MainLayout.setVerticalGroup(
            jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_MainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_Monitoring, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Meni, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Time, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_LogOFF, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Zaposleni, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel_Close)
                        .addComponent(jLabel_Sektor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField_Sektor, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(jPanel_MainLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jTextField_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 14, Short.MAX_VALUE)
                .addComponent(jPanel_Monitoring, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel_MainLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel_PrijaviSe, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_OdjaviSe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MainLayout.createSequentialGroup()
                    .addContainerGap(57, Short.MAX_VALUE)
                    .addComponent(jPanel_KafeMeni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MainLayout.createSequentialGroup()
                    .addContainerGap(61, Short.MAX_VALUE)
                    .addComponent(jPanel_Zaposleni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_MainLayout.createSequentialGroup()
                    .addGap(7, 7, 7)
                    .addComponent(jLabel_logo)
                    .addContainerGap(627, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Main, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel_CloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_CloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel_CloseMouseClicked

    private void jLabel_MonitoringMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MonitoringMouseClicked
        jPanel_Monitoring.setVisible(true);
        jLabel_logo.setVisible(true);
        jPanel_KafeMeni.setVisible(false);
        jLabel_PrijaviSe.setVisible(true);
        jLabel_OdjaviSe.setVisible(true);
        hide_employee_dialog();

    }//GEN-LAST:event_jLabel_MonitoringMouseClicked
    
    public void hide_employee_dialog() {
        jScrollPane13.setVisible(false);
        Tabela_Zaposleni.setVisible(false);
        jTable_Records.setVisible(false);
        jButton_Promeni_Pincode.setVisible(false);
        jButton_Promeni_Sektor.setVisible(false);
        jButton_Promeni_Privilegiju.setVisible(false);
        jButton_ProveriEvidenciju.setVisible(false);
        jButton_ProveriEvidenciju_DAN.setVisible(false);
        jButton_addNewEmoloyee.setVisible(false);
        jTextField_Dan.setVisible(false);
        jButton_Drop_Emoloyer.setVisible(false);
        jButton_ProveriPazar.setVisible(false);
    }
    
    public void show_employee_dialog() {
        jScrollPane13.setVisible(true);
        Tabela_Zaposleni.setVisible(true);
        jTable_Records.setVisible(true);
        jButton_Promeni_Pincode.setVisible(true);
        jButton_Promeni_Sektor.setVisible(true);
        jButton_Promeni_Privilegiju.setVisible(true);
        jButton_ProveriEvidenciju.setVisible(true);
        jButton_ProveriEvidenciju_DAN.setVisible(true);
        jButton_addNewEmoloyee.setVisible(true);
        jTextField_Dan.setVisible(true);
        jButton_Drop_Emoloyer.setVisible(true);
        jButton_ProveriPazar.setVisible(true);
    }

    private void jLabel_MeniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MeniMouseClicked
        jPanel_Monitoring.setVisible(false);
        clear_table(jTable_Drinks);
        insert_menu(jTable_Drinks);
        jPanel_KafeMeni.setVisible(true);
        hide_employee_dialog();
        jLabel_OdjaviSe.setVisible(false);
        jLabel_PrijaviSe.setVisible(false);
    }//GEN-LAST:event_jLabel_MeniMouseClicked
    public void check_sektor(JLabel label, int table) {
        if (label.getText().substring(0, 1).equals(jTextField_Sektor.getText())) {
            jLabel_Number_Of_Table.setText("Table " + table);
            show_dialog_stavke();
        } else {
            JOptionPane.showMessageDialog(this, "Ovo nije vaš sektor rada");
        }
    }
    private void jLabel_Table_1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_Table_1MouseClicked
        check_sektor(jLabel_SektorA1, 1);
    }//GEN-LAST:event_jLabel_Table_1MouseClicked
    

    private void jCheckBox_AddChangesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBox_AddChangesMouseClicked
        if (jCheckBox_AddChanges.isEnabled()) {
            if (!jCheckBox_AddChanges.isSelected()) {
                hide_fields_drinks();
            } else {
                show_fields_drinks();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nemate privilegiju nad ovim poljem");
        }
    }//GEN-LAST:event_jCheckBox_AddChangesMouseClicked

    private void jButton_Dodaj_PiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Dodaj_PiceActionPerformed
        if (jTextField_Cena_Pica.getText().isEmpty() || jTextField_Cena_Pica.getText().isEmpty() || jTextField_Raspolozivost_Pica.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Morate popuniti sva polja!", "OBAVESTENJE", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int cena = Integer.parseInt(jTextField_Cena_Pica.getText());
        int raspolozivost = Integer.parseInt(jTextField_Raspolozivost_Pica.getText());
        String ime = jTextField_Ime_Pica.getText();
        
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("udawj332asdu4a2sda");
            writeUTF(jTextField_Username.getText().toUpperCase());
            Pice p = new Pice(null, ime, cena, raspolozivost);
            writeObject(p);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        clear_table(jTable_Drinks);
        insert_menu(jTable_Drinks);
    }//GEN-LAST:event_jButton_Dodaj_PiceActionPerformed

    private void jButton_Izbrisi_PiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Izbrisi_PiceActionPerformed
        String ime = jTextField_Ime_Pica.getText();
        if(ime.isEmpty())
            JOptionPane.showMessageDialog(this, "Morate uneti ime pića kojeg želte da obrišete","GREŠKA",JOptionPane.ERROR_MESSAGE);
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("asdwagtk345ksdaso");
            writeUTF(jTextField_Username.getText().toUpperCase());
            writeUTF(ime);
            
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            String provera=ois.readUTF();
            if(provera.equals("Ne postoji"))
                JOptionPane.showMessageDialog(this, "Piće koje ste uneli ne postoji","GREŠKA",JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        clear_table(jTable_Drinks);
        insert_menu(jTable_Drinks);
    }//GEN-LAST:event_jButton_Izbrisi_PiceActionPerformed

    private void jButton_IzaberiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_IzaberiActionPerformed
        jDialog_Pica.setVisible(false);
        if (jTextArea_IzabranaPica.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Niste nista izabrali!!!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String izabranaPica = jTextArea_IzabranaPica.getText();
        izabranaPica = izabranaPica.substring(0, izabranaPica.length() - 3);
        jTextArea_Pica.setText(izabranaPica);
        tekst = "";
        jTextField_UkupnaCena.setText(ukupnaCena + "");
    }//GEN-LAST:event_jButton_IzaberiActionPerformed

    private void jButtonIzaberiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonIzaberiMouseClicked
        jDialog_Pica.setVisible(true);
        ukupnaCena = 0;
    }//GEN-LAST:event_jButtonIzaberiMouseClicked

    private void jButton_DodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DodajActionPerformed
        if (!jTextField_Kolicina_Pica.getText().equals("")) {
            String value = jTextField_Kolicina_Pica.getText();
            Pattern pattern = Pattern.compile("^[0-9]{1,}$");
            Matcher m = pattern.matcher(value);
            if (m.find() == false) {
                jTextField_Kolicina_Pica.setText("");
                JOptionPane.showMessageDialog(this, "Polje ne sme biti prazno i u polju je dozvoljeno unositi samo brojeve",
                        "POGRESAN UNOS", JOptionPane.ERROR_MESSAGE);
                jTextField_Kolicina_Pica.grabFocus();
                return;
            }
        }
        izaberi_pice();
    }//GEN-LAST:event_jButton_DodajActionPerformed

    private void jTable_MeniPicaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable_MeniPicaFocusGained
        jTextField_Kolicina_Pica.setText("");
    }//GEN-LAST:event_jTable_MeniPicaFocusGained
    
    public int check_ID_Bill() {
        int id_racuna = -1;
        
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("ioniu23984rnjwckmlgerop");
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            id_racuna = Integer.parseInt(ois.readUTF());
            
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id_racuna;
    }

    private void jButton_KreirajRacunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_KreirajRacunActionPerformed
        String pice = jTextArea_Pica.getText();
        String racun = "";
        String[] arr = pice.split(" , ", 30);
        for (int i = 0; i < arr.length; i++) {
            racun += arr[i] + "\n";
        }
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String str = String.valueOf(timestamp);
        String[] arrOfStr = str.split(" ", 2);
        int id_racuna = check_ID_Bill();
        jTextArea_RACUN.setText(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n"
                + "                            Beograd\n"
                + "                         " + arrOfStr[0] + " \n"
                + "                         " + arrOfStr[1] + "    \n"
                + "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n"
                + "                   Broj RACUNA " + id_racuna + "\n"
                + "                   Zaposleni " + jTextField_Username.getText().toUpperCase() + "\n"
                + "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n"
                + racun + "\n"
                + "                                                              \n"
                + "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n"
                + "Za uplatu:                                      " + jTextField_UkupnaCena.getText() + ".00a\n"
                + "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n"
                + "                                                  @MGCAFFE ");

    }//GEN-LAST:event_jButton_KreirajRacunActionPerformed

    private void jButton_PotvrdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PotvrdiActionPerformed
        if (jTextArea_Pica.getText().isEmpty() || jTextField_UkupnaCena.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Polja ne smeju biti prazna");
            return;
        }
        int id = check_id_narudzbenice();
        if (id == 0) {
            id = 1;
        }
        String tekst = "Broj narudzbenice-" + id + "\n" + jTextArea_Pica.getText() + "\n" + "Racun:" + jTextField_UkupnaCena.getText();
        System.out.println(tekst);
        switch (jLabel_Number_Of_Table.getText()) {
            case "Table 1":
                jTextArea_Table1.setText(tekst);
                jTextField_Status_Table1.setBackground(Color.red);
                break;
            case "Table 2":
                jTextArea_Table2.setText(tekst);
                jTextField_Status_Table2.setBackground(Color.red);
                break;
            case "Table 3":
                jTextArea_Table3.setText(tekst);
                jTextField_Status_Table3.setBackground(Color.red);
                break;
            case "Table 4":
                jTextArea_Table4.setText(tekst);
                jTextField_Status_Table4.setBackground(Color.red);
                break;
            case "Table 5":
                jTextArea_Table5.setText(tekst);
                jTextField_Status_Table5.setBackground(Color.red);
                break;
            case "Table 6":
                jTextArea_Table6.setText(tekst);
                jTextField_Status_Table6.setBackground(Color.red);
                break;
        }
        jDialog_Stavke.setVisible(false);
        
        String pica = jTextArea_Pica.getText();
        String[] arr1 = pica.split(" , ", 30);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String pica1 = "";
        ArrayList<Stavka_narudzbenice> snList = new ArrayList<>();
        Stavka_narudzbenice sn;
        
        for (int i = 0; i < arr1.length; i++) {
            pica1 = arr1[i];
            String[] arr2 = pica1.split(" x ", 2);
            sn = new Stavka_narudzbenice(id, check_id_pica(arr2[0]), timestamp, Integer.parseInt(arr2[1]));
            System.out.println(sn.toString());
            snList.add(sn);
            
            update_availability(Integer.parseInt(arr2[1]), check_id_pica(arr2[0]));
        }
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("dnas87vsddsa0sadmalms");
            writeUTF(jTextField_Username.getText().toUpperCase());
            writeObject(snList);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_PotvrdiActionPerformed
    
    public int check_id_narudzbenice() {
        int id = -1;
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("uinjwiembomazam342sjd");
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            id = Integer.parseInt(ois.readUTF());
            
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public int check_id_pica(String ime) {
        int id = -1;
        
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("nweiphe903lep76kemqz");
            writeUTF(ime);
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            id = Integer.parseInt(ois.readUTF());
            
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id;
    }
    
    public void update_availability(Integer kolicina, Integer id_pica) {
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("opiwonewwnei478jnfwenoe");
            writeUTF(kolicina + "");
            writeUTF(id_pica + "");
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jLabel_ZaposleniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ZaposleniMouseClicked
        if (jLabel_Zaposleni.isEnabled() == true) {
            show_employee_dialog();
            jLabel_OdjaviSe.setVisible(false);
            jLabel_PrijaviSe.setVisible(false);
            jPanel_Monitoring.setVisible(false);
            jPanel_KafeMeni.setVisible(false);
            jPanel_Zaposleni.setVisible(true);
            clear_table(jTable_Zaposleni);
            insert_employee();
        } else {
            JOptionPane.showMessageDialog(this, "Nemate privilegiju nad ovim poljem");
        }
    }//GEN-LAST:event_jLabel_ZaposleniMouseClicked

    private void jButton_Promeni_SektorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Promeni_SektorActionPerformed
        int kolona = 7;
        int red = jTable_Zaposleni.getSelectedRow();
        String username = jTable_Zaposleni.getModel().getValueAt(red, 5).toString();
        String sektor = jTable_Zaposleni.getModel().getValueAt(red, kolona).toString();
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            Korisnik u = new Korisnik(null, username, null, null, null, null, sektor, null, null);
            writeUTF("ndalwij213nasldaw634");
            writeUTF(jTextField_Username.getText().toUpperCase());
            writeObject(u);
            
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_Promeni_SektorActionPerformed

    private void jButton_ProveriEvidencijuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ProveriEvidencijuActionPerformed
        clear_table(jTable_Records);
        int red = jTable_Zaposleni.getSelectedRow();
        if (red == -1) {
            JOptionPane.showMessageDialog(this, "Morate oznaciti zaposlenog za kojeg zelite da vidite podatke", "OBAVESTENJE", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String jmbg = jTable_Zaposleni.getModel().getValueAt(red, 3).toString();
        insert_menu_records(jmbg);
    }//GEN-LAST:event_jButton_ProveriEvidencijuActionPerformed
    
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void jButton_Promeni_PrivilegijuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_Promeni_PrivilegijuMouseClicked
        int kolona = 8;
        int red = jTable_Zaposleni.getSelectedRow();
        String username = jTable_Zaposleni.getModel().getValueAt(red, 5).toString();
        String privilegija = jTable_Zaposleni.getModel().getValueAt(red, kolona).toString();
        
        try {
            socket = new Socket("localhost", 4789);
            oos = new ObjectOutputStream(this.socket.getOutputStream());
            
            Korisnik u = new Korisnik(null, username, null, null, null, null, null, privilegija, null);
            writeUTF("akswsm76asdwiekawsd94");
            writeUTF(jTextField_Username.getText().toUpperCase());
            writeObject(u);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (1 == Integer.parseInt(privilegija)) {
            boolean signal = true;
            //default pincode
            String pinkod = "1111";
            while (signal) {
                pinkod = JOptionPane.showInputDialog("Postavi novi pinkod(4 broja) , primer->1111", 4);
                if (pinkod.toCharArray().length == 4 && isNumeric(pinkod)) {
                    signal = false;
                }
            }
            set_pincode(username, pinkod);
        } else {
            set_pincode(username, " ");
            jTable_Zaposleni.getModel().setValueAt("", red, 9);
        }
    }//GEN-LAST:event_jButton_Promeni_PrivilegijuMouseClicked

    private void jButton_ProveriEvidenciju_DANMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_ProveriEvidenciju_DANMouseClicked
        clear_table(jTable_Records);
        int red = jTable_Zaposleni.getSelectedRow();
        if (red == -1) {
            JOptionPane.showMessageDialog(this, "Morate oznaciti zaposlenog za kojeg zelite da vidite podatke", "OBAVESTENJE", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String jmbg = jTable_Zaposleni.getModel().getValueAt(red, 3).toString();
        String uzmiDan = jTextField_Dan.getText();
        String[] nizDatum = uzmiDan.split("-", 2);
        String dan = nizDatum[0];
        String mesec = nizDatum[1];
        
        insert_menu_records_day(dan, mesec, jmbg);
    }//GEN-LAST:event_jButton_ProveriEvidenciju_DANMouseClicked

    private void jTextField_DanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_DanFocusGained
        jTextField_Dan.setForeground(Color.BLACK);
    }//GEN-LAST:event_jTextField_DanFocusGained
    public void set_pincode(String username, String pincode) {
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            Korisnik u1 = new Korisnik(username, pincode);
            writeUTF("jngglr327hf3432n234jfd");
            writeObject(u1);
            System.out.println("objekat iz privilegije poslat");
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jButton_Promeni_PincodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_Promeni_PincodeMouseClicked
        int kolona = 9;
        int red = jTable_Zaposleni.getSelectedRow();
        String username = jTable_Zaposleni.getModel().getValueAt(red, 5).toString();
        String pincode = jTable_Zaposleni.getModel().getValueAt(red, kolona).toString();
        if (pincode.toCharArray().length < 4 || pincode.toCharArray().length > 4) {
            JOptionPane.showMessageDialog(this, "Pinkod se sastoji od 4 broja");
            return;
        }
        
        int advanced_privilages = Integer.parseInt(jTable_Zaposleni.getModel().getValueAt(red, 8).toString());
        if (advanced_privilages != 0) {
            try {
                socket = new Socket("localhost", 4789);
                oos = new ObjectOutputStream(this.socket.getOutputStream());
                
                Korisnik u = new Korisnik(null, username, null, null, null, null, null, null, pincode);
                writeUTF("hjgt368ksdf90feedhsa2");
                writeUTF(jTextField_Username.getText().toUpperCase());
                writeObject(u);
            } catch (IOException ex) {
                Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (advanced_privilages == 0 && !(jTable_Zaposleni.getModel().getValueAt(red, kolona).toString()).isEmpty()) {
            set_pincode(username, " ");
        } else {
            JOptionPane.showMessageDialog(this, "Ne mozete promeniti/postaviti pinkod nekome ko nema privilegiju");
            jTable_Zaposleni.getModel().setValueAt("", red, 9);
        }
    }//GEN-LAST:event_jButton_Promeni_PincodeMouseClicked
    
    public boolean login_check(Timestamp vreme) {
        boolean signal = false;
        String jmbg = check_jmbg();
        try {
            socket = new Socket("localhost", 4789);
            oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("gdoimrd9845grsmgreljde6g");
            writeUTF(String.valueOf(vreme));
            writeUTF(jmbg);
            
            ois = new ObjectInputStream(this.socket.getInputStream());
            signal = ois.readBoolean();
            
            oos.close();
            ois.close();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return signal;
    }

    private void jButton_PrijaviDolazakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PrijaviDolazakActionPerformed
        Timestamp vreme = new Timestamp(System.currentTimeMillis());
        if (login_check(vreme)) {
            try {
                socket = new Socket("localhost", 4789);
                oos = new ObjectOutputStream(this.socket.getOutputStream());
                Evidencija_dolaska ed = new Evidencija_dolaska(null, null, vreme);
                writeUTF("fkpoewmfks4mpr05sd1g");
                writeUTF(jTextField_Username.getText().trim());
                writeObject(ed);
            } catch (IOException ex) {
                Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ne možete se prijaviti 2 puta u toku istog dana", "INF", JOptionPane.ERROR_MESSAGE);
        }
        jDialog_Prijava.setVisible(false);
    }//GEN-LAST:event_jButton_PrijaviDolazakActionPerformed

    private void jLabel_PrijaviSeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_PrijaviSeMouseClicked
        jDialog_Prijava.setVisible(true);
        jDialog_Prijava.setSize(423, 220);
        jDialog_Prijava.setLocationRelativeTo(null);
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Calendar c = Calendar.getInstance();
                        hours = c.get(Calendar.HOUR_OF_DAY);
                        if (hours > 12) {
                            hours -= 12;
                        }
                        minutes = c.get(Calendar.MINUTE);
                        minutes = c.get(Calendar.SECOND);
                        
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                        Date date = c.getTime();
                        timeString = sdf.format(date);
                        jLabel_VremeDolaska.setText(timeString);
                        t.sleep(1000);
                    }
                } catch (Exception e) {
                }
            }
        });
        myThread.start();
    }//GEN-LAST:event_jLabel_PrijaviSeMouseClicked

    private void jLabel_OdjaviSeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_OdjaviSeMouseClicked
        jDialog_Odjava.setVisible(true);
        jDialog_Odjava.setSize(460, 320);
        jDialog_Odjava.setLocationRelativeTo(null);
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Calendar c = Calendar.getInstance();
                        hours = c.get(Calendar.HOUR_OF_DAY);
                        if (hours > 12) {
                            hours -= 12;
                        }
                        minutes = c.get(Calendar.MINUTE);
                        minutes = c.get(Calendar.SECOND);
                        
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                        Date date = c.getTime();
                        timeString = sdf.format(date);
                        jLabel_VremeDolaska1.setText(timeString);
                        t.sleep(1000);
                    }
                } catch (Exception e) {
                }
            }
        });
        myThread.start();
        jLabel_OdjavaPAZAR.setText("Danasnji PAZAR iznosi : " + jTextField_Pazar.getText());
    }//GEN-LAST:event_jLabel_OdjaviSeMouseClicked
    public void naplati(JTextArea jta, JTextField jtf) {
        if (jta.getText().length() != 0) {
            pay_table(jta, jtf);
        } else {
            JOptionPane.showMessageDialog(this, "Sto je prazan!!!", "GRESKA", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void jButton_Naplati_Table1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Naplati_Table1ActionPerformed
        naplati(jTextArea_Table1, jTextField_Status_Table1);
    }//GEN-LAST:event_jButton_Naplati_Table1ActionPerformed

    private void jButton_Naplati_Table2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Naplati_Table2ActionPerformed
        naplati(jTextArea_Table2, jTextField_Status_Table2);
    }//GEN-LAST:event_jButton_Naplati_Table2ActionPerformed

    private void jButton_Naplati_Table3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Naplati_Table3ActionPerformed
        naplati(jTextArea_Table3, jTextField_Status_Table3);
    }//GEN-LAST:event_jButton_Naplati_Table3ActionPerformed

    private void jButton_Naplati_Table4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Naplati_Table4ActionPerformed
        naplati(jTextArea_Table4, jTextField_Status_Table4);
    }//GEN-LAST:event_jButton_Naplati_Table4ActionPerformed

    private void jButton_Naplati_Table5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Naplati_Table5ActionPerformed
        naplati(jTextArea_Table5, jTextField_Status_Table5);
    }//GEN-LAST:event_jButton_Naplati_Table5ActionPerformed

    private void jButton_Naplati_Table6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Naplati_Table6ActionPerformed
        naplati(jTextArea_Table6, jTextField_Status_Table6);
    }//GEN-LAST:event_jButton_Naplati_Table6ActionPerformed

    private void jLabel_Table_2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_Table_2MouseClicked
        check_sektor(jLabel_SektorA2, 2);
    }//GEN-LAST:event_jLabel_Table_2MouseClicked

    private void jLabel_Table_3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_Table_3MouseClicked
        check_sektor(jLabel_SektorA3, 3);
    }//GEN-LAST:event_jLabel_Table_3MouseClicked

    private void jLabel_Table_4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_Table_4MouseClicked
        check_sektor(jLabel_SektorB1, 4);
    }//GEN-LAST:event_jLabel_Table_4MouseClicked

    private void jLabel_Table_5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_Table_5MouseClicked
        check_sektor(jLabel_SektorB2, 5);
    }//GEN-LAST:event_jLabel_Table_5MouseClicked

    private void jLabel_Table_6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_Table_6MouseClicked
        check_sektor(jLabel_SektorB3, 6);
    }//GEN-LAST:event_jLabel_Table_6MouseClicked
    
    public void check_max_id_employee() {
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("fewjkn233kjfs022ewfpo42");
            
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            jTextField_ID.setText(ois.readUTF());
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jButton_addNewEmoloyeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addNewEmoloyeeActionPerformed
        jDialog_Dodaj_Zaposlenog.setVisible(true);
        jDialog_Dodaj_Zaposlenog.setSize(470, 480);
        jDialog_Dodaj_Zaposlenog.setLocationRelativeTo(null);
        check_max_id_employee();
        jTextField_addPincode.setEnabled(false);
        jTextField_Ime.grabFocus();
    }//GEN-LAST:event_jButton_addNewEmoloyeeActionPerformed

    private void jCheckBox_PrivilegesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox_PrivilegesStateChanged
        if (jCheckBox_Privileges.isSelected()) {
            jTextField_addPincode.setEnabled(true);
        } else {
            jTextField_addPincode.setEnabled(false);
            jTextField_addPincode.setText("");
        }
    }//GEN-LAST:event_jCheckBox_PrivilegesStateChanged
    public void clear_addEmployer_dialog() {
        jTextField_ID.setText("");
        jTextField_Ime.setText("");
        jTextField_Prezime.setText("");
        jTextField_JMBG.setText("");
        jTextField_Telefon.setText("");
        jTextField_addUsername.setText("");
        jTextField_addPassword.setText("");
        jTextField_addPincode.setText("");
        jCheckBox_Privileges.setSelected(false);
    }
    
    public boolean check_pincode_validation() {
        boolean signal = false;
        if (jCheckBox_Privileges.isSelected()) {
            String value = jTextField_addPincode.getText();
            Pattern pattern = Pattern.compile("^[0-9]{4}$");
            Matcher m = pattern.matcher(value);
            if (m.find() == false) {
                signal = true;
            }
        }
        return signal;
    }
    
    public boolean check_fields_employer() {
        boolean signal = true;
        if (jTextField_Ime.getText().isEmpty() || jTextField_Prezime.getText().isEmpty() || jTextField_JMBG.getText().isEmpty() || jTextField_Telefon.getText().isEmpty()
                || jTextField_addUsername.getText().isEmpty() || jTextField_addPassword.getText().isEmpty() || check_pincode_validation()) {
            signal = false;
        }
        return signal;
        
    }

    private void jButton_Add_new_EmployerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Add_new_EmployerActionPerformed
        if (check_fields_employer()) {
            String id = jTextField_ID.getText();
            String ime = jTextField_Ime.getText();
            String prezime = jTextField_Prezime.getText();
            String jmbg = jTextField_JMBG.getText();
            String telefon = jTextField_Telefon.getText();
            String username = jTextField_addUsername.getText();
            String password = jTextField_addPassword.getText();
            String sektor = (String) jComboBox_addSektor.getSelectedItem();
            int privilegija = 0;
            if (jCheckBox_Privileges.isSelected()) {
                privilegija = 1;
            }
            String pincode = jTextField_addPincode.getText();
            String advanced_privilages = "" + privilegija;
            
            ZaposleniJOIN zj = new ZaposleniJOIN(Integer.parseInt(id), username, password, advanced_privilages, pincode, sektor, ime, prezime, jmbg, telefon);
            System.out.println(zj.toString());
            try {
                socket = new Socket("localhost", 4789);
                oos = new ObjectOutputStream(this.socket.getOutputStream());
                writeUTF("lfepds3498fasd876sdf");
                writeUTF(jTextField_Username.getText().toUpperCase());
                writeObject(zj);
                jDialog_Dodaj_Zaposlenog.setVisible(false);
            } catch (IOException ex) {
                Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            clear_addEmployer_dialog();
        } else
            JOptionPane.showMessageDialog(this, "SVA POLJA MORAJU BITI POPUNJENA U PRAVILNOM OBLIKU", "GRESKA", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jButton_Add_new_EmployerActionPerformed

    private void jLabel_LogOFFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_LogOFFMouseClicked
        try {
            socket = new Socket("localhost", 4789);
            oos = new ObjectOutputStream(this.socket.getOutputStream());
            Thread myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            writeUTF(jTextField_Username.getText() + " se izlogovao/la!\n");
                        } catch (IOException ex) {
                            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            myThread.start();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
        new LoginForm().setVisible(true);
    }//GEN-LAST:event_jLabel_LogOFFMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (jTextField_Username.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Morate se prvo ulogovati!!!", "GRESKA", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            new LoginForm().setVisible(true);
        }
        
        try {
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            
            writeUTF(jTextField_Username.getText() + " se ulogovao/la! \n");
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextField_Pazar.setText("0");
    }//GEN-LAST:event_formWindowOpened

    private void jButton_Promeni_PiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Promeni_PiceActionPerformed
        int red = jTable_Drinks.getSelectedRow();
        String ime="";
        try{
        ime = jTable_Drinks.getModel().getValueAt(red, 0).toString();
        }catch(ArrayIndexOutOfBoundsException ae){
            JOptionPane.showMessageDialog(this, "Morate označiti piće koje nad kojim želite vršiti promene","GREŠKA",JOptionPane.ERROR_MESSAGE);
            return;
        }
        String novoIme = null;
        Integer novaCena = null;
        Integer novaRaspolozivost = null;
        boolean proveraPolja=true;
        if (!jTextField_Ime_Pica.getText().equals("")) {
            novoIme = jTextField_Ime_Pica.getText();
            proveraPolja=false;
        }
        if (!jTextField_Cena_Pica.getText().equals("")) {
            novaCena = Integer.parseInt(jTextField_Cena_Pica.getText());
            proveraPolja=false;
        }
        if (!jTextField_Raspolozivost_Pica.getText().equals("")) {
            novaRaspolozivost = Integer.parseInt(jTextField_Raspolozivost_Pica.getText());
            proveraPolja=false;
        }
        
        if(proveraPolja)
            JOptionPane.showMessageDialog(this, "Morate uneti novu vrednost","GREŠKA",JOptionPane.ERROR_MESSAGE);
        
        Pice p = new Pice(null, ime, null, null, novoIme, novaCena, novaRaspolozivost);
        try {
            socket = new Socket("localhost", 4789);
            oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("dmwapsdomqwp028asm56m");
            writeUTF(jTextField_Username.getText().toUpperCase());
            writeObject(p);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        clear_table(jTable_Drinks);
        insert_menu(jTable_Drinks);
    }//GEN-LAST:event_jButton_Promeni_PiceActionPerformed

    private void jButton_Drop_EmoloyerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Drop_EmoloyerActionPerformed
        int red = jTable_Zaposleni.getSelectedRow();
        String id = jTable_Zaposleni.getModel().getValueAt(red, 0).toString();
        try {
            socket = new Socket("localhost", 4789);
            oos = new ObjectOutputStream(this.socket.getOutputStream());
            
            writeUTF("sappdm234asdams2343mds");
            writeUTF(jTextField_Username.getText().toUpperCase());
            writeUTF(id);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_Drop_EmoloyerActionPerformed

    private void jLabel_CloseDialogLogINMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_CloseDialogLogINMouseClicked
        jDialog_Prijava.setVisible(false);
    }//GEN-LAST:event_jLabel_CloseDialogLogINMouseClicked

    private void jButton_OdjaviSeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OdjaviSeActionPerformed
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Integer pazar = Integer.parseInt(jTextField_Pazar.getText());
        try {
            socket = new Socket("localhost", 4789);
            oos = new ObjectOutputStream(this.socket.getOutputStream());
            Evidencija_odlaska eo = new Evidencija_odlaska(null, null, timestamp, pazar);
            writeUTF("pdsaw32fe90vsmawjiddh");
            writeUTF(jTextField_Username.getText().trim());
            writeObject(eo);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }//GEN-LAST:event_jButton_OdjaviSeActionPerformed

    private void jLabel_CloseDialogLogOffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_CloseDialogLogOffMouseClicked
        jDialog_Odjava.setVisible(false);
    }//GEN-LAST:event_jLabel_CloseDialogLogOffMouseClicked

    private void jDialog_PrijavaWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialog_PrijavaWindowLostFocus
        jDialog_Prijava.setVisible(false);
    }//GEN-LAST:event_jDialog_PrijavaWindowLostFocus

    private void jDialog_OdjavaWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialog_OdjavaWindowLostFocus
        jDialog_Odjava.setVisible(false);
    }//GEN-LAST:event_jDialog_OdjavaWindowLostFocus
    
    public void check_field_validation(JTextField jtf) {
        if (!jtf.getText().equals("")) {
            String value = jtf.getText();
            Pattern pattern = Pattern.compile("^[A-Za-z]{2,}$");
            Matcher m = pattern.matcher(value);
            if (m.find() == false) {
                jtf.setText("");
                JOptionPane.showMessageDialog(this, "Polje ne sme biti prazno i u polju je dozvoljeno unositi samo slova(NAJMANJE 2)",
                        "POGRESAN UNOS", JOptionPane.ERROR_MESSAGE);
                jtf.grabFocus();
            }
        }
    }

    private void jTextField_ImeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_ImeFocusLost
        check_field_validation(jTextField_Ime);
    }//GEN-LAST:event_jTextField_ImeFocusLost

    private void jTextField_PrezimeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_PrezimeFocusLost
        check_field_validation(jTextField_Prezime);
    }//GEN-LAST:event_jTextField_PrezimeFocusLost

    private void jTextField_JMBGFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_JMBGFocusLost
        if (!jTextField_JMBG.getText().equals("")) {
            String value = jTextField_JMBG.getText();
            Pattern pattern = Pattern.compile("^[0-9]{13}$");
            Matcher m = pattern.matcher(value);
            if (m.find() == false) {
                jTextField_JMBG.setText("");
                JOptionPane.showMessageDialog(this, "Polje ne sme biti prazno i u polju je dozvoljeno unositi samo brojeve(JMBG SE SASTOJI OD 13 BROJEVA)",
                        "POGRESAN UNOS", JOptionPane.ERROR_MESSAGE);
                jTextField_JMBG.grabFocus();
            }
        }
    }//GEN-LAST:event_jTextField_JMBGFocusLost

    private void jTextField_TelefonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_TelefonFocusLost
        if (!jTextField_Telefon.getText().equals("") || jTextField_Telefon.getText().equals("000/000-0000")) {
            String value = jTextField_Telefon.getText();
            Pattern pattern = Pattern.compile("^[0-9]{3}/[0-9]{3}-[0-9]{4}$");
            Matcher m = pattern.matcher(value);
            if (m.find() == false) {
                jTextField_Telefon.setForeground(new Color(171, 171, 235));
                jTextField_Telefon.setText("000/000-0000");
                JOptionPane.showMessageDialog(this, "Polje ne sme biti prazno i u polju je dozvoljeno unositi samo brojeve(FORMAT : 000/000-0000)",
                        "POGRESAN UNOS", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jTextField_TelefonFocusLost

    private void jTextField_TelefonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_TelefonFocusGained
        if (jTextField_Telefon.getText().equals("000/000-0000")) {
            jTextField_Telefon.setText("");
            jTextField_Telefon.setForeground(Color.black);
        }
    }//GEN-LAST:event_jTextField_TelefonFocusGained

    private void jDialog_PicaWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialog_PicaWindowActivated
        clear_table(jTable_MeniPica);
        insert_menu(jTable_MeniPica);
    }//GEN-LAST:event_jDialog_PicaWindowActivated

    private void jButton_UnesiRacunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_UnesiRacunActionPerformed
        logger.log(Level.FINEST, "Racuni");
        logger.setLevel(Level.FINEST);
        FileHandler fh;
        try {
            fh = new FileHandler("Racuni.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.log(Level.FINEST, jTextArea_RACUN.getText() + "\n\n=============================================================================\n");
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JOptionPane.showMessageDialog(this, "RACUN USPESNO UNET U SISTEM");
    }//GEN-LAST:event_jButton_UnesiRacunActionPerformed

    private void jButton_ProveriPazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ProveriPazarActionPerformed
        clear_table(jTable_Pazar);
        int red = jTable_Zaposleni.getSelectedRow();
        String JMBG = jTable_Zaposleni.getModel().getValueAt(red, 3).toString();
        LinkedList<Evidencija_odlaska> list = new LinkedList<>();
        try {
            socket = new Socket("localhost", 4789);
            oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("finsid324op98sdjfsnacms");
            writeUTF(JMBG);
            
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            list = (LinkedList<Evidencija_odlaska>) ois.readObject();
            System.out.println("ois primljennnn");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Evidencija_odlaska evo : list) {
            String tbData[] = {evo.getDatum(), String.valueOf(evo.getPazar())};
            DefaultTableModel tblModel = (DefaultTableModel) jTable_Pazar.getModel();
            
            tblModel.addRow(tbData);
        }
    }//GEN-LAST:event_jButton_ProveriPazarActionPerformed

    private void jLabel_Check_AvailabilityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_Check_AvailabilityMouseClicked
        jDialog_Check_Availability.setSize(400, 640);
        jDialog_Check_Availability.setVisible(true);
    }//GEN-LAST:event_jLabel_Check_AvailabilityMouseClicked

    private void jDialog_Check_AvailabilityWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialog_Check_AvailabilityWindowActivated
        clear_table(jTable__Availability);
        insert_menu_availability(jTable__Availability);
    }//GEN-LAST:event_jDialog_Check_AvailabilityWindowActivated

    private void jButton_PostaviRaspolozivostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PostaviRaspolozivostActionPerformed
        int red = jTable__Availability.getSelectedRow();
        String ime_pica = jTable__Availability.getModel().getValueAt(red, 0).toString();        
        Integer raspolozivost = Integer.parseInt(jTextField_PostaviRaspolozivost.getText());
        Pice p = new Pice(null, ime_pica, null, null, null, null, raspolozivost);
        try {
            socket = new Socket("localhost", 4789);
            oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("ijefkl9824nwfeauomle8ryt");
            writeUTF(jTextField_Username.getText());
            writeObject(p);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_PostaviRaspolozivostActionPerformed

    private void jButton_PostaviRaspolozivostSvimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PostaviRaspolozivostSvimaActionPerformed
        String raspolozivost = jTextField_PostaviRaspolozivostSvima.getText();
        try {
            socket = new Socket("localhost", 4789);
            oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("pnhf64hidpzamwqrfuer3nfs");
            writeUTF(jTextField_Username.getText());
            writeUTF(raspolozivost);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_PostaviRaspolozivostSvimaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmployeeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Tabela_Zaposleni;
    private javax.swing.JButton jButtonIzaberi;
    private javax.swing.JButton jButton_Add_new_Employer;
    private javax.swing.JButton jButton_Dodaj;
    private javax.swing.JButton jButton_Dodaj_Pice;
    private javax.swing.JButton jButton_Drop_Emoloyer;
    private javax.swing.JButton jButton_Izaberi;
    private javax.swing.JButton jButton_Izbrisi_Pice;
    private javax.swing.JButton jButton_KreirajRacun;
    private javax.swing.JButton jButton_Naplati_Table1;
    private javax.swing.JButton jButton_Naplati_Table2;
    private javax.swing.JButton jButton_Naplati_Table3;
    private javax.swing.JButton jButton_Naplati_Table4;
    private javax.swing.JButton jButton_Naplati_Table5;
    private javax.swing.JButton jButton_Naplati_Table6;
    private javax.swing.JButton jButton_OdjaviSe;
    private javax.swing.JButton jButton_PostaviRaspolozivost;
    private javax.swing.JButton jButton_PostaviRaspolozivostSvima;
    private javax.swing.JButton jButton_Potvrdi;
    private javax.swing.JButton jButton_PrijaviDolazak;
    private javax.swing.JButton jButton_Promeni_Pice;
    private javax.swing.JButton jButton_Promeni_Pincode;
    private javax.swing.JButton jButton_Promeni_Privilegiju;
    private javax.swing.JButton jButton_Promeni_Sektor;
    private javax.swing.JButton jButton_ProveriEvidenciju;
    private javax.swing.JButton jButton_ProveriEvidenciju_DAN;
    private javax.swing.JButton jButton_ProveriPazar;
    private javax.swing.JButton jButton_UnesiRacun;
    private javax.swing.JButton jButton_addNewEmoloyee;
    public static javax.swing.JCheckBox jCheckBox_AddChanges;
    private javax.swing.JCheckBox jCheckBox_Privileges;
    private javax.swing.JComboBox<String> jComboBox_addSektor;
    private javax.swing.JDialog jDialog_Check_Availability;
    private javax.swing.JDialog jDialog_Dodaj_Zaposlenog;
    private javax.swing.JDialog jDialog_Odjava;
    private javax.swing.JDialog jDialog_Pica;
    private javax.swing.JDialog jDialog_Prijava;
    private javax.swing.JDialog jDialog_Stavke;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel_Cena_Pica;
    private javax.swing.JLabel jLabel_Check_Availability;
    private javax.swing.JLabel jLabel_Close;
    private javax.swing.JLabel jLabel_CloseDialogLogIN;
    private javax.swing.JLabel jLabel_CloseDialogLogOff;
    private javax.swing.JLabel jLabel_Datum_Vreme;
    private javax.swing.JLabel jLabel_DodajIzmene;
    private javax.swing.JLabel jLabel_Ime_Pica;
    private javax.swing.JLabel jLabel_IzaberiPice;
    private javax.swing.JLabel jLabel_KafeMeni;
    private javax.swing.JLabel jLabel_Kolicina;
    private javax.swing.JLabel jLabel_LogOFF;
    private javax.swing.JLabel jLabel_Meni;
    private javax.swing.JLabel jLabel_Monitoring;
    private javax.swing.JLabel jLabel_Number_Of_Table;
    private javax.swing.JLabel jLabel_OdjavaPAZAR;
    private javax.swing.JLabel jLabel_OdjaviSe;
    private javax.swing.JLabel jLabel_PrijaviSe;
    private javax.swing.JLabel jLabel_ProveraPazara;
    private javax.swing.JLabel jLabel_Raspolozivost_Pica;
    private javax.swing.JLabel jLabel_Sektor;
    private javax.swing.JLabel jLabel_SektorA1;
    private javax.swing.JLabel jLabel_SektorA2;
    private javax.swing.JLabel jLabel_SektorA3;
    private javax.swing.JLabel jLabel_SektorB1;
    private javax.swing.JLabel jLabel_SektorB2;
    private javax.swing.JLabel jLabel_SektorB3;
    private javax.swing.JLabel jLabel_Slika;
    public static javax.swing.JLabel jLabel_Table_1;
    public static javax.swing.JLabel jLabel_Table_2;
    public static javax.swing.JLabel jLabel_Table_3;
    public static javax.swing.JLabel jLabel_Table_4;
    public static javax.swing.JLabel jLabel_Table_5;
    public static javax.swing.JLabel jLabel_Table_6;
    private javax.swing.JLabel jLabel_Time;
    private javax.swing.JLabel jLabel_UkupnaCena;
    private javax.swing.JLabel jLabel_Vreme;
    private javax.swing.JLabel jLabel_VremeDolaska;
    private javax.swing.JLabel jLabel_VremeDolaska1;
    public static javax.swing.JLabel jLabel_Zaposleni;
    private javax.swing.JLabel jLabel_logo;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel_KafeMeni;
    private javax.swing.JPanel jPanel_Main;
    private javax.swing.JPanel jPanel_Meni;
    private javax.swing.JPanel jPanel_Monitoring;
    private javax.swing.JPanel jPanel_OdjaviSe;
    private javax.swing.JPanel jPanel_PrijaviDolazak;
    private javax.swing.JPanel jPanel_Zaposleni;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable_Drinks;
    private javax.swing.JTable jTable_MeniPica;
    private javax.swing.JTable jTable_Pazar;
    private javax.swing.JTable jTable_Records;
    private javax.swing.JTable jTable_Zaposleni;
    private javax.swing.JTable jTable__Availability;
    private javax.swing.JTextArea jTextArea_IzabranaPica;
    private javax.swing.JTextArea jTextArea_Pica;
    private javax.swing.JTextArea jTextArea_RACUN;
    private javax.swing.JTextArea jTextArea_Table1;
    private javax.swing.JTextArea jTextArea_Table2;
    private javax.swing.JTextArea jTextArea_Table3;
    private javax.swing.JTextArea jTextArea_Table4;
    private javax.swing.JTextArea jTextArea_Table5;
    private javax.swing.JTextArea jTextArea_Table6;
    private javax.swing.JTextField jTextField_Cena_Pica;
    private javax.swing.JTextField jTextField_Dan;
    private javax.swing.JTextField jTextField_ID;
    private javax.swing.JTextField jTextField_Ime;
    private javax.swing.JTextField jTextField_Ime_Pica;
    private javax.swing.JTextField jTextField_JMBG;
    private javax.swing.JTextField jTextField_Kolicina_Pica;
    private javax.swing.JTextField jTextField_Pazar;
    private javax.swing.JTextField jTextField_PostaviRaspolozivost;
    private javax.swing.JTextField jTextField_PostaviRaspolozivostSvima;
    private javax.swing.JTextField jTextField_Prezime;
    private javax.swing.JTextField jTextField_Raspolozivost_Pica;
    public static javax.swing.JTextField jTextField_Sektor;
    public static javax.swing.JTextField jTextField_Status_Table1;
    public static javax.swing.JTextField jTextField_Status_Table2;
    public static javax.swing.JTextField jTextField_Status_Table3;
    public static javax.swing.JTextField jTextField_Status_Table4;
    public static javax.swing.JTextField jTextField_Status_Table5;
    public static javax.swing.JTextField jTextField_Status_Table6;
    private javax.swing.JTextField jTextField_Telefon;
    private javax.swing.JTextField jTextField_UkupnaCena;
    public static javax.swing.JTextField jTextField_Username;
    private javax.swing.JTextField jTextField_addPassword;
    private javax.swing.JTextField jTextField_addPincode;
    private javax.swing.JTextField jTextField_addUsername;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        
        try {
            while (true) {
                Calendar c = Calendar.getInstance();
                hours = c.get(Calendar.HOUR_OF_DAY);
                if (hours > 12) {
                    hours -= 12;
                }
                minutes = c.get(Calendar.MINUTE);
                minutes = c.get(Calendar.SECOND);
                
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                Date date = c.getTime();
                timeString = sdf.format(date);
                jLabel_Time.setText(timeString);
                t.sleep(1000);
            }
        } catch (Exception e) {
        }
    }
    
}
