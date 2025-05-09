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
        add(new JLabel("Identifiant :"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Bouton d'inscription
        submitButton = new JButton("S'inscrire");
        submitButton.addActionListener(e -> inscrireAdmin());
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

        AdminDashboard adminDashboard = new AdminDashboard(identifiant, motDePasse);

        AdminDashboardService adminDashboardService = new AdminDashboardService(networkConfig);
        boolean isCorrect = adminDashboardService.EnregistrementAdmin(adminDashboard);

        if (isCorrect) {
            JOptionPane.showMessageDialog(this, "Inscription réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            // Fermer la fenêtre d'inscription et reddirection vers la page de d'authentification
            this.dispose();
            new ConnexionAdmin();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CreationAdmin::new);
    }
}
