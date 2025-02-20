package edu.ezip.ing1.pds.business.server;

import edu.ezip.ing1.pds.business.dto.DashboardData;
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
        //CitoyenService citoyenService = new CitoyenService();
        this.dashboardRepository = new DashboardRepository();
        
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
        DashboardData dashboardData = dashboardRepository.fetchDashboardStats(connection);

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(dashboardData));
    }
}
