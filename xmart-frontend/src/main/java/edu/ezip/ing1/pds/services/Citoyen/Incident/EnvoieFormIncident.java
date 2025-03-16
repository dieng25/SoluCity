package edu.ezip.ing1.pds.services.Citoyen.Incident;



import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Citoyens;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Citoyen.CitoyenService;
import edu.ezip.ing1.pds.services.Citoyen.Connexion.ConfirmationInscription;
import edu.ezip.ing1.pds.services.Citoyen.IncidentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;

public class EnvoieFormIncident implements ActionListener {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private FormulaireIncident form;

    public EnvoieFormIncident(FormulaireIncident formulaireIncident) {
        form = formulaireIncident;
    }

    public void actionPerformed(ActionEvent e) {
        if (!ValidFormIncident.isValid(form)) {
            JOptionPane.showMessageDialog(form, "Veuillez remplir correctement tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String categorie = form.getCategorie();
        String nom = form.getNom();
        String prenom = form.getPrenom();
        String tel = form.getTel();
        String email = form.getEmail();
        String cp = form.getCodePostal();
        String titre = form.getTitre();
        String description = form.getDescription();
        Date date = form.getDate();
        int priorite = ValidFormIncident.getPrioriteIndex(form.getPriorite());


        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);

        Citoyen citoyen = new Citoyen(tel, nom, prenom, email, null);
        CitoyenService citoyenService = new CitoyenService(networkConfig);

        try {
            boolean exists = citoyenService.selectTelExist(tel);
            if (exists) {
                JOptionPane.showMessageDialog(form, "Vous avez déjà un compte. Veuillez vous connecter.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }catch (InterruptedException | IOException ex) {
            logger.error("ne peut pas vérifié le numéro de téléphone", e);
            JOptionPane.showMessageDialog(form, "Erreur de connexion au serveur. Veuillez réessayer plus tard.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        try {
            citoyenService.insertCitoyen(citoyen);
        } catch (InterruptedException | IOException ex) {
            logger.error("Erreur lors de l'envoie des données du citoyen", e);
            JOptionPane.showMessageDialog(form, "Erreur de connexion au serveur. Veuillez réessayer plus tard.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        Incident incident = new Incident(titre, description, date, categorie, 0, cp, priorite, null, tel, cp);
        IncidentService incidentService = new IncidentService(networkConfig);
        try {
            incidentService.insertIncident(incident);
        } catch (InterruptedException | IOException ex) {
            logger.error("Erreur lors de l'envoie des données ticket incident", e);
            JOptionPane.showMessageDialog(form, "Erreur de connexion au serveur. Veuillez réessayer plus tard.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        try {
            Citoyens citoyens = citoyenService.selectCitoyens();
            final String[] id = new String[1];
            if (citoyens != null) {
                for (Citoyen c : citoyens.getCitoyens()) {
                    if (c.getTelNum().equals(tel)) {
                        id[0] = c.getIdentifiant();
                        break;
                    }
                }
            }
            form.dispose();
            SwingUtilities.invokeLater(() -> new ConfirmationInscription(id[0]));
        } catch (InterruptedException | IOException ex) {
            logger.error("Erreur lors de la récupération de l'identifiant du citoyen", ex);
            JOptionPane.showMessageDialog(form, "Erreur de connexion au serveur. Veuillez contacter l'administrateur pour récupérer vos identifiants de connexion.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

}
