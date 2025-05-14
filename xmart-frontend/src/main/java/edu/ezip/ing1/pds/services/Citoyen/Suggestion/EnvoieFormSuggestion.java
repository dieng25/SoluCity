package edu.ezip.ing1.pds.services.Citoyen.Suggestion;

import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Citoyens;
import edu.ezip.ing1.pds.business.dto.Suggestion;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Citoyen.CitoyenService;
import edu.ezip.ing1.pds.services.Citoyen.Connexion.ConfirmationInscription;
import edu.ezip.ing1.pds.services.Citoyen.SuggestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;

public class EnvoieFormSuggestion implements ActionListener {

    private final static String LoggingLabel = "FrontEnd - EnvoieFormSuggestion";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private FormulaireSuggestion form;

    public EnvoieFormSuggestion(FormulaireSuggestion formulaireSuggestion) {
        form = formulaireSuggestion;
    }

    public void actionPerformed(ActionEvent e) {
        if (!ValidFormSuggestion.isValid(form)) {
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



        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);

        Citoyen citoyen = new Citoyen(tel, nom, prenom, email, null);
        CitoyenService citoyenService = new CitoyenService(networkConfig);

        try {
            boolean exists = citoyenService.selectTelExist(tel);
            if (exists) {
                JOptionPane.showMessageDialog(form, "Vous avez déjà un compte. Veuillez vous connecter.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }catch (InterruptedException | IOException | IllegalStateException ex) {
            logger.error("ne peut pas vérifié le numéro de téléphone", ex);
            JOptionPane.showMessageDialog(form, "Erreur de connexion au serveur. Veuillez réessayer plus tard.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            citoyenService.insertCitoyen(citoyen);
        } catch (InterruptedException | IOException ex) {
            logger.error("Erreur lors de l'envoie des données du citoyen", ex);
            JOptionPane.showMessageDialog(form, "Erreur de connexion au serveur. Veuillez réessayer plus tard.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;

        }

        Suggestion suggestion = new Suggestion(titre, description, date, categorie, 0, cp, null,null, tel, cp, categorie);
        SuggestionService suggestionService = new SuggestionService(networkConfig);
        try {
            suggestionService.insertSuggestion(suggestion);
        } catch (InterruptedException | IOException ex) {
            logger.error("Erreur lors de l'envoie des données ticket suggestion", ex);
            JOptionPane.showMessageDialog(form, "Erreur de connexion au serveur. Veuillez réessayer plus tard. Suggestion non envoyé.",
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
