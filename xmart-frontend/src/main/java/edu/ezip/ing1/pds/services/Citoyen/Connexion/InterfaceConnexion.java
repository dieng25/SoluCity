package edu.ezip.ing1.pds.services.Citoyen.Connexion;

import edu.ezip.ing1.pds.MainFrameCitoyen;

import javax.swing.*;
import java.awt.*;

public class InterfaceConnexion extends JFrame {

    private JTextField identifiantField, telephoneField;

    public InterfaceConnexion() {
        setTitle("Connexion");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Numéro de téléphone: "));
        telephoneField = new JTextField();
        formPanel.add(telephoneField);

        formPanel.add(new JLabel("Identifiant: "));
        identifiantField = new JTextField();
        formPanel.add(identifiantField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton connexionButton = new JButton("Connexion");
        connexionButton.setPreferredSize(null);
        buttonPanel.add(connexionButton);

        JButton backButton = new JButton("Retour");
        backButton.setPreferredSize(null);
        backButton.addActionListener(e -> {
            this.dispose();
            new MainFrameCitoyen();
        });
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public String getTelephone() { return telephoneField.getText(); }
    public String getIdentifiant() { return identifiantField.getText(); }

}
