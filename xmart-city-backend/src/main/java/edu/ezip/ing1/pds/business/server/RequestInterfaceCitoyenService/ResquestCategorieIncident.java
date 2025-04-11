package edu.ezip.ing1.pds.business.server.RequestInterfaceCitoyenService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.CategorieIncident;
import edu.ezip.ing1.pds.business.dto.CategorieIncidents;
import edu.ezip.ing1.pds.business.dto.Mairie;
import edu.ezip.ing1.pds.business.dto.Mairies;
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

public class ResquestCategorieIncident {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        SELECT_ALL_CategorieIncident("SELECT t.CategorieIncident FROM CategorieIncident t");


        private final String query;

        private Queries(final String query) {
            this.query = query;
        }

    }

    public static ResquestCategorieIncident inst = null;

    public static final ResquestCategorieIncident getInstance() {
        if (inst == null) {
            inst = new ResquestCategorieIncident();
        }
        return inst;
    }

    private ResquestCategorieIncident() {
    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;

        final ResquestCategorieIncident.Queries queryEnum = Enum.valueOf(ResquestCategorieIncident.Queries.class, request.getRequestOrder());
        switch(queryEnum) {
            case SELECT_ALL_CategorieIncident:
                response = SelectAllCategorieIncident(request, connection);
                break;
            default:
                break;

        }

        return response;
    }


    private Response SelectAllCategorieIncident(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_CategorieIncident.query);
        CategorieIncidents categorieIncidents = new CategorieIncidents();
        while (res.next()) {
            CategorieIncident categorieIncident = new CategorieIncident();
            categorieIncident.setCategorieIncident(res.getString(1 ));
            categorieIncidents.add(categorieIncident);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(categorieIncidents));
    }

}
