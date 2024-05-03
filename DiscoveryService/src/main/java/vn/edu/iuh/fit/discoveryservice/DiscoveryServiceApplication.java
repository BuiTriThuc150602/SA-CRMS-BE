package vn.edu.iuh.fit.discoveryservice;

import freemarker.core.Environment;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {


  public static void main(String[] args) {
    SpringApplication.run(DiscoveryServiceApplication.class, args);
  }


}
