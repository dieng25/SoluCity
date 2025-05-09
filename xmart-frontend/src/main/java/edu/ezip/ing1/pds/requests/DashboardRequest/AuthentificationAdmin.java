package edu.ezip.ing1.pds.requests.DashboardRequest;
import edu.ezip.ing1.pds.business.dto.DashboardDto.AdminDashboard;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import java.io.IOException;

public class AuthentificationAdmin extends ClientRequest<Object, Boolean> {

    public AuthentificationAdmin(
            NetworkConfig networkConfig, int myBirthDate, Request request, AdminDashboard f, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, f, bytes);
    }

    @Override
    public Boolean readResult(String body) throws IOException {
        // Le serveur renvoie "true" si l'authentification est réussie, "false" sinon
        return Boolean.parseBoolean(body);
    }
}
