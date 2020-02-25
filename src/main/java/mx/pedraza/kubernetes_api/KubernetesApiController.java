package mx.pedraza.kubernetes_api;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class KubernetesApiController {

	@Value("${kubernetesApiServerUrl}")
   	private String kubernetesApiServerUrl;

	@GetMapping("/api/kubernetes/metrics")
	public HashMap<String, String> vote() {
		String netCoreMessage = getMessageFromNetCore();
		String springBootMessage = getSpringBootMessage();
		HashMap<String, String> map = new HashMap<>();
		map.put("Message1", netCoreMessage);
		map.put("Message2", springBootMessage);
		return map;
	}

	private String getSpringBootMessage() {
		String currentIp = "";
		try {  
			InetAddress iAddress = InetAddress.getLocalHost();
			currentIp = iAddress.getHostAddress();
		} catch(Exception e) { }
  		
		String now = (new Date()).toString();
		String message = String.format("SpringBoot says Hi! from %s at %s", currentIp, now);
		return message;
	}

	private String getMessageFromNetCore() {
		/*RestTemplate restTemplate = new RestTemplate();
		JsonNode jsonNode = restTemplate.getForObject(this.kubernetesApiServerUrl, JsonNode.class);
		String message = jsonNode.get("message").asText();
		return message;*/
		return "Message";
	}
}