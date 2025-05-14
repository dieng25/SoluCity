package edu.ezip.ing1.pds.services.Citoyen.Connexion.ConsulteIncident;

import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Citoyen.IncidentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class RecupIncident implements ActionListener {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private Citoyen citoyen;
    private final JFrame parentWindow;


    public RecupIncident(Citoyen citoyen, JFrame parentWindow) {
        this.citoyen = citoyen;
        this.parentWindow = parentWindow;
    }

    public void actionPerformed(ActionEvent e) {
        String tel = citoyen.getTelNum();

        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        IncidentService incidentService = new IncidentService(networkConfig);

        try {
            List<Incident> incidents = incidentService.selectIncidentsByTel(tel);
            if (incidents.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Aucun incident trouvé.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                parentWindow.dispose();
                new IncidentConsulte(incidents, citoyen);
            }

        } catch (InterruptedException | IOException ex) {
            logger.error("Erreur lors de la récupération des incidents", ex);
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des incidents.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}








