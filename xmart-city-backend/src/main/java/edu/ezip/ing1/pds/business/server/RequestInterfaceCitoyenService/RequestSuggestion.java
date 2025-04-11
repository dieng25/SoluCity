package edu.ezip.ing1.pds.business.server.RequestInterfaceCitoyenService;

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

public class RequestSuggestion {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        INSERT_SUGGESTION("INSERT INTO Suggestion (Titre, Description, date_creation, Categorie, Statut, CodePostal_ticket, date_cloture, Commentaire, tel_num, Code_Postal, CategorieSuggestion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
        SELECT_SUGGESTION("SELECT t.Id_ticket, t.Titre, t.Description, t.date_creation, t.Categorie, t.Statut, t.CodePostal_ticket, t.date_cloture, t.Commentaire, t.tel_num, FROM Suggestion t"),
        SELECT_SUGGESTION_BY_TEL("SELECT Id_ticket, Titre, Description, date_creation, Categorie, Statut, CodePostal_ticket, date_cloture, Commentaire, tel_num FROM Suggestion WHERE tel_num = ?");

        private final String query;

        private Queries(final String query) {
            this.query = query;
        }

    }

    public static RequestSuggestion inst = null;

    public static final RequestSuggestion getInstance() {
        if (inst == null) {
            inst = new RequestSuggestion();
        }
        return inst;
    }

    private RequestSuggestion() {
    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;

        final RequestSuggestion.Queries queryEnum = Enum.valueOf(RequestSuggestion.Queries.class, request.getRequestOrder());
        switch(queryEnum) {
            case INSERT_SUGGESTION:
                response = InsertSuggestion(request, connection);
                break;
            case SELECT_SUGGESTION:
                response = SelectSugestion(request, connection);
                break;
            case SELECT_SUGGESTION_BY_TEL:
                response = SelectSuggestionByTel(request, connection);
                break;
            default:
                break;

        }

        return response;
    }


    private Response InsertSuggestion(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        PreparedStatement stmt = connection.prepareStatement(RequestSuggestion.Queries.INSERT_SUGGESTION.query);
        Suggestion suggestion = objectMapper.readValue(request.getRequestBody(), Suggestion.class);

        stmt.setString(1, suggestion.getTitre());
        stmt.setString(2, suggestion.getDescription());
        stmt.setDate(3, suggestion.getDate_creation());
        stmt.setString(4, suggestion.getCategorie());
        stmt.setInt(5, suggestion.getStatut());
        stmt.setString(6, suggestion.getCP_Ticket());
        stmt.setDate(7, suggestion.getDate_cloture());
        stmt.setString(8, suggestion.getCommentaire());
        stmt.setString(9, suggestion.getTelNum());
        stmt.setString(10, suggestion.getCP());
        stmt.setString(11, suggestion.getCategorieSuggestion());
        int rowsInserted = stmt.executeUpdate();

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(suggestion));
    }


    private Response SelectSugestion(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_SUGGESTION.query);
        Suggestions suggestions = new Suggestions();
        while (res.next()) {
            Suggestion suggestion = new Suggestion();
            suggestion.setIdTicket(res.getInt(1));
            suggestion.setTitre(res.getString(2));
            suggestion.setDescription(res.getString(3));
            suggestion.setDate_creation(res.getDate(4));
            suggestion.setCategorie(res.getString(5));
            suggestion.setStatut(res.getInt(6));
            suggestion.setCP_Ticket(res.getString(7));
            suggestion.setDate_cloture(res.getDate(8));
            suggestion.setCommentaire(res.getString(9));
            suggestion.setTelNum(res.getString(10));
            suggestions.add(suggestion);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(suggestions));
    }


    private Response SelectSuggestionByTel(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        Citoyen citoyen = objectMapper.readValue(request.getRequestBody(), Citoyen.class); // Récupérer le citoyen

        PreparedStatement stmt = connection.prepareStatement(RequestSuggestion.Queries.SELECT_SUGGESTION_BY_TEL.query);
        stmt.setString(1, citoyen.getTelNum());
        ResultSet res = stmt.executeQuery();

        Suggestions suggestions = new Suggestions();

        while (res.next()) {
            Suggestion suggestion = new Suggestion();
            suggestion.setIdTicket(res.getInt(1));
            suggestion.setTitre(res.getString(2));
            suggestion.setDescription(res.getString(3));
            suggestion.setDate_creation(res.getDate(4));
            suggestion.setCategorie(res.getString(5));
            suggestion.setStatut(res.getInt(6));
            suggestion.setCP_Ticket(res.getString(7));
            suggestion.setDate_cloture(res.getDate(8));
            suggestion.setCommentaire(res.getString(9));
            suggestion.setTelNum(res.getString(10));

            suggestions.add(suggestion);
        }

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(suggestions));
    }
}
