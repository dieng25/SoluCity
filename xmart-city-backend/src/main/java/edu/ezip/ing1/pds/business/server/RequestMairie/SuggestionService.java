package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Suggestion;
import edu.ezip.ing1.pds.business.dto.Suggestions;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;

public class SuggestionService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        SELECT_ALL_SUGGESTIONS("SELECT * FROM Suggestion");
        
        private final String query;

        Queries(final String query) {
            this.query = query;
        }
    }

    public static SuggestionService inst = null;

    public static final SuggestionService getInstance() {
        if (inst == null) {
            inst = new SuggestionService();
        }
        return inst;
    }

    private SuggestionService() {}

    public final Response dispatch(final Request request, final Connection connection)
            throws SQLException, IOException {
        Response response = null;

        final Queries queryEnum = Enum.valueOf(SuggestionService.Queries.class, request.getRequestOrder());
        if (queryEnum == Queries.SELECT_ALL_SUGGESTIONS) {
            response = getAllSuggestions(request, connection);
        }
        return response;
    }

    private Response getAllSuggestions(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_SUGGESTIONS.query);
    
        Suggestions suggestions = new Suggestions();
        while (res.next()) {
            Suggestion suggestion = new Suggestion();
            suggestion.setTitre(res.getString("Titre"));
            suggestion.setDescription(res.getString("Description"));
            suggestion.setDate_creation(res.getDate("date_creation"));
            suggestion.setCategorie(res.getString("Categorie"));
            suggestion.setStatut(res.getInt("Statut"));
            suggestion.setCP_Ticket(res.getString("CodePostal_ticket"));

            suggestions.add(suggestion);
             
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(suggestions));
    }
}
