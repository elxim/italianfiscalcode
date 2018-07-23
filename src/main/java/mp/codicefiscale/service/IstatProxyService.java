package mp.codicefiscale.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mp.codicefiscale.CfProperties;


@RestController
public class IstatProxyService {

	
	@Autowired
	CfProperties serviceUrl;
	

	public String getCodiceComune(String comune, String sigla) throws JsonProcessingException, IOException {

	
		
		RestTemplate restTemplate = new RestTemplate();
		
		//serviceUrl = "http://192.168.125.140:8084/istatproxy/comuni/%s";

		ResponseEntity<String> responseEntity = restTemplate.getForEntity(String.format(serviceUrl.getIstatproxyUrl(), comune),
				String.class);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(responseEntity.getBody());
		
		JsonNode comuni = node.path("comuni");

		if (comuni.isArray()) { // Se ci sono più comuni controllo la sigla
		    for (final JsonNode objNode : comuni) {
		    	
		    	JsonNode comuneNode = objNode.path("comune");
		    	
		    	if (comuneNode.isMissingNode()) {
					// if "name" node is missing
				} else {

					if (   (sigla.equalsIgnoreCase("IT") || 
							sigla.equalsIgnoreCase("EE") ||
							sigla.length()>2 ||
							sigla.equalsIgnoreCase(comuneNode.path("sigla").asText()
							))
							
							&& (comune.length()==comuneNode.path("denominazioneItaliano").asText().length()))
					{
						return comuneNode.path("codiceCatastaleComune").asText();
					}
				}
		    }
		} else { // c'è un solo comune
			JsonNode comuneNode = comuni.path("comune");
	    	
	    	if (comuneNode.isMissingNode()) {
				// if "name" node is missing
			} else {

				//if (sigla.trim().equalsIgnoreCase(comuneNode.path("sigla").asText().trim())) {
					return comuneNode.path("codiceCatastaleComune").asText();
				//}
			}
		}

		return "----";
	}

}
