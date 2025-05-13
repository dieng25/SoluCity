package edu.ezip.ing1.pds.DashboardFenetre;
import edu.ezip.ing1.pds.business.dto.DashboardDto.AdminDashboard;
import edu.ezip.ing1.pds.services.Dashboard.AdminDashboardService;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class CreationAdmin extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(CreationAdmin.class);
    private final static String networkConfigFile = "network.yaml";

    private NetworkConfig networkConfig;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton submitButton;

    public CreationAdmin() {
        setTitle("Inscription Administrateur");
        setLayout(new GridLayout(4, 2, 10, 10));
        setSize(600, 400);
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
        usernameField = new JTextField();
        add(usernameField);

    usernameField.addKeyListener(new java.awt.event.KeyAdapter() {
    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        String currentText = usernameField.getText();
        int position = usernameField.getCaretPosition(); 
        usernameField.setText(currentText.toLowerCase());
        usernameField.setCaretPosition(Math.min(position, usernameField.getText().length()));
    }
});


        add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule, un chiffre et un caractère spécial"));

        // Bouton d'inscription
        submitButton = new JButton("S'inscrire");
        submitButton.addActionListener(e -> inscrireAdmin());
        submitButton.setBackground(new Color(0, 123, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setFocusPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(submitButton);

        setVisible(true);
    }

    private void inscrireAdmin() {
        String identifiant = usernameField.getText();
        String motDePasse = new String(passwordField.getPassword());

        // controle de validation
        if (identifiant.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

// Validation de l'email
if (!identifiant.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
    JOptionPane.showMessageDialog(this,
            "L'email n'est pas valide. Il doit contenir '@' et un point '.'",
            "Erreur", JOptionPane.ERROR_MESSAGE);
    return;
}

if (identifiant.length() > 50) {
    JOptionPane.showMessageDialog(this,
            "L'adresse mail ne doit pas dépasser 50 caractères.",
            "Erreur", JOptionPane.ERROR_MESSAGE);
    return;
}

        // Validation du mot de passe
if (!motDePasse.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$")) {
    JOptionPane.showMessageDialog(this,
            "Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule, un chiffre et un caractère spécial.",
            "Erreur", JOptionPane.ERROR_MESSAGE);
    return;
}
if (motDePasse.length() > 50) {
    JOptionPane.showMessageDialog(this,
            "Le mot de passe ne doit pas dépasser 50 caractères.",
            "Erreur", JOptionPane.ERROR_MESSAGE);
    return;
}



        AdminDashboard adminDashboard = new AdminDashboard(null, null, identifiant, motDePasse);

        AdminDashboardService adminDashboardService = new AdminDashboardService(networkConfig);
        boolean isCorrect = adminDashboardService.EnregistrementAdmin(adminDashboard);

        if (isCorrect) {
            JOptionPane.showMessageDialog(this, "Inscription réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            // Fermer et aller vers la page de connexion
            this.dispose();
            new ConnexionAdmin();
        } 
        else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription: Email déjà associé à un compte.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CreationAdmin::new);
    }

}