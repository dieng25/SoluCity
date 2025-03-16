package edu.ezip.ing1.pds;

import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Mairie.IncidentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class FenetreIncident extends JFrame {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private NetworkConfig networkConfig;
    private JTable incidentTable;
    private DefaultTableModel tableModel;

    public FenetreIncident() {
        setTitle("Fenêtre des Incidents");
        setLayout(new BorderLayout());
        setSize(950, 950);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setLocationRelativeTo(null);

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
        incidentButton.setBackground(Color.CYAN);
        add(incidentButton, BorderLayout.NORTH);

        //table des incidents
        String[] columnNames = {"Titre", "Description", "Date de création", "Code Postal", "Priorité", "Statut"};
        tableModel = new DefaultTableModel(columnNames, 0);
        incidentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(incidentTable);
        add(scrollPane, BorderLayout.CENTER);
        setBackground(Color.CYAN);
        setVisible(true);
    }

    private void displayIncidents() {
        try {
            IncidentService incidentService = new IncidentService(networkConfig);
            Incidents incidents = incidentService.selectIncidents();

            // Vider le tableau avant de recharger les données
            tableModel.setRowCount(0);

            // Remplir le tableau avec les incidents
            for (Incident incident : incidents.getIncidents()) {
                tableModel.addRow(new Object[]{
                        incident.getTitre(),
                        incident.getDescription(),
                        incident.getDate_creation(),
                        incident.getCP_Ticket(),
                        incident.getPriorite(),
                        incident.getStatut()
                });
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Erreur lors du chargement des incidents", e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreIncident::new);
    }
}
