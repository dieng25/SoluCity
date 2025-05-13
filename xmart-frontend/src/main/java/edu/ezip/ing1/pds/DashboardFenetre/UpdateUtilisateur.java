package edu.ezip.ing1.pds.DashboardFenetre;

import edu.ezip.ing1.pds.business.dto.DashboardDto.UserDashboard;
import edu.ezip.ing1.pds.services.Dashboard.AdminDashboardService;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class UpdateUtilisateur extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(UpdateUtilisateur.class);
    private final static String networkConfigFile = "network.yaml";

    private NetworkConfig networkConfig;
    private JTextField CodePostalField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton submitButton;

    public UpdateUtilisateur() {
        setTitle("Modifier les infos de connexion de l'utilisateur");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        try {
            networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
            logger.debug("Chargement du fichier de configuration réseau : {}", networkConfig.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion au serveur.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        panel.add(new JLabel("Code Postal :"));
        CodePostalField = new JTextField();
        CodePostalField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(CodePostalField);

        panel.add(Box.createRigidArea(new Dimension(0, 10))); 

        panel.add(new JLabel("Modifier l'Email :"));
        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(usernameField);

        panel.add(Box.createRigidArea(new Dimension(0, 10))); 

        panel.add(new JLabel("Modifier le Mot de passe :"));
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(passwordField);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); 

        submitButton = new JButton("Modifier");
        submitButton.addActionListener(e -> ReinscrireUtilisateur());
        submitButton.setBackground(new Color(0, 123, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setFocusPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(submitButton);

        add(panel);
        setVisible(true);
    }

    private void ReinscrireUtilisateur() {
        String CodePostal = CodePostalField.getText();
        String identifiant = usernameField.getText();
        String motDePasse = new String(passwordField.getPassword());

        if (CodePostal.isEmpty() || identifiant.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

                 // Je veux que le code postal = entier de 5 chiffres exactement
    if (!CodePostal.matches("\\d{5}")) {
        JOptionPane.showMessageDialog(this, "Le code postal doit contenir exactement 5 chiffres.", "Code Postal invalide",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validation de l'email
if (!identifiant.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
    JOptionPane.showMessageDialog(this, "L'email n'est pas valide. Il doit contenir '@' et un point '.'", "Erreur",
            JOptionPane.ERROR_MESSAGE);
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

        UserDashboard userDashboard = new UserDashboard(CodePostal, identifiant, motDePasse);

        AdminDashboardService adminDashboardService = new AdminDashboardService(networkConfig);
        boolean isCorrect = adminDashboardService.MettreAJourUtilisateur(userDashboard);

        if (isCorrect) {
            JOptionPane.showMessageDialog(this, "Modification réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new FenetreChoixAdmin().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification car ce Code Postal n'est associé à aucun compte.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UpdateUtilisateur::new);
    }
}
