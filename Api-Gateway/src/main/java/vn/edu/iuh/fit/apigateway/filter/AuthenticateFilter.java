package vn.edu.iuh.fit.apigateway.filter;

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

import java.net.URI;
import java.util.List;
import java.util.Map;

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
                            var isStudent = rolesList.stream().anyMatch(role -> role.get("name").equals("student"));
                            log.info("Roles: " + rolesList);
                            log.info("Is Admin: " + isAdmin);
                            log.info("Is Student: " + isStudent);
                            if (isAdmin) {
                                // Kiểm tra xem request là endpoint của admin
                                if (routerValidator.adminEndpoints.test(exchange.getRequest())) {
                                    return chain.filter(exchange); // Cho phép truy cập
                                } else {
                                    return Mono.error(new AppException(ErrorCode.UNAUTHORIZED));
                                }
                            } else if (isStudent) { // Kiểm tra nếu người dùng là student
                                // Kiểm tra xem request là endpoint của student
                                if (routerValidator.studentEndpoints.test(exchange.getRequest())) {
                                    return chain.filter(exchange); // Cho phép truy cập
                                } else {
                                    return Mono.error(new AppException(ErrorCode.UNAUTHORIZED));
                                }
                            } else { // Người dùng không phải là admin hoặc student
                                return Mono.error(new AppException(ErrorCode.UNAUTHORIZED));
                            }
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
