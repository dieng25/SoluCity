package edu.ezip.ing1.pds.requests;

import edu.ezip.ing1.pds.business.dto.Fonctionnaire;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class AuthenticateFonctionnaireClientRequest extends ClientRequest<Object, Boolean> {

    public AuthenticateFonctionnaireClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Fonctionnaire f, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, f, bytes);
    }

    @Override
    public Boolean readResult(String body) throws IOException {
        // Le serveur renvoie "true" si l'authentification est réussie, "false" sinon
        return Boolean.parseBoolean(body);
    }
}
