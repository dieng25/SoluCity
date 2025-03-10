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
   
       DASHBOARD_REQUEST("SELECT " +
                "(SELECT COUNT(*) FROM Incident) AS total_incidents, " +
                "(SELECT COUNT(*) FROM Incident WHERE statut = 1) AS incidents_en_cours, " +
                "(SELECT COUNT(*) FROM Incident WHERE statut = 2) AS incidents_resolus, " +
                "(SELECT COUNT(*) FROM Incident WHERE statut = 0) AS incidents_non_ouverts, " +
                "(SELECT COUNT(*) FROM Incident WHERE Priorite = 0) AS priorite_non_defini, " +
                "(SELECT COUNT(*) FROM Incident WHERE Priorite = 1) AS priorite_faible, " +
                "(SELECT COUNT(*) FROM Incident WHERE Priorite = 2) AS priorite_moyenne, " +
                "(SELECT COUNT(*) FROM Incident WHERE Priorite = 3) AS priorite_haute"),

        GLOBAL_REQUEST("SELECT\r\n" + //
                        "    -- Nombre total d'incidents signalés\r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS totalIncidents,\r\n" + //
                        "    \r\n" + //
                        "    -- Nombre total de suggestions envoyées\r\n" + //
                        "    (SELECT COUNT(*) FROM Suggestion WHERE (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS totalSuggestion,\r\n" + //
                        "\r\n" + //
                        "    -- Répartition des incidents par catégorie\r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE Categorie = 'Voirie' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentCatVoirie,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE Categorie = 'Eclairage Public' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentCatEclairagePublic,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE incident_categorie = 'Espaces Verts' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentCatEspaceVerts,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE incident_categorie = 'Propreté' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentCatProprete,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE incident_categorie = 'Animaux errants ou retro' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentCatAnimauxErrants,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE incident_categorie = 'Autres' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentCatAutres,\r\n" + //
                        "\r\n" + //
                        "    -- Répartition des suggestions par catégorie\r\n" + //
                        "    (SELECT COUNT(*) FROM Suggestion WHERE suggestion_categorie = 'Voirie' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS suggestionCatVoirie,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Suggestion WHERE suggestion_categorie = 'Eclairage Public' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS suggestionCatEclairagePublic,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Suggestion WHERE suggestion_categorie = 'Espaces Verts' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS suggestionCatEspaceVerts,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Suggestion WHERE suggestion_categorie = 'Propreté' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS suggestionCatProprete,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Suggestion WHERE suggestion_categorie = 'Animaux errants ou retro' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS suggestionCatAnimauxErrants,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Suggestion WHERE suggestion_categorie = 'Autres' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS suggestionCatAutres,\r\n" + //
                        "\r\n" + //
                        "    -- Répartition des incidents par priorité\r\n" + //
                        "\r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE incident_priorite = 0 AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentNonDefini,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE incident_priorite = 1 AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentLevelBas,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE incident_priorite = 2 AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentLevelMoyen,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE incident_priorite = 3 AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentLevelHaut\r\n" + //
                        "");

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