package vn.edu.iuh.fit.authservice.dto.Messages;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import vn.edu.iuh.fit.authservice.dto.requests.AuthRegisterRequest;

import java.util.function.Consumer;
import vn.edu.iuh.fit.authservice.services.AuthService;

@Component
@Slf4j
public class UserCreationListener {

  @Autowired
  private AuthService authService;

  @Bean
  public Consumer<UserCreationMessage> createAccount() {
    return message -> {
      var account = new AuthRegisterRequest();
      account.setId(message.getId());
      account.setName(message.getName());
      account.setEmail(message.getEmail());
      account.setPassword(message.getPassword());
      account.setRoles(message.getRoles());
      authService.createUser(account);
    };
  }
}
