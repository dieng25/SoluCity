package edu.ezip.ing1.pds.MarieFrames;

import edu.ezip.ing1.pds.business.dto.Fonctionnaire;
import edu.ezip.ing1.pds.services.Mairie.FonctionnaireService;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.*;
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Configuration réseau
        try {
            networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
            logger.debug("Chargement du fichier de configuration réseau : {}", networkConfig.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion au serveur.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Contenu principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Connexion Fonctionnaire");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainPanel.add(title);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(
                new TitledBorder(new LineBorder(Color.GRAY), "Identifiants", TitledBorder.LEFT, TitledBorder.TOP));

        formPanel.add(new JLabel("Email :"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Code Postal :"));
        codePostalField = new JTextField();
        formPanel.add(codePostalField);

        mainPanel.add(formPanel);

        // Espace pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        registerButton = new JButton("Créer un compte");
        registerButton.setBackground(Color.LIGHT_GRAY);
        registerButton.setForeground(Color.BLACK);
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerButton.addActionListener(e -> {
        this.dispose();
        new FenetreCreationCompteFonctionnaire();
    });
        buttonPanel.add(registerButton);

        submitButton = new JButton("Se connecter");
        submitButton.setBackground(new Color(25, 118, 210)); 
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setPreferredSize(new Dimension(150, 40));
        submitButton.addActionListener(e -> authentifierFonctionnaire());
        buttonPanel.add(submitButton);

        mainPanel.add(buttonPanel);

        add(mainPanel);

        setVisible(true);
    }

    private void authentifierFonctionnaire() {
        String email = emailField.getText();
        String motDePasse = new String(passwordField.getPassword());
        String codePostal = codePostalField.getText();

        if (email.isEmpty() || motDePasse.isEmpty() || codePostal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Fonctionnaire fonctionnaire = new Fonctionnaire(email, motDePasse, codePostal);
        FonctionnaireService fonctionnaireService = new FonctionnaireService(networkConfig);
        boolean result = fonctionnaireService.authenticateFonctionnaire(fonctionnaire);

        if (result) {
            JOptionPane.showMessageDialog(this, "Connexion réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
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
