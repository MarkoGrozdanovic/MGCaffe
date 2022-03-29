/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Korisnik;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author grozd
 */
public class LoginForm extends javax.swing.JFrame {

    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public LoginForm() {
        initComponents();

        //postavljanje forme na centar ekrana
        this.setLocationRelativeTo(null);

        Border title = BorderFactory.createMatteBorder(0, 1, 1, 1, Color.yellow);
        JPanel_Title.setBorder(title);

        Border minimize_close = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black);
        jLabel_Close.setBorder(minimize_close);
        jLabel_Minimize.setBorder(minimize_close);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel_Minimize = new javax.swing.JLabel();
        JPanel_Title = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel_Close = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel_Password = new javax.swing.JLabel();
        jLabel_Username = new javax.swing.JLabel();
        jTextField_Username = new javax.swing.JTextField();
        jPasswordField_Password = new javax.swing.JPasswordField();
        jButton_Login = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(51, 0, 153));

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        jLabel_Minimize.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel_Minimize.setText(" -");
        jLabel_Minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_MinimizeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_MinimizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_MinimizeMouseExited(evt);
            }
        });

        JPanel_Title.setBackground(new java.awt.Color(0, 102, 0));
        JPanel_Title.setFont(new java.awt.Font("Ebrima", 0, 48)); // NOI18N
        JPanel_Title.setName("JPanel_Title"); // NOI18N

        jLabel3.setBackground(new java.awt.Color(0, 204, 55));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Login");

        javax.swing.GroupLayout JPanel_TitleLayout = new javax.swing.GroupLayout(JPanel_Title);
        JPanel_Title.setLayout(JPanel_TitleLayout);
        JPanel_TitleLayout.setHorizontalGroup(
            JPanel_TitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanel_TitleLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(65, 65, 65))
        );
        JPanel_TitleLayout.setVerticalGroup(
            JPanel_TitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanel_TitleLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel_Close.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel_Close.setText(" x ");
        jLabel_Close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_CloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_CloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_CloseMouseExited(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

        jLabel_Password.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/password.png"))); // NOI18N
        jLabel_Password.setText("jLabel2");

        jLabel_Username.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/username.png"))); // NOI18N

        jTextField_Username.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jTextField_Username.setForeground(new java.awt.Color(153, 153, 153));
        jTextField_Username.setText("username");
        jTextField_Username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_UsernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_UsernameFocusLost(evt);
            }
        });
        jTextField_Username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_UsernameKeyPressed(evt);
            }
        });

        jPasswordField_Password.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jPasswordField_Password.setForeground(new java.awt.Color(153, 153, 153));
        jPasswordField_Password.setText("username");
        jPasswordField_Password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPasswordField_PasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordField_PasswordFocusLost(evt);
            }
        });
        jPasswordField_Password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordField_PasswordKeyPressed(evt);
            }
        });

        jButton_Login.setBackground(new java.awt.Color(51, 51, 255));
        jButton_Login.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jButton_Login.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Login.setText("Login");
        jButton_Login.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_LoginMouseEntered(evt);
            }
        });
        jButton_Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel_Username)
                            .addComponent(jLabel_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPasswordField_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(jButton_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField_Username)
                    .addComponent(jLabel_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel_Password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPasswordField_Password))
                .addGap(18, 18, 18)
                .addComponent(jButton_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(160, Short.MAX_VALUE)
                .addComponent(JPanel_Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jLabel_Minimize, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_Close, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JPanel_Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_Minimize, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Close, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(24, 24, 24)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //metoda za slanje String-ova pomoću ObjectOutputStream-a
     public void writeUTF(String s) throws IOException{
        oos.writeUTF(s);
        oos.flush();
    }
     //metoda za slanje objekata pomoću ObjectOutputStream-a
     public void writeObject(Object o) throws IOException{
        oos.writeObject(o);
        oos.flush();
    }
    
    //metoda koja za datog korisnika vraća kom sektoru pripada
    public String check_sektor_with_username(String username) {
        String ret = "";
        try {
            //inicijalizacija soketa tj. dodeljivanje ip adrese i porta
            socket = new Socket("localhost", 4789);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            writeUTF("mfksldfsdkmfs3349sdmfsd");
            Korisnik k=new Korisnik(null,username,null);
            writeObject(k);

            this.ois = new ObjectInputStream(this.socket.getInputStream());
            ret += ois.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    public void log_in() throws IOException {
        String username = jTextField_Username.getText();
        String password = String.valueOf(jPasswordField_Password.getPassword());
        
        if (jTextField_Username.getText().isEmpty() || (String.valueOf(jPasswordField_Password.getPassword())).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Polja ne smeju biti prazna!!!", "GRESKA", JOptionPane.WARNING_MESSAGE);
            return;
        }
                
        socket = new Socket("localhost", 4789);
        this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        writeUTF("jkgoq489jsdfns38jmsd");
        Korisnik k=new Korisnik(null,username,password);
        oos.writeObject(k);
        oos.flush();

        this.ois = new ObjectInputStream(this.socket.getInputStream());
        //Nakon što smo poslali datog Korisnika,dobijamo informaciju nazad da li dati korisnik postoji
        String accepted = ois.readUTF();

        EmployeeForm cf = new EmployeeForm();
        //Ukoliko korisnik postoji ulazimo u if statement,a ako ne postoji dobijamo grešku
        if (accepted.equals("log_in_accepted")) {
            String privilages = ois.readUTF();
            String pincode = "";
            //provera da li dati korisnik ima privilegije i citanje njegovog pinkod-a ukoliko ih ima
            if (privilages.equals("1")) {
                pincode = ois.readUTF();
            }
                /*ulazimo u jos jedan if statement u kome se korisniku traži da unese pinkod i proverava da li je korisnik uneo dobar pinkod
                U slučaju da korisnik nema privilegije,otvara se novi prozor tj. program nastavlja dalje sa radom*/
                if (privilages.equals("1")) {
                    String m = JOptionPane.showInputDialog("Unesite pinkod", 42);
                        if (m.equals(pincode)) {
                            JOptionPane.showMessageDialog(this, "Uspesno ste se ulogovali");
                            this.dispose();
                            cf.show();
                            EmployeeForm.jTextField_Sektor.setText("X");
                        } else {
                            JOptionPane.showMessageDialog(this, "Uneli ste pogresan pincode");
                            jTextField_Username.setText("");
                            jPasswordField_Password.setText("");
                        }

                    EmployeeForm.jTextField_Username.setText(username);

                } else {
                    this.dispose();
                    cf.show();
                    EmployeeForm.jLabel_Zaposleni.setEnabled(false);
                    EmployeeForm.jCheckBox_AddChanges.setEnabled(false);
                    EmployeeForm.jTextField_Sektor.setText(check_sektor_with_username(username));
                    EmployeeForm.jTextField_Username.setText(username);
                    EmployeeForm.jTextField_Username.setSize(71, 31);
                }
        } else {
            JOptionPane.showMessageDialog(this, "Uneli ste pogresan username ili password","GREŠKA",JOptionPane.ERROR_MESSAGE);
            jTextField_Username.setText("");
            jPasswordField_Password.setText("");
        }

    }
    private void jLabel_MinimizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MinimizeMouseEntered
        Border minimize_close = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white);
        jLabel_Minimize.setBorder(minimize_close);
        jLabel_Minimize.setForeground(Color.white);
    }//GEN-LAST:event_jLabel_MinimizeMouseEntered

    private void jLabel_CloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_CloseMouseExited
        Border minimize_close = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black);
        jLabel_Close.setBorder(minimize_close);
        jLabel_Close.setForeground(Color.black);
    }//GEN-LAST:event_jLabel_CloseMouseExited

    private void jLabel_CloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_CloseMouseEntered
        Border minimize_close = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white);
        jLabel_Close.setBorder(minimize_close);
        jLabel_Close.setForeground(Color.white);
    }//GEN-LAST:event_jLabel_CloseMouseEntered

    private void jLabel_MinimizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MinimizeMouseExited
        Border minimize_close = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black);
        jLabel_Minimize.setBorder(minimize_close);
        jLabel_Minimize.setForeground(Color.black);
    }//GEN-LAST:event_jLabel_MinimizeMouseExited

    private void jTextField_UsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_UsernameFocusGained
        if (jTextField_Username.getText().trim().toLowerCase().equals("username")) {
            jTextField_Username.setText("");
            jTextField_Username.setForeground(Color.black);
        }

        Border jtf = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.yellow);

        jTextField_Username.setBorder(jtf);

    }//GEN-LAST:event_jTextField_UsernameFocusGained

    private void jTextField_UsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_UsernameFocusLost
        if (jTextField_Username.getText().trim().equals("") || jTextField_Username.getText().trim().toLowerCase().equals("username")) {
            jTextField_Username.setText("username");
            jTextField_Username.setForeground(new Color(153, 153, 153));
        }
        jTextField_Username.setBorder(null);
    }//GEN-LAST:event_jTextField_UsernameFocusLost

    private void jPasswordField_PasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField_PasswordFocusGained
        String pass = String.valueOf(jPasswordField_Password.getPassword());
        jPasswordField_Password.setText("");
        jPasswordField_Password.setForeground(Color.black);
        Border jtf = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.yellow);
        jPasswordField_Password.setBorder(jtf);
    }//GEN-LAST:event_jPasswordField_PasswordFocusGained

    private void jPasswordField_PasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField_PasswordFocusLost
        String pass = String.valueOf(jPasswordField_Password.getPassword());

        if (pass.trim().toLowerCase().equals("password") || pass.toLowerCase().equals("")) {
            jPasswordField_Password.setText("password");
            jPasswordField_Password.setForeground(new Color(153, 153, 153));
        }
        jPasswordField_Password.setBorder(null);
    }//GEN-LAST:event_jPasswordField_PasswordFocusLost

    private void jButton_LoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_LoginMouseEntered
        jButton_Login.setBackground(new Color(0, 101, 183));
    }//GEN-LAST:event_jButton_LoginMouseEntered

    private void jLabel_MinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_MinimizeMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel_MinimizeMouseClicked

    private void jLabel_CloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_CloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel_CloseMouseClicked

    private void jPasswordField_PasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField_PasswordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                log_in();
            } catch (IOException ex) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jPasswordField_PasswordKeyPressed

    private void jTextField_UsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_UsernameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && !jTextField_Username.getText().equals("")) {
            jPasswordField_Password.grabFocus();
        }
    }//GEN-LAST:event_jTextField_UsernameKeyPressed

    private void jButton_LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LoginActionPerformed
        try {
            log_in();
        } catch (IOException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_LoginActionPerformed

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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanel_Title;
    private javax.swing.JButton jButton_Login;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel_Close;
    private javax.swing.JLabel jLabel_Minimize;
    private javax.swing.JLabel jLabel_Password;
    private javax.swing.JLabel jLabel_Username;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField jPasswordField_Password;
    private javax.swing.JTextField jTextField_Username;
    // End of variables declaration//GEN-END:variables
}
