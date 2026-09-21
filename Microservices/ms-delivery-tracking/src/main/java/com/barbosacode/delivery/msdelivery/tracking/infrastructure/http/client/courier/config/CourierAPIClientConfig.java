package com.barbosacode.delivery.msdelivery.tracking.infrastructure.http.client.courier.config;

import com.barbosacode.delivery.msdelivery.tracking.infrastructure.http.client.courier.CourierAPIClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class CourierAPIClientConfig {

    @Value("${courier.api.base-url}")
    String baseUrl;

    @Bean
    public CourierAPIClient courierAPIClient() {

        RestClient restClient = RestClient
                .builder()
                .baseUrl(baseUrl)
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);

        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory
                        .builderFor(adapter)
                        .build();

        return factory.createClient(CourierAPIClient.class);
    }
}