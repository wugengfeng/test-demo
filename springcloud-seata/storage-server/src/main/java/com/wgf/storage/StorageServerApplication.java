package com.wgf.storage;

import com.wgf.annotation.EnableMicroserviceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMicroserviceClient
@SpringBootApplication
public class StorageServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageServerApplication.class, args);
    }

}
