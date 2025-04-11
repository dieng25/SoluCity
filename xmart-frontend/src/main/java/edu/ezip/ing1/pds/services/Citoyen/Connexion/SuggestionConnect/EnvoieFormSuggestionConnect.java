package edu.ezip.ing1.pds.services.Citoyen.Connexion.SuggestionConnect;

import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.Suggestion;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Citoyen.Connexion.AccueilConnexion;
import edu.ezip.ing1.pds.services.Citoyen.Connexion.IncidentConnect.FormIncidentConnect;
import edu.ezip.ing1.pds.services.Citoyen.Connexion.IncidentConnect.ValidFormIncidentConnect;
import edu.ezip.ing1.pds.services.Citoyen.SuggestionService;
import edu.ezip.ing1.pds.services.Citoyen.Suggestion.EnvoieFormSuggestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;

public class EnvoieFormSuggestionConnect implements ActionListener {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private FormSuggestionConnect form;
    private Citoyen citoyen;

    public EnvoieFormSuggestionConnect(FormSuggestionConnect formSuggestionConnect, Citoyen citoyen) {
        form = formSuggestionConnect;
        this.citoyen = citoyen;
    }

    public void actionPerformed(ActionEvent e) {
        if (!ValidFormSuggestionConnect.isValid(form)) {
            return;
        }

        String categorie = form.getCategorie();
        String tel = form.getTel();
        String cp = form.getCodePostal();
        String titre = form.getTitre();
        String description = form.getDescription();
        Date date = form.getDate();


        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);

        Suggestion suggestion = new Suggestion(titre, description, date, categorie, 0, cp, null,null, tel, cp, categorie);
        SuggestionService suggestionService = new SuggestionService(networkConfig);

        try {
            suggestionService.insertSuggestion(suggestion);
            JOptionPane.showMessageDialog(form, "Le Ticket Suggestion a bien été envoyé.  ",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);
            form.dispose();
            new AccueilConnexion(citoyen).setVisible(true);
        } catch (InterruptedException | IOException ex) {
            logger.error("Erreur lors de l'envoie des données ticket suggestion", e);
            JOptionPane.showMessageDialog(form, "Erreur de connexion au serveur. Veuillez réessayer plus tard.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }



    }
}
