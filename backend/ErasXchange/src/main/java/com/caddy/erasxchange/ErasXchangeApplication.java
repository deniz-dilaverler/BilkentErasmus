package com.caddy.erasxchange;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import com.caddy.erasxchange.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(
        FileStorageProperties.class
)
@OpenAPIDefinition
public class ErasXchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErasXchangeApplication.class, args);
    }

}
