package mx.pedraza.kubernetes_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.pedraza.kubernetes_api.services.NodesService;

/**
 * Exposes a REST endpoint to GET information about the nodes in the Kubernetes cluster.
 */
@RestController()
@RequestMapping("/api/nodes")
public class NodesController {

	@Autowired
	private NodesService service;

	/**
	 * Gets the number of nodes present in the cluster.
	 * @return An integer representing the node count in the cluster.
	 */
	@GetMapping("/count")
	public int count() {
		return service.getCount();
	}

	/**
	 * Gets the CPU and memory metrics of the nodes present in the cluster.
	 * @return A string containing the output metrics of 'kubectl top nodes'.
	 */
	@GetMapping("/status")
	public String status() {
		return service.getStatus();
	}
}