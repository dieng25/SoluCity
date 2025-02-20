package edu.ezip.ing1.pds.business.dto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Field;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DashboardData {
    
    @JsonProperty("total_incident")
    private int totalIncident;
    
    @JsonProperty("incident_en_cours")
    private int incidentEnCours;
    
    @JsonProperty("incident_resolu")
    private int incidentResolu;
    
    @JsonProperty("incident_non_ouvert")
    private int incidentNonOuvert;
    
    @JsonProperty("non_defini")
    private int nonDefini;
    
    @JsonProperty("faible")
    private int faible;
    
    @JsonProperty("moyen")
    private int moyen;
    
    @JsonProperty("haut")
    private int haut;

    public DashboardData() {
    }

    // Getters et Setters

    public int getTotalIncident() {
        return totalIncident;
    }

    public void setTotalIncident(int totalIncident) {
        this.totalIncident = totalIncident;
    }

    public int getIncidentEnCours() {
        return incidentEnCours;
    }

    public void setIncidentEnCours(int incidentEnCours) {
        this.incidentEnCours = incidentEnCours;
    }

    public int getIncidentResolu() {
        return incidentResolu;
    }

    public void setIncidentResolu(int incidentResolu) {
        this.incidentResolu = incidentResolu;
    }

    public int getIncidentNonOuvert() {
        return incidentNonOuvert;
    }

    public void setIncidentNonOuvert(int incidentNonOuvert) {
        this.incidentNonOuvert = incidentNonOuvert;
    }

    public int getNonDefini() {
        return nonDefini;
    }

    public void setNonDefini(int nonDefini) {
        this.nonDefini = nonDefini;
    }

    public int getFaible() {
        return faible;
    }

    public void setFaible(int faible) {
        this.faible = faible;
    }

    public int getMoyen() {
        return moyen;
    }

    public void setMoyen(int moyen) {
        this.moyen = moyen;
    }

    public int getHaut() {
        return haut;
    }

    public void setHaut(int haut) {
        this.haut = haut;
    }

public DashboardData build(final ResultSet resultSet) 
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "total_incident", "incident_en_cours", "incident_resolu", 
                "incident_non_ouvert", "non_defini", "faible", "moyen", "haut");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, totalIncident, incidentEnCours, 
                incidentResolu, incidentNonOuvert, nonDefini, faible, moyen, haut);
    }

    private void setFieldsFromResultSet(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true); // Rendre le champ accessible si nécessaire
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, final int... fieldValues)
            throws SQLException {
        int ix = 0;
        for (final int fieldValue : fieldValues) {
            preparedStatement.setInt(++ix, fieldValue);
        }
        return preparedStatement;
    }
    @Override
    public String toString() {
        return "DashboardData{" +
                "totalIncident=" + totalIncident +
                ", incidentEnCours=" + incidentEnCours +
                ", incidentResolu=" + incidentResolu +
                ", incidentNonOuvert=" + incidentNonOuvert +
                ", nonDefini=" + nonDefini +
                ", faible=" + faible +
                ", moyen=" + moyen +
                ", haut=" + haut +
                '}';
    }
}
