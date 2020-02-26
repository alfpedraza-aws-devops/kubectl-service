package mx.pedraza.kubernetes_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.pedraza.kubernetes_api.models.FibonacciJobRequestModel;
import mx.pedraza.kubernetes_api.services.FibonacciJobService;

/**
 * Exposes a REST endpoint to perform several operations with the Fibonacci Job.
 */
@RestController()
@RequestMapping("/api/fibonacci-job")
public class FibonacciJobController {

	@Autowired
	private FibonacciJobService service;

	/**
	 * Gets the number of fibonacci jobs running in the cluster.
	 * Either can be 0 or 1, since only one job will be allowed to be
	 * in execution by this application. See "service.createJob" for details.
	 * @return An integer representing the job count.
	 */
	@GetMapping("/count")
	public int count() {
		return service.getCount();
	}

	/**
	 * Gets the status of the fibonacci deployment.
	 * @return A string containing the output of 'kubectl describe hpa fibonacci'
	 */
	@GetMapping("/status")
	public String status() {
		return service.getStatus();
	}

	/**
	 * Gets the parameters used to execute the fibonacci job.
	 * @return  A FibonacciJobRequestModel object containing the parameters.
	 */
	@GetMapping("/parameters")
	public FibonacciJobRequestModel parameters() {
		return service.getParameters();
	}

	/**
	 * Creates a new fibonacci job. This operation is idempotent meaning that
	 * only one fibonacci job will be created, no matter how many requests are
	 * made to this endpoint.
	 * @param details The parameters used to configure the fibonacci job.
	 * @return An HTTP OK 200 status.
	 */
	@PutMapping()
	public ResponseEntity<Void> put(@RequestBody FibonacciJobRequestModel details) {
		service.createJob(details);
		return ResponseEntity.ok().build();
	}

	/**
	 * Deletes the executing fibonacci job. This operation is idempotent meaning that
	 * this will only delete the existing job if it's running, and it will do nothing if
	 * there is not a running job.
	 * @return An HTTP OK 200 status.
	 */
	@DeleteMapping()
	public ResponseEntity<Void> delete() {
		service.deleteJob();
		return ResponseEntity.ok().build();
	}
}