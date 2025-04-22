package br.com.lucas.santos.case_itau_notificacao.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class SqsConfig {

    @Value("${aws.endpoint.sqs}")
    private String endpoint;

    @Bean
    public SqsAsyncClient sqsAsyncClient(){
        return SqsAsyncClient.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.SA_EAST_1)
                .build();
    }
}
