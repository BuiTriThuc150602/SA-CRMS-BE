package vn.edu.iuh.fit.apigateway.repositories;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;
import vn.edu.iuh.fit.apigateway.dto.responses.ApiResponse;
import vn.edu.iuh.fit.apigateway.dto.responses.ClaimsResponse;
public interface AuthenticateClient {
  @GetExchange(url = "/auth/get-claims", accept = MediaType.APPLICATION_JSON_VALUE)
  Mono<ApiResponse<ClaimsResponse>> getClaims(@RequestHeader List<String> authorization);
}
