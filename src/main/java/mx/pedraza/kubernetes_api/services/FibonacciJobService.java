package mx.pedraza.kubernetes_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.pedraza.kubernetes_api.models.FibonacciJobRequestModel;

@Service
public class FibonacciJobService {

    @Autowired
    private CommandLineHelper commandLine;

    public int getCount() {
        String script = "kubectl get job fibonacci-load";
        String result = commandLine.execute(script);
        // Convert result to integer.
        int count = 0;
        return count;
    }

    public String getStatus() {
        String script = "kubectl describe hpa fibonacci-load";
        String result = commandLine.execute(script);
        return result;
    }

    public FibonacciJobRequestModel getParameters() {
        String script = "kubectl get job fibonacci-load -o json";
        String output = commandLine.execute(script);
        // Convert result to json
        // Get concurrency from json
        // Get requests from json
        FibonacciJobRequestModel result = new FibonacciJobRequestModel();
        result.setConcurrency(0);
        result.setRequests(0);
        return result;
    }

    public boolean createJob(FibonacciJobRequestModel details) {
        String template = getJobTemplate(details);
        String script = "kubectl apply -f -"; // Append the job template.
        String result = commandLine.execute(script);
        return true;
    }

    private String getJobTemplate(FibonacciJobRequestModel details) {
        String template = "";
        // Read Template File
        // Replace Parameters
        // Get Generated Template
        return template;
    }

    public boolean deleteJob() {
        String script = "kubectl delete job fibonacci-load";
        String result = commandLine.execute(script);
        return true;
    }
}