package mx.pedraza.kubernetes_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.pedraza.kubernetes_api.models.FibonacciJobRequestModel;
import mx.pedraza.kubernetes_api.services.FibonacciJobService;

@RestController()
@RequestMapping("/api/fibonacci-job")
public class FibonacciJobController {

	@Autowired
	private FibonacciJobService service;

	@GetMapping("/count")
	public int count() {
		return service.getCount();
	}

	@GetMapping("/status")
	public String status() {
		return service.getStatus();
	}

	@GetMapping("/parameters")
	public FibonacciJobRequestModel parameters() {
		return service.getParameters();
	}

	@PutMapping()
	public String put(@RequestBody FibonacciJobRequestModel details) {
		service.createJob(details);
		return "Fibonacci Job Created";
	}

	@DeleteMapping()
	public String delete() {
		service.deleteJob();
		return "Fibonacci Job Deleted";
	}
}