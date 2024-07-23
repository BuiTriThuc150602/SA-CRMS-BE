package vn.edu.iuh.fit.notificationservice.services;

import feign.FeignException;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.notificationservice.dto.requests.EmailRequestFromClient;
import vn.edu.iuh.fit.notificationservice.dto.requests.EmailRequestToMailService;
import vn.edu.iuh.fit.notificationservice.dto.responses.EmailResponse;
import vn.edu.iuh.fit.notificationservice.enums.ErrorCode;
import vn.edu.iuh.fit.notificationservice.exceptions.AppException;
import vn.edu.iuh.fit.notificationservice.models.EmailSender;
import vn.edu.iuh.fit.notificationservice.repositories.httpClient.EmailClient;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailService {
  final EmailClient emailClient;
  @Value("${email.api.key}")
  String apiKey;
  @Value("${email.sender.name}")
  String senderName;
  @Value("${email.sender.mail}")
  String senderEmail;

  public EmailResponse sendEmail(EmailRequestFromClient request) {
    EmailRequestToMailService emailRequestToMailService = EmailRequestToMailService.builder()
        .sender(
            EmailSender.builder().
                name(senderName).
                email(senderEmail)

                .build()
        )
        .to(List.of(request.getTo()))
        .subject(request.getSubject())
        .htmlContent(request.getHtmlContent())
        .build();

    try {
      return emailClient.sendEmail(apiKey, emailRequestToMailService);
    } catch (FeignException e) {
      log.error("Cannot send email {}", e.getMessage());
      throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
    }
  }

}
