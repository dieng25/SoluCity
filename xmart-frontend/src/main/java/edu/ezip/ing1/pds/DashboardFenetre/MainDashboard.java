package edu.ezip.ing1.pds.DashboardFenetre;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainDashboard extends JFrame {

    public MainDashboard() {

        setTitle("Tableau de bord dynamique");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(Color.WHITE);
        backgroundPanel.setLayout(new BorderLayout());


        JLabel welcomeLabel = new JLabel("Bienvenue Admin!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(0, 123, 255));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        backgroundPanel.add(welcomeLabel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel();
        //buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(Color.lightGray);
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        JButton VueEnsemble = new JButton("Vue d'ensemble");
        VueEnsemble.addActionListener(e -> openIncidentDashboard());
        JButton StatsIncidents = new JButton("Stats Incidents");
        JButton StatsSuggestions = new JButton("Stats Suggestions");
        JButton StatsMairies = new JButton("Stats Mairies");
        
        JButton[] buttons = {VueEnsemble, StatsIncidents, StatsSuggestions,StatsMairies };
        for (JButton btn : buttons) {
            btn.setBackground(new Color(0, 123, 255));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        buttonPanel.add(VueEnsemble);
        buttonPanel.add(StatsIncidents);
        buttonPanel.add(StatsSuggestions);
        buttonPanel.add(StatsMairies);

        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        add(backgroundPanel, BorderLayout.CENTER);

        //JMenuBar menuBar = new JMenuBar();

        //JMenu dashboardsMenu = new JMenu("Tableaux de bord");

        //JMenuItem General = new JMenuItem("Vue d'ensemble");
        //General.addActionListener(e -> openIncidentDashboard());
        //dashboardsMenu.add(General);

        //JMenuItem IncidentItem = new JMenuItem("Statistiques sur les incidents");
        //JMenuItem SuggestionItem = new JMenuItem("Statistiques sur les suggestions");
        //JMenuItem MairieItem = new JMenuItem("Statistiques par Mairie");
        // TableauItem = new JMenuItem("Tableau de bord spécifique");
        
        //dashboardsMenu.add(General);
        //dashboardsMenu.addSeparator();
        //dashboardsMenu.add(IncidentItem);
        //dashboardsMenu.addSeparator();
        //dashboardsMenu.add(SuggestionItem);
        //dashboardsMenu.addSeparator();
        //dashboardsMenu.add(MairieItem);
        //dashboardsMenu.addSeparator();
        //dashboardsMenu.add(TableauItem);
        //menuBar.add(dashboardsMenu);

        //setJMenuBar(menuBar);
    }

    // fonction pour ouvrir la fenêtre qui va afficher les infos globales
    private void openIncidentDashboard() {
        SwingUtilities.invokeLater(() -> {
            try {
                try {
                    new GlobalIHM();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //setVisible(true);
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