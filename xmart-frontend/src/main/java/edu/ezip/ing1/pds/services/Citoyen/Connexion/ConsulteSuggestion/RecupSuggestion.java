package edu.ezip.ing1.pds.services.Citoyen.Connexion.ConsulteSuggestion;

import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Suggestion;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Citoyen.SuggestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class RecupSuggestion implements ActionListener {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private Citoyen citoyen;


    public RecupSuggestion(Citoyen citoyen) {
        this.citoyen = citoyen;
    }

    public void actionPerformed(ActionEvent e) {
        String tel = citoyen.getTelNum();

        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        SuggestionService suggestionService = new SuggestionService(networkConfig);

        try {
            List<Suggestion> suggestions = suggestionService.selectSuggestionsByTel(tel);
            if (suggestions.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Aucune suggestion trouvé pour cet utilisateur.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                new SuggestionConsulte(suggestions, citoyen);
            }

        } catch (InterruptedException | IOException ex) {
            logger.error("Erreur lors de la récupération des suggestions", ex);
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des suggestions.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

}
