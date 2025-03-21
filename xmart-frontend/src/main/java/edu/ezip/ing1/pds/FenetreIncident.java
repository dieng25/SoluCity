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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        incidentButton.setBackground(new Color(0, 123, 255));
        add(incidentButton, BorderLayout.NORTH);

        //table des incidents
        String[] columnNames = {"Titre", "Description", "Date de création", "Code Postal", "Priorité", "Statut"};
        tableModel = new DefaultTableModel(columnNames, 0);
        incidentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(incidentTable);
        add(scrollPane, BorderLayout.CENTER);
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
                // Conversion de la priorité en chaîne
                String prioriteString = convertPrioriteToString(incident.getPriorite());
                
                // Conversion du statut en chaîne
                String statutString = convertStatutToString(incident.getStatut());
    
                tableModel.addRow(new Object[]{
                        incident.getTitre(),
                        incident.getDescription(),
                        incident.getDate_creation(),
                        incident.getCP_Ticket(),
                        prioriteString,  // Afficher la priorité -> correspondance en chaine 
                        statutString 
                });
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Erreur lors du chargement des incidents", e);
        }
    }
    
    private String convertPrioriteToString(int priorite) {
        switch (priorite) {
            case 0:
                return "vide";
            case 1:
                return "faible";
            case 2:
                return "moyen";
            case 3:
                return "élevé";
            default:
                return "non definie";
        }
    }
    
    private String convertStatutToString(int statut) {
        switch (statut) {
            case 0:
                return "reçu";
            case 1:
                return "en cours de traitement";
            case 2:
                return "demande traitée";
            default:
                return "non definie";
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreIncident::new);
    }
}
