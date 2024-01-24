package org.pah_monitoring.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeansProvider {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
