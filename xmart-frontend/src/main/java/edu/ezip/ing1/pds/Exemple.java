package edu.ezip.ing1.pds;

import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.SelectIncidentClientRequest;
import edu.ezip.ing1.pds.services.Mairie.IncidentTableFrame;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Exemple {
    private JFrame frame;
    private JButton incidentsButton;
    private JButton suggestionsButton;
    private JButton statistiqueButton;
    private JButton trierButton;
    private JTextArea resultArea;

    private NetworkConfig networkConfig; // 🔹 Ajout
    private int clientId; // 🔹 Ajout

    public Exemple(NetworkConfig networkConfig, int clientId) { // 🔹 Ajout des paramètres
        this.networkConfig = networkConfig;
        this.clientId = clientId;

        // Création et configuration de la fenêtre principale
        frame = new JFrame("Dashboard Mairie");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(Color.WHITE);

        // Création du menu
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 123, 255));
        JMenu menu = new JMenu("Options");
        menu.setForeground(Color.WHITE);
        JMenuItem profileItem = new JMenuItem("Profil");
        JMenuItem settingItem = new JMenuItem("Paramètre");
        JMenuItem logoutItem = new JMenuItem("Se déconnecter");
        JMenuItem exitItem = new JMenuItem("Quitter");
        exitItem.addActionListener(e -> System.exit(0));
        menu.add(profileItem);
        menu.add(settingItem);
        menu.add(logoutItem);
        menu.addSeparator();
        menu.add(exitItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        // Panel central pour les boutons avec GridBagLayout
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        incidentsButton = new JButton("Afficher Incidents");
        suggestionsButton = new JButton("Afficher Suggestions");
        statistiqueButton = new JButton("Statistiques et Prédictions");
        trierButton = new JButton("Trier par Statut");

        // Configuration uniforme des boutons
        JButton[] buttons = {incidentsButton, suggestionsButton, statistiqueButton, trierButton};
        for (JButton btn : buttons) {
            btn.setBackground(new Color(0, 123, 255));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setFocusPainted(false);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(incidentsButton, gbc);

        gbc.gridx = 1;
        centerPanel.add(suggestionsButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(statistiqueButton, gbc);

        gbc.gridx = 1;
        centerPanel.add(trierButton, gbc);

        // Zone d'affichage des résultats
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Résultats"));
        scrollPane.setPreferredSize(new Dimension(680, 200));

        // Ajouter les panels dans la fenêtre principale
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Actions des boutons
        incidentsButton.addActionListener(e -> afficherIncidents()); // 🔹 Mise à jour

        frame.setVisible(true);
    }

    // Nouvelle méthode pour afficher IncidentTableFrame
    private void afficherIncidents() {
        SwingUtilities.invokeLater(() -> {
            new IncidentTableFrame(networkConfig, clientId).setVisible(true);
        });
    }
    public static void main(String[] args) {
        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setIpaddress("127.0.0.1");
        networkConfig.setTcpport(45065);
    
        int clientId = 1;
        SwingUtilities.invokeLater(() -> new Exemple(networkConfig, clientId));
    }
    
}
