package edu.ezip.ing1.pds.business.server.RequestMairie;
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
        SELECT_ALL_INCIDENTS("SELECT * FROM Incident"),
        UPDATE_INCIDENT("UPDATE Incident SET statut = ?, date_cloture = ? WHERE Id_ticket = ?");

        private final String query;

        Queries(final String query) {
            this.query = query;
        }
        public String getQuery() {
            return query;
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
        switch (queryEnum) {
            case SELECT_ALL_INCIDENTS:
                response =  getAllIncidents(request, connection);
                break;
            case UPDATE_INCIDENT:
                response =  setIncident(request, connection);
                break;
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
            incident.setDate_creation(res.getDate("date_creation"));
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
    private Response setIncident(final Request request, final Connection connection)
        throws SQLException, IOException {

    ObjectMapper objectMapper = new ObjectMapper();

    Incident incident = objectMapper.readValue(request.getRequestBody(), Incident.class);

    if (incident == null || incident.getIdTicket() <= 0 || incident.getStatut() < 0) {
        return new Response(request.getRequestId(),
                            "{\"success\": false, \"error\": \"Données d'incident invalides\"}");
    }

    try (PreparedStatement pstmt = connection.prepareStatement(
            Queries.UPDATE_INCIDENT.getQuery())) {
        

        java.sql.Date todayDate = new java.sql.Date(System.currentTimeMillis());
        pstmt.setInt(1, incident.getStatut());
        pstmt.setDate(2, todayDate);
        pstmt.setInt(3, incident.getIdTicket());
        int rowsUpdated = pstmt.executeUpdate();

        String resultJson = "{\"success\": " + (rowsUpdated > 0) + "}";
        return new Response(request.getRequestId(), resultJson);

    } catch (SQLException e) {
        e.printStackTrace();
        return new Response(request.getRequestId(),
                            "{\"success\": false, \"error\": \"Erreur base de données\"}");
    }
}

    
}
