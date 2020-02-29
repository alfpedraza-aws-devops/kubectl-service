package mx.pedraza.kubernetes_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.pedraza.kubernetes_api.services.ClusterService;

/**
 * Exposes a REST endpoint to GET information about the machines
 * running in the Kubernetes cluster.
 */
@RestController()
@RequestMapping("/api/cluster")
public class ClusterController {

	@Autowired
	private ClusterService service;

	/**
	 * Gets the number of machines present in the cluster.
	 * @return An integer representing the machine count in the cluster.
	 */
	@GetMapping("/machine-count")
	public int machineCount() {
		return service.getMachineCount();
	}

	/**
	 * Gets the CPU and memory metrics of the machines present in the cluster.
	 * @return A string containing the output metrics of 'kubectl top nodes'.
	 */
	@GetMapping("/status")
	public String status() {
		return service.getStatus();
	}

	/**
	 * Gets the status of the fibonacci horizontal pod autoscaler.
	 * @return A string containing the output of 'kubectl describe hpa fibonacci'
	 */
	@GetMapping("/hpa-status")
	public String hpaStatus() {
		return service.getHpaStatus();
	}

}