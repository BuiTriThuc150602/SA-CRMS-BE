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
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vn.edu.iuh.fit.apigateway.enums.ErrorCode;
import vn.edu.iuh.fit.apigateway.exceptions.AppException;

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
      if (!routerValidator.isSecured.test(exchange.getRequest())) {
        return chain.filter(exchange);
      } else if (routerValidator.isInternal.test(exchange.getRequest())) {
        return Mono.error(new AppException(ErrorCode.UNAUTHORIZED));
      }
      URI uri = exchange.getRequest().getURI();
      HttpHeaders headers = exchange.getRequest().getHeaders();
      log.info("Request to : " + uri.getPath());
      var Authorization = headers.get("Authorization");
      if (Authorization == null || Authorization.isEmpty()) {
        return Mono.error(new AppException(ErrorCode.UNAUTHENTICATED));
      }
      return webClient.get()
          .uri("http://AUTHENTICATESERVICE/auth/get-claims")
          .headers(httpHeaders -> httpHeaders.addAll(headers))
          .retrieve()
          .bodyToMono(Map.class)
          .flatMap(claims -> {
            if (claims == null) {
              return Mono.error(
                  new AppException(ErrorCode.UNAUTHORIZED));
            } else {
              Map<String, Object> body = (Map<String, Object>) claims.get("result");
              List<Map<String, Object>> rolesList = (List<Map<String, java.lang.Object>>) body.get(
                  "roles");
              var isAdmin = rolesList.stream().anyMatch(role -> role.get("name").equals("admin"));
              log.info("Roles: " + rolesList);
              log.info("Is Admin: " + isAdmin);
              if (routerValidator.adminEndpoints.test(exchange.getRequest())) {
                if (!isAdmin) {
                  return Mono.error(
                      new AppException(ErrorCode.UNAUTHORIZED));
                }
              }
              return chain.filter(exchange);
            }
          }).onErrorResume(e -> {
            if (e instanceof AppException) {
              return Mono.error(e);
            }
            return Mono.error(new AppException(ErrorCode.UNAUTHORIZED));
          });
    };
  }

  public static class Config {

  }
}
