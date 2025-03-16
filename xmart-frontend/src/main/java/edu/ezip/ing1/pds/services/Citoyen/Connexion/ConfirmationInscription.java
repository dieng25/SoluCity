package edu.ezip.ing1.pds.services.Citoyen.Connexion;

import edu.ezip.ing1.pds.MainFrameCitoyen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmationInscription extends JFrame {
    public ConfirmationInscription(String identifiant) {
        setTitle("Inscription Réussie");
        setSize(400, 200);
        setLayout(new GridLayout(4, 1));

        JLabel messageLabel1 = new JLabel("Votre compte a été créé avec succès !", SwingConstants.CENTER);
        JLabel messageLabel2 = new JLabel("Votre identifiant : " + identifiant, SwingConstants.CENTER);
        JLabel messageLabel3 = new JLabel("Vous pouvez maintenant vous connecter avec votre numéro de téléphone.", SwingConstants.CENTER);

        JButton retourButton = new JButton("Retour à l'accueil");
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new MainFrameCitoyen());


            }
        });

        add(messageLabel1);
        add(messageLabel2);
        add(messageLabel3);
        add(retourButton);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}