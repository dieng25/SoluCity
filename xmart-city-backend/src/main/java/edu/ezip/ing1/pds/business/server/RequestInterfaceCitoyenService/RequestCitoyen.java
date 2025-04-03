package edu.ezip.ing1.pds.business.server.RequestInterfaceCitoyenService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Citoyens;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Random;

public class RequestCitoyen {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        INSERT_CITOYEN("INSERT INTO Citoyen (tel_num, Nom, Prenom, email, Identifiant) VALUES (?, ?, ?, ?, ?)"),
        SELECT_CITOYEN("SELECT t.tel_num, t.Nom, t.Prenom, t.email, t.Identifiant FROM Citoyen t" ),
        SELECT_TEL_EXIST("SELECT COUNT(*) FROM Citoyen WHERE tel_num = ?"),
        SELECT_CONNEXION("SELECT tel_num, Nom, Prenom, email, Identifiant FROM Citoyen WHERE tel_num = ? AND Identifiant = ?"),
        SELECT_INCIDENT_BY_TEL("SELECT Id_ticket, Titre, Description, date_creation, Categorie, Statut, CodePostal_ticket, Priorite, date_cloture, tel_num FROM Incident WHERE tel_num = ?");

        private final String query;

        private Queries(final String query) {
            this.query = query;
        }

    }


    public static RequestCitoyen inst = null;

    public static final RequestCitoyen getInstance() {
        if (inst == null) {
            inst = new RequestCitoyen();
        }
        return inst;
    }

    private RequestCitoyen() {
    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;

        final Queries queryEnum = Enum.valueOf(Queries.class, request.getRequestOrder());
        switch(queryEnum) {
            case INSERT_CITOYEN:
                response = InsertCitoyen(request, connection);
                break;
            case SELECT_CITOYEN:
                response = SelectCitoyen(request, connection);
                break;
            case SELECT_TEL_EXIST:
                response = SelectTelExist(request, connection);
                break;
            case SELECT_CONNEXION:
                response = SelectConnexion(request, connection);
                break;
            case SELECT_INCIDENT_BY_TEL:
                response = SelectIncidentByTel(request, connection);
                break;
            default:
                break;

        }

        return response;
    }

    private Response InsertCitoyen(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        Citoyen citoyen = objectMapper.readValue(request.getRequestBody(), Citoyen.class);

        //Crée un Identifiant -> Champ Identifiant table Citoyen
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

    private Response SelectTelExist(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        PreparedStatement stmt = connection.prepareStatement(Queries.SELECT_TEL_EXIST.query);
        Citoyen citoyen = objectMapper.readValue(request.getRequestBody(), Citoyen.class);

        stmt.setString(1, citoyen.getTelNum());
        ResultSet res = stmt.executeQuery();
        boolean exists = false;

        if (res.next()) {
            exists = res.getInt(1) > 0;
        }

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(exists));
    }


    private Response SelectConnexion(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        Citoyen citoyen = objectMapper.readValue(request.getRequestBody(), Citoyen.class);

        PreparedStatement stmt = connection.prepareStatement(Queries.SELECT_CONNEXION.query);
        stmt.setString(1, citoyen.getTelNum());
        stmt.setString(2, citoyen.getIdentifiant());

        ResultSet res = stmt.executeQuery();
        Citoyen authenticatedCitoyen = null;

        if (res.next()) {
            authenticatedCitoyen = new Citoyen();
            authenticatedCitoyen.setTelNum(res.getString("tel_num"));
            authenticatedCitoyen.setNom(res.getString("Nom"));
            authenticatedCitoyen.setPrenom(res.getString("Prenom"));
            authenticatedCitoyen.setEmail(res.getString("email"));
            authenticatedCitoyen.setIdentifiant(res.getString("Identifiant"));
        }

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(authenticatedCitoyen));
    }


    private Response SelectIncidentByTel(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        Citoyen citoyen = objectMapper.readValue(request.getRequestBody(), Citoyen.class);

        PreparedStatement stmt = connection.prepareStatement(Queries.SELECT_INCIDENT_BY_TEL.query);
        stmt.setString(1, citoyen.getTelNum());
        ResultSet res = stmt.executeQuery();

        Incidents incidents = new Incidents();

        while (res.next()) {
            Incident incident = new Incident();
            incident.setIdTicket(res.getInt(1));
            incident.setTitre(res.getString(2));
            incident.setDescription(res.getString(3));
            incident.setDate_creation(res.getDate(4));
            incident.setCategorie(res.getString(5));
            incident.setStatut(res.getInt(6));
            incident.setCP_Ticket(res.getString(7));
            incident.setPriorite(res.getInt(8));
            incident.setDate_cloture(res.getDate(9));
            incident.setTelNum(res.getString(10));

            incidents.add(incident);
        }

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(incidents));
    }
}
