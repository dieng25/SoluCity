package edu.ezip.ing1.pds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SoluCityApp extends JFrame {
    public SoluCityApp() {
        // Configuration de la fenêtre principale
        setTitle("SoluCityApp");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création du label de bienvenue
        JLabel welcomeLabel = new JLabel("Bienvenue sur SoluCityApp!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Création des boutons
        JButton dashboardButton = new JButton("Dashboard");
        JButton citoyenButton = new JButton("Citoyen");
        JButton mairieButton = new JButton("Mairie");

        // Ajout des actions aux boutons
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
            }
        });

        // Mise en page des composants
        setLayout(new BorderLayout());
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.add(dashboardButton);
        buttonPanel.add(citoyenButton);
        buttonPanel.add(mairieButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SoluCityApp().setVisible(true));
    }
}
