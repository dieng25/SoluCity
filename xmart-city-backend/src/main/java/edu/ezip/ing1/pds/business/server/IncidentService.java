package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        PreparedStatement stmt = connection.prepareStatement(Queries.SELECT_ALL_INCIDENTS.query);
        ResultSet rs = stmt.executeQuery();

        List<Incident> incidents = new ArrayList<>();
        while (rs.next()) {
            Incident incident = new Incident();
            incident.setIdTicket(rs.getInt("Id_ticket"));
            incident.setTitre(rs.getString("Titre"));
            incident.setDescription(rs.getString("Description"));
            incident.setdate(rs.getDate("date_emis"));
            incident.setCategorie(rs.getString("Catégorie"));
            incident.setStatut(rs.getInt("Statut"));
            incident.setCP_Ticket(rs.getString("CodePostal_ticket"));
            incident.setPriorite(rs.getInt("Priorité"));
            incidents.add(incident);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(incidents));
    }
}
