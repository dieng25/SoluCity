package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "incident")
public class Incident {
    private String Titre;
    private String Description;
    private String Date;
    private String Categorie;
    private int Statut;
    private int CP_Ticket;
    private int Priorite;

    public Incident() {
            this.Statut = 0;
    }

    public Incident(String Titre, String Description, String Date, String Categorie, int Statut, int CP_Ticket, int Priorite) {
        this.Titre = Titre;
        this.Description = Description;
        this.Date = Date;
        this.Categorie = Categorie;
        this.Statut = Statut;
        this.CP_Ticket = CP_Ticket;
        this.Priorite = Priorite;
    }

    public String getTitre() {
        return Titre;
    }

    @JsonProperty("incident_Titre")
    public void setTitre(String Titre) {
        this.Titre = Titre;
    }

    public String getDescription() {
        return Description;
    }

    @JsonProperty("incident_Description")
    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDate() {
        return Date;
    }

    @JsonProperty("incident_Date")
    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getCategorie() {
        return Categorie;
    }

    @JsonProperty("incident_Categorie")
    public void setCategorie(String Categorie) {
        this.Categorie = Categorie;
    }

    public int getStatut() {
        return Statut;
    }

    @JsonProperty("incident_statut")
    public void setStatut(int Statut) {
        this.Statut = Statut;
    }

    public int getCP_Ticket() {
        return CP_Ticket;
    }

    @JsonProperty("incident_CPTicket")
    public void setCP_Ticket(int CP_Ticket) {
        this.CP_Ticket = CP_Ticket;
    }

    public int getPriorite() {
        return Priorite;
    }

    @JsonProperty("incident_Priorite")
    public void setPriorite(int Priorite) {
        this.Priorite = Priorite;
    }

    public final Incident build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet,  "Titre", "Description", "date_emis", "Catégorie", "statut", "CodePostal_ticket", "Priorité");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, Titre, Description, Date, Categorie, Statut, CP_Ticket, Priorite);
    }

    private void setFieldsFromResultSet(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, Object... values)
            throws SQLException {
        int ix = 0;
        for (Object value : values) {
            preparedStatement.setObject(++ix, value);
        }
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "Titre='" + Titre + '\'' +
                ", Description='" + Description + '\'' +
                ", Date='" + Date + '\'' +
                ", Categorie='" + Categorie + '\'' +
                ", Statut=" + Statut +
                ", CP_Ticket=" + CP_Ticket +
                ", Priorite=" + Priorite +
                '}';
    }
}
