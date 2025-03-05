package edu.ezip.ing1.pds.services.Citoyen.Incident;

import javax.swing.*;
import java.awt.*;

public class CategorieIncident extends JFrame {

    public CategorieIncident() {
        setTitle("Sélection Catégorie");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        String[] categories = {"Voirie", "Éclairage public", "Espaces verts", "Propreté", "Animaux errants ou retrouvés mort", "Autres"};

        for (String category : categories) {
            JButton button = new JButton(category);
            button.addActionListener(e -> {
                new FormulaireIncident(category);
                dispose();
            });
            add(button);
        }

        setVisible(true);
    }


}
