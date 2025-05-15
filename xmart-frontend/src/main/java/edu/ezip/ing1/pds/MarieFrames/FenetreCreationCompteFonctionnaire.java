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

public class FenetreCreationCompteFonctionnaire extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(FenetreCreationCompteFonctionnaire.class);
    private final static String networkConfigFile = "network.yaml";

    private NetworkConfig networkConfig;
    private JTextField emailField, codePostalField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JButton connecButton;

    public FenetreCreationCompteFonctionnaire() {
        setTitle("Inscription Fonctionnaire");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // setLocationRelativeTo(null);

        try {
            networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
            logger.debug("Chargement du fichier de configuration réseau : {}", networkConfig.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion au serveur.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // === Composants graphiques ===
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(25, 35, 25, 35));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Créer un compte fonctionnaire");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainPanel.add(title);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 12, 12));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Informations personnelles",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.PLAIN, 14)));

        formPanel.setOpaque(true);  // transparent pour fond
        emailField = new JTextField();
        passwordField = new JPasswordField();
        codePostalField = new JTextField();

        formPanel.add(new JLabel("Email :"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Mot de passe :"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Code Postal :"));
        formPanel.add(codePostalField);

        mainPanel.add(formPanel);

        submitButton = new JButton("S'inscrire");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setBackground(new Color(25, 118, 210));
        submitButton.setPreferredSize(new Dimension(110, 40));
        submitButton.addActionListener(e -> inscrireFonctionnaire());

        connecButton = new JButton("Se connecter");
        connecButton.setBackground(Color.LIGHT_GRAY);
        connecButton.setForeground(Color.BLACK);
        connecButton.setFocusPainted(false);
        connecButton.setPreferredSize(new Dimension(110, 40));
        connecButton.addActionListener(e -> {
        this.dispose();
        new FenetreConnexionFonctionnaire();
    });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(25, 0, 0, 0));
        buttonPanel.add(submitButton);
        buttonPanel.add(connecButton);

        mainPanel.add(buttonPanel);
        add(mainPanel);
        setVisible(true);
    }

    private void inscrireFonctionnaire() {
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
        boolean isCorrect = fonctionnaireService.registerFonctionnaire(fonctionnaire);

        if (isCorrect) {
            JOptionPane.showMessageDialog(this, "Inscription réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new FenetreConnexionFonctionnaire();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        SwingUtilities.invokeLater(FenetreCreationCompteFonctionnaire::new);
    }
}
