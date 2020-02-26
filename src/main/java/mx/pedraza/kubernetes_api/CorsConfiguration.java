package mx.pedraza.kubernetes_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Allows to customize the Spring MVC default settings.
 */
@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {
    
    // Gets the parameter value from the application properties.
    @Value("${corsOrigins}")
    private String corsOrigins;

    /**
     *  Configure cross origin requests processing.
     * @param registry The object used to setup the configuration.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOrigins(corsOrigins);
    }
}