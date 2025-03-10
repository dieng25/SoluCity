package edu.ezip.ing1.pds;
import javax.swing.*;
/*import edu.ezip.ing1.pds.business.server.MairieServices;*/
import edu.ezip.ing1.pds.commons.Request;

import java.awt.*;

public class MairieGUI {
    private JFrame frame;
    private JButton incidentsButton;
    private JButton suggestionsButton;
    private JButton statistiqueButton;
    private JButton trierButton;
    private JTextArea resultArea;

    public MairieGUI() {
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

        //Actions des boutons
        incidentsButton.addActionListener(e -> showData("incidents"));
        suggestionsButton.addActionListener(e -> showData("suggestions"));
        statistiqueButton.addActionListener(e -> showData("Statistiques et prédictions"));
        trierButton.addActionListener(e -> showData("trier par statut"));

        frame.setVisible(true);
    }

    private void showData(String type) {

        // MairieServices mairieServices = MairieServices.getInstance();
        // Ici vous devez préparer une Request et une Connection (par exemple via JDBC)
        Request request = new Request();
        // Par exemple, on définit le type de requête en fonction du bouton cliqué
        switch (type) {
            case "incidents":
                request.setRequestOrder("SELECT_ALL_INCIDENTS");
                break;
            case "suggestions":
                request.setRequestOrder("SELECT_ALL_SUGGESTIONS");
                break;
            // Autres cas...
            default:
                break;
        }
        //affichage dans la zone de résultats
        // result
        // resultArea.setText(result.toString());
    }

    public static void main(String[] args) {
        User.initMainFrame();
    }
}
