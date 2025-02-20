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

    public static SoluCityService inst = null;
    public static final SoluCityService getInstance()  {
        if(inst == null) {
            inst = new SoluCityService();
        }
        return inst;
    }

    private SoluCityService() {

    }


}
