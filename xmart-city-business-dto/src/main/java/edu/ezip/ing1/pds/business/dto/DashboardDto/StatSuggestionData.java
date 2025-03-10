package edu.ezip.ing1.pds.business.dto.DashboardDto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "statSuggestionData")
public class StatSuggestionData {

    private int SuggestionNonVue;
    private int SuggestionEnCours;
    private int SuggestionTraitee;
    private double DelaiVoirie;
    private double DelaiEclairagePublic;
    private double DelaiEspaceVerts;
    private double DelaiProprete;
    private double DelaiAnimauxErrants;
    private double DelaiAutres;
    private String SuggestionTop1;
    private String SuggestionTop2;

    // Getters
    public int getSuggestionNonVue() {
        return SuggestionNonVue;
    }

    public int getSuggestionEnCours() {
        return SuggestionEnCours;
    }

    public int getSuggestionTraitee() {
        return SuggestionTraitee;
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

    public String getSuggestionTop1() {
        return SuggestionTop1;
    }

    public String getSuggestionTop2() {
        return SuggestionTop2;
    }

    // Setters avec annotations @JsonProperty
    @JsonProperty("suggestion_non_vue")
    public void setSuggestionNonVue(int SuggestionNonVue) {
        this.SuggestionNonVue = SuggestionNonVue;
    }

    @JsonProperty("suggestion_en_cours")
    public void setSuggestionEnCours(int SuggestionEnCours) {
        this.SuggestionEnCours = SuggestionEnCours;
    }

    @JsonProperty("suggestion_traitee")
    public void setSuggestionTraitee(int SuggestionTraitee) {
        this.SuggestionTraitee = SuggestionTraitee;
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

    @JsonProperty("suggestion_top1")
    public void setSuggestionTop1(String SuggestionTop1) {
        this.SuggestionTop1 = SuggestionTop1;
    }

    @JsonProperty("suggestion_top2")
    public void setSuggestionTop2(String SuggestionTop2) {
        this.SuggestionTop2 = SuggestionTop2;
    }

    public StatSuggestionData build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "SuggestionNonVue", "SuggestionEnCours", 
                "SuggestionTraitee", "DelaiVoirie", "DelaiEclairagePublic", "DelaiEspaceVerts", 
                "DelaiProprete", "DelaiAnimauxErrants", "DelaiAutres", "SuggestionTop1", "SuggestionTop2");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, SuggestionNonVue, SuggestionEnCours, 
                SuggestionTraitee, DelaiVoirie, DelaiEclairagePublic, DelaiEspaceVerts, 
                DelaiProprete, DelaiAnimauxErrants, DelaiAutres, SuggestionTop1, SuggestionTop2);
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
            }
        }
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "StatSuggestionData{" +
                "SuggestionNonVue=" + SuggestionNonVue +
                ", SuggestionEnCours=" + SuggestionEnCours +
                ", SuggestionTraitee=" + SuggestionTraitee +
                ", DelaiVoirie=" + DelaiVoirie +
                ", DelaiEclairagePublic=" + DelaiEclairagePublic +
                ", DelaiEspaceVerts=" + DelaiEspaceVerts +
                ", DelaiProprete=" + DelaiProprete +
                ", DelaiAnimauxErrants=" + DelaiAnimauxErrants +
                ", DelaiAutres=" + DelaiAutres +
                ", SuggestionTop1='" + SuggestionTop1 + '\'' +
                ", SuggestionTop2='" + SuggestionTop2 + '\'' +
                '}';
    }
}
