package com.bdd.project.verification_normalization.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties()
@ConfigurationProperties(prefix = "weatherinfo")
public class ApiConfiguration {
    private String kafkaCheckpointPath;
    private String bootstrapServer;


    private List<String> validWeatherParams = new ArrayList<>();

    public List<String> getValidWeatherParams() {
        return validWeatherParams;
    }

    public void setValidWeatherParams(List<String> validWeatherParams) {
        this.validWeatherParams = validWeatherParams;
    }

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public void setBootstrapServer(String bootstrapServer) {
        this.bootstrapServer = bootstrapServer;
    }


    public String getKafkaCheckpointPath() {
        return kafkaCheckpointPath;
    }

    public void setKafkaCheckpointPath(String kafkaCheckpointPath) {
        this.kafkaCheckpointPath = kafkaCheckpointPath;
    }
}
