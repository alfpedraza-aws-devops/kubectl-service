package mx.pedraza.kubernetes_api.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CommandLineHelper {

    public String execute(String commandLine) {
        try {
            // Splits the command line into an array of arguments.
            String[] arguments = commandLine.split(" ");
            List<String> processArgs = Arrays.asList(arguments);

            // Creates a new process redirecting the error stream to the output.
            ProcessBuilder processBuilder = new ProcessBuilder(processArgs);
	        processBuilder.redirectErrorStream(true);
	        Process process = processBuilder.start();
	        StringBuilder processOutput = new StringBuilder();

            // Reads the output from the process.
	        BufferedReader processOutputReader = new BufferedReader(
			    new InputStreamReader(process.getInputStream()));
	        String readLine;
            while ((readLine = processOutputReader.readLine()) != null) {
                processOutput.append(readLine + System.lineSeparator());
            }
            process.waitFor();

            // Returns the output.
            String result = processOutput.toString();
            return result;
        } catch(Exception e) {
            return e.toString();
        }
    }
}