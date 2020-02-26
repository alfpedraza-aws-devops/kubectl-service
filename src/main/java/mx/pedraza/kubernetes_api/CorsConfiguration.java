package mx.pedraza.kubernetes_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Customizes the Spring MVC default settings.
 */
@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {
    
    // Gets the "corsOrigins" parameter from the application properties.
    @Value("${corsOrigins}")
    private String corsOrigins;

    /**
     *  Configure cross origin requests processing.
     * @param registry The object used to setup the configuration.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow access to the specified origins to all paths in the app ("/**").
        registry
            .addMapping("/**")
            .allowedMethods("GET","HEAD","POST","PUT","DELETE")
            .allowedOrigins(corsOrigins);
    }
}