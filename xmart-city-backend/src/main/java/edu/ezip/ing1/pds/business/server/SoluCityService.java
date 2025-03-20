package edu.ezip.ing1.pds.business.server;

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
    private final InterfaceCitoyenService interfaceCitoyenService;
    private final MairieServices mairieServices;
    private final DashboardRepository dashboardRepository;

    public SoluCityService() {
        this.interfaceCitoyenService = InterfaceCitoyenService.getInstance();
        this.mairieServices = MairieServices.getInstance();
        this.dashboardRepository = DashboardRepository.getInstance();
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
                case "INSERT_CITOYEN":
                case "INSERT_INCIDENT":
                case "SELECT_ALL_MAIRIES":
                    response = interfaceCitoyenService.dispatch(request, connection);
                    break;
                case "DASHBOARD_REQUEST":
                    response = dashboardRepository.fetchDashboardData(request, connection);
                    break;
                case "GLOBAL_REQUEST":
                    response = dashboardRepository.fetchGlobalData(request, connection, dateDebut, dateFin, codePostal);
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