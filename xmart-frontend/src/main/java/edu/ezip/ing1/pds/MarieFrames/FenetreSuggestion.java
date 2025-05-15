package edu.ezip.ing1.pds.MarieFrames;

import edu.ezip.ing1.pds.business.dto.Suggestion;
import edu.ezip.ing1.pds.business.dto.Suggestions;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Mairie.SuggestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.RowFilter;
import java.awt.*;
import java.io.IOException;
import java.util.regex.Pattern;

public class FenetreSuggestion extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger("FrontEnd");
    private static final String networkConfigFile = "network.yaml";

    private NetworkConfig networkConfig;
    private JTable suggestionTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public FenetreSuggestion() {
        setTitle("Fenêtre des Suggestions");
        setLayout(new BorderLayout());
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Charger la config réseau
        try {
            networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion au serveur.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- Panel de contrôle ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton btnVoir   = new JButton("Voir les suggestions");
        JButton btnFiltre = new JButton("Filtrer suggestions");

        btnVoir.setBackground(new Color(0, 123, 255));
        btnVoir.setForeground(Color.WHITE);
        btnFiltre.setBackground(new Color(108, 117, 125));
        btnFiltre.setForeground(Color.WHITE);

        topPanel.add(btnVoir);
        topPanel.add(btnFiltre);
        add(topPanel, BorderLayout.NORTH);

        // --- Tableau ---
        String[] columns = {
            "Titre", "Description", "Date création", "Date clôture",
            "CodePostal_ticket", "Catégorie", "Statut", "Commentaire",
        };
        tableModel = new DefaultTableModel(columns, 0);
        suggestionTable = new JTable(tableModel);
        suggestionTable.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(tableModel);
        suggestionTable.setRowSorter(sorter);

        add(new JScrollPane(suggestionTable), BorderLayout.CENTER);

        // Actions
        btnVoir.addActionListener(e -> {
            displaySuggestions();
            sorter.setRowFilter(null);
        });

        btnFiltre.addActionListener(e -> {
            String[] opts = {"Par statut", "Par catégorie"};
            String crit = (String) JOptionPane.showInputDialog(
                this,
                "Critère de filtre :",
                "Filtrer suggestions",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opts,
                opts[0]
            );
            if (crit == null) return;

            String prompt = crit.equals("Par statut")
                ? "Entrez le statut (reçu / en cours de traitement / demande traitée) :"
                : "Entrez la catégorie :";
            String val = JOptionPane.showInputDialog(this, prompt);
            if (val == null) return;

            // colonnes : Catégorie = index 5, Statut = index 6
            int col = crit.equals("Par statut") ? 6 : 5;
            RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter(
                "^" + Pattern.quote(val) + "$", col
            );
            sorter.setRowFilter(rf);
        });

        displaySuggestions();
        setVisible(true);
    }

    private void displaySuggestions() {
        tableModel.setRowCount(0);
        try {
            SuggestionService svc = new SuggestionService(networkConfig);
            Suggestions suggestions = svc.selectSuggestions();
            for (Suggestion s : suggestions.getSuggestions()) {
                tableModel.addRow(new Object[]{
                    s.getTitre(),
                    s.getDescription(),
                    s.getDate_creation(),
                    s.getDate_cloture(),
                    s.getCP_Ticket(),
                    s.getCategorie(),
                    convertStatut(s.getStatut()),
                    s.getCommentaire(),
                });
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Erreur chargement suggestions", e);
            JOptionPane.showMessageDialog(this,
                "Impossible de charger les suggestions.",
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String convertStatut(int s) {
        String res;
        switch (s) {
            case 0: res = "reçu";                  break;
            case 1: res = "en cours de traitement";break;
            case 2: res = "demande traitée";       break;
            default:res = "non définie";           break;
        }
        return res;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreSuggestion::new);
    }
}
