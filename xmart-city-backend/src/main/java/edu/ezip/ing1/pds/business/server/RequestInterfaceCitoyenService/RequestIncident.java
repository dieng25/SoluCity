package edu.ezip.ing1.pds.business.server.RequestInterfaceCitoyenService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class RequestIncident {
    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        INSERT_INCIDENT("INSERT INTO Incident (Titre, Description, date_creation, Categorie, Statut, CodePostal_ticket, Priorite, date_cloture, tel_num, Code_Postal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
        SELECT_INCIDENT("SELECT t.Id_ticket, t.Titre, t.Description, t.date_creation, t.Categorie, t.Statut, t.CodePostal_ticket, t.Priorite, t.date_cloture, t.tel_num, FROM Incident t"),
        SELECT_INCIDENT_BY_TEL("SELECT Id_ticket, Titre, Description, date_creation, Categorie, Statut, CodePostal_ticket, Priorite, date_cloture, tel_num FROM Incident WHERE tel_num = ?");

        private final String query;

        private Queries(final String query) {
            this.query = query;
        }

    }

    public static RequestIncident inst = null;

    public static final RequestIncident getInstance() {
        if (inst == null) {
            inst = new RequestIncident();
        }
        return inst;
    }

    private RequestIncident() {
    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;

        final Queries queryEnum = Enum.valueOf(Queries.class, request.getRequestOrder());
        switch(queryEnum) {
            case INSERT_INCIDENT:
                response = InsertIncident(request, connection);
                break;
            case SELECT_INCIDENT:
                response = SelectIncident(request, connection);
                break;
            case SELECT_INCIDENT_BY_TEL:
                response = SelectIncidentByTel(request, connection);
                break;
            default:
                break;

        }

        return response;
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


    private Response SelectIncident(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_INCIDENT.query);
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


    private Response SelectIncidentByTel(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        Citoyen citoyen = objectMapper.readValue(request.getRequestBody(), Citoyen.class); // Récupérer le citoyen

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
