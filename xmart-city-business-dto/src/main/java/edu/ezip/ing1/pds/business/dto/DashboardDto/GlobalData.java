package edu.ezip.ing1.pds.business.dto.DashboardDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Field;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonRootName(value = "globalData")
public class GlobalData {

    private int totalIncidents;
    private int totalSuggestion;
    private int suggestionCatVoirie;
    private int suggestionCatEclairagePublic;
    private int suggestionCatEspaceVerts;
    private int suggestionCatProprete;
    private int suggestionCatAnimauxErrants;
    private int suggestionCatAutres;
    private int incidentCatVoirie;
    private int incidentCatEclairagePublic;
    private int incidentCatEspaceVerts;
    private int incidentCatProprete;
    private int incidentCatAnimauxErrants;
    private int incidentCatAutres;
    private int incidentLevelBas;
    private int incidentLevelMoyen;
    private int incidentLevelHaut;

    public GlobalData() {
    }

    // Getters et Setters
    
  public int getTotalIncidents() {
    return totalIncidents;
}
@JsonProperty("total_incidents")
public void setTotalIncidents(int totalIncidents) {
    this.totalIncidents = totalIncidents;
}

public int getTotalSuggestion() {
    return totalSuggestion;
}
@JsonProperty("total_suggestion")
public void setTotalSuggestion(int totalSuggestion) {
    this.totalSuggestion = totalSuggestion;
}

public int getSuggestionCatVoirie() {
    return suggestionCatVoirie;
}
@JsonProperty("suggestion_cat_voirie")
public void setSuggestionCatVoirie(int suggestionCatVoirie) {
    this.suggestionCatVoirie = suggestionCatVoirie;
}

public int getSuggestionCatEclairagePublic() {
    return suggestionCatEclairagePublic;
}
@JsonProperty("suggestion_cat_eclairage_public")
public void setSuggestionCatEclairagePublic(int suggestionCatEclairagePublic) {
    this.suggestionCatEclairagePublic = suggestionCatEclairagePublic;
}

public int getSuggestionCatEspaceVerts() {
    return suggestionCatEspaceVerts;
}
@JsonProperty("suggestion_cat_espace_verts")
public void setSuggestionCatEspaceVerts(int suggestionCatEspaceVerts) {
    this.suggestionCatEspaceVerts = suggestionCatEspaceVerts;
}

public int getSuggestionCatProprete() {
    return suggestionCatProprete;
}
@JsonProperty("suggestion_cat_proprete")
public void setSuggestionCatProprete(int suggestionCatProprete) {
    this.suggestionCatProprete = suggestionCatProprete;
}

public int getSuggestionCatAnimauxErrants() {
    return suggestionCatAnimauxErrants;
}
@JsonProperty("suggestion_cat_animaux_errants")
public void setSuggestionCatAnimauxErrants(int suggestionCatAnimauxErrants) {
    this.suggestionCatAnimauxErrants = suggestionCatAnimauxErrants;
}

public int getSuggestionCatAutres() {
    return suggestionCatAutres;
}
@JsonProperty("suggestion_cat_autres")
public void setSuggestionCatAutres(int suggestionCatAutres) {
    this.suggestionCatAutres = suggestionCatAutres;
}

public int getIncidentCatVoirie() {
    return incidentCatVoirie;
}
@JsonProperty("incident_cat_voirie")
public void setIncidentCatVoirie(int incidentCatVoirie) {
    this.incidentCatVoirie = incidentCatVoirie;
}

public int getIncidentCatEclairagePublic() {
    return incidentCatEclairagePublic;
}
@JsonProperty("incident_cat_eclairage_public")
public void setIncidentCatEclairagePublic(int incidentCatEclairagePublic) {
    this.incidentCatEclairagePublic = incidentCatEclairagePublic;
}

public int getIncidentCatEspaceVerts() {
    return incidentCatEspaceVerts;
}
@JsonProperty("incident_cat_espace_verts")
public void setIncidentCatEspaceVerts(int incidentCatEspaceVerts) {
    this.incidentCatEspaceVerts = incidentCatEspaceVerts;
}

public int getIncidentCatProprete() {
    return incidentCatProprete;
}
@JsonProperty("incident_cat_proprete")
public void setIncidentCatProprete(int incidentCatProprete) {
    this.incidentCatProprete = incidentCatProprete;
}

public int getIncidentCatAnimauxErrants() {
    return incidentCatAnimauxErrants;
}
@JsonProperty("incident_cat_animaux_errants")
public void setIncidentCatAnimauxErrants(int incidentCatAnimauxErrants) {
    this.incidentCatAnimauxErrants = incidentCatAnimauxErrants;
}

public int getIncidentCatAutres() {
    return incidentCatAutres;
}
@JsonProperty("incident_cat_autres")
public void setIncidentCatAutres(int incidentCatAutres) {
    this.incidentCatAutres = incidentCatAutres;
}

public int getIncidentLevelBas() {
    return incidentLevelBas;
}
@JsonProperty("incident_level_bas")
public void setIncidentLevelBas(int incidentLevelBas) {
    this.incidentLevelBas = incidentLevelBas;
}

public int getIncidentLevelMoyen() {
    return incidentLevelMoyen;
}
@JsonProperty("incident_level_moyen")
public void setIncidentLevelMoyen(int incidentLevelMoyen) {
    this.incidentLevelMoyen = incidentLevelMoyen;
}

public int getIncidentLevelHaut() {
    return incidentLevelHaut;
}
@JsonProperty("incident_level_haut")
public void setIncidentLevelHaut(int incidentLevelHaut) {
    this.incidentLevelHaut = incidentLevelHaut;
}

    public GlobalData build(final ResultSet resultSet) 
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "total_incidents", "total_suggestion", 
                "suggestion_cat_voirie", "suggestion_cat_eclairage_public", "suggestion_cat_espace_verts", 
                "suggestion_cat_proprete", "suggestion_cat_animaux_errants", "suggestion_cat_autres", 
                "incident_cat_voirie", "incident_cat_eclairage_public", "incident_cat_espace_verts", 
                "incident_cat_proprete", "incident_cat_animaux_errants", "incident_cat_autres", 
                "incident_level_bas", "incident_level_moyen", "incident_level_haut");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, totalIncidents, totalSuggestion, 
                suggestionCatVoirie, suggestionCatEclairagePublic, suggestionCatEspaceVerts, 
                suggestionCatProprete, suggestionCatAnimauxErrants, suggestionCatAutres, 
                incidentCatVoirie, incidentCatEclairagePublic, incidentCatEspaceVerts, 
                incidentCatProprete, incidentCatAnimauxErrants, incidentCatAutres, 
                incidentLevelBas, incidentLevelMoyen, incidentLevelHaut);
    }

    private void setFieldsFromResultSet(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
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
        return "GlobalData{" +
                "totalIncidents=" + totalIncidents +
                ", totalSuggestion=" + totalSuggestion +
                ", suggestionCatVoirie=" + suggestionCatVoirie +
                ", suggestionCatEclairagePublic=" + suggestionCatEclairagePublic +
                ", suggestionCatEspaceVerts=" + suggestionCatEspaceVerts +
                ", suggestionCatProprete=" + suggestionCatProprete +
                ", suggestionCatAnimauxErrants=" + suggestionCatAnimauxErrants +
                ", suggestionCatAutres=" + suggestionCatAutres +
                ", incidentCatVoirie=" + incidentCatVoirie +
                ", incidentCatEclairagePublic=" + incidentCatEclairagePublic +
                ", incidentCatEspaceVerts=" + incidentCatEspaceVerts +
                ", incidentCatProprete=" + incidentCatProprete +
                ", incidentCatAnimauxErrants=" + incidentCatAnimauxErrants +
                ", incidentCatAutres=" + incidentCatAutres +
                ", incidentLevelBas=" + incidentLevelBas +
                ", incidentLevelMoyen=" + incidentLevelMoyen +
                ", incidentLevelHaut=" + incidentLevelHaut +
                '}';
    }
}
