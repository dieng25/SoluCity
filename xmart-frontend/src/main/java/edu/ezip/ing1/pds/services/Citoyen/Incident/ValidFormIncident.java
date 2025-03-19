package edu.ezip.ing1.pds.services.Citoyen.Incident;

import javax.swing.*;

public class ValidFormIncident {

    public static boolean isValid(FormulaireIncident form){
        if (form.getNom().isEmpty() || form.getPrenom().isEmpty() || form.getTel().isEmpty() ||
                form.getEmail().isEmpty() || form.getCodePostal().isEmpty() || form.getTitre().isEmpty() ||
                form.getDescription().isEmpty()) {
            JOptionPane.showMessageDialog(form, "Tous les champs doivent être remplis.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!form.getTel().matches("0\\d{9}")) {
            JOptionPane.showMessageDialog(form, "Le numéro de téléphone doit commencer par 0 et contenir 10 chiffres.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!form.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(form, "L'email est incorrect.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
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
