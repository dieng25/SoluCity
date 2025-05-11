package edu.ezip.ing1.pds.business.dto.DashboardDto;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@JsonRootName(value = "userDashboard")
public class UserDashboard {

    private String CodePostal;
    private String Utilisateur;
    private String MotDePasse;

    public UserDashboard() {}

    public UserDashboard(String CodePostal, String Utilisateur, String MotDePasse) {
        this.CodePostal = CodePostal;
        this.Utilisateur = Utilisateur;
        this.MotDePasse = MotDePasse;
        
    }

    public String getCodePostal() {
        return CodePostal;
    }

    public String getUtilisateur() {
        return Utilisateur;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    @JsonProperty("code_postal")
    public void setCodePostal(String CodePostal) {
        this.CodePostal = CodePostal;
    }

    @JsonProperty("utilisateur")
    public void setUtilisateur(String Utilisateur) {
        this.Utilisateur = Utilisateur;
    }

    @JsonProperty("mot_de_passe")
    public void setMotDePasse(String MotDePasse) {
        this.MotDePasse = MotDePasse;
    }
    
    public UserDashboard build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "CodePostal", "Utilisateur", "MotDePasse");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, CodePostal, Utilisateur, MotDePasse);
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
        return "UserDashboard{" +
                "CodePostal=" + CodePostal +
                ", Utilisateur='" + Utilisateur + '\'' +
                ", MotDePasse='" + MotDePasse + '\'' +
                '}';
    }
}

