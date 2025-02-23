package edu.ezip.ing1.pds.business.server;

import edu.ezip.ing1.pds.business.dto.DashboardData;
import edu.ezip.ing1.pds.business.dto.DashboardDatas;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;

import java.io.IOException;
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
        TOTAL_INCIDENTS("SELECT COUNT(*) FROM incidents"),
        INCIDENTS_EN_COURS("SELECT COUNT(*) FROM incidents WHERE statut = 1"),
        INCIDENTS_RESOLU("SELECT COUNT(*) FROM incidents WHERE statut = 2"),
        INCIDENTS_NO("SELECT COUNT(*) FROM incidens WHERE statut = 0"),
        NON_DEFINI("SELECT COUNT(*) FROM incidents WHERE Priorité = 0"),
        FAIBLE("SELECT COUNT(*) FROM incidents WHERE Priorité = 1"),
        MOYEN("SELECT COUNT(*) FROM incidents WHERE Priorité = 2"),
        HAUT("SELECT COUNT(*) FROM incidents WHERE Priorité = 3");

        private final String query;

        Queries(final String query) {
            this.query = query;
        }

        public String getQuery() {
            return query;
        }
    }

    public DashboardDatas fetchDashboardStats(final Connection connection) throws SQLException {
        final Statement stmt = connection.createStatement();

        int totalIncident = countIncidents(stmt, Queries.TOTAL_INCIDENTS.getQuery());
        int incidentEnCours = countIncidents(stmt, Queries.INCIDENTS_EN_COURS.getQuery());
        int incidentResolu = countIncidents(stmt, Queries.INCIDENTS_RESOLU.getQuery());
        int incidentNonOuvert = countIncidents(stmt, Queries.INCIDENTS_NO.getQuery());
        int nonDefini = countIncidents(stmt, Queries.NON_DEFINI.getQuery());
        int faible = countIncidents(stmt, Queries.FAIBLE.getQuery());
        int moyen = countIncidents(stmt, Queries.MOYEN.getQuery());
        int haut = countIncidents(stmt, Queries.HAUT.getQuery());

        //return new DashboardDatas();
    //}
    
    /*public DashboardDatas fetchDashboardStats(final Connection connection) throws SQLException {
        final Statement stmt = connection.createStatement();
    
        int totalIncident = countIncidents(stmt, Queries.TOTAL_INCIDENTS.getQuery());
        int incidentEnCours = countIncidents(stmt, Queries.INCIDENTS_EN_COURS.getQuery());
        int incidentResolu = countIncidents(stmt, Queries.INCIDENTS_RESOLU.getQuery());
        int incidentNonOuvert = countIncidents(stmt, Queries.INCIDENTS_NO.getQuery());
        int nonDefini = countIncidents(stmt, Queries.NON_DEFINI.getQuery());
        int faible = countIncidents(stmt, Queries.FAIBLE.getQuery());
        int moyen = countIncidents(stmt, Queries.MOYEN.getQuery());
        int haut = countIncidents(stmt, Queries.HAUT.getQuery());
    */

        // Création d'un objet DashboardData avec les valeurs récupérées
        DashboardData dashboardData = new DashboardData();
        dashboardData.setTotalIncident(totalIncident);
        dashboardData.setIncidentEnCours(incidentEnCours);
        dashboardData.setIncidentResolu(incidentResolu);
        dashboardData.setIncidentNonOuvert(incidentNonOuvert);
        dashboardData.setNonDefini(nonDefini);
        dashboardData.setFaible(faible);
        dashboardData.setMoyen(moyen);
        dashboardData.setHaut(haut);
    
        // Ajout du DashboardData dans DashboardDatas
        DashboardDatas dashboardDatas = new DashboardDatas();
        dashboardDatas.add(dashboardData);
        return dashboardDatas;
    }
    

    private int countIncidents(Statement stmt, String query) throws SQLException {
        ResultSet res = stmt.executeQuery(query);
        if (res.next()) {
            return res.getInt(1);
        }
        return 0;
    }

    public static DashboardRepository inst = null;
    public static DashboardRepository getInstance() {
        if (inst == null) {
            inst = new DashboardRepository();
        }
        return inst;
    }

    private DashboardRepository() {
    }


    //final Response response = DashboardRepository.fetchDashboardStats(connection);
public final Response dispatch(final Request request, final Connection connection)
            throws SQLException, IOException {
        Response response = null;

        if ("DASHBOARD_REQUEST".equals(request.getRequestOrder())) {
            response = getDashboardData(request, connection);
        } else {
            logger.error("Requête non reconnue : {}", request.getRequestOrder());
        }

        return response;
    }

    private Response getDashboardData(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();

        // récupérer les statistiques grace à repository
        DashboardDatas dashboardDatas = dashboardRepository.fetchDashboardStats(connection);

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(dashboardDatas));
    }

}
