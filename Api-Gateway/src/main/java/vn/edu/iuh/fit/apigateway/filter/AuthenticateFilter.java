package vn.edu.iuh.fit.apigateway.filter;

import java.net.URI;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class AuthenticateFilter extends AbstractGatewayFilterFactory<AuthenticateFilter.Config> {

  @Autowired
  private WebClient.Builder webClientBuilder;
  @Autowired
  private RouterValidator routerValidator;
  private final Logger log = LoggerFactory.getLogger(AuthenticateFilter.class);

  public AuthenticateFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    WebClient webClient = webClientBuilder.build();
    return (exchange, chain) -> {
      String errorMess = "Forbidden, you don't have permission to access this service.";
      if (!routerValidator.isSecured.test(exchange.getRequest())) {
        return chain.filter(exchange);
      } else if (routerValidator.isInternal.test(exchange.getRequest())) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        var dataBuffer = exchange.getResponse().bufferFactory().wrap(errorMess.getBytes());
        return exchange.getResponse().writeWith(Flux.just(dataBuffer));
      }
      URI uri = exchange.getRequest().getURI();
      HttpHeaders headers = exchange.getRequest().getHeaders();
      log.info("Request to : " + uri.getPath());
      var Authorization = headers.get("Authorization");
      if (Authorization == null || Authorization.isEmpty()) {
        errorMess = "Unauthorized, you need to login to access this service.";
        var dataBuffer = exchange.getResponse().bufferFactory().wrap(errorMess.getBytes());
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        return exchange.getResponse().writeWith(Flux.just(dataBuffer));
      }
      return webClient.get()
          .uri("http://AUTHENTICATESERVICE/auth/get-claims")
          .headers(httpHeaders -> httpHeaders.addAll(headers))
          .retrieve()
          .bodyToMono(Map.class)
          .flatMap(claims -> {
            if (claims == null) {
              String error = "Unauthorized, you need to login to access this service.";
              var dataBuffer = exchange.getResponse().bufferFactory().wrap(error.getBytes());
              exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
              exchange.getResponse().getHeaders().add("Content-Type", "application/json");
              return exchange.getResponse().writeWith(Flux.just(dataBuffer));
            } else {
              List<String> rolesList = (List<String>) claims.get("roles");
//              routers of admin
              if (routerValidator.adminEndpoints.test(exchange.getRequest())) {
                if (!rolesList.contains("admin")) {
                  exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                  return exchange.getResponse().setComplete();
                }
              }
              return chain.filter(exchange);
            }
          });
    };
  }

  public static class Config {

  }

  ;
}
