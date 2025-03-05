package edu.ezip.ing1.pds.services.Citoyen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class CreationTicketIncident extends JFrame {

    private JTextField nomField, prenomField, telField, emailField, cpField, titreField;
    private JTextArea descriptionArea;
    private JComboBox<String> prioriteBox;
    private String categorie;
    private Date date_sql;

    public CreationTicketIncident() {
        setTitle("Sélection Catégorie");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        String[] categories = {"Voirie", "Éclairage public", "Espaces verts", "Propreté", "Animaux errants ou retrouvés mort", "Autres"};

        for (String category : categories) {
            JButton button = new JButton(category);
            button.addActionListener(e -> {
                this.categorie = category;
                initFormulaireIncident();
            });
            add(button);
        }

        setVisible(true);
    }

    private void initFormulaireIncident() {
        getContentPane().removeAll();
        setTitle("Déclaration d'Incident");
        setSize(500, 600);
        setLayout(new GridLayout(11, 2));

        add(new JLabel("Catégorie: "));
        add(new JLabel(categorie));

        date_sql = new Date(System.currentTimeMillis());

        add(new JLabel("Date: "));
        add(new JLabel(date_sql.toString()));

        add(new JLabel("Nom: "));
        nomField = new JTextField();
        add(nomField);

        add(new JLabel("Prénom: "));
        prenomField = new JTextField();
        add(prenomField);

        add(new JLabel("Numéro de téléphone: "));
        telField = new JTextField();
        add(telField);

        add(new JLabel("Email: "));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Code Postal: "));
        cpField = new JTextField();
        add(cpField);

        add(new JLabel("Titre: "));
        titreField = new JTextField();
        add(titreField);

        add(new JLabel("Description: "));
        descriptionArea = new JTextArea();
        add(descriptionArea);

        add(new JLabel("Priorité: "));
        prioriteBox = new JComboBox<>(new String[]{"", "Faible", "Moyen", "Élevée"});
        add(prioriteBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        JButton sendButton = new JButton("Envoyer");
        sendButton.addActionListener(new EnvoieFormulaireIncident());
        buttonPanel.add(sendButton);

        JButton backButton = new JButton("Retour");
        backButton.addActionListener(e -> {
            this.dispose();
            new CreationTicketIncident().setVisible(true);
        });
        buttonPanel.add(backButton);

        add(buttonPanel);
    }

    private class EnvoieFormulaireIncident implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isValidForm()) {
                JOptionPane.showMessageDialog(CreationTicketIncident.this, "Veuillez remplir correctement tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String tel = telField.getText();
            String email = emailField.getText();
            String cp = cpField.getText();
            String titre = titreField.getText();
            String description = descriptionArea.getText();
            int priorite = getPrioriteIndex(prioriteBox.getSelectedItem().toString());

        }
    }

    private boolean isValidForm() {
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() || telField.getText().isEmpty() ||
                emailField.getText().isEmpty() || cpField.getText().isEmpty() || titreField.getText().isEmpty() ||
                descriptionArea.getText().isEmpty()) {
            return false;
        }

        if (!telField.getText().matches("0\\d{9}")) {
            return false;
        }

        if (!cpField.getText().matches("\\d{5}")) {
            return false;
        }

        return true;
    }

    private int getPrioriteIndex(String priorite) {
        switch (priorite) {
            case "Faible": return 1;
            case "Moyen": return 2;
            case "Élevée": return 3;
            default: return 0;
        }
    }
}
