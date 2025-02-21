package edu.ezip.ing1.pds;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class User {

    public static final String USER_FILE = "users.txt";
    public static JFrame mainFrame;

    public static void initMainFrame() {
        mainFrame = new JFrame("Authentification Mairie");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 400); // Fenêtre initiale de taille fixe
        mainFrame.setLocationRelativeTo(null); // Centrer la fenêtre

        showLoginForm(); // Afficher le formulaire de connexion par défaut

        mainFrame.setVisible(true);
    }

    public static void showLoginForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titleLabel = new JLabel("Connexion");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Champs email avec placeholder
        JTextField emailField = new JTextField("Adresse Email");
        emailField.setPreferredSize(new Dimension(250, 30));
        emailField.setMaximumSize(new Dimension(250, 30));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setForeground(Color.GRAY);
        emailField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (emailField.getText().equals("Adresse Email")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("Adresse Email");
                    emailField.setForeground(Color.GRAY);
                }
            }
        });

        // Champs mot de passe avec placeholder
        JPasswordField passwordField = new JPasswordField("Mot de passe");
        passwordField.setPreferredSize(new Dimension(250, 30));
        passwordField.setMaximumSize(new Dimension(250, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0); // Désactiver le masquage initial
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("Mot de passe")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('*'); // Activer le masquage
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("Mot de passe");
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0); // Désactiver le masquage
                }
            }
        });

        JButton loginButton = new JButton("Connexion");
        loginButton.setPreferredSize(new Dimension(250, 40));
        loginButton.setMaximumSize(new Dimension(250, 40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);

        JLabel registerLabel = new JLabel("Vous n'avez pas de compte?");
        registerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton registerButton = new JButton("Créer un compte");
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setForeground(new Color(0, 123, 255));
        registerButton.setBackground(Color.WHITE);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerButton.addActionListener(e -> showRegisterForm());

        // bouton de cnx
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (authenticateUser(email, password)) {
                JOptionPane.showMessageDialog(mainFrame, "Connexion réussie !", "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                // Si connexion reussie, alors on autorise l'accés à l'application 
                mainFrame.dispose(); // Ferme le formulaire de connexion
                SwingUtilities.invokeLater(() -> {
                    new MairieGUI(); // Lance votre interface Mairie
                });
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Email ou mot de passe incorrect.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ajouter les composants au panneau
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(registerLabel);
        panel.add(registerButton);
        panel.add(Box.createVerticalGlue());

        mainFrame.getContentPane().removeAll(); // Nettoyer le contenu précédent
        mainFrame.add(panel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * Montre le formulaire d'inscription
     * - Ne demande plus nom et prénom
     * - Demande plutôt le code postal
     */
    public static void showRegisterForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titleLabel = new JLabel("Créer un compte");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Champ code postal avec placeholder
        JTextField codePostalField = new JTextField("Code Postal");
        codePostalField.setPreferredSize(new Dimension(250, 30));
        codePostalField.setMaximumSize(new Dimension(250, 30));
        codePostalField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPlaceholder(codePostalField, "Code Postal");

        // Champs email avec placeholder
        JTextField emailField = new JTextField("Adresse Email");
        emailField.setPreferredSize(new Dimension(250, 30));
        emailField.setMaximumSize(new Dimension(250, 30));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPlaceholder(emailField, "Adresse Email");

        // Champs mot de passe avec placeholder
        JPasswordField passwordField = new JPasswordField("Mot de passe");
        passwordField.setPreferredSize(new Dimension(250, 30));
        passwordField.setMaximumSize(new Dimension(250, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("Mot de passe")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('*');
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("Mot de passe");
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });

        JButton saveButton = new JButton("Enregistrer");
        saveButton.setPreferredSize(new Dimension(250, 40));
        saveButton.setMaximumSize(new Dimension(250, 40));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setBackground(new Color(0, 123, 255));
        saveButton.setForeground(Color.WHITE);

        JButton backButton = new JButton("Retour");
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setForeground(new Color(0, 123, 255));
        backButton.setBackground(Color.WHITE);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton.addActionListener(e -> showLoginForm());

        // Bouton d'enregistrement
        saveButton.addActionListener(e -> {
            String codePostal = codePostalField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (codePostal.isEmpty() || email.isEmpty() || password.isEmpty() ||
                codePostal.equals("Code Postal") || email.equals("Adresse Email") || password.equals("Mot de passe")) {
                JOptionPane.showMessageDialog(mainFrame, "Tous les champs sont obligatoires.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Sauvegarde dans le fichier. Ici, on enregistre le code postal, l'email et le mot de passe.
                saveUserToFile(codePostal, email, password);
                JOptionPane.showMessageDialog(mainFrame, "Compte créé avec succès !", "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                showLoginForm();
            }
        });

        // Ajouter les composants au panneau
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(codePostalField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(saveButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(backButton);
        panel.add(Box.createVerticalGlue());

        mainFrame.getContentPane().removeAll();
        mainFrame.add(panel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * Méthode générique pour ajouter un placeholder à un JTextField
     */
    public static void addPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    /**
     * Sauvegarde du user dans un fichier
     * Dans ce nouveau format, on enregistre : codePostal, email, password
     * séparés par des virgules.
     */
    public static void saveUserToFile(String codePostal, String email, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(String.join(",", codePostal, email, password));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Authentifie un utilisateur à partir du fichier
     * Désormais, le fichier est stocké sous forme : codePostal, email, password
     * On compare seulement email et password pour la connexion.
     */
    public static boolean authenticateUser(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Vérifier la taille de parts car maintenant on stocke 3 champs
                if (parts.length == 3 && parts[1].equals(email) && parts[2].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
