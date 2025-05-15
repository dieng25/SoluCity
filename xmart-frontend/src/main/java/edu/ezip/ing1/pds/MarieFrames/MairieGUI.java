package edu.ezip.ing1.pds.MarieFrames;

import javax.swing.*;

import edu.ezip.ing1.pds.DashboardFenetre.ConnexionUtilisateur;
import edu.ezip.ing1.pds.DashboardFenetre.MainDashboard;

import java.awt.*;

public class MairieGUI {
    private JFrame frame;
    private JButton incidentsButton;
    private JButton suggestionsButton;
    private JButton statistiqueButton;
    private JButton trierButton;

    public MairieGUI() {
        // fenêtre principale App Mairie
        frame = new JFrame("Dashboard Mairie");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        incidentsButton      = new JButton("Afficher Incidents");
        suggestionsButton    = new JButton("Afficher Suggestions");
        statistiqueButton    = new JButton("Statistiques et Prédictions");
        trierButton          = new JButton("Trier par Statut/Priorité");

        // Configuration uniforme des boutons
        for (JButton btn : new JButton[]{incidentsButton, suggestionsButton, statistiqueButton, trierButton}) {
            btn.setBackground(new Color(0, 123, 255));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setFocusPainted(false);
        }

        gbc.gridx = 0; gbc.gridy = 0;
        frame.add(incidentsButton, gbc);
        gbc.gridx = 1;
        frame.add(suggestionsButton, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        frame.add(statistiqueButton, gbc);
        gbc.gridx = 1;
        frame.add(trierButton, gbc);

        // Actions des boutons
        incidentsButton.addActionListener(e ->
            new FenetreIncident()  // affiche tout
        );

        suggestionsButton.addActionListener(e ->
            new FenetreSuggestion()
        );

        statistiqueButton.addActionListener(e ->
            new MainDashboard().setVisible(true)
            // new ConnexionUtilisateur()
        );

        trierButton.addActionListener(e -> {
            // Boîte de dialogue pour choisir “statut” ou “priorité”
            String[] options = {"Statut", "Priorité"};
            String choix = (String) JOptionPane.showInputDialog(
                frame,
                "Afficher uniquement par :",
                "Filtrer les incidents",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            if (choix != null) {
                if (choix.equals("Statut")) {
                    // filtre par statut
                    new FenetreIncident("statut");
                } else {
                    // filtre par priorité
                    new FenetreIncident("priorite");
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MairieGUI::new);
    }
}
