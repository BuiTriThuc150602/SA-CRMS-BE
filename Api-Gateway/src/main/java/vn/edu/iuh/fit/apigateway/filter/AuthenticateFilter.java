package vn.edu.iuh.fit.apigateway.filter;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import vn.edu.iuh.fit.apigateway.enums.ErrorCode;
import vn.edu.iuh.fit.apigateway.exceptions.AppException;
import vn.edu.iuh.fit.apigateway.service.AuthenticateService;

@Component
public class AuthenticateFilter extends AbstractGatewayFilterFactory<AuthenticateFilter.Config> {
  @Autowired
  private RouterValidator routerValidator;
  @Autowired
  private AuthenticateService authenticateService;
  private final Logger log = LoggerFactory.getLogger(AuthenticateFilter.class);

    public AuthenticateFilter() {
        super(Config.class);
    }

  @Override
  public GatewayFilter apply(Config config) {
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
      return authenticateService.getClaims(Authorization)
          .flatMap(claimsResponse -> {
            boolean isAdmin = claimsResponse.getResult().getRoles().stream()
                .anyMatch(role -> role.getName().equals("admin"));
            if (routerValidator.adminEndpoints.test(exchange.getRequest())) {
              if (!isAdmin) {
                return Mono.error(
                    new AppException(ErrorCode.UNAUTHORIZED));
              }
            }
            return chain.filter(exchange);
          }).onErrorResume(throwable -> {
            log.error("Error when authenticate: {}", throwable.getMessage());
            return Mono.error(new AppException(ErrorCode.UNAUTHENTICATED));
          });
    };
  }

    public static class Config {

    }
}
