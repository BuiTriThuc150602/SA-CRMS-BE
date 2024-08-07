package vn.edu.iuh.fit.authservice.controllers;

import com.nimbusds.jose.JOSEException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.authservice.dto.requests.AuthRegisterRequest;
import vn.edu.iuh.fit.authservice.dto.requests.AuthRequestMapping;
import vn.edu.iuh.fit.authservice.dto.requests.ChangePasswordRequest;
import vn.edu.iuh.fit.authservice.dto.requests.RoleRequest;
import vn.edu.iuh.fit.authservice.dto.responses.ApiResponse;
import vn.edu.iuh.fit.authservice.dto.responses.AuthenticationResponse;
import vn.edu.iuh.fit.authservice.dto.responses.RoleResponse;
import vn.edu.iuh.fit.authservice.dto.responses.ClaimsResponse;
import vn.edu.iuh.fit.authservice.dto.responses.UserResponse;
import vn.edu.iuh.fit.authservice.enums.ErrorCode;
import vn.edu.iuh.fit.authservice.exceptions.AppException;
import vn.edu.iuh.fit.authservice.services.AuthService;
import vn.edu.iuh.fit.authservice.services.JWTService;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private AuthService authService;
  @Autowired
  private JWTService jwtService;

  @PostMapping("/login")
  public ApiResponse<AuthenticationResponse> getToken(@RequestBody AuthRequestMapping authRequest) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequest.getId(),
              authRequest.getPassword()));
      String token = authService.generateToken(authRequest.getId());
      return ApiResponse.<AuthenticationResponse>builder()
          .result(new AuthenticationResponse(token, true))
          .build();

    } catch (AuthenticationException aue) {
      log.error("Error when authenticate: {}", aue.getMessage());
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
  }

  @PostMapping("/add-role")
  @PreAuthorize("hasRole('admin')")
  public ResponseEntity<String> addNewRole(@RequestBody RoleRequest roleRequest) {
    try {
      if (roleRequest.getRoleName() == null || roleRequest.getRoleName().trim().isEmpty()) {
        return ResponseEntity.status(400).body("Role name is required");
      }
      var newRole = authService.saveRole(roleRequest);
      if (newRole == null) {
        return ResponseEntity.status(409).body("Role already exists");
      }
      return ResponseEntity.status(201).body(newRole.getName() + " created");
    } catch (ErrorResponseException e) {
      log.error("Error when add new role: {}", e.getMessage());
      return ResponseEntity.status(500).body(e.getMessage());
    }
  }

  @PostMapping("/register")
  @PreAuthorize("hasRole('admin')")
  public ApiResponse<?> addNewUser(@RequestBody AuthRegisterRequest authRequest) {
    var user = authService.createUser(authRequest);
    Set<RoleResponse> roles = user.getRoles().stream()
        .map(role -> new RoleResponse(role.getName(), role.getDescription()))
        .collect(Collectors.toSet());
    return ApiResponse.<UserResponse>builder()
        .result(new UserResponse(user.getId(), user.getName(), user.getEmail(), roles))
        .build();

  }

  @GetMapping("/get-claims")
  public ApiResponse<?> getClaims(@RequestHeader String Authorization) {
    log.info("Get claims for : {}", Authorization);
    if (Authorization == null) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    } else if (!Authorization.startsWith("Bearer ")) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
    var token = Authorization.substring(7);
    try {
      var signedJWT = jwtService.validateToken(token);
      if (signedJWT == null) {
        throw new AppException(ErrorCode.UNAUTHENTICATED);
      } else {
        log.info("Claims: {}", signedJWT.getJWTClaimsSet().toJSONObject());
        var rolesClaims = signedJWT.getJWTClaimsSet().getStringClaim("roles");
        var rolesClaimsArray = Arrays.asList(rolesClaims.split(","));
        Set<RoleResponse> roles = rolesClaimsArray.stream()
            .map(role -> new RoleResponse(role, ""))
            .collect(Collectors.toSet());
        return ApiResponse.<ClaimsResponse>builder()
            .result(new ClaimsResponse(
                signedJWT.getJWTClaimsSet().getSubject(),
                roles
            ))
            .build();
      }
    } catch (ParseException | JOSEException ex) {
      log.error("Error when get claims: {}", ex.getMessage());
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
  }

  @PostMapping("/change-password")
  public ApiResponse<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
    var result = authService.changePassword(
        changePasswordRequest.getId(),
        changePasswordRequest.getOldPassword(),
        changePasswordRequest.getNewPassword()
    );

    if (result) {
      return ApiResponse.<String>builder()
          .result("Password changed")
          .build();
    } else {
      throw new AppException(ErrorCode.INVALID_OLD_PASSWORD);
    }

  }
}

