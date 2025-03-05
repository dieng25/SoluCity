package edu.ezip.ing1.pds.services.Citoyen.Incident;


import edu.ezip.ing1.pds.business.dto.Mairie;
import edu.ezip.ing1.pds.business.dto.Mairies;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Citoyen.MairieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Date;

public class FormulaireIncident extends JFrame {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private JTextField nomField, prenomField, telField, emailField, titreField;
    private JTextArea descriptionArea;
    private JComboBox<String> cpField, prioriteBox;
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
        setUppercaseKeyListener(nomField);

        add(new JLabel("Prénom: "));
        prenomField = new JTextField();
        add(prenomField);
        setUppercaseKeyListener(prenomField);

        add(new JLabel("Numéro de téléphone: "));
        telField = new JTextField();
        add(telField);

        add(new JLabel("Email: "));
        emailField = new JTextField();
        add(emailField);

        try {
            final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
            logger.debug("Load Network config file : {}", networkConfig.toString());

            final MairieService mairieService = new MairieService(networkConfig);
            Mairies mairies = mairieService.selectMairies();

            add(new JLabel("Code Postal: "));
            cpField = new JComboBox<>();
            for (Mairie mairie : mairies.getMairies()) {
                cpField.addItem(mairie.getCodePostal());
            }
            cpField.setSelectedIndex(0);
            cpField.setEditable(true);
            add(cpField);

        } catch (IOException | InterruptedException e) {
            logger.error("Erreur lors du chargement des codes postaux", e);
            JOptionPane.showMessageDialog(this, "Erreur de connexion au serveur. Veuillez réessayer plus tard.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        add(new JLabel("Titre: "));
        titreField = new JTextField();
        add(titreField);
        setUppercaseKeyListener(titreField);

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

    private void setUppercaseKeyListener(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                JTextField source = (JTextField) e.getSource();
                source.setText(source.getText().toUpperCase());
            }
        });
    }

    public String getCategorie() { return categorie; }
    public String getNom() { return nomField.getText(); }
    public String getPrenom() { return prenomField.getText(); }
    public String getTel() { return telField.getText(); }
    public String getEmail() { return emailField.getText(); }
    public String getCodePostal() { return (String) cpField.getSelectedItem(); }
    public String getTitre() { return titreField.getText(); }
    public String getDescription() { return descriptionArea.getText(); }
    public Date getDate() { return date_sql; }
    public String getPriorite() { return (String) prioriteBox.getSelectedItem(); }
}
