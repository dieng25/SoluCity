package edu.ezip.ing1.pds.requests.DashboardRequest;
import edu.ezip.ing1.pds.business.dto.DashboardDto.UserDashboard;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import java.io.IOException;

public class EnregistrementUtilisateur extends ClientRequest<Object, Boolean> {

    public EnregistrementUtilisateur(
            NetworkConfig networkConfig, int myBirthDate, Request request, UserDashboard f, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, f, bytes);
    }

    @Override
    public Boolean readResult(String body) throws IOException {
        // Le serveur renvoie "true" ou "false"
        return Boolean.parseBoolean(body);
    }
}