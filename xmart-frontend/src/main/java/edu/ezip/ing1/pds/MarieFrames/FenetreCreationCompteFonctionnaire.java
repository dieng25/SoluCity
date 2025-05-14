package edu.ezip.ing1.pds.MarieFrames;

import edu.ezip.ing1.pds.business.dto.Fonctionnaire;
import edu.ezip.ing1.pds.services.Mairie.FonctionnaireService;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class FenetreCreationCompteFonctionnaire extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(FenetreCreationCompteFonctionnaire.class);
    private final static String networkConfigFile = "network.yaml";

    private NetworkConfig networkConfig;
    private JTextField emailField, codePostalField;
    private JPasswordField passwordField;
    private JButton submitButton;

    public FenetreCreationCompteFonctionnaire() {
        setTitle("Inscription Fonctionnaire");
        setLayout(new GridLayout(4, 2, 10, 10));
        setSize(700, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // setLocationRelativeTo(null);

        // Charger la configuration réseau
        try {
            networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
            logger.debug("Chargement du fichier de configuration réseau : {}", networkConfig.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion au serveur.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Champs du formulaire
        add(new JLabel("Email :"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Code Postal :"));
        codePostalField = new JTextField();
        add(codePostalField);

        // Bouton d'inscription
        submitButton = new JButton("S'inscrire");
        submitButton.addActionListener(e -> inscrireFonctionnaire());
        add(submitButton);

        setVisible(true);
    }

    private void inscrireFonctionnaire() {
        String email = emailField.getText();
        String motDePasse = new String(passwordField.getPassword());
        String codePostal = codePostalField.getText();

        // controle de validation
        if (email.isEmpty() || motDePasse.isEmpty() || codePostal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Fonctionnaire fonctionnaire = new Fonctionnaire(email, motDePasse, codePostal);

        FonctionnaireService fonctionnaireService = new FonctionnaireService(networkConfig);
        boolean isCorrect = fonctionnaireService.registerFonctionnaire(fonctionnaire);

        if (isCorrect) {
            JOptionPane.showMessageDialog(this, "Inscription réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            // Fermer la fenêtre d'inscription et reddirection vers la page de d'authentification
            this.dispose();
            new FenetreConnexionFonctionnaire();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreCreationCompteFonctionnaire::new);
    }
}
