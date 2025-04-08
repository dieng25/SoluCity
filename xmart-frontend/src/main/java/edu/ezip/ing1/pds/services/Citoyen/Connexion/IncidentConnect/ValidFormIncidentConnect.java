package edu.ezip.ing1.pds.services.Citoyen.Connexion.IncidentConnect;

import javax.swing.*;

public class ValidFormIncidentConnect {

    public static boolean isValid(FormIncidentConnect form){
        if (form.getCodePostal().isEmpty() || form.getTitre().isEmpty() || form.getDescription().isEmpty()) {
            JOptionPane.showMessageDialog(form, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;

    }

    public static int getPrioriteIndex(String priorite) {
        switch (priorite) {
            case "Faible":
                return 1;
            case "Moyen":
                return 2;
            case "Élevée":
                return 3;
            default:
                return 0;
        }
    }
}
