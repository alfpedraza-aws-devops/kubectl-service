package mx.pedraza.kubernetes_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.pedraza.kubernetes_api.services.NodesService;


@RestController()
@RequestMapping("/api/nodes")
public class NodesController {

	@Autowired
	private NodesService service;

	@GetMapping("/count")
	public int count() {
		return service.getCount();
	}

	@GetMapping("/status")
	public String status() {
		return service.getStatus();
	}
}