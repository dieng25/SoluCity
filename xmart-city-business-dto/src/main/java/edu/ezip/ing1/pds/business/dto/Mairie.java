package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "mairie")
public class Mairie {
    private String codePostal;
    private String email;
    private String mdp;

    public Mairie() {
    }

    public Mairie(String codePostal, String email, String mdp) {
        this.codePostal = codePostal;
        this.email = email;
        this.mdp = mdp;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getEmail() {
        return email;
    }

    public String getMdp() {
        return mdp;
    }

    @JsonProperty("mairie_code_postal")
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @JsonProperty("mairie_email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("mairie_mdp")
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public final Mairie build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "codePostal", "email", "mdp");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, codePostal, email, mdp);
    }

    private void setFieldsFromResultSet(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, final String... fieldValues)
            throws SQLException {
        int index = 0;
        for (final String fieldValue : fieldValues) {
            preparedStatement.setString(++index, fieldValue);
        }
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "Mairie{" +
                "codePostal='" + codePostal + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                '}';
    }
}
