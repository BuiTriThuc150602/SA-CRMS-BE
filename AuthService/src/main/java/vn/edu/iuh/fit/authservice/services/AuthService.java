package vn.edu.iuh.fit.authservice.services;

import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;
import vn.edu.iuh.fit.authservice.dto.Messages.EmailMessageProducer;
import vn.edu.iuh.fit.authservice.dto.Messages.EmailRecipient;
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
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

  UserCredentialRepository userCredentialRepository;
  RoleRepository roleRepository;
  JWTService jwtService;
  PasswordEncoder passwordEncoder;
  StreamBridge streamBridge;

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
    try {
      UserCredential newUser = userCredentialRepository.save(saveUser);
      EmailMessageProducer emailMessageProducer = EmailMessageProducer.builder()
          .to(EmailRecipient.builder()
              .name(newUser.getName())
              .email(newUser.getEmail())
              .build())
          .subject("Create account")
          .htmlContent(
              "<div style='font-family: Arial, sans-serif; line-height: 1.6; padding: 20px; border: 1px solid #ccc; border-radius: 10px;'>"
                  + "<h1 style='color: #00bcd4; text-align: center;'>Welcome New Student</h1>"
                  + "<p>Dear " + newUser.getName() + ",</p>"
                  + "<p>Your account has been created by the admin of your school.</p>"
                  + "<p>Please login to the school system (CRAMS) with your <b>username</b> and <b>password</b> as your <b>student ID</b>. You may change your password after logging in successfully.</p>"
                  + "<p style='color: #00bcd4; text-align: center;'>Thank you</p>"
                  + "<div style='text-align: center; margin-top: 20px;'>"
                  + "<a href='http://school-login-url' style='background-color: #00bcd4; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Login Now</a>"
                  + "</div>"
                  + "</div>"
          ).build();
      streamBridge.send("sendEmail", emailMessageProducer);
      return newUser;
    } catch (Exception e) {
      throw new AppException(ErrorCode.USER_EXISTED);
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

  /*
  function : Change password of user
  params : userId, oldPassword,newPassword
  return : boolean
  */
  public boolean changePassword(String userId, String oldPassword, String newPassword) {
    Optional<UserCredential> userCredential = userCredentialRepository.findById(userId);
    if (userCredential.isEmpty()) {
      log.error("User not found");
      return false;
    } else {
      if (!passwordEncoder.matches(oldPassword, userCredential.get().getPassword())) {
        log.error("Old password is not correct");
        return false;
      }
      userCredential.get().setPassword(passwordEncoder.encode(newPassword));
      userCredentialRepository.save(userCredential.get());
      EmailMessageProducer emailMessageProducer = EmailMessageProducer.builder()
          .to(EmailRecipient.builder()
              .name(userCredential.get().getName())
              .email(userCredential.get().getEmail())
              .build())
          .subject("Change password")
          .htmlContent(
              """
                  <div style='font-family: Arial, sans-serif; line-height: 1.6; padding: 20px; border: 1px solid #ccc; border-radius: 10px;'>
                  <h1 style='color: #00bcd4; text-align: center;'>Change password</h1>
                  <p>Dear """
                  + userCredential.get().getName() + """
                      ,</p>
                      <p>Your password has been changed successfully.</p>
                      <p>If you did not change your password, please contact the admin of your school.</p>
                      <p style='color: #00bcd4; text-align: center;'>Thank you</p>
                  """
          )
          .build();
      streamBridge.send("sendEmail", emailMessageProducer);
      return true;

    }

  }


}
