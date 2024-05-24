package vn.edu.iuh.fit.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import vn.edu.iuh.fit.apigateway.enums.ErrorCode;
import vn.edu.iuh.fit.apigateway.exceptions.AppException;
import vn.edu.iuh.fit.apigateway.service.AuthenticateService;

import java.net.URI;
import java.util.function.Predicate;

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
                        boolean isStudent = claimsResponse.getResult().getRoles().stream()
                                .anyMatch(role -> role.getName().equals("student"));
                        log.info("Is Student: " + isStudent);
                        log.info("Is Admin: " + isAdmin);

                        Predicate<ServerHttpRequest> endpointSelector = null;
                        if (isAdmin) {
                            log.info("Run isAdmin");
                            endpointSelector = routerValidator.adminEndpoints;
                        } else if (isStudent) {
                            log.info("Run isStudent");
                            endpointSelector = routerValidator.studentEndpoints;
                        }

                        if (endpointSelector != null) {
                            boolean matches = endpointSelector.test(exchange.getRequest());
                            log.info("Endpoint: " + matches);
                            if (!matches) {
                                return Mono.error(new AppException(ErrorCode.UNAUTHORIZED));
                            } else {
                                return chain.filter(exchange);
                            }
                        } else {
                            return Mono.error(new AppException(ErrorCode.UNAUTHORIZED));
                        }
                    }).onErrorResume(throwable -> {
                        log.error("Error when authenticate: {}", throwable.getMessage());
                        return Mono.error(new AppException(ErrorCode.UNAUTHENTICATED));
                    });
        };
    }

    public static class Config {

    }
}
