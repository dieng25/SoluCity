package edu.ezip.ing1.pds.services.Citoyen.Connexion.ConsulteIncident;

import edu.ezip.ing1.pds.business.dto.Incident;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class IncidentTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {
            "Numéro du Ticket", "Code Postal", "Catégorie", "Titre", "Date de Création", "Statut", "Date de Clôture"
    };

    private List<Incident> incidents;

    public IncidentTableModel(List<Incident> incidents) {
        this.incidents = incidents;
    }

    @Override
    public int getRowCount() {
        return incidents.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Incident incident = incidents.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return incident.getIdTicket();
            case 1:
                return incident.getCP_Ticket();
            case 2:
                return incident.getCategorie();
            case 3:
                return incident.getTitre();
            case 4:
                return incident.getDate_creation();
            case 5:
                return getStatut(incident.getStatut());
            case 6:
                return incident.getDate_cloture();
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
                return "Fermé";
            default:
                return "Inconnu";
        }
    }
}