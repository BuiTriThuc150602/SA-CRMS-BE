package vn.edu.iuh.fit.configurationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigurationServiceApplication {

  public static void main(String[] args) {
//    print out a variable of application.properties  to check if it is running


    SpringApplication.run(ConfigurationServiceApplication.class, args);
  }

}
