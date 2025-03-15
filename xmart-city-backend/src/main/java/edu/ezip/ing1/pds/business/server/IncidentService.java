package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;

public class IncidentService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        SELECT_ALL_INCIDENTS("SELECT * FROM Incident");
        
        private final String query;

        Queries(final String query) {
            this.query = query;
        }
    }

    public static IncidentService inst = null;

    public static final IncidentService getInstance() {
        if (inst == null) {
            inst = new IncidentService();
        }
        return inst;
    }

    private IncidentService() {}

    public final Response dispatch(final Request request, final Connection connection)
            throws SQLException, IOException {
        Response response = null;

        final Queries queryEnum = Enum.valueOf(IncidentService.Queries.class, request.getRequestOrder());
        if (queryEnum == Queries.SELECT_ALL_INCIDENTS) {
            response = getAllIncidents(request, connection);
        }
        return response;
    }

    private Response getAllIncidents(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_INCIDENTS.query);
    
        Incidents incidents = new Incidents();
        while (res.next()) {
            Incident incident = new Incident();
            incident.setIdTicket(res.getInt("Id_ticket"));
            incident.setTitre(res.getString("Titre"));
            incident.setDescription(res.getString("Description"));
            incident.setdate(res.getDate("date_creation"));
            incident.setCategorie(res.getString("Categorie"));
            incident.setStatut(res.getInt("Statut"));
            incident.setCP_Ticket(res.getString("CodePostal_ticket"));
            incident.setPriorite(res.getInt("Priorite"));
            incident.setDate_cloture(res.getDate("date_cloture"));
            incident.setTelNum(res.getString("tel_num"));
            incident.setCP(res.getString("Code_Postal"));

            incidents.add(incident);
             
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(incidents));
    }
}
