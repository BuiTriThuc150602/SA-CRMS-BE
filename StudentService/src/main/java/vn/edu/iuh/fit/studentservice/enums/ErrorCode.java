package vn.edu.iuh.fit.studentservice.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(3999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(3001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(3002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(3003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(3004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(3005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(3006, "Unauthenticated, Invalid id or password", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(3007, "You do not have permission", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED(3008, "Token expired", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(3009, "Invalid token", HttpStatus.UNAUTHORIZED),
    INVALID_DOB(3010, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST(3011, "Request not match any thing, please check it", HttpStatus.NOT_FOUND),
    ;
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
      this.code = code;
      this.message = message;
      this.statusCode = statusCode;
    }
}
