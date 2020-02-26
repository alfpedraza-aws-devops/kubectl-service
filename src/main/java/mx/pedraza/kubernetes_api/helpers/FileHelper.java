package mx.pedraza.kubernetes_api.helpers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Allows to perform file and resources manipulations.
 */
@Service
public class FileHelper {

    /**
     * Reads the resource and return its contents.
     * @param resourcePath The path of the resource to read.
     * @return A string with the contents of the resource.
     */
    public String readResource(String resourcePath) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(resourcePath);
            InputStream inputStream = classPathResource.getInputStream();
            String result = getStringFromInputStream(inputStream);            
            return result;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Reads the contents of the InputStream and returns a String.
    private String getStringFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1)
            outputStream.write(buffer, 0, length);

        Charset charset = StandardCharsets.UTF_8;
        String result = outputStream.toString(charset);
        return result;
    }

    /**
     * Writes the file content to the specified path.
     * @param filePath The path of the file to write.
     * @param content The contents of the file to write.
     */
    public void writeFile(String filePath, String content) {
        try {
            Path path = Paths.get(filePath);
            Charset charset = StandardCharsets.UTF_8;
            byte[] bytes = content.getBytes(charset);
            Files.write(path, bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes the specified file.
     * @param filePath The path of the file to delete.
     */
    public void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.delete(path);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}