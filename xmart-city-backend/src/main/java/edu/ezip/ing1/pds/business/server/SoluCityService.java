package edu.ezip.ing1.pds.business.server;

import edu.ezip.ing1.pds.business.server.RequestInterfaceCitoyenService.RequestCitoyen;
import edu.ezip.ing1.pds.business.server.RequestInterfaceCitoyenService.RequestIncident;
import edu.ezip.ing1.pds.business.server.RequestInterfaceCitoyenService.RequestMairie;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class SoluCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private static SoluCityService inst = null;
    private final RequestIncident requestIncident;
    private final RequestMairie requestMairie;
    private final RequestCitoyen requestCitoyen;
    private final IncidentService incidentService;
    private final SuggestionService suggestionService;
    private final FonctionnaireService fonctionnaireService;
    private final DashboardRepository dashboardRepository;

    public SoluCityService() {
        this.dashboardRepository = DashboardRepository.getInstance();
        this.incidentService = IncidentService.getInstance();
        this.suggestionService = SuggestionService.getInstance();
        this.fonctionnaireService = FonctionnaireService.getInstance();
        this.requestIncident = RequestIncident.getInstance();
        this.requestMairie = RequestMairie.getInstance();
        this.requestCitoyen = RequestCitoyen.getInstance();
    }


    public static final SoluCityService getInstance() {
        if (inst == null) {
            inst = new SoluCityService();
        }
        return inst;
    }

    public final Response dispatch(final Request request, final Connection connection, Date dateDebut, Date dateFin, String codePostal)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;

        try {
            switch (request.getRequestOrder()) {
                case "INSERT_INCIDENT":
                case "SELECT_INCIDENT":
                case "SELECT_INCIDENT_BY_TEL":
                    response = requestIncident.dispatch(request, connection);
                    break;
                case "SELECT_ALL_MAIRIES":
                    response = requestMairie.dispatch(request, connection);
                    break;
                case "INSERT_CITOYEN":
                case "SELECT_CITOYEN":
                case "SELECT_TEL_EXIST":
                case "SELECT_CONNEXION":
                case "SELECT_CITOYEN_BY_TEL":
                    response = requestCitoyen.dispatch(request, connection);
                    break;
                case "DASHBOARD_REQUEST":
                    response = dashboardRepository.fetchDashboardData(request, connection);
                    break;
                case "GLOBAL_REQUEST":
                    response = dashboardRepository.fetchGlobalData(request, connection, dateDebut, dateFin, codePostal);
                    break;
                case "SELECT_ALL_INCIDENTS":
                    response = incidentService.dispatch(request, connection);
                    break;
                case "SELECT_ALL_SUGGESTIONS":
                    response = suggestionService.dispatch(request, connection);
                    break;
                case "AUTHENTICATE":
                case "REGISTER":
                    response = fonctionnaireService.dispatch(request, connection);
                    break;
            }
        } catch (SQLException e) {
            logger.error("Erreur SQL lors du traitement de la requête", e);
        } catch (IOException e) {
            logger.error("Erreur d'entrée/sortie lors du traitement de la requête", e);
        } catch (Exception e) {
            logger.error("Erreur inconnue lors du traitement de la requête", e);
        }

        return response;
        
    }
}