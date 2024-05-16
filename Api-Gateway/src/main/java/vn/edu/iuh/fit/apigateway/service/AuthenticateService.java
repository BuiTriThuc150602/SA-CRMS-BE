package vn.edu.iuh.fit.apigateway.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import vn.edu.iuh.fit.apigateway.dto.responses.ApiResponse;
import vn.edu.iuh.fit.apigateway.dto.responses.ClaimsResponse;
import vn.edu.iuh.fit.apigateway.repositories.AuthenticateClient;

@Service
public class AuthenticateService {
  @Autowired
  private AuthenticateClient authenticateClient;
  public Mono<ApiResponse<ClaimsResponse>> getClaims(List<String> authorization) {
    return authenticateClient.getClaims(authorization);
  }
}
