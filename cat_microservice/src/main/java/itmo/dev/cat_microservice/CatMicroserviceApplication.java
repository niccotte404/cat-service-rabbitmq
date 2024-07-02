package itmo.dev.cat_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "itmo.dev.cat_microservice.repositories")
@EntityScan(basePackages = "itmo.dev.common.models")
@SpringBootApplication
public class CatMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatMicroserviceApplication.class, args);
    }

}
