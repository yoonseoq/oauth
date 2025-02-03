package com.green.greengram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //인서트나 업뎃 되었을때 활성화되는 이유
@ConfigurationPropertiesScan
@SpringBootApplication
public class GreenGramApplication {
    public static void main(String[] args) {
        SpringApplication.run(GreenGramApplication.class, args);
    }

}
