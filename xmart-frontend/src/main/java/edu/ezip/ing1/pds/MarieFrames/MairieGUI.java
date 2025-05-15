package edu.ezip.ing1.pds.MarieFrames;
import edu.ezip.ing1.pds.DashboardFenetre.*;
import javax.swing.*;
import java.awt.*;

public class MairieGUI {
    private JFrame frame;
    private JButton incidentsButton;
    private JButton suggestionsButton;
    private JButton statistiqueButton;
    private JButton trierButton;

    public MairieGUI() {
        // fenêtre principale App Mairie
        frame = new JFrame("Dashboard Mairie");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(Color.WHITE);

        // Création du menu
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 123, 255));
        JMenu menu = new JMenu("Options");
        menu.setForeground(Color.WHITE);
        JMenuItem profileItem = new JMenuItem("Profil");
        JMenuItem settingItem = new JMenuItem("Paramètre");
        JMenuItem logoutItem = new JMenuItem("Se déconnecter");
        JMenuItem exitItem = new JMenuItem("Quitter");
        exitItem.addActionListener(e -> System.exit(0));
        menu.add(profileItem);
        menu.add(settingItem);
        menu.add(logoutItem);
        menu.addSeparator();
        menu.add(exitItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        // Panel central pour les boutons avec GridBagLayout
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        incidentsButton = new JButton("Afficher Incidents");
        suggestionsButton = new JButton("Afficher Suggestions");
        statistiqueButton = new JButton("Statistiques et Prédictions");
        trierButton = new JButton("Trier par Statut");

        // Configuration uniforme des boutons
        JButton[] buttons = {incidentsButton, suggestionsButton, statistiqueButton, trierButton};
        for (JButton btn : buttons) {
            btn.setBackground(new Color(0, 123, 255));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setFocusPainted(false);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(incidentsButton, gbc);

        gbc.gridx = 1;
        centerPanel.add(suggestionsButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(statistiqueButton, gbc);

        gbc.gridx = 1;
        centerPanel.add(trierButton, gbc);

        // Ajouter le panel dans la fenêtre principale
        frame.add(centerPanel, BorderLayout.CENTER);

        //Actions des boutons
        incidentsButton.addActionListener(e -> new FenetreIncident());
        suggestionsButton.addActionListener(e -> new FenetreSuggestion());
        statistiqueButton.addActionListener(e ->  new ConnexionUtilisateur().setVisible(true));
        // trierButton.addActionListener(e -> new FenetreTrier());

        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new MairieGUI();
        // User.initMainFrame(); //pour l'authentification
    }
}
