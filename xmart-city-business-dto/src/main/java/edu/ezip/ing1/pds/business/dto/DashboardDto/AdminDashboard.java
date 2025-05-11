package edu.ezip.ing1.pds.business.dto.DashboardDto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "adminDashboard")

public class AdminDashboard {

    private String Username;
    private String Password;
    private String NewUsername;
    private String NewPassword;

    public AdminDashboard() {}

    public AdminDashboard(String NewUsername, String NewPassword, String Username, String Password) {
        this.Username = Username;
        this.Password = Password;
        this.NewUsername = NewPassword;
        this.NewPassword = NewPassword;
    }

    public String getUsername() {
        return Username;
    }

    public String getNewUsername(){
        return NewUsername;
    }

    public String getPassword() {
        return Password;
    }

    public String getNewPassword(){
        return NewPassword;
    }

    @JsonProperty("username")
    public void setUsername(String Username) {
        this.Username = Username;
    }
    @JsonProperty("NewUsername")
    public void setNewUsername(String NewUsername) {
        this.NewUsername = NewUsername;
    }

    @JsonProperty("password")
    public void setPassword(String Password) {
        this.Password = Password;
    }

    @JsonProperty("Newpassword")
    public void setNewPassword(String NewPassword) {
        this.NewPassword = NewPassword;
    }
    
    public AdminDashboard build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "Username", "NewUsername", "Password", "NewPassword");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, NewUsername, NewPassword, Username, Password);
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
        return "AdminDashboard{" +
                "NewUsername=" + NewUsername +
                ", NewPassword='" + NewPassword + '\'' +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
