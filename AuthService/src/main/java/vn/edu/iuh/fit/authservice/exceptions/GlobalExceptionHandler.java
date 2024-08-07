package vn.edu.iuh.fit.authservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.iuh.fit.authservice.dto.responses.ApiResponse;
import vn.edu.iuh.fit.authservice.enums.ErrorCode;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  ResponseEntity<ApiResponse<?>> handlingRuntimeException(RuntimeException exception) {
    log.error("Exception: ", exception);

    return ResponseEntity.badRequest().body(ApiResponse.builder()
        .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
        .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
        .build());
  }

  @ExceptionHandler(value = AppException.class)
  ResponseEntity<ApiResponse<?>> handlingAppException(AppException exception) {
    ErrorCode errorCode = exception.getErrorCode();
    log.error("AppException: {}", exception.getMessage());
    return ResponseEntity.status(errorCode.getStatusCode())
        .body(ApiResponse.builder()
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .build());
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(AccessDeniedException exception) {
    ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

    return ResponseEntity.status(errorCode.getStatusCode())
        .body(ApiResponse.builder()
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .build());
  }

  @ExceptionHandler(value = IllegalStateException.class)
  ResponseEntity<ApiResponse<?>> handlingIllegalStateException(IllegalStateException exception) {
    ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
    log.error("IllegalStateException: {}", exception.getMessage());
    return ResponseEntity.status(errorCode.getStatusCode())
        .body(ApiResponse.builder()
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .build());
  }


}
