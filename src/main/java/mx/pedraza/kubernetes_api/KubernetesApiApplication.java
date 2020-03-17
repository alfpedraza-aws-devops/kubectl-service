package mx.pedraza.kubernetes_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the application main class.
 * It's used to run the Spring Boot framework and initialize the app.
 */
@SpringBootApplication
public class KubernetesApiApplication {

	/**
	 * Initializes the Spring Boot framework and starts the web server.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(KubernetesApiApplication.class, args);
	}
	
}
