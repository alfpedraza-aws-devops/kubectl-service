package mx.pedraza.kubernetes_api.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Executes command line scripts.
 */
@Service
public class ShellHelper {

    /**
     * Executes a command line script and gets the output.
     * @param commandLine The command line to execute.
     * @return A string containing the output of the execution.
     */
    public String execute(String commandLine) {
        try {
            // Splits the command line into an array of string arguments.
            String[] arguments = commandLine.split(" ");
            List<String> processArgs = Arrays.asList(arguments);

            // Creates a new process, redirecting any error stream to the output.
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
            throw new RuntimeException(e);
        }
    }
    
}