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


        JLabel welcomeLabel = new JLabel("Bienvenue!", JLabel.CENTER);
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
        StatsIncidents.addActionListener(e -> openIncidentStats());
        JButton StatsSuggestions = new JButton("Stats Suggestions");
        StatsSuggestions.addActionListener(e -> openSuggestionStats());
        JButton StatsMairies = new JButton("Stats Mairies");
        StatsMairies.addActionListener(e -> openMairieStats());
        
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
    // fonction pour ouvrir la fenêtre qui va afficher les stats incidents
    private void openIncidentStats() {
        SwingUtilities.invokeLater(() -> {
            try {
                try {
                    new IncidentIHM();
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

    // fonction pour ouvrir la fenêtre qui va afficher les stats suggestions
    private void openSuggestionStats() {
        SwingUtilities.invokeLater(() -> {
            try {
                try {
                    new SuggestionIHM();
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

    // fonction pour ouvrir la fenêtre qui va afficher les stats Mairies
    private void openMairieStats() {
        SwingUtilities.invokeLater(() -> {
            try {
                try {
                    new MairieIHM();
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