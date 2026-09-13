package com.barbosacode.delivery.msdelivery.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MsDeliveryTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsDeliveryTrackingApplication.class, args);
    }

}
