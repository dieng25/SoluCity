package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "fonctionnaire")
public class Fonctionnaire {
    private String codePostal;
    private String email;
    private String mdp;

    public Fonctionnaire() {}

    public Fonctionnaire(String email, String mdp, String codePostal) {
        this.email = email;
        this.mdp = mdp;
        this.codePostal = codePostal;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getEmail(){
        return email;
    }

    public String getMdp(){
        return mdp;
    }

    @JsonProperty("fonctionnaire_code_postal")
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @JsonProperty("fonctionnaire_email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("fonctionnaire_mdp")
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Fonctionnaire build(ResultSet rs) throws SQLException {
        this.email = rs.getString("email");
        this.mdp = rs.getString("mdp");
        this.codePostal = rs.getString("code_postal");
        return this;
    }

    public PreparedStatement build(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.email);
        ps.setString(2, this.mdp);
        ps.setString(3, this.codePostal);
        return ps;
    }

    @Override
    public String toString() {
        return "Fonctionnaire{" +
                "email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", codePostal='" + codePostal + '\'' +
                '}';
    }
}
