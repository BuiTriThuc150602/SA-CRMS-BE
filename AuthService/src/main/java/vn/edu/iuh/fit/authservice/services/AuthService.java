package vn.edu.iuh.fit.authservice.services;

import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;
import vn.edu.iuh.fit.authservice.dto.AuthRegisterRequest;
import vn.edu.iuh.fit.authservice.dto.RoleRequest;
import vn.edu.iuh.fit.authservice.models.Role;
import vn.edu.iuh.fit.authservice.models.Student;
import vn.edu.iuh.fit.authservice.models.UserCredential;
import vn.edu.iuh.fit.authservice.models.User_Role;
import vn.edu.iuh.fit.authservice.repositories.RoleRepository;
import vn.edu.iuh.fit.authservice.repositories.StudentRepository;
import vn.edu.iuh.fit.authservice.repositories.UserCredentialRepository;
import vn.edu.iuh.fit.authservice.repositories.User_RoleRepository;

@Service
@Slf4j
public class AuthService {

  @Autowired
  private UserCredentialRepository userCredentialRepository;
  @Autowired
  private User_RoleRepository userRoleRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private JWTService jwtService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Transactional
  public UserCredential saveUser(AuthRegisterRequest authRequest) {
    try {
      var existUser = userCredentialRepository.findById(authRequest.getId())
          .orElse(null);

      if (existUser != null) {
        log.error("User {} already exists", authRequest.getId());
        throw new ErrorResponseException(HttpStatusCode.valueOf(409));
      }
      List<String> roles = authRequest.getRoles();
      UserCredential saveUser;
      if (roles.contains("student")) {
        saveUser = new Student();
        saveUser.setId(authRequest.getId());
        saveUser.setName(authRequest.getName());
        saveUser.setEmail(authRequest.getEmail());
        saveUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        saveUser.setActive(true);
      } else {
        saveUser = new UserCredential(
            authRequest.getId(),
            authRequest.getName(),
            authRequest.getEmail(),
            passwordEncoder.encode(authRequest.getPassword()),
            true
        );
      }
      var finalSaveUser = userCredentialRepository.save(saveUser);

      roles.forEach(roleName -> {
        User_Role userRole = new User_Role();
        Role role = roleRepository.findByNameIgnoreCase(roleName)
            .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));
        userRole.setRole(role);
        userRole.setUser(finalSaveUser);
        userRoleRepository.save(userRole);
      });
      return saveUser;
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new ErrorResponseException(HttpStatus.CONFLICT);
    }
  }

  @Transactional
  public Role saveRole(RoleRequest roleRequest) {
    try {
      Role existRole = roleRepository.findByNameIgnoreCase(roleRequest.getRoleName())
          .orElse(null);
      if (existRole == null) {
        Role role = new Role();
        role.setName(roleRequest.getRoleName());
        role.setDescription(roleRequest.getDescription());
        return roleRepository.save(role);
      }
      return null;
    } catch (Exception e) {
      throw new ErrorResponseException(HttpStatus.CONFLICT);
    }
  }

  public Claims getClams(String token) {
    if (!jwtService.validateToken(token)) {
      throw new ErrorResponseException(HttpStatusCode.valueOf(401));
    }
    return jwtService.getClaims(token);
  }

  public Map<String, Object> getUserByToken(String token) {
    var claims = jwtService.getClaims(token);
    var userId = claims.get("userId", String.class);
    List<User_Role> roles = userRoleRepository.findByUser_Id(userId);
    if (roles.isEmpty()) {
      throw new ErrorResponseException(HttpStatusCode.valueOf(401));
    }
    var thisUser = roles.get(0).getUser();
    var userRoles = roles.stream().map(User_Role::getRole).map(Role::getName).toList();
    return Map.of("user", thisUser, "roles", userRoles);
  }


  public String generateToken(String userId) {
    UserCredential userCredential = userCredentialRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    List<User_Role> roles = userRoleRepository.findByUser_Id(userId);
    return jwtService.generateToken(userCredential, roles);
  }


}
