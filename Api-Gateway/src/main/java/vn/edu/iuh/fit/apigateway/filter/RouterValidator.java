package vn.edu.iuh.fit.apigateway.filter;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {

  public static final List<String> openApiEndpoints = List.of(
      "/api/v1/auth/login",
      "/eureka"
  );

  public static final List<String> internalApiEndpoints = List.of(
      "/api/v1/auth/validate"
  );

  public static final List<String> adminApiEndpoints = List.of(
      "/api/v1/auth/register",
      "/api/v1/auth/add-role",
      "/api/v1/students/test"
  );


  public Predicate<ServerHttpRequest> isSecured =
      request -> openApiEndpoints
          .stream()
          .noneMatch(uri -> request.getURI().getPath().contains(uri));

  public Predicate<ServerHttpRequest> isInternal = request -> internalApiEndpoints
      .stream()
      .anyMatch(uri -> request.getURI().getPath().contains(uri));

  public Predicate<ServerHttpRequest> adminEndpoints =
      request -> adminApiEndpoints
          .stream()
          .anyMatch(uri -> request.getURI().getPath().contains(uri));
}
