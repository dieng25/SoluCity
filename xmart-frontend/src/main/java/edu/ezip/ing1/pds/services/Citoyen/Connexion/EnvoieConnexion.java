package edu.ezip.ing1.pds.services.Citoyen.Connexion;

import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Citoyen.CitoyenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EnvoieConnexion implements ActionListener {
    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private InterfaceConnexion connect;

    public EnvoieConnexion(InterfaceConnexion interfaceConnexion) {
        connect = interfaceConnexion;
    }

    public void actionPerformed(ActionEvent e) {
        if (!ValidConnexion.isConnectValid(connect)) {
            return;
        }

        String Telephone = connect.getTelephone();
        String id = connect.getIdentifiant();

        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);

        CitoyenService citoyenService = new CitoyenService(networkConfig);

        try{
            Citoyen citoyen = citoyenService.selectConnexion(Telephone, id);
            if (citoyen == null) {
                logger.error("ne trouve pas de citoyen", e);
                JOptionPane.showMessageDialog(connect, "Numéro de téléphone ou identifiant incorrect.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                connect.dispose();
                new AccueilConnexion(citoyen);

            }
        }catch (InterruptedException | IOException ex){
            logger.error("erreur connexion serveur", ex);
            JOptionPane.showMessageDialog(connect, "Erreur de connexion au serveur. Veuillez réessayer plus tard.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

}
