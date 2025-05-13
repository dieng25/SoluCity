package edu.ezip.ing1.pds.DashboardFenetre;
import edu.ezip.ing1.pds.business.dto.DashboardDto.AdminDashboard;
import edu.ezip.ing1.pds.services.Dashboard.AdminDashboardService;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class ConnexionAdmin extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(ConnexionAdmin.class);
    private final static String networkConfigFile = "network.yaml";

    private NetworkConfig networkConfig;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JButton registerButton;

    public ConnexionAdmin() {
        setTitle("Connexion Administrateur");
        setLayout(new GridLayout(4, 2, 10, 10));
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        // Bouton de connexion
        submitButton = new JButton("Se connecter");
        submitButton.addActionListener(e -> authentifierAdmin());
        add(submitButton);

        registerButton = new JButton("Creer un compte");
        registerButton.addActionListener(e -> new CreationAdmin());
        add(registerButton);

        JButton[] buttons = {submitButton, registerButton};
        for (JButton btn : buttons) {
            btn.setBackground(new Color(0, 123, 255));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        setVisible(true);
    }

    private void authentifierAdmin() {
        String identifiant = usernameField.getText();
        String motDePasse = new String(passwordField.getPassword());

        // controles de validation 
        if (identifiant.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Création de l'Admin pour l'authentification
        AdminDashboard adminDashboard = new AdminDashboard(null, null, identifiant, motDePasse);

        AdminDashboardService adminDashboardService = new AdminDashboardService(networkConfig);
        boolean result = adminDashboardService.AuthentificationAdmin(adminDashboard);

        if (result) {
            JOptionPane.showMessageDialog(this, "Connexion réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            // Fermer la fenêtre d'auth et rediriger vers la page d'accueil
            this.dispose();
            new FenetreChoixAdmin().setVisible(true);    
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la connexion. Email ou mot de passe incorrect.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConnexionAdmin::new);
    }
}
