package edu.ezip.ing1.pds.services.Citoyen.Incident;

public class ValidFormIncident {

    public static boolean isValid(FormulaireIncident form){
        if (form.getNom().isEmpty() || form.getPrenom().isEmpty() || form.getTel().isEmpty() ||
                form.getEmail().isEmpty() || form.getCodePostal().isEmpty() || form.getTitre().isEmpty() ||
                form.getDescription().isEmpty()) {
            return false;
        }

        if (!form.getTel().matches("0\\d{9}")) {
            return false;
        }

        if (!form.getCodePostal().matches("\\d{5}")) {
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
