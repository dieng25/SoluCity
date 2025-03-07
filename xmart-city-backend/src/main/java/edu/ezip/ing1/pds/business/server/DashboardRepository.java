package edu.ezip.ing1.pds.business.server;

import edu.ezip.ing1.pds.business.dto.DashboardData;
import edu.ezip.ing1.pds.business.dto.DashboardDatas;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class DashboardRepository {
    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
       // DASHBOARD_REQUEST("SELECT COUNT(*) FROM incidents, SELECT COUNT(*) FROM incidents WHERE statut = 1, SELECT COUNT(*) FROM incidents WHERE statut = 2, SELECT COUNT(*) FROM incidens WHERE statut = 0, SELECT COUNT(*) FROM incidents WHERE Priorité = 0, SELECT COUNT(*) FROM incidents WHERE Priorité = 1, SELECT COUNT(*) FROM incidents WHERE Priorité = 2, SELECT COUNT(*) FROM incidents WHERE Priorité = 3");
       
       DASHBOARD_REQUEST("SELECT " +
                "(SELECT COUNT(*) FROM Incident) AS total_incidents, " +
                "(SELECT COUNT(*) FROM Incident WHERE statut = 1) AS incidents_en_cours, " +
                "(SELECT COUNT(*) FROM Incident WHERE statut = 2) AS incidents_resolus, " +
                "(SELECT COUNT(*) FROM Incident WHERE statut = 0) AS incidents_non_ouverts, " +
                "(SELECT COUNT(*) FROM Incident WHERE Priorite = 0) AS priorite_non_defini, " +
                "(SELECT COUNT(*) FROM Incident WHERE Priorite = 1) AS priorite_faible, " +
                "(SELECT COUNT(*) FROM Incident WHERE Priorite = 2) AS priorite_moyenne, " +
                "(SELECT COUNT(*) FROM Incident WHERE Priorite = 3) AS priorite_haute");

        private final String query;

        private Queries(final String query) {
            this.query = query;
        }
    }
    
    public static DashboardRepository inst = null;
    public static final DashboardRepository getInstance()  {
        if(inst == null) {
            inst = new DashboardRepository();
        }
        return inst;
    }

    private DashboardRepository() {

    }

    private Response fetchDashboardStats (final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.DASHBOARD_REQUEST.query);
        DashboardDatas dashboardDatas = new DashboardDatas();

        /*while (res.next()) {
            DashboardData dashboardData = new DashboardData();
            dashboardData.setTotalIncident(res.getInt(1));
            dashboardData.setIncidentEnCours(res.getInt(2));
            dashboardData.setIncidentResolu(res.getInt(3));
            dashboardData.setIncidentNonOuvert(res.getInt(4));
            dashboardData.setNonDefini(res.getInt(5));
            dashboardData.setFaible(res.getInt(6));
            dashboardData.setMoyen(res.getInt(7));
            dashboardData.setHaut(res.getInt(8));
        }*/

        if (res.next()) {  
        DashboardData dashboardData = new DashboardData();
        dashboardData.setTotalIncident(res.getInt("total_incidents"));
        dashboardData.setIncidentEnCours(res.getInt("incidents_en_cours"));
        dashboardData.setIncidentResolu(res.getInt("incidents_resolus"));
        dashboardData.setIncidentNonOuvert(res.getInt("incidents_non_ouverts"));
        dashboardData.setNonDefini(res.getInt("priorite_non_defini"));
        dashboardData.setFaible(res.getInt("priorite_faible"));
        dashboardData.setMoyen(res.getInt("priorite_moyenne"));
        dashboardData.setHaut(res.getInt("priorite_haute"));

        dashboardDatas.getDashboardDataSet().add(dashboardData);
    }
    logger.debug(" Données Dashboard récupérées: {}", dashboardDatas);
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(dashboardDatas));
    }
    
public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        //Response response = null;
        Response response = fetchDashboardStats(request, connection);
        return response;
    }
}