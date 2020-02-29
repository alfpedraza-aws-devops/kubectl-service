package mx.pedraza.kubernetes_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.pedraza.kubernetes_api.helpers.FileHelper;
import mx.pedraza.kubernetes_api.helpers.JsonHelper;
import mx.pedraza.kubernetes_api.helpers.ShellHelper;
import mx.pedraza.kubernetes_api.models.FibonacciJobRequestModel;

/**
 * Contains logic to get, create and delete a Fibonacci Job instance.
 */
@Service
public class FibonacciJobService {

    @Autowired
    private ShellHelper shellHelper;

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private JsonHelper jsonHelper;

    // Gets the "jobTargetUrl" parameter from the application properties.
    @Value("${jobTargetUrl}")
    private String jobTargetUrl;

    /**
     * Gets the number of fibonacci job instances running in the cluster.
     * This can be either 0 or 1 since only one instance is allowed to run
     * at the same time by this application. See "createJob" method for more info.
     * @return The number of fibonacci job instances running.
     */
    public int getCount() {
        String script = "kubectl get job fibonacci-job -o json";
        String json = shellHelper.execute(script);
        if (!jsonHelper.isValid(json)) return 0;
        int result = jsonHelper.getArrayLength(json);
        return result;
    }

    /**
     * Gets the values used to create the executing fibonacci job.
     * @return A FibonacciJobRequestModel containing the parameters used to create the job.
     */
    public FibonacciJobRequestModel getParameters() {
        String script = "kubectl get job fibonacci-job -o json";
        String json = shellHelper.execute(script);
        FibonacciJobRequestModel result = new FibonacciJobRequestModel();
        if (!jsonHelper.isValid(json)) return result;

        int requests = Integer.valueOf(jsonHelper.read(json, "$.spec.template.spec.containers[0].env[?(@.name=='REQUESTS')].value"));
        int concurrency = Integer.valueOf(jsonHelper.read(json, "$.spec.template.spec.containers[0].env[?(@.name=='CONCURRENCY')].value"));
        result.setRequests(requests);
        result.setConcurrency(concurrency);

        return result;
    }

    /**
     * Creates a new fibonacci job.
     * @param details The parameters used to create the job.
     */
    public void createJob(FibonacciJobRequestModel details) {
        // This operation is idempotent.
        // So if there is one running instance, then just return.
        if (getCount() > 0) return;

        // Get the parameter values.
        String fileName = "fibonacci-job.yaml";
        String requests = String.valueOf(details.getRequests());
        String concurrency = String.valueOf(details.getConcurrency());

        // Build the Kubernetes manifest file with the parameters.
        String template = fileHelper.readResource(fileName);
        template = template.replaceAll("##REQUESTS##", requests);
        template = template.replaceAll("##CONCURRENCY##", concurrency);
        template = template.replaceAll("##JOB_TARGET_URL##", jobTargetUrl);
        fileHelper.writeFile(fileName, template);

        // Applies the manifest in Kubernetes and deletes the temporary file.
        String script = "kubectl apply -f " + fileName;
        String result = shellHelper.execute(script);
        System.out.println(result);
        fileHelper.deleteFile(fileName);
    }

    /**
     * Deletes the fibonacci job.
     */
    public void deleteJob() {
        // This operation is idempotent.
        // So if there is nothing to delete, just return.
        if (getCount() == 0) return;

        String script = "kubectl delete job fibonacci-job";
        shellHelper.execute(script);
    }
    
}