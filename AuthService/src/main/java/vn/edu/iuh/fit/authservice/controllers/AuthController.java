package vn.edu.iuh.fit.authservice.controllers;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.authservice.dto.AuthRegisterRequest;
import vn.edu.iuh.fit.authservice.dto.AuthRequestMapping;
import vn.edu.iuh.fit.authservice.dto.RoleRequest;
import vn.edu.iuh.fit.authservice.models.Role;
import vn.edu.iuh.fit.authservice.models.UserCredential;
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
  public ResponseEntity<Object> getToken(@RequestBody AuthRequestMapping authRequest) {
    Map<String, String> response = new HashMap<>();
    try {
      Authentication authenticate = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequest.getId(),
              authRequest.getPassword()));
      if (authenticate.isAuthenticated()) {
        String token = authService.generateToken(authRequest.getId());
        response.put("token", token);
        return ResponseEntity.ok(response);
      }

      return ResponseEntity.badRequest().build();
    } catch (AuthenticationException aue) {
      log.error("Error when authenticate: {}", aue.getMessage());
      response.put("error", "Invalid username or password");
      return ResponseEntity.badRequest().body(response);
    }
  }

  @PostMapping("/add-role")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String > addNewRole(@RequestBody RoleRequest roleRequest) {
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
  public ResponseEntity<Object> addNewUser(@RequestBody AuthRegisterRequest authRequest) {
    try {
      return ResponseEntity.status(201).body(authService.saveUser(authRequest));
    } catch (Exception e) {
      if(e instanceof ErrorResponseException && ((ErrorResponseException) e).getStatusCode() == HttpStatusCode.valueOf(409)){
        return ResponseEntity.status(409).body("User already exists");
      }
      return ResponseEntity.status(500).body(e.getMessage());

    }
  }

  @GetMapping("/get-claims")
  public ResponseEntity<Object> getClaims(@RequestHeader String Authorization) {
    if (Authorization == null) {
      throw new ErrorResponseException(HttpStatusCode.valueOf(401));
    } else if (!Authorization.startsWith("Bearer ")) {
      throw new ErrorResponseException(HttpStatusCode.valueOf(401));
    }
    String token = Authorization.substring(7);
    var claims = authService.getClams(token);
    return ResponseEntity.ok(claims);
  }

  @GetMapping("/get-user")
  public ResponseEntity<Object> getUserByToken(@RequestHeader String Authorization) {
    if (Authorization == null) {
      throw new ErrorResponseException(HttpStatusCode.valueOf(401));
    } else if (!Authorization.startsWith("Bearer ")) {
      return ResponseEntity.status(401).body("Token not supported");
    }
    String token = Authorization.substring(7);
    var user = authService.getUserByToken(token);
    return ResponseEntity.ok(user);
  }

}
