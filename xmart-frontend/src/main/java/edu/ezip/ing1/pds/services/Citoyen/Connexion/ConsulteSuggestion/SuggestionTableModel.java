package edu.ezip.ing1.pds.services.Citoyen.Connexion.ConsulteSuggestion;

import edu.ezip.ing1.pds.business.dto.Suggestion;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SuggestionTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {
            "Numéro du Ticket", "Code Postal", "Catégorie", "Titre", "Date de Création", "Statut", "Date de Clôture"
    };

    private List<Suggestion> suggestions;

    public SuggestionTableModel(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    @Override
    public int getRowCount() {
        return suggestions.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Suggestion suggestion = suggestions.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return suggestion.getIdTicket();
            case 1:
                return suggestion.getCP_Ticket();
            case 2:
                return suggestion.getCategorie();
            case 3:
                return suggestion.getTitre();
            case 4:
                return suggestion.getDate_creation();
            case 5:
                return getStatut(suggestion.getStatut());
            case 6:
                return suggestion.getDate_cloture();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    private String getStatut(int statut) {
        switch (statut) {
            case 0:
                return "Ouvert";
            case 1:
                return "En cours de traitement";
            case 2:
                return "Accepté";
            case 3:
                return "Refusé";
            default:
                return "Inconnu";
        }
    }

}
