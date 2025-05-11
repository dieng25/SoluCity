package edu.ezip.ing1.pds.DashboardFenetre;
import edu.ezip.ing1.pds.business.dto.DashboardDto.UserDashboard;
import edu.ezip.ing1.pds.services.Dashboard.AdminDashboardService;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.*;
import java.awt.*;

public class CreationUtilisateur extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(CreationUtilisateur.class);
    private final static String networkConfigFile = "network.yaml";

    private NetworkConfig networkConfig;
    private JTextField CodePostalField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton submitButton;

    public CreationUtilisateur() {
        setTitle("Inscription Utilisateur");
        setLayout(new GridLayout(4, 2, 10, 10));
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        try {
            networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
            logger.debug("Chargement du fichier de configuration réseau : {}", networkConfig.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion au serveur.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        add(new JLabel("Code Postal :"));
        CodePostalField = new JTextField();
        add(CodePostalField);

        add(new JLabel("Email :"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        add(passwordField);

        submitButton = new JButton("Inscrire");
        submitButton.addActionListener(e -> inscrireUtilisateur());
        submitButton.setBackground(new Color(0, 123, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setFocusPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(submitButton);

        setVisible(true);
    }

    private void inscrireUtilisateur() {
        String CodePostal = CodePostalField.getText();
        String identifiant = usernameField.getText();
        String motDePasse = new String(passwordField.getPassword());

        if (CodePostal.isEmpty() || identifiant.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDashboard userDashboard = new UserDashboard(CodePostal, identifiant, motDePasse);

        AdminDashboardService adminDashboardService = new AdminDashboardService(networkConfig);
        boolean isCorrect = adminDashboardService.EnregistrementUtilisateur(userDashboard);

        if (isCorrect) {
            JOptionPane.showMessageDialog(this, "Inscription réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CreationUtilisateur::new);
    }
}
