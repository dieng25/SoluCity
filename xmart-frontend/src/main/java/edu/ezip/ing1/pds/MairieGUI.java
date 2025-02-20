package edu.ezip.ing1.pds;
import javax.swing.*;
import java.awt.*;

public class MairieGUI {
    private JFrame frame;
    private JTextField codePostalField;
    private JButton incidentsButton;
    private JButton suggestionsButton;
    private JButton statistiqueButton;
    private JTextArea resultArea;

    public MairieGUI() {
        frame = new JFrame("Client Mairie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Panel du haut pour le code postal
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Code Postal: "));
        codePostalField = new JTextField(10);
        topPanel.add(codePostalField);

        // Boutons
        incidentsButton = new JButton("Afficher Incidents");
        suggestionsButton = new JButton("Afficher Suggestions");
        statistiqueButton = new JButton("Afficher Statistiques");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(incidentsButton);
        buttonPanel.add(suggestionsButton);
        buttonPanel.add(statistiqueButton);
        incidentsButton.setBackground(Color.CYAN);
        suggestionsButton.setBackground(Color.CYAN);
        statistiqueButton.setBackground(Color.CYAN);
        // Zone d'affichage des résultats
        // resultArea = new JTextArea();
        // resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem exitItem = new JMenuItem("Quitter");
        exitItem.addActionListener(e -> System.exit(0));
        menu.add(exitItem);
        menuBar.add(menu);
        menuBar.setBackground(Color.CYAN);
        frame.setJMenuBar(menuBar);

        // Ajouter composants
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Actions des boutons
        incidentsButton.addActionListener(e -> showData("incidents"));
        suggestionsButton.addActionListener(e -> showData("suggestions"));
        statistiqueButton.addActionListener(e -> showData("suggestions"));


        frame.setVisible(true);
    }

    private void showData(String type) {
        String codePostal = codePostalField.getText().trim();
        if (codePostal.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Veuillez entrer un code postal.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Affichage de données : appel aux services
        // String result = "Affichage des " + type + " pour le code postal: " + codePostal + "\n";
        // result += "- Exemple 1\n- Exemple 2\n- Exemple 3\n";

        // resultArea.setText(result);
    }

    public static void main(String[] args) {
        User.initMainFrame();
    }
}
