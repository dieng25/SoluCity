package edu.ezip.ing1.pds.MarieFrames;

import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Mairie.IncidentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;

public class FenetreIncident extends JFrame {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private NetworkConfig networkConfig;
    private JTable incidentTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public FenetreIncident() {
        setTitle("Fenêtre des Incidents");
        setLayout(new BorderLayout());
        setSize(950, 950);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        // Bouton pour afficher les incidents
        JButton incidentButton = new JButton("voir les incidents");
        incidentButton.addActionListener(e -> displayIncidents());
        incidentButton.setBackground(new Color(0, 123, 255));
        add(incidentButton, BorderLayout.NORTH);

        // Table des incidents
        String[] columnNames = {"ID", "Titre", "Description", "Date de création", "Date de clôture", "Code Postal", "Priorité", "Statut", "Modifier"};
        tableModel = new DefaultTableModel(columnNames, 0);
        incidentTable = new JTable(tableModel);
        incidentTable.setRowHeight(30);

        // Masquer la colonne ID
        TableColumn idCol = incidentTable.getColumn("ID");
        idCol.setMinWidth(0);
        idCol.setMaxWidth(0);
        idCol.setWidth(0);

        // Bouton Modifier
        TableColumn column = incidentTable.getColumn("Modifier");
        column.setCellRenderer(new ButtonRenderer());
        column.setCellEditor(new ButtonEditor(new JCheckBox()));

        // Tri par défaut unique sur "Date de création" (index=3), du plus récent au plus ancien
        sorter = new TableRowSorter<>(tableModel);
        incidentTable.setRowSorter(sorter);
        sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(3, SortOrder.DESCENDING)));
        sorter.setSortable(0, false); // ID caché
        sorter.setSortable(8, false); // Modifier non triable

        JScrollPane scrollPane = new JScrollPane(incidentTable);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private void displayIncidents() {
        try {
            IncidentService incidentService = new IncidentService(networkConfig);
            Incidents incidents = incidentService.selectIncidents();

            // Vider le tableau
            tableModel.setRowCount(0);

            // Remplir le tableau
            for (Incident incident : incidents.getIncidents()) {
                String prioriteString = convertPrioriteToString(incident.getPriorite());
                String statutString = convertStatutToString(incident.getStatut());

                tableModel.addRow(new Object[]{
                    incident.getIdTicket(),
                    incident.getTitre(),
                    incident.getDescription(),
                    incident.getDate_creation(),
                    incident.getDate_cloture(),
                    incident.getCP_Ticket(),
                    prioriteString,
                    statutString,
                    "Modifier"
                });
            }

            // Réappliquer le tri
            sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(3, SortOrder.DESCENDING)));

        } catch (IOException | InterruptedException e) {
            logger.error("Erreur lors du chargement des incidents", e);
        }
    }

    private String convertPrioriteToString(int priorite) {
        switch (priorite) {
            case 0: return "vide";
            case 1: return "faible";
            case 2: return "moyen";
            case 3: return "élevé";
            default: return "non définie";
        }
    }

    private String convertStatutToString(int statut) {
        switch (statut) {
            case 0: return "reçu";
            case 1: return "en cours de traitement";
            case 2: return "demande traitée";
            default: return "non défini";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreIncident::new);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Modifier");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String statut;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Modifier");
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    int selectedRow = incidentTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        statut = (String) incidentTable.getValueAt(selectedRow, 7);
                        String[] options = {"reçu", "en cours de traitement", "demande traitée"};
                        String newStatus = (String) JOptionPane.showInputDialog(
                                FenetreIncident.this,
                                "Sélectionnez le nouveau statut",
                                "Mettre à jour le statut",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                options,
                                statut
                        );
                        if (newStatus != null && !newStatus.equals(statut)) {
                            int incidentId = (int) incidentTable.getValueAt(selectedRow, 0);
                            int statutInt = convertStatutToInt(newStatus);
                            Incident updatedIncident = new Incident();
                            updatedIncident.setIdTicket(incidentId);
                            updatedIncident.setStatut(statutInt);
                            updateIncidentInBackend(updatedIncident);
                            displayIncidents(); //rafficher le tableau apres mise à jour, recharger les donnees de la base
                        }
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }

        private int convertStatutToInt(String statut) {
            switch (statut) {
                case "reçu": return 0;
                case "en cours de traitement": return 1;
                case "demande traitée": return 2;
                default: return -1;
            }
        }

        private void updateIncidentInBackend(Incident incident) {
            try {
                IncidentService incidentService = new IncidentService(networkConfig);
                incidentService.updateIncident(incident);
            } catch (IOException | InterruptedException e) {
                JOptionPane.showMessageDialog(FenetreIncident.this, "Erreur lors de la mise à jour de l'incident.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
