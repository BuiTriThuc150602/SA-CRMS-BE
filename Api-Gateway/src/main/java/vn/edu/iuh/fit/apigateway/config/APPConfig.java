package vn.edu.iuh.fit.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import vn.edu.iuh.fit.apigateway.repositories.AuthenticateClient;

@Configuration
@Slf4j
public class APPConfig {
  @Bean
  @LoadBalanced
  public WebClient loadBalancedWebClientBuilder() {
    log.info("Create WebClient");
    return WebClient.builder().baseUrl("http://192.168.1.7:8010").build();
  }

  @Bean
  public AuthenticateClient authenticateClient(WebClient webClient) {
    log.info("Create AuthenticateClient");
    HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
        .builderFor(WebClientAdapter.create(webClient)).build();
    return httpServiceProxyFactory.createClient(AuthenticateClient.class);
  }


}
