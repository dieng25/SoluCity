package edu.ezip.ing1.pds.services.Citoyen.Connexion.SuggestionConnect;


import javax.swing.*;

public class ValidFormSuggestionConnect {

    public static boolean isValid(FormSuggestionConnect form){
        if (form.getTitre().isEmpty() || form.getDescription().isEmpty()) {
            JOptionPane.showMessageDialog(form, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;

    }

}
