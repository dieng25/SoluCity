package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "categorieS")
public class CategorieSuggestion {
    private String CategorieSuggestion;

    public CategorieSuggestion() {}

    public CategorieSuggestion(String CategorieSuggestion) {
        this.CategorieSuggestion = CategorieSuggestion;
    }

    public String getCategorieSuggestion() {
        return CategorieSuggestion;
    }

    @JsonProperty("categorieS_CategorieSuggestion")
    public void setCategorieSuggestion(String CategorieSuggestion) {
        this.CategorieSuggestion = CategorieSuggestion;
    }


    public final CategorieSuggestion build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "CategorieSuggestion");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, CategorieSuggestion);
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
        return "CategorieSuggestion{" +
                "CategorieSuggestion='" + CategorieSuggestion + '\'' +
                '}';
    }
}
