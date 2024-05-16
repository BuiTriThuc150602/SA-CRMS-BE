package vn.edu.iuh.fit.studentservice.exceptions;

import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.studentservice.enums.ErrorCode;

@Getter
@Setter
public class AppException extends RuntimeException{
  private ErrorCode errorCode;
  public AppException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
