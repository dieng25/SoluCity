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
        setLayout(new GridLayout(0, 1));

        add(new JLabel("Priorité: " + getPrioriteDescription(incident.getPriorite())));
        add(new JLabel("Description: " + incident.getDescription()));

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
