package edu.ezip.ing1.pds.services.Citoyen.Incident;


import edu.ezip.ing1.pds.MainFrameCitoyen;
import edu.ezip.ing1.pds.business.dto.CategorieIncident;
import edu.ezip.ing1.pds.business.dto.CategorieIncidents;
import edu.ezip.ing1.pds.business.dto.Mairie;
import edu.ezip.ing1.pds.business.dto.Mairies;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Citoyen.CategorieIncidentService;
import edu.ezip.ing1.pds.services.Citoyen.ConfirmeExit;
import edu.ezip.ing1.pds.services.Citoyen.LimiteCaractere;
import edu.ezip.ing1.pds.services.Citoyen.MairieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Date;

public class FormulaireIncident extends ConfirmeExit {

    private final static String LoggingLabel = "FrontEnd - FormulaireIncident";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private JTextField nomField, prenomField, telField, emailField, titreField;
    private JTextArea descriptionArea;
    private JComboBox<String> categorieI, cpField, prioriteBox;
    private String categorie;
    private Date date_sql;

    public FormulaireIncident() {
        super();
        getContentPane().removeAll();
        setTitle("Déclaration d'Incident");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        NetworkConfig networkConfig = null;
        try {
            networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
            logger.debug("Load Network config file : {}", networkConfig.toString());
        }catch (Exception e) {
            logger.error("erreur de connexion au serveur", e);
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion au serveur. Veuillez réessayer plus tard.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        try{
            final CategorieIncidentService categorieIncidentService = new CategorieIncidentService(networkConfig);
            CategorieIncidents categorieIncidents = categorieIncidentService.selectCategorieIncidents();

            formPanel.add(new JLabel("Catégorie : "));
            categorieI = new JComboBox<>();
            for (CategorieIncident categorieIncident : categorieIncidents.getCategorieIncidents()) {
                categorieI.addItem(categorieIncident.getCategorieIncident());
            }
            formPanel.add(categorieI);

        } catch (IOException | InterruptedException e) {
            logger.error("Erreur lors du chargement des catégorie incident", e);
        }


        java.util.Date currentDate = new java.util.Date();
        date_sql = new Date(currentDate.getTime());

        formPanel.add(new JLabel("Date: "));
        formPanel.add(new JLabel(date_sql.toString()));

        formPanel.add(new JLabel("Nom: "));
        nomField = new JTextField();
        formPanel.add(nomField);
        nomField.setDocument(new LimiteCaractere(50));
        setUppercaseKeyListener(nomField);

        formPanel.add(new JLabel("Prénom: "));
        prenomField = new JTextField();
        formPanel.add(prenomField);
        prenomField.setDocument(new LimiteCaractere(50));
        setUppercaseKeyListener(prenomField);

        formPanel.add(new JLabel("Numéro de téléphone: "));
        telField = new JTextField();
        formPanel.add(telField);

        formPanel.add(new JLabel("Email: "));
        emailField = new JTextField();
        formPanel.add(emailField);


        try{
            final MairieService mairieService = new MairieService(networkConfig);
            Mairies mairies = mairieService.selectMairies();

            formPanel.add(new JLabel("Code Postal: "));
            cpField = new JComboBox<>();
            for (Mairie mairie : mairies.getMairies()) {
                cpField.addItem(mairie.getCodePostal());
            }
            formPanel.add(cpField);

        } catch (IOException | InterruptedException e) {
            logger.error("Erreur lors du chargement des codes postaux", e);
        }

        formPanel.add(new JLabel("Titre: "));
        titreField = new JTextField();
        formPanel.add(titreField);
        titreField.setDocument(new LimiteCaractere(50));
        setUppercaseKeyListener(titreField);

        formPanel.add(new JLabel("Priorité: "));
        prioriteBox = new JComboBox<>(new String[]{"", "Faible", "Moyen", "Élevée"});
        formPanel.add(prioriteBox);

        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        descriptionPanel.add(new JLabel("Description: "), BorderLayout.NORTH);
        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setDocument(new LimiteCaractere(20000));
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionPanel.add(descriptionScrollPane, BorderLayout.CENTER);

        add(formPanel, BorderLayout.NORTH);
        add(descriptionPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton sendButton = new JButton("Envoyer");
        sendButton.setPreferredSize(null);
        sendButton.addActionListener(new EnvoieFormIncident(this));
        buttonPanel.add(sendButton);

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

    private void setUppercaseKeyListener(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                JTextField source = (JTextField) e.getSource();
                source.setText(source.getText().toUpperCase());
            }
        });
    }

    public String getCategorie() { return (String) categorieI.getSelectedItem(); }
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
