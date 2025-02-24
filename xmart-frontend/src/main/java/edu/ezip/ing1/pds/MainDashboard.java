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

        JMenu generalMenu = new JMenu("General");
        JMenuItem afficherItem = new JMenuItem("Afficher");
        afficherItem.addActionListener(e -> openIncidentDashboard());
        generalMenu.add(afficherItem);

        JMenu trierParMenu = new JMenu("Trier Par");
        JMenuItem villeItem = new JMenuItem("Ville");
        JMenuItem departementItem = new JMenuItem("Département");
        trierParMenu.add(villeItem);
        trierParMenu.add(departementItem);

        dashboardsMenu.add(generalMenu);
        dashboardsMenu.add(trierParMenu);
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