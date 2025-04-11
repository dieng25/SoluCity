package edu.ezip.ing1.pds.business.dto.DashboardDto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "statMairieData")
public class StatMairieData {

    private String IncidentMairieTop1;
    private String IncidentMairieTop2;
    private String IncidentMairieTop3;
    private String SuggestionMairieTop1;
    private String SuggestionMairieTop2;
    private String SuggestionMairieTop3;
    private int delaiIncidentMairie;
    private int delaiSuggestionMairie;

    // Getters
    public String getIncidentMairieTop1() {
        return IncidentMairieTop1;
    }

    public String getIncidentMairieTop2() {
        return IncidentMairieTop2;
    }

    public String getIncidentMairieTop3() {
        return IncidentMairieTop3;
    }

    public String getSuggestionMairieTop1() {
        return SuggestionMairieTop1;
    }

    public String getSuggestionMairieTop2() {
        return SuggestionMairieTop2;
    }

    public String getSuggestionMairieTop3() {
        return SuggestionMairieTop3;
    }

    public int getDelaiIncidentMairie() {
        return delaiIncidentMairie;
    }

    public int getDelaiSuggestionMairie() {
        return delaiSuggestionMairie;
    }

    // Setters avec annotations @JsonProperty
    @JsonProperty("incident_mairie_top1")
    public void setIncidentMairieTop1(String IncidentMairieTop1) {
        this.IncidentMairieTop1 = IncidentMairieTop1;
    }

    @JsonProperty("incident_mairie_top2")
    public void setIncidentMairieTop2(String IncidentMairieTop2) {
        this.IncidentMairieTop2 = IncidentMairieTop2;
    }

    @JsonProperty("incident_mairie_top3")
    public void setIncidentMairieTop3(String IncidentMairieTop3) {
        this.IncidentMairieTop3 = IncidentMairieTop3;
    }

    @JsonProperty("suggestion_mairie_top1")
    public void setSuggestionMairieTop1(String SuggestionMairieTop1) {
        this.SuggestionMairieTop1 = SuggestionMairieTop1;
    }

    @JsonProperty("suggestion_mairie_top2")
    public void setSuggestionMairieTop2(String SuggestionMairieTop2) {
        this.SuggestionMairieTop2 = SuggestionMairieTop2;
    }

    @JsonProperty("suggestion_mairie_top3")
    public void setSuggestionMairieTop3(String SuggestionMairieTop3) {
        this.SuggestionMairieTop3 = SuggestionMairieTop3;
    }

    @JsonProperty("delai_incident_mairie")
    public void setDelaiIncidentMairie(int delaiIncidentMairie) {
        this.delaiIncidentMairie = delaiIncidentMairie;
    }

    @JsonProperty("delai_suggestion_mairie")
    public void setDelaiSuggestionMairie(int delaiSuggestionMairie) {
        this.delaiSuggestionMairie = delaiSuggestionMairie;
    }
    
    // Construction depuis un ResultSet
    public StatMairieData build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "IncidentMairieTop1", "IncidentMairieTop2", "IncidentMairieTop3", 
                "SuggestionMairieTop1", "SuggestionMairieTop2", "SuggestionMairieTop3");
        return this;
    }

    // Construction d'un PreparedStatement
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, IncidentMairieTop1, IncidentMairieTop2, IncidentMairieTop3, 
                SuggestionMairieTop1, SuggestionMairieTop2, SuggestionMairieTop3);
    }

    private void setFieldsFromResultSet(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, final Object... fieldValues)
            throws SQLException {
        int ix = 0;
        for (final Object fieldValue : fieldValues) {
            if (fieldValue instanceof String) {
                preparedStatement.setString(++ix, (String) fieldValue);
            }
        }
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "StatMairieData{" +
                "IncidentMairieTop1='" + IncidentMairieTop1 + '\'' +
                ", IncidentMairieTop2='" + IncidentMairieTop2 + '\'' +
                ", IncidentMairieTop3='" + IncidentMairieTop3 + '\'' +
                ", SuggestionMairieTop1='" + SuggestionMairieTop1 + '\'' +
                ", SuggestionMairieTop2='" + SuggestionMairieTop2 + '\'' +
                ", SuggestionMairieTop3='" + SuggestionMairieTop3 + '\'' +
                '}';
    }
}
