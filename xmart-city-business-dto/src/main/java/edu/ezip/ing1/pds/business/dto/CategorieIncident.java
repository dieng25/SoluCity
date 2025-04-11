package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "categorieI")
public class CategorieIncident {
    private String CategorieIncident;

    public CategorieIncident() {}

    public CategorieIncident(String CategorieIncident) {
        this.CategorieIncident = CategorieIncident;
    }

    public String getCategorieIncident() {
        return CategorieIncident;
    }

    @JsonProperty("categorieI_CategorieIncident")
    public void setCategorieIncident(String CategorieIncident) {
        this.CategorieIncident = CategorieIncident;
    }

    public final CategorieIncident build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "CategorieIncident");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, CategorieIncident);
    }

    private void setFieldsFromResulset(final ResultSet resultSet, final String ... fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for(final String fieldName : fieldNames ) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, final String ... fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;
        for(final String fieldName : fieldNames ) {
            preparedStatement.setString(++ix, fieldName);
        }
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "CategorieIncident{" +
                "CategorieIncident='" + CategorieIncident + '\'' +
                '}';
    }


}
