package edu.ezip.ing1.pds.services.Citoyen.Connexion.ConsulteIncident;

import edu.ezip.ing1.pds.business.dto.Incident;

import javax.swing.*;
import java.awt.*;

public class ShowIncidentDetails extends JFrame {

    public ShowIncidentDetails(Incident incident) {

        setTitle("Détails de l'Incident");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel prioriteLabel = new JLabel("Priorité: " + getPrioriteDescription(incident.getPriorite()));
        prioriteLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(prioriteLabel, BorderLayout.NORTH);

        JPanel descriptionPanel = new JPanel(new BorderLayout());
        JLabel descriptionLabel = new JLabel("Description :");
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);

        JTextArea descriptionArea = new JTextArea(incident.getDescription());
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        descriptionPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(descriptionPanel, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);



    }

    private String getPrioriteDescription(int priorite) {
        switch (priorite) {
            case 0:
                return "Pas de priorité définie";
            case 1:
                return "Faible";
            case 2:
                return "Moyen";
            case 3:
                return "Élevée";
            default:
                return "Inconnue";
        }
    }
}
