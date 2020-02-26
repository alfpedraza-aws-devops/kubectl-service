package mx.pedraza.kubernetes_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.pedraza.kubernetes_api.helpers.ShellHelper;
import mx.pedraza.kubernetes_api.helpers.JsonHelper;

/**
 * 
 */
@Service
public class NodesService {

    @Autowired
    private ShellHelper shellHelper;

    @Autowired
    private JsonHelper jsonHelper;

    /**
     * 
     * @return
     */
    public int getCount() {
        String script = "kubectl get nodes -o json";
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
        String script = "kubectl top nodes";
        String result = shellHelper.execute(script);
        return result;
    }
}