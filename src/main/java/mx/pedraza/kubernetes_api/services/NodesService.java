package mx.pedraza.kubernetes_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodesService {

    @Autowired
    private CommandLineHelper commandLine;

    public int getCount() {
        String script = "kubectl get nodes";
        String result = commandLine.execute(script);
        // Convert result to integer.
        int count = 0;
        return count;
    }

    public String getStatus() {
        String script = "kubectl top nodes";
        String result = commandLine.execute(script);
        return result;
    }
}