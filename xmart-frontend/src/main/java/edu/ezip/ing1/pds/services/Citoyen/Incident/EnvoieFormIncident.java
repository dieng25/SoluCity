package edu.ezip.ing1.pds.services.Citoyen.Incident;



import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Citoyen.CitoyenService;
import edu.ezip.ing1.pds.services.Citoyen.IncidentService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;

public class EnvoieFormIncident implements ActionListener {

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
            citoyenService.insertCitoyen(citoyen);
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }

        Incident incident = new Incident(titre, description, date, categorie, 0, cp, priorite, null, tel, cp);
        IncidentService incidentService = new IncidentService(networkConfig);
        try {
            incidentService.insertIncident(incident);
        } catch (InterruptedException | IOException ex) {
        ex.printStackTrace();
        }

    }

}
