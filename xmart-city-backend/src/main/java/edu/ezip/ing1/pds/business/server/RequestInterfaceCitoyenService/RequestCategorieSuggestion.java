package edu.ezip.ing1.pds.business.server.RequestInterfaceCitoyenService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.CategorieIncident;
import edu.ezip.ing1.pds.business.dto.CategorieIncidents;
import edu.ezip.ing1.pds.business.dto.CategorieSuggestion;
import edu.ezip.ing1.pds.business.dto.CategorieSuggestions;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RequestCategorieSuggestion {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        SELECT_ALL_CategorieSuggestion("SELECT t.CategorieSuggestion FROM CategorieSuggestion t");


        private final String query;

        private Queries(final String query) {
            this.query = query;
        }

    }

    public static RequestCategorieSuggestion inst = null;

    public static final RequestCategorieSuggestion getInstance() {
        if (inst == null) {
            inst = new RequestCategorieSuggestion();
        }
        return inst;
    }

    private RequestCategorieSuggestion() {
    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;

        final RequestCategorieSuggestion.Queries queryEnum = Enum.valueOf(RequestCategorieSuggestion.Queries.class, request.getRequestOrder());
        switch(queryEnum) {
            case SELECT_ALL_CategorieSuggestion:
                response = SelectAllCategorieSuggestion(request, connection);
                break;
            default:
                break;

        }

        return response;
    }


    private Response SelectAllCategorieSuggestion(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(RequestCategorieSuggestion.Queries.SELECT_ALL_CategorieSuggestion.query);
        CategorieSuggestions categorieSuggestions = new CategorieSuggestions();
        while (res.next()) {
            CategorieSuggestion categorieSuggestion = new CategorieSuggestion();
            categorieSuggestion.setCategorieSuggestion(res.getString(1 ));
            categorieSuggestions.add(categorieSuggestion);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(categorieSuggestions));
    }
}
