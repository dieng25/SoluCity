package edu.ezip.ing1.pds;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.ezip.ing1.pds.DashboardFenetre.*;

public class SoluCityApp extends JFrame {
    public SoluCityApp() {

        setTitle("SoluCityApp");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        getContentPane().setBackground(Color.WHITE);


        JLabel welcomeLabel = new JLabel("Bienvenue sur SoluCityApp!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(0, 123, 255));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));


        JButton citoyenButton = new JButton("Citoyen");
        JButton mairieButton = new JButton("Mairie");
        JButton dashboardButton = new JButton("Statistiques");
        


        JButton[] buttons = {dashboardButton, citoyenButton, mairieButton};
        for (JButton btn : buttons) {
            btn.setBackground(new Color(0, 123, 255));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }


        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainDashboard().setVisible(true);

            }
        });

        citoyenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrameCitoyen();

            }
        });

        mairieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MairieGUI();
                // User.initMainFrame(); //pour s'authentifier d'abord avant d'avoir accés à l'app Mairie
            }
        });


        setLayout(new BorderLayout());


        add(welcomeLabel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(citoyenButton);
        buttonPanel.add(mairieButton);
        buttonPanel.add(dashboardButton);
        
        add(buttonPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SoluCityApp().setVisible(true));
    }
}


