package edu.ezip.ing1.pds;

import edu.ezip.ing1.pds.services.Citoyen.Connexion.InterfaceConnexion;
import edu.ezip.ing1.pds.services.Citoyen.Incident.CategorieIncident;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrameCitoyen {
    public MainFrameCitoyen() {

        //public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Accueil");
            mainFrame.setSize(400, 300);
            mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setLayout(new BorderLayout());

            JLabel titleLabel = new JLabel("Accueil", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            mainFrame.add(titleLabel, BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

            JButton incidentButton = new JButton("Déclarer un Incident");
            incidentButton.setFont(new Font("Arial", Font.PLAIN, 14));
            incidentButton.addActionListener(e -> {
                int response = JOptionPane.showConfirmDialog(mainFrame,
                        "Est-ce la première fois que vous déclarez un incident ou faites une suggestion ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(mainFrame, "Veuillez vous connecter pour déclarer un incident.");
                } else {
                    new CategorieIncident();
                    mainFrame.dispose();
                }
            });

            JButton suggestionButton = new JButton("Faire une Suggestion");
            suggestionButton.setFont(new Font("Arial", Font.PLAIN, 14));
            suggestionButton.addActionListener(e -> {
                int response = JOptionPane.showConfirmDialog(mainFrame,
                        "Est-ce la première fois que vous faites une suggestion ou déclarez un incident ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(mainFrame, "Veuillez vous connecter pour faire une suggestion.");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Fonctionnalité Suggestion à implémenter.");
                }
            });

            JButton connexionButton = new JButton("Connexion");
            connexionButton.setFont(new Font("Arial", Font.PLAIN, 14));
            connexionButton.addActionListener(e -> {
                new InterfaceConnexion();
                mainFrame.dispose();
            });

            buttonPanel.add(incidentButton);
            buttonPanel.add(suggestionButton);
            buttonPanel.add(connexionButton);

            mainFrame.add(buttonPanel, BorderLayout.CENTER);

            mainFrame.setVisible(true);
        });
    }
}





