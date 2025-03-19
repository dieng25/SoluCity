package edu.ezip.ing1.pds.services.Citoyen.Connexion;

import edu.ezip.ing1.pds.business.dto.Citoyen;

import javax.swing.*;
import java.awt.*;

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
        buttonPanel.add(incidentButton);

        JButton suggestionButton = new JButton("Faire une Suggestion");
        suggestionButton.setFont(new Font("Arial", Font.PLAIN, 12));
        suggestionButton.setPreferredSize(null);
        buttonPanel.add(suggestionButton);

        JButton consulterTicketButton = new JButton("Consulter mes Tickets");
        consulterTicketButton.setFont(new Font("Arial", Font.PLAIN, 12));
        consulterTicketButton.setPreferredSize(null);
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
