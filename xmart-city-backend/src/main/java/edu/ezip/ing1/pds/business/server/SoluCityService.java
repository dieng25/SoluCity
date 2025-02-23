package edu.ezip.ing1.pds.business.server;

import edu.ezip.ing1.pds.business.dto.DashboardData;
import edu.ezip.ing1.pds.business.dto.DashboardDatas;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class SoluCityService {

    private final InterfaceCitoyenService citoyenService;
    private final MairieServices mairieServices;
    private final DashboardRepository dashboardRepository;

    public SoluCityService() {
        this.citoyenService = InterfaceCitoyenService.getInstance();
        this.mairieServices = MairieServices.getInstance();
        this.dashboardRepository = new DashboardRepository();
    }


    public Response SoluCityService(Request request, Connection connection)
            throws SQLException, IOException, InvocationTargetException, IllegalAccessException {

        switch (request.getRequestOrder()) {
            case "INSERT_CITOYEN":
            case "INSERT_INCIDENT":
                return citoyenService.dispatch(request, connection);

            default:
                throw new IllegalArgumentException("Requête non supportée : " + request.getRequestOrder());
        }
    }
}

/*
    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final DashboardRepository dashboardRepository;

    public static SoluCityService inst = null;
    public static final SoluCityService getInstance()  {
        if(inst == null) {
            inst = new SoluCityService();
        }
        return inst;
    }

    private SoluCityService() {
        InterfaceCitoyenService citoyenService = new InterfaceCitoyenService();
        this.dashboardRepository = new DashboardRepository();
        MairieServices mairieServices =  MairieServices.getInstance();
        
    }

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
    }*/

