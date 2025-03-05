package edu.ezip.ing1.pds.services.Mairie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.SelectIncidentClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.UUID;

public class IncidentTableFrame extends JFrame {

    private static final String LoggingLabel = "FrontEnd - IncidentTableFrame";
    private static final Logger logger = LoggerFactory.getLogger(LoggingLabel);
    
    private JTable table;
    private DefaultTableModel tableModel;
    private NetworkConfig networkConfig;
    private int clientId;

    private static final String selectRequestOrder = "SELECT_ALL_INCIDENTS";

    public IncidentTableFrame(NetworkConfig networkConfig, int clientId) {
        this.networkConfig = networkConfig;
        this.clientId = clientId;

        setTitle("Liste des Incidents");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configuration du tableau
        String[] columnNames = {"ID", "Titre", "Description", "Date", "Catégorie", "Statut", "Code Postal", "Priorité"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Bouton Actualiser
        JButton refreshButton = new JButton("Actualiser");
        refreshButton.addActionListener(e -> loadIncidents());

        JPanel panel = new JPanel();
        panel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        // Charger les incidents au démarrage
        loadIncidents();
    }

    private void loadIncidents() {
        try {
            // Génération d'un identifiant unique de requête
            final String requestId = UUID.randomUUID().toString();
            final Request request = new Request();
            request.setRequestId(requestId);
            request.setRequestOrder(selectRequestOrder);
            request.setRequestContent(""); // Pas de contenu spécifique

            logger.info(" Envoi de la requête avec ID: {}", requestId);

            // Sérialisation de la requête
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

            // Création de la pile de requêtes
            final Deque<ClientRequest> clientRequests = new ArrayDeque<>();
            final SelectIncidentClientRequest clientRequest = new SelectIncidentClientRequest(
                    networkConfig,
                    clientId,
                    request,
                    null,
                    requestBytes
            );

            clientRequests.push(clientRequest);

            // Exécuter la requête
            while (!clientRequests.isEmpty()) {
                final ClientRequest currentRequest = clientRequests.pop();
                currentRequest.join();

                List<Incident> incidents = ((SelectIncidentClientRequest) currentRequest).getResult();
                if (incidents == null ) {
                    logger.warn("⚠️ le retour de la requete est null");
                    JOptionPane.showMessageDialog(this, "Aucun incident trouvé.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                logger.info(" {} incidents récupérés.", incidents.size());

                // Mise à jour de la table
                tableModel.setRowCount(0); // Vider la table avant d'ajouter de nouvelles données
                for (Incident incident : incidents) {
                    tableModel.addRow(new Object[]{
                            incident.getIdTicket(),
                            incident.getTitre(),
                            incident.getDescription(),
                            incident.getDate_creation(),
                            incident.getCategorie(),
                            incident.getStatut(),
                            incident.getCP_Ticket(),
                            incident.getPriorite()
                    });
                }
            }

        } catch (IOException | InterruptedException e) {
            logger.error("Erreur lors de la récupération des incidents", e);
            JOptionPane.showMessageDialog(this, "Erreur de connexion au serveur", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
