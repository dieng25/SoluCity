package edu.ezip.ing1.pds.business.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@JsonRootName(value = "suggestion")
public class Suggestion {
    private int IdTicket;
    private String Titre;
    private String Description;
    private Date date_creation;
    private String Categorie;
    private int Statut;
    private String CP_Ticket;
    private Date date_cloture;
    private String Commentaire;
    private String telNum;
    private String CP;
    private String CategorieSuggestion;

    public Suggestion() {
        this.Statut = 0;
    }


    public Suggestion(String Titre, String Description, Date date_creation, String Categorie, int Statut, String CP_Ticket, Date date_cloture, String Commentaire, String telNum, String CP, String CategorieSuggestion) {
        this.Titre = Titre;
        this.Description = Description;
        this.date_creation = date_creation;
        this.Categorie = Categorie;
        this.Statut = Statut;
        this.CP_Ticket = CP_Ticket;
        this.date_cloture = date_cloture;
        this.Commentaire = Commentaire;
        this.telNum = telNum;
        this.CP = CP;
        this.CategorieSuggestion = CategorieSuggestion;
    }


    public int getIdTicket() {
        return IdTicket;
    }

    public String getTitre() {
        return Titre;
    }

    public String getDescription() {
        return Description;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public String getCategorie() {
        return Categorie;
    }

    public int getStatut() {
        return Statut;
    }

    public String getCP_Ticket() {
        return CP_Ticket;
    }

    public Date getDate_cloture() {
        return date_cloture;
    }

    public String getCommentaire() {
        return Commentaire;
    }

    public String getTelNum() {
        return telNum;
    }

    public String getCP() {
        return CP;
    }

    public String getCategorieSuggestion() {
        return CategorieSuggestion;
    }

    @JsonProperty("suggestion_IdTicket")
    public void setIdTicket(int IdTicket) {
        this.IdTicket = IdTicket;
    }

    @JsonProperty("suggestion_Titre")
    public void setTitre(String Titre) {
        this.Titre = Titre;
    }

    @JsonProperty("suggestion_Description")
    public void setDescription(String Description) {
        this.Description = Description;
    }

    @JsonProperty("suggestion_DateCreation")
    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    @JsonProperty("suggestion_Categorie")
    public void setCategorie(String Categorie) {
        this.Categorie = Categorie;
    }

    @JsonProperty("suggestion_Statut")
    public void setStatut(int Statut) {
        this.Statut = Statut;
    }

    @JsonProperty("suggestion_CPTicket")
    public void setCP_Ticket(String CP_Ticket) {
        this.CP_Ticket = CP_Ticket;
    }

    @JsonProperty("suggestion_DateCloture")
    public void setDate_cloture(Date date_cloture) {
        this.date_cloture = date_cloture;
    }

    @JsonProperty("suggestion_Commentaire")
    public void setCommentaire(String Commentaire) {
        this.Commentaire = Commentaire;
    }

    @JsonProperty("suggestion_TelNum")
    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    @JsonProperty("suggestion_CP")
    public void setCP(String CP) {
        this.CP = CP;
    }

    @JsonProperty("suggestion_CategorieSuggestion")
    public void setCategorieSuggestion(String CategorieSuggestion) {
        this.CategorieSuggestion = CategorieSuggestion;
    }


    public final Suggestion build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet,  "Id_ticket", "Titre", "Description", "date_creation", "Categorie", "Statut", "CodePostal_ticket", "date_cloture", "Commentaire",  "Code_Postal", "tel_num", "CategorieIncident");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, IdTicket, Titre, Description, date_creation, Categorie, Statut, CP_Ticket, date_cloture, Commentaire, telNum, CP, CategorieSuggestion);
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
        return "Suggestion{" +
                "IdTicket=" + IdTicket +
                "Titre='" + Titre + '\'' +
                ", Description='" + Description + '\'' +
                ", date_creation='" + date_creation + '\'' +
                ", Categorie='" + Categorie + '\'' +
                ", Statut=" + Statut + '\'' +
                ", CP_Ticket=" + CP_Ticket + '\'' +
                ", date_cloture=" + date_cloture +
                ", Commentaire='" + Commentaire + '\'' +
                '}';
    }
}



