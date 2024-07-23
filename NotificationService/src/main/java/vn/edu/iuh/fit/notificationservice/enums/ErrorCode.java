package vn.edu.iuh.fit.notificationservice.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum ErrorCode {

  UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
  INVALID_REQUEST(9011, "Request not match any thing, please check it", HttpStatus.NOT_FOUND),
  CANNOT_SEND_EMAIL(9012, "Cannot send email", HttpStatus.BAD_REQUEST),
  ;

  int code;
  String message;
  HttpStatus statusCode;

  ErrorCode(int code, String message, HttpStatus statusCode) {
    this.code = code;
    this.message = message;
    this.statusCode = statusCode;
  }

}
