package edu.ezip.ing1.pds.services.Citoyen.Connexion.IncidentConnect;

import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Citoyen.Connexion.AccueilConnexion;
import edu.ezip.ing1.pds.services.Citoyen.IncidentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;

public class EnvoieFormIncidentConnect implements ActionListener {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private FormIncidentConnect form;
    private Citoyen citoyen;

    public EnvoieFormIncidentConnect(FormIncidentConnect formIncidentConnect, Citoyen citoyen) {
        form = formIncidentConnect;
        this.citoyen = citoyen;
    }

    public void actionPerformed(ActionEvent e) {
        if (!ValidFormIncidentConnect.isValid(form)) {
            return;
        }

        String categorie = form.getCategorie();
        String tel = form.getTel();
        String cp = form.getCodePostal();
        String titre = form.getTitre();
        String description = form.getDescription();
        Date date = form.getDate();
        int priorite = ValidFormIncidentConnect.getPrioriteIndex(form.getPriorite());

        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);

        Incident incident = new Incident(titre, description, date, categorie, 0, cp, priorite, null, tel, cp);
        IncidentService incidentService = new IncidentService(networkConfig);
        JOptionPane.showMessageDialog(form, "Le Ticket Incident a bien été envoyé.  ",
                "Succès", JOptionPane.INFORMATION_MESSAGE);
        form.dispose();
        new AccueilConnexion(citoyen).setVisible(true);
        try {
            incidentService.insertIncident(incident);
        } catch (InterruptedException | IOException ex) {
            logger.error("Erreur lors de l'envoie des données ticket incident", e);
            JOptionPane.showMessageDialog(form, "Erreur de connexion au serveur. Veuillez réessayer plus tard.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }


    }
}
