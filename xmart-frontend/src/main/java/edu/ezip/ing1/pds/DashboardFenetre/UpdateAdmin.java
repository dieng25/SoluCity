package edu.ezip.ing1.pds.DashboardFenetre;

import edu.ezip.ing1.pds.business.dto.DashboardDto.AdminDashboard;
import edu.ezip.ing1.pds.services.Dashboard.AdminDashboardService;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class UpdateAdmin extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(UpdateAdmin.class);
    private final static String networkConfigFile = "network.yaml";

    private NetworkConfig networkConfig;
    private JTextField OldUsernameField;
    private JPasswordField OldPasswordField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton submitButton;

    public UpdateAdmin() {
        setTitle("Modifier mes infos de connexion");
        setSize(600, 400);  // Ajuster la taille de la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
        panel.add(new JLabel("Ancienne adresse mail :"));
        OldUsernameField = new JTextField();
        OldUsernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Taille ajustée
        panel.add(OldUsernameField);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Ancien mot de passe :"));
        OldPasswordField = new JPasswordField();
        OldPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Taille ajustée
        panel.add(OldPasswordField);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Nouvelle adresse mail :"));
        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Taille ajustée
        panel.add(usernameField);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Nouveau mot de passe :"));
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Taille ajustée
        panel.add(passwordField);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Bouton de soumission
        submitButton = new JButton("Mettre à jour");
        submitButton.addActionListener(e -> ReinscrireAdmin());
        submitButton.setBackground(new Color(0, 123, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setFocusPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(submitButton);

        add(panel);
        setVisible(true);
    }

    private void ReinscrireAdmin() {
        String identifiant = usernameField.getText();
        String OldIdentifiant = OldUsernameField.getText();
        String OldMotDePasse = new String(OldPasswordField.getPassword());
        String motDePasse = new String(passwordField.getPassword());

        // Contrôle de validation
        if (OldIdentifiant.isEmpty() || OldMotDePasse.isEmpty() || identifiant.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        AdminDashboard adminDashboard = new AdminDashboard(identifiant, motDePasse, OldIdentifiant, OldMotDePasse);

        AdminDashboardService adminDashboardService = new AdminDashboardService(networkConfig);
        boolean isCorrect = adminDashboardService.MettreAJourAdmin(adminDashboard);

        if (isCorrect) {
            JOptionPane.showMessageDialog(this, "Modification réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UpdateAdmin::new);
    }
}
