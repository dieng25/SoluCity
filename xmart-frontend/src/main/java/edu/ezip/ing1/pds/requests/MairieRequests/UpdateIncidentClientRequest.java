package edu.ezip.ing1.pds.requests;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import java.io.IOException;
import java.util.Map;

public class UpdateIncidentClientRequest extends ClientRequest<Incident, Boolean> {

    public UpdateIncidentClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Incident info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Boolean readResult(String body) throws IOException {
        // On s'attend à ce que la réponse soit un message de succès ou d'échec sous forme de JSON
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Boolean> resultMap = mapper.readValue(body, Map.class);
        // Le JSON devrait contenir un champ "success" qui indique si la mise à jour a été effectuée avec succès
        final Boolean success = resultMap.get("success");
        return success;
    }
}
