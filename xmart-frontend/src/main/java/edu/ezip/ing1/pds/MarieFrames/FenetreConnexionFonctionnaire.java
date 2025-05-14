package edu.ezip.ing1.pds;
import edu.ezip.ing1.pds.business.dto.Fonctionnaire;
import edu.ezip.ing1.pds.services.Mairie.FonctionnaireService;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.*;
import java.awt.*;

public class FenetreConnexionFonctionnaire extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(FenetreConnexionFonctionnaire.class);
    private final static String networkConfigFile = "network.yaml";

    private NetworkConfig networkConfig;
    private JTextField emailField, codePostalField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JButton registerButton;

    public FenetreConnexionFonctionnaire() {
        setTitle("Connexion Fonctionnaire");
        setLayout(new GridLayout(4, 2, 10, 10));
        setSize(650, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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

        // Bouton de connexion
        submitButton = new JButton("Se connecter");
        submitButton.addActionListener(e -> authentifierFonctionnaire());
        add(submitButton);
        
        registerButton = new JButton("Creer un compte");
        registerButton.addActionListener(e -> new FenetreCreationCompteFonctionnaire());
        add(registerButton);

        setVisible(true);
    }

    private void authentifierFonctionnaire() {
        String email = emailField.getText();
        String motDePasse = new String(passwordField.getPassword());
        String codePostal = codePostalField.getText();

        // controles de validation 
        if (email.isEmpty() || motDePasse.isEmpty() || codePostal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Création du fonctionnaire pour l'authentification
        Fonctionnaire fonctionnaire = new Fonctionnaire(email, motDePasse, codePostal);

        FonctionnaireService fonctionnaireService = new FonctionnaireService(networkConfig);
        boolean result = fonctionnaireService.authenticateFonctionnaire(fonctionnaire);

        if (result) {
            JOptionPane.showMessageDialog(this, "Connexion réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            // Fermer la fenêtre d'auth et rediriger vers la page d'accueil
            this.dispose();
            new MairieGUI();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la connexion. Email ou mot de passe incorrect.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreConnexionFonctionnaire::new);
    }
}
