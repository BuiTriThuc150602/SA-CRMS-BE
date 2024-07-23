package vn.edu.iuh.fit.notificationservice.dto.messages;

import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import vn.edu.iuh.fit.notificationservice.dto.requests.EmailRequestFromClient;
import vn.edu.iuh.fit.notificationservice.services.EmailService;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SendEmailFromMessage {

  EmailService emailService;

  @Bean
  public Consumer<EmailRequestFromClient> sendEmail() {
    return message -> {
      log.info("Sending email to: " + message.getTo().getEmail());
      emailService.sendEmail(message);
    };
  }


}
