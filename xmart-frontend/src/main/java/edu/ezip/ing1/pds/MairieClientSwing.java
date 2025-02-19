import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MairieClientSwing {
    private JFrame frame;
    private JTextField codePostalField;
    private JButton incidentsButton;
    private JButton suggestionsButton;
    private JTextArea resultArea;

    public MairieClientSwing() {
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
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(incidentsButton);
        buttonPanel.add(suggestionsButton);
        
        // Zone d'affichage des résultats
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        
        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem exitItem = new JMenuItem("Quitter");
        exitItem.addActionListener(e -> System.exit(0));
        menu.add(exitItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        
        // Ajouter composants
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);
        
        // Actions des boutons
        incidentsButton.addActionListener(e -> fetchData("incident"));
        suggestionsButton.addActionListener(e -> fetchData("suggestion"));
        
        frame.setVisible(true);
    }

    private void fetchData(String type) {
        String codePostal = codePostalField.getText().trim();
        if (codePostal.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Veuillez entrer un code postal.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Simulation d'une récupération de données
        String result = "Affichage des " + type + " pour la mairie: " + codePostal + "\n";
        result += "(Les données seront récupérées depuis le backend)\n";
        
        resultArea.setText(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MairieClientSwing::new);
    }
}
