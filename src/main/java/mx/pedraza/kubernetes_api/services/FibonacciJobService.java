package mx.pedraza.kubernetes_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.pedraza.kubernetes_api.helpers.FileHelper;
import mx.pedraza.kubernetes_api.helpers.JsonHelper;
import mx.pedraza.kubernetes_api.helpers.ShellHelper;
import mx.pedraza.kubernetes_api.models.FibonacciJobRequestModel;

/**
 * 
 */
@Service
public class FibonacciJobService {

    @Autowired
    private ShellHelper shellHelper;

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private JsonHelper jsonHelper;

    // Gets the parameter value from the application properties.
    @Value("${jobTargetUrl}")
    private String jobTargetUrl;

    /**
     * 
     * @return
     */
    public int getCount() {
        String script = "kubectl get job fibonacci-job -o json";
        String json = shellHelper.execute(script);
        if (!jsonHelper.isValid(json)) return 0;
        int result = jsonHelper.getElementLength(json);
        return result;
    }

    /**
     * 
     * @return
     */
    public String getStatus() {
        String script = "kubectl describe hpa fibonacci";
        String result = shellHelper.execute(script);
        return result;
    }

    /**
     * 
     * @return
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
     * 
     * @param details
     * @return
     */
    public void createJob(FibonacciJobRequestModel details) {
        // This operation is idempotent.
        // So if there is nothing to create, just return.
        if (getCount() > 0) return;

        // Get the parameter values.
        String fileName = "fibonacci-job.yaml";
        String requests = String.valueOf(details.getRequests());
        String concurrency = String.valueOf(details.getConcurrency());

        // Build the manifest file with the parameters.
        String file = fileHelper.readResource(fileName);
        String template = "";
        template = file.replaceAll("##REQUESTS##", requests);
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
     * 
     */
    public void deleteJob() {
        // This operation is idempotent.
        // So if there is nothing to delete, just return.
        if (getCount() == 0) return;

        String script = "kubectl delete job fibonacci-job";
        shellHelper.execute(script);
    }
}