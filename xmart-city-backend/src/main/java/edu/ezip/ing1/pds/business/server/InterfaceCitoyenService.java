package edu.ezip.ing1.pds.business.server;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Random;

public class InterfaceCitoyenService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        INSERT_CITOYEN("INSERT INTO Citoyen (tel_num, Nom, Prenom, email, Identifiant) VALUES (?, ?, ?, ?, ?)"),
        INSERT_INCIDENT("INSERT INTO Incident (Titre, Description, date_creation, Categorie, Statut, CodePostal_ticket, Priorite, date_cloture, tel_num, Code_Postal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
        SELECT_ALL_MAIRIES("SELECT t.Code_Postal FROM Mairie t"),
        SELECT_CITOYEN("SELECT t.tel_num, t.Nom, t.Prenom, t.email, t.Identifiant FROM Citoyen t" );

        private final String query;

        private Queries(final String query) {
            this.query = query;
        }

    }

    public static InterfaceCitoyenService inst = null;

    public static final InterfaceCitoyenService getInstance() {
        if (inst == null) {
            inst = new InterfaceCitoyenService();
        }
        return inst;
    }

    private InterfaceCitoyenService() {
    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;

        final Queries queryEnum = Enum.valueOf(Queries.class, request.getRequestOrder());
        switch(queryEnum) {
            case INSERT_CITOYEN:
                response = InsertCitoyen(request, connection);
                break;
            case INSERT_INCIDENT:
                response = InsertIncident(request, connection);
                break;
            case SELECT_ALL_MAIRIES:
                response = SelectAllMairies(request, connection);
                break;
            case SELECT_CITOYEN:
                response = SelectCitoyen(request, connection);
                break;
            default:
                break;
        }

        return response;
    }

    private Response InsertCitoyen(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        Citoyen citoyen = objectMapper.readValue(request.getRequestBody(), Citoyen.class);

        //Crée un Identifiant Unique -> Champ Identifiant table Citoyen
        String identifiant = generateIdentifiant(citoyen.getPrenom(), citoyen.getNom());
        citoyen.setIdentifiant(identifiant);

        PreparedStatement stmt = connection.prepareStatement(Queries.INSERT_CITOYEN.query);
        stmt.setString(1, citoyen.getTelNum());
        stmt.setString(2, citoyen.getNom());
        stmt.setString(3, citoyen.getPrenom());
        stmt.setString(4, citoyen.getEmail());
        stmt.setString(5, citoyen.getIdentifiant());
        int rowsInserted = stmt.executeUpdate();
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(citoyen));
    }

    private String generateIdentifiant(String prenom, String nom) {
        String firstLetterPrenom = prenom.substring(0, 1).toUpperCase();
        String firstLetterNom = nom.substring(0, 1).toUpperCase();
        String lastLetterNom = nom.substring(nom.length() - 1).toUpperCase();
        String randomPart = generateRandomString(4);
        return firstLetterPrenom + firstLetterNom + lastLetterNom + randomPart;
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }


    private Response InsertIncident(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        PreparedStatement stmt = connection.prepareStatement(Queries.INSERT_INCIDENT.query);
        Incident incident = objectMapper.readValue(request.getRequestBody(), Incident.class);

        stmt.setString(1, incident.getTitre());
        stmt.setString(2, incident.getDescription());
        stmt.setDate(3, incident.getDate_creation());
        stmt.setString(4, incident.getCategorie());
        stmt.setInt(5, incident.getStatut());
        stmt.setString(6, incident.getCP_Ticket());
        stmt.setInt(7, incident.getPriorite());
        stmt.setDate(8, incident.getDate_cloture());
        stmt.setString(9, incident.getTelNum());
        stmt.setString(10, incident.getCP());
        int rowsInserted = stmt.executeUpdate();

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(incident));
    }

    private Response SelectAllMairies(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_MAIRIES.query);
        Mairies mairies = new Mairies();
        while (res.next()) {
            Mairie mairie = new Mairie();
            mairie.setCodePostal(res.getString(1 ));
            mairies.add(mairie);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(mairies));
    }

    private Response SelectCitoyen(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_CITOYEN.query);
        Citoyens citoyens = new Citoyens();
        while (res.next()) {
            Citoyen citoyen = new Citoyen();
            citoyen.setTelNum(res.getString(1));
            citoyen.setNom(res.getString(2));
            citoyen.setPrenom(res.getString(3));
            citoyen.setEmail(res.getString(4));
            citoyen.setIdentifiant(res.getString(5));
            citoyens.add(citoyen);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(citoyens));
    }


}




