package edu.ezip.ing1.pds.MarieFrames;

import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Mairie.IncidentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.IOException;
import java.util.Collections;
import java.util.regex.Pattern;

public class FenetreIncident extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger("FrontEnd");
    private static final String networkConfigFile = "network.yaml";

    private NetworkConfig networkConfig;
    private JTable incidentTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    /** Constructeur par défaut : affiche tous les incidents */
    public FenetreIncident() {
        initComponents();
        displayIncidents();
    }

    /**
     * Constructeur filtre : "statut" ou "priorite"
     * n’affiche que les lignes correspondant au critère
     */
    public FenetreIncident(String filtre) {
        initComponents();
        displayIncidents();
        appliquerFiltre(filtre);
    }

    /** Initialise l’UI et le tableau */
    private void initComponents() {
        setTitle("Fenêtre des Incidents");
        setLayout(new BorderLayout());
        setSize(950, 950);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Charger la configuration réseau
        try {
            networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
            logger.debug("Config réseau : {}", networkConfig);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion au serveur.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tableau
        String[] columns = {
                "ID", "Titre", "Description", "Date de création",
                "Date de clôture", "Code Postal", "Priorité", "Statut", "Modifier"
        };
        tableModel = new DefaultTableModel(columns, 0);
        incidentTable = new JTable(tableModel);
        incidentTable.setRowHeight(30);

        // Masquer la colonne ID
        TableColumn idCol = incidentTable.getColumn("ID");
        idCol.setMinWidth(0);
        idCol.setMaxWidth(0);

        // Colonne Modifier
        TableColumn modCol = incidentTable.getColumn("Modifier");
        modCol.setCellRenderer(new ButtonRenderer());
        modCol.setCellEditor(new ButtonEditor(new JCheckBox()));

        // RowSorter et filtre initial nul
        sorter = new TableRowSorter<>(tableModel);
        incidentTable.setRowSorter(sorter);

        add(new JScrollPane(incidentTable), BorderLayout.CENTER);
        setVisible(true);
    }

    /** Remplit le tableau depuis le backend */
    private void displayIncidents() {
        tableModel.setRowCount(0);
        try {
            IncidentService svc = new IncidentService(networkConfig);
            Incidents incs = svc.selectIncidents();
            for (Incident i : incs.getIncidents()) {
                tableModel.addRow(new Object[] {
                        i.getIdTicket(),
                        i.getTitre(),
                        i.getDescription(),
                        i.getDate_creation(),
                        i.getDate_cloture(),
                        i.getCP_Ticket(),
                        convertPriorite(i.getPriorite()),
                        convertStatut(i.getStatut()),
                        "Modifier"
                });
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Erreur chargement incidents", e);
        }
    }

    /**
     * Filtre le JTable pour n’afficher que les lignes dont
     * le statut (col 7) ou la priorité (col 6) correspond
     */
    private void appliquerFiltre(String critere) {
        int col = critere.equals("statut") ? 7 : 6;
        String valeur = critere.equals("statut")
                ? JOptionPane.showInputDialog(this, "Statut à afficher (reçu/en cours/demande traitée) :")
                : JOptionPane.showInputDialog(this, "Priorité à afficher (faible/moyen/élevé) :");
        if (valeur != null) {
            // expression exacte, case-sensitive
            RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("^" + Pattern.quote(valeur) + "$", col);
            sorter.setRowFilter(rf);
        }
    }

    private String convertPriorite(int p) {
        switch (p) {
            case 1:
                return "faible";
            case 2:
                return "moyen";
            case 3:
                return "élevé";
            default:
                return "vide";
        }
    }

    private String convertStatut(int s) {
        switch (s) {
            case 0:
                return "reçu";
            case 1:
                return "en cours de traitement";
            case 2:
                return "demande traitée";
            default:
                return "inconnu";
        }
    }

    // Renderer & Editor pour le bouton "Modifier"
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Modifier");
        }

        public Component getTableCellRendererComponent(JTable t, Object v,
                boolean s, boolean f,
                int r, int c) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private final JButton button = new JButton("Modifier");

        public ButtonEditor(JCheckBox cb) {
            super(cb);
            button.setOpaque(true);
            button.addActionListener(e -> {
                fireEditingStopped();
                int row = incidentTable.getSelectedRow();
                if (row >= 0) {
                    String actuel = (String) incidentTable.getValueAt(row, 7);
                    String[] opts = { "reçu", "en cours de traitement", "demande traitée" };
                    String nv = (String) JOptionPane.showInputDialog(
                            FenetreIncident.this,
                            "Choisissez le nouveau statut :",
                            "Mettre à jour le statut",
                            JOptionPane.PLAIN_MESSAGE,
                            null, opts, actuel);
                    if (nv != null && !nv.equals(actuel)) {
                        int id = (int) incidentTable.getValueAt(row, 0);
                        int code;
                        switch (nv) {
                            case "reçu":
                                code = 0;
                                break;
                            case "en cours de traitement":
                                code = 1;
                                break;
                            case "demande traitée":
                                code = 2;
                                break;
                            default:
                                code = -1;
                                break;
                        }

                        try {
                            Incident upd = new Incident();
                            upd.setIdTicket(id);
                            upd.setStatut(code);
                            new IncidentService(networkConfig).updateIncident(upd);
                            displayIncidents();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(
                                    FenetreIncident.this,
                                    "Erreur lors de la mise à jour.",
                                    "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
        }

        public Component getTableCellEditorComponent(JTable t, Object v,
                boolean s, int r, int c) {
            return button;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreIncident::new);
    }
}
