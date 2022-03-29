/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Start;

import View.EmployeeForm;
import View.LoginForm;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author grozd
 */
public class Main {

    private JFrame frame;
    private ImageIcon caffeIcon;
    private JLabel myLabel;

    public void LoadingForm() {

        caffeIcon = new ImageIcon(this.getClass().getResource("main-background.jpg"));
        myLabel = new JLabel();
        myLabel.setSize(550, 412);
        myLabel.setIcon(caffeIcon);

        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setLayout(new BorderLayout());
        frame.add(myLabel,BorderLayout.CENTER);
        frame.setSize(550, 412);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel jp = new JPanel();
        jp.setBackground(new Color(114, 76, 33));
        JLabel jl = new JLabel();
        jl.setForeground(Color.WHITE);
        jp.setLayout(new GridBagLayout());
        jp.add(jl);
        jp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jl.setText("CAFFE MENAGEMENT SYSTEM");
        jl.setFont(new Font("Segoe Script", Font.BOLD, 26));

        JPanel jp1 = new JPanel();
        jp1.setBackground(new Color(114, 76, 33));
        JProgressBar pb = new JProgressBar(0, 100);
        pb.setSize(100, 100);
        jp1.add(pb);

        frame.add(jp, BorderLayout.NORTH);
        frame.add(jp1, BorderLayout.SOUTH);

        pb.setStringPainted(true);

        int i = 0;
        while (i <= 102) {

            if (i <= 0 && i <= 50) {
                pb.setString("Učitavanje...");
            } else if (i <= 50 && i <= 70) {
                pb.setString("Sačekajte neko vreme");
            } else if (i <= 70 && i <= 90) {
                pb.setString("JoŠ malo");
            } else if (i == 100) {
                pb.setString("Gotovo");
            }

            pb.setValue(i);

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }

            i = i + 10;
        }

        frame.dispose();
    }

    public static void main(String[] args) {

        Main main = new Main();
        main.LoadingForm();
        new LoginForm().setVisible(true);
//          new EmployeeForm().setVisible(true);
    }
}
