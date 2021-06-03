package com.mbss.lordsmobile.tools.config;

import com.mbss.lordsmobile.tools.Constants;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ToolsConfig {

    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .rootUri(Constants.ROOT_URI)
                .build();
    }
}
