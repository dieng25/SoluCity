package edu.ezip.ing1.pds.services.Citoyen.Incident;

import javax.swing.*;
import java.awt.*;

public class CategorieIncident extends JFrame {

    public CategorieIncident() {
        setTitle("Sélection Catégorie");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Veuillez sélectionner une catégorie", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] categories = {"Voirie", "Éclairage public", "Espaces verts", "Propreté", "Animaux errants ou retrouvés mort", "Autres"};

        for (String category : categories) {
            JButton button = new JButton(category);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.setPreferredSize(null);
            panel.add(button);
            button.addActionListener(e -> {
                new FormulaireIncident(category);
                dispose();
            });

        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }


}
