package edu.ezip.ing1.pds.business.server;

import edu.ezip.ing1.pds.business.dto.DashboardData;
import edu.ezip.ing1.pds.business.dto.DashboardDatas;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.DashboardDto.GlobalData;
import edu.ezip.ing1.pds.business.dto.DashboardDto.GlobalDatas;
import edu.ezip.ing1.pds.business.dto.DashboardDto.StatIncidentData;
import edu.ezip.ing1.pds.business.dto.DashboardDto.StatIncidentDatas;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.PreparedStatement;

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
                        "    (SELECT COUNT(*) FROM Incident WHERE Categorie = 'Espaces Verts' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentCatEspaceVerts,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE Categorie = 'Proprete' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentCatProprete,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE Categorie = 'Animaux errants ou retrouvés morts' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentCatAnimauxErrants,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE Categorie = 'Autres' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentCatAutres,\r\n" + //
                        "\r\n" + //
                        "    -- Répartition des suggestions par catégorie\r\n" + //
                        "    (SELECT COUNT(*) FROM Suggestion WHERE Categorie = 'Voirie' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS suggestionCatVoirie,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Suggestion WHERE Categorie = 'Éclairage Public' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS suggestionCatEclairagePublic,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Suggestion WHERE Categorie = 'Espaces Verts' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS suggestionCatEspaceVerts,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Suggestion WHERE Categorie = 'Propreté' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS suggestionCatProprete,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Suggestion WHERE Categorie = 'Animaux errants ou retrouvés morts' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS suggestionCatAnimauxErrants,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Suggestion WHERE Categorie = 'Autres' AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS suggestionCatAutres,\r\n" + //
                        "\r\n" + //
                        "    -- Répartition des incidents par priorité\r\n" + //
                        "\r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE Priorite = 0 AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentNonDefini,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE Priorite = 1 AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentLevelBas,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE Priorite = 2 AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentLevelMoyen,\r\n" + //
                        "    \r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE Priorite = 3 AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentLevelHaut\r\n" + //
                        ""),

        STAT_INCIDENT_REQUEST("SELECT\r\n" + //
                        "    -- Répartition des incidents par statut\r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE Statut = 0 AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentStatutEnAttente,\r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE Statut = 1 AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentStatutEnCours,\r\n" + //
                        "    (SELECT COUNT(*) FROM Incident WHERE Statut = 2 AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS incidentStatutResolus,\r\n" + //
                        "\r\n" + //
                        "    -- Délai moyen de résolution des incidents par catégorie\r\n" + //
                        "    (SELECT ROUND(AVG(DATEDIFF(date_cloture, date_creation)), 2) FROM Incident WHERE Categorie = 'Voirie' AND date_cloture IS NOT NULL AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS delaiMoyenVoirie,\r\n" + //
                        "    (SELECT ROUND(AVG(DATEDIFF(date_cloture, date_creation)), 2) FROM Incident WHERE Categorie = 'Eclairage Public' AND date_cloture IS NOT NULL AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS delaiMoyenEclairagePublic,\r\n" + //
                        "    (SELECT ROUND(AVG(DATEDIFF(date_cloture, date_creation)), 2) FROM Incident WHERE Categorie = 'Espaces Verts' AND date_cloture IS NOT NULL AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS delaiMoyenEspaceVerts,\r\n" + //
                        "    (SELECT ROUND(AVG(DATEDIFF(date_cloture, date_creation)), 2) FROM Incident WHERE Categorie = 'Proprete' AND date_cloture IS NOT NULL AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS delaiMoyenProprete,\r\n" + //
                        "    (SELECT ROUND(AVG(DATEDIFF(date_cloture, date_creation)), 2) FROM Incident WHERE Categorie = 'Animaux errants ou retrouvés morts' AND date_cloture IS NOT NULL AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS delaiMoyenAnimaux,\r\n" + //
                        "    (SELECT ROUND(AVG(DATEDIFF(date_cloture, date_creation)), 2) FROM Incident WHERE Categorie = 'Autres' AND date_cloture IS NOT NULL AND (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout')) AS delaiMoyenAutres,\r\n" + //
                        "\r\n" + //
                        "    -- Top 2 des catégories des incidents les plus signalées\r\n" + //
                        "    (SELECT Categorie FROM Incident WHERE (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout') GROUP BY Categorie ORDER BY COUNT(*) DESC LIMIT 1) AS topIncidentCategorie1,\r\n" + //
                        "    (SELECT Categorie FROM Incident WHERE (date_creation BETWEEN ? AND ?) AND (CodePostal_ticket = ? OR ? = 'tout') GROUP BY Categorie ORDER BY COUNT(*) DESC LIMIT 1 OFFSET 1) AS topIncidentCategorie2,\r\n" + //
                        "\r\n" + //
                        "    -- Tableau des incidents les plus urgents (priorité élevée, en attente de traitement)\r\n" + //
                        "    (SELECT * \n" + //
                                 "FROM Incident \n" + //
                                 "WHERE Priorite = 3  \n" + //
                                 "AND Statut = 0  \n" + //
                                 "AND (date_creation BETWEEN ? AND ?)  \n" + //
                                 "AND (CodePostal_ticket = ? OR ? = 'tout')  \n" + //
                                 "ORDER BY date_creation ASC;\n" + //
                        ");"
        );
        

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
 
   public Response fetchDashboardData(final Request request, final Connection connection)
            throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        DashboardDatas dashboardDatas = new DashboardDatas();
        DashboardData dashboardData = new DashboardData();

        // DASHBOARD_REQUEST avec Statement
        try (Statement stmt = connection.createStatement();
             ResultSet dashboardResult = stmt.executeQuery(Queries.DASHBOARD_REQUEST.query)) {
            if (dashboardResult.next()) {
                dashboardData.setTotalIncident(dashboardResult.getInt("total_incidents"));
        dashboardData.setIncidentEnCours(dashboardResult.getInt("incidents_en_cours"));
        dashboardData.setIncidentResolu(dashboardResult.getInt("incidents_resolus"));
        dashboardData.setIncidentNonOuvert(dashboardResult.getInt("incidents_non_ouverts"));
        dashboardData.setNonDefini(dashboardResult.getInt("priorite_non_defini"));
        dashboardData.setFaible(dashboardResult.getInt("priorite_faible"));
        dashboardData.setMoyen(dashboardResult.getInt("priorite_moyenne"));
        dashboardData.setHaut(dashboardResult.getInt("priorite_haute"));
            }
        }
        dashboardDatas.getDashboardDataSet().add(dashboardData);
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(dashboardDatas));
    }
    
    
    
    public Response fetchGlobalData(final Request request, final Connection connection, Date dateDebut, Date dateFin, String codePostal)
    throws SQLException, JsonProcessingException {

        final ObjectMapper objectMapper = new ObjectMapper();
        GlobalDatas globalDatas = new GlobalDatas();
        GlobalData globalData = new GlobalData();
         
        // GLOBAL_REQUEST avec PreparedStatement
         try (PreparedStatement globalPs = connection.prepareStatement(Queries.GLOBAL_REQUEST.query)) {
            int index = 1;
            for (int i = 0; i < 18; i++) {
                globalPs.setDate(index++, dateDebut);
                globalPs.setDate(index++, dateFin);
                globalPs.setString(index++, codePostal);
                globalPs.setString(index++, codePostal);
            }

            ResultSet globalResult = globalPs.executeQuery();
            if (globalResult.next()) {
                globalData.setTotalIncidents(globalResult.getInt("totalIncidents"));
                globalData.setTotalSuggestion(globalResult.getInt("totalSuggestion"));

                globalData.setIncidentCatVoirie(globalResult.getInt("incidentCatVoirie"));
                globalData.setIncidentCatEclairagePublic(globalResult.getInt("incidentCatEclairagePublic"));
                globalData.setIncidentCatEspaceVerts(globalResult.getInt("incidentCatEspaceVerts"));
                globalData.setIncidentCatProprete(globalResult.getInt("incidentCatProprete"));
                globalData.setIncidentCatAnimauxErrants(globalResult.getInt("incidentCatAnimauxErrants"));
                globalData.setIncidentCatAutres(globalResult.getInt("incidentCatAutres"));

                globalData.setSuggestionCatVoirie(globalResult.getInt("suggestionCatVoirie"));
                globalData.setSuggestionCatEclairagePublic(globalResult.getInt("suggestionCatEclairagePublic"));
                globalData.setSuggestionCatEspaceVerts(globalResult.getInt("suggestionCatEspaceVerts"));
                globalData.setSuggestionCatProprete(globalResult.getInt("suggestionCatProprete"));
                globalData.setSuggestionCatAnimauxErrants(globalResult.getInt("suggestionCatAnimauxErrants"));
                globalData.setSuggestionCatAutres(globalResult.getInt("suggestionCatAutres"));

                globalData.setIncidentNonDefini(globalResult.getInt("incidentNonDefini"));
                globalData.setIncidentLevelBas(globalResult.getInt("incidentLevelBas"));
                globalData.setIncidentLevelMoyen(globalResult.getInt("incidentLevelMoyen"));
                globalData.setIncidentLevelHaut(globalResult.getInt("incidentLevelHaut"));
            }
        }
        globalDatas.getGlobalDataSet().add(globalData);
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(globalDatas));

    }

public Response fetchIncidentStatData(final Request request, final Connection connection, Date dateDebut, Date dateFin, String codePostal)
        throws SQLException, JsonProcessingException {

    final ObjectMapper objectMapper = new ObjectMapper();
    StatIncidentDatas statIncidentDatas = new StatIncidentDatas();
    StatIncidentData statIncidentData = new StatIncidentData();

    try (PreparedStatement statPs = connection.prepareStatement(Queries.STAT_INCIDENT_REQUEST.query)) {
        int index = 1;
        final int NbrSousRequetes = 11; 
        for (int i = 0; i < NbrSousRequetes; i++) {
            statPs.setDate(index++, dateDebut);
            statPs.setDate(index++, dateFin);
            statPs.setString(index++, codePostal);
            statPs.setString(index++, codePostal);
        }

        ResultSet statResult = statPs.executeQuery();

        if (statResult.next()) {
            statIncidentData.setIncidentNonResolu(statResult.getInt("incidentStatutEnAttente"));
            statIncidentData.setIncidentEnCours(statResult.getInt("incidentStatutEnCours"));
            statIncidentData.setIncidentResolu(statResult.getInt("incidentStatutResolus"));

            statIncidentData.setDelaiVoirie(statResult.getDouble("delaiMoyenVoirie"));
            statIncidentData.setDelaiEclairagePublic(statResult.getDouble("delaiMoyenEclairagePublic"));
            statIncidentData.setDelaiEspaceVerts(statResult.getDouble("delaiMoyenEspaceVerts"));
            statIncidentData.setDelaiProprete(statResult.getDouble("delaiMoyenProprete"));
            statIncidentData.setDelaiAnimauxErrants(statResult.getDouble("delaiMoyenAnimaux"));
            statIncidentData.setDelaiAutres(statResult.getDouble("delaiMoyenAutres"));

            statIncidentData.setIncidentTop1(statResult.getString("topIncidentCategorie1"));
            statIncidentData.setIncidentTop2(statResult.getString("topIncidentCategorie2"));
        }

        // pour les incidents urgents
        if (statResult.getStatement().getMoreResults()) {
            ResultSet urgentResult = statResult.getStatement().getResultSet();
            while (urgentResult.next()) {
                Incident incident = null;
                try {
                    incident = new Incident().build(urgentResult);
                } catch (NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
                statIncidentData.getIncidentsUrgents().add(incident);
            }
        }
    }

    statIncidentDatas.getStatIncidentDataSet().add(statIncidentData);
    return new Response(request.getRequestId(), objectMapper.writeValueAsString(statIncidentDatas));
}

public final Response dispatch(final Request request, final Connection connection, Date dateDebut, Date dateFin, String codePostal)
    throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
Response response = null;

final Queries queryEnum = Enum.valueOf(Queries.class, request.getRequestOrder());
switch(queryEnum) {
    case DASHBOARD_REQUEST:
        response = fetchDashboardData(request, connection);
        break;
    case GLOBAL_REQUEST:
        response = fetchGlobalData(request, connection, dateDebut, dateFin, codePostal);
        break;
    case STAT_INCIDENT_REQUEST:
        response = fetchIncidentStatData(request, connection, dateDebut, dateFin, codePostal);
        break;
    default:
        break;
}

return response;
}    
}