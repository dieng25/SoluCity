package edu.ezip.ing1.pds.services.Citoyen.Incident;


import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class FormulaireIncident extends JFrame {

    private JTextField nomField, prenomField, telField, emailField, cpField, titreField;
    private JTextArea descriptionArea;
    private JComboBox<String> prioriteBox;
    private String categorie;
    private Date date_sql;

    public FormulaireIncident(String categorie) {
        getContentPane().removeAll();
        this.categorie = categorie;
        setTitle("Déclaration d'Incident");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 2));

        add(new JLabel("Catégorie: "));
        add(new JLabel(categorie));

        java.util.Date currentDate = new java.util.Date();
        date_sql = new Date(currentDate.getTime());

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
        sendButton.addActionListener(new EnvoieFormIncident(this));
        buttonPanel.add(sendButton);

        JButton backButton = new JButton("Retour");
        backButton.addActionListener(e -> {
            this.dispose();
            new CategorieIncident().setVisible(true);
        });
        buttonPanel.add(backButton);

        add(buttonPanel);

        setVisible(true);
    }

    public String getCategorie() { return categorie; }
    public String getNom() { return nomField.getText(); }
    public String getPrenom() { return prenomField.getText(); }
    public String getTel() { return telField.getText(); }
    public String getEmail() { return emailField.getText(); }
    public String getCodePostal() { return cpField.getText(); }
    public String getTitre() { return titreField.getText(); }
    public String getDescription() { return descriptionArea.getText(); }
    public Date getDate() { return date_sql; }
    public String getPriorite() { return (String) prioriteBox.getSelectedItem(); }
}
