package edu.ezip.ing1.pds.business.dto.DashboardDto;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ezip.ing1.pds.business.dto.Incident;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonRootName(value = "statIncidentData")
public class StatIncidentData {

    private int IncidentNonResolu;
    private int IncidentEnCours;
    private int IncidentResolu;
    private double DelaiVoirie;
    private double DelaiEclairagePublic;
    private double DelaiEspaceVerts;
    private double DelaiProprete;
    private double DelaiAnimauxErrants;
    private double DelaiAutres;
    private String IncidentTop1;
    private String IncidentTop2;
    private List<Incident> incidentsUrgents;

    // Getters
    public int getIncidentNonResolu() {
        return IncidentNonResolu;
    }

    public int getIncidentEnCours() {
        return IncidentEnCours;
    }

    public int getIncidentResolu() {
        return IncidentResolu;
    }

    public double getDelaiVoirie() {
        return DelaiVoirie;
    }

    public double getDelaiEclairagePublic() {
        return DelaiEclairagePublic;
    }

    public double getDelaiEspaceVerts() {
        return DelaiEspaceVerts;
    }

    public double getDelaiProprete() {
        return DelaiProprete;
    }

    public double getDelaiAnimauxErrants() {
        return DelaiAnimauxErrants;
    }

    public double getDelaiAutres() {
        return DelaiAutres;
    }

    public String getIncidentTop1() {
        return IncidentTop1;
    }

    public String getIncidentTop2() {
        return IncidentTop2;
    }

    public List<Incident> getIncidentsUrgents() {
        return incidentsUrgents;
    }

    @JsonProperty("incident_non_resolu")
    public void setIncidentNonResolu(int IncidentNonResolu) {
        this.IncidentNonResolu = IncidentNonResolu;
    }

    @JsonProperty("incident_en_cours")
    public void setIncidentEnCours(int IncidentEnCours) {
        this.IncidentEnCours = IncidentEnCours;
    }

    @JsonProperty("incident_resolu")
    public void setIncidentResolu(int IncidentResolu) {
        this.IncidentResolu = IncidentResolu;
    }

    @JsonProperty("delai_voirie")
    public void setDelaiVoirie(double DelaiVoirie) {
        this.DelaiVoirie = DelaiVoirie;
    }

    @JsonProperty("delai_eclairage_public")
    public void setDelaiEclairagePublic(double DelaiEclairagePublic) {
        this.DelaiEclairagePublic = DelaiEclairagePublic;
    }

    @JsonProperty("delai_espace_verts")
    public void setDelaiEspaceVerts(double DelaiEspaceVerts) {
        this.DelaiEspaceVerts = DelaiEspaceVerts;
    }

    @JsonProperty("delai_proprete")
    public void setDelaiProprete(double DelaiProprete) {
        this.DelaiProprete = DelaiProprete;
    }

    @JsonProperty("delai_animaux_errants")
    public void setDelaiAnimauxErrants(double DelaiAnimauxErrants) {
        this.DelaiAnimauxErrants = DelaiAnimauxErrants;
    }

    @JsonProperty("delai_autres")
    public void setDelaiAutres(double DelaiAutres) {
        this.DelaiAutres = DelaiAutres;
    }

    @JsonProperty("incident_top1")
    public void setIncidentTop1(String IncidentTop1) {
        this.IncidentTop1 = IncidentTop1;
    }

    @JsonProperty("incident_top2")
    public void setIncidentTop2(String IncidentTop2) {
        this.IncidentTop2 = IncidentTop2;
    }

    @JsonProperty("incident_urgents")
    public void setIncidentsUrgents(List<Incident> incidentsUrgents) {
        this.incidentsUrgents = incidentsUrgents;
    }

    public StatIncidentData build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "IncidentNonResolu", "IncidentEnCours", 
                "IncidentResolu", "DelaiVoirie",  "DelaiEclairagePublic", "DelaiEspaceVerts", 
                "DelaiProprete", "DelaiAnimauxErrants", "DelaiAutres", "IncidentTop1", "IncidentTop2, incidentsUrgents");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, IncidentNonResolu, IncidentEnCours, 
                IncidentResolu, DelaiVoirie, DelaiEclairagePublic, DelaiEspaceVerts, DelaiProprete, DelaiAnimauxErrants, DelaiAutres, IncidentTop1, IncidentTop2, incidentsUrgents);
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
            if (fieldValue instanceof Integer) {
                preparedStatement.setInt(++ix, (Integer) fieldValue);
            } else if (fieldValue instanceof Double) {
                preparedStatement.setDouble(++ix, (Double) fieldValue);
            } else if (fieldValue instanceof String) {
                preparedStatement.setString(++ix, (String) fieldValue);
            } else if (fieldValue instanceof List<?>) {
                List<?> list = (List<?>) fieldValue;
                for (Object item : list) {
                    if (item instanceof Integer) {
                        preparedStatement.setInt(++ix, (Integer) item);
                    } else if (item instanceof String) {
                        preparedStatement.setString(++ix, (String) item);
                    }
                }
            }
        }
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "StatIncidentData{" +
                "IncidentNonResolu=" + IncidentNonResolu +
                ", IncidentEnCours=" + IncidentEnCours +
                ", IncidentResolu=" + IncidentResolu +
                ", DelaiVoirie=" + DelaiVoirie +
                ", DelaiEclairagePublic=" + DelaiEclairagePublic +
                ", DelaiEspaceVerts=" + DelaiEspaceVerts +
                ", DelaiProprete=" + DelaiProprete +
                ", DelaiAnimauxErrants=" + DelaiAnimauxErrants +
                ", DelaiAutres=" + DelaiAutres +
                ", IncidentTop1='" + IncidentTop1 + '\'' +
                ", IncidentTop2='" + IncidentTop2 + '\'' +
                ", incidentsUrgents='" + incidentsUrgents + '\'' +
                '}';
    }
}
