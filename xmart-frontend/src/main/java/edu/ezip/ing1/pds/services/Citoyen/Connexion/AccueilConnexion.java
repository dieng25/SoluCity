package edu.ezip.ing1.pds.services.Citoyen.Connexion;

import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.services.Citoyen.Connexion.ConsulteIncident.RecupIncident;
import edu.ezip.ing1.pds.services.Citoyen.Connexion.IncidentConnect.CategorieIncidentConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AccueilConnexion extends JFrame {

    public AccueilConnexion(Citoyen citoyen) {
        setTitle("Accueil de connexion");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JLabel mssgLabel = new JLabel("Bonjour " + citoyen.getPrenom() + " " + citoyen.getNom() + " !", SwingConstants.CENTER);
        mssgLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mssgLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(mssgLabel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton incidentButton = new JButton("Déclarer un Incident");
        incidentButton.setFont(new Font("Arial", Font.PLAIN, 12));
        incidentButton.setPreferredSize(null);
        incidentButton.addActionListener(e -> new CategorieIncidentConnect(citoyen));
        buttonPanel.add(incidentButton);

        JButton suggestionButton = new JButton("Faire une Suggestion");
        suggestionButton.setFont(new Font("Arial", Font.PLAIN, 12));
        suggestionButton.setPreferredSize(null);
        suggestionButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Fonctionnalité Suggestion à implémenter.");
        });
        buttonPanel.add(suggestionButton);

        JButton consulterTicketButton = new JButton("Consulter mes Tickets");
        consulterTicketButton.setFont(new Font("Arial", Font.PLAIN, 12));
        consulterTicketButton.setPreferredSize(null);

        consulterTicketButton.addActionListener(e -> {
            Object[] options = {"Incident", "Suggestion"};
            int choice = JOptionPane.showOptionDialog(
                    AccueilConnexion.this,
                    "Quel type de ticket souhaitez-vous consulter ?",
                    "Consulter Tickets",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    null
            );

            if (choice == 0) {
                dispose();
                RecupIncident recupIncident = new RecupIncident(citoyen);
                recupIncident.actionPerformed(new ActionEvent(consulterTicketButton, ActionEvent.ACTION_PERFORMED, null));

            } else if (choice == 1) {
                JOptionPane.showMessageDialog(this, "Fonctionnalité Suggestion à implémenter.");
            }

        });

        buttonPanel.add(consulterTicketButton);

        add(buttonPanel, BorderLayout.CENTER);

        JPanel deconnexionPanel = new JPanel();
        deconnexionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); 
        deconnexionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton deconnexionButton = new JButton("Déconnexion");
        deconnexionButton.setFont(new Font("Arial", Font.PLAIN, 12));
        deconnexionButton.setPreferredSize(null);
        deconnexionPanel.add(deconnexionButton);
        deconnexionButton.addActionListener(e -> {
            dispose();
            new InterfaceConnexion();
        });

        add(deconnexionPanel, BorderLayout.SOUTH);



        setVisible(true);
    }
}
