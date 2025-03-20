package edu.ezip.ing1.pds.services.Citoyen.Connexion.IncidentConnect;

import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.services.Citoyen.Connexion.AccueilConnexion;

import javax.swing.*;
import java.awt.*;

public class CategorieIncidentConnect extends JFrame {

    public CategorieIncidentConnect(Citoyen citoyen) {
        setTitle("Sélection Catégorie");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Titre en haut de la fenêtre
        JLabel titleLabel = new JLabel("Veuillez sélectionner une catégorie", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Créer un panneau pour les boutons
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] categories = {"Voirie", "Éclairage public", "Espaces verts", "Propreté", "Animaux errants ou retrouvés morts", "Autres"};

        for (String category : categories) {
            JButton button = new JButton(category);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.setPreferredSize(null);
            panel.add(button);

            button.addActionListener(e -> {
                new FormIncidentConnect(category, citoyen);
                dispose();
            });
        }

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Retour");
        backButton.setPreferredSize(null);
        backButton.addActionListener(e -> {
            this.dispose();
            new AccueilConnexion(citoyen).setVisible(true);
        });
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

    }
}
