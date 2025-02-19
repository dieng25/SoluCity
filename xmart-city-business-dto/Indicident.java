package edu.ezip.ing1.pds.business.dto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Incident {

    private String id_ticket;
    private String titre;
    private String description;
    private String date_emis;
    private String categorie;
    private int statut;
    private String codePostal_ticket;
    private String tel_num;
    private String code_Postal;

    // Constructeur
    public Incident(String id_ticket, String titre, String description, String date_emis,
                    String categorie, int statut, String codePostal_ticket, String tel_num, String code_Postal) {
        this.id_ticket = id_ticket;
        this.titre = titre;
        this.description = description;
        this.date_emis = date_emis;
        this.categorie = categorie;
        this.statut = statut;
        this.codePostal_ticket = codePostal_ticket;
        this.tel_num = tel_num;
        this.code_Postal = code_Postal;
    }

    // Getters
    public String getId_ticket() {
        return id_ticket;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getDate_emis() {
        return date_emis;
    }

    public String getCategorie() {
        return categorie;
    }

    public int getStatut() {
        return statut;
    }

    public String getCodePostal_ticket() {
        return codePostal_ticket;
    }

    public String getTel_num() {
        return tel_num;
    }

    public String getCode_Postal() {
        return code_Postal;
    }

    // Setters
    public void setId_ticket(String id_ticket) {
        this.id_ticket = id_ticket;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate_emis(String date_emis) {
        this.date_emis = date_emis;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public void setCodePostal_ticket(String codePostal_ticket) {
        this.codePostal_ticket = codePostal_ticket;
    }

    public void setTel_num(String tel_num) {
        this.tel_num = tel_num;
    }

    public void setCode_Postal(String code_Postal) {
        this.code_Postal = code_Postal;
    }

    // Méthode de remplissage depuis ResultSet
    public Incident build(ResultSet resultSet) throws SQLException {
        this.id_ticket = resultSet.getString("id_ticket");
        this.titre = resultSet.getString("titre");
        this.description = resultSet.getString("description");
        this.date_emis = resultSet.getString("date_emis");
        this.categorie = resultSet.getString("categorie");
        this.statut = resultSet.getInt("statut");
        this.codePostal_ticket = resultSet.getString("codePostal_ticket");
        this.tel_num = resultSet.getString("tel_num");
        this.code_Postal = resultSet.getString("code_Postal");
        return this;
    }

    // Méthode de construction de PreparedStatement
    public PreparedStatement build(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, this.id_ticket);
        preparedStatement.setString(2, this.titre);
        preparedStatement.setString(3, this.description);
        preparedStatement.setString(4, this.date_emis);
        preparedStatement.setString(5, this.categorie);
        preparedStatement.setInt(6, this.statut);
        preparedStatement.setString(7, this.codePostal_ticket);
        preparedStatement.setString(8, this.tel_num);
        preparedStatement.setString(9, this.code_Postal);
        return preparedStatement;
    }
}
