package vn.edu.iuh.fit.notificationservice.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.iuh.fit.notificationservice.models.EmailRecipient;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestFromClient {

  EmailRecipient to;
  String subject;
  String htmlContent;


}
