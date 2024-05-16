package vn.edu.iuh.fit.authservice.services;

import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;
import vn.edu.iuh.fit.authservice.dto.requests.AuthRegisterRequest;
import vn.edu.iuh.fit.authservice.dto.requests.RoleRequest;
import vn.edu.iuh.fit.authservice.enums.ErrorCode;
import vn.edu.iuh.fit.authservice.exceptions.AppException;
import vn.edu.iuh.fit.authservice.models.Role;
import vn.edu.iuh.fit.authservice.models.UserCredential;
import vn.edu.iuh.fit.authservice.repositories.RoleRepository;
import vn.edu.iuh.fit.authservice.repositories.UserCredentialRepository;

@Service
@Slf4j
public class AuthService {

  @Autowired
  private UserCredentialRepository userCredentialRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private JWTService jwtService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Transactional
  public UserCredential createUser(AuthRegisterRequest authRequest) {
    log.info("Create user: {}", authRequest);
    if (userCredentialRepository.existsById(authRequest.getId())) {
      throw new AppException(ErrorCode.USER_EXISTED);
    }
    Set<String> roles = authRequest.getRoles();
    Set<Role> roleSet = roleRepository.findByNameIn(roles);
    UserCredential saveUser = new UserCredential(
        authRequest.getId(),
        authRequest.getName(),
        authRequest.getEmail(),
        passwordEncoder.encode(authRequest.getPassword()),
        true,
        roleSet
    );
    return userCredentialRepository.save(saveUser);

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

  public UserCredential getInfo() {
    var context = SecurityContextHolder.getContext();
    var name = context.getAuthentication().getName();
    log.info("User name: {}", name);
    return userCredentialRepository.findByName(name)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
  }

  public String generateToken(String userId) {
    UserCredential userCredential = userCredentialRepository.findById(userId)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    return jwtService.generateToken(userCredential);
  }


}
