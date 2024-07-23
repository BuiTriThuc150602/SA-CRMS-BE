package vn.edu.iuh.fit.notificationservice.dto.requests;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.iuh.fit.notificationservice.models.EmailRecipient;
import vn.edu.iuh.fit.notificationservice.models.EmailSender;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestToMailService {

  EmailSender sender;
  List<EmailRecipient> to;
  String subject;
  String htmlContent;


}
