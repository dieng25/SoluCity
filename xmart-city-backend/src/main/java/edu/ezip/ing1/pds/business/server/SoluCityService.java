package edu.ezip.ing1.pds.business.server;

import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class SoluCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private static SoluCityService inst = null;
    private final InterfaceCitoyenService interfaceCitoyenService;
    private final IncidentService incidentService;
    private final DashboardRepository dashboardRepository;

    public SoluCityService() {
        this.interfaceCitoyenService = InterfaceCitoyenService.getInstance();
        this.dashboardRepository = DashboardRepository.getInstance();
        this.incidentService = IncidentService.getInstance();
    }


    public static final SoluCityService getInstance() {
        if (inst == null) {
            inst = new SoluCityService();
        }
        return inst;
    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;

        try {
            switch (request.getRequestOrder()) {
                case "INSERT_CITOYEN":
                case "INSERT_INCIDENT":
                case "SELECT_ALL_MAIRIES":
                case "SELECT_CITOYEN":
                    response = interfaceCitoyenService.dispatch(request, connection);
                    break;
                case "DASHBOARD_REQUEST":
                    response = dashboardRepository.dispatch(request, connection);
                    break;
                case "SELECT_ALL_INCIDENTS":
                    response = incidentService.dispatch(request, connection);
                    break;
            }
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de la requête", e);
        }
        return response;
    }

}



