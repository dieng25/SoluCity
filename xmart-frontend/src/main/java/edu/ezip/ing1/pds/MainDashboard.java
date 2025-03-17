package edu.ezip.ing1.pds;
import javax.swing.*;
import java.awt.*;

public class MainDashboard extends JFrame {

    public MainDashboard() {

        setTitle("SoluCityBoard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(Color.CYAN);
        backgroundPanel.setLayout(new BorderLayout());


        JLabel welcomeLabel = new JLabel("Welcome to SoluCityBoard! Admin", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        backgroundPanel.add(welcomeLabel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(Color.lightGray);
        
        JButton viewProfileButton = new JButton("View Profile");
        JButton settingsButton = new JButton("Settings");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(viewProfileButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(logoutButton);

        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        add(backgroundPanel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu dashboardsMenu = new JMenu("Dashboards");

        JMenuItem General = new JMenuItem("Vue d'ensemble");
        General.addActionListener(e -> openIncidentDashboard());
        dashboardsMenu.add(General);

        JMenuItem IncidentItem = new JMenuItem("Statistiques sur les incidents");
        JMenuItem SuggestionItem = new JMenuItem("Statistiques sur les suggestions");
        JMenuItem MairieItem = new JMenuItem("Statistiques par Mairie");
        JMenuItem TableauItem = new JMenuItem("Tableau de bord spécifique");
        
        dashboardsMenu.add(General);
        dashboardsMenu.addSeparator();
        dashboardsMenu.add(IncidentItem);
        dashboardsMenu.addSeparator();
        dashboardsMenu.add(SuggestionItem);
        dashboardsMenu.addSeparator();
        dashboardsMenu.add(MairieItem);
        dashboardsMenu.addSeparator();
        dashboardsMenu.add(TableauItem);
        menuBar.add(dashboardsMenu);

        setJMenuBar(menuBar);
    }

    // fonction pour ouvrir la fenêtre qui va afficher les infos globales
    private void openIncidentDashboard() {
        SwingUtilities.invokeLater(() -> {
            try {
                new IncidentDashboardGlobal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainDashboard().setVisible(true);
        });
    }
}