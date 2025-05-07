package edu.ezip.ing1.pds.business.dto.DashboardDto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "tableauSpecifiqueData")
public class TableauSpecifiqueData {

    private int IdUrgent;
    private String TitreUrgent;
    private String DescriptionUrgent;
    private String CategorieUrgent;

    public int getIdUrgent() {
        return IdUrgent;
    }

    public String getTitreUrgent() {
        return TitreUrgent;
    }

    public String getDescriptionUrgent() {
        return DescriptionUrgent;
    }

    public String getCategorieUrgent() {
        return CategorieUrgent;
    }

    @JsonProperty("id_urgent")
    public void setIdUrgent(int IdUrgent) {
        this.IdUrgent = IdUrgent;
    }

    @JsonProperty("titre_urgent")
    public void setTitreUrgent(String TitreUrgent) {
        this.TitreUrgent = TitreUrgent;
    }

    @JsonProperty("description_urgent")
    public void setDescriptionUrgent(String DescriptionUrgent) {
        this.DescriptionUrgent = DescriptionUrgent;
    }

    @JsonProperty("categorie_urgent")
    public void setCategorieUrgent(String CategorieUrgent) {
        this.CategorieUrgent = CategorieUrgent;
    }
    
    public TableauSpecifiqueData build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "IdUrgent", "TitreUrgent", "DescriptionUrgent", "CategorieUrgent");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, IdUrgent, TitreUrgent, DescriptionUrgent, CategorieUrgent);
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
            } else if (fieldValue instanceof String) {
                preparedStatement.setString(++ix, (String) fieldValue);
            }
        }
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "TableauSpecifiqueData{" +
                "IdUrgent=" + IdUrgent +
                ", TitreUrgent='" + TitreUrgent + '\'' +
                ", DescriptionUrgent='" + DescriptionUrgent + '\'' +
                ", CategorieUrgent='" + CategorieUrgent + '\'' +
                '}';
    }
}

