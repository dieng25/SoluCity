package edu.ezip.ing1.pds;

import edu.ezip.ing1.pds.business.dto.Suggestion;
import edu.ezip.ing1.pds.business.dto.Suggestions;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Mairie.SuggestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class FenetreSuggestion extends JFrame {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private NetworkConfig networkConfig;
    private JTable suggestionTable;
    private DefaultTableModel tableModel;

    public FenetreSuggestion() {
        setTitle("Fenêtre des Suggestions");
        setLayout(new BorderLayout());
        setSize(950, 950);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre

        // Charger la configuration réseau
        try {
            networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
            logger.debug("Chargement du fichier de configuration réseau : {}", networkConfig.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion au serveur. Veuillez réessayer.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Bouton pour afficher les suggestions
        JButton suggestionButton = new JButton("Voir les Suggestions");
        suggestionButton.addActionListener(e -> displaySuggestions());
        suggestionButton.setBackground(Color.CYAN);
        add(suggestionButton, BorderLayout.NORTH);

        // Table des suggestions
        String[] columnNames = {"Titre", "Description", "Date", "Code Postal", "Statut"};
        tableModel = new DefaultTableModel(columnNames, 0);
        suggestionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(suggestionTable);
        add(scrollPane, BorderLayout.CENTER);
        setBackground(Color.CYAN);
        setVisible(true);
    }

    private void displaySuggestions() {
        try {
            SuggestionService suggestionService = new SuggestionService(networkConfig);
            Suggestions suggestions = suggestionService.selectSuggestions(); 

            // Vider le tableau avant de recharger les données
            tableModel.setRowCount(0);

            // Remplir le tableau avec les suggestions
            for (Suggestion suggestion : suggestions.getSuggestions()) {
                tableModel.addRow(new Object[]{
                        suggestion.getTitre(),
                        suggestion.getDescription(),
                        suggestion.getDate(),
                        suggestion.getCP_Ticket(),
                        suggestion.getStatut()
                });

                // Affichage dans la console
                System.out.println("Suggestion : " + suggestion.getTitre());
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Erreur lors du chargement des suggestions", e);
            JOptionPane.showMessageDialog(this,
                    "Impossible de charger les suggestions.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreSuggestion::new);
    }
}
