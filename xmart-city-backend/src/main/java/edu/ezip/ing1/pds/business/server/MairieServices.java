package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.Suggestion;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MairieServices {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        INSERT_CITOYEN("INSERT INTO Citoyen (tel_num, Nom, Prénom, email, Identifiant) VALUES (?, ?, ?, ?, ?)"),
        INSERT_INCIDENT("INSERT INTO Incident (Titre, Description, date_emis, Catégorie, Statut, CodePostal_ticket, Priorité, tel_num, Code_Postal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"),
        INSERT_SUGGESTION("INSERT INTO Suggestion (Titre, Description, date_emis, Catégorie, Statut, CodePostal_ticket, tel_num) VALUES (?, ?, ?, ?, ?, ?, ?)" ),
        SELECT_ALL_INCIDENTS("SELECT Id_ticket, Titre, Description, date_emis, Catégorie, Statut, CodePostal_ticket, Priorité, tel_num, Code_Postal FROM Incident"),
        SELECT_ALL_SUGGESTIONS("SELECT Id_ticket, Titre, Description, date_emis, Catégorie, Statut, CodePostal_ticket, tel_num FROM Suggestion");

        private final String query;

        private Queries(final String query) {
            this.query = query;
        }
    }

    public static MairieServices inst = null;

    public static final MairieServices getInstance() {
        if (inst == null) {
            inst = new MairieServices();
        }
        return inst;
    }

    private MairieServices() {
    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;

        final Queries queryEnum = Enum.valueOf(MairieServices.Queries.class, request.getRequestOrder());
        switch(queryEnum) {
            case INSERT_CITOYEN:
                response = InsertCitoyen(request, connection);
                break;
            case INSERT_INCIDENT:
                response = InsertIncident(request, connection);
                break;
            case INSERT_SUGGESTION:
                response = InsertSuggestion(request, connection);
                break;
            case SELECT_ALL_INCIDENTS:
                response = SelectAllIncidents(request, connection);
                break;
            case SELECT_ALL_SUGGESTIONS:
                response = SelectAllSuggestions(request, connection);
                break;
            default:
                // Vous pouvez renvoyer une réponse d'erreur ou gérer d'autres cas
                break;
        }
        return response;
    }

    private Response InsertCitoyen(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        Citoyen citoyen = objectMapper.readValue(request.getRequestBody(), Citoyen.class);

        // Générer un identifiant unique à partir du prénom et du nom
        String identifiant = generateIdentifiant(citoyen.getPrenom(), citoyen.getNom());
        citoyen.setIdentifiant(identifiant);

        PreparedStatement stmt = connection.prepareStatement(Queries.INSERT_CITOYEN.query);
        stmt.setString(1, citoyen.getTelNum());
        stmt.setString(2, citoyen.getNom());
        stmt.setString(3, citoyen.getPrenom());
        stmt.setString(4, citoyen.getEmail());
        stmt.setString(5, citoyen.getIdentifiant());
        stmt.executeUpdate();

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
        PreparedStatement stmt = connection.prepareStatement(Queries.INSERT_INCIDENT.query, Statement.RETURN_GENERATED_KEYS);
        Incident incident = objectMapper.readValue(request.getRequestBody(), Incident.class);
        stmt.setString(1, incident.getTitre());
        stmt.setString(2, incident.getDescription());
        stmt.setDate(3, incident.getDate());
        stmt.setString(4, incident.getCategorie());
        stmt.setInt(5, incident.getStatut());
        stmt.setString(6, incident.getCP_Ticket());
        stmt.setInt(7, incident.getPriorite());
        // stmt.setString(8, incident.getTelNum());
        stmt.setString(9, incident.getCP_Ticket());
        stmt.executeUpdate();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            incident.setIdTicket(generatedKeys.getInt(1));
        }

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(incident));
    }

    private Response InsertSuggestion(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        PreparedStatement stmt = connection.prepareStatement(Queries.INSERT_SUGGESTION.query, Statement.RETURN_GENERATED_KEYS);
        Suggestion suggestion = objectMapper.readValue(request.getRequestBody(), Suggestion.class);
        stmt.setString(1, suggestion.getTitre());
        stmt.setString(2, suggestion.getDescription());
        // stmt.setDate(3, suggestion.getDate());
        stmt.setString(4, suggestion.getCategorie());
        stmt.setInt(5, suggestion.getStatut());
        // stmt.setString(6, suggestion.getCP_Ticket());
        // stmt.setString(7, suggestion.getTelNum());
        stmt.executeUpdate();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            // suggestion.setIdTicket(generatedKeys.getInt(1));
        }

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(suggestion));
    }

    private Response SelectAllIncidents(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_INCIDENTS.query);
        List<Incident> incidents = new ArrayList<>();
        while (res.next()) {
            Incident incident = new Incident();
            incident.setIdTicket(res.getInt("Id_ticket"));
            incident.setTitre(res.getString("Titre"));
            incident.setDescription(res.getString("Description"));
            // incident.setDate(res.getDate("date_emis"));
            incident.setCategorie(res.getString("Catégorie"));
            incident.setStatut(res.getInt("Statut"));
            incident.setCP_Ticket(res.getString("CodePostal_ticket"));
            incident.setPriorite(res.getInt("Priorité"));
            // incident.setCP_Ticket(LoggingLabel);(res.getString("Code_Postal"));
            incidents.add(incident);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(incidents));
    }

    private Response SelectAllSuggestions(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_SUGGESTIONS.query);
        List<Suggestion> suggestions = new ArrayList<>();
        while (res.next()) {
            Suggestion suggestion = new Suggestion();
            suggestion.setCP_Ticket(res.getInt("Id_ticket"));
            suggestion.setTitre(res.getString("Titre"));
            suggestion.setDescription(res.getString("Description"));
            suggestion.setDate(res.getString("date_emis"));
            suggestion.setCategorie(res.getString("Catégorie"));
            suggestion.setStatut(res.getInt("Statut"));
            // suggestion.setCP_Ticket(res.getInt("LoggingLabel"));
            suggestions.add(suggestion);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(suggestions));
    }
}
