package vn.edu.iuh.fit.apigateway.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
  @Value("${service.authenticate.url}")
  private String authenticateServiceUrl;
  @Bean
  @LoadBalanced
  public WebClient loadBalancedWebClientBuilder() {
    log.info("Create WebClient");
    return WebClient.builder().baseUrl(authenticateServiceUrl).build();
  }

  @Bean
  public AuthenticateClient authenticateClient(WebClient webClient) {
    HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
        .builderFor(WebClientAdapter.create(webClient)).build();
    return httpServiceProxyFactory.createClient(AuthenticateClient.class);
  }


}
