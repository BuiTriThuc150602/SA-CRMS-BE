package vn.edu.iuh.fit.authservice.exceptions;

import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.authservice.enums.ErrorCode;

@Getter
@Setter
public class AppException extends RuntimeException{
  private ErrorCode errorCode;
  public AppException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
