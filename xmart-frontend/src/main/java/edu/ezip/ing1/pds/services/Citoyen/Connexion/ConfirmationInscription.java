package edu.ezip.ing1.pds.services.Citoyen.Connexion;

import edu.ezip.ing1.pds.MainFrameCitoyen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmationInscription extends JFrame {
    public ConfirmationInscription(String identifiant) {
        setTitle("Inscription Réussie");
        setSize(620, 400);
        setLayout(new BorderLayout());

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new GridLayout(5, 1, 10, 10));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel messageLabel1 = new JLabel("Votre compte a été créé avec succès !", SwingConstants.CENTER);
        JLabel messageLabel2 = new JLabel("Votre identifiant : " + identifiant, SwingConstants.CENTER);
        JLabel messageLabel3 = new JLabel("Identifiant à conserver", SwingConstants.CENTER);
        JLabel messageLabel4 = new JLabel("Vous pouvez maintenant vous connecter avec votre numéro de téléphone et votre identifiant.", SwingConstants.CENTER);

        messagePanel.add(messageLabel1);
        messagePanel.add(messageLabel2);
        messagePanel.add(messageLabel3);
        messagePanel.add(messageLabel4);

        add(messagePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton retourButton = new JButton("Retour à l'accueil");
        retourButton.setFont(new Font("Arial", Font.PLAIN, 14));
        retourButton.setPreferredSize(null);
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new MainFrameCitoyen());


            }
        });

        buttonPanel.add(retourButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}