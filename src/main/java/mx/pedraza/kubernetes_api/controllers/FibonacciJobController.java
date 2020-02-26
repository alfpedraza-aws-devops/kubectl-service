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
 * Exposes a REST endpoint to perform several operations with the fibonacci.
 */
@RestController()
@RequestMapping("/api/fibonacci-job")
public class FibonacciJobController {

	@Autowired
	private FibonacciJobService service;

	/**
	 * 
	 * @return
	 */
	@GetMapping("/count")
	public int count() {
		return service.getCount();
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/status")
	public String status() {
		return service.getStatus();
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/parameters")
	public FibonacciJobRequestModel parameters() {
		return service.getParameters();
	}

	/**
	 * 
	 * @param details
	 * @return
	 */
	@PutMapping()
	public ResponseEntity<Void> put(@RequestBody FibonacciJobRequestModel details) {
		service.createJob(details);
		return ResponseEntity.ok().build();
	}

	/**
	 * 
	 * @return
	 */
	@DeleteMapping()
	public ResponseEntity<Void> delete() {
		service.deleteJob();
		return ResponseEntity.ok().build();
	}
}