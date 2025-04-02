package vn.edu.iuh.fit.getwayservice.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import vn.edu.iuh.fit.getwayservice.repositories.UserServiceClient;

import java.util.List;

@Configuration
public class WebClientConfiguration {

    @Value("${app.user-client-url}")
    private String userClientUrl;

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .baseUrl(userClientUrl)
                .build();
    }

    @Bean
    CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(List.of("*")); // Hoặc thay thế bằng domain cụ thể
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowCredentials(true);  // Nếu dùng cookies/token trong request

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(source);
    }

    @Bean
    UserServiceClient identityClient(WebClient webClient){
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient)).build();

        return httpServiceProxyFactory.createClient(UserServiceClient.class);
    }
}