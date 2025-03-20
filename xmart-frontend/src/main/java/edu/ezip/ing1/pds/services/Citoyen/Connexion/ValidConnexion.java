package edu.ezip.ing1.pds.services.Citoyen.Connexion;

import javax.swing.*;

public class ValidConnexion {

    public static boolean isConnectValid(InterfaceConnexion connect){
        if ( connect.getTelephone().isEmpty() ||
                connect.getIdentifiant().isEmpty() ) {
            JOptionPane.showMessageDialog(connect, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!connect.getTelephone().matches("0\\d{9}")) {
            JOptionPane.showMessageDialog(connect, "Le numéro de téléphone doit commencer par 0 et contenir 10 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;

    }

}
