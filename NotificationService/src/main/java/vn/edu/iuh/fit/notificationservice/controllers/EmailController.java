package vn.edu.iuh.fit.notificationservice.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.notificationservice.dto.requests.EmailRequestFromClient;
import vn.edu.iuh.fit.notificationservice.dto.responses.ApiResponse;
import vn.edu.iuh.fit.notificationservice.dto.responses.EmailResponse;
import vn.edu.iuh.fit.notificationservice.services.EmailService;

@RestController
@RequestMapping("notifications/email")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
  private EmailService emailService;

  @PostMapping("/send")
  public ApiResponse<EmailResponse> sendEmail(@RequestBody EmailRequestFromClient request) {
    return ApiResponse.<EmailResponse>builder()
        .result(emailService.sendEmail(request))
        .build();
  }


}
