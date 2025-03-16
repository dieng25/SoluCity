package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "incident")
public class Incident {
    private int IdTicket;
    private String Titre;
    private String Description;
    private Date date_creation;
    private String Categorie;
    private int Statut;
    private String CP_Ticket;
    private int Priorite;
    private Date date_cloture;
    private String telNum;
    private String CP;


    public Incident() {
        this.Statut = 0;
    }

    public Incident(String Titre, String Description, Date date_creation, String Categorie, int Statut, String CP_Ticket, int Priorite, Date date_cloture, String telNum, String CP) {
        this.Titre = Titre;
        this.Description = Description;
        this.date_creation = date_creation;
        this.Categorie = Categorie;
        this.Statut = Statut;
        this.CP_Ticket = CP_Ticket;
        this.Priorite = Priorite;
        this.date_cloture = date_cloture;
        this.telNum = telNum;
        this.CP = CP;

    }

    public int getIdTicket() {
        return IdTicket;
    }

    @JsonProperty("incident_IdTicket")
    public void setIdTicket(int IdTicket) {
        this.IdTicket = IdTicket;
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

    public Date getDate_creation() {
        return date_creation;
    }

    @JsonProperty("incident_DateCreation")
    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
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

    public String getCP_Ticket() {
        return CP_Ticket;
    }

    @JsonProperty("incident_CPTicket")
    public void setCP_Ticket(String CP_Ticket) {
        this.CP_Ticket = CP_Ticket;
    }

    public int getPriorite() {
        return Priorite;
    }

    @JsonProperty("incident_Priorite")
    public void setPriorite(int Priorite) {
        this.Priorite = Priorite;
    }

    public Date getDate_cloture() {
        return date_cloture;
    }

    @JsonProperty("incident_DateCloture")
    public void setDate_cloture(Date date_cloture) {
        this.date_cloture = date_cloture;
    }

    public String getTelNum() {
        return telNum;
    }

    @JsonProperty("incident_TelNum")
    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getCP() {
        return CP;
    }

    @JsonProperty("incident_CP")
    public void setCP(String CP) {
        this.CP = CP;
    }

    public final Incident build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet,  "Id_ticket", "Titre", "Description", "date_creation", "Categorie", "Statut", "CodePostal_ticket", "Priorite", "date_cloture", "tel_num", "Code_Postal");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, IdTicket, Titre, Description, date_creation, Categorie, Statut, CP_Ticket, Priorite, date_cloture, telNum, CP);
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
                "IdTicket=" + IdTicket +
                "Titre='" + Titre + '\'' +
                ", Description='" + Description + '\'' +
                ", date_creation='" + date_creation + '\'' +
                ", Categorie='" + Categorie + '\'' +
                ", Statut=" + Statut + '\'' +
                ", CP_Ticket=" + CP_Ticket + '\'' +
                ", Priorite=" + Priorite + '\'' +
                ", date_cloture=" + date_cloture +
                '}';
    }
}